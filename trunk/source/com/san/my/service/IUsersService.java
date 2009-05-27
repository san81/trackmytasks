package com.san.my.service;

import java.util.List;

import com.san.tmts.dataobjects.UsersDO;

/**
 * Service for user management actions.
 * 
 * @author santosh
 *
 */
public interface IUsersService {
	/**
	 * Validates the user. retun values are
	 * 0 - if user find and password matches
	 * 1 - if user find but invalid password
	 * 2 - if user not found.
	 * @param email
	 * @param password
	 * @return
	 */
	public int validateUser(String email,String password); 
	
	/**
	 * Returns the list of users
	 * 
	 * @return
	 */
	public List<UsersDO> getUsersList();
}
