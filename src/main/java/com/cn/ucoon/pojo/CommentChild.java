package com.cn.ucoon.pojo;

import java.util.Date;

public class CommentChild {
    private Integer commentid;

    private Integer fromuserid;

    private Integer touserid;

    private String content;

    private Date commenttime;

    public Integer getCommentid() {
        return commentid;
    }

    public void setCommentid(Integer commentid) {
        this.commentid = commentid;
    }

    public Integer getFromuserid() {
        return fromuserid;
    }

    public void setFromuserid(Integer fromuserid) {
        this.fromuserid = fromuserid;
    }

    public Integer getTouserid() {
        return touserid;
    }

    public void setTouserid(Integer touserid) {
        this.touserid = touserid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCommenttime() {
        return commenttime;
    }

    public void setCommenttime(Date commenttime) {
        this.commenttime = commenttime;
    }
}