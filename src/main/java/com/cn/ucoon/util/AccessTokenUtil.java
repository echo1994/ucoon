package com.cn.ucoon.util;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.ucoon.pojo.wx.AccessToken;
import com.cn.ucoon.pojo.wx.JsApiTicket;

public class AccessTokenUtil {

	private static Logger log = LoggerFactory.getLogger(AccessTokenUtil.class);  
    
      
    /** 
    * @Description: //设置access_token 
     */  
    public static void initAndSetAccessToken() {  
        log.info("execute initAndSetAccessToken Start : {}", TimeUtil.timeStamp2Date(System.currentTimeMillis()/1000 + "", null));  
        
        String appid = WeixinUtil.appid;  
        String appsecret = WeixinUtil.appsecret;  
        if(!appid.isEmpty() && !appsecret.isEmpty()) {  
            AccessToken accessToken = WeixinUtil.getAccessToken(appid,appsecret);  
            if(null != accessToken) {  
                /** 
                 * cache access_token 
                 */  
                ServletContext sc = ServletContextUtil.get();  
                sc.removeAttribute("access_token");  
                sc.setAttribute("access_token", accessToken);  
              //这里可以向数据库中写access_token
                /** 
                 * cache jsapi_ticket 
                 */  
                JsApiTicket jsApiTicket = WeixinUtil.getJsApiTicket(accessToken.getToken());  
                if(null != jsApiTicket) {
                    sc.removeAttribute("jsapi_ticket");
                    sc.setAttribute("jsapi_ticket", jsApiTicket);
                }
                
            }  
        } else {  
            log.info("execute initAndSetAccessToken appid,appsecret 为空.{}");  
        }  
        log.info("execute initAndSetAccessToken End   : {}", TimeUtil.timeStamp2Date(System.currentTimeMillis()/1000 + "", null));  
    }  


}
