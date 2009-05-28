package com.san.my.web.action.account;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.san.my.common.util.springs.ServiceLocator;
import com.san.my.service.IUsersService;
import com.san.tmts.dataobjects.UsersDO;

public class UserActions {

	private List<UsersDO> users;
	private int userId;
	private String userName;
	private String password;
	private String email;	
	public String execute() throws Exception {
		IUsersService service = ServiceLocator.getUserService();
		users = service.getUsersList();		
		return ActionSupport.SUCCESS;
	}
	
//	public String editUser() throws Exception {
//		
//	}
	
	public List<UsersDO> getUsers() {
		return users;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
