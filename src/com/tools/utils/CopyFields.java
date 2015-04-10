package com.tools.utils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;


/**
 * 字段复制
 *
 */
public class CopyFields {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
    }
    /**
     * 
     * @param original 原始对象
     * @param target 目标对象
     */
    public static Object doCopy(Object original, Object target) {
        
        Field[] fields = original.getClass().getDeclaredFields();
        
        Method originalMethod = null;
        Method targetMethod = null;
        
        String originalMethodName = null;
        String targetMethodName = null;
        
        for (Field field : fields) {
           
            String name = field.getName();
            int length = name.length();
            
            originalMethodName = new StringBuffer("get").
            append(name.substring(0,1).toUpperCase()).
            append(name.substring(1,length)).toString();
            
            targetMethodName = new StringBuffer("set").
            append(name.substring(0,1).toUpperCase()).
            append(name.substring(1,length)).toString();
            try {
            	originalMethod = original.getClass().getDeclaredMethod(originalMethodName);
                targetMethod = target.getClass().getDeclaredMethod(
                		targetMethodName,field.getType());
                targetMethod.invoke(target, originalMethod.invoke(original));
            } catch (Exception e) {
              // e.printStackTrace();
            }
        }
        return target;
    }
    /**
     * 部分更新对象,而不会因为页面少传了一个字段,而导致数据库的值为空.或者重复使用get,set方法更新数据库对象
     * @param obj 生成的对象,从页面存过来的
     * @param saveObj 保存的对象(存在数据库的)
     * @param updateFields 需要更新的字段集合(request.getParameterMap().keySet())
     * @return 返回saveObj,并设置了要更新的字段
     */
    public static Object partUpdate(Object obj,Object saveObj,Set<String> updateFields){
    	if(saveObj==null||obj==null) return saveObj;
    	if(obj.getClass()!=saveObj.getClass()) return saveObj;
    	Field[] fields = saveObj.getClass().getDeclaredFields();
    	Method getMethod = null;
        Method setMethod = null;
        String getMethodName = null;
        String setMethodName = null;
        
    	for (Field field : fields) {
            String name = field.getName();
            //如果要更新的字段不包含
            if(!updateFields.contains(name)) continue;
            int length = name.length();
            
            getMethodName = new StringBuffer("get").
            append(name.substring(0,1).toUpperCase()).
            append(name.substring(1,length)).toString();
            
            setMethodName = new StringBuffer("set").
            append(name.substring(0,1).toUpperCase()).
            append(name.substring(1,length)).toString();
            try {
            	getMethod = obj.getClass().getDeclaredMethod(getMethodName);
                setMethod = saveObj.getClass().getDeclaredMethod(setMethodName,field.getType());
                setMethod.invoke(saveObj, getMethod.invoke(obj));
            } catch (Exception e) {
              // e.printStackTrace();
            }
        }
    	return saveObj;
    }
    
}