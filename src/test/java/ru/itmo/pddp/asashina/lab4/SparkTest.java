package ru.itmo.pddp.asashina.lab4;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.Test;
import ru.itmo.pddp.asashina.lab4.task1.BookRatingAnalyzer;
import ru.itmo.pddp.asashina.lab4.task2.BookRatingProcessor;

import java.io.File;

class SparkTest {

    private final BookRatingAnalyzer bookRatingAnalyzer = new BookRatingAnalyzer();
    private final BookRatingProcessor bookRatingProcessor = new BookRatingProcessor();

    @Test
    void analyzeTest() {
        File testFile = new File("/Users/a.sashina/IdeaProjects/data-processing/data/Books_rating.csv");
        System.out.println("--- Задание №1 Аналитика данных ---");
        System.out.println("Размер оригинального файла: " + testFile.length() / 1024 + " Кб");

        Dataset<Row> result = bookRatingAnalyzer.analyze();

        System.out.println("Количество книг с рейтингом более 4.0: " + result.count());
        System.out.println("Подробный вывод: " + result.showString(90, 0, true));
    }

    @Test
    void processTest() {
        System.out.println("--- Задание №2 Очистка и преобразование данных ---");
        System.out.println("Количество записей в оригинальном датасете: " + bookRatingProcessor.count());

        Dataset<Row> result = bookRatingProcessor.process();

        System.out.println("Количество записей после преобразования данных: " + result.count());
        System.out.println(
                "Некоторый вывод преобразованных данных: "
                        + result.select("Title", "Price", "User_id", "profileName")
                            .showString(25, 0, true));
    }

}
