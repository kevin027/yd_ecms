package com.yida.core.interceptors;

import com.tools.sys.SysConstant;
import com.yida.core.base.entity.Account;
import com.yida.core.base.entity.Function;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        HandlerMethod handlerMethod =(HandlerMethod)obj;
		String controller=handlerMethod.getBeanType().getSimpleName();
		String method=handlerMethod.getMethod().getName();
		String fowardStr="["+new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date())+"]访问【"+controller+"】=>【"+method+"】";

        System.out.println("----->访问地址："+servletPath);
        HttpSession session =  request.getSession();
        Account user = (Account) session.getAttribute(SysConstant.LOGIN_ACCOUNT);
        if(null != user){
            if(exclude(servletPath)){
                System.out.println("----->"+fowardStr);
                return true;
            }else{
                Class<?> controllerClass=handlerMethod.getBean().getClass();
                Permission p = handlerMethod.getMethodAnnotation(Permission.class);
                this.setpageMenuFuns(user,p,request);
                System.out.println("----->"+fowardStr);
                return true;
            }
        }else{
            if(exclude(servletPath)){
                System.out.println("----->"+fowardStr);
                return true;
            }else{
                System.out.println("----->"+fowardStr);
                return false;
            }
        }
	}

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse request, Object response, ModelAndView mv)
            throws Exception {
        if(null != mv) {
            System.out.println("----->访问页面：" + mv.getViewName() + ".jsp");
        }
    }

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e)
			throws Exception {
	}

	public boolean exclude(String servletPath) {
        if(excludeUrls != null) {
            for (String exc : excludeUrls) {
                if (servletPath.indexOf(exc)!=-1) {
                    return true;
                }
            }
        }
		return false;
	}

    /**
     * 获取权限菜单按钮
     * @param user
     * @param p
     * @param request
     */
    public void setpageMenuFuns(Account user,Permission p,HttpServletRequest request){
        List<Function> funs = user.getFunctions();
        List<Function> pageMenuFuns = new ArrayList<>();
        if (null != p && null != funs) {
            for (int i = 0; i < funs.size(); i++) {
                Function temp = funs.get(i);
                if (p.code().equals(temp.getCode())) {
                    for (int j = 0; j < funs.size(); j++) {
                        Function f = funs.get(j);
                        if (null != f.getParent() && temp.getId().equals(f.getParent().getId())) {
                            pageMenuFuns.add(f);
                            System.out.println(temp.getId() + ":" + temp.getName() + "----------" + f.getParent().getId() + ":" + f.getName());
                        }
                    }
                    break;
                }
            }
        }
        request.setAttribute("pageMenuFuns", pageMenuFuns);
    }

	public String[] getExcludeUrls() {
		return excludeUrls;
	}
	public void setExcludeUrls(String[] excludeUrls) {
		this.excludeUrls = excludeUrls;
	}
}
