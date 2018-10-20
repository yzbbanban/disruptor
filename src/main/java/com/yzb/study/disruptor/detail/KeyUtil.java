package com.yzb.study.disruptor.detail;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

/**
 * Created by brander on 2018/10/20
 */
public class KeyUtil {

    public static String generatorUUID(){
        TimeBasedGenerator timeBasedGenerator= Generators.timeBasedGenerator(EthernetAddress.constructMulticastAddress());
        return timeBasedGenerator.generate().toString();
    }


    public static void main(String[] args) {
        System.err.println(KeyUtil.generatorUUID());
        System.err.println(KeyUtil.generatorUUID());
    }

}
