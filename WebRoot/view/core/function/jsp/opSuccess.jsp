<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%     
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath %>"/>
	<title>功能添加页面</title>
	<meta charset="utf-8" />
	<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
	<link rel="stylesheet" type="text/css" href="js/jquery/layer-1.6/skin/layer.css" />
	<script type="text/javascript" src="js/jquery/layer-1.6/layer.min.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script>
		$(function() {
			var msg = "你的操作成功。";
			showInfo(msg, function() {
				if (parent && parent.layer && parent.layer.getFrameIndex(window.name)) {
					var i = parent.layer.getFrameIndex(window.name);
					parent.layer.close(i);
				}
			});
		});
	</script>
</head>
<body style="text-align:center; width:100%; margin:0 auto;">
	
</body>
</html>