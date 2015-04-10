// 模态窗口
var modalDialog;

// 表格对象
var datagrid;


//人员信息
var detailRowData = {
	toolbar : '#toolbar',
	pagination : false,
	pageSize : 10,
	singleSelect : true,
	height:450,
	striped : true,
	pageList : [ 5, 10, 15, 20, 25, 30, 35, 40, 45, 50 ],
	fit : true,
	fitColumns : false,
	nowrap : true,
	border : false,
	rownumbers : true,
	title : '',
	iconCls : 'icon-save',
	idField : 'id',
	columns : [ [ 
	 {checkbox : true},{
		field : 'name',
		title : '姓名',
		width : 100
	},{
		field : 'sex',
		title : '性别',
		width : 50,
		formatter:function(value, rowData, rowIndex) {
			if(1 == value)return "男";
			if(2 == value)return "女";
			if(3 == value)return "其他";
		}
	},{
		field : 'identityCard',
		title : '身份证',
		width : 100
	}, {
		field : 'specialty',
		title : '专业',
		width : 150
	},{
		field : 'position',
		title : '职务',
		width : 150
	}, {
		field : 'telephone',
		title : '联系电话',
		width : 150
	}, {
		field : 'phone',
		title : '手机',
		width : 150
	}, {
		field : 'email',
		title : '邮箱',
		width : 150
	} ] ]
};

$(function(){
	  // 根据参数生成表格
	  datagrid = $('#datagrid').datagrid($.extend(detailRowData, {url:'cooperPerson/listCooperPerson.do?cooperId='+$('#cooperId').val()}));;
		
	  // 注册按钮事件
	  $('.easyui-linkbutton[id]').each(function() {
		// 按约定检索是否有该事件。
		var onClickFunction = window[this.id + 'Click'];
		if (onClickFunction) {
		  $(this).on('click', onClickFunction);
		}
	  });
});

//添加按钮事件
function addBtnClick(ev) {
	modalDialog = top.sy.modalDialog({
		id:'u_frame'
		,title : '添加人员'
		,width : 800
		,height : 500
		,href : 'cooperPerson/addCooperPerson.do?cooperPerson.cooper.id='+$('#cooperId').val()
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
	  ,msg : '请选择一个人员进行编辑！' + (names.length > 0 ? '您已经选择了【' + names.join(',') + '】。' : '')
	  ,title : '提示'
    });
    return false;
  }
	
  modalDialog = top.sy.modalDialog({
	id:'u_frame'
	,title : '编辑人员'
	,width : 800
	,height : 500
	,href : 'cooperPerson/modCooperPerson.do?cooperPerson.id=' + rows[0].id
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
    top.$.messager.confirm('请确认', '您确定要删除当前所选人员？', function(r) {
      if (r) {
    	
    	// 获取选中ID，做为请求的参数
	    var data = {'cooperPerson.id' : rows[0].id};
		
		// 通过ajax请求删除人员。
        $.ajax({
          url : 'cooperPerson/delCooperPerson.do'
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