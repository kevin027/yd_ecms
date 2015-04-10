function showAlert(msg, callback) {
  if (top.jQuery && top.jQuery.messager) {
    top.jQuery.messager.alert("提示", msg, 'info', callback);
  } 
  else if (jQuery && jQuery.messager) {
    jQuery.messager.alert('提示', msg, 'info');	  
  } else {
    window.alert(msg);
    if (callback && ("function" == typeof callback)) callback();
  }	
}

function showInfo(params) {
  var p = $.extend({'title': '提示', 'width': 500, 'timeout': 1500}, params);
  if (top.jQuery && top.jQuery.messager) {
    top.jQuery.messager.show(p);
  } 
  else if (jQuery && jQuery.messager) {
    jQuery.messager.alert(p);	  
  } else {
    window.alert(p.msg);
  }
}

function showConfirm(title, msg, fn1, fn2) {
  if (top.jQuery && top.jQuery.messager) {
    top.jQuery.messager.confirm(title, msg, function(r){
      if (r) {
        if (fn1 && ("function" == typeof fn1)) fn1();
      }	else {
        if (fn2 && ("function" == typeof fn2)) fn2(); 	  
      }
    });
  } 
  else if (jQuery && jQuery.messager) {
    jQuery.messager.confirm(title, msg, function(r){
      if (r) {
        if (fn1 && ("function" == typeof fn1)) fn1();
      }	else {
        if (fn2 && ("function" == typeof fn2)) fn2(); 	  
      }
    });
  } else {
    if (window.confirm(msg)) {
      if (fn1 && ("function" == typeof fn1)) fn1();
    } else {
      if (fn2 && ("function" == typeof fn2)) fn2();
    }
  }
}

/**
 * 判断datagrid是否并且只选中一条记录，返回选中行。
 */
function dataGridSelOne(datagrid, invalidInfo) {
  var rows = datagrid.datagrid('getSelections');
  if (1 != rows.length) {
    showAlert(invalidInfo ? invalidInfo : '请选择一条记录进行操作！');
	return null;
  }
  return rows[0];
}

function newTab(url, title, type) {
  if (!type) {
    var tabs = top.$('#tabs');
    tabs.tabs('add', {    
      title: title   
      , content: '<iframe id="monitorIframe" style="width:100%;height:98%;border:none" frameborder="none" src="' + url + '"></iframe>'    
      , closable: true   
    });
  } else {
	window.open(url); // chrome
    // window.open(url, title, 'fullscreen=1,location=no,titlebar=no,toolbar=no,menubar=no,status=no'); //ie
  }
}

function numberFormat(value) {
  var symbol = '';
  if (-1 != value.indexOf('-')) {
    symbol = '-';
    value = value.substring(1, value.length);
  }
  var ps = value.split(".");
  var prefixLen = ps[0].length%3;
  var prefix = ps[0].substring(0, prefixLen);
  for (var i = 0; i < parseInt(ps[0].length/3); i++) {
    if ('' != prefix) prefix += ',';
    prefix += ps[0].substring(prefixLen, prefixLen+3);
    prefixLen += 3;
  }
  return symbol + prefix + (1 < ps.length ? ("." + ps[1]) : "");	
}

function easyuiMoneyFormatter(value, row, index) {
  if (!value) return '0.00';
  if (value && !isNaN(value)) {
    value = parseFloat(value).toFixed(2);
    if (-1 == value.indexOf(',')) {
      return numberFormat(value);
    }
  }	
  return 0.00;
}

function easyuiMoneyStyler(value, row, index) {
  if (!value) return '';
  if (value && !isNaN(value)) {
    return 0 > value ? 'color:red' : ''; 
  }
}

function ajaxResultHandle(result, successCallback, errorCallback) {
  parent.$.messager.alert('提示', result.msg, 'info', function() {
    if (result.status) {
      if (successCallback) {successCallback(result);};
    } else {
      if (errorCallback) {errorCallback(result);};
    }
  });
}

/*
$(document).ready(function() {
  $('body').find('span.staffSelector').on('click', function(){
    var t = $(this);
    top.chooseStaff(top, function(staff){
      t.prev().val(staff.name).prev().val(staff.id);
    });
    return false;
  });
	  
  $('body').find('span.departmentSelector').on('click', function(){
    var t = $(this);
    top.chooseDept(top, function(dept){
      t.prev().val(dept.name).prev().val(dept.id);
    });
    return false;
  });
});*/