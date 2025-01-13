package ru.itmo.pddp.asashina.eventgenerator.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.itmo.pddp.asashina.eventgenerator.message.EventMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventProducer {

    private final KafkaTemplate<String, EventMessage> eventKafkaTemplate;

    public boolean sendMessage(EventMessage message) {
        return eventKafkaTemplate.send("book.reviews", message)
                .handle((ignored, exception) -> {
                    if (exception != null) {
                        log.warn("Произошла ошибка при попытке отправить сообщение в топик book.reviews", exception);
                        return false;
                    }
                    log.info("Отправлено сообщение: {}", message);
                    return true;
                })
                .join();
    }

}
