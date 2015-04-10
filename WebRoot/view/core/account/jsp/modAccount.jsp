<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath()+"/"; %>
<!-- 引入自定义样式  -->
<link href="core/role/js/plugin/css/chosen.css" rel="stylesheet" type="text/css" />
<s:form id="modAccount_form" namespace="/account" action="updateAccount" method="post" cssStyle="padding-top:10px; padding-bottom:0px;">
	<s:hidden name="saveAccountForm.id" />
	<s:hidden name="saveAccountForm.roleIds" />
		<!-- 响应式布局类  table-responsive-->
		<div class="table-responsive " style="padding:10px">
		    <div class="row">
				<button class="btn" style="float:right;" name="saveBtn">保存</button>
			</div>
			<table class="table" style="margin-top:10px">
				<tr>
					<td>账号名称：</td>
					<td><s:textfield name="saveAccountForm.accounts" /></td>
				</tr>
				<tr>
					<td>是否有效：</td>
					<td><s:select name="saveAccountForm.invalid" height="50px" list="#{false:'有效', true:'无效'}" /></td>
				</tr>
				<tr>
					<td>角色选择： </td>
					<td>
						<select name="roleSlelect" style="width:400px" id="user_languages" multiple="true" data-placeholder="选择角色..." cssClass="span8">
							<s:iterator id="cur" value="#request.roles">
								<option value="<s:property value="#cur.id" />"
									<s:if test="null != saveAccountForm.roleIds && saveAccountForm.roleIds.contains(#cur.id)">selected="true"</s:if>
								>
									<s:property value="#cur.name" />
								</option>
							</s:iterator>
						</select>
					</td>
				</tr>
				<tr>
					<td>关联人员: </td>
					<td>
					   <s:hidden id="accountId" name="saveAccountForm.staffId"></s:hidden>
					   	<div style="width:345px" class="input-append">
						<span style="float:left">
					    <s:textfield id="accountName" name="saveAccountForm.staffName" readonly="true"></s:textfield>
						</span>
					    <button class="btn" name="chooseBtn">选择</button>
					    <button class="btn" name="cancelBtn">取消</button>
						</div>
					</td>
				</tr>
				
			</table>
		</div>
</s:form>
<script src="core/role/js/plugin/chosen.jquery.js" /></script>
<script src="core/role/js/plugin/jquery.autosize.js" /></script>
<script src="core/role/js/plugin/gebo_user_profile.js" /></script>
<script src="core/account/js/modAccount.js"></script>