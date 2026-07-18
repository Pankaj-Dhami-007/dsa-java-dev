package systemdesign.basics.scalability;

public class ScalabilityInSystemDesign {

}

/*

========================================================================
                SCALABILITY IN SYSTEM DESIGN
========================================================================

Scalability is one of MOST IMPORTANT
goals of system design.

========================================================================

Every modern application must scale.

========================================================================
EXAMPLES
========================================================================

- Instagram
- Netflix
- Amazon
- WhatsApp
- YouTube

========================================================================

All these systems handle:
millions or billions of users.

========================================================================
MAIN QUESTION
========================================================================

How applications handle
massive traffic growth?

========================================================================

This is called:
        Scalability

========================================================================
WHAT IS SCALABILITY?
========================================================================

Scalability is system capability
to handle increasing traffic,
users,
and data
without performance degradation.

========================================================================
SIMPLE DEFINITION
========================================================================

Ability of system to grow efficiently.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Suppose application handles:
        1,000 users

========================================================================

Later:
        10 million users arrive.

========================================================================

If system still works efficiently:
        system is scalable.

========================================================================
PROBLEM WITHOUT SCALABILITY
========================================================================

As traffic increases:
- server crashes
- response slows
- database overload occurs
- downtime happens

========================================================================

This destroys:
- user experience
- reliability
- business growth

========================================================================
MAIN GOALS OF SCALABILITY
========================================================================

1. Handle more users
2. Handle more requests
3. Handle more data
4. Maintain performance
5. Maintain availability

========================================================================
TYPES OF SCALING
========================================================================

1. Vertical Scaling
2. Horizontal Scaling

========================================================================
1. VERTICAL SCALING
========================================================================

Increasing power of same machine.

========================================================================
EXAMPLE
========================================================================

Increase:
- CPU
- RAM
- Storage

========================================================================

Server becomes stronger.

========================================================================
SIMPLE IDEA
========================================================================

Big server becomes:
        bigger server

========================================================================
ADVANTAGES
========================================================================

1. Easy implementation
2. No distributed complexity
3. Simple architecture

========================================================================
DISADVANTAGES
========================================================================

1. Hardware limit exists
2. Expensive
3. Downtime possible
4. Single point of failure

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Vertical scaling cannot continue forever.

========================================================================

Because:
single machine has physical limits.

========================================================================
2. HORIZONTAL SCALING
========================================================================

Adding more servers
instead of increasing server size.

========================================================================
EXAMPLE
========================================================================

1 server
    ↓
10 servers
    ↓
100 servers

========================================================================
SIMPLE IDEA
========================================================================

Instead of:
one huge server

========================================================================

Use:
many smaller servers.

========================================================================
ADVANTAGES
========================================================================

1. Better scalability
2. Better fault tolerance
3. High availability
4. Distributed traffic

========================================================================
DISADVANTAGES
========================================================================

1. Complex architecture
2. Distributed systems challenges
3. Data consistency problems
4. Network communication overhead

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Modern internet systems mostly prefer:
        Horizontal Scaling

========================================================================
WHY?
========================================================================

Because global-scale applications
cannot run efficiently on single server.

========================================================================
SCALABILITY FLOW
========================================================================

Users
   ↓
Load Balancer
   ↓
Multiple Servers
   ↓
Distributed Database

========================================================================
IMPORTANT SYSTEM DESIGN UNDERSTANDING
========================================================================

Scalability is NOT only:
adding servers.

========================================================================

It involves:
- caching
- load balancing
- database scaling
- CDN
- distributed systems
- asynchronous processing

========================================================================
SCALING BOTTLENECKS
========================================================================

As system grows,
different bottlenecks appear.

========================================================================
COMMON BOTTLENECKS
========================================================================

1. CPU bottleneck
2. Memory bottleneck
3. Database bottleneck
4. Network bottleneck
5. Disk I/O bottleneck

========================================================================
DATABASE SCALING IS HARDEST
========================================================================

Scaling application servers:
        relatively easier

========================================================================

Scaling databases:
        very difficult

========================================================================

Because:
data consistency becomes complex.

========================================================================
READ SCALING
========================================================================

Suppose:
millions of read requests exist.

========================================================================

Example:
Instagram feed viewing.

========================================================================

Solutions:
- cache
- read replicas
- CDN

========================================================================
WRITE SCALING
========================================================================

Suppose:
huge write traffic exists.

========================================================================

Example:
WhatsApp messages.

========================================================================

Solutions:
- sharding
- partitioning
- distributed databases
- message queues

========================================================================
IMPORTANT CONCEPT
========================================================================

Read-heavy systems
and write-heavy systems
scale differently.

========================================================================
READ HEAVY SYSTEMS
========================================================================

More reads than writes.

========================================================================
EXAMPLES
========================================================================

- YouTube
- Netflix
- Blogs

========================================================================
WRITE HEAVY SYSTEMS
========================================================================

Large number of writes.

========================================================================
EXAMPLES
========================================================================

- Chat systems
- Logging systems
- Analytics systems

========================================================================
AUTO SCALING
========================================================================

Modern cloud systems support:
        Auto Scaling

========================================================================

Meaning:
servers automatically added/removed
based on traffic.

========================================================================
EXAMPLE
========================================================================

Traffic spike:
        add more servers

========================================================================

Traffic decreases:
        remove extra servers

========================================================================
CLOUD PLATFORMS
========================================================================

- :contentReference[oaicite:0]{index=0}
- :contentReference[oaicite:1]{index=1}
- :contentReference[oaicite:2]{index=2}

========================================================================
IMPORTANT METRICS
========================================================================

1. Throughput
2. Latency
3. Availability
4. Response Time
5. Concurrent Users

========================================================================
1. THROUGHPUT
========================================================================

Number of requests processed
per second.

========================================================================
2. LATENCY
========================================================================

Time taken for request-response cycle.

========================================================================
3. AVAILABILITY
========================================================================

How often system remains operational.

========================================================================
EXAMPLE
========================================================================

99.99% uptime

========================================================================
4. RESPONSE TIME
========================================================================

How fast system responds to request.

========================================================================
5. CONCURRENT USERS
========================================================================

Users using system simultaneously.

========================================================================
IMPORTANT SYSTEM DESIGN UNDERSTANDING
========================================================================

Large-scale systems must support:
high concurrency.

========================================================================

Meaning:
many users accessing system together.

========================================================================
SCALABILITY STRATEGIES
========================================================================

1. Load Balancing
2. Caching
3. Database Replication
4. Database Sharding
5. CDN
6. Message Queues
7. Stateless Servers

========================================================================
STATELESS SERVERS
========================================================================

Modern scalable systems prefer:
        stateless servers

========================================================================
WHAT IS STATELESS?
========================================================================

Server does not permanently store
client session state.

========================================================================
WHY IMPORTANT?
========================================================================

Because any request can go
to any server.

========================================================================

This simplifies:
horizontal scaling.

========================================================================
REAL WORLD ARCHITECTURE
========================================================================

Users
   ↓
CDN
   ↓
Load Balancer
   ↓
Application Servers
   ↓
Redis Cache
   ↓
Database Cluster

========================================================================
SCALABILITY CHALLENGES
========================================================================

1. Distributed coordination
2. Data consistency
3. Network failures
4. Replication lag
5. Cache invalidation
6. Fault tolerance

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Scaling introduces:
        distributed systems problems

========================================================================

This is why:
distributed systems
are core of advanced system design.

========================================================================
CAPACITY PLANNING
========================================================================

Estimating:
- future traffic
- storage growth
- server requirements

========================================================================
EXAMPLE
========================================================================

Suppose:
1 million users upload photos daily.

========================================================================

Need estimation for:
- storage
- bandwidth
- servers
- database growth

========================================================================
IMPORTANT INTERVIEW QUESTIONS
========================================================================

1. What is scalability?

→ Ability of system to handle increasing load efficiently.

========================================================================

2. Difference between vertical and horizontal scaling?

Vertical
---------
increase machine power

Horizontal
-----------
add more servers

========================================================================

3. Why horizontal scaling preferred?

→ Better scalability and fault tolerance.

========================================================================

4. What is bottleneck?

→ Component limiting system performance.

========================================================================

5. Why database scaling difficult?

→ Maintaining consistency across distributed data.

========================================================================

6. What is throughput?

→ Requests processed per second.

========================================================================

7. What is latency?

→ Time taken for request-response cycle.

========================================================================

8. What is stateless server?

→ Server that does not store client session state.

========================================================================

9. What is auto scaling?

→ Automatic server addition/removal based on traffic.

========================================================================
MOST IMPORTANT INTERVIEW LINE
========================================================================

Scalability is system capability
to efficiently handle increasing users,
traffic,
and data
while maintaining performance and availability.

========================================================================

*/