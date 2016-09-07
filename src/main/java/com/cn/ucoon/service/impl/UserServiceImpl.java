package com.cn.ucoon.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cn.ucoon.dao.UserMapper;
import com.cn.ucoon.pojo.User;
import com.cn.ucoon.service.UserService;
import com.cn.ucoon.util.TimeUtil;
import com.cn.ucoon.util.WeixinUtil;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userDao;
	
	@Override
	public User getUserById(int userId) {
		return userDao.selectByPrimaryKey(userId);
	}

	
	/**
	 * 用户第一次拉取的信息为用户的注册信息，往后的信息不再拉取直接跳过
	 */
	@Override
	public User getUserByCode(String code) {
		User user = null;
		
		try {
			JSONObject jsonObject = WeixinUtil.getAuthAccessToken(WeixinUtil.appid, WeixinUtil.appsecret, code);

			String access_token = jsonObject.getString("access_token");//网页授权需要的凭证
			String openid = jsonObject.getString("openid");//用户openid
			System.out.println(openid);
			
			//String unionid = jsonObject.getString("unionid");//微信开放云id
			
			//通过openid判断用户是否已经拉取，即判断用户是否存在
			user = userDao.selectByOpenId(openid);
			if(user == null){
				user = new User();
				
				Date regist_time  = new Date();
				
				JSONObject userJsonObject = WeixinUtil.getUserInfo(access_token, openid);
				user.setOpenId(openid);
				user.setNickName(userJsonObject.getString("nickname"));
				user.setSex(userJsonObject.getInteger("sex"));
				user.setProvince(userJsonObject.getString("province"));
				user.setCity(userJsonObject.getString("city"));
				user.setCountry(userJsonObject.getString("country"));
				user.setHeadImgUrl(userJsonObject.getString("headimgurl"));
				user.setRegistTime(regist_time);//根据当前时间戳更新
				
				userDao.regist(user);
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			//return null;
		}
		
		return user;
	}


	@Override
	public List<User> getAllUser() {
		return userDao.selectAll();
	}


	@Override
	public String getHeadUrl(String userId) {
		return userDao.selectUserHeadUrl(userId);
	}


	@Override
	public String getOpenIdbyUserId(Integer userId) {
		return userDao.selectOpenId(userId);
	}

//	public User getUserById(int userId) {
//		// TODO Auto-generated method stub
//		return this.userDao.selectByPrimaryKey(userId);
//	}
	
	
}
