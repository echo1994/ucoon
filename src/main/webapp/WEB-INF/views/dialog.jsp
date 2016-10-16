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
    <title>Hello MUI</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <!--标准mui.css-->
    <link rel="stylesheet" href="css/mui.min.css">
    <link rel="stylesheet" href="css/style.css">

    <style>
        .mui-btn {
            display: block;
            width: 120px;
            margin: 10px auto;
        }

        #info {
            padding: 20px 10px;
        }
        .mui-popup-inner{
            padding: 0;
        }
        .mui-popup-title{
            padding: 10px 0;
            font-size: 14px;
        }

        .mui-popup-text .inptel{
            width: 58%;
            padding: 5px;
            margin: 0;
        }
        .mui-popup-text .inpyzm{
            width: 90%;
            padding: 5px;
            margin: 10px 0 20px 0;
        }

        .get-btn{
            padding: 5px;
            height: 40px;
            width: 80px;
            margin-left: 5px;
            background: #ccc;
            color: #fff;
            border: none;
        }
        button:enabled:active{
            background: #ccc;
        }

        .mui-popup-button{
            color: #C3D94F;
        }
    </style>

</head>

<body>

<div class="mui-content">
    <div class="mui-content-padded" style="margin: 5px;text-align: center;">
        <button id='confirmBtn' type="button" class="mui-btn mui-btn-blue mui-btn-outlined">手机绑定</button>
        <div id="info"></div>
    </div>
</div>
<script src="js/mui.min.js"></script>
<script type="text/javascript" charset="utf-8">
    //mui初始化
    mui.init({
        swipeBack: true //启用右滑关闭功能
    });

    document.getElementById("confirmBtn").addEventListener('tap', function() {
        var btnArray = ['取消', '绑定'];
        mui.confirm('MUI是个好框架，确认？', '手机绑定', btnArray, function(e) {
            if (e.index == 1) {
                info.innerText = '你刚确认MUI是个好框架';
            } else {
                info.innerText = 'MUI没有得到你的认可，继续加油'
            }
        })
        document.querySelector('.mui-popup-text').innerHTML='<input id="tel" class="inptel" onkeyup="telTest()" autofocus type="tel" placeholder="请输入您的手机号"><button class="get-btn" id="getBtn">获取验证码</button><input class="inpyzm" type="tel" name="" id="" placeholder="请输入验证码">'

    });


    //手机验证
    var reg =  /^[1][3-8]+\d{9}$/;

    function telTest() {
        var getBtn = document.getElementById("getBtn");
        var inpTelval = document.getElementById("tel").value;
        if (reg.test(inpTelval)){
            getBtn.style.background ="#C3D94F";
            getBtn.addEventListener("click", setTime);
        }else {
            getBtn.style.background ="#ccc";
            getBtn.removeEventListener("click", setTime);
        }
    }
    //60s倒计时
    var countdown=60;
    function setTime() {
        var getBtn = document.getElementById("getBtn");
        getBtn.style.background ="#ccc";
        if (countdown == 0) {
            getBtn.innerText="重新获取";
            getBtn.style.background ="#C3D94F";
            countdown = 60;
            getBtn.addEventListener("click", setTime);
        } else {
            getBtn.innerText="已发送(" + countdown + ")";
            countdown--;
            getBtn.removeEventListener("click", setTime);
            setTimeout(function () {
                setTime()
            },1000)
        }
    }


</script>

</body>

</html>