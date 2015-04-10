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
  ,nowrap : true
  ,border : false
  ,rownumbers : true
  ,title : ''
  ,iconCls : 'icon-save'
  ,singleSelect : true
};

// 表格自定义数据
var dataGridCustom = {
  url : 'staff/listStaff.do'
  ,toolbar : '#toolbar'
  ,frozenColumns : [[ 
    {checkbox : true}
   ,{
     field : 'name',
	 title : '姓名',
	 width : 120,
	 sortable : false,
	 tooltip: false
    }
  ]]
  ,columns : [[ 
    {field : 'sex', title : '性别', width : 60, align : 'left', formatter : function(value, rowData, rowIndex) {switch(value){case "MALE": return "男"; case "FEMALE" : return "女"; default: return "";}}}
   ,{field : 'orgNames', title : '所属机构', width : 300, tooltip:true}
   ,{field : 'isOrg', title : '人员类型', width : 120, align : 'left', formatter : function(value, rowData, rowIndex) {switch(value){case 1: return "<span style='color:red;'>中介单位人员</span>"; case 2 : return "<span style='color:red;'>建设单位人员</span>"; default: return "中心人员";}}}
   ,{field : 'createDate', title : '创建日期', width : 100, tooltip:true}
   ,{field : 'phone', title : '手机号码', width : 100}
   ,{field : 'email', title : '电子邮箱', width : 200}
   ,{field : 'remark', title : '备注', width : 600, tooltip:true} 
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

//添加人员详细信息
function addUserBaseInfoClick(ev){
	top.$("#tabs").tabs('add',{
		title: '新增用户信息',
		selected: true,
		closable : true,
		content : '<iframe scrolling="auto" frameborder="0"  src="core/staff/jsp/addStaffInfo.jsp" style="width:100%;height:98%;"></iframe>'
	  }
  );
}

// 清空按钮事件
function resetBtnClick(ev) {
  queryBar.find('input').val('');
  return false;
}

// 添加按钮事件
function addStaffBtnClick(ev) {
  modalDialog = top.sy.modalDialog({
	id:'u_frame'
	,title : '添加用户'
	,width : 800
	,height : 600
	,href : 'staff/addStaff.do'
    ,onDestroy : function() {
      datagrid.datagrid('unselectAll');
	  datagrid.datagrid('reload');
    }
  });
  return false;
}

// 编辑按钮事件
function modStaffBtnClick(ev) {
  var rows = datagrid.datagrid('getSelections');
  if (1 != rows.length) {
    var names = [];
    for (var i = 0; i < rows.length; i++) {
	  names.push(rows[i].name);
	}
    top.$.messager.show({
	  width: '600'
	  ,msg : '请选择一个用户进行编辑！' + (names.length > 0 ? '您已经选择了【' + names.join(',') + '】。' : '')
	  ,title : '提示'
    });
    return false;
  }
  
  if (1 == rows[0].isOrg || 2 == rows[0].isOrg) {
      $.messager.alert('提示', '该页面不支持对单位人员进行操作，请选择一个中心内部人员。', 'error');
      return false;
  }
  
  modalDialog = top.sy.modalDialog({
	id:'u_frame'
	,title : '编辑用户'
	,width : 800
	,height : 600
	,href : 'staff/modStaff.do?staffId=' + rows[0].id
    ,onDestroy : function() {
      datagrid.datagrid('unselectAll');
	  datagrid.datagrid('reload');	
    }
  });
  return false;
}

// 删除按钮事件
function delStaffBtnClick(ev) {
  var rows = datagrid.datagrid('getSelections');
  if (rows.length > 0) {
    $.messager.confirm('请确认', '您要删除当前所选项目？', function(r) {
      if (r) {
    	
    	// 获取选中ID，做为请求的参数
	    var data = {};
		for (var i = 0; i < rows.length; i++) {
			if (1 == rows[i].isOrg || 2 == rows[i].isOrg) {
		      $.messager.alert('提示', '该页面不支持对单位人员进行操作，请选择一个中心内部人员。', 'error');
		      return false;
			}
			data['staffIds[' + i + ']'] = rows[i].id;
		}
		
		// 通过ajax请求删除人员。
        $.ajax({
          url : 'staff/deleteStaffs.do'
		  ,data : data
		  ,cache : false
		  ,dataType : "json"
		  ,success : function(result) {
			var resultMsg;
			if (result.success) {
			  resultMsg = result.success;
			  datagrid.datagrid('unselectAll');
			  datagrid.datagrid('reload');
			} else {
			  resultMsg = result.error;
			}
		    $.messager.show({
		      title : '提示'
		      ,msg : resultMsg
		    });
		  }
        });
      }
    });
  } else {
    $.messager.alert('提示', '请选择要删除的记录！', 'error');
  }
  return false;
}

// 取消选择按钮事件
function cancelSelectBtnClick() {
  datagrid.datagrid('unselectAll');
  return false;
}

//去除表头checkbox
$(function(){
	$(".datagrid-header-check > input").remove();
});