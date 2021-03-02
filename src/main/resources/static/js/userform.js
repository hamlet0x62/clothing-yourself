const modelId = "model#id";
const noticeTemp = "<tr class='notice-line'><td colspan=1></td><td><div class='input-notice'>#msg</div><td></tr>";
const idRegex = /[\d^]*(\d)/;

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
	// console.log("fillForm");
	$("input[type='password']").val(""); // clear password field
	for(var field of Object.keys(data)){
		var fieldVal = data[field];
		var fieldEl = $("input[name='#field'][type!='radio']".replace("#field", field));
		if(fieldEl.length > 0){
			fieldEl.val(fieldVal);
		}
	}
	var isAdminRadio = $("input[name='isAdmin'][value='#isAdmin']".replace("#isAdmin", data.isAdmin?1:0));
	isAdminRadio.click();
	if(data.modelId !== undefined){
		var genderRadio = $("input[name='gender'][value='#gender']".replace("#gender", data.gender));
		
		genderRadio.click();
//		console.log(radio);
		//console.log(data);
		//console.log("#" + modelId.replace("#id", data.modelId));
		var model = $("#" + modelId.replace("#id", data.modelId) + " img");
		if(model.length > 0){
			model.click();
			return ;
		}
		$("#modelGroup").on("model-loaded", function (e){
			var model = $("#" + modelId.replace("#id", data.modelId) + " img");
			console.log(model);
			model.click();
		});

	}
	

}

function onRefreshDataReturn(rv){
//	console.log(rv);
	console.log(rv);
	if(rv.code === -10){
		alert(rv.description);
	}else if(rv.code === 20){
		parent.window.location.href = `${basePath}/` + rv.nextAction;
	}else if(rv.code === 0){
		fillForm(rv.data);
	}
}

function refreshForm(id){
//	console.log("entered");
	var api = `${basePath}/` + "api/user/self";
	if(id !== undefined){
		api = `${basePath}/` + `api/user/${id}`;
	}
	request("GET", api, "", onRefreshDataReturn, serverError);
}


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
		if(rv.data !== undefined){
			onRefreshDataReturn(rv);
		}else{
			refreshForm();
		}
	}else{
		console.log('Unkown Error');
	}
}


function onSubmit(e){
 	var data = {};
 	var submitApi = e.targetApi || selfUpdateApi;
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
	request("post", submitApi, data, onSubmitSuccess);
//	console.log(data);
}