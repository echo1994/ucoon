package com.cn.ucoon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.CreditsMapper;
import com.cn.ucoon.pojo.Credits;
import com.cn.ucoon.service.CreditsService;


@Service
@Transactional
public class CreditsServiceImpl implements CreditsService {

	@Autowired
	private CreditsMapper creditsMapper;
	
	@Override
	public Integer countCredits(Integer userId) {

		Integer total = 0;
		List<Credits> lists = creditsMapper.selectByUserId(userId);
		for (int i = 0; i < lists.size(); i++) {
			String C = lists.get(i).getPlusOrMinus();
			
			if(C.equals("plus")){
				total = total + lists.get(i).getQuantity();
				
			}else{
				
				total = total - lists.get(i).getQuantity();
			}
		}
		
		return total;
	}

}
