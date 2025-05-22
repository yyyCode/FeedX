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
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @Author MiSinG
 * @Date 2024/3/4
 * @Version V1.0
 **/
@Service
public class FeedServiceImpl implements FeedService {

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

        return null;
    }
}
