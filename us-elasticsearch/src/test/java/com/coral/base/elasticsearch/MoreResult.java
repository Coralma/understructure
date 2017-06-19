package com.coral.base.elasticsearch;

import java.util.Date;

/**
 * Created by CCC on 2016/6/16.
 */
public class MoreResult extends BaseResult {

    private String name;

    private Date firstDate;

    private Long secondDate;

    private String content;

    private String contentNot;

    private String message;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(Date firstDate) {
        this.firstDate = firstDate;
    }

    public Long getSecondDate() {
        return secondDate;
    }

    public void setSecondDate(Long secondDate) {
        this.secondDate = secondDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentNot() {
        return contentNot;
    }

    public void setContentNot(String contentNot) {
        this.contentNot = contentNot;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
