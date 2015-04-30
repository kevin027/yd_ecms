// 组织机构树ztree对象
var orgTreeObj;

// 组织机构树ztree异步加载节点的URL
var orgTreeAsyncUrl = "org/listOrgTreeNode";

// 组织机构树ztree的配置
var orgTreeSetting = {
  view: {
    selectedMulti: false
  }
  ,callback: {
    onExpand: function(event, treeId, treeNode) { // 展开时关闭其它同级节点
      if (orgTreeObj) {
        var siblings = orgTreeObj.getNodesByParam("pid", treeNode.pid, null);
        $.each(siblings, function() {
          if (treeNode.id != this.id && this.open) {
            orgTreeObj.expandNode(this, false, true);	
          }
        });
      }
    }
    ,onClick: function(event, treeId, treeNode) {
      $('#orgInfo').load('org/orgTreeNodeInfo', {'orgId' : treeNode.id});
    }
  }
};

$(document).ready(function() {
	
  // 加载机构树
  reloadZtree();
  
  // 为按钮注册事件
  $('button[id]').each(function() {
	var onClickFunction = window[this.id + 'Click'];
	if (onClickFunction) {
	  $(this).on('click', onClickFunction);
	}
  });
});

// 加载树方法
function reloadZtree() {
  $.getJSON('org/listOrgTreeWithoutCheckBox', function(orgTreeData) {
    orgTreeObj = $.fn.zTree.init($("#orgTree"), orgTreeSetting, orgTreeData);
    $('#orgInfo').html('');
  });
}

// 添加按钮事件
function addOrgBtnClick(ev) {
  var nodes = orgTreeObj.getSelectedNodes();
  
  var title = '新增机构';
  var url = 'org/addAuditOrg';
  if (0 < nodes.length) {
    var selNode = nodes[0];
    if ('company' != selNode.iconSkin && 'department' != selNode.iconSkin) {
    	$.messager.alert('提示', '该页面不支持对人员节点进行操作，请选择一个机构或部门。', 'error');
    	return false;
    }
    
    if (1 == selNode.isOrg) {
        $.messager.alert('提示', '该页面不支持对单位节点进行操作，请选择一个机构或部门。', 'error');
        return false;
    }
    
    title = '新增部门'	;
    url = 'org/addDepartment';
    url += '?orgId=' + selNode.id;
  }
  
  if (title == '新增机构') {
	var canAddAuditOrg = false;
	$.ajax({
	  url:'org/canAddAuditOrg'
	  ,type:'get'
	  ,dataType:'json'
	  ,cache:false
	  ,async:false
	  ,success: function(result) {
	    canAddAuditOrg = result.success;
	  }
	});
	if (!canAddAuditOrg) {
	  $.messager.alert('提示', '对不起，你没有权限添加机构节点，如果需要添加部门，请选择一个机构或部门节点。', 'error'); 
      return false;
	}
  }

  modalDialog = parent.sy.modalDialog({
	id:'u_frame'
	,title: title
	,width: 700
	,height: 400
	,href: url
	,onDestroy: function() {
      reloadZtree();
    }
  });
  
  return false;
}

// 删除按钮事件
function delOrgBtnClick(ev) {
  var nodes = orgTreeObj.getSelectedNodes();
  if (1 != nodes.length) {
    $.messager.alert('提示', '请先选择要删除的机构或部门！', 'error');
    return false;
  }
  
  var selNode = nodes[0];
  if ('company' != selNode.iconSkin && 'department' != selNode.iconSkin) {
    $.messager.alert('提示', '该页面不支持对人员节点进行操作，请选择一个机构或部门。', 'error');
    return false;
  }
  
  if (1 == selNode.isOrg) {
      $.messager.alert('提示', '该页面不支持对单位节点进行操作，请选择一个机构或部门。', 'error');
      return false;
  }
  
  if (!selNode.pid) {
    var canDelAuditOrg = false;
    $.ajax({
      url:'org/canDelAuditOrg'
      ,type:'get'
      ,dataType:'json'
      ,cache:false
      ,async:false
      ,success: function(result) {
        canDelAuditOrg = result.success;
      }
    });
    if (!canDelAuditOrg) {
      $.messager.alert('提示', '对不起，你没有权限删除机构节点。', 'error'); 
      return false;
    }
  }
  
  $.messager.confirm('警告', '数据删除后将不能恢复，您确定要删除当前所选项目？', function(r) {
	if (!r) return;
    var url = ('company' == selNode.iconSkin) ? 'org/delAuditOrg' : 'org/delDepartment';
    $.post(url, {'orgId' : selNode.id}, function(result) {
      $.messager.show({
        title : '提示'
        ,msg : result.success ? result.success : result.error
      });
      if (result.success) {
        reloadZtree(); 
      }
    }, 'json');
  });
  return false;
}

function expandOrgBtnClick(){
    orgTreeObj.expandAll(true);
}

function collapseOrgBtnClick(){
    orgTreeObj.expandAll(false);
}