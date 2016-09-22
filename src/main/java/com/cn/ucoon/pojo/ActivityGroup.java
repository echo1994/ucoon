package com.cn.ucoon.pojo;

public class ActivityGroup {
    private Integer activityGroupId;

    private Integer userId;

    private String groupRank;

    public Integer getActivityGroupId() {
        return activityGroupId;
    }

    public void setActivityGroupId(Integer activityGroupId) {
        this.activityGroupId = activityGroupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getGroupRank() {
        return groupRank;
    }

    public void setGroupRank(String groupRank) {
        this.groupRank = groupRank == null ? null : groupRank.trim();
    }
}