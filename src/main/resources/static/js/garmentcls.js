const updateGarmentClsApi = "api/garmentcls/update/#id";
const deleteGarmentClsApi = "api/garmentcls/del/#id";
const onUpdateSuccess = onOperationSuccess("修改成功");
const onDeleteSuccess = onOperationSuccess("删除成功");




function onOperationSuccess(msg) {
	function operationSuccess(rv){
		if(rv.code === 0){
			alert(msg);
		}else if(rv.code === -10){
			alert(rv.description);
		}
		loadGarmentClsWinds();
	}
	
	return operationSuccess; 
}
function removeGarmentCls(){
	var currentWind = $(event.target).parent().parent();
	var id = currentWind.data("id");
	request("GET", addBasePath(deleteGarmentClsApi.replace("#id", id)), "", onDeleteSuccess);
}

function updateGarmentCls(){
	var currentWind = $(event.target).parent().parent();
	var id = currentWind.data("id");
	var data = getInputData(currentWind.find("input"));
	request("POST", addBasePath(updateGarmentClsApi.replace("#id", id)), data, onUpdateSuccess);
}