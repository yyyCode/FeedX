package com.yqz.data;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import redis.clients.jedis.Jedis;

public class RedisUserProfileSink implements SinkFunction<UserProfile> {
    private transient Jedis jedis;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void invoke(UserProfile userProfile, Context context) throws Exception {
        if (jedis == null) {
            jedis = new Jedis("localhost", 6379);
        }
        String json = mapper.writeValueAsString(userProfile);
        jedis.set("user_profile:" + userProfile.getUserId(), json);
    }


}
