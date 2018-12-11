package com.yzb.study.disruptor.common.height.multi;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.yzb.study.disruptor.Data;
import com.yzb.study.disruptor.util.DataConsumer;

import java.util.concurrent.Executors;

/**
 *
 * @author wangban
 * @date 13:44 2018/12/11
 */
public class MainDIs {
    public static void main(String[] args) {
        int ringBufferSize = 65536;
        final long startTime = System.currentTimeMillis();
        final Disruptor<Data> disruptor = new Disruptor<Data>(new EventFactory<Data>() {
            @Override
            public Data newInstance() {
                return new Data();
            }
        },
                ringBufferSize,
                Executors.newSingleThreadExecutor(),
                ProducerType.SINGLE,
                new YieldingWaitStrategy()
        );


        DataConsumer consumer = new DataConsumer();
        disruptor.handleEventsWith(consumer);
        disruptor.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RingBuffer<Data> ringBuffer = disruptor.getRingBuffer();
                for (long i = 0; i < 100000; i++) {
                    long seq = ringBuffer.next();
                    Data data = ringBuffer.get(seq);
                    data.setId(i);
                    data.setName("c" + i);
                    ringBuffer.publish(seq);
                }
                long endTime = System.currentTimeMillis();
                System.out.println("Disruptor constTime = " + (endTime - startTime) + "ms");
            }
        }).start();

    }
}
