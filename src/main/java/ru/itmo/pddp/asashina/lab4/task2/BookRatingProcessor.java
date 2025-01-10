package ru.itmo.pddp.asashina.lab4.task2;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import ru.itmo.pddp.asashina.lab4.utils.SparkUtils;

import static org.apache.spark.sql.functions.when;

public class BookRatingProcessor {

    public long count() {
        return SparkUtils.getBookRatings()
                .count();
    }

    public Dataset<Row> process() {
        Dataset<Row> dataset = SparkUtils.getBookRatings();
        Dataset<Row> datasetWithoutNullValues = dataset.filter(dataset.col("User_id").isNotNull());
        Dataset<Row> datasetWithModifiedProfileName = datasetWithoutNullValues.na()
                .fill("Anonymous", new String[] { "profileName" });
        return datasetWithModifiedProfileName
                .withColumn(
                        "Price",
                        when(datasetWithModifiedProfileName.col("Price").isNull(), "0.0")
                                .otherwise(datasetWithModifiedProfileName.col("Price").multiply(110)));
    }

}
