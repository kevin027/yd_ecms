var addActionUrl = 'account/addAccount.do';
var modActionUrl = 'account/modAccount.do';
var delActionUrl = 'account/delAccount.do';
var listActionUrl = 'account/listAccount.do';
var resetPasswordUrl = 'account/resetAccountPassword.do';

// 搜索栏
var queryBar = $('#queryBar');

// 模态窗口
var modalDialog;

// 表格对象
var datagrid;

// 表格默认参数。
var dataGridDefault = {
  pagination : true
  ,pageSize : 10
  ,striped : true
  ,pageList : [5, 10, 15, 20, 25, 30, 35, 40, 45, 50]
  ,fit : true
  ,fitColumns : false
  ,nowrap : false
  ,border : false
  ,rownumbers : true
  ,title : ''
  ,iconCls : 'icon-save'
  ,singleSelect : true
};

// 表格自定义数据
var dataGridCustom = {
  url : listActionUrl
  ,toolbar : '#toolbar'
  ,frozenColumns : [[ 
    {checkbox : true}
   ,{
     field : 'accounts',
	 title : '账号名称',
	 width : 120,
	 sortable : false
    }
  ]]
  ,columns : [[ 
   {field:'staffName', title:'关联人员', width:120}
   ,{field:'roleNames', title:'关联角色', width:400, tooltip:true}
   ,{field:'createDate', title:'创建日期', width:150}
   ,{field:'invalid', title:'是否有效', width:80, align:'left', formatter:function(value, rowData, rowIndex) {return ("0" == value)?"有效":"<span style='color:red'>无效</span>";}}
  ]]
  ,onRowContextMenu : function (e, rowIndex, rowData) {
    e.preventDefault();
    $(this).datagrid('unselectAll');
    $(this).datagrid('selectRow', rowIndex);
    $('#menu').menu('show', {
	  left : e.pageX,
	  top : e.pageY
    });
  }
};

// 文档加载完后要执行的方法
$(document).ready(function() {
	
  // 根据参数生成表格
  datagrid = $('#datagrid').datagrid($.extend(dataGridDefault, dataGridCustom));
	
  // 注册按钮事件
  $('.easyui-linkbutton[id]').each(function() {
	// 按约定检索是否有该事件。
	var onClickFunction = window[this.id + 'Click'];
	if (onClickFunction) {
	  $(this).on('click', onClickFunction);
	}
  });
});

// 查找按钮事件
function searchBtnClick(ev) {
  var query = {};
  queryBar.find('[name]').each(function() {
    query[this.name] = $.trim(this.value);
  });
  datagrid.datagrid('load', query);
}

// 清空按钮事件
function resetBtnClick(ev) {
  queryBar.find('input').val('');
  return false;
}

// 重置密码
function resetPasswordBtnClick(ev) {
  var rows = datagrid.datagrid('getSelections');
  if (1 != rows.length) {
    var names = [];
    for (var i = 0; i < rows.length; i++) {
      names.push(rows[i].accounts);
    }
    top.$.messager.alert('提示', '请选择一个账号进行编辑！' + (names.length > 0 ? '您已经选择了【' + names.join(',') + '】。' : ''), 'info');
    return false;
  }
  
  top.$.messager.confirm('请确认', '您要重置选中账号的密码为123吗？', function(r) {
    if (!r) return;
    $.ajax({
      url: resetPasswordUrl
      , data: {'accountId' : rows[0].id}
      , cache: false
      , type: "post"
      , dataType: "json"
      , success: function(result) {
          $.messager.alert('提示', result.msg, 'info');
      }
    });
  });
}

// 编辑按钮事件
function modAccountBtnClick(ev) {
  var rows = datagrid.datagrid('getSelections');
  if (1 != rows.length) {
    var names = [];
    for (var i = 0; i < rows.length; i++) {
	  names.push(rows[i].accounts);
	}
    top.$.messager.show({
	  width: '600'
	  ,msg : '请选择一个账号进行编辑！' + (names.length > 0 ? '您已经选择了【' + names.join(',') + '】。' : '')
	  ,title : '提示'
    });
    return false;
  }
  
  modalDialog = parent.sy.modalDialog({
	id:'u_frame',
	title : '编辑账号',
	width : 600,
	height : 400,
	href : modActionUrl + '?accountId=' + rows[0].id
    ,onDestroy : function() {
	  datagrid.datagrid('reload');	
    }
  });
  return false;
  
}

// 增加按钮事件
function addAccountBtnClick(ev) {	
  modalDialog = parent.sy.modalDialog({
    id:'u_frame',
    title : '增加账号',
    width : 600,
    height : 400,
    href : addActionUrl
    ,onDestroy : function() {
      datagrid.datagrid('reload');	
    }
  });
  return false;
}

//删除按钮事件
function delAccountBtnClick(ev) {	
  var rows = datagrid.datagrid('getSelections');
  if (1 != rows.length) {
    var names = [];
    for (var i = 0; i < rows.length; i++) {
	  names.push(rows[i].accounts);
	}
    top.$.messager.show({
	  width: '600'
	  ,msg : '请选择一个账号进行编辑！' + (names.length > 0 ? '您已经选择了【' + names.join(',') + '】。' : '')
	  ,title : '提示'
    });
    return false;
  }
  
  if (rows.length > 0) {
    top.$.messager.confirm('请确认', '您要删除当前所选项目？', function(r) {
      if (r) {
        // 获取选中ID，做为请求的参数
	    var data = {accountId : rows[0].id};
			
        // 通过ajax请求删除人员。
        $.ajax({
	      url : delActionUrl
		  ,data : data
		  ,cache : false
		  ,type : "post"
		  ,dataType : "json"
		  ,success : function(result) {
			$.messager.alert('提示', result.msg, 'info');
			if (result.status) {
			  datagrid.datagrid('unselectAll');
			  datagrid.datagrid('reload');
			}
		  }
	    });
      }
    });
  } else {
    top.$.messager.alert('提示', '请选择要删除的记录！', 'info');
  }
  return false;
}

//去除表头checkbox
$(function(){
	$(".datagrid-header-check > input").remove();
});