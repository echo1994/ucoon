package com.cn.ucoon.pojo;

import java.util.Date;

public class Apply {
    private Integer applyId;

    private Integer userId;

    private Integer missionId;

    private Date takeTime;

    private Integer takeState;

    private String note;

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    public Date getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(Date takeTime) {
        this.takeTime = takeTime;
    }

    public Integer getTakeState() {
        return takeState;
    }

    public void setTakeState(Integer takeState) {
        this.takeState = takeState;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}