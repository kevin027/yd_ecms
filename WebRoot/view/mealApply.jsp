<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
if (null == request.getSession(false)) {
	System.out.println("session is null");
	response.sendRedirect("login.jsp");
	return;
}
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<jsp:include page="/view/inc.jsp"></jsp:include>
<html html>  
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no" >
	<!--个人信息-->
    <div id="mainPanle" region="center" title="个人信息" iconCls="icon-user_gray" style="overflow-y:auto">
    <form id="mealApplyForm">
        <table class="table table-bordered table-condensed"  style="height:100%">
           <tr>
           		<td style="text-align:center;border-left:none;width:100px">姓名</td>
           		<td style="text-align:left">sssssss</td>
           	</tr>
           <tr>
           		<td style="border-left:none;text-align:center">部门</td>
           		<td style="text-align:left">sssssss</td>
           	</tr>
           <tr>
           		<td style="border-left:none;text-align:center">星期</td>
           		<td style="text-align:left">sssssss</td>
           	</tr>
           <tr>
           		<td style="border-left:none;text-align:center">日期</td>
           		<td style="text-align:left">sssssss</td>
           	</tr>
           <tr>
           		<td style="border-left:none;text-align:center" >备注：</td>
           		<td style="text-align:left">
					<textarea rows="3" style="width:90%"></textarea>
				</td>
           	</tr>
        </table>
    </form>
    </div>
 </body>   
 <script>
 $("textarea").autosize();
 </script>
 </html>