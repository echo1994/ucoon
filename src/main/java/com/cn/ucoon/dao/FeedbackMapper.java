package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Feedback;

import java.util.List;
import java.util.Map;

public interface FeedbackMapper {
    int deleteByPrimaryKey(Integer feedbackId);

    int insert(Feedback record);

    Feedback selectByPrimaryKey(Integer feedbackId);

    List<Feedback> selectAll();

    int updateByPrimaryKey(Feedback record);
    
    
    // 通过 反馈状态 已阅 未阅+分页
 	public List<Feedback> selectType(Map map);

 	public Integer selectTypeCount(Integer status);

 	// 通过ID获取该feedBack详情
 	public Feedback getDetail(Integer id);

 	public int updateStatus(Feedback feedback);
}