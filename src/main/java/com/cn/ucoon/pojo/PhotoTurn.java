package com.cn.ucoon.pojo;

public class PhotoTurn implements java.io.Serializable {
	
	//主健 id
	private Integer photoId;
	//图片本地路径
	private String photoUrl;
	//图片名称
	private String photoName;
	//图片跳转路径
	private String photoGoUrl;
	//图片是否跳转 1：可跳转  0：不可跳
	private Integer isGo;
	//轮播图的位置
	private Integer local;
	public Integer getPhotoId() {
		return photoId;
	}
	public void setPhotoId(Integer photoId) {
		this.photoId = photoId;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getPhotoGoUrl() {
		return photoGoUrl;
	}
	public void setPhotoGoUrl(String photoGoUrl) {
		this.photoGoUrl = photoGoUrl;
	}
	public Integer getIsGo() {
		return isGo;
	}
	public void setIsGo(Integer isGo) {
		this.isGo = isGo;
	}
	public Integer getLocal() {
		return local;
	}
	public void setLocal(Integer local) {
		this.local = local;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	
	
	
}
