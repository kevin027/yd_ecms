<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style>
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

<!-- 引入自定义样式  -->
<link href="${ctx}view/core/role/js/plugin/css/chosen.css" rel="stylesheet" type="text/css" />
<script src="${ctx}view/core/role/js/plugin/chosen.jquery.js" /></script>

<form id="addAccount_form" action="${ctx}account/saveAccount" method="post" style="padding-top:10px; padding-bottom:0px;">
	<input type="hidden" name="id" value="${form.id}"/>
	<input type="hidden" name="roleIds" value="${form.roleIds}"/>
		<!-- 响应式布局类  table-responsive-->
		<div class="table-responsive " style="padding:10px">
		    <div class="row">
				<button class="btn" style="float:right;" name="saveBtn">保存</button>
			</div>
			<table class="table" style="margin-top:10px">
				<tr>
					<td>账号名称：</td>
					<td><input type="text" name="accounts" value="${form.accounts}"/></td>
				</tr>
				<tr>
					<td>是否有效：</td>
					<td>
                        <select id="invalid" name="invalid">
                            <option value="0" <c:if test="${form.invalid eq false }">selected="selected"</c:if>>有效</option>
                            <option value="1" <c:if test="${form.invalid eq true }">selected="selected"</c:if>>无效</option>
                        </select>
                    </td>
				</tr>
				<tr>
					<td>角色选择： </td>
					<td >
                        <select name="roleIds" style="width:400px" id="user_languages" multiple="true" data-placeholder="选择角色..." class="span8">
                        <c:forEach items="${roles}" var="r" varStatus="st">
                            <option value="${r.id}"> ${r.name} </option>
                        </c:forEach>
                        </select>
					</td>
				</tr>
				<tr>
					<td>关联人员：</td>
					<td>
						<input type="hidden" id="accountId" name="staffId" value="${form.staffId}"/>
						<div style="width:345px" class="input-append">
						<span style="float:left">
						    <input type="text" id="accountName" value="${form.staffName}" readonly="true"/>
						</span>
					    <button class="btn" name="chooseBtn">选择</button>
					    <button class="btn" name="cancelBtn">取消</button>
						</div>
					</td>
				</tr>
			</table>
		</div>
</form>


<script src="${ctx}view/core/role/js/plugin/jquery.autosize.js" /></script>
<script src="${ctx}view/core/role/js/plugin/gebo_user_profile.js" /></script>
<script src="${ctx}view/core/account/js/addAccount.js" /></script>