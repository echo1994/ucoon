package com.cn.ucoon.pojo;

import java.util.Date;

public class MissionOrders {
    private Integer missionOrderId;

    private String missionOrderNum;

    private Integer userId;

    private Integer missionId;

    private Date orderTime;

    private Date finishTime;

    private Integer state;

    public Integer getMissionOrderId() {
        return missionOrderId;
    }

    public void setMissionOrderId(Integer missionOrderId) {
        this.missionOrderId = missionOrderId;
    }

    public String getMissionOrderNum() {
        return missionOrderNum;
    }

    public void setMissionOrderNum(String missionOrderNum) {
        this.missionOrderNum = missionOrderNum == null ? null : missionOrderNum.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}