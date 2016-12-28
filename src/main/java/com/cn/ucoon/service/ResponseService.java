package com.cn.ucoon.service;

import java.util.List;

import com.cn.ucoon.pojo.Response;

public interface ResponseService {
	//添加回复信息
	public int insertResponse(Response response);
	
	//查询回复信息
	public Response findByName(String name);
	
	//查询所有
	public List<Response> findall();
	
	//删除数据
	public int deleteById(int id);
}
