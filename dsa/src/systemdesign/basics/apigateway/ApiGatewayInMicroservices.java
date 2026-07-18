package systemdesign.basics.apigateway;

public class ApiGatewayInMicroservices {

}

/*

========================================================================
                    API GATEWAY
========================================================================

API Gateway is one of MOST IMPORTANT
components in microservices architecture.

========================================================================

Almost every modern microservices system uses:
        API Gateway

========================================================================
EXAMPLES
========================================================================

- :contentReference[oaicite:0]{index=0}
- :contentReference[oaicite:1]{index=1}
- :contentReference[oaicite:2]{index=2}

========================================================================
MAIN QUESTION
========================================================================

Why API Gateway needed?

========================================================================
PROBLEM WITHOUT API GATEWAY
========================================================================

Suppose application has:
- User Service
- Payment Service
- Order Service
- Notification Service
- Chat Service

========================================================================

Without API Gateway:

Client directly communicates
with all services.

========================================================================
FLOW
========================================================================

Mobile App
   ↓
User Service

========================================================================

Mobile App
   ↓
Payment Service

========================================================================

Mobile App
   ↓
Order Service

========================================================================

Mobile App
   ↓
Notification Service

========================================================================
PROBLEMS
========================================================================

1. Too many network calls
2. Complex frontend logic
3. Security difficult
4. Authentication repeated everywhere
5. Tight coupling
6. Difficult monitoring

========================================================================
THIS IS WHY
========================================================================

API Gateway created.

========================================================================
WHAT IS API GATEWAY?
========================================================================

API Gateway is single entry point
for all client requests.

========================================================================
SIMPLE IDEA
========================================================================

Client communicates only with:
        API Gateway

========================================================================

Gateway internally routes requests
to appropriate microservices.

========================================================================
FLOW WITH API GATEWAY
========================================================================

Client
   ↓
API Gateway
   ↓
User Service
Order Service
Payment Service
Notification Service

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Client no longer needs knowledge of:
- service locations
- service ports
- internal architecture

========================================================================

API Gateway hides internal complexity.

========================================================================
MAIN RESPONSIBILITIES
========================================================================

1. Request Routing
2. Authentication
3. Authorization
4. Rate Limiting
5. Load Balancing
6. Logging
7. Monitoring
8. Response Aggregation

========================================================================
1. REQUEST ROUTING
========================================================================

Gateway routes request
to correct microservice.

========================================================================
EXAMPLE
========================================================================

/users
    ↓
User Service

========================================================================

/payments
    ↓
Payment Service

========================================================================
2. AUTHENTICATION
========================================================================

Gateway validates:
- JWT token
- session
- API keys

========================================================================

Instead of implementing authentication
inside every service.

========================================================================
ADVANTAGE
========================================================================

Centralized security management.

========================================================================
3. AUTHORIZATION
========================================================================

Checks:
whether user has permission
to access resource.

========================================================================
EXAMPLE
========================================================================

Admin APIs accessible only
to admin users.

========================================================================
4. RATE LIMITING
========================================================================

Limits excessive requests from clients.

========================================================================
WHY IMPORTANT?
========================================================================

Protects system from:
- abuse
- bots
- DDOS attacks
- overload

========================================================================
EXAMPLE
========================================================================

User allowed:
100 requests/minute

========================================================================
IF LIMIT EXCEEDED
========================================================================

Gateway blocks requests.

========================================================================
5. LOAD BALANCING
========================================================================

Gateway distributes traffic
across service instances.

========================================================================
EXAMPLE
========================================================================

Payment Service has:
5 instances

========================================================================

Gateway distributes requests among them.

========================================================================
6. LOGGING
========================================================================

Gateway logs:
- requests
- responses
- errors
- latency

========================================================================

Useful for:
debugging and monitoring.

========================================================================
7. MONITORING
========================================================================

Tracks:
- traffic
- failures
- response time
- API usage

========================================================================
8. RESPONSE AGGREGATION
========================================================================

Gateway may combine responses
from multiple services.

========================================================================
EXAMPLE
========================================================================

Mobile App requests:
user dashboard.

========================================================================

Gateway fetches:
- user profile
- order history
- notifications

========================================================================

Then combines into:
single response.

========================================================================
ADVANTAGE
========================================================================

Reduces multiple client requests.

========================================================================
IMPORTANT SYSTEM DESIGN UNDERSTANDING
========================================================================

API Gateway improves:
- abstraction
- security
- maintainability
- scalability

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Gateway acts like:
        Front Door

========================================================================

All traffic enters through gateway.

========================================================================
REAL WORLD FLOW
========================================================================

Client
   ↓
API Gateway
   ↓
Authentication Service
Order Service
Payment Service
Notification Service

========================================================================
POPULAR API GATEWAY TOOLS
========================================================================

- :contentReference[oaicite:3]{index=3}
- :contentReference[oaicite:4]{index=4}
- :contentReference[oaicite:5]{index=5}
- :contentReference[oaicite:6]{index=6}

========================================================================
SPRING CLOUD GATEWAY
========================================================================

Popular in Java microservices ecosystem.

========================================================================

Provides:
- routing
- filters
- authentication
- rate limiting

========================================================================
IMPORTANT CONCEPT
========================================================================

Gateway should NOT contain
business logic.

========================================================================

Gateway mainly handles:
cross-cutting concerns.

========================================================================
WHAT ARE CROSS-CUTTING CONCERNS?
========================================================================

Common functionalities needed
across multiple services.

========================================================================
EXAMPLES
========================================================================

1. Authentication
2. Logging
3. Monitoring
4. Security
5. Rate limiting

========================================================================
API GATEWAY vs LOAD BALANCER
========================================================================

Load Balancer
--------------
distributes traffic

========================================================================

API Gateway
-------------
routes requests and handles
application-level concerns.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

API Gateway may internally use:
        Load Balancer

========================================================================
API GATEWAY CHALLENGES
========================================================================

1. Single point of failure
2. Additional latency
3. Complex routing
4. Gateway overload

========================================================================
SOLUTIONS
========================================================================

1. Multiple gateway instances
2. Horizontal scaling
3. Caching
4. High availability setup

========================================================================
BACKEND FOR FRONTEND (BFF)
========================================================================

Sometimes separate gateways created
for different clients.

========================================================================
EXAMPLE
========================================================================

Mobile Gateway
Web Gateway
Admin Gateway

========================================================================

Because each frontend has:
different data requirements.

========================================================================
IMPORTANT SYSTEM DESIGN UNDERSTANDING
========================================================================

As systems grow,
client-service communication complexity
increases massively.

========================================================================

API Gateway centralizes this complexity.

========================================================================
SECURITY BENEFIT
========================================================================

Internal services remain hidden
from public internet.

========================================================================

Only API Gateway exposed publicly.

========================================================================
SERVICE DISCOVERY + API GATEWAY
========================================================================

Gateway often integrates with:
        Service Discovery

========================================================================

Gateway dynamically finds:
available service instances.

========================================================================
EXAMPLE
========================================================================

Payment Service scales:
5 → 20 instances

========================================================================

Gateway automatically routes traffic
to new instances.

========================================================================
REAL WORLD LARGE SCALE FLOW
========================================================================

Users
   ↓
CDN
   ↓
API Gateway
   ↓
Load Balancer
   ↓
Microservices
   ↓
Redis / Kafka / Databases

========================================================================
IMPORTANT INTERVIEW QUESTIONS
========================================================================

1. What is API Gateway?

→ Single entry point for client requests.

========================================================================

2. Why API Gateway needed?

→ Centralized routing, security,
monitoring, and request management.

========================================================================

3. Difference between API Gateway and Load Balancer?

Load Balancer
--------------
traffic distribution

API Gateway
-------------
application-level routing and management

========================================================================

4. What is rate limiting?

→ Restricting number of requests from client.

========================================================================

5. Why authentication handled in gateway?

→ Centralized security management.

========================================================================

6. What is response aggregation?

→ Combining multiple service responses
into single response.

========================================================================

7. Why API Gateway important in microservices?

→ Hides internal service complexity.

========================================================================

8. Can API Gateway become bottleneck?

→ Yes

========================================================================

9. How to avoid gateway failure?

→ Multiple gateway instances and scaling.

========================================================================
MOST IMPORTANT INTERVIEW LINE
========================================================================

API Gateway acts as centralized entry point
in microservices architecture
handling routing,
security,
monitoring,
and request management.

========================================================================

*/