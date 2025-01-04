package ru.itmo.pddp.asashina.lab4.task1;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import ru.itmo.pddp.asashina.lab4.config.SparkConfig;

public class BookRatingAnalyzer {

    public Dataset<Row> analyze() {
        return SparkConfig.getSparkSession()
                .read()
                .schema(
                        DataTypes.createStructType(new StructField[]{
                                DataTypes.createStructField("Id", DataTypes.StringType, true),
                                DataTypes.createStructField("Title", DataTypes.StringType, true),
                                DataTypes.createStructField("Price", DataTypes.DoubleType, true),
                                DataTypes.createStructField("User_id", DataTypes.StringType, true),
                                DataTypes.createStructField("profileName", DataTypes.StringType, true),
                                DataTypes.createStructField("review/helpfulness", DataTypes.StringType, true),
                                DataTypes.createStructField("review/score", DataTypes.DoubleType, true),
                                DataTypes.createStructField("review/time", DataTypes.StringType, true),
                                DataTypes.createStructField("review/summary", DataTypes.StringType, true),
                                DataTypes.createStructField("review/text", DataTypes.StringType, true)
                        }))
                .csv("file:///Users/anastasiasasina/IdeaProjects/data-processing/data/Books_rating.csv")
                .select("Title", "review/score")
                .groupBy("Title")
                .mean("review/score")
                .filter("review/score>=4.0")
                .select("Title", "review/score");
    }

}
