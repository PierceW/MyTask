package com.akazam.test.rdpc;


import org.apache.storm.Config;
import org.apache.storm.security.auth.SimpleTransportPlugin;
import org.apache.storm.utils.DRPCClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author Alex
 * @ClassName ExecuteDrpc
 * @description
 * @date 2019/1/29 11:00
 */
public class ExecuteDrpc {
    private static Logger logger = LoggerFactory.getLogger(ExecuteDrpc.class);

    public static void main(String[] args) throws Exception {
        Config conf = new Config();
        conf.setMaxSpoutPending(20);
        conf.put(Config.NIMBUS_SEEDS, Arrays.asList("cdh003.bigdata.com"));
        conf.put(Config.NIMBUS_THRIFT_PORT, 6627);
        conf.put(Config.STORM_ZOOKEEPER_SERVERS, Arrays.asList("cdh003.bigdata.com"));
        conf.put(Config.STORM_ZOOKEEPER_PORT, 2181);

        //NOTE 要设置Config.DRPC_THRIFT_TRANSPORT_PLUGIN属性，不然client直接跑空指针
        conf.put(Config.DRPC_THRIFT_TRANSPORT_PLUGIN, SimpleTransportPlugin.class.getName());
        conf.put(Config.STORM_NIMBUS_RETRY_TIMES, 3);
        conf.put(Config.STORM_NIMBUS_RETRY_INTERVAL, 10000);
        conf.put(Config.STORM_NIMBUS_RETRY_INTERVAL_CEILING, 10000);
        conf.put(Config.DRPC_MAX_BUFFER_SIZE, 104857600); // 100M

        DRPCClient client = new DRPCClient(conf, "cdh003.bigdata.com", 3772);
        for (int i = 0; i < 100; i++) {
            System.out.println("----------------------------------------- " + i + " -----------------------------------------");
            System.out.println("DRPC Result: " + client.execute("words", "cat the dog jumped"));
            Thread.sleep(1000);
        }
    }
}
