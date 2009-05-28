package com.san.my.web.interceptors;

import java.util.Map;

import javax.servlet.FilterConfig;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.san.my.common.global.IConstants;
import com.san.my.framework.StartUp;



public class LoginInterceptor implements Interceptor {
	static Logger logger = Logger.getLogger(LoginInterceptor.class);

	FilterConfig filterConfig = null;

	public void init() {
		StartUp.init();		
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		
		 // TODO Login validation - exclude for image requests
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		System.out.println("----------");
		if(session.get(IConstants.KEY_USER_IN_SESSION)==null)
			return IConstants.LOGIN_ACTION;		
		return invocation.invoke();
	}

	public void destroy() {
		
	}

}