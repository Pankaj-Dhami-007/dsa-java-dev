package com.dhami.java_dev.microservices;

/**
 * ============================================================
 *              LOAD BALANCER - QUICK REVISION
 * ============================================================
 *
 *  Author : Dhami
 *  Level  : 2 Years Experience
 *  Use    : Interview last-minute revision
 *
 * ============================================================
 *  1. WHAT IS A LOAD BALANCER?
 * ============================================================
 *
 *  A component that distributes incoming traffic across
 *  multiple servers to ensure:
 *      - No single server is overloaded
 *      - High availability
 *      - Horizontal scalability
 *
 *  One-liner:
 *      "A Load Balancer is a traffic cop for servers."
 *
 *
 * ============================================================
 *  2. DIAGRAM - BASIC
 * ============================================================
 *
 *                          +------------------+
 *                     +--> |   Server 1       |
 *                     |    +------------------+
 *                     |
 *   Client -------> [ LOAD BALANCER ]
 *                     |
 *                     |    +------------------+
 *                     +--> |   Server 2       |
 *                          +------------------+
 *
 *
 * ============================================================
 *  3. WHY WE USE IT? (PROBLEMS IT SOLVES)
 * ============================================================
 *
 *  Problem                          | Solution
 *  ---------------------------------|--------------------------------
 *  Server overload                  | Even traffic distribution
 *  Single point of failure          | Routes around dead servers
 *  Hard to scale horizontally       | Add servers transparently
 *  Uneven traffic                   | Smart algorithms
 *  Downtime during deploys          | Draining + rolling restart
 *  Client complexity                | One endpoint for clients
 *
 *
 * ============================================================
 *  4. L4 vs L7 LOAD BALANCER
 * ============================================================
 *
 *  Feature              | L4 LB           | L7 LB
 *  ---------------------|-----------------|-----------------
 *  OSI Layer            | Transport (4)   | Application (7)
 *  Sees                 | IP + Port       | Full HTTP data
 *  Routing by URL       | NO              | YES
 *  Routing by Header    | NO              | YES
 *  SSL Termination      | Limited         | YES
 *  Sticky Sessions      | IP-based        | Cookie-based
 *  Speed                | Very Fast       | Fast
 *  Protocol Support     | Any TCP/UDP     | HTTP/gRPC/WS
 *  Examples             | AWS NLB, HAProxy| Nginx, AWS ALB
 *
 *  Interview line:
 *      "L4 routes by IP+port. L7 routes by URL/headers.
 *       Microservices typically use L7."
 *
 *
 * ============================================================
 *  5. LOAD BALANCING ALGORITHMS
 * ============================================================
 *
 *  Algorithm               | State | Load Aware | Use Case
 *  ------------------------|-------|------------|-------------------
 *  Round Robin             | No    | No         | Equal servers
 *  Weighted Round Robin    | Yes   | No         | Mixed capacity
 *  Least Connections       | Yes   | Yes        | Variable req time
 *  Weighted Least Conns    | Yes   | Yes        | Mixed + variable
 *  IP Hash                 | Yes   | No         | Sticky sessions
 *  Consistent Hash         | Yes   | No         | Caching/sharding
 *  Random                  | No    | No         | Simple systems
 *  Least Response Time     | Yes   | Yes        | Perf-sensitive
 *
 *  Quick rules:
 *      - Stateless services      -> Round Robin / Least Conn
 *      - Different server specs  -> Weighted Round Robin
 *      - Long-lived connections  -> Least Connections
 *      - Session in memory       -> IP Hash
 *      - Distributed cache       -> Consistent Hash
 *
 *
 * ============================================================
 *  6. HEALTH CHECKS
 * ============================================================
 *
 *  LB probes each server to know if it is alive.
 *
 *  Types:
 *      Active  -> LB sends probes on schedule (GET /health)
 *      Passive -> LB watches real traffic for errors
 *      Hybrid  -> Both (best practice)
 *
 *  Server States:
 *      HEALTHY | UNHEALTHY | DRAINING | STARTING | OUT_OF_SERVICE
 *
 *  Key Parameters:
 *      interval, timeout, healthyThreshold,
 *      unhealthyThreshold, path
 *
 *  Spring Boot:
 *      GET /actuator/health
 *      -> { "status": "UP" }
 *
 *
 * ============================================================
 *  7. SPRING CLOUD GATEWAY + LOAD BALANCING
 * ============================================================
 *
 *  Spring Cloud Gateway does NOT balance by itself.
 *  It uses Spring Cloud LoadBalancer internally.
 *
 *      lb://user-service
 *          |
 *          v
 *      Spring Cloud LoadBalancer (uses Eureka/Consul)
 *          |
 *          v
 *      Picks one instance via Round Robin
 *
 *  So there are TWO levels of load balancing:
 *
 *      Level 1 (OUTSIDE) -> L7 LB (Nginx/ALB)
 *                          Distributes across Gateway instances
 *
 *      Level 2 (INSIDE)  -> Spring Cloud LoadBalancer
 *                          Distributes across Service instances
 *
 *
 * ------------------------------------------------------------
 *  DIAGRAM
 * ------------------------------------------------------------
 *
 *                    +------------------+
 *   Client ------->  |  L7 LB (Nginx)   |  <-- Gateway instances
 *                    +------------------+
 *                        |         |
 *                        v         v
 *                   +---------+  +---------+
 *                   | Gateway1|  | Gateway2|
 *                   +---------+  +---------+
 *                        |         |
 *                        +----+----+
 *                             |
 *                             v
 *                  +---------------------+
 *                  | Spring Cloud        |  <-- Services
 *                  | LoadBalancer        |
 *                  +---------------------+
 *                             |
 *              +--------------+--------------+
 *              v              v              v
 *         [User-I1]      [User-I2]      [Order-I1]
 *
 *
 * ============================================================
 *  8. FORWARD PROXY vs REVERSE PROXY
 * ============================================================
 *
 *  Forward Proxy:
 *      - Sits in FRONT of CLIENTS
 *      - Hides CLIENT identity
 *      - Example: VPN, Squid, corporate proxy
 *
 *  Reverse Proxy:
 *      - Sits in FRONT of SERVERS
 *      - Hides SERVER identity
 *      - Example: Nginx, HAProxy, Envoy
 *
 *  Simple Rule:
 *      Forward = Client-side
 *      Reverse = Server-side
 *
 *  Nginx in our diagram = REVERSE PROXY (acting as L7 LB)
 *
 *
 * ============================================================
 *  9. LOAD BALANCER vs API GATEWAY vs REVERSE PROXY
 * ============================================================
 *
 *  Feature                  | LB  | Rev Proxy | API Gateway
 *  -------------------------|-----|-----------|-------------
 *  Distributes traffic      | YES | YES       | YES
 *  SSL termination          | YES | YES       | YES
 *  Auth                     | NO  | Sometimes | YES
 *  Rate limiting            | NO  | Sometimes | YES
 *  Aggregation              | NO  | NO        | YES
 *  Transformation           | NO  | NO        | YES
 *  Protocol translation     | NO  | NO        | YES
 *  Business logic aware     | NO  | NO        | YES
 *
 *
 * ============================================================
 *  10. CLIENT-SIDE vs SERVER-SIDE LOAD BALANCING
 * ============================================================
 *
 *  Client-Side LB:
 *      - Client knows all instances
 *      - Client picks one (e.g., Spring Cloud LB, Ribbon)
 *      - Pros: No extra hop
 *      - Cons: Client must implement logic
 *
 *  Server-Side LB:
 *      - LB sits in front of servers
 *      - Client calls LB only (Nginx, AWS ALB)
 *      - Pros: Simple client
 *      - Cons: Extra hop
 *
 *
 * ============================================================
 *  11. INTERVIEW QUESTIONS (Q & A)
 * ============================================================
 *
 *  Q1. What is a Load Balancer?
 *  A.  A component that distributes traffic across multiple
 *      servers for availability and scalability.
 *
 *  Q2. L4 vs L7 - difference?
 *  A.  L4 routes by IP/port. L7 routes by URL/headers/
 *      content. L7 is smarter, L4 is faster.
 *
 *  Q3. Which algorithm is best?
 *  A.  Depends. Round Robin for stateless, Least Connections
 *      for variable load, IP Hash for sticky sessions.
 *
 *  Q4. What is a health check?
 *  A.  A periodic probe to check if a server is alive. Dead
 *      servers are removed from the pool.
 *
 *  Q5. Active vs Passive health check?
 *  A.  Active = LB probes on schedule. Passive = LB watches
 *      real traffic for errors. Production uses both.
 *
 *  Q6. Does Spring Cloud Gateway do load balancing?
 *  A.  Not by itself. It uses Spring Cloud LoadBalancer
 *      internally to pick a service instance.
 *
 *  Q7. If Gateway already balances services, why do we
 *      need an external LB?
 *  A.  External LB balances the GATEWAY instances for HA.
 *      Internal LB balances SERVICE instances.
 *      Two levels - different purposes.
 *
 *  Q8. What is a Reverse Proxy?
 *  A.  A proxy that sits in front of servers and hides
 *      server identity. Nginx, HAProxy, Envoy.
 *
 *  Q9. Forward vs Reverse Proxy?
 *  A.  Forward hides CLIENTS (VPN). Reverse hides SERVERS
 *      (Nginx).
 *
 *  Q10. What is Sticky Session?
 *  A.  Same client always routed to same server (via IP
 *      hash or cookie). Needed when session is stored
 *      in server memory. Avoid in microservices - use
 *      shared session store (Redis) instead.
 *
 *  Q11. What is Consistent Hashing?
 *  A.  Hashing technique where adding/removing a server
 *      remaps only a small fraction of keys. Used in
 *      distributed caches (Redis Cluster).
 *
 *  Q12. Common LB tools?
 *  A.  Nginx, HAProxy, Envoy, Traefik, AWS ALB/NLB,
 *      Spring Cloud LoadBalancer.
 *
 *
 * ============================================================
 *  12. GOLDEN LINES (SAY THESE IN INTERVIEW)
 * ============================================================
 *
 *  1. "A Load Balancer is a traffic cop for servers."
 *
 *  2. "L4 routes by IP+port, L7 routes by URL+headers.
 *      Microservices use L7."
 *
 *  3. "Spring Cloud Gateway uses Spring Cloud LoadBalancer
 *      internally - it does not balance by itself."
 *
 *  4. "Two levels of LB: external for Gateway instances,
 *      internal for Service instances."
 *
 *  5. "Always use health checks - active + passive -
 *      for zero-downtime deployments."
 *
 *
 * ============================================================
 *  END OF QUICK REVISION
 * ============================================================
 */
public class LoadBalancerQuickRevision {
    // Revision notes only - no runtime logic required.
}