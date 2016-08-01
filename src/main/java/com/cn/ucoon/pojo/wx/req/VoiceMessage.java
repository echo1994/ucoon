package com.cn.ucoon.pojo.wx.req;

public class VoiceMessage extends BaseMessage{

	
	private String MediaId;//ý��ID
	
	private String Format;//������ʽ

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}
	
	
}
