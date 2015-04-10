<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%    
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1    
	response.setHeader("Pragma","no-cache"); //HTTP 1.0    
	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server    
%>
<s:form id="modDepartmentForm" >
	<s:hidden name="saveDepartmentForm.id"></s:hidden>
	<s:hidden name="saveDepartmentForm.parentId"></s:hidden>
	<fieldset style="padding:5%;padding-top:0px">
		<!-- 响应式布局类  table-responsive-->
		<div class="table-responsive">
		    <div class="formSep">
		    <div class="row">
				<%-- <s:if test="%{#department.isOrg != 1}">
					<button class="btn pull-right"  name="saveBtn">保存</button>
				</s:if> --%>
				<c:if test="${department.isOrg ne 1}">
					<button class="btn pull-right"  name="saveBtn">保存</button>
				</c:if>
			</div>
		    </div>
			<table class="table table-bordered table-condensed" >
				<tr>
					<c:if test="${department.isOrg ne 1}"><td>科室名称：</td></c:if>
					<c:if test="${department.isOrg eq 1}"><td>单位名称：</td></c:if>
					<td><s:textfield name="saveDepartmentForm.name" style="width:80%" /></td>
				</tr>
				<tr>
					<c:if test="${department.isOrg ne 1}"><td>科室编码：</td></c:if>
					<c:if test="${department.isOrg eq 1}"><td>单位编码：</td></c:if>
					<td><s:textfield name="saveDepartmentForm.code" style="width:80%" /></td>
				</tr>
				<tr>
					<c:if test="${department.isOrg ne 1}"><td>科室负责人：</td></c:if>
					<c:if test="${department.isOrg eq 1}"><td>单位负责人：</td></c:if>
					<td>
						<s:hidden name="saveDepartmentForm.leaderId" />
						<span class="input-large uneditable-input pop_over" 
	                        	data-content="请通过选择按钮选择人员" data-placement="top">
	                     	<s:property value="saveDepartmentForm.leaderName" />
	                 	</span>
	                 	<span class="btn staffSelector">选择</span>
					</td>
				</tr>
				<tr>
					<c:if test="${department.isOrg ne 1}"><td>科室排序：</td></c:if>
					<c:if test="${department.isOrg eq 1}"><td>单位排序：</td></c:if>
					<td><s:textfield name="saveDepartmentForm.sortCode" maxlength="2" style="width:80%" /></td>
				</tr>
			</table>
		</div>
	</fieldset>
</s:form>
<script>
  var mf = $('#modDepartmentForm');
  mf.find('[name=saveBtn]').on('click', function() {
    $('#modDepartmentForm').form('submit', {
      'url' : 'org/updateDepartment.do'
      ,success : function(result) {
    	result = JSON.parse(result);
	    var resultMessage;
	    if (result.success) {
		  resultMessage = result.success;
		  var selNodes = orgTreeObj.getSelectedNodes();
		  if (0 < selNodes.length){
			  selNodes[0].name = $.trim($('[name="saveDepartmentForm.name"]').val());
			  orgTreeObj.updateNode(selNodes[0]);
		  }
		} else {
		  resultMessage = result.error;
        }
        $.messager.show({
          title:'提示'
          ,msg: resultMessage
          ,timeout:2000
          ,showType:'slide'
        });
      }
    });
    return false;
  });
  
  mf.find('span.staffSelector').on('click', function() {
	var op = $(this);
    chooseStaff(parent, function(staff) {
      op.prev().text(staff.name).prev().val(staff.id);
    });
    return false;
  });
  
  $("table tr td").css({"padding-left":"15px","line-height":"50px"});
</script>