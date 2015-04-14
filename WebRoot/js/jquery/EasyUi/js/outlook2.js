/***********************************************************************

* 作者：hzc
* 创建日期：2013\9\12
* 

**********************************************************/

$(document).ready(function () {
  registerLeftMenuEvent();
  registerTabMenuEvent();
  
  // 注册退出登录按钮事件
  $('#loginOut').on('click', function() {
    $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
      if (r) {
        location.href = 'logout';
      }
    });
  });
  
  // 右侧iframe加载系统桌面页面
  document.getElementsByName('contentBody')[0].src='desktop/main';
});

// 注册左侧二级菜单事件
function registerLeftMenuEvent() {
	
  // 选择左侧菜单栏二级菜单
  var _menus = $('#nav').find('a[rel]');
  
  // 选择右侧标签页
  var _tabs = $('#tabs');
  
  // 二级菜单hover事件
  with(_menus) {

	// 左侧菜单栏二级菜单点击事件。
    on('click', function(ev) {
      var tabTitle = $(this).find('.nav').text();
	  var url = this.rel;
		
	  // 判断右侧标签页是否已存在和该菜单名字相同的页签
	  if (!_tabs.tabs('exists', tabTitle)) { // 如果不存在就新建页签
	    	
	    // 添加新的标签页
	    $('#tabs').tabs('add', {
		  title : tabTitle,
	      content : '<iframe scrolling="auto" frameborder="0"  src="'+ url +'" style="width:100%;height:98%;"></iframe>',
		  closable : true
		});
		  
		var _tab = $('#tabs').find('.tabs-title').filter(':contains(' + tabTitle + ')');
		  
		// 为新的标签页绑定右键打开菜单的事件
		_tab.bind('contextmenu', function(e){
		  $('#mm').menu('show', {
		    left: e.pageX,
		    top: e.pageY
		  });
 
		  $('#tabs').tabs('select', tabTitle);
	      $('#mm').data('currTitle', tabTitle);
		  return false;
	    });
		  
	    // 为新的标签页绑定双击鼠标关闭该标签页的事件
	    _tab.on('dblclick', function(e) { 
	    	if(tabTitle != '系统桌面'){
	    		$('#tabs').tabs('close', tabTitle);
	    	}
	      return false;
	    });
		  
      } else { // 如果存在则更新页签内容
    	$('#tabs').tabs('select', tabTitle);
	    $('#tabs').tabs('update', {
		  'tab' : $('#tabs').tabs('getTab', tabTitle),
		  'options' : {
		    content : '<iframe scrolling="auto" frameborder="0" src="'+url+'" style="width:100%;height:98%;"></iframe>'
		  }
	    });
	  }
	  return false;
    });
	  
	// 鼠标停留
    on('mouseover', function() {
      $(this).parent().addClass("hover");
    });
    
    // 鼠标移开
    on('mouseout', function() {
      $(this).parent().removeClass("hover");
    });
  }	
}


// 注册右键菜单的各操作项事件
function registerTabMenuEvent() {
  // 刷新
  $('#mm-tabupdate').on('click', function() {
	var currTitle = $('#mm').data('currTitle');
    var currTab = $('#tabs').tabs('getTab', currTitle);
    var url = $(currTab.panel('options').content).attr('src');
    $('#tabs').tabs('update', {
	  tab:currTab,
	  options : {
	    content:createFrame(url)
	  }
	});
	return false; 
  });
  
  // 关闭
  $('#mm-tabclose').on('click', function() {
	var currTitle = $('#mm').data('currTitle');
	$('#tabs').tabs('close', currTitle);
	return false;
  });
  
  // 全部关闭
  $('#mm-tabcloseall').on('click', function(){
    $('#tabs').find('.tabs-title').each(function() {
    	if($(this).text() != '系统桌面'){
    		$('#tabs').tabs('close', $(this).text());
    	}
    });
  });
  
  // 除此以外全部关闭
  $('#mm-tabcloseother').on('click', function() {
	var currTitle = $('#mm').data('currTitle');
    $('#tabs').find('.tabs-title').each(function() {
      if (currTitle != $(this).text()) {
    	  if($(this).text() != '系统桌面'){
    		  $('#tabs').tabs('close', $(this).text());  
    	  }
      }
    });
  });
}

//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}