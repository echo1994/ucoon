package com.cn.ucoon.pojo;

import java.util.List;

public class Mobile implements java.io.Serializable{
	
	private Integer id;
	//模板Id
	private String mobileId;
	//模板标题
	private String mobileTitle;
	//模板头部参数
	private String mobileFirstParam;
	//模板 底部参数
	private String mobileRemark;
	//模板参数
	private String mobileContents;
	private String[] contents;
	private String[] colors;
	private String [] keywords;
	
	
	
	
	
	public String[] getColors() {
		return colors;
	}
	public void setColors(String[] colors) {
		this.colors = colors;
	}
	public String[] getKeywords() {
		return keywords;
	}
	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}
	public String[] getContents() {
		return contents;
	}
	public void setContents(String[] contents) {
		this.contents = contents;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMobileId() {
		return mobileId;
	}
	public void setMobileId(String mobileId) {
		this.mobileId = mobileId;
	}
	public String getMobileTitle() {
		return mobileTitle;
	}
	public void setMobileTitle(String mobileTitle) {
		this.mobileTitle = mobileTitle;
	}
	public String getMobileFirstParam() {
		return mobileFirstParam;
	}
	public void setMobileFirstParam(String mobileFirstParam) {
		this.mobileFirstParam = mobileFirstParam;
	}
	public String getMobileRemark() {
		return mobileRemark;
	}
	public void setMobileRemark(String mobileRemark) {
		this.mobileRemark = mobileRemark;
	}
	public String getMobileContents() {
		return mobileContents;
	}
	public void setMobileContents(String mobileContents) {
		this.mobileContents = mobileContents;
	}


	
}
