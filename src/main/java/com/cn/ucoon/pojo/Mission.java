package com.cn.ucoon.pojo;

import java.util.Date;

public class Mission {
    private Integer missionId;

    private Integer missionTypeId;

    private String missionTitle;

    private String missionDescribe;

    private String pictures;

    private Integer peopleCount;

    private String place;

    private Date startTime;

    private Date endTime;

    private Date publishTime;

    private Integer userId;

    private Integer viewCount;

    private Integer missionPrice;

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    public Integer getMissionTypeId() {
        return missionTypeId;
    }

    public void setMissionTypeId(Integer missionTypeId) {
        this.missionTypeId = missionTypeId;
    }

    public String getMissionTitle() {
        return missionTitle;
    }

    public void setMissionTitle(String missionTitle) {
        this.missionTitle = missionTitle == null ? null : missionTitle.trim();
    }

    public String getMissionDescribe() {
        return missionDescribe;
    }

    public void setMissionDescribe(String missionDescribe) {
        this.missionDescribe = missionDescribe == null ? null : missionDescribe.trim();
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures == null ? null : pictures.trim();
    }

    public Integer getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getMissionPrice() {
        return missionPrice;
    }

    public void setMissionPrice(Integer missionPrice) {
        this.missionPrice = missionPrice;
    }
}