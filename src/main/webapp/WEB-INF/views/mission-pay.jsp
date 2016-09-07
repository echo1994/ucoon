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
			    url: "/ucoon/wx/sign?url="+URL,
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
			</div>
			<ul class="pay-way">
				<li><i class="mui-icon iconfont icon-me fl"></i><span>余额支付</span><i class="circle circle-active fr"></i></li>
				<li><i class="mui-icon iconfont icon-wechat-pay fl"></i><span>微信支付</span><i class="circle fr"></i></li>
				<li><i class="mui-icon iconfont icon-alipay fl"></i><span>支付宝</span><i class="circle fr"></i></li>
				<li><i class="mui-icon iconfont icon-card fl"></i><span>银行卡</span><i class="circle fr"></i></li>
			</ul>
			<button class="pay-btn" id="pay">确认支付<span><fmt:formatNumber value="${sessionScope.mission.missionPrice }" type="currency"/></span></button>
		
			
		</div>
		<script src="js/mui.min.js"></script>
		<script type="text/javascript" charset="utf-8">
			(function($, doc) {
				wx.ready(function () {
					//页面加载时调用
					wx.checkJsApi({
					    jsApiList: [
					      'chooseWXPay'
					    ],
					    success: function (res) {
					      alert(JSON.stringify(res));
					    }
					 });
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
					pay: doc.querySelector('#pay')
				};
				
				ui.pay.addEventListener('tap', function(event) {
					
					$.ajax({
					    url: "/ucoon/pay/getPay/",
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
							        window.location.href="/ucoon/mysend";
							   }
						    }) 
					    },
					  	dataType: "json",
					  	async:true
					}); 
				  
		
				})
				
			}(mui, document));
		</script>
	</body>

</html>