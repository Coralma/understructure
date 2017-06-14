package com.cccis.base.redis.test;

import java.io.Serializable;

/**
 * Created by CCC on 2016/4/27.
 */
public class SampleModel implements Serializable {

    private String name;
    private Integer age;

    public SampleModel(String name, Integer age) {
        this.name = name;
        this.age= age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
