package com.example.springbootbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Main Spring Boot application class for the Online Shopping Mall system
 */
@SpringBootApplication
@EnableCaching
public class springbootbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(springbootbackendApplication.class, args);
	}

}
