package com.coral.base.hystrix.model;

import java.util.Date;

/**
 * Created by CCC on 2016/3/21.
 */
public class ApiInfoLog {

    private String api;
    private String log;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
