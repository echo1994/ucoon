<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<title>后台管理系统</title>
		<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
		<link rel="stylesheet" href="css/cj.css" type="text/css" />
		<link rel="stylesheet" href="css/list.css" type="text/css" />		
	</head>
	<body >
		<div class="listbox">
			<div class="pagetitle">轮播图添加
				<a href="admin/adv_photo">>>返回列表</a>
			</div>
			<form  name="myform" id="myform" >
				<table class="table table-bordered table-condensed">
					
					<tr>
						<td colspan="2">轮播图信息</td>
					</tr>
	
	
					<tr>
						<td class="item_title">图片选择:</td>
						<td class="item_input"><input type="file" id="photoTurn" name="photoTurn" class="form-control input-sm"></td>
					</tr>
					<tr>
						<td class="item_title">图片名称:</td>
						<td class="item_input"><input type="text" id="photoName" name="photoName"  class="form-control input-sm"></td>
					</tr>
					<tr>
						<td class="item_title">图片跳转路径:</td>
						<td class="item_input"><input type="text" id="photoGoUrl" name="photoGoUrl"  class="form-control input-sm"></td>
					</tr>
					<tr>
						<td class="item_title">轮播位置:</td>
						<td class="item_input"><select class="form-control selectpicker" id="local" name="local"></select></td>
					</tr>
					<tr>
						<td class="item_title">是否跳转:</td>
						<td class="item_input">
						
								<label class="radio-inline"><input type="radio" class="isGo" name="isGo"  value="0">停止</label>
								<label class="radio-inline"><input type="radio" class="isGo" name="isGo" value="1" checked="checked">允许</label>
						</td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td class="item_title"></td>
						<td class="item_input">
							<p  onclick="ajaxFileUpload()" class="btn btn-primary btn-sm" style="width:150px;">保存</p>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>

<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script> 
	<script src="js/ajaxfileupload.js" type="text/javascript"></script> 
<script>
	//初始化select框
	for(var i = 1;i<10;i++){
			option = '<option value="'+i+'">'+i+'</option>';
			$("select").append(option);
	}
							

		function ajaxFileUpload(){ 
		//在ajaxfileupload这边data用json+type=post ，可以传递中文非乱码，如果是序列化则是不行
		var photoName = $("#photoName").val();
		var photoGoUrl = $("#photoGoUrl").val();
		var local = $("#local").val();
		var isGo = $(':radio[name="isGo"]:checked').val();
	//	alert(JSON.stringify({photoName:photoName,photoGoUrl:photoGoUrl,local:local,isGo:isGo}))
	//	return ;
	//	var photoMsg  = $("#myform").serialize();
		//alert(photoMsg) 
	//开始上传文件时显示一个图片,文件上传完成将图片隐藏
	//$("#loading").ajaxStart(function(){$(this).show();}).ajaxComplete(function(){$(this).hide();});
	//执行上传文件操作的函数
	$.ajaxFileUpload({
		type:"post",	
		//处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
		url:"admin/PhotofileUp",
		data:{photoName:photoName,photoGoUrl:photoGoUrl,local:local,isGo:isGo},
		secureuri:false,                       //是否启用安全提交,默认为false 
		fileElementId:'photoTurn',           //文件选择框的id属性
		dataType:'text',                       //服务器返回的格式,可以是json或xml等
		success:function(data, status){        //服务器响应成功时的处理函数
		window.location.href="admin/adv_photo";
			/* data = data.replace("<PRE>", '');  //ajaxFileUpload会对服务器响应回来的text内容加上<pre>text</pre>前后缀
			data = data.replace("</PRE>", '');
			data = data.replace("<pre>", '');
			data = data.replace("</pre>", ''); //本例中设定上传文件完毕后,服务端会返回给前台[0`filepath]
			if(data.substring(0, 1) == 0){     //0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
				$("img[id='uploadImage']").attr("src", data.substring(2));
				$('#result').html("图片上传成功<br/>");
			}else{
				$('#result').html('图片上传失败，请重试！！');
			} */
		}
		/* ,
		error:function(data, status, e){ //服务器响应失败时的处理函数
		//	$('#result').html('图片上传失败，请重试！！');
		} */
	});
}
</script>
