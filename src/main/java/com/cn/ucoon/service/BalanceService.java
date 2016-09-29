package com.cn.ucoon.service;

import java.math.BigDecimal;

import com.cn.ucoon.pojo.Balance;
import com.cn.ucoon.pojo.BalanceOrder;

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
	
	
	public boolean insertBalanceOrder(BalanceOrder order);
	
	
	public boolean changeOrderStateByOrderNum(BalanceOrder order);
	
	public boolean insertBlance(Balance balance);
	
	
}
