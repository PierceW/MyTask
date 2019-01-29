package com.akazam.test.spout;

import com.akazam.test.common.CommonData;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author Alex
 * @ClassName SourceSpout
 * @description
 * @date 2019/1/28 13:54
 */
public class SourceSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;

    ConcurrentLinkedDeque<String> queue = new ConcurrentLinkedDeque<>();

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        String[] timeArray = {"2019-01-28 13:55:26", "2019-01-28 10:55:26", "2019-01-28 03:55:26", "2019-01-28 22:55:26"};
        String[] sidArray = {"ABYH6Y4V4SCV00", "ABYH6Y4V4SCV01", "ABYH6Y4V4SCV02", "ABYH6Y4V4SCV03"};
        String[] urlArray = {"http://www.jd.com/1.html", "http://www.jd.com/2.html", "http://www.jd.com/3.html"
                , "http://www.jd.com/4.html"};

        for (int i = 0; i < 60000; i++) {
            Random r = new Random();
            int k = r.nextInt(4);
            queue.add(timeArray[k] + "\t" + sidArray[k] + "\t" + urlArray[k]);
        }
        this.collector = collector;
    }

    @Override
    public void nextTuple() {
        if (queue.size() > 0) {
            String line = queue.poll();
            this.collector.emit(new Values(line));
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(CommonData.LINE));
    }
}
