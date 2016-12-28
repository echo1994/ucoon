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
			<div class="pagetitle">反馈信息管理</div>
			<table class="table table-bordered table-condensed">
			  <thead>	
				<tr>
					<th width="5%">ID</th>
					<th width="10%">微信用户Id</th>
					<th width="10%">任务Id</th>					
					<th width="30%">反馈内容</th>
					<th width="20%">反馈时间</th>
					<th width="10%">状态</th>
					<th width="20%">操作</th>
				</tr>
			  </thead>
			  <tbody id="tb1">
			  		<c:forEach items="${feedback}" var="a">
			  	<tr>
			  		<td class="text-center">${a.feedback_id}</td>
			  		<td class="text-center">${a.open_id}</td>
			  		<td class="text-center">${a.activity_id}</td>
			  		<td class="text-center">${a.feedback_content}</td>
			  		<td class="text-center">${a.feedback_time}</td>
			  		<c:if test="${a.status==0}">
			  		<td class="text-center">未阅</td>
			  		</c:if>
			  		<c:if test="${a.status==1}">
			  		<td class="text-center">已阅</td>
			  		</c:if>
			  		<c:if test="${a.status==0}">
			  		<td class="text-center">
			  		<a href="javascript:;" onclick="return confime(this);">确认已阅览</a>
			  		</td>
			  		</c:if>
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
function confime(obj){
	if(window.XMLHttpRequest){
			xmlhttp=new XMLHttpRequest();
		}else{
			xmlhttp=new ActiveXObject('Microsoft.XMLHTTP');
		}
		
	var tr=obj.parentElement.parentElement;
	var url="<%=path%>/admin/cFeedback?feedbackId="+escape(tr.cells[0].innerText);
		xmlhttp.open("get",url);
		xmlhttp.onreadystatechange=function(){
			if(xmlhttp.readyState==4){
			var bool=xmlhttp.responseText;
			if(bool=="true"){
			alert("确定浏览成功");
			tr.cells[5].innerHTML="已阅";
			tr.cells[6].innerHTML="";
		}else{
			alert("确定浏览失败");
		}
	  }
		};
		xmlhttp.send(null);
		return false;
}
</script>