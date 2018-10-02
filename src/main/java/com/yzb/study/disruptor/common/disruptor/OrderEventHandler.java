package com.yzb.study.disruptor.common.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * Created by brander on 2018/10/2
 */
public class OrderEventHandler implements EventHandler<OrderEvent> {


    @Override
    public void onEvent(OrderEvent orderEvent, long l, boolean b) throws Exception {
        System.out.println("消费者：" + orderEvent.getValue() );
    }
}
