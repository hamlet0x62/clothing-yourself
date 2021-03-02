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
<title>个人信息</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/main.css" />
<script type="text/javascript" src="<%=basePath%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/main.js"></script>

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

<div id="userForm" >
<div class="title">个人信息</div>
<table class="infoTable" style="width:100%">
<tr><td class="label">用户名:</td><td><input name="username" type="text"></td></tr>
<tr><td class="label">用户实名:</td><td><input name="realName" type="text"></td></tr>
<tr><td class="label">密码:</td><td><input name="password" type="password" placeholder="若无须修改则留空值"></td></tr>
<tr><td class="label">确认密码:</td><td><input name="repeatpwd" type="password" placeholder="若无须修改则留空值"></td></tr>
<tr><td class="label">性别:</td><td>
<input name="gender" type="radio" value="m" ><label for="m">男</label>
<input name="gender" type="radio" value="f" ><label for="f">女</label>
</td>
</tr>
</table>
<div class="subtitle" >模型选择</div>
<div id="modelGroup" style="display:none">
</div>
<div style="text-align: center"><button id="submitBtn" class="confirmBtn">修改</button></div>
</div>

<script type="text/javascript">
var modelAvatarApi = "<%=basePath%>/api/model/findByGender/#gender";
var modelAvatarImgSrc = "<%=basePath%>/images/data/model/#filename";
var selfUpdateApi = "<%=basePath%>/api/user/selfupdate";
var modelId = "model#id";
var noticeTemp = "<tr class='notice-line'><td colspan=1></td><td><div class='input-notice'>#msg</div><td></tr>";
var idRegex = /[\d^]*(\d)/;

function makeModelAvatarDiv(modelData){
	var modelAvatar = $("<div>");
	var avatarImg = $("<img>");
	avatarImg.attr("src", modelAvatarImgSrc.replace("#filename", modelData.avatarFilename));
	modelAvatar.addClass("modelBox");
	modelAvatar.append(avatarImg);
	modelAvatar.attr("id", modelId.replace("#id", modelData.id));
	modelAvatar.on('click', onModelChange);
	return modelAvatar;
}

function showGenderAvatar(avatarList){
	console.log(avatarList);
	var modelGroup = $("#modelGroup");
	modelGroup.html("");
	
	$(avatarList).each(function (i, it){
		modelGroup.append(makeModelAvatarDiv(it));
		console.log(it.id);
	});
	modelGroup.trigger("model-loaded");
	
}

function onModelChange(e){
	var model = $(e.target).parent();
	if(model.hasClass('modelBox')){
		$('.modelBox').removeClass('modelBox-checked');
		console.log(model);
		$(model).addClass('modelBox-checked');
	}
}

function fillForm(data){
	console.log(data);
	$("input[type='password']").val(""); // clear password field
	for(var field of Object.keys(data)){
		var fieldVal = data[field];
		var fieldEl = $("input[name='#field'][type!='radio']".replace("#field", field));
		if(fieldEl.length > 0){
			fieldEl.val(fieldVal);
		}
	}
	if(data.modelId !== undefined){
		var radio = $("input[name='gender'][value='#gender']".replace("#gender", data.gender));
		radio.click();
		console.log(radio);
		console.log("#" + modelId.replace("#id", data.modelId));
		$("#modelGroup").on("model-loaded", function (e){
			var model = $("#" + modelId.replace("#id", data.modelId) + " img");
			console.log(model);
			if(model.length > 0){
				model.click();
			}
		});

	}
	

}

function onRefreshDataReturn(rv){
	console.log(rv);
	if(rv.code === -10){
		alert(rv.description);
	}else if(rv.code === 20){
		parent.window.location.href = "<%=basePath%>/" + rv.nextAction;
	}else if(rv.code === 0){
		fillForm(rv.data);
	}
}

function refreshForm(){
	console.log("entered");
	var api = "<%=basePath%>/api/user/self";
	request("GET", api, "", onRefreshDataReturn, serverError);
}



$(document).ready(function () {
	var form = $("#userForm");
	refreshForm();
	$("#submitBtn").on("click", onSubmit);
	$("input[name='gender']").on("change", function (el){
		$("#modelGroup").css('display', 'block');
		var genderCaption = el.target.value;
		var api = modelAvatarApi.replace("#gender", genderCaption);
		request("GET", api, "", showGenderAvatar, serverError);
		console.log(el.target.value);
	})
	form.css({position: "absolute",
		top: $(window).height()/2 -form.height()*0.7,
		left: $(window).width()/2-form.width()/2
		}
	); 
})

function removeInputNotice(){
	$('.notice-line').remove();
}

function addFiledValidationError(field, msg){
	
	var fieldEl = $("input[name=#field]".replace("#field", field));
	var lineObj = fieldEl.parent().parent();
	//var newLine = $("<tr>");
	var newTd = $(noticeTemp.replace("#msg", msg));
	//newLine.append(newTd);
	//newTd.append(noticeTemp.replace("#msg", msg))
	newTd.insertAfter(lineObj);
}

function onSubmitSuccess(rv){
	if(rv.code === -10 ){
		for(var field of Object.keys(rv.data)){
			var fieldVal = rv.data[field];
			var fieldEl = $("input[name='#field'][type!='radio']".replace("#field", field));
			addFiledValidationError(field, rv.data[field]);
		}
		
	}else if(rv.code === 0){
		alert(rv.description);
		refreshForm();
	}else{
		console.log('Unkown Error');
	}
}

function onSubmit(e){
 	var data = {};
 	removeInputNotice();
 	var model = $('.modelBox-checked');
 	if(model.length === 0){
 		alert("请选择一个头像");
 		return;
 	}
 	
 	var checkedModelId = idRegex.exec(model.attr('id'))[0];
	$("#userForm input").each(function (i, item) {
		if(!(item.type == "radio") || item.checked){
			data[item.name] = item.value;
		}
	}); 
	data.modelId = checkedModelId;
	request("post", selfUpdateApi, data, onSubmitSuccess);
	console.log(data);
}


</script>

</body>
</html>