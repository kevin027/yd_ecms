package com.yida.log.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tools.utils.StringUtils;
import com.yida.core.base.controller.BaseController;
import com.yida.core.base.service.BaseService;
import com.yida.core.common.PageInfo;
import com.yida.core.exception.EntityNotFoundException;
import com.yida.log.entity.Log;
import com.yida.log.vo.ListLogForm;

@Service
@Scope("singleton")
@Lazy(false)
public class LogService extends BaseService{
	private static Logger logger = LoggerFactory.getLogger(LogService.class);
	private static final Pattern pattern = Pattern.compile("(?<=\\$\\{)[^}]+(?=\\})");
	/**
	 * 当前线程范围内的值
	 */
	private static ThreadLocal<Map<String, Object>> values=new ThreadLocal<Map<String, Object>>();
	
	//查找日志信息
	public List<Log> listLog(ListLogForm query, PageInfo pageInfo) {
		StringBuilder fromBuilder = new StringBuilder(" from ").append(logDao.getTableName() + " o where 1=1");
		
		List<Object> paramList = new ArrayList<Object>();
		if (null != query) {
			String operator = StringUtils.notNull(query.getOperator()).trim();
			if (0 < operator.length()) {
				fromBuilder.append(" and o.operator like ?");
				paramList.add("%" + operator + "%");
			}
		}
		
		String fromSql = fromBuilder.toString();
		Object[] params = paramList.toArray();
		if (null != pageInfo) {
			int total = logDao.findCountBySql("select count(o.id) " + fromSql, params);
			pageInfo.setTotalResult(total);
		}
		
		List<Log> list = logDao.findListBySql("select o.* " + fromSql + " order by o.op_date desc", params, pageInfo);
		return list;
	}

	/**
	 * 记录操作日志
	 * @param operator 操作人
	 * @param opDate 操作时间
	 * @param opType 操作类型
	 * @param module 操作模块
	 * @param description 操作详细信息
	 * @param ip 操作IP
	 * @return
	 */
	@Transactional
	public Log log(String opType,String module,String description,String user,String ip) {
		Log log=new Log();
		log.setId(null);
		log.setIp(ip);
		log.setOperator(user);
		log.setOpDate(new Date());
		log.setOpType(opType);
		log.setModule(module);
		log.setDescription(description);
		this.logDao.persist(log);
		return log;
	}
	
	
	@Transactional
	public Log saveLog(Log log) {
		this.logDao.persist(log);
		return log;
	}
	
	
	@Transactional
	public void updateLog(Log log) {
		this.logDao.merge(log);
	}

	
	@Transactional
	public void delLogById(String id) {
		Log log = this.logDao.get(id);
		if (null == log) {
			throw new EntityNotFoundException(Log.class, id);
		}
		
		try {
			this.logDao.remove(log);
		} catch (Exception e) {
			throw new IllegalStateException("存在关联数据。");
		}
		
	}

	
	public Log getLogById(String logId) {
		return this.logDao.get(logId);
	}

	@Transactional
	public void doLog(Log log){
		if(log==null) return;
		log.setId(null);
		//获取客户端信息,方便跟踪
		//String userAgentStr=BaseController.getCurRequest().getHeader("User-Agent");
		log.setOpDate(new Date());
		log.setOperator(BaseController.getCurrentStaff().getName());
		log.setIp(BaseController.getRequestIP());
		logDao.persist(log);
		LogService.clearCurValues();
	}
	
	public String fillDescription(String template) {
		if(template==null) return "";
		String temp = template;
		if (null != template && !"".equals(template)) {
			Matcher m = pattern.matcher(template);
			while (m.find()) {
				int start = m.start();
				int end = m.end();
				String exp = temp.substring(start, end);
				Object val = getCurValue(exp);
				template = template.replace("${" + exp + "}",val==null?"[]":"["+val.toString()+"]");
			}
		}
		return template;
	}
	
	//输入表达式获取当前的值,支持1级表达式,如-> user.name
	private Object getCurValue(String exp){
		Object val = "";
		try {
		if(exp.contains(".")){
			String objName=exp.split("\\.")[0];
			String fieldName=exp.split("\\.")[1];
			Object obj=getCurValues(objName);
			if(obj==null) return val;
			int length=fieldName.length();
			String getMethodName="get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,length);
			Method method = obj.getClass().getDeclaredMethod(getMethodName);
			val=method.invoke(obj);
		}else {
			val=getCurValues(exp);
		}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("日志设置变量错误,请检查el表达式:"+exp,e);
			logger.error("日志设置变量错误!"+e.getMessage());
		}
		return val;
	}
	
	public static void setRecordLog(Boolean record){
		setCurValues("isLog", record);
	}
	
	/**
	 * 设置当前线程范围内的值
	 * @param key Map的key
	 * @param value
	 */
	public static void setCurValues(String key,Object value){
		getValueMap().put(key, value);
	}
	
	public static boolean containCurValues(String key){
		return getValueMap().containsKey(key);
	}
	
	/**
	 * 获取当前线程范围内的值
	 * @param key Map的key
	 * @return
	 */
	public static Object getCurValues(String key){
		if(!getValueMap().containsKey(key)) return null;
		return getValueMap().get(key);
	}
	
	private static Map<String, Object> getValueMap(){
		if(values.get()==null) values.set(new HashMap<String, Object>());
		return values.get();
	}
	
	/**
	 * 清除当前线程范围内的值,日志完成后必须清除,
	 * 否则将存在服务器的线程池中,影响并发出错可能
	 * 
	 */
	public static void clearCurValues(){
		getValueMap().clear();
	}
	
	public static boolean isLog(){
		//是否需要记录日志
		return getValueMap().containsKey("isLog");
	}
}
