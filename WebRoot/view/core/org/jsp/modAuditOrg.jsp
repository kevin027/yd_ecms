<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<s:form id="modAuditOrgForm">
	<s:hidden name="saveAuditOrgForm.id"></s:hidden>
	<fieldset style="padding:5%;padding-top:0px">
		<!-- 响应式布局类  table-responsive-->
		<div class="table-responsive ">
			<div class="formSep">
				<div class="row">
					<div>
						<button class="btn" style="float:right;" name="saveBtn">保存</button>
					</div>
				</div>
			</div>
			<table class="table table-bordered table-condensed">
				<tr>
					<td>机构名称：</td>
					<td><s:textfield name="saveAuditOrgForm.name" style="width:80%" /></td>
				</tr>
				<tr>
					<td>机构编码：</td>
					<td><s:textfield name="saveAuditOrgForm.code" style="width:80%" /></td>
				</tr>
				<tr>
					<td>中心领导：</td>
					<td>
						<s:hidden name="saveAuditOrgForm.leaderId" />
						<span class="input-large uneditable-input pop_over" 
	                        	data-content="请通过选择按钮选择人员" data-placement="top">
	                     	<s:property value="saveAuditOrgForm.leaderName" />
	                 	</span>
	                 	<span class="btn staffSelector">选择</span>
					</td>
				</tr>
				<tr>
					<td>机构排序：</td>
					<td><s:textfield name="saveAuditOrgForm.sortCode" maxlength="2"
							style="width:80%" /></td>
				</tr>
			</table>
		</div>
	</fieldset>
</s:form>
<script>
  var mf = $('#modAuditOrgForm');
  mf.find('[name=saveBtn]').on('click', function() {
    $('#modAuditOrgForm').form('submit', {
      'url' : 'org/updateAuditOrg.do'
      ,success : function(result) {
    	result = JSON.parse(result);
	    var resultMessage;
	    if (result.success) {
		  resultMessage = result.success;
		  var selNodes = orgTreeObj.getSelectedNodes();
		  if (0 < selNodes.length){
			  selNodes[0].name = $.trim($('[name="saveAuditOrgForm.name"]').val());
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
  
  mf.find('span.staffSelector').on('click', function(){
    var op = $(this);
    chooseStaff(parent, function(staff) {
      op.prev().text(staff.name).prev().val(staff.id);
    });
    return false;	  
  });
  $("table tr td").css({"padding-left":"15px","line-height":"50px"});
</script>