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
<title>梦想试衣间</title>
<link rel="stylesheet" href="<%=basePath%>/css/main.css">
<script type="text/javascript" src="<%=basePath%>/js/main.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.min.js"></script>
</head>
<frameset rows="70px,*" border="0">       
	<frame src="<%=basePath%>/jsp/banner.jsp" scrolling="no"/>
	<frameset cols="140px,*" border="1">
		<frame src="<%=basePath%>/jsp/menu.jsp" />
		<frame name="workspace" src="<%=basePath%>/jsp/userinfo.jsp" />
 	</frameset>	
 </frameset>
</html>