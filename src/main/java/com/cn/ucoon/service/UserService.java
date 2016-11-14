package com.cn.ucoon.service;

import java.util.HashMap;
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
	public String getHeadUrl(Integer userId);
	
	/**
	 * 根据user_id获取open_id
	 * @param userId
	 * @return
	 */
	public String getOpenIdbyUserId(Integer userId);
	
	
	/**
	 * 根据open_id获取user_id
	 * @param userId
	 * @return
	 */
	public Integer getUserIdbyOpenId(String openId);
	
	public List<User> getAllUser();


	/**
	 * 更新用户地理位置
	 * @param latitude
	 * @param longitude
	 */
	public void updatePosition(String latitude,String longitude);
	
	/**
	 * 更新签名
	 * @param latitude
	 * @param longitude
	 */
	public boolean updateUserSignature(User user);
	
	/**
	 * 更新电话
	 * @param latitude
	 * @param longitude
	 */
	public boolean updateUserPhone(User user);
	
	/**
	 * 更新微信号
	 * @param latitude
	 * @param longitude
	 */
	public boolean updateUserWxId(User user);
	
	
	public boolean updateUserName(User user);
	
	
	/**
	 * 判断是否绑定手机
	 * @param userId
	 * @return
	 */
	public boolean isBindPhone(Integer userId);
	
	
	/**
	 * 获取用户的详细信息 包含评价信息
	 * @param userId
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public List<HashMap<String, Object>> selectDetailWithEvabyUserId(Integer userId);
	
}
