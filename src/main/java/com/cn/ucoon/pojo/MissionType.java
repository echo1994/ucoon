package com.cn.ucoon.pojo;

public class MissionType {
    private Integer missionTypeId;

    private String missionTypeName;

    public Integer getMissionTypeId() {
        return missionTypeId;
    }

    public void setMissionTypeId(Integer missionTypeId) {
        this.missionTypeId = missionTypeId;
    }

    public String getMissionTypeName() {
        return missionTypeName;
    }

    public void setMissionTypeName(String missionTypeName) {
        this.missionTypeName = missionTypeName == null ? null : missionTypeName.trim();
    }
}