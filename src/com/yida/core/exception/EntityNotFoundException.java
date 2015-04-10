package com.yida.core.exception;

import java.io.Serializable;

public class EntityNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private Class<?> type;
	private Serializable id;
	public EntityNotFoundException(Class<?> type, Serializable id) {
		this.type = type;
		this.id = id;
	}

	@Override
	public String getMessage() {
		return String.format("找不到%1$s[id=%2$s]的记录，可能已经被删除。", type.getSimpleName(), id);
	}
}
