# Circuit Breaker Interview Answers

## Basic Understanding
### What is a circuit breaker pattern? Why do we need it?
A circuit breaker is a resilience pattern used in distributed systems to prevent repeated calls to a failing service. It detects failures and stops calls temporarily, allowing the failing service time to recover and protecting the system from cascading failures.

### What problems does a circuit breaker solve in microservices?
It prevents cascading failures, avoids resource exhaustion (threads, memory), reduces latency amplification, and improves overall system stability.

### How is a circuit breaker different from retries or timeouts?
Timeouts limit how long a call waits. Retries reattempt failed calls (which can worsen load). A circuit breaker proactively blocks calls when failure thresholds are exceeded.

### Where would you use a circuit breaker in a system?
At service-to-service calls, external APIs, message brokers, and sometimes database calls.

### What happens if you don’t use a circuit breaker?
Failures propagate, leading to thread pool exhaustion, slow responses, outages, and complete system collapse.

## Circuit Breaker States
### What are the different states of a circuit breaker?
Closed, Open, and Half-Open.

### Explain Closed, Open, and Half-Open states
- Closed: Requests flow normally; failures are monitored.
- Open: All requests fail fast without calling the dependency.
- Half-Open: Limited test requests are allowed to check recovery.

### What happens in the Half-Open state?
A small number of requests are allowed. Success closes the circuit; failure reopens it.

### How does the circuit breaker transition back to Closed?
After successful requests in Half-Open exceed the success threshold.

### Who decides when the circuit breaker should open or close?
The circuit breaker logic based on configured metrics and thresholds.

### What happens if Half-Open requests fail again?
The circuit transitions back to Open and waits for the next retry window.

## Metrics & Thresholds
### What metrics are used to trip a circuit breaker?
Failure rate, slow call rate, timeout rate, and exception count.

### How do you calculate failure rate?
Failures / total calls within a sliding window.

### What is a sliding window in circuit breakers?
A rolling set of recent calls used to calculate metrics.

### Request volume vs failure percentage—why do both matter?
Low traffic can distort failure percentage, so minimum call volume avoids false positives.

### How do you avoid false positives when traffic is low?
Configure a minimum number of calls before evaluation.

## Implementation Questions
### How would you implement a circuit breaker from scratch?
Track call results, maintain state machine, evaluate thresholds, and short-circuit calls when necessary.

### What data structures would you use to track failures?
Ring buffers, counters, timestamps, and atomic variables.

### Is the circuit breaker state shared or per instance?
Usually per instance; shared state requires coordination.

### How do you ensure thread safety?
Using atomic variables, locks, or synchronized structures.

### Can multiple threads open the circuit breaker at the same time?
Yes, but atomic state transitions prevent inconsistency.

## Framework-Specific (Spring)
### Have you used Resilience4j / Hystrix?
Resilience4j is lightweight, functional, and preferred since Hystrix is deprecated.

### What annotations are used in Spring Boot?
@CircuitBreaker, @Retry, @TimeLimiter.

### How do you define fallback methods?
Using fallbackMethod attribute pointing to a local method.

### What happens if the fallback also fails?
An exception propagates or a default response is returned.

### Hystrix vs Resilience4j
Hystrix is deprecated and thread-based; Resilience4j is non-blocking and modular.

## Circuit Breaker vs Other Patterns
### Circuit breaker vs retry—when should you NOT retry?
Do not retry on non-idempotent calls or when failure is systemic.

### Circuit breaker vs bulkhead pattern
Bulkhead isolates resources; circuit breaker blocks failing calls.

### How do circuit breakers work with timeouts?
Timeouts detect slow failures; circuit breakers react to them.

### Client side or server side circuit breaker?
Client-side is preferred to reduce load on failing services.

### Do you use circuit breaker for database calls?
Sometimes, for remote databases or read replicas, but cautiously.

## Distributed Systems
### Is circuit breaker state local or global?
Usually local; global state improves consistency but increases complexity.

### How would you design a circuit breaker for a high-traffic API?
Use client-side breakers, granular instances, low-latency metrics, and dashboards.

### What happens during partial outages?
Circuit breakers isolate failures and allow healthy paths to operate.

### How does a circuit breaker help with graceful degradation?
By serving fallbacks instead of failing requests.

## Edge Cases & Real World
### What if a dependency is slow but not failing?
Use slow-call thresholds and timeouts.

### How do you handle long-running requests?
Apply timeouts and async execution.

### What if dependency recovers but CB stays open too long?
Tune waitDurationInOpenState and monitor recovery.

### How do you tune configuration in production?
Gradual tuning with metrics and load testing.

### How do you monitor circuit breaker behavior?
Using metrics like open rate, failure rate, rejected calls.

## Scenario-Based
### Downstream service intermittently failing—what do you do?
Add circuit breaker, retries with backoff, and fallback.

### Users see fallback responses—how do you debug?
Check logs, breaker metrics, downstream latency and error rates.

### Circuit breaker opens only under peak traffic—why?
Latency spikes and resource saturation under load.

### How do you test circuit breaker behavior?
Unit tests with failure simulation and integration tests.

### How do you load test a circuit breaker?
Stress downstream dependencies while monitoring breaker state transitions.
