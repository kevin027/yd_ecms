<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%    
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName()+":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath %>"/>
	<title>系统操作日志查询主页面</title>
	<meta charset="utf-8" />
	<jsp:include page="/inc.jsp"></jsp:include>
</head>
<body>
	
	<div class="easyui-layout" data-options="fit : true,border : false">
		<!-- 查询条件 -->
		<div id="queryBar" data-options="region:'north',title:'查询条件',border:false" style="height:90px; overflow:hidden; padding:10px">
			<div class="well" style="margin:0px;padding:5px" >
				<table class="tableForm " >
					<tr>
						<th style="padding-left:10px">操作人</th>
						<td colspan="2" style="padding-left:10px">
							<input name="query.operator" type="text" class="span1 search-query" placeholder="请输入人员名称"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		
		<!-- 工具栏 -->
		<div id="toolbar" class="datagrid-toolbar" style="height: auto;display: none;">			
			<div>
				<a class="easyui-linkbutton" iconCls="icon-search" plain="false" href="javascript:void(0);" id="searchBtn">查找</a>
				<a class="easyui-linkbutton" iconCls="icon-bin" plain="false" href="javascript:void(0);" id="resetBtn">清空</a>
			</div>
		</div>
		
		<!-- 表格数据 -->
		<div data-options="region:'center',border:false,toolbar:toolbar">
			<table id="datagrid"></table>
		</div>
	</div>
	
	<!-- 引入jeasyui.extensions.datagrid.js插件-->
	<script type="text/javascript" src="<%=basePath%>js/jquery/EasyUi/jquery-easyui-1.3.4/extended/jeasyui.extensions.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery/EasyUi/jquery-easyui-1.3.4/extended/jeasyui.extensions.menu.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery/EasyUi/jquery-easyui-1.3.4/extended/jeasyui.extensions.datagrid.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery/EasyUi/jquery-easyui-1.3.4/extended/jeasyui.icons.all.js"></script>
	<script src="<%=basePath %>log/js/main.js"></script>
</body>
</html>