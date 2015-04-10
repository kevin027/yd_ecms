<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
</head>
<body>
<form namespace="/file" action="upload" method="post" enctype="multipart/form-data">
	<input type="hidden" name="refModule" value="test" />
	<input name="upload" type="file" />
	<input name="upload" type="file" />
</form>

<form namespace="/file" action="download" method="post">
	<input type="text" name="fileId" />
</form>
</body>
</html>