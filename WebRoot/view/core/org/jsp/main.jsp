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
	<title>组织机构管理主页面</title>
	<meta charset="utf-8" />
	<jsp:include page="/view/inc.jsp"></jsp:include>
	<link type="text/css" href="js/jquery/zTree3.5.14/css/zTreeStyle/zTreeStyle.css" rel="stylesheet"/>
	<style type="text/css">
		.ztree li span.button.auditOrg_ico_open{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/server_chart.png")}
		.ztree li span.button.auditOrg_ico_close{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/server_chart.png")}
		.ztree li span.button.auditOrg_ico_docu{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/server_chart.png")}
		.ztree li span.button.department_ico_open{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/shape_align_left.png")}
		.ztree li span.button.department_ico_close{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/shape_align_left.png")}
		.ztree li span.button.department_ico_docu{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/shape_align_left.png")}
		.ztree li span.button.female_ico_open{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/user/user_female.png")}
		.ztree li span.button.female_ico_close{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/user/user_female.png")}
		.ztree li span.button.female_ico_docu{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/user/user_female.png")}
		.ztree li span.button.male_ico_open{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/user/user.png")}
		.ztree li span.button.male_ico_close{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/user/user.png")}
		.ztree li span.button.male_ico_docu{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/user/user.png")}
	</style>
</head>
<body>
	<input type="hidden" id="ztreeNodes" value='${ztreeNodes}' />
	<div class="easyui-layout"  fit="true" >
	    <div region="west" title="组织机构" style="padding:5px; width:300px;">
		    <div class="formSep btn-group" style="width:95%">
		    	<button id="addOrgBtn" class="btn btn-default btn-sm" style="font-size:12px">增加</button>
		    	<button id="delOrgBtn" class="btn btn-default btn-sm" style="font-size:12px">删除</button>
		    </div>
			<div>
				<ul id="orgTree" class="ztree"></ul>
			</div>
	    </div>
	    <div region="center" title="明细" style="padding:5px;" >
			<div id="orgInfo"></div>
	    </div>
	</div>
	<script type="text/javascript" src="js/dynamicloader.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="core/org/js/main.js"></script>
</body>
</html>