/**
 * 
 */
package com.epam.mck.validation.application.service;

import java.util.List;

/**
 * Interface for all Service Implementations within this {@link ValidationApplication}.
 * 
 * @author Raghavendra_Phani
 *
 */
public interface ValidationService <T,K> {
	public abstract  List<T> validate(K validatable) throws Exception ;
}
