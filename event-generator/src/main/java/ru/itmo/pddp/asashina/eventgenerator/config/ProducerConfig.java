package ru.itmo.pddp.asashina.eventgenerator.config;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.itmo.pddp.asashina.eventgenerator.message.EventMessage;

import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;

@Configuration
public class ProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public KafkaTemplate<String, EventMessage> eventKafkaTemplate() {
        return new KafkaTemplate<>(eventProducerFactory());
    }

    @Bean
    public ProducerFactory<String, EventMessage> eventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(
                Map.of(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers),
                new StringSerializer(),
                new JsonSerializer<>());
    }

}
