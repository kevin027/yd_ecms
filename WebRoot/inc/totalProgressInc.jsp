<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

	<script type="text/javascript" src='<%=basePath %>js/jquery-1.9.1.min.js'> </script>
		<!-- 引入bootstrap样式-->
	<link href="<%=basePath %>js/bootstrap/bootstrap-2.3.1/css/bootstrap.css" rel="stylesheet" media="screen">
	<link href="<%=basePath %>js/bootstrap/bootstrap-3.0.0-dist/dist/css/bootstrap3.css" rel="stylesheet" media="screen">
	<link href="<%=basePath %>css/style.css" rel="stylesheet" media="screen">
	<!-- 引入easyui样式-->
	<link href="<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/themes/icon.css" rel="stylesheet" media="screen">
	<link id="easyuiTheme" href="<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/themes/<c:out value="${cookie.easyuiThemeName.value}" default="metro-green"/>/easyui.css" rel="stylesheet" media="screen">
	

	
	<!-- 引入自定义样式     编辑表格
	<link href="js/bootstrap/plugin/editBootstrapTable/x-editable/bootstrap-editable/css/bootstrap-editable.css" rel="stylesheet" type="text/css" />
	-->
	
	<!--引入bootstrap插件-->
	<script type="text/javascript" src='<%=basePath %>js/bootstrap/bootstrap-2.3.1/js/bootstrap.js'> </script>
	<script type="text/javascript" src='<%=basePath %>business/bm/js/jquery.actual.min.js'> </script>
	<script type="text/javascript" src='<%=basePath %>business/bm/js/jquery_cookie.min.js'> </script>
	<script type="text/javascript" src='<%=basePath %>business/bm/js/antiscroll.js'> </script>
	<script type="text/javascript" src='<%=basePath %>business/bm/js/selectNav.js'> </script>
	<script type="text/javascript" src='<%=basePath %>business/bm/js/gebo_common.js'> </script>
	
	<!-- 引入easyui插件-->
	<script type="text/javascript" src='<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/jquery.easyui.min.js'> </script>
	<script type="text/javascript" src="<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js"></script>
	
	<!--选择部门  -->
	<!-- <script src="core/role/js/plugin/chosen.jquery.js" ></script>
	<script src="core/role/js/plugin/jquery.autosize.js" ></script>
	<script src="core/role/js/plugin/gebo_user_profile.js" ></script> -->
	<!-- 引入自定义样式     选择控件-->
	<!-- <link href="core/role/js/plugin/css/chosen.css" rel="stylesheet" type="text/css" /> -->
	<!--选择部门  -->
	<link href="<%=basePath %>js/jquery/chosen/css/chosen.css" rel="stylesheet" type="text/css" />
	<script src="<%=basePath %>js/jquery/chosen/chosen.jquery.js" ></script>
	
	<script src="js/jquery/EasyUi/jquery-easyui-portal/extJquery.js" ></script>
	<script src="js/jquery.autosize.min.js"></script>