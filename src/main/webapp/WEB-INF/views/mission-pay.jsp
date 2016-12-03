<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8">
		<title>任务发布-支付</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="format-detection" content="telephone=no" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="css/mui.min.css">
		<link rel="stylesheet" href="css/style.css">
		<link href="css/iconfont.css" rel="stylesheet" />
		
		<style>
			body {
				background-color: #efeff4;
			}
		</style>
		<script src="js/jquery-2.1.4.min.js"></script>
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript" charset="utf-8">
			var URL = window.location.href.split('#')[0]; //获取当前页面的url
			URL = encodeURIComponent(URL);
			var appid,nonceStr,signature,timestamp;
			//ajax同步更新全局变量，异步无法更新
			$.ajax({
			    url: "wx/sign?url="+URL,
			    success: function(result){
			    	appid = result.appId;
			    	timestamp=result.timestamp;
			    	nonceStr=result.nonceStr;
			    	signature=result.signature;
			    },
			  	dataType: "json",
			  	async:false
			});
			
			wx.config({
			    debug: false,
			    appId: appid,
			    timestamp: timestamp,
			    nonceStr: nonceStr,
			    signature: signature,
			    jsApiList: [
			      'checkJsApi',
			      'chooseWXPay'
			    ]
			});
			
		</script>
	</head>

	<body>
		
		
		<div class="mui-content">
			<div class="order-mes">
				<p class="price"><fmt:formatNumber value="${sessionScope.mission.missionPrice }" type="currency"/></p>
				<p class="task-name">${sessionScope.mission.missionTitle }</p>
				<p class="order-id">订单编号：${sessionScope.orders.missionOrderNum }</p>
				<p>请在15分钟内完成支付</p>
			</div>
			<ul class="pay-way">
				<li class="payment"><i class="mui-icon iconfont icon-me fl"></i><span>余额支付</span><i class="circle fr me"></i></li>
				<li class="payment"><i class="mui-icon iconfont icon-wechat-pay fl"></i><span>微信支付</span><i class="circle fr wechat circle-active"></i></li>
				<li class="payment"><i class="mui-icon iconfont icon-alipay fl"></i><span>支付宝</span><i class="circle fr alipay"></i></li>
				<li class="payment"><i class="mui-icon iconfont icon-card fl"></i><span>银行卡</span><i class="circle fr card"></i></li>
			</ul>
			<button class="pay-btn" id="pay">确认支付<span><fmt:formatNumber value="${sessionScope.mission.missionPrice }" type="currency"/></span></button>
		
			
		</div>
		<script src="js/mui.min.js"></script>
		<script type="text/javascript" charset="utf-8">
			(function($, doc) {
				wx.ready(function () {
					//页面加载时调用
					/* wx.checkJsApi({
					    jsApiList: [
					      'chooseWXPay'
					    ],
					    success: function (res) {
					      alert(JSON.stringify(res));
					    }
					 }); */
				});		
			
				$.init({
					gestureConfig: {
						tap: true, //默认为true
						doubletap: true, //默认为false
						longtap: true, //默认为false
						swipe: true, //默认为true
						drag: true, //默认为true
						hold: true, //默认为false，不监听
						release: true //默认为false，不监听
					}
				});
			
				var ui = {
					body: doc.querySelector('body'),
					pay: doc.querySelector('#pay'),
				};
				var payWay = "wechat";
				
				$(".payment").each(function(index, value) {
					this.addEventListener('tap', function(event) {
						var li = this;
						var classList = li.lastChild.classList;
						if (!classList.contains('circle-active')) {
							var active = li.parentNode.querySelector('.circle-active');
							active.classList.remove('circle-active');
							classList.add('circle-active');
							
						}
						if(classList.contains('me')){
							payWay = "me";
						} else if(classList.contains('wechat')){
							payWay = "wechat";
						} else if(classList.contains('alipay')){
							payWay = "alipay";
						} else{
							payWay = "card";
						}
					});
				
				})
				
				
				ui.pay.addEventListener('tap', function(event) {
					var payUrl="";
					var url = window.location.href; //获取当前页面的url
					var args = url.split("?");
					var retval = "";
					/*参数为空*/
					if(args[0] == url) {
						payUrl = "pay/getPay/";
					}else{
						var str = args[1];
						var arg1 = str.split("=");
						var id = arg1[1];
						payUrl = "pay/getPay/" + id;
					}
					
				
					if(payWay == "wechat"){
						$.ajax({
						    url: payUrl,
						    success: function(result){
						    	if(result.result_type == "error"){
						    		alert(result.msg);
						    		return;
						    	}
						    	
						    	var config = eval("(" + result.msg + ")");
						    	
					    	    wx.chooseWXPay({
							       timestamp: config.timestamp,
							       nonceStr: config.nonce,
							       package:config.packageName,
							       signType: config.signType, // 注意：新版支付接口使用 MD5 加密
							       paySign: config.signature,
							       success: function (res) {
								        // 支付成功后的回调函数
								        alert("支付成功");
								        window.location.href="mysend";
								   }
							    }) 
						    },
						  	dataType: "json",
						  	async:true
						}); 
					}else{
						alert("暂时只支持微信支付");
					}
					
				  
		
				})
				
			}(mui, document));
		</script>
	</body>

</html>