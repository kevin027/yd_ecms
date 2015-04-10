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

    <jsp:include page="/inc.jsp"></jsp:include>
  
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no" >
	

	<!--左侧头像区-->
    <div region="west" hide="true" iconCls="icon-user_photo" split="false" collapsible="false" title="个人头像" style="width:180px;" id="west">
		<img src="http://www.placehold.it/120x120/EFEFEF/AAAAAA" style="margin:40px 20px" class="img-polaroid">
		<div >
		<button class="btn btn-info" type="button" style="margin-left:30px;width:105px" disabled="disabled">Adam</button>
		</div>
    </div>
	
	<!--个人信息-->
    <div id="mainPanle" region="center" title="个人信息" iconCls="icon-user_gray" style="overflow-y:hidden">
        <table class="table table-bordered table-condensed"  style="height:100%">
           <tr>
           		<td style="text-align:center;border-left:none;width:100px">用户</td>
           		<td style="text-align:left"><s:property value="#session.LOGIN_ACCOUNT.showName" /></td>
           	</tr>
           <tr>
           		<td style="text-align:center;border-left:none;width:100px">账号</td>
           		<td style="text-align:left"><s:property value="#session.LOGIN_ACCOUNT.accounts" /></td>
           	</tr>
           <tr>
           		<td style="border-left:none;text-align:center">密码</td>
           		<td style="text-align:left">******</td>
           	</tr>
           <tr>
           		<td style="border-left:none;text-align:center">E-mail</td>
           		<td style="text-align:left"><s:property value="#session.LOGIN_ACCOUNT.staff.email" /></td>
           	</tr>
           <tr>
           		<td style="border-left:none;text-align:center">最近工作：</td>
           		<td style="text-align:left">
					<ul class="list_a">
						<li>华南农业大学从化校区建筑工程</li>
						<li>广州大学从化校区2期建设</li>
					</ul>
				</td>
           	</tr>
        </table>
    </div>
    
  