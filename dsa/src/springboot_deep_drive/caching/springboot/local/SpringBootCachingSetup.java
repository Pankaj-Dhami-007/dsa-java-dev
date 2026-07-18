package springboot_deep_drive.caching.springboot.local;

/*
 * ============================================================================
 *  SPRING BOOT LOCAL CACHING — SETUP & OVERVIEW
 * ============================================================================
 *
 *  SIMPLE DEFINITION:
 *  Spring Boot caching lets you add cache to ANY method with just ONE
 *  annotation (@Cacheable). Spring handles all the boilerplate —
 *  checking cache, storing results, evicting entries.
 *
 *  REAL-LIFE ANALOGY:
 *  You tell a receptionist: "Annotate this task with @Cacheable". Now
 *  every time someone asks for the result, the receptionist checks her
 *  drawer FIRST. If found → hand it over instantly. If not → do the
 *  work, save a copy in the drawer, then hand it over.
 *
 * ============================================================================
 *  STEP 1: ADD DEPENDENCY (build.gradle / pom.xml)
 * ============================================================================
 *
 *  // Gradle (choose ONE provider):
 *  implementation 'org.springframework.boot:spring-boot-starter-cache'
 *  implementation 'com.github.ben-manes.caffeine:caffeine'     // Caffeine (BEST)
 *  implementation 'org.ehcache:ehcache'                        // EHCache
 *
 *  // Maven:
 *  <dependency>
 *      <groupId>org.springframework.boot</groupId>
 *      <artifactId>spring-boot-starter-cache</artifactId>
 *  </dependency>
 *  <dependency>
 *      <groupId>com.github.ben-manes.caffeine</groupId>
 *      <artifactId>caffeine</artifactId>
 *  </dependency>
 *
 * ============================================================================
 *  STEP 2: ENABLE CACHING (@EnableCaching)
 * ============================================================================
 *
 *  @SpringBootApplication
 *  @EnableCaching                  // ← THIS ONE LINE enables all caching
 *  public class Application {
 *      public static void main(String[] args) {
 *          SpringApplication.run(Application.class, args);
 *      }
 *  }
 *
 *  WHAT @EnableCaching DOES:
 *  1. Scans for @Cacheable, @CachePut, @CacheEvict annotations
 *  2. Creates AOP proxies around annotated methods
 *  3. Wires CacheManager (auto-detects provider if present)
 *  4. Manages cache interceptors (before/after method execution)
 *
 * ============================================================================
 *  STEP 3: CONFIGURE CACHE MANAGER (Auto or Manual)
 * ============================================================================
 *
 *  AUTO-DETECTION (Zero Config):
 *    Spring Boot auto-configures CacheManager based on classpath:
 *    - Caffeine on classpath → CaffeineCacheManager
 *    - EHCache on classpath → EhCacheCacheManager
 *    - Neither → SimpleCacheManager (ConcurrentHashMap — dev only!)
 *
 *  MANUAL CONFIG (Recommended for production):
 *    @Configuration
 *    public class CacheConfig {
 *        @Bean
 *        public CacheManager cacheManager() {
 *            CaffeineCacheManager cm = new CaffeineCacheManager("users", "products");
 *            cm.setCaffeine(Caffeine.newBuilder()
 *                .maximumSize(500)
 *                .expireAfterWrite(10, TimeUnit.MINUTES)
 *                .recordStats());
 *            return cm;
 *        }
 *    }
 *
 * ============================================================================
 *  ARCHITECTURE DIAGRAM (How Spring Caching Works)
 * ============================================================================
 *
 *  ┌─────────────────────────────────────────────────────────────────────┐
 *  │  CLIENT CODE                                                       │
 *  │  userService.getUser(1L)                                           │
 *  │       │                                                            │
 *  │       v                                                            │
 *  │  ┌───────────┐                                                     │
 *  │  │ AOP Proxy │ ← @EnableCaching creates this proxy                 │
 *  │  │ (Spring)  │                                                     │
 *  │  └─────┬─────┘                                                     │
 *  │        │                                                            │
 *  │  ┌─────▼──────┐      ┌──────────────────┐                          │
 *  │  │ Cache      │ ───> │ CacheManager     │                          │
 *  │  │ Interceptor│      │ (e.g. Caffeine)  │                          │
 *  │  │ (Aspect)   │      │                  │                          │
 *  │  └─────┬──────┘      │  ┌────────────┐  │                          │
 *  │        │             │  │ users cache│  │                          │
 *  │  ┌─────▼──────┐      │  │  1L → {...}│  │                          │
 *  │  │ Actual     │      │  │  2L → {...}│  │                          │
 *  │  │ Service    │      │  └────────────┘  │                          │
 *  │  │ Method     │      └──────────────────┘                          │
 *  │  └────────────┘                                                    │
 *  └─────────────────────────────────────────────────────────────────────┘
 *
 *  FLOW on @Cacheable("users"):
 *    1. Proxy intercepts getUser(1L)
 *    2. Generates key = "1" (or custom key)
 *    3. Asks CacheManager: "Does 'users' cache have key '1'?"
 *    4. If YES → return cached value (method NEVER executes)
 *    5. If NO  → execute actual method → store result → return
 *
 * ============================================================================
 *  HOW TO EXPLAIN IN INTERVIEWS (Step-by-Step)
 * ============================================================================
 *
 *  "Spring Boot caching works through AOP proxies:
 *
 *   1. I add @EnableCaching on a @Configuration class.
 *      This creates a proxy around every bean with @Cacheable methods.
 *
 *   2. When a @Cacheable method is called, Spring generates a cache key
 *      (default: all method parameters, custom: SpEL expression).
 *
 *   3. The CacheInterceptor checks the key against the CacheManager.
 *      CacheManager is backed by a provider (Caffeine, Redis, etc.).
 *
 *   4. Cache HIT → return cached value directly (no method execution).
 *      Cache MISS → execute method, store result in cache, return.
 *
 *   5. For writes, @CachePut always executes and updates cache.
 *      @CacheEvict removes stale entries.
 *
 *   The key advantage: NO boilerplate. One annotation does it all."
 *
 * ============================================================================
 *  IMPORTANT LIMITATION: Proxy-based AOP
 * ============================================================================
 *
 *  @Cacheable only works when called FROM OUTSIDE the class.
 *  A method in the SAME class calling another @Cacheable method
 *  BYPASSES the proxy → no caching!
 *
 *  ❌ WON'T WORK:
 *  class UserService {
 *      @Cacheable("users")
 *      public User getUser(Long id) { ... }
 *
 *      public void process() {
 *          this.getUser(1L); // ← Proxy bypassed! No caching!
 *      }
 *  }
 *
 *  ✅ SOLUTIONS:
 *  1. Self-inject: @Autowired UserService self (creates proxy reference)
 *  2. Extract to another @Service bean
 *  3. Use @Cacheable on higher-level method (call from controller)
 *  4. AspectJ mode (@EnableCaching(mode = AdviceMode.ASPECTJ))
 *
 * ============================================================================
 *  APPLICATION.PROPERTIES (Common Settings)
 * ============================================================================
 *
 *  # Cache type (auto-detected, but explicit is better)
 *  spring.cache.type=caffeine          # caffeine, redis, ehcache, simple, none
 *
 *  # Cache names to create (optional — auto-created otherwise)
 *  spring.cache.cache-names=users,products,sessions
 *
 *  # Caffeine-specific (alternative to Java config)
 *  spring.cache.caffeine.spec=maximumSize=500,expireAfterWrite=10m
 *
 *  # Redis-specific (for later)
 *  spring.cache.redis.time-to-live=3600000
 *  spring.cache.redis.cache-null-values=false
 *  spring.cache.redis.use-key-prefix=true
 *
 * ============================================================================
 *  INTERVIEW Q&A — Spring Boot Caching Setup
 * ============================================================================
 *
 *  Q: What does @EnableCaching do internally?
 *  A: It registers a CacheInterceptor (AOP advice) and a CacheManager.
 *     The interceptor wraps all @Cacheable/@CachePut/@CacheEvict methods
 *     with cache lookup logic before/after method execution.
 *
 *  Q: How does Spring choose which CacheManager to use?
 *  A: Auto-detection via spring.factories or @ConditionalOnClass.
 *     Priority: Caffeine > Redis > EHCache > JCache > Simple.
 *     You can override with @Bean or spring.cache.type property.
 *
 *  Q: What happens if no CacheManager is configured?
 *  A: Spring falls back to SimpleCacheManager backed by ConcurrentHashMap.
 *     OK for dev/test but NOT for production (no TTL, no eviction, no stats).
 *
 *  Q: Can I have MULTIPLE CacheManagers?
 *  A: Yes! Primary + secondary. Use @Primary on one, qualify others.
 *     Or use CompositeCacheManager to chain multiple.
 *
 *  Q: How do I DISABLE caching for tests?
 *  A: @TestPropertySource(properties = "spring.cache.type=NONE")
 *     Or mock CacheManager. This bypasses cache entirely.
 */

public class SpringBootCachingSetup {

    // ================================================================
    //  CODE EXAMPLE: Complete Spring Boot Caching Setup
    //  (As string — won't compile without Spring dependencies)
    // ================================================================

    static final String ENABLE_CACHING_EXAMPLE = """
        @SpringBootApplication
        @EnableCaching                          // ← Activates caching
        public class Application {
            public static void main(String[] args) {
                SpringApplication.run(Application.class, args);
            }
        }
        """;

    static final String CAFFEINE_CONFIG_EXAMPLE = """
        @Configuration
        public class CacheConfig {

            @Bean
            public CacheManager cacheManager() {
                CaffeineCacheManager cm = new CaffeineCacheManager(
                    \"users\", \"products\", \"sessions\"
                );
                cm.setCaffeine(Caffeine.newBuilder()
                    .maximumSize(10_000)              // Max 10K entries
                    .expireAfterWrite(30, TimeUnit.MINUTES)  // TTL
                    .expireAfterAccess(10, TimeUnit.MINUTES) // Sliding
                    .refreshAfterWrite(5, TimeUnit.MINUTES)  // Refresh
                    .recordStats()                    // Enable metrics
                );
                cm.setAllowNullValues(false);          // Don't cache null
                return cm;
            }

            @Bean
            public CacheManager secondaryCacheManager() {
                // Simple in-memory for non-critical data
                return new SimpleCacheManager();
            }
        }
        """;

    static final String PROPERTIES_EXAMPLE = """
        # application.properties
        spring.cache.type=caffeine
        spring.cache.cache-names=users,products,sessions
        spring.cache.caffeine.spec=\\
            maximumSize=10000,\\
            expireAfterWrite=30m,\\
            recordStats
        """;

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   SPRING BOOT CACHING — SETUP & OVERVIEW");
        System.out.println("\n  Dependencies needed:");
        System.out.println("    spring-boot-starter-cache");
        System.out.println("    caffeine (for CaffeineCacheManager)");
        System.out.println("============================================");

        System.out.println("\n=== 1. @EnableCaching ===");
        System.out.println(ENABLE_CACHING_EXAMPLE);

        System.out.println("=== 2. CacheManager Config ===");
        System.out.println(CAFFEINE_CONFIG_EXAMPLE);

        System.out.println("=== 3. application.properties ===");
        System.out.println(PROPERTIES_EXAMPLE);

        System.out.println("\n=== HOW SPRING CACHING WORKS (Interview) ===");
        String[] steps = {
            "1. @EnableCaching creates AOP proxy around @Cacheable beans",
            "2. Method call → proxy intercepts → generates cache key",
            "3. Proxy asks CacheManager: 'Does this key exist?'",
            "4. HIT → return cached (method SKIPPED)",
            "5. MISS → execute method → store result → return",
            "6. @CachePut always executes + updates cache",
            "7. @CacheEvict removes stale entries post-execution"
        };
        for (String step : steps) {
            System.out.println("   " + step);
        }

        System.out.println("\n=== LIMITATION ===");
        System.out.println("   Same-class internal calls BYPASS caching!");
        System.out.println("   Solution: self-inject or extract to separate bean");

        System.out.println("\n============================================");
        System.out.println("  Next: CaffeineCacheConfig — fine-tuning");
        System.out.println("============================================");
    }
}
