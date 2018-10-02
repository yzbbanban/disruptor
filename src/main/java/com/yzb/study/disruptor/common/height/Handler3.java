package com.yzb.study.disruptor.common.height;


import com.lmax.disruptor.EventHandler;

/**
 * Created by brander on 2018/10/2
 */
public class Handler3 implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("handler3: Name: " + trade.getName() + ",ID: " + trade.getId() + "Instance: " + trade.toString());
    }
}
