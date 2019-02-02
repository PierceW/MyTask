package com.akazam.test.trident.aggregator;

import org.apache.storm.trident.operation.ReducerAggregator;
import org.apache.storm.trident.tuple.TridentTuple;

/**
 * @author Alex
 * @ClassName Sum
 * @description
 * @date 2019/2/1 16:17
 */
public class SumReducer implements ReducerAggregator {
    @Override
    public Object init() {
        return null;
    }

    @Override
    public Object reduce(Object curr, TridentTuple tuple) {
        return null;
    }
}
