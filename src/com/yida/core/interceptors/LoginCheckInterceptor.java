package com.yida.core.interceptors;

import com.tools.sys.SysConstant;
import com.yida.core.base.entity.Account;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LoginCheckInterceptor implements HandlerInterceptor {
	
	private String[] excludeUrls;
	/**
	 * preHandle：拦截器的前端，执行控制器之前所要处理的方法，通常用于权限控制、日志，其中，Object o表示下一个拦截器；
	 * postHandle：控制器的方法已经执行完毕，转换成视图之前的处理；
	 * afterCompletion：视图已处理完后执行的方法，通常用于释放资源；
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		if(obj instanceof ResourceHttpRequestHandler||obj instanceof DefaultServletHttpRequestHandler){
			return true;
		}
		String servletPath = request.getServletPath();
		servletPath = servletPath == null ? "" : servletPath;
		int index = servletPath.lastIndexOf("/");
		if(!"/".equals(servletPath) && index==(servletPath.length()-1)){
			servletPath=servletPath.substring(0, servletPath.length()-1);
		}
		System.out.println("----->访问地址："+servletPath);
		HttpSession session =  request.getSession();
		Account user = (Account) session.getAttribute(SysConstant.LOGIN_ACCOUNT);
		Boolean eb=exclude(servletPath,user);
		boolean isFlag = false;
		HandlerMethod handlerMethod =(HandlerMethod)obj;
		String controller=handlerMethod.getBeanType().getSimpleName();
		String method=handlerMethod.getMethod().getName();
		String fowardStr="["+new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date())+"]访问【"+controller+"】=>【"+method+"】";
		if(eb){
			isFlag = true;
            System.out.println("----->正常访问："+fowardStr);
        }else{
            System.out.println("----->异常访问："+fowardStr);
			Map<String, List<String>> operas  =  (Map<String, List<String>>) session.getAttribute(SysConstant.KEY_SESSION_PERMISSION);
			outer:
			for (Map.Entry<String, List<String>> map : operas.entrySet()){
				for (String opera : map.getValue()){
					if (servletPath.indexOf(opera) != -1){ 
						//有权限 (应该把这个模块对应的操作放到Session: 主要对页面的按钮进行控制)
						isFlag = true;
						break outer;
					}
				}
			}
			return true;
		}
		if (isFlag){
			return  true;
		}else{
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse request, Object response, ModelAndView mv)
			throws Exception {
		System.out.println("----->访问页面："+mv.getViewName()+".jsp");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e)
			throws Exception {
	}

	private boolean exclude(String servletPath,Account user) {
		if(user!=null){
			return true;
		}else{
			if(excludeUrls != null) {
				for (String exc : excludeUrls) {
					if (servletPath.indexOf(exc)!=-1) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public String[] getExcludeUrls() {
		return excludeUrls;
	}
	public void setExcludeUrls(String[] excludeUrls) {
		this.excludeUrls = excludeUrls;
	}
}
