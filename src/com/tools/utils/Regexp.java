package com.tools.utils;

/**
 * @描述 正则表达式类
 * @author kevin
 *
 */
public enum Regexp {
	
	Account("^[A-z\\d_\\u4e00-\\u9fa5]{3,16}$"),
	EMAIL("\\w+([-+\\.']\\w+)*@\\w+([-\\.]\\w+)*\\.\\w+([-\\.]\\w+)*"),
	// PHONE_NO("^0?(13[0-9]?|15[8-9]?|153|156|18[7-9])[0-9]{8}$|^(\\d{3,4}-)?\\d{3,8}$"); 
	PHONE_NO("^1\\d{10}$");
	
	private String regExp;
	
	private Regexp(String regExp) {
		this.regExp = regExp;
	}
	
	public boolean validate(String testStr) {
		return testStr.matches(this.regExp);
	}

	@Override
	public String toString() {
		return (null == regExp) ? super.toString() : regExp;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(Regexp.Account.validate("Clarence"));
		System.out.println(Regexp.PHONE_NO.validate("13760671221"));
		System.out.println(Regexp.PHONE_NO.validate("0759-2310663"));
		System.out.println(Regexp.EMAIL.validate("clarence0124@gmail.com"));
	}
	
}
