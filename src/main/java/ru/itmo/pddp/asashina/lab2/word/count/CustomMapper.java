package ru.itmo.pddp.asashina.lab2.word.count;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class CustomMapper extends Mapper<Object, Text, Text, IntWritable> {

    private static final IntWritable ONE = new IntWritable(1);

    private final Text word = new Text();

    @Override
    protected void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context)
            throws IOException, InterruptedException {

        var input = value.toString()
                .trim()
                .toLowerCase()
                .split("\\W+");
        for (var in : input) {
            word.set(in);
            context.write(word, ONE);
        }
    }

}
