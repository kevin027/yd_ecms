<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<html>
<head>
<base href="${ctx}" />
<title><fmt:message key="SYSTEM_NAME" /></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<!-- Force Latest IE rendering engine -->
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<!-- Mobile Specific Metas -->
<meta content="text/html; charset=utf-8" http-equiv="content-type" />
<!-- JS  -->
<script src="${ctx}js/jquery-1.9.1.min.js"></script>
<script src="${ctx}js/app.js"></script>
<script src="${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/jquery.easyui.min.js"></script>
<script src="${ctx}js/common.js"></script>

<link href="${ctx}css/new-login.css" rel="stylesheet" type="text/css" />
<script src="${ctx}js/jquery.SuperSlide.js"></script>

<!-- Stylesheets -->
<link rel="Shortcut Icon" href="${ctx}img/favicon/default.ico">
<script type="text/javascript">
	$(function() {
		$(".i-text").focus(function(){
			$(this).addClass('h-light');
		});

		$(".i-text").focusout(function(){
			$(this).removeClass('h-light');
		});
		//文本框的效果
		//验证
		//简单验证一下。
		var subimtLoginForm = function() {
			if ('' === $.trim($('#accounts').val())) {
				$("#accounts").css({
					"border" : "1px solid red"
				});
			} else if ('' === $.trim($('#password').val())) {
				$("#password").css({
					"border" : "1px solid red"
				});
			} else {
				$('#login_form').submit();
				$(".error-box").text("正在登录系统，请稍后...");
			}
		};

		$('#accounts, #password').bind('keydown', function(ev) {
			if (13 == ev.keyCode) {
				return subimtLoginForm() && false;
			}
			ev.stopPropagation();
		});

		$('#login').bind('click', function(ev) {
			return subimtLoginForm() && false;
		});

		if (window.localStorage) {
			var lastLoginAccounts = localStorage.getItem('lastLoginAccounts');
			if (lastLoginAccounts)
				$('#accounts').val(lastLoginAccounts);
		}

		$('#loginReset').bind('click', function(ev) {
			$("#accounts").val("");
			$("#password").val("");
		});
		
		$(window).resize(function(){
			  var width = $(window).width();
			  if(width >= 855){
				  $('.login-aside').css('right','15%');
				  $('#loginLogo').removeClass('hide').parent().next().addClass('hide');
			  }else if(width >= 755 && width <= 855){
				  $('.login-aside').css('right','25%');
				  $('#loginLogo').removeClass('hide').parent().next().addClass('hide');
			  }else if(width >= 655 && width <= 755){
				  $('.login-aside').css('right','35%');
				  $('#loginLogo').addClass('hide').parent().next().removeClass('hide');
			  }else if(width >= 370 && width <= 655){
				  $('.login-aside').css('right','55%');
				  $('#loginLogo').addClass('hide').parent().next().removeClass('hide');
			  }else{
				  $('.login-aside').css('right','65%');
				  $('#loginLogo').addClass('hide').parent().next().removeClass('hide');
			  }
	    });
	});
</script>
</head>
<body style="overflow-y:hidden;">
	<div class="header">
		<div class="headerLogo">
			<a title="<fmt:message key="SYSTEM_NAME" />" target="_self" href="#">
				<%-- <img alt="logo" id="loginLogo" src="images/<fmt:message key="LOGIN_LOGO" />"> --%> 
			</a>
			<h1 class="hide">
			<a title="<fmt:message key="SYSTEM_NAME" />" target="_self" href="#"><fmt:message key="SYSTEM_NAME" /></a>
			</h1>
		</div>
		<div class="headerNav">
			<a target="_blank" href="#">相关链接</a>
			<a target="_blank" href="#">相关链接</a>
			<a target="_blank" href="#">相关链接</a>
			<a target="_blank" href="#">系统帮助</a>
		</div>
	</div>

	<div class="banner">
		<div class="login-aside">
			<div id="o-box-up"></div>
			<div id="o-box-down" style="table-layout:fixed;">
				<div class="error-box">${error_msg}</div>
				<form id="login_form" namespace="/" action="login" method="post">
					<div class="fm-item">
						<label for="logonId" class="form-label">帐 号</label>
						<input type="text" id="accounts" name="loginForm.accounts" placeholder="输入用户名" class="i-text">
						<div class="ui-form-explain"></div>
					</div>

					<div class="fm-item">
						<label for="logonId" class="form-label">密 码</label>
						<input id="password" name="loginForm.password" type="password" placeholder="输入密码" class="i-text">
						<div class="ui-form-explain"></div>
					</div>
					
					<div class="fm-item">
						<label for="logonId" class="form-label"></label>
						<input type="submit" value="" tabindex="4" id="login" class="btn-login">
						<div class="ui-form-explain"></div>
					</div>
				</form>
			</div>
		</div>

		<div class="bd">
			<ul>
				<li style="background:url(${ctx}images/themes/theme-pic1.jpg) #CCE1F3 center 0 no-repeat;"></li>
				<li style="background:url(${ctx}images/themes/theme-pic2.jpg) #BCE0FF center 0 no-repeat;"></li>
				<li style="background:url(${ctx}images/themes/theme-pic3.jpg) #BCE0FF center 0 no-repeat;"></li>
			</ul>
		</div>

		<div class="hd">
			<ul></ul>
		</div>
	</div>
	<script type="text/javascript">
		jQuery(".banner").slide({
			titCell : ".hd ul",
			mainCell : ".bd ul",
			effect : "fold",
			autoPlay : true,
			autoPage : true,
			trigger : "click"
		});
	</script>

	<div class="banner-shadow"></div>

	<div class="footer">
		<fmt:message key="SYSTEM_NAME" />
		<br /><br /> 
		Copyright © 2014-2049 All Rights Reserved Powered By <fmt:message key="COPYRIGHT" /> 
		<br /><br /> 
		技术支持   <a href="#" target="_blank" title="覃业欣">覃业欣  : 18620855891</a>
	</div>
</body>
</html>