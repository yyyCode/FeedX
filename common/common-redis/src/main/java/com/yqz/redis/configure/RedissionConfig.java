package com.yqz.redis.configure;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

import org.redisson.config.Config;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.stereotype.Service;


@Slf4j
@Configuration
public class RedissionConfig {
    private final String REDISSON_PREFIX = "redis://";
    private final RedisProperties redisProperties;

    public RedissionConfig(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @Bean(name = "redissonClient")
    public RedissonClient redissonClient() {
        Config config = new Config();
        // 使用Jackson JSON编解码器替代默认的FST编解码器，避免Java 17的反射访问限制
        config.setCodec(new JsonJacksonCodec());

        // 单机模式配置
        String url = REDISSON_PREFIX + redisProperties.getHost() + ":" + redisProperties.getPort();
        config.useSingleServer()
            .setAddress(url)
            .setPassword(redisProperties.getPassword())
            .setDatabase(redisProperties.getDatabase())
            .setPingConnectionInterval(2000);
        config.setLockWatchdogTimeout(10000L);

        try {
            return Redisson.create(config);
        } catch (Exception e) {
            log.error("RedissonClient init redis url:[{}], Exception:", 1, e);
            return null;
        }
    }
}
