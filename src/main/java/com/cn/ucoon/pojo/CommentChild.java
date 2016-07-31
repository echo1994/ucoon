package com.cn.ucoon.pojo;

import java.util.Date;

public class CommentChild {
    private Integer commentId;

    private Integer fromuserId;

    private Integer touserId;

    private String content;

    private Date commentTime;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getFromuserId() {
        return fromuserId;
    }

    public void setFromuserId(Integer fromuserId) {
        this.fromuserId = fromuserId;
    }

    public Integer getTouserId() {
        return touserId;
    }

    public void setTouserId(Integer touserId) {
        this.touserId = touserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }
}