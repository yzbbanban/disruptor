package com.yzb.study.disruptor.common.tr;

import com.yzb.study.disruptor.util.AESUtil;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018/12/11.
 */
public class Main44 {

    public static void main(String[] args) {

//        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(80,
//                100,
//                100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), new ThreadFactory() {
//            @Override
//            public Thread newThread(Runnable r) {
//                Thread thread = new Thread(r);
//                thread.setName("aorder-thread");
//                //守护线程
//                if (thread.isDaemon()) {
//                    thread.setDaemon(false);
//                }
//                //顺序
//                if (Thread.NORM_PRIORITY != thread.getPriority()) {
//                    thread.setPriority(Thread.NORM_PRIORITY);
//                }
//                return thread;
//            }
//        });

        // 设置数据
        ExecutorService poolExecutor = Executors.newFixedThreadPool(110);
        StringBuffer sb = new StringBuffer();
        CountDownLatch countDownLatch = new CountDownLatch(100000);
        long startTime = System.currentTimeMillis();
        System.out.println("开始时间：" + System.currentTimeMillis());
        for (int i = 0; i < 100000; i++) {
            poolExecutor.submit(new Runnable() {
                @Override
                public void run() {

                    try {
                        List<Object> rowList = new ArrayList<>();
                        NetworkParameters params = MainNetParams.get();
                        ECKey key = new ECKey();
                        String passPrivateKey = AESUtil.encrypt(key.getPrivateKeyAsHex());
                        sb.append(key.getPublicKeyAsHex() + ",");
                        sb.append(passPrivateKey + ",");
                        sb.append(key.toAddress(params)+"\n");

                        countDownLatch.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        try {
            countDownLatch.await();
            FileUtil.outToFile("address", "F:/data/", sb.toString());
            System.out.println("结束时间：" + (System.currentTimeMillis() - startTime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
