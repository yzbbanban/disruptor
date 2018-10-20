package com.yzb.study.disruptor.common.tr;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import com.yzb.study.disruptor.common.height.multi.Order;

import java.util.concurrent.Executors;

/**
 * Created by brander on 2018/10/8
 */
public class Main3 {


    public static void main(String[] args) {
        RingBuffer<Order> ringBuffer = RingBuffer.create(ProducerType.MULTI, new EventFactory<Order>() {
            @Override
            public Order newInstance() {
                return new Order();
            }
        }, 1024 * 1024, new BlockingWaitStrategy());

        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        Consumer2[] consumer2s = new Consumer2[10];
        for (int i = 0; i < 10; i++) {
            consumer2s[i] = new Consumer2("C" + i);
        }

        WorkerPool workerPool = new WorkerPool(ringBuffer, sequenceBarrier, new MyExceptionHandler(), consumer2s);

        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        workerPool.start(Executors.newFixedThreadPool(8));
    }


    static class MyExceptionHandler implements ExceptionHandler {

        @Override
        public void handleEventException(Throwable throwable, long l, Object o) {

        }

        @Override
        public void handleOnStartException(Throwable throwable) {

        }

        @Override
        public void handleOnShutdownException(Throwable throwable) {

        }
    }
}
