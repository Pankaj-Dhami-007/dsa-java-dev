package springboot_deep_drive.caching.interview;

/*
 * ============================================================================
 *  CACHING INTERVIEW QUESTIONS — COMPLETE Q&A
 *  Categorized by topic for quick reference
 * ============================================================================
 *
 *  HOW TO USE THIS:
 *    Each Q has: Question, Short Answer, Key Points to mention in interview
 *    TOPIC sections: Basics, Eviction, Patterns, Problems, Distributed,
 *    Spring, System Design, Redis-specific
 */

public class CachingInterviewQA {

    /*
     * ====================================================================
     *  SECTION 1: CACHING BASICS (FOUNDATION)
     * ====================================================================
     *
     * Q1: What is caching and why is it important?
     * A:  Storing frequently accessed data in fast storage (RAM) near the
     *     application. Reduces latency (memory: μs vs DB: ms), reduces DB
     *     load, improves throughput, handles traffic spikes.
     *
     * Q2: What is cache hit vs cache miss?
     * A:  HIT = data found in cache → fast return.
     *     MISS = data not found → fetch from DB → populate cache.
     *     Hit ratio = hits / (hits + misses). Target > 90%.
     *
     * Q3: What is TTL and why is it needed?
     * A:  Time-To-Live — how long an entry stays valid. Prevents stale data,
     *     auto-refreshes cache, prevents memory leak from unused entries.
     *
     * Q4: What's the difference between cache and database?
     * A:  Cache: fast, volatile, small (RAM), temporary copies, eventual
     *     consistency. DB: slow, persistent, large (disk), source of truth,
     *     strong consistency.
     *
     * Q5: When should you NOT use caching?
     * A:  - Frequently changing data (stock ticks)
     *     - Strong consistency required (banking)
     *     - Small datasets where DB is fast enough
     *     - 100% real-time requirements
     *
     * ====================================================================
     *  SECTION 2: EVICTION POLICIES
     * ====================================================================
     *
     * Q6: Explain LRU Cache.
     * A:  Evicts Least Recently Used item. HashMap + Doubly LinkedList.
     *     GET/PUT/EVICT all O(1). Used in Redis (allkeys-lru), CPU cache.
     *     Weakness: scan resistance — one-time bulk reads flush hot data.
     *
     * Q7: Explain LFU Cache.
     * A:  Evicts Least Frequently Used item. Tracks access count. Better
     *     for popularity patterns. Weakness: "celebrity problem" — once
     *     popular items never leave. Solution: frequency aging.
     *
     * Q8: LRU vs LFU — which one to choose?
     * A:  LRU for temporal locality (recently accessed → likely reused).
     *     LFU for frequency patterns (popular items stay popular).
     *     Real systems use hybrid (ARC, 2Q) or Redis with LFU+aging.
     *
     * Q9: What is FIFO cache?
     * A:  First-In-First-Out — evicts oldest inserted item. Simple queue.
     *     Ignores access patterns. Suffers from Belady's Anomaly (more
     *     space can increase miss rate).
     *
     * Q10: What is Belady's MIN algorithm?
     * A:  Theoretical optimal — evict item used farthest in future.
     *     Impractical (needs future knowledge). Used as benchmark.
     *
     * ====================================================================
     *  SECTION 3: CACHING PATTERNS
     * ====================================================================
     *
     * Q11: Explain Cache-Aside (Lazy Loading).
     * A:  App checks cache → miss → loads from DB → populates cache.
     *     On write: invalidate (delete) cache, not update.
     *     Most common pattern. Weakness: 3 network hops on miss, stale
     *     data race condition possible.
     *
     * Q12: Explain Read-Through Cache.
     * A:  Cache library auto-loads from DB on miss. App only talks to
     *     cache. Cleaner code but couples cache to DB loading logic.
     *
     * Q13: Explain Write-Through Cache.
     * A:  Write to cache + DB synchronously. Strong consistency but
     *     higher write latency (must wait for DB).
     *
     * Q14: Explain Write-Behind (Write-Back) Cache.
     * A:  Write to cache → return fast. Async batch write to DB later.
     *     Very fast writes, reduces DB load. Risk: data loss if cache
     *     crashes before flush. Used in analytics, clickstream.
     *
     * Q15: Explain Write-Around Cache.
     * A:  Write directly to DB (skip cache). Cache only populated on
     *     read miss. Prevents cache pollution from write-once-read-never
     *     data.
     *
     * Q16: What is Refresh-Ahead Cache?
     * A:  Proactively refreshes expiring entries before they expire.
     *     Predicts future accesses. Minimizes misses but complex and
     *     may waste resources on unused refreshes.
     *
     * ====================================================================
     *  SECTION 4: CACHING PROBLEMS (Critical)
     * ====================================================================
     *
     * Q17: What is Cache Penetration and how do you solve it?
     * A:  Requests for NON-EXISTENT data constantly bypass cache and
     *     hit DB. Solutions:
     *     1) Cache null values (short TTL)
     *     2) Bloom Filter (probabilistic "definitely not in DB" check)
     *     3) Parameter validation (reject invalid IDs early)
     *
     * Q18: What is Cache Avalanche and how do you solve it?
     * A:  Many keys EXPIRE SIMULTANEOUSLY → all hit DB. Solutions:
     *     1) Randomize TTL (jitter ±20%)
     *     2) Cache warming before peak traffic
     *     3) Circuit breaker (serve stale data on DB failure)
     *     4) Multi-level cache (L1 + L2)
     *     5) Redis persistence (RDB/AOF)
     *
     * Q19: What is Cache Breakdown (Hotkey) and how do you solve it?
     * A:  Single popular key EXPIRES → concurrent requests overwhelm DB.
     *     Solutions:
     *     1) Mutex lock (only 1 thread loads from DB)
     *     2) Perpetual TTL (background thread refreshes)
     *     3) Local cache + Redis (L1 survives expiry)
     *     4) Serve stale data while refreshing
     *
     * Q20: What is Cache Stampede (Thundering Herd)?
     * A:  Many requests simultaneously miss cache → all hit DB.
     *     Can happen from: avalanche, breakdown, or cache restart.
     *     Solution: locking, gradual refresh, stale-while-revalidate.
     *
     * ====================================================================
     *  SECTION 5: DISTRIBUTED CACHING
     * ====================================================================
     *
     * Q21: How do you distribute cache across multiple servers?
     * A:  Sharding — split data across servers.
     *     Naive: hash(key) % N (problem: N changes → all keys remap).
     *     Better: Consistent Hashing (only 1/N keys move).
     *
     * Q22: Explain Consistent Hashing.
     * A:  Servers placed on a hash ring (0 to 2^32-1). Each key goes to
     *     the next server clockwise. Adding/removing server only moves
     *     keys between adjacent servers. Virtual nodes ensure balanced
     *     distribution.
     *
     * Q23: Why do we need virtual nodes in consistent hashing?
     * A:  Without virtual nodes, servers may cluster on the ring causing
     *     uneven load distribution. Virtual nodes (100-200 per server)
     *     spread each server's responsibility across the ring → balanced.
     *
     * Q24: How does Redis Cluster handle sharding?
     * A:  16384 fixed hash slots (not a ring). hash(key) % 16384 →
     *     slot mapped to server. Slot migration enables resharding.
     *     Client can connect to any node; gets redirected if needed.
     *
     * Q25: What data would you store in a distributed cache vs local cache?
     * A:  Distributed (Redis): user sessions, product catalog, feed data
     *     (shared across instances). Local (Caffeine): config data,
     *     metadata, frequently accessed small data (instance-specific).
     *
     * ====================================================================
     *  SECTION 6: REDIS-SPECIFIC QUESTIONS
     * ====================================================================
     *
     * Q26: What Redis data structures are used for caching?
     * A:  String — simple key-value (most common)
     *     List — feed, queue
     *     Set — unique items, tags
     *     Sorted Set — leaderboard, trending (score-based)
     *     Hash — object fields (user profile)
     *     Geo — location-based queries (Uber)
     *
     * Q27: How does Redis handle TTL expiration?
     * A:  1) Lazy: check expiry on every key access
     *     2) Active: every 100ms, sample 20 random keys, delete expired,
     *        repeat if >25% expired
     *     3) When full: evict by policy (volatile-lru, allkeys-lru, etc.)
     *
     * Q28: What Redis eviction policies are available?
     * A:  volatile-lru, allkeys-lru, volatile-lfu, allkeys-lfu,
     *     volatile-random, allkeys-random, volatile-ttl, noeviction
     *     (default: noeviction — returns error when full)
     *
     * Q29: How do you use Redis as a distributed lock?
     * A:  SETNX key value — sets only if key doesn't exist.
     *     With TTL: SET key value NX EX 10 (auto-expire to avoid deadlock).
     *     For production: Redlock algorithm (quorum-based).
     *
     * Q30: Redis vs Memcached — which one for caching?
     * A:  Redis: rich data structures, persistence, replication, Lua scripting
     *     Memcached: simpler, multi-threaded, pure key-value, no persistence
     *     Choose Redis for complex apps, Memcached for simple key-value at scale
     *
     * ====================================================================
     *  SECTION 7: SPRING BOOT CACHING
     * ====================================================================
     *
     * Q31: What Spring Boot annotations are used for caching?
     * A:  @Cacheable — check cache, skip method on hit
     *     @CachePut — execute method + update cache
     *     @CacheEvict — remove from cache
     *     @Caching — combine multiple annotations
     *     @CacheConfig — class-level cache settings
     *     @EnableCaching — enable on @Configuration class
     *
     * Q32: How do you configure cache providers in Spring Boot?
     * A:  Add dependency + spring.cache.type (redis, caffeine, ehcache, simple)
     *     For Redis: spring-boot-starter-data-redis + RedisCacheManager config
     *     For Caffeine: caffeine dependency + CaffeineCacheManager
     *
     * Q33: How do you handle cache TTL in Spring Boot?
     * A:  In application.yml: spring.cache.redis.time-to-live=3600000
     *     Or programmatically: RedisCacheConfiguration.defaultCacheConfig()
     *         .entryTtl(Duration.ofHours(1))
     *
     * Q34: What is the proxy limitation of @Cacheable?
     * A:  Spring uses AOP proxies. methodA() calling methodB() in SAME class
     *     bypasses proxy → no caching. Solutions: self-inject, AspectJ mode,
     *     or restructure code.
     *
     * ====================================================================
     *  SECTION 8: SYSTEM DESIGN WITH CACHING
     * ====================================================================
     *
     * Q35: How would you design Twitter feed with caching?
     * A:  Fan-out on write: when user tweets, push tweet ID to all followers'
     *     feed cache (Redis list). For celebrities: fan-out on read (fetch
     *     live + merge). Feed cache capped at 800 entries, TTL 24h.
     *
     * Q36: How would you design YouTube with caching?
     * A:  Video content → CDN (CloudFront). Metadata → Redis (TTL 1h, sliding).
     *     Trending → Redis sorted set (score=views/hr, recompute every 5min).
     *     Recommendations → pre-computed, cached per user, TTL 1h.
     *
     * Q37: How would you design Uber with caching?
     * A:  Driver locations → Redis Geo sets (GEOADD/GEORADIUS).
     *     Updates every 3s → Redis (fast). Batch flush to DB every 5s
     *     (Write-Behind). If Redis crashes: acceptable loss (drivers reconnect).
     *
     * Q38: How would you design a URL shortener with caching?
     * A:  Multi-level cache: L1 (Caffeine, ~10μs) + L2 (Redis, ~1ms).
     *     L1 TTL < L2 TTL. On L1 miss → check L2 → if miss → DB.
     *     Cache-Aside pattern. Redirect target: < 10ms.
     *
     * ====================================================================
     *  SECTION 9: ADVANCED / CONCEPTUAL
     * ====================================================================
     *
     * Q39: What is cache coherence in distributed systems?
     * A:  Ensuring all cache nodes have consistent view of data.
     *     Protocols: Write-Invalidate (invalidate copies on write),
     *     Write-Update (update all copies). Invalidation is more common
     *     (less bandwidth).
     *
     * Q40: What is "stale data" and when is it acceptable?
     * A:  Cache has old version while DB has new data. Acceptable when:
     *     eventual consistency is OK (news feed, product catalog, trending)
     *     Not acceptable: banking, stock trading, medical data.
     *
     * Q41: What is a Bloom Filter and how is it used in caching?
     * A:  Probabilistic data structure. Tells if an item DEFINITELY does NOT
     *     exist (no false negatives). May say "exists" when it doesn't
     *     (false positives). Used to prevent cache penetration — reject
     *     non-existent keys before hitting DB/cache.
     *
     * Q42: What is cache warming?
     * A:  Pre-populating cache before traffic arrives. Strategies:
     *     1) Pre-load popular data on app startup
     *     2) Background jobs to refresh cache periodically
     *     3) "Lazy warming" — first request loads, subsequent requests benefit
     *
     * Q43: How do you measure cache performance?
     * A:  Hit ratio (>90% good, >95% excellent)
     *     Latency (p50/p99 of cache reads)
     *     Eviction rate (high → need more memory)
     *     Miss rate per second (spikes indicate problems)
     *     DB load reduction (before/after caching)
     *
     * Q44: What is the difference between write-invalidate and write-update?
     * A:  Invalidate: on write, mark cache entries as stale (simpler, lazy)
     *     Update: on write, push new data to all cache nodes (complex, eager)
     *     Most systems use invalidate (less bandwidth, eventual consistency).
     *
     * Q45: How do you handle cache in microservices?
     * A:  Each service can have its own cache (bounded context).
     *     Shared cache (Redis) for cross-service data (sessions, auth).
     *     Cache per service = independent scaling, no coupling.
     *     Shared cache = data duplication, but consistent view.
     */

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   CACHING INTERVIEW Q&A");
        System.out.println("   45 Questions — 9 Sections");
        System.out.println("============================================");

        printSection("SECTION 1: CACHING BASICS (Q1-Q5)", 1, 5);
        printSection("SECTION 2: EVICTION POLICIES (Q6-Q10)", 6, 10);
        printSection("SECTION 3: CACHING PATTERNS (Q11-Q16)", 11, 16);
        printSection("SECTION 4: CACHING PROBLEMS (Q17-Q20)", 17, 20);
        printSection("SECTION 5: DISTRIBUTED CACHING (Q21-Q25)", 21, 25);
        printSection("SECTION 6: REDIS-SPECIFIC (Q26-Q30)", 26, 30);
        printSection("SECTION 7: SPRING BOOT CACHING (Q31-Q34)", 31, 34);
        printSection("SECTION 8: SYSTEM DESIGN (Q35-Q38)", 35, 38);
        printSection("SECTION 9: ADVANCED (Q39-Q45)", 39, 45);

        System.out.println("\n============================================");
        System.out.println("  QUICK TIPS FOR INTERVIEW:");
        System.out.println("============================================");
        System.out.println("  START with: 'Caching stores frequently accessed");
        System.out.println("  data in memory to reduce latency and DB load.'");
        System.out.println("  MENTION: Hit ratio > 90%, TTL, eviction policy");
        System.out.println("  COVER: Penetration, Avalanche, Breakdown");
        System.out.println("  SHOW: You know both local AND distributed cache");
        System.out.println("  END with: Trade-offs (consistency vs performance)");
        System.out.println("============================================");
    }

    private static void printSection(String name, int start, int end) {
        System.out.println("\n  " + name);
        System.out.println("  " + "-".repeat(name.length()));
        System.out.println("  Questions " + start + "-" + end
            + " — read the source comments above for full answers.");
    }
}
