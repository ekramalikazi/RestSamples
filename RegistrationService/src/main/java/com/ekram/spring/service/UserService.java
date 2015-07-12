package com.ekram.spring.service;

import java.util.List;

import com.ekram.spring.model.User;

/**
 * User Service to perform actions on user entity.
 * 
 * @author kazi_e
 *
 */
public interface UserService {

	/**
	 * returns all users in the system.
	 * 
	 * @return
	 */
	List<User> getUsers();

	/**
	 * returns user based on supplied username.
	 * 
	 * @param username
	 * @return
	 */
	User getUser(String username);
	
	/**
	 * Creates a new user in system.
	 * 
	 * @param emp
	 * @return
	 */
	User create(User emp);

	/**
	 * Deletes a user based on supplied username.
	 * @param username
	 */
	void delete(String username);
	
	

}
