package com.cn.ucoon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.MobileMapper;
import com.cn.ucoon.pojo.Mobile;
import com.cn.ucoon.service.MobileService;

@Service
@Transactional
public class MobileServiceImpl implements MobileService{

	@Autowired
	private MobileMapper mobileMapper;
	
	
	@Override
	public List<Mobile> getMobiles() {
		// TODO Auto-generated method stub
		return mobileMapper.getMobiles();
	}


	@Override
	public Mobile getOneMobile(String mobileid) {
		// TODO Auto-generated method stub
		return mobileMapper.getOneMobile(mobileid);
	}


	@Override
	public Integer addMobile(Mobile mobile) {
		// TODO Auto-generated method stub
		return mobileMapper.addMobile(mobile);
	}


	@Override
	public Integer delOneMobile(Integer MobileId) {
		// TODO Auto-generated method stub
		return mobileMapper.delOneMobile(MobileId);
	}


	@Override
	public Integer delMoreMobile(Integer[] mobileids) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobileids", mobileids);
		return mobileMapper.delMoreMobile(map);
	}

}
