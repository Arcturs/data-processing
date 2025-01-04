package ru.itmo.pddp.asashina.lab4.config;

import lombok.experimental.UtilityClass;
import org.apache.spark.sql.SparkSession;

@UtilityClass
public class SparkConfig {

    private static final SparkSession INSTANCE = SparkSession.builder()
            .appName("SparkTestApp")
            .config("spark.jars", "target/data-processing-1.0-SNAPSHOT.jar")
            .master("spark://localhost:7077")
            .getOrCreate();

    public static SparkSession getSparkSession() {
        return INSTANCE;
    }

}
