const modelAvatarUrl = "images/data/model/#filename";
const removeUserApi = "api/user/del/#id";
const updateUserApi = "api/user/update/#id";
const opButton = "<div><button class='alterBtn' onclick='onUserAlter(#id)'>修改</button><button class='delBtn' onclick='onUserRemove(#id)'>删除</button></div>";



function replaceAll(s, target, replaceVal) {
	while(s.indexOf(target) !== -1){
		s = s.replace(target, replaceVal);
	}
	
	return s;
}

function onRemoveEnsure(rv){
	console.log(rv);
	if(rv.code === 0){
		$(`#userline-${rv.data}`).remove();
		alert("已删除");
	}else if(rv.code === -10){
		console.log(rv.description);
		alert("删除失败，错误信息见日志");
	}

}

function onUserAlter(userId){
	refreshForm(userId);
	var userForm = $("#userForm");
	userForm.data("id", userId);
	centerObject(userForm)
	showObject(userForm);
}

function onUserRemove(userId){
//	e.preventDefault();
	if(confirm("确认要删除吗？")){
		request("POST", addBasePath(removeUserApi.replace("#id", userId)), null, onRemoveEnsure);
	}
	
}

function refreshTable(){
	request("GET", userlistApi, "", loadUsers, serverError);
}

function clearTable(){
	$("table.userTable tbody").html("");
}

function loadUsers(rv){
	console.log(rv);
	if(rv.code === -10){
		alert(rv.description);
	}else if(rv.code === 0){
		clearTable();
		$(rv.data).each(function (i, user) {
			var lineEl = `<tr id='userline-${user.id}'><td>${user.id}</td><td>${user.username}</td>`
				+ `<td>${user.realName}</td><td>${user.gender == 'f'? '女': '男'}</td>`
				+`<td><img src='${addBasePath(modelAvatarUrl.replace('#filename', user.avatarFilename))}'>`
				+`</td><td>${user.isAdmin? '是': '否'}</td><td>${replaceAll(opButton, '#id', user.id)}</td></tr>`;
			$("table.userTable tbody").append($(lineEl));
		});
		centerObject($("#userTable"));
	}
}

function ensureModification(e) {
	var userId = $("#userForm").data("id");
	console.log(userId);
	e.targetApi = addBasePath(updateUserApi.replace("#id", userId));
	onSubmit(e);
}