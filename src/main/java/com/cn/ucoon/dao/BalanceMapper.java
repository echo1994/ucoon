package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Balance;

import java.util.List;

public interface BalanceMapper {
    int deleteByPrimaryKey(Integer balanceId);

    int insert(Balance record);

    Balance selectByPrimaryKey(Integer balanceId);

    List<Balance> selectByUserIdAndState(Integer userId,Integer state);
    
    List<Balance> selectAll();

    int updateByPrimaryKey(Balance record);
    
    int updateStatusbyOrdersId(Balance record);
}