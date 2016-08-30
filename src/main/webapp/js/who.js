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
// 百度地图API功能
function G(id) {
	return document.getElementById(id);
}
var map = new BMap.Map("l-map");
map.centerAndZoom("厦门", 13); // 初始化地图,设置城市和地图级别。
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

var myValue;
ac.addEventListener("onconfirm", function(e) { // 鼠标点击下拉列表后的事件
	var _value = e.item.value;
	myValue = _value.province + _value.city + _value.district + _value.street
			+ _value.business;
	G("searchResultPanel").innerHTML = "onconfirm<br />index = " + e.item.index
			+ "<br />myValue = " + myValue;
	$(".tangram-suggestion").text("");
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
})(mui);

/*
 * document.getElementById('mysend').addEventListener('tap', function() {
 * 
 * mui.openWindow({ url : "mysend.html" }); });
 */

/* mui.init */
mui.init();
// 主界面和侧滑菜单界面均支持区域滚动；
mui('#offCanvasSideScroll').scroll();
mui('#offCanvasContentScroll').scroll();
// 实现ios平台原生侧滑关闭页面；
if (mui.os.plus && mui.os.ios) {
	mui.plusReady(function() { // 5+
		// iOS暂时无法屏蔽popGesture时传递touch事件，故该demo直接屏蔽popGesture功能
		plus.webview.currentWebview().setStyle({
			'popGesture' : 'none'
		});
	});
}
/* mui.init */

/* 自动定位 */
$(function() {
	navigator.geolocation.getCurrentPosition(translatePoint); // 定位
});
function translatePoint(position) {
	var currentLat = position.coords.latitude;
	var currentLon = position.coords.longitude;
	var gpsPoint = new BMap.Point(currentLon, currentLat);
	BMap.Convertor.translate(gpsPoint, 0, initLocation); // 转换坐标
}
function initLocation(point) {
	var gc = new BMap.Geocoder();
	var s;
	gc.getLocation(point, function(rs) {
		var addComp = rs.addressComponents;
		s = addComp.province + "" + addComp.city + "" + addComp.district + ""
				+ addComp.street + "" + addComp.streetNumber;
		var myGeo = new BMap.Geocoder();
		myGeo.getPoint(s, function(point) {
			$("#suggestId").val(s);
		});
	});
}
/* 自动定位 */

// 添加图片事件绑定
var addimgCo = document.getElementById('addimgCo');
addimgCo.addEventListener('tap', function() {
	$('#imgUpload').click();
});

var formSub = document.getElementById('send-btn');
formSub.addEventListener('tap', function() {
	// 微信支付
	$('.mui-input-group').submit();
});
// 添加图片事件绑定
