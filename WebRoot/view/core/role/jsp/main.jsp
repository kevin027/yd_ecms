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
	<style type="text/css">
	.dbn{display:none}
	.dbb{display:block}
	    .ztree li span.button.company_ico_open{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/server_chart.png")}
		.ztree li span.button.company_ico_close{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/server_chart.png")}
		.ztree li span.button.company_ico_docu{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/server_chart.png")}
		.ztree li span.button.department_ico_open{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/shape_align_left.png")}
		.ztree li span.button.department_ico_close{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/shape_align_left.png")}
		.ztree li span.button.department_ico_docu{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/shape_align_left.png")}
		.ztree li span.button.female_ico_open{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/user/user_female.png")}
		.ztree li span.button.female_ico_close{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/user/user_female.png")}
		.ztree li span.button.female_ico_docu{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/user/user_female.png")}
		.ztree li span.button.male_ico_open{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/user/user.png")}
		.ztree li span.button.male_ico_close{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/user/user.png")}
		.ztree li span.button.male_ico_docu{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/user/user.png")}
	</style>
</head>
<body>
	<div class="easyui-layout"  fit="true" >
	    <div id="content" region="center"  >
			<div class="easyui-layout" data-options="fit:true,border:false">
			
				<!-- 查询条件 -->
				<div id="queryBar" data-options="region:'north',title:'查询条件',border:false" style="height:80px; overflow:hidden; padding:10px">
					<div class="well" style="margin:0px;padding:2px" >
						<table class="tableForm">
							<tr>
								<th style="padding-left:10px">角色名称</th>
								<td style="padding-left:10px">
									<input name="query.roleName" type="text" class="span1" placeholder="请输入角色名称..." />
								</td>
								<!-- <th style="padding-left:10px">人员名称</th>
								<td style="padding-left:10px">
									<input name="query.staffName" type="text" class="span1" placeholder="请输入人员名称..."  />
								</td> -->
							</tr>
						</table>
					</div>
				</div>
				
				<!-- 菜单栏 -->
				<div id="toolbar" class="datagrid-toolbar" style="height: auto;display: none;">			
					<div>
                        <c:forEach items="${pageMenuFuns}" var="o" varStatus="st">
                            <c:if test="${o.type eq 'BUTTON'}">
                                <a class="easyui-linkbutton" iconCls="${o.icon}" plain="false" href="javascript:void(0);" id="${o.code}">${o.name}</a>
                            </c:if>
                        </c:forEach>
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
	    </div>	
	    
	    <div region="east" title="授权栏" collapsible=true style="width:320px;" border="false">
	    	<div class="easyui-layout" data-options="fit:true,border:false">
				<div data-options="region:'center',border:false">
					
					<%-- 授权栏 --%>
				    <div class="easyui-accordion" fit="true">
				    	<%-- 功能授权 --%>
				    	<c:if test="${isFunctionEmpowerBtn eq true}">
					    <div title="功能授权" data-options="iconCls:'icon-bussAuthorization'" style="overflow:auto;padding:10px;">
        		    		<div class="well" style="margin:0px;padding:2px;" >
				    			<a id="functionEmpowerBtn" href="javascript:void(0)" width="80px" class="easyui-linkbutton" data-options="iconCls:'icon-authorization',plain:true">授权</a>
				    			<a id="functionCheckAllBtn" href="javascript:void(0)" width="80px" class="easyui-linkbutton" data-options="iconCls:'icon-flag_orange',plain:true">全选/取消</a>
				    			<a id="functionExpandAllBtn" href="javascript:void(0)" width="80px" class="easyui-linkbutton" data-options="iconCls:'icon-arrow_out',plain:true">展开</a>
				    			<a id="functionCollapseAllBtn" href="javascript:void(0)" width="80px" class="easyui-linkbutton" data-options="iconCls:'icon-arrow_in',plain:true">收起</a>
				    		</div>
					        <ul class="ztree" id="functionTree"></ul>
					    </div>
					    </c:if>
					    
					    <%-- 账号授权 --%>
					    <c:if test="${isAccountEmpowerBtn eq true}">
					    <div title="账号授权" data-options="iconCls:'icon-peopleAuthorization'" style="padding:10px;">
				        	<div class="well" style="margin:0px;padding:2px" >
				    			<a id="accountEmpowerBtn" href="javascript:void(0)" width="80px" class="easyui-linkbutton" data-options="iconCls:'icon-authorization',plain:true">授权</a>
				    			<a id="accountCheckAllBtn" href="javascript:void(0)" width="80px" class="easyui-linkbutton" data-options="iconCls:'icon-flag_orange',plain:true">全选/取消</a>
				    			<a id="accountExpandAllBtn" href="javascript:void(0)" width="80px" class="easyui-linkbutton" data-options="iconCls:'icon-arrow_out',plain:true">展开</a>
				    			<a id="accountCollapseAllBtn" href="javascript:void(0)" width="80px" class="easyui-linkbutton" data-options="iconCls:'icon-arrow_in',plain:true">收起</a>
				    		</div>
				    		<ul class="ztree" id="accountTree"></ul>
					    </div>
					    </c:if>
					    
					</div>
				</div>
			</div>
	    </div>
	</div>
	
	<!-- 引入jeasyui.extensions.datagrid.js插件-->
	<script type="text/javascript" src="${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/extended/jeasyui.extensions.js"></script>
	<script type="text/javascript" src="${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/extended/jeasyui.extensions.menu.js"></script>
	<script type="text/javascript" src="${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/extended/jeasyui.extensions.datagrid.js"></script>
	<script type="text/javascript" src="${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/extended/jeasyui.icons.all.js"></script>
	<script src="${ctx}view/core/role/js/main.js"></script>
</body>
</html>