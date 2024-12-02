package ru.itmo.pddp.asashina.lab2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class HadoopWordCountService {

    public void count(String inFilePath, String outFilePath)
            throws IOException, InterruptedException, ClassNotFoundException {

        var conf = new Configuration();
        var job = Job.getInstance(conf, "word-count");
        job.setJarByClass(HadoopWordCountService.class);
        job.setMapperClass(CustomMapper.class);
        job.setCombinerClass(CustomReducer.class);
        job.setReducerClass(CustomReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(inFilePath));
        FileOutputFormat.setOutputPath(job, new Path(outFilePath));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}
