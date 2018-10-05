package com.yzb.study.disruptor.detail;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by brander on 2018/10/3
 */
public class Review {
    public static void main(String[] args) throws Exception {
//        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
//
//
//        CopyOnWriteArrayList cowal = new CopyOnWriteArrayList();
//
//        cowal.add("11");
//
//        AtomicLong count = new AtomicLong(0);
//
//        boolean flag = count.compareAndSet(0, 2);
//        System.out.println(flag);
//        System.out.println(count.get());


        Object lock = new Object();
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i < 10; i++) {
                    sum += i;
                }
//                synchronized (lock) {
//                    try {
//                        lock.wait();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                LockSupport.park();

                System.out.println(sum);
            }
        });

        a.start();

        Thread.sleep(1000);

        LockSupport.unpark(a);
//        synchronized (lock){
//            lock.notify();
//        }

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,
                Runtime.getRuntime().availableProcessors() * 2,
                60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(200), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("order-thread");
                //守护线程
                if (thread.isDaemon()) {
                    thread.setDaemon(false);
                }
                //顺序
                if (Thread.NORM_PRIORITY != thread.getPriority()) {
                    thread.setPriority(Thread.NORM_PRIORITY);
                }
                return thread;
            }
        }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("拒绝策略: " + executor);
            }
        });


        ReentrantLock reentrantLock=new ReentrantLock(false);

        CountDownLatch latch=new CountDownLatch(1);


    }
}
