package com.cn.ucoon.service;

import java.util.List;

import com.cn.ucoon.pojo.PhotoTurn;

public interface PhotoTurnService {
		//查看图片
		public List<PhotoTurn> getPhoto();
		//获取单个图片
		public PhotoTurn getOnePhoto(Integer photoId);
		//添加图片
		public Integer addPhoto(PhotoTurn photoTurn);
		//修改图片状态，包括 可用状态，位置，是否跳转
		public Integer changePhoto(PhotoTurn photoTurn);
		//删除图片
		public Integer deletePhoto(Integer photoId); 
		//删除图片,批量
		public Integer deleteGroupPhoto(Integer [] photoIds); 
		//获取轮播展示图片
		public List<PhotoTurn> getOutPic();
}
