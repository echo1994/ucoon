<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>后台管理系统</title>
		<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
		<link rel="stylesheet" href="css/cj.css" type="text/css" />
		<link rel="stylesheet" href="css/list.css" type="text/css" />	
	</head>
	<body >
		<div id="middle">
    <button id="addmmean" onclick="Am()">添加主菜单</button>
    <button id="tijiao" onclick="sub()">提交</button>

 

</div>
<script>
    var oAddmmean=document.getElementsByClassName("addmmean");
    var oMmean=document.getElementsByClassName("mmean");
    var oMiddle=document.getElementById("middle")
    var oZmean=document.getElementsByClassName("zmean");
    var index=1;
    function Am() {
        if (oMmean.length>=3){return}
        oMiddle.innerHTML +="<br><div class='mmean'> " +
                "主菜单： <input class='putmmean"+index+"' placeholder='name' > " +
                "<input class='putmmean"+index+"' placeholder='type' >" +
                " <input class='putmmean"+index+"' placeholder='url' > " +
                "<button onclick='Az(this,"+index+")'>添加子菜单</button> " +
                "<div class='zmean'></div>" +
                "</div>";
                index++;
    }
    function Az(obj,num) {
        var bcd=obj.parentNode.getElementsByClassName("putmmean"+num);
        var abc=obj.parentNode.getElementsByClassName("zmean");
        alert(abc.length);
        if (abc.length>=6){return}
        bcd[2].style.display="none";
        bcd[1].style.display="none";
        abc[0].innerHTML +="<div class='zmean'> " +
                "子菜单： <input class='putzmean"+num+"' placeholder='name' > " +
                "<input class='putzmean"+num+"' placeholder='type'> " +
                "<input class='putzmean"+num+"' placeholder='url'>" +
                "</div>"
    }
</script>
	</body>
</html>
<script src="script/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="script/jquery.alerts.js" type="text/javascript"></script>
<script src="js/tongyong.js" type="text/javascript"></script>
<script src="js/admin.js" type="text/javascript"></script>
<script type="text/javascript">
	function sub(){
		var menu;
		var ziz=new Array()	;
		var button=new Array();
			var p=$(".putmmean1");
			var zi=$(".putzmean1");
			if(p.length>0){
				if(zi.length==0){
					var bu={"name":p[0].value,"type":p[1].value,"url":p[2].value};
					 button[0]=bu;
				}else{
					
					alert(zi.length);
					for(var j=0;j<zi.length/3;j++){
						var i=j*3;
						var zix={"name":zi[i].value,"type":zi[i+1].value,"url":zi[i+2].value};
						ziz[j]=zix;
					}
					var bu={"name":p[0].value,"sub_button":ziz};
					button[0]=bu;
					alert(JSON.stringify(bu));
					menu={"button":bu};
				}
			}
			p=$(".putmmean2");
			zi=$(".putzmean2");
			if(p.length>0){
				if(zi.length==0){
					var bu={"name":p[0].value,"type":p[1].value,"url":p[2].value};
					button[1]=bu;
				}else{
					
					for(var j=0;j<zi.length/3;j++){
						var i=j*3;
						var zix={"name":zi[i].value,"type":zi[i+1].value,"url":zi[i+2].value};
						ziz[j]=zix;
					}
					var bu={"name":p[0].value,"sub_button":ziz};
					button[1]=bu;
				}
			}
			p=$(".putmmean3");
			zi=$(".putzmean3");
			if(p.length>0){
				if(zi.length==0){
					var bu={"name":p[0].value,"type":p[1].value,"url":p[2].value};
					button[2]=bu;
				}else{
					
					alert(zi.length);
					for(var j=0;j<zi.length/3;j++){
						var i=j*3;
						var zix={"name":zi[i].value,"type":zi[i+1].value,"url":zi[i+2].value};
						ziz[j]=zix;
					}
					var bu={"name":p[0].value,"sub_button":ziz};
					button[2]=bu;
				}
			}
			var but={"button":button};
			var menu={"menu":but};
			alert(JSON.stringify(menu));
		$.ajax({
		url:"admin/geteditMenu",
		type:"post",
		contentType: "application/json",	
		data:JSON.stringify(menu),
		success:function(data){
			if(data=="true"){
				alert("修改成功");
				window.location.reload();
			}else{
				alert("修改失败");
			}
		}
		});
	}
</script>