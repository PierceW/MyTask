package com.akazam.test.bolt;

import com.akazam.test.common.CommonData;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author Alex
 * @ClassName UVFmtBolt
 * @description
 * @date 2019/1/28 14:01
 */
public class UVFmtBolt extends BaseRichBolt {

    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String line = input.getStringByField(CommonData.LINE);
        String date = line.split("\t")[0];
        String sid = line.split("\t")[1];

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.format(new Date(sdf.parse(date).getTime()));

            this.collector.emit(new Values(date,sid));
            this.collector.ack(input);
        } catch (Exception e) {
            e.printStackTrace();
            this.collector.fail(input);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("date", "sid"));
    }
}
