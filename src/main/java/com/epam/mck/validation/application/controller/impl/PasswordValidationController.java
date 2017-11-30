/**
 * 
 */
package com.epam.mck.validation.application.controller.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.epam.mck.validation.application.configuration.impl.PasswordValidationConfiguration;
import com.epam.mck.validation.application.controller.ValidationController;
import com.epam.mck.validation.application.exception.ValidationException;
import com.epam.mck.validation.application.service.impl.PasswordValidationService;

/**
 * 
 * Implementation of {@link ValidationController}  for Password validation.
 * 
 * @author Raghavendra_Phani
 * 
 *
 */

@RestController
@RequestMapping(value = "/password")
public class PasswordValidationController implements ValidationController<ResponseEntity<String>, String> {
	private static final Logger LOG = LoggerFactory.getLogger(PasswordValidationController.class);

	@Autowired
	private PasswordValidationService validatorService;
	
 
	@Autowired
    private PasswordValidationConfiguration configuration;
	
	  /**
	   * {@link RequestMapping} for validation.
	   * @param  {{@link String }  password  
	   * @return  {@link ResponseEntity} 
	   */
	@Override
	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	public ResponseEntity<String> validate(@RequestBody (required=false) final String password) {
		LOG.info("Received password for validation ");
		ResponseEntity<String> response = null;
		List<String> errorList = null;
		try {
			errorList = validatorService.validate(password);
		} catch (ValidationException ex) {
			LOG.error("Error due to Exception : ", ex);
			errorList = new ArrayList<String>();
			errorList.add(ex.getMessage());
		}
		if(errorList != null && !errorList.isEmpty()) {
			response = new ResponseEntity<String>(buildErrorMessage(errorList), HttpStatus.BAD_REQUEST);
		}else {
			response = new ResponseEntity<>(configuration.getSuccess(), HttpStatus.OK);
			
		}

		LOG.info("Password validation completed. ");
		return response;

	}

	/**
	 * This method will build the actual error message based given list
	 * 
	 * @param {{@link
	 * 			List} errorList
	 * @return {@link String} errorMsg
	 */
	private String buildErrorMessage(List<String> errorList) {
		StringBuilder errorMsgBuilder = new StringBuilder();
			for (String errorMsg : errorList) {
				errorMsgBuilder.append(errorMsg);
				errorMsgBuilder.append("\n");
		}
	
		String errorMsg = errorMsgBuilder.toString();
		LOG.info("Error Message ----> " + errorMsg);
		return errorMsg;
	}
}
