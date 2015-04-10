package com.yida.core.base.vo;

import java.util.List;

import com.yida.core.base.entity.Function;

public class ListFunctionForm {
	
	private String parentFunctionId;
	private List<String> excludeFunctionIds;
	private Function.Type functionType;

	public String getParentFunctionId() {
		return parentFunctionId;
	}

	public void setParentFunctionId(String parentFunctionId) {
		this.parentFunctionId = parentFunctionId;
	}

	public List<String> getExcludeFunctionIds() {
		return excludeFunctionIds;
	}

	public void setExcludeFunctionIds(List<String> excludeFunctionIds) {
		this.excludeFunctionIds = excludeFunctionIds;
	}

	public Function.Type getFunctionType() {
		return functionType;
	}

	public void setFunctionType(Function.Type functionType) {
		this.functionType = functionType;
	}
	
}
