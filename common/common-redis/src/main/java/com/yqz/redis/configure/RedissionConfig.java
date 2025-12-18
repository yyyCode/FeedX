package com.yqz.redis.configure;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

import org.redisson.config.Config;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
@Slf4j
@Configuration
public class RedissionConfig {

    private static final String REDIS_ADDRESS = "redis://10.21.32.13:6379";
    private static final int DATABASE = 0;
    private static final String PASSWORD = "12345678"; // 没密码就写 null

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec());

        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(REDIS_ADDRESS)
                .setDatabase(DATABASE)
                .setPingConnectionInterval(2000);

        if (PASSWORD != null && !PASSWORD.isEmpty()) {
            serverConfig.setPassword(PASSWORD);
        }

        config.setLockWatchdogTimeout(10_000L);

        log.info("Redisson hard-code config init, address={}", REDIS_ADDRESS);

        return Redisson.create(config);
    }
}
