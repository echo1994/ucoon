package com.cn.ucoon.service;

import java.math.BigDecimal;

import com.cn.ucoon.pojo.Balance;

public interface BalanceService {

	/**
	 * 查询用户余额
	 * @param userId
	 * @return
	 */
	public BigDecimal countBalance(Integer userId);
	
	/**
	 * 查询累计收入
	 * @param userId
	 * @return
	 */
	public BigDecimal countPlusBalance(Integer userId);
	
	
	public boolean insertBalanceOrder(Balance order);
	
	
	public boolean changeOrderStateByOrderNum(Balance order);
	
	public boolean insertBlance(Balance balance);
	
	
}
