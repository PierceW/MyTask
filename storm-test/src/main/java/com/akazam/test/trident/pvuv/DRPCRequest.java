package com.akazam.test.trident.pvuv;

import org.apache.storm.Config;
import org.apache.storm.security.auth.SimpleTransportPlugin;
import org.apache.storm.utils.DRPCClient;

import java.util.Arrays;

/**
 * @author Alex
 * @ClassName DRPCRequest
 * @description
 * @date 2019/2/1 17:59
 */
public class DRPCRequest {

    private static final String STORM_SERVER = "192.168.33.212";
    private static final Integer STORM_PORT = 6627;
    private static final String[] ZOOKEEPER_SERVER = {"192.168.33.212"};
    private static final Integer ZOOKEEPER_PORT = 2181;
    private static final String STORM_DRPC_SERVER = "192.168.33.212";
    private static final Integer STORM_DRPC_PORT = 3772;

    public static void main(String[] args) throws Exception {
        Config conf = new Config();
        conf.setMaxSpoutPending(5);


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

        DRPCClient drpc = new DRPCClient(conf, STORM_DRPC_SERVER, STORM_DRPC_PORT);
        for (int i = 0; i < 1000; i++) {
            System.out.println("DRPC RESULT 20190201_01: " + drpc.execute("PVCount", "20190201_01"));
            System.out.println("DRPC RESULT 20190201_08: " + drpc.execute("PVCount", "20190201_08"));
            System.out.println("DRPC RESULT 20190201_16: " + drpc.execute("PVCount", "20190201_16"));
            System.out.println("DRPC RESULT 20190201_24: " + drpc.execute("PVCount", "20190201_24"));
            Thread.sleep(10000);
        }
    }
}
