// 功能选择树
var funTreeObj; 
	
//树显示的一些设置
var funTreeSetting = {
  view: {
    selectedMulti: false
  }
  , check: {
    enable: true
    ,chkStyle: 'checkbox' 
    ,chkboxType: {chkboxType: { "Y": "p", "N": "s" }}
  }
  , callback: {
    onExpand: function(event, treeId, treeNode) { // 展开时关闭其它同级节点
      if (funTreeObj) {
        var siblings = funTreeObj.getNodesByParam("pid", treeNode.pid, null);
        $.each(siblings, function() {
          if (treeNode.id != this.id && this.open) {
            funTreeObj.expandNode(this, false, true);	
          }
        });
      }
    }
    , onClick: function (event, treeId, treeNode) {
        $('#functionInfo').load('fun/modFunction.do', {'function.id' : treeNode.id});
    }
  }
};

(function($) {

  //加载树
  loadFunctionTree();
  
  //注册按钮事件
  $('button[id]').each(function() {
	// 按约定检索是否有该事件。
	var onClickFunction = window[this.id + 'Click'];
	if (onClickFunction) {
	  $(this).on('click', onClickFunction);
	}
  });

})(jQuery);

//加载功能树
function loadFunctionTree() {
  $.getJSON('fun/listFunctionForSelect.do', function(zTreeNodes) {
    funTreeObj = $.fn.zTree.init($("#funTree"), funTreeSetting, zTreeNodes);
    $('#functionInfo').html();
  });
}

//增加功能按钮事件
function addFunctionBtnClick() {
  var url = 'fun/addFunction.do';
  var nodes = funTreeObj.getSelectedNodes();
  if (1 == nodes.length) {
    url = url + '?function.parent.id=' + nodes[0].id;
  }
  
  modalDialog = parent.sy.modalDialog({
	id:'u_frame'
	,title : '添加功能'
	,iconCls:'icon-server_add'
	,width : 650
	,height : 480
	,href : url
	,onDestroy: function() {
	  loadFunctionTree();
    }
  });
};

// 删除功能按钮事件
function delFunctionBtnClick() {
  var nodes = funTreeObj.getSelectedNodes();
  if (1 != nodes.length) {
    $.messager.alert('提示', '请先选择要删除的功能！', 'error');
    return false;
  }
  
  var selNode = nodes[0];
  if (selNode.children && 0 < selNode.children.length) {
    $.messager.alert('提示', '该功能节点下仍存在子功能节点，不能删除！', 'error');
    return false;
  }

  $.messager.confirm('警告', '数据删除后将不能恢复，您确定要删除当前所选项目？', function(r) {
    if (!r) return;
    var url = 'fun/deleteFunction.do?function.id=' + selNode.id;
    $.post(url, function(result) {
	  $.messager.show({
	    title : '提示'
	    ,msg : result.success ? result.success : result.error
      });
	  if (result.success) {
	    loadFunctionTree(); 
	  }
    }, 'json');
  });
}


//修改功能按钮事件
function modFunctionBtnClick() {
  var nodes = funTreeObj.getSelectedNodes();
  if (1 != nodes.length) {
    $.messager.alert('提示', '请先选择要删除的功能！', 'error');
    return false;
  }
  if (!selNode) {
    showTips('请选择一个节点进行操作。');
    return false;
  }
  
  var url = 'fun/editFunctionPage.action?do.id=' + selNode.id;
  $.layer({
      type : 2,
      shade : [0],
      title : ['修改功能',true],
      iframe : {src : url},
      shade : [0.5 , '#000' , true],
      area : ['800px' , '600px'],
      offset : ['50%', '50%'],
      end : function() {
	    window.location.href = window.location.href;
	  }
  });
}
