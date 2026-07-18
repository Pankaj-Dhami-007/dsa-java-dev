package springboot_deep_drive.caching.springboot.local;

/*
 * ============================================================================
 *  SPRING BOOT LOCAL CACHE — INTERVIEW Q&A
 *  All questions specifically about local (in-process) caching with Spring
 * ============================================================================
 *
 *  SECTION 1: BASICS & SETUP
 * ============================================================================
 *
 *  Q1: What is the difference between local cache and distributed cache?
 *  A:  LOCAL (Caffeine, Guava, EHCache):
 *        - Runs in the SAME JVM as the app
 *        - Fastest (~μs access time)
 *        - Data NOT shared across instances
 *        - Lost on app restart
 *        - Use for: config, product catalog, reference data
 *
 *      DISTRIBUTED (Redis, Memcached):
 *        - Runs as SEPARATE service
 *        - ~1ms network latency
 *        - Data SHARED across all app instances
 *        - Survives app restarts (persistent)
 *        - Use for: sessions, cache across microservices
 *
 *  Q2: What dependencies do I need for Spring Boot local caching?
 *  A:  Minimum: spring-boot-starter-cache
 *      For Caffeine (recommended): caffeine
 *      For EHCache: ehcache + javax.cache
 *      No extra dependency needed for Simple (default, dev only)
 *
 *  Q3: What does @EnableCaching do?
 *  A:  1. Registers CacheInterceptor (AOP advice)
 *      2. Creates proxy around beans with @Cacheable methods
 *      3. Auto-detects CacheManager from classpath
 *      4. Initializes cache infrastructure
 *
 *  Q4: How does Spring auto-detect the cache provider?
 *  A:  Uses Spring Boot auto-configuration (@ConditionalOnClass):
 *      - Caffeine on classpath → CaffeineCacheManager
 *      - EHCache on classpath → EhCacheCacheManager
 *      - JCache on classpath → JCacheCacheManager
 *      - None of above → SimpleCacheManager (ConcurrentHashMap)
 *      Override with: spring.cache.type=caffeine
 *
 * ============================================================================
 *  SECTION 2: @Cacheable DETAILS
 * ============================================================================
 *
 *  Q5: What is the default cache key?
 *  A:  SimpleKeyGenerator generates key from ALL method parameters.
 *      SimpleKey [param1, param2, ...]. Override with: key="#paramName"
 *
 *  Q6: What SpEL expressions can I use in key?
 *  A:  #paramName, #param.field, #root.methodName, #root.targetClass,
 *      #root.args[0], T(Class).staticMethod(). See CacheableAnnotation.java.
 *
 *  Q7: What is the difference between condition and unless?
 *  A:  condition runs BEFORE method (skip cache entirely if false).
 *      unless runs AFTER method (don't store result if true).
 *      Example: condition="#id>0" + unless="#result==null"
 *
 *  Q8: How does sync=true prevent thundering herd?
 *  A:  Uses key-level locking. Thread 1 acquires lock and loads data.
 *      Other threads block on the same key. After load, they read from
 *      cache. Only ONE DB call for N concurrent requests.
 *
 *  Q9: Can @Cacheable be used on private methods?
 *  A:  No. Spring AOP proxies only work with public methods.
 *      Private/internal calls bypass the proxy entirely.
 *      Solution: AspectJ mode or extract to separate bean.
 *
 * ============================================================================
 *  SECTION 3: CAFFEINE-SPECIFIC
 * ============================================================================
 *
 *  Q10: Why is Caffeine recommended over Guava?
 *  A:   Caffeine is 5-10x faster, uses 30% less memory, has Window
 *       TinyLfu eviction policy (better hit ratio than LRU), supports
 *       async loading, and is actively maintained (Guava Cache is in
 *       maintenance mode).
 *
 *  Q11: What is Window TinyLfu?
 *  A:   Caffeine's adaptive eviction policy:
 *       - Admission Window (LRU): new entries arrive here first
 *       - Main Space (Frequency Sketch): if entry is accessed again → promote
 *       - Frequency sketch tracks popularity with minimal memory
 *       - Result: scan-resistant, better hit ratio than pure LRU
 *
 *  Q12: expireAfterWrite vs expireAfterAccess vs refreshAfterWrite?
 *  A:   expireAfterWrite: fixed TTL from creation/last update
 *       expireAfterAccess: sliding TTL (extends on every read)
 *       refreshAfterWrite: async reload BEFORE expiry (stale served while loading)
 *       Combine: expireAfterWrite=1h, expireAfterAccess=15m, refreshAfterWrite=5m
 *
 *  Q13: How do you monitor Caffeine cache?
 *  A:   Caffeine.newBuilder().recordStats() → exposes:
 *       hitRate(), missRate(), evictionCount(), averageLoadPenalty()
 *       Spring Boot Actuator + Micrometer auto-exports these metrics.
 *
 *  Q14: What are Caffeine's weakKeys/weakValues/softValues?
 *  A:   Memory-efficient references:
 *       weakKeys: GC can collect keys when no other reference exists
 *       weakValues: GC can collect values when no other reference exists
 *       softValues: GC collects values when heap memory is low
 *       Trade-off: better memory usage, but entries can disappear anytime
 *
 * ============================================================================
 *  SECTION 4: @CachePut & @CacheEvict
 * ============================================================================
 *
 *  Q15: @CachePut vs @CacheEvict — when to use which?
 *  A:   @CachePut: you KNOW the new value (save, update)
 *       → cache is always fresh, next read = HIT
 *       @CacheEvict: you DON'T have the new value (delete, bulk update)
 *       → next read = MISS → fetches fresh from DB
 *
 *  Q16: What is beforeInvocation in @CacheEvict?
 *  A:   false (default): evict AFTER method → if method throws, cache preserved
 *       true: evict BEFORE method → even if method throws, cache cleared
 *       Use true when cache MUST be cleared regardless of method outcome
 *
 *  Q17: Can @CacheEvict be used on void methods?
 *  A:   Yes, and it's the MOST COMMON use case for void methods
 *       (delete operations). Unlike @Cacheable which needs return value.
 *
 *  Q18: allEntries=true vs specifying a key — trade-offs?
 *  A:   allEntries=true: clears ENTIRE cache (simple but cold)
 *       key="#id": removes single entry (targeted but needs key)
 *       Use allEntries for bulk updates, key for single operations.
 *
 * ============================================================================
 *  SECTION 5: @Caching & @CacheConfig
 * ============================================================================
 *
 *  Q19: When should I use @Caching?
 *  A:   When one method needs to update MULTIPLE cache entries:
 *       - Save user: cache by id + email + username
 *       - Delete post: evict post + feed + search index
 *       - Update product: update cache + evict search results
 *
 *  Q20: What does @CacheConfig do?
 *  A:   Sets default cacheNames/keyGenerator/cacheManager at class level.
 *       Reduces repetition: instead of @Cacheable("users") on every method,
 *       write @CacheConfig(cacheNames="users") once on the class.
 *
 * ============================================================================
 *  SECTION 6: PERFORMANCE & BEST PRACTICES
 * ============================================================================
 *
 *  Q21: What are the common pitfalls with Spring caching?
 *  A:   1. Internal method calls bypass proxy (no caching!)
 *       2. @Cacheable on void methods (caches null — useless)
 *       3. Not setting TTL for production (memory leak)
 *       4. Caching sensitive data (passwords, PII)
 *       5. Over-caching (caching everything instead of hot data)
 *       6. No monitoring (don't know hit ratio — flying blind)
 *
 *  Q22: How do you handle cache in testing?
 *  A:   @TestPropertySource(properties = "spring.cache.type=NONE")
 *       Or use @DirtiesContext to reset caches between tests.
 *       Or mock CacheManager for unit tests.
 *
 *  Q23: What cache size is appropriate for local cache?
 *  A:   Depends on heap and data size:
 *       - Reference data: 100-1000 entries
 *       - Product catalog: 10K-100K entries
 *       - User sessions: 10K-100K entries
 *       Rule: Don't exceed 20% of heap. Monitor eviction rate.
 *
 *  Q24: Local cache vs Redis — which is faster for reads?
 *  A:   Local cache: ~10-50μs (same JVM, no network)
 *       Redis: ~0.5-2ms (network round trip)
 *       Local is 20-100x faster BUT data is not shared.
 *       Best: L1 (Caffeine) + L2 (Redis) hybrid.
 *
 *  Q25: How do you implement L1 + L2 caching in Spring?
 *  A:   Use @Cacheable with multiple cache names:
 *       @Cacheable({"caffeine", "redis"})
 *       Checks L1 first → L2 second → DB.
 *       Or implement custom CacheManager with two-tier logic.
 *
 * ============================================================================
 *  SECTION 7: INTERVIEW ANSWER SCRIPTS
 * ============================================================================
 *
 *  "How would you implement caching in Spring Boot?"
 *
 *  SCRIPT:
 *  "First, I add spring-boot-starter-cache + caffeine dependencies.
 *
 *   Next, I add @EnableCaching on a @Configuration class. This activates
 *   AOP-based cache interception.
 *
 *   I configure a CaffeineCacheManager bean with:
 *   - maximumSize based on available heap
 *   - expireAfterWrite for TTL
 *   - recordStats for monitoring
 *
 *   On my service methods, I add:
 *   - @Cacheable for reads (checks cache, skips method on hit)
 *   - @CachePut for writes (always executes + updates cache)
 *   - @CacheEvict for deletes (removes stale entries)
 *
 *   For the same-class proxy issue, I either self-inject or extract
 *   caching to a separate service layer.
 *
 *   I monitor hit ratio with Micrometer + Grafana. Target > 90%.
 *   If evictions are high, I increase cache size or tune TTL."
 */

public class LocalCacheInterviewQA {

    static final String[][] QA = {
        {"Q1", "Local vs Distributed cache?",
         "Local: same JVM, ~μs, not shared, lost on restart. "
         + "Distributed: separate service, ~ms, shared across instances, persistent."},
        {"Q2", "What does @EnableCaching do?",
         "Creates AOP proxy, registers CacheInterceptor, auto-detects CacheManager."},
        {"Q3", "Default cache key?",
         "SimpleKeyGenerator — ALL method parameters. Override with key=''#param''."},
        {"Q4", "condition vs unless?",
         "condition: before method (skip cache). unless: after method (don't store)."},
        {"Q5", "sync=true purpose?",
         "Key-level locking. Prevents thundering herd on cache miss."},
        {"Q6", "Why Caffeine over Guava?",
         "5-10x faster, 30% less memory, Window TinyLfu > LRU, actively maintained."},
        {"Q7", "expireAfterWrite vs expireAfterAccess?",
         "Write: fixed TTL from creation. Access: sliding TTL (extends on read)."},
        {"Q8", "What is Window TinyLfu?",
         "Admission window (LRU) + frequency sketch. Scan-resistant. Higher hit ratio."},
        {"Q9", "@CachePut vs @CacheEvict?",
         "Put: execute + update cache. Evict: remove from cache (next read refreshes)."},
        {"Q10", "beforeInvocation=true?",
         "Evict BEFORE method runs. Use when cache must clear even if method fails."},
        {"Q11", "When to use @Caching?",
         "One method affecting multiple caches (save by id + email + username)."},
        {"Q12", "What does @CacheConfig do?",
         "Class-level defaults: cacheNames, keyGenerator, cacheManager. Reduces repetition."},
        {"Q13", "Common caching pitfalls?",
         "Internal proxy bypass, void + @Cacheable, no TTL, caching PII, no monitoring."},
        {"Q14", "How to disable cache for tests?",
         "spring.cache.type=NONE or @DirtiesContext."},
        {"Q15", "L1 + L2 caching?",
         "@Cacheable({\"caffeine\", \"redis\"}) — checks L1 first, then L2, then DB."}
    };

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   LOCAL CACHE — INTERVIEW Q&A");
        System.out.println("   15 Essential Questions");
        System.out.println("============================================");

        System.out.println("\n  " + "-".repeat(80));
        System.out.printf("  %-5s %-30s %s%n", "#", "Question", "Answer");
        System.out.println("  " + "-".repeat(80));

        for (String[] qa : QA) {
            String num = qa[0];
            String question = qa[1];
            String answer = qa[2];

            // Word wrap the answer
            System.out.printf("  %-5s %-30s %s%n", num, question, "");
            String[] words = answer.split(" ");
            StringBuilder line = new StringBuilder("       ");
            for (String word : words) {
                if (line.length() + word.length() > 75) {
                    System.out.println(line);
                    line = new StringBuilder("       ");
                }
                line.append(word).append(" ");
            }
            if (line.length() > 7) System.out.println(line);
            System.out.println();
        }

        System.out.println("\n============================================");
        System.out.println("  INTERVIEW SCRIPT: \"How to cache in Spring?\"");
        System.out.println("============================================");
        System.out.println("  \"I use Caffeine as the local cache provider");
        System.out.println("   with spring-boot-starter-cache. @EnableCaching");
        System.out.println("   activates AOP proxies. I configure");
        System.out.println("   CaffeineCacheManager with max size, TTL, and stats.");
        System.out.println("   @Cacheable for reads, @CachePut for writes,");
        System.out.println("   @CacheEvict for deletes. I monitor hit ratio");
        System.out.println("   via Micrometer, targeting > 90%. For the proxy");
        System.out.println("   limitation, I use self-injection or extract");
        System.out.println("   caching to a dedicated service layer.\"");
        System.out.println("============================================");
    }
}
