/**
 * 
 */
package com.epam.mck.validation.application.controller.test;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.mck.validation.application.ValidationApplication;
import com.epam.mck.validation.application.configuration.impl.PasswordValidationConfiguration;
import com.epam.mck.validation.application.controller.impl.PasswordValidationController;

/**
 * Unit Test of  {@link PasswordValidationController}.
 * Utilizes application-test.properties for test configuration  with 'test' as active profile
 *  @author Raghavendra_Phani  
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test") 
public class PasswordValidationControllerTest {
	
	
	@Configuration
	@Import(ValidationApplication.class)
	@PropertySource( "classpath:application-test.properties")
	public static class Config {
	}
	
	@Autowired 
	private PasswordValidationController controller;
	@Autowired  
	private PasswordValidationConfiguration configuration;
	
	private Map<String,String> errorMessages;
	
	@Before
	public void setup() {
		errorMessages = configuration.getErrorMessages();
	}
 
 	
 	 @Test
 	public void testForUpperCaseLetters() {
 		ResponseEntity<String> response = controller.validate("Abcde");
 		assertTrue(response.getBody().contains(errorMessages.get("upperCase")));
 	}

 	@Test
 	public void testforInvalidLength() {
 		ResponseEntity<String> response = controller.validate("a1");
 		assertTrue(response.getBody().contains(errorMessages.get("length")));
 	}
 	
 	 @Test
 	public void testForSequenceofLetters() {
 		ResponseEntity<String> response = controller.validate("abcabc");
 		assertTrue(response.getBody().contains(errorMessages.get("sequence")));
 	}
 	
 	@Test
 	public void testForSequenceOfNumbers() {
 		ResponseEntity<String> response = controller.validate("test11");
 		assertTrue(response.getBody().contains(errorMessages.get("sequence")));
 	}
 	
 	@Test
 	public void testForSequenceOfLetterAndDigits() {
 		ResponseEntity<String> response = controller.validate("a12a12");
 		assertTrue(response.getBody().contains(errorMessages.get("sequence")));
 	}

 	@Test
 	public void testForLowLength() {
 		ResponseEntity<String> response = controller.validate("a12b");
 		assertTrue(response.getBody().contains(errorMessages.get("length")));
 	}
 	
 	@Test
 	public void testForAboveLength() {
 		ResponseEntity<String> response = controller.validate("1234abc563test78");
 		assertTrue(response.getBody().contains(errorMessages.get("length")));
 	}

	@Test
	public void testForSpecialCharacters() {
		ResponseEntity<String> response = controller.validate("test@123");
		assertTrue(response.getBody().contains(errorMessages.get("special")));
	}
	
	// Negative Test
	@Test
	public void assertValidPassword() {
		ResponseEntity<String> response = controller.validate("test123");
		assertFalse(response.getBody().contains(errorMessages.get("length")));
		assertFalse(response.getBody().contains(errorMessages.get("numbers")));
		assertFalse(response.getBody().contains(errorMessages.get("sequence")));
		assertFalse(response.getBody().contains(errorMessages.get("special")));
		assertFalse(response.getBody().contains(errorMessages.get("letters")));
		assertFalse(response.getBody().contains(errorMessages.get("upperCase")));
	}

}
