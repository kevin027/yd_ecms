package com.aop;


import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yida.log.entity.Log;
import com.yida.log.service.LogService;

/**
 * 
 * @author kevin
 *
 */
@Component
@Aspect
public class AspectAudit {
	
	private @Resource LogService logService;
	
	private static Logger logger = LoggerFactory.getLogger(AspectAudit.class);

	/**
	 * 定义切入点方法
	 */
	@Pointcut(value="execution(* com.yida.basedata.*.service.*.*(..))")
	public void pointMethod() {
		System.out.println("定义切入点");
	}
	
	//日志的切入方法
	@Pointcut(value="@annotation(com.aop.LogAudit)")
	public void logMethod() {
		System.out.println("日志的切入方法");
	}

	/**
	 * 切入点方法之前执行
	 * 
	 * @param jp
	 */
	@Before("pointMethod()")
	public void doBefore(JoinPoint jp) {
		System.out.println("切入点方法之前执行"+logService);
		
	}

	/**
	 * 切入点方法之后执行
	 * 
	 * @param jp
	 */
	@After(value= "pointMethod()")
	public void doAfter(JoinPoint jp) throws Exception {
		System.out.println("切入点方法之后执行"+logService);
	}
	
	/**
	 * 后置通知,可执行日志记录等.
	 * 
	 * @param jp
	 * @param result
	 */
	@AfterReturning(value = "logMethod()", returning = "result")
	public void doArterReturning(JoinPoint jp,Object result){
		MethodSignature methodSignature = (MethodSignature) jp.getSignature();  
		Method method = methodSignature.getMethod();
		LogAudit lc=method.getAnnotation(LogAudit.class);
		if(lc!=null&&(lc.focusLog()||LogService.isLog())){
			//把目标类方法参数的值放到当前线程的值域当中
			//参数对象
			Object[] args=jp.getArgs();
			//参数名
			CodeSignature signature = (CodeSignature)jp.getSignature();
			String[] paramNames=signature.getParameterNames();
			if(paramNames.length!=args.length) return;
			for (int i = 0; i < args.length; i++) {
				if(!LogService.containCurValues(paramNames[i])) LogService.setCurValues(paramNames[i], args[i]);
			}
			
			// 获取目标对象的类名
			//String targetName = jp.getTarget().getClass().getName();
			try{
				Log log=new Log();
				log.setModule(lc.module());
				log.setOpType(lc.opType());
				String description=logService.fillDescription(lc.description());
				log.setDescription(description);
				logService.doLog(log);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 环绕通知,切入前后,可进行方法执行计时等
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around(value="pointMethod()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		long st = System.currentTimeMillis();
		// 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行
		Object result = pjp.proceed();// result的值就是被拦截方法的返回值
		long et = System.currentTimeMillis();
		// 调用的方法名
		String methodName = pjp.getSignature().getName();
		// 获取目标对象的类名
		String targetName = pjp.getTarget().getClass().getName();
		long total=(et-st);
		String cost=total>1000?(total/1000+"秒"):(total+"毫秒");
		if(total>500)
		{
			System.out.println("-AOP-执行【"+targetName+"】."+methodName+"用时"+cost+"！");
		}
		return result;
	}

	// 异常通知
	@AfterThrowing(value = "pointMethod()", throwing = "e")
	public void doThrow(JoinPoint jp, Throwable e) {
		// 调用的方法名
		String methodName = jp.getSignature().getName();
		// 获取目标对象的类名
		String targetName = jp.getTarget().getClass().getName();
		logger.error("-AOP-系统执行出错!目标" + targetName + "."+ methodName);
		Object[] o = jp.getArgs();// 通过这个方法获取切入点方法的参数值.返回一个对象数组
		for (int i = 0; i < o.length; i++) {
			logger.error("参数"+i+"->"+o[i]);
		}
		// logger.error(e.getMessage(),e);
	}

}
