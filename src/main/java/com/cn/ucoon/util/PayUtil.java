package com.cn.ucoon.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.ucoon.pojo.wx.JsAPIConfig;
import com.cn.ucoon.pojo.wx.PayCallback;
import com.cn.ucoon.pojo.wx.PayRefund;
import com.cn.ucoon.pojo.wx.PayRefundRespose;
import com.cn.ucoon.pojo.wx.SendRedPack;
import com.cn.ucoon.pojo.wx.SendRedPackRespose;
import com.cn.ucoon.pojo.wx.UnifiedOrderRequestExt;
import com.cn.ucoon.pojo.wx.UnifiedOrderRespose;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class PayUtil {
	
	private static Logger log = LoggerFactory.getLogger(PayUtil.class);
	
	//D:/certs/apiclient_cert.p12
	///data/certs/apiclient_cert.p12
	private static String file_url = "/data/certs/apiclient_cert.p12";
	
	// 平台抽取的费用
	public final static float rate = 0.08f;
	
	// 密钥
	private final static String Key = "350181199404211838malingkaiecho9";

	// 商户id
	public final static String MCH_ID = "1372543502";

	//统一下单url
	private final static String ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	//普通红包url
	private final static String ORDERY_RED_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
	
	//退款url
	private final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	
	/**
     * 现金红包
     * @Title: bindDevice 
     * @return String   
     * @throws
     */
    public static SendRedPackRespose sendRedPack(SendRedPack redPack) throws Exception{
        String sign = createSendRedPackOrderSign(redPack);
        redPack.setSign(sign);

     	XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder(
     				"_-", "_"))); 
     						
     	xStream.alias("xml", redPack.getClass());// 根元素名需要是xml
        
        
        String xml = xStream.toXML(redPack);
        String response = ssl(ORDERY_RED_URL,xml);
        
        xStream.alias("xml", SendRedPackRespose.class);
        SendRedPackRespose respose = (SendRedPackRespose) xStream.fromXML(response);;
        
    
		System.out.println(respose);
		return respose;
		
    }
    
    /**
     * 申请退款
     * @Title: bindDevice 
     * @return String   
     * @throws
     */
    public static PayRefundRespose payRefund(PayRefund payRefund) throws Exception{
        String sign = createPayRefundOrderSign(payRefund);
        payRefund.setSign(sign);

     	XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder(
     				"_-", "_"))); 
     						
     	xStream.alias("xml", payRefund.getClass());// 根元素名需要是xml
        
        
        String xml = xStream.toXML(payRefund);
        String response = ssl(REFUND_URL,xml);
        
        xStream.alias("xml", PayRefundRespose.class);
        PayRefundRespose respose = (PayRefundRespose) xStream.fromXML(response);;
        
    
		System.out.println(respose);
		return respose;
		
    }
	
	/**
	 * 生成订单
	 * 
	 * @param orderId
	 *            订单号
	 * @param ip
	 *            用户IP
	 * @param body
	 *            商品描述 例如：商家名称-销售商品类目
	 * @param fee
	 *            金额需要扩大100倍:1代表支付时是0.01
	 * @return
	 */
	public static String createOrderInfo(String orderId, String ip,
			String body, String fee, String notify_url, String trade_type,String openid) {
		// 生成订单对象
		UnifiedOrderRequestExt unifiedOrderRequest = new UnifiedOrderRequestExt();
		unifiedOrderRequest.setAppid(WeixinUtil.appid);// 公众账号ID
		unifiedOrderRequest.setMch_id(MCH_ID);// 商户号
		unifiedOrderRequest.setNonce_str(makeUUID());// 随机字符串
		unifiedOrderRequest.setBody(body);// 商品描述
		unifiedOrderRequest.setOut_trade_no(orderId);// 商户订单号
		unifiedOrderRequest.setTotal_fee(fee); // 金额需要扩大100倍:1代表支付时是0.01
		unifiedOrderRequest.setSpbill_create_ip(ip);// 用户IP
		unifiedOrderRequest.setNotify_url(notify_url);// 通知地址
		unifiedOrderRequest.setTrade_type(trade_type);// JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
		unifiedOrderRequest.setOpenid(openid);
		unifiedOrderRequest.setSign(createSign(unifiedOrderRequest));// 签名
		
		
		// 将订单对象转为xml格式
		XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder(
				"_-", "_"))); 
						
		xStream.alias("xml", UnifiedOrderRequestExt.class);// 根元素名需要是xml
		return xStream.toXML(unifiedOrderRequest);
	}

	/**
	 * 调统一下单API
	 * 
	 * @param orderInfo
	 * @return UnifiedOrderRespose
	 */
	public static UnifiedOrderRespose httpOrder(String orderInfo) {
		
		UnifiedOrderRespose unifiedOrderRespose = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(ORDER_URL)
					.openConnection();
			// 加入数据
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			BufferedOutputStream buffOutStr = new BufferedOutputStream(
					conn.getOutputStream());
			buffOutStr.write(orderInfo.getBytes());
			buffOutStr.flush();
			buffOutStr.close();

			// 获取输入流
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			XStream xStream = new XStream(new XppDriver(
					new XmlFriendlyNameCoder("_-", "_")));// 说明3(见文末)
			// 将请求返回的内容通过xStream转换为UnifiedOrderRespose对象
			xStream.alias("xml", UnifiedOrderRespose.class);
			unifiedOrderRespose = (UnifiedOrderRespose) xStream.fromXML(sb
					.toString());
			System.out.println(unifiedOrderRespose);
			log.info("支付结果：" + unifiedOrderRespose + "\n");
			// 根据微信文档return_code 和result_code都为SUCCESS的时候才会返回code_url
			if (null != unifiedOrderRespose
					&& "SUCCESS".equals(unifiedOrderRespose.getReturn_code())
					&& "SUCCESS".equals(unifiedOrderRespose.getResult_code())) {
				return unifiedOrderRespose;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 红包配置
	 * @param redPack
	 * @return
	 */
	public static String createSendRedPackOrderSign(SendRedPack redPack){
        StringBuffer sign = new StringBuffer();
        sign.append("act_name=").append(redPack.getAct_name());
        sign.append("&client_ip=").append(redPack.getClient_ip());
        sign.append("&mch_billno=").append(redPack.getMch_billno());
        sign.append("&mch_id=").append(redPack.getMch_id());
        sign.append("&nonce_str=").append(redPack.getNonce_str());
        sign.append("&re_openid=").append(redPack.getRe_openid());
        sign.append("&remark=").append(redPack.getRemark());
        sign.append("&send_name=").append(redPack.getSend_name());
        sign.append("&total_amount=").append(redPack.getTotal_amount());
        sign.append("&total_num=").append(redPack.getTotal_num());
        sign.append("&wishing=").append(redPack.getWishing());
        sign.append("&wxappid=").append(redPack.getWxappid());
        sign.append("&key=").append(Key);
        return MD5Util.MD5Encode(sign.toString(),"UTF-8").toUpperCase();
    }
	
	/**
	 * 退款配置
	 * @param redPack
	 * @return
	 */
	public static String createPayRefundOrderSign(PayRefund payRefund){
		SortedMap<String, String> refundParams = new TreeMap<String, String>();
		refundParams.put("appid", payRefund.getAppid());
		refundParams.put("mch_id", payRefund.getMch_id());
		refundParams.put("nonce_str", payRefund.getNonce_str());
		refundParams.put("out_trade_no", payRefund.getOut_trade_no());
		refundParams.put("out_refund_no", payRefund.getOut_refund_no());
		refundParams.put("total_fee", payRefund.getTotal_fee() + "");
		refundParams.put("refund_fee", payRefund.getRefund_fee() + "");
		refundParams.put("op_user_id", payRefund.getMch_id());
		
        
        StringBuffer sb = new StringBuffer();
		Set es = refundParams.entrySet();// 字典序
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			// 为空不参与签名、参数名区分大小写
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
        
		sb.append("key=" + Key);
		String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();// MD5加密
        return sign;
    }
	
	  /**
     * 获取支付配置
     * @Title: createPayConfig
     * @Description: TODO
     * @param @param preayId 统一下单prepay_id
     * @param @return
     * @param @throws Exception    
     * @return JsAPIConfig    
     * @throws
     */
    public static JsAPIConfig createPayConfig(String prepayId) throws Exception {
        JsAPIConfig config = new JsAPIConfig();

        String nonce = makeUUID();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String packageName = "prepay_id="+prepayId;
        StringBuffer sign = new StringBuffer();
        sign.append("appId=").append(WeixinUtil.appid);
        sign.append("&nonceStr=").append(nonce);
        sign.append("&package=").append(packageName);
        sign.append("&signType=").append(config.getSignType());
        sign.append("&timeStamp=").append(timestamp);
        sign.append("&key=").append(Key);
        String signature = MD5Util.MD5Encode(sign.toString(),"UTF-8").toUpperCase();

        config.setAppId(WeixinUtil.appid);
        config.setNonce(nonce);
        config.setTimestamp(timestamp);
        config.setPackageName(packageName);
        config.setSignature(signature);

        return config;
    }

	/**
	 * 随机字符串 创建UUID
	 * 
	 * @return
	 */
	public static String makeUUID() {
		Date date = new Date();
		StringBuffer s = new StringBuffer(new SimpleDateFormat(
				"yyyyMMddHHmmssSSS").format(date));
		return s.append((new Random().nextInt(900) + 100)).toString();
	}

	/**
	 * 生成签名
	 * 
	 * @param appid_value
	 * @param mch_id_value
	 * @param productId
	 * @param nonce_str_value
	 * @param trade_type
	 * @param notify_url
	 * @param spbill_create_ip
	 * @param total_fee
	 * @param out_trade_no
	 * @return
	 */
	private static String createSign(UnifiedOrderRequestExt unifiedOrderRequest) {
		// 根据规则创建可排序的map集合
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", unifiedOrderRequest.getAppid());
		packageParams.put("body", unifiedOrderRequest.getBody());
		packageParams.put("mch_id", unifiedOrderRequest.getMch_id());
		packageParams.put("nonce_str", unifiedOrderRequest.getNonce_str());
		packageParams.put("notify_url", unifiedOrderRequest.getNotify_url());
		packageParams
				.put("out_trade_no", unifiedOrderRequest.getOut_trade_no());
		packageParams.put("spbill_create_ip",
				unifiedOrderRequest.getSpbill_create_ip());
		packageParams.put("trade_type", unifiedOrderRequest.getTrade_type());
		packageParams.put("total_fee", unifiedOrderRequest.getTotal_fee());
		packageParams.put("openid", unifiedOrderRequest.getOpenid());

		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();// 字典序
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			// 为空不参与签名、参数名区分大小写
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		// 第二步拼接key，key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
		sb.append("key=" + Key);
		String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();// MD5加密
		return sign;
	}
	
	 /**
     * 生成收到支付结果的确认信息
     * @Title: getPayCallback
     * @Description: TODO
     * @param @return    
     * @return String    
     * @throws
     */
    public static String getPayCallback(){
        PayCallback callback = new PayCallback();
        
        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder(
				"_-", "_"))); 
						
		xStream.alias("xml", callback.getClass());// 根元素名需要是xml
		return xStream.toXML(callback);
       
    }
    
    
    public static String getOrdersNum(Integer userId, Integer missionId) {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String nums = dateFormat.format(date) + missionId
				+ (int) (Math.random() * 10) + userId
				+ (int) (Math.random() * 10);

		return nums;
	}
    
    
    private static String ssl(String url,String data){
        StringBuffer message = new StringBuffer();
        try {
            KeyStore keyStore  = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File(file_url));
            keyStore.load(instream, MCH_ID.toCharArray());
         // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, MCH_ID.toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            HttpPost httpost = new HttpPost(url);

            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(data, "UTF-8"));
            System.out.println("executing request" + httpost.getRequestLine());

            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        message.append(text);
                    }

                }
                EntityUtils.consume(entity);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } 

        return message.toString();
    }
}
