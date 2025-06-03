package com.yqz.count.service.impl;

import com.yqz.count.api.CountServiceIRPC;
import com.yqz.count.batch.BatchUtil;
import com.yqz.count.dto.StatisticsDTO;
import com.yqz.count.entity.LikeBo;
import com.yqz.count.service.CountService;
import org.apache.dubbo.config.annotation.DubboService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Description: TODO
 * @Author MiSinG
 * @Date 2024/3/17
 * @Version V1.0
 **/

@Service
@DubboService(interfaceClass = CountServiceIRPC.class)
public class CountServiceImpl implements CountService, CountServiceIRPC {

    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    @Qualifier(value = "redissonClient")
    private RedissonClient redissonClient;

    private Long itemId;

    private BatchUtil<Integer, Integer> batchUtil;

    @PostConstruct
    private void postConstructorInit() {
        // todo 当请求数量达到20个，或每过5s合并执行一次请求
        batchUtil = BatchUtil.getInstance((input, handlerType) -> {
            System.out.println("处理类型:" + handlerType + ",接受到批量请求参数:" + input);
            int sum = input.stream().mapToInt(x -> x).sum();
            if (sum != 0){
                redisTemplate.opsForValue().increment("item:" + itemId + ":like", sum);
            }
            return null;
        }, 100, 5);
    }

    @Override
    public Integer like(LikeBo likeBo) {
        itemId = likeBo.getItemId();
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(likeBo.getUserId() + ":like:" + "Bloom");
        bloomFilter.tryInit(1000L,0.01);
        if (likeBo.getType() == 1 && !bloomFilter.contains(itemId)) {
            return 1;
        }else if(likeBo.getType() == 0){
            return 0;
        }
        // 兜底
        return null;
    }


    @Override
    public Long getLikeCountByItemId(Long itemId) {
        // 查询item点赞量
        Object o = redisTemplate.opsForValue().get("item:" + itemId + ":like");
        return (Long) o;
    }

    @Override
    public Long getCollectCountByItemId(Long itemId) {
        // 获取item的收藏量
       return (Long) redisTemplate.opsForValue().get("item:" + itemId + ":collect");
    }

    @Override
    public Long getCommentCountByItemId(Long itemId) {
        // 获取item的评论数量
        return (Long) redisTemplate.opsForValue().get("item:" + itemId + ":comment");
    }

    @Override
    public Long getViewCountByItemId(Long itemId) {
        // 获取item的观看数量
        return (Long) redisTemplate.opsForValue().get("item:" + itemId + ":view");
    }

    @Override
    public StatisticsDTO getStatisticsDTO(Long itemId) {
        // 计算统计数据
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setCollectCount(getCollectCountByItemId(itemId));
        statisticsDTO.setCommentCount(getCommentCountByItemId(itemId));
        statisticsDTO.setLikeCount(getLikeCountByItemId(itemId));
        statisticsDTO.setViewCount(getViewCountByItemId(itemId));
        return statisticsDTO;
    }


}
