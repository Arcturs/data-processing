package ru.itmo.pddp.asashina.lab4.task3;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import ru.itmo.pddp.asashina.lab4.utils.SparkUtils;

public class BookRatingEDAService {

    public long countBooks() {
        return SparkUtils.getBookRatings()
                .select("Title")
                .distinct()
                .count();
    }

    public Dataset<Row> getAwfulReviewedBooks() {
        return SparkUtils.getBookRatings()
                .na()
                .fill("0.0", new String[] { "review_score" })
                .select("Title", "review_score")
                .groupBy("Title")
                .mean("review_score")
                .filter("avg(review_score)<1.0")
                .select("Title", "avg(review_score)");
    }

    public Dataset<Row> getAwesomeReviewedBooks() {
        return SparkUtils.getBookRatings()
                .na()
                .fill("0.0", new String[] { "review_score" })
                .select("Title", "review_score")
                .groupBy("Title")
                .mean("review_score")
                .filter("avg(review_score)=5.0")
                .select("Title", "avg(review_score)");
    }

    public Dataset<Row> getReviewedBooks(String lowScore, String highScore) {
        return SparkUtils.getBookRatings()
                .na()
                .fill("0.0", new String[] { "review_score" })
                .select("Title", "review_score")
                .groupBy("Title")
                .mean("review_score")
                .filter("avg(review_score)>=" + lowScore + " and avg(review_score)<" + highScore)
                .select("Title", "avg(review_score)");
    }

    public Dataset<Row> getScoreAndPriceRelation() {
        return SparkUtils.getBookRatings()
                .na()
                .fill("0.0", new String[] { "review_score", "Price" })
                .select("Title", "review_score", "Price")
                .groupBy("Title")
                .mean("review_score", "Price")
                .filter("avg(Price) is not null")
                .select("Title", "avg(review_score)", "avg(Price)");
    }

    public Dataset<Row> getScoreAndTitleLengthRelation() {
        return SparkUtils.getBookRatings()
                .na()
                .fill("0.0", new String[] { "review_score" })
                .select("Title", "review_score")
                .groupBy("Title")
                .mean("review_score")
                .withColumn("Title", functions.length(new Column("Title")))
                .select("Title", "avg(review_score)");
    }

}
