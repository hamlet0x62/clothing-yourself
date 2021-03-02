const onUpdateSuccess = onOperationSuccess("修改成功");
const onDeleteSuccess = onOperationSuccess("删除成功");

function onOperationSuccess(msg) {
	function operationSuccess(rv){
		if(rv.code === 0){
			alert(msg);
		}else if(rv.code === -10){
			alert(rv.description);
		}
		loadGarments();
	}
	
	return operationSuccess; 
}
function deleteGarment(){
	if(!confirm("确定要删除吗")){
		return ;
	}
	var currentWind = $(event.target).parent().parent();
	var id = currentWind.attr("data-id");
	request("GET", deleteGarmentApi.replace("#id", id), "", onDeleteSuccess);
}

function getDataFromOptionBox(data, box, noAlert){
	var flag = true;
	noAlert = noAlert !== undefined ? noAlert : false;
	var selectedOption = $(".selectedOption", box);
	var fieldName = selectedOption.attr("data-field");
	var fieldVal = selectedOption.attr("data-value");
	if(fieldVal !== undefined && fieldVal !== null){
		data[fieldName] = fieldVal;
	}else{
		var label = $(box).siblings("label").text();
		label && !noAlert && alert(label + "未选择");
		flag = false;
	}
	
	return flag;
}

function getDataFromWind(curWind){
	var data = {};
	
	$("input[type!='file']", curWind).each(function(i, e) {
		data[e.name] = e.value;
	});
	
	var uploadFilename = $(".uploadFilename", curWind).attr("data-filename");
	
	if(uploadFilename !== undefined && uploadFilename.length > 0){
		data.assetFilename = uploadFilename;
	}
	var flag = true;
	var optionBoxes = $(".optionBox", curWind);
	optionBoxes.each(function (i, box){
		flag = getDataFromOptionBox(data, box);
	});
	
	return flag && data || null;
	
}


function queryGarments(isQueryAll){
	var queryBox = $("#queryBox");
	
	isQueryAll = isQueryAll !== undefined ? isQueryAll : false;
	var data = {};
	var flag = true;
	//console.log(data);
	if(!isQueryAll){
		$(".optionBox", queryBox).each(function (i, box){
			flag = getDataFromOptionBox(data, box, true);
		})
	}
	//console.log(data);
	request("POST", queryGarmentApi, data, onResponse);
}


