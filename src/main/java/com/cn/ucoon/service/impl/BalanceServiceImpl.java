package com.cn.ucoon.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.BalanceMapper;
import com.cn.ucoon.pojo.Balance;
import com.cn.ucoon.service.BalanceService;

@Service
@Transactional
public class BalanceServiceImpl implements BalanceService {

	@Autowired
	private BalanceMapper balanceMapper;
	
	
	
	@Override
	public BigDecimal countBalance(Integer userId) {
		
		BigDecimal total = new BigDecimal("0.00");
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(3);
		list.add(4);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("userId", userId);
		List<Balance> lists = balanceMapper.selectByUserIdAndState(map);
		for (int i = 0; i < lists.size(); i++) {
			String C = lists.get(i).getPlusOrMinus();
			if(C.equals("plus")){
				total = total.add(lists.get(i).getQuantity());
			}else{
				total = total.subtract(lists.get(i).getQuantity());
			}
		}
		
		
		return total;
	}


	@Override
	public BigDecimal countPlusBalance(Integer userId) {
		BigDecimal total = new BigDecimal("0.00"); //四舍五入
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(3);
		list.add(4);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("userId", userId);
		List<Balance> lists = balanceMapper.selectByUserIdAndState(map);
		for (int i = 0; i < lists.size(); i++) {
			String C = lists.get(i).getPlusOrMinus();
			
			if(C.equals("plus")){
				total = total.add(lists.get(i).getQuantity());
				
			}
		}
		
		return total;
	}


	@Override
	public boolean insertBalanceOrder(Balance order) {
		int i = balanceMapper.insert(order);
		if(i > 0){
			return true;
		}
		
		
		return false;
	}


	@Override
	public boolean changeOrderStateByOrderNum(Balance order) {
		int i = balanceMapper.updateStatusbyOrdersId(order);
		
		if( i > 0 ){
			return true;
		}
		
		return false;
	}


	@Override
	public boolean insertBlance(Balance balance) {
		int i = balanceMapper.insert(balance);
		if(i > 0){
			return true;
			
		}
		
		return false;
	}

}
