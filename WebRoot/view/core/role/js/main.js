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
};

// 表格自定义数据
var dataGridCustom = {
  url : 'role/listRole'
  ,idField: 'id'
  ,toolbar: '#toolbar'	
  ,frozenColumns : [[ 
    {checkbox : true}
   ,{
     field: 'name',
	 title: '角色名称',
	 width: 180,
	 sortable : false,
	 tooltip: false
    }
  ]]
  ,columns: [[ 
    {field: 'invalid', title : '是否有效', width : 100, formatter : function(value, rowData, rowIndex) {return ("0" == value)?"有效":"<span style='color:red'>无效</span>";}}
   ,{field: 'accountNames', title : '关联帐号', tooltip:true, width : 400}
   ,{field: 'remark', title : '备注', tooltip:true, width : 400} 
  ]]
  ,singleSelect: true
  ,onSelect: function(rowIndex, rowData) {
    loadAccountTreeAfterSelectRole();
    loadFunctionTreeAfterSelectRole();
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
  queryBar.find('[name^=query]').each(function() {
    query[this.name] = $.trim(this.value);
  });
  datagrid.datagrid('load', query);
}

// 清空按钮事件
function resetBtnClick(ev) {
  queryBar.find('input').val('');
  return false;
}

// 添加按钮事件
function addRoleBtnClick(ev) {
  modalDialog = parent.sy.modalDialog({
	id:'u_frame'
	,title : '添加角色'
	,iconCls:'icon-user'
	,width : 700
	,height : 450
	,href : 'role/addRole'
    ,onDestroy : function() {
      datagrid.datagrid('unselectAll');
	  datagrid.datagrid('reload');	
    }
  });
  return false;
}

// 编辑按钮事件
function modRoleBtnClick(ev) {
  var rows = datagrid.datagrid('getSelections');
  if (1 != rows.length) {
    $.messager.show({
	  width: '600'
	  ,msg : '请选择一个角色进行编辑！'
	  ,title : '提示'
    });
    return false;
  }
	
  modalDialog = parent.sy.modalDialog({
	id:'u_frame'
	,title : '编辑用户'
	,width : 700
	,height : 600
	,href : 'role/modRole?roleId=' + rows[0].id
    ,onDestroy : function() {
      datagrid.datagrid('unselectAll');
	  datagrid.datagrid('reload');	
    }
  });
  return false;
}

// 删除按钮事件
function delRoleBtnClick(ev) {
  var rows = datagrid.datagrid('getSelections');
  if (rows.length > 0) {
    $.messager.confirm('请确认', '您要删除当前所选项目？', function(r) {
      if (r) {
    	
    	// 获取选中ID，做为请求的参数
        /*
	    var data = {};
		for (var i = 0; i < rows.length; i++) {
		  data['accountIds[' + i + ']'] = rows[i].id;
		}*/
		
    	var data = {'roleId' : rows[0].id};
    	  
		// 通过ajax请求删除。
        $.ajax({
          url : 'role/delRole'
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
  datagrid.datagrid('reload');
  return false;
}

// 权限树
var functionTree;
function loadFunctionTreeAfterSelectRole() {
  var rows = datagrid.datagrid('getSelections');
  if (rows.length > 0) {
    var roleId = rows[0].id;
    $.get('fun/listFunctionForSelect?selRoleId=' + roleId, function(zTreeNodes) {
      // 树显示的一些设置
      var setting = {
        view: {
          selectedMulti: false
        }
        ,check: {
          enable:true
         ,chkStyle:"checkbox" 
         ,chkboxType: {"Y": "ps", "N": "s"}
        }
        ,callback : {
          onExpand : function(event, treeId, treeNode) {
            if (functionTree) {
              var siblings = functionTree.getNodesByParam("pid", treeNode.pid, null);
              $.each(siblings, function() {
            	if (treeNode.id != this.id && this.open) {
            	  functionTree.expandNode(this, false, true);	
            	}
              });
            }	  
          }
        }
      };
      functionTree = $.fn.zTree.init($("#functionTree"), setting, zTreeNodes);
    }, 'json');
  }
}

// 人员树
var accountTree;
function loadAccountTreeAfterSelectRole() {
  var rows = datagrid.datagrid('getSelections');
  if (rows.length > 0) {
    var roleId = rows[0].id;
    $.get('org/listOrgTreeForAccountSelect?selRoleId=' + roleId, function(zTreeNodes) {
      // 树显示的一些设置
      var setting = {
        view: {
          selectedMulti: false
        }
        ,check: {
          enable:true
         ,chkStyle:"checkbox" 
         ,chkboxType: {"Y": "ps", "N": "s"}
        }
        ,callback : {
          onExpand : function(event, treeId, treeNode) {
            if (accountTree) {
              var siblings = accountTree.getNodesByParam("pid", treeNode.pid, null);
              $.each(siblings, function() {
            	if (treeNode.id != this.id && this.open) {
            	  accountTree.expandNode(this, false, true);	
            	}
              });
            }	  
          }
        }
      };
      accountTree = $.fn.zTree.init($("#accountTree"), setting, zTreeNodes);
    }, 'json');
  }
}

//功能树授权按钮事件
function functionEmpowerBtnClick(ev) {
	
  // 表格选中的角色ID
  var rows = datagrid.datagrid('getSelections');
  if (1 != rows.length) {
    top.$.messager.alert('信息', '请选择一条角色记录进行操作。', 'info');
    return false;
  }
  
  if (!functionTree) {
    return false;
  }
  
  $.messager.confirm('确定要为当前角色列表中选中的记录添加选中的功能吗？', function(r) {
    if (r) {
      // 功能树选中的功能ID
      var ids = [];
      $.each(functionTree.getCheckedNodes(true), function() {
        ids.push(this.id);
      });
    	  
      // 参数
      var data = {
        'roleId': rows[0].id
        ,'functionIds' : ids.join(',')
      };
    	  
      $.post('role/functionEmpower', data, function(result) {
    	var rmsg = result.success ? result.success : result.error;
    	$.messager.alert('提示', rmsg, 'info');
    	if (result.success) {
      	  datagrid.datagrid('reload');	
      	}
      }, 'json');
    } 
  });
}

//功能树全选或取消全选按钮事件
var functionCheckAll = false;
function functionCheckAllBtnClick(ev) {
  if (!functionTree) return false;
  functionCheckAll = !functionCheckAll;
  functionTree.checkAllNodes(functionCheckAll);	  
  return false;
}

// 功能树展开全部按钮事件
function functionExpandAllBtnClick(ev) {
  if (!functionTree) return false;
  functionTree.expandAll(true);	  
  return false;
}

// 功能树收起全部按钮事件
function functionCollapseAllBtnClick(ev) {
  if (!functionTree) return false;
  functionTree.expandAll(false);	  
  return false;
}

// 组织机构树授权按钮事件
function accountEmpowerBtnClick(ev) { 
	
  // 检查是否已经选中了一条角色进行操作
  var rows = datagrid.datagrid('getSelections');
  if (1 != rows.length) {
    top.$.messager.alert('信息', '请选择一条角色记录进行操作。', 'info');
    return false;
  }

  // 检查是否已经加载了账号信息
  if (!accountTree) {
    return false;
  }
  
  $.messager.confirm('确定要为当前角色列表中选中的记录授予选中的账号吗？', function(r) {
    if (r) {
      // 功能树选中的功能ID
      var ids = [];
      $.each(accountTree.getCheckedNodes(true), function() {
        ids.push(this.id);
      });
    	  
      // 参数
      var data = {
        'roleId': rows[0].id
        ,'accountIds' : ids.join(',')
      };
    	  
      $.post('role/accountEmpower', data, function(result) {
    	var rmsg = result.success ? result.success : result.error;
    	$.messager.alert('提示', rmsg, 'info');
    	if (result.success) {
    	  datagrid.datagrid('reload');	
    	}
      }, 'json');
    } 
  });
}

// 组织机构树全选或取消全选按钮事件
var accountCheckAll = false;
function accountCheckAllBtnClick(ev) {
  if (!accountTree) return false;
  accountCheckAll = !accountCheckAll;
  accountTree.checkAllNodes(accountCheckAll);	  
  return false;
}

// 组织机构树展开全部按钮事件
function accountExpandAllBtnClick(ev) {
  if (!accountTree) return false;
  accountTree.expandAll(true);	  
  return false;
}

// 组织机构树收起全部按钮事件
function accountCollapseAllBtnClick(ev) {
  if (!accountTree) return false;
  accountTree.expandAll(false);	  
  return false;
}

//去除表头checkbox
$(function(){
	$(".datagrid-header-check > input").remove();
});