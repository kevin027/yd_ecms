<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 引入自定义样式  -->
<link href="<%=basePath %>core/role/js/plugin/css/chosen.css" rel="stylesheet" type="text/css" />
<s:form id="u_form" class="form-horizontal form_validation_ttip">
	<s:hidden name="saveRoleForm.roleId" id="roleId"></s:hidden>
	<fieldset style="padding:20px">
	<table class="table">
		<tr>
			<td>角色名称</td>
			<td><s:textfield name="saveRoleForm.roleName"/></td>
		</tr>
		<tr>
			<td>是否有效</td>
			<td>
			<s:select name="saveRoleForm.invalid" height="50px" list="#{false:'有效', true:'无效'}" />
			</td>
		</tr>
		
		<s:if test="null != #request.auditOrgs">
		<tr>
			<td>所属机构</td>
			<td>
		        <s:select name="saveRoleForm.auditOrgId" list="#request.auditOrgs" listKey="id" listValue="name" headerKey="" headerValue="请选择"/>
			</td>
		</tr>
		</s:if>
		
		<tr>
			<td>备注</td>
			<td><s:textarea name="saveRoleForm.remark" rows="8" style="width:486px" cssClass="input-xlarge" data-placeholder="点击添加备注..."></s:textarea></td>
		</tr>
	</table>
	</fieldset>
	
	<!-- 流式布局 保存清空-->
	<div class="row-fluid" style="padding-bottom:0px" id="opBar">
		<div class="span11">
			<div class="control-group">
				<label for="u_signature" class="control-label"></label>
				<div class="controls" style="margin-top:15px">
					<span id="saveBtn" class="btn btn-gebo" style="margin-left:5px;float:right">保存</span>
				</div>
			</div>
		</div>
	</div>
</s:form>
<script src="<%=basePath%>core/role/js/modRole.js"></script>
<script src="<%=basePath%>core/role/js/plugin/chosen.jquery.js"></script>
<script src="<%=basePath%>core/role/js/plugin/jquery.autosize.js"></script>
<script src="<%=basePath%>core/role/js/plugin/gebo_user_profile.js"></script>