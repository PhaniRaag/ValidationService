package com.epam.mck.validation.application.test;


import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.epam.mck.validation.application.ValidationApplication;
import com.epam.mck.validation.application.configuration.impl.PasswordValidationConfiguration;
import com.epam.mck.validation.application.controller.impl.PasswordValidationController;



/**
 *  1) Out-of-container test for {@link ValidationApplication} using mock post calls.
 *  2) Verifies everything -- except for the call to the config-server.
 *  3) All the test cases use  application-test.properties for test configuration 
 * 
 *   @author  Raghavendra_Phani
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test") 
public class OutOfContainerTest {
	
	@Autowired  PasswordValidationController controller;
	@Autowired 	WebApplicationContext spring;
	@Autowired  PasswordValidationConfiguration configuration;
	MockMvc mockMvc;
	
 	@Configuration
	@Import(ValidationApplication.class)
 	@PropertySource( "classpath:application-test.properties")
	public static class Config {
	}
 	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(spring).build();
	}
  
	@Test
	public void propertyLoadTest() throws Exception {
		assertTrue(configuration.getErrorMessages().keySet().size()== 6);
		assertTrue(configuration.getRules().keySet().size() == 6);
		assertTrue(configuration.getSuccess().equals("Password is accepted"));
	}
	
	@Test
	public void pwdAcceptedTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/password/validate").content("123test"))
			.andExpect(status().isOk())
			.andExpect(content().string(configuration.getSuccess()));
		
	}
	@Test
	public void pwdNotAcceptedTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/password/validate").content("test"))
			.andExpect(status().isBadRequest());
	}
	
	
	
	 
	  
  
}
