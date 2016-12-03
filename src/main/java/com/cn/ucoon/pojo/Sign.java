package com.cn.ucoon.pojo;

import java.util.Date;

public class Sign {
    private Integer signId;

    private Integer userId;

    private Integer signSeriesCount;

    private Integer signCount;

    private Date lastmodifytime;

    public Integer getSignId() {
        return signId;
    }

    public void setSignId(Integer signId) {
        this.signId = signId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSignSeriesCount() {
        return signSeriesCount;
    }

    public void setSignSeriesCount(Integer signSeriesCount) {
        this.signSeriesCount = signSeriesCount;
    }

    public Integer getSignCount() {
        return signCount;
    }

    public void setSignCount(Integer signCount) {
        this.signCount = signCount;
    }

    public Date getLastmodifytime() {
        return lastmodifytime;
    }

    public void setLastmodifytime(Date lastmodifytime) {
        this.lastmodifytime = lastmodifytime;
    }
}