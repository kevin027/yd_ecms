<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 引入EasyUI -->
<link href="<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/themes/metro/easyui.css" rel="stylesheet" type="text/css" media="screen">
<link href="<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/themes/icon.css" rel="stylesheet" type="text/css" media="screen">
<script src="<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/jquery.easyui.min.js" charset="UTF-8" type="text/javascript"></script>
<script src="<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js" charset="UTF-8" type="text/javascript"></script>

