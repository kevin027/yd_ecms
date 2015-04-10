<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html >
<head>
<meta charset="utf-8" />
<script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
<jsp:include page="/inc/bootstrap-2.3.1.jsp"></jsp:include>
<link href="<%=basePath %>css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath %>js/bootstrap/plugin/bootstrap.plugins.js"></script>
<script src="<%=basePath %>js/jquery/EasyUi/jquery-easyui-1.3.4/jquery.easyui.min.js" ></script>
<style type="text/css">
.file{ position:absolute; top:0; right:80px; height:24px; filter:alpha(opacity:0);opacity: 0;width:260px }
</style>
</head>
<body>
<form action='<%=basePath %>file/<%=request.getAttribute("actionFlag") %>.do' method="post" enctype="multipart/form-data" id="outcomeFileForm">
	<input type="hidden" name="outcomeFileId" value="${outcomeFileId }">
	<input type="hidden" name="businessId" value="${businessId }">
	<input type="hidden" name="stepId" value="${stepId }">
	<input type="hidden" name="refModule" value="${refModule }">
		<div class="formSep">
			<div class="row-fluid">
				<div class="span1"></div>
				<div class="span4" style="margin-top:20px">
						<p><span class="label label-info">文件上传</span></p>
						<div data-provides="fileupload" class="fileupload fileupload-new" style="padding:0px">
						<input type="hidden" />
						<div class="input-append">
							<div class="uneditable-input span6" style="padding:0px;height:30px;line-height:30px">
							<i class="icon-file fileupload-exists"></i>
						    <span class="fileupload-preview"></span>
							</div>
							<span class="btn btn-file">
							<span class="fileupload-new">选择文件</span>
							<span class="fileupload-exists">修改</span>
							<input type="file" class="file" name="upload"/></span>
							<a data-dismiss="fileupload" class="btn fileupload-exists" href="#">移除</a>
						</div>
						</div>
				</div>
			</div>
		</div>
		<div class="formSep">
			<div class="row-fluid">
			    <div class="span1"></div>
				<div class="span4" style="margin-top:20px">
				<p><span class="label label-info">备注</span></p>
				<textarea name="remark" rows="4" class="input-xxlarge"></textarea>
				</div>
			</div>
		</div>
	<input type="button" value="提交" id="outcomeFileUpload" class="btn" style="float:right;margin-right:30px" />
	<script>
		$('#outcomeFileUpload').on('click', function() {
			$('#outcomeFileForm').form('submit', {
			  'success' : function(result) {
			    result = JSON.parse(result);
                if (result.msg) {
                  top.$.messager.alert('提示', result.msg, 'info', function(){
                    if (result.status && top && top.$) {
                      var cr = top.$('#outcomeFilePage');
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
	</script>
</form>
</body>
</html>