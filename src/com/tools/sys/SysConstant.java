package com.tools.sys;

public class SysConstant {
	public final static String JSON_RESULT_PAGE = "json";
	public final static String AJAX_REQ_STATUS = "status";
	public final static String AJAX_MSG = "msg";
	public final static String AJAX_SUCCESS = "success";
	public final static String AJAX_ERROR = "error";
	public final static String MAIN_MENU = "MAIN_MENU";
	public final static String HTTP_SESSION_ID = "HTTP_SESSION_ID";
	public final static String LOGIN_ACCOUNT = "LOGIN_ACCOUNT";
	public static String KEY_SESSION_PERMISSION="USER_PERMISSION";
	
	/*-------------------------日志方式----------------------------------*/
	public static final int OP_UNKNOW = -1;//退回
	public static final int OP_File = 0;//文件
	public static final int OP_ADD = 1;  //增加
	public static final int OP_DELETE = 2;//删除
	public static final int OP_UPDATE = 3;//修改
	public static final int OP_EVAL_CHECK = 4;//检测
	public static final int OP_EVAL_SORT = 5;//排序
	public static final int OP_OVER = 6;//结束
	public static final int OP_FAIL = 7;//失败
	public static final int OP_SUBMIT = 8;//提交
	public static final int OP_LOGIN = 9;//登录
	public static final int OP_UPLOADFILE_ERROR = 10;//上传文件出错
	public static final int OP_PROJECT_FLOW = 11;//项目操作过程
	public static final int OP_SAVE = 12;//保存
}
