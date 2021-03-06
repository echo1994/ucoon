package com.cn.ucoon.pojo;

import java.math.BigDecimal;
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

    private BigDecimal missionPrice;

    private String telephone;

    private Integer missionStatus;

    private Integer picCount;
    
    private String missionLng;
    
    private String missionLat;

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

    public BigDecimal getMissionPrice() {
        return missionPrice;
    }

    public void setMissionPrice(BigDecimal missionPrice) {
        this.missionPrice = missionPrice;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public Integer getMissionStatus() {
        return missionStatus;
    }

    public void setMissionStatus(Integer missionStatus) {
        this.missionStatus = missionStatus;
    }

    public Integer getPicCount() {
        return picCount;
    }

    public void setPicCount(Integer picCount) {
        this.picCount = picCount;
    }
    
    

	public String getMissionLng() {
		return missionLng;
	}

	public void setMissionLng(String missionLng) {
		this.missionLng = missionLng;
	}

	public String getMissionLat() {
		return missionLat;
	}

	public void setMissionLat(String missionLat) {
		this.missionLat = missionLat;
	}

	@Override
	public String toString() {
		return "Mission [missionId=" + missionId + ", missionTypeId="
				+ missionTypeId + ", missionTitle=" + missionTitle
				+ ", missionDescribe=" + missionDescribe + ", pictures="
				+ pictures + ", peopleCount=" + peopleCount + ", place="
				+ place + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", publishTime=" + publishTime + ", userId=" + userId
				+ ", viewCount=" + viewCount + ", missionPrice=" + missionPrice
				+ ", telephone=" + telephone + ", missionStatus="
				+ missionStatus + ", picCount=" + picCount + ", missionLng="
				+ missionLng + ", missionLat=" + missionLat + "]";
	}

	
    
    
    
}