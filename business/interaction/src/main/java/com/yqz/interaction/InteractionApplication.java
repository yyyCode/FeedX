package com.yqz.interaction;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDubbo
@ComponentScan(value = {"com.yqz.interaction", "com.yqz"})
public class InteractionApplication {

    public static void main(String[] args) {
        SpringApplication.run(InteractionApplication.class, args);
    }

}
