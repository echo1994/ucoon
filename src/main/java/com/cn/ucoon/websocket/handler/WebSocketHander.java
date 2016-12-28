package com.cn.ucoon.websocket.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.ucoon.pojo.Messages;
import com.cn.ucoon.pojo.User;
import com.cn.ucoon.service.MessageService;
import com.cn.ucoon.service.UserService;
import com.cn.ucoon.util.ServiceUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Spring WebSocket API的核心接口 消息处理中心
 * 
 * @author mlk
 *
 */
public class WebSocketHander implements WebSocketHandler {

	private static final Logger logger = Logger
			.getLogger(WebSocketHander.class);

	private static final ArrayList<WebSocketSession> users = new ArrayList<>();

	// private ApplicationContext ac = null;

	@Resource
	private MessageService messageService = ServiceUtil.getMessageService();

	@Resource
	private UserService userService = ServiceUtil.getUserService();

	// 初次链接成功执行
	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {

		// WebApplicationContext applicationContext = WebApplicationContextUtils
		// .getWebApplicationContext(sce.getServletContext());
		//
		System.out.println("链接成功");
		users.add(session);
		String toUserID = (String) session.getAttributes().get("TOUSERID");
		String fromUserID = (String) session.getAttributes().get("FROMUSERID");
		if (fromUserID != null && toUserID != null) {
			// 查询未读消息,及历史记录 echo 代表对方发的 self代表自己发的

			Integer count = 5;
			List<HashMap<String, Object>> list = messageService
					.selectMsgHistory(Integer.valueOf(fromUserID),
							Integer.valueOf(toUserID), 0, count);
			for (int i = 0; i < list.size(); i++) {
				Integer fromUserId = (Integer) list.get(i).get("from_user_id");
				User user = userService.getUserById(fromUserId);
				list.get(i).put("nick_name", user.getNickName());
				list.get(i).put("head_img_url", user.getHeadImgUrl());
				if (fromUserId == Integer.valueOf(fromUserID)) {
					// 自己发的
					list.get(i).put("sender", "self");
				} else {

					list.get(i).put("sender", "echo");
				}
			}

			ObjectMapper mapper = new ObjectMapper();
			String jsonfromList = "";
			try {
				jsonfromList = mapper.writeValueAsString(list);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jsonfromList = "{}";
			}
			session.sendMessage(new TextMessage(jsonfromList + ""));
		}
	}

	// 接受消息处理消息
	@Override
	public void handleMessage(WebSocketSession webSocketSession,
			WebSocketMessage<?> webSocketMessage) throws Exception {
		System.out.println("接受消息处理");

		System.out.println(webSocketMessage.getPayload());

		JSONArray array = new JSONArray();

		// 转化为json,需要try catch 判断json是否转化成功
		JSONObject message = JSON.parseObject(webSocketMessage.getPayload()
				+ "");
		String headurl = userService.getHeadUrl(message
				.getInteger("from_user_id"));
		// 存数据库
		Messages messages = new Messages();
		messages.setFromUserId(message.getInteger("from_user_id"));
		messages.setMessageDetail(message.getString("message_detail"));
		messages.setMessageNatureType(0);
		messages.setMessageStatus(1);
		messages.setMessageType(message.getString("message_type"));
		messages.setPsotTime(new Date());
		messages.setToUserId(message.getInteger("to_user_id"));

		if (messageService.insert(messages)) {
			message.put("sender", "echo");
			message.put("head_img_url", headurl);
			array.add(message);
			sendMessageToUser(message.getString("to_user_id"), new TextMessage(
					array + ""));

		}

		// sendMessageToUsers(new TextMessage(webSocketMessage.getPayload() +
		// ""));

	}

	@Override
	public void handleTransportError(WebSocketSession webSocketSession,
			Throwable throwable) throws Exception {
		if (webSocketSession.isOpen()) {
			webSocketSession.close();
		}
		logger.debug("链接出错，关闭链接......");
		users.remove(webSocketSession);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession webSocketSession,
			CloseStatus closeStatus) throws Exception {
		logger.debug("链接关闭......" + closeStatus.toString());
		users.remove(webSocketSession);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 给所有在线用户发送消息
	 *
	 * @param message
	 */
	public void sendMessageToUsers(TextMessage message) {
		System.out.println("~~发送");
		for (WebSocketSession user : users) {
			try {
				if (user.isOpen()) {
					user.sendMessage(message);
					System.out.println("alluser" + message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 给某个用户发送消息
	 *
	 * @param userName
	 * @param message
	 */
	public void sendMessageToUser(String userID, TextMessage message) {
		for (WebSocketSession user : users) {
			System.out.println(user);
			if (user.getAttributes().get("FROMUSERID").equals(userID)) {
				try {
					System.out.println(11111);
					user.sendMessage(message);
				/*	if (user.isOpen()) {
						user.sendMessage(message);

					}*/
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

	/**
	 * 给某个群发送消息
	 *
	 * @param groupID
	 * @param message
	 */
	public void sendMessageToGroup(String groupID, TextMessage message) {
		for (WebSocketSession user : users) {
			System.out.println(user);
			if (user.getAttributes().get("FROMUSERID").equals(groupID)) {
				try {
					if (user.isOpen()) {
						user.sendMessage(message);

					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

}
