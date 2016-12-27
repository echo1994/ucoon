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
    <meta charset="utf-8">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta content="telephone=no,email=no" name="format-detection">
    <link rel="stylesheet" href="css/style.css">
    <script src="js/flexible.js"></script>

    <title>泉友车生活</title>
    <style>
        html{
            background-color: #fff;
        }
    </style>
</head>
<body>
    <div class="h-header">
        <img src="images/pic1.png" alt="">
        <p class="user-name">Toad</p>
    </div>
    <table class="list">
        <thead >
            <tr>
                <th width="30%">时间</th>
                <th width="30%">订单号</th>
                <th width="20%">充值金额</th>
                <th width="20%">状态</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>20161201</td>
                <td>20161201</td>
                <td class="red">+206</td>
                <td>成功</td>
            </tr>
            <tr>
                <td>20161201</td>
                <td>20161201</td>
                <td class="red">+206</td>
                <td>成功</td>
            </tr>
            <tr>
                <td>20161201</td>
                <td>20161201</td>
                <td class="red">+206</td>
                <td>成功</td>
            </tr>
        </tbody>
    </table>


</body>
<script>
    document.body.addEventListener('touchstart', function() {}, false);

</script>
</html>