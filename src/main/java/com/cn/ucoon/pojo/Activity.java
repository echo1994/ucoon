package com.cn.ucoon.pojo;

import java.util.Date;

public class Activity {
    private Integer activityId;

    private Integer userId;

    private String activityName;

    private Date activityTime;

    private String activityPlace;

    private String activityDetailPlace;

    private String activityDesc;

    private Date activityCreateTime;

    private String activityLng;

    private String activityLat;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName == null ? null : activityName.trim();
    }

    public Date getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(Date activityTime) {
        this.activityTime = activityTime;
    }

    public String getActivityPlace() {
        return activityPlace;
    }

    public void setActivityPlace(String activityPlace) {
        this.activityPlace = activityPlace == null ? null : activityPlace.trim();
    }

    public String getActivityDetailPlace() {
        return activityDetailPlace;
    }

    public void setActivityDetailPlace(String activityDetailPlace) {
        this.activityDetailPlace = activityDetailPlace == null ? null : activityDetailPlace.trim();
    }

    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc == null ? null : activityDesc.trim();
    }

    public Date getActivityCreateTime() {
        return activityCreateTime;
    }

    public void setActivityCreateTime(Date activityCreateTime) {
        this.activityCreateTime = activityCreateTime;
    }

    public String getActivityLng() {
        return activityLng;
    }

    public void setActivityLng(String activityLng) {
        this.activityLng = activityLng == null ? null : activityLng.trim();
    }

    public String getActivityLat() {
        return activityLat;
    }

    public void setActivityLat(String activityLat) {
        this.activityLat = activityLat == null ? null : activityLat.trim();
    }
}