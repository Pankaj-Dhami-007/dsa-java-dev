package java_recap.springboot.annotations.performance;

/*

=================================================================
            SPRING CACHE ANNOTATIONS
=================================================================

Annotations:
-------------
@EnableCaching
@Cacheable
@CachePut
@CacheEvict

Core Concepts:
---------------
1. Caching
2. Performance Optimization
3. Redis Integration
4. Memory Storage
5. AOP Interception
6. Cache Lifecycle
7. Distributed Caching

=================================================================
                WHY CACHING EXISTS?
=================================================================

Imagine API:

GET /products/1

=================================================================
                FLOW
=================================================================

Request
   ↓
Database Query
   ↓
Response Returned

=================================================================
                PROBLEM
=================================================================

If 1 million users request same product:
-----------------------------------------

Database hit happens 1 million times.

=================================================================
                RESULT
=================================================================

1. Slow performance
2. High DB load
3. Increased latency
4. Scalability problems

=================================================================
                SOLUTION
=================================================================

Caching.

=================================================================
                WHAT IS CACHE?
=================================================================

Cache =
--------
Temporary fast storage
for frequently accessed data.

=================================================================
                SIMPLE MEANING
=================================================================

Instead of hitting database repeatedly,
reuse already fetched data.

=================================================================
                FLOW WITH CACHE
=================================================================

First Request
    ↓
DB Query Executes
    ↓
Data stored in cache

Next Requests
    ↓
Directly return cached data

=================================================================
                HUGE BENEFITS
=================================================================

1. Faster response
2. Reduced DB load
3. Better scalability
4. Lower latency

=================================================================
                REAL PROJECT USE CASES
=================================================================

1. Product catalog
2. User profile
3. Game leaderboard
4. Configurations
5. Exchange rates
6. API responses

=================================================================
                TYPES OF CACHE
=================================================================

1. In-memory Cache
2. Distributed Cache

=================================================================
                IN-MEMORY CACHE
=================================================================

Stored inside application memory.

Examples:
----------

ConcurrentHashMap
Caffeine Cache

=================================================================
                PROBLEM
=================================================================

Not shared across servers.

=================================================================
                DISTRIBUTED CACHE
=================================================================

Shared external cache.

=================================================================
                MOST USED
=================================================================

Redis

=================================================================
                WHY REDIS POPULAR?
=================================================================

1. Extremely fast
2. In-memory
3. Distributed
4. TTL support
5. Pub/Sub support

=================================================================
                1. @EnableCaching
=================================================================

Main annotation enabling caching.

=================================================================
                DEFINITION
=================================================================

@EnableCaching tells Spring:

"Enable cache infrastructure."

=================================================================
                EXAMPLE
=================================================================

@Configuration
@EnableCaching
public class CacheConfig {

}

=================================================================
                WHAT HAPPENS INTERNALLY?
=================================================================

Spring enables:
----------------

1. Cache interceptors
2. Cache manager
3. AOP proxies

=================================================================
                VERY IMPORTANT
=================================================================

Spring Cache works internally using:
-------------------------------------

AOP Proxies

=================================================================
                FLOW
=================================================================

Method Call
    ↓
Proxy Intercepts
    ↓
Checks cache
    ↓
Cache hit/miss decision

=================================================================
                2. @Cacheable
=================================================================

Most important cache annotation.

=================================================================
                DEFINITION
=================================================================

@Cacheable tells Spring:

"Store method result in cache."

=================================================================
                EXAMPLE
=================================================================

@Cacheable("products")

public Product getProduct(Long id) {

    return repo.findById(id);

}

=================================================================
                FIRST REQUEST
=================================================================

Cache MISS
    ↓
Method Executes
    ↓
DB Query Executes
    ↓
Result Stored in Cache

=================================================================
                NEXT REQUEST
=================================================================

Cache HIT
    ↓
Data returned directly from cache
    ↓
Method NOT executed

=================================================================
                IMPORTANT
=================================================================

Huge performance improvement.

=================================================================
                CACHE KEY
=================================================================

By default:
------------

Method parameters used as key.

=================================================================
                EXAMPLE
=================================================================

getProduct(1)

Cache key:
------------

1

=================================================================
                CUSTOM KEY
=================================================================

@Cacheable(
    value = "products",
    key = "#id"
)

=================================================================
                SpEL
=================================================================

Spring Expression Language.

=================================================================
                EXAMPLE
=================================================================

key = "#user.id"

=================================================================
                CONDITION CACHING
=================================================================

@Cacheable(
   condition = "#id > 10"
)

=================================================================
                MEANING
=================================================================

Cache only if condition true.

=================================================================
                UNLESS
=================================================================

@Cacheable(
   unless = "#result == null"
)

=================================================================
                MEANING
=================================================================

Do NOT cache null results.

=================================================================
                REAL PROJECT USE CASE
=================================================================

1. Product APIs
2. Currency exchange rates
3. User settings
4. Frequently viewed data

=================================================================
                3. @CachePut
=================================================================

Used for cache update.

=================================================================
                IMPORTANT DIFFERENCE
=================================================================

@Cacheable:
-------------
May skip method execution.

@CachePut:
------------
ALWAYS executes method.

=================================================================
                EXAMPLE
=================================================================

@CachePut(
    value = "products",
    key = "#product.id"
)

public Product updateProduct(
        Product product
) {

    return repo.save(product);

}

=================================================================
                WHAT HAPPENS?
=================================================================

Method executes
        ↓
Updated result stored in cache

=================================================================
                WHY IMPORTANT?
=================================================================

Keeps cache synchronized with DB.

=================================================================
                REAL PROJECT USE CASE
=================================================================

Update APIs:
-------------

1. Product update
2. User profile update
3. Game settings update

=================================================================
                4. @CacheEvict
=================================================================

Used for removing cache entries.

=================================================================
                WHY NEEDED?
=================================================================

Cached data can become stale.

=================================================================
                EXAMPLE
=================================================================

@CacheEvict(
    value = "products",
    key = "#id"
)

public void deleteProduct(Long id) {

    repo.deleteById(id);

}

=================================================================
                WHAT HAPPENS?
=================================================================

Cache entry removed after method execution.

=================================================================
                WHY IMPORTANT?
=================================================================

Prevents stale/outdated data.

=================================================================
                REMOVE ALL CACHE
=================================================================

@CacheEvict(
    value = "products",
    allEntries = true
)

=================================================================
                USE CASE
=================================================================

Mass updates/config refresh.

=================================================================
                CACHE FLOW
=================================================================

Request
   ↓
Proxy Intercepts
   ↓
Cache Checked
   ↓
Cache HIT?
   ↓ YES → Return Cached Data
   ↓ NO
Method Executes
   ↓
Result Cached
   ↓
Return Response

=================================================================
                CACHE MANAGER
=================================================================

Central component managing caches.

=================================================================
                RESPONSIBILITIES
=================================================================

1. Cache creation
2. Cache retrieval
3. Cache eviction
4. TTL handling

=================================================================
                COMMON CACHE PROVIDERS
=================================================================

1. ConcurrentMap
2. Caffeine
3. Redis
4. EhCache

=================================================================
                REDIS CACHE FLOW
=================================================================

Application
    ↓
Redis Cache
    ↓
Database

=================================================================
                TTL (TIME TO LIVE)
=================================================================

Defines cache expiry time.

=================================================================
                WHY IMPORTANT?
=================================================================

Avoid stale data forever.

=================================================================
                EXAMPLE
=================================================================

Product cache expires after:
-----------------------------

10 minutes

=================================================================
                CACHE HIT
=================================================================

Data found in cache.

=================================================================
                CACHE MISS
=================================================================

Data NOT found.

=================================================================
                CACHE PENETRATION
=================================================================

Repeated requests for invalid data.

=================================================================
                CACHE STAMPEDE
=================================================================

Multiple requests rebuilding cache simultaneously.

=================================================================
                CACHE CONSISTENCY PROBLEM
=================================================================

DB updated
BUT
cache not updated.

=================================================================
                RESULT
=================================================================

Users see old data.

=================================================================
                SOLUTION
=================================================================

@CachePut
or
@CacheEvict

=================================================================
                IMPORTANT LIMITATION
=================================================================

Self-invocation issue exists here too.

=================================================================
                WHY?
=================================================================

Caching uses AOP proxies internally.

=================================================================
                EXAMPLE
=================================================================

methodA()
   ↓
calls methodB()

@Cacheable on methodB may fail.

=================================================================
                INTERVIEW IMPORTANT
=================================================================

Q:
---
How @Cacheable works internally?

=================================================================
                ANSWER
=================================================================

Spring creates AOP proxy around bean.
Proxy intercepts method call,
checks cache first,
returns cached result if present,
otherwise executes method and stores result.

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
Difference between @Cacheable
and @CachePut?

=================================================================
                ANSWER
=================================================================

@Cacheable:
-------------
Skips method execution if cache hit.

@CachePut:
------------
Always executes method
and updates cache.

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
Why distributed cache needed?

=================================================================
                ANSWER
=================================================================

In multi-server systems,
local cache not shared.
Distributed cache like Redis
provides shared cache across servers.

=================================================================
                TRICKY INTERVIEW QUESTION
=================================================================

Q:
---
Does cache replace database?

NO.

=================================================================
                WHY?
=================================================================

Cache temporary storage only.
Database remains source of truth.

=================================================================
                ANOTHER TRICKY QUESTION
=================================================================

Q:
---
Why stale cache problem occurs?

=================================================================
                ANSWER
=================================================================

Because cached data may not update
after database changes.

=================================================================
                COMMON MISTAKES
=================================================================

1. Caching everything

2. Missing cache eviction

3. Huge object caching

4. No TTL configuration

5. Ignoring distributed systems

=================================================================
                BEST PRACTICE
=================================================================

1. Cache frequently read data
2. Use Redis for distributed apps
3. Configure TTL carefully
4. Evict/update stale cache properly
5. Monitor cache hit ratio

=================================================================
                REAL INTERVIEW ANSWER
=================================================================

Q. Explain complete flow of @Cacheable.

Answer:
--------
1. Client calls cached method
2. Spring proxy intercepts call
3. Cache manager checks cache
4. If cache hit → return cached result
5. If cache miss → execute method
6. Store result in cache
7. Return response

=================================================================
                SUMMARY
=================================================================

@EnableCaching
----------------
Enables cache infrastructure.

@Cacheable
------------
Stores method result in cache.

@CachePut
-----------
Updates cache after execution.

@CacheEvict
-------------
Removes stale cache entries.

Spring Cache internally uses:
------------------------------

1. AOP proxies
2. Cache interceptors
3. Cache managers

=================================================================

*/

public class CacheAnnotationsNotes {
}