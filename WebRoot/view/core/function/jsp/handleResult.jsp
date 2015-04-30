<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<base href="${ctx}"/>
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