package com.cn.ucoon.websocket.handler;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Spring WebSocket API的核心接口
 * 消息处理中心
 * @author mlk
 *
 */
public class WebSocketHander implements WebSocketHandler {

	private static final Logger logger = Logger.getLogger(WebSocketHander.class);

    private static final ArrayList<WebSocketSession> users = new ArrayList<>();

    //初次链接成功执行
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    	System.out.println("链接成功");
    	users.add(session);
        String toUserID = (String) session.getAttributes().get("TOUSERID");
        String fromUserID = (String) session.getAttributes().get("FROMUSERID");
        if(fromUserID!= null){
            //查询未读消息
//            int count = 5;
//            session.sendMessage(new TextMessage(count + ""));
        }
    }

    //接受消息处理消息
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
    	System.out.println("接受消息处理");
    	
    	System.out.println(webSocketMessage.getPayload());
    	
    	//转化为json,需要try catch 判断json是否转化成功
    	JSONObject message = JSON.parseObject(webSocketMessage.getPayload() + "");
    	sendMessageToUser(message.getString("to_user_id"),new TextMessage(webSocketMessage.getPayload() + ""));
    	//sendMessageToUsers(new TextMessage(webSocketMessage.getPayload() + ""));

    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if(webSocketSession.isOpen()){
            webSocketSession.close();
        }
        logger.debug("链接出错，关闭链接......");
        users.remove(webSocketSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
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
