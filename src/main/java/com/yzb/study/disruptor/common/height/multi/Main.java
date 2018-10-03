package com.yzb.study.disruptor.common.height.multi;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * Created by brander on 2018/10/3
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        //1 创建 ringuffer
        RingBuffer<Order> ringBuffer = RingBuffer.create(ProducerType.MULTI, new EventFactory<Order>() {
            @Override
            public Order newInstance() {
                return new Order();
            }
        }, 1024 * 1024, new YieldingWaitStrategy());

        //2 通过 ringBuffer 创建一个屏障
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        //3 创建多消费者数组
        Consumer[] consumers = new Consumer[10];
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer("C" + i);
        }

        //4 构建多消费者工作池
        WorkerPool<Order> workerPool = new WorkerPool<Order>(ringBuffer, sequenceBarrier, new EventExceptionHandler(), consumers);

        //5 构建多个sequence 序号 用于单独统计消费进度，并且设置到 ringBuffer 中
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        //6 启动 workPool
        workerPool.start(Executors.newFixedThreadPool(8));

        CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 100; i++) {
            Producer producer = new Producer(ringBuffer);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for (int j = 0; j < 100; j++) {
                        producer.sendData(UUID.randomUUID().toString());
                    }
                }
            }).start();
        }

        Thread.sleep(2000);

        System.err.println("----------线程创建完毕，开始生产数据----------");

        latch.countDown();

        Thread.sleep(10000);
        System.err.println("第三个消费者处理的任务总数： " + consumers[2].getCount());

    }


    static class EventExceptionHandler implements ExceptionHandler<Order> {
        @Override
        public void handleEventException(Throwable throwable, long l, Order order) {

        }

        @Override
        public void handleOnStartException(Throwable throwable) {

        }

        @Override
        public void handleOnShutdownException(Throwable throwable) {

        }
    }
}
