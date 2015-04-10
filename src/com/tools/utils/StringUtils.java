package com.tools.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.PropertyFilter;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yida.core.common.IExportCallBack;

public class StringUtils {

    private static Logger logger = LoggerFactory.getLogger(StringUtils.class);

    public final static String ELLIPSIS = "...";

    public final static String EMPTY_STRING = "";

    private StringUtils() {
        throw new AssertionError(getClass() + "不需要实例化!");
    }

    /**
     * 生成三十二位随机字符串
     * @return
     */
    public static String uuid() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 判断字符串是否有实际意义
     * @param str
     * @return
     */
    public static boolean isMeaningFul(String str) {
        return (null != str) && (0 < str.trim().length());
    }
    /**
     * 判断两字符串是否有实际意义且相等
     * @param str1
     * @param str2
     * @return boolean
     */
    public static boolean isEqual(String str1,String str2) {
        if(!isMeaningFul(str1)) return false;
        if(!isMeaningFul(str2)) return false;
        return str1.equals(str2);
    }
    /**
     * 返回 一段字符串的缩略表达形式，如果字符串为空时不处理
     * @param str
     * @param lengthLimit
     * @param suffix
     * @return
     */
    public static String forShort(String str, int lengthLimit, String suffix) {
        if (null != str && str.length() > lengthLimit) {
            str = str.substring(0, lengthLimit);
            if (isMeaningFul(suffix)) {
                str += suffix;
            }
        }
        return str;
    }

    /**
     * 返回 一段字符串的缩略表达形式。
     * @param str
     * @param lengthLimit
     * @return
     */
    public static String forShort(String str, int lengthLimit) {
        return forShort(str, lengthLimit, StringUtils.ELLIPSIS);
    }

    /**
     *
     * @param <P>
     * @param <S>
     * @param defaultValue
     * @param candidateValue
     * @return
     */
    public static <P, S extends P> P notNull(S defaultValue, S candidateValue) {
        if (null == defaultValue) {
            if (null == candidateValue) {
                throw new IllegalArgumentException("the defaultValue is null also");
            }
            return candidateValue;
        }
        return defaultValue;
    }

    /**
     *
     * @param defaultString
     * @param candidateStrings
     * @return
     */
    public static String notNull (String defaultString, String...candidateStrings) {
        String candidateString = emptyOrUnique(candidateStrings);
        candidateString = (null == candidateString) ? EMPTY_STRING : candidateString;
        return StringUtils.notNull(defaultString, candidateString);
    }

    /**
     *
     * @param str
     * @return
     */
    public static String notNullAndTrim(String str) {
        return StringUtils.notNull(str, "").toString().trim();
    }

    /**
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        return (null == str) ? str : str.trim();
    }


    /**
     * 下划线命名法转驼峰记名法
     * @param underScoreCase
     * @return
     */
    public static String underScoreCaseToCamelCase(String underScoreCase) {
        if (null == underScoreCase) {
            throw new IllegalArgumentException("要转换的字符串不能为null");
        }
        if (-1 == underScoreCase.indexOf("_")) {
            return underScoreCase;
        }
        underScoreCase = underScoreCase.toLowerCase();
        Pattern p = Pattern.compile("_([a-z])");
        Matcher m = p.matcher(underScoreCase);
        StringBuffer sb = new StringBuffer();
        while(m.find()) {
            m.appendReplacement(sb, m.group(1).toUpperCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰记名法转下划线命名法
     * @param camelCase
     * @return
     */
    public static String camelCaseToUnderScoreCase(String camelCase) {
        if (null == camelCase) {
            throw new IllegalArgumentException("要转换的字符串不能为null");
        }
        Pattern p = Pattern.compile("([A-Z])");
        Matcher m = p.matcher(camelCase);
        StringBuffer sb = new StringBuffer();
        while(m.find()) {
            m.appendReplacement(sb, "_" + m.group(1).toLowerCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 将字符串首字符转大写返回
     * @param fieldName
     * @return
     * @throws Exception
     */
    public static String firstCharToUpperCase(String fieldName) throws Exception {
        String a = fieldName.substring(1);
        char b = fieldName.charAt(0);
        if (b > 96 && b < 123) {
            return (char)(b-32) + a;
        }
        return fieldName;
    }

    public static String join(Iterable<String> items, String token) {
        StringBuilder sb = new StringBuilder();
        if (!items.iterator().hasNext()) return EMPTY_STRING;
        for (String item : items) {
            sb.append(item).append(token);
        }
        sb.setLength(sb.length()-token.length());
        return sb.toString();
    }

    public static String toUtf8Str(String utf8Code) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;
        while((i=utf8Code.indexOf("\\u", pos)) != -1){
            sb.append(utf8Code.substring(pos, i));
            if(i+5 < utf8Code.length()){
                pos = i+6;
                sb.append((char)Integer.parseInt(utf8Code.substring(i+2, i+6), 16));
            }
        }
        return sb.toString();
    }

    /**
     * 检查对象字段是否为空，
     * 该对象字段必须添加了注解@CheckNull
     * @param obj
     * @return
     */
    public static JSONObject checkNull(Object obj,Integer batch){
        JSONObject result = new JSONObject();
        if(obj==null){
            result.put("success", false);
            result.put("msg", "对象为空");
            return result;
        }
        String nullFields = "",fieldName = "";
        Field[] fields = obj.getClass().getDeclaredFields();
        for(Field f:fields){
            CheckNull cn = f.getAnnotation(CheckNull.class);
            if(cn==null || !cn.isCheck() || (batch != null && cn.batch()!=batch)) continue;
            try {

                if ("class java.lang.String".equals(f.getGenericType())) {
                    Method m = obj.getClass().getMethod("get" + getMethodName(f.getName()));
                    String val = (String) m.invoke(obj);// 调用getter方法获取属性值
                    if(!StringUtils.isMeaningFul(val)){
                        fieldName += m.getName();
                        nullFields += (StringUtils.isMeaningFul(nullFields)?(","+cn.name()):cn.name());
                    }
                }else{
                    Method m = obj.getClass().getMethod("get" + getMethodName(f.getName()));
                    fieldName = m.getName();
                    Object val = m.invoke(obj);// 调用getter方法获取属性值
                    if(val==null || !StringUtils.isMeaningFul(String.valueOf(val))){
                        fieldName += m.getName();
                        nullFields += (StringUtils.isMeaningFul(nullFields)?(","+cn.name()):cn.name());
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }
        logger.info(fieldName+"==="+nullFields);
        if(StringUtils.isMeaningFul(nullFields)){
            result.put("success", false);
            result.put("msg", nullFields+"不能为空，请检查！");
        }else{
            result.put("success", true);
        }
        return result;
    }

    // 把一个字符串的第一个字母大写、效率是最高的、
    public static String getMethodName(String fildeName) throws Exception{
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    /**
     * 该方法用于判断含有可变长参数的方法其可变长参数是否最多仅有一个参数
     * @param <P> 可变参数类型的父类类型
     * @param <S> 可变参数类型
     * @param variableParameters 可变参数的对象
     * @return {@code variableParaemters[0]}或者 null
     * @throws
     *  <p><{@link IllegalArgumentException} 当参数VariableParaemters包含多个参数的时候</p>
     */
    public static <P, S extends P> P emptyOrUnique(S[] variableParameters) {
        if (null == variableParameters || 0 == variableParameters.length) return null;
        if (1 < variableParameters.length) {
            IllegalArgumentException illegallArgumentException
                    = new IllegalArgumentException("最多只支持一个参数");
            throw illegallArgumentException;
        }
        return variableParameters[0];
    }

    /**
     * 判断两个参数是否绝对相等，包含NULL值的比较
     * @param <T>
     * @param obj1
     * @param obj2
     * @return
     */
    public static <T> boolean absEquals(T obj1, T obj2) {
        if (null == obj1) {
            return (null == obj2);
        }
        return obj1.equals(obj2);
    }

    // JSON日期对象处理
    public final static SimpleDateFormat DATE_FORMAT_YMD = new SimpleDateFormat("yyyy-MM-dd");
    public final static JsonValueProcessor JSON_VALUE_DATA_PROCESSOR = new JsonValueProcessor() {

        @Override
        public Object processArrayValue(Object o, JsonConfig jc) {
            return process(o);
        }

        @Override
        public Object processObjectValue(String k, Object o, JsonConfig jc) {
            return process(o);
        }

        public Object process(Object value) {
            if (null == value || !(value instanceof Date)) return EMPTY_STRING;
            return DATE_FORMAT_YMD.format((Date) value);
        }
    };

    public static String toJsonArray(Collection<?> collection, JsonConfig...jcs) {
        JsonConfig jc = (null == jcs || 0 == jcs.length) ? new JsonConfig() : jcs[0];
        jc.registerJsonValueProcessor(Date.class, JSON_VALUE_DATA_PROCESSOR);
        return JSONSerializer.toJSON(collection, jc).toString();
    }

    /**
     * 将Collection转化成JSON字符串格式，Collection中的对象转化成JSON时，只包含includePropertyNames指定的字段名。
     * @param collection
     * @param includePropertyNames
     * @param jcs
     * @return
     */
    public static String toJsonArrayIncludeProperty(Collection<?> collection, final Collection<String> includePropertyNames, JsonConfig...jcs) {
        JsonConfig jc = (null == jcs || 0 == jcs.length) ? new JsonConfig() : jcs[0];
        jc.registerJsonValueProcessor(Date.class, JSON_VALUE_DATA_PROCESSOR);
        jc.setJsonPropertyFilter(new PropertyFilter() {
            @Override
            public boolean apply(Object entity, String name, Object value) {
                return !includePropertyNames.contains(name);
            }
        });
        return JSONSerializer.toJSON(collection, jc).toString();
    }

    /**
     * 将Collection转化成JSON字符串格式，Collection中的对象转化成JSON时，不包含excludePropertyNames指定的字段名。
     * @param collection
     * @param excludePropertyNames
     * @param jcs
     * @return
     */
    public static String toJsonArrayExcludeProperty(Collection<?> collection, final Collection<String> exculdePropertyNames, JsonConfig...jcs) {
        JsonConfig jc = (null == jcs || 0 == jcs.length) ? new JsonConfig() : jcs[0];
        jc.registerJsonValueProcessor(Date.class, JSON_VALUE_DATA_PROCESSOR);
        jc.setJsonPropertyFilter(new PropertyFilter() {
            @Override
            public boolean apply(Object entity, String name, Object value) {
                return exculdePropertyNames.contains(name);
            }
        });
        return JSONSerializer.toJSON(collection, jc).toString();
    }

    /**
     * 从request中取出参数填充字符串${参数名}的表达方式
     * @param request
     * @param template
     * @return
     */
    public static String requestParamsToTemplate(HttpServletRequest request, String template) {
        Pattern p = Pattern.compile("\\$\\{.+?\\}");
        Matcher m = p.matcher(template);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String paramName = template.substring(m.start() + 2, m.end()-1);
            String paramValue = StringUtils.notNull(request.getParameter(paramName));
            if (!StringUtils.isMeaningFul(paramValue) && request.getAttribute(paramName) instanceof String) {
                paramValue = StringUtils.notNull((String)request.getAttribute(paramName));
            }
            m.appendReplacement(sb, paramValue);
        }
        m.appendTail(sb);
        return sb.toString();
    }



    public static void exportExcel(Map<String, Object> excelBeans,
                                   Path templatePath, ServletOutputStream os, IExportCallBack callback) {
        XLSTransformer transformer = new XLSTransformer();
        try (
                FileInputStream fis = new FileInputStream(templatePath.toFile());
                InputStream is = new BufferedInputStream(fis);) {
            Workbook hssfWorkbook = transformer.transformXLS(is, excelBeans);
            if (null != callback) callback.handle(hssfWorkbook);
            hssfWorkbook.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 自动组装sql查询语句
     * @param o			查询对象
     * @param pref		查询别名
     * @param fuzzy		模糊查询的属性，属性名必须完全一样
     * @param clear		清晰查询，属性名也必须完全一样
     * @param number	数字查询>=
     * @param date		类似日期那样的区间查询，o对象里面有两个属性，分别加上字母'S'和'E'代表开始和结束
     * @return	List<String>	返回所有有值的and语句
     */
    public static List<String> autoSql(Object o,String pref,List<String> fuzzy,List<String> clear,List<String> number,List<String> date){
        List<String> list = new ArrayList<String>();
        if(fuzzy!=null && fuzzy.size()>0){
            for(String fu:fuzzy){
                try {
                    Method m = o.getClass().getMethod("get"+getMethodName(fu));
                    String val = (String) m.invoke(o);
                    if(StringUtils.isMeaningFul(val))
                        list.add(" and "+pref+"."+fu+" like '%"+val+"%' ");
                } catch (Exception e) {
                    continue;
                }
            }
        }
        if(clear!=null && clear.size()>0){
            for(String fu:clear){
                try {
                    Method m = o.getClass().getMethod("get"+getMethodName(fu));
                    String val = (String) m.invoke(o);
                    if(StringUtils.isMeaningFul(val))
                        list.add(" and "+pref+"."+fu+" like '"+val+"' ");
                } catch (Exception e) {
                    continue;
                }
            }
        }
        if(number!=null && number.size()>0){
            for(String fu:number){
                try {
                    Method m = o.getClass().getMethod("get"+getMethodName(fu));
                    Double val = (Double) m.invoke(o);
                    if(val!=null)
                        list.add(" and "+pref+"."+fu+" >="+val);
                } catch (Exception e) {
                    continue;
                }
            }
        }
        if(date!=null && date.size()>0){
            for(String fu:date){
                try {
                    Method ms = o.getClass().getMethod("get"+getMethodName(fu)+"S");
                    Method me = o.getClass().getMethod("get"+getMethodName(fu)+"E");
                    String vals = (String) ms.invoke(o);
                    String vale = (String) me.invoke(o);
                    if(StringUtils.isMeaningFul(vals))
                        list.add(" and "+pref+"."+fu+" >= '"+vals+"' ");
                    if(StringUtils.isMeaningFul(vale))
                        list.add(" and "+pref+"."+fu+" <= '"+vale+"' ");
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return list;
    }

    public static String getMethodName2(String fieldName) {
        String a = fieldName.substring(1);
        char b = fieldName.charAt(0);
        if (b > 96 && b < 123) {
            return (char)(b-32) + a;
        }
        return fieldName;
    }
}
