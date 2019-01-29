package com.akazam.test.topology;

import com.akazam.test.bolt.UVDeepVisitBolt;
import com.akazam.test.bolt.UVFmtBolt;
import com.akazam.test.bolt.UVSumBolt;
import com.akazam.test.spout.SourceSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alex
 * @ClassName UVTopology
 * @description
 * @date 2019/1/28 9:53
 */
public class UVTopology {

    public static final String SPOUT_ID = SourceSpout.class.getSimpleName();
    public static final String UV_FMT_BOLT_ID = UVFmtBolt.class.getSimpleName();
    public static final String UV_DEEP_VISIT_BOLT_ID = UVDeepVisitBolt.class.getSimpleName();
    public static final String UV_SUM_BOLT_ID = UVSumBolt.class.getSimpleName();

    public static Map<String, Object> getConfig() {
        Map<String, Object> conf = new HashMap<>();
//        conf.put(Config.TOPOLOGY_RECEIVER_BUFFER_SIZE , 8);
        conf.put(Config.TOPOLOGY_TRANSFER_BUFFER_SIZE, 32);
        conf.put(Config.TOPOLOGY_EXECUTOR_RECEIVE_BUFFER_SIZE, 16384);
        conf.put(Config.TOPOLOGY_EXECUTOR_SEND_BUFFER_SIZE, 16384);
        return conf;
    }

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout(SPOUT_ID, new SourceSpout(), 1);
        builder.setBolt(UV_FMT_BOLT_ID, new UVFmtBolt(), 4).shuffleGrouping(SPOUT_ID);
        builder.setBolt(UV_DEEP_VISIT_BOLT_ID, new UVDeepVisitBolt(), 4)
                .fieldsGrouping(UV_FMT_BOLT_ID, new Fields("date", "sid"));
        builder.setBolt(UV_SUM_BOLT_ID, new UVSumBolt(), 1).shuffleGrouping(UV_DEEP_VISIT_BOLT_ID);

        Map<String, Object> conf = getConfig();
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(UVTopology.class.getSimpleName(), conf, builder.createTopology());
    }
}
