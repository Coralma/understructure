package com.cccis.base.hystrix.model;

import java.util.Date;

/**
 * Created by CCC on 2016/3/21.
 */
public class ApiErrorLog {

    private String api;
    private String log;
    private String inputEntity;
    private Date createDate;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getInputEntity() {
        return inputEntity;
    }

    public void setInputEntity(String inputEntity) {
        this.inputEntity = inputEntity;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
