package com.dhami.java_dev.microservices;

/**
 * ============================================================
 *              API GATEWAY - INTERVIEW QUICK REVISION
 * ============================================================
 *
 *  Author : Dhami
 *  Topic  : API Gateway (Microservices)
 *  Use    : Interview prep + last-minute revision
 *
 * ============================================================
 *  1. WHAT IS AN API GATEWAY? (Definition - 1 liner)
 * ============================================================
 *
 *  A single entry point for all client requests into a
 *  microservices system. It routes requests to the appropriate
 *  backend services and handles cross-cutting concerns such as
 *  authentication, rate limiting, logging, and load balancing.
 *
 *  Simple Analogy:
 *    API Gateway = The main gate + security guard of a building
 *    - Everyone enters through one gate
 *    - The guard verifies identity (auth)
 *    - The guard directs you to the correct floor (routing)
 *
 *
 * ============================================================
 *  2. DIAGRAM - WITHOUT API GATEWAY (Direct Calls)
 * ============================================================
 *
 *      +-----------+       +------------------+
 *      | Mobile    | ----> |  User Service    |
 *      +-----------+       +------------------+
 *           |              +------------------+
 *           +------------> |  Order Service   |
 *           |              +------------------+
 *           |              +------------------+
 *           +------------> | Payment Service  |
 *                          +------------------+
 *
 *      +-----------+       +------------------+
 *      | Web App   | ----> |  User Service    |
 *      +-----------+       +------------------+
 *           |              +------------------+
 *           +------------> |  Order Service   |
 *           |              +------------------+
 *           |              +------------------+
 *           +------------> | Payment Service  |
 *                          +------------------+
 *
 *  Problems:
 *    - Clients must know every service address
 *    - Each client handles auth, retries, rate limits
 *    - Chatty communication (many round trips)
 *    - Hard to refactor or version services
 *
 *
 * ============================================================
 *  3. DIAGRAM - WITH API GATEWAY (Single Entry Point)
 * ============================================================
 *
 *      +-----------+                          +------------------+
 *      | Mobile    | ----+                    |  User Service    |
 *      +-----------+     |                    +------------------+
 *                        |                    +------------------+
 *      +-----------+     |    +-----------+   |  Order Service   |
 *      | Web App   | ----+--> |   API     |-->+------------------+
 *      +-----------+     |    |  GATEWAY  |   +------------------+
 *                        |    +-----------+   |  Payment Service |
 *      +-----------+     |                    +------------------+
 *      | Partner   | ----+                    +------------------+
 *      +-----------+                          |  Inventory Svc   |
 *                                             +------------------+
 *
 *  Benefits:
 *    - Single endpoint for all clients
 *    - Centralized auth, rate limit, logging
 *    - Backend services stay hidden and simple
 *
 *
 * ============================================================
 *  4. DIAGRAM - REQUEST LIFECYCLE THROUGH GATEWAY
 * ============================================================
 *
 *   Client
 *     |
 *     v
 *  [1] TLS Termination
 *     |
 *     v
 *  [2] Authentication (JWT / API Key)
 *     |
 *     v
 *  [3] Authorization (RBAC / Scopes)
 *     |
 *     v
 *  [4] Rate Limiting Check
 *     |
 *     v
 *  [5] Request Transformation (headers, path rewrite)
 *     |
 *     v
 *  [6] Service Discovery + Load Balancer
 *     |
 *     v
 *  [7] Circuit Breaker + Timeout
 *     |
 *     v
 *  Backend Microservice
 *     |
 *     v
 *  [8] Response Transformation / Filtering
 *     |
 *     v
 *  [9] Optional Caching
 *     |
 *     v
 *  [10] Logging + Tracing + Return to Client
 *
 *
 * ============================================================
 *  5. DIAGRAM - GATEWAY AGGREGATION PATTERN
 * ============================================================
 *
 *   Client ---- /api/dashboard ----> +-------------+
 *                                    | API GATEWAY |
 *                                    +-------------+
 *                                       |   |   |
 *                        +--------------+   |   +--------------+
 *                        v                  v                  v
 *                 +-------------+   +-------------+   +----------------+
 *                 | User Svc    |   | Order Svc   |   | Notification   |
 *                 +-------------+   +-------------+   +----------------+
 *                        |                  |                  |
 *                        +------------------+------------------+
 *                                           |
 *                                           v
 *                              Merged JSON Response -> Client
 *
 *  One client call -> Multiple service calls -> Single response.
 *
 *
 * ============================================================
 *  6. DIAGRAM - GATEWAY vs LOAD BALANCER vs REVERSE PROXY
 * ============================================================
 *
 *   Client --> [ Load Balancer ] --> Service A
 *                                    Service B
 *              (Only distributes traffic)
 *
 *   Client --> [ Reverse Proxy ] --> Backend Server
 *              (Distributes + SSL + caching)
 *
 *   Client --> [ API GATEWAY ] --> Service A
 *                                 Service B
 *                                 Service C
 *              (Distribution + Auth + Rate Limit +
 *               Aggregation + Transformation + Tracing)
 *
 *  Interview one-liner:
 *    "API Gateway = Reverse Proxy + Application-Aware Logic"
 *
 *
 * ============================================================
 *  7. DIAGRAM - API GATEWAY vs BFF (Backend For Frontend)
 * ============================================================
 *
 *   WITHOUT BFF:
 *     Mobile  ---+
 *     Web     ---+--> [ API GATEWAY ] --> Microservices
 *     Partner ---+
 *
 *   WITH BFF:
 *     Mobile  --> [ Mobile BFF  ] --+
 *     Web     --> [ Web BFF     ] --+--> Microservices
 *     Partner --> [ Partner BFF ] --+
 *
 *   Gateway = one for all clients.
 *   BFF     = one per client type (tailored responses).
 *
 *
 * ============================================================
 *  8. CORE RESPONSIBILITIES
 * ============================================================
 *
 *  Responsibility        | Description
 *  ----------------------|------------------------------------
 *  Routing               | Map path/method -> backend service
 *  Aggregation           | Combine multiple service responses
 *  Authentication        | Verify identity (JWT, OAuth2, API Key)
 *  Authorization         | Enforce RBAC / scopes
 *  Rate Limiting         | Prevent abuse (e.g., 100 req/min)
 *  Load Balancing        | Distribute across instances
 *  Caching               | Reduce backend load
 *  Transformation        | Modify headers/body/format
 *  Circuit Breaking      | Stop calling failing services
 *  Monitoring            | Metrics, logs, traces
 *
 *
 * ============================================================
 *  9. PROBLEMS IT SOLVES
 * ============================================================
 *
 *  Problem                              | Solution by Gateway
 *  -------------------------------------|-------------------------------
 *  Client must know all services        | Single endpoint exposure
 *  Multiple round trips (chatty)        | Request aggregation
 *  Auth in every service                | Centralized authentication
 *  Rate limit per service               | Global rate limiting
 *  SSL certs everywhere                 | SSL termination at gateway
 *  Protocol mismatch (REST vs gRPC)     | Protocol translation
 *  Service failures cascade             | Circuit breaker + fallback
 *  Hard to trace requests               | Centralized logging/tracing
 *  Versioning chaos                     | Route /v1, /v2 to services
 *
 *
 * ============================================================
 *  10. API GATEWAY vs LOAD BALANCER vs REVERSE PROXY
 * ============================================================
 *
 *  Feature                  | LB  | Reverse Proxy | API Gateway
 *  -------------------------|-----|---------------|-------------
 *  Distributes traffic      | YES |     YES       |    YES
 *  SSL termination          | YES |     YES       |    YES
 *  Auth                     | NO  |   Sometimes   |    YES
 *  Rate limiting            | NO  |   Sometimes   |    YES
 *  Request transformation   | NO  |     NO        |    YES
 *  Service aggregation      | NO  |     NO        |    YES
 *  Protocol translation     | NO  |     NO        |    YES
 *  Business logic awareness | NO  |     NO        |    YES
 *
 *
 * ============================================================
 *  11. COMMON DESIGN PATTERNS
 * ============================================================
 *
 *  Pattern                | Use Case
 *  -----------------------|-----------------------------------
 *  Gateway Routing        | /api/users/*  -> user-service
 *  Gateway Aggregation    | /dashboard    -> calls 3 services
 *  Gateway Offloading     | Move SSL, auth, cache to gateway
 *  Gateway Filtering      | Header injection, A/B testing
 *
 *
 * ============================================================
 *  12. POPULAR API GATEWAY TOOLS
 * ============================================================
 *
 *  Tool                     | Type          | Notes
 *  -------------------------|---------------|----------------------
 *  Kong                     | Open Source   | Plugin-based, Nginx
 *  NGINX / NGINX Plus       | OSS / Comm.   | High performance
 *  AWS API Gateway          | Managed       | Serverless, AWS native
 *  Azure API Management     | Managed       | Enterprise portal
 *  Google Apigee            | Managed       | Analytics-heavy
 *  Spring Cloud Gateway     | OSS (Java)    | Reactive, Spring eco
 *  Traefik                  | OSS           | Cloud-native
 *  Envoy                    | OSS           | Service mesh + gateway
 *  Istio Ingress Gateway    | OSS           | Kubernetes mesh
 *
 *  For Java / Spring Boot interviews:
 *      -> Spring Cloud Gateway (most asked)
 *      -> Netflix Zuul (legacy, deprecated)
 *
 *
 * ============================================================
 *  13. SPRING CLOUD GATEWAY - SNIPPET TO REMEMBER
 * ============================================================
 *
 *  @Configuration
 *  public class GatewayConfig {
 *
 *      @Bean
 *      public RouteLocator customRoutes(RouteLocatorBuilder builder) {
 *          return builder.routes()
 *              .route("user-service", r -> r
 *                  .path("/api/users/**")
 *                  .uri("lb://USER-SERVICE"))
 *              .route("order-service", r -> r
 *                  .path("/api/orders/**")
 *                  .uri("lb://ORDER-SERVICE"))
 *              .build();
 *      }
 *  }
 *
 *  Key Concepts:
 *      Route     : id + uri + predicates + filters
 *      Predicate : when to match (path, method, header)
 *      Filter    : modify request/response (auth, logging)
 *
 *
 * ============================================================
 *  14. SERVICE MESH + GATEWAY (Modern Architecture)
 * ============================================================
 *
 *   Internet
 *      |
 *      v
 *   +----------------+
 *   |  API GATEWAY   |   <-- North-South traffic
 *   +----------------+
 *      |
 *      v
 *   +--------------------------------------+
 *   |  SERVICE MESH (Istio / Linkerd)      |  <-- East-West traffic
 *   |                                      |
 *   |   [Svc A] <--> [Svc B] <--> [Svc C]  |
 *   +--------------------------------------+
 *
 *  Gateway handles external traffic.
 *  Service Mesh handles internal service-to-service traffic.
 *
 *
 * ============================================================
 *  15. WHEN NOT TO USE AN API GATEWAY
 * ============================================================
 *
 *  - Simple monolith (reverse proxy is enough)
 *  - Ultra low-latency systems (extra hop hurts)
 *  - Very small teams (ops overhead)
 *  - When it becomes a bottleneck without HA setup
 *
 *
 * ============================================================
 *  16. COMMON PITFALLS (Interview Trap Questions)
 * ============================================================
 *
 *  - Single Point of Failure    -> use HA / clustering
 *  - Bottleneck                 -> scale horizontally + cache
 *  - Fat Gateway (God Service)  -> NO business logic inside
 *  - Latency                    -> keep it lean
 *  - Config sprawl              -> GitOps, versioned config
 *  - Vendor lock-in             -> prefer OSS if possible
 *
 *
 * ============================================================
 *  17. TOP INTERVIEW QUESTIONS (Q & A)
 * ============================================================
 *
 *  Q1.  What is an API Gateway?
 *  A.   Single entry point for all client requests in a
 *       microservices architecture that handles routing and
 *       cross-cutting concerns.
 *
 *  Q2.  Why not call microservices directly?
 *  A.   Client complexity, auth duplication, chatty calls,
 *       protocol mismatch, hard to trace.
 *
 *  Q3.  Difference between API Gateway and Load Balancer?
 *  A.   LB only distributes traffic. Gateway adds auth,
 *       aggregation, transformation, rate limiting.
 *
 *  Q4.  Difference between API Gateway and BFF?
 *  A.   Gateway = one for all clients. BFF = one per client
 *       type (mobile / web / partner).
 *
 *  Q5.  How does API Gateway help security?
 *  A.   Central auth (JWT/OAuth2), rate limiting, hides
 *       internal topology, WAF, SSL termination.
 *
 *  Q6.  What is Gateway Aggregation?
 *  A.   Gateway calls multiple services in parallel and
 *       merges their responses into one.
 *
 *  Q7.  What is Circuit Breaker in Gateway?
 *  A.   Stop calling a failing service for a cooldown period
 *       and return a fallback response.
 *
 *  Q8.  What is the biggest risk with API Gateway?
 *  A.   Single Point of Failure -> must run HA + scale out.
 *
 *  Q9.  Can API Gateway contain business logic?
 *  A.   NO. Keep it thin - only routing + cross-cutting.
 *
 *  Q10. Which gateway for Spring Boot?
 *  A.   Spring Cloud Gateway (reactive, non-blocking).
 *       Zuul is legacy / deprecated.
 *
 *
 * ============================================================
 *  18. ONE-LINER SUMMARY (Say this in interview)
 * ============================================================
 *
 *  "API Gateway is the front door of microservices - it
 *   does not do the business work, but decides who enters,
 *   where they go, and how they are treated."
 *
 * ============================================================
 */
public class ApiGatewayQuickRevision {
    // Revision notes only - no runtime logic required.
}