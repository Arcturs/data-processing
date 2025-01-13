package ru.itmo.pddp.asashina.lab4.config;

import lombok.experimental.UtilityClass;
import org.apache.spark.sql.SparkSession;

@UtilityClass
public class SparkConfig {

    private SparkSession INSTANCE;
    private SparkSession STREAMING_INSTANCE;

    public static SparkSession getSparkSession() {
        if (INSTANCE == null) {
            INSTANCE = SparkSession.builder()
                    .appName("SparkTestApp")
                    .master("spark://localhost:7077")
                    .getOrCreate();
        }
        return INSTANCE;
    }

    public static SparkSession getStreamingSession() {
        if (STREAMING_INSTANCE == null) {
            STREAMING_INSTANCE = SparkSession.builder()
                    .appName("SparkStreamTestApp")
                    .config(
                            "spark.jars",
                            "spark-sql-kafka-0-10_2.12-3.2.4.jar,kafka-clients-2.8.1.jar," +
                                    "commons-pool2-2.12.0.jar,spark-streaming-kafka-0-10_2.12-3.2.4.jar," +
                                    "spark-token-provider-kafka-0-10_2.12-3.2.4.jar," +
                                    "build/libs/data-processing-1.0-SNAPSHOT.jar")
                    .master("local[*]")
                    .getOrCreate();
        }
        return STREAMING_INSTANCE;
    }

}
