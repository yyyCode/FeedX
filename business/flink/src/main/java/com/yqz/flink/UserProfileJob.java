package com.yqz.flink;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

public class UserProfileJob {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Kafka 消费配置
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "user-behavior-group");

        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>(
                "user_behavior_topic",
                new SimpleStringSchema(),
                props
        );

        DataStream<String> stream = env.addSource(consumer);

        DataStream<UserBehavior> behaviorStream = stream.map(line -> {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(line, UserBehavior.class);
        });

        // 更新 Redis 中的用户画像
        behaviorStream.map(new UserProfileUpdater());

        env.execute("User Profile Builder");
    }
}
