package com.san.my.service;

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
}
