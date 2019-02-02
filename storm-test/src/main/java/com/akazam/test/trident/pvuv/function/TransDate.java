package com.akazam.test.trident.pvuv.function;

import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;

/**
 * @author Alex
 * @ClassName TransDate
 * @description
 * @date 2019/2/1 17:47
 */
public class TransDate extends BaseFunction {
    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        String val = tuple.getString(0);
        System.out.println("TransDate emit data: --> " + val);
        collector.emit(new Values(val));
    }
}
