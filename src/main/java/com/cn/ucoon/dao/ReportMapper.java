package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Report;
import java.util.List;

public interface ReportMapper {
    int deleteByPrimaryKey(Integer reportId);

    int insert(Report record);

    Report selectByPrimaryKey(Integer reportId);

    List<Report> selectAll();

    int updateByPrimaryKey(Report record);
    
}