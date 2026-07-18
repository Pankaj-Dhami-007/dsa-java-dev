package springboot_deep_drive.caching.springboot.local;

/*
 * ============================================================================
 *  @Cacheable ANNOTATION — DEEP DIVE
 * ============================================================================
 *
 *  SIMPLE DEFINITION:
 *  @Cacheable is Spring's READ-CACHE annotation. It checks the cache
 *  BEFORE method execution. If found → return cached value (skip method).
 *  If not → execute method, store result in cache, return.
 *
 *  REAL-LIFE ANALOGY:
 *  A student asks a teacher a question. The teacher checks her lesson
 *  notes (cache) first. If she's answered this before → reads from notes.
 *  If not → works out the answer, writes it down for next time, tells student.
 *
 * ============================================================================
 *  @Cacheable ANNOTATION BREAKDOWN
 * ============================================================================
 *
 *  @Cacheable(
 *      value = "users",            // Cache name (same as cache("users"))
 *      key = "#userId",            // SpEL expression for cache key
 *      condition = "#userId > 0",  // Only cache if condition is TRUE
 *      unless = "#result == null", // Don't cache if condition is TRUE
 *      sync = false                // Synchronized access (lock on miss)
 *  )
 *  public User getUser(Long userId) { ... }
 *
 *  ┌────────────┬──────────┬────────────────────────────────────────┐
 *  │ Attribute  │ Required │ Description                            │
 *  ├────────────┼──────────┼────────────────────────────────────────┤
 *  │ value      │ ✅       │ Cache name(s) — maps to CacheManager   │
 *  │ cacheNames │ ✅       │ Alias for value                        │
 *  │ key        │ ❌       │ SpEL key expression (default: all args)│
 *  │ keyGenerator│ ❌      │ Custom KeyGenerator bean name           │
 *  │ condition  │ ❌       │ SpEL — cache ONLY if true              │
 *  │ unless     │ ❌       │ SpEL — do NOT cache if true            │
 *  │ sync       │ ❌       │ Synchronize cache miss loading         │
 *  └────────────┴──────────┴────────────────────────────────────────┘
 *
 * ============================================================================
 *  KEY GENERATION (SpEL — Spring Expression Language)
 * ============================================================================
 *
 *  ┌─────────────────────────────┬──────────────────────────────────┐
 *  │ Expression                  │ Result                           │
 *  ├─────────────────────────────┼──────────────────────────────────┤
 *  │ #userId                     │ Parameter named userId           │
 *  │ #user.id                    │ user.getId()                     │
 *  │ #root.methodName            │ Method name ("getUser")          │
 *  │ #root.method.name           │ Method object name               │
 *  │ #root.targetClass           │ Class of target                  │
 *  │ #root.args[0]               │ First argument (index-based)     │
 *  │ #result                     │ Method return value (only in     │
 *  │                             │ unless, not in key)              │
 *  │ T(java.lang.Math).random()  │ Static method call               │
 *  │ #userId.toString()          │ Chained method call              │
 *  └─────────────────────────────┴──────────────────────────────────┘
 *
 *  DEFAULT KEY: SimpleKeyGenerator generates key from ALL parameters.
 *  Equivalent to: new SimpleKey(params...) — hash of all args.
 *
 * ============================================================================
 *  @Cacheable(sync = true) — Thundering Herd Protection
 * ============================================================================
 *
 *  WITHOUT sync (default):
 *    Thread A: cache MISS → executing method → storing result
 *    Thread B: cache MISS → executing method → storing result  ← DUPLICATE!
 *    Thread C: cache MISS → executing method → storing result  ← DUPLICATE!
 *
 *  WITH sync = true:
 *    Thread A: cache MISS → LOCKS key → executing → stores → unlocks
 *    Thread B: cache MISS → WAITS for lock → reads cached result
 *    Thread C: cache MISS → WAITS for lock → reads cached result
 *
 *  Use sync=true when:
 *    - Method is EXPENSIVE (slow DB call, API call)
 *    - Multiple concurrent requests for same key expected
 *    - Cache MISS scenario is common (cold start)
 *
 * ============================================================================
 *  @Cacheable(condition) — Conditional Caching
 * ============================================================================
 *
 *  @Cacheable(value = "users", condition = "#userId > 0")
 *  public User getUser(Long userId)
 *    // Only caches when userId is POSITIVE
 *    // Negative IDs bypass cache entirely
 *
 *  @Cacheable(value = "users", unless = "#result == null")
 *  public User getUser(Long userId)
 *    // Caches everything EXCEPT null results
 *    // (prevents caching non-existent entries)
 *
 *  @Cacheable(value = "expensive", condition = "#cached",
 *             unless = "#result == null")
 *  public Data compute(boolean cached)
 *    // Only cache when cached=true and result is not null
 *
 * ============================================================================
 *  MULTIPLE CACHE NAMES
 * ============================================================================
 *
 *  @Cacheable({"users", "profiles"})
 *  public User getUser(Long id)
 *    // Checks users FIRST → if miss, checks profiles → if miss, execute
 *    // Result is stored in BOTH caches on miss
 *    // Useful for hierarchical cache (L1 + L2 style)
 *
 * ============================================================================
 *  INTERVIEW Q&A — @Cacheable
 * ============================================================================
 *
 *  Q: What happens if @Cacheable method throws an exception?
 *  A: The exception propagates to caller. Result is NOT cached
 *     (unless using @CachePut which always executes).
 *
 *  Q: Can I use @Cacheable on a private method?
 *  A: NO. Spring AOP only works with public methods. Private methods
 *     bypass the proxy. Use AspectJ mode for non-public methods.
 *
 *  Q: What is the difference between condition and unless?
 *  A: condition runs BEFORE method (decides if cache is checked).
 *     unless runs AFTER method (decides if result is stored).
 *     condition="#id>0" → skip cache check for invalid ids.
 *     unless="#result==null" → don't cache null results.
 *
 *  Q: How does sync=true work internally?
 *  A: Uses a ConcurrentHashMap of locks (one per key).
 *     Thread 1 acquires lock and loads data. Other threads block
 *     on the same key's lock. After load, cache is populated and
 *     other threads read from cache (not DB).
 *
 *  Q: @Cacheable on void method — what happens?
 *  A: @Cacheable stores the return value. Void returns null.
 *     Caching null is typically useless. Better to use @CacheEvict
 *     for void methods (invalidate cache on completion).
 *
 *  Q: Can I use @Cacheable with Mono/Flux (WebFlux)?
 *  A: Yes but carefully. Cache the BLOCKING result, not the reactive
 *     wrapper. Use .cache() operator from Reactor instead for reactive
 *     pipelines. Or use @Cacheable on a blocking method called from
 *     reactive chain via .publishOn().
 */

public class CacheableAnnotation {

    static final String BASIC_EXAMPLE = """
        @Service
        public class UserService {

            @Cacheable(value = "users", key = "#userId")
            public User getUser(Long userId) {
                // Only executes on cache MISS
                System.out.println(\"Fetching from DB: \" + userId);
                return userRepository.findById(userId).orElse(null);
            }
        }

        // Usage:
        // userService.getUser(1L);  // MISS → DB call → cached
        // userService.getUser(1L);  // HIT  → from cache (no DB!)
        // userService.getUser(2L);  // MISS → DB call → cached
        """;

    static final String SYNC_EXAMPLE = """
        @Cacheable(value = \"expensive\", key = "#query\", sync = true)
        public SearchResult search(String query) {
            // sync=true: only 1 thread executes this on cache miss
            // Other threads wait for the result
            return searchService.slowSearch(query);
        }

        // When 100 users search same term simultaneously:
        // Thread 1: acquires lock → executes search
        // Threads 2-100: wait → then read from cache
        // Result: 1 DB call instead of 100!
        """;

    static final String CONDITION_EXAMPLE = """
        // Cache only valid user IDs
        @Cacheable(value = \"users\", condition = "#userId > 0\")
        public User getUser(Long userId) { ... }

        // Don't cache null results (prevents cache penetration)
        @Cacheable(value = \"users\", unless = "#result == null\")
        public User getUser(Long userId) { ... }

        // Combined: cache only valid IDs AND non-null results
        @Cacheable(value = \"users\",
                   condition = "#userId > 0",
                   unless = "#result == null")
        public User getUser(Long userId) { ... }

        // Cache based on method argument boolean
        @Cacheable(value = \"data\", condition = "#cacheable")
        public Data compute(boolean cacheable, String input) { ... }
        """;

    static final String KEY_EXAMPLES = """
        // Default key (all params): SimpleKey [userId, tenantId]
        @Cacheable(\"users\")
        public User getUser(Long userId, String tenantId)

        // Single param as key
        @Cacheable(value = \"users\", key = "#userId\")

        // Object field as key
        @Cacheable(value = \"users\", key = "#user.id\")

        // String concatenation
        @Cacheable(value = \"users\", key = "#tenant + ':' + #userId\")

        // Static method
        @Cacheable(value = \"users\",
                   key = \"T(com.example.KeyUtil).generate(#id)\")

        // Root object access
        @Cacheable(value = \"users\",
                   key = \"#root.methodName + ':' + #id\")
        """;

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   @Cacheable — Deep Dive");
        System.out.println("============================================");

        System.out.println("\n=== 1. Basic Usage ===");
        System.out.println(BASIC_EXAMPLE);

        System.out.println("=== 2. sync=true (Thundering Herd Protection) ===");
        System.out.println("  Without sync:");
        System.out.println("    Thread A: MISS → execute slowMethod()→ store");
        System.out.println("    Thread B: MISS → execute slowMethod()→ store  ← DUPE!");
        System.out.println("    Thread C: MISS → execute slowMethod()→ store  ← DUPE!");
        System.out.println("\n  With sync=true:");
        System.out.println("    Thread A: MISS → LOCK → execute → store → UNLOCK");
        System.out.println("    Thread B: MISS → WAIT → reads cached result");
        System.out.println("    Thread C: MISS → WAIT → reads cached result");
        System.out.println(SYNC_EXAMPLE);

        System.out.println("=== 3. Condition & Unless ===");
        System.out.println("  condition = '#id > 0'  → check BEFORE method (skip cache entirely)");
        System.out.println("  unless   = '#result == null' → check AFTER method (don't store null)");
        System.out.println(CONDITION_EXAMPLE);

        System.out.println("=== 4. Key Generation ===");
        System.out.println(KEY_EXAMPLES);

        System.out.println("\n=== @Cacheable FLOW DIAGRAM (Interview) ===");
        System.out.println("  ┌─────────────────────────────────────────┐");
        System.out.println("  │  Call @Cacheable(\"users\") getUser(1L)  │");
        System.out.println("  │            │                            │");
        System.out.println("  │            v                            │");
        System.out.println("  │  ┌──────────────────┐                  │");
        System.out.println("  │  │ Generate Key     │                  │");
        System.out.println("  │  │ users::1         │                  │");
        System.out.println("  │  └────────┬─────────┘                  │");
        System.out.println("  │           v                            │");
        System.out.println("  │  ┌──────────────────┐                  │");
        System.out.println("  │  │ condition met?   │ ─NO──→ Execute   │");
        System.out.println("  │  └────────┬─────────┘        method    │");
        System.out.println("  │           YES                          │");
        System.out.println("  │           v                            │");
        System.out.println("  │  ┌──────────────────┐                  │");
        System.out.println("  │  │ Key in cache?    │                  │");
        System.out.println("  │  └────────┬─────────┘                  │");
        System.out.println("  │      YES        NO                     │");
        System.out.println("  │       │          │                     │");
        System.out.println("  │       v          v                     │");
        System.out.println("  │  ┌────────┐ ┌──────────┐              │");
        System.out.println("  │  │ Return │ │ Execute  │              │");
        System.out.println("  │  │ Cached │ │ Method   │              │");
        System.out.println("  │  │ Value  │ │ (DB call)│              │");
        System.out.println("  │  └────────┘ └────┬─────┘              │");
        System.out.println("  │                    │                    │");
        System.out.println("  │                    v                    │");
        System.out.println("  │  ┌──────────────────┐                  │");
        System.out.println("  │  │ unless met?      │ ─YES──→ Don't   │");
        System.out.println("  │  └────────┬─────────┘        cache    │");
        System.out.println("  │           NO                           │");
        System.out.println("  │           v                            │");
        System.out.println("  │  ┌──────────────────┐                  │");
        System.out.println("  │  │ Store in Cache   │                  │");
        System.out.println("  │  └────────┬─────────┘                  │");
        System.out.println("  │           v                            │");
        System.out.println("  │  ┌──────────────────┐                  │");
        System.out.println("  │  │ Return Value     │                  │");
        System.out.println("  │  └──────────────────┘                  │");
        System.out.println("  └─────────────────────────────────────────┘");

        System.out.println("\n============================================");
        System.out.println("  @Cacheable: Check-first-then-execute");
        System.out.println("============================================");
    }
}
