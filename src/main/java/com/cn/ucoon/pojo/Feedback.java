package com.cn.ucoon.pojo;

import java.util.Date;

public class Feedback {
    private Integer feedbackId;

    private Integer userId;

    private String feedbackQuestion;

    private String feedbackCantact;

    private Integer picCount;

    private String pictures;

    private Date feedbackTime;

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFeedbackQuestion() {
        return feedbackQuestion;
    }

    public void setFeedbackQuestion(String feedbackQuestion) {
        this.feedbackQuestion = feedbackQuestion == null ? null : feedbackQuestion.trim();
    }

    public String getFeedbackCantact() {
        return feedbackCantact;
    }

    public void setFeedbackCantact(String feedbackCantact) {
        this.feedbackCantact = feedbackCantact == null ? null : feedbackCantact.trim();
    }

    public Integer getPicCount() {
        return picCount;
    }

    public void setPicCount(Integer picCount) {
        this.picCount = picCount;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures == null ? null : pictures.trim();
    }

    public Date getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(Date feedbackTime) {
        this.feedbackTime = feedbackTime;
    }
}