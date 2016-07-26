package com.comm.util.page;


import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;



public class PagerFilter implements Filter {
	public static final String PAGE_SIZE_NAME = "ps";
	
	
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		// 获得所有的设置参数
//		Enumeration<String> e = filterConfig.getInitParameterNames();
//		while (e.hasMoreElements()) {
//			String parameter = e.nextElement();
//			String value = filterConfig.getInitParameter(parameter);// 获得参数值
////			response.setHeader(parameter, value);// 设置header属性
//			System.out.println(value);
//		}
		
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		SystemContext.setOffset(getOffset(httpRequest));
		SystemContext.setPagesize(getPagesize(httpRequest));
		try{
			chain.doFilter(request, response);
		}finally{
			SystemContext.removeOffset();
			SystemContext.removePagesize();
		}
	}
	
	private int getOffset(HttpServletRequest request){
		int offset = 0;
		try {
			offset = Integer.parseInt(request.getParameter("pager.offset"));
		} catch (Exception ignore) {
		}
		return offset;
	}
	
	private int getPagesize(HttpServletRequest httpRequest){
		String psvalue = httpRequest.getParameter("pagesize");
		if(psvalue != null && !psvalue.trim().equals("")){
			Integer ps = 0;
			try {
				ps = Integer.parseInt(psvalue);
			} catch (Exception e) {
			}
			if(ps != 0){
				httpRequest.getSession().setAttribute(PAGE_SIZE_NAME, ps);
			}
		}

		Integer pagesize = (Integer)httpRequest.getSession().getAttribute(PAGE_SIZE_NAME);
		if(pagesize == null){
			httpRequest.getSession().setAttribute(PAGE_SIZE_NAME, 10);
			return 10;
		}
		
		return pagesize;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
