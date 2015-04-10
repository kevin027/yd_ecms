package com.yida.core.exception;

/**
 * 已经存在同名实体异常
 */
public class NameRepeatException extends RuntimeException {

	private static final long serialVersionUID = -333953449213570303L;

	private Class<?> type;
	private String repeatName;
	public NameRepeatException(Class<?> type, String repeatName) {
		this.type = type;
		this.repeatName = repeatName;
	}

	@Override
	public String getMessage() {
		return "记录同名错误：entity=" + type + ";repeatName=" + repeatName;
	}
}
