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
<title>个人详情</title>
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
			var key2 = $(this).attr("data-result");
			alert(key1 + "," + key2);
			$.ajax({
				url : 'apply/confirmAppliment/' + key1 + '/' + key2,
				data : {
					startIndex : 0,
					endIndex : 0
				},
				async : false,
				type : 'post',
				success : function(data) {
					if (data == "true") {
						alert("操作成功");
					}
					history.go(-1);
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
			<img src="images/pic2.jpg">
			<div class="ucoon-user">${au.nick_name}<i
					class="mui-icon iconfont icon-man"></i>
			</div>
		</div>
		<div class="more-mes clearfix">
			<div class="mes-col fl">
				<i class="mui-icon mui-icon-phone"></i><span class="lab">电话</span><span>${au.phone}</span>
			</div>
			<div class="mes-col fl">
				<i class="mui-icon mui-icon-weixin"></i><span class="lab">微信号</span><span>${au.weixin_id}</span>

			</div>
		</div>
		<div class="liuyan">
			<p class="liu-title">留言：</p>
			<p class="liu-cont">${au.note}</p>

		</div>


		<div class="discus">
			<p class="pinglun">
				<span>评论</span><span>(5)</span>
				<button class="fr pinglunn-btn">评论</button>
			</p>
			<ul class="m-discus">
				<li class="discus-col">
					<div class="father">
						<img class="fl" src="images/muwu.jpg">
						<div class="f-r fr">
							<p class="discus-time">08-08 13:30</p>
						</div>
						<div class="f-m">
							<p>
								满血复活大魔王<i class="mui-icon iconfont icon-man"></i>
							</p>
							<p class="discus-content">有钱啥都干</p>
						</div>
					</div>
					<div class="son clearfix">
						<div class="s-r fr">
							<p class="discus-time">08-08 13:36</p>
						</div>
						<div class="s-m fl">
							<p>
								<span>Toad</span>@<span>满血复活大魔王</span>
							</p>
							<p class="discus-content">睡觉干吗？</p>
						</div>
					</div>
					<div class="son clearfix">
						<div class="s-r fr">
							<p class="discus-time">08-08 13:52</p>
						</div>
						<div class="s-m fl">
							<p>
								<span>满血复活大魔王</span>@<span>Toad</span>
							</p>
							<p class="discus-content">都说了有钱就干！！！</p>
						</div>
					</div>
				</li>
				<li class="discus-col">
					<div class="father">
						<img class="fl" src="images/pic1.png">
						<div class="f-r fr">
							<p class="discus-time">08-08 13:30</p>
						</div>
						<div class="f-m">
							<p>
								你大爷<i class="mui-icon iconfont icon-women"></i>
							</p>
							<p class="discus-content">有钱啥都干</p>
						</div>
					</div>
				</li>
				<li class="discus-col">
					<div class="father">
						<img class="fl" src="images/muwu.jpg">
						<div class="f-r fr">
							<p class="discus-time">08-08 13:30</p>
						</div>
						<div class="f-m">
							<p>
								满血复活大魔王<i class="mui-icon iconfont icon-man"></i>
							</p>
							<p class="discus-content">有钱啥都干</p>
						</div>
					</div>
				</li>
			</ul>
		</div>
		<br /> <br /> <br /> <br />

		<div class="fix-btn">
			<button class="fl">聊一聊</button>
			<button class="fl cur" data-confirm="${aId}" data-result="1">选Ta</button>
		</div> 

	</div>

</body>
<script>
	
</script>
</html>
