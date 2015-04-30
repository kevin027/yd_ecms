<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<base href="${ctx}"/>
	<title>新增功能类型页面</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body style="text-align:center; width:100%; margin:0 auto;">
	<form action="/fun/saveFunctionType" method="post">
		<ul>
			<li>功能类型编码：<input type="text" name="functionType.id" /></li>
			<li>功能类型名称：<input type="text" name="functionType.name" /></li>
			<li><input type="submit" value="提交"/></li>
		</ul>
	</form>
	
	<form action="/fun/saveFunction" method="post">
		<ul>
			<li>菜单名称：<input type="text" name="function.name" /></li>
			<li>菜单排序：<input type="text" name="functionType.name" maxlength="2"/></li>
			<li><input type="submit" value="提交"/></li>
		</ul>
	</form>
</body>
</html>