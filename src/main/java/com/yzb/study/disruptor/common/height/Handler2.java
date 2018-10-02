package com.yzb.study.disruptor.common.height;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * Created by brander on 2018/10/2
 */
public class Handler2 implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("handler2: SET ID");
        trade.setId("1");
        Thread.sleep(2000);
    }
}