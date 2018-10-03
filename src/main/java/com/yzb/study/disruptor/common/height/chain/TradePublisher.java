package com.yzb.study.disruptor.common.height.chain;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by brander on 2018/10/2
 */
public class TradePublisher implements Runnable {
    private final CountDownLatch latch;
    private final Disruptor<Trade> disruptor;
    private static final int PUBLISH_COUNT = 1;

    public TradePublisher(CountDownLatch latch, Disruptor<Trade> disruptor) {
        this.latch = latch;
        this.disruptor = disruptor;
    }

    @Override
    public void run() {
        TradeEventTranslator eventTranslator = new TradeEventTranslator();
        for (int i = 0; i < PUBLISH_COUNT; i++) {
            disruptor.publishEvent(eventTranslator);
        }
        latch.countDown();
    }
}


class TradeEventTranslator implements EventTranslator<Trade> {

    private Random random = new Random();

    @Override
    public void translateTo(Trade event, long sequence) {
        this.generateTrade(event);
    }

    private void generateTrade(Trade event) {
        event.setPrice(random.nextDouble() * 9999);
    }
}