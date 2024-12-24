package ru.itmo.pddp.asashina.lab2.web.graph;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class CustomMapper extends Mapper<Object, Text, Text, Text> {

    private final Text key = new Text();
    private final Text value = new Text();

    @Override
    protected void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context)
            throws IOException, InterruptedException {

        String[] input = value.toString().split("\n");
        for (String in : input) {
            String[] parts = in.split("\t");
            this.key.set(parts[1]);
            this.value.set(parts[0]);
            context.write(this.key, this.value);
        }
    }

}
