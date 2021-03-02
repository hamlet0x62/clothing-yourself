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
<script type="text/javascript" src="<%=basePath%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/main.js"></script>
<title></title>
<style type="text/css">
img {
	width: 80px;
	position:relative;
	top:5%;
}

a {
	border-style: none;
	display: block;
	text-align: center;
	height: 100%;
	position:relative;
}

a:hover {
	border-style: outset;
}

div.menuBtn {
	height: 100px;
}

a:visited {
	color: white;

}

body {
	background-color: rgb(164, 170, 177);  
}
</style>
</head>
<body>
<div class="menuBtn"><a href="<%=basePath %>/jsp/userinfo.jsp" target="workspace"><img src="<%=basePath %>/images/ui/self.png"></img></a></div>
<div class="menuBtn"><a href="<%=basePath %>/jsp/userlist.jsp" target="workspace"><img src="<%=basePath %>/images/ui/userList.png"></img></a>
</div>
<div class="menuBtn"><a href="<%=basePath %>/jsp/garmentcls.jsp" target="workspace"><img src="<%=basePath %>/images/ui/catalog.png"></img></a>
</div>
<div class="menuBtn"><a href="<%=basePath %>/jsp/garmentlist.jsp" target="workspace"><img src="<%=basePath %>/images/ui/suits.png"></img></a>
</div>
<div class="menuBtn"><a href="<%=basePath %>/jsp/wear.jsp" target="workspace"><img src="<%=basePath %>/images/ui/mySuits.png"></img></a>
</div>
<div class="menuBtn"><a id="logoutBtn" href="#"><img src="<%=basePath %>/images/ui/exit.png"></img></a>
</div>
<script type="text/javascript">

$(document).ready(function (){
	$("#logoutBtn").on("click", function (e){
		e.preventDefault();
		function onLogoutSuccess(rv){
			if(rv.code === -10){
				alert(rv.description);
			}else if(rv.code === 20){
				console.log(rv)
				alert(rv.description);
				parent.window.document.location.href = "<%=basePath%>/" + rv.nextAction;
			}else{
				alert("Unknown error");
			}
		}
		var logoutUrl = "<%=basePath %>/api/auth/logout";
		request("GET", logoutUrl, "", onLogoutSuccess, serverError, false);
	})
})

</script>
</body>
</html>
