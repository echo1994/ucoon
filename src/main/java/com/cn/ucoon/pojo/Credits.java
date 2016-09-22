package com.cn.ucoon.pojo;

import java.util.Date;

public class Credits {
    private Integer creditsId;

    private String acquisitionType;

    private Date acquisitionTime;

    private String plusOrMinus;

    private Integer quantity;

    private Integer userId;

    public Integer getCreditsId() {
        return creditsId;
    }

    public void setCreditsId(Integer creditsId) {
        this.creditsId = creditsId;
    }

    public String getAcquisitionType() {
        return acquisitionType;
    }

    public void setAcquisitionType(String acquisitionType) {
        this.acquisitionType = acquisitionType == null ? null : acquisitionType.trim();
    }

    public Date getAcquisitionTime() {
        return acquisitionTime;
    }

    public void setAcquisitionTime(Date acquisitionTime) {
        this.acquisitionTime = acquisitionTime;
    }

    public String getPlusOrMinus() {
        return plusOrMinus;
    }

    public void setPlusOrMinus(String plusOrMinus) {
        this.plusOrMinus = plusOrMinus == null ? null : plusOrMinus.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}