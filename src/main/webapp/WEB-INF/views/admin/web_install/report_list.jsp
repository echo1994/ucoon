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
			<div class="pagetitle">举报信息管理</div>
			<table class="table table-bordered table-condensed">
			  <thead>	
				<tr>
					<th width="5%">ID</th>
					<th width="10%">任务Id</th>	
					<th width="10%">微信用户Id</th>				
					<th width="30%">内容</th>
					<th width="20%">反馈时间</th>
					<th width="10%">状态</th>
					<th width="20%">操作</th>
				</tr>
			  </thead>
			  <tbody id="tb1">
			  		<c:forEach items="${report}" var="a">
			  	<tr>
			  		<td class="text-center">${a.reportId}</td>
			  		<td class="text-center">${a.missionId}</td>
			  		<td class="text-center">${a.userId}</td>
			  		<td class="text-center">${a.content}</td>
			  		<td class="text-center">${a.time}</td>
			  		<td class="text-center">${a.status}</td>
			  		<td class="text-center">
			  		<c:if test="${a.status!='已处理'}">
			  		<a href="javascript:;" onclick="return delUser(this);">删除用户</a>
			  		<select onchange="return xiajia(this)">
			  			<option>任务操作</option>
			  			<option value="0">任务下架</option>
			  		</select>
			  		<!-- <a href="javascript:;" onclick="return delMission(this);">删除任务</a> -->
			  		</c:if>
			  		</td>
			  	</tr>
			  		</c:forEach>
			  	</tr>
			  </tbody>
			</table>
			<div class="paging">
			  <ul class="pagination">
			    <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">‹</span></a></li>
			    <li class="active"><a href="#">1</a></li>
			    <li><a href="#">2</a></li>								    
			    <li><a href="#" aria-label="Next"><span aria-hidden="true">›</span></a></li>
			    <li><span>1/1022 页</span></li>
			    <li><input type="text" class="form-control"></li>
				<li><a href="javascript:;">Go</a></li>
				<li><span>30655 条记录</span></li>
			  </ul>
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
function delUser(obj){
	if(confirm("您确定删除？"))confimdelUser(obj);
    return false; 
}
function confimdelUser(obj){
	if(window.XMLHttpRequest){
			xmlhttp=new XMLHttpRequest();
		}else{
			xmlhttp=new ActiveXObject('Microsoft.XMLHTTP');
		}
		
	var tr=obj.parentElement.parentElement;
	var url="<%=path%>/admin/delUser?userId="+escape(tr.cells[2].innerText)+
	"&reportId="+escape(tr.cells[0].innerText);
		xmlhttp.open("get",url);
		xmlhttp.onreadystatechange=function(){
			if(xmlhttp.readyState==4){
			var bool=xmlhttp.responseText;
			if(bool=="true"){
			window.location.href="admin/report_list";
			alert("删除成功");
		}else if(bool=="miss"){
			alert("该用户已删除");
		}
		else{
			alert("删除失败");
		}
	  }
		};
		xmlhttp.send(null);
		return false;
}
function delMission(obj){
	if(confirm("您确定删除？")) confimdelMission(obj);
		return false;
}
function confimdelMission(obj){
	if(window.XMLHttpRequest){
			xmlhttp=new XMLHttpRequest();
		}else{
			xmlhttp=new ActiveXObject('Microsoft.XMLHTTP');
		}
		
	var tr=obj.parentElement.parentElement;
	var url="<%=path%>/admin/delMission?missionId="+escape(tr.cells[1].innerText)+
	"&reportId="+escape(tr.cells[0].innerText);
		xmlhttp.open("get",url);
		xmlhttp.onreadystatechange=function(){
			if(xmlhttp.readyState==4){
			var bool=xmlhttp.responseText;
			if(bool=="true"){
			alert("删除成功");
			tr.cells[5].innerHTML="已处理";
		}else if(bool=="miss"){
			alert("该任务已删除");
		}
		else{
			alert("删除失败");
		}
	  }
		};
		xmlhttp.send(null);
		return false;
}
function xiajia(obj){
	var sign=$('select option:selected').val();
	if(sign==0){
		if(confirm("确认下架")) confirmxiajia(obj);
		return false;
	}
	alert(sign);
}
function confirmxiajia(obj){
	if(window.XMLHttpRequest){
			xmlhttp=new XMLHttpRequest();
		}else{
			xmlhttp=new ActiveXObject('Microsoft.XMLHTTP');
		}
		
	var tr=obj.parentElement.parentElement;
	var url="<%=path%>/admin/xiaMission?missionId="+escape(tr.cells[1].innerText)+
	"&reportId="+escape(tr.cells[0].innerText);
	alert(url);
		xmlhttp.open("get",url);
		xmlhttp.onreadystatechange=function(){
			if(xmlhttp.readyState==4){
			var bool=xmlhttp.responseText;
			if(bool=="true"){
			alert("下架成功");
			tr.cells[5].innerHTML="已处理";
			tr.cells[6].style.display="none";
		}else if(bool=="miss"){
			alert("该任务已下架");
		}
		else{
			alert("下架失败");
		}
	  }
		};
		xmlhttp.send(null);
		return false;
}
</script>