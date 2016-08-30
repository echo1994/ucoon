$(function() {
	//上传logo
	if($("#logoimg").length>0){
		up_img({
			list:"#logoList",	//列表框
			pick:"#logoPicker",	//上传按钮
			s_url:"",			//服务器接收端URL
			img_Num:1			//图片数量
		});
	}
	//上传营业执照
	if($("#yingyeimg").length>0){
		up_img({
			list:"#yingyeList",	//列表框
			pick:"#yingyePicker",	//上传按钮
			s_url:"",			//服务器接收端URL
			img_Num:1			//图片数量
		});
	}
	//上传环境照
	if($("#huanjingimg").length>0){
		up_img({
			list:"#huanjingList",	//列表框
			pick:"#huanjingPicker",	//上传按钮
			s_url:"",			//服务器接收端URL
			img_Num:5			//图片数量
		});
	}
	$(".selectlb").click(function(){
		$('#jobid_box').modal('show');
		zw_box.zwShow();
	})
});


//兼职类别选择
var zw_array=[{"Id":1,"PositionName":"促销/导购","IsEnabled":true,"Sort":1,"ReMark":null},{"Id":2,"PositionName":"传单派发","IsEnabled":true,"Sort":2,"ReMark":null},{"Id":3,"PositionName":"钟点工","IsEnabled":true,"Sort":3,"ReMark":null},{"Id":4,"PositionName":"服务员","IsEnabled":true,"Sort":4,"ReMark":null},{"Id":5,"PositionName":"生活配送员","IsEnabled":true,"Sort":5,"ReMark":null},{"Id":6,"PositionName":"护工","IsEnabled":true,"Sort":6,"ReMark":null},{"Id":7,"PositionName":"家教","IsEnabled":true,"Sort":7,"ReMark":null},{"Id":8,"PositionName":"软件开发/编程","IsEnabled":true,"Sort":8,"ReMark":null},{"Id":9,"PositionName":"美工/平面设计","IsEnabled":true,"Sort":9,"ReMark":null}];
var zw_box={
	//加载所有职位
	zwShow : function(){
		var checkH="";//所有职位html
		for(var i in zw_array){
			//console.log(zw_array);
			checkH+='<li><a href="javascript:;" onclick="zw_box.selectjobid('+zw_array[i].Id+''+",'"+ zw_array[i].PositionName +"'"+')">'+ zw_array[i].PositionName +'</a></li>';
		}
		$("#lbbox ul").html(checkH);
	},
	selectjobid:function(id,val){
		$("#jobid").val(id);
		$(".selectlb").html(''+ val +'<span class="caret"></span>');
		$('#jobid_box').modal('hide');
	}
}
