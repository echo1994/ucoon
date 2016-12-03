<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false" %>  
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
		<link rel="stylesheet" href="css/style.css">
		<script src="js/jquery-2.1.4.min.js"></script>
    	<script src="js/flexible.js"></script>
  </head>
  
  <body>
  
  	<div class="pic"></div>
	<p class="tip"> ${msg }，<span id="time">3</span>秒后跳转</p>
    <script>
    	var time = 2;
    	setInterval(function(){
    		document.getElementById("time").innerHTML = time;
    		if(time == 0){
    			window.location.href='${url}';
    		}else{
    			time--;
    		}
    	},1000);  
    </script>
  </body>
</html>
