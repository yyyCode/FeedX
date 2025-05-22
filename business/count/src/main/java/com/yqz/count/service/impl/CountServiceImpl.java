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

/**
 * @Description: TODO
 * @Author MiSinG
 * @Date 2024/3/17
 * @Version V1.0
 **/

@Service
@DubboService(interfaceClass = CountServiceIRPC.class)
public class CountServiceImpl implements CountService, CountServiceIRPC {


    @Override
    public Long getLikeCountByItemId(Long itemId) {
        return 0L;
    }

    @Override
    public Long getCollectCountByItemId(Long itemId) {
        return 0L;
    }

    @Override
    public Long getCommentCountByItemId(Long itemId) {
        return 0L;
    }

    @Override
    public Long getViewCountByItemId(Long itemId) {
        return 0L;
    }

    @Override
    public StatisticsDTO getStatisticsDTO(Long itemId) {
        return null;
    }

    @Override
    public Integer like(LikeBo likeBo) {
        return 0;
    }
}
