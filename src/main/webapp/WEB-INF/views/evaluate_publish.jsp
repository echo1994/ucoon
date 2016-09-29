<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<title>发布任务</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="css/mui.min.css">
		<link href="css/style.css" rel="stylesheet" />
		<link href="css/iconfont.css" rel="stylesheet" />
		<script src="js/jquery-2.1.4.min.js"></script>
		<style>
		html,
		body{
			background: #fff;
		}
			.mui-content{
				background: #fff;
			}
			.basic-mes{
				margin-top: 20px;
			}
			.basic-mes img{
				width: 60px;
				height: 60px;
			}
			.ucoon-user{
				color: #999;
			}
			.five-star .star-cot{
				width: 180px;
				height: 40px;
				margin: 30px auto 0 auto;
			}
			.five-star .star-cot li{
				float: left;
				
			}
			.five-star .star-cot li i{
				font-size: 35px;
				color: #C3D94F;
			}
			.star-info{
				text-align: center;
				color: #ddd;
				font-size: 12px;
			}
			.txtinput{
				margin: 20px auto 0 auto;
				width: 250px;
			}
			.txtinput textarea{
				font-size: 12px;
				padding: 5px;
				height: 80px;
			}
			.txtinput button{
				margin-top: 20px;
				background: #C3D94F;
				width: 250px;
				color: #fff;
			}
		</style>
	</head>

	<body>
		<div class="mui-content">
			<h2 class="title-who"><i class="mui-icon iconfont icon-star-empty"></i>评价</h2>
			<div class="basic-mes">
			<!--头像-->
				<img src="${user.headImgUrl}">
				<div class="ucoon-user">${user.nickName}
					<c:choose>
					    <c:when test="${user.sex == 2}">
					       <i class="mui-icon iconfont icon-women"></i>
					    </c:when>
					    <c:otherwise>
					        <i class="mui-icon iconfont icon-man"></i>
					    </c:otherwise>
					</c:choose>
					
				</div>
			</div>
			<div class="five-star">
				<ul class="star-cot">
					<li><i class="mui-icon iconfont icon-star-empty"></i></li>
					<li><i class="mui-icon iconfont icon-star-empty"></i></li>
					<li><i class="mui-icon iconfont icon-star-empty"></i></li>
					<li><i class="mui-icon iconfont icon-star-empty"></i></li>
					<li><i class="mui-icon iconfont icon-star-empty"></i></li>
				</ul>
				<p class="star-info">点击星星，为Ta评价！</p>
			</div>
			<div class="txtinput">
			<textarea placeholder="对Ta的服务满意吗？表扬下Ta或者给Ta点建议吧！" id="content"></textarea>
			
			<button id="submit">提交评价</button>
			</div>
		</div>
		<script src="js/mui.min.js"></script>
		<script type="text/javascript">
		
			($(function() {
				var score = 0;//分数
				var content = "";//留言
				
				$('li').each(function(index){
					var prompt=['很差','比较差','一般','比较好','非常好'];	//评价提示语
					var num=$(this).index();		//遍历img元素，设置单独的id
					$(this).on("mouseover click",function(){	//设置鼠标滑动和点击都会触发事件
						score = num + 1;
						$('li').find("i").removeClass('icon-star');//当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色
						$(this).find("i").addClass('icon-star');		//设置鼠标当前所在图片为打星颜色图
						$(this).prevAll().find("i").addClass('icon-star');	//设置鼠标当前的前面星星图片为打星颜色图
						$('.star-info').text(prompt[num]);		//根据id的索引值作为数组的索引值
					});
				});
				
				
				$("#submit").on('tap',function(){
					if(score == 0){
						alert("分数不能为0");
						return;
					}
					content = $("#content").val();
					
					$.ajax({
						url : 'applyOrders/addEvaluate',
						data : {
							score : score,
							content : content,
							missionId : ${evaluate.missionId}
						},
						async : false,
						type : 'post',
						dataType : 'json',
						success : function(data) {
							if(data.result != 'success'){
								alert(data.msg);
								return;
							}
							alert(data.msg);
							window.location.href = "mysend";
						}
					})
				
				})
			}))
		</script>
	</body>

</html>
