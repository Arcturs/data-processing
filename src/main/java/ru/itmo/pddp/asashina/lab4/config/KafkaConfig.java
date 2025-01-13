package ru.itmo.pddp.asashina.lab4.config;

import lombok.experimental.UtilityClass;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Map;

@UtilityClass
public class KafkaConfig {

    public static Map<String, Object> getKafkaParams() {
        return Map.of(
                "bootstrap.servers", "localhost:9092",
                "key.deserializer", StringDeserializer.class,
                "value.deserializer", StringDeserializer.class,
                "group.id", "books.reviews.1",
                "auto.offset.reset", "latest",
                "enable.auto.commit", true);
    }

}
