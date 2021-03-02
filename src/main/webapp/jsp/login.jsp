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
<link rel="stylesheet" href="<%=basePath%>/css/main.css">
<script type="text/javascript" src="<%=basePath%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/main.js"></script>

<style type="text/css">
body {
	background-image: url(/images/ui/loginWindowBg1920.png);
	background-size: 100%;
	background-repeat: space;
	
}
</style>
<meta charset="UTF-8">
<title>登陆</title>
</head>
<body>
<div id="loginForm">
<div class="title">
登陆
</div>
<div class="logoBox">
</div>
<table>
<tr><td>用户名:</td><td><input name="username" type="text"></td></tr>
<tr><td>密码:</td><td><input name="password" type="password"></td></tr>
<tr><td colspan=1></td><td><div id="msg" class="input-notice"></div></td></tr>
</table>


<div style="text-align:center;margin-bottom:20px;margin-top:20px;">
<button id="loginBtn" style="width:40%;padding:0;" class="confirmBtn" >登陆</button>
<button id="registerBtn" style="width:40%;padding:0;" class="confirmBtn" >注册</button>
 </div>
</div>
</body>
<script>
var basePath = "<%=basePath%>" ;
var loginUrl = "<%=basePath%>/api/auth/login";  
var tableLine = "`key` value<br>";
function onLoginSuccess(rv){
	if(rv.code == -10){
		var msgDiv = $('#loginForm #msg');
		msgDiv.html("");
		for (var k in rv.data){
			if(k) {
				var msg = tableLine.replace("value", rv.data[k]).replace("key", k);
				msgDiv.append(msg);
			}
		}
		if(rv.data == ""){
			msgDiv.append(rv.description);
		}
		console.log(rv.data);
	}else if(rv.code === 20){
		redirect(basePath, rv);
	}
}

$(document).ready(
		function (){
			var loginBtn = $('#loginBtn');
			var form = $('#loginForm');
			centerObject(form);
			loginBtn.on('click', function (){
				var loginData = convert2JsonData(form);
				console.log(loginData);
				request("post", loginUrl, loginData, onLoginSuccess, serverError);
			});
			var $enterKey = 13;
			$("input[name='password']").on("keydown", function(e){
				if(e.keyCode === $enterKey){
					loginBtn.click();
				}
			});
			$("#registerBtn").on("click", function (e) {
				document.location.replace("register.jsp");
			})
		});
</script>
</html>