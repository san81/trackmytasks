package com.san.my.dao;

import java.util.List;

import com.san.tmts.dataobjects.UsersDO;

/**
 * Interface for user related operations.
 * 
 * @author santosh
 *
 */
public interface UsersDAO {
	
	/**
	 * Load the user, based on the given Email id.
	 * 
	 * @param email
	 * @return
	 */
	public UsersDO loadUser(String email);
	
	public List<UsersDO> getUsersList();
}
