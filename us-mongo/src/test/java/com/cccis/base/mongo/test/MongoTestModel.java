package com.cccis.base.mongo.test;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cccis.base.mongo.MBaseEntity;

/**
 * Created by CCC on 2015/11/26.
 */
@Document(collection = "MongoTestModel")
public class MongoTestModel extends MBaseEntity {

    @Id
    public String mid;

    @Indexed
    public String name;

    public Integer age;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
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
