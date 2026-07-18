package springboot_deep_drive.caching.spring;

/*
 * SIMPLE DEFINITION:
 * Spring Boot makes caching easy with ANNOTATIONS. Just add @Cacheable
 * to a method and Spring automatically stores/retrieves results from
 * a configured cache manager (Redis, Caffeine, EHCache, etc.).
 *
 * REAL-LIFE ANALOGY:
 * You tell a receptionist: "Every time someone asks for John's phone
 * number, check your Rolodex FIRST. If it's there, give it. If not,
 * look it up in the directory and add it." That's @Cacheable.
 *
 * ============================================================================
 *  SPRING BOOT CACHING — Complete Guide
 * ============================================================================
 *
 *  SPRING CACHING ANNOTATIONS:
 *  ┌────────────────┬────────────────────────────────────────────────┐
 *  │ Annotation     │ What it does                                  │
 *  ├────────────────┼────────────────────────────────────────────────┤
 *  │ @Cacheable     │ Check cache first; skip method if found       │
 *  │ @CacheEvict    │ Remove entries from cache                     │
 *  │ @CachePut      │ Execute method AND update cache               │
 *  │ @Caching       │ Combine multiple annotations                  │
 *  │ @CacheConfig   │ Share cache config at class level             │
 *  └────────────────┴────────────────────────────────────────────────┘
 *
 * ============================================================================
 *  @Cacheable DETAILS
 * ============================================================================
 *
 *  public @Cacheable(value="users", key="#userId") User getUser(Long userId)
 *
 *  HOW IT WORKS:
 *    1. Spring generates key = "users::" + userId
 *    2. Checks if key exists in configured cache
 *    3. If FOUND → return cached value (method NOT executed)
 *    4. If MISS → execute method → store result in cache → return
 *
 *  KEY GENERATION:
 *    - Default: SimpleKeyGenerator (all method params)
 *    - Custom: key="#userId", key="#user.name"
 *    - SpEL: key="#root.methodName + #id"
 *
 *  CONDITIONAL CACHING:
 *    - condition="#result != null" (only cache non-null)
 *    - unless="#result.isExpired()" (skip caching if condition met)
 *
 * ============================================================================
 *  @CacheEvict DETAILS
 * ============================================================================
 *
 *  public @CacheEvict(value="users", key="#userId") void deleteUser(Long userId)
 *
 *  OPTIONS:
 *    - allEntries=true → clear entire cache (like cache.clear())
 *    - beforeInvocation=true → evict BEFORE method executes
 *    - condition → only evict when condition is true
 *
 * ============================================================================
 *  @CachePut DETAILS
 * ============================================================================
 *
 *  public @CachePut(value="users", key="#user.id") User updateUser(User user)
 *
 *  Always executes the method AND updates cache (unlike @Cacheable
 *  which MAY skip the method).
 *
 * ============================================================================
 *  @Caching DETAILS
 * ============================================================================
 *
 *  @Caching(put = {
 *      @CachePut(value="users", key="#user.id"),
 *      @CachePut(value="users", key="#user.email")
 *  })
 *  Used when you need multiple cache operations for one method.
 *
 * ============================================================================
 *  CACHE MANAGERS (Configure which cache backend)
 * ============================================================================
 *
 *  ┌────────────┬────────────────────────────────────────────────────┐
 *  │ Provider   │ Dependency                                        │
 *  ├────────────┼────────────────────────────────────────────────────┤
 *  │ Redis      │ spring-boot-starter-data-redis                    │
 *  │ Caffeine   │ caffeine (in-memory, fastest)                     │
 *  │ EHCache    │ ehcache (mature, XML config)                      │
 *  │ JCache     │ JSR-107 implementation                            │
 *  │ Simple     │ ConcurrentHashMap (default, dev only)             │
 *  └────────────┴────────────────────────────────────────────────────┘
 *
 * ============================================================================
 *  CACHE CONFIGURATION (application.yml)
 * ============================================================================
 *  spring:
 *    cache:
 *      type: redis
 *      redis:
 *        time-to-live: 3600000  # 1 hour (in ms)
 *        cache-null-values: false
 *        use-key-prefix: true
 *        key-prefix: "myapp:"
 *
 * ============================================================================
 *  INTERVIEW Q&A — Spring Caching
 * ============================================================================
 *
 *  Q1: @Cacheable vs @CachePut — what's the difference?
 *  A1: @Cacheable checks cache first → returns cached if found (skips method).
 *      @CachePut always executes method AND updates cache.
 *      Use @Cacheable for READ operations, @CachePut for WRITE operations
 *      where you want to refresh the cache.
 *
 *  Q2: What happens when two @Cacheable methods have the same cache name?
 *  A2: They share the SAME cache. A put from one method affects the other.
 *      This can cause issues if they store different types. Use unique
 *      cache names for different data types.
 *
 *  Q3: How do you handle cache expiration in Spring?
 *  A3: Configure TTL in application.yml (Redis) or use @Cacheable with
 *      a custom CacheManager that sets TTL. Spring annotations don't
 *      have a TTL parameter — it's handled by the cache provider.
 *
 *  Q4: How do you disable caching in tests?
 *  A4: Set spring.cache.type=NONE in test profile. Or mock the
 *      CacheManager. Or add @DirtiesContext to reset caches between tests.
 *
 *  Q5: How do you cache null values in Spring?
 *  A5: By default, Spring does NOT cache null (method returns null → no cache).
 *      Set spring.cache.redis.cache-null-values=true to enable.
 *      This is useful for cache penetration prevention.
 *
 *  Q6: What is the proxy limitation of @Cacheable?
 *  A6: Spring uses AOP proxies. @Cacheable only works when called FROM
 *      OUTSIDE the class (through the proxy). A method calling another
 *      method in the SAME class bypasses the proxy → no caching.
 *      Solution: Self-injection or AspectJ mode.
 *
 *  Q7: How do you implement distributed caching with Spring?
 *  A7: Use Redis as the cache provider. Redis is shared across all app
 *      instances. Configure RedisConnectionFactory with Redis server
 *      details. All instances use the same Redis cluster.
 *      Add @EnableCaching + spring-boot-starter-data-redis.
 */

public class SpringCacheDemo {

    /*
     * NOTE: This is a CONCEPTUAL demo class. Spring annotations only
     * work in a proper Spring Boot application with @EnableCaching.
     *
     * Below we demonstrate the LOGIC behind each annotation using
     * manual cache operations, so you understand exactly what Spring
     * does under the hood.
     */

    // Simulated cache store
    private static final java.util.concurrent.ConcurrentHashMap<String, Object>
        cache = new java.util.concurrent.ConcurrentHashMap<>();

    // ================================================================
    //  DEMO: @Cacheable logic (manual implementation)
    // ================================================================
    static class CacheableDemo {

        /*
         * @Cacheable(value = "users", key = "#userId")
         * public User getUser(Long userId) { ... }
         *
         * Spring translates this to:
         */
        static String getUser(Long userId) {
            String cacheKey = "users::" + userId;

            // 1. CHECK CACHE FIRST
            Object cached = cache.get(cacheKey);
            if (cached != null) {
                System.out.println("   [@Cacheable] HIT: " + cacheKey);
                return (String) cached;
            }

            // 2. CACHE MISS → execute method
            System.out.println("   [@Cacheable] MISS: " + cacheKey + " → executing method");
            String result = actualDbCall(userId);

            // 3. STORE RESULT IN CACHE
            if (result != null) {
                cache.put(cacheKey, result);
                System.out.println("   [@Cacheable] Cached: " + cacheKey + " = " + result);
            }
            return result;
        }

        static String actualDbCall(Long userId) {
            // Simulate DB call
            if (userId <= 0) return null;
            return "User_" + userId;
        }

        static void demo() {
            System.out.println("\n─────── @Cacheable DEMO ──────────────");

            System.out.println("\n  First call (MISS → execute + cache):");
            System.out.println("  Result: " + getUser(1L));

            System.out.println("\n  Second call (HIT → skip method):");
            System.out.println("  Result: " + getUser(1L));

            System.out.println("\n  Different user (MISS again):");
            System.out.println("  Result: " + getUser(2L));

            System.out.println("\n  Non-existent user (null result):");
            System.out.println("  Result: " + getUser(-1L));
            // Spring does NOT cache null by default
            System.out.println("  (Note: null not cached — next call still misses)");
        }
    }

    // ================================================================
    //  DEMO: @CachePut logic (manual implementation)
    // ================================================================
    static class CachePutDemo {

        /*
         * @CachePut(value = "users", key = "#user.id")
         * public User updateUser(Long id, String name) { ... }
         *
         * Always executes method AND updates cache.
         */
        static String updateUser(Long id, String name) {
            String cacheKey = "users::" + id;

            // 1. ALWAYS execute the method
            System.out.println("   [@CachePut] Executing method for " + id);
            String result = "User_" + id + "_" + name;

            // 2. ALWAYS update the cache
            cache.put(cacheKey, result);
            System.out.println("   [@CachePut] Updated cache: " + cacheKey + " = " + result);

            return result;
        }

        static void demo() {
            System.out.println("\n─────── @CachePut DEMO ───────────────");

            System.out.println("\n  Update user (always executes + caches):");
            updateUser(1L, "Alice");
            System.out.println("  Cache now: " + cache.get("users::1"));

            System.out.println("\n  Update again (always executes):");
            updateUser(1L, "Bob");
            System.out.println("  Cache now: " + cache.get("users::1"));
        }
    }

    // ================================================================
    //  DEMO: @CacheEvict logic (manual implementation)
    // ================================================================
    static class CacheEvictDemo {

        /*
         * @CacheEvict(value = "users", key = "#userId")
         * public void deleteUser(Long userId) { ... }
         *
         * Removes entry from cache after method executes.
         */
        static void deleteUser(Long userId) {
            String cacheKey = "users::" + userId;

            // Execute method logic (delete from DB)
            System.out.println("   [@CacheEvict] Deleted user " + userId + " from DB");

            // Remove from cache
            cache.remove(cacheKey);
            System.out.println("   [@CacheEvict] Evicted: " + cacheKey);
        }

        static void demo() {
            System.out.println("\n─────── @CacheEvict DEMO ─────────────");

            // First populate cache
            cache.put("users::1", "User_1");
            System.out.println("  Before delete - cache has: " + cache.get("users::1"));

            deleteUser(1L);

            System.out.println("  After delete - cache has: " + cache.get("users::1"));
        }
    }

    // ================================================================
    //  DEMO: @CacheEvict(allEntries=true) — clear entire cache
    // ================================================================
    static class CacheClearDemo {

        /*
         * @CacheEvict(value = "users", allEntries = true)
         * public void clearAllUsers() { ... }
         */
        static void clearAllUsers() {
            cache.clear();
            System.out.println("   [@CacheEvict(allEntries)] Cleared entire 'users' cache!");
        }

        static void demo() {
            System.out.println("\n─────── @CacheEvict(allEntries) DEMO ──");

            cache.put("users::1", "User_1");
            cache.put("users::2", "User_2");
            cache.put("products::1", "Product_1"); // different cache
            System.out.println("  Cache size before clear: " + cache.size());

            clearAllUsers();

            System.out.println("  Cache size after clear: " + cache.size());
            // Only entries with matching cache name are cleared
            // Since we use single map, all are cleared. In Spring,
            // each cache(name) is separate.
        }
    }

    // ================================================================
    //  DEMO: @Caching — combine multiple operations
    // ================================================================
    static class CachingDemo {

        /*
         * @Caching(
         *   put = {
         *     @CachePut(value = "users", key = "#user.id"),
         *     @CachePut(value = "users", key = "#user.email")
         *   },
         *   evict = {
         *     @CacheEvict(value = "users", key = "'all-users'")
         *   }
         * )
         */
        static void saveUserWithMultipleCaches(Long id, String email, String name) {
            String userKey = "users::" + id;
            String emailKey = "users::email::" + email;
            String allKey = "users::all-users";

            System.out.println("   [@Caching] Saving user " + id + " (" + email + ")");

            // Multiple @CachePut
            String value = "User_" + id + "_" + name;
            cache.put(userKey, value);
            cache.put(emailKey, value);
            System.out.println("   [@Caching] Cached: " + userKey + " & " + emailKey);

            // @CacheEvict
            cache.remove(allKey);
            System.out.println("   [@Caching] Evicted: " + allKey);
        }

        static void demo() {
            System.out.println("\n─────── @Caching DEMO ─────────────────");
            saveUserWithMultipleCaches(1L, "alice@email.com", "Alice");
            System.out.println("  By ID: " + cache.get("users::1"));
            System.out.println("  By email: " + cache.get("users::email::alice@email.com"));
        }
    }

    // ================================================================
    //  MAIN
    // ================================================================
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   SPRING BOOT CACHING — Complete Demo");
        System.out.println("============================================");
        System.out.println("  (Annotations simulated manually)");
        System.out.println("  In real app: @EnableCaching + @Cacheable");

        CacheableDemo.demo();
        CachePutDemo.demo();
        CacheEvictDemo.demo();
        CacheClearDemo.demo();
        CachingDemo.demo();

        System.out.println("\n============================================");
        System.out.println("  SPRING CACHE QUICK REFERENCE");
        System.out.println("============================================");
        System.out.println("  @Cacheable     → Check cache first (skip method on hit)");
        System.out.println("  @CachePut      → Execute method + update cache always");
        System.out.println("  @CacheEvict    → Remove from cache (single or all)");
        System.out.println("  @Caching       → Combine multiple annotations");
        System.out.println("  @CacheConfig   → Class-level cache settings");
        System.out.println("\n  Providers: Redis, Caffeine, EHCache, JCache, Simple");
        System.out.println("  Enable: @EnableCaching on @Configuration class");
        System.out.println("  Key: SpEL expressions (#id, #result, #root.methodName)");
        System.out.println("============================================");
    }
}
