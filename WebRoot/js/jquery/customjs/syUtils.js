/**
 * 包含easyui的扩展和常用的方法
 * 
 * 
 */

var sy = $.extend({}, sy);/* 全局对象 */

/*
$.parser.auto = false;
$(function() {
	$.messager.progress({
		text : '页面加载中,请稍后....',
		interval : 100
	});
	$.parser.parse(window.document);
	window.setTimeout(function() {
		$.messager.progress('close');
		if (self != parent) {
			window.setTimeout(function() {
				
				$.messager.progress('close');
			}, 500);
		}
	}, 1);
	$.parser.auto = true;
});
*/
$.fn.panel.defaults.onBeforeDestroy = function() {/* tab关闭时回收内存 */
	var frame = $('iframe', this);
	try {
		if (frame.length > 0) {
			frame[0].contentWindow.document.write('');
			frame[0].contentWindow.close();
			frame.remove();
			if ($.browser.msie) {
				CollectGarbage();
			}
		} else {
			$(this).find('.combo-f').each(function() {
				var panel = $(this).data().combo.panel;
				panel.panel('destroy');
			});
		}
	} catch (e) {
	}
};

$.fn.panel.defaults.loadingMessage = '数据加载中，请稍候....';
$.fn.datagrid.defaults.loadMsg = '数据加载中，请稍候....';

var easyuiErrorFunction = function(XMLHttpRequest) {
	/* $.messager.progress('close'); */
	/* alert(XMLHttpRequest.responseText.split('<script')[0]);*/ 
	$.messager.alert('错误', XMLHttpRequest.responseText.split('<script')[0]);
};
$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;

var easyuiPanelOnMove = function(left, top) {/* 防止超出浏览器边界 */
	if (left < 0) {
		$(this).window('move', {
			left : 1
		});
	}
	if (top < 0) {
		$(this).window('move', {
			top : 1
		});
	}
};
$.fn.panel.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;

$.extend($.fn.validatebox.defaults.rules, {
	eqPassword : {/* 扩展验证两次密码 */
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '密码不一致！'
	}
});

$.extend($.fn.datagrid.defaults.editors, {
	combocheckboxtree : {
		init : function(container, options) {
			var editor = $('<input/>').appendTo(container);
			options.multiple = true;
			editor.combotree(options);
			return editor;
		},
		destroy : function(target) {
			$(target).combotree('destroy');
		},
		getValue : function(target) {
			return $(target).combotree('getValues').join(',');
		},
		setValue : function(target, value) {
			$(target).combotree('setValues', sy.getList(value));
		},
		resize : function(target, width) {
			$(target).combotree('resize', width);
		}
	}
});

/**
 * 获得项目根路径
 * 
 * 使用方法：sy.bp();
 */
sy.bp = function() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
};

/**
 * 增加formatString功能
 * 
 * 使用方法：sy.fs('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 */
sy.fs = function(str) {
	for ( var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};

/**
 * 增加命名空间功能
 * 
 * 使用方法：sy.ns('jQuery.bbb.ccc','jQuery.eee.fff');
 */
sy.ns = function() {
	var o = {}, d;
	for ( var i = 0; i < arguments.length; i++) {
		d = arguments[i].split(".");
		o = window[d[0]] = window[d[0]] || {};
		for ( var k = 0; k < d.slice(1).length; k++) {
			o = o[d[k + 1]] = o[d[k + 1]] || {};
		}
	}
	return o;
};


/**
 * 创建一个模式化的dialog，实现全屏遮罩
 * 
 * @requires jQuery,EasyUI
 * 
 */
sy.modalDialog = function(options) {
	var opts = $.extend({
		title : '&nbsp;',
		width : 640,
		height : 480,
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		}
	}, options);
	opts.modal = true;// 强制此dialog为模式化，无视传递过来的modal参数
	
	return $('<div/>').dialog(opts);
};


/**
 * 生成UUID
 */
sy.random4 = function() {
	return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
};
sy.UUID = function() {
	return (sy.random4() + sy.random4() + "-" + sy.random4() + "-" + sy.random4() + "-" + sy.random4() + "-" + sy.random4() + sy.random4() + sy.random4());
};

/**
 * 获得URL参数
 */
sy.getUrlParam = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
};

sy.getList = function(value) {
	if (value) {
		var values = [];
		var t = value.split(',');
		for ( var i = 0; i < t.length; i++) {
			values.push('' + t[i]);/* 避免他将ID当成数字 */
		}
		return values;
	} else {
		return [];
	}
};

sy.png = function() {
	var imgArr = document.getElementsByTagName("IMG");
	for ( var i = 0; i < imgArr.length; i++) {
		if (imgArr[i].src.toLowerCase().lastIndexOf(".png") != -1) {
			imgArr[i].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + imgArr[i].src + "', sizingMethod='auto')";
			imgArr[i].src = "images/blank.gif";
		}
		if (imgArr[i].currentStyle.backgroundImage.lastIndexOf(".png") != -1) {
			var img = imgArr[i].currentStyle.backgroundImage.substring(5, imgArr[i].currentStyle.backgroundImage.length - 2);
			imgArr[i].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + img + "', sizingMethod='crop')";
			imgArr[i].style.backgroundImage = "url('images/blank.gif')";
		}
	}
};
sy.bgPng = function(bgElements) {
	for ( var i = 0; i < bgElements.length; i++) {
		if (bgElements[i].currentStyle.backgroundImage.lastIndexOf(".png") != -1) {
			var img = bgElements[i].currentStyle.backgroundImage.substring(5, bgElements[i].currentStyle.backgroundImage.length - 2);
			bgElements[i].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + img + "', sizingMethod='crop')";
			bgElements[i].style.backgroundImage = "url('images/blank.gif')";
		}
	}
};

sy.isLessThanIe8 = function() {/* 判断浏览器是否是IE并且版本小于8 */
	return ($.browser.msie && $.browser.version < 8);
};

//人员选择
function chooseStaff(scope, chooseStaffCallback) {
  var _selStaff = null;
  var _success = false;
  var _chooseStaffWindow = null;
  _chooseStaffWindow = scope.sy.modalDialog({
    id:'_chooseStaffWindow',
    title : '人员选择',
    width : 630,
    height : 550,
    href : 'view/core/account/jsp/selectStaffPage.jsp'
    ,onDestroy : function() {
      if (_success) {
        chooseStaffCallback(_selStaff);  
      }
    }
  	,buttons : [
  	  {'text':'确定', 'handler' : function() {
        var _body = _chooseStaffWindow.dialog('body');
        var chks = _body.find('#_staffList').find('input:checkbox:checked');
    	if (1 != chks.length) {
    	  top.$.messager.alert('提示', '请选择一个用户再进行确定！', 'error');
    	  _selStaff = null;
    	} else {
    	  _selStaff = {'id' : chks.val(), 'name' : chks.next().text()};
    	  _success = true;
    	  _chooseStaffWindow && _chooseStaffWindow.dialog('close');
    	}
  	  }}
  	  ,{'text':'关闭', 'handler' : function() {
  		_chooseStaffWindow && _chooseStaffWindow.dialog('close');
  	  }}
  	]
  });    	
}

function chooseStaff2(scope, chooseStaffCallback) {
  var _selStaff = null;
  var _success = false;
  var _chooseStaffWindow = null;
  _chooseStaffWindow = scope.sy.modalDialog({
    id:'_chooseStaffWindow2',
    title : '人员选择',
    width : 630,
    height : 550,
    href : 'view/core/account/jsp/selectStaffPage2.jsp'
    ,onDestroy : function() {
      if (_success) {
        chooseStaffCallback(_selStaff);  
      }
    }
  	,buttons : [
  	  {'text':'确定', 'handler' : function() {
        var _body = _chooseStaffWindow.dialog('body');
        var chks = _body.find('#_staffList').find('input:checkbox:checked');
    	if (0 == chks.length) {
    	  top.$.messager.alert('提示', '请选择一个用户再进行确定！', 'error');
    	  _selStaff = null;
    	} else {
    	  _selStaff = [];
    	  var _set = {};
    	  $.each(chks, function() {
    		  id = this.value;
    		  name = $(this).next().text();
    		  if (!_set[id]) {
    			_selStaff.push({id:id, name:name});
    		    _set[id] = name;
    		  }
    	  });
    	  _success = true;
    	  _chooseStaffWindow && _chooseStaffWindow.dialog('close');
    	}
  	  }}
  	  ,{'text':'关闭', 'handler' : function() {
  		_chooseStaffWindow && _chooseStaffWindow.dialog('close');
  	  }}
  	]
  });    	
}

function chooseDept(scope, chooseDeptCallback) {
  var _selDept = null;
  var _selDeptSuccess = false;
  var _chooseDeptWindow = null;
  _chooseDeptWindow = scope.sy.modalDialog({
    id:'_chooseDeptWindow',
    title : '部门选择',
    width : 630,
    height : 550,
    href : 'view/core/org/jsp/selectDeptPage.jsp'
    ,onDestroy : function() {
      if (_selDeptSuccess) {
        chooseDeptCallback(_selDept);  
      }
    }
  	,buttons : [
  	  {'text':'确定', 'handler' : function() {
        var _body = _chooseDeptWindow.dialog('body');
        var chks = _body.find('#_deptList').find('input:checkbox:checked');
    	if (1 != chks.length) {
    	  top.$.messager.alert('提示', '请在右边列表中选择一个部门再进行确定！', 'error');
    	  _selDept = null;
    	  _selDeptSuccess = false;
    	} else {
    	  _selDept = {'id': chks.val(), 'name': chks.next().text(), 'fullName': chks.attr('alt')};
    	  _selDeptSuccess = true;
    	  if (_chooseDeptWindow) {
    	    _chooseDeptWindow.dialog('close');
    	  }
    	}
  	  }}
  	  , {'text':'关闭', 'handler' : function() {
  		_selDeptSuccess = false;
  		_selDept = null;
  		if (_chooseDeptWindow) {
    	  _chooseDeptWindow.dialog('close');
        }
  	  }}
  	]
  });    	
}

//合同选择
function chooseContract(callBack) {
	var contractBusinessId = null;
	if (0 != $("#contractBusinessId").length) {
		contractBusinessId = $("#contractBusinessId").val();
	}
	var chooseWindow = null;
	chooseWindow = top.sy.modalDialog({
		href : '',
		//href : 'contract/chooseContract.do?contractBusinessId=' + contractBusinessId,
		title : "合同选择",
		width : 700,
		height: 550,
		buttons : [ {
			'text' : '确定',
			'handler' : function() {
				var body = chooseWindow.dialog('body');
				var temp = body.find('#contractList');
				if (temp.datagrid('getSelected') == null) {
					top.$.messager.alert("请选择一条记录");
					return;
				}
				var selIdNo = body.find('#selectedContract').val();
				if (selIdNo != '') {
					var rets = selIdNo.split('=');
					callBack && callBack({
						'id' : rets[0]
						,'contractNo' : rets[1]
					});
					chooseWindow.dialog('destroy');
				}
			}
		}]
	});
};

function chooseContract2(domId) {
	chooseContract(function(c){
		$('#' + domId).val(c.contractNo);
	});
}
