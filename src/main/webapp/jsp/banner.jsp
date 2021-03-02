<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + 
                                      request.getServerName() + ":" +
                                      request.getServerPort() + path;
%>    
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" href="<%=basePath%>/css/main.css">
<script type="text/javascript" src="<%=basePath%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/main.js"></script>

<style type="text/css">
.banner{
	background-image:url(/images/ui/loginWindowBg1920.png);
	background-repeat: no-repeat;  
	height: 100%;
	width: 100%;
	position: absolute;
	top: 0px;
	left: 0px;  
}

.banner p {
    top: 2em;
    position: absolute;
}
.logo {
	height:45px;
	top: 5%;
	position: absolute;
}

body {
/* 	background-color: rgb(164, 170, 177);  */
}
</style>
<title>banner</title>
</head>  
<body> 
<div class="banner"></div>
<div class="logo"><img src="<%=basePath%>/images/ui/themeBanner.png"> </div>
<script type="text/javascript">
const basePath = "<%=basePath%>";
const roleLabelApi = addBasePath("api/auth/roleLabel");
const currentRoleInfo = "<p>当前用户：#roleLabel</p>";

function getRoleName(role){
	console.log(role);
	if(role === "ANONYMOUS"){
		return "匿名用户";
	}else if(role === "ADMIN"){
		return "管理员";
	}else if(role === "USER"){
		return "普通用户";
	}else{
		return "ERROR";
	}
}

$(document).ready(function (e){
	request("GET", roleLabelApi, "", function (rv){
		if(rv.code === 0){
			$(".banner").html(currentRoleInfo.replace("#roleLabel", getRoleName(rv.data)));
		}
	})
})

</script> 
</body>
</html>
