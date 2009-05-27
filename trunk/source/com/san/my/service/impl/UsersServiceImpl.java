package com.san.my.service.impl;

import com.san.my.dao.UsersDAO;
import com.san.my.service.IUsersService;
import com.san.tmts.dataobjects.UsersDO;

public class UsersServiceImpl implements IUsersService {

	private UsersDAO usersDAO;
	
	public void setUsersDAO(UsersDAO userDao) {
		this.usersDAO = userDao;
	}
	
	public int validateUser(String email, String password) {
		UsersDO user = usersDAO.loadUser(email);
		if(user==null)
			return 2;
		if(user.getPassword().equals(password))
			return 0;
		else return 1;		
	}

}
