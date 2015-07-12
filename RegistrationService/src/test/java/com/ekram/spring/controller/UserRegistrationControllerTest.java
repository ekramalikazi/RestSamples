package com.ekram.spring.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ekram.spring.constants.UserRestURIConstants;
import com.ekram.spring.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/appServlet/servlet-context.xml")
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
		this.mockMvc.perform(post(UserRestURIConstants.CREATE_USER)
						  .content(asJsonString(user1))
						  .contentType(MediaType.APPLICATION_JSON)
						  .accept(MediaType.APPLICATION_JSON))
						  .andExpect(status().isCreated());
              
	}
	
	@Test
	public void testGetUsers() throws Exception {
		this.mockMvc.perform(get(UserRestURIConstants.GET_USERS)
						  .contentType(MediaType.APPLICATION_JSON)
						  .accept(MediaType.APPLICATION_JSON))
						  .andExpect(status().isFound());
              
	}
	
	@Test
	public void testDeleteUser() throws Exception {
		String url = "/rest/user/delete/" + user1.getUsername();
		this.mockMvc.perform(delete(url)
						  .contentType(MediaType.APPLICATION_JSON)
						  .accept(MediaType.APPLICATION_JSON))
						  .andExpect(status().isOk());
              
	}
	
	public static String asJsonString(final User user) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(user);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
