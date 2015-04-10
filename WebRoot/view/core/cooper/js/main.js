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
  ,singleSelect : true
  ,title : ''
  ,iconCls : 'icon-save'
  ,onRowContextMenu : function (e, rowIndex, rowData) {
    e.preventDefault();
    $(this).datagrid('unselectAll');
    $(this).datagrid('selectRow', rowIndex);
    $('#menu').menu('show', {
      left : e.pageX
      ,top : e.pageY
    });
  }
};

// 表格自定义数据
var dataGridCustom = {
  url : 'cooper/listCooper.do'
  ,toolbar : '#toolbar'
  ,idField : 'id'		

  ,columns : [[ 
	{checkbox : true}
	,{
	  field : 'name',
		 title : '单位名称',
		 sortable : false,
		 tooltip: false,
		 width : 200
	 },
	 {field : 'address',width : 250, title : '单位地址', align : 'left'}
    ,{field : 'postcode',width : 100, title : '邮政编码', align : 'left'}
    ,{field : 'unitPhone',width : 100, title : '单位电话', align : 'left'}
    ,{field : 'fax',width : 100, title : '传真', align : 'left'}
    ,{field : 'linkUser',width : 100, title : '联系人', align : 'left'}
    ,{field : 'linkPhone',width : 100, title : '联系电话', align : 'left'}
    ,{field : 'lastUpdateDate',width : 90, title : '最近更新时间', align : 'left'}
    ,{field : 'createDate',width : 90, title : '创建时间', align : 'left'}
    ,{field : 'handler.name',width : 90, title : '创建人', align : 'left', formatter:function(value1, rowData, rowIndex){var value = rowData['handler']; return (value) ? value.name : "";}}
    ,{field : 'remark',width : 160, title : '备注', align : 'left'}
  ]]
	,view : detailview
	,detailFormatter : function(index, row) {
		return '<div><iframe scrolling="auto" frameborder="0" id="datagrid-detailview'+index+'" src="" style="width:100%;height:400px;"></iframe></div>';
	},
	onExpandRow : function(index, row) {
		if($('#datagrid-detailview'+index).attr('src')==''){
			$('#datagrid-detailview'+index).attr('src','cooperPerson/cooperPerson.do?cooperId='+row.id);
		}
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

// 添加按钮事件
function addBtnClick(ev) {
	modalDialog = parent.sy.modalDialog({
		id:'u_frame'
		,title : '添加单位'
		,width : 800
		,height : 500
		,href : 'cooper/addCooper.do'
	    ,onDestroy : function() {
	      datagrid.datagrid('unselectAll');
		  datagrid.datagrid('reload');
	    }
	  });
	  return false;
}

// 编辑按钮事件
function modifyBtnClick(ev) {
  var rows = datagrid.datagrid('getSelections');
  if (1 != rows.length) {
    var names = [];
    for (var i = 0; i < rows.length; i++) {
	  names.push(rows[i].name);
	}
    top.$.messager.show({
	  width: '600'
	  ,msg : '请选择一个单位进行编辑！' + (names.length > 0 ? '您已经选择了【' + names.join(',') + '】。' : '')
	  ,title : '提示'
    });
    return false;
  }
	
  modalDialog = parent.sy.modalDialog({
	id:'u_frame'
	,title : '编辑单位'
	,width : 800
	,height : 500
	,href : 'cooper/modCooper.do?cooper.id=' + rows[0].id
    ,onDestroy : function() {
      datagrid.datagrid('unselectAll');
	  datagrid.datagrid('reload');	
    }
  });
  return false;
}

// 删除按钮事件
function delBtnClick(ev) {
  var rows = datagrid.datagrid('getSelections');
  if (rows.length == 1) {
    top.$.messager.confirm('请确认', '删除单位的同时会删除相关的人员，您确定要删除当前所选单位？', function(r) {
      if (r) {
    	
    	// 获取选中ID，做为请求的参数
	    var data = {'cooperId' : rows[0].id};
		
		// 通过ajax请求删除人员。
        $.ajax({
          url : 'cooper/delCooper.do'
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
		    top.$.messager.show({
		      title : '提示'
		      ,msg : resultMsg
		    });
		  }
        });
      }
    });
  } else {
    top.$.messager.alert('提示', '请选择一条要删除的记录！', 'error');
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