<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<title>提示</title>
		<meta content="yes" name="apple-mobile-web-app-capable">
	    <meta content="yes" name="apple-touch-fullscreen">
	    <meta content="telephone=no,email=no" name="format-detection">
	    <meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="css/style.css">
    	<script src="js/flexible.js"></script> 
    	<script src="js/jquery-2.1.4.min.js"></script> 
  </head>
 
  <body>
  
  	<div class="pic"></div>
	<p class="tip"> ${sessionScope.msg }，<span id="time">3</span>秒后跳转</p>
   
  </body>
  
</html>
  <script type="text/javascript">
   	Load();
    function Load() {
        for (var i = 3; i >= 0; i--) {
            window.setTimeout('doUpdate(' + i + ')', (3 - i) * 1000);
        }
    }
    function doUpdate(num) {
        $("#time").text(num);
        if (num == 0) {
            window.location = '${sessionScope.url}';
        }
    }
   </script>