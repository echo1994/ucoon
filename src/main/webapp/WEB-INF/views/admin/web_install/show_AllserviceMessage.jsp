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
			<div class="pagetitle">单发客服管理</div>
			<div class="buttonz">
				<a href="javascript:void(0);" onclick="sendAll()" class="btn btn-success btn-sm">批量发送（选中）</a>
				<a href="javascript:void(0);" class="btn btn-danger btn-sm" onclick="delall_photogroup();">批量删除</a>
			</div>
			<table class="table table-bordered table-condensed">
				<tr>
					<th width="1%"><input type="checkbox" id="checkall"></th>
					<th width="4%">序号</th>
					<th width="8%">openId</th>
					<th width="8%">手机号</th>
					<th width="34%">消息内容</th>
					<th width="8%">客服对象</th>					
					<th width="17%">发送时间</th>
					<th width="20%">操作</th>
				</tr>
				<c:forEach items="${serviceMessages}" var="message" varStatus="Status"> 
					<tr style="text-align:center" toUser="${message.openId}" opid="${message.serviceMessageId}">
					<td class="text-center" width="1%"><input type="checkbox" name="checklist" tel="${message.phone}" value="${message.serviceMessageId}"></td>
					<td width="4%">${Status.index+1}</td>
					<td width="8%">${message.openId}</td>
					<td width="8%">${message.phone}</td>
					<td width="34%">${message.serviceMessageContent}</td>	
					<th width="8%">${message.serverName}</th>				
					<td width="17%">${message.sendTime}</td>
					<td width="20%">
						<a href="admin/edit_oneserviceMessageMapping?openid=${message.openId}">继续发送</a><!-- style="display:inline-block;width:50%;text-align:center;" -->
						<a href="javascript:void(0);" opid="${message.serviceMessageId}" onclick="deletemessage(this);" >删除</a>
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
		function sendAll(){
			var tel = "";
			$("input[name='checklist']:checked").each(function(){   	 
	      			tel+=($.trim($(this).attr("tel")))+",";    // 将选中的值添加到数组priv_ids中    $.trim 表明去掉首尾字符串
	      			
	  		}); 
	  		tel=tel.substring(0,tel.length-1);
				window.location.href="admin/edit_oneserviceMessageMapping?telephone="+tel;
		}
		
		function delall_photogroup(){
			if(confirm("确定要删除这些数据？")){
				var servicemessageIds = [];
				$("input[name='checklist']:checked").each(function(){   
	      				servicemessageIds.push($.trim($(this).val()));    // 将选中的值添加到数组priv_ids中    $.trim 表明去掉首尾字符串
	  			}); 
				$.ajax({
					url:"admin/delete_moresingserviceMessageMapping",
					data:{"servicemessageIds":servicemessageIds},
					success:function(result){
						if(result=="success"){
							for(var a=0;a<servicemessageIds.length;a++){
				  				$("table").find("tr").each(function(){
								if($(this).attr("opid")==servicemessageIds[a])
									$(this).remove();
								})
				  			} 
						}
						else alert("删除失败");
					}
				}); 
			}else return false;
			
		}
		
		//删除单条数据
		function deletemessage(obj){
			var  aid=obj.getAttribute("opid");
			if(window.confirm('确定删除？'))
				deleteOne(aid);
			return false;
			}
			 function deleteOne(opid){
				$.ajax({
				url:"admin/delete_singserviceMessageMapping",
				data:{"servicemessageId":opid*1},
				success:function(result){
					if(result=="success"){
						$("table").find("tr").each(function(){
							if($(this).attr("opid")==opid)
								$(this).remove();
						})
					}else{
						alert("删除失败");
					}
				}
			}); 
		} 
	
	</script>
</html>
