package com.cn.ucoon.util;

public class sendThread extends Thread{
	private String openid;
	private String msgtype;
	private String servname;
	private String content; 
	public sendThread(String openid,String msgtype,String servname,String content){
		this.openid = openid;
		this.msgtype = msgtype;
		this.servname = servname;
		this.content = content;
	}
	  public void run(){
		int a=WeixinUtil.customSend(openid, msgtype,servname,content); 
		String statu = a==0?"success":"fail";
		System.out.println(statu+"==="+openid);
	  }
}
