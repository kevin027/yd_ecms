// 表格默认参数。
var dataGridDefault = {
    pagination : true
    ,pageSize : 50
    ,striped : true
    ,pageList : [5, 10, 20, 30, 50, 100, 500, 1000, 5000, 10000]
    ,fit : true
    ,fitColumns : false
    ,nowrap : true
    ,border : false
    ,rownumbers : true
    ,singleSelect:true
    ,title : ''
    ,iconCls : ''
};
if($.fn.datagrid){
    $.fn.datagrid.defaults.pageList=dataGridDefault.pageList;
    $.fn.datagrid.defaults.pageSize=dataGridDefault.pageSize;
}
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
    var p = $.extend({'title': '提示','timeout': 1500}, params);
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

function showMsg(msg,title){
    if(!title) title="系统提示";
    showError(msg,title);
}

function showError(msg,title){
    if(!title) title="错误提示";
    $.messager.show({
        title:title,
        msg:msg,
        timeout:6000,
        showSpeed:300,
        showType:'fade',
        iconCls:'icon-warn',
        icon:' '
    });
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

/**
 * 金额格式化方法，显示两位小数并带千位分隔符
 */
function numberFormat(value) {
    if(null == value) return "";

    if (value.constructor == String) {
        value = parseFloat(value);
    }
    value = value.toFixed(2) + '';

    var symbol = '';
    if (-1 != value.indexOf('-')) {
        symbol = '-';
        value = value.substring(1, value.length);
    }
    var ps = value.split(".");
    var prefixLen = ps[0].length % 3;
    var prefix = ps[0].substring(0, prefixLen);
    for (var i = 0; i < parseInt(ps[0].length/3); i++) {
        if ('' != prefix) prefix += ',';
        prefix += ps[0].substring(prefixLen, prefixLen+3);
        prefixLen += 3;
    }
    return symbol + prefix + "." + ps[1];
}

/**
 * easyui单元格格式化，金额显示两位小数并带千位分隔符
 */
function easyuiMoneyFormatter(value, row, index) {
    if (null == value || ('' == value && 0 != value)) {
        return '';
    }
    if (0 == value) return '0.00';
    if (!isNaN(value)) {
        value = parseFloat(value).toFixed(2);
        if (-1 == value.indexOf(',')) {
            return numberFormat(value);
        }
    }
    return '0.00';
}

/**
 * easyui单元格格式化，金额显示三位小数并带千位分隔符
 */
function easyuiMoneyFormatter2(value, row, index) {
    if (value && 0 != value && !isNaN(value)) {
        value = parseFloat(value).toFixed(3);
        if (-1 == value.indexOf(',')) {
            return numberFormat(value);
        }
    }	else if (0 == value) {
        return '0.000';
    }
    return '';
}

function easyuiRatioFormatter(value, row, index) {
    if (null == value || '' == value) return '';
    if (0 == value) return '0.00';
    if (!isNaN(value)) {
        return parseFloat(value).toFixed(2);
    }
    return '0.00';
}

/**
 * easyui单元格格式化，日期只显示年月日部分
 */
function easyuiDateFormat(value, row, index) {
    if (!value) return '';
    return value.split(/\s+/)[0];
}

/**
 * easyui单元格样式，金额负数时显示红色
 * @param value
 * @param row
 * @param index
 * @returns
 */
function easyuiMoneyStyler(value, row, index) {
    if (!value) return '';
    if (value && !isNaN(value)) {
        return 0 > value ? 'color:red' : '';
    }
}

/**
 * easyui日期控件格式化-日期格式为yyyy-MM-dd
 * @param date
 * @returns {String}
 */
function dateFormatter(date) {
    var month = date.getMonth()+1;
    if (month < 10) month = ('0'+month);
    var day = date.getDate();
    if (day < 10) day = ('0'+day);
    return date.getFullYear()+'-'+month+'-'+day;
}

/**
 * easyui日期控件格式化-日期格式为yyyy年MM月dd日
 * @param date
 * @returns {String}
 */
function dateFormatter2(date) {
    var month = date.getMonth()+1;
    if (month < 10) month = ('0'+month);
    var day = date.getDate();
    if (day < 10) day = ('0'+day);
    return date.getFullYear()+'年'+month+'月'+day+'日';
}

/**
 * easyui日期控件格式化-日期格式为yyyy-MM-dd HH:mm
 * @param date
 * @returns {String}
 */
function dateFormatter3(date) {
    var month = date.getMonth()+1;
    if (month < 10) month = ('0'+month);
    var day = date.getDate();
    if (day < 10) day = ('0'+day);
    //return date.getFullYear()+'-'+month+'-'+day+' '+'';
    return date;
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

function stringToFloat(str) {
    if ('' == str) str = '0';
    return parseFloat(str.replace(/,/g, ''));
}

var $w=1000;
var $h=500;
$(function(){
    $w=$(window).width();
    $h=$(window).height();
    $(window).resize(function(){
        $w=$(window).width();
        $h=$(window).height();
    });
    //全局ajax事件,如果成功后,返回错误或者无权限,则提示
    $(document).ajaxSuccess(function(evt, request, settings){
        var result=request.responseJSON;
        if(!result){
            //尝试转换为json
            try{
                result=$.parseJSON(request.responseText);
            }catch(e){}
        }
        if(result){
            if(result.error){
                showError(result.msg,'请求错误');
            }
            else if(result.noPermission){
                showError(result.msg,'无权访问');
            }
        }


    });
    $(document).ajaxError(function(event,request, settings){
        showError('请求发生错误,请重试!','请求错误');
    });
});
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

var yd = $.extend({}, yd);/* 全局对象 */

yd.modalDialog = function(options) {
    var opts = $.extend({
        title : '&nbsp;',
        width : 640,
        height : 480,
        modal : true,
        onClose : function() {
            $(this).dialog('destroy');
        }
    }, options);
    opts.modal = true;

    return $('<div/>').dialog(opts);
};

function easyuiNumber2(value, row, index) {
    if(value&&typeof(value)=='number') return value.toFixed(2);
    return value;
}
function easyuiNumber3(value, row, index) {
    if(value&&typeof(value)=='number') return value.toFixed(3);
    return value;
}


function formatBoolean(value, row, index) {
    return value?"是":"否";
}

function formatShortTime(value){
    return value;
}
