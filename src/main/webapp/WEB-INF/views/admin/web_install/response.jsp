<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			<div class="pagetitle">回复列表</div>
			<div class="buttonz">
				<a href="admin/add_response" class="btn btn-success btn-sm">添加规则</a>
				<a href="javascript:;" class="btn btn-danger btn-sm" onclick="delall_admingroup();">批量删除</a>
			<table class="table table-bordered table-condensed">
			  <thead>	
				<tr>
					<th width="1%"><input type="checkbox" id="checkall" onclick="checkall(this)"></th>
					<th width="5%">ID</th>
					<th width="10%">规则名</th>
					<th width="10%">关键词</th>					
					<th width="10%">回复</th>
					<th width="10%">图片url</th>
					<th width="10%">类型</th>
					<th width="10%">描述</th>
					<th width="10%">跳转链接</th>
					<th width="10%">标题</th>
					<th width="10%">操作</th>
				</tr>
			  </thead>
			  <tbody id="tb1">
			  		<c:forEach items="${responses}" var="a">
			  	<tr>
			  		<td class="text-center"><input type="checkbox" name="a" value="${a.responseId}"></td>
			  		<td class="text-center">${a.responseId}</td>
			  		<td class="text-center">${a.ruleName}</td>
			  		<td class="text-center">${a.keywords }</td>
			  		<td class="text-center">${a.resText }</td>
			  		<td class="text-center">${a.photo }</td>
			  		<td class="text-center">${a.type }</td>
			  		<td class="text-center">${a.describe }</td>
			  		<td class="text-center">${a.url }</td>
			  		<td class="text-center">${a.title }</td>
			  		<td>
			  		<a href="javascript:;" onclick="return deladmingroup(this);">删除</a>
			  		</td>
			  	</tr>
			  		</c:forEach>
			  	</tr>
			  </tbody>
			</table>
			</div>
		</div>
	</body>
</html>
<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="script/jquery.alerts.js" type="text/javascript"></script>
<script src="js/tongyong.js" type="text/javascript"></script>
<script src="js/admin.js" type="text/javascript"></script>
<script type="text/javascript">
	var xmlhttp = new MyXmlHttp();
function deladmingroup(obj){
	if(confirm("您确定删除？"))confimdel(obj);
    return false; 
}
function confimdel(obj){
	if(window.XMLHttpRequest){
			xmlhttp=new XMLHttpRequest();
		}else{
			xmlhttp=new ActiveXObject('Microsoft.XMLHTTP');
		}
		
	var tr=obj.parentElement.parentElement;
	var url="<%=path%>/admin/delresponse?id="+escape(tr.cells[1].innerText);
		xmlhttp.open("post",url);
		xmlhttp.onreadystatechange=function(){
			if(xmlhttp.readyState==4){
			var bool=xmlhttp.responseText;
			if(bool=="true"){
			tr.parentElement.parentElement.deleteRow(tr.rowIndex);
			alert("删除成功");
		}else{
			alert("删除失败");
		}
	  }
		};
		xmlhttp.send(null);
		return false;
}
function delall_admingroup(){
	if(confirm("您确定删除？")) confimdelAll();
		return false;
}
function confimdelAll(){
	var a=$("input[name='a']:checked").serialize();
	$.ajax({
		url:"admin/delAllresponse",
		type:"post",
		data:a,
		success:function(data){
			if(data=="true"){
				alert("删除成功");
				window.location.reload();
			}else{
				alert("删除失败");
			}
		}
	});
}

function checkall(obj){
	var chk=obj.checked;
	var arr=document.getElementsByTagName("INPUT");
	for(var i=0,j=arr.length;i<j;i++){
		if(arr[i].type=="checkbox"){
			arr[i].checked = chk;
		}
	}
}
</script>