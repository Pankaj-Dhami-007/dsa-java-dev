package springboot_deep_drive.caching.springboot.local;

/*
 * ============================================================================
 *  @CacheEvict ANNOTATION — CACHE INVALIDATION
 * ============================================================================
 *
 *  SIMPLE DEFINITION:
 *  @CacheEvict REMOVES entries from cache after (or before) method
 *  execution. This prevents stale data — when DB is updated, the
 *  old cache entry is deleted so next read fetches fresh data.
 *
 *  REAL-LIFE ANALOGY:
 *  A library removes an old book from the "featured shelf" when a
 *  new edition arrives. Next time someone asks for that book, the
 *  librarian goes to the main archive (DB) and gets the latest copy.
 *
 * ============================================================================
 *  @CacheEvict ANNOTATION BREAKDOWN
 * ============================================================================
 *
 *  @CacheEvict(
 *      value = "users",              // Cache name
 *      key = "#userId",              // Specific entry to evict
 *      allEntries = false,           // Clear ENTIRE cache?
 *      beforeInvocation = false,     // Evict BEFORE method runs?
 *      condition = "#userId != null" // Only evict if condition true
 *  )
 *  public void deleteUser(Long userId) { ... }
 *
 *  ┌────────────────┬──────────┬────────────────────────────────────┐
 *  │ Attribute      │ Required │ Description                        │
 *  ├────────────────┼──────────┼────────────────────────────────────┤
 *  │ value          │ ✅       │ Cache name(s) to evict from       │
 *  │ key            │ ❌       │ Specific entry key to remove       │
 *  │ allEntries     │ ❌       │ Clear the entire cache (default no)│
 *  │ beforeInvocation│ ❌      │ Evict BEFORE method executes       │
 *  │ condition      │ ❌       │ Only evict if SpEL condition true  │
 *  └────────────────┴──────────┴────────────────────────────────────┘
 *
 * ============================================================================
 *  EVICT SINGLE ENTRY vs EVICT ALL
 * ============================================================================
 *
 *  @CacheEvict(value = "users", key = "#userId")
 *  public void deleteUser(Long userId)
 *    // Removes ONLY "users::123" from cache
 *    // Other entries (users::456, etc.) remain intact
 *
 *  @CacheEvict(value = "users", allEntries = true)
 *  public void clearAllUsers()
 *    // Removes ALL entries from "users" cache
 *    // Equivalent to cacheManager.getCache("users").clear()
 *    // Useful for bulk updates, data reloads
 *
 *  ┌──────────────────┬─────────────────────┬─────────────────────┐
 *  │                  │ key = #id           │ allEntries = true   │
 *  ├──────────────────┼─────────────────────┼─────────────────────┤
 *  │ What's evicted?  │ Single entry        │ Entire cache        │
 *  │ Use case         │ Delete one user     │ Massive data update │
 *  │ Performance      │ Fast (O(1))         │ Slow for large cache│
 *  │ Cache warming    │ Auto (next read)    │ All entries cold    │
 *  └──────────────────┴─────────────────────┴─────────────────────┘
 *
 * ============================================================================
 *  beforeInvocation = true — Evict BEFORE Method Executes
 * ============================================================================
 *
 *  DEFAULT (beforeInvocation = false):
 *    @CacheEvict(value = "users", key = "#userId")
 *    public void deleteUser(Long userId) {
 *        repository.deleteById(userId);  // ← If this THROWS, eviction
 *    }                                       //   NEVER happens!
 *    // Cache still has stale entry if delete fails!
 *
 *  WITH beforeInvocation = true:
 *    @CacheEvict(value = "users", key = "#userId", beforeInvocation = true)
 *    public void deleteUser(Long userId) {
 *        repository.deleteById(userId);  // ← Even if this throws,
 *    }                                       //   cache was already evicted!
 *    // Cache cleaned BEFORE method — safe even on failure
 *
 *  WHEN TO USE:
 *    - Method might throw exception → you still want cache evicted
 *    - Method is a "best effort" delete (cache should reflect attempt)
 *    - You want to evict even if method fails (stale data > no data?)
 *
 *  DEFAULT is safer for tx: if method fails, cache stays (consistent).
 *
 * ============================================================================
 *  COMMON PATTERN: @Cacheable + @CacheEvict (The Invalidate-on-Write)
 * ============================================================================
 *
 *  @Service
 *  public class ProductService {
 *
 *      // Read
 *      @Cacheable(value = "products", key = "#id")
 *      public Product getProduct(Long id) { ... }
 *
 *      // Write: update DB + EVICT cache (next read will re-cache)
 *      @CacheEvict(value = "products", key = "#id")
 *      public Product updateProduct(Long id, Product updated) {
 *          return repository.save(updated);
 *      }
 *
 *      // Delete: remove from DB + EVICT cache
 *      @CacheEvict(value = "products", key = "#id")
 *      public void deleteProduct(Long id) {
 *          repository.deleteById(id);
 *      }
 *  }
 *
 *  This is the Cache-Aside pattern: invalidate on write.
 *  Next read after update: MISS → fresh data from DB → cache.
 *
 * ============================================================================
 *  MASS EVICTION SCENARIOS
 * ============================================================================
 *
 *  // Clear entire product cache after bulk import
 *  @CacheEvict(value = "products", allEntries = true)
 *  @Scheduled(cron = "0 0 3 * * ?")  // Daily at 3 AM
 *  public void refreshProductCache() { ... }
 *
 *  // Evict multiple caches at once
 *  @Caching(evict = {
 *      @CacheEvict(value = "products", allEntries = true),
 *      @CacheEvict(value = "productSearch", allEntries = true),
 *      @CacheEvict(value = "categories", allEntries = true)
 *  })
 *  public void massiveDataUpdate() { ... }
 *
 * ============================================================================
 *  @CacheEvict on VOID methods
 * ============================================================================
 *
 *  @CacheEvict is perfect for void methods (unlike @Cacheable/@CachePut
 *  which need a return value to cache). Common pattern:
 *
 *  @CacheEvict(value = "users", key = "#userId")
 *  public void deleteUser(Long userId) {
 *      repository.deleteById(userId);  // void return
 *  }
 *
 * ============================================================================
 *  INTERVIEW Q&A — @CacheEvict
 * ============================================================================
 *
 *  Q: Why use @CacheEvict instead of @CachePut?
 *  A: @CacheEvict simply removes the old entry. Next read will
 *     MISS and fetch fresh data. This is simpler and avoids the
 *     race condition where you might cache stale data (if the
 *     method result is outdated). @CachePut is better when you
 *     KNOW the new value is correct.
 *
 *  Q: When would you use beforeInvocation = true?
 *  A: When the method is unreliable (might throw) but you still
 *     want the cache cleaned. Example: a delete that sometimes
 *     fails — you'd rather have the cache empty (next read fetches
 *     fresh) than have stale data in cache.
 *
 *  Q: What happens if you evict a key that doesn't exist?
 *  A: Nothing. Cache eviction is idempotent — removing a non-existent
 *     key is a no-op. Safe to call multiple times.
 *
 *  Q: @CacheEvict on a method that returns a value?
 *  A: Works fine. The return value is returned normally, it's just
 *     NOT cached (unlike @CachePut). The method executes and returns,
 *     but no cache update happens.
 *
 *  Q: Can you combine @Cacheable and @CacheEvict?
 *  A: Yes, on the same method! But rarely useful.
 *     @Cacheable checks cache, @CacheEvict removes from cache.
 *     Net effect: always executes (evict before check? depends on order).
 *     Better to use @CachePut if you want execute + store.
 */

public class CacheEvictAnnotation {

    static final String BASIC_EVICT = """
        @Service
        public class UserService {

            @Cacheable(value = \"users\", key = \"#id\")
            public User getUser(Long id) { ... }

            // INVALIDATE on update (next read fetches fresh)
            @CacheEvict(value = \"users\", key = \"#id\")
            public User updateUser(Long id, User updated) {
                return repository.save(updated);
            }

            // INVALIDATE on delete
            @CacheEvict(value = \"users\", key = \"#id\")
            public void deleteUser(Long id) {
                repository.deleteById(id);
            }
        }

        // FLOW:
        // getUser(1L)          → MISS → DB → cache[1] = user
        // deleteUser(1L)       → cache evicts key "1"
        // getUser(1L)          → MISS → DB (fresh fetch — no stale data!)
        """;

    static final String ALL_ENTRIES = """
        // Evict a single entry
        @CacheEvict(value = \"users\", key = \"#userId\")
        public void deleteUser(Long userId)
        // Removes: users::123

        // Evict ENTIRE cache
        @CacheEvict(value = \"users\", allEntries = true)
        public void clearUserCache()
        // Removes: users::123, users::456, users::789... ALL of them

        // Scheduled mass eviction (nightly refresh)
        @CacheEvict(value = \"products\", allEntries = true)
        @Scheduled(cron = \"0 0 3 * * ?\")
        public void nightlyCacheRefresh() { ... }
        """;

    static final String BEFORE_INVOCATION = """
        // DEFAULT (false): evict AFTER method — if method throws, eviction SKIPPED
        @CacheEvict(value = \"users\", key = \"#id\")
        public void deleteUser(Long id) {
            throw new RuntimeException(\"DB down!\");
            // Cache NOT evicted — stale data preserved (consistent)
        }

        // beforeInvocation=true: evict BEFORE method — even if method throws
        @CacheEvict(value = \"users\", key = \"#id\", beforeInvocation = true)
        public void deleteUser(Long id) {
            throw new RuntimeException(\"DB down!\");
            // Cache WAS evicted — next read will miss (may get fresh or error)
        }

        // When to use which:
        // false (default): Transactional safety — rollback keeps cache valid
        // true:            You MUST clear cache regardless of method outcome
        """;

    static final String MASS_CLEAR = """
        @Caching(evict = {
            @CacheEvict(value = \"products\", allEntries = true),
            @CacheEvict(value = \"productSearch\", allEntries = true),
            @CacheEvict(value = \"categories\", allEntries = true),
            @CacheEvict(value = \"inventory\", allEntries = true)
        })
        public void fullProductCatalogRefresh() {
            // Called after bulk import, price update, etc.
            // All 4 caches are cleared atomically
        }
        """;

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   @CacheEvict — Deep Dive");
        System.out.println("============================================");

        System.out.println("\n=== CACHE STRATEGIES ===");
        System.out.println("  Cache-Aside (Invalidate-on-Write):");
        System.out.println("    1. Read:  @Cacheable → miss → DB → cache");
        System.out.println("    2. Write: @CacheEvict → remove from cache");
        System.out.println("    3. Read:  @Cacheable → miss → DB → cache (fresh!)");
        System.out.println("  This is the MOST COMMON pattern in Spring Boot.");

        System.out.println("\n=== 1. Single Entry Eviction ===");
        System.out.println(BASIC_EVICT);

        System.out.println("\n=== 2. All Entries vs Single Entry ===");
        System.out.println(ALL_ENTRIES);

        System.out.println("\n=== 3. beforeInvocation ===");
        System.out.println(BEFORE_INVOCATION);

        System.out.println("\n=== 4. Mass Eviction (Multiple Caches) ===");
        System.out.println(MASS_CLEAR);

        System.out.println("\n=== COMPARISON: @CachePut vs @CacheEvict ===");
        System.out.println("  ┌──────────────┬──────────────────────────┬─────────────────────────┐");
        System.out.println("  │              │ @CachePut                │ @CacheEvict             │");
        System.out.println("  ├──────────────┼──────────────────────────┼─────────────────────────┤");
        System.out.println("  │ Action       │ Updates cache with new   │ Removes entry from      │");
        System.out.println("  │              │ value                    │ cache                   │");
        System.out.println("  │ Next Read    │ HIT (has new value)     │ MISS (fetches from DB) │");
        System.out.println("  │ When to use  │ Know the new value      │ Don't have new value    │");
        System.out.println("  │              │ (save, update)          │ (delete, bulk update)   │");
        System.out.println("  │ Race cond?   │ Possible (stale write)  │ Safe (always refetches) │");
        System.out.println("  └──────────────┴──────────────────────────┴─────────────────────────┘");

        System.out.println("\n=== FLOW DIAGRAM ===");
        System.out.println("  ┌─────────────────────────────────────────┐");
        System.out.println("  │  Call @CacheEvict deleteUser(1L)        │");
        System.out.println("  │            │                            │");
        System.out.println("  │            v                            │");
        System.out.println("  │  ┌──────────────────┐                  │");
        System.out.println("  │  │ beforeInvocation? │                  │");
        System.out.println("  │  └────────┬─────────┘                  │");
        System.out.println("  │       NO        YES                    │");
        System.out.println("  │        │          │                     │");
        System.out.println("  │        │          v                     │");
        System.out.println("  │        │   ┌──────────────┐            │");
        System.out.println("  │        │   │ EVICT cache   │            │");
        System.out.println("  │        │   │ NOW           │            │");
        System.out.println("  │        │   └──────┬───────┘            │");
        System.out.println("  │        │          v                     │");
        System.out.println("  │        └────┬─────┘                    │");
        System.out.println("  │             v                           │");
        System.out.println("  │  ┌──────────────────┐                  │");
        System.out.println("  │  │ Execute Method    │                  │");
        System.out.println("  │  │ (DB operation)   │                  │");
        System.out.println("  │  └────────┬─────────┘                  │");
        System.out.println("  │         THROWS?                         │");
        System.out.println("  │      YES        NO                     │");
        System.out.println("  │       │          │                     │");
        System.out.println("  │       v          v                     │");
        System.out.println("  │  ┌────────┐ ┌──────────────┐          │");
        System.out.println("  │  │ before │ │ before=false │          │");
        System.out.println("  │  │ =true? │ │ → EVICT cache│          │");
        System.out.println("  │  │ → done │ │   AFTER      │          │");
        System.out.println("  │  │ =false │ └──────┬───────┘          │");
        System.out.println("  │  │ → cache│        v                   │");
        System.out.println("  │  │   stays│  ┌──────────┐             │");
        System.out.println("  │  │   valid│  │ Return   │             │");
        System.out.println("  │  └────────┘  └──────────┘             │");
        System.out.println("  └─────────────────────────────────────────┘");

        System.out.println("\n============================================");
        System.out.println("  @CacheEvict: Invalidate cache on write");
        System.out.println("============================================");
    }
}
