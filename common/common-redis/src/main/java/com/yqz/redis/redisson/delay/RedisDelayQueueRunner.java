package com.yqz.redis.redisson.delay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Map;

import java.util.concurrent.TimeUnit;
@Slf4j
@Component
public class RedisDelayQueueRunner {

    @Autowired
    private RedisDelayQueueUtil redisDelayQueueUtil;

    @Autowired
    private ThreadPoolTaskExecutor ptask;

    @EventListener(ApplicationReadyEvent.class)
    public void listen() {
        ptask.execute(() -> {
            while (true) {
                try {
                    Map<Object,Object> value =
                            redisDelayQueueUtil.getDelayQueue("TEST_QUEUE");

                    if (value != null) {
                        log.info("收到延迟消息: {}", value);
                    }

                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (Exception e) {
                    log.error("延迟队列监听异常", e);
                    try {
                        TimeUnit.SECONDS.sleep(2); // ❗ 防止刷日志
                    } catch (InterruptedException ignored) {}
                }
            }
        });

        log.info("Redisson 延迟队列监听启动成功");
    }
}
