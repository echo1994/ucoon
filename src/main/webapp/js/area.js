$(function () {
    //加载省份
    access_pro();
    //选择省份加载市
    $("#s_province").change(function () {
        var selValue = $(this).val();
        $("#s_city option:gt(0)").remove();
        $("#s_county option:gt(0)").remove();
        access_city(selValue);//加载市
    });
    //选择市加载区 
    $("#s_city").change(function () {
        var selValue = $(this).val();
        $("#s_county option:gt(0)").remove();
        access_county(selValue);//加载区
    });
});
//加载省市区的默认选中
function init() {
    var provinceCode = $("#k_province").val();
    var cityCode = $("#k_city").val();
    var countyCode = $("#k_county").val();
    access_pro();
    access_city(provinceCode);
    access_county(cityCode);
    console.log(provinceCode);
    $("#s_province").val(provinceCode);
    $("#s_city").val(cityCode);
    $("#s_county").val(countyCode);
}

//省
function access_pro() {
    $.ajax({
        type: "Get",
        url: "/Common/Region/Provinces",
        dataType: "json",
        //async:false,
        success: function (data) {
            pro_optionAdd(data);
            //写在这里
        }
    });
}
//添加省份的选项
function pro_optionAdd(data,v) {
    $.each(data, function (k, item) {
        var option = "<option value='" + item.ProvinceCode + "'>" + item.ProvinceName + "</option>";
        $("#s_province").append(option);
    });
}

//市
function access_city(proCode) {
    $.ajax({
        type: "Get",
        url: "/Common/Region/Cities",
        dataType: "json",
        data:{ provinceCode:proCode },
        success: function (data) {
            city_optionAdd(data);
        }
    });
}
//添加市的选项
function city_optionAdd(data) {
    $.each(data, function (k, item) {
        var option = "<option value='" + item.CityCode + "'>" + item.CityName + "</option>";
        $("#s_city").append(option);
    });
}


//区
function access_county(cityCode) {
    $.ajax({
        type: "Get",
        url: "/Common/Region/Counties",
        dataType: "json",
        data: { cityCode: cityCode },
        success: function (data) {
            county_optionAdd(data);
        }
    });
}
//添加区的选项
function county_optionAdd(data) {
    $.each(data, function (k, item) {
        var option = "<option value='" + item.CountyCode + "'>" + item.CountyName + "</option>";
        $("#s_county").append(option);
    });
}

