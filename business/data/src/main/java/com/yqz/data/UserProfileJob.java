package com.yqz.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import java.util.*;
import java.util.Properties;

@Component
public class UserProfileJob implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties kafkaProps = new Properties();
        kafkaProps.setProperty("bootstrap.servers", "localhost:9092");
        kafkaProps.setProperty("group.id", "user-profile-group");

        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>(
                "user_behavior",
                (KafkaDeserializationSchema) new SimpleStringSchema(),
                kafkaProps
        );

        DataStream<String> stream = env.addSource(consumer);

        DataStream<UserProfile> userProfileStream = stream
                .map((MapFunction<String, UserProfile>) new UserBehaviorToUserProfileMapper());


        userProfileStream.addSink(new RedisUserProfileSink());

        env.execute("User Profile Builder Job");
    }

    // 假设输入是 JSON，如 {"userId":"u1", "behavior":"click", "category":"sports"}
    public static class UserBehaviorToUserProfileMapper implements MapFunction<String, UserProfile> {
        private final ObjectMapper objectMapper = new ObjectMapper();
        private final Map<String, UserProfile> state = new HashMap<>();

        @Override
        public UserProfile map(String value) throws Exception {
            Map<String, Object> map = objectMapper.readValue(value, Map.class);
            String userId = (String) map.get("userId");
            String behavior = (String) map.get("behavior");
            String category = (String) map.get("category");

            UserProfile profile = state.getOrDefault(userId, new UserProfile(userId, new HashMap<>(), new HashMap<>())) ;

            // 更新行为计数
            profile.getActionCount().merge(behavior, 1, Integer::sum);

            // 更新兴趣分数
            profile.getInterests().merge(category, 1.0, Double::sum);

            state.put(userId, profile);
            return profile;
        }
    }
}

