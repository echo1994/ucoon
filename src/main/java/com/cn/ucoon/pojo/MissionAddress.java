package com.cn.ucoon.pojo;

public class MissionAddress {
    private Integer id;

    private Integer userId;
    
    private String place;

    private String missionLng;

    private String missionLat;

    
    
    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getMissionLng() {
        return missionLng;
    }

    public void setMissionLng(String missionLng) {
        this.missionLng = missionLng == null ? null : missionLng.trim();
    }

    public String getMissionLat() {
        return missionLat;
    }

    public void setMissionLat(String missionLat) {
        this.missionLat = missionLat == null ? null : missionLat.trim();
    }
}