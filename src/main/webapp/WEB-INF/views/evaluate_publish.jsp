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
    <meta charset="utf-8">
    <title>发布任务</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="css/mui.min.css">
    <link href="css/style.css" rel="stylesheet" />
    <link href="css/iconfont.css" rel="stylesheet" />
    <script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
    <style>
        html,
        body{
            background: #fff;
        }
        .mui-content{
            background: #fff;
        }
        .basic-mes{
            margin-top: 10px;
            width: 80px;
        }
        .basic-mes img{
            width: 40px;
            height:40px;
        }
        .ucoon-user{
            display: -webkit-box;
            color: #999;
            font-size: 12px;
            overflow: hidden;
            text-overflow: ellipsis;
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
            width: 80px;
        }
        .five-star .star-cot{
            width: 150px;
            height: 40px;
            margin: 14px auto 0 auto;
        }
        .five-star .star-cot li{
            float: left;

        }
        .five-star .star-cot li i{
            font-size: 30px;
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
        .commit{
            display: block;
            margin-top: 20px;
            background: #C3D94F;
            width: 250px;
            margin: 0 auto;
            color: #fff;
        }
        .m-user-mes{
            width: 240px;
            margin: 0 auto;
        }
        .line{
            letter-spacing: -3px;
            color: #ddd;
            font-size: 10px;
            text-align: center;
        }
        .line span{
            letter-spacing: 0;
            color: #ddd;
            margin:0 20px;
        }
    </style>
</head> 
<script src="js/mui.min.js"></script>
<script type="text/javascript">
    function starClick(id) {
        var prompt=['很差','比较差','一般','比较好','非常好'];	//评价提示语
        $(id).find('li').on('click',function () {
            var num=$(this).index();		//遍历img元素，设置单独的id
            $(id).find("i").removeClass('icon-star');//当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色
            $(this).find("i").addClass('icon-star');		//设置鼠标当前所在图片为打星颜色图
            $(this).prevAll().find("i").addClass('icon-star');	//设置鼠标当前的前面星星图片为打星颜色图
            $(this).parent().parent().find('.star-info').text(prompt[num]);		//根据id的索引值作为数组的索引值
        	$(this).parent().parent().find('.star-score').val(num+1);		//根据id的索引值作为数组的索引值
        
        })

    }
   

</script>
<body>
<div class="mui-content">
    <h2 class="title-who"><i class="mui-icon iconfont icon-star-empty"></i>评价</h2>
	<form action="mission/addEvaluate" method="post" id="myform">
		<input type="hidden" name="missionId" value="${missionId }">
		<c:forEach items="${list}" var="info">
		    <div class="m-people">
		        <div class="line">
		            ————————
		            <span>对TA的评价</span>
		            ————————
		        </div>
		        <div class="m-user-mes clearfix">
		            <div class="basic-mes fl">
		                <!--头像-->
		                <img src="${info.head_img_url }">
		                <span class="ucoon-user">${info.nick_name }</span >
		            </div>
		            <div class="five-star fr">
		                <ul class="star-cot" id="people${info.apply_id }">
		                    <li><i class="mui-icon iconfont icon-star-empty"></i></li>
		                    <li><i class="mui-icon iconfont icon-star-empty"></i></li>
		                    <li><i class="mui-icon iconfont icon-star-empty"></i></li>
		                    <li><i class="mui-icon iconfont icon-star-empty"></i></li>
		                    <li><i class="mui-icon iconfont icon-star-empty"></i></li>
		                </ul>
		                <p class="star-info">点击星星，为Ta评价！</p>
		                <input type="hidden" class="star-score" name="score">
		                <input type="hidden" name="userId" value="${info.user_id }">
		                <script>
		                	starClick(people${info.apply_id });
		                </script>
		            </div>
		        </div>
		        <div class="txtinput">
		            <textarea placeholder="对Ta的服务满意吗？表扬下Ta或者给Ta点建议吧！" name="content"></textarea>
		        </div>
		    </div>
	    </c:forEach>
	</form>
    <button class="commit">提交评价</button>
</div>
<script>
	$(function(){
	
		$(".commit").click(function(){
		
			$("#myform").submit();
			
		});
	
	})
</script>
</body>

</html>
