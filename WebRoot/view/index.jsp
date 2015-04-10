<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
if (null == request.getSession(false)) {
	System.out.println("session is null");
	response.sendRedirect("login.jsp");
	return;
}
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
	<base href="<%=basePath %>" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title><c: value="${SYSTEM_NAME}" /></title>
    <style type="text/css">
     .window-mask {
	  position: absolute;
	  display:none;
	  left: 0;
	  top: 0;
	  width: 100%;
	  height: 100%;
	  filter: alpha(opacity=80);
	  opacity: 0.80;
	  font-size: 1px;
	  *zoom: 1;
	  overflow: hidden;
	  z-index: 9013;
	}
    </style>
    <link href="<%=basePath %>js/jquery/grumble/css/grumble.css" rel="stylesheet" type="text/css" />
    <link rel = "Shortcut Icon" href="<%=basePath %>img/favicon/favicon.ico">
    <jsp:include page="inc.jsp"></jsp:include>
    <script type="text/javascript" src="js/index.js"> </script>
</head>
<body class="easyui-layout ptrn_e" id="main" style="overflow-y: hidden" scroll="no">
	<input type="hidden" id="basePath" value="<%=basePath %>" />
	<input type="hidden" id="loginAccounts" value="<s:property value="#session.LOGIN_ACCOUNT.accounts" />" />
	<input type="hidden" id="lockFlag" value="<s:property value="#session.LOGIN_ACCOUNT.lock" />" />

	<!--top信息-->
    <div region="north"  split="false" class="ptrn_bg" border="false" style="overflow: hidden; height: 80px;
        line-height: 60px;">
        
	        <div style="width:500px;height:80px;padding-left:0px; margin-top:0px;float:left">
	        <img src="img/<s:text name='INDEX_LOGO' />" align="absmiddle" style="float:left;"/> 
	        </div>	
	        		
			<div id="timerSpan" style="position: absolute;right: 30px; top: 5px;height:26px ;line-height:26px"></div>
			<div id="grumble2" style="position: absolute; right: 0px; bottom: 0px;height:26px ;line-height:26px">
				<span><c:out value="#session.LOGIN_ACCOUNT.showName" /></span>
				<c:if test="#request.auditOrgs.size != 0">
				<a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_gsxzbMenu" iconCls="icon-building">机构</a>
				</c:if>
				<a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_kzmbMenu" iconCls="icon-cog">控制面板</a>
				<a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_zxMenu" iconCls="icon-key">注销</a>
				<a id="btnHideNorth" class="easyui-linkbutton l-btn l-btn-plain" data-options="plain: true, iconCls: 'layout-button-up'" >				</a>
			</div>
			
			<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
				<div onclick="userInfo();" iconCls="icon-user_gray" >个人信息</div>
				<div onclick="editPwd()" iconCls="icon-wrench">修改密码</div>
				<div class="menu-sep"></div>
				<div iconCls="icon-rainbow">
					<span>更换主题</span>
					<div style="width: 100px;" id="changeTheme">
						<div onclick="sy.changeTheme('default');" iconCls="icon-ok">默认主题 </div>
						<div onclick="sy.changeTheme('gray');" >灰色</div>
						<div onclick="sy.changeTheme('black');" >黑色</div>
						<div onclick="sy.changeTheme('bootstrap');" >渐变</div>
						<div class="menu-sep"></div>
						<div onclick="sy.changeTheme('metro');" >简洁主题</div>
						<div onclick="sy.changeTheme('metro-gray');" >简洁灰</div>
						<div onclick="sy.changeTheme('metro-blue');" >简洁蓝</div>
						<div onclick="sy.changeTheme('metro-green');" >简洁绿</div>
						<div onclick="sy.changeTheme('metro-red');" >简洁红</div>
						<div onclick="sy.changeTheme('metro-orange');" >简洁橙</div>
					</div>
				</div>
			</div>
			
			<div id="layout_north_zxMenu" style="width: 100px; display: none;">
				<div id="lockWin" iconCls="icon-lock">锁定窗口</div>
				<div id="loginOut" iconCls="icon-system-log-out">安全退出</div>
			</div>
		
			<div id="layout_north_gsxzbMenu" style="width: 100px; display: none;">
				<c:forEach var="c" items="#request.auditOrgs" varStatus="st">
					<div id="<c:out value="#c.id" />" 
						onclick="window.location.href = 'index.do?selAuditOrgId=<c:out value="#c.id" />';"
						<c:if test="#session.LOGIN_ACCOUNT.currentAuditOrgId == #c.id" >iconCls="icon-flag_orange"</s:if>>
						<c:out value="#c.name" />
					</div>
				</c:forEach>
			</div>		
    </div>

	<!--菜单信息-->
    <div region="west" hide="true" class="ptrn_e" iconCls="icon-direction" split="true" title="<span style='font-family:微软雅黑,Verdana, Geneva, sans-serif,宋体;'>导航菜单</span>" style="width:160px;" id="west">
		<a id="grumble1" style="position:absolute;display:block;top:40%;left:80px"></a>
		<div id="nav" class="easyui-accordion" data-options="animate:false" fit="true" border="false" >
		<!--  导航内容 -->
			<c:forEach var="menu1" items="%{#request.menus}" varStatus="st">
				<div title="${menu1.name}" class="ptrn_e" iconCls=" ${menu1.icon}">
					<ul>
						<c:forEach var="menu2" items="${children}" varStatus="st">
							<li>
								<div>
									<a ref="${menu2.id}" href="javascript:void(0);" rel="${menu2.href}">
										<span class="${menu2.icon}">&nbsp;</span><span class="nav">${menu2.name}</span>
									</a>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>
			</c:forEach>
		</div>
    </div>
	
	<!--主面板信息-->
    <div id="mainPanle" region="center" style=" overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false">
			<div title="系统桌面" class="ptrn_e" iconCls="icon-desk1" style="padding:10px;overflow:hidden; color:red; " >
              <iframe name="contentBody" id="contentBody" src="" frameborder="0" class="ptrn_e" style="border: 0; width: 100%; height: 98%;"></iframe>
			</div>
			<div title="全部任务" class="ptrn_e" iconCls="splashy-folder_classic" style="padding:10px;overflow:hidden; color:red; " >
              <iframe name="contentBody1" id="contentBody1" src="" frameborder="0" class="ptrn_e" style="border: 0; width: 100%; height: 98%;"></iframe>
			</div>
			<div title="参与任务" class="ptrn_e" iconCls="splashy-folder_classic" style="padding:10px;overflow:hidden; color:red; " >
              <iframe name="contentBody2" id="contentBody2" src="" frameborder="0" class="ptrn_e" style="border: 0; width: 100%; height: 98%;"></iframe>
			</div>
			<div title="当前任务" class="ptrn_e" iconCls="splashy-folder_classic" style="padding:10px;overflow:hidden; color:red; " >
              <iframe name="contentBody3" id="contentBody3" src="<%=basePath %>budget/main.do" frameborder="0" class="ptrn_e" style="border: 0; width: 100%; height: 98%;"></iframe>
			</div>
		</div>
    </div>
    <a id="grumble3" style="position: absolute;display:block;bottom:200px;right:400px"></a>
    
    <!--bottom信息-->
	<div region="south" split="false" class="ptrn_e" style="height: 30px;  ">
        <div class="footer"><a href="<c:out value="${COMPANY_INDEX}" />" target="_blank"><c:out value="${COMPANY_NAME}" /></a></div>
    </div>
  
	<!--页签右键操作-->
	<div id="mm" class="easyui-menu" style="width:150px; display:none;">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
	</div>

	
	<!--用户信息 -->
	<div id="userinfo"></div> 
	<!--用户密码修改 -->
	<div id="editPwd" style="padding:20px;overflow:hidden">
	<!--修改密码-->
	<form id="pwdForm" namespace="/account" action="modAccountPassword">
        <table class="table table-bordered"  >
           <tr>
           		<td style="text-align:center;width:60px">原密码</td>
           		<td style="text-align:left"><input id="opwd" type="text" name="modifyPasswordForm.oldPassword" width="50px" class="easyui-validatebox" data-options="required:true,missingMessage:'密码必须填',tipPosition:'top'"></td>
           	</tr>
           <tr>
           		<td style="text-align:center">新密码</td>
           		<td style="text-align:left"><input id="pwd" type="password" name="modifyPasswordForm.newPassword" width="50px" class="easyui-validatebox" data-options="required:true,missingMessage:'新密码必须填',tipPosition:'top'"></td>
           	</tr>
           <tr>
           		<td style="text-align:center">重复密码</td>
           		<td style="text-align:left"><input id="rpwd" type="password" width="50px" validType="equals['#pwd']" class="easyui-validatebox" data-options="required:true,missingMessage:'请再次填写新密码',tipPosition:'top'"></td>
           	</tr>
           <tr>
           		<td colspan="2">
			        <div>
			          <button class="btn btn-small" type="button" style="float:right" onclick="clearFormPwd()">重置</button>
			          <button class="btn btn-small" type="button" style="float:right" onclick="saveFormPwd()">保存</button>
			        </div>
				</td>
           	</tr>
        </table>
    </form>
	</div> 	
	
	<!--用户锁定 -->
	<div id="loginDialog" style="padding:20px; overflow:hidden">
	<form id="loginDialogForm" namespace="/" action="unlockAccount">
        <table class="table table-bordered"  >
           <tr>
           		<td style="text-align:center">密码：</td>
           		<td style="text-align:left"><input type="password" name="loginForm.password" width="50px" class="easyui-validatebox"  data-options="required:true,missingMessage:'密码必填',tipPosition:'top'"></td>
           </tr>
        </table>
    </form>
	</div> 
	
	<!-- 引导页面 -->
	<div class="window-mask" style="background:black;"></div>
	<span class="btn btn-large btn-block btn-inverse" id="startOa" style="position:absolute; left:50%; top:50%; display:none;width:300px;height:100px;margin:-50px -150px;line-height:85px;font-size:30px;z-index:9015;font-family:'Comic Sans MS';filter: alpha(opacity=80);opacity: 0.80;" >开始体验</span>
	
	<!-- 更换主题 -->
	<script type="text/javascript" src="<%=basePath %>js/jquery/customjs/changeEasyuiTheme.js" charset="utf-8"></script>	
	<script type="text/javascript" src="<%=basePath %>js/jquery/EasyUi/js/outlook2.js" charset="utf-8"></script>
	<script src="js/jquery/grumble/js/Bubble.js"></script>
	<script src="js/jquery/grumble/js/jquery.grumble.js"></script>
</body>
</html>