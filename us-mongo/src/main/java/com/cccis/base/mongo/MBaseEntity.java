package com.cccis.base.mongo;

import java.io.Serializable;
import java.util.Date;

public class MBaseEntity implements Serializable {

    private Date createDate;

    private Date lastModifyDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }
}
