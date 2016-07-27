package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Evaluate;
import java.util.List;

public interface EvaluateMapper {
    int insert(Evaluate record);

    List<Evaluate> selectAll();
}