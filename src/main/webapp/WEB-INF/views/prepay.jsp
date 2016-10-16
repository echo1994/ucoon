<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>"> 

<title></title>
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link href="css/mui.min.css" rel="stylesheet" />

</head>
<body contextmenu="return false;">
<button id="pay">支付</button>
<input type="hidden" id="prepayid" value="${prepayid}" />
</body>
<script src="js/mui.min.js"></script>
<script src="js/arttmpl.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="http://lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.min.js"></script>
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
			pay: doc.querySelector('#pay'),
			prepayid: doc.querySelector('#prepayid')
		};
		
		ui.pay.addEventListener('tap', function(event) {
			
			var prepayid = ui.prepayid.value;
			$.ajax({
			    url: "pay/getPay/" + prepayid,
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
				       paySign: config.signature
				    }) 
			    },
			  	dataType: "json",
			  	async:true
			}); 
		  

		})
		
	}(mui, document));
</script>


</html>
