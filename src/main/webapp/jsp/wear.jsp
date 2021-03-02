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
<title>穿衣</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/userlist.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/userform.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/wear.css" />

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
	background-repeat: no-repeat;
	overflow: hidden;
}
</style>
</head>
<body>
<div id="suitSpace">
<div id="windSpace" style="height:100%;width:40%">
<div id="newWind" class="wind">
<div class="item"><label>编号</label><span class="no">jeans</span></div>
<div class="item"><label>名称</label><span class="name">牛仔</span></div>
<div class="item"><label>价格</label><span class="price">¥200.00</span></div>
<div class="opBox"><div class="zIndex"></div><div class="zIndexNum">1</div>
<div class="up"></div><div class="down"></div><div class="remove"></div></div>
</div>
</div>

<div id="modelSpace">
<%-- <img class="clothes" src="<%=basePath%>/images/data/suits/wShirt03.png"> --%>
<div id="ground">
</div>
<div id="priceTag">
<span class="text">总价</span>
<span class="value">$200</span>
</div>
</div>

<div id="asideBar">
<div id="queryBox">
<label>选择分类：</label>
<div class="optionBox">
<div class="selectedOption" data-field="clazzId">请选择</div>
<ul><li data-option="selectAll">所有</li></ul>
</div>
</div>

<div id="originGarmentWind" class="garmentWind">
<div class="garmentDisplay" style="display:inline-block;width:250px;">
<img class="clothes" src="<%=basePath%>/images/data/suits/wShirt03.png">
<div class="item"><label>编号</label><span class="no">jeans</span></div>
<div class="item"><label>名称</label><span class="name">牛仔</span></div>
<div class="item"><label>价格</label><span class="price">¥200.00</span></div>
</div>
<div class="opAddTo"></div>
</div>

</div>

</div>  

<script type="text/javascript">
const basePath = "<%=basePath%>";
const addSuitApi = addBasePath(`api/game/addSuit/#garmentId`);
const removeSuitApi = addBasePath(`api/game/removeSuit/#id`);
const adjustSuitApi = addBasePath(`api/game/adjust/#uGarmentId`);
const garmentQueryApi = addBasePath(`api/garment/query`);
const garmentlistApi = addBasePath(`api/garment/all`);
const garmentclsApi = addBasePath(`api/garmentcls/all`);
const currentGarmentsApi = addBasePath(`api/game/garments`);
const currentUserApi = addBasePath("api/user/self");
const modelApi = addBasePath("api/model/#id");
const garmentImagePrefix = addBasePath("/images/data/suits/#filename");
const assetUrl = "url(/images/data/model/#filename)";
const self = {gender: 'm', loaded: false};

function clearUserGarmentWinds(){
	$("div[class='wind'][id!='newWind']").remove();
}

function getInputData(inputs){
	var data = {};
	inputs.each(function (i, input){
		data[input.name] = input.value;
	})
	return data;
}

function fillUserGarmentIntoWind(userGarment, wind){
	//
	wind = $(wind);
	$("span.no", wind).text(userGarment.no);
	$("span.name", wind).text(userGarment.displayText);
	$("span.price", wind).text(userGarment.price.formatMoney());
	//console.log(userGarment);
	$(".opBox .zIndexNum", wind).text(userGarment.zindex);
	$(".opBox .zIndexNum", wind).attr("data-value", userGarment.zindex);
	$(".opBox .up", wind).on("click", function (e){
		adjustSuit("up");
	});
	$(".opBox .down", wind).on("click", function (e){
		adjustSuit("down");
	});
	$(".opBox .remove", wind).on("click", function (e){
		removeSuit();
	});
	
}

function addClothes2Model(userGarment){
	const modelSpace = $("#modelSpace");
	if(userGarment.assetFilename === null){
		return ;
	}
	var img = $("<img>");
	img.addClass("clothes");
	console.log(userGarment);
	var clothesUrl= garmentImagePrefix.replace("#filename", userGarment.assetFilename);
	img.attr("src", clothesUrl);
	img.css({'z-index': userGarment.zindex});
	img.insertBefore($("div#ground"));
}

function fillGarmentIntoWind(garment, wind){
	wind = $(wind);
	var garmentImg = garment.assetFilename || "unknown.png";
	var filename = garmentImagePrefix.replace("#filename", garmentImg);
	// console.log(filename);
	$(".garmentDisplay img", wind).attr("src", filename);
	$("span.no", wind).text(garment.no);
	$("span.name", wind).text(garment.displayText);
	$("span.price", wind).text(garment.price.formatMoney());
	
}

function clearGarmentWinds() {
	$("div[class='garmentWind'][id!='originGarmentWind']").remove();
}

function onUserGarmentsLoaded(userGarments){
	
	const newWind = $("#newWind");
	
	var total = 0;
	$(userGarments).each(function (i, uGarment) {
		var wind = newWind.clone();
		wind.attr("id", "");
		wind.attr("data-id", uGarment.id);
		addDrawerEvent(wind);
		wind.insertAfter(newWind);
		fillUserGarmentIntoWind(uGarment, wind);
		addClothes2Model(uGarment);
		total += uGarment.price;
	});
	$("#modelSpace").trigger("totalCounted", {"total": total});
	
}

function onGarmentsLoaded(garments){
	const originWind = $("#originGarmentWind");
	
	// remove current windows
	clearGarmentWinds();
	
	$(garments).each(function (i, garment) {
		var wind = originWind.clone();
		wind.attr("id", "");
		wind.attr("data-id", garment.id);
		addDrawerEvent(wind);
		wind.insertAfter(originWind);
		fillGarmentIntoWind(garment, wind);
		$("div.opAddTo", wind).on("click", addSuit);
	});
	
}

function onGarmentsResponse(rv){
	if(rv.code === -10){
		alert(rv.description);
	}else if(rv.code === 0){
		onGarmentsLoaded(rv.data);
	}
}

function onUserGarmentsResponse(rv){
	if(rv.code === -10){
		alert(rv.description);
	}else if(rv.code === 0){
		clearUserGarmentWinds();
		// clear weared clothes
		$("#modelSpace .clothes").remove();
		onUserGarmentsLoaded(rv.data);
	}
}

function addSuit(event){
	// console.log(event);
	if(event === undefined){
		return ;
	}
	var curWind = $(event.target).parent();
	var garmentId = $(curWind).attr("data-id");
	if(garmentId !== undefined){
		request("POST", addSuitApi.replace("#garmentId", garmentId), "", function(rv) {
			if(rv.code === 0){
				loadModel();
			}else{
				alert(rv.description);
			}
		})
	}
}

function adjustSuit(mutation){
	if(event === undefined){
		return;
	}
	var curSuitWind = $(event.target).parent().parent();
	var curZindex = $(".zIndexNum", curSuitWind).attr("data-value");
	var uGarmentId = curSuitWind.attr("data-id");
	curZindex = parseInt(curZindex);
	if(curZindex === undefined || Number.isNaN(curZindex)){
		return ;
	}

	if(mutation === "up"){
		curZindex += 1;
		
	}else{
		curZindex -= 1;
	}
	request("POST", adjustSuitApi.replace("#uGarmentId", uGarmentId),
			{'zindex': curZindex}, function (data){loadModel();});
}

function removeSuit(){
	if(event === undefined){
		return ;
	}
	
	var wind = $(event.target).parent().parent();
	var uGarmentId = wind.attr("data-id");
	if(uGarmentId !== undefined){
		request("POST", removeSuitApi.replace("#id", uGarmentId), "", function (rv) {
			if(rv.code === 0) {
				alert("删除成功");
				loadModel();
			}else{
				alert(rv.description);
			}
		})
	}
	
	
}

function loadGarments(){
	var selectedOptions = $("#queryBox .selectedOption");
	selectedOptions.attr("data-value", undefined);
	selectedOptions.text("所有");
	request("POST", garmentQueryApi, self, onGarmentsResponse);
/* 	onGarmentsLoaded([1, 1, 1]); */
}

function queryGarments(clsOptionId){
	if(clsOptionId === undefined){
		return ;
	}
	
	request("POST", garmentQueryApi, {
		"clazzId": clsOptionId,
		"gender": self.gender, 
	}, onGarmentsResponse);
}

function loadGarmentclsOptions(clsOptions){
	var optionBoxes = $(".selectedOption[data-field='clazzId']").parent();
	console.log(optionBoxes);
	var curUls = optionBoxes.find("ul");
	curUls.children().not("li[data-option='selectAll']").remove();
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

function requestUserGarments(){
	request("GET", currentGarmentsApi, "", onUserGarmentsResponse);
}

function loadModel() {
	request("GET", currentUserApi, "", function (rv){
		var user = rv.data;
		self.gender = user.gender;
		request("GET", modelApi.replace("#id", user.modelId), "", function (rv2){
			var modelFilename = rv2.data.bodyFilename;
			var modelSpace = $("#modelSpace");
			modelSpace.css({"background-image": assetUrl.replace("#filename", modelFilename)});
			modelSpace.trigger("modelLoaded");
			if(!self.loaded){
				self.loaded = true;
				modelSpace.trigger("firstModelLoaded");
			}
		})
	})
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
		if($(this).attr("data-option") === "selectAll"){
			loadGarments();
		}else{
			queryGarments(optionVal);
		}
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



$(document).ready(function (){
	loadModel();
	$("#modelSpace").on("modelLoaded", requestUserGarments);
	$("#modelSpace").on("totalCounted", function (e, data){
		 $("#priceTag .value").text(data["total"].formatMoney());
	})
	$("#modelSpace").on("firstModelLoaded", loadGarments);
	requestGarmentclsOptions();
	$(document).on("click", function(e){
		$(".optionBox ul").slideUp(1);
	})
	$(document).on("garmentclsLoaded", function () {
		addDrawerEvent($("#queryBox"));
		});
	
	/* loadGarments();
	requestGarmentclsOptions(); */
	/* addDrawerEvent($("#newWind"));
	$(document).on("click", function(e){
		$(".optionBox ul").slideUp(1);
	})
	requestGarmentclsOptions();
	$(document).on("garmentclsLoaded", loadGarments);
	*/
});



</script>

</body>
</html>