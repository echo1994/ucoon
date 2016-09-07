package com.cn.ucoon.pojo;

import java.util.Date;

public class Comments {
    private Integer commentId;

    private Integer userId;

    private Integer missionId;

    private String content;

    private Date commentTime;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
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

	@Override
	public String toString() {
		return "Comments [commentId=" + commentId + ", userId=" + userId
				+ ", missionId=" + missionId + ", content=" + content
				+ ", commentTime=" + commentTime + "]";
	}
    
    
    
}