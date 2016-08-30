package com.cn.ucoon.service;

import java.util.List;

import com.cn.ucoon.pojo.Admin;

public interface AdminService {

	/**
	 * 验证用户名和密码 
	 * @param username
	 * @param password
	 * @return
	 */
	public Admin checkUser(String username,String password);
	
	public List<Admin> getAllAdmins();
}
