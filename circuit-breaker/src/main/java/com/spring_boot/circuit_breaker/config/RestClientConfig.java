package com.spring_boot.circuit_breaker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    /**
     * Creates a RestTemplate bean for performing synchronous HTTP requests.
     *
     * This RestTemplate can be injected into services using @Autowired
     * and used to call external or internal REST APIs.
     *
     * Common use cases:
     * - Calling other microservices
     * - Integrating with third‑party APIs
     * - Working with Circuit Breakers (Resilience4j)
     */
    @Bean
    public RestTemplate restTemplate(){
        // Returns a default RestTemplate instance
        // Can be enhanced later with interceptors, timeouts, or error handlers
        return new RestTemplate();
    }
}