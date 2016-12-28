package com.cn.ucoon.dao;

import java.util.List;

import com.cn.ucoon.pojo.ServerWeixin;

public interface ServerWeixinMapper {

	//获取微信客服信息
	public List<ServerWeixin> getServer();
}
