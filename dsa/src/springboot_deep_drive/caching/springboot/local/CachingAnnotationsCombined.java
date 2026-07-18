package springboot_deep_drive.caching.springboot.local;

/*
 * ============================================================================
 *  @Caching & @CacheConfig — COMBINED & CLASS-LEVEL CACHING
 * ============================================================================
 *
 *  SIMPLE DEFINITION:
 *  @Caching lets you apply MULTIPLE cache annotations on ONE method.
 *  @CacheConfig sets DEFAULT cache settings at the CLASS level so you
 *  don't repeat the same cache name on every method.
 *
 *  REAL-LIFE ANALOGY:
 *  @Caching: Like a to-do list with multiple tasks — "update the recipe
 *  card AND remove the old sticky note AND file a copy in the cabinet."
 *  @CacheConfig: Like putting "KITCHEN" label on the whole folder instead
 *  of writing it on every single page inside.
 *
 * ============================================================================
 *  @Caching — Multiple Annotations on One Method
 * ============================================================================
 *
 *  SYNTAX:
 *  @Caching(
 *      evict = {
 *          @CacheEvict(value = "users", key = "#user.id"),
 *          @CacheEvict(value = "users", key = "#user.email")
 *      },
 *      put = {
 *          @CachePut(value = "users", key = "#user.username")
 *      }
 *  )
 *  public User saveUser(User user) { ... }
 *
 *  AVAILABLE COMBINATIONS:
 *  ┌──────────────┬────────────────────────────────────────────────┐
 *  │ Attribute    │ Purpose                                        │
 *  ├──────────────┼────────────────────────────────────────────────┤
 *  │ evict        │ Array of @CacheEvict to apply                  │
 *  │ put          │ Array of @CachePut to apply                    │
 *  │ cacheable    │ Array of @Cacheable to apply (rarely needed)   │
 *  └──────────────┴────────────────────────────────────────────────┘
 *
 *  ┌─────────────────────────────────────────────────────────────────┐
 *  │  WARNING: @Cacheable in @Caching can cause confusion because    │
 *  │  the method ALWAYS executes (due to other annotations needing  │
 *  │  execution). @Cacheable's "skip method on hit" is overridden.  │
 *  │  Use @Cacheable alone if you want skip-on-hit behavior.        │
 *  └─────────────────────────────────────────────────────────────────┘
 *
 * ============================================================================
 *  @CacheConfig — Class-Level Cache Defaults
 * ============================================================================
 *
 *  @Service
 *  @CacheConfig(cacheNames = "users")   // ← Default cache name for ALL methods
 *  public class UserService {
 *
 *      @Cacheable(key = "#id")           // cacheNames inherited: value="users"
 *      public User getUser(Long id) { ... }
 *
 *      @CachePut(key = "#user.id")       // cacheNames inherited: value="users"
 *      public User saveUser(User user) { ... }
 *
 *      @CacheEvict(key = "#id")          // cacheNames inherited: value="users"
 *      public void deleteUser(Long id) { ... }
 *  }
 *
 *  @CacheConfig OPTIONS:
 *  ┌────────────────┬────────────────────────────────────────────────┐
 *  │ Attribute      │ Description                                   │
 *  ├────────────────┼────────────────────────────────────────────────┤
 *  │ cacheNames     │ Default cache name(s) for all methods         │
 *  │ keyGenerator   │ Default key generator bean name               │
 *  │ cacheManager   │ Default cache manager bean name               │
 *  └────────────────┴────────────────────────────────────────────────┘
 *
 *  NOTE: Method-level annotations OVERRIDE class-level @CacheConfig.
 *
 * ============================================================================
 *  COMPLETE SERVICE EXAMPLE (All 5 patterns)
 * ============================================================================
 *
 *  @Service
 *  @CacheConfig(cacheNames = "users")
 *  @Slf4j
 *  public class UserService {
 *
 *      // READ: Check cache, skip on hit
 *      @Cacheable(key = "#id")
 *      public User getUser(Long id) {
 *          return repository.findById(id).orElse(null);
 *      }
 *
 *      // CREATE: Execute + put in cache (by id AND email)
 *      @Caching(put = {
 *          @CachePut(key = "#result.id"),
 *          @CachePut(key = "#result.email")
 *      })
 *      public User createUser(User user) {
 *          return repository.save(user);
 *      }
 *
 *      // UPDATE: Execute + put in cache
 *      @CachePut(key = "#user.id")
 *      public User updateUser(User user) {
 *          return repository.save(user);
 *      }
 *
 *      // DELETE: Evict from cache
 *      @CacheEvict(key = "#id")
 *      public void deleteUser(Long id) {
 *          repository.deleteById(id);
 *      }
 *
 *      // BULK DELETE: Evict entire cache
 *      @CacheEvict(allEntries = true)
 *      public void deleteAllUsers() {
 *          repository.deleteAll();
 *      }
 *  }
 *
 * ============================================================================
 *  COMMON COMBINATION PATTERNS
 * ============================================================================
 *
 *  PATTERN 1: Update + Evict related caches
 *    @Caching(
 *        put = @CachePut(value = "users", key = "#user.id"),
 *        evict = {
 *            @CacheEvict(value = "userSearch", allEntries = true),
 *            @CacheEvict(value = "userCounts", allEntries = true)
 *        }
 *    )
 *    public User saveUser(User user)
 *
 *  PATTERN 2: Update multiple lookup keys
 *    @Caching(put = {
 *        @CachePut(value = "products", key = "#p.id"),
 *        @CachePut(value = "products", key = "#p.sku"),
 *        @CachePut(value = "products", key = "#p.barcode")
 *    })
 *    public Product saveProduct(Product p)
 *
 *  PATTERN 3: Evict from all related caches on delete
 *    @Caching(evict = {
 *        @CacheEvict(value = "users", key = "#id"),
 *        @CacheEvict(value = "profiles", key = "#id"),
 *        @CacheEvict(value = "permissions", key = "#id")
 *    })
 *    public void deleteUser(Long id)
 *
 * ============================================================================
 *  INTERVIEW Q&A — @Caching & @CacheConfig
 * ============================================================================
 *
 *  Q: When should I use @Caching instead of separate annotations?
 *  A: When one operation affects MULTIPLE caches. Example: saving a
 *     user should update cache by ID + email + username. @Caching
 *     bundles all operations in one atomic annotation call.
 *
 *  Q: Is @Caching transactional? (All-or-nothing?)
 *  A: NO. Each cache operation is independent. If one @CachePut fails,
 *     the others still execute. For transactional behavior, wrap in
 *     a @Transactional method and rely on DB rollback.
 *
 *  Q: What's the advantage of @CacheConfig?
 *  A: DRY — avoids repeating cacheNames on every method. Also makes
 *     it easy to rename the cache for the entire service (change one
 *     line instead of N lines).
 *
 *  Q: @CacheConfig vs method-level — which takes priority?
 *  A: Method-level OVERRIDES class-level. If @CacheConfig says
 *     cacheNames="users" but method says @Cacheable("admins"),
 *     the method uses "admins".
 *
 *  Q: Can I use @CacheConfig on non-Service classes?
 *  A: Yes, any Spring bean (marked with @Component, @Service, etc.)
 *     can use @CacheConfig. But it's most commonly used on @Service.
 *
 *  Q: What happens if @Caching has both @Cacheable and @CachePut?
 *  A: The method ALWAYS executes (because @CachePut forces execution).
 *     @Cacheable's "skip on hit" is effectively ignored. Avoid this
 *     combination — it's confusing. Use @CachePut + @CacheEvict instead.
 */

public class CachingAnnotationsCombined {

    static final String COMPLETE_SERVICE = """
        @Service
        @CacheConfig(cacheNames = \"users\")
        public class UserService {

            // READ
            @Cacheable(key = \"#id\")
            public User getUser(Long id) { ... }

            // CREATE (update cache by id + email)
            @Caching(put = {
                @CachePut(key = \"#result.id\"),
                @CachePut(key = \"#result.email\")
            })
            public User createUser(User user) { ... }

            // UPDATE
            @CachePut(key = \"#user.id\")
            public User updateUser(User user) { ... }

            // DELETE
            @CacheEvict(key = \"#id\")
            public void deleteUser(Long id) { ... }

            // BULK DELETE
            @CacheEvict(allEntries = true, beforeInvocation = true)
            public void deleteAll() { ... }
        }
        """;

    static final String CACHING_PATTERNS = """
        // PATTERN 1: Save + clear search indexes
        @Caching(
            put = @CachePut(value = \"users\", key = \"#user.id\"),
            evict = {
                @CacheEvict(value = \"searchResults\", allEntries = true),
                @CacheEvict(value = \"userStats\", allEntries = true)
            }
        )
        public User saveUser(User user) { ... }

        // PATTERN 2: Multiple lookup keys for same entity
        @Caching(put = {
            @CachePut(value = \"products\", key = \"#p.id\"),
            @CachePut(value = \"products\", key = \"#p.sku\"),
            @CachePut(value = \"products\", key = \"#p.barcode\")
        })
        public Product saveProduct(Product p) { ... }

        // PATTERN 3: Delete user + cascade eviction
        @Caching(evict = {
            @CacheEvict(value = \"users\", key = \"#id\"),
            @CacheEvict(value = \"profiles\", key = \"#id\"),
            @CacheEvict(value = \"permissions\", key = \"#id\"),
            @CacheEvict(value = \"orders\", key = \"#id\")
        })
        public void deleteUserCascade(Long id) { ... }
        """;

    static final String CACHECONFIG_EXAMPLE = """
        // WITHOUT @CacheConfig (repetitive):
        @Service
        public class UserService {
            @Cacheable(value = \"users\", key = \"#id\")
            public User getUser(Long id) { ... }

            @CachePut(value = \"users\", key = \"#user.id\")
            public User saveUser(User user) { ... }

            @CacheEvict(value = \"users\", key = \"#id\")
            public void deleteUser(Long id) { ... }
        }

        // WITH @CacheConfig (clean):
        @Service
        @CacheConfig(cacheNames = \"users\")
        public class UserService {
            @Cacheable(key = \"#id\")
            public User getUser(Long id) { ... }

            @CachePut(key = \"#user.id\")
            public User saveUser(User user) { ... }

            @CacheEvict(key = \"#id\")
            public void deleteUser(Long id) { ... }
        }

        // SAME EFFECT — but second is cleaner!
        """;

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   @Caching & @CacheConfig");
        System.out.println("============================================");

        System.out.println("\n=== Complete Service (All Annotations) ===");
        System.out.println(COMPLETE_SERVICE);

        System.out.println("\n=== @Caching — Common Patterns ===");
        System.out.println(CACHING_PATTERNS);

        System.out.println("\n=== @CacheConfig — Class-Level Defaults ===");
        System.out.println(CACHECONFIG_EXAMPLE);

        System.out.println("\n=== ANNOTATION DECISION TREE (Interview) ===");
        System.out.println("  ┌─────────────────────────────────────────┐");
        System.out.println("  │  What do you want to do?                │");
        System.out.println("  ├─────────────────────────────────────────┤");
        System.out.println("  │  READ data?                             │");
        System.out.println("  │     → @Cacheable                        │");
        System.out.println("  │     (skip method if cached)             │");
        System.out.println("  ├─────────────────────────────────────────┤");
        System.out.println("  │  WRITE data + update cache?             │");
        System.out.println("  │     → @CachePut                         │");
        System.out.println("  │     (always execute + store)            │");
        System.out.println("  ├─────────────────────────────────────────┤");
        System.out.println("  │  DELETE data + clear cache?             │");
        System.out.println("  │     → @CacheEvict                       │");
        System.out.println("  │     (remove stale entries)              │");
        System.out.println("  ├─────────────────────────────────────────┤");
        System.out.println("  │  Multiple cache operations?             │");
        System.out.println("  │     → @Caching                          │");
        System.out.println("  │     (put + evict combinations)          │");
        System.out.println("  ├─────────────────────────────────────────┤");
        System.out.println("  │  Reduce repetition?                     │");
        System.out.println("  │     → @CacheConfig(cacheNames = \"x\")   │");
        System.out.println("  │     (class-level defaults)              │");
        System.out.println("  └─────────────────────────────────────────┘");

        System.out.println("\n============================================");
        System.out.println("  NEXT: LocalCacheInterviewQA");
        System.out.println("============================================");
    }
}
