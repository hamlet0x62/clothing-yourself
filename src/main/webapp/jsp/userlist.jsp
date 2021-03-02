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
<title>用户管理</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/userlist.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/userform.css" />

<script type="text/javascript" src="<%=basePath%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/main.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/userform.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/userTable.js"></script>


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

<div id="userTable" style="width:80%;text-align:center;">
<table class="userTable" style="width:100%;text-align:center;">
<thead>
<tr><th width="20">ID</th><th width="40">用户名</th><th width="40">用户实名</th><th width="10">性别</th>
<th width="80">选择模型</th><th width="80">是否管理员</th><th width="80">操作</th></tr>
</thead>

<tbody>
<tr><td>ID</td><td>用户名</td><td>用户实名</td><td>性别</td>
<td>选择模型</td><td>是否管理员</td><td>操作</td></tr>
</tbody>

</table>
</div>


<div id="userForm" style="display:none" >
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
<tr>
<td class="label">是否管理员:</td><td>
<input name="isAdmin" type="radio" value="1" ><label for="y">是</label>
<input name="isAdmin" type="radio" value="0" ><label for="y">否</label>
</td>
</tr>
</table>
<div class="subtitle" >模型选择</div>
<div id="modelGroup" style="display:none">
</div>
<div style="text-align: center" class="dialogBtnGroup">
<button id="submitBtn" class="ensureBtn">修改</button>
<button id="cancelBtn" class="cancelBtn" onclick="onCancelDlg()">关闭窗口</button>
</div>
</div>

<script type="text/javascript">
const basePath = "<%=basePath%>";
const modelAvatarApi = "<%=basePath%>/api/model/findByGender/#gender";
const modelAvatarImgSrc = "<%=basePath%>/images/data/model/#filename";
const selfUpdateApi = "<%=basePath%>/api/user/selfupdate";
const userlistApi = `${basePath}/api/user/all`;

function onCancelDlg(){
	console.log('clicked');
	hideObject($("#userForm"));
	refreshTable();
}

$(document).ready(function () {
	var form = $("#userForm");
	var userTable = $("#userTable");
	refreshForm();
	$("#submitBtn").on("click", ensureModification);
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
	userTable.css({position: "absolute",
		top: $(window).height()/2 -userTable.height()*0.7,
		left: $(window).width()/2-userTable.width()/2
		}
	);
	refreshTable();

})



</script>

</body>
</html>