<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form id="saveAuditOrgForm" >
	<fieldset style="padding:5%;padding-top:0px">
	    <!-- 响应式布局类  table-responsive-->
		<div class="table-responsive ">
		    <div class="formSep">
		    <div class="row">
			    <div style="visibility:hidden">
			       <button class="btn" style="float:right;" id="addAuditOrg_saveBtn">保存</button>
			    </div>
			</div>
		    </div>
			<table class="table table-bordered table-condensed" >
				<tr>
					<td>机构名称：</td>
					<td><input type="text" name="name" value="${form.name}" style="width:80%" /></td>
				</tr>
				<tr>
					<td>机构编码：</td>
					<td><input type="text" name="code" value="${form.code}" style="width:80%" /></td>
				</tr>
				<tr>
					<td>机构排序：</td>
					<td><input type="text" name="sortCode" value="${form.sortCode}" maxlength="2" style="width:80%"/></td>
				</tr>
			</table>
		</div>
	</fieldset>
</form>
<script>
$("table tr td").css({"padding-left":"15px","line-height":"50px"}).find('input:text').attr('readonly', true);
</script>