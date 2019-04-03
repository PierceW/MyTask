package com.alex.test.akazam;

import com.alex.test.student.RestClientFactoryImpl;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ActionRequestFailureHandler;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch.util.RetryRejectedExecutionFailureHandler;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.flink.streaming.connectors.elasticsearch6.RestClientFactory;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.util.Collector;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestClientBuilder;

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

        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.33.212:9092");
        props.put("zookeeper.connect", "192.168.33.212:2181");
        props.put("group.id", "student-group-1");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest");

        FlinkKafkaConsumer011<String> myConsumer = new FlinkKafkaConsumer011<>("pub_visit_topic001", new SimpleStringSchema(), props);

        DataStream<PraseJsonEntity> stream = env.addSource(myConsumer).flatMap(new ParseJSON()).setParallelism(5);

        // 数据写入ES
        List<HttpHost> esHttphost = new ArrayList<>();
        esHttphost.add(new HttpHost("192.168.33.20", 9200, "http"));

        ElasticsearchSink.Builder<PraseJsonEntity> esSinkBuilder = new ElasticsearchSink.Builder<>(
                esHttphost,
                new ElasticsearchSinkFunction<PraseJsonEntity>() {

                    public IndexRequest createIndexRequest(PraseJsonEntity element) {
                        Map<String, Object> json = element.getObjectMap();
                        json.put("timestamp", System.currentTimeMillis());
                        json.put("es_timestamp", new Date());

                        return Requests.indexRequest()
                                .index(element.getName().toLowerCase())
                                .type(element.getName().toLowerCase())
                                .source(json);
                    }

                    @Override
                    public void process(PraseJsonEntity element, RuntimeContext ctx, RequestIndexer indexer) {
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

        stream.addSink(esSinkBuilder.build()).setParallelism(5);
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
        public void flatMap(String element, Collector<PraseJsonEntity> collector) throws Exception {
            /**
             * 需要解析string数据,把数据放到entity里面
             */
            List<PraseJsonEntity> entityList = JsonUtil.parseJson(element);
            for (PraseJsonEntity jsonEntity : entityList) {
                collector.collect(jsonEntity);
            }
        }
    }

}
