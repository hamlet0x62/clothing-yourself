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
<title>衣物管理</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/userlist.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/userform.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/garmentlist.css" />
<link href="<%=basePath%>/css/jquery.fileupload.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=basePath%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/main.js"></script>
<script src="<%=basePath%>/js/vendor/jquery.ui.widget.js"></script>
<script src="<%=basePath%>/js/jquery.iframe-transport.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/jquery.fileupload.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>/js/garmentlist.js"></script>

<style type="text/css">
body {
	background-color: rgb(164, 170, 177);
	background-image: url(/images/ui/workspace.png);
	background-size: 100%;
	background-repeat: repeat-y;
}
</style>
</head>
<body>
<div id="queryBox">
<label>性别：</label>
<div class="optionBox" style="width: 100px;">
<div class="selectedOption" data-field="gender">请选择</div>
<ul><li data-option="m">男</li><li data-option="f">女</li></ul>
</div>
<label>类别：</label>
<div class="optionBox" style="width: 100px;">
<div class="selectedOption" data-field="clazzId">请选择</div><ul><li>男</li><li>女</li></ul>
</div>

<div class="btnBox"><div id="clearIcon" class="clearCondBtn"></div><button class="queryBtn" onclick="queryGarments()">查找</button>
</div>

</div>

<div id="windSpace">
<div id="newWind" class="wind">
<div style="display:block">
<div class="leftGroup">
<div class="windGroup"><div class="suitTitle">服饰</div></div>  
<div class="windGroup"><label for="no">编号：</label><input name="no" type="text"></div>
<div class="windGroup"><label for="displayText">名称：</label><input name="displayText" type="text" value='#displayText'></div>
<div class="windGroup"><label for="price">价格：</label><input name="price" type="text" value='#price'></div>  
<div class="windGroup" style="height:2em"><label for="gender">适用性别：</label>
<div class="optionBox" style="width: 100px">
<div class="selectedOption" data-field="gender">请选择</div><ul><li data-option="m">男</li><li data-option="f">女</li></ul>
</div>
</div>  
<div class="windGroup" style="height:2em"><label for="clazzId">类别：</label>
<div class="optionBox">
<div class="selectedOption" data-field="clazzId">请选择</div><ul><li>One</li><li>two</li></ul></div>
</div>
</div>
<div class="uploadContainer">
	<div class="uploadFilename" data-field="assetFilename">请点击上传图片</div>
	<input type="file" name="files[]" multiple data-url="<%=basePath%>/api/upload/garmentImg">
	<img src="<%=basePath%>/images/ui/unknown.png">
</div>
</div>
<div class="btnGroup"><button class="addBtn" onclick="createGarment()">添加</button><button class="delBtn" onclick="deleteGarment()">删除</button></div>
</div>
</div>

<script type="text/javascript">
const basePath = "<%=basePath%>";
const garmentQueryApi = addBasePath(`api/garment/query`);
const garmentlistApi = addBasePath(`api/garment/all`);
const garmentclsApi = addBasePath(`api/garmentcls/all`);
const addGarmentApi = addBasePath(`api/garment/create`);
const deleteGarmentApi = addBasePath(`api/garment/del/#id`);
const updateGarmentApi = addBasePath(`api/garment/update/#id`);
const queryGarmentApi = addBasePath(`api/garment/query`);
const garmentImagePrefix = addBasePath("images/data/suits/#filename");

	
function clearWinds(){
	$("div[class='wind'][id!='newWind']").remove();
}

function getInputData(inputs){
	var data = {};
	inputs.each(function (i, input){
		data[input.name] = input.value;
	})
	return data;
}

function fillGarmentIntoWind(garment, wind){
	wind = $(wind);
	
	var addBtn = $("button.addBtn", wind);
	addBtn.text("修改");
	addBtn.attr("onclick", "");
	addBtn.on("click", updateGarment);
	
	var uploadContainer = $(".uploadContainer", wind);
	var garmentImg = garment.assetFilename || "unknown.png";
	var uploadText = garment.assetFilename || "点击上传图片";
	var filename = garmentImagePrefix.replace("#filename", garmentImg);
	
	// console.log(filename);
	$("img", uploadContainer).attr("src", filename);
	$(".uploadFilename", uploadContainer).text(uploadText);
	if(garment.assetFilename !== null){
		$(".uploadFilename", uploadContainer).attr("data-filename", garment.assetFilename);
	}
	$("input", wind).each(function (i, elem){
		var field = elem.name;
		if(garment[field] === undefined){
			return ;
		}
		elem.value = garment[field];
	});
	$(".optionBox", wind).each(function (i, elem){
		var selectedOption = $(".selectedOption", elem);
		var field = selectedOption.attr("data-field");

		var optionVal = garment[field];

		selectedOption.attr("data-value", optionVal)
		
		var optionLi = "ul li[data-option='#val']".replace("#val", optionVal);
		var optionLiText = $(optionLi, elem).text();
		$(optionLi, elem).addClass("selected");
		$("li", elem).not(optionLi).removeClass("selected");
		selectedOption.text(optionLiText);
		// console.log($(optionLi, elem));
		console.log(optionLi);
	});
}

function onGarmentsLoaded(garments){
	const newWind = $("#newWind");
	
	// remove current windows
	clearWinds();
	$(garments).each(function (i, garment) {
		var wind = newWind.clone();
		wind.attr("id", "");
		wind.attr("data-id", garment.id);
		addDrawerEvent(wind);
		var uploadContainer = $(".uploadContainer", wind);
		addUploadEvent(uploadContainer);
		wind.insertAfter(newWind);
		fillGarmentIntoWind(garment, wind);
	});
	
}

function onResponse(rv){
	if(rv.code === -10){
		alert(rv.description);
	}else if(rv.code === 0){
		onGarmentsLoaded(rv.data);
	}
}

function loadGarments(){
	var queryBox = $("#queryBox");
	var selectedOptions = $(".selectedOption", queryBox);
	selectedOptions.attr("data-value", null);
	selectedOptions.text("请选择");
	$(".selected", queryBox).removeClass("selected");
	request("GET", garmentlistApi, "", onResponse);
/* 	onGarmentsLoaded([1, 1, 1]); */
}

function loadGarmentclsOptions(clsOptions){
	var optionBoxes = $(".selectedOption[data-field='clazzId']").parent();
	console.log(optionBoxes);
	var curUls = optionBoxes.find("ul");
	curUls.children().remove();
	$(clsOptions).each(function (i, clsOption){
		var newLi = $("<li>");
		newLi.append(clsOption.clazzName);
		newLi.attr("data-option", clsOption.id);
		curUls.append(newLi);
	// 	console.log(clsOptions);
	});
	optionBoxes.each(function (i, box) {addLiEvent(box);});
	$(document).trigger("garmentclsLoaded");
}

function requestGarmentclsOptions() {
	request("GET", garmentclsApi, "", function (rv) {
		loadGarmentclsOptions(rv.data);
	})
}


function onPostSuccess(rv){
	if(rv.code === 0){
		alert(rv.description);
		loadGarments();
	}else if(rv.code === -10){
		alert(rv.description);
	}
}

function createGarment(){
	postGarment(addGarmentApi);
}

function updateGarment(){
	if(event === undefined){
		return ;
	}
	var curWind = $(event.target).parent().parent();
	curWind = $(curWind);
	var id = curWind.attr("data-id");
	if(id !== undefined){
		console.log(id);
		var api = updateGarmentApi.replace("#id", id);
		postGarment(api);
	}
	
}


function postGarment(url){
	
	if(event == undefined){
		return ;
	}
	event.stopPropagation();
	var curWind = $(event.target).parent().parent();
	var data = getDataFromWind(curWind);
	console.log(data);
	
	data !== null && request("POST", url, data, onPostSuccess);
}

function addLiEvent(curOptionBox){
	$("ul li", curOptionBox).unbind("click");
	$("ul li", curOptionBox).on("click", function (e){
		console.log("clicked");
		var optionBox = $(this).parent().parent();
		var optionVal = $(this).data("id");
		if(!optionVal){
			optionVal = $(this).attr("data-option");
		}
		var optionText = $(this).text();
		var textBox = optionBox.find(".selectedOption");
		var curUl = optionBox.find("ul");
		textBox.text(optionText);
		textBox.attr("data-value", optionVal);
		$(this).siblings("li").removeClass("selected");
		$(this).addClass("selected");
		curUl.slideToggle(1);
		return false;
	});
}

function addDrawerEvent(optionBoxContainers){
	var optionBoxes = $(".optionBox", optionBoxContainers);
	$(optionBoxes).each(function (i, elem){
		$(".selectedOption", elem).on("click", function (e) {
			$(".optionBox ul").not($(this).siblings(".optionBox ul")).slideUp(5);
			$(this).siblings(".optionBox ul").slideToggle(1);

			return false;
		});
		addLiEvent(elem);
	});
	
}

function addUploadEvent(container){
    $('input', container).fileupload({
        done: function (e, data) {
            var img = $(e.target).siblings("img");
            var imgSrc = garmentImagePrefix.replace("#filename", data.result.data);
            setTimeout(function() {img.attr("src", imgSrc);}, 1000);
            var filenameBox = $(e.target).siblings(".uploadFilename");
            filenameBox.text(data.result.data);
            filenameBox.attr("data-filename", data.result.data);
            }
    });
    $(container).on("click", function (e){
    	var input = $("input", e.target);
    	$(input).click();
    });
}


$(document).ready(function (){
	/* loadGarments();
	requestGarmentclsOptions(); */
	addDrawerEvent($("#newWind"));
	addDrawerEvent($("#queryBox"))
	$(document).on("click", function(e){
		$(".optionBox ul").slideUp(1);
	})
	requestGarmentclsOptions();
	$(document).on("garmentclsLoaded", loadGarments);
	
    addUploadEvent($('#newWind .uploadContainer'));
    $("#clearIcon").on("click", function (e) {
    	loadGarments();
    });
});



</script>

</body>
</html>