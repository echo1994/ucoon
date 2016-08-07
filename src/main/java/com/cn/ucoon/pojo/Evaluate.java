package com.cn.ucoon.pojo;

import java.util.Date;

public class Evaluate {
    private Integer missionId;

    private Integer publishId;

    private Integer executorId;

    private String publishEvaluate;

    private String executorEvaluate;

    private Float publishScore;

    private Float executorScore;

    private Date epevaluateTime;

    private Date peevaluateTime;

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    public Integer getPublishId() {
        return publishId;
    }

    public void setPublishId(Integer publishId) {
        this.publishId = publishId;
    }

    public Integer getExecutorId() {
        return executorId;
    }

    public void setExecutorId(Integer executorId) {
        this.executorId = executorId;
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

    public Date getEpevaluateTime() {
        return epevaluateTime;
    }

    public void setEpevaluateTime(Date epevaluateTime) {
        this.epevaluateTime = epevaluateTime;
    }

    public Date getPeevaluateTime() {
        return peevaluateTime;
    }

    public void setPeevaluateTime(Date peevaluateTime) {
        this.peevaluateTime = peevaluateTime;
    }
}