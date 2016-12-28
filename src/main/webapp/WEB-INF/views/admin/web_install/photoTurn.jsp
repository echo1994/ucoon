<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//System.out.println(basePath);http://localhost:8080/ucoon/
pageContext.setAttribute("basePath",basePath);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'photoTurn.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
		<link rel="stylesheet" href="css/cj.css" type="text/css" />
		<link rel="stylesheet" href="css/list.css" type="text/css" />
  </head>
  
  <body>
  <div class="listbox">
			<div class="pagetitle">轮播图管理</div>
			<div class="buttonz">
				<a href="admin/add_phototurn" class="btn btn-success btn-sm">添加轮播图</a>
				<a href="javascript:;" class="btn btn-danger btn-sm" onclick="delall_photogroup();">批量删除</a>
			</div>
			<table class="table table-bordered table-condensed">
				<tr>
					<th width="1%"><input type="checkbox" id="checkall"></th>
					<th width="4%">序号</th>
					<th width="15%">图片名称</th>
					<th width="40%">跳转路径</th>
					<th width="10%">跳转状态</th>					
					<th width="10%">图片轮播位置</th>
					<th width="20%">操作</th>
				</tr>
				<c:forEach items="${photoList}" var="photo" varStatus="Status">
					
					<tr style="text-align:center" opid=${photo.photoId}>
					<td class="text-center"><input type="checkbox" name="checklist" pname="${photo.photoUrl}" value="${photo.photoId}"></td>
					<td width="5%">${Status.index+1}</td>
					<td width="15%">${photo.photoName}</td>
					<td width="45%">${photo.photoGoUrl}</td>
					<td width="10%">
									<c:if test="${photo.isGo==1}">可跳转</c:if>
									<c:if test="${photo.isGo==0}">不可跳转</c:if>
					</td>					
					<td width="10%"><c:out value="第${photo.local}张"></c:out></td>
					<td width="30%">
					<a href="admin/edit_phototurn?photoId=${photo.photoId}" style="display:inline-block;width:50%;text-align:center;">编辑</a><a href="javascript:void(0);" pname="${photo.photoUrl}" opid="${photo.photoId}" onclick="deletePhoto(this);" style="display:inline-block;width:50%;text-align:center;">删除</a>
					</td>
				</tr>	
				</c:forEach>			
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
	
		<!--<img src="admin/reqmImage/2/JPG" style="width:200px;height:200px"> -->
  </body>
  	<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script>
 	<script src="script/jquery.alerts.js" type="text/javascript"></script>
	<script src="js/tongyong.js" type="text/javascript"></script>
	<script src="js/admin.js" type="text/javascript"></script>
	<script type="text/javascript">
		function delall_photogroup(){
			if(confirm("确定要删除这些数据？")){
				var photoIds = [];
				var pnames = [];
				$("input[name='checklist']:checked").each(function(){       
	      				photoIds.push($.trim($(this).val()));    // 将选中的值添加到数组priv_ids中    $.trim 表明去掉首尾字符串
	      				pnames.push($(this).attr("pname"));
	  			});
	  			alert(pnames)
				$.ajax({
					url:"admin/deletephotogroup",
					data:{"photoIds":photoIds,"pnames":pnames},
					success:function(result){
						window.location.href="admin/adv_photo";
					}
				}); 
			}else return false;
			
		}
		function deletePhoto(obj){
			var  aid=obj.getAttribute("opid");
			var  pname=obj.getAttribute("pname");
			if(window.confirm('确定删除？'))
				deleteOne(aid,pname);
			return false;
			}
			 function deleteOne(opid,pname){
			/*  
			这样则是不行的，传不过去，封装好的可以穿json对象参数
			var aa = JSON.stringify({"opid":opid*1});
			 alert(aa) */
				$.ajax({
				url:"admin/photoDelete",
				data:{"opid":opid*1,"pname":pname},
				success:function(result){
					 $("table").find("tr").each(function(){
						if($(this).attr("opid")==opid)
							$(this).remove();
					}) 
				}
			}); 
			} 
	
	</script>
</html>
