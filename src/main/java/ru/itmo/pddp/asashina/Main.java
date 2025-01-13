package ru.itmo.pddp.asashina;

import org.apache.spark.sql.streaming.StreamingQueryException;
import ru.itmo.pddp.asashina.lab4.task4.EventProcessor;

import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) throws TimeoutException, StreamingQueryException {
        System.out.println("--- Задание №4 Обработка данных в реальном времени ---");
        var groupedValues = new EventProcessor().getGroupedValues();
        var countEvents = groupedValues.count();
        var meanScore = groupedValues.mean("reviewScore");

        var countEventsStream = countEvents.writeStream()
                .outputMode("complete")
                .format("console")
                .start();
        var meanScoreStream = meanScore.writeStream()
                .outputMode("complete")
                .format("console")
                .start();

        countEventsStream.awaitTermination();
        meanScoreStream.awaitTermination();
    }

}
