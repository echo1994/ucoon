package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Balance;

import java.util.List;
import java.util.Map;

public interface BalanceMapper {
    int deleteByPrimaryKey(Integer balanceId);

    int insert(Balance record);

    Balance selectByPrimaryKey(Integer balanceId);

    List<Balance> selectByUserIdAndState(Map<String, Object> map);
    
    List<Balance> selectAll();

    int updateByPrimaryKey(Balance record);
    
    int updateStatusbyOrdersId(Balance record);
}