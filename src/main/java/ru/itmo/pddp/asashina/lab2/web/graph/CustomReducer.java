package ru.itmo.pddp.asashina.lab2.web.graph;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class CustomReducer extends Reducer<Text, Text, Text, Text> {

    private final Text result = new Text();

    @Override
    protected void reduce(
            Text key, Iterable<Text> values,
            Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {

        int count = 0;
        for (Text value : values) {
            count += value.toString().split(",").length;
        }
        result.set(String.valueOf(count));
        context.write(key, result);
    }

}
