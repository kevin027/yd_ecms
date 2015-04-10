package com.tools.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.METHOD})   
@Retention(RetentionPolicy.RUNTIME)  
public abstract @interface CheckNull {
	/**
	 *	字段的中文名字 ,用作提示信息
	 */
	public abstract String name() default "";
	/***
	 * 如果设置为true，则保存更新时就检测该字段是否为空
	 * @return
	 */
	public abstract boolean isCheck() default false; 
	
	/**
	 * 检查的批次
	 * 一个对象中如果字段过多，不是所有的字段都在同一方法里必须，则用此字段来区分
	 * @return
	 */
	public abstract int batch() default 0;
}
