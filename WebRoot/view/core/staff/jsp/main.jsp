<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<base href="${ctx}"/>
	<title>人员管理主页面</title>
	<meta charset="utf-8" />
	<jsp:include page="/view/inc.jsp"></jsp:include>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<!-- 查询条件 -->
		<div id="queryBar" data-options="region:'north',title:'查询条件',border:false" style="height:90px; overflow:hidden; padding:10px">
			<div class="well" style="margin:0px;padding:5px" >
				<table class="tableForm">
					<tr>
						<th style="padding-left:10px">用户名称</th>
						<td colspan="2" style="padding-left:10px">
							<input name="staffName" type="text" class="span1" placeholder="请输入用户名称"/>
						</td>
						<th style="padding-left:10px">创建日期</th>
						<td colspan="2" style="padding-left:10px">
							<input name="createDateFrom" class="easyui-datebox" editable="false" style="width: 100px;height: 28px;" />
							 至 
							<input name="createDateTo" class="easyui-datebox" editable="false" style="width: 100px;height: 28px;" />
						</td>
					</tr>
				</table>
			</div>
		</div>
		
		<!-- 工具栏 -->
		<div id="toolbar" class="datagrid-toolbar" style="height: auto;display: none;">			
			<div>
                <c:forEach items="${pageMenuFuns}" var="o" varStatus="st">
                    <c:if test="${o.type eq 'BUTTON'}">
                        <a class="easyui-linkbutton" iconCls="${o.icon}" plain="false" href="javascript:void(0);" id="${o.code}">${o.name}</a>
                    </c:if>
                </c:forEach>
				<!-- <a class="easyui-linkbutton" iconCls="icon-user_add" plain="false" href="javascript:void(0);" id="addUserBaseInfo">新增人员详细信息</a> -->
				<a class="easyui-linkbutton" iconCls="icon-undo" plain="false" href="javascript:void(0);" id="cancelSelectBtn">取消选中</a>
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
	<script type="text/javascript" src="${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/extended/jeasyui.extensions.js"></script>
	<script type="text/javascript" src="${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/extended/jeasyui.extensions.menu.js"></script>
	<script type="text/javascript" src="${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/extended/jeasyui.extensions.datagrid.js"></script>
	<script type="text/javascript" src="${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/extended/jeasyui.icons.all.js"></script>
	<script src="${ctx}view/core/staff/js/main.js"></script>
</body>
</html>