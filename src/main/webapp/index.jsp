<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<script src="http://cdn.bootcss.com/sockjs-client/1.1.1/sockjs.min.js"></script>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet"
	href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet"
	href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<!--<script type="text/javascript" src="js/jquery-1.7.2.js"></script>-->
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>webSocket测试</title>
<script type="text/javascript">
	$(function() {

		var fromuserid =<%=request.getParameter("fromid")%>;
		var touserid =<%=request.getParameter("toid")%>;
		var websocket;
		if ('WebSocket' in window) {
			alert("WebSocket");
			websocket = new WebSocket("ws://127.0.0.1:8080/ucoon/echo?userid="
					+ fromuserid);
		} else if ('MozWebSocket' in window) {
			alert("MozWebSocket");
			websocket = new MozWebSocket("ws://ucoon/echo");
		} else {
			alert("SockJS");
			websocket = new SockJS("http://127.0.0.1:8080/ucoon/sockjs/echo");
		}
		websocket.onopen = function(evnt) {
			$("#tou").html("链接服务器成功!")
		};
		websocket.onmessage = function(evnt) {
			//接收消息
			$("#msg").html($("#msg").html() + "<br/>" + evnt.data);
		};
		websocket.onerror = function(evnt) {
		};
		websocket.onclose = function(evnt) {
			$("#tou").html("与服务器断开了链接!")
		}
		$('#send').bind('click', function() {
			send();
		});
		function send() {
			//发送消息
			/* 
			{
				"from_user_id": "发送方",
			    "to_user_id": "接收方",
			    "message_type": "消息类型",
			    "post_time": "发送时间（时间戳）",
			    "message_detail": "消息内容"
			} 
			 */

			if (websocket != null) {
				var message = document.getElementById('message').value;
				var timestamp = new Date().getTime();
				var message_content = '{"from_user_id":"' + fromuserid
						+ '","to_user_id":"' + touserid + '","message_type":"text"'
						+ ',"post_time":"' + timestamp + '","message_detail":"'
						+ message + '"}';
				websocket.send(message_content);
			} else {
				alert('未与服务器链接.');
			}
		}
	});
</script>

</head>
<body>

	<div class="page-header" id="tou">webSocket及时聊天Demo程序</div>
	<div class="well" id="msg"></div>
	<div class="col-lg">
		<div class="input-group">
			<input type="text" class="form-control" placeholder="发送信息..."
				id="message"> <span class="input-group-btn">
				<button class="btn btn-default" type="button" id="send">发送</button>
			</span>
		</div>
		<!-- /input-group -->
	</div>
	<!-- /.col-lg-6 -->
	</div>
	<!-- /.row -->
</body>

</html>