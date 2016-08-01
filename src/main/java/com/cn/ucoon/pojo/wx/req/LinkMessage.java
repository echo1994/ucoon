package com.cn.ucoon.pojo.wx.req;

public class LinkMessage extends BaseMessage{

	private String Title;//��Ϣ����
	
	private String Description;//��Ϣ����
	
	private String Url;//��Ϣ����

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
}
