package com.cn.ucoon.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ucoon.dao.CommentChildMapper;
import com.cn.ucoon.dao.CommentMapper;
import com.cn.ucoon.pojo.Comments;
import com.cn.ucoon.pojo.CommentsChild;
import com.cn.ucoon.service.CommentsService;

@Service
@Transactional
public class CommentsServiceImpl implements CommentsService {

	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private CommentChildMapper commentChildMapper;
	
	@Override
	public List<HashMap<String, Object>> selectLimitedbyMissionId(
			Integer missionId, Integer startIndex, Integer endIndex) {
		// TODO Auto-generated method stub
		return commentMapper.selectLimitedbyMissionId(missionId, startIndex, endIndex);
	}

	@Override
	public List<HashMap<String, Object>> selectbyCommentsId(Integer commentsId) {
		// TODO Auto-generated method stub
		return commentChildMapper.selectbyCommentsId(commentsId);
	}

	@Override
	public Comments insertComments(Integer userId, String content,
			Integer missionId) {
		Comments comments = new Comments();
		comments.setUserId(userId);
		comments.setContent(content);
		comments.setMissionId(missionId);
		comments.setCommentTime(new Date());
		commentMapper.insert(comments);
		
		return comments;
	}

	@Override
	public boolean deleteCommentsbyCommentsId(Integer CommentsId) {
		
		int i = commentMapper.deleteByPrimaryKey(CommentsId);
		
		if(i > 0){
			//同时删除子评论
			this.deleteCommentsChildByCommentsId(CommentsId);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean deleteCommentsChildByCommentsId(Integer CommentsId) {
		int i = commentChildMapper.deleteByCommentsId(CommentsId);
		
		if(i > 0){
			return true;
		}
		
		return false;
	}

	@Override
	public CommentsChild insertCommentsChild(Integer fromUserId,
			Integer toUserId, String content, Integer commentId) {
		CommentsChild child = new CommentsChild();
		
		child.setCommentId(commentId);
		child.setCommentTime(new Date());
		child.setContent(content);
		child.setFromuserId(fromUserId);
		child.setTouserId(toUserId);
		commentChildMapper.insert(child);
		
		return child;
	}

	@Override
	public boolean deleteCommentsChildByCommentsChildId(Integer CommentsChildId) {
		
		int i = commentChildMapper.deleteByPrimaryKey(CommentsChildId);
		
		if(i > 0){
			return true;
		}
		
		return false;
	}

}
