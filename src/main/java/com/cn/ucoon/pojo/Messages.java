package com.cn.ucoon.pojo;

import java.util.Date;

public class Messages {
    private Integer messageId;

    private String messageDetail;

    private Integer messageStatus;

    private Date psotTime;

    private String messageType;

    private Integer fromUserId;

    private Integer toUserId;

    private Integer messageNatureType;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail == null ? null : messageDetail.trim();
    }

    public Integer getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(Integer messageStatus) {
        this.messageStatus = messageStatus;
    }

    public Date getPsotTime() {
        return psotTime;
    }

    public void setPsotTime(Date psotTime) {
        this.psotTime = psotTime;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public Integer getMessageNatureType() {
        return messageNatureType;
    }

    public void setMessageNatureType(Integer messageNatureType) {
        this.messageNatureType = messageNatureType;
    }
}