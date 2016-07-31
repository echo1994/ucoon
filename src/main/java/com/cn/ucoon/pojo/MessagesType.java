package com.cn.ucoon.pojo;

public class MessagesType {
    private Integer messageTypeId;

    private String messageTypeName;

    public Integer getMessageTypeId() {
        return messageTypeId;
    }

    public void setMessageTypeId(Integer messageTypeId) {
        this.messageTypeId = messageTypeId;
    }

    public String getMessageTypeName() {
        return messageTypeName;
    }

    public void setMessageTypeName(String messageTypeName) {
        this.messageTypeName = messageTypeName == null ? null : messageTypeName.trim();
    }
}