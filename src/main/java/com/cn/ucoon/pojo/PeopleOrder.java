package com.cn.ucoon.pojo;

import java.util.Date;

public class PeopleOrder {
    private Integer userId;

    private Integer orderId;

    private Date takeTime;

    private Integer takeState;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(Date takeTime) {
        this.takeTime = takeTime;
    }

    public Integer getTakeState() {
        return takeState;
    }

    public void setTakeState(Integer takeState) {
        this.takeState = takeState;
    }
}