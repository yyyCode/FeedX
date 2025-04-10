package com.yqz.count;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDubbo
@ComponentScan(value = {"com.yqz.count", "com.yqz"})
public class CountApplication {

    public static void main(String[] args) {
        SpringApplication.run(CountApplication.class, args);
    }

}
