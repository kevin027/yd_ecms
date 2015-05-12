package com.excepttion;

import com.tools.sys.ExceptionTool;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.DataTruncation;
/**
 * spring MVC
 * @author kevin
 * @描述 统一错误处理类
 */
public class ExceptionHandler implements HandlerExceptionResolver {
	public static Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		logger.error(ex.getMessage(), ex);
		request.setAttribute("errormsg", ex.getMessage());
		//有该注解的返回json,java.sql.DataTruncation
		String msg="你访问的页面出错!";
		Throwable t=ExceptionTool.getSpecialException(ex, DataTruncation.class);
		if(t!=null&&t instanceof DataTruncation) {
			msg="你输入的字符过多!";
		}
		ResponseBody rb=null;
		if(handler instanceof HandlerMethod){
			HandlerMethod hm=(HandlerMethod)handler;
			rb=hm.getMethodAnnotation(ResponseBody.class);
		}
		if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))||rb!=null){
            try {
				JSONObject json=new JSONObject();
				json.put("success", false);
				json.put("msg", msg);
				json.put("rows", "[]");
				json.put("total", 0);
				json.put("error", true);
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/json");
				response.getWriter().write(json.toString());
			} catch (Exception e) {
                System.out.println("出现全局异常！");
            }
			return new ModelAndView();
		}
		ModelAndView mv=new ModelAndView("error");
		mv.addObject(ex);
		return mv;
	}

}
