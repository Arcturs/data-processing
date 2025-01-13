package ru.itmo.pddp.asashina.lab4.task4;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.RelationalGroupedDataset;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import ru.itmo.pddp.asashina.lab4.config.SparkConfig;

public class EventProcessor {

    public RelationalGroupedDataset getGroupedValues() {
        var stream = SparkConfig.getStreamingSession()
                .readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "localhost:9092")
                .option("subscribe", "book.reviews")
                .load();
        var schema = DataTypes.createStructType(new StructField[]{
                DataTypes.createStructField("title",  DataTypes.StringType, true),
                DataTypes.createStructField("reviewScore",  DataTypes.DoubleType, true),
                DataTypes.createStructField("reviewTime",  DataTypes.LongType, true)
        });
        var decoded_dataset = stream.select(
                functions.from_json(new Column("value").cast("string"), schema)
                        .alias("data"))
                .select("data.*")
                .withColumn("timestamp", functions.current_timestamp());
        return decoded_dataset
                .groupBy(functions.window(new Column("timestamp"), "1 minute"));
    }

}
