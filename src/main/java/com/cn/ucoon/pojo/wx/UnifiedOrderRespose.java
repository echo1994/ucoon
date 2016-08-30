package com.cn.ucoon.pojo.wx;

/** 
 * 统一下单返回参数 
 * @author Y 
 * 
 */  
public class UnifiedOrderRespose {  
    private String return_code;             //返回状态码  
    private String return_msg;              //返回信息  
    private String appid;                   //公众账号ID  
    private String mch_id;                  //商户号  
    private String device_info;             //设备号  
    private String nonce_str;               //随机字符串  
    private String sign;                    //签名  
    private String result_code;             //业务结果  
    private String err_code;                //错误代码  
    private String err_code_des;            //错误代码描述  
    private String trade_type;              //交易类型  
    private String prepay_id;               //预支付交易会话标识  
    private String code_url;                //二维码链接  
	public synchronized String getReturn_code() {
		return return_code;
	}
	public synchronized void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public synchronized String getReturn_msg() {
		return return_msg;
	}
	public synchronized void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public synchronized String getAppid() {
		return appid;
	}
	public synchronized void setAppid(String appid) {
		this.appid = appid;
	}
	public synchronized String getMch_id() {
		return mch_id;
	}
	public synchronized void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public synchronized String getDevice_info() {
		return device_info;
	}
	public synchronized void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public synchronized String getNonce_str() {
		return nonce_str;
	}
	public synchronized void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public synchronized String getSign() {
		return sign;
	}
	public synchronized void setSign(String sign) {
		this.sign = sign;
	}
	public synchronized String getResult_code() {
		return result_code;
	}
	public synchronized void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public synchronized String getErr_code() {
		return err_code;
	}
	public synchronized void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public synchronized String getErr_code_des() {
		return err_code_des;
	}
	public synchronized void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public synchronized String getTrade_type() {
		return trade_type;
	}
	public synchronized void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public synchronized String getPrepay_id() {
		return prepay_id;
	}
	public synchronized void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}
	public synchronized String getCode_url() {
		return code_url;
	}
	public synchronized void setCode_url(String code_url) {
		this.code_url = code_url;
	}
	@Override
	public String toString() {
		return "UnifiedOrderRespose [return_code=" + return_code
				+ ", return_msg=" + return_msg + ", appid=" + appid
				+ ", mch_id=" + mch_id + ", device_info=" + device_info
				+ ", nonce_str=" + nonce_str + ", sign=" + sign
				+ ", result_code=" + result_code + ", err_code=" + err_code
				+ ", err_code_des=" + err_code_des + ", trade_type="
				+ trade_type + ", prepay_id=" + prepay_id + ", code_url="
				+ code_url + "]";
	}
    
	
    
}  