/*
 登录界面
 */
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
					    	window.location.href = 'admin/index';// 登录后跳转
					    	if (result.result == "success") {
								setTimeout(function() {
									window.location.href = 'admin/index';// 登录后跳转
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
