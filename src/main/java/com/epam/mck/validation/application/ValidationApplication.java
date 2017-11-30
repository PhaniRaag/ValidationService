package com.epam.mck.validation.application;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Cloud Ready MicroService to validate given data. 
 * Currently Password validation is available.
 * Eureka/ Discovery configuration should be added.
 *  
 * @author Raghavendra_Phani
 *
 */

@SpringBootApplication
public class ValidationApplication extends SpringBootServletInitializer {
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ValidationApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ValidationApplication.class, args);
	}

}
