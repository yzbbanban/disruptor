package com.yzb.study.disruptor.detail;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by brander on 2018/10/3
 */
public class Review {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();


        CopyOnWriteArrayList cowal = new CopyOnWriteArrayList();

        cowal.add("11");

        AtomicLong count = new AtomicLong(0);

        boolean flag = count.compareAndSet(0, 2);
        System.out.println(flag);
        System.out.println(count.get());

    }
}
