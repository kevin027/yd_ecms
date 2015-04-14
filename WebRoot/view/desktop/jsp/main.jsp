<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="/view/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	var portalLayout;
	var portal;
	var _tabs = top.$('#tabs');
	$(function() {
		portalLayout = $('#portalLayout').layout({
			fit : true
		});
		$(window).resize(function() {
			portalLayout.layout('panel', 'center').panel('resize', {
				width : 1,
				height : 1
			});
		});

		panels = [ {
			id : 'p1',
			title : '待办任务<span class="badge badge-important" style="margin-left:10px;cursor:default"></span>',
			height : 200,
			iconCls:'icon-note_edit',
			tools:[{
				iconCls:'more',
				handler:function(){
					parent.$("#tabs").tabs("add", {
						  title : "待办任务",
					      content : '<iframe scrolling="auto" frameborder="0" src="desktop/personalTodosMore.do" style="width:100%;height:99%;"></iframe>',
						  closable : true
						});
				}
			}], 
			collapsible : true,
			closable:false,
			href : '${ctx}desktop/personalTodos'
		}, {
			id : 'p2',
			title : '邮件通讯<span class="badge badge-important" style="margin-left:10px;cursor:default"></span>',
			height : 200,
			iconCls:'icon-email',
			collapsible : true,
			closable:false,
			href : '${ctx}desktop/personalMails',
			tools:[{
				iconCls:'more',
				handler:function(){
					
				}
			}]
		}, {
			id : 'p4',
			title : '进度提醒<span class="badge badge-important" style="margin-left:10px;cursor:default"></span>',
			height : 200,
			iconCls:'icon-process',
			collapsible : true,
			closable:false,
			href : '',
			tools:[{
				iconCls:'more',
				handler:function(){
					parent.$("#tabs").tabs("add", {
						  title : "评审任务",
					      content : '<iframe scrolling="auto" frameborder="0" src="desktop/personalTodosMore.do" style="width:100%;height:99%;"></iframe>',
						  closable : true
						});
				}
			}]
		}, {
			id : 'p5',
			title : '公告栏<span class="badge badge-important" style="margin-left:10px;cursor:default"></span>',
			height : 200,
			iconCls:'icon-comments',
			collapsible : true,
			closable:false,
			href : '${ctx}desktop/publicNotices',
			tools:[{
				iconCls:'more',
				handler:function(){
					
				}
			}]
		}, {
			id : 'p6',
			title : '任务论坛<span class="badge badge-important" style="margin-left:10px;cursor:default"></span>',
			height : 200,
			iconCls:'icon-user_comment',
			collapsible : true,
			closable:false,
			href : '${ctx}desktop/applyNotices',
			tools:[{
				iconCls:'more',
				handler:function(){
					
				}
			}]
		}, {
			id : 'p3',
			title : '系统帮助<span class="badge badge-important" style="margin-left:10px;cursor:default"></span>',
			height : 200,
			iconCls:'icon-server',
			collapsible : true,
			closable:false,
			href : '${ctx}desktop/systemDevMark',
			tools:[{
				iconCls:'more',
				handler:function(){
					
				}
			}]
		}];

		portal = $('#portal').portal({
			border : false,
			fit : true,
			onStateChange : function() {
				$.cookie('portal-state', getPortalState(), {
					expires : 6
				});
			}
		});
		var state = $.cookie('portal-state');
		if (!state) {
			state = 'p1,p5,p6:p4,p2,p3';/*冒号代表列，逗号代表行*/
		}
		addPortalPanels(state);
		portal.portal('resize');
		
		
		//td背景
		//$("table td").addClass("ptrn_e");
		
		
		//更多代办提示
		//$.easyui.tooltip.init($(".icon-format_indent_more",$("#p1").prev()), { content: "点击查看更多评审任务", trackMouse: false});
	
		$(".more").text('更多').css({"width":"30px","color":"#333","margin-top":"-2px"});
	});
   //获取面板对象
	function getPanelOptions(id) {
		for ( var i = 0; i < panels.length; i++) {
			if (panels[i].id == id) {
				return panels[i];
			}
		}
		return undefined;
	}
   //获取面板顺序
	function getPortalState() {
		var aa = [];
		for ( var columnIndex = 0; columnIndex < 2; columnIndex++) {
			var cc = [];
			var panels = portal.portal('getPanels', columnIndex);
			for ( var i = 0; i < panels.length; i++) {
				cc.push(panels[i].attr('id'));
			}
			aa.push(cc.join(','));
		}
		return aa.join(':');
	}
   //添加面板
	function addPortalPanels(portalState) {
		var columns = portalState.split(':');
		for ( var columnIndex = 0; columnIndex < columns.length; columnIndex++) {
			var cc = columns[columnIndex].split(',');
			for ( var j = 0; j < cc.length; j++) {
				var options = getPanelOptions(cc[j]);
				if (options) {
					var p = $('<div/>').attr('id', options.id).appendTo('body');
					p.panel(options);
					portal.portal('add', {
						panel : p,
						columnIndex : columnIndex
					});
				}
			}
		}
	}
   
	//获取待处理消息数量
	function getTodos(num,id){
		if(num == 0){
		$(id).prev().find('span.badge-important').text(0);
		}else{
		$(id).prev().find('span.badge-important').text(num);
		}
		//显示数量
		//$("span.badge-important",$(id).prev()).text(num);
	}
	
	//打开连接
	function getLink(tabTitle,url){
		_tabs.tabs('add', {
			  method:'get',
		  	  title : tabTitle,
		      content : '<iframe scrolling="auto" frameborder="0"  src="'+ url +'" style="width:100%;height:97%;overflow:hidden;"></iframe>',
		  	  closable : true
		  	});
	   return false;
	}
</script>
</head>
<body>
	<div id="portalLayout">
		<div data-options="region:'center',border:false">
			<div id="portal" style="position: relative" class="ptrn_e"class="ptrn_e">
				<div class="ptrn_e"></div>
				<div class="ptrn_e"></div>
			</div>
		</div>
	</div>
</body>
</html>