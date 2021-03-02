<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>衣物类别管理</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/userlist.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/userform.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/garmentcls.css" />

<script type="text/javascript" src="<%=basePath%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/main.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/userTable.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/garmentcls.js"></script>

<style type="text/css">
body {
	background-color: rgb(164, 170, 177);
	background-image: url(/images/ui/workspace.png);
	background-size: 100%;
	background-repeat: no-repeat;
}
</style>
</head>
<body>

<div id="windSpace" style="text-align:center;">
<div id="newWind" class="wind">
<div class="windGroup"><div class="suitTitle">服饰类别</div></div>
<div class="windGroup"><label for="no">编号：</label><input name="no" type="text"></div>
<div class="windGroup"><label for="clazzName">名称：</label><input name="clazzName" type="text"></div>
<div class="btnGroup"><button class="addBtn" onclick="addGarmentCls()">添加</button></div>
</div>
</div>

<script type="text/javascript">
const basePath = "<%=basePath%>";
const garmentlistApi = addBasePath(`api/garmentcls/all`);
const addGarmentApi = addBasePath(`api/garmentcls/create`);

const cataWindTemp = `<div class="wind">` + 
	`<div class="windGroup"><div class="suitTitle">服饰类别</div></div>` + 
	`<div class="windGroup"><label for="no">编号：</label><input name="no" type="text" value='#no'></div>` + 
	`<div class="windGroup"><label for="no">名称：</label><input name="clazzName" type="text" value='#clsName'></div>` + 
	`<div class="btnGroup"><button class="alterBtn" onclick='updateGarmentCls()'>修改</button><button class="delBtn" ` + 
	`onclick='removeGarmentCls()'>删除</button></div></div>`;

	
function clearWinds(){
	$("div[class='wind'][id!='newWind']").remove();
}

function onGarmentClsAdded(rv){
	if(rv.code === 0){
		var catalogSpace = $("#windSpace");
		var wind = createCataWind(rv.data);
		catalogSpace.append(wind);
	} else{
		alert(rv.description);
	}
}

function getInputData(inputs){
	var data = {};
	inputs.each(function (i, input){
		data[input.name] = input.value;
	})
	return data;
}

function addGarmentCls(){
	var inputs = $("#newWind input");
	data = getInputData(inputs);
	console.log(data);
	request("POST", addGarmentApi, data, onGarmentClsAdded);
	
}

function createCataWind(garmentCls){
	var wind = $(cataWindTemp.replace("#no", garmentCls.no).replace("#clsName", garmentCls.clazzName));
	wind.data("id", garmentCls.id);
	
	return wind;
}

function onCatalogLoaded(garmentClazzes){
	const cataWind = $("#newWind");
	clearWinds();
	$(garmentClazzes).each(function (i, garmentCls) {
		var wind = createCataWind(garmentCls)
		wind.insertAfter(cataWind);
	})
}

function onResponse(rv){
	if(rv.code === -10){
		alert(rv.description);
	}else if(rv.code === 0){
		onCatalogLoaded(rv.data);
	}
}

function loadGarmentClsWinds(){
	request("GET", garmentlistApi, "", onResponse);
}

$(document).ready(function (){
	loadGarmentClsWinds();
})




</script>

</body>
</html>