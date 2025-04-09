package com.yqz.redis.redisson.delay.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component(value = "orderPaymentTimeout")
@Slf4j
public class OrderPaymentTimeout implements RedisDelayQueueHandle<Map<Object,Object>> {
    @Override
    public void execute(Map<Object,Object> map) {
        log.info("(收到订单支付超时延迟消息) {}", map);
        // TODO 订单支付超时，自动取消订单处理业务...
        String id =(String) map.get("orderId");
        String msg =(String) map.get("remark");
        System.out.println("id = " + id);
        System.out.println("msg = " + msg);
    }
}