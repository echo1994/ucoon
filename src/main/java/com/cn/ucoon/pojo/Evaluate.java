package com.cn.ucoon.pojo;

import java.util.Date;

public class Evaluate {
    private Integer missionid;

    private Integer publishid;

    private Integer executorid;

    private String publishEvaluate;

    private String executorEvaluate;

    private Float publishScore;

    private Float executorScore;

    private Date evaluatetime;

    public Integer getMissionid() {
        return missionid;
    }

    public void setMissionid(Integer missionid) {
        this.missionid = missionid;
    }

    public Integer getPublishid() {
        return publishid;
    }

    public void setPublishid(Integer publishid) {
        this.publishid = publishid;
    }

    public Integer getExecutorid() {
        return executorid;
    }

    public void setExecutorid(Integer executorid) {
        this.executorid = executorid;
    }

    public String getPublishEvaluate() {
        return publishEvaluate;
    }

    public void setPublishEvaluate(String publishEvaluate) {
        this.publishEvaluate = publishEvaluate == null ? null : publishEvaluate.trim();
    }

    public String getExecutorEvaluate() {
        return executorEvaluate;
    }

    public void setExecutorEvaluate(String executorEvaluate) {
        this.executorEvaluate = executorEvaluate == null ? null : executorEvaluate.trim();
    }

    public Float getPublishScore() {
        return publishScore;
    }

    public void setPublishScore(Float publishScore) {
        this.publishScore = publishScore;
    }

    public Float getExecutorScore() {
        return executorScore;
    }

    public void setExecutorScore(Float executorScore) {
        this.executorScore = executorScore;
    }

    public Date getEvaluatetime() {
        return evaluatetime;
    }

    public void setEvaluatetime(Date evaluatetime) {
        this.evaluatetime = evaluatetime;
    }
}