//批量删除会员
function delall_user(){
	var inc=$("input[name='checklist']:checked");//选中的input
	if(inc.length==0){
			jAlert("您没有选中任何项，请选中后继续操作。");
			return false;
	}else{
		for(var i=0;i<inc.length;i++){
			console.log($(inc[i]).val());//勾选的人员ID
		}
		jConfirm("要删除"+inc.length+"位会员吗？","提示框？",function(r) {
			   if(r){
			    	jAlert('删除成功！',"提示框",function(){
						location.reload();
					});
			   }			   
		});
	}
}
//批量删除简历
function delall_jianli(){
	var inc=$("input[name='checklist']:checked");//选中的input
	if(inc.length==0){
			jAlert("您没有选中任何项，请选中后继续操作。");
			return false;
	}else{
		for(var i=0;i<inc.length;i++){
			console.log($(inc[i]).val());//勾选的人员ID
		}
		jConfirm("要删除"+inc.length+"条简历吗？","提示框？",function(r) {
			   if(r){
			    	jAlert('删除成功！',"提示框",function(){
						location.reload();
					});
			   }			   
		});
	}
}
//删除会员
function deluser(id){
	jConfirm("确定要删除？","提示框？",function(r) {
			   if(r){
			    	jAlert('删除成功！',"提示框",function(){
						location.reload();
					});
			   }			   
		});
}

//删除简历
function deljianli(id){
	jConfirm("确定要删除？","提示框？",function(r) {
			   if(r){
			    	jAlert('删除成功！',"提示框",function(){
						location.reload();
					});
			   }			   
		});
}
//全选
$(document).on("click","#checkall",function(){
	if($(this).is(":checked")){
		$("input[name='checklist']").prop("checked",true);
	}else{
		$("input[name='checklist']").prop("checked",false);
	}
});