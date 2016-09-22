package com.cn.ucoon.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class SendUtil {

	private static String APIKEY = "926484a088a7d05b809116cbe2579eb9";
	
	private static Logger log = LoggerFactory.getLogger(SendUtil.class);
	
	/**
	 * 
		JSON返回示例 :
		{
		"returnstatus": "Success",---------- 返回状态值：成功返回Success 失败返回：Faild
		"message": "ok",---------- 返回信息
		"remainpoint": "0",---------- 运营商结算无意义，可不用解析
		"taskID": "123456",---------- 返回本次任务的序列ID
		"successCounts": "1"---------- 返回成功短信数     
		}
	 * @param mobile 发送号码：多个号码用半角逗号隔开，每个号码计费一条
	 * @param content
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static boolean send(String mobile,String content) throws UnsupportedEncodingException{
		String msg = URLEncoder.encode(content, "UTF-8");
		
		String httpUrl = "http://apis.baidu.com/kingtto_media/106sms/106sms";
		
		String httpArg = "mobile=" + mobile + "&content=" + msg + "&tag=2";
		
		JSONObject jsonResult = JSONObject.parseObject(request(httpUrl, httpArg));
		
		if(jsonResult != null){
			if (!jsonResult.getString("returnstatus").equals("Success")) {
				Integer result = jsonResult.getIntValue("taskID");
				log.error("发送短信失败 taskID:{}", result);
			}else{
				Integer result = jsonResult.getIntValue("taskID");
				log.error("发送短信成功 taskID:{}", result);
				return true;
			}
		}
		 
		
		return false;
		
		
	}
	
	
	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	        connection.setRequestProperty("apikey",  APIKEY);
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
}
