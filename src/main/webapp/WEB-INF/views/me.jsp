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
		<title>个人中心</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<script src="js/mui.min.js"></script>
		<link href="css/mui.min.css" rel="stylesheet" />
		<link href="css/style.css" rel="stylesheet" />
		<link href="css/iconfont.css" rel="stylesheet" />
		
		
		<script src="js/jquery-2.1.4.min.js"></script>
	</head>
	<style>
		.basic-mes{
			margin: 0;
		padding: 15px 0;
		background:#fff;
		}
		.basic-mes img{
			width: 60px;
			height: 60px;
		}
		.mes-edit .edit-col{
			background: #fff;
			margin-top: 1px;
			padding: 10px;
			color: #999;
		}
		.lab{
			color: #555;
		}
		.mui-popup-button{
			color: #C3D94F;
		}
	</style>
	
	<body>
		<div class="mui-content">
			<div class="basic-mes">
			<!--头像-->
				<img src="${u.headImgUrl }">
				<div class="ucoon-user">${u.nickName }
				<c:choose>
					 <c:when test="${u.sex == 2}">
					 <i class="mui-icon iconfont icon-woman"></i>
					 </c:when>
					 <c:otherwise>
						<i class="mui-icon iconfont icon-man"></i>
					 </c:otherwise>
				</c:choose>
				</div>
			</div>
			<div class="mes-edit clearfix">
				<div class="edit-col">
					<span class="lab">昵称:</span><span>${u.nickName }</span><i id="edit-name" class="mui-icon mui-icon-compose fr"></i>
				</div>
				<div class="edit-col">
					<span class="lab">电话:</span><span>${u.phone }</span>
				</div>
				<div class="edit-col">
					<span class="lab">微信号:</span><span>${u.weixinId }</span><i id="edit-wx" class="mui-icon mui-icon-compose fr"></i>
				</div>
				<div class="edit-col">
					<span class="lab">个性签名:</span><span>${u.signature }</span> <i id="edit-say" class="mui-icon mui-icon-compose fr"></i>
				</div>
				<div class="edit-col">
					<span class="lab">默认地址:</span><span>${u.country }${u.province }${u.city }</span>
					
				</div>
				
			</div>

		</div>
		
		
	</body>
	<script>
		document.getElementById("edit-say").addEventListener('tap', function(e) {
			var btnArray = ['取消', '保存'];
			mui.prompt('输入您的个性签名', '有空个性签名', '有空ucoon', btnArray, function(e) {
				if (e.index == 1) {
					$.ajax({
						url : 'user/signature',
						data : {content:e.value},
						async : false,
						type : 'post',
						dataType : 'json',
						success : function(data) {
							if(data.result == 'error'){
								alert(data.msg);
								return;
							}
							window.history.go(0);
						}
					})
				} else {
				
				}
			})
		});
		
		
		document.getElementById("edit-name").addEventListener('tap', function(e) {
			var btnArray = ['取消', '保存'];
			mui.prompt('输入您的微信号', '微信号', '有空ucoon', btnArray, function(e) {
				if (e.index == 1) {
					$.ajax({
						url : 'user/name',
						data : {content:e.value},
						async : false,
						type : 'post',
						dataType : 'json',
						success : function(data) {
							if(data.result == 'error'){
								alert(data.msg);
								return;
							}
							window.history.go(0);
						}
					})
				} else {
				
				}
			})
		});
		document.getElementById("edit-wx").addEventListener('tap', function(e) {
			var btnArray = ['取消', '保存'];
			mui.prompt('输入您的微信号', '微信号', '有空ucoon', btnArray, function(e) {
				if (e.index == 1) {
					$.ajax({
						url : 'user/wxId',
						data : {content:e.value},
						async : false,
						type : 'post',
						dataType : 'json',
						success : function(data) {
							if(data.result == 'error'){
								alert(data.msg);
								return;
							}
							window.history.go(0);
						}
					})
				} else {
				
				}
			})
		});
		
	</script>
</html>
