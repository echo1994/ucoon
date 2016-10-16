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
<meta charset="UTF-8">
<title>选人个人详情</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<script src="js/mui.min.js"></script>
<script src="js/jquery-2.1.4.min.js"></script>
<link href="css/mui.min.css" rel="stylesheet" />
<link href="css/style.css" rel="stylesheet" />
<link href="css/iconfont.css" rel="stylesheet" />
</head>
<script type="text/javascript">
	$(function() {
		$(".cur").bind("tap", function() {
			var key1 = $(this).attr("data-confirm");
			alert(key1);
			$.ajax({
				url : 'applyOrders/chosePeople/' + key1,
				async : false,
				type : 'get',
				success : function(data) {
					if (data != "success") {
						alert("选人失败");
						return; 
					}
					mui.openWindow({
						url: "mission/selectpeople/" + ${orders.missionId}
					});
				}
			});
		});
	})
</script>
<style>
.basic-mes {
	margin: 0;
	padding: 15px 0;
	background: #fff;
}

.basic-mes img {
	width: 60px;
	height: 60px;
}

.mysend {
	margin: 0;
}

.mysend .mysend-col {
	height: 70px;
	border-bottom: 1px solid #ddd;
	margin: 0;
}

.mysend .mysend-col img {
	width: 45px;
	height: 45px;
	margin: 10.5px 5px;
}

.mysend .mysend-col .m-t .t-m {
	padding-top: 9px;
}

.mysend .mysend-col .m-t {
	border-bottom: 0;
	height: 70px;
}
</style>

<body>
	<div class="mui-content">
		<div class="basic-mes">
			<!--头像-->
			<img src="${user.headImgUrl }">
			<div class="ucoon-user">${user.nickName }
				<c:choose>
					 <c:when test="${user.sex == 2}">
					 <i class="mui-icon iconfont icon-woman"></i>
					 </c:when>
					 <c:otherwise>
						<i class="mui-icon iconfont icon-man"></i>
					 </c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="more-mes clearfix">
			<div class="mes-col fl">
				<i class="mui-icon mui-icon-phone"></i><span class="lab">电话</span><span><a href="tel:${user.phone }">${user.phone }</a></span>
			</div>
			<div class="mes-col fl">
				<i class="mui-icon mui-icon-weixin"></i><span class="lab">微信号</span><span>${user.weixinId}</span>

			</div>
		</div>
		<div class="liuyan">
			<p class="liu-title">留言：</p>
			<p class="liu-cont">${orders.note}</p>

		</div>


		<div class="discus">
			<p class="pinglun">
				<span>别人对他的评价</span><span>(${size})</span>
			</p>
			<ul class="m-discus">
				<c:forEach items="${infos}" var="info">
					<li class="discus-col">
						<div class="father">
							<img class="fl" src="${info.head_img_url }">
							<div class="f-r fr">
								<p class="discus-time">${info.peevaluate_time }</p>
							</div>
							<div class="f-m">
								<p>
									${info.nick_name }
									<c:choose>
										 <c:when test="${info.sex == 2}">
										 <i class="mui-icon iconfont icon-woman"></i>
										 </c:when>
										 <c:otherwise>
											<i class="mui-icon iconfont icon-man"></i>
										 </c:otherwise>
									</c:choose>
									
								</p>
								<p class="discus-content">${info.publish_evaluate }</p>
							</div>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<br /> <br /> <br /> <br />

		<div class="fix-btn">
			<button class="fl">聊一聊</button>
			<button class="fl cur" data-confirm="${orders.applyId}" data-result="1">选Ta</button>
		</div> 

	</div>

</body>
<script>
	
</script>
</html>
