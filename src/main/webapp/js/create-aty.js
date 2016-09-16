mui.init({ 
	swipeBack:true //启用右滑关闭功能
});
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
		document.body.classList.add('menu-open');
		menuWrapper.className = 'menu-wrapper fade-in-down animated mui-active';
		menu.className = 'menu bounce-in-down animated';
		backdrop.style.opacity = 1;
	}
	setTimeout(function() {
		busying = false;
	}, 500);
}

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



//参数定义
var current_city = "厦门市";//默认厦门市，在自动定位时改变，用于检索
var address_temp = "";//用于存放当前地址
var lng_temp = "";//用于存放当前经度
var lat_temp = "";//用于存放当前纬度

// 百度地图API功能
function G(id) {
	return document.getElementById(id);
}
var map = new BMap.Map("l-map");
var point = new BMap.Point(118.099805,24.584511);
var marker = new BMap.Marker(point);// 创建标注
map.addOverlay(marker);             // 将标注添加到地图中
map.centerAndZoom(point, 11);
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




// 添加定位控件
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

G("search").addEventListener("click", function() { // 搜索按钮点击事件
	var _value = G("suggestId").value; 
	if(_value == ""){
		alert("请输入地址");
		return;
	}
	
	map.clearOverlays();    //清除地图上所有覆盖物
	
	function myFun(){
		var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
		
		map.centerAndZoom(pp, 18);
		marker.setPosition(pp)
		map.addOverlay(marker);    //添加标注
		var geoc = new BMap.Geocoder();
		geoc.getLocation(pp, function(rs){
			var addComp = rs.addressComponents;
			G("suggestId").value = addComp.province + addComp.city + addComp.district+ addComp.street + addComp.streetNumber + "(" + _value +  ")";
			address_temp = addComp.province + addComp.city + addComp.district+ addComp.street + addComp.streetNumber + "(" + _value +  ")";
			lng_temp = pp.lng;
			lat_temp = pp.lat;
		});
	}
	var local = new BMap.LocalSearch(map, { //智能搜索
	  onSearchComplete: myFun
	});
	//local.search(_value);//不限制范围

	local.searchNearby(_value,current_city,20000); //限制范围 ，规范：当前城市 20千米内检索
	
});

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
		current_city = addComp.city;//定位当前城市，改变默认城市

		setPlace(s);
		address_temp = s;
		lng_temp = pp.lng;
		lat_temp = pp.lat;
		var myGeo = new BMap.Geocoder();
		myGeo.getPoint(s, function(point) {
			$("#suggestId").val(s);
			$("#menu-btn").val(s);
		});
	});
	
}


function setPlace(myValue){
		map.clearOverlays();    //清除地图上所有覆盖物
	
		function myFun(){
			var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
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


var formSub = document.getElementById('send-btn');
formSub.addEventListener('tap', function() {
	
	
	
	
	$('.mui-input-group').submit();
	
		
});