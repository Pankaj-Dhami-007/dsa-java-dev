package systemdesign.basics.distributedsystems;

public class DistributedSystemsBasics {

}

/*

========================================================================
                DISTRIBUTED SYSTEMS
========================================================================

Distributed Systems is core foundation
of modern large-scale applications.

========================================================================

Almost every internet-scale company uses:
        Distributed Systems

========================================================================
EXAMPLES
========================================================================

- :contentReference[oaicite:0]{index=0}
- :contentReference[oaicite:1]{index=1}
- :contentReference[oaicite:2]{index=2}
- :contentReference[oaicite:3]{index=3}

========================================================================
MAIN QUESTION
========================================================================

Why Distributed Systems needed?

========================================================================
PROBLEM WITH SINGLE MACHINE
========================================================================

Single server has limits:
- CPU limit
- RAM limit
- Storage limit
- Network limit

========================================================================

Single machine also creates:
        Single Point of Failure

========================================================================
EXAMPLE
========================================================================

Suppose:
entire Instagram runs on one server.

========================================================================

If server crashes:
entire platform becomes unavailable.

========================================================================

Impossible for global-scale systems.

========================================================================
THIS IS WHY
========================================================================

Distributed Systems created.

========================================================================
WHAT IS DISTRIBUTED SYSTEM?
========================================================================

Distributed System is collection
of multiple independent machines
working together as single system.

========================================================================
SIMPLE IDEA
========================================================================

Instead of:
one powerful machine

========================================================================

Use:
many connected machines.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Users usually see:
        one application

========================================================================

Internally:
hundreds or thousands of servers exist.

========================================================================
EXAMPLE
========================================================================

WhatsApp appears as:
single application.

========================================================================

Internally:
- chat servers
- media servers
- notification servers
- databases
- caches
- gateways

========================================================================
MAIN GOALS OF DISTRIBUTED SYSTEMS
========================================================================

1. Scalability
2. Fault tolerance
3. High availability
4. Performance
5. Reliability

========================================================================
SCALABILITY
========================================================================

Traffic increases:
add more machines.

========================================================================

System capacity grows horizontally.

========================================================================
FAULT TOLERANCE
========================================================================

If one machine fails:
system continues running.

========================================================================

Very important for:
internet-scale applications.

========================================================================
HIGH AVAILABILITY
========================================================================

System remains operational
most of the time.

========================================================================
EXAMPLE
========================================================================

99.99% uptime.

========================================================================
PERFORMANCE
========================================================================

Work distributed across machines.

========================================================================

Reduces:
- load
- latency
- bottlenecks

========================================================================
IMPORTANT SYSTEM DESIGN UNDERSTANDING
========================================================================

Distributed systems solve:
scaling problems

========================================================================

BUT create:
distributed complexity.

========================================================================
COMMON DISTRIBUTED SYSTEM COMPONENTS
========================================================================

1. Load Balancers
2. Multiple Servers
3. Distributed Databases
4. Distributed Cache
5. Message Queues

========================================================================
DISTRIBUTED SYSTEM FLOW
========================================================================

Users
   ↓
Load Balancer
   ↓
Multiple Application Servers
   ↓
Redis / Kafka
   ↓
Distributed Database

========================================================================
IMPORTANT CHALLENGES
========================================================================

Distributed systems are difficult because:
machines communicate over network.

========================================================================

Network introduces:
- latency
- failures
- packet loss
- partial failures

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

In distributed systems:
network is NEVER fully reliable.

========================================================================
MAIN CHALLENGES
========================================================================

1. Network failures
2. Data consistency
3. Synchronization
4. Partial failures
5. Distributed coordination
6. Latency

========================================================================
1. NETWORK FAILURES
========================================================================

Machines communicate over network.

========================================================================

Network may:
- disconnect
- timeout
- become slow

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Machine may be alive
but network unreachable.

========================================================================

This creates complex failure scenarios.

========================================================================
2. DATA CONSISTENCY
========================================================================

Suppose data copied
across multiple servers.

========================================================================

Question:
How all copies remain synchronized?

========================================================================
EXAMPLE
========================================================================

Bank balance updated on:
Server-1

========================================================================

Server-2 still shows old balance.

========================================================================

This creates:
        consistency problem

========================================================================
3. PARTIAL FAILURE
========================================================================

Very important distributed systems concept.

========================================================================

Some components fail,
others continue running.

========================================================================
EXAMPLE
========================================================================

Notification Service crashes.

========================================================================

Other services still operational.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Partial failure impossible
inside single-machine systems.

========================================================================

But very common in distributed systems.

========================================================================
4. DISTRIBUTED COORDINATION
========================================================================

Multiple machines must coordinate safely.

========================================================================
EXAMPLE
========================================================================

Preventing same seat booking twice
in ticket booking systems.

========================================================================
THIS REQUIRES
========================================================================

Distributed locking and coordination.

========================================================================
5. LATENCY
========================================================================

Communication between machines
takes time.

========================================================================

Remote calls slower than:
local memory access.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Network calls are expensive.

========================================================================

Microservices introduce:
network communication overhead.

========================================================================
CAP THEOREM
========================================================================

Most important distributed systems theorem.

========================================================================

CAP stands for:
- Consistency
- Availability
- Partition Tolerance

========================================================================
1. CONSISTENCY
========================================================================

All nodes show same data simultaneously.

========================================================================
EXAMPLE
========================================================================

Every server shows same bank balance.

========================================================================
2. AVAILABILITY
========================================================================

System always responds to requests.

========================================================================

Even during failures.

========================================================================
3. PARTITION TOLERANCE
========================================================================

System continues working
despite network failures.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Distributed systems cannot fully guarantee:
all three simultaneously.

========================================================================

Must make trade-offs.

========================================================================
EXAMPLE
========================================================================

Banking systems prefer:
Consistency

========================================================================

Social media often prefers:
Availability

========================================================================
EVENTUAL CONSISTENCY
========================================================================

Data may not become consistent immediately.

========================================================================

Eventually all nodes synchronize.

========================================================================
EXAMPLE
========================================================================

Instagram likes may update
after slight delay.

========================================================================
STRONG CONSISTENCY
========================================================================

All users immediately see same data.

========================================================================
EXAMPLE
========================================================================

Bank transactions.

========================================================================
REPLICATION
========================================================================

Copying data across multiple servers.

========================================================================
WHY REPLICATION?
========================================================================

1. Fault tolerance
2. High availability
3. Faster reads

========================================================================
SHARDING
========================================================================

Splitting data across multiple machines.

========================================================================
EXAMPLE
========================================================================

Users 1-1M → Shard-1

Users 1M-2M → Shard-2

========================================================================
WHY SHARDING?
========================================================================

Single database cannot handle
massive data forever.

========================================================================
DISTRIBUTED CACHE
========================================================================

Shared cache across multiple servers.

========================================================================
EXAMPLE
========================================================================

Redis cluster.

========================================================================
DISTRIBUTED MESSAGE SYSTEMS
========================================================================

Large-scale systems use:
- Kafka
- RabbitMQ

========================================================================

For asynchronous distributed communication.

========================================================================
IMPORTANT SYSTEM DESIGN UNDERSTANDING
========================================================================

Modern backend systems are mostly:
distributed systems.

========================================================================

Understanding distributed systems
is core of senior backend engineering.

========================================================================
REAL WORLD EXAMPLE
========================================================================

Netflix Architecture

Users
   ↓
CDN
   ↓
API Gateway
   ↓
Microservices
   ↓
Kafka / Redis
   ↓
Distributed Databases

========================================================================
IMPORTANT CONCEPT
========================================================================

Horizontal scaling creates:
distributed systems.

========================================================================

As soon as multiple machines exist:
distributed complexity begins.

========================================================================
COMMON USE CASES
========================================================================

1. Social media platforms
2. Banking systems
3. Streaming systems
4. Cloud platforms
5. E-commerce systems

========================================================================
IMPORTANT INTERVIEW QUESTIONS
========================================================================

1. What is Distributed System?

→ Multiple machines working together
as single system.

========================================================================

2. Why Distributed Systems needed?

→ Scalability and fault tolerance.

========================================================================

3. What is partial failure?

→ Some components fail while others continue.

========================================================================

4. What is consistency?

→ Same data across all nodes.

========================================================================

5. What is eventual consistency?

→ Data becomes consistent after some delay.

========================================================================

6. What is replication?

→ Copying data across multiple servers.

========================================================================

7. What is sharding?

→ Splitting data across machines.

========================================================================

8. What is CAP theorem?

→ Trade-off between consistency,
availability,
and partition tolerance.

========================================================================

9. Why distributed systems difficult?

→ Network failures and coordination complexity.

========================================================================
MOST IMPORTANT INTERVIEW LINE
========================================================================

Distributed systems use multiple machines
working together
to achieve scalability,
fault tolerance,
and high availability.

========================================================================

*/