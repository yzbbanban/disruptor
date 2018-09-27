package com.yzb.study.disruptor.util;

import com.lmax.disruptor.EventHandler;
import com.yzb.study.disruptor.Data;

/**
 * Created by brander on 2018/9/25
 */
public class DataConsumer implements EventHandler<Data> {

    int i;
    long startTime;

    public DataConsumer() {
        startTime = System.currentTimeMillis();
    }


    @Override
    public void onEvent(Data data, long l, boolean b) throws Exception {
        i++;
        if (i == 10000000) {
            long endTime = System.currentTimeMillis();
            System.out.println("Disruptor constTime = " + (endTime - startTime) + "ms");
        }
    }
}
