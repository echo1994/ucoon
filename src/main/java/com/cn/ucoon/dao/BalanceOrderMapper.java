package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.BalanceOrder;
import java.util.List;

public interface BalanceOrderMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(BalanceOrder record);

    BalanceOrder selectByPrimaryKey(Integer orderId);

    List<BalanceOrder> selectAll();

    int updateByPrimaryKey(BalanceOrder record);
    
    int updateStatusbyOrdersId(BalanceOrder record);
}