<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%    
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName()+":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath %>"/>
	<title>账号管理主页面</title>
	<meta charset="utf-8" />
	<jsp:include page="/inc.jsp"></jsp:include>
<script type="text/javascript" charset="UTF-8">
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	$(function() {

		userForm = $('#userForm').form();

		//编辑用户
		userDialog = $('#userDialog').show().dialog({
			modal : true,
			title : '用户信息',
			buttons : [ {
				text : '确定',
				iconCls :'icon-save',
				handler : function() {
					if (userForm.find('[name=id]').val() != '') {
						userForm.form('submit', {
							url : 'userController.do?edit',
							success : function(data) {
								userDialog.dialog('close');
								$.messager.show({
									msg : '用户编辑成功！',
									title : '提示'
								});
								datagrid.datagrid('reload');
							}
						});
					} else {
						userForm.form('submit', {
							url : 'userController.do?add',
							success : function(data) {
								try {
									var d = $.parseJSON(data);
									if (d) {
										userDialog.dialog('close');
										$.messager.show({
											msg : '用户创建成功！',
											title : '提示'
										});
										datagrid.datagrid('reload');
									}
								} catch (e) {
									$.messager.show({
										msg : '用户名称已存在！',
										title : '提示'
									});
								}
							}
						});
					}
				}
			} ]
		}).dialog('close');

		//初始化表格
		datagrid = $('#datagrid').datagrid({
			url : 'account/listAccount.do',
			toolbar : '#toolbar',
			title : '',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			pageList : [ 5, 10, 15, 20, 25, 30, 35, 40, 45, 50 ],
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'id',
			frozenColumns : [ [ {
				title : 'id',
				field : 'id',
				width : 50,
				checkbox : true
			}, {
				field : 'accounts',
				title : '用户名称',
				width : 100,
				sortable : true
			} ] ],
			columns : [ [ {
				field : 'password',
				title : '密码',
				width : 100,
				formatter : function(value, rowData, rowIndex) {
					return '******';
				}
			}, {
				field : 'createDate',
				title : '创建时间',
				width : 150,
				sortable : true
			}, {
				field : 'createDate',
				title : '最后修改时间',
				width : 150,
				sortable : true
			}, {
				field : 'roleText',
				title : '角色',
				width : 200
			}, {
				field : 'roleId',
				title : '角色',
				width : 200,
				hidden : true
			} ] ],
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});

	});

	function append() {
		userDialog.dialog('open');
		passwordInput.validatebox({
			required : true
		});
		userForm.find('[name=name]').removeAttr('readonly');
		userForm.form('clear');
	}

	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length != 1 && rows.length != 0) {
			var names = [];
			for ( var i = 0; i < rows.length; i++) {
				names.push(rows[i].name);
			}
			$.messager.show({
				msg : '只能择一个用户进行编辑！您已经选择了【' + names.join(',') + '】' + rows.length + '个用户',
				title : '提示'
			});
		} else if (rows.length == 1) {
			passwordInput.validatebox({
				required : false
			});
			userForm.find('[name=name]').attr('readonly', 'readonly');
			userDialog.dialog('open');
			userForm.form('clear');
			userForm.form('load', {
				id : rows[0].id,
				name : rows[0].name,
				password : '',
				createdatetime : rows[0].createdatetime,
				modifydatetime : rows[0].modifydatetime,
				roleId : sy.getList(rows[0].roleId)
			});
		}
	}

	function editRole() {
		var ids = [];
		var rows = datagrid.datagrid('getSelections');
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			userRoleForm.find('input[name=userIds]').val(ids.join(','));
			userRoleDialog.dialog('open');
		} else {
			$.messager.alert('提示', '请选择要编辑的记录！', 'error');
		}
	}

	function remove1() {
		var ids = [];
		var rows = datagrid.datagrid('getSelections');
		if (rows.length > 0) {
			$.messager.confirm('请确认', '您要删除当前所选项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : 'userController.do?del',
						data : {
							ids : ids.join(',')
						},
						cache : false,
						dataType : "json",
						success : function(response) {
							datagrid.datagrid('unselectAll');
							datagrid.datagrid('reload');
							$.messager.show({
								title : '提示',
								msg : '删除成功！'
							});
						}
					});
				}
			});
		} else {
			$.messager.alert('提示', '请选择要删除的记录！', 'error');
		}
	}

	function searchFun() {
		datagrid.datagrid('load', {
			name : $('#toolbar input[name=name]').val(),
			createdatetimeStart : $('#toolbar input[comboname=createdatetimeStart]').datetimebox('getValue'),
			createdatetimeEnd : $('#toolbar input[comboname=createdatetimeEnd]').datetimebox('getValue'),
			modifydatetimeStart : $('#toolbar input[comboname=modifydatetimeStart]').datetimebox('getValue'),
			modifydatetimeEnd : $('#toolbar input[comboname=modifydatetimeEnd]').datetimebox('getValue')
		});
	}
	function clearFun() {
		$('#toolbar input').val('');
		datagrid.datagrid('load', {});
	}
</script>
</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;" class="panel-body panel-body-noheader panel-body-noborder">
		<table id="datagrid"></table>
	</div>
  
	<div id="userDialog" iconCls="icon-mini-add" style="display: none;overflow: hidden;height:250px;width:500px">
		<form id="userForm" method="post">
			<table class="tableForm" style="margin:10px auto">
				<tr>
					<th>登录名称</th>
					<td><input name="name" class="easyui-validatebox" required="true" missingMessage 
					="登录名不能为空哦！"/></td>
				</tr>
				<tr >
					<th>登录密码</th>
					<td><input name="password" type="password" required="true" missingMessage 
					="密码也不能为空哦！"/></td>
				</tr>
				
				<tr>
					<th>账户类型</th>
					<td><input name="roleType" class="easyui-validatebox" required="true" missingMessage 
					="类型也不能偷懒不填！"/></td>
				</tr>				
				<tr>
					<th>创建日期</th>
					<td><input name="createdatetime" class="easyui-datebox" style="width: 156px;" /></td>
				</tr>
				<tr>
					<th>所属角色</th>
					<td><select name="roleId" style="width: 156px;">
					<option>管理员</option>
					<option>普通用户</option>
					</select></td>
				</tr>
			</table>
		</form>
	</div>


	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="append();" iconCls="icon-add">增加</div>
		<div onclick="remove1();" iconCls="icon-remove">删除</div>
		<div onclick="edit();" iconCls="icon-edit">编辑</div>
	</div>
</body>
</html>