package com.akazam.test.trident.function;

import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.tuple.TridentTuple;

/**
 * @author Alex
 * @ClassName CheckEvenSumFilter
 * @description
 * @date 2019/1/30 15:41
 */
public class CheckEvenSumFilter extends BaseFilter {
    @Override
    public boolean isKeep(TridentTuple tuple) {
        int a = tuple.getInteger(0);
        int b = tuple.getInteger(1);
        return (a + b) % 2 == 0;
    }
}
