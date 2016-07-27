package com.cn.ucoon.pojo;

import java.util.Date;

public class Mission {
    private Integer id;

    private Integer missiontypeid;

    private String title;

    private String describe;

    private String pictures;

    private Integer peoplenum;

    private String place;

    private Date starttime;

    private Date endtime;

    private Date publishtime;

    private Integer userid;

    private Integer orderid;

    private Integer views;

    private String leavemessage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMissiontypeid() {
        return missiontypeid;
    }

    public void setMissiontypeid(Integer missiontypeid) {
        this.missiontypeid = missiontypeid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures == null ? null : pictures.trim();
    }

    public Integer getPeoplenum() {
        return peoplenum;
    }

    public void setPeoplenum(Integer peoplenum) {
        this.peoplenum = peoplenum;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getLeavemessage() {
        return leavemessage;
    }

    public void setLeavemessage(String leavemessage) {
        this.leavemessage = leavemessage == null ? null : leavemessage.trim();
    }
}