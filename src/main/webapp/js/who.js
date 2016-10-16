/* 多图预览 */
filecount = 0;
$(document)
		.ready(
				function(e) {
					
					// 判断浏览器是否有FileReader接口
					if (typeof FileReader == 'undefined') {
						$("#destination")
								.css({
									'background' : 'none'
								})
								.html(
										'亲,您的浏览器还不支持HTML5的FileReader接口,无法使用图片本地预览,请更新浏览器获得最好体验');
						// 如果浏览器是ie
						if ($.browser.msie === true) {
							// ie6直接用file input的value值本地预览
							if ($.browser.version == 6) {
								$("#imgUpload")
										.change(
												function(event) {
													// ie6下怎么做图片格式判断?
													var src = event.target.value;
													// var src =
													// document.selection.createRange().text;
													// //选中后 selection对象就产生了
													// 这个对象只适合ie
													var img = '<img src="'
															+ src
															+ '" width="200px" height="200px" />';
													$("#destination").empty()
															.append(img);
												});
							}
							// ie7,8使用滤镜本地预览
							else if ($.browser.version == 7
									|| $.browser.version == 8) {
								$("#imgUpload")
										.change(
												function(event) {
													$(event.target).select();
													var src = document.selection
															.createRange().text;
													var dom = document
															.getElementById('destination');
													// 使用滤镜 成功率高
													dom.filters
															.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
													dom.innerHTML = '';
													// 使用和ie6相同的方式 设置src为绝对路径的方式
													// 有些图片无法显示 效果没有使用滤镜好
													/*
													 * var img = '<img
													 * src="'+src+'"
													 * width="200px"
													 * height="200px" />';
													 * $("#destination").empty().append(img);
													 */
												});
							}
						}
						// 如果是不支持FileReader接口的低版本firefox 可以用getAsDataURL接口
						else if ($.browser.mozilla === true) {
							$("#imgUpload")
									.change(
											function(event) {
												// firefox2.0没有event.target.files这个属性
												// 就像ie6那样使用value值
												// 但是firefox2.0不支持绝对路径嵌入图片
												// 放弃firefox2.0
												// firefox3.0开始具备event.target.files这个属性
												// 并且开始支持getAsDataURL()这个接口
												// 一直到firefox7.0结束
												// 不过以后都可以用HTML5的FileReader接口了
												if (event.target.files) {
													// console.log(event.target.files);

													for (var i = 0; i < event.target.files.length; i++) {
														var img = '<img src="'
																+ event.target.files
																		.item(i)
																		.getAsDataURL()
																+ '" width="200px" height="200px"/>';
														$("#destination")
																.empty()
																.append(img);
													}

												} else {
													// console.log(event.target.value);
													// $("#imgPreview").attr({'src':event.target.value});
												}
											});
						}
					} else {
						// 单图上传 version 2
						$("#imgUpload").change(function(e) {
							var file = e.target.files[0];
							var reader = new FileReader();
							reader.onload = function(e) {
								// displayImage($('bd'),e.target.result);
								// alert('load');
								$("#imgPreview").attr({
									'src' : e.target.result
								});
							}
							reader.readAsDataURL(file);
						});
						// 多图上传 input file控件里指定multiple属性 e.target是dom类型
						$("#imgUpload")
								.change(
										function(e) {
											if (filecount
													+ event.target.files.length > 5) {
												alert("上传图片不能超过5张");
											} else {
												for (var i = 0; i < e.target.files.length; i++) {
													var file = e.target.files
															.item(i);
													// 允许文件MIME类型
													// 也可以在input标签中指定accept属性
													// console.log(/^image\/.*$/i.test(file.type));
													if (!(/^image\/.*$/i
															.test(file.type))) {
														continue; // 不是图片
														// 就跳出这一次循环
													}

													// 实例化FileReader API
													var freader = new FileReader();
													freader.readAsDataURL(file);
													freader.onload = function(e) {
														var img = '<img src="'
																+ e.target.result
																+ '" width="200px" height="200px"/>';
														var img = '<li><img src="'
																+ e.target.result
																+ '"/></li>';
														$(".addimg")
																.append(img);
													}
												}
												filecount += e.target.files.length;
											}
										});

					}
				});
/* 多图预览 */

//参数定义
var current_city = "厦门市";//默认厦门市，在自动定位时改变，用于检索
var address_temp = "";//用于存放当前地址
var lng_temp = "";//用于存放当前经度
var lat_temp = "";//用于存放当前纬度

//百度地图API功能
function G(id) {
	return document.getElementById(id);
}
var map = new BMap.Map("l-map");
var point = new BMap.Point(118.099805,24.584511);
var marker = new BMap.Marker(point);// 创建标注
map.addOverlay(marker);             // 将标注添加到地图中
map.centerAndZoom(point, 15);
map.enableScrollWheelZoom(true);
map.enableInertialDragging();
marker.enableDragging();

map.addEventListener("click", function(e){        
	var geoc = new BMap.Geocoder();   
	var pt = e.point;
	map.panTo(pt); 
	map.clearOverlays();    //清除地图上所有覆盖物
	marker.setPosition(pt)
	map.addOverlay(marker);    //添加标注

	geoc.getLocation(pt, function(rs){
		var addComp = rs.addressComponents;

		var s = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
		G("suggestId").value = s;
		address_temp = s;
		lng_temp = pt.lng;
		lat_temp = pt.lat;
	});        
});


marker.addEventListener("dragend", function(e){        
	var geoc = new BMap.Geocoder();   
	var pt = e.point;
	map.panTo(pt); 
	map.clearOverlays();    //清除地图上所有覆盖物
	marker.setPosition(pt)
	map.addOverlay(marker);    //添加标注

	geoc.getLocation(pt, function(rs){
		var addComp = rs.addressComponents;
		var s = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
		G("suggestId").value = s;
		address_temp = s;
		lng_temp = pt.lng;
		lat_temp = pt.lat;
	});        
});




//添加定位控件
var geolocationControl = new BMap.GeolocationControl();
geolocationControl.addEventListener("locationSuccess", function(e){
	var pt = e.point;
	// 定位成功事件
	var address = '';
	address += e.addressComponent.province;
	address += e.addressComponent.city;
	address += e.addressComponent.district;
	address += e.addressComponent.street;
	address += e.addressComponent.streetNumber;
	  
	setPlace(address);
	address_temp = address;
	lng_temp = pt.lng;
	lat_temp = pt.lat;
  
});
geolocationControl.addEventListener("locationError",function(e){
	// 定位失败事件
  alert(e.message);
});
map.addControl(geolocationControl);


var ac = new BMap.Autocomplete( // 建立一个自动完成的对象
{
	"input" : "suggestId",
	"location" : map
});
ac.addEventListener("onhighlight", function(e) { // 鼠标放在下拉列表上的事件
	var str = "";
	var _value = e.fromitem.value;
	var value = "";
	if (e.fromitem.index > -1) {
		value = _value.province + _value.city + _value.district + _value.street
				+ _value.business;
	}
	str = "FromItem<br />index = " + e.fromitem.index + "<br />value = "
			+ value;

	value = "";
	if (e.toitem.index > -1) {
		_value = e.toitem.value;
		value = _value.province + _value.city + _value.district + _value.street
				+ _value.business;
	}
	str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = "
			+ value;
	G("searchResultPanel").innerHTML = str;
});


ac.addEventListener("onconfirm", function(e) { // 鼠标点击下拉列表后的事件
	
	var _value = e.item.value;

	var myValue = _value.province + _value.city + _value.district + _value.street
			+ _value.business;
	G("searchResultPanel").innerHTML = "onconfirm<br />index = " + e.item.index
			+ "<br />myValue = " + myValue;
	setPlace(myValue);
	var myGeo = new BMap.Geocoder();
	// 将地址解析结果显示在地图上,并调整地图视野
	myGeo.getPoint(myValue, function(point){
		if (point) {
			lng_temp = point.lng;
			lat_temp = point.lat;
		}else{
			alert("您选择地址没有解析到结果!");
		}
	});
	address_temp = myValue;
	
});

// G("search").addEventListener("click", function() { // 搜索按钮点击事件
// 	var _value = G("suggestId").value;
// 	if(_value == ""){
// 		alert("请输入地址");
// 		return;
// 	}
//
// 	map.clearOverlays();    //清除地图上所有覆盖物
//
// 	function myFun(){
// 		var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
//
// 		map.centerAndZoom(pp, 18);
// 		marker.setPosition(pp)
// 		map.addOverlay(marker);    //添加标注
// 		var geoc = new BMap.Geocoder();
// 		geoc.getLocation(pp, function(rs){
// 			var addComp = rs.addressComponents;
// 			G("suggestId").value = addComp.province + addComp.city + addComp.district+ addComp.street + addComp.streetNumber + "(" + _value +  ")";
// 			address_temp = addComp.province + addComp.city + addComp.district+ addComp.street + addComp.streetNumber + "(" + _value +  ")";
// 			lng_temp = pp.lng;
// 			lat_temp = pp.lat;
// 		});
// 	}
// 	var local = new BMap.LocalSearch(map, { //智能搜索
// 	  onSearchComplete: myFun
// 	});
// 	//local.search(_value);//不限制范围
//
// 	local.searchNearby(_value,current_city,20000); //限制范围 ，规范：当前城市 20千米内检索
//
// });
G("save").addEventListener("click", function() { // 搜索按钮点击事件
	
	
	if(address_temp != "" && lng_temp != "" && lat_temp != ""){
		$("#menu-btn").val(address_temp);
		$("#lng").val(lng_temp);
		$("#lat").val(lat_temp);
		toggleMenu();
	}else{
		
		alert("请选择地址！");
	}
	

	
});




/* data-picker组件 */
(function($) {
	$.init();
	var result1 = $('#result1')[0];
	var result2 = $('#result2')[0];
	var btns = $('.btn');
	btns.each(function(i, btn) {
		btn.addEventListener('tap', function() {
			var optionsJson = this.getAttribute('data-options') || '{}';
			var options = JSON.parse(optionsJson);
			var id = this.getAttribute('id');
			/*
			 * 首次显示时实例化组件 示例为了简洁，将 options 放在了按钮的 dom 上 也可以直接通过代码声明 optinos
			 * 用于实例化 DtPicker
			 */
			var picker = new $.DtPicker(options);
			picker.show(function(rs) {
				/*
				 * rs.value 拼合后的 value rs.text 拼合后的 text rs.y 年，可以通过 rs.y.vaue 和
				 * rs.y.text 获取值和文本 rs.m 月，用法同年 rs.d 日，用法同年 rs.h 时，用法同年 rs.i
				 * 分（minutes 的第二个字母），用法同年
				 */if (i == 0)
					result1.value = rs.text;
				else
					result2.value = rs.text;
				/*
				 * 返回 false 可以阻止选择框的关闭 return false;
				 */
				/*
				 * 释放组件资源，释放后将将不能再操作组件 通常情况下，不需要示放组件，new DtPicker(options)
				 * 后，可以一直使用。 当前示例，因为内容较多，如不进行资原释放，在某些设备上会较慢。 所以每次用完便立即调用 dispose
				 * 进行释放，下次用时再创建新实例。
				 */
				picker.dispose();
			});
		}, false);
	});

	var userPicker = new $.PopPicker();
	userPicker.setData([{
		value: '0.5',
		text: '0.5小时后'
	}, {
		value: '1',
		text: '1小时后'
	}, {
		value: '2',
		text: '2小时后'
	},{
		value: '3',
		text: '3小时后'
	}, {
		value: '4',
		text: '4小时后'
	}, {
		value: '5',
		text: '5小时后'
	}, {
		value: '6',
		text: '6小时后'
	}, {
		value: '7',
		text: '7小时后'
	}, {
		value: '8',
		text: '8小时后'
	}, {
		value: '9',
		text: '9小时后'
	}, {
		value: '10',
		text: '10小时后'
	}, {
		value: '11',
		text: '11小时后'
	}, {
		value: '12',
		text: '12小时后'
	}, {
		value: '13',
		text: '13小时后'
	}, {
		value: '14',
		text: '14小时后'
	}, {
		value: '15',
		text: '15小时后'
	}, {
		value: '16',
		text: '16小时后'
	}, {
		value: '17',
		text: '17小时后'
	}, {
		value: '18',
		text: '18小时后'
	}, {
		value: '19',
		text: '19小时后'
	}, {
		value: '20',
		text: '20小时后'
	}, {
		value: '21',
		text: '21小时后'
	}, {
		value: '22',
		text: '22小时后'
	}, {
		value: '23',
		text: '23小时后'
	}, {
		value: '24',
		text: '24小时后'
	}]);
	
	var showUserPickerButton = document.getElementById('showUserPicker');
//			var userResult = document.getElementById('userResult');
	showUserPickerButton.addEventListener('tap', function(event) {
		userPicker.show(function(items) {
//					showUserPickerButton.value = JSON.stringify(items[0].text);
			showUserPickerButton.value = items[0].text;
			//返回 false 可以阻止选择框的关闭
			//return false;
		});
	}, false);

})(mui);


//历史地址下拉选择
function changeF() {
	$('#menu-btn').val($("#sel").find("option:selected").val())
	$('#lng').val($("#sel").find("option:selected").attr("data-m"));
	$('#lat').val($("#sel").find("option:selected").attr("data-t"));
}

$(document).ready(
	$("#l-map").css("height",$(window).height())
)


/*
 * document.getElementById('mysend').addEventListener('tap', function() {
 * 
 * mui.openWindow({ url : "mysend.html" }); });
 */

/* mui.init */
mui.init();

var menuWrapper = document.getElementById("menu-wrapper");
var menu = document.getElementById("menu");
var menuWrapperClassList = menuWrapper.classList;
var backdrop = document.getElementById("menu-backdrop");
var info = document.getElementById("info");
var cancel = document.getElementById("cancel");

cancel.addEventListener('tap', toggleMenu);
document.getElementById("menu-btn").addEventListener('tap', toggleMenu);

//下沉菜单中的点击事件
mui('#menu').on('tap', 'a', function() {
	toggleMenu();
	info.innerHTML = '你已选择：'+this.innerHTML;
});
var busying = false;

function toggleMenu() {
	if (busying) {
		return;
	}
	busying = true;
	if (menuWrapperClassList.contains('mui-active')) {
		document.body.classList.remove('menu-open');
		menuWrapper.className = 'menu-wrapper fade-out-up animated';
		menu.className = 'menu bounce-out-up animated';
		setTimeout(function() {
			backdrop.style.opacity = 0;
			menuWrapper.classList.add('hidden');
		}, 500);
	} else {
		document.getElementById('menu-wrapper').scrollIntoView();
		document.body.classList.add('menu-open');
		menuWrapper.className = 'menu-wrapper fade-in-down animated mui-active';
		menu.className = 'menu bounce-in-down animated';
		backdrop.style.opacity = 1;
	}
	setTimeout(function() {
		busying = false;
	}, 500);
}

/* mui.init */

/* 自动定位 */
$(function() {
	/*alert(2);*/
	navigator.geolocation.getCurrentPosition(translatePoint); // 定位
});
function translatePoint(position) {
	var currentLat = position.coords.latitude;
	var currentLon = position.coords.longitude;
	var gpsPoint = new BMap.Point(currentLon, currentLat);
	BMap.Convertor.translate(gpsPoint, 0, initLocation); // 转换坐标
}
function initLocation(point) {
	/*alert(1);*/
	var gc = new BMap.Geocoder();
	var s;
	gc.getLocation(point, function(rs) {
		var addComp = rs.addressComponents;
		s = addComp.province + "" + addComp.city + "" + addComp.district + ""
				+ addComp.street + "" + addComp.streetNumber;
		current_city = addComp.city;//定位当前城市，改变默认城市
		address_temp = s;
		lng_temp = point.lng;
		lat_temp = point.lat;

		// alert(s);
		$("#suggestId").val(s);
		$("#menu-btn").val(s);
		setPlace(s);
		
	});
	
}

function setPlace(myValue){
	map.clearOverlays();    //清除地图上所有覆盖物

	function myFun(){
		var pp = local.getResults().getPoi(0).point;
		//获取第一个智能搜索的结果
		map.centerAndZoom(pp, 18);
		marker.setPosition(pp)
		map.addOverlay(marker);    //添加标注

	}
	var local = new BMap.LocalSearch(map, { //智能搜索
	  onSearchComplete: myFun
	});

	local.search(myValue);

}
/* 自动定位 */




// 添加图片事件绑定
var addimgCo = document.getElementById('addimgCo');
addimgCo.addEventListener('tap', function() {
	$('#imgUpload').click();
});
//添加图片事件绑定
var formSub = document.getElementById('send-btn');
formSub.addEventListener('tap', function() {
	// 微信支付
	
	
	
	$('.mui-input-group').submit();
	//$('#myModal').modal('show')
		
});

//var pay = document.getElementById('pay');
//pay.addEventListener('tap', function() {
//	
//	$.ajax({
//	    url: "/ucoon/pay/getPay",
//	    success: function(result){
//	    	if(result.result_type == "error"){
//	    		alert(result.msg);
//	    		return;
//	    	}
//	    	
//	    	var config = eval("(" + result.msg + ")");
//	    	
//    	    wx.chooseWXPay({
//		       timestamp: config.timestamp,
//		       nonceStr: config.nonce,
//		       package:config.packageName,
//		       signType: config.signType, // 注意：新版支付接口使用 MD5 加密
//		       paySign: config.signature
//		    }) 
//	    },
//	  	dataType: "json",
//	  	async:true
//	});
//	
//});


