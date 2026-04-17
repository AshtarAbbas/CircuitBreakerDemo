package com.spring_boot.circuit_breaker.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;

@Configuration
public class CircuitBreakerConfiguration {

    /**
     * Creates and registers a CircuitBreakerRegistry bean.
     * The registry holds multiple named CircuitBreaker configurations.
     */
    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry(){

        // -------------------------------
        // Default Circuit Breaker Config
        // -------------------------------
        // Uses Resilience4j's built-in default values.
        // Suitable for general-purpose use cases where
        // no fine tuning is required.
        CircuitBreakerConfig defaultCircuitBreakerConfig =
                CircuitBreakerConfig.ofDefaults();

        // --------------------------------------------------
        // Circuit Breaker Config for EXTERNAL applications
        // --------------------------------------------------
        // Intended for unstable or unknown third-party services.
        // - Smaller sliding window to react quickly
        // - Lower failure threshold for faster protection
        // - Longer open state to avoid hammering the external system
        CircuitBreakerConfig externalCircuitBreakerConfig =
                CircuitBreakerConfig.custom()
                        // Number of calls used to calculate the failure rate
                        .slidingWindowSize(5)

                        // Percentage of failures required to open the circuit
                        .failureRateThreshold(40)

                        // Time the circuit remains OPEN before trying half-open
                        .waitDurationInOpenState(Duration.ofSeconds(60))
                        .build();

        // --------------------------------------------------
        // Circuit Breaker Config for INTERNAL applications
        // --------------------------------------------------
        // Intended for stable internal microservices.
        // - Larger sliding window for better accuracy
        // - Higher failure tolerance
        // - Shorter open state duration
        CircuitBreakerConfig internalCircuitBreakerConfig =
                CircuitBreakerConfig.custom()
                        // Larger sample size for internal reliability
                        .slidingWindowSize(20)

                        // Higher failure threshold since we trust internal services
                        .failureRateThreshold(70)

                        // Shorter recovery time
                        .waitDurationInOpenState(Duration.ofSeconds(15))
                        .build();

        // ------------------------------------
        // Create CircuitBreakerRegistry
        // ------------------------------------
        // Registers multiple named circuit breakers
        // which can be referenced by name in services
        return CircuitBreakerRegistry.of(
                Map.of(
                        "default", defaultCircuitBreakerConfig,
                        "external", externalCircuitBreakerConfig,
                        "internal", internalCircuitBreakerConfig
                )
        );
    }
}