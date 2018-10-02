package com.yzb.study.disruptor.common.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by brander on 2018/10/2
 */
public class OrderEventFactory implements EventFactory<OrderEvent> {


    @Override
    public OrderEvent newInstance() {
        return new OrderEvent();
    }
}
