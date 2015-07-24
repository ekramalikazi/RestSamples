package com.ekram.spring.controller;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ekram.spring.constants.UserRestURIConstants;
import com.ekram.spring.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@WebAppConfiguration
public class UserRegistrationControllerTest {

	private static final String EXISTING_USERNAME = "testuser";
	private static final String EXISTING_EMAIL = "test@mailcatch.com";
	
	@Autowired private WebApplicationContext wac;
 
    private MockMvc mockMvc;
 
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
	
	private static User user1;
	
	@BeforeClass
	public static void oneTimeSetUp() {
		user1 = new User();
		user1.setUsername(EXISTING_USERNAME);
		user1.setEmail(EXISTING_EMAIL);
	}
	
	@Test
	public void testCreateUser() throws Exception {
		MvcResult result = this.mockMvc.perform(post(UserRestURIConstants.CREATE_USER)
						  .content(asJsonString(user1))
						  .contentType(MediaType.APPLICATION_JSON)
						  .accept(MediaType.APPLICATION_JSON))
						  .andDo(print())
						  .andExpect(status().isCreated()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		User createdUser = convertToUser(content);
		
		assertThat(user1.getEmail(), is(createdUser.getEmail()));
		assertThat(user1.getUsername(), is(createdUser.getUsername()));
		assertThat(createdUser.getCreatedDate(), notNullValue());
	}
	
	@Test
	public void testGetUsers() throws Exception {
		this.mockMvc.perform(get(UserRestURIConstants.GET_USERS)
						  .contentType(MediaType.APPLICATION_JSON)
						  .accept(MediaType.APPLICATION_JSON))
						  .andDo(print())
						  .andExpect(status().isFound());
              
	}
	
	@Test
	public void testDeleteUser() throws Exception {
		String url = "/rest/user/delete/" + user1.getUsername();
		/*this.mockMvc.perform(delete(url)
						  .contentType(MediaType.APPLICATION_JSON)
						  .accept(MediaType.APPLICATION_JSON))
						  .andExpect(status().isOk());*/
		this.mockMvc.perform(put(url)
				  .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON))
				  .andExpect(status().isOk());
              
	}
	
	private static String asJsonString(final User user) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(user);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	private static User convertToUser(final String jsonString) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final User userObject = mapper.readValue(jsonString, User.class);
	        return userObject;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
