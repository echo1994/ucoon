package com.cn.ucoon.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.ResponseMapper;
import com.cn.ucoon.pojo.Response;
import com.cn.ucoon.service.ResponseService;

@Service
@Transactional
public class ResponseServiceImpl implements ResponseService{
	
	@Resource
	private ResponseMapper responseDao;
	
	@Override
	public int insertResponse(Response response) {
		// TODO Auto-generated method stub
		return responseDao.insert(response);
	}

	@Override
	public Response findByName(String name) {
		// TODO Auto-generated method stub
		return responseDao.selectByKey(name);
	}

	@Override
	public List<Response> findall() {
		// TODO Auto-generated method stub
		return responseDao.select();
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return responseDao.deleteById(id);
	}

}
