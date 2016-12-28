<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<title>后台管理系统</title>
		<link rel="stylesheet" href="css/cj.css" type="text/css" />		
		<link rel="stylesheet" href="css/login.css" type="text/css" />		
	</head>
	<body class="loginpage">
		<div class="loginbox">
			<div class="loginboxinner">
				<div class="logo">
					<h1 class="logo"><img src="images/bus_logo.png"></h1>
					<span class="slogan">后台管理系统 </span>
				</div>
				<br style="clear:both" />
				<div class="nousername">
					<div class="loginmsg"></div>
				</div>
				<div class="username">
					<div class="usernameinner">
						<input type="text" name="username" id="username" placeholder="用户名" />
					</div>
				</div>
				<div class="password">
					<div class="passwordinner">
						<input type="password" name="password" id="password" placeholder="密码" />
					</div>
				</div>
				<div class="code">
					<div class="codeinner">
						<input type="type" name="code" id="code" placeholder="验证码" />
						<span class="codeimg" id="codeimg"><img src="code/checkCode" /></span>
					</div>
				</div>
				<button type="button" id="oa_login">登录</button>
			</div>
		</div>
	</body>
</html>
<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script>
<!-- <script src="js/login.js" type="text/javascript"></script> -->
<script type="text/javascript">

$(function() {
	$('#oa_login').click(
			function() {
				var username = $('#username').val(), userpassword = $(
						'#password').val(), code = $('#code').val();
				if (username == "") {
					$('.loginmsg').text("用户名不能为空！")
					$('.nousername').fadeIn();
					return false;
				} else if (userpassword == "") {
					$('.loginmsg').text("密码不能为空！")
					$('.nousername').fadeIn();

				} else if (code == "") {
					$('.loginmsg').text("验证码不能为空！")
					$('.nousername').fadeIn();

				} else {
					$("#oa_login").attr("disabled", true).html(
							"正在登录中<span class='dotting '></span>");
					$.ajax({
					    url: "admin/checkuser?code=" + code + "&username="
						+ username + "&password=" + userpassword,
					    success: function(result){
					    	//window.location.href = 'admin/index';// 登录后跳转
					    	if (result.result == "success") {
								setTimeout(function() {
										window.location.href = 'admin/index'
									//window.location.href = 'admin/index';//登录后跳转
								}, 1000);
							} else {
								setTimeout(function() {
									$('.loginmsg').text(result.msg);
									$('.nousername').fadeIn();
									$("#oa_login").attr("disabled", false).html("登录");
								}, 1000);
								$("#codeimg").find("img").attr('src',
										"code/checkCode?" + new Date().getTime());
							}
					    },
					  	dataType: "json",
					  	async:true
					});
				}
			});

	$("#codeimg").click(
			function() {
				$(this).find("img").attr('src',
						"code/checkCode?" + new Date().getTime());

			});
});
</script>