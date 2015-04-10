var listUrl = 'log/listLog.do';

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
};

// 表格自定义数据
var dataGridCustom = {
  url : listUrl
  ,toolbar : '#toolbar'
  ,idField : 'id'		
  ,frozenColumns : [[ 
    {checkbox : false}
   ,{
     field : 'operator',
	 title : '操作人',
	 width : 100,
	 sortable : false
    }
  ]]
,singleSelect : true
  ,columns : [[ 
	 {field:'opType', width:160, title:'操作类型'}
	,{field:'module', width:200, title:'系统模块'}
	,{field:'opDate', width:160, title:'操作时间'}
	,{field:'description', width:400, title:'详细信息'}
    ,{field:'ip', width:160, title:'操作IP地址'}
  ]]
  ,onRowContextMenu : function (e, rowIndex, rowData) {
    e.preventDefault();
    $(this).datagrid('unselectAll');
    $(this).datagrid('selectRow', rowIndex);
    $('#menu').menu('show', {
	  left : e.pageX,
	  top : e.pageY
    });
  },
	onBeforeLoad:	function(data){
		$(this).datagrid('uncheckAll');
		$(this).datagrid('unselectAll');
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

//去除表头checkbox
$(function(){
	$(".datagrid-header-check > input").remove();
});