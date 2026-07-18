package springboot_deep_drive.caching.springboot.local;

/*
 * ============================================================================
 *  @CachePut ANNOTATION — WRITE CACHE
 * ============================================================================
 *
 *  SIMPLE DEFINITION:
 *  @CachePut ALWAYS executes the method AND updates the cache with
 *  the result. Unlike @Cacheable, it NEVER skips the method.
 *  Used for: update operations where cache must stay fresh.
 *
 *  REAL-LIFE ANALOGY:
 *  A chef updates a recipe. He ALWAYS cooks the new version (executes
 *  method) and updates his recipe book (cache) with the new result.
 *  He never skips cooking just because the old recipe is in the book.
 *
 * ============================================================================
 *  @CachePut ANNOTATION BREAKDOWN
 * ============================================================================
 *
 *  @CachePut(
 *      value = "users",         // Cache name to update
 *      key = "#user.id",        // Key for the cached entry
 *      condition = "#user.id != null",  // Only update if true
 *      unless = "#result == null"       // Don't update if true
 *  )
 *  public User updateUser(User user) { ... }
 *
 *  ┌────────────┬──────────┬────────────────────────────────────────┐
 *  │ Attribute  │ Required │ Description                            │
 *  ├────────────┼──────────┼────────────────────────────────────────┤
 *  │ value      │ ✅       │ Cache name(s) to update               │
 *  │ key        │ ❌       │ SpEL key (default: all args)           │
 *  │ condition  │ ❌       │ Execute cache update ONLY if true     │
 *  │ unless     │ ❌       │ Skip cache update if true             │
 *  └────────────┴──────────┴────────────────────────────────────────┘
 *
 * ============================================================================
 *  @Cacheable vs @CachePut — COMPARISON
 * ============================================================================
 *
 *  ┌──────────────┬─────────────────────────┬─────────────────────────┐
 *  │ Aspect       │ @Cacheable              │ @CachePut               │
 *  ├──────────────┼─────────────────────────┼─────────────────────────┤
 *  │ Method exec  │ SKIPPED on cache hit    │ ALWAYS executes        │
 *  │ Purpose      │ READ (fetch data)       │ WRITE (update cache)   │
 *  │ Cache check  │ Before method           │ After method           │
 *  │ Cache update │ On miss only            │ Every time             │
 *  │ Common use   │ getById(), findByEmail()│ save(), update()       │
 *  │ Return value │ Cached result           │ Cached result          │
 *  └──────────────┴─────────────────────────┴─────────────────────────┘
 *
 * ============================================================================
 *  COMMON PATTERN: @Cacheable for reads + @CachePut for writes
 * ============================================================================
 *
 *  @Service
 *  public class ProductService {
 *
 *      // READ: Check cache first
 *      @Cacheable(value = "products", key = "#id")
 *      public Product getProduct(Long id) { ... }
 *
 *      // WRITE: Always execute AND update cache
 *      @CachePut(value = "products", key = "#product.id")
 *      public Product saveProduct(Product product) {
 *          return repository.save(product);
 *      }
 *
 *      // WRITE: Always execute AND update cache
 *      @CachePut(value = "products", key = "#product.id")
 *      public Product updateProduct(Product product) {
 *          return repository.save(product);
 *      }
 *  }
 *
 *  FLOW:
 *    productService.getProduct(1L)     → MISS → DB → cache[1] = product
 *    productService.getProduct(1L)     → HIT  → returns cached (no DB)
 *    productService.updateProduct(p1)  → DB update → cache[1] = updated
 *    productService.getProduct(1L)     → HIT  → returns UPDATED value!
 *
 * ============================================================================
 *  @CachePut with DIFFERENT KEY than lookup
 * ============================================================================
 *
 *  Sometimes you need to update multiple cache entries on one write:
 *
 *  @Caching(put = {
 *      @CachePut(value = "users", key = "#user.id"),
 *      @CachePut(value = "users", key = "#user.email"),
 *      @CachePut(value = "users", key = "#user.username")
 *  })
 *  public User saveUser(User user) { ... }
 *
 *  Now lookup by id, email, OR username will all hit cache!
 *
 * ============================================================================
 *  DANGER: @CachePut + Cache Miss Race Condition
 * ============================================================================
 *
 *  Thread A: getProduct(1L) → MISS → loading from DB
 *  Thread B: updateProduct(p1) → @CachePut runs → cache[1] = NEW
 *  Thread A: loads from DB → returns OLD value (does NOT update cache!)
 *
 *  Now cache has NEW, but Thread A returned OLD data.
 *  Next caller gets NEW (correct) but stale was served momentarily.
 *  Solution: use @CacheEvict instead of @CachePut, or accept eventual consistency.
 *
 * ============================================================================
 *  INTERVIEW Q&A — @CachePut
 * ============================================================================
 *
 *  Q: Why would you use @CachePut instead of @CacheEvict + next read?
 *  A: @CachePut proactively updates cache → next read is HIT.
 *     @CacheEvict removes entry → next read is MISS → must load from DB.
 *     @CachePut saves one DB call at the cost of always executing.
 *     Use @CachePut when you KNOW the new value (updates).
 *     Use @CacheEvict when you don't have the new value (deletes).
 *
 *  Q: Can @CachePut be used with a void method?
 *  A: Technically yes, but it caches null (pointless). Use @CacheEvict
 *     instead for void methods (delete/clear operations).
 *
 *  Q: What if @CachePut key doesn't exist yet?
 *  A: It creates the entry. Like put() on a map. First access = first cache.
 *
 *  Q: @CachePut vs repository.save() returning a different object?
 *  A: @CachePut caches the RETURN value. If save() returns a new instance
 *     with generated ID, make sure the key expression matches the new ID.
 *     Example: key="#result.id" (use result, not argument).
 */

public class CachePutAnnotation {

    static final String COMPARISON = """
        @Service
        public class UserService {

            // === READ: @Cacheable ===
            @Cacheable(value = \"users\", key = \"#id\")
            public User getUser(Long id) {
                System.out.println(\"DB call: getUser(\" + id + \")\");
                return repository.findById(id).orElse(null);
            }

            // === WRITE: @CachePut ===
            @CachePut(value = \"users\", key = \"#user.id\")
            public User createUser(User user) {
                System.out.println(\"DB call: createUser(\" + user + \")\");
                return repository.save(user);
            }

            // === UPDATE: @CachePut ===
            @CachePut(value = \"users\", key = \"#user.id\")
            public User updateUser(User user) {
                System.out.println(\"DB call: updateUser(\" + user + \")\");
                return repository.save(user);
            }
        }

        // DEMO:
        // getUser(1L)       → MISS → DB → cache[1] = data
        // getUser(1L)       → HIT  → NO DB (cached)
        // updateUser(data)  → DB   → cache[1] = updated data
        // getUser(1L)       → HIT  → returns updated (NO DB!)
        """;

    static final String MULTI_KEY_EXAMPLE = """
        @Caching(put = {
            @CachePut(value = \"users\", key = \"#user.id\"),
            @CachePut(value = \"users\", key = \"#user.email\"),
            @CachePut(value = \"users\", key = \"#user.username\")
        })
        public User saveUser(User user) {
            return repository.save(user);
        }

        // Now ALL these lookups hit cache:
        // getUserById(1L)        → HIT (cached by #user.id)
        // getUserByEmail(\"a@b\") → HIT (cached by #user.email)
        // getUserByUsername(\"alice\") → HIT (cached by #user.username)
        """;

    static final String KEY_WITH_RESULT = """
        // Use #result when the cache key comes from the return value
        @CachePut(value = \"users\", key = \"#result.id\")
        public User createUser(String name, String email) {
            // ID is auto-generated by DB
            User user = new User(name, email);
            return repository.save(user);  // user.id now has value
        }

        // Also valid: condition on result
        @CachePut(value = \"users\", key = \"#user.id\",
                  unless = \"#result == null\")
        public User updateUser(User user) {
            return repository.save(user);
        }
        """;

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   @CachePut — Deep Dive");
        System.out.println("============================================");

        System.out.println("\n=== @Cacheable vs @CachePut ===");
        System.out.println("  ┌──────────────────┬─────────────────────────┬────────────────────────┐");
        System.out.println("  │                  │ @Cacheable              │ @CachePut              │");
        System.out.println("  ├──────────────────┼─────────────────────────┼────────────────────────┤");
        System.out.println("  │ Method executes? │ Skipped on HIT         │ ALWAYS executes        │");
        System.out.println("  │ Cache update     │ On MISS only           │ Every call             │");
        System.out.println("  │ Best for         │ READ operations        │ WRITE operations       │");
        System.out.println("  │ Example          │ getById(), findByName()│ save(), update()       │");
        System.out.println("  └──────────────────┴─────────────────────────┴────────────────────────┘");

        System.out.println("\n=== Complete Service Example ===");
        System.out.println(COMPARISON);

        System.out.println("=== Multiple Keys (same data, multiple lookups) ===");
        System.out.println(MULTI_KEY_EXAMPLE);

        System.out.println("=== Key from Result ===");
        System.out.println(KEY_WITH_RESULT);

        System.out.println("\n=== FLOW DIAGRAM ===");
        System.out.println("  ┌─────────────────────────────────────────┐");
        System.out.println("  │  Call @CachePut(\"users\") saveUser()    │");
        System.out.println("  │            │                            │");
        System.out.println("  │            v                            │");
        System.out.println("  │  ┌──────────────────┐                  │");
        System.out.println("  │  │ Execute Method    │                  │");
        System.out.println("  │  │ (ALWAYS runs)    │                  │");
        System.out.println("  │  └────────┬─────────┘                  │");
        System.out.println("  │           v                            │");
        System.out.println("  │  ┌──────────────────┐                  │");
        System.out.println("  │  │ unless = true?   │ ─YES──→ Skip    │");
        System.out.println("  │  └────────┬─────────┘        cache    │");
        System.out.println("  │           NO              update      │");
        System.out.println("  │           v                            │");
        System.out.println("  │  ┌──────────────────┐                  │");
        System.out.println("  │  │ Update Cache     │                  │");
        System.out.println("  │  │ key = #user.id   │                  │");
        System.out.println("  │  └────────┬─────────┘                  │");
        System.out.println("  │           v                            │");
        System.out.println("  │  ┌──────────────────┐                  │");
        System.out.println("  │  │ Return Value     │                  │");
        System.out.println("  │  └──────────────────┘                  │");
        System.out.println("  └─────────────────────────────────────────┘");

        System.out.println("\n=== BEST PRACTICE ===");
        System.out.println("  1. Use @CachePut for SAVE/UPDATE (eager cache refresh)");
        System.out.println("  2. Use @CacheEvict for DELETE (cache invalidation)");
        System.out.println("  3. Match @CachePut key with @Cacheable key");
        System.out.println("  4. Use #result for auto-generated keys");
        System.out.println("  5. Use @Caching to update multiple cache keys");

        System.out.println("\n============================================");
        System.out.println("  @CachePut: Execute-then-cache (always)");
        System.out.println("============================================");
    }
}
