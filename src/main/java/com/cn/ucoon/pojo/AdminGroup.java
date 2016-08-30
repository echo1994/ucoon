package com.cn.ucoon.pojo;

public class AdminGroup {
    private Integer groupId;

    private String groupName;

    private String groupRights;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getGroupRights() {
        return groupRights;
    }

    public void setGroupRights(String groupRights) {
        this.groupRights = groupRights == null ? null : groupRights.trim();
    }
}