package springboot_deep_drive.caching.springboot;

/**
 * ============================================================================
 *  ⚡ QUICK REVISION — SPRING BOOT CACHING INTERVIEW GUIDE ⚡
 *  Most asked questions | Diagrams | Annotations explained | No code
 * ============================================================================
 *
 *  ┌─────────────────────────────────────────────────────────────────────┐
 *  │                         CACHING BASICS                             │
 *  └─────────────────────────────────────────────────────────────────────┘
 *
 *  WHAT IS CACHING?
 *  → Storing frequently accessed data in FAST storage (RAM)
 *  → Avoids slow DB/API calls
 *  → Speed: RAM ~0.1ms | Redis ~1ms | DB ~50ms
 *
 *  WHY CACHE?
 *  1. Reduce latency (50x faster)
 *  2. Reduce DB load (fewer queries)
 *  3. Handle traffic spikes
 *  4. Lower infrastructure costs
 *
 *  CACHE HIT vs MISS?
 *  → HIT = data found in cache = FAST
 *  → MISS = data not found = fetch from DB = SLOW
 *  → Target hit ratio > 90%
 *
 *  TTL (Time-To-Live)?
 *  → Auto-expires entries after set time
 *  → Prevents stale data
 *  → Prevents memory leak
 *
 *  EVICTION vs EXPIRY?
 *  → Eviction = cache FULL → remove oldest (LRU, LFU, FIFO)
 *  → Expiry = TTL reached → remove stale (time-based)
 *
 *  ┌─────────────────────────────────────────────────────────────────────┐
 *  │                LOCAL vs DISTRIBUTED CACHE                          │
 *  └─────────────────────────────────────────────────────────────────────┘
 *
 *  ┌──────────┬───────────────────┬──────────────────────┐
 *  │          │ LOCAL (Caffeine)  │ DISTRIBUTED (Redis)  │
 *  ├──────────┼───────────────────┼──────────────────────┤
 *  │ Speed    │ ~10μs             │ ~1ms                 │
 *  │ Shared   │ ❌ Single JVM    │ ✅ All instances     │
 *  │ Persist  │ ❌ Lost on rest   │ ✅ RDB/AOF           │
 *  │ Use for  │ Config, ref data  │ Sessions, catalog    │
 *  └──────────┴───────────────────┴──────────────────────┘
 *
 *  BEST: L1 (Caffeine) + L2 (Redis) HYBRID
 *
 *  ┌─────────────────────────────────────────────────────────────────────┐
 *  │                3 CRITICAL CACHING PROBLEMS                         │
 *  └─────────────────────────────────────────────────────────────────────┘
 *
 *  1. CACHE PENETRATION
 *     → Problem: Requesting NON-EXISTENT data → every request hits DB
 *     → Fix: Cache null values + Bloom Filter
 *     → Diagram: Req → Cache Miss → DB Miss → Repeat → DB Overload ❌
 *       Fix: Req → Cache (null) → Return null → NO DB HIT ✅
 *
 *  2. CACHE AVALANCHE
 *     → Problem: MANY keys expire at SAME TIME → all hit DB
 *     → Fix: Randomize TTL (±20% jitter) + Cache warming
 *     → Diagram: 12:00 → Keys A,B,C,D all expire → DB spike → Crash ❌
 *       Fix: A(12:05) B(12:12) C(12:18) D(12:25) → staggered ✅
 *
 *  3. CACHE BREAKDOWN (Hotkey)
 *     → Problem: ONE popular key expires → thousands hit DB
 *     → Fix: Mutex lock (only 1 thread loads) + Perpetual TTL
 *     → Diagram: 1000 reqs → 1 key miss → 1000 DB calls ❌
 *       Fix: 1 loads DB, 999 wait → read cache ✅
 *
 *  ┌─────────────────────────────────────────────────────────────────────┐
 *  │              CACHING PATTERNS (6 Total)                           │
 *  └─────────────────────────────────────────────────────────────────────┘
 *
 *  1. CACHE-ASIDE (Lazy Loading) ★ MOST COMMON
 *     → App checks cache → miss → DB → populate cache
 *     → On write: INVALIDATE (delete), don't update
 *
 *  2. READ-THROUGH
 *     → Cache auto-loads from DB on miss
 *     → App only talks to cache
 *
 *  3. WRITE-THROUGH
 *     → Write to cache + DB SYNCHRONOUSLY
 *     → Strong consistency, slower writes
 *
 *  4. WRITE-BEHIND (Write-Back)
 *     → Write to cache → return IMMEDIATELY
 *     → Async batch write to DB later
 *     → Fast writes, risk of data loss
 *
 *  5. WRITE-AROUND
 *     → Write directly to DB (skip cache)
 *     → Cache only on read miss
 *     → Avoids cache pollution
 *
 *  6. REFRESH-AHEAD
 *     → Proactively refresh expiring entries
 *     → Predicts future access, minimizes misses
 *
 *  ┌─────────────────────────────────────────────────────────────────────┐
 *  │        EVICTION POLICIES (How to remove when full)                │
 *  └─────────────────────────────────────────────────────────────────────┘
 *
 *  LRU  → Evicts Least Recently Used item
 *  LFU  → Evicts Least Frequently Used item
 *  FIFO → Evicts First-In-First-Out (oldest inserted)
 *  TTL  → Evicts expired items (time-based)
 *  MRU  → Evicts Most Recently Used (rare)
 *  RR   → Random Replacement (surprisingly effective)
 *
 *  ┌───────────────────┬───────────────────────────────────────────────┐
 *  │ Use temporal loc  │ LRU (news feed, recent items)                │
 *  │ Use popularity    │ LFU (viral content, trending)                │
 *  │ Use simple FIFO   │ FIFO (logs, equal-value data)                │
 *  └───────────────────┴───────────────────────────────────────────────┘
 *
 *  ┌─────────────────────────────────────────────────────────────────────┐
 *  │           CONSISTENT HASHING (Distributed Caching)                │
 *  └─────────────────────────────────────────────────────────────────────┘
 *
 *  PROBLEM: hash(key) % N → adding/removing server remaps ALL keys
 *  SOLUTION: Hash ring → only 1/N keys move
 *
 *  DIAGRAM:
 *       0
 *   ┌────┐──── Server A
 *   │         │
 *   │   RING  │   Key → next server clockwise
 *   │         │
 *   └────┘──── Server B
 *      Server C
 *
 *  Virtual nodes: 100-200 per server → balanced distribution
 *
 *  ┌─────────────────────────────────────────────────────────────────────┐
 *  │        SPRING BOOT CACHING ANNOTATIONS (Complete Reference)       │
 *  └─────────────────────────────────────────────────────────────────────┘
 *
 *  ──────────────────────────────────────────────────────────────────────
 *  @EnableCaching
 *  ──────────────────────────────────────────────────────────────────────
 *  → WHAT: Activates Spring's cache management system
 *  → WHERE: On @Configuration or @SpringBootApplication class
 *  → HOW: Creates AOP proxy around @Cacheable beans
 *  → INTERVIEW: "One line enables all caching — Spring auto-configures
 *     CacheManager, creates interceptors, and weaves AOP proxies."
 *
 *  ──────────────────────────────────────────────────────────────────────
 *  @Cacheable                          ★ MOST IMPORTANT
 *  ──────────────────────────────────────────────────────────────────────
 *  → WHAT: Checks cache BEFORE method. If hit → skip method.
 *  → WHERE: On READ methods (getById, findByEmail)
 *  → ATTRIBUTES:
 *     value/cacheNames  → Cache name (required)
 *     key               → SpEL key expression (default: all params)
 *     condition         → Cache ONLY if true (before method)
 *     unless            → DON'T cache if true (after method)
 *     sync              → Lock on miss (prevents thundering herd)
 *     cacheManager      → Specific cache manager bean
 *     keyGenerator      → Custom key generator bean
 *
 *  → EXAMPLES:
 *     @Cacheable("users")                        → key = all params
 *     @Cacheable(value="users", key="#id")       → key = id param
 *     @Cacheable(value="users", sync=true)       → thundering herd protection
 *     @Cacheable(condition="#id>0")              → only cache valid ids
 *     @Cacheable(unless="#result==null")         → don't cache null
 *     @Cacheable({"caffeine","redis"})           → L1 + L2 hybrid
 *
 *  → FLOW:
 *     Call method
 *       → Generate key (e.g., "users::1")
 *       → condition true? NO → execute method (no cache)
 *       → condition true? YES
 *         → Key in cache? YES → return cached (method SKIPPED)
 *         → Key in cache? NO → execute method
 *           → unless true? YES → return result (no cache)
 *           → unless true? NO → store in cache → return result
 *
 *  → INTERVIEW: "@Cacheable is for READ operations. It checks cache
 *     first. On HIT the method is NEVER executed. On MISS, it executes
 *     and stores the result. sync=true prevents thundering herd."
 *
 *  ──────────────────────────────────────────────────────────────────────
 *  @CachePut
 *  ──────────────────────────────────────────────────────────────────────
 *  → WHAT: ALWAYS executes method AND updates cache
 *  → WHERE: On WRITE methods (save, update)
 *  → ATTRIBUTES: Same as @Cacheable (value, key, condition, unless)
 *
 *  → EXAMPLES:
 *     @CachePut(value="users", key="#user.id")     → update cache on save
 *     @CachePut(key="#result.id")                  → key from return value
 *     @CachePut(unless="#result==null")            → don't cache null saves
 *
 *  → VS @Cacheable:
 *     @Cacheable → skip on hit (READ)
 *     @CachePut  → always execute (WRITE)
 *
 *  → INTERVIEW: "@CachePut is for WRITE operations. It ALWAYS executes
 *     the method AND updates the cache with the result. Use for save
 *     and update operations where you know the new value."
 *
 *  ──────────────────────────────────────────────────────────────────────
 *  @CacheEvict
 *  ──────────────────────────────────────────────────────────────────────
 *  → WHAT: Removes entries from cache (invalidation)
 *  → WHERE: On DELETE methods, bulk updates
 *  → ATTRIBUTES:
 *     value/cacheNames  → Cache name (required)
 *     key               → Specific entry to remove
 *     allEntries        → Clear ENTIRE cache (default false)
 *     beforeInvocation  → Evict BEFORE method (default false)
 *     condition         → Only evict if true
 *
 *  → EXAMPLES:
 *     @CacheEvict(value="users", key="#id")             → evict single entry
 *     @CacheEvict(value="users", allEntries=true)       → clear all users
 *     @CacheEvict(beforeInvocation=true)                → evict even if fails
 *     @CacheEvict(allEntries=true) + @Scheduled(cron)   → nightly refresh
 *
 *  → VS @CachePut:
 *     @CachePut → update cache with new value
 *     @CacheEvict → remove from cache (next read refetches)
 *
 *  → INTERVIEW: "@CacheEvict removes stale entries. Use for DELETE
 *     operations or bulk updates. allEntries=true clears entire cache.
 *     beforeInvocation=true evicts even if the method throws."
 *
 *  ──────────────────────────────────────────────────────────────────────
 *  @Caching
 *  ──────────────────────────────────────────────────────────────────────
 *  → WHAT: Groups MULTIPLE cache annotations on ONE method
 *  → WHERE: When one operation affects multiple caches/keys
 *  → ATTRIBUTES: put[], evict[], cacheable[]
 *
 *  → EXAMPLES:
 *     @Caching(put = {
 *         @CachePut(key="#result.id"),
 *         @CachePut(key="#result.email")
 *     })
 *     → Save user: cache by id AND email
 *
 *     @Caching(evict = {
 *         @CacheEvict(key="#id"),
 *         @CacheEvict(cacheNames="search", allEntries=true)
 *     })
 *     → Delete: evict user cache + clear search cache
 *
 *  → INTERVIEW: "@Caching lets you apply multiple cache annotations
 *     when one operation affects multiple cache entries."
 *
 *  ──────────────────────────────────────────────────────────────────────
 *  @CacheConfig
 *  ──────────────────────────────────────────────────────────────────────
 *  → WHAT: Class-level cache defaults (DRY)
 *  → WHERE: On @Service class
 *  → ATTRIBUTES: cacheNames, keyGenerator, cacheManager
 *
 *  → EXAMPLE:
 *     @Service
 *     @CacheConfig(cacheNames = "users")
 *     public class UserService {
 *         @Cacheable(key="#id")    // inherits cacheNames="users"
 *         @CachePut(key="#u.id")   // inherits cacheNames="users"
 *     }
 *
 *  → INTERVIEW: "@CacheConfig reduces repetition. Set cacheNames
 *     once at class level instead of on every method."
 *
 *  ┌─────────────────────────────────────────────────────────────────────┐
 *  │              SPRING BOOT CACHE PROVIDERS                          │
 *  └─────────────────────────────────────────────────────────────────────┘
 *
 *  ┌──────────┬──────────────┬───────────┬────────────────────────────┐
 *  │ Provider │ Dependency   │ Best For  │ Notes                      │
 *  ├──────────┼──────────────┼───────────┼────────────────────────────┤
 *  │ Caffeine │ caffeine     │ Local     │ ★ FASTEST (10μs)           │
 *  │ Redis    │ data-redis   │ Shared    │ ★ Standard for production  │
 *  │ Simple   │ none (built) │ Dev only  │ ConcurrentHashMap, no TTL  │
 *  │ EHCache  │ ehcache      │ Legacy    │ XML config, disk overflow  │
 *  └──────────┴──────────────┴───────────┴────────────────────────────┘
 *
 *  ┌─────────────────────────────────────────────────────────────────────┐
 *  │              REDIS KEY POINTS (Quick Facts)                       │
 *  └─────────────────────────────────────────────────────────────────────┘
 *
 *  → In-memory data structure store (not just cache!)
 *  → 10+ data types: String, List, Set, Sorted Set, Hash, Geo, Streams
 *  → Persistence: RDB (snapshot) + AOF (write log)
 *  → Deployment: Standalone | Sentinel (HA) | Cluster (sharding)
 *  → Client: Lettuce (default, async) vs Jedis (blocking)
 *  → Serialization: ALWAYS use JSON (avoid JDK serialization!)
 *  → Spring: RedisTemplate + @Cacheable(RedisCacheManager)
 *  → Distributed locks: SET key value NX EX (SETNX)
 *  → Monitor: INFO command, RedisInsight, Grafana
 *
 *  ┌─────────────────────────────────────────────────────────────────────┐
 *  │              TOP 10 INTERVIEW QUESTIONS (Short Answers)           │
 *  └─────────────────────────────────────────────────────────────────────┘
 *
 *  Q1: How does Spring Boot caching work?
 *  A: @EnableCaching creates AOP proxy. @Cacheable checks cache via
 *     CacheManager. On HIT → return cached (skip method).
 *     On MISS → execute, store, return.
 *
 *  Q2: @Cacheable vs @CachePut?
 *  A: @Cacheable = READ (may skip method on hit).
 *     @CachePut = WRITE (always executes + updates cache).
 *
 *  Q3: condition vs unless?
 *  A: condition = before method (decide if cache is checked).
 *     unless = after method (decide if result is stored).
 *
 *  Q4: What is sync=true?
 *  A: Key-level locking. Only 1 thread loads on cache miss.
 *     Others wait → prevents thundering herd.
 *
 *  Q5: Why Caffeine over Guava?
 *  A: 5-10x faster, 30% less memory, Window TinyLfu > LRU,
 *     actively maintained (Guava is deprecated).
 *
 *  Q6: expireAfterWrite vs expireAfterAccess?
 *  A: Write = fixed TTL from creation.
 *     Access = sliding TTL (extends on every read).
 *
 *  Q7: Same-class proxy limitation?
 *  A: Internal method calls bypass AOP proxy → no caching.
 *     Fix: self-inject or extract to separate bean.
 *
 *  Q8: Cache Penetration?
 *  A: Non-existent data → every request hits DB.
 *     Fix: cache null values + Bloom Filter.
 *
 *  Q9: Cache Avalanche?
 *  A: Mass TTL expiry → all hit DB.
 *     Fix: randomize TTL + cache warming + circuit breaker.
 *
 *  Q10: Cache Breakdown?
 *  A: Single hot key expires → concurrent requests overwhelm DB.
 *     Fix: mutex lock + perpetual TTL + L1 cache.
 *
 *  ┌─────────────────────────────────────────────────────────────────────┐
 *  │              SpEL KEY EXPRESSIONS (Quick Ref)                     │
 *  └─────────────────────────────────────────────────────────────────────┘
 *
 *  key="#userId"                    → Parameter name
 *  key="#user.id"                   → Object field
 *  key="#root.methodName"           → Method name
 *  key="#root.args[0]"              → First argument
 *  key="#result.id"                 → Return value field (only in @CachePut)
 *  key="'users:' + #id"             → String concatenation
 *  key="T(java.lang.Math).random()" → Static method
 *
 *  ┌─────────────────────────────────────────────────────────────────────┐
 *  │              SYSTEM DESIGN - Caching Strategy                      │
 *  └─────────────────────────────────────────────────────────────────────┘
 *
 *  TWITTER FEED:
 *  → Fan-out on write: push tweet to all followers' feed caches
 *  → Celebrities: fan-out on read (fetch live + merge)
 *  → Cache: Redis lists, capped at 800, TTL 24h
 *
 *  YOUTUBE:
 *  → Video: CDN (not app cache)
 *  → Metadata: Redis, TTL 1h sliding
 *  → Trending: Redis sorted set (score=views/hr, recompute 5min)
 *
 *  UBER:
 *  → Driver locations: Redis Geo sets
 *  → Update every 3s → Redis. Batch flush to DB every 5s
 *  → Write-Behind pattern (fast writes, acceptable loss)
 *
 *  URL SHORTENER:
 *  → L1 (Caffeine, 10μs) + L2 (Redis, 1ms)
 *  → Cache-Aside: check L1 → L2 → DB
 *  → L1 TTL < L2 TTL (L1 expires first, refreshes from L2)
 *
 *  ┌─────────────────────────────────────────────────────────────────────┐
 *  │         INTERVIEW ANSWER SCRIPT - "Implement Caching"             │
 *  └─────────────────────────────────────────────────────────────────────┘
 *
 *  "I add spring-boot-starter-cache + caffeine dependencies.
 *   @EnableCaching activates AOP proxies for cache interception.
 *
 *   I configure CaffeineCacheManager with maximumSize, TTL, and
 *   recordStats for monitoring.
 *
 *   On service methods:
 *   → @Cacheable for reads (checks cache, skips on hit)
 *   → @CachePut for writes (always executes + caches result)
 *   → @CacheEvict for deletes (removes stale entries)
 *   → @Caching when one operation affects multiple caches
 *
 *   I use sync=true on expensive methods to prevent thundering herd.
 *   I monitor hit ratio via Micrometer, targeting > 90%.
 *
 *   For distributed caching, I replace Caffeine with Redis —
 *   same annotations, just swap the CacheManager.
 *   Redis is shared across all instances and survives restarts."
 *
 *  ┌─────────────────────────────────────────────────────────────────────┐
 *  │         QUICK TIPS — DO's and DON'Ts                              │
 *  └─────────────────────────────────────────────────────────────────────┘
 *
 *  ✅ DO:
 *  - Cache only HOT data (frequently read, rarely written)
 *  - Set TTL for every cache entry
 *  - Monitor hit ratio (> 90%)
 *  - Use sync=true for expensive operations
 *  - Override serialization to JSON (Redis)
 *  - Handle cache failure gracefully (fallback to DB)
 *
 *  ❌ DON'T:
 *  - Cache sensitive data (passwords, PII)
 *  - Use @Cacheable on void methods
 *  - Call @Cacheable methods internally in same class
 *  - Use default JDK serialization (Redis)
 *  - Cache everything (only cache what's needed)
 *  - Forget to evict/update cache on writes
 *
 *  ============================================================================
 *  END OF QUICK REVISION GUIDE — Good luck with your interviews! 🚀
 *  ============================================================================
 */

public class QuickRevisionInterviewGuide {

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   ⚡ SPRING BOOT CACHING - QUICK REVISION");
        System.out.println("   Interviews | Annotations | Diagrams");
        System.out.println("============================================");
        System.out.println("  This file contains the COMPLETE quick");
        System.out.println("  revision guide in the comments above.");
        System.out.println("  Open this file and read the Javadoc");
        System.out.println("  comments for:");
        System.out.println("    → All 6 caching annotations explained");
        System.out.println("    → 10 interview Q&A (short answers)");
        System.out.println("    → 3 critical problems (diagrams)");
        System.out.println("    → 6 caching patterns");
        System.out.println("    → Eviction policies comparison");
        System.out.println("    → Local vs Redis comparison table");
        System.out.println("    → System design (Twitter,YouTube,Uber)");
        System.out.println("    → DOs and DON'Ts");
        System.out.println("    → Interview answer script");
        System.out.println("============================================");
        System.out.println("  Read the comments above the class for");
        System.out.println("  the FULL revision guide.");
        System.out.println("============================================");
    }
}
