package com.yqz.redis.redisson.delay;

import com.yqz.core.utils.StringUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
@Slf4j
@Component
@DependsOn("redissonClient")   // ⬅️ 关键
public class RedisDelayQueueUtil {

    @Autowired
    private RedissonClient redissonClient;

    @PostConstruct
    public void init() {
        log.info("RedisDelayQueueUtil init, redissonClient = {}", redissonClient);
    }

    public <T> T getDelayQueue(String queueCode) {
        RBlockingDeque<Map<Object,Object>> blockingDeque =
                redissonClient.getBlockingDeque(queueCode);
        return (T) blockingDeque.poll();
    }
}
