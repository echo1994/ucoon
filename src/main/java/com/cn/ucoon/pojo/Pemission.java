package com.cn.ucoon.pojo;

import java.io.Serializable;
/*
 * 权限类
 */
public class Pemission implements Serializable{
	private Integer permission_id;
	private String permission_name;
	
	public Pemission() {
		
	}
	public Integer getPermission_id() {
		return permission_id;
	}
	public void setPermission_id(Integer permission_id) {
		this.permission_id = permission_id;
	}
	public String getPermission_name() {
		return permission_name;
	}
	public void setPermission_name(String permission_name) {
		this.permission_name = permission_name;
	}
	
}
