<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
			<div class="pagetitle">新增回复
				<a href="admin/response">>>返回列表</a>
			</div>
		<table class="table table-bordered table-condensed">
			<tr>
				<td class="item_title"><span class="impt">*</span>类型:</td>
				<td class="item_input">
				<input type="radio" id="type" name="type" value="text" checked="checked"onchange="change()">文本
				<input type="radio" id="type" name="type" value="news"onchange="change()">图文</td>
			</tr>
			<tr>
				<td class="item_title"><span class="impt">*</span>规则名:</td>
				<td class="item_input">
				<input type="text" id="ruleName" name="ruleName" class="form-control input-sm"></td>
			</tr>
			<tr>
				<td class="item_title"><span class="impt">*</span>关键字:</td>
				<td class="item_input">
				<input type="text" id="keywords" name="keywords" class="form-control input-sm"></td>
			</tr>
			<tr class="tt">
				<td class="item_title"><span class="impt">*</span>回复内容:</td>
				<td class="item_input">
				<input type="text" id="resText" name="resText" class="form-control input-sm"></td>
			</tr>
			<tr class="pp">
				<td class="item_title"><span class="impt">*</span>回复图片:</td>
				<td class="item_input">
				<input type="file" id="resPhoto" name="resPhoto"></td>
			</tr>
			<tr class="pp">
				<td class="item_title"><span class="impt">*</span>描述:</td>
				<td class="item_input">
				<input type="text" id="describe" name="describe" class="form-control input-sm"></td>
			</tr>
			<tr class="pp">
				<td class="item_title"><span class="impt">*</span>标题:</td>
				<td class="item_input">
				<input type="text" id="title" name="title" class="form-control input-sm"></td>
			</tr>
			<tr class="pp">
				<td class="item_title"><span class="impt">*</span>跳转的url:</td>
				<td class="item_input">
				<input type="text" id="url" name="url" class="form-control input-sm"></td>
			</tr>
			<tr>
				<td class="item_title"></td>
				<td class="item_input">
					<button type="button" class="btn btn-primary btn-sm"style="width:150px;" onclick="sub()">保存</button>
				</td>
			</tr>
		</table>
		</div>
	</body>
</html>
<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="script/bootstrap.min.js" type="text/javascript"></script> 
<script src="js/admin.js" type="text/javascript"></script>
<script src="js/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function(){
	for(var i=0;i<$(".pp").size();i++){
		$(".pp")[i].style.visibility="hidden";
		//$(".pp")[i].style.display="none";
		}
	});
	
	function change(){
		var t=$('input:radio[name=type]:checked').val();
		if(t=='news'){
			for(var i=0;i<$(".pp").size();i++){
				$(".pp")[i].style.visibility="visible";
				//$(".pp")[i].style.display="inline";
			}
			for(var i=0;i<$(".tt").size();i++){
				$(".tt")[i].style.visibility="hidden";
			}
		}
		if(t=='text'){
			for(var i=0;i<$(".pp").size();i++){
				$(".pp")[i].style.visibility="hidden";
			}
			for(var i=0;i<$(".tt").size();i++){
				$(".tt")[i].style.visibility="visible";
			}
		}
	}
	
	function sub(){
		var ruleName=$("#ruleName").val();
		var keywords=$("#keywords").val();
		var resText=$("#resText").val();
		var describe=$("#describe").val();
		var type=$('input:radio[name=type]:checked').val();
		var title=$("#title").val();
		var url=$("#url").val();
		
		if(!ruleName){
			alert("规则名不能为空；");
			return;
		}
		if(!keywords){
			alert("关键词不能为空；");
			return;
		}
		if(!resText&&$('input:radio[name=type]:checked').val()=='text'){
			alert("回复内容不能为空；");
			return;
		}
		var response={
			"ruleName":ruleName,
			"keywords":keywords,
			"resText":resText,
			"type":type,
			"describe":describe,
			"title":title,
			"url":url
		}
		$.ajaxFileUpload({
		type:"post",	
		//处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
		data:response,
		url:"admin/getadd_response",
		secureuri:false,                       //是否启用安全提交,默认为false 
		fileElementId:'resPhoto',           //文件选择框的id属性
		dataType:'text',                       //服务器返回的格式,可以是json或xml等
		success:function(data, status){        //服务器响应成功时的处理函数
			if(data=="0"){
				alert("添加成功");
				location.replace(location.href);
			}else if(data=="2"){
				alert("添加失败");
			}else if(data=="1"){
				alert("该规则名已存在");
			}
		}
	});
}
</script>


