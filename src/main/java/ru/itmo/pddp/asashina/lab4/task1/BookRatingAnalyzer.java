package ru.itmo.pddp.asashina.lab4.task1;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import ru.itmo.pddp.asashina.lab4.utils.SparkUtils;

public class BookRatingAnalyzer {

    public Dataset<Row> analyze() {
        return SparkUtils.getBookRatings()
                .select("Title", "review_score")
                .groupBy("Title")
                .mean("review_score")
                .filter("avg(review_score)>=4.0")
                .select("Title", "avg(review_score)");
    }

}
