package com.akazam.test.bolt;

import com.akazam.test.common.CommonData;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alex
 * @ClassName UVDeepVisitBolt
 * @description
 * @date 2019/1/28 14:06
 */
public class UVDeepVisitBolt extends BaseRichBolt {

    OutputCollector collector;

    Map<String, Integer> counts = new HashMap<>();

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String date = input.getStringByField("date");
        String sid = input.getStringByField("sid");
        String key = date.concat(CommonData.UNDER_LINE).concat(sid);
        Integer count = 0;
        count = counts.get(key);
        if (count == null) {
            count = 0;
        }
        count++;
        counts.put(key, count);
        this.collector.emit(new Values(key, count));
        this.collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("date_sid", "count"));
    }
}
