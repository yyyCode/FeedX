package com.yqz.interaction;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RedissonTestRunner implements CommandLineRunner {

    @Autowired(required = false)
    private RedissonClient redissonClient;

    @Override
    public void run(String... args) throws IOException {
        if (redissonClient == null) {
            System.err.println("❌ RedissonClient 未创建成功！");
            System.err.println("可能原因：");
            System.err.println("1. 配置文件中缺少 spring.redis.redisson.config");
            System.err.println("2. 依赖未添加 redisson-spring-boot-starter");
            System.err.println("3. 配置格式错误");
        } else {
            System.out.println("✅ RedissonClient 创建成功！");
            System.out.println("配置信息：" + redissonClient.getConfig().toJSON());
        }
    }
}