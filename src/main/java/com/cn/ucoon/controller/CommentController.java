package com.cn.ucoon.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cn.ucoon.pojo.Comments;
import com.cn.ucoon.pojo.CommentsChild;
import com.cn.ucoon.pojo.User;
import com.cn.ucoon.service.CommentsService;
import com.cn.ucoon.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentsService commentsService;

	@Autowired
	private UserService userService;

	/**
	 * 分页查询
	 * 
	 * @param missionId
	 *            通过missionId加载
	 * @param startIndex
	 *            开始位置
	 * @param endIndex
	 *            结束位置
	 * @return json
	 */
	@RequestMapping(value = "/getCommentsLimited", method = RequestMethod.POST)
	@ResponseBody
	public String getMissionsLimited(
			@RequestParam(value = "missionId", required = true) Integer missionId,
			@RequestParam(value = "startIndex", required = true) Integer startIndex,
			@RequestParam(value = "endIndex", required = true) Integer endIndex,
			HttpServletRequest request) {
		List<HashMap<String, Object>> comments = null;

		comments = commentsService.selectLimitedbyMissionId(missionId,
				startIndex, endIndex);
		for (int i = 0; i < comments.size(); i++) {
			List<HashMap<String, Object>> commentsChild = commentsService
					.selectbyCommentsId((Integer) comments.get(i).get(
							"comment_id"));
			comments.get(i).put("child", commentsChild);
		}
		ObjectMapper mapper = new ObjectMapper();
		String jsonfromList = "";
		try {
			jsonfromList = mapper.writeValueAsString(comments);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonfromList = "{}";
		}
		System.out.println(jsonfromList);
		return jsonfromList;
	}

	@RequestMapping(value = "/saveComments", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject saveComment(
			@RequestParam(value = "content", required = true) String content,
			@RequestParam(value = "missionId", required = true) Integer missionId,
			HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Integer user_id = (Integer) request.getSession()
				.getAttribute("user_id");

		Comments comments = commentsService.insertComments(user_id, content,
				missionId);

		if (comments == null) {
			json.put("result", "error");
			json.put("msg", "评论出错了~");
			return json;
		}

		json.put("result", "success");
		User user = userService.getUserById(user_id);
		JSONObject commentsJson = (JSONObject) JSONObject.toJSON(comments);
		JSONObject userJson = (JSONObject) JSONObject.toJSON(user);
		JSONObject json2 = new JSONObject();
		json2.put("user", userJson);
		json2.put("comments", commentsJson);
		json.put("msg", json2);

		System.out.println(json);

		return json;
	}

	@RequestMapping(value = "/saveCommentsChild", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject saveCommentChild(
			@RequestParam(value = "content", required = true) String content,
			@RequestParam(value = "commentId", required = true) Integer commentId,
			@RequestParam(value = "toUserId", required = true) Integer toUserId,
			HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Integer user_id = (Integer) request.getSession()
				.getAttribute("user_id");

		CommentsChild child = commentsService.insertCommentsChild(user_id,
				toUserId, content, commentId);

		if (child == null) {
			json.put("result", "error");
			json.put("msg", "评论出错了~");
			return json;
		}

		json.put("result", "success");
		User fromUser = userService.getUserById(user_id);
		User toUser = userService.getUserById(toUserId);
		JSONObject commentsJson = (JSONObject) JSONObject.toJSON(child);
		JSONObject fromUserJson = (JSONObject) JSONObject.toJSON(fromUser);
		JSONObject toUserJson = (JSONObject) JSONObject.toJSON(toUser);

		JSONObject json2 = new JSONObject();
		json2.put("fromuser", fromUserJson);
		json2.put("touser", toUserJson);
		json2.put("commentschild", commentsJson);
		json.put("msg", json2);

		System.out.println(json);

		return json;
	}

	@RequestMapping(value = "/checkComments", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject checkComment(
			@RequestParam(value = "commentId", required = true) Integer commentId,
			@RequestParam(value = "userId", required = true) Integer fromuserId,
			HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Integer user_id = (Integer) request.getSession()
				.getAttribute("user_id");
		if (fromuserId == user_id) {
			// 如果是同一个用户操作的话，表示删除
			json.put("result", "delete");
			json.put("msg", commentId);
			return json;
		}
		JSONObject user = (JSONObject) JSONObject.toJSON(userService
				.getUserById(fromuserId));
		json.put("result", "success");
		json.put("user", user);
		return json;
	}

	@RequestMapping(value = "/deleteComments", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteComment(
			@RequestParam(value = "commentId", required = true) Integer commentId,
			HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Integer user_id = (Integer) request.getSession()
				.getAttribute("user_id");
		if (user_id == null) {
			json.put("result", "error");
			json.put("msg", "未登录");
			return json;
		}
		if (commentsService.deleteCommentsbyCommentsId(commentId)) {
			json.put("result", "success");
			json.put("msg", "删除成功");
		} else {
			json.put("result", "fail");
			json.put("msg", "删除失败");
		}
		return json;
	}

	@RequestMapping(value = "/deleteCommentsChild", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteCommentChild(
			@RequestParam(value = "commentChildId", required = true) Integer commentChildId,
			HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Integer user_id = (Integer) request.getSession()
				.getAttribute("user_id");
		if (user_id == null) {
			json.put("result", "error");
			json.put("msg", "未登录");
			return json;
		}
		if (commentsService
				.deleteCommentsChildByCommentsChildId(commentChildId)) {
			json.put("result", "success");
			json.put("msg", "删除成功");
		} else {
			json.put("result", "fail");
			json.put("msg", "删除失败");
		}
		return json;
	}
}
