package com.cn.ucoon.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Balance {
    private Integer balanceId;

    private String consumingRecords;

    private Date consumingTime;

    private String plusOrMinus;

    private BigDecimal quantity;

    private Integer userId;
    
    private String orderNum;
    
    private Integer orderState;


    public Integer getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Integer balanceId) {
        this.balanceId = balanceId;
    }

    public String getConsumingRecords() {
        return consumingRecords;
    }

    public void setConsumingRecords(String consumingRecords) {
        this.consumingRecords = consumingRecords == null ? null : consumingRecords.trim();
    }

    public Date getConsumingTime() {
        return consumingTime;
    }

    public void setConsumingTime(Date consumingTime) {
        this.consumingTime = consumingTime;
    }

    public String getPlusOrMinus() {
        return plusOrMinus;
    }

    public void setPlusOrMinus(String plusOrMinus) {
        this.plusOrMinus = plusOrMinus == null ? null : plusOrMinus.trim();
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}
    
    
    
}