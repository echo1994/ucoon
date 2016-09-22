package com.cn.ucoon.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.ReportMapper;
import com.cn.ucoon.pojo.Report;
import com.cn.ucoon.service.ReportService;


@Service
@Transactional
public class ReportServiceImpl implements ReportService {

	@Resource
	private ReportMapper reportMapper;
	
	@Override
	public boolean saveMissionReport(Integer missionId, Integer userId,
			String content) {
		Report record = new Report();
		record.setContent(content);
		record.setMissionId(missionId);
		record.setUserId(userId);
		
		
		int i = reportMapper.insert(record);
	
		if(i > 0){
			return true;
			
		}
		
		return false;
	}

}
