package com.san.my.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.san.my.framework.RegSessionContainer;
import com.san.my.framework.StartUp;



public class LoginListener implements Filter {
	static Logger logger = Logger.getLogger(LoginListener.class);

	FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		StartUp.init();
	}

	public void destroy() {
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//logger.debug("begin doFilter()...");
		HttpServletRequest hreq = (HttpServletRequest) request;
		HttpSession session = hreq.getSession();
		HttpServletResponse hres = (HttpServletResponse) response;
		RegSessionContainer regSession = new RegSessionContainer(session);
        
		// don't intercept logout, timeout, error and asset requests
		String contextPath = hreq.getContextPath();
		String requestURI = hreq.getRequestURI();
		if (requestURI.endsWith(".gif") || requestURI.endsWith(".jpg")
				|| requestURI.endsWith(".css") || requestURI.endsWith(".js")
				|| requestURI.endsWith(".dtd")
				|| requestURI.endsWith("SrcResponseListener")
				|| requestURI.equals(contextPath + "/logout")
				|| requestURI.equals(contextPath + "/error.jsp")
				|| requestURI.equals(contextPath + "/sso/sign_out.jsp")
				|| requestURI.equals(contextPath + "/timeout.jsp")) {
			chain.doFilter(request, response);
			return;
		}
		// logger.debug("requestURI = " + requestURI);

		/*
		 * catch session timeouts redirect user to timeout page
		 */
		/**
		 * TODO://need to validate
		 */
		//super.validateSession(hreq, hres);		
		chain.doFilter(request, response);
	}

}