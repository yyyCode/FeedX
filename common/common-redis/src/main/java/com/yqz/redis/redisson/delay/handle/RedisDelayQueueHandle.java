package com.yqz.redis.redisson.delay.handle;
public interface RedisDelayQueueHandle<T> {

    /**
     * 延迟队列执行器
     * @param t 参数
     */
    void execute(T t);

}
