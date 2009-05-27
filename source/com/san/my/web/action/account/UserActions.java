package com.san.my.web.action.account;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.san.my.common.util.springs.ServiceLocator;
import com.san.my.service.IUsersService;
import com.san.tmts.dataobjects.UsersDO;

public class UserActions {

	private List<UsersDO> users;

	public String execute() throws Exception {
		IUsersService service = ServiceLocator.getUserService();
		users = service.getUsersList();		
		return ActionSupport.SUCCESS;
	}

	public List<UsersDO> getUsers() {
		return users;
	}

}
