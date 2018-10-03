package com.yzb.study.disruptor.common.height.multi;

import com.lmax.disruptor.WorkHandler;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by brander on 2018/10/3
 */
public class Consumer implements WorkHandler<Order> {

    private String consumerId;
    private static AtomicInteger count = new AtomicInteger(0);
    private Random random = new Random();

    public Consumer(String consumerId) {
        this.consumerId = consumerId;
    }

    public int getCount() {
        return count.get();
    }

    @Override
    public void onEvent(Order order) throws Exception {
        Thread.sleep(1 * random.nextInt(5));
        System.out.println("当前消费者：" + this.consumerId + ",消费者Id：" + order.getId());
        count.incrementAndGet();
    }
}
