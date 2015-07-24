package com.ekram.spring.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ekram.spring.controller.exception.DuplicateUserException;
import com.ekram.spring.controller.exception.UserNotFoundException;
import com.ekram.spring.helper.FileHelper;
import com.ekram.spring.model.User;

/**
 * InMemory DAO to encapsulate user storage.
 * 
 * @author kazi_e
 *
 */
@Component("inMemoryUserDao")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class InMemoryUserDao {

	private Map<String, User> users;

	public InMemoryUserDao() {
		users = Collections.synchronizedMap(new HashMap<String, User>());
	}

	public boolean userExists(String username) {
		return users.containsKey(username);
	}
	
	public User createUser(User user) {
		if (userExists(user.getUsername())) {
			throw new DuplicateUserException();
		}
		users.put(user.getUsername(), user);
		return user;
	}
	
	public void updateUser(User user) {
		if (!userExists(user.getUsername())) {
			throw new UserNotFoundException();
		}
		users.put(user.getUsername(), user);
	}
	
	public void removeUser(User user) {
		if (!userExists(user.getUsername())) {
			throw new UserNotFoundException();
		}
		users.remove(user.getUsername());
	}
	
	public User findUser(String username) {
		User user = users.get(username);
		if (user == null) {
			throw new UserNotFoundException();
		}
		return user;
	}
	
	public List<User> findAll(){
		return new ArrayList<User>(users.values());
	}
	
	@PreDestroy
	public void doCleanUp(){
		FileHelper.writeToJson(users);
	}
	
	@PostConstruct
	public void doPost(){
		Map<String, User> users = FileHelper.readFromJson();
		
		if(users != null && users.size() > 0){
			this.users.putAll(users);
		}
		
	}
}
