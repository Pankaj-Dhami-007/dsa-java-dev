package systemdesign.basics.proxy;

public class ReverseProxyAndForwardProxy {

}

/*

========================================================================
            FORWARD PROXY AND REVERSE PROXY
========================================================================

Proxy servers are VERY IMPORTANT
components in networking
and system design.

========================================================================

Modern backend architectures heavily use:
- proxies
- reverse proxies

========================================================================
EXAMPLES
========================================================================

- :contentReference[oaicite:0]{index=0}
- :contentReference[oaicite:1]{index=1}
- :contentReference[oaicite:2]{index=2}

========================================================================
MAIN QUESTION
========================================================================

Why proxy servers needed?

========================================================================
PROBLEM
========================================================================

Direct communication between:
- clients
- servers

can create problems:
- security issues
- scalability problems
- traffic management difficulty
- hidden infrastructure exposure

========================================================================
THIS IS WHY
========================================================================

Proxy servers exist.

========================================================================
WHAT IS PROXY SERVER?
========================================================================

Proxy server acts as:
intermediate server
between client and destination.

========================================================================
SIMPLE IDEA
========================================================================

Instead of direct communication:

Client
   ↓
Proxy
   ↓
Destination Server

========================================================================
TYPES OF PROXY
========================================================================

1. Forward Proxy
2. Reverse Proxy

========================================================================
1. FORWARD PROXY
========================================================================

Forward Proxy sits:
between client and internet.

========================================================================
FLOW
========================================================================

Client
   ↓
Forward Proxy
   ↓
Internet Server

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Forward proxy represents:
        client

========================================================================
MAIN PURPOSES
========================================================================

1. Privacy
2. Content filtering
3. Access control
4. Caching

========================================================================
REAL WORLD EXAMPLE
========================================================================

Company employees access internet
through company proxy.

========================================================================

Company can:
- block websites
- monitor traffic
- filter content

========================================================================
FORWARD PROXY USE CASES
========================================================================

1. Corporate networks
2. VPN systems
3. Anonymous browsing
4. Internet filtering

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Destination server may not know:
actual client identity.

========================================================================

Only sees:
proxy server.

========================================================================
FORWARD PROXY ANALOGY
========================================================================

Suppose:
assistant communicates on your behalf.

========================================================================

Other people communicate with:
assistant

========================================================================

NOT directly with you.

========================================================================
2. REVERSE PROXY
========================================================================

Most IMPORTANT for backend system design.

========================================================================

Reverse Proxy sits:
between internet clients
and backend servers.

========================================================================
FLOW
========================================================================

Client
   ↓
Reverse Proxy
   ↓
Backend Servers

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Reverse proxy represents:
        backend servers

========================================================================

Clients usually do NOT know
actual backend server details.

========================================================================
MAIN RESPONSIBILITIES
========================================================================

1. Load balancing
2. SSL termination
3. Caching
4. Security
5. Compression
6. Request routing

========================================================================
1. LOAD BALANCING
========================================================================

Reverse proxy distributes traffic
across multiple servers.

========================================================================
EXAMPLE
========================================================================

Client
   ↓
NGINX
   ↓
Server-1
Server-2
Server-3

========================================================================
2. SSL TERMINATION
========================================================================

HTTPS encryption handled
at reverse proxy.

========================================================================

Backend servers may communicate
internally using HTTP.

========================================================================
ADVANTAGE
========================================================================

Reduces encryption overhead
on backend servers.

========================================================================
3. CACHING
========================================================================

Frequently requested responses
cached inside reverse proxy.

========================================================================

Reduces backend load.

========================================================================
EXAMPLE
========================================================================

Static images cached in NGINX.

========================================================================
4. SECURITY
========================================================================

Reverse proxy hides:
internal backend infrastructure.

========================================================================

Protects backend servers
from direct internet exposure.

========================================================================
ADVANTAGE
========================================================================

Improved security and abstraction.

========================================================================
5. COMPRESSION
========================================================================

Reverse proxy compresses responses.

========================================================================

Reduces:
- bandwidth usage
- response size

========================================================================
6. REQUEST ROUTING
========================================================================

Routes requests
to different backend services.

========================================================================
EXAMPLE
========================================================================

/api/users
    ↓
User Service

========================================================================

/api/payments
    ↓
Payment Service

========================================================================
IMPORTANT SYSTEM DESIGN UNDERSTANDING
========================================================================

Reverse proxy often acts as:
- API Gateway
- Load Balancer
- Security Layer

========================================================================
POPULAR REVERSE PROXY SOFTWARE
========================================================================

- :contentReference[oaicite:3]{index=3}
- :contentReference[oaicite:4]{index=4}
- :contentReference[oaicite:5]{index=5}

========================================================================
NGINX
========================================================================

Very popular reverse proxy.

========================================================================

Used for:
- load balancing
- caching
- SSL termination
- routing

========================================================================
REAL WORLD FLOW
========================================================================

Users
   ↓
CDN
   ↓
Reverse Proxy (NGINX)
   ↓
Load Balancer
   ↓
Application Servers

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Many people confuse:
API Gateway
and
Reverse Proxy.

========================================================================
DIFFERENCE
========================================================================

REVERSE PROXY
---------------
Generic traffic management layer.

========================================================================

API GATEWAY
-------------
Microservices-focused intelligent gateway.

========================================================================

API Gateway often built using:
reverse proxy technologies.

========================================================================
FORWARD PROXY vs REVERSE PROXY
========================================================================

FORWARD PROXY
---------------
Protects clients.

========================================================================

REVERSE PROXY
---------------
Protects backend servers.

========================================================================
IMPORTANT INTERVIEW POINT
========================================================================

Forward proxy hides:
        client identity

========================================================================

Reverse proxy hides:
        server identity

========================================================================
REVERSE PROXY BENEFITS
========================================================================

1. Scalability
2. Security
3. High availability
4. Performance optimization
5. Centralized traffic management

========================================================================
HIGH AVAILABILITY
========================================================================

If backend server fails:
reverse proxy redirects traffic
to healthy servers.

========================================================================
THIS IMPROVES
========================================================================

Fault tolerance.

========================================================================
REVERSE PROXY + MICROSERVICES
========================================================================

Microservices architectures heavily use:
reverse proxies.

========================================================================

Because:
many backend services exist.

========================================================================
CHALLENGES
========================================================================

1. Proxy bottleneck
2. Misconfiguration
3. Single point of failure

========================================================================
SOLUTIONS
========================================================================

1. Multiple proxy instances
2. Horizontal scaling
3. Distributed architecture

========================================================================
IMPORTANT REAL WORLD EXAMPLE
========================================================================

Large-scale systems rarely expose:
backend servers directly.

========================================================================

Traffic usually flows through:
- CDN
- Reverse Proxy
- API Gateway
- Load Balancer

========================================================================
SECURITY BENEFIT
========================================================================

Reverse proxy can:
- block malicious traffic
- filter requests
- prevent attacks

========================================================================
IMPORTANT INTERVIEW QUESTIONS
========================================================================

1. What is proxy server?

→ Intermediate server between client and destination.

========================================================================

2. Difference between forward and reverse proxy?

Forward Proxy
---------------
represents client

Reverse Proxy
---------------
represents server

========================================================================

3. Why reverse proxy important?

→ Security, load balancing,
routing, caching.

========================================================================

4. What is SSL termination?

→ HTTPS handled at reverse proxy.

========================================================================

5. Why reverse proxy used in microservices?

→ Centralized routing and traffic management.

========================================================================

6. What is caching in reverse proxy?

→ Storing frequently requested responses.

========================================================================

7. Difference between reverse proxy and load balancer?

Load Balancer
--------------
traffic distribution only

Reverse Proxy
---------------
broader traffic management

========================================================================

8. Why backend servers hidden behind reverse proxy?

→ Security and abstraction.

========================================================================
MOST IMPORTANT INTERVIEW LINE
========================================================================

Reverse proxy acts as intermediary
between clients and backend servers
providing security,
load balancing,
caching,
and centralized traffic management.

========================================================================

*/