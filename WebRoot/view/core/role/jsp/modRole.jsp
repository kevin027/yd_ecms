<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- 引入自定义样式  -->
<link href="${ctx}view/core/role/js/plugin/css/chosen.css" rel="stylesheet" type="text/css" />
<form id="u_form" class="form-horizontal form_validation_ttip">
    <input type="hidden" name="roleId" id="roleId" value="${form.roleId}"/>
	<fieldset style="padding:20px">
	<table class="table">
		<tr>
			<td>角色名称</td>
			<td><input type="text" name="roleName" value="${form.roleName}"/></td>
		</tr>
		<tr>
			<td>是否有效</td>
			<td>
                <select id="invalid" name="invalid">
                    <option value="0" <c:if test="${form.invalid eq false }">selected="selected"</c:if>>有效</option>
                    <option value="1" <c:if test="${form.invalid eq true }">selected="selected"</c:if>>无效</option>
                </select>
			</td>
		</tr>
		
		<s:if test="null != #request.auditOrgs">
		<tr>
			<td>所属机构</td>
			<td>
                <select name="auditOrgId" style="width:400px" data-placeholder="选择机构...">
                    <c:forEach items="${auditOrgs}" var="org" varStatus="st">
                        <option value="${org.id}" <c:if test="${org.id eq form.auditOrgId}">selected="true"</c:if>>
                                ${org.name}
                        </option>
                    </c:forEach>
                </select>
			</td>
		</tr>
		</s:if>
		
		<tr>
			<td>备注</td>
			<td><textarea name="remark" rows="6" style="width:486px" class="input-xlarge" data-placeholder="点击添加备注...">${form.remark}</textarea></td>
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
</form>
<script src="${ctx}view/core/role/js/modRole.js"></script>
<script src="${ctx}view/core/role/js/plugin/chosen.jquery.js"></script>
<script src="${ctx}view/core/role/js/plugin/jquery.autosize.js"></script>
<script src="${ctx}view/core/role/js/plugin/gebo_user_profile.js"></script>