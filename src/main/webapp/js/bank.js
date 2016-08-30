//删除企业银行卡
function del_cobank(ID){
	jConfirm("确定要删除？","提示框？",function(r) {
			   if(r){
			    	jAlert('删除成功！',"提示框",function(){
						location.reload();
					});
			   }			   
		});
}
//删除会员银行卡
function del_userbank(){
	jConfirm("确定要删除？","提示框？",function(r) {
			   if(r){
			    	jAlert('删除成功！',"提示框",function(){
						location.reload();
					});
			   }			   
		});
}
