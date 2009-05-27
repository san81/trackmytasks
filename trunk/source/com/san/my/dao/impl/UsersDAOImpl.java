package com.san.my.dao.impl;

import java.util.List;

import com.san.my.dao.UsersDAO;
import com.san.tmts.dataobjects.UsersDO;

public class UsersDAOImpl extends ObjectDAOImpl implements UsersDAO{
	
	public UsersDO loadUser(String email){
		List<UsersDO> users = find("from UsersDO where email='"+email+"'");
		if(users.size()==0)
			return null;
		return users.get(0);
	}
}
