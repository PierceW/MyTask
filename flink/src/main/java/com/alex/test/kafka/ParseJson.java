package com.alex.test.kafka;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer08;
import org.apache.flink.util.Collector;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.util.*;

/**
 * @ClassName ParseJson
 * @Description TODO
 * @Author Alex
 * @Date 2019/3/1 14:19
 * @Version 1.0
 **/
public class ParseJson {

    public static void main(String[] args) throws Exception {

        final ParameterTool parameterTool = ParameterTool.fromArgs(args);

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(parameterTool);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.33.212:9092");
        properties.setProperty("zookeeper.connect", "192.168.33.212:2181");
        properties.setProperty("group.id", "test");

        FlinkKafkaConsumer08<String> myConsumer = new FlinkKafkaConsumer08<>("wiki-result", new SimpleStringSchema(), properties);
        myConsumer.setStartFromEarliest();
//        myConsumer.setStartFromLatest();


        // You can also specify the exact offsets the consumer should start from for each partition:
        // 指定消费情况
//        Map<KafkaTopicPartition, Long> specificStartOffsets = new HashMap<>();
//        specificStartOffsets.put(new KafkaTopicPartition("myTopic", 0), 23L);
//        specificStartOffsets.put(new KafkaTopicPartition("myTopic", 1), 31L);
//        specificStartOffsets.put(new KafkaTopicPartition("myTopic", 2), 43L);
//        myConsumer.setStartFromSpecificOffsets(specificStartOffsets);



        DataStream<String> stream = env.addSource(myConsumer);

//        DataStream<Tuple2<String, String>> dataStream = stream.flatMap(new ParseJSON());

//        dataStream.print();

        // 数据写入ES
        List<HttpHost> httpHosts = new ArrayList<>();
        httpHosts.add(new HttpHost("192.168.33.212", 9200, "http"));

        ElasticsearchSink.Builder<String> esSinkBuilder = new ElasticsearchSink.Builder<>(
                httpHosts, new ElasticsearchSinkFunction<String>() {
            @Override
            public void process(String element, RuntimeContext ctx, RequestIndexer indexer) {
                indexer.add(createIndexRequest(element));
            }

            public IndexRequest createIndexRequest(String element) {
                Map<String, String> json = new HashMap<>();
                json.put("data", element);

                return Requests.indexRequest().index("my_index").type("my_type").source(json);
            }
        }
        );

        esSinkBuilder.setBulkFlushMaxActions(1);

        esSinkBuilder.setRestClientFactory(
                restClientBuilder -> {
//                    restClientBuilder.setDefaultHeaders()
                    restClientBuilder.setMaxRetryTimeoutMillis(300);
                    restClientBuilder.setPathPrefix("index_");
                }
        );

        stream.addSink(esSinkBuilder.build());


        env.execute("flink learning connectors es6");
    }

    public static class ParseJSON implements FlatMapFunction<String, Tuple2<String, String>> {
        @Override
        public void flatMap(String json, Collector<Tuple2<String, String>> out) throws Exception {
            out.collect(new Tuple2<>("test", json));
        }
    }


}
