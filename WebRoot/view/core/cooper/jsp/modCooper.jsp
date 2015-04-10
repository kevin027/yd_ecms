<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath()+"/"; %>
<!-- 引入自定义样式  -->
<link href="<%=basePath %>core/role/js/plugin/css/chosen.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath %>core/role/js/plugin/chosen.jquery.js" /></script>
<s:form id="saveCooperForm" method="post" 
	namespace="/cooper" action="updateCooper" cssStyle="padding-top:10px; padding-bottom:0px;">
	
	<!-- 隐藏域 -->
	<s:hidden name="cooper.id" />
	<s:hidden name="cooper.handler.id" id="handlerId"/>
	<s:hidden name="cooper.handler.name" id="handlerName"/>
	<s:hidden name="cooper.createDate" />
	<s:hidden name="cooper.auditOrgId" />
	<s:hidden name="cooper.orgDepId" />
	<!-- 响应式布局类  table-responsive-->
	<div class="table-responsive " style="padding:10px">
		<div class="row">
			<button class="btn" style="float:right;" name="saveBtn">保存</button>
		</div>
		<table class="table " style="margin-top:10px">
			<tr>
				<td>单位名称：</td>
				<td colspan="3">
					<s:textfield name="cooper.name"  cssStyle="width:99%;"
						cssClass="easyui-validatebox" data-options="required:true,validType:'length[2,32]'"/>
				</td>
			</tr>
			<tr>
				<td>单位地址：</td>
				<td colspan="3">
					<s:textfield name="cooper.address"  cssStyle="width:99%;"
						cssClass="easyui-validatebox" data-options="required:true,validType:'length[2,128]'"/>
				</td>
			</tr>
			<tr>
				<td>邮政编码：</td>
				<td colspan="3">
					<s:textfield name="cooper.postcode" data-options="" 
						cssClass="easyui-validatebox" validType="zip" />
				</td>
			</tr>
			<tr>
				<td>单位电话：</td>
				<td>
					<s:textfield name="cooper.unitPhone" data-options="required:true"  
						cssClass="easyui-validatebox"  validType="phone"/>
				</td>
				<td>单位传真：</td>
				<td>
					<s:textfield name="cooper.fax" data-options="" 
						cssClass="easyui-validatebox"  validType="phone"  />
				</td>
			</tr>
			<tr>
				<td>联系人：</td>
				<td>
					<s:textfield name="cooper.linkUser" 
						cssClass="easyui-validatebox" data-options="required:true,validType:'length[2,32]'" />
				</td>
				<td>联系电话：</td>
				<td>
					<s:textfield name="cooper.linkPhone" data-options="required:true"  
						cssClass="easyui-validatebox"  validType="phoneOrMobile"/>
				</td>
			</tr>
			<tr>
				<td>状&nbsp;&nbsp;态：</td>
				<td>
					<s:select name="cooper.invalid" height="50px" list="#{false:'有效', true:'无效'}" />
				</td>
				<td>排&nbsp;&nbsp;序：</td>
				<td>
					<s:textfield name="cooper.sort" cssClass="easyui-numberbox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<td>备&nbsp;&nbsp;注：</td>
				<td colspan="3">
					<s:textarea name="cooper.remark" cssClass="easyui-validatebox" cssStyle="width:99%;" />
				</td>
			</tr>
		</table>
	</div>
	
</s:form>
<script src="<%=basePath %>core/role/js/plugin/jquery.autosize.js" /></script>
<script src="<%=basePath %>core/cooper/js/gebo_unit_profile.js" /></script>
<script src="<%=basePath %>core/cooper/js/modCooper.js" /></script>
