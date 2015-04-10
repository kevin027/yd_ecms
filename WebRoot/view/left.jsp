<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div id="nav" class="easyui-accordion" fit="true" border="false">
<!--  导航内容 -->
	<c:forEach var="menu1" items="%{#request.menus}" varStatus="st1">
		<div title="${menu1.name}" iconCls="icon icon-sys">
		<ul>
			<c:forEach var="menu2" items="children" varStatus="st2">
				<li>
					<div>
						<a ref="${menu2.id}" href="javascript:void(0);" rel="${menu2.linkSrc}" >
							<span class="icon icon-sys" >&nbsp;</span><span class="nav">${menu2.name}</span>
						</a>
					</div>
				</li>
			</c:forEach>
		</ul>
		</div>
	</c:forEach>
</div>