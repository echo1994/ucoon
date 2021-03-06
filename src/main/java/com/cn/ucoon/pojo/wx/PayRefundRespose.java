package com.cn.ucoon.pojo.wx;

public class PayRefundRespose {

	
	private String return_code; //返回状态码
	private String return_msg; //返回信息
	private String result_code; //业务结果
	private String err_code; //错误代码
	private String err_code_des; //错误代码描述
	private String appid; //公众账号ID
	private String mch_id; //商户号
	private String nonce_str; //随机字符串
	private String sign; //签名
	private String transaction_id; //微信订单号
	private String out_trade_no; //商户订单号
	private String out_refund_no; //商户退款单号
	private String refund_id; //微信退款单号
	private int refund_fee; //申请退款金额
	private int total_fee; //订单金额
	private int cash_fee; //现金支付金额
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getOut_refund_no() {
		return out_refund_no;
	}
	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}
	public String getRefund_id() {
		return refund_id;
	}
	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}
	public int getRefund_fee() {
		return refund_fee;
	}
	public void setRefund_fee(int refund_fee) {
		this.refund_fee = refund_fee;
	}
	public int getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}
	public int getCash_fee() {
		return cash_fee;
	}
	public void setCash_fee(int cash_fee) {
		this.cash_fee = cash_fee;
	}
	@Override
	public String toString() {
		return "PayRefundRespose [return_code=" + return_code + ", return_msg="
				+ return_msg + ", result_code=" + result_code + ", err_code="
				+ err_code + ", err_code_des=" + err_code_des + ", appid="
				+ appid + ", mch_id=" + mch_id + ", nonce_str=" + nonce_str
				+ ", sign=" + sign + ", transaction_id=" + transaction_id
				+ ", out_trade_no=" + out_trade_no + ", out_refund_no="
				+ out_refund_no + ", refund_id=" + refund_id + ", refund_fee="
				+ refund_fee + ", total_fee=" + total_fee + ", cash_fee="
				+ cash_fee + "]";
	}
	
	
	
	
	
}
