package com.yzb.study.disruptor.common.tr;

import com.lmax.disruptor.WorkHandler;
import com.yzb.study.disruptor.common.height.multi.Order;

/**
 * Created by brander on 2018/10/8
 */
public class Consumer2 implements WorkHandler<Order> {

    private String productId;

    public Consumer2(String productId) {
        this.productId = productId;
    }

    @Override
    public void onEvent(Order order) throws Exception {
        System.out.println("order = [" + order + "]");
        System.out.println("order = [" + productId + "]");

    }
}
