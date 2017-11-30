
package com.epam.mck.validation.application.configuration.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import com.epam.mck.validation.application.configuration.ValidationConfiguration;


/**
 * Implementation of {@link ValidationConfiguration} for password validation.
 * This Class is a POJO of Configuration that was fetched from config-server.
 * This class will fetch Configuration from application.properties when config-server is down.
 * Once a "refresh" is hit, a new proxy is created when all the threads running are finished.
 * 
 * @see {@link RefreshScope} which allows a {@link Component} to refresh itself when "refresh" post URL is hit. 
 *      {@link ConfigurationProperties} which allows a {@link Component} to fetch given prefixed configuration.
 * @author Raghavendra_Phani
 * 
 */
@Component
@RefreshScope
@ConfigurationProperties(prefix = "validation")
public class PasswordValidationConfiguration implements ValidationConfiguration<String,Pattern> {

	private static final Logger LOG = LoggerFactory.getLogger(PasswordValidationConfiguration.class);
	private String nullPwd;
	private String success;
	private String genericMessage;
	private Map<String, String> rules;
	private HashMap<String, String> errorMessages;
	private HashMap<String,Pattern> validationRules;

	public String getNullPwd() {
		return nullPwd;
	}

	public void setNullPwd(String nullPwd) {
		this.nullPwd = nullPwd;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getGenericMessage() {
		return genericMessage;
	}

	public void setGenericMessage(String genericMessage) {
		this.genericMessage = genericMessage;
	}

	public Map<String, String> getRules() {
		return rules;
	}

	public void setRules(Map<String, String> rules) {
		this.rules = rules;
	}

	
	public HashMap<String, String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(HashMap<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}

 
/**
 * Lazy loads {@link validationsRules} when asked for the first time and only once per refresh cycle.
 * */
 	 
	public HashMap<String,Pattern> getValidationRules2() {
		LOG.info("Validation Rules available : " + getRules());
		if(validationRules == null || validationRules.isEmpty()){
			  validationRules = new HashMap<String, Pattern>();
			  for (String rule : getRules().keySet()) {
					validationRules.put(rule, Pattern.compile(getRules().get(rule)));
			}
		}
		return validationRules;
	} 
}
