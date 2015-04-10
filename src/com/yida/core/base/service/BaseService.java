package com.yida.core.base.service;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tools.sys.DebugTool;
import com.tools.sys.SysConstant;
import com.tools.utils.CopyFields;
import com.tools.utils.StringUtils;
import com.yida.core.base.controller.BaseController;
import com.yida.core.base.entity.Account;
import com.yida.core.common.JSONConfigFactory;
import com.yida.log.service.LogService;
/**
 * 系统基础Service
 * @author kevin
 */
@Transactional
@Service
public class BaseService extends BaseServiceProperties {
	private static Logger logger = LoggerFactory.getLogger(BaseService.class);
	/**
	 * 获取当前进程的request对象
	 * @return
	 */
	public HttpServletRequest getCurRequest() {
		return BaseController.getCurRequest();
	}
	
	public static String List2Json(List<?> result,Integer total){
		JSONArray array=JSONArray.fromObject(result, JSONConfigFactory.getYMDConfig());
		JSONObject json=new JSONObject();
		json.put("total", total);
		json.put("rows", array);
		return json.toString();
	}
	
	public static String List2Json4FullDate(List<?> result,Integer total){
		JSONArray array=JSONArray.fromObject(result, JSONConfigFactory.getYMDHMSConfig());
		JSONObject json=new JSONObject();
		json.put("total", total);
		json.put("rows", array);
		return json.toString();
	}
	
	/**
	 * 返回带有easyui分页属性的json结果
	 * @param result 数据列表list
	 * @param total list的总条目数量
	 * @return 带有easyui分页属性的json结果
	 */
	public static JSONObject List2JsonObject(List<?> result,Integer total){
		JSONArray array=JSONArray.fromObject(result, JSONConfigFactory.getYMDConfig());
		JSONObject json=new JSONObject();
		json.put("total", total);
		json.put("rows", array);
		return json;
	}
	
	public static String List2Json(List<?> result,Integer total,List<?> footer){
		JSONArray array=JSONArray.fromObject(result, JSONConfigFactory.getYMDConfig());
		JSONObject json=new JSONObject();
		json.put("total", total);
		json.put("rows", array);
		json.put("footer", footer);
		return json.toString();
	}
	
	public static String List2JsonLongDate(List<?> result,Integer total){
		JSONArray array=JSONArray.fromObject(result, JSONConfigFactory.getSimpleConfig());
		JSONObject json=new JSONObject();
		json.put("total", total);
		json.put("rows", array);
		return json.toString();
	}
	public static JSONArray list2JsonArray(List<?> list){
		if(list==null) return new JSONArray();
		JSONArray array=JSONArray.fromObject(list,  JSONConfigFactory.getYMDConfig());
		return array;
	}
	
	public <E> E getObject(Class<E> entityClass,Serializable id)
	{
		if(id==null) return null;
		return sqlCommonDao.getEntity(entityClass, id);
	}
	
	public boolean deleteObject(Object obj){
		try {
			sqlCommonDao.removeEntity(obj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean saveOrUpdate(Object obj){
		try {
			sqlCommonDao.saveOrUpdate(obj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			DebugTool.printValues(obj);
			return false;
		}
	}
	/****
	 * 通用保存方法，只针对单个实体，且ID类型为32位手动生成的字符串
	 * 保存前检查必填项，默认检查第1批
	 * @param model
	 * @param o
	 * @return JSONObject:{"saveId":String,"msg":String,"success":true/false}
	 */
	public <E> JSONObject jsonSaveOrUpdate(Class<E> model,Object o){
		JSONObject js = new JSONObject();
		js = StringUtils.checkNull(o, 1);
		if(!js.getBoolean("success"))
			throw new IllegalStateException(js.getString("msg"));
		try {
			Method getId = o.getClass().getDeclaredMethod("getId");
			String id = (String) getId.invoke(o);
			E saveObj = getObject(model, id);
			if(saveObj!=null){
				Set<String> updateFields=getCurRequest().getParameterMap().keySet();
				CopyFields.partUpdate(o, saveObj, updateFields);
				saveOrUpdate(saveObj);
			}else{
				Method  setId = o.getClass().getDeclaredMethod("setId",String.class);
				setId.invoke(o, StringUtils.uuid());
				sqlCommonDao.persist(o);
			}
			js.put("msg", "保存成功");
			js.put("success", true);
			js.put("saveId", getId.invoke(o));
		} catch (Exception e) {
			throw new IllegalStateException("保存失败！");
		}
		
		return js;
	}
	
	public <E> String jsonGet(Class<E> entityClass,Serializable id){
		E obj=getObject(entityClass, id);
		return JSONObject.fromObject(obj).toString();
	}
	
	public String jsonDelete(Object obj){
		JSONObject json=new JSONObject();
		Boolean result=deleteObject(obj);
		json.put("success", result);
		if(result) json.put("msg", "删除数据成功!");
		else if(!result) json.put("msg", "删除数据失败!");
		return json.toString();
	}
	
	public  String jsonUpdate(Object obj){
		JSONObject json=new JSONObject();
		boolean result=saveOrUpdate(obj);
		if(result){
			json.put("success", true);
			json.put("msg", "保存数据成功!");
		}
		else
		{
			json.put("success", false);
			json.put("msg", "保存数据失败!");
		}
		return json.toString();
	}
	
	public String msg2json(Boolean success,String msg){
		JSONObject json=new JSONObject();
		json.put("success", success);
		json.put("msg", msg);
		return json.toString();
	}
	
	public Integer getCurRows(){
		return BaseController.getCurRows();
	}
	
	public Integer getCurPage(){
		return BaseController.getCurPage();
	}
	
	/**
	 * 获取当前登录的账号
	 * @return
	 */
	public static Account getCurrentAccount() {
		return (Account) BaseController.getCurSession().getAttribute(SysConstant.LOGIN_ACCOUNT);
	}
	
	public static String getSortHql(Map<String, String []> params)
	{
		String hqlOrderBy="";
		if(params.containsKey("sort"))
		{
			String sortField=params.get("sort")[0];
			String order="";
			if(params.containsKey("order")){
				order=params.get("order")[0];
			}
			hqlOrderBy=" order by "+sortField+" "+order;
		}
		return hqlOrderBy;
	}

	//是否系统关键字,非查询字段
	private static boolean isKeyword(String s){
		if("page".equals(s)) return true;
		if("rows".equals(s)) return true;
		if("sort".equals(s)) return true;
		if("order".equals(s)) return true;
		return false;
	}
	
	/**
	 * 根据系统设置的标准,获取查询的HQL,详细标准请参考文档
	 * @param entityClass 要查询的实体类
	 * @param params 页面提交过来的参数
	 * @param fixedParam
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getQueryHql(Class entityClass,Map<String, String []> params,String fixedParam){
		//long st=System.currentTimeMillis();
		String hql=" from "+entityClass.getSimpleName()+" as vo where 1=1 ";
		for(String key:params.keySet())
		{
			String value=params.get(key)[0];
			//替换所有单引号,以防出错
			if(value!=null) value=value.replace("'", "''");
			if(!StringUtils.isMeaningFul(value)) continue;
			if(isKeyword(key)) continue;
			//只有符合abc$like$1这种规则的参数才进行添加
			//第一位字段名,第二位like或者=<>,第二位可省略,默认为=
			//原正则表达式
			if(StringUtils.isMeaningFul(key)&&key.matches("\\w+(\\$(like|likeL|likeR|eq|gt|lt|gteq|lteq|ne))?"))
			{
				String[] keys=key.split("\\$");
				//字段名
				String field=keys[0];
				//查询条件,like还是=
				String condition="eq";
				if(keys.length>1){
					condition=keys[1];
				}
				Class<?> fieldType=null;
				try {
					fieldType=entityClass.getDeclaredField(field).getType();
					
				} catch (Exception e) {
					
					if(fieldType==null){
						try {
							fieldType = entityClass.getSuperclass().getDeclaredField(field).getType();
						} catch (Exception e1) {
							continue;
						} 
					}
				}
				if("like".equals(condition)){
					value="%"+value+"%";
				}
				else if("likeL".equals(condition)){
					value=value+"%";
				}
				else if("likeR".equals(condition)){
					value="%"+value;
				}
				
				if("Date".equals(fieldType.getSimpleName())||"String".equals(fieldType.getSimpleName())){
					value="'"+value+"'";
				}
				//转换condition
				
				if("eq".equals(condition))
				{
					condition="=";
				}
				else if("like".equals(condition)||"likeL".equals(condition)||"likeR".equals(condition))
				{
					condition="like";
				}
				else if("gt".equals(condition))
				{
					condition=">";
				}
				else if("lt".equals(condition))
				{
					condition="<";
				}
				else if("gteq".equals(condition))
				{
					condition=">=";
				}
				else if("lteq".equals(condition))
				{
					condition="<=";
				}else if("ne".equals(condition)){
					condition = "<>";
				}
				
				hql+=" and "+field+" "+condition+" "+value;
			}
		}
		//long et=System.currentTimeMillis();
		System.out.println("HQL="+hql);
		return hql+" "+(fixedParam==null?"":fixedParam);
	}
	
	/**
	 * 参数值
	 * @param recordLog
	 * @param values 操作内容中的变量值
	 * ----
	 * 传递格式：arg0,arg1,arg2.....依此顺序
	 */
	public void setVa(boolean recordLog,Object ... values){
		LogService.setRecordLog(true);
		try{
			if(null != values){
				for(int i=0;i<values.length;i++){
					LogService.setCurValues("arg"+i, values[i]);
					logger.info("参数---->"+i+":"+values[i]);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 其他大众数据
	 * 获取数据SQL，通过权限过滤
	 * @param account --  用户
	 * @param arg0 --  查询语句中的别名 ：Table as arg0
	 * @return
	 */
	/*public String getOtherFuntionSQL(String arg0){
		String auditOrgId = BaseController.getCurAuditOrgId();
		StringBuffer sb = new StringBuffer();
		if(auditOrgId!=null){
			sb.append(" and "+arg0+".audit_org_id = '"+auditOrgId+"' ");
		}
		return sb.toString();
	}*/
}
