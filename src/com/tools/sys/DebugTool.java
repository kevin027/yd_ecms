package com.tools.sys;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DebugTool {
	//输出对象的各种属性值
	public static void printValues(Object obj){
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            int length = name.length();
            String methodName = new StringBuffer("get").
            append(name.substring(0,1).toUpperCase()).
            append(name.substring(1,length)).toString();
            try {
            	Method method = obj.getClass().getDeclaredMethod(methodName);
            	Object result= method.invoke(obj);
            	if(result!=null) System.err.println("("+result.toString().length()+")"+name+"=>"+result);
            } catch (Exception e) {
              // e.printStackTrace();
            }
        }
	}
	
}
