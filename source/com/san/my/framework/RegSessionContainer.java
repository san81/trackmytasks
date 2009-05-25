package com.san.my.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RegSessionContainer {
	private HttpSession regSession;    
    public RegSessionContainer(HttpServletRequest request) {
		regSession = request.getSession(false);
	}
	/**
	 * RegSessionContainer constructor comment.
	 */
	public RegSessionContainer(HttpSession session) {
		regSession = session;
	}

 }