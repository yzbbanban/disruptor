package com.yzb.study.disruptor.common.height;

import com.lmax.disruptor.EventHandler;

/**
 * Created by brander on 2018/10/2
 */
public class Handler4 implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("handler4: SET PRICE");
        trade.setPrice(17.0);
    }
}
