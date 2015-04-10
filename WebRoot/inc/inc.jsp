<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!-- splashy图标库 -->
<link href="<%=basePath %>img/splashy/splashy.css" rel="stylesheet" type="text/css" />


<!-- 引入jQuery -->
<script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>

