<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%    
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1    
	response.setHeader("Pragma","no-cache"); //HTTP 1.0    
	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server    
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath %>"/>
	<title>功能添加页面</title>
	<meta charset="utf-8" />
	<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/jquery/layer-1.6/layer.min.js"></script>
</head>
<body >
	<s:form namespace="/fun" action="saveFunction" method="post" cssStyle="padding:50px;padding-bottom:0px;" id="funAddForm">
		<s:hidden name="function.id" />
		<s:hidden name="function.parent.id" />
		
		<table class="table">
			<tr>
				<td>功能名称：</td>
				<td><input type="text" name="function.name" /></td>
			</tr>
			
			<tr>
				<td>功能编码：</td>
				<td><input type="text" name="function.code" title="系统编码识别用"/></td>
			</tr>
			
			<tr>
				<td>功能类型：</td>
				<td style="text-align:left">
					<s:select name="function.type" list="#request['functionTypeList']" listValue="chinese" />
				</td>
			</tr>
			
			<tr>
				<td>功能链接：</td>
				<td style="text-align:left"><input type="text" name="function.href" /></td>
			</tr>
			
			<tr>
				<td>功能图标：</td>
				<td style="text-align:left"><input type="text" name="function.icon" /></td>
			</tr>
			
			<tr>
				<td>功能排序：</td>
				<td><input type="text" name="function.sortCode" maxlength="2" /></td>
			</tr>
			
			<tr>
				<td>功能分组：</td>
				<td><s:textfield name="function.groups" /></td>
			</tr>
			
			<tr>
				<td colspan="2"><input type="button" class="btn pull-right" value="提交" name="saveBtn" /></td>
			</tr>
		</table>
	</s:form>
	<script type="text/javascript" src="core/function/js/addFunction.js"></script>
</body>
</html>