package com.yzb.study.disruptor.common.height;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by brander on 2018/10/2
 */
public class Main2 {
    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(4);


        //1构建
        Disruptor<Trade> disruptor = new Disruptor<Trade>(new EventFactory<Trade>() {
            @Override
            public Trade newInstance() {
                return new Trade();
            }
        }, 1024 * 1024, Executors.newFixedThreadPool(4), ProducerType.SINGLE, new BusySpinWaitStrategy());

        //2把消费者设置到 Disruptor 中 handleEventsWith

        //2.1串行操作
//        disruptor.handleEventsWith(new Handler1())
//                .handleEventsWith(new Handler2())
//                .handleEventsWith(new Handler3())
//                .handleEventsWith(new Handler4());


        //2.2并行操作
//        disruptor.handleEventsWith(new Handler1());
//        disruptor.handleEventsWith(new Handler2());
//        disruptor.handleEventsWith(new Handler3());
//        disruptor.handleEventsWith(new Handler4());

        //并行操作
//        disruptor.handleEventsWith(new Handler1(),new Handler2(),new Handler3(),new Handler4());

        //菱形操作
//        disruptor.handleEventsWith(new Handler1(),new Handler2()).handleEventsWith(new Handler3());
        EventHandlerGroup<Trade> ehGroup = disruptor.handleEventsWith(new Handler1(), new Handler2());
        ehGroup.then(new Handler3());

        //3启动 disruptor
        RingBuffer<Trade> ringBuffer = disruptor.start();


        CountDownLatch latch = new CountDownLatch(1);

        long begin = System.currentTimeMillis();

        executorService.submit(new TradePublisher(latch, disruptor));

        latch.await();

        disruptor.shutdown();
        executorService.shutdown();

        System.out.println("总耗时： " + (System.currentTimeMillis() - begin));
    }
}
