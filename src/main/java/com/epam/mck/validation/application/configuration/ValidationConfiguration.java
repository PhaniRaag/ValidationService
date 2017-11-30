/**
 * 
 */
package com.epam.mck.validation.application.configuration;

import java.util.Map;

/**
 * 
 * Interface for all Configuration Implementations with in this {@link ValidationApplication}.
 * Implementations should be lazy proxies.
 * 
 * @author Raghavendra_Phani
 * 
 * 
 *  
 *
 */
public interface ValidationConfiguration<T, K> {
	
	public abstract  Map<T,K> getValidationRules();
}
