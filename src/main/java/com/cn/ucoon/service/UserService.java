package com.cn.ucoon.service;

import java.util.List;

import com.cn.ucoon.pojo.User;

public interface UserService {
	
	public User getUserById(int userId);
	
	
	/**
	 * 网页授权登陆，首次返回保存用户对象
	 * @param code
	 * @return 返回用户对象
	 */
	public User getUserByCode(String code);
	
	
	/**
	 * 获取用户头像url
	 * @param userId
	 * @return
	 */
	public String getHeadUrl(String userId);
	
	/**
	 * 根据user_id获取open_id
	 * @param userId
	 * @return
	 */
	public String getOpenIdbyUserId(Integer userId);
	
	public List<User> getAllUser();


	
	
	
}
