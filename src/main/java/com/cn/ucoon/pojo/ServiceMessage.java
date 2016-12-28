package com.cn.ucoon.pojo;

import java.util.Date;

public class ServiceMessage {

	//主键ID
	private Integer serviceMessageId;
	//接受的用户userid
	private String openId;
	//短信内容
	private String serviceMessageContent;
	//短信时间
	private Date sendTime; 
	//用户名称
	private String userName;
	//手机号码
	private String phone;
	//消息状态
	private String type;
	//其他参数
	private String otherParam;
	//发用的客服id
	private Integer serverId;
	//客服名称
	private String serverName;
	
	
	
	 
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public Integer getServerId() {
		return serverId;
	}
	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}
	public String getOtherParam() {
		return otherParam;
	}
	public void setOtherParam(String otherParam) {
		this.otherParam = otherParam;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	} 
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Integer getServiceMessageId() {
		return serviceMessageId;
	}
	public void setServiceMessageId(Integer serviceMessageId) {
		this.serviceMessageId = serviceMessageId;
	}
	public String getServiceMessageContent() {
		return serviceMessageContent;
	}
	public void setServiceMessageContent(String serviceMessageContent) {
		this.serviceMessageContent = serviceMessageContent;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	
}
