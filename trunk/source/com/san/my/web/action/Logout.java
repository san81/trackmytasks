package com.san.my.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.san.my.common.global.IConstants;

public class Logout {
	
	public String execute() throws Exception {
		//clear all the session params
		ActionContext.getContext().getSession().clear();
		return IConstants.LOGIN_ACTION;
	}
}
