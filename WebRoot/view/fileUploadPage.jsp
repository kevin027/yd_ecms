<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<title>通用文件上传页面</title>
<meta charset="utf-8" />
<script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
<jsp:include page="/inc/bootstrap-2.3.1.jsp"></jsp:include>
<link href="<%=basePath %>css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath %>js/bootstrap/plugin/bootstrap.plugins.js"></script>
<script src="<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/jquery.easyui.min.js" ></script>
<!-- 自适应textarea -->
<script src="<%=basePath %>js/jquery.autosize.min.js"></script>
</head>
<body>
<form id="commonFileUploadForm" namespace="/file" action="upload" method="post" enctype="multipart/form-data">
	<input type="hidden" name="refModule" value="${refModule}">
	<input type="hidden" name="refRecordId" value="${refRecordId}">
	<input type="hidden" name="stepId" value="${stepId}">
	
	<div class="formSep">
		<div class="row-fluid">
			<div class="span1"></div>
			<div class="span4" style="margin-top:20px">
					<p><span class="label label-info">文件上传</span></p>
					<div data-provides="fileupload" class="fileupload fileupload-new">
					<input type="hidden" />
					<div class="input-append">
						<div class="uneditable-input span6" style="padding:0px;line-height:25px">
						<i class="icon-file fileupload-exists"></i>
					    <span class="fileupload-preview"></span>
						</div>
						<span class="btn btn-file" style="height:20px">
						<span class="fileupload-new">选择文件</span>
						<span class="fileupload-exists">修改</span>
						<input type="file" name="upload" class="file"/>
						</span>
						<a data-dismiss="fileupload" class="btn fileupload-exists" href="#">移除</a>
					</div>
					</div>
			</div>
			
		</div>
		<div class="row-fluid">
			<div class="span1"></div>
			<div class="span10" style="margin-top:20px">
					<p><span class="label label-info">备注</span></p>
					<textarea name="remark" rows="4" style="width:95%"></textarea>
			</div>
			
		</div>
	</div>
	<input type="button" id="commonFileUploadBtn" class="btn" value="提交" style="float:right;margin-right:30px"></input>
</form>
<script>
$('#commonFileUploadBtn').on('click', function() {
	
	var isSelect = true;
	
	$('#commonFileUploadForm').find('[type=file]').each(function() {
		if ('' == $.trim(this.value)) {
			top.$.messager.alert('提示', '请先选择要上传的要文件.');
			isSelect = false;
			return false;
		}
	});
	
	if (!isSelect) {
		return false;
	}
	
	$('#commonFileUploadForm').form('submit', {
	  'success' : function(result) {
	    result = JSON.parse(result);
        if (result.msg) {
          top.$.messager.alert('提示', result.msg, 'info', function(){
            if (result.status && top && top.$) {
              var cr = top.$('#commonFileUploadPage');
              if (0 < cr.length) {
                cr.dialog('close');	
              }
            }	  
          });
        }
	  }
	});
	return false;
});
$(function(){
	$("textarea").autosize();
});
</script>
</body>
</html>