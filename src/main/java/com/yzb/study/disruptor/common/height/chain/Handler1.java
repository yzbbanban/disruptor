package com.yzb.study.disruptor.common.height.chain;


import com.lmax.disruptor.EventHandler;

/**
 * Created by brander on 2018/10/2
 */
public class Handler1 implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("handler1: SET NAME");
        trade.setName("H1");
        Thread.sleep(1000);
    }


}
