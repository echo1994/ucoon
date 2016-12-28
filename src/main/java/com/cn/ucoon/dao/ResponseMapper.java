package com.cn.ucoon.dao;

import java.util.List;

import com.cn.ucoon.pojo.Response;

public interface ResponseMapper {
	//添加数据
	int insert(Response response);
	//根据关键词查询回复
	Response selectByKey(String key);
	//查询所有数据
	List<Response> select();
	//根据id删除数据
	int deleteById(int id);
}
