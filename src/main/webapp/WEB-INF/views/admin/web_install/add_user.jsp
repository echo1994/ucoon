<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html;charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>后台管理系统</title>
		<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
		<link rel="stylesheet" href="css/cj.css" type="text/css" />
		<link rel="stylesheet" href="css/list.css" type="text/css" />	
    <style type="text/css">
        *{word-break:break-all;word-wrap:break-word;}
        div,p,form,h1,h2,h3,h4,h5,h6,input,textarea,span,label{margin:0;padding:0;}
        body{font-size:14px;font-family:"Microsoft YaHei","微软雅黑";margin:0;padding:0;color:#676767;background:#fff;}
        input,textarea{font-family:"Microsoft YaHei","微软雅黑";font-size:16px;}
        textarea{resize:none;outline:none;}
        dl,dd,dt,ul,li,ol{list-style-type:none;margin:0;padding:0;}
        a{outline:none;text-decoration:none;}
        img{vertical-align:middle;}
        i{font-style:normal;}
        .infolist{font-size:14px;position:relative;color:#494949;min-height:38px;margin:5px 0;box-sizing:border-box;-moz-box-sizing:border-box;-webkit-box-sizing:border-box;width:100%;float:left;clear:both;}
        .infolist lable{position:absolute;width:5em;left:0;top:0;overflow:hidden;height:22px;padding:8px 0;line-height:22px;}
        .infolist .liststyle p{line-height:22px;padding:8px 0;}
        .infolist .sex a{display:inline-block;height:38px;line-height:38px;float:left;margin-right:15px;color:#494949;padding-left:20px;background-image:url(../images/sex.png);background-position:2px 12px;background-repeat:no-repeat;background-size:13px auto;}

        .infolist .liststyle .m_text{height:36px;line-height:36px;border-radius:5px;padding:0 10px;background:#F7F7F7;border:1px solid #E8E8E8;color:#9E9E9E;box-sizing:border-box;-moz-box-sizing:border-box;-webkit-box-sizing:border-box;}
        .infolist .liststyle .m_age{width:50%;}
        .infolist .liststyle .m_tel{width:65%;}
        .infolist .liststyle .m_address{width:100%;clear:both;}
        .infolist .liststyle .m_password{width:70%;}
        .infolist .liststyle span{position:relative;height:36px;line-height:36px;display:inline-block;border:1px solid #E8E8E8;border-radius:5px;padding:0 0 0 8px;cursor:pointer;background:white;float:left;margin-right:9px;margin-bottom:10px;}
        .infolist .liststyle span i{display:inline-block;height:36px;line-height:36px;padding-right:30px;position:relative;}
        .infolist .liststyle span i:after{content:"";display:block;width:20px;height:36px;position:absolute;right:0;top:0;background-image:url(images/jt_nor.png);background-repeat:no-repeat;background-position:0 center;background-size:10px auto;}
        .infolist .liststyle span.active{border-radius:5px 5px 0 0;border-bottom:0;}

        .infolist .liststyle span ul{width:100%;border-radius:0 0 5px 5px;position:absolute;border:1px solid #E8E8E8;background:white;left:-1px;top:36px;border-top:0;margin-bottom:10px;display:none;z-index:10;}
        .infolist .liststyle span ul li{width:100%;float:left;overflow:hidden;border-top:1px solid #E8E8E8;}
        .infolist .liststyle span ul li:first-child{border-top:0;}
        .infolist .liststyle span ul li a{display:block;height:36px;line-height:36px;padding:0 8px;color:#494949;}
    </style>
			
			
   </head>
  
  <body >
			<div class="pagetitle">新增成员
				<a href="admin/user_list">>>返回列表</a>
			</div>
		<form action="admin/getadd_User" method="post">
				<table class="table table-bordered table-condensed">
					<tr>
						<td colspan="2">成员基本信息设置</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>用户ID:</td>
						<td class="item_input">
							<input type="text" id="openId" name="openId"  class="form-control input-sm">
						</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>微信名:</td>
						<td class="item_input">
							<input type="text" id="nickName" name="nickName"  class="form-control input-sm">
						</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>微信号:</td>
						<td class="item_input">
							<input type="text" id="weixinId" name="weixinId"  class="form-control input-sm">
						</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>性别:</td>
						<td class="item_input">
						<label>
							<input type="radio" id="sex" name="sex" checked="checked" value="0">
							男
						</label>
						<label>
							<input type="radio" id="sex" name="sex" value="1">
							女
						</label>
						</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>个性签名:</td>
						<td class="item_input">
							<textarea  name="signature" id="signature" style="height:60px;width:500px" class="form-control"></textarea>
						</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>生日:</td>
						<td class="item_input">
							<input type="text" name="bb" id="bb"onfocus="MyCalendar.SetDate(this)"/>
						</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>手机号码:</td>
						<td class="item_input">
							<input type="text" id="phone" name="phone"  class="form-control input-sm">
						</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>真实姓名:</td>
						<td class="item_input">
							<input type="text" id="name" name="name"  class="form-control input-sm">
						</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>头像:</td>
						<td class="item_input">
							<input type="file" name="head" id="head" style="width:64px;">
						</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>省份:</td>
						<td class="item_input">
							<input type="text" id="province" name="province"  class="form-control input-sm">
						</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>城市:</td>
						<td class="item_input">
							<input type="text" id="city" name="city"  class="form-control input-sm">
						</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>个人简介:</td>
						<td class="item_input">
							<textarea style="height:120px;width:500px" class="form-control" name="intro"id="intro"></textarea>
						</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>评分:</td>
						<td class="item_input">
							<input type="text" id="credit" name="credit"  class="form-control input-sm">
						</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>注册时间</td>
						<td class="item_input">
					 <input type="text" name="rr" id="rr"onfocus="MyCalendar.SetDate(this)"/>
					
						</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>状态:</td>
						<td class="item_input">
							<input type="text" id="state" name="state"  class="form-control input-sm">
						</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>纬度:</td>
						<td class="item_input">
							<input type="text" id="latitude" name="latitude"  class="form-control input-sm">
						</td>
					</tr>
					<tr>
						<td class="item_title"><span class="impt">*</span>经度:</td>
						<td class="item_input">
							<input type="text" id="longitude" name="longitude"  class="form-control input-sm">
						</td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td class="item_title"></td>
						<td class="item_input">
							<p  class="btn btn-primary btn-sm" style="width:150px;" onclick="sub()" >提交</p>
						</td>
					</tr>
				</table>
	</form> 
	</body>
</html>
<script src="js/mydate.js" type="text/javascript"></script>
<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="script/bootstrap.min.js" type="text/javascript"></script> 
<script src="js/admin.js" type="text/javascript"></script>
<script src="js/wuajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript">
	function sub(){
	var c=$("input").serialize();
	var openId=$("#openId").val();
	var nickName=$("#nickName").val();
	var weixinId=$("#weixinId").val();
	var sex=$("#sex").val();
	var signature=$("#signature").val();
	var bb=$("#bb").val();
	var phone=$("#phone").val();
	var name=$("#name").val();
	var intro=$("#intro").val();
	var credit=$("#credit").val();
	var rr=$("#rr").val();
	var province=$("#province").val();
	var city=$("#city").val();
	var state=$("#state").val();
	var latitude=$("#latitude").val();
	var longitude=$("#longitude").val();
	var user={
		openId:openId,
		nickName:nickName,
		weixinId:weixinId,
		sex:sex,
		signature:signature,
		phone:phone,
		bb:bb,
		credit:phone,
		name:name,
		intro:intro,
		credit:credit,
		rr:rr,
		province:province,
		city:city,
		state:state,
		latitude:latitude,
		longitude:longitude
	};
	$.ajaxFileUpload({
		type:"post",	
		//处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
		data:user,
		url:"admin/getadd_User",
		secureuri:false,                       //是否启用安全提交,默认为false 
		fileElementId:'head',           //文件选择框的id属性
		dataType:'text',                       //服务器返回的格式,可以是json或xml等
		success:function(data, status){        //服务器响应成功时的处理函数
			if(data=="true"){
				alert("添加成功");
				window.location.onload();
			}else{
				alert("添加失败");
			}
		}
	});
	}
</script>