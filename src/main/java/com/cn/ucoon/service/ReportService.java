package com.cn.ucoon.service;

import com.cn.ucoon.pojo.Feedback;

public interface ReportService {
	
	public boolean saveMissionReport(Integer missionId,Integer userId,String content);
	
	public boolean saveFeedBack(Feedback feedback);

}
