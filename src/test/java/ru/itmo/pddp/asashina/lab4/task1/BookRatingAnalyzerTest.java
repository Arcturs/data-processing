package ru.itmo.pddp.asashina.lab4.task1;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.Test;

import java.io.File;

class BookRatingAnalyzerTest {

    private final BookRatingAnalyzer bookRatingAnalyzer = new BookRatingAnalyzer();

    @Test
    void analyzeTest() {
        File testFile = new File("/Users/a.sashina/IdeaProjects/data-processing/data/Books_rating.csv");
        System.out.println("--- Задание №1 Аналитика данных ---");
        System.out.println("Размер оригинального файла: " + testFile.length() / 1024 + " Кб");

        long start = System.currentTimeMillis();
        Dataset<Row> result = bookRatingAnalyzer.analyze();
        long time = System.currentTimeMillis() - start;
        System.out.println("Время анализа файла: " + time + " мс");

        System.out.println("Количество книг с рейтингом более 4.0: " + result.count());
        System.out.println("Подробный вывод: " + result.showString(90, 0, true));
    }

}
