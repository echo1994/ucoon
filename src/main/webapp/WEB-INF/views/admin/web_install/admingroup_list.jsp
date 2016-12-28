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
			<div class="pagetitle">管理员分组管理</div>
			<div class="buttonz">
				<a href="admin/add_admingroup_list" class="btn btn-success btn-sm">增加分组</a>
				<a href="javascript:;" class="btn btn-danger btn-sm" onclick="delall_admingroup();">批量删除</a>
			</div>
			<table class="table table-bordered table-condensed">
			  <thead>	
				<tr>
					<th width="1%"><input type="checkbox" id="checkall" onclick="checkall(this)"></th>
					<th width="5%">ID</th>
					<th width="20%">名称</th>
					<th width="64%">权限</th>					
					<th width="10%">操作</th>
				</tr>
			  </thead>
			  <tbody id="tb1">
			  		<c:forEach items="${admingroup}" var="a">
			  	<tr>
			  		<td class="text-center"><input type="checkbox" name="a" value="${a.groupId}"></td>
			  		<td class="text-center">${a.groupId}</td>
			  		<td class="text-center">${a.groupName}</td>
			  		<td class="text-center">${a.groupRights }</td>
			  		<td>
			  		<a href="admin/editadmingroup?groupname=${a.groupName }">编辑</a>
			  		<a href="javascript:;" onclick="return deladmingroup(this);">删除</a>
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
// $(document).ready(function(){  
// 	var str="";
// 	$.ajax({
// 		type:"post",
// 		dataType:"json",
// 		url:"admin/getadmingroup_list",
// 		success:function(data){
// 			 for(var i=0;i<data.length;i++){
// 				var s=data[i];
				
// 			str+=(
// 					'<tr><td class="text-center"><input type="checkbox" name="a" value="'+s.groupId+'"></td><td class="text-center">'
// 					+s.groupId+'</td><td>'+s.groupName+'</td><td class="text-center">'
// 				);
				
// 				var params={
// 				"groupId":s.groupId,
// 				}
// 				$.ajax({
// 					type:"post",
// 					url:"admin/getpemission",
// 					data:params,
// 					success:function(data1){
// 						for(var i=0;i<data1.length;i++){
// 							str+=" ";
// 							str+=data1[i];
// 						}
// 			str+=('</td><td><a href="admin/editadmingroup?groupname='+s.groupName+'">编辑</a><a href="javascript:;" onclick="return deladmingroup(this);">删除</a></td></tr>');
// 					}
// 				});
// 			} 
// 		$("#tb1").append(str);
// 		}
// 	});
	
// });
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
	var url="<%=path%>/admin/deladmingroup_list?id="+escape(tr.cells[1].innerText);
		xmlhttp.open("get",url);
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
		url:"admin/delAlladmingroup_list",
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