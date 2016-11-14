package com.cn.ucoon.util;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.ucoon.service.MessageService;
import com.cn.ucoon.service.UserService;

/**
 * 普通类调用service
 * 不需要引入xml
 * @author mlk
 *
 */
@Service
public final class ServiceUtil {

    private static MessageService messageService;
    
    private static UserService userService;

    @Resource
    public void setMessageService(MessageService messageService) {
        ServiceUtil.messageService = messageService;
    }

    public static MessageService getMessageService() {
        return messageService;
    }

    @Resource
    public void setUserService(UserService userService) {
        ServiceUtil.userService = userService;
    }

    public static UserService getUserService() {
        return userService;
    }
}
