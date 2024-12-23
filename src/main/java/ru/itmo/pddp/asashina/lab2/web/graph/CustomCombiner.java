package ru.itmo.pddp.asashina.lab2.web.graph;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CustomCombiner extends Reducer<Text, Text, Text, Text> {

    private final Text result = new Text();

    @Override
    protected void reduce(
            Text key, Iterable<Text> values,
            Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {

        var parents = StreamSupport.stream(values.spliterator(), false)
                .map(Text::toString)
                .collect(Collectors.joining(","));
        result.set(parents);
        context.write(key, result);
    }

}
