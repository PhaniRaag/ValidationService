package com.epam.mck.validation.application;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Cloud Ready MicroService to validate given data. 
 * Currently Password validation is available.
 * Eureka/ Discovery configuration should be added.
 *  
 * @author Raghavendra_Phani
 *
 */

@SpringBootApplication
public class ValidationApplication {
	public static void main(String[] args) {
		SpringApplication.run(ValidationApplication.class, args);
	}

}
