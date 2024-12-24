package ru.itmo.pddp.asashina.lab2.web.graph;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class HadoopWebGraphService {

    public static void count(String inFilePath, String outFilePath)
            throws IOException, InterruptedException, ClassNotFoundException {

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "web-graph");
        job.setJarByClass(HadoopWebGraphService.class);
        job.setMapperClass(CustomMapper.class);
        job.setCombinerClass(CustomCombiner.class);
        job.setReducerClass(CustomReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(inFilePath));
        FileOutputFormat.setOutputPath(job, new Path(outFilePath));
        job.waitForCompletion(true);
    }

}
