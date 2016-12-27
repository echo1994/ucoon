package com.cn.ucoon.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.swing.event.UndoableEditListener;

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
import com.alibaba.fastjson.JSONObject;

/**
 * 包含根据经纬度计算距离
 * 百度地址api 根据地址返回经纬度
 * @author mlk
 *
 */
public class MapUtil {

	private static Logger log = LoggerFactory.getLogger(MapUtil.class);
	
	private static double EARTH_RADIUS = 6378.137;
	
	
	//Geocoding APIWeb服务API
	private static String geocoding_api_url = "http://api.map.baidu.com/geocoder/v2/?address=ADDRESS&output=json&ak=AK"; 
	
	//百度api密钥，服务器端
	private static String AK = "ABSzRV8UGBlHlEfHGQtdj1qDdzTIjda0";

	/*
	  json返回格式
	  {
	  status: 0,
	  result: {
	    location: {
	      lng: 116.30814954222,
	      lat: 40.056885091681
	    },
	    precise: 1,
	    confidence: 80,
	    level: "商务大厦"
	  }
	  特别说明： 若解析status字段为OK，但结果内容为空，原因分析及可尝试方法：
	   1、地址库里无此数据，本次结果为空； 
	   2、加入city字段重新解析； 
	   3、将过于详细或简单的地址更改至省市区县街道重新解析。
	}*/
	/**
	 * 
	 * @param address
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static JSONObject getLatAndLngbyPlaceName(String address) throws UnsupportedEncodingException {
		address = URLEncoder.encode(address, "UTF-8");
		

		String requestUrl = geocoding_api_url.replace("ADDRESS", address).replace(
				"AK", AK);
		
		CloseableHttpClient client = HttpClients.createDefault();
		JSONObject jsonObject = null;
		HttpPost post = new HttpPost(requestUrl);
		String responseContent = null; // 响应内容
		CloseableHttpResponse response = null;
		try {
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				responseContent = EntityUtils.toString(entity, "UTF-8");
			}
			jsonObject = JSON.parseObject(responseContent);
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
		
		
		// 如果请求失败
		if (null == jsonObject) {
			log.error("获取经纬度失败 errcode:{} errmsg:{}",
						jsonObject.getIntValue("status"),
						jsonObject.getString("result"));
		}
		
		return jsonObject;
	}
	
	
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 根据两个位置的经纬度，来计算两地的距离（单位为M） 参数为String类型
	 * 
	 * @param lat1
	 *            用户经度
	 * @param lng1
	 *            用户纬度
	 * @param lat2
	 *            商家经度
	 * @param lng2
	 *            商家纬度
	 * @return
	 */
	public static String getDistance(String lat1Str, String lng1Str,
			String lat2Str, String lng2Str) {
		Double lat1 = Double.parseDouble(lat1Str);
		Double lng1 = Double.parseDouble(lng1Str);
		Double lat2 = Double.parseDouble(lat2Str);
		Double lng2 = Double.parseDouble(lng2Str);

		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double difference = radLat1 - radLat2;
		double mdifference = rad(lng1) - rad(lng2);
		double distance = 2 * Math.asin(Math.sqrt(Math.pow(
				Math.sin(difference / 2), 2)
				+ Math.cos(radLat1)
				* Math.cos(radLat2)
				* Math.pow(Math.sin(mdifference / 2), 2)));
		distance = distance * EARTH_RADIUS;
		distance = Math.round(distance * 1000);
		String distanceStr = distance + "";
		distanceStr = distanceStr.substring(0, distanceStr.indexOf("."));

		return getStandardDistance(distanceStr);
	}
	
	public static String getStandardDistance(String distance){
		Double distan = Double.valueOf(distance);
		
		if(distan < 2000){
			return distance + "米";
		}else{
			int i = (int) Math.round(distan/1000);
			return i + "公里";
		}
		
	}

	/**
	 * 获取当前用户一定距离以内的经纬度值 
	 * 单位米 return 
	 * minLat 最小经度
	 * minLng 最小纬度
	 * maxLat 
	 * 最大经度 maxLng
	 * 最大纬度 minLat
	 */
	public static Map<String, String> getAround(String latStr, String lngStr, String raidus) {
		Map<String, String> map = new HashMap<String, String>();

		Double latitude = Double.parseDouble(latStr);// 传值给经度
		Double longitude = Double.parseDouble(lngStr);// 传值给纬度

		Double degree = (24901 * 1609) / 360.0; // 获取每度
		double raidusMile = Double.parseDouble(raidus);

		Double mpdLng = Double.parseDouble((degree
				* Math.cos(latitude * (Math.PI / 180)) + "").replace("-", ""));
		Double dpmLng = 1 / mpdLng;
		Double radiusLng = dpmLng * raidusMile;
		// 获取最小经度
		Double minLng = longitude - radiusLng;
		// 获取最大经度
		Double maxLng = longitude + radiusLng;

		Double dpmLat = 1 / degree;
		Double radiusLat = dpmLat * raidusMile;
		// 获取最小纬度
		Double minLat = latitude - radiusLat;
		// 获取最大纬度
		Double maxLat = latitude + radiusLat;

		map.put("minLat", minLat + "");
		map.put("maxLat", maxLat + "");
		map.put("minLng", minLng + "");
		map.put("maxLng", maxLng + "");

		return map;
	}
	
	
	public static JSONArray getPois(String keyword,Integer limit) throws UnsupportedEncodingException{
		keyword = URLEncoder.encode(keyword, "UTF-8");
		String requestUrl = "https://mainsite-restapi.ele.me/v2/pois?extras[]=count&geohash=ws7gr0q5kr0z&keyword=" + keyword + "&limit=" + limit + "&type=nearby";
		JSONArray jsonArray = null;
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
			httpUrlConn.setRequestMethod("GET");

			if ("GET".equalsIgnoreCase("GET")) {
				httpUrlConn.connect();
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
			jsonArray = JSONArray.parseArray(buffer.toString());
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonArray;
	}
	
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		// 济南国际会展中心经纬度：117.11811 36.68484
		// 趵突泉：117.00999000000002 36.66123
//		System.out.println(getDistance("24.556794","118.115669","24.569119","118.101997"));
//		Double i = (double) 11100;
//		System.out.println(Math.round(i/1000));
		//System.out.println(getAround("24.556794","118.115669", "20000"));
		// 117.01028712333508(Double), 117.22593287666493(Double),
		// 36.44829619896034(Double), 36.92138380103966(Double)
		
//		System.out.println(getLatAndLngbyPlaceName("厦门市集美区集美学村"));
		System.out.println(getPois("集美大学六社区", 10));
	}

}
