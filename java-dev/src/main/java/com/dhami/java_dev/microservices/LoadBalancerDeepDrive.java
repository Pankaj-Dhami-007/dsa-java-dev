package com.dhami.java_dev.microservices;

// Load Balancer ka main kaam incoming requests ko multiple server instances ke beech distribute karna hai.
/**
 * ============================================================
 *              LOAD BALANCER - DEEP DIVE
 * ============================================================
 *
 *  Author : Dhami
 *  Topic  : Load Balancer (Deep Dive - not quick revision)
 *  Style  : Step by step, one concept at a time
 *
 * ============================================================
 *  STEP 1: WHAT IS A LOAD BALANCER?
 * ============================================================
 *
 *  A Load Balancer (LB) is a component that sits between
 *  clients and a group of servers, and distributes incoming
 *  requests across those servers so that:
 *
 *      - No single server is overwhelmed
 *      - Traffic is spread evenly
 *      - System stays available even if some servers fail
 *
 *  In simple words:
 *
 *      "A Load Balancer is a traffic cop for servers."
 *
 *  It decides WHICH server should handle THIS request.
 *
 *
 * ------------------------------------------------------------
 *  DIAGRAM - BASIC IDEA
 * ------------------------------------------------------------
 *
 *                          +------------------+
 *                     +--> |   Server 1       |
 *                     |    +------------------+
 *                     |
 *   Client -------> [ LOAD BALANCER ] --> +------------------+
 *                     |                   |   Server 2       |
 *                     |                   +------------------+
 *                     |
 *                     |    +------------------+
 *                     +--> |   Server 3       |
 *                          +------------------+
 *
 *  Client does NOT know how many servers exist.
 *  Client only talks to the Load Balancer.
 *  The LB decides the target server.
 *
 *
 * ------------------------------------------------------------
 *  WHY DOES THIS MATTER?
 * ------------------------------------------------------------
 *
 *  Without LB:
 *      - One server gets all traffic -> crashes
 *      - If server dies -> whole app down
 *      - Hard to scale (add servers manually)
 *
 *  With LB:
 *      - Traffic spread evenly
 *      - Dead server removed automatically
 *      - Add/remove servers without client changes
 *
 *
 * ------------------------------------------------------------
 *  KEY PROPERTIES
 * ------------------------------------------------------------
 *
 *  Property          | Meaning
 *  ------------------|-----------------------------------------
 *  Distribution      | Spreads requests across servers
 *  Health Checking   | Detects dead servers, stops sending traffic
 *  Scalability       | Add/remove servers transparently
 *  Availability      | Keeps app alive if a server fails
 *  Transparency      | Client sees ONE endpoint, not many
 *
 *
 * ============================================================
 *  END OF STEP 1
 * ============================================================
 */
public class LoadBalancerDeepDrive {
    // Step 1 - notes only, no logic yet.
}

/**
 * ============================================================
 *  STEP 2: WHY DO WE NEED A LOAD BALANCER?
 * ============================================================
 *
 *  Let us understand the problem FIRST, then the solution.
 *
 *
 * ------------------------------------------------------------
 *  2.1 THE PROBLEM - SINGLE SERVER
 * ------------------------------------------------------------
 *
 *  Imagine your app runs on ONE server:
 *
 *      Client(s) -----> [ SERVER ]
 *
 *  Problems:
 *
 *      1. Traffic grows -> server overloaded -> slow/crash
 *      2. Server dies   -> entire app goes DOWN
 *      3. Deploy new code -> downtime
 *      4. Cannot scale horizontally
 *
 *
 * ------------------------------------------------------------
 *  2.2 NAIVE FIX - ADD MORE SERVERS (NO LB)
 * ------------------------------------------------------------
 *
 *      Client ----> [ Server 1 ]
 *      Client ----> [ Server 2 ]
 *      Client ----> [ Server 3 ]
 *
 *  New problems:
 *
 *      1. Which server should the client call?
 *      2. Client must know ALL server addresses
 *      3. If one server dies, client keeps hitting it
 *      4. No even distribution -> some servers idle, some die
 *
 *
 * ------------------------------------------------------------
 *  2.3 PROPER FIX - ADD A LOAD BALANCER
 * ------------------------------------------------------------
 *
 *      Client ----> [ LOAD BALANCER ] ----> [ Server 1 ]
 *                                     ----> [ Server 2 ]
 *                                     ----> [ Server 3 ]
 *
 *  Now:
 *      - Client calls ONE address (the LB)
 *      - LB spreads traffic evenly
 *      - LB skips dead servers (health check)
 *      - Add/remove servers anytime
 *
 *
 * ------------------------------------------------------------
 *  2.4 PROBLEMS THE LOAD BALANCER SOLVES
 * ------------------------------------------------------------
 *
 *  Problem                          | How LB Solves It
 *  ---------------------------------|--------------------------------
 *  Server overload                  | Distributes traffic evenly
 *  Single point of failure          | Routes around dead servers
 *  Horizontal scaling difficulty    | Add servers transparently
 *  Uneven traffic distribution      | Algorithms (RR, Least Conn)
 *  Deployments with downtime        | Drain + rolling restart
 *  Client complexity                | One endpoint for clients
 *  Session stickiness (optional)    | Sticky sessions support
 *  Health monitoring                | Built-in health checks
 *
 *
 * ------------------------------------------------------------
 *  2.5 BENEFITS - ONE LINE EACH
 * ------------------------------------------------------------
 *
 *  - Scalability    : Add servers without touching clients
 *  - Availability   : Survives server failures
 *  - Performance    : Even load = faster response
 *  - Flexibility    : Swap/upgrade servers freely
 *  - Security       : Hides backend topology
 *  - Zero downtime  : Rolling deploys via draining
 *
 *
 * ------------------------------------------------------------
 *  2.6 REAL-WORLD ANALOGY
 * ------------------------------------------------------------
 *
 *  Think of a supermarket with 5 billing counters
 *  and one "queue manager" who sends each customer
 *  to the counter with the shortest line.
 *
 *      Customers = Requests
 *      Counters  = Servers
 *      Manager   = Load Balancer
 *
 *  If a counter closes, the manager stops sending
 *  customers there. That is exactly what an LB does.
 *
 *
 * ============================================================
 *  END OF STEP 2
 * ============================================================
 */


/**
 * ============================================================
 *  STEP 3: WHERE DOES A LOAD BALANCER SIT?
 *          (LAYER 4 vs LAYER 7)
 * ============================================================
 *
 *  Load Balancers operate at different layers of the OSI model.
 *  The two MOST important ones for interviews:
 *
 *      - L4 (Transport Layer)  -> TCP / UDP
 *      - L7 (Application Layer) -> HTTP / HTTPS / gRPC
 *
 *  Understanding this distinction is CRITICAL because it
 *  changes WHAT the LB can see and HOW it routes.
 *
 *
 * ------------------------------------------------------------
 *  3.1 LAYER 4 (L4) LOAD BALANCER - TRANSPORT LAYER
 * ------------------------------------------------------------
 *
 *  Works at:
 *      - TCP / UDP level
 *      - Sees only IP address + port
 *      - Does NOT look inside the packet payload
 *
 *  Routing decision based on:
 *      - Source IP
 *      - Destination IP
 *      - Port number
 *
 *  Example:
 *      Client IP: 10.0.0.5  ->  LB  ->  Server 3
 *      (LB did not read the HTTP URL or headers)
 *
 *
 *  DIAGRAM - L4
 *  ------------
 *
 *      Client
 *        |
 *        v
 *   [ L4 LB ]  <-- sees IP + Port only
 *        |
 *        +--> Server 1 (TCP)
 *        +--> Server 2 (TCP)
 *        +--> Server 3 (TCP)
 *
 *
 *  Pros:
 *      - Very FAST (no packet inspection)
 *      - Low latency
 *      - Handles ANY protocol (TCP/UDP)
 *      - Simple to configure
 *
 *  Cons:
 *      - Cannot route by URL / path / header
 *      - No HTTP-level features (cookies, sticky sessions
 *        based on URL, content-based routing)
 *      - Cannot terminate SSL intelligently
 *
 *
 * ------------------------------------------------------------
 *  3.2 LAYER 7 (L7) LOAD BALANCER - APPLICATION LAYER
 * ------------------------------------------------------------
 *
 *  Works at:
 *      - HTTP / HTTPS / gRPC / WebSocket level
 *      - Reads the FULL request (URL, headers, cookies, body)
 *
 *  Routing decision based on:
 *      - URL path         (/api/users vs /api/orders)
 *      - Host header      (api.example.com vs web.example.com)
 *      - HTTP method      (GET / POST)
 *      - Cookies          (sticky sessions)
 *      - Headers          (custom routing rules)
 *
 *
 *  DIAGRAM - L7
 *  ------------
 *
 *      Client
 *        |
 *        v
 *   [ L7 LB ]  <-- sees full HTTP request
 *        |
 *        +-- /api/users/*   --> User Service
 *        +-- /api/orders/*  --> Order Service
 *        +-- /api/payments/*--> Payment Service
 *
 *
 *  Pros:
 *      - Smart routing (path, host, header-based)
 *      - SSL termination
 *      - Sticky sessions via cookies
 *      - Can modify requests/responses
 *      - Better observability (logs full request)
 *      - Supports WebSocket, gRPC
 *
 *  Cons:
 *      - Slower than L4 (packet inspection)
 *      - Higher CPU usage
 *      - More complex configuration
 *
 *
 * ------------------------------------------------------------
 *  3.3 L4 vs L7 - COMPARISON TABLE
 * ------------------------------------------------------------
 *
 *  Feature                | L4 LB           | L7 LB
 *  -----------------------|-----------------|-----------------
 *  OSI Layer              | Transport (4)   | Application (7)
 *  Sees                   | IP + Port       | Full HTTP data
 *  Routing by URL         | NO              | YES
 *  Routing by Header      | NO              | YES
 *  SSL Termination        | Limited         | YES
 *  Sticky Sessions        | IP-based        | Cookie-based
 *  Speed                  | Very Fast       | Fast
 *  CPU Usage              | Low             | Higher
 *  Protocol Support       | Any TCP/UDP     | HTTP/gRPC/WS
 *  Use Case               | Raw throughput  | Microservices
 *
 *
 * ------------------------------------------------------------
 *  3.4 WHICH ONE SHOULD YOU USE?
 * ------------------------------------------------------------
 *
 *  Use L4 when:
 *      - You need maximum performance
 *      - You do not need content-based routing
 *      - You handle non-HTTP protocols (DB, MQTT, etc.)
 *      - Example: AWS NLB, HAProxy (TCP mode)
 *
 *  Use L7 when:
 *      - You are building microservices
 *      - You need path/host-based routing
 *      - You want SSL termination at the LB
 *      - Example: NGINX, AWS ALB, Traefik, Envoy
 *
 *
 * ------------------------------------------------------------
 *  3.5 REAL-WORLD EXAMPLES
 * ------------------------------------------------------------
 *
 *  AWS:
 *      NLB  = Network Load Balancer  -> L4
 *      ALB  = Application LB         -> L7
 *      CLB  = Classic LB             -> L4 + L7 (legacy)
 *
 *  Software:
 *      HAProxy     -> Supports both L4 and L7
 *      NGINX       -> Primarily L7
 *      Envoy       -> L7 (also L4)
 *      Traefik     -> L7 (Kubernetes-native)
 *
 *
 * ------------------------------------------------------------
 *  3.6 INTERVIEW ONE-LINER
 * ------------------------------------------------------------
 *
 *  "L4 LB routes by IP and port - fast but blind.
 *   L7 LB routes by URL, headers, and content - smarter
 *   but slightly slower. Microservices typically use L7."
 *
 *
 * ============================================================
 *  END OF STEP 3
 * ============================================================
 */

/*

DIAGRAM 1 — ONE API GATEWAY + MULTIPLE SERVICES + INSTANCES + L7 LOAD BALANCER

┌──────────────┐      HTTP/HTTPS       ┌──────────────────┐      ┌─────────────────┐
│    CLIENT    │ ───────────────────► │ L7 LOAD BALANCER │ ───► │   API GATEWAY   │
│ Flutter/Web  │                       │                  │      │    Instance     │
└──────────────┘                       └──────────────────┘      └────────┬────────┘
                                                                          │
                                      ┌───────────────────────────────────┼────────────────────────┐
                                      │                                   │                        │
                                      ▼                                   ▼                        ▼
                             ┌─────────────────┐                  ┌─────────────────┐      ┌─────────────────┐
                             │   AUTH SERVICE  │                  │ EMPLOYEE SERVICE│      │  LEAVE SERVICE  │
                             └────────┬────────┘                  └────────┬────────┘      └────────┬────────┘
                                      │                                   │                        │
                         ┌────────────┼────────────┐         ┌────────────┼────────────┐  ┌────────┼─────────┐
                         │            │            │         │            │            │  │        │         │
                         ▼            ▼            ▼         ▼            ▼            ▼  ▼        ▼         ▼
                      ┌─────┐      ┌─────┐      ┌─────┐   ┌─────┐      ┌─────┐      ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐
                      │ A1  │      │ A2  │      │ A3  │   │ E1  │      │ E2  │      │ E3  │ │ L1  │ │ L2  │ │ L3  │
                      └─────┘      └─────┘      └─────┘   └─────┘      └─────┘      └─────┘ └─────┘ └─────┘ └─────┘



DIAGRAM 2 — MULTIPLE API GATEWAYS + MULTIPLE SERVICES + INSTANCES + L7 LOAD BALANCER

┌──────────────┐      HTTP/HTTPS       ┌──────────────────┐
│    CLIENT    │ ───────────────────► │ L7 LOAD BALANCER │
│ Flutter/Web  │                       │                  │
└──────────────┘                       └────────┬─────────┘
                                               │
                              ┌────────────────┴────────────────┐
                              │                                 │
                              ▼                                 ▼
                     ┌─────────────────┐               ┌─────────────────┐
                     │   API GATEWAY   │               │   API GATEWAY   │
                     │   Instance #1   │               │   Instance #2   │
                     └────────┬────────┘               └────────┬────────┘
                              │                                 │
                              └──────────────┬──────────────────┘
                                             │
                    ┌────────────────────────┼────────────────────────┐
                    │                        │                        │
                    ▼                        ▼                        ▼
             ┌──────────────┐        ┌────────────────┐       ┌──────────────┐
             │ AUTH SERVICE │        │EMPLOYEE SERVICE│       │ LEAVE SERVICE│
             └──────┬───────┘        └───────┬────────┘       └──────┬───────┘
                    │                        │                        │
             ┌──────┼──────┐          ┌──────┼──────┐          ┌──────┼──────┐
             │      │      │          │      │      │          │      │      │
             ▼      ▼      ▼          ▼      ▼      ▼          ▼      ▼      ▼
           ┌────┐ ┌────┐ ┌────┐     ┌────┐ ┌────┐ ┌────┐     ┌────┐ ┌────┐ ┌────┐
           │ A1 │ │ A2 │ │ A3 │     │ E1 │ │ E2 │ │ E3 │     │ L1 │ │ L2 │ │ L3 │
           └────┘ └────┘ └────┘     └────┘ └────┘ └────┘     └────┘ └────┘ └────┘
 */

/* ============================================================
 *   SPRING CLOUD GATEWAY - LOAD BALANCING AT TWO LEVELS
 * ============================================================
 *
 *                    +------------------+
 *   Client ------->  |  L7 LB (Nginx)   |   <-- Gateway instances distribute
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
 *                  | Spring Cloud        |   <-- Services distribute
 *                  | LoadBalancer        |
 *                  +---------------------+
 *                             |
 *              +--------------+--------------+
 *              v              v              v
 *         [User-Svc-I1]  [User-Svc-I2]  [Order-Svc-I1]
 *
 *
 * ------------------------------------------------------------
 *  WHY TWO LOAD BALANCERS?
 * ------------------------------------------------------------
 *
 *  Level 1 (OUTSIDE - Nginx L7 LB):
 *      - Distributes traffic across GATEWAY instances
 *      - Reason: Gateway itself can crash -> need HA
 *      - Client sees ONE address (the LB)
 *
 *  Level 2 (INSIDE - Spring Cloud LoadBalancer):
 *      - Distributes traffic across SERVICE instances
 *      - Happens inside the Gateway
 *      - Uses service discovery (Eureka/Nacos/Consul)
 *
 *
 * ------------------------------------------------------------
 *  WHY NGINX HERE? (and what is it - reverse or forward?)
 * ------------------------------------------------------------
 *
 *  Nginx in this diagram = REVERSE PROXY (not forward proxy)
 *
 *  Forward Proxy:
 *      - Sits in front of CLIENTS
 *      - Hides client identity from servers
 *      - Example: VPN, corporate proxy, Squid
 *
 *      Client --> [Forward Proxy] --> Internet/Server
 *
 *  Reverse Proxy:
 *      - Sits in front of SERVERS
 *      - Hides server identity from clients
 *      - Example: Nginx, HAProxy, Envoy
 *
 *      Client --> [Reverse Proxy] --> Server(s)
 *
 *  In our diagram, Nginx is in FRONT of the Gateways,
 *  so it is a REVERSE PROXY acting as an L7 Load Balancer.
 *
 *
 * ------------------------------------------------------------
 *  WHY NGINX SPECIFICALLY?
 * ------------------------------------------------------------
 *
 *  - Battle-tested, production-grade
 *  - Handles L7 (HTTP) + L4 (TCP)
 *  - SSL termination
 *  - Health checks
 *  - Rate limiting, caching
 *  - Lightweight, high performance
 *
 *  Alternatives:
 *      AWS ALB, HAProxy, Traefik, Envoy, Cloudflare LB
 *
 *
 * ------------------------------------------------------------
 *  INTERVIEW ONE-LINER
 * ------------------------------------------------------------
 *
 *  "Spring Cloud Gateway uses Spring Cloud LoadBalancer
 *   internally for service instances. A separate L7 reverse
 *   proxy like Nginx sits in front to distribute traffic
 *   across multiple Gateway instances for high availability."
 *
 * ============================================================
 */

/**
 * ============================================================
 *  STEP 4: LOAD BALANCING ALGORITHMS
 * ============================================================
 *
 *  A Load Balancer needs a RULE to decide which server
 *  handles the next request. That rule is called an
 *  ALGORITHM.
 *
 *  Different algorithms suit different situations.
 *
 * ============================================================
 *  4.1 ROUND ROBIN
 * ============================================================
 *
 *  Requests are sent to servers in a FIXED ORDER, one by one,
 *  then start over from the beginning.
 *
 *  DIAGRAM
 *  -------
 *
 *   Req1 --> Server 1
 *   Req2 --> Server 2
 *   Req3 --> Server 3
 *   Req4 --> Server 1
 *   Req5 --> Server 2
 *   Req6 --> Server 3
 *
 *  Pros:
 *      - Simplest to implement
 *      - Even distribution if all servers are equal
 *      - Zero overhead
 *
 *  Cons:
 *      - Ignores server load
 *      - If one server is slow, it still gets traffic
 *      - Assumes all servers are identical
 *
 *  Use When:
 *      - All servers have equal capacity
 *      - Requests take similar time
 *
 * ============================================================
 *  4.2 WEIGHTED ROUND ROBIN
 * ============================================================
 *
 *  Like Round Robin, but each server gets a WEIGHT.
 *  Higher weight = more requests.
 *
 *  DIAGRAM
 *  -------
 *
 *   Server 1 (weight 3) --> gets 3 out of 6 requests
 *   Server 2 (weight 2) --> gets 2 out of 6 requests
 *   Server 3 (weight 1) --> gets 1 out of 6 requests
 *
 *   Req1 --> Server 1
 *   Req2 --> Server 1
 *   Req3 --> Server 1
 *   Req4 --> Server 2
 *   Req5 --> Server 2
 *   Req6 --> Server 3
 *
 *  Pros:
 *      - Handles servers with different capacities
 *      - Useful during rolling upgrades (drain old servers)
 *
 *  Cons:
 *      - Weights must be tuned manually
 *      - Still ignores real-time load
 *
 *  Use When:
 *      - Servers have different CPU/RAM
 *      - Gradual rollout / canary deployment
 *
 * ============================================================
 *  4.3 LEAST CONNECTIONS
 * ============================================================
 *
 *  Sends the next request to the server with the FEWEST
 *  active connections right now.
 *
 *  DIAGRAM
 *  -------
 *
 *   Before request:
 *      Server 1 -> 10 active connections
 *      Server 2 ->  3 active connections   <-- least
 *      Server 3 ->  7 active connections
 *
 *   Next request goes to Server 2.
 *
 *  Pros:
 *      - Adapts to real-time load
 *      - Great when requests vary in duration
 *      - Handles slow servers naturally
 *
 *  Cons:
 *      - Slightly more tracking overhead
 *      - New servers may get flooded first
 *
 *  Use When:
 *      - Request durations vary a lot
 *      - Long-lived connections (WebSocket, DB)
 *
 * ============================================================
 *  4.4 WEIGHTED LEAST CONNECTIONS
 * ============================================================
 *
 *  Combines weights + least connections.
 *  Formula: connections / weight -> pick smallest
 *
 *  DIAGRAM
 *  -------
 *
 *   Server 1 (weight 3) -> 6 conns -> 6/3 = 2.0
 *   Server 2 (weight 1) -> 1 conn  -> 1/1 = 1.0  <-- least
 *   Server 3 (weight 2) -> 4 conns -> 4/2 = 2.0
 *
 *   Next request goes to Server 2.
 *
 *  Use When:
 *      - Both capacity AND load matter
 *
 * ============================================================
 *  4.5 IP HASH (STICKY SESSIONS)
 * ============================================================
 *
 *  Uses a hash of the client IP to decide the server.
 *  Same client IP -> same server (as long as server list is same).
 *
 *  DIAGRAM
 *  -------
 *
 *   hash(clientIP) % numServers = index
 *
 *   Client A (IP 1.1.1.1) --> Server 1
 *   Client B (IP 2.2.2.2) --> Server 3
 *   Client C (IP 3.3.3.3) --> Server 2
 *   Client A again        --> Server 1  (sticky)
 *
 *  Pros:
 *      - Session stickiness without cookies
 *      - Good for caching (same client -> same cache)
 *
 *  Cons:
 *      - Uneven if some IPs generate more traffic
 *      - If server dies, its clients get remapped
 *
 *  Use When:
 *      - App stores session in server memory
 *      - Cache affinity matters
 *
 * ============================================================
 *  4.6 URL HASH / CONSISTENT HASHING
 * ============================================================
 *
 *  Hash the URL (or a key) to pick the server.
 *  Used heavily in caching and sharding.
 *
 *  DIAGRAM
 *  -------
 *
 *   hash("/api/users")  --> Server 1 (always)
 *   hash("/api/orders") --> Server 2 (always)
 *
 *  Consistent Hashing is a variant where adding/removing
 *  servers only remaps a small fraction of keys.
 *
 *  Use When:
 *      - Distributed cache (Redis cluster)
 *      - Same resource should hit same server
 *
 * ============================================================
 *  4.7 RANDOM
 * ============================================================
 *
 *  Pick a server randomly.
 *
 *  Pros:
 *      - Very simple
 *      - No state needed
 *
 *  Cons:
 *      - Can be uneven in short bursts
 *
 *  Use When:
 *      - Simple systems with many servers
 *
 * ============================================================
 *  4.8 LEAST RESPONSE TIME
 * ============================================================
 *
 *  Send to server with the FASTEST average response time.
 *
 *  Pros:
 *      - Adapts to slow servers
 *
 *  Cons:
 *      - Needs monitoring/feedback loop
 *
 *  Use When:
 *      - Real-time performance matters more than equality
 *
 * ============================================================
 *  ALGORITHM COMPARISON TABLE
 * ============================================================
 *
 *  Algorithm               | State | Load Aware | Use Case
 *  ------------------------|-------|------------|-------------------
 *  Round Robin             | No    | No         | Equal servers
 *  Weighted Round Robin    | Yes   | No         | Mixed capacity
 *  Least Connections       | Yes   | Yes        | Variable req time
 *  Weighted Least Conns    | Yes   | Yes        | Mixed + variable
 *  IP Hash                 | Yes   | No         | Sticky sessions
 *  URL / Consistent Hash   | Yes   | No         | Caching, sharding
 *  Random                  | No    | No         | Simple systems
 *  Least Response Time     | Yes   | Yes        | Perf-sensitive
 *
 * ============================================================
 *  WHICH ONE TO USE?
 * ============================================================
 *
 *  - Stateless services              -> Round Robin / Least Conn
 *  - Servers with different specs    -> Weighted Round Robin
 *  - Long-lived connections          -> Least Connections
 *  - Session in memory               -> IP Hash
 *  - Distributed cache               -> Consistent Hash
 *  - Simplest possible               -> Random
 *
 * ============================================================
 *  INTERVIEW ONE-LINER
 * ============================================================
 *
 *  "Round Robin is the default for stateless services.
 *   Least Connections adapts to real-time load.
 *   IP Hash provides sticky sessions.
 *   Consistent Hashing is used for cache affinity."
 *
 * ============================================================
 *  END OF STEP 4
 * ============================================================
 */


/**
 * ============================================================
 *  STEP 5: HEALTH CHECKS
 * ============================================================
 *
 *  A Load Balancer's smartness depends on ONE thing:
 *  "Does it know which servers are ALIVE?"
 *
 *  If the LB keeps sending traffic to a dead server,
 *  the whole point of load balancing is lost.
 *
 *  That is where HEALTH CHECKS come in.
 *
 *
 * ============================================================
 *  5.1 WHAT IS A HEALTH CHECK?
 * ============================================================
 *
 *  A Health Check is a periodic probe the Load Balancer
 *  sends to each backend server to verify it is alive
 *  and ready to serve traffic.
 *
 *  If the probe SUCCEEDS -> server stays in the pool
 *  If the probe FAILS    -> server is removed from the pool
 *
 *
 * ------------------------------------------------------------
 *  DIAGRAM - HEALTH CHECK FLOW
 * ------------------------------------------------------------
 *
 *                     +------------------+
 *   Load Balancer --> |  GET /health     | --> Server 1  (200 OK)  [HEALTHY]
 *                     +------------------+
 *
 *                     +------------------+
 *   Load Balancer --> |  GET /health     | --> Server 2  (500)     [UNHEALTHY]
 *                     +------------------+
 *                          |
 *                          v
 *                 Server 2 REMOVED from pool
 *
 *                     +------------------+
 *   Load Balancer --> |  GET /health     | --> Server 3  (200 OK)  [HEALTHY]
 *                     +------------------+
 *
 *   Traffic now goes only to Server 1 and Server 3.
 *
 *
 * ============================================================
 *  5.2 TYPES OF HEALTH CHECKS
 * ============================================================
 *
 *  (A) ACTIVE HEALTH CHECK
 *  ------------------------
 *      - LB sends probes on its own schedule
 *      - Example: every 5 seconds, GET /health
 *      - Pros: Fast detection
 *      - Cons: Extra traffic, needs an endpoint
 *
 *      DIAGRAM
 *      -------
 *      LB ---> Server  (every 5s)  "Are you alive?"
 *
 *
 *  (B) PASSIVE HEALTH CHECK
 *  -------------------------
 *      - LB observes REAL traffic
 *      - If server returns too many 5xx errors -> mark dead
 *      - Pros: No extra traffic
 *      - Cons: Slower detection (needs real requests)
 *
 *      DIAGRAM
 *      -------
 *      Client -> LB -> Server (500 error)  x N times
 *                      LB marks server as DEAD
 *
 *
 *  (C) HYBRID (BEST PRACTICE)
 *  --------------------------
 *      - Active + Passive together
 *      - Real-world production setups use this
 *
 *
 * ============================================================
 *  5.3 HEALTH CHECK STATES
 * ============================================================
 *
 *  A server can be in one of these states:
 *
 *  State         | Meaning
 *  --------------|-----------------------------------------
 *  HEALTHY       | Receives traffic normally
 *  UNHEALTHY     | Failed checks, removed from pool
 *  DRAINING      | Finishing ongoing requests, no new ones
 *  STARTING      | Warmed up but not yet receiving traffic
 *  OUT_OF_SERVICE| Manually removed by admin
 *
 *
 * ============================================================
 *  5.4 KEY CONFIGURATION PARAMETERS
 * ============================================================
 *
 *  Parameter              | Meaning
 *  -----------------------|------------------------------------
 *  Interval               | How often to check (e.g., 5s)
 *  Timeout                | Max wait for response (e.g., 2s)
 *  Healthy Threshold      | Successes needed to mark HEALTHY
 *  Unhealthy Threshold    | Failures needed to mark UNHEALTHY
 *  Path                   | Endpoint to probe (/health, /actuator/health)
 *  Protocol               | HTTP / HTTPS / TCP / gRPC
 *
 *
 *  EXAMPLE:
 *
 *      interval:           5s
 *      timeout:            2s
 *      healthyThreshold:   2
 *      unhealthyThreshold: 3
 *      path:               /health
 *
 *      -> Probe every 5s
 *      -> If it fails 3 times in a row, mark UNHEALTHY
 *
 *
 * ============================================================
 *  5.5 WHAT SHOULD A /health ENDPOINT CHECK?
 * ============================================================
 *
 *  A health endpoint should verify the server is actually
 *  ready to serve - not just "I am running".
 *
 *  Good checks:
 *      - Database connection alive?
 *      - Cache (Redis) reachable?
 *      - Disk space OK?
 *      - Downstream critical service reachable?
 *
 *  Spring Boot Actuator provides this out-of-the-box:
 *
 *      GET /actuator/health
 *      -> { "status": "UP" }
 *
 *  You can configure what it checks in application.yml:
 *
 *      management:
 *        endpoints:
 *          web:
 *            exposure:
 *              include: health
 *        endpoint:
 *          health:
 *            show-details: always
 *
 *
 * ============================================================
 *  5.6 L4 HEALTH CHECK vs L7 HEALTH CHECK
 * ============================================================
 *
 *  Type  | Method             | What it verifies
 *  ------|--------------------|------------------------------
 *  L4    | TCP handshake      | Port is open, server reachable
 *  L7    | HTTP GET /health   | App is actually healthy
 *
 *  L4 check is faster but shallow.
 *  L7 check is slower but smarter.
 *
 *  Best practice: Use L7 HTTP health checks when possible.
 *
 *
 * ============================================================
 *  5.7 HEALTH CHECKS IN SPRING CLOUD
 * ============================================================
 *
 *  Spring Cloud LoadBalancer + Eureka:
 *
 *      - Each service registers itself to Eureka
 *      - Sends heartbeat every 30s (default)
 *      - If heartbeat missing for 90s -> removed
 *
 *      application.yml:
 *
 *          eureka:
 *            client:
 *              serviceUrl:
 *                defaultZone: http://localhost:8761/eureka/
 *            instance:
 *              lease-renewal-interval-in-seconds: 30
 *              lease-expiration-duration-in-seconds: 90
 *
 *  Spring Boot Actuator /actuator/health is used by the LB.
 *
 *
 * ============================================================
 *  5.8 WHY HEALTH CHECKS MATTER
 * ============================================================
 *
 *  Without health checks:
 *      - LB keeps sending to dead servers
 *      - Users see errors / timeouts
 *      - Retry storms
 *
 *  With health checks:
 *      - Dead servers removed automatically
 *      - Traffic flows only to healthy nodes
 *      - Zero-downtime deployments
 *      - Graceful scaling up/down
 *
 *
 * ============================================================
 *  5.9 INTERVIEW ONE-LINER
 * ============================================================
 *
 *  "Health checks let the Load Balancer know which servers
 *   are alive. Active checks probe on a schedule; passive
 *   checks watch real traffic. Together they enable
 *   zero-downtime deployments and automatic failover."
 *
 * ============================================================
 *  END OF STEP 5
 * ============================================================
 */


/**
 * ============================================================
 *  STEP 6: CLIENT-SIDE vs SERVER-SIDE LOAD BALANCING
 * ============================================================
 *
 *  This is one of the MOST CONFUSING topics for beginners
 *  because the word "client" does NOT mean end-user here.
 *
 *  Let us clear that confusion once and for all.
 *
 *
 * ============================================================
 *  6.1 THE ROOT CONFUSION - WHAT DOES "CLIENT" MEAN?
 * ============================================================
 *
 *  In "Client-Side LB", the word CLIENT does NOT mean
 *  the end-user (browser / mobile app).
 *
 *  CLIENT = the application that is SENDING the request.
 *
 *  Whoever sends a request is a CLIENT for that call.
 *  Whoever receives it is a SERVER.
 *
 *  The SAME component can be both:
 *
 *      Browser --> Gateway       : Gateway = SERVER
 *      Gateway --> User Service  : Gateway = CLIENT
 *
 *  Role depends on CONTEXT, not on the machine.
 *
 *
 * ------------------------------------------------------------
 *  DIAGRAM - WHO IS CLIENT, WHO IS SERVER?
 * ------------------------------------------------------------
 *
 *   Browser --> [Gateway] --> [User Service]
 *
 *   Call 1: Browser --> Gateway
 *           Browser = CLIENT
 *           Gateway = SERVER
 *
 *   Call 2: Gateway --> User Service
 *           Gateway       = CLIENT
 *           User Service  = SERVER
 *
 *  Same Gateway. Different role. Depends on the call.
 *
 *
 * ============================================================
 *  6.2 WHAT IS CLIENT-SIDE LOAD BALANCING?
 * ============================================================
 *
 *  The CALLER (client) itself:
 *      - Knows all service instances
 *      - Picks one using an algorithm
 *      - Sends the request DIRECTLY to that instance
 *
 *  No intermediate load balancer exists.
 *
 *
 * ------------------------------------------------------------
 *  DIAGRAM - CLIENT-SIDE LB
 * ------------------------------------------------------------
 *
 *   Gateway (client)
 *        |
 *        |  Gateway itself has the list:
 *        |     [User-I1, User-I2, User-I3]
 *        |
 *        |  Gateway applies Round Robin locally
 *        |
 *        +-----> [User-I1]
 *        +-----> [User-I2]
 *        +-----> [User-I3]
 *
 *  Decision made at: CLIENT (Gateway) side
 *  Extra hop      : NONE
 *  LB component   : NONE (logic is inside the client)
 *
 *
 * ============================================================
 *  6.3 WHAT IS SERVER-SIDE LOAD BALANCING?
 * ============================================================
 *
 *  A dedicated component (LB) sits in front of the servers.
 *  The caller sends request to the LB. LB decides which
 *  server handles it.
 *
 *  The caller does NOT know how many servers exist.
 *
 *
 * ------------------------------------------------------------
 *  DIAGRAM - SERVER-SIDE LB
 * ------------------------------------------------------------
 *
 *   Order Service (caller)
 *        |
 *        v
 *   +--------------------+
 *   |   LOAD BALANCER    |   <-- Decision happens HERE
 *   +--------------------+
 *        |
 *        +-----> [User-I1]
 *        +-----> [User-I2]
 *        +-----> [User-I3]
 *
 *  Decision made at: SERVER side (the LB)
 *  Extra hop      : YES (through LB)
 *  LB component   : DEDICATED (Nginx, ALB, HAProxy)
 *
 *
 * ============================================================
 *  6.4 SIDE-BY-SIDE COMPARISON
 * ============================================================
 *
 *  Aspect                  | Client-Side LB      | Server-Side LB
 *  ------------------------|---------------------|-------------------
 *  Who decides?            | The caller          | A dedicated LB
 *  Extra hop?              | NO                  | YES
 *  Bottleneck risk?        | NO                  | Possible
 *  Client complexity       | High                | Low
 *  Service discovery       | At client          | At LB
 *  Algorithms              | Inside client lib   | Inside LB config
 *  Examples                | Spring Cloud LB,    | Nginx, HAProxy,
 *                          | Ribbon, gRPC LB     | AWS ALB/NLB
 *  Best for                | Internal services   | External traffic
 *
 *
 * ============================================================
 *  6.5 HOW SPRING CLOUD GATEWAY DOES CLIENT-SIDE LB
 * ============================================================
 *
 *  When you write:
 *
 *      .uri("lb://USER-SERVICE")
 *
 *  The "lb://" prefix means:
 *      "Use Client-Side Load Balancing for USER-SERVICE"
 *
 *  What happens internally:
 *
 *      1. Gateway sees "lb://USER-SERVICE"
 *      2. Gateway asks Spring Cloud LoadBalancer:
 *             "Give me instances of USER-SERVICE"
 *      3. LoadBalancer queries Eureka / Consul / Nacos:
 *             [10.0.0.1:8081, 10.0.0.2:8081, 10.0.0.3:8081]
 *      4. LoadBalancer applies Round Robin (default)
 *             -> picks 10.0.0.2:8081
 *      5. Gateway sends request DIRECTLY to 10.0.0.2:8081
 *
 *  No intermediate LB exists.
 *  Gateway itself acts as the "client" that balances.
 *
 *
 * ------------------------------------------------------------
 *  DIAGRAM - SPRING CLOUD GATEWAY CLIENT-SIDE LB
 * ------------------------------------------------------------
 *
 *   Client
 *      |
 *      v
 *   [Gateway]
 *      |
 *      |  "lb://USER-SERVICE"
 *      v
 *   [Spring Cloud LoadBalancer]
 *      |
 *      |  Asks Eureka:
 *      |     -> [I1, I2, I3]
 *      |
 *      |  Round Robin: picks I2
 *      v
 *   [User-I2]   <-- Direct call. No LB in between.
 *
 *
 * ============================================================
 *  6.6 THE TWO LEVELS OF LOAD BALANCING (RECAP)
 * ============================================================
 *
 *  In a real Spring Cloud microservices setup, BOTH levels
 *  exist simultaneously:
 *
 *      Level 1 (OUTSIDE): SERVER-SIDE LB
 *          - Nginx / AWS ALB
 *          - Distributes across GATEWAY instances
 *          - Purpose: Gateway HA
 *
 *      Level 2 (INSIDE): CLIENT-SIDE LB
 *          - Spring Cloud LoadBalancer
 *          - Distributes across SERVICE instances
 *          - Purpose: Service scaling
 *
 *
 * ------------------------------------------------------------
 *  DIAGRAM - BOTH LEVELS TOGETHER
 * ------------------------------------------------------------
 *
 *                     +------------------+
 *    Client ------->  |  L7 LB (Nginx)   |   <-- SERVER-SIDE LB
 *                     +------------------+
 *                         |         |
 *                         v         v
 *                    +---------+  +---------+
 *                    |Gateway1 |  |Gateway2 |
 *                    +---------+  +---------+
 *                         |         |
 *                         +----+----+
 *                              |
 *                              v
 *                   +---------------------+
 *                   | Spring Cloud        |   <-- CLIENT-SIDE LB
 *                   | LoadBalancer        |
 *                   +---------------------+
 *                              |
 *              +---------------+---------------+
 *              v               v               v
 *         [User-I1]       [User-I2]       [Order-I1]
 *
 *    Level 1 balances Gateway instances (server-side).
 *    Level 2 balances Service instances (client-side).
 *
 *
 * ============================================================
 *  6.7 WHY DOES END-USER NOT KNOW ANY OF THIS?
 * ============================================================
 *
 *  The end-user (browser / mobile) only knows:
 *      - The Gateway's public URL
 *
 *  The end-user does NOT know:
 *      - How many Gateway instances exist
 *      - How many services exist
 *      - How many instances per service
 *      - Which algorithm is used
 *      - Whether client-side or server-side LB is used
 *
 *  All of this is INTERNAL BACKEND MAGIC.
 *  End-user just sends a request and receives a response.
 *
 *
 * ============================================================
 *  6.8 WHEN TO USE WHICH?
 * ============================================================
 *
 *  Use CLIENT-SIDE LB when:
 *      - Service-to-service internal calls
 *      - You want to avoid an extra hop
 *      - You want zero LB bottleneck
 *      - Example: Spring Cloud, gRPC, Istio sidecar
 *
 *  Use SERVER-SIDE LB when:
 *      - Traffic comes from EXTERNAL clients
 *      - You want centralized control
 *      - You need SSL termination and WAF
 *      - Example: Nginx, AWS ALB, HAProxy
 *
 *
 * ============================================================
 *  6.9 INTERVIEW ONE-LINERS
 * ============================================================
 *
 *  1. "'Client' in Client-Side LB does NOT mean end-user.
 *      It means the caller application."
 *
 *  2. "The same Gateway can be a server (to browser) and a
 *      client (to User Service) - role depends on context."
 *
 *  3. "Client-Side LB: caller picks the instance, no extra
 *      hop. Server-Side LB: dedicated LB decides, adds a hop."
 *
 *  4. "Spring Cloud Gateway uses Client-Side LB via
 *      Spring Cloud LoadBalancer - 'lb://' prefix."
 *
 *  5. "Real production uses BOTH: server-side LB for
 *      Gateway HA, client-side LB for Service scaling."
 *
 *
 * ============================================================
 *  END OF STEP 6
 * ============================================================
 */

/*

Your PC → Router1 → Router2 → Router3 → Google Server
            1 hop    2 hop    3 hop

            Jitne zyada routers beech me, utne zyada hops, utni zyada latency.

Server-Side LB (extra hop hai)
text
Client → [LB] → Service
         ^
         |
      Ye ek EXTRA hop hai
Client → Service ke beech me LB ek extra stop hai.

Request pehle LB pe jaati hai

LB decide karta hai

Phir Service pe jaati hai

Total: 2 hops
Cost: Extra latency + LB potential bottleneck

Client-Side LB (no extra hop)
text
Client → Service
         ^
         |
      Direct. Koi extra stop nahi.
Client khud decide karta hai

Direct Service pe bhejta hai

Beech me koi LB nahi

Total: 1 hop
Benefit: Kam latency, no bottleneck
 */
/**
 *
 * Hop matlab ek network jump
SERVER-SIDE LB (2 hops):
   Client ──(hop 1)──> LB ──(hop 2)──> Service

CLIENT-SIDE LB (1 hop):
   Client ──(hop 1)──> Service
 */