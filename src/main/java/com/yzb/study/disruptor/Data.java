package com.yzb.study.disruptor;

import java.io.Serializable;

/**
 * Created by brander on 2018/9/25
 */
public class Data implements Serializable {
    private Long id;
    private String name;

    public Data() {
    }

    public Data(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
