package com.alex.test.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import scala.Tuple2;

/**
 * @ClassName WordCount
 * @Description TODO
 * @Author Alex
 * @Date 2019/2/19 17:30
 * @Version 1.0
 **/
public class WordCount {

    public static void main(String[] args) {

        final ParameterTool params = ParameterTool.fromArgs(args);

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataStream<String> text;
        if (params.has("input")) {
            text = env.readTextFile(params.get("input"));
        } else {
            text = env.fromElements(WordCountData.WORDS);
        }

        DataStream<Tuple2<String, Integer>> counts = text.flatMap(new Tokenizer())
                .keyBy(0).sum(1);

        if (params.has("input")) {
            counts.writeAsText(params.get("output"));
        } else {
            counts.print();
        }

    }

    public static final class Tokenizer implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> collector) throws Exception {
            String[] tokens = value.toLowerCase().split("\\W+");

            for (String token : tokens) {
                if (token.length() > 0) {
                    collector.collect(new Tuple2<>(token, 1));
                }
            }
        }
    }

}
