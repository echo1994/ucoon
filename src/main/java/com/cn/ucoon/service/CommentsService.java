package com.cn.ucoon.service;

import java.util.HashMap;
import java.util.List;

import com.cn.ucoon.pojo.Comments;
import com.cn.ucoon.pojo.CommentsChild;

public interface CommentsService {

	
	public List<HashMap<String, Object>> selectLimitedbyMissionId(Integer missionId,
			Integer startIndex, Integer endIndex);
	
	public List<HashMap<String, Object>> selectbyCommentsId(Integer commentsId);
	
	
	public Comments insertComments(Integer userId,String content,Integer missionId); 
	
	public CommentsChild insertCommentsChild(Integer fromUserId,Integer toUserId,String content,Integer commentId); 
	
	public boolean deleteCommentsbyCommentsId(Integer CommentsId);
	
	public boolean deleteCommentsChildByCommentsId(Integer CommentsId);
	
	public boolean deleteCommentsChildByCommentsChildId(Integer CommentsChildId);
}
