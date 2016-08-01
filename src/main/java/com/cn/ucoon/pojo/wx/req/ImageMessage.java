package com.cn.ucoon.pojo.wx.req;


public class ImageMessage extends BaseMessage{
	
	private String PicUrl;//ͼƬ����
	
	private String MediaId;//ͼƬ��Ϣý��id�����Ե��ö�ý���ļ����ؽӿ���ȡ��ݡ�

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	
}
