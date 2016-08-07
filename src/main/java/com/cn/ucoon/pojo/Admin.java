package com.cn.ucoon.pojo;

public class Admin {
    private Integer adminId;

    private String adminName;

    private String adminPsw;

    private Integer adminRank;

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName == null ? null : adminName.trim();
    }

    public String getAdminPsw() {
        return adminPsw;
    }

    public void setAdminPsw(String adminPsw) {
        this.adminPsw = adminPsw == null ? null : adminPsw.trim();
    }

    public Integer getAdminRank() {
        return adminRank;
    }

    public void setAdminRank(Integer adminRank) {
        this.adminRank = adminRank;
    }
}