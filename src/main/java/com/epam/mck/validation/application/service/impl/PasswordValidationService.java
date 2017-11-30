/**
 * 
 */
package com.epam.mck.validation.application.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.epam.mck.validation.application.configuration.impl.PasswordValidationConfiguration;
import com.epam.mck.validation.application.exception.ValidationException;
import com.epam.mck.validation.application.service.ValidationService;


/**
 * Implementation of {@link ValidationService} for password validation.
 * The Service that applies rules on given password and returns the {@link List} of issues with password if any.
 * @author Raghavendra_Phani
*/
@Service
public class PasswordValidationService implements ValidationService<String,String> {
	
	@Autowired
    private PasswordValidationConfiguration passwordValidationConfiguration;
		
	  /**
	   * Service method to apply rules on password
	   * @param password  
	   * @return  {@link List}
	   */
	@Override
	public  List<String>  validate(String password) throws ValidationException{
		List<String> errorList = new ArrayList<>();
		if (password  != null && !password.isEmpty()) {	
			try{
				HashMap<String,Pattern> validationRules = passwordValidationConfiguration.getValidationRules();
				  HashMap<String,String> errorMessages =  passwordValidationConfiguration.getErrorMessages();
				for(String rule : validationRules.keySet()) {
					Pattern pattern = validationRules.get(rule); 
					Matcher matcher = pattern.matcher(password);
					if (matcher.find()) {
						errorList.add(errorMessages.get(rule));
					}
				}
			} catch(Exception ex){
				
				throw new ValidationException(ex.getMessage());
			}
			  
		} else {
			errorList.add(passwordValidationConfiguration.getNullPwd());
		}
		return errorList;
	}
	 
}
