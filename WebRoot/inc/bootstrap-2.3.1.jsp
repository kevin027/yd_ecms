<%@ page language="java" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<link href="<%=basePath %>js/bootstrap/bootstrap-2.3.1/css/bootstrap.css" rel="stylesheet" type="text/css" media="screen">
<script src="<%=basePath %>js/bootstrap/bootstrap-2.3.1/js/bootstrap.js" charset="UTF-8" type="text/javascript"></script>