<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath()+"/"; %>
<!-- 引入自定义样式  -->
<link href="<%=basePath %>core/role/js/plugin/css/chosen.css" rel="stylesheet" type="text/css" />

<s:form id="saveCooperPersonForm" method="post" 
	namespace="/cooperPerson" action="saveCooperPerson" cssStyle="padding-top:10px; padding-bottom:0px;">
	
	<!-- 隐藏域 -->
	<s:hidden name="cooperPerson.id" id="saveId" />
	<s:hidden name="cooperPerson.cooper.id" id="cooperId"/>
	<s:hidden name="cooperPerson.sysStaffId" id="sysStaffId"/>
	<!-- 响应式布局类  table-responsive-->
	<div class="table-responsive " style="padding:10px">
		<div class="row">
			<button class="btn" style="float:right;" name="saveBtn">保存</button>
		</div>
		<table class="table " style="margin-top:10px">
			<tr>
				<td>姓名：</td>
				<td>
					<s:textfield name="cooperPerson.name" cssClass="easyui-validatebox" data-options="required:true" />
				</td>
				<td>身份证：</td>
				<td>
					<s:textfield name="cooperPerson.identityCard" cssClass="easyui-validatebox" data-options="required:true,validType:'idcared'" />
				</td>
			</tr>
			<tr>
				<td>性别：</td>
				<td>
					<s:select name="cooperPerson.sex" height="50px" list="#{1:'男', 2:'女', 3:'其他'}" />
				</td>
				<td>出生日期：</td>
				<td>
					<input type="text" class="easyui-datebox input-large" 
		                    	style="height:30px;line-height:30px;width:220px;" name="cooperPerson.birthDate" 
		                    	value="<s:date name="cooperPerson.birthDate" format="yyyy-MM-dd"/>"
		                    	data-options="editable:false" />
				</td>
			</tr>
			<tr>
				<td>毕业学校：</td>
				<td>
					<s:textfield name="cooperPerson.shcool" cssClass="easyui-validatebox" data-options="" />
				</td>
				<td>所学专业：</td>
				<td>
					<s:textfield name="cooperPerson.specialty" cssClass="easyui-validatebox" data-options="" />
				</td>
			</tr>
			<tr>
				<td>家庭地址：</td>
				<td colspan="3">
					<s:textfield name="cooperPerson.familyAddress" cssClass="easyui-validatebox" data-options="" cssStyle="width:99%;"/>
				</td>
			</tr>
			<tr>
				<td>职务：</td>
				<td>
					 <s:textfield name="cooperPerson.position" cssClass="easyui-validatebox" data-options="" /> 
				</td>
				<td>工作年限：</td>
				<td>
					<input type="text" id="workYears" class="easyui-numberspinner" 
		                    	style="height:30px;line-height:30px;width:220px;" name="cooperPerson.workYears" 
		                    	value='${cooperPerson.workYears}' data-options="min:1,max:100,editable:true" />
					年
				</td>
			</tr>
			<tr>
				<td>联系电话：</td>
				<td>
					<s:textfield name="cooperPerson.telephone" cssClass="easyui-validatebox" data-options="" />
				</td>
				<td>办公电话：</td>
				<td>
					<s:textfield name="cooperPerson.officeTel" cssClass="easyui-validatebox" data-options="" />
				</td>
			</tr>
			<tr>
				<td>手机： </td>
				<td>
		        	<s:textfield name="cooperPerson.phone" cssClass="easyui-validatebox" data-options="required:true,validType:'mobile'" />
				</td>
				<td>邮箱：</td>
				<td>
					<s:textfield name="cooperPerson.email"  cssClass="easyui-validatebox" data-options="validType:'email'" />
				</td>
			</tr>
			<tr>
				<td>邮政编码：</td>
				<td>
					<s:textfield name="cooperPerson.postcode"  cssClass="easyui-validatebox" data-options="" />
				</td>
				<td>状态：</td>
				<td>
					<s:select name="cooperPerson.invalid" height="50px" list="#{false:'有效', true:'无效'}" />
				</td>
			</tr> 
			<tr>
				<td>备&nbsp;&nbsp;注：</td>
				<td colspan="3">
					<s:textarea name="cooperPerson.remark" cssClass="easyui-validatebox" cssStyle="width:99%;" />
				</td>
			</tr>
		</table>
	</div>
	
</s:form>
<script src="<%=basePath %>core/cooper/js/addCooperPerson.js" /></script>
