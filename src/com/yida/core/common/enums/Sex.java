package com.yida.core.common.enums;

public enum Sex {
	
	FEMALE("女"),MALE("男"),OTHER("其它");
	
	private final String chinese;
	private Sex(String chinese) {
		this.chinese = chinese;
	}

	public String getChinese() {
		return chinese;
	}
	
}
