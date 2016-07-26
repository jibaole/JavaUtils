package com.comm.util.aop.rw.split;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

//@Aspect
//@Component("aspectOfLogger")
public class DataSourceAdvice implements MethodBeforeAdvice,
		AfterReturningAdvice, ThrowsAdvice {
	// service方法执行之前被调用
	@Override
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		System.out.println("switchto: " + target.getClass().getName() + "class"
				+ method.getName() + "method");
		try{
		if (method.getName().indexOf("add")>-1
				|| method.getName().indexOf("create")>-1
				|| method.getName().indexOf("save")>-1
				|| method.getName().indexOf("edit")>-1
				|| method.getName().indexOf("update")>-1
				|| method.getName().indexOf("delete")>-1
				|| method.getName().indexOf("remove")>-1
				|| method.getName().indexOf("batch")>-1
				|| method.getName().indexOf("call")>-1
				|| method.getName().indexOf("execute")>-1
				|| method.getName().indexOf("generate")>-1) {
			System.out.println("======"+method.getName()+" switchto: master");
			DataSourceSwitcher.setMaster();
			DataSourceSwitcher.setMaster();
		} else {
			System.out.println("======"+method.getName()+" switchto: slave");
			DataSourceSwitcher.setSlave();
		}}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	// service方法执行完之后被调用
	public void afterReturning(Object arg0, Method method, Object[] args,
			Object target) throws Throwable {
	}

	// 抛出Exception之后被调用
	public void afterThrowing(Method method, Object[] args, Object target,
			Exception ex) throws Throwable {
		
		try{
		//DataSourceSwitcher.setSlave();
		//DataSourceSwitcher.setMaster();
		
		System.out.println("出现异常,切换到: master");
		}catch(Exception e){
			
			//DataSourceSwitcher.setMaster();
			e.printStackTrace();
			
			
		}
	}

}