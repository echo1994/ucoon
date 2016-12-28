package com.cn.ucoon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.PhotoTurnMapper;
import com.cn.ucoon.pojo.PhotoTurn;
import com.cn.ucoon.service.PhotoTurnService;


@Service
@Transactional
public class PhotoTurnServiceImpl implements PhotoTurnService{
	
	@Autowired
	private PhotoTurnMapper photoTurnMapper;
	
	@Override
	public List<PhotoTurn> getPhoto() { 
		return photoTurnMapper.getPhoto();
	}

	@Override
	public Integer addPhoto(PhotoTurn photoTurn) {
		
		return photoTurnMapper.addPhoto(photoTurn);
	}

	@Override
	public Integer changePhoto(PhotoTurn photoTurn) {
		photoTurnMapper.changePhoto(photoTurn);
		return 1;
	}

	@Override
	public Integer deletePhoto(Integer photoId) {
		//photoTurnMapper.deletePhoto(photoId);
		return photoTurnMapper.deletePhoto(photoId);
	}

	@Override
	public Integer deleteGroupPhoto(Integer[] photoIds) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("photoIds", photoIds);
		photoTurnMapper.deleteGroupPhoto(map);
		return 1;
	}

	@Override
	public PhotoTurn getOnePhoto(Integer photoId) {
		// TODO Auto-generated method stub
		return photoTurnMapper.getOnePhoto(photoId);
	}

	@Override
	public List<PhotoTurn> getOutPic() {
		// TODO Auto-generated method stub
		return photoTurnMapper.getOutPic();
	}

}
