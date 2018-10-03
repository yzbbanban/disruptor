package com.yzb.study.disruptor.common.height.chain;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
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
        }, 1024 * 1024, Executors.newFixedThreadPool(5), ProducerType.SINGLE, new BusySpinWaitStrategy());

        //2把消费者设置到 Disruptor 中 handleEventsWith

        //2.1串行操作
//        disruptor.handleEventsWith(new Handler1())
//                .handleEventsWith(new Handler2())
//                .handleEventsWith(new Handler3());


        //2.2并行操作 两种方式，添加多个 handler 实现即可
//        disruptor.handleEventsWith(new Handler1());
//        disruptor.handleEventsWith(new Handler2());
//        disruptor.handleEventsWith(new Handler3());

        //并行操作
//        disruptor.handleEventsWith(new Handler1(),new Handler2(),new Handler3());

        //2.3 菱形操作
//        disruptor.handleEventsWith(new Handler1(),new Handler2()).handleEventsWith(new Handler3());

//        EventHandlerGroup<Trade> ehGroup = disruptor.handleEventsWith(new Handler1(), new Handler2());
//        ehGroup.then(new Handler3());

        //2.4 六边形操作
        Handler1 h1 = new Handler1();
        Handler2 h2 = new Handler2();
        Handler3 h3 = new Handler3();
        Handler4 h4 = new Handler4();
        Handler5 h5 = new Handler5();
        disruptor.handleEventsWith(h1,h4);
        disruptor.after(h1).handleEventsWith(h2);
        disruptor.after(h4).handleEventsWith(h5);
        disruptor.after(h2,h5).handleEventsWith(h3);


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
