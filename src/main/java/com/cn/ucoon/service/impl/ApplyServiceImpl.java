package com.cn.ucoon.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ucoon.dao.ApplyMapper;
import com.cn.ucoon.dao.OrderMapper;
import com.cn.ucoon.pojo.Apply;
import com.cn.ucoon.pojo.Orders;
import com.cn.ucoon.service.ApplyService;
import com.cn.ucoon.service.OrderService;

@Service
public class ApplyServiceImpl implements ApplyService {

	@Autowired
	private ApplyMapper applyMapper;

	@Autowired
	private OrderMapper orderMapper;

	@Override
	public List<HashMap<String, String>> selectApplybyUMID(Integer userId,
			Integer missionId) {
		// TODO Auto-generated method stub
		return applyMapper.selectApplybyUMID(userId, missionId);
	}

	@Override
	public int addAppliment(Apply apply) {
		// TODO Auto-generated method stub
		return applyMapper.insert(apply);
	}

	@Override
	public List<HashMap<String, String>> selectByMissionId(Integer missionId) {
		// TODO Auto-generated method stub
		return applyMapper.selectByMissionId(missionId);
	}

	@Override
	public boolean confirmApply(Integer applyId, Integer result) {
		// TODO Auto-generated method stub
		// 先改变apply的状态，再生成订单
		if (result == 1) {
			// for (int i = 0; i < userIds.length; i++) {
			try {
				applyMapper.updateStateByApplyId(applyId, 1);// 1表示申请被确认接受
				Apply apply = applyMapper.selectByPrimaryKey(applyId);
				System.out.println(apply);
				Orders orders = new Orders(getOrdersNum(apply.getUserId(),
						apply.getMissionId()), apply.getUserId(),
						apply.getMissionId(), new Date(), 0);
				System.out.println(orders);
				System.out.println(orderMapper.insert(orders));// 生成订单
				// }
			} catch (Exception r) {
				r.printStackTrace();
			}
		} else {
			applyMapper.updateStateByApplyId(applyId, 2);// 2拒绝
		}
		return true;
	}

	public String getOrdersNum(Integer userId, Integer missionId) {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String nums = dateFormat.format(date) + missionId
				+ (int) (Math.random() * 10) + userId
				+ (int) (Math.random() * 10);

		return nums;
	}

	@Override
	public Apply selectByPrimaryKey(Integer applyId) {
		// TODO Auto-generated method stub
		return applyMapper.selectByPrimaryKey(applyId);
	}

}
