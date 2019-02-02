package com.akazam.test.trident.pvuv;

import com.akazam.test.trident.pvuv.function.TrackSplit;
import com.akazam.test.trident.pvuv.function.TransDate;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.security.auth.SimpleTransportPlugin;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.operation.builtin.MapGet;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Arrays;

/**
 * @author Alex
 * @ClassName TridentPVUV
 * @description
 * @date 2019/2/1 16:33
 */
public class TridentPVUV {
    private static final String STORM_SERVER = "192.168.33.212";
    private static final Integer STORM_PORT = 6627;
    private static final String[] ZOOKEEPER_SERVER = {"192.168.33.212"};
    private static final Integer ZOOKEEPER_PORT = 2181;
    private static final String STORM_DRPC_SERVER = "192.168.33.212";
    private static final Integer STORM_DRPC_PORT = 3772;

    public static StormTopology buildTopology(LocalDRPC drpc) {

        // 设置数据，无限循环
        FixedBatchSpout spout = new FixedBatchSpout(new Fields("track"), 5,
                new Values("10954404902 impala.xxx.com/item/283132068 impala 20190201 01"),
                new Values("10954404902 impala.xxx.com/item/283132068 impala 20190201 01"),
                new Values("10954404902 impala.xxx.com/item/283132068 impala 20190201 01"),
                new Values("10954404902 impala.xxx.com/item/283132068 impala 20190201 01"),
                new Values("10954404902 impala.xxx.com/item/283132068 impala 20190201 02"),
                new Values("10954404902 spark.xxx.com/item/28313gdr68 spark 20190201 03"),
                new Values("10954404902 spark.xxx.com/item/28313gdr68 spark 20190201 04"),
                new Values("10954404902 spark.xxx.com/item/28313gdr68 spark 20190201 04"),
                new Values("10954404903 storm.xxx.com/item/283152068 storm 20190201 05"),
                new Values("10954404903 storm.xxx.com/item/283152068 storm 20190201 06"),
                new Values("10954404904 dfsdf.xxx.com/item/28313r068 flink 20190201 07"),
                new Values("10954404904 dfsdf.xxx.com/item/28313r068 flink 20190201 08"),
                new Values("10954404905 hive.xxx.com/item/2833432068 hive 20190201 09"),
                new Values("10954404905 hive.xxx.com/item/2833432068 hive 20190201 09"),
                new Values("10954404904 hawq.xxx.com/item/2831fe068 hawq 20190201 10"),
                new Values("10954404904 hawq.xxx.com/item/2831fe068 hawq 20190201 11"),
                new Values("10954404907 hawq.xxx.com/item/2831fe068 elasticsearch 20190201 12"),
                new Values("10954404907 hawq.xxx.com/item/2831fe068 elasticsearch 20190201 12"),
                new Values("10954404907 hawq.xxx.com/item/2831fe068 elasticsearch 20190201 12"),
                new Values("10954404907 hawq.xxx.com/item/2831fe068 elasticsearch 20190201 12"),
                new Values("10954404907 hawq.xxx.com/item/2831fe068 elasticsearch 20190201 12"),
                new Values("10954404907 hawq.xxx.com/item/2831fe068 elasticsearch 20190201 13"),
                new Values("10954404908 hawq.xxx.com/item/2831fe068 kibana 20190201 14"),
                new Values("10954404908 hawq.xxx.com/item/2831fe068 kibana 20190201 15"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 16"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 16"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 16"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 17"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 18"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 19"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 20"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 20"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 21"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 22"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 22"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 23"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 24")
        );
        spout.setCycle(true);

        TridentTopology topology = new TridentTopology();
        TridentState PVState = topology.newStream("userStats", spout).shuffle()
                .each(new Fields("track"), new TrackSplit(), new Fields("userId", "url", "position", "date"))
                .parallelismHint(2)
                .groupBy(new Fields("date"))
                .persistentAggregate(new MemoryMapState.Factory(), new Count(), new Fields("aggregates_pv"));

        topology.newDRPCStream("PVCount", drpc)
                .each(new Fields("args"), new TransDate(), new Fields("date"))
                .groupBy(new Fields("date"))
                .stateQuery(PVState, new Fields("date"), new MapGet(), new Fields("count"))
                .project(new Fields("date", "count"));


        return topology.build();
    }

    public static StormTopology buildRemoteTopology() {

        // 设置数据，无限循环
        FixedBatchSpout spout = new FixedBatchSpout(new Fields("track"), 5,
                new Values("10954404902 impala.xxx.com/item/283132068 impala 20190201 01"),
                new Values("10954404902 impala.xxx.com/item/283132068 impala 20190201 01"),
                new Values("10954404902 impala.xxx.com/item/283132068 impala 20190201 01"),
                new Values("10954404902 impala.xxx.com/item/283132068 impala 20190201 01"),
                new Values("10954404902 impala.xxx.com/item/283132068 impala 20190201 02"),
                new Values("10954404902 spark.xxx.com/item/28313gdr68 spark 20190201 03"),
                new Values("10954404902 spark.xxx.com/item/28313gdr68 spark 20190201 04"),
                new Values("10954404902 spark.xxx.com/item/28313gdr68 spark 20190201 04"),
                new Values("10954404903 storm.xxx.com/item/283152068 storm 20190201 05"),
                new Values("10954404903 storm.xxx.com/item/283152068 storm 20190201 06"),
                new Values("10954404904 dfsdf.xxx.com/item/28313r068 flink 20190201 07"),
                new Values("10954404904 dfsdf.xxx.com/item/28313r068 flink 20190201 08"),
                new Values("10954404905 hive.xxx.com/item/2833432068 hive 20190201 09"),
                new Values("10954404905 hive.xxx.com/item/2833432068 hive 20190201 09"),
                new Values("10954404904 hawq.xxx.com/item/2831fe068 hawq 20190201 10"),
                new Values("10954404904 hawq.xxx.com/item/2831fe068 hawq 20190201 11"),
                new Values("10954404907 hawq.xxx.com/item/2831fe068 elasticsearch 20190201 12"),
                new Values("10954404907 hawq.xxx.com/item/2831fe068 elasticsearch 20190201 12"),
                new Values("10954404907 hawq.xxx.com/item/2831fe068 elasticsearch 20190201 12"),
                new Values("10954404907 hawq.xxx.com/item/2831fe068 elasticsearch 20190201 12"),
                new Values("10954404907 hawq.xxx.com/item/2831fe068 elasticsearch 20190201 12"),
                new Values("10954404907 hawq.xxx.com/item/2831fe068 elasticsearch 20190201 13"),
                new Values("10954404908 hawq.xxx.com/item/2831fe068 kibana 20190201 14"),
                new Values("10954404908 hawq.xxx.com/item/2831fe068 kibana 20190201 15"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 16"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 16"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 16"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 17"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 18"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 19"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 20"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 20"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 21"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 22"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 22"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 23"),
                new Values("10954404906 hawq.xxx.com/item/2831fe068 logstash 20190201 24")
        );
        spout.setCycle(true);

        TridentTopology topology = new TridentTopology();
        TridentState PVState = topology.newStream("userStats", spout).shuffle()
                .each(new Fields("track"), new TrackSplit(), new Fields("userId", "url", "position", "date"))
                .parallelismHint(2)
                .groupBy(new Fields("date"))
                .persistentAggregate(new MemoryMapState.Factory(), new Count(), new Fields("aggregates_pv"));

        topology.newDRPCStream("PVCount")
                .each(new Fields("args"), new TransDate(), new Fields("date"))
                .groupBy(new Fields("date"))
                .stateQuery(PVState, new Fields("date"), new MapGet(), new Fields("count"))
                .project(new Fields("date", "count"));

        return topology.build();
    }

    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException, InterruptedException {
        Config conf = new Config();
        conf.setMaxSpoutPending(5);
        String topoName = "PVCounter";
        if (args.length == 0) {
            LocalDRPC drpc = new LocalDRPC();
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology(topoName, conf, buildTopology(drpc));

            for (int i = 0; i < 100; i++) {
                System.out.println("DRPC RESULT 20190201_01: " + drpc.execute("PVCount", "20190201_01"));
                System.out.println("DRPC RESULT 20190201_08: " + drpc.execute("PVCount", "20190201_08"));
                System.out.println("DRPC RESULT 20190201_16: " + drpc.execute("PVCount", "20190201_16"));
                System.out.println("DRPC RESULT 20190201_24: " + drpc.execute("PVCount", "20190201_24"));
                Thread.sleep(10000);
            }
        } else {
            conf.setNumWorkers(3);
            conf.put(Config.NIMBUS_SEEDS, Arrays.asList(STORM_SERVER));
            conf.put(Config.NIMBUS_THRIFT_PORT, STORM_PORT);
            conf.put(Config.STORM_ZOOKEEPER_SERVERS, Arrays.asList(ZOOKEEPER_SERVER));
            conf.put(Config.STORM_ZOOKEEPER_PORT, ZOOKEEPER_PORT);

            //NOTE 要设置Config.DRPC_THRIFT_TRANSPORT_PLUGIN属性，不然client直接跑空指针
            conf.put(Config.DRPC_SERVERS, Arrays.asList(STORM_DRPC_SERVER));
            conf.put(Config.DRPC_PORT, STORM_DRPC_PORT);
            conf.put(Config.DRPC_THRIFT_TRANSPORT_PLUGIN, SimpleTransportPlugin.class.getName());
            conf.put(Config.STORM_NIMBUS_RETRY_TIMES, 3);
            conf.put(Config.STORM_NIMBUS_RETRY_INTERVAL, 10000);
            conf.put(Config.STORM_NIMBUS_RETRY_INTERVAL_CEILING, 10000);
            conf.put(Config.DRPC_MAX_BUFFER_SIZE, 104857600); // 100M

            StormSubmitter.submitTopology(topoName, conf, buildRemoteTopology());
        }
    }
}
