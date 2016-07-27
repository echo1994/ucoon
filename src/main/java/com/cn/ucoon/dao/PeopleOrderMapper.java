package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.PeopleOrder;
import java.util.List;

public interface PeopleOrderMapper {
    int insert(PeopleOrder record);

    List<PeopleOrder> selectAll();
}