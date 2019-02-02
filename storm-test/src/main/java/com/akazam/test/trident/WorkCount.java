package com.akazam.test.trident;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.security.auth.SimpleTransportPlugin;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.operation.builtin.FilterNull;
import org.apache.storm.trident.operation.builtin.MapGet;
import org.apache.storm.trident.operation.builtin.Sum;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.trident.testing.Split;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.DRPCClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author Alex
 * @ClassName WorkCount
 * @description
 * @date 2019/1/29 10:06
 */
public class WorkCount {

    private static Logger logger = LoggerFactory.getLogger(WorkCount.class);

    private static StormTopology buildTopology(LocalDRPC localDRPC) {
        /*
         * 创建spout
         *
         * **/
        FixedBatchSpout spout = new FixedBatchSpout(new Fields("sentence"), 3,
                new Values("the cow jumped over the moon"),
                new Values("the man went to the store and bought some candy"),
                new Values("four score and seven years ago"),
                new Values("how many apples can you eat"));

        spout.setCycle(true);

        /*
         * 创建topology
         * */
        TridentTopology topology = new TridentTopology();

        TridentState wordCounts = topology.newStream("spout1", spout)
                .each(new Fields("sentence"), new Split(), new Fields("word"))
                .groupBy(new Fields("word"))
                .persistentAggregate(new MemoryMapState.Factory(), new Count(), new Fields("count"))
                .parallelismHint(6);

        /*
         * 创建Stream words， 方法名为 words， 对入参分次 分别获取 words对应的count
         * 然后计算和
         * */
        topology.newDRPCStream("words", localDRPC)
                .each(new Fields("args"), new Split(), new Fields("word"))
                .groupBy(new Fields("word"))
                .stateQuery(wordCounts, new Fields("word"), new MapGet(), new Fields("count"))
                .each(new Fields("count"), new FilterNull())
                .aggregate(new Fields("count"), new Sum(), new Fields("sum"));

        return topology.build();
    }

    public static void main(String[] args) throws Exception {
        Config conf = new Config();
        conf.setMaxSpoutPending(20);

        if (args.length == 0) {
            LocalDRPC drpc = new LocalDRPC();
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("wordCount", conf, buildTopology(drpc));
            for (int i = 0; i < 100; i++) {
                System.out.println("DRPC RESULT: " + drpc.execute("words", "cat the dog jumped"));
                logger.info("DRPC RESULT: " + drpc.execute("words", "cat the dog jumped"));
                Thread.sleep(1000);
            }
        } else {
            conf.put(Config.NIMBUS_SEEDS, Arrays.asList("cdh003.bigdata.com"));
            conf.put(Config.NIMBUS_THRIFT_PORT, 6627);
            conf.put(Config.STORM_ZOOKEEPER_SERVERS, Arrays.asList("cdh003.bigdata.com"));
            conf.put(Config.STORM_ZOOKEEPER_PORT, 2181);

            //NOTE 要设置Config.DRPC_THRIFT_TRANSPORT_PLUGIN属性，不然client直接跑空指针
            conf.put(Config.DRPC_SERVERS, Arrays.asList("cdh003.bigdata.com"));
            conf.put(Config.DRPC_PORT, 3772);
            conf.put(Config.DRPC_THRIFT_TRANSPORT_PLUGIN, SimpleTransportPlugin.class.getName());
            conf.put(Config.STORM_NIMBUS_RETRY_TIMES, 3);
            conf.put(Config.STORM_NIMBUS_RETRY_INTERVAL, 10000);
            conf.put(Config.STORM_NIMBUS_RETRY_INTERVAL_CEILING, 10000);
            conf.put(Config.DRPC_MAX_BUFFER_SIZE, 104857600); // 100M

            StormSubmitter.submitTopology("wordCount", conf, buildTopology(null));
//            DRPCClient client = new DRPCClient(conf, "cdh003.bigdata.com", 3772);
//            for (int i = 0; i < 100; i++) {
//                System.out.println("DRPC Result: " + client.execute("words", "cat the dog jumped"));
//                Thread.sleep(1000);
//            }
        }
    }
}
