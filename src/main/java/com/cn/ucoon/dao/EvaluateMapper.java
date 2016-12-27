package com.cn.ucoon.dao;

import com.cn.ucoon.pojo.Evaluate;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface EvaluateMapper {
    int deleteByPrimaryKey(@Param("missionId") Integer missionId, @Param("publishId") Integer publishId, @Param("executorId") Integer executorId);

    int insert(Evaluate record);

    Evaluate selectByPrimaryKey(@Param("missionId") Integer missionId, @Param("publishId") Integer publishId, @Param("executorId") Integer executorId);

    List<Evaluate> selectAll();

    int updateByPrimaryKey(Evaluate record);
    
    int updatePublishByMidAndPidAndEid(Evaluate record);
    
    Evaluate selectByMissionId(Integer missionId);
    
    Evaluate selectByMidAndPidAndEid(Integer missionId,Integer publishId,Integer executorId);
    
    
    int updateExecutorByMidAndPidAndEid(Evaluate evaluate);
    
    Integer selectExecutorScoreByUserId(Integer userId);
    
    Integer selectPublishScoreByUserId(Integer userId);
    
    List<HashMap<String, Object>> selectLimitedbyPublishId(Integer publishId,
			Integer startIndex, Integer endIndex);
}