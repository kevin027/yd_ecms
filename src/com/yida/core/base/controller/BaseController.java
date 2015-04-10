package com.yida.core.base.controller;

import java.beans.PropertyEditorSupport;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tools.sys.DateTypeEditor;
import com.tools.sys.SysConstant;
import com.yida.core.base.entity.Account;
import com.yida.core.base.entity.Function;
import com.yida.core.base.entity.Staff;
import com.yida.core.common.PageInfo;

/**
 * @描述 基础Controller
 * @author Kevin
 *
 */
public class BaseController extends BaseControllerProperties{
	
	public final static ReentrantReadWriteLock staffTableModifyLock = new ReentrantReadWriteLock();
	
	@InitBinder
	protected void initBinder(HttpServletRequest request,ServletRequestDataBinder binder) throws Exception {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(BigDecimal.class,new PropertyEditorSupport() {
					@Override
					public void setAsText(String text) throws IllegalArgumentException {
						if (text != null && !"".equals(text))
							setValue(new BigDecimal(Double.valueOf(text)));
					}
				});
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String value) {
				if(StringUtils.hasText(value)){
					try {
						if(value.length()>10){
							setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
						}else{
							setValue(new SimpleDateFormat("yyyy-MM-dd").parse(value));
							
						}
					} catch (ParseException e) {
						setValue(null);
					}
				}
			}

			@Override
			public String getAsText() {
				return new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format((Date) getValue());
			}
		});
	
	    binder.registerCustomEditor(Timestamp.class, new DateTypeEditor());
	    binder.registerCustomEditor(java.sql.Date.class, new DateTypeEditor());
	}
	
	/**
	 * 获取当前访问线程的request,不需要再在每个方法注入.<br/>
	 * 与@RequestMapping中方法的(HttpServletRequest request)获取的是同一对象
	 * @return 当前线程的request
	 */
	public static HttpServletRequest getCurRequest() {
		ServletRequestAttributes requestAttributes =(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=requestAttributes.getRequest();
		return request;
	}
	
	/**
	 * 获取当前访问线程的session对象.
	 * @return
	 */
	public static HttpSession getCurSession(){
		return getCurRequest().getSession();
	}
	
	/**
	 * 获取当前请求的IP,对使用代理后都有效
	 * @return
	 */
	public static String getRequestIP(){
		HttpServletRequest request=getCurRequest();
		String ip = request.getHeader("x-forwarded-for"); 
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		ip = request.getHeader("Proxy-Client-IP"); 
		} 
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		ip = request.getHeader("WL-Proxy-Client-IP"); 
		} 
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		ip = request.getRemoteAddr(); 
		} 
		return ip; 
	}
	
	public static Integer getCurPage(){
		String p=getCurRequest().getParameter("page");
		if(p!=null) return Integer.valueOf(p);
		return 0;
	}
	
	public static Integer getCurRows(){
		String rows=getCurRequest().getParameter("rows");
		if(rows!=null) return Integer.valueOf(rows);
		return 0;
	}
	
	public void setPage(Integer page) {
		this.page = page;
		if (null != page && !"".equals(page)) {
			if (null == pageInfo) {
				pageInfo = new PageInfo();
			}
			pageInfo.setCurrentPage(page);
		}
	}

	public void setRows(Integer rows) {
		this.rows = rows;
		if (null != rows && !"".equals(rows)) {
			if (null == pageInfo) {
				pageInfo = new PageInfo();
			}
			pageInfo.setMaxResult(rows);
		}
	}
	
	public Integer getPage() {
		return page;
	}
	
	public Integer getRows() {
		return rows;
	}
	
	/**
	 * jxls模板导出
	 * @param beans
	 * @param templatePath
	 * @param os
	 */
	protected void exportExcel(Map<String, Object> beans, String templatePath, OutputStream os) {
		XLSTransformer transformer = new XLSTransformer();
		InputStream is = null;
		try {
			File file = new File(templatePath);
			is = new BufferedInputStream(new FileInputStream(file));
			Workbook hssfWorkBook = transformer.transformXLS(is, beans);
			hssfWorkBook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void exportWord(Map<String, Object> beans, String templatePath,String exportName, OutputStream sos) {
		File file = new File(templatePath);
		FileInputStream in;
		try {
			in = new FileInputStream(file);
			HWPFDocument doc = new HWPFDocument(in);
			Range range = doc.getRange();
			//替换文本内容
			for (Map.Entry<String,Object> entry:beans.entrySet()) {
			 range.replaceText(entry.getKey(),String.valueOf(entry.getValue()));
			}
			doc.write(sos);
			sos.flush();
			sos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 统计报表的导出
	 * @param beans
	 * @param templateFile 模板文件的文件目录
	 * @param downName 下载时的文件名
	 * @param response
	 * @throws Exception
	 */
	protected void export_report(Map<String, Object> beans, String templateFile,String downName,HttpServletResponse response) throws Exception {
		XLSTransformer transformer = new XLSTransformer();
		InputStream fis = new FileInputStream(templateFile);
		HSSFWorkbook wb= (HSSFWorkbook) transformer.transformXLS(fis, beans);
		response.reset();
		response.setContentType("application/octet-stream; charset=utf-8");  
		response.setHeader("content-disposition","attachment;filename="+downName+".xls");
		OutputStream os = response.getOutputStream();
		wb.write(os);
	}
	
	protected String downFile(File file,String fileName,HttpServletResponse res) throws IOException{
		OutputStream os =null;
		if(file==null||!file.exists()) return "文件不存在!";
	    try {
	    	os= res.getOutputStream();  
	    	fileName=new String(fileName.getBytes(), "ISO8859-1");
	        res.reset();  
	        res.setHeader("Content-Disposition", "attachment; filename="+fileName);  
	        res.setContentType("application/octet-stream; charset=utf-8");
	        os.write(FileUtils.readFileToByteArray(file));  
	        os.flush();
	    }catch(Exception e){
	    	e.printStackTrace();
	    	return "下载文件不成功!";
	    }
	    finally {
	        if (os != null) {
	            os.close();
	        }
	    }  
	    return null;
	}
	/**
	 * 获取当前登录的账号
	 * @return
	 */
	public static Account getCurrentAccount() {
		return (Account) getCurSession().getAttribute(SysConstant.LOGIN_ACCOUNT);
	}
	
	public static Staff getCurrentStaff(){
		return null;
	}
	
	/**
	 * 返回
	 * @param result
	 * @return
	 */
	public String jsonResultView(JSONObject result){
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 获取当前登录操作所影响的机构的ID
	 * @return
	 */
	public String getCurrentAuditOrgId() {
		if (null != getCurSession()) {
			Account a = (Account) getCurSession().getAttribute(SysConstant.LOGIN_ACCOUNT);
			if (null != a) {
				return a.getCurrentAuditOrgId();
			}
		}
		return null;
	}
	
	/**
	 * 获取当前用户所有的权限菜单
	 * @return
	 */
	public static List<Function> getCurFunctions(){
		if(getCurrentAccount()==null) return new ArrayList<>();
		return getCurrentAccount().getFunctions();
	}
	
	/**
	 * 获取spring工厂 
	 * @return
	 */
	public static ApplicationContext getSpringFactory(){
		return WebApplicationContextUtils.getWebApplicationContext(getCurSession().getServletContext());
	}
	
	protected boolean isAjaxRequest() {
		return "xmlhttprequest".equalsIgnoreCase(getCurRequest().getHeader("X-Requested-With"));
	}
	
}
