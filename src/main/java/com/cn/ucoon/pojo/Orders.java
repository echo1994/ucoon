package com.cn.ucoon.pojo;

import java.util.Date;

public class Orders {
	private Integer orderId;

	private String orderNum;

	private Integer userId;

	private Integer missionId;

	private Date finishTime;

	private Date orderTime;

	private Integer state;

	public Orders(String orderNum, Integer userId, Integer missionId,
			Date orderTime, Integer state) {
		super();
		this.orderNum = orderNum;
		this.userId = userId;
		this.missionId = missionId;
		this.orderTime = orderTime;
		this.state = state;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
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

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderNum=" + orderNum
				+ ", userId=" + userId + ", missionId=" + missionId
				+ ", finishTime=" + finishTime + ", orderTime=" + orderTime
				+ ", state=" + state + "]";
	}

}