package com.yzb.study.disruptor.common.disruptor;

/**
 * Created by brander on 2018/10/2
 */
public class OrderEvent {
    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
