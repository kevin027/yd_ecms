<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath()+"/"; %>
<style>
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
<!-- 引入自定义样式  -->
<link href="core/role/js/plugin/css/chosen.css" rel="stylesheet" type="text/css" />
<script src="core/role/js/plugin/chosen.jquery.js" /></script>
<s:form id="addAccount_form" namespace="/account" action="saveAccount" method="post" cssStyle="padding-top:10px; padding-bottom:0px;">
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
					<td >
			             <s:select list="#request.roles" listKey="id" listValue="name + ' - ' + auditOrg.name" name="roleSlelect" style="width:400px" id="user_languages" multiple="true" data-placeholder="选择角色..." cssClass="span8"></s:select>
					</td>
				</tr>
				<tr>
					<td>关联人员：</td>
					<td>
						<s:hidden id="accountId" name="saveAccountForm.staffId"></s:hidden>
						<div style="width:345px" class="input-append">
						<span style="float:left">
						<s:textfield id="accountName" readonly="true"></s:textfield>
						</span>
					    <button class="btn" name="chooseBtn">选择</button>
					    <button class="btn" name="cancelBtn">取消</button>
						</div>
					</td>
				</tr>
			</table>
		</div>
</s:form>


<script src="core/role/js/plugin/jquery.autosize.js" /></script>
<script src="core/role/js/plugin/gebo_user_profile.js" /></script>
<script src="core/account/js/addAccount.js" /></script>