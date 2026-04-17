package com.spring_boot.circuit_breaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Circuit Breaker Spring Boot application.
 *
 * The @SpringBootApplication annotation is a convenience annotation that:
 *  - Enables component scanning
 *  - Enables auto-configuration
 *  - Marks this as a Spring Boot application
 */
@SpringBootApplication
public class CircuitBreakerApplication {

    /**
     * Main method used to launch the Spring Boot application.
     *
     * @param args command-line arguments passed during application startup
     */
    public static void main(String[] args) {
        // Bootstraps the Spring application context
        SpringApplication.run(CircuitBreakerApplication.class, args);
    }

}