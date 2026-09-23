package com.dhami.java_dev.caching.caffeine_cache;


// Why?
//spring-boot-starter-cache gives you: -> Spring Cache abstraction
//while: caffeine -> gives you the actual cache implementation.
public class CaffeineProvider {

    // ============================================================
    // CAFFEINE CACHE — KEY CONCEPTS
    // ============================================================

    // 1. Caffeine is a high-performance, in-memory caching library for Java.
    // 2. It is the modern replacement for Guava Cache (much faster, better hit rate).
    // 3. Spring Boot has first-class support — just add the dependency and Spring auto-configures it.
    // 4. Caffeine sits in the JVM heap — data is lost on application restart (unlike Redis).
    // 5. It supports TTL (expireAfterWrite), TTI (expireAfterAccess), and size-based eviction.
    // 6. Eviction policies: LRU (least recently used) and LFU (least frequently used) via Window TinyLFU.
    // 7. You configure it via a `CaffeineCacheManager` bean or `application.properties`.
    // 8. Each cache name can have its own spec (different TTL, size, etc.) via `registerCustomCache`.

    // ============================================================
    // DEPENDENCY (pom.xml)
    // ============================================================
    // <dependency>
    //     <groupId>com.github.ben-manes.caffeine</groupId>
    //     <artifactId>caffeine</artifactId>
    // </dependency>

    // ============================================================
    // COMMON CACHE SPECS (used in CaffeineCacheManager)
    // ============================================================

    // maximumSize(100)                  → cache holds max 100 entries, evicts when full
    // expireAfterWrite(10, MINUTES)     → entry expires 10 min after being written
    // expireAfterAccess(5, MINUTES)     → entry expires 5 min after last read/write
    // refreshAfterWrite(1, MINUTE)      → async refresh of entry after 1 min (keeps old value meanwhile)
    // weakKeys() / weakValues()         → entry removed when key/value is garbage collected
    // softValues()                      → entry removed when JVM is low on memory
    // recordStats()                     → enables hit/miss/eviction statistics

    // ============================================================
    // KEY METHODS / API
    // ============================================================

    // Caffeine.newBuilder()             → builder to create a native Caffeine cache
    // .maximumSize(n)                   → set max entries
    // .expireAfterWrite(duration)       → set write-based expiry
    // .expireAfterAccess(duration)      → set access-based expiry
    // .recordStats()                    → enable stats collection
    // .build()                          → build the cache
    // cache.get(key, k -> value)        → get or compute if absent (atomic)
    // cache.put(key, value)             → manual put
    // cache.invalidate(key)             → remove one entry
    // cache.invalidateAll()             → remove all entries
    // cache.asMap()                     → view cache as a ConcurrentMap
    // cache.stats()                     → returns hit/miss/eviction counts

    // ============================================================
    // SPRING INTEGRATION
    // ============================================================

    // @EnableCaching                   → required on a @Configuration class
    // CaffeineCacheManager             → Spring's wrapper around Caffeine
    // setCaffeine(Caffeine.newBuilder()...) → attach a Caffeine spec to the manager
    // registerCustomCache("name", cache)    → register a per-cache custom configuration
    // @Cacheable("products")           → uses the cache named "products"
    // @CacheEvict / @CachePut          → evict / update entries

    // ============================================================
    // CAFFEINE vs OTHER CACHES
    // ============================================================

    // ConcurrentMapCacheManager  → simplest, no eviction, no TTL, no size limit (default)
    // Caffeine                   → in-memory, fast, TTL + size + stats (single JVM)
    // Redis                      → distributed, network-based, survives restart (multi-instance)
    // EhCache                    → in-memory + disk, feature-rich, heavier

    // ============================================================
    // WHEN TO USE CAFFEINE
    // ============================================================

    // - Single JVM application (not distributed).
    // - Read-heavy workloads where data can be recomputed on miss.
    // - When you need TTL, size limits, and high throughput.
    // - When you do NOT need cache to survive application restart.

    // ============================================================
    // GOTCHAS
    // ============================================================

    // - Data is lost on restart (not persistent).
    // - Not shared across multiple instances (use Redis for that).
    // - Self-invocation still bypasses caching (proxy rule applies).
    // - Default spec (no maximumSize) can grow unbounded — always set a limit.
    // - `refreshAfterWrite` returns stale value while refreshing in background.
}

/**

CacheManager
     ↓
CaffeineCacheManager
     ↓
Caffeine

 Auto-configured Caffeine ko customize kaise kare ->

 spring.cache.type=caffeine
spring.cache.caffeine.spec=maximumSize=1000,expireAfterWrite=10m

or

 @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES) // TTL
                .maximumSize(500)); // Max size
        return cacheManager;
    }


 */