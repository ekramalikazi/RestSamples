package com.ekram.spring.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ekram.spring.constants.UserRestURIConstants;
import com.ekram.spring.model.User;
import com.ekram.spring.service.UserService;

@RestController
public class UserRegistrationController {

	private static final Logger LOG = LogManager.getLogger();

	@Autowired UserService userService;
	
	@RequestMapping(value = UserRestURIConstants.GET_USER, 
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable("username") String username) {
		LOG.info("Start getUser. username = "+username);
		User user = userService.getUser(username);

		return new ResponseEntity<User>(user, HttpStatus.FOUND);
	}

	@RequestMapping(value = UserRestURIConstants.GET_USERS, 
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUsers() {
		LOG.info("Start getUsers");
		
		List<User> users = userService.getUsers();

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("HeaderKey", "HeaderData");
		return new ResponseEntity<List<User>>(users, responseHeaders, HttpStatus.FOUND);
	}

	@RequestMapping(value = UserRestURIConstants.CREATE_USER,
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
		LOG.info("Start createUser");

		User userCreated = userService.create(user);
		
		String uri = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path(UserRestURIConstants.GET_USER).buildAndExpand(userCreated.getUsername()).toString();
        
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("HeaderKey", "HeaderData");
		responseHeaders.add("Location", uri);
		return new ResponseEntity<User>(userCreated, responseHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = UserRestURIConstants.DELETE_USER, 
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Void> deleteUser(@PathVariable("username") String username) {
		LOG.info("Start deleteUser");
		
		userService.delete(username);
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}

}
