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
		<meta charset="UTF-8">
		<title>财富中心</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="format-detection" content="telephone=no" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="css/mui.min.css">
		<link rel="stylesheet" href="css/style.css">
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
			<div class="g-twobox clearfix">
				<div class="col fl">
					<h3>可用余额</h3>
					<p>${balance}</p>
				</div>
				<div class="col fr">
					<h3>可用空点</h3>
					<p>${credits}</p>
				</div>
			</div>
			<div class="g-twobox clearfix">
				<div class="col fl">
					<h3>累计收入</h3>
					<p>${plusBalance}</p>
				</div>
				<!-- 点击跳转到我发布的中未支付界面 -->
				<div class="col fr">
					<h3>待处理</h3>
					<p>${unPaidMission}</p>
				</div>
			</div>
			<div class="contrl-btn">
				<button class="recharge-btn">充值</button>
				<button class="withdraw-btn">提现</button>
			</div>
			
		</div>
	</body>
	<script src="js/mui.min.js"></script>
	<script>
		document.getElementsByClassName("recharge-btn")[0].addEventListener('tap', function() {
				var btnArray = ['取消', '确认'];
				mui.prompt('请输入你要充值的金额：', '请输入数字', '有空ucoon', btnArray, function(e) {
					if (e.index == 1) {
					
						var reg = /^\+?(?!0+(\.00?)?$)\d+(\.\d\d?)?$/;
						var prize = e.value;
						if (prize.match(reg) == null) {
							alert("请填写正确金额");
							return;
						}
						
						$.ajax({
							url : 'wealth/recharge/',
							data : {
								money : e.value,
							},
							async : false,
							type : 'post',
							success : function(data) {
								if (data.result_type == "success") {
								
								
									var config = eval("(" + data.msg + ")");
									
									wx.chooseWXPay({
								       timestamp: config.timestamp,
								       nonceStr: config.nonce,
								       package:config.packageName,
								       signType: config.signType, // 注意：新版支付接口使用 MD5 加密
								       paySign: config.signature,
								       success: function (res) {
									        // 支付成功后的回调函数
									        alert("充值成功");
									        history.go(0);
									   }
								    }) 
								}else{
									alert(data.msg);
								}
								
							}
						});
						
					} else {
					}
				})
		})
	
		document.getElementsByClassName("withdraw-btn")[0].addEventListener('tap', function() {
				var btnArray = ['取消', '确认'];
				mui.prompt('微信规定提现金额必须大于一元，提现后会有惊喜~', '请输入数字', '有空ucoon', btnArray, function(e) {
					if (e.index == 1) {
					
						var reg = /^\+?(?!0+(\.00?)?$)\d+(\.\d\d?)?$/;
						var prize = e.value;
						if (prize.match(reg) == null) {
							alert("请填写正确金额");
							return;
						}
					
						
						if(e.value < 1){
							alert("提现金额必须大于1元");
							return;
						}
						
						
						$.ajax({
							url : 'wealth/withdraw_cash/',
							data : {
								money : e.value,
							},
							async : false,
							type : 'post',
							success : function(data) {
								alert(data.msg);
								history.go(0);
							}
						});
						
					} else {
					}
				})
		})
		
		
		
	</script>
</html>
