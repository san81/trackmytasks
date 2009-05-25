package com.san.my.web.interceptors;

import javax.servlet.FilterConfig;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.san.my.framework.StartUp;



public class LoginInterceptor implements Interceptor {
	static Logger logger = Logger.getLogger(LoginInterceptor.class);

	FilterConfig filterConfig = null;

	public void init() {
		StartUp.init();		
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Login validation - exclude for image requests
		return invocation.invoke();
	}

	public void destroy() {
		
	}

}