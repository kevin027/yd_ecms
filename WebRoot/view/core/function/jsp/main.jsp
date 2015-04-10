<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%    
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1    
	response.setHeader("Pragma","no-cache"); //HTTP 1.0    
	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server    
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName()+":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath %>"/>
	<title>系统功能管理主页面</title>
	<meta charset="utf-8" />
	<jsp:include page="/inc.jsp"></jsp:include>
	<link type="text/css" href="js/jquery/zTree3.5.14/css/zTreeStyle/zTreeStyle.css" rel="stylesheet"/>
</head>
<body>
	<input type="hidden" id="ztreeNodes" value='${ztreeNodes}' />
	<div class="easyui-layout"  fit="true" >
	 	<div region="west" title="系统功能" style="padding:5px; width:180px;">
		    <div class="formSep btn-group" style="width:95%">
		    	<button id="addFunctionBtn" class="btn btn-default btn-sm" style="font-size:12px">添加</button>
				<button id="delFunctionBtn" class="btn btn-default btn-sm" style="font-size:12px">删除</button>
		    </div>
			<div>
				<ul id="funTree" class="ztree"></ul>
			</div>
	    </div>
	    <div region="center" title="明细" style="padding:5px;" >
			<div id="functionInfo"></div>
	    </div>
	</div>
	<script type="text/javascript" src="js/dynamicloader.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="core/function/js/main.js"></script>
</body>
</html>