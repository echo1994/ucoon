package com.cn.ucoon.service;

import java.util.List;
import java.util.Map;

import com.cn.ucoon.pojo.ServiceMessage;

public interface ServiceMessageService {
		//查看所有
		public List<ServiceMessage> getAllServiceMessage();
		//单个发送
		public Integer sendOneServiceMessage(ServiceMessage servicemsg);
		//部分发送
		//单个删除
		public Integer delOneServiceMessage(Integer servicemessageId);
		//批量删除
		public Integer delmoreServiceMessage(Integer [] servicemessageIds);
		//获取所有的openid
		public List<String> getAllOpenId();
}
