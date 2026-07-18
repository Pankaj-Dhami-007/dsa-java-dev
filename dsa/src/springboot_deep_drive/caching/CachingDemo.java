package springboot_deep_drive.caching;

/*
 * ============================================================================
 *  CACHING - COMPLETE INTERVIEW GUIDE (Entry Point)
 * ============================================================================
 *
 *  WHAT IS CACHING?
 *  ----------------
 *  Caching is storing frequently accessed data in a high-speed storage layer
 *  (memory) so future requests can be served faster than fetching from the
 *  original source (DB, API, disk).
 *
 *    DIAGRAM:
 *    ┌──────┐     Cache Hit    ┌───────────┐
 *    │Client│ ──────────────>  │   Cache   │ (RAM/Memory - FAST)
 *    │      │                  │ (Redis)   │
 *    │      │     Cache Miss   └─────┬─────┘
 *    │      │ ─────────────────────> │
 *    │      │                        v
 *    │      │                  ┌───────────┐
 *    │      │ <──────────────  │ Database  │ (Disk - SLOW)
 *    └──────┘   Returns data  └───────────┘
 *                 & populates
 *                   cache
 *
 * ============================================================================
 *  WHY CACHE? (INTERVIEW ANSWER)
 * ============================================================================
 *  1. Reduce latency       - Memory reads ~microseconds vs DB ~milliseconds
 *  2. Reduce DB load       - Fewer queries to database
 *  3. Improve throughput   - Serve more requests with same infrastructure
 *  4. Handle traffic spikes- Absorb sudden load without scaling DB
 *
 * ============================================================================
 *  KEY METRICS (INTERVIEW)
 * ============================================================================
 *  - Cache Hit Ratio  = Hits / (Hits + Misses)  — Aim > 90%
 *  - Latency savings  = (DB_time - Cache_time) * Hits
 *  - TTL              = Time-To-Live — how long data stays in cache
 *  - Eviction         = Removing old data when cache is full
 *
 * ============================================================================
 *  TOP 10 INTERVIEW Q&A (Entry Level)
 * ============================================================================
 *
 *  Q1: What is the difference between cache and database?
 *  A1: Cache is fast, volatile, small (RAM). DB is slow, persistent, large (Disk).
 *      Cache stores temporary copies; DB stores source of truth.
 *
 *  Q2: What is Cache Hit vs Cache Miss?
 *  A2: HIT  = Data found in cache → return fast (happy path)
 *      MISS = Data NOT in cache → fetch from DB, populate cache, return
 *
 *  Q3: What is TTL and why is it important?
 *  A3: TTL = Time-To-Live. Data expires after TTL, forcing a refresh.
 *      Prevents serving stale data. Balances freshness vs performance.
 *
 *  Q4: What are common cache eviction policies?
 *  A4: LRU (Least Recently Used), LFU (Least Frequently Used), FIFO, TTL
 *      See techniques/ package for implementations.
 *
 *  Q5: What is Cache Penetration?
 *  A5: When requests keep coming for data that DOESN'T EXIST in DB either.
 *      Every request misses cache AND hits DB → DB can crash.
 *      Solution: Cache null/empty values briefly, Bloom Filters.
 *
 *  Q6: What is Cache Avalanche?
 *  A6: Large number of cache keys expire SIMULTANEOUSLY → all requests go to DB.
 *      Solution: Randomize TTL, use caching warm-up, add rate limiting.
 *
 *  Q7: What is Cache Breakdown (Hotkey Invalidation)?
 *  A7: A SINGLE popular key expires → many concurrent requests hit DB.
 *      Solution: Mutex lock (only one thread loads, others wait), perpetual TTL.
 *
 *  Q8: What is Cache-Aside Pattern?
 *  A8: Application code manages cache. Check cache first → miss → load from DB
 *      → populate cache. Most common pattern.
 *
 *  Q9: What is Write-Through vs Write-Behind?
 *  A9: Write-Through: Write to cache AND DB synchronously (consistent, slower)
 *      Write-Behind: Write to cache, async write to DB later (fast, risk of loss)
 *
 *  Q10: When should you NOT use caching?
 *  A10: - Frequently changing data (stock prices every ms)
 *        - Small datasets where DB is fast enough
 *        - When strong consistency is required (banking transactions)
 *        - Data that must be 100% real-time (live sports scores)
 *
 * ============================================================================
 *  CACHING LAYERS IN SYSTEM DESIGN
 * ============================================================================
 *  ├── Browser Cache     (LocalStorage, Cache-Control headers)
 *  ├── CDN Cache         (CloudFront, Cloudflare, Akamai)
 *  ├── API Gateway Cache (Rate-limited responses)
 *  ├── Application Cache (In-memory: Caffeine, Guava, Ehcache)
 *  ├── Distributed Cache (Redis, Memcached)      ← Most asked in interviews
 *  └── Database Cache    (Buffer pool, query cache)
 *
 * ============================================================================
 *  REAL-WORLD EXAMPLES
 * ============================================================================
 *  - Amazon:          Product details cached in Redis (reduces DB load 90%)
 *  - Twitter:         Trending tweets in Memcached
 *  - YouTube:         Video metadata in CDN + Redis
 *  - Netflix:         Personalized recommendations cached with TTL
 *  - Uber:            Driver location cache (Redis Geo commands)
 *
 * ============================================================================
 *  COMMON CACHING TERMINOLOGY (INTERVIEW)
 * ============================================================================
 *  Cache Stampede / Thundering Herd:
 *    Many requests simultaneously miss → all hit DB. Prevent with locking.
 *
 *  Cache Warming:
 *    Pre-populating cache before traffic arrives (e.g., during deployment).
 *
 *  Stale Data:
 *    Cache has old version while DB has updated data. Acceptable for reads.
 *
 *  Write-Around Cache:
 *    Write directly to DB, invalidate cache entry. Next read will fetch fresh.
 */

public class CachingDemo {

    /*
     * ====================================================================
     *  PRACTICAL DEMO #1: Simulate a simple in-memory cache
     * ====================================================================
     *  Shows: Cache Hit, Cache Miss, TTL behavior, Eviction
     *
     *  INTERVIEW: "Design a simple cache with expiry and eviction"
     */
    public static void main(String[] args) {

        System.out.println("============================================");
        System.out.println("   CACHING DEMO - COMPLETE GUIDE");
        System.out.println("============================================");

        // Why Cache? Let's simulate the speed difference
        demoLatencyComparison();

        // -----------------------------------------
        // 1. SIMPLE CACHE DEMO (Manual HashMap approach)
        // -----------------------------------------
        System.out.println("\n=== 1. BASIC CACHE (Manual) ===");
        SimpleCache cache = new SimpleCache(3); // max 3 entries

        // Simulate DB fetch for first request
        System.out.println("Request 1: Get user_101");
        String user = cache.get("user_101");
        if (user == null) {
            // Cache MISS - fetch from DB
            user = fetchFromDatabase("user_101"); // simulated slow call
            cache.put("user_101", user);
            System.out.println("   [CACHE MISS] Populated from DB");
        } else {
            System.out.println("   [CACHE HIT] Returning cached value");
        }

        // Second request - should be cache HIT
        System.out.println("\nRequest 2: Get user_101 (again)");
        user = cache.get("user_101");
        if (user == null) {
            user = fetchFromDatabase("user_101");
            cache.put("user_101", user);
            System.out.println("   [CACHE MISS] Populated from DB");
        } else {
            System.out.println("   [CACHE HIT] " + user);
        }

        // Fill cache and trigger eviction
        cache.put("user_102", fetchFromDatabase("user_102"));
        cache.put("user_103", fetchFromDatabase("user_103"));
        cache.put("user_104", fetchFromDatabase("user_104")); // should evict oldest

        System.out.println("\nCache after adding 4 entries (capacity=3):");
        cache.display();

        // ====================================================================
        //  INTERVIEW TIP:
        //  The above is a SIMPLE FIFO cache. Real caches use LRU or LFU.
        //  We implement LRU, LFU, TTL, etc. in separate technique classes.
        // ====================================================================

        System.out.println("\n============================================");
        System.out.println("  WHAT WE WILL COVER:");
        System.out.println("============================================");
        System.out.println("  Next: CachingTechniques (strategies)");
        System.out.println("  Then: LRU, LFU, TTL Caches");
        System.out.println("  Then: Cache-Aside, Read/Write-Through");
        System.out.println("  Then: Distributed Caching & Consistent Hashing");
        System.out.println("  Then: Cache Penetration, Avalanche, Breakdown");
        System.out.println("  Then: Spring Boot @Cacheable Demo");
        System.out.println("  Then: System Design with Caching");
    }

    // Simulate a slow database call
    private static String fetchFromDatabase(String key) {
        try {
            Thread.sleep(100); // simulate 100ms DB latency
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Data_for_" + key + "_at_" + System.currentTimeMillis();
    }

    private static void demoLatencyComparison() {
        /*
         * DEMO: Cache vs DB latency comparison
         *
         *  Memory (RAM):               ~50-100 nanoseconds
         *  SSD:                        ~50-150 microseconds
         *  Network call to Redis:      ~0.5-2 milliseconds
         *  Database query (MySQL):     ~10-100 milliseconds
         *
         *  So Redis cache is ~50-100x faster than DB!
         */
        System.out.println("Latency Comparison (typical):");
        System.out.println("  Cache (RAM):        ~0.1 ms");
        System.out.println("  Redis (network):    ~1 ms");
        System.out.println("  Database (MySQL):   ~50 ms");
        System.out.println("  Benefit: Cache is ~50-500x faster");
    }

    /*
     * ====================================================================
     *  INNER CLASS: Simple FIFO Cache with capacity limit
     *  Responsibility: Demonstrate basic cache behavior
     * ====================================================================
     *
     *  Eviction Policy: FIFO (First-In-First-Out)
     *  When full, removes the oldest entry.
     *
     *  INTERVIEW: "Implement a simple cache with size limit"
     */
    static class SimpleCache {
        private final java.util.LinkedHashMap<String, String> map;
        private final int capacity;

        SimpleCache(int capacity) {
            this.capacity = capacity;
            // LinkedHashMap maintains insertion order → FIFO eviction
            this.map = new java.util.LinkedHashMap<String, String>(capacity, 0.75f, false) {
                @Override
                protected boolean removeEldestEntry(
                        java.util.Map.Entry<String, String> eldest) {
                    return size() > SimpleCache.this.capacity;
                }
            };
        }

        String get(String key) {
            return map.get(key);
        }

        void put(String key, String value) {
            map.put(key, value);
        }

        void display() {
            map.forEach((k, v) ->
                System.out.println("   " + k + " -> " + v));
        }
    }
}
