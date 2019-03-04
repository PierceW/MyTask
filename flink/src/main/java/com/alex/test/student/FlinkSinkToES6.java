package com.alex.test.student;

import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch.util.RetryRejectedExecutionFailureHandler;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @ClassName FlinkSinkToES6
 * @Description TODO
 * @Author Alex
 * @Date 2019/3/4 16:00
 * @Version 1.0
 **/
public class FlinkSinkToES6 {
    private static final Logger log = LoggerFactory.getLogger(FlinkSinkToES6.class);

    private static final String READ_TOPIC = "student";

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.33.212:9092");
        props.put("zookeeper.connect", "192.168.33.212:2181");
        props.put("group.id", "student-group-1");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest");

        DataStreamSource<String> student = env.addSource(
                //这个 kafka topic 需要和上面的工具类的 topic 一致
                new FlinkKafkaConsumer011<>(READ_TOPIC, new SimpleStringSchema(), props))
                .setParallelism(1);
//                .map(string -> JSON.parseObject(string, Student.class)); //Fastjson 解析字符串成 student 对象
        student.print();
        log.info("student:" + student);
        List<HttpHost> esHttphost = new ArrayList<>();
        esHttphost.add(new HttpHost("192.168.33.212", 9200, "http"));

        ElasticsearchSink.Builder<String> esSinkBuilder = new ElasticsearchSink.Builder<>(
                esHttphost,
                new ElasticsearchSinkFunction<String>() {

                    public IndexRequest createIndexRequest(String element) {
                        Map<String, String> json = new HashMap<>();
                        json.put("data", element);
                        log.info("data:" + element);

                        return Requests.indexRequest()
                                .index("index-student")
                                .type("student")
                                .source(json);
                    }

                    @Override
                    public void process(String element, RuntimeContext ctx, RequestIndexer indexer) {
                        indexer.add(createIndexRequest(element));
                    }
                }
        );

        esSinkBuilder.setBulkFlushMaxActions(1);
//        esSinkBuilder.setRestClientFactory(
//                restClientBuilder -> {
//                    restClientBuilder.setDefaultHeaders()
//                }
//        );
        esSinkBuilder.setRestClientFactory(new RestClientFactoryImpl());
        esSinkBuilder.setFailureHandler(new RetryRejectedExecutionFailureHandler());

        student.addSink(esSinkBuilder.build());
        env.execute("flink learning connectors kafka");
    }
}
