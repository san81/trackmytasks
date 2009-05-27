package com.san.my.web.action.account;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.san.my.common.util.springs.ServiceLocator;
import com.san.my.service.IUsersService;
import com.san.tmts.dataobjects.UsersDO;

public class UserActions extends ActionSupport implements ServletRequestAware{
	
	 private HttpServletRequest request;
	 public String execute() throws Exception {
	    	IUsersService service = ServiceLocator.getUserService();
	    	List<UsersDO> users = service.getUsersList();	    	
	    	request.setAttribute("users", users);    	
	    	return SUCCESS;
	    }
	 
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}
}
