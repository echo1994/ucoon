package com.cn.ucoon.service;

import java.math.BigDecimal;

public interface BalanceService {

	/**
	 * 查询用户余额
	 * @param userId
	 * @return
	 */
	public BigDecimal countBalance(Integer userId);
}
