package systemdesign.basics.database;

public class DatabaseInSystemDesign {

}

/*

========================================================================
                    DATABASE IN SYSTEM DESIGN
========================================================================

Database is one of MOST IMPORTANT
components of backend systems.

========================================================================

Almost every real-world application
depends on database.

========================================================================
EXAMPLES
========================================================================

Instagram stores:
- users
- posts
- comments
- reels
- followers

========================================================================

WhatsApp stores:
- chats
- messages
- media
- user profiles

========================================================================

Amazon stores:
- products
- orders
- payments
- inventory

========================================================================
MAIN QUESTION
========================================================================

Why database needed?

========================================================================

Why server cannot store data directly?

========================================================================
PROBLEM WITHOUT DATABASE
========================================================================

Suppose backend server stores data in memory.

========================================================================

PROBLEMS
========================================================================

1. Data lost after server restart
2. Data sharing impossible
3. Scaling becomes difficult
4. No persistence
5. No reliable storage

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Server handles:
        business logic

========================================================================

Database handles:
        data storage

========================================================================
SIMPLE FLOW
========================================================================

Client
   ↓
Backend Server
   ↓
Database
   ↓
Response

========================================================================
WHAT IS DATABASE?
========================================================================

Database is organized storage system
used to store, retrieve, update,
and manage application data efficiently.

========================================================================
DATABASE RESPONSIBILITIES
========================================================================

1. Store data permanently
2. Retrieve data efficiently
3. Support concurrent access
4. Ensure consistency
5. Prevent data corruption

========================================================================
TYPES OF DATABASES
========================================================================

1. SQL Database
2. NoSQL Database

========================================================================
1. SQL DATABASE
========================================================================

SQL databases store data in:
        tables

========================================================================
EXAMPLES
========================================================================

- :contentReference[oaicite:0]{index=0}
- :contentReference[oaicite:1]{index=1}
- :contentReference[oaicite:2]{index=2}

========================================================================
STRUCTURE
========================================================================

Rows
Columns
Tables
Relationships

========================================================================
EXAMPLE TABLE
========================================================================

Users Table

| id | name   | email           |
|----|--------|-----------------|
| 1  | Pankaj | abc@gmail.com   |

========================================================================
IMPORTANT CHARACTERISTICS
========================================================================

1. Structured schema
2. ACID properties
3. Strong consistency
4. Relationships supported

========================================================================
WHAT IS ACID?
========================================================================

ACID ensures reliable transactions.

========================================================================
A → Atomicity
========================================================================

Transaction fully succeeds
or fully fails.

========================================================================
C → Consistency
========================================================================

Database remains valid after transaction.

========================================================================
I → Isolation
========================================================================

Transactions do not interfere improperly.

========================================================================
D → Durability
========================================================================

Committed data survives crashes.

========================================================================
REAL EXAMPLE
========================================================================

Bank transfer.

========================================================================

Money should not disappear
if server crashes midway.

========================================================================
THIS IS WHY
========================================================================

Banking systems heavily use:
        SQL databases

========================================================================
2. NoSQL DATABASE
========================================================================

NoSQL databases are designed for:
- scalability
- flexibility
- large-scale distributed systems

========================================================================
EXAMPLES
========================================================================

- :contentReference[oaicite:3]{index=3}
- :contentReference[oaicite:4]{index=4}
- :contentReference[oaicite:5]{index=5}

========================================================================
TYPES OF NOSQL DATABASES
========================================================================

1. Document Database
2. Key-Value Database
3. Column Database
4. Graph Database

========================================================================
DOCUMENT DATABASE
========================================================================

Stores JSON-like documents.

========================================================================
EXAMPLE
========================================================================

{
    "id": 1,
    "name": "Pankaj",
    "skills": ["Java", "Spring Boot"]
}

========================================================================
WHY NOSQL CREATED?
========================================================================

Because SQL databases struggle at:
- massive scale
- flexible schema
- distributed architecture

========================================================================
SQL vs NOSQL
========================================================================

SQL
-----
Structured schema
Strong consistency
Complex joins

========================================================================

NoSQL
-------
Flexible schema
High scalability
Distributed friendly

========================================================================
WHEN SQL USED?
========================================================================

Use SQL when:
- strong consistency needed
- transactions important
- relationships complex

========================================================================
EXAMPLES
========================================================================

- Banking
- Payments
- ERP systems

========================================================================
WHEN NOSQL USED?
========================================================================

Use NoSQL when:
- huge scale required
- flexible schema needed
- distributed systems required

========================================================================
EXAMPLES
========================================================================

- Social media
- Chat systems
- Analytics systems

========================================================================
IMPORTANT DATABASE OPERATIONS
========================================================================

1. INSERT
2. SELECT
3. UPDATE
4. DELETE

========================================================================
COMMON SYSTEM DESIGN PROBLEM
========================================================================

Database becomes bottleneck
when traffic increases.

========================================================================

Suppose:
10 million users access system.

========================================================================

Single database may fail due to:
- high reads
- high writes
- heavy joins
- concurrent traffic

========================================================================
THIS LEADS TO ADVANCED CONCEPTS
========================================================================

1. Indexing
2. Replication
3. Sharding
4. Caching
5. Read replicas

========================================================================
INDEXING
========================================================================

Index improves data search speed.

========================================================================
WITHOUT INDEX
========================================================================

Database scans complete table.

========================================================================

Very slow for huge data.

========================================================================
WITH INDEX
========================================================================

Database finds data quickly.

========================================================================
IMPORTANT ANALOGY
========================================================================

Index in database is like:
book index page.

========================================================================

Instead of reading entire book,
you directly jump to topic.

========================================================================
REPLICATION
========================================================================

Copying database into multiple servers.

========================================================================
WHY REPLICATION?
========================================================================

1. High availability
2. Fault tolerance
3. Faster reads

========================================================================
EXAMPLE
========================================================================

One primary database
multiple read replicas.

========================================================================
SHARDING
========================================================================

Splitting huge database into smaller parts.

========================================================================
EXAMPLE
========================================================================

Users 1-1M
        ↓
Shard-1

========================================================================

Users 1M-2M
        ↓
Shard-2

========================================================================
WHY SHARDING?
========================================================================

Single database cannot handle
massive traffic forever.

========================================================================
IMPORTANT SYSTEM DESIGN UNDERSTANDING
========================================================================

Scaling database is MUCH HARDER
than scaling application servers.

========================================================================

Because:
data consistency is difficult.

========================================================================
READ HEAVY vs WRITE HEAVY SYSTEMS
========================================================================

Read Heavy
------------
More reads than writes.

Examples:
- YouTube
- Instagram feed

========================================================================

Write Heavy
-------------
Large write operations.

Examples:
- Chat systems
- Logging systems

========================================================================
DATABASE CONNECTION POOLING
========================================================================

Creating database connection is expensive.

========================================================================

Applications use:
        connection pools

========================================================================

Instead of:
creating new connection per request.

========================================================================
VERY IMPORTANT REAL WORLD FLOW
========================================================================

Client
   ↓
Load Balancer
   ↓
Application Server
   ↓
Cache
   ↓
Database

========================================================================

Later we study:
- Cache
- Redis
- Replication
- Sharding
- Distributed databases

========================================================================
IMPORTANT INTERVIEW QUESTIONS
========================================================================

1. Why database required?

→ Permanent and reliable data storage.

========================================================================

2. Difference between SQL and NoSQL?

SQL
-----
structured and consistent

NoSQL
-------
scalable and flexible

========================================================================

3. What is ACID?

→ Properties ensuring reliable transactions.

========================================================================

4. What is indexing?

→ Technique to improve query performance.

========================================================================

5. Why replication used?

→ High availability and faster reads.

========================================================================

6. What is sharding?

→ Splitting large database into smaller parts.

========================================================================

7. Why database scaling difficult?

→ Maintaining consistency across distributed data.

========================================================================

8. What is database bottleneck?

→ Database becoming slow due to huge traffic.

========================================================================
MOST IMPORTANT INTERVIEW LINE
========================================================================

Database is persistent storage layer
of backend systems responsible for
efficient, reliable, and scalable
data management.

========================================================================

*/