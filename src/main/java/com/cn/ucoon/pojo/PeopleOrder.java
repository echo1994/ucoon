package com.cn.ucoon.pojo;

import java.util.Date;

public class PeopleOrder {
    private Integer userid;

    private Integer orderid;

    private Date taketime;

    private Integer takestate;

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

    public Date getTaketime() {
        return taketime;
    }

    public void setTaketime(Date taketime) {
        this.taketime = taketime;
    }

    public Integer getTakestate() {
        return takestate;
    }

    public void setTakestate(Integer takestate) {
        this.takestate = takestate;
    }
}