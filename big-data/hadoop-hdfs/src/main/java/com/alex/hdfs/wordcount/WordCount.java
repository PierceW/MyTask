package com.alex.hdfs.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;

/**
 * @ClassName WordCount
 * @Description todo
 * @Author AlexWang
 * @Date 2019/6/18 0018 13:57
 * @Version 1.0
 **/

public class WordCount {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        String[] otherStr = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherStr.length< 2) {
            System.out.println("Usage: wordcount <in> [<in>...] <out>");
            System.exit(2);
        }

        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(WordCount.class);
        job.setMapperClass(SplitMap.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        for (int i = 0; i < otherStr.length - 1; i++) {
            FileInputFormat.addInputPath(job, new Path(otherStr[i]));
        }
        FileOutputFormat.setOutputPath(job,
                new Path(otherStr[otherStr.length - 1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
