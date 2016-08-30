//全选
$(document).on("click","#checkall",function(){
	if($(this).is(":checked")){
		$("input[name='checklist']").prop("checked",true);
	}else{
		$("input[name='checklist']").prop("checked",false);
	}
});
//批量删除管理员组
function delall_admingroup(){
	var inc=$("input[name='checklist']:checked");//选中的input
	if(inc.length==0){
			jAlert("您没有选中任何项，请选中后继续操作。");
			return false;
	}else{
		for(var i=0;i<inc.length;i++){
			console.log($(inc[i]).val());//勾选的人员ID
		}
		jConfirm("要删除"+inc.length+"组管理员组吗？","提示框？",function(r) {
			   if(r){
			    	jAlert('删除成功！',"提示框",function(){
						location.reload();
					});
			   }			   
		});
	}
}
//删除管理员组
function deladmingroup(id){
	jConfirm("确定要删除？","提示框？",function(r) {
		   if(r){
		    	jAlert('删除成功！',"提示框",function(){
					location.reload();
				});
		   }			   
	});
}
//批量删除成员
function delall_admin(){
	var inc=$("input[name='checklist']:checked");//选中的input
	if(inc.length==0){
			jAlert("您没有选中任何项，请选中后继续操作。");
			return false;
	}else{
		for(var i=0;i<inc.length;i++){
			console.log($(inc[i]).val());//勾选的人员ID
		}
		jConfirm("要删除"+inc.length+"位成员吗？","提示框？",function(r) {
			   if(r){
			    	jAlert('删除成功！',"提示框",function(){
						location.reload();
					});
			   }			   
		});
	}
}
//删除成员
function deladmin(id){
	jConfirm("确定要删除？","提示框？",function(r) {
		   if(r){
		    	jAlert('删除成功！',"提示框",function(){
					location.reload();
				});
		   }			   
	});
}
//全选权限
function check_is_all(obj){
	if($(obj).is(":checked")){
		$(obj).parent().parent().next().find("input[name='checklist']").prop("checked",true);
	}else{
		$(obj).parent().parent().next().find("input[name='checklist']").prop("checked",false);
	}
}
