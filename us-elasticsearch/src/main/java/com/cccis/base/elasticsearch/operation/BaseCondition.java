package com.cccis.base.elasticsearch.operation;

/**
 * Created by CCC on 2016/6/15.
 */
public class BaseCondition {

    protected int pageNo = 1;
    protected int pageSize = 10;

    public int getPageNo() {
        return pageNo;
    }

    public BaseCondition setPageNo(int pageNo) {
        if (pageNo < 1)
            pageNo = 1;
        this.pageNo = pageNo;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public BaseCondition setPageSize(int pageSize) {
        if (pageSize < 1)
            pageSize = 10;
        this.pageSize = pageSize;
        return this;
    }

    public int getFrom() {
        return (pageNo - 1) * pageSize;
    }

    public int getSize() {
        return pageSize;
    }
}
