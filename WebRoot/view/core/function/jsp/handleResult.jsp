<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%    
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1    
	response.setHeader("Pragma","no-cache"); //HTTP 1.0    
	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server    
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath %>"/>
	<title>处理结果页面</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body style="text-align:center; width:100%; margin:0 auto;">
	<script>
		function forward() {
	      window.location.href = '<%=request.getAttribute("returnPage") %>';
	    }
		window.onload = function() {
		  window.setTimeout(forward, 1000);
		};
	</script>
</body>
</html>