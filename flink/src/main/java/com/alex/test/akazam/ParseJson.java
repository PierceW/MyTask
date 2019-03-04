package com.alex.test.akazam;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ActionRequestFailureHandler;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch6.RestClientFactory;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.util.Collector;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestClientBuilder;

import java.net.InetAddress;
import java.net.InetSocketAddress;
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

        FlinkKafkaConsumer011<String> myConsumer = new FlinkKafkaConsumer011<>("wiki-result", new SimpleStringSchema(), properties);
        myConsumer.setStartFromEarliest();
//        myConsumer.setStartFromLatest();


        // You can also specify the exact offsets the consumer should start from for each partition:
        // 指定消费情况
//        Map<KafkaTopicPartition, Long> specificStartOffsets = new HashMap<>();
//        specificStartOffsets.put(new KafkaTopicPartition("myTopic", 0), 23L);
//        specificStartOffsets.put(new KafkaTopicPartition("myTopic", 1), 31L);
//        specificStartOffsets.put(new KafkaTopicPartition("myTopic", 2), 43L);
//        myConsumer.setStartFromSpecificOffsets(specificStartOffsets);


        DataStream<PraseJsonEntity> stream = env.addSource(myConsumer).flatMap(new ParseJSON());

        // 数据写入ES
        Map<String, String> config = new HashMap<>();
        // This instructs the sink to emit after every element, otherwise they would be buffered
        config.put("bulk.flush.max.actions", "10");
        config.put("cluster.name", "elasticsearch");

        List<InetSocketAddress> transports = new ArrayList<>();
        transports.add(new InetSocketAddress(InetAddress.getByName("192.168.33.212"), 9300));

//        stream.addSink(esSinkBuilder.build());

//        stream.addSink(new ElasticsearchSink<PraseJsonEntity>(config, transports, new ParseJSON()));

        stream.print();

        env.execute("flink learning connectors es6");
    }

    public static class ResetClient implements RestClientFactory {
        @Override
        public void configureRestClientBuilder(RestClientBuilder restClientBuilder) {
            Header[] headers = new BasicHeader[]{new BasicHeader("Content-Type","application/json")};
            restClientBuilder.setDefaultHeaders(headers); //以数组的形式可以添加多个header
            restClientBuilder.setMaxRetryTimeoutMillis(90000);
        }
    }


    public static class FailureAction implements ActionRequestFailureHandler {
        @Override
        public void onFailure(ActionRequest actionRequest, Throwable throwable, int i, RequestIndexer requestIndexer) throws Throwable {

        }
    }


    public static class JsonInserter implements ElasticsearchSinkFunction<PraseJsonEntity> {

        @Override
        public void process(PraseJsonEntity praseJsonEntity, RuntimeContext runtimeContext, RequestIndexer indexer) {
            Map<String, Object> json = praseJsonEntity.getObjectMap();
            IndexRequest request = Requests.indexRequest().index(praseJsonEntity.getName()).type(praseJsonEntity.getName()).source(json);
            indexer.add(request);
        }
    }

    public static class ParseJSON implements FlatMapFunction<String, PraseJsonEntity> {

        @Override
        public void flatMap(String s, Collector<PraseJsonEntity> collector) throws Exception {
            PraseJsonEntity jsonEntity = new PraseJsonEntity();
            /**
             * 需要解析string数据,把数据放到entity里面
             */
            collector.collect(jsonEntity);
        }
    }


}
