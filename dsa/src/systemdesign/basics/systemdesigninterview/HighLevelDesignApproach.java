package systemdesign.basics.systemdesigninterview;

public class HighLevelDesignApproach {

}

/*

========================================================================
                HIGH LEVEL DESIGN (HLD)
========================================================================

High Level Design is one of MOST IMPORTANT
parts of system design interviews.

========================================================================

Companies ask HLD to evaluate:
- architecture thinking
- scalability understanding
- distributed systems knowledge
- backend engineering skills

========================================================================
EXAMPLES
========================================================================

Design:
- WhatsApp
- Instagram
- YouTube
- URL Shortener
- Food Delivery System
- Netflix

========================================================================
MAIN QUESTION
========================================================================

How to approach
system design interview properly?

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

System design interviews are NOT about:
perfect architecture.

========================================================================

Interview mainly checks:
your thinking process.

========================================================================
MAIN GOALS OF HLD
========================================================================

1. Scalability
2. Availability
3. Reliability
4. Maintainability
5. Performance

========================================================================
WHAT IS HIGH LEVEL DESIGN?
========================================================================

Designing overall architecture
of large-scale system.

========================================================================
HLD FOCUSES ON
========================================================================

1. Major components
2. System interactions
3. Databases
4. Scaling
5. Communication
6. Infrastructure

========================================================================
NOT FOCUSED ON
========================================================================

- low-level classes
- methods
- implementation details

========================================================================
EXAMPLE
========================================================================

HLD discusses:
- API Gateway
- Load Balancer
- Database
- Cache
- Message Queue

========================================================================

NOT:
Java class implementation.

========================================================================
MOST IMPORTANT INTERVIEW RULE
========================================================================

ALWAYS start with:
requirements clarification.

========================================================================
STEP 1 : REQUIREMENTS GATHERING
========================================================================

Clarify:
- functional requirements
- non-functional requirements

========================================================================
1. FUNCTIONAL REQUIREMENTS
========================================================================

What system should do.

========================================================================
EXAMPLE
========================================================================

WhatsApp should:
- send messages
- support groups
- show online status

========================================================================
2. NON-FUNCTIONAL REQUIREMENTS
========================================================================

Quality requirements.

========================================================================
EXAMPLES
========================================================================

- scalability
- latency
- availability
- consistency

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Different systems prioritize:
different requirements.

========================================================================
EXAMPLE
========================================================================

Banking:
strong consistency.

========================================================================

Social media:
high availability.

========================================================================
STEP 2 : ESTIMATE SCALE
========================================================================

Estimate:
- users
- requests
- storage
- traffic

========================================================================
EXAMPLE
========================================================================

10 million daily users.

========================================================================

Average:
100 messages/day/user

========================================================================

Total:
1 billion messages/day

========================================================================
WHY ESTIMATION IMPORTANT?
========================================================================

Architecture depends on scale.

========================================================================

Small system and global system
require different designs.

========================================================================
STEP 3 : HIGH LEVEL COMPONENTS
========================================================================

Identify major components.

========================================================================
EXAMPLE
========================================================================

Users
   ↓
Load Balancer
   ↓
API Gateway
   ↓
Microservices
   ↓
Redis/Kafka
   ↓
Database

========================================================================
COMMON COMPONENTS
========================================================================

1. Load Balancer
2. API Gateway
3. Microservices
4. Cache
5. Database
6. CDN
7. Message Queue

========================================================================
STEP 4 : DATABASE DESIGN
========================================================================

Choose:
- SQL or NoSQL
- replication
- sharding
- indexing

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Database choice depends on:
business requirements.

========================================================================
EXAMPLE
========================================================================

Chat systems:
NoSQL often preferred.

========================================================================

Banking:
SQL preferred.

========================================================================
STEP 5 : API DESIGN
========================================================================

Define important APIs.

========================================================================
EXAMPLE
========================================================================

POST /messages

========================================================================

GET /users/{id}

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

API design should be:
simple and scalable.

========================================================================
STEP 6 : SCALABILITY DISCUSSION
========================================================================

Discuss:
how system handles traffic growth.

========================================================================
TOPICS
========================================================================

1. Horizontal scaling
2. Load balancing
3. Caching
4. Database scaling
5. CDN

========================================================================
STEP 7 : BOTTLENECK ANALYSIS
========================================================================

Identify possible bottlenecks.

========================================================================
EXAMPLES
========================================================================

- database overload
- cache miss storms
- message queue lag
- hot partitions

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Good system design engineers
always think about:
failure scenarios.

========================================================================
STEP 8 : FAULT TOLERANCE
========================================================================

Discuss:
how system survives failures.

========================================================================
TOPICS
========================================================================

1. Replication
2. Retry mechanisms
3. Failover
4. Circuit breaker
5. Redundancy

========================================================================
STEP 9 : SECURITY
========================================================================

Discuss:
- authentication
- authorization
- rate limiting
- encryption

========================================================================
STEP 10 : OBSERVABILITY
========================================================================

Discuss:
- monitoring
- logging
- tracing
- alerts

========================================================================
VERY IMPORTANT INTERVIEW UNDERSTANDING
========================================================================

System design interviews are:
discussion-oriented.

========================================================================

Interviewers expect:
trade-off discussions.

========================================================================
TRADE-OFFS
========================================================================

Every design decision has:
advantages and disadvantages.

========================================================================
EXAMPLE
========================================================================

Strong consistency:
better correctness
but lower availability.

========================================================================

Eventual consistency:
better scalability
but temporary stale data.

========================================================================
IMPORTANT SYSTEM DESIGN THINKING
========================================================================

There is NO:
perfect architecture.

========================================================================

Only:
trade-offs based on requirements.

========================================================================
COMMON HLD QUESTIONS
========================================================================

1. Design WhatsApp
2. Design Instagram
3. Design URL Shortener
4. Design TinyURL
5. Design YouTube
6. Design Notification System
7. Design Ride Sharing App

========================================================================
URL SHORTENER EXAMPLE
========================================================================

Users submit long URL
   ↓
Backend generates short code
   ↓
Stores mapping in database
   ↓
Returns short URL

========================================================================
IMPORTANT TOPICS DISCUSSED
========================================================================

- unique ID generation
- database design
- cache
- scaling redirects

========================================================================
CHAT SYSTEM EXAMPLE
========================================================================

Topics discussed:
- WebSocket
- message queues
- online status
- message delivery
- distributed systems

========================================================================
IMPORTANT INTERVIEW SKILLS
========================================================================

1. Communication
2. Structured thinking
3. Requirement clarification
4. Trade-off analysis
5. Scalability understanding

========================================================================
COMMON MISTAKES
========================================================================

1. Jumping into solution immediately
2. Ignoring requirements
3. Overengineering
4. Ignoring bottlenecks
5. No trade-off discussion

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Interviewers value:
clarity and reasoning
more than complexity.

========================================================================
GOOD INTERVIEW FLOW
========================================================================

Requirements
   ↓
Scale Estimation
   ↓
High-Level Architecture
   ↓
Database Design
   ↓
Scaling Discussion
   ↓
Failure Handling
   ↓
Trade-Offs

========================================================================
CONSISTENCY vs AVAILABILITY DISCUSSION
========================================================================

Important in many interviews.

========================================================================
EXAMPLE
========================================================================

Messaging system:
availability prioritized.

========================================================================

Payment system:
consistency prioritized.

========================================================================
REAL WORLD SYSTEM DESIGN FLOW
========================================================================

Users
   ↓
CDN
   ↓
Load Balancer
   ↓
API Gateway
   ↓
Microservices
   ↓
Kafka/Redis
   ↓
Databases

========================================================================
IMPORTANT SYSTEM DESIGN UNDERSTANDING
========================================================================

Most modern architectures combine:
- microservices
- caching
- distributed systems
- asynchronous messaging
- replication
- container orchestration

========================================================================
CAPACITY ESTIMATION
========================================================================

Sometimes interview includes:
back-of-envelope calculations.

========================================================================
EXAMPLES
========================================================================

1. Storage estimation
2. QPS estimation
3. Bandwidth estimation
4. Cache size estimation

========================================================================
QPS
========================================================================

Queries Per Second.

========================================================================

Important scalability metric.

========================================================================
LATENCY TARGETS
========================================================================

Example:
API response < 200ms

========================================================================
HIGH AVAILABILITY TARGETS
========================================================================

Example:
99.99% uptime

========================================================================
IMPORTANT INTERVIEW QUESTIONS
========================================================================

1. What is High Level Design?

→ Designing overall scalable architecture.

========================================================================

2. Why requirements clarification important?

→ Architecture depends on requirements.

========================================================================

3. Why trade-offs important in HLD?

→ No architecture is perfect.

========================================================================

4. What is bottleneck analysis?

→ Identifying scaling/performance issues.

========================================================================

5. What should HLD focus on?

→ Major components and scalability.

========================================================================

6. Difference between HLD and LLD?

HLD
----
system architecture

LLD
----
classes and implementation

========================================================================

7. Why scale estimation important?

→ Design changes according to traffic scale.

========================================================================

8. Why fault tolerance important?

→ Systems must survive failures.

========================================================================

9. Why observability important?

→ Debugging and reliability in production.

========================================================================
MOST IMPORTANT INTERVIEW LINE
========================================================================

High Level Design focuses on designing
scalable,
reliable,
and maintainable
distributed system architecture
through structured requirement-driven thinking.

========================================================================

*/