package com.cn.ucoon.pojo;

import java.util.Date;

public class SignHistory {
    private Integer signHistoryId;

    private Integer userId;

    private Date signHistoryTime;

    public Integer getSignHistoryId() {
        return signHistoryId;
    }

    public void setSignHistoryId(Integer signHistoryId) {
        this.signHistoryId = signHistoryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getSignHistoryTime() {
        return signHistoryTime;
    }

    public void setSignHistoryTime(Date signHistoryTime) {
        this.signHistoryTime = signHistoryTime;
    }
}