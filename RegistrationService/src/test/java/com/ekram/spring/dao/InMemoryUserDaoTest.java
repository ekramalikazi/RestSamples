package com.ekram.spring.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.ekram.spring.controller.exception.DuplicateUserException;
import com.ekram.spring.model.User;

public class InMemoryUserDaoTest {

	private static final String EXISTING_USERNAME = "testuser";
	private static final String EXISTING_EMAIL = "test@mailcatch.com";

	private static final String NEW_USERNAME = "newtestuser";
	private static final String NEW_EMAIL = "newtest@mailcatch.com";
	
	private User user;
	private InMemoryUserDao inMemoryUserDao;

	@Before
	public void init() {
		user = new User();
		user.setUsername(EXISTING_USERNAME);
		user.setEmail(EXISTING_EMAIL);
		inMemoryUserDao = new InMemoryUserDao();
		inMemoryUserDao.createUser(user);
	}

	@Test
	public void userExists() {
		assertTrue(inMemoryUserDao.userExists(EXISTING_USERNAME));
		assertFalse(inMemoryUserDao.userExists(NEW_USERNAME));
	}
	
	@Test
	public void createNewUser() {
		User user = new User();
		user.setUsername(NEW_USERNAME);
		user.setEmail(NEW_EMAIL);
		inMemoryUserDao.createUser(user);
		assertEquals(inMemoryUserDao.findUser(NEW_USERNAME).getEmail(), NEW_EMAIL);
	}

	@Test(expected = DuplicateUserException.class)
	public void createDuplicateAccount() {
		User user = new User();
		user.setUsername(EXISTING_USERNAME);
		user.setEmail(EXISTING_EMAIL);
		inMemoryUserDao.createUser(user);
	}

}
