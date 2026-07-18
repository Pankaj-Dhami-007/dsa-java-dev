package springboot_deep_drive.caching.springboot.local;

/*
 * ============================================================================
 *  CAFFEINE CACHE — CONFIGURATION DEEP DIVE
 * ============================================================================
 *
 *  SIMPLE DEFINITION:
 *  Caffeine is the FASTEST Java in-memory cache library. It's the
 *  successor to Guava Cache. Spring Boot integrates it automatically
 *  when caffeine is on the classpath.
 *
 *  REAL-LIFE ANALOGY:
 *  Caffeine is like a professional organizer for your desk. It decides:
 *  - How many items fit (maximumSize)
 *  - When to throw old items out (expireAfterWrite)
 *  - When to refresh items before they expire (refreshAfterWrite)
 *  - What to track for performance metrics (recordStats)
 *
 * ============================================================================
 *  WHY CAFFEINE? (vs Guava, EHCache, Simple)
 * ============================================================================
 *
 *  ┌──────────────┬──────────┬──────────┬──────────┬──────────┐
 *  │ Feature      │ Caffeine │ Guava    │ EHCache  │ Simple   │
 *  ├──────────────┼──────────┼──────────┼──────────┼──────────┤
 *  │ Performance  │ ★★★★★   │ ★★★★    │ ★★★     │ ★★       │
 *  │ Memory (off- │ ✅       │ ❌       │ ✅       │ ❌       │
 *  │ heap)        │          │          │          │          │
 *  │ TTL          │ ✅       │ ✅       │ ✅       │ ❌       │
 *  │ Sliding TTL  │ ✅       │ ✅       │ ✅       │ ❌       │
 *  │ Async Load   │ ✅       │ ❌       │ ✅       │ ❌       │
 *  │ Stats        │ ✅       │ ✅       │ ✅       │ ❌       │
 *  │ XML Config   │ ❌       │ ❌       │ ✅       │ ❌       │
 *  │ Spring Boot  │ Auto     │ Manual   │ Auto     │ Default  │
 *  └──────────────┴──────────┴──────────┴──────────┴──────────┘
 *
 *  Caffeine is 5-10x faster than Guava and uses 30% less memory.
 *  It uses Window TinyLfu eviction policy (better than LRU).
 *
 * ============================================================================
 *  CAFFEINE BUILDER OPTIONS (Full Reference)
 * ============================================================================
 *
 *  ┌──────────────────────┬─────────────────────────────────────────────┐
 *  │ Option               │ What it does                               │
 *  ├──────────────────────┼─────────────────────────────────────────────┤
 *  │ maximumSize(N)       │ Max entries in cache (most common)         │
 *  │ maximumWeight(N)     │ Max weight (custom weight function)        │
 *  │ expireAfterWrite(D)  │ Entry expires N after creation/last update │
 *  │ expireAfterAccess(D) │ Entry expires N after last read/write     │
 *  │ refreshAfterWrite(D) │ Refresh entry BEFORE expiry (async)        │
 *  │ weakKeys()           │ Use WeakReference for keys                 │
 *  │ weakValues()         │ Use WeakReference for values               │
 *  │ softValues()         │ Use SoftReference for values               │
 *  │ recordStats()        │ Enable hit/miss/eviction statistics        │
 *  │ removalListener(L)   │ Callback when entry is evicted/expired     │
 *  │ writer(W)            │ Write-through to external store            │
 *  │ executor(E)          │ Custom thread pool for async operations    │
 *  │ ticker(T)            │ Custom time source (for testing)           │
 *  └──────────────────────┴─────────────────────────────────────────────┘
 *
 * ============================================================================
 *  RECOMMENDED CONFIGURATIONS (by use case)
 * ============================================================================
 *
 *  1. PRODUCT CATALOG (reads >> writes, high volume):
 *     Caffeine.newBuilder()
 *         .maximumSize(50_000)
 *         .expireAfterWrite(1, TimeUnit.HOURS)
 *         .recordStats()
 *
 *  2. USER SESSIONS (short-lived, sliding expiry):
 *     Caffeine.newBuilder()
 *         .maximumSize(100_000)
 *         .expireAfterAccess(30, TimeUnit.MINUTES)
 *         .recordStats()
 *
 *  3. CONFIGURATION DATA (rarely changes, must be fresh):
 *     Caffeine.newBuilder()
 *         .maximumSize(1_000)
 *         .expireAfterWrite(5, TimeUnit.MINUTES)
 *         .refreshAfterWrite(1, TimeUnit.MINUTES)
 *
 *  4. API RATE LIMITER (small, very fast):
 *     Caffeine.newBuilder()
 *         .maximumSize(10_000)
 *         .expireAfterWrite(1, TimeUnit.SECONDS)
 *         .executor(Runnable::run) // sync execution
 *
 * ============================================================================
 *  CODE EXAMPLE: CaffeineCacheConfig (as seen in real project)
 * ============================================================================
 *
 *  @Configuration
 *  public class CaffeineCacheConfig {
 *
 *      @Bean
 *      public CacheManager cacheManager() {
 *          // Create cache names
 *          List<String> cacheNames = Arrays.asList("users", "products", "sessions");
 *
 *          // Build Caffeine instance
 *          Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
 *              .maximumSize(10_000)
 *              .expireAfterWrite(30, TimeUnit.MINUTES)
 *              .expireAfterAccess(10, TimeUnit.MINUTES)
 *              .refreshAfterWrite(5, TimeUnit.MINUTES)
 *              .recordStats()
 *              .removalListener((key, value, cause) ->
 *                  log.info("Evicted {} due to {}", key, cause));
 *
 *          // Build CacheManager
 *          CaffeineCacheManager cm = new CaffeineCacheManager();
 *          cm.setCacheNames(cacheNames);
 *          cm.setCaffeine(caffeine);
 *          cm.setAllowNullValues(false);
 *
 *          return cm;
 *      }
 *  }
 *
 * ============================================================================
 *  PER-CACHE CONFIGURATION (Different settings per cache)
 * ============================================================================
 *
 *  Need different TTL for different caches?
 *
 *  @Configuration
 *  public class PerCacheConfig {
 *
 *      @Bean
 *      public CacheManager cacheManager() {
 *          SimpleCacheManager cm = new SimpleCacheManager();
 *
 *          cm.setCaches(Arrays.asList(
 *              buildCache("users", 10_000, 30, TimeUnit.MINUTES),
 *              buildCache("sessions", 100_000, 15, TimeUnit.MINUTES),
 *              buildCache("products", 50_000, 60, TimeUnit.MINUTES)
 *          ));
 *
 *          return cm;
 *      }
 *
 *      private CaffeineCache buildCache(String name, int maxSize,
 *                                        int ttl, TimeUnit unit) {
 *          return new CaffeineCache(name, Caffeine.newBuilder()
 *              .maximumSize(maxSize)
 *              .expireAfterWrite(ttl, unit)
 *              .recordStats()
 *              .build());
 *      }
 *  }
 *
 * ============================================================================
 *  INTERVIEW Q&A — Caffeine Cache
 * ============================================================================
 *
 *  Q: Why is Caffeine faster than Guava Cache?
 *  A: Caffeine uses Window TinyLfu eviction policy (vs Guava's LRU).
 *     WTinyLfu uses a frequency sketch to track popular entries with
 *     minimal memory. It's scan-resistant and has better hit ratios.
 *     Also, Caffeine uses striked buffers for concurrent access.
 *
 *  Q: expireAfterWrite vs expireAfterAccess — when to use which?
 *  A: expireAfterWrite: fixed TTL from creation/update. Good for data
 *     that changes periodically (product catalog refreshed hourly).
 *     expireAfterAccess: sliding TTL. Good for sessions (extend on use).
 *     They can be combined (e.g., max 30m, sliding 10m).
 *
 *  Q: What is refreshAfterWrite and how is it different from expiry?
 *  A: refreshAfterWrite reloads the entry ASYNCHRONOUSLY before it
 *     expires. Old value is served while refresh happens (no blocking).
 *     Expiry removes entry → next request blocks until reloaded.
 *     Use refresh for frequently accessed data with acceptable staleness.
 *
 *  Q: How do you monitor Caffeine cache performance?
 *  A: Enable recordStats() → expose via Actuator or Micrometer:
 *     - hitRate, missRate, evictionCount, averageLoadPenalty
 *     - @Bean public CaffeineCacheMetrics() binds to Micrometer
 *
 *  Q: What is Window TinyLfu?
 *  A: Eviction policy that maintains:
 *     1) Admission window (small LRU — new entries go here first)
 *     2) Main space (SKETCH-based frequency tracking)
 *     If an entry in the window is accessed again soon → promoted.
 *     If not → frequency sketch decides if it should stay.
 *     Result: better hit ratio than pure LRU, scan-resistant.
 *
 *  Q: Can Caffeine cache be persisted to disk?
 *  A: Not directly (it's in-memory only). For persistence, use:
 *     - Redis (distributed, persistent)
 *     - EHCache (supports disk overflow)
 *     - Caffeine + write-through to DB
 *
 *  Q: Is Caffeine thread-safe?
 *  A: Yes, fully thread-safe. Uses ConcurrentHashMap internally with
 *     fine-grained locking and lock-free operations for reads.
 */

public class CaffeineCacheConfig {

    static final String BASIC_CONFIG = """
        @Configuration
        public class CaffeineCacheConfig {

            @Bean
            public CacheManager cacheManager() {
                CaffeineCacheManager cm = new CaffeineCacheManager(
                    \"users\", \"products\", \"sessions\"
                );
                cm.setCaffeine(Caffeine.newBuilder()
                    .maximumSize(10_000)
                    .expireAfterWrite(30, TimeUnit.MINUTES)
                    .expireAfterAccess(10, TimeUnit.MINUTES)
                    .refreshAfterWrite(5, TimeUnit.MINUTES)
                    .recordStats()
                    .removalListener((key, value, cause) ->
                        System.out.println(\"Evicted: \" + key + \" cause: \" + cause)
                    )
                );
                cm.setAllowNullValues(false);
                return cm;
            }
        }
        """;

    static final String PER_CACHE_CONFIG = """
        @Configuration
        public class PerCacheConfig {

            @Bean
            public CacheManager cacheManager() {
                SimpleCacheManager cm = new SimpleCacheManager();
                cm.setCaches(Arrays.asList(
                    cache(\"users\", 10_000, 30, TimeUnit.MINUTES),
                    cache(\"sessions\", 100_000, 15, TimeUnit.MINUTES),
                    cache(\"products\", 50_000, 60, TimeUnit.MINUTES),
                    cache(\"search\", 5_000, 5, TimeUnit.MINUTES)
                ));
                return cm;
            }

            private CaffeineCache cache(String name, int maxSize,
                                         int ttl, TimeUnit unit) {
                return new CaffeineCache(name,
                    Caffeine.newBuilder()
                        .maximumSize(maxSize)
                        .expireAfterWrite(ttl, unit)
                        .recordStats()
                        .build()
                );
            }
        }
        """;

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   CAFFEINE CACHE — CONFIGURATION");
        System.out.println("============================================");

        System.out.println("\n=== Recommended Caffeine Specs ===");
        String[][] specs = {
            {"Product Catalog", "50K", "1h", "no", "reads >> writes"},
            {"User Sessions", "100K", "30m", "15m sliding", "login sessions"},
            {"Config Data", "1K", "5m", "1m refresh", "rarely changes"},
            {"Rate Limiter", "10K", "1s", "no", "very short TTL"},
        };
        System.out.printf("  %-20s %-8s %-12s %-15s %s%n", "Use Case", "Max Size", "Write TTL", "Access TTL", "Notes");
        System.out.println("  " + "-".repeat(80));
        for (String[] row : specs) {
            System.out.printf("  %-20s %-8s %-12s %-15s %s%n", row[0], row[1], row[2], row[3], row[4]);
        }

        System.out.println("\n=== Global Config (same for all caches) ===");
        System.out.println(BASIC_CONFIG);

        System.out.println("\n=== Per-Cache Config (different TTL per cache) ===");
        System.out.println(PER_CACHE_CONFIG);

        System.out.println("\n=== WINDOW TINY LFU (Caffeine's eviction) ===");
        System.out.println("  ┌─────────────────────────────────────────────┐");
        System.out.println("  │  New Entry                                  │");
        System.out.println("  │     │                                       │");
        System.out.println("  │     v                                       │");
        System.out.println("  │  ┌──────────┐     Hit?                     │");
        System.out.println("  │  │ Admission│ ────yes──→ Promote to main   │");
        System.out.println("  │  │ Window   │                              │");
        System.out.println("  │  │ (LRU)    │     no ──→ Frequency check   │");
        System.out.println("  │  └──────────┘              │               │");
        System.out.println("  │                              v              │");
        System.out.println("  │  ┌──────────┐     ┌──────────────┐        │");
        System.out.println("  │  │ Main     │ ←── │ Frequency    │        │");
        System.out.println("  │  │ Space    │     │ Sketch       │        │");
        System.out.println("  │  │ (SKETCH) │     └──────────────┘        │");
        System.out.println("  │  └──────────┘                              │");
        System.out.println("  └─────────────────────────────────────────────┘");
        System.out.println("  Result: Better hit ratio than LRU alone,");
        System.out.println("  resistant to one-time scan attacks.");

        System.out.println("\n============================================");
        System.out.println("  Key takeaway: Use Caffeine for ALL local");
        System.out.println("  caching in Spring Boot. It's the fastest,");
        System.out.println("  most modern, and Spring Boot auto-configures it.");
        System.out.println("============================================");
    }
}
