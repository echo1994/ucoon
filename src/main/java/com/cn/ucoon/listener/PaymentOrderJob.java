package com.cn.ucoon.listener;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cn.ucoon.service.MissionService;
import com.cn.ucoon.util.ServiceUtil;

/** 
 * 扫描超过15分钟天未付款的订单关闭 
 */  
public class PaymentOrderJob extends QuartzJobBean{

	
	@Resource
	private MissionService missionService = ServiceUtil.getMissionService();
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		
		missionService.unPaidMissionScan(new DateTime().minusMinutes(15).toDate());
	}

}
