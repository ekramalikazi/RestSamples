package com.ekram.spring.service;

import java.util.List;

import com.ekram.spring.model.User;

public interface UserService {

	List<User> getUsers();

	User getUser(String username);
	
	User create(User emp);

	void delete(String username);
	
	

}
