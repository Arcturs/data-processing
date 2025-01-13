package ru.itmo.pddp.asashina.eventgenerator.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.itmo.pddp.asashina.eventgenerator.message.EventMessage;
import ru.itmo.pddp.asashina.eventgenerator.producer.EventProducer;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class EventScheduler {

    private static final Random RANDOM = new Random();
    private static final List<String> BOOK_TITLES = List.of(
            "Book1", "Book2", "Book3",
            "Book4", "Book5", "Book6",
            "Book7", "Book8", "Book9");
    private static final List<Double> REVIEWS = List.of(1.0, 2.0, 3.0, 4.0, 5.0);

    private final EventProducer producer;

    @Scheduled(fixedRateString = "25000")
    public void sendMessageToBecomeLeader() {
        var messageCount = RANDOM.nextInt(7);
        for (var i = 0; i < messageCount; i++) {
            producer.sendMessage(
                    new EventMessage()
                            .setTitle(BOOK_TITLES.get(RANDOM.nextInt(BOOK_TITLES.size())))
                            .setReviewScore(REVIEWS.get(RANDOM.nextInt(REVIEWS.size())))
                            .setReviewTime(System.currentTimeMillis()));
        }
    }

}
