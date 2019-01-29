package com.akazam.test.rdpc;

import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.trident.testing.Split;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

/**
 * @author Alex
 * @ClassName TridentTopology
 * @description
 * @date 2019/1/28 18:19
 */
public class TestTridentTopology {
    public static void main(String[] args) {

        // 循环地访问语句集来生成语句数据流
        FixedBatchSpout spout = new FixedBatchSpout(new Fields("sentence"), 3,
                new Values("the cow jumped over the moon"),
                new Values("the man went to the store and bought some candy"),
                new Values("four score and seven years ago"),
                new Values("how many apples can you eat"));
        spout.setCycle(true);


        TridentTopology topology = new TridentTopology();

        /*
         * TridentTopology 有一个叫做 newStream 的方法，这个方法可以从一个输入数据源中读取数据创建一个新的数据流
         * 输入的数据源就是前面定义的 FixedBatchSpout
         * 输入数据源也可以是像 Kestrel 和 Kafka 这样的消息系统
         * Trident 会通过 ZooKeeper 一直跟踪每个输入数据源的一小部分状态（Trident 具体消费对象的相关元数据）
         * 例如这里的 “spout1” 就对应着 ZooKeeper 中的一个节点，而 Trident 就会在该节点中存放数据源的元数据（metadata）
         *
         */
        TridentState wordCounts = topology.newStream("spout1", spout)
                .each(new Fields("sentence"), new Split(), new Fields("word"))
                .groupBy(new Fields("word"))
                /**
                 * 拓扑的剩余部分负责统计单词的数量并将结果保存到持久化存储中。
                 * 首先，数据流根据 “word” 域分组，然后使用 Count聚合器持续聚合每个小组。
                 * persistentAggregate 方法用于存储并更新 state 源中的聚合结果。
                 * 在这个例子中，单词的数量结果是保存在内存中的，不过可以根据需要切换到 Memcached、Cassandra 或者其他持久化存储中。
                 * 切换存储模型也非常简单，
                 * 只需要像下面这样（使用 trident-memcached 修改
                 * persistentAggregate 行中的一个参数（其中，“serverLocations” 是 Memcached 集群的地址/端口列表）即可
                 * .persistentAggregate(MemcachedState.transactional(serverLocations), new Count(), new Fields("count"))
                 * persistentAggregate 方法所存储的值就表示所有从数据流中发送出来的块的聚合结果。
                 *
                 * */
                .persistentAggregate(new MemoryMapState.Factory(), new Count(), new Fields("count"))
                .parallelismHint(6);
        wordCounts.newValuesStream();
    }
}
