package com.cn.ucoon.pojo;

import java.util.Date;

public class Messages {
    private Integer id;

    private String postmessages;

    private Integer status;

    private Date time;

    private Integer messagestypeid;

    private Integer fromuserid;

    private Integer touserid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPostmessages() {
        return postmessages;
    }

    public void setPostmessages(String postmessages) {
        this.postmessages = postmessages == null ? null : postmessages.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getMessagestypeid() {
        return messagestypeid;
    }

    public void setMessagestypeid(Integer messagestypeid) {
        this.messagestypeid = messagestypeid;
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
}