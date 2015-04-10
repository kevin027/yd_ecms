package com.yida.core.common.ztree;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标识是返回遵循和jquery-ztree编写的数据格式。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JsonListResultForZtree {
	String version() default "3.5.14";
}
