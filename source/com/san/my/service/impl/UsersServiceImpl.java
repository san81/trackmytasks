package com.san.my.service.impl;

import java.util.List;

import com.san.my.dao.UsersDAO;
import com.san.my.service.IUsersService;
import com.san.tmts.dataobjects.UsersDO;

public class UsersServiceImpl implements IUsersService {

	private UsersDAO usersDAO;
	
	public void setUsersDAO(UsersDAO userDao) {
		this.usersDAO = userDao;
	}
	
	public UsersDO loadUser(String email, String password) {
		return usersDAO.loadUser(email);				
	}
	
	public List<UsersDO> getUsersList(){
		return usersDAO.getUsersList();
	}

}
