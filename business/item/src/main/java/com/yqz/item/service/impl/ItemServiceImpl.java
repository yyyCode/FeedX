package com.yqz.item.service.impl;

import cn.hutool.core.lang.hash.Hash32;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yqz.core.utils.bean.BeanUtils;
import com.yqz.interaction.api.FollowServiceIRPC;
import com.yqz.item.api.ItemServiceIRPC;
import com.yqz.item.dto.ItemDTO;
import com.yqz.item.mapper.ItemMapper;
import com.yqz.item.po.Item;
import com.yqz.item.service.ItemService;
import com.yqz.minio.MinioUtil;
import com.yqz.redis.service.RedisService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author 86139
 * @description 针对表【item】的数据库操作Service实现
 * @createDate 2024-02-26 17:34:23
 */
@Service
@DubboService
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item>
        implements ItemServiceIRPC, ItemService {

    @Autowired
    private MinioUtil minioUtil;


    @Autowired
    private RedisService redisService;

    @Autowired
    @Qualifier(value = "redissonClient")
    private RedissonClient redissonClient;

    @DubboReference
    private FollowServiceIRPC followServiceIRPC;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;



    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public void postItem(MultipartFile file, Item itemBo) throws Exception {
        String filePath= minioUtil.uploadFile(file, file.getOriginalFilename(), file.getContentType());
        Assert.notNull(filePath, "文件上传失败");
        itemBo.setVideoUrl(filePath);
        Assert.isTrue(itemMapper.insert(itemBo)>0,"上传成功");
        // 获取粉丝数量
        Long followerCount = followServiceIRPC.getFollowerCount(itemBo.getUserId());
        if(followerCount>1000L){
            // todo hash分片，避免大Key
            int shardId=calculateShardId(itemBo.getUserId(),itemBo.getId());
            String outBoxKey = String.format("feed:outbox:%s:%d",itemBo.getUserId(),shardId);

            // 是大V，更新到outBox
            RedisTemplate redisTemplate = redisService.redisTemplate;
            redisTemplate.opsForZSet().add(outBoxKey,
                    itemBo.getId(),
                    (double) itemBo.getCreatedAt().toEpochSecond(ZoneOffset.of("+8")));

        }else{
            // 不是大V，更新到粉丝inBox
            List<Long> fansIds = followServiceIRPC.listFansId(itemBo.getUserId());
            CompletableFuture.runAsync(()->fansIds.forEach(fansId -> {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("itemId", itemBo.getId().toString());
                hashMap.put("fanId", fansId.toString());
                hashMap.put("score",Long.valueOf(itemBo.getCreatedAt().toEpochSecond(ZoneOffset.of("+8"))).toString());
                kafkaTemplate.send("user_inbox", JSON.toJSONString(hashMap));
            }));

        }

    }


    // 分片算法：按用户ID和动态ID哈希分片
    private int calculateShardId(Long userId, Long itemId) {
        return (userId.hashCode() ^ itemId.hashCode()) % 10; // 分10片
    }


    @Override
    public List<Item> listItem(String targetId) {
        return List.of();
    }

    @Override
    public ItemDTO getItem(Long id) {
        return null;
    }
}




