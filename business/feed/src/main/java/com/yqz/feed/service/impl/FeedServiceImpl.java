package com.yqz.feed.service.impl;

import com.yqz.count.api.CountServiceIRPC;
import com.yqz.count.dto.StatisticsDTO;
import com.yqz.feed.entity.vo.FeedVo;
import com.yqz.feed.entity.vo.StatisticsVo;
import com.yqz.feed.service.FeedService;
import com.yqz.interaction.api.FollowServiceIRPC;
import com.yqz.item.api.ItemServiceIRPC;
import com.yqz.item.dto.ItemDTO;
import com.yqz.user.api.UserServiceI;
import com.yqz.user.dto.UserDTO;
import org.apache.dubbo.config.annotation.DubboReference;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @Author MiSinG
 * @Date 2024/3/4
 * @Version V1.0
 **/
@Service
public class FeedServiceImpl implements FeedService {

    private static final ExecutorService executor = new ThreadPoolExecutor(
            8,  // corePoolSize (建议 CPU 核数 * 2)
            32, // maxPoolSize (根据 RPC 的阻塞时间调整)
            30, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000), // 避免 OOM
            new ThreadPoolExecutor.CallerRunsPolicy() // 降级策略
    );

    @DubboReference
    private UserServiceI userServiceI;

    @DubboReference
    private ItemServiceIRPC itemServiceIRPC;

    @DubboReference
    private FollowServiceIRPC followServiceIRPC;

    @DubboReference
    private CountServiceIRPC countServiceIRPC;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public List<FeedVo> feed(Long userId, Integer size) {
        /*ArrayList<FeedVo> feedVos = new ArrayList<>();*/
        // TODO: 获取offset
        Long lastOffset = (Long)redisTemplate.opsForValue().get(userId.toString() + ":feed:" + "offset");
        if (lastOffset == null) {
            // offset不存在，说明是第一次获取
            lastOffset = 0L;
        }

        // 从用户的收件箱获取未读内容
        Set<ZSetOperations.TypedTuple<Long>>  itemSet=new HashSet<>();
        Set<ZSetOperations.TypedTuple<Long>> selfSet=redisTemplate.opsForZSet()
                .rangeByScore("feed:inbox:"+userId, lastOffset+1,System.currentTimeMillis());
        if (selfSet != null && !selfSet.isEmpty()) {
            itemSet.addAll(selfSet);
        }

        // 获取大V的id
        List<Long> superId=followServiceIRPC.listSuperId(userId);
        Long finalLastOffset = lastOffset;
        superId.forEach(id ->{
            Set<ZSetOperations.TypedTuple<Long>> fansSet = redisTemplate.opsForZSet()
                    .rangeByScore("feed:outbox:" + id, finalLastOffset + 1, System.currentTimeMillis());
            if (fansSet != null && !fansSet.isEmpty()) {
                itemSet.addAll(fansSet);
            }
        });


        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(userId + ":feed:" + "bloom");
        bloomFilter.tryInit(500000L,0.01);

        List<ZSetOperations.TypedTuple<Long>> sortSet = itemSet.stream()
                .distinct()
                .filter(longTypedTuple -> !bloomFilter.contains(longTypedTuple.getValue()))
                .sorted((o1, o2) -> (int) (o2.getScore() - o1.getScore()))
                .limit(size)
                .peek(e -> bloomFilter.add(e.getValue()))
                .collect(Collectors.toList());


        // 更新offset
        if(!sortSet.isEmpty()) {
            ZSetOperations.TypedTuple<Long> lastTuple = sortSet.get(sortSet.size() - 1);  //获取最后一条信息
            lastOffset = Objects.requireNonNull(lastTuple.getScore()).longValue(); //获取信息的时间戳作为偏移量
            redisTemplate.opsForValue().set(userId + ":feed:" + "offset", lastOffset); //更新redis缓存

        }
/*

        // todo 主要性能瓶颈，想办法解决
        sortSet.forEach(e ->{
            FeedVo feedVo = new FeedVo();
            Long itemId=e.getValue();
            ItemDTO item = itemServiceIRPC.getItem(itemId);
            feedVo.setItem(item);

            UserDTO author = userServiceI.getUserDTOById(item.getUserId());
            feedVo.setAuthor(author);

            StatisticsDTO statisticsDTO = countServiceIRPC.getStatisticsDTO(itemId);
            StatisticsVo statisticsVo = new StatisticsVo();

            feedVos.add(feedVo);
        });


*/

        // 2. 并行获取 FeedVo 数据
        List<CompletableFuture<FeedVo>> futures = sortSet.stream()
                .map(e -> CompletableFuture.supplyAsync(() -> {
                    Long itemId = e.getValue();
                    FeedVo feedVo = new FeedVo();

                    // 并行发起所有 RPC 调用（无嵌套）
                    CompletableFuture<ItemDTO> itemFuture = CompletableFuture.supplyAsync(
                            () -> itemServiceIRPC.getItem(itemId), executor);
                    CompletableFuture<UserDTO> userFuture = CompletableFuture.supplyAsync(
                            () -> userServiceI.getUserDTOById(itemFuture.join().getUserId()), executor);
                    CompletableFuture<StatisticsDTO> statsFuture = CompletableFuture.supplyAsync(
                            () -> countServiceIRPC.getStatisticsDTO(itemId), executor);

                    // 合并结果
                    feedVo.setItem(itemFuture.join());
                    feedVo.setAuthor(userFuture.join());
                    feedVo.setStatistics(StatisticsVo.getStatisticsVo(statsFuture.join()));
                    return feedVo;
                }, executor))
                .collect(Collectors.toList());
                // 3. 等待所有任务完成
        List<FeedVo> feedVos = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());


        kafkaTemplate.send("user_inbox_del", userId + ":" + lastOffset);


        return feedVos;
    }
}
