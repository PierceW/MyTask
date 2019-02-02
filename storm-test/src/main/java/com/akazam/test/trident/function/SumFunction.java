package com.akazam.test.trident.function;

import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;

/**
 * @author Alex
 * @ClassName SumFunction
 * @description
 * @date 2019/1/30 15:50
 */
public class SumFunction extends BaseFunction {
    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        int num1 = tuple.getInteger(0);
        int num2 = tuple.getInteger(1);
        collector.emit(new Values(num1 + num2));
    }
}
