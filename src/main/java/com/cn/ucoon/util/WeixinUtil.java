package com.cn.ucoon.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.cn.ucoon.pojo.wx.AccessToken;
import com.cn.ucoon.pojo.wx.JsApiTicket;
import com.cn.ucoon.pojo.wx.Menu;

/**
 * 内部开发通用接口工具类
 */
public class WeixinUtil {
	
	// 第三方用户唯一凭证
	public static String appid = "wx7687c14ac73b051b";
	// 第三方用户唯一凭证密钥
	public static String appsecret = "58761104834ed5a2a617443a5b838568";
	
	
	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	//获取网页access_token
	public final static String auth_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code"; 
	
	//拉取用户信息
	public final static String get_userinfo_url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	//获取网页jsapi_ticket
	public final static String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	
	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

	
	/**
	 * 
	 * 获取公众号全部用户openid
	 * 
	 * @param nextOpenID
	 * @return
	 */
	public static ArrayList<String> getOpenId(String nextOpenID) {
		String Url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token="+ TokenThread.accessToken.getToken();
		
		
		ArrayList<String> list = new ArrayList<String>();
		CloseableHttpClient client = HttpClients.createDefault();
		
		if(!nextOpenID.equals("")){
			Url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token="+ TokenThread.accessToken.getToken()+"&next_openid=" + nextOpenID;
//			StringEntity myEntity = new StringEntity(nextOpenID,
//			ContentType.APPLICATION_FORM_URLENCODED);// 构造请求数据
//			post.setEntity(myEntity);// 设置请求体
		}
		HttpPost post = new HttpPost(Url);
		String responseContent = null; // 响应内容
		CloseableHttpResponse response = null;
		try {
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				responseContent = EntityUtils.toString(entity, "UTF-8");
			}
			JSONObject result = JSON.parseObject(responseContent);
			String nextopenid = (String) result.get("next_openid");//下一个openid，没有则为空
			int total = (Integer) result.get("total");//总用户数
			JSONObject data = null;
			if(!nextopenid.equals("")){
				data = JSONObject.parseObject(result.getString("data"));
				JSONArray openid = JSON.parseArray(data.getString("openid"));
				 //必须用JSONArray来转，用JSONObject会出现以下错误信息：  
	            // A JSONObject text must begin with '{' at character 1 of ['apple','orange']  
				for(int i = 0;i < openid.size();i++){
					list.add((String) openid.get(i));
				}
			}
			
			
			if(!nextopenid.equals("")){
				ArrayList<String> nextlist = getOpenId(nextopenid);
				list.addAll(nextlist);
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null)
					response.close();

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (client != null)
						client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}

		return jsonObject;
	}

	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = access_token_url.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}",
						jsonObject.getIntValue("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	/**
	 * 获取网页access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return json
	 */
	public static JSONObject getAuthAccessToken(String appid, String appsecret,String code) {

		String requestUrl = auth_access_token_url.replace("APPID", appid).replace(
				"SECRET", appsecret).replace("CODE", code);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null == jsonObject) {
			System.out.println("网页请求失败");
			log.error("获取token失败 errcode:{} errmsg:{}",
						jsonObject.getIntValue("errcode"),
						jsonObject.getString("errmsg"));
		}
		return jsonObject;
	}
	
	/**
	 * 获取网页jsapi_ticket
	 * 
	 * @param appsecret
	 *            密钥
	 * @return json
	 */
	public static JsApiTicket getJsApiTicket(String accessToken) {

		JsApiTicket jsApiTicket = null; 
		String requestUrl = jsapi_ticket_url.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功  
        if (null != jsonObject) {  
            try {  
                jsApiTicket = new JsApiTicket();  
                jsApiTicket.setTicket(jsonObject.getString("ticket"));  
                jsApiTicket.setExpiresIn(jsonObject.getIntValue("expires_in"));  
            } catch (JSONException e) {  
                accessToken = null;  
                // 获取jsApiTicket失败  
                log.error("获取jsApiTicket失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));  
            }  
        }  
		return jsApiTicket;
	}
	
	

	
	
	public static JSONObject getUserInfo(String access_token, String open_id) {
		//lang默认简体即： zh_CN
		
		String requestUrl = get_userinfo_url.replace("ACCESS_TOKEN", access_token).replace(
				"OPENID", open_id);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null == jsonObject) {
			System.out.println("网页请求失败");
			log.error("获取用户信息失败 errcode:{} errmsg:{}",
						jsonObject.getIntValue("errcode"),
						jsonObject.getString("errmsg"));
		}
		return jsonObject;
	}
	
	/**
	 * 创建菜单
	 * 
	 * @param menu 菜单实例
	 * @param accessToken 有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(Menu menu, String accessToken) {
		int result = 0;

		// 拼装创建菜单的url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSON.toJSONString(menu);
		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getIntValue("errcode")) {
				result = jsonObject.getIntValue("errcode");
				log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
			}
		}

		return result;
	}
	
	//获取缓存的token
	public static AccessToken  getAccessToken() {  
        return (AccessToken) ServletContextUtil.get().getAttribute("access_token");  
    }  
	
	//获取缓存的ticket
	public static JsApiTicket  getJsApiTicket() {  
        return (JsApiTicket) ServletContextUtil.get().getAttribute("jsapi_ticket");  
    }  
	
	public static Map<String, String> getJSSDK(String url) {  
        JsApiTicket jsApiTicket = getJsApiTicket();
        if(null != jsApiTicket) {  
            String ticket = jsApiTicket.getTicket();  
            Map<String, String> ret = Sign.sign(ticket, url);  
            ret.put("appId", appid);  
            return ret;  
        }  
        return null;  
    };  
}
