
(function() {
	var index = 1;
	var size = null;
	var imageIndexIdNum = 0;
	var starIndex = 0;
	var feedback = {
		question: document.getElementById('question'), 
		contact: document.getElementById('contact'), 
		imageList: document.getElementById('image-list'),
		submitBtn: document.getElementById('submit')
	};
	var url = 'https://service.dcloud.net.cn/feedback';
	feedback.files = [];
	feedback.uploader = null;  
	feedback.deviceInfo = null; 
	mui.plusReady(function() {
		//设备信息，无需修改
		feedback.deviceInfo = {
			appid: plus.runtime.appid, 
			imei: plus.device.imei, //设备标识
			images: feedback.files, //图片文件
			p: mui.os.android ? 'a' : 'i', //平台类型，i表示iOS平台，a表示Android平台。
			md: plus.device.model, //设备型号
			app_version: plus.runtime.version,
			plus_version: plus.runtime.innerVersion, //基座版本号
			os:  mui.os.version,
			net: ''+plus.networkinfo.getCurrentType()
		}
	});
	/**
	 *提交成功之后，恢复表单项 
	 */
	feedback.clearForm = function() {
		feedback.question.value = '';
		feedback.contact.value = '';
		feedback.imageList.innerHTML = '';
		feedback.newPlaceholder();
		feedback.files = [];
		index = 0;
		size = 0;
		imageIndexIdNum = 0;
		starIndex = 0;
		//清除所有星标
		mui('.icons i').each(function (index,element) {
			if (element.classList.contains('mui-icon-star-filled')) {
				element.classList.add('mui-icon-star')
	  			element.classList.remove('mui-icon-star-filled')
			}
		})
	};
	feedback.getFileInputArray = function() {
		return [].slice.call(feedback.imageList.querySelectorAll('.file'));
	};
	feedback.addFile = function(path) {
		feedback.files.push({name:"images"+index,path:path});
		index++;
	};
	
	feedback.submitBtn.addEventListener('tap', function(event) {
		if (feedback.question.value == '' ||
			(feedback.contact.value != '' &&
				feedback.contact.value.search(/^(\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+)|([1-9]\d{4,9})$/) != 0)) {
			return mui.toast('信息填写不符合规范');
		}
		if (feedback.question.value.length > 200 || feedback.contact.value.length > 200) {
			return mui.toast('信息超长,请重新填写~')
		}
		//判断网络连接
		if(plus.networkinfo.getCurrentType()==plus.networkinfo.CONNECTION_NONE){
			return mui.toast("连接网络失败，请稍后再试");
		}
		feedback.send(mui.extend({}, feedback.deviceInfo, {
			content: feedback.question.value,
			contact: feedback.contact.value,
			images: feedback.files,
			score:''+starIndex
		})) 
	}, false)
	feedback.send = function(content) {
		feedback.uploader = plus.uploader.createUpload(url, {
			method: 'POST'
		}, function(upload, status) {
//			plus.nativeUI.closeWaiting()
			console.log("upload cb:"+upload.responseText);
			if(status==200){
				var data = JSON.parse(upload.responseText);
				//上传成功，重置表单
				if (data.ret === 0 && data.desc === 'Success') {
//					mui.toast('反馈成功~')
					console.log("upload success");
//					feedback.clearForm();
				}
			}else{
				console.log("upload fail");
			}
			
		});
		//添加上传数据
		mui.each(content, function(index, element) {
			if (index !== 'images') {
				console.log("addData:"+index+","+element);
//				console.log(index);
				feedback.uploader.addData(index, element)
			} 
		});
		//添加上传文件
		mui.each(feedback.files, function(index, element) {
			var f = feedback.files[index];
			console.log("addFile:"+JSON.stringify(f));
			feedback.uploader.addFile(f.path, {
				key: f.name
			});
		});
		//开始上传任务
		feedback.uploader.start();
		mui.alert("感谢反馈，点击确定关闭","问题反馈","确定",function () {
			feedback.clearForm();
			mui.back();
		});
//		plus.nativeUI.showWaiting();
	};
	
	 //应用评分
	 mui('.icons').on('tap','i',function(){
	  	var index = parseInt(this.getAttribute("data-index"));
	  	var parent = this.parentNode;
	  	var children = parent.children;
	  	if(this.classList.contains("mui-icon-star")){
	  		for(var i=0;i<index;i++){
  				children[i].classList.remove('mui-icon-star');
  				children[i].classList.add('mui-icon-star-filled');
	  		}
	  	}else{
	  		for (var i = index; i < 5; i++) {
	  			children[i].classList.add('mui-icon-star')
	  			children[i].classList.remove('mui-icon-star-filled')
	  		}
	  	}
	  	starIndex = index;
  });
  	//选择快捷输入
	mui('.mui-popover').on('tap','li',function(e){
	  document.getElementById("question").value = document.getElementById("question").value + this.children[0].innerHTML;
	  mui('.mui-popover').popover('toggle')
	}) 
})();