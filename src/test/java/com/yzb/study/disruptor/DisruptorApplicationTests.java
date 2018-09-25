package com.yzb.study.disruptor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ArrayBlockingQueue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DisruptorApplicationTests {


    @Test
    public void contextLoads() {

        final ArrayBlockingQueue<Data> queue = new ArrayBlockingQueue<Data>(10000000);

        final long startTime = System.currentTimeMillis();

        new Thread(new Runnable() {
            @Override
            public void run() {
                long i = 0;
                while (i < 10000000) {
                    Data data = new Data(i, "c" + i);
                    try {
                        queue.put(data);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                int k = 0;
                while (k < 10000000) {
                    try {
                        queue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                long endTime = System.currentTimeMillis();
                System.out.println("ArrayBlockingQueue constTime = " + (endTime - startTime));
            }
        }).start();

    }

}
