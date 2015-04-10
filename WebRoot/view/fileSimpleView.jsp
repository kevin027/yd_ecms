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
<title></title>
<meta charset="utf-8" />
<base href="<%=basePath %>" />
<!-- splashy图标库 -->
<link href="<%=basePath %>img/splashy/splashy.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
<jsp:include page="/inc/easyui.jsp"></jsp:include>
<jsp:include page="/inc/bootstrap-2.3.1.jsp"></jsp:include>
</head>
<body>
<ul class="list_a">
<c:forEach var="o" items="#request.list" varStatus="st">
<li id="<c:out value="#o.id"/>">
	<a href="file/download.do?fileId=<c:out value="#o.id"/>" target="_self" status="s">
		<c:out value="#s.count" />
		<c:out value="#o.name"/>
		<span class="muted">(<fmt:formatDate value="#o.createDate"  pattern="yyyy-MM-dd HH:mm:ss" />)</span></a>
		<i class="icon-remove" style="cursor:pointer"  name="delBtn"></i>
</li>
</c:forEach>
</ul>
<script>
	$('[name=delBtn]').on('click', function() {
		var fileId = $(this).parent().attr('id');
		top.$.messager.confirm('确认','您确认想要删除文件吗？',function(r){    
		    if (r){   
		    	$.post('file/delFile.do?fileId=' + fileId, function(r){
					

					if (r.status) {
						top.$.messager.alert('消息','删除成功');
					  
					  window.location.href = window.location.href;	
					} else {
						top.$.messager.alert('消息','删除失败');
					}
				}, 'json');
		    }    
		}); 
		
		return false;		
	});
</script>
</body>
</html>