package com.cn.ucoon.pojo;

import java.util.Date;

public class ApplyTeam {
    private Integer id;

    private String school;

    private String applyerName;

    private String selfIntroduce;

    private String certificateImg;

    private String applyerPhone;

    private String tags;

    private Integer userId;

    private Integer applyStatus;

    private Date applyTime;

    private Integer picCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public String getApplyerName() {
        return applyerName;
    }

    public void setApplyerName(String applyerName) {
        this.applyerName = applyerName == null ? null : applyerName.trim();
    }

    public String getSelfIntroduce() {
        return selfIntroduce;
    }

    public void setSelfIntroduce(String selfIntroduce) {
        this.selfIntroduce = selfIntroduce == null ? null : selfIntroduce.trim();
    }

    public String getCertificateImg() {
        return certificateImg;
    }

    public void setCertificateImg(String certificateImg) {
        this.certificateImg = certificateImg == null ? null : certificateImg.trim();
    }

    public String getApplyerPhone() {
        return applyerPhone;
    }

    public void setApplyerPhone(String applyerPhone) {
        this.applyerPhone = applyerPhone == null ? null : applyerPhone.trim();
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getPicCount() {
        return picCount;
    }

    public void setPicCount(Integer picCount) {
        this.picCount = picCount;
    }
}