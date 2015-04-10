package com.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kevin
 */
@Target(ElementType.METHOD)   
@Retention(RetentionPolicy.RUNTIME)  
public @interface LogAudit {
	
	/**
	 * 操作人
	 * @return
	 */
	//public abstract String operator();
	
	/**
	 * 操作ip
	 * @return
	 */
	//public abstract String ip();
	
	/**
	 * 模块名
	 * @return
	 */
	public abstract String module() default "";
	
	/**
	 * 操作类型
	 * @return
	 */
	public abstract String opType() default "";
	
	/**
	 * 操作内容
	 * @return
	 * --参数写法按照：arg0,arg1,arg2....依次填写
	 */
	public abstract String description();
	
	/**
	 * 是否强制记录日志,此选项将忽略线程值,直接读取方法名中的对应参数
	 * @return
	 */
	public abstract boolean focusLog() default false;
	
}
