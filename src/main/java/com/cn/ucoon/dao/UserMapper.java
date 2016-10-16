package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.User;

import java.util.HashMap;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    User selectByPrimaryKey(Integer userId);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
    
    //通过openid取得用户对象
    User selectByOpenId(String openId);
    
    //微信授权注册用户
    int regist(User record);

    //获取用户头像
	String selectUserHeadUrl(String userId);
	
	//根据user_id获取open_id
	String selectOpenId(Integer userId);
	
	int updateUserLatAndLng(User record);
	
	int updateUserSignatrue(User record);
	
	int updateUserPhone(User record);
	
	int updateUserWxId(User record);
	
	int updateUserName(User user);
	
	Integer selectUserId(String open_id);
	
	String selectPhoneByUserId(Integer userId);
	
	
	List<HashMap<String, Object>> selectForUserDetailsEvaluate(Integer userId);
		
}