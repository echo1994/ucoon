package com.cn.ucoon.pojo.wx.resp;

public class ImageMessage{
	//校历qzon60gJ3Ws_YtXx3CIwmcABRCBdywATOKrjQMv1vTA\
	
	//{"media_id":"qzon60gJ3Ws_YtXx3CIwmXF0R8lbccX3s4ohf25Vrow","url":"https://mmbiz.qlogo.cn/mmbiz/jm1tCd0gIGH3b8ODEHC32L7ZHE0YtMicBA5dnUsz7VQYuQGymXzGSKjRwcPCEIDCHibrPh0CTiaiaEy6xZGDZWGjJA/0?wx_fmt=jpeg"}
	//qzon60gJ3Ws_YtXx3CIwmXF0R8lbccX3s4ohf25Vrow

	private String ToUserName;//���շ��˺�
	
	private String FromUserName;//������΢�ź�
	
	private long CreateTime;
	
	private String MsgType;
	
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	
	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
}
