<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<link href="<%=basePath %>css/style.css" rel="stylesheet" type="text/css" />

<!-- splashy图标库 -->
<link href="<%=basePath %>img/splashy/splashy.css" rel="stylesheet" type="text/css" />

<!-- 引入自定义样式  -->
<link href="<%=basePath %>js/jquery/EasyUi/css/default.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>js/jquery/EasyUi/css/baseCss.css" rel="stylesheet" type="text/css" media="screen">


<!-- 引入jQuery -->
<script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>

<!-- 引入上传插件plupload -->
<link href="<%=basePath %>js/plupload-2.1.2/js/jquery.plupload.queue/css/jquery.plupload.queue.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath %>js/plupload-2.1.2/js/plupload.full.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/plupload-2.1.2/js/jquery.plupload.queue/jquery.plupload.queue.js"></script>
<script type="text/javascript" src="<%=basePath %>js/plupload-2.1.2/js/i18n/zh_CN.js"></script>


<!-- 引入bootstrap样式 -->
<link href="<%=basePath %>js/bootstrap/bootstrap-2.3.1/css/bootstrap.css" rel="stylesheet" media="screen">
<%-- <script type="text/javascript" src="<%=basePath %>js/bootstrap/bootstrap-3.0.0-dist/dist/js/bootstrap.min.js"></script> --%>
<script type="text/javascript" src="<%=basePath %>js/bootstrap/bootstrap-2.3.1/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath %>js/bootstrap/bootstrap-3.0.0-dist/dist/js/bootstrap-tab.js"></script>

<!-- 引入icheck插件   bootstrap拓展 -->
<link href="<%=basePath %>js/bootstrap/plugin/icheckCss/blue.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="<%=basePath %>js/bootstrap/plugin/jquery.icheck.js"></script>     

<!-- 引入datetimepicker插件   bootstrap拓展 -->
<link href="<%=basePath %>js/bootstrap/plugin/dateTime/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="<%=basePath %>js/bootstrap/plugin/dateTime/js/bootstrap-datetimepicker.min.js"></script>     
<script type="text/javascript" src="<%=basePath %>js/bootstrap/plugin/dateTime/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>     

 <!-- 引入EasyUI -->
<link rel="stylesheet" id="easyuiTheme" type="text/css" href="<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/themes/<c:out value="${cookie.easyuiThemeName.value}" default="metro-blue"/>/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/themes/icon.css" />
<script type="text/javascript" src="<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js"></script>

<!-- 表格插件 -->
<script type="text/javascript" src="<%=basePath %>js/jquery/EasyUi/Application/jquery.edatagrid.js"></script>     


<!-- 引入cookie插件 -->
<script type="text/javascript" src='<%=basePath %>js/jquery/customjs/jquery.cookie.js'> </script>
	
	
<!-- 引入bootstrap popover插件
<script type="text/javascript" src="<%=basePath%>js/bootstrap/bootstrap-3.0.0-dist/dist/js/bootstrap-popover.js"></script>
 -->
<!-- 引入EasyUI Portal插件 -->
<link rel="stylesheet" href="<%=basePath %>js/jquery/EasyUi/jquery-easyui-portal/portal.css" type="text/css">
<script type="text/javascript" src="<%=basePath %>js/jquery/EasyUi/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script>


<!-- 扩展jQuery -->
<script src="<%=basePath %>js/jquery/customjs/syUtils.js" charset="UTF-8" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery/customjs/extJquery.js" charset="utf-8"></script>


<!-- 引入jeasyui.extensions.datagrid.js插件，表头右键菜单-->
<script type="text/javascript" src="<%=basePath%>js/jquery/EasyUi/jquery-easyui-1.3.4/extended/jquery.jdirk.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/extended/icon-all.css" />
<script type="text/javascript" src="<%=basePath%>js/jquery/EasyUi/jquery-easyui-1.3.4/extended/jeasyui.extensions.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery/EasyUi/jquery-easyui-1.3.4/extended/jeasyui.extensions.menu.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery/EasyUi/jquery-easyui-1.3.4/extended/jeasyui.extensions.datagrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery/EasyUi/jquery-easyui-1.3.4/extended/jeasyui.extensions.validatebox.js"></script>

<!-- ztree 用于组织机构树 -->
<link rel="stylesheet" href="<%=basePath %>js/jquery/zTree3.5.14/css/zTreeStyle/zTreeStyle.css" />
<script src="<%=basePath %>js/jquery/zTree3.5.14/js/jquery.ztree.core-3.5.min.js"></script>
<script src="<%=basePath %>js/jquery/zTree3.5.14/js/jquery.ztree.excheck-3.5.min.js"></script>

<!-- 引入FusionCharts图标插件 -->
<script src="<%=basePath %>js/FusionCharts/charts/FusionCharts.js"></script>

<!-- 自适应textarea -->
<script src="<%=basePath %>js/jquery.autosize.min.js"></script>
<script src="<%=basePath %>js/commonData.js"></script>