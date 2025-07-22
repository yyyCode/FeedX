package com.yqz.flink;

import org.apache.flink.api.common.functions.RichMapFunction;
import redis.clients.jedis.Jedis;

public  class UserProfileUpdater extends RichMapFunction<UserBehavior, Void> {

    private transient Jedis jedis;

    @Override
    public void open(org.apache.flink.configuration.Configuration parameters) {
        jedis = new Jedis("localhost", 6379);
    }

    @Override
    public Void map(UserBehavior behavior) {
        String key = "user_profile:" + behavior.userId;

        // 统计行为次数
        switch (behavior.action) {
            case "click":
                jedis.hincrBy(key, "click_count", 1);
                break;
            case "like":
                jedis.hincrBy(key, "like_count", 1);
                break;
            case "comment":
                jedis.hincrBy(key, "comment_count", 1);
                break;
        }

        // 增加感兴趣的标签
        if (behavior.tags != null) {
            for (String tag : behavior.tags) {
                jedis.sadd("user_tags:" + behavior.userId, tag);
            }
        }

        return null;
    }

    @Override
    public void close() {
        if (jedis != null) {
            jedis.close();
        }
    }
}