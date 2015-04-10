// 模态窗口
var modalDialog;
// 表格对象
var datagrid;
// 表格默认参数。
var dataGridDefault = {
  pagination : true
  ,pageSize : 50
  ,striped : true
  ,idField : 'id'
  ,pageList : [5, 10, 15, 20, 25, 30, 35, 40, 45, 50]
  ,fit : true
  ,fitColumns : false
  ,nowrap : true
  ,border : true
  ,rownumbers : true
  ,singleSelect:true
  ,onRowContextMenu : function (e, rowIndex, rowData) {
	    e.preventDefault();
	    $(this).datagrid('unselectAll');
	    $(this).datagrid('selectRow', rowIndex);
	    $('#menu').menu('show', {
		  left : e.pageX,
		  top : e.pageY
	    });
	  }
	,enableHeaderContextMenu: true    //此属性开启表头列名称右键点击菜单
	,enableHeaderClickMenu: false //此属性开启表头列名称右侧那个箭头形状的鼠标左键点击菜单
};
