/**
 * 
 */
package com.epam.mck.validation.application.controller;

 

/**
 * 
 * Interface for all Controller Implementations with in this {@link ValidationApplication}.
 * @author Raghavendra_Phani
 *
 */

public interface ValidationController<K,T> {
	
	public  abstract K validate(T validatable);
	
}