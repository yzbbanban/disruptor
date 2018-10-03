package com.yzb.study.disruptor.common.height.chain;

import com.lmax.disruptor.EventHandler;

/**
 * Created by brander on 2018/10/2
 */
public class Handler5 implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("handler5: GET PRICE" + trade.getPrice());
        trade.setPrice(trade.getPrice() + 3.0);
    }
}
