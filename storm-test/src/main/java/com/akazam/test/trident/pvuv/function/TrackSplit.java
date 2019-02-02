package com.akazam.test.trident.pvuv.function;

import org.apache.commons.lang.StringUtils;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;

/**
 * @author Alex
 * @ClassName TrackSplit
 * @description
 * @date 2019/1/30 17:05
 */
public class TrackSplit extends BaseFunction {
    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        String sentence = tuple.getString(0);
        if (StringUtils.isNotBlank(sentence)) {
            String[] items = sentence.split(" ");
            if (items.length == 5) {
                String userId = items[0];
                String url = items[1];
                String buttonPosition = items[2];
                String date = items[3];
                String hour = items[4];
                collector.emit(new Values(userId, url, buttonPosition, date + "_" + hour));
            }
        }
    }
}
