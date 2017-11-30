/**
 * 
 */
package com.epam.mck.validation.application.exception;

import com.epam.mck.validation.application.ValidationApplication;

/**
 * General exception class for {@link ValidationApplication} 
 * @author  Raghavendra_Phani
 *
 */
public class ValidationException extends Exception {

	public ValidationException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
