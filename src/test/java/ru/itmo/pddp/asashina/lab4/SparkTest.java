package ru.itmo.pddp.asashina.lab4;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.Test;
import ru.itmo.pddp.asashina.lab4.task1.BookRatingAnalyzer;
import ru.itmo.pddp.asashina.lab4.task2.BookRatingProcessor;
import ru.itmo.pddp.asashina.lab4.task3.BookRatingEDAService;

import java.io.File;

class SparkTest {

    private final BookRatingAnalyzer bookRatingAnalyzer = new BookRatingAnalyzer();
    private final BookRatingProcessor bookRatingProcessor = new BookRatingProcessor();
    private final BookRatingEDAService bookRatingEDAService = new BookRatingEDAService();

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

    @Test
    void edaTest() {
        System.out.println("--- Задание №3 EDA ---");
        System.out.println("Количество книг, на которые оставили отзывы: " + bookRatingEDAService.countBooks());

        Dataset<Row> result = bookRatingEDAService.getAwfulReviewedBooks();
        System.out.println("Количество книг с рейтингом менее 1.0: " + result.count());
        System.out.println("Частичный вывод: " + result.showString(15, 0, true));

        result = bookRatingEDAService.getReviewedBooks("1.0", "2.0");
        System.out.println("Количество книг с рейтингом более 1.0, но не менее 2.0: " + result.count());
        System.out.println("Частичный вывод: " + result.showString(15, 0, true));

        result = bookRatingEDAService.getReviewedBooks("2.0", "3.0");
        System.out.println("Количество книг с рейтингом более 2.0, но не менее 3.0: " + result.count());
        System.out.println("Частичный вывод: " + result.showString(15, 0, true));

        result = bookRatingEDAService.getReviewedBooks("3.0", "4.0");
        System.out.println("Количество книг с рейтингом более 3.0, но не менее 4.0: " + result.count());
        System.out.println("Частичный вывод: " + result.showString(15, 0, true));

        result = bookRatingEDAService.getReviewedBooks("4.0", "5.0");
        System.out.println("Количество книг с рейтингом более 4.0, но не менее 5.0: " + result.count());
        System.out.println("Частичный вывод: " + result.showString(15, 0, true));

        result = bookRatingEDAService.getAwesomeReviewedBooks();
        System.out.println("Количество книг с рейтингом равным 5.0: " + result.count());
        System.out.println("Частичный вывод: " + result.showString(15, 0, true));

        result = bookRatingEDAService.getScoreAndPriceRelation();
        System.out.println(
                "Соотношение цены и 'качества' книг (полный вывод): "
                        + result.showString(124, 0, true));

        result = bookRatingEDAService.getScoreAndTitleLengthRelation();
        System.out.println(
                "Соотношение 'качества' и количества букв в названии книг (полный вывод): "
                        + result.showString(124, 0, true));
    }

}
