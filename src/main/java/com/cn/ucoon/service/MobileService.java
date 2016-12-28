package com.cn.ucoon.service;

import java.util.List;
import java.util.Map;

import com.cn.ucoon.pojo.Mobile;

public interface MobileService {
	
	//获取模板信息
		public List<Mobile> getMobiles();

		//获取一条mobile数据
		public Mobile getOneMobile(String mobileid);

		//保存模板信息录入模板消息
		public Integer addMobile(Mobile mobile);
		//保存模板信息
		//修改模板信息
		//删除模板信息
		public Integer delOneMobile(Integer MobileId);
		public Integer delMoreMobile(Integer[] mobileids);

}
