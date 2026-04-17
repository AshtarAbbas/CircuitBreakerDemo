# Order Service

A simple Spring Boot **Order Service** demonstrating a REST API endpoint for creating orders. This project is suitable as a starter example for microservices and circuit breaker demonstrations.

---

## 📦 Project Structure

```
order-service
└── src
    └── main
        └── java
            └── com.circuit_breaker.order_service
                ├── controller
                │   └── OrderController.java
                └── response
                    └── OrderResponse.java
```

---

## 🚀 API Endpoint

### Create Order

**Request**
```
GET /api/create-order
```

**Response**
```
HTTP Status: 201 CREATED
```

```json
{
  "id": 1,
  "status": "Order Created Successfully"
}
```

---

## 🧩 Code Overview

### OrderController
- Exposes REST endpoint for order creation
- Uses `ResponseEntity` to return HTTP status `201 CREATED`

### OrderResponse
- Simple DTO containing:
  - `id` (Long)
  - `status` (String)

---

## ⚙️ Configuration

**application.properties**

```properties
spring.application.name=order-service
server.port=8081
```

---

## 🛠️ How to Run

1. Clone the repository
2. Ensure Java 17+ and Maven are installed
3. Run the application:

```bash
mvn spring-boot:run
```

4. Access the API:

```
http://localhost:8081/api/create-order
```

---

## ✅ Tech Stack

- Java
- Spring Boot
- REST API
- Maven

---

## 📘 Notes

- This service currently returns static data
- Can be extended with database integration or circuit breaker (Resilience4j)

---

## 📄 License

This project is for learning and demonstration purposes.
