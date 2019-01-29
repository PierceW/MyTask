package com.akazam.test.bolt;

import com.akazam.test.common.CommonData;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alex
 * @ClassName UVSumBolt
 * @description
 * @date 2019/1/28 14:17
 */
public class UVSumBolt extends BaseRichBolt {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private OutputCollector collector;
    Map<String, Integer> counts = new HashMap<>();

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        int pv = 0;
        int uv = 0;

        String dateSid = input.getStringByField("date_sid");
        Integer count = input.getIntegerByField("count");

        String currDate = sdf.format(new Date());

        try {
            Date accessDate = sdf.parse(dateSid.split(CommonData.UNDER_LINE)[0]);
            if (!dateSid.startsWith(currDate) && accessDate.after(new Date())) {
                counts.clear();
            }

            counts.put(dateSid, count);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (dateSid.startsWith(currDate)) {
                uv++;
                pv += entry.getValue();
            }
        }

        System.out.println(currDate + "的pv数为"+pv+",uv数为"+uv);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
