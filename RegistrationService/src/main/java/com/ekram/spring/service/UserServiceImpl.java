package com.ekram.spring.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ekram.spring.dao.InMemoryUserDao;
import com.ekram.spring.model.User;

@Service("userService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UserServiceImpl implements UserService {

	@Autowired InMemoryUserDao inMemoryUserDao;

	@Override
	public List<User> getUsers() {
		return inMemoryUserDao.findAll();
	}

	@Override
	public User create(User user) {
		
		if (user == null){
			throw new RuntimeException("user is null");
		}
		user.setCreatedDate(new Date());
		return inMemoryUserDao.createUser(user);
	}

	@Override
	public void delete(String username) {
		if ("".equals(username)){
			throw new RuntimeException("username is empty");
		}
		User user = inMemoryUserDao.findAccount(username);
		inMemoryUserDao.removeAccount(user);
	}
}
