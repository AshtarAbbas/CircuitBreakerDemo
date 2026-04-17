package com.spring_boot.circuit_breaker.response;

/**
 * Data Transfer Object (DTO) used to represent
 * the response returned from the Order Service.
 *
 * This class is used to map request/response payloads
 * when communicating between microservices.
 */
public class OrderResponse {

    /**
     * Unique identifier of the order
     */
    private Long id;

    /**
     * Status message of the order
     * (e.g., CREATED, FAILED, SUCCESS)
     */
    private String status;

    /**
     * Default no-args constructor.
     *
     * Required for:
     * - JSON serialization/deserialization (Jackson)
     * - Framework usage (Spring, Hibernate, etc.)
     */
    public OrderResponse() {
        // No-args constructor
    }

    /**
     * Parameterized constructor for easy object creation.
     *
     * @param id     unique order identifier
     * @param status status message of the order
     */
    public OrderResponse(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    /**
     * Returns the order ID.
     *
     * @return order id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the order ID.
     *
     * @param id order identifier
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the order status.
     *
     * @return order status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the order status.
     *
     * @param status order status message
     */
    public void setStatus(String status) {
        this.status = status;
    }
}