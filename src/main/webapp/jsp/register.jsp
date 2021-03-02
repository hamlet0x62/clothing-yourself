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
<title>注册</title>
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
<div id="registerForm" class="userForm">
<div class="title">用户注册</div>
<table class="infoTable" style="width:100%">
<tr><td class="label">用户名:</td><td><input name="username" type="text"></td></tr>
<tr><td class="label">用户实名:</td><td><input name="realName" type="text"></td></tr>
<tr><td class="label">密码:</td><td><input name="password" type="password"></td></tr>
<tr><td class="label">确认密码:</td><td><input name="repeatpwd" type="password"></td></tr>
<tr><td class="label">性别:</td><td>
<input name="gender" type="radio" value="m" ><label for="m">男</label>
<input name="gender" type="radio" value="f" ><label for="f">女</label>
</td>
</tr>

</table>
<div class="subtitle" >模型选择</div>
<div id="modelGroup" style="display:none">
</div>
<div style="text-align: center">
<button id="submitBtn" class="confirmBtn" onclick="onRegister">提交</button>  
<button class="loginFromRegBtn" onclick="onRegister">已有账号，去登陆</button>
</div>

</div>

<script type="text/javascript">
var modelAvatarApi = "<%=basePath%>/api/model/findByGender/#gender";
var modelAvatarImgSrc = "<%=basePath%>/images/data/model/#filename";
var registerApi = "<%=basePath%>/api/user/create";
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
	
}

function onModelChange(e){
	var model = $(e.target).parent();
	if(model.hasClass('modelBox')){
		$('.modelBox').removeClass('modelBox-checked');
		console.log(model);
		$(model).addClass('modelBox-checked');
	}
}

$(document).ready(function () {
	var form = $("#registerForm");
	$("#submitBtn").on("click", onRegister);
	$(".loginFromRegBtn").on("click", function (e) {
		document.location.replace("login.jsp");
	});
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
	
	var fieldEl = $("input[name='#field']".replace("#field", field));
	var lineObj = fieldEl.parent().parent();
	//var newLine = $("<tr>");
	var newTd = $(noticeTemp.replace("#msg", msg));
	//newLine.append(newTd);
	//newTd.append(noticeTemp.replace("#msg", msg))
	newTd.insertAfter(lineObj);
	
	
	
}

function onRegisterSuccess(data){
	console.log(data);
	if(data.code === -10 ){
		showMessage(data);
	}else if(data.code === 20){
		alert('注册成功');
		document.location.href = "<%=basePath%>/" + data.nextAction;
	}else{
		console.log('Unkown Error');
	}
}

function onRegister(e){
 	var data = {};
 	removeInputNotice();
 	var model = $('.modelBox-checked');
 	if(model.length === 0){
 		alert("请选择一个头像");
 		return;
 	}
 	
 	var checkedModelId = idRegex.exec(model.attr('id'))[0];
	$("#registerForm input").each(function (i, item) {
		if(!(item.type == "radio") || item.checked){
			if(item.value.length === 0){
				addFiledValidationError(item.name, "此项不能为空"); 
			}
			data[item.name] = item.value;
		}
	}); 
	data.modelId = checkedModelId;
	request("post", registerApi, data, onRegisterSuccess);
	console.log(data);
}
</script>

</body>
</html>