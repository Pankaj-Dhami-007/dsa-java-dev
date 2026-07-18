package springboot_deep_drive.caching.springboot.redis;

/*
 * ============================================================================
 *  REDIS + SPRING BOOT — FULL INTEGRATION GUIDE
 * ============================================================================
 *
 *  SIMPLE DEFINITION:
 *  Spring Boot integrates with Redis through two main approaches:
 *  1. @Cacheable annotations (same as Caffeine, but backed by Redis)
 *  2. RedisTemplate (direct Redis operations for advanced use cases)
 *
 *  REAL-LIFE ANALOGY:
 *  Think of Redis as a magic shared whiteboard that ALL app instances
 *  can read/write. Spring Boot gives you two ways to use it:
 *  - @Cacheable = sticky notes (auto-managed, simple)
 *  - RedisTemplate = drawing directly on the whiteboard (full control)
 *
 * ============================================================================
 *  APPROACH 1: @Cacheable with Redis (Spring Auto-Config)
 * ============================================================================
 *
 *  Once dependencies are added and application.yml configured:
 *    - Spring Auto-config creates RedisCacheManager
 *    - @Cacheable works EXACTLY like with Caffeine, but backed by Redis
 *    - Cache entries are stored as Redis keys: "users::1" → JSON value
 *    - TTL is controlled by spring.cache.redis.time-to-live
 *
 *  WHAT HAPPENS INTERNALLY:
 *  @Cacheable(value = "users", key = "#userId")
 *  public User getUser(Long userId)
 *
 *  1. Spring generates key: "users::1"
 *  2. RedisCacheManager checks: EXISTS users::1
 *  3. If FOUND → deserialize JSON → return User
 *  4. If MISS → execute method → serialize User to JSON → SET users::1
 *     → SETEX users::1 TTL (auto-expire)
 *
 *  ADVANTAGES over Caffeine:
 *    - Cache is SHARED across all app instances
 *    - Data survives app restarts (persistence)
 *    - Centralized TTL management
 *
 *  DISADVANTAGES:
 *    - Slower than Caffeine (~1ms vs ~10μs)
 *    - Network dependency (Redis must be running)
 *    - Serialization overhead (Java object ↔ JSON)
 *
 * ============================================================================
 *  APPROACH 2: RedisTemplate (Direct Redis Access)
 * ============================================================================
 *
 *  Use RedisTemplate when you need:
 *    - Custom data structures (List, Set, Sorted Set, Geo)
 *    - Atomic operations (INCR, DECR for counters)
 *    - Pub/Sub messaging
 *    - Distributed locks (SETNX)
 *    - Cache-aside pattern (manual cache control)
 *    - Operations that don't fit @Cacheable (batch, pipeline)
 *
 *  REDISTEMPLATE CONFIGURATION:
 *  @Configuration
 *  public class RedisConfig {
 *
 *      @Bean
 *      public RedisTemplate<String, Object> redisTemplate(
 *              RedisConnectionFactory connectionFactory) {
 *          RedisTemplate<String, Object> template = new RedisTemplate<>();
 *          template.setConnectionFactory(connectionFactory);
 *
 *          // Key serializer (String → bytes)
 *          template.setKeySerializer(new StringRedisSerializer());
 *
 *          // Value serializer (Object → JSON)
 *          template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
 *
 *          // Hash serializers
 *          template.setHashKeySerializer(new StringRedisSerializer());
 *          template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
 *
 *          template.afterPropertiesSet();
 *          return template;
 *      }
 *
 *      // For String-specific operations (simpler)
 *      @Bean
 *      public StringRedisTemplate stringRedisTemplate(
 *              RedisConnectionFactory connectionFactory) {
 *          return new StringRedisTemplate(connectionFactory);
 *      }
 *  }
 *
 *  REDISTEMPLATE USAGE:
 *  @Service
 *  public class RedisCacheService {
 *
 *      @Autowired
 *      private RedisTemplate<String, Object> redisTemplate;
 *
 *      public void cacheUser(User user) {
 *          redisTemplate.opsForValue().set("user:" + user.getId(), user);
 *      }
 *
 *      public User getUser(Long id) {
 *          return (User) redisTemplate.opsForValue().get("user:" + id);
 *      }
 *
 *      public void incrementCounter(String key) {
 *          redisTemplate.opsForValue().increment(key);
 *      }
 *
 *      public void addToLeaderboard(String user, double score) {
 *          redisTemplate.opsForZSet().add("leaderboard", user, score);
 *      }
 *
 *      public Set<Object> getTopUsers(int n) {
 *          return redisTemplate.opsForZSet()
 *              .reverseRange("leaderboard", 0, n - 1);
 *      }
 *
 *      public List<String> getFromList(String key, long start, long end) {
 *          return redisTemplate.opsForList().range(key, start, end);
 *      }
 *  }
 *
 * ============================================================================
 *  SERIALIZATION — CRITICAL DETAIL
 * ============================================================================
 *
 *  Redis stores bytes. Java objects must be serialized/deserialized.
 *  Options (from fastest to slowest):
 *
 *  ┌────────────────┬──────────┬───────────┬────────────────────────┐
 *  │ Serializer     │ Speed    │ Readable  │ Use Case               │
 *  ├────────────────┼──────────┼───────────┼────────────────────────┤
 *  │ String         │ Fastest  │ ✅        │ Simple strings         │
 *  │ Jackson JSON   │ Fast     │ ✅        │ General objects (best) │
 *  │ JdkSerializatn │ Slow     │ ❌        │ Default — AVOID!       │
 *  │ Kryo           │ Fastest  │ ❌        │ High-performance       │
 *  │ Protocol Buff  │ Very Fast│ ❌        │ Cross-language         │
 *  │ Snappy+JSON    │ Fast     │ ✅        │ Compressed JSON        │
 *  └────────────────┴──────────┴───────────┴────────────────────────┘
 *
 *  SPRING BOOT DEFAULT: JdkSerializationRedisSerializer (very slow!).
 *  ALWAYS override to Jackson2JsonRedisSerializer for production.
 *
 *  RECOMMENDED CONFIG:
 *  @Bean
 *  public RedisCacheConfiguration cacheConfiguration() {
 *      return RedisCacheConfiguration.defaultCacheConfig()
 *          .entryTtl(Duration.ofHours(1))
 *          .disableCachingNullValues()
 *          .serializeKeysWith(
 *              RedisSerializationContext.SerializationPair
 *                  .fromSerializer(new StringRedisSerializer()))
 *          .serializeValuesWith(
 *              RedisSerializationContext.SerializationPair
 *                  .fromSerializer(new GenericJackson2JsonRedisSerializer()));
 *  }
 *
 * ============================================================================
 *  REDIS OPERATIONS COMPLETE REFERENCE
 * ============================================================================
 *
 *  ┌────────────┬────────────────────────────┬────────────────────────┐
 *  │ Data Type  │ Common Operations          │ Use Case               │
 *  ├────────────┼────────────────────────────┼────────────────────────┤
 *  │ ValueOps   │ get, set, increment, append│ Simple cache, counters │
 *  │ ListOps    │ leftPush, rightPop, range  │ Queue, feed, timeline  │
 *  │ SetOps     │ add, members, intersect    │ Tags, friends, filters │
 *  │ ZSetOps    │ add, range, score, rank    │ Leaderboard, trending  │
 *  │ HashOps    │ put, get, entries, values  │ Object fields, profile │
 *  │ GeoOps     │ add, radius, distance      │ Nearby, location       │
 *  │ StreamOps  │ add, read, group, ack      │ Event sourcing, queue  │
 *  └────────────┴────────────────────────────┴────────────────────────┘
 *
 * ============================================================================
 *  REAL-WORLD EXAMPLE: PRODUCT CATALOG WITH REDIS
 * ============================================================================
 *
 *  @Service
 *  public class ProductRedisService {
 *
 *      @Autowired
 *      private RedisTemplate<String, Object> redis;
 *
 *      private static final String KEY_PREFIX = "product:";
 *      private static final String TRENDING_KEY = "trending:products";
 *      private static final String STOCK_KEY = "stock:";
 *
 *      // Cache-Aside with Redis
 *      public Product getProduct(Long id) {
 *          String key = KEY_PREFIX + id;
 *
 *          // 1. Check Redis
 *          Product cached = (Product) redis.opsForValue().get(key);
 *          if (cached != null) {
 *              return cached;
 *          }
 *
 *          // 2. MISS → load from DB
 *          Product product = productRepository.findById(id);
 *
 *          // 3. Cache in Redis with 1h TTL
 *          redis.opsForValue().set(key, product, 1, TimeUnit.HOURS);
 *
 *          return product;
 *      }
 *
 *      // Update product + refresh cache
 *      public Product updateProduct(Product product) {
 *          Product saved = productRepository.save(product);
 *          redis.opsForValue().set(
 *              KEY_PREFIX + saved.getId(), saved, 1, TimeUnit.HOURS);
 *          return saved;
 *      }
 *
 *      // Delete product + evict cache
 *      public void deleteProduct(Long id) {
 *          productRepository.deleteById(id);
 *          redis.delete(KEY_PREFIX + id);
 *      }
 *
 *      // Trending products (Sorted Set)
 *      public void recordProductView(Long productId) {
 *          redis.opsForZSet().incrementScore(
 *              TRENDING_KEY, productId.toString(), 1);
 *      }
 *
 *      public List<Product> getTrending(int limit) {
 *          Set<Object> ids = redis.opsForZSet()
 *              .reverseRange(TRENDING_KEY, 0, limit - 1);
 *          return ids.stream()
 *              .map(id -> getProduct(Long.valueOf(id.toString())))
 *              .collect(Collectors.toList());
 *      }
 *
 *      // Stock counter (atomic)
 *      public boolean decrementStock(Long productId, int quantity) {
 *          String key = STOCK_KEY + productId;
 *          Long remaining = redis.opsForValue().decrement(key, quantity);
 *          if (remaining < 0) {
 *              // Rollback
 *              redis.opsForValue().increment(key, quantity);
 *              return false; // Out of stock
 *          }
 *          return true;
 *      }
 *  }
 *
 * ============================================================================
 *  REDIS CACHE CONFIGURATION (Full Java Config)
 * ============================================================================
 *
 *  @Configuration
 *  @EnableCaching
 *  public class RedisCacheConfig {
 *
 *      @Bean
 *      public RedisCacheManager cacheManager(RedisConnectionFactory cf) {
 *          // Per-cache configurations
 *          Map<String, RedisCacheConfiguration> configs = new HashMap<>();
 *
 *          configs.put("products", RedisCacheConfiguration.defaultCacheConfig()
 *              .entryTtl(Duration.ofHours(1))
 *              .prefixCacheNameWith("prod:"));
 *
 *          configs.put("sessions", RedisCacheConfiguration.defaultCacheConfig()
 *              .entryTtl(Duration.ofMinutes(30))
 *              .prefixCacheNameWith("sess:"));
 *
 *          configs.put("stock", RedisCacheConfiguration.defaultCacheConfig()
 *              .entryTtl(Duration.ofSeconds(30))
 *              .prefixCacheNameWith("stk:"));
 *
 *          return RedisCacheManager.builder(cf)
 *              .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
 *                  .entryTtl(Duration.ofHours(1))
 *                  .serializeValuesWith(
 *                      SerializationPair.fromSerializer(
 *                          new GenericJackson2JsonRedisSerializer())))
 *              .withInitialCacheConfigurations(configs)
 *              .build();
 *      }
 *  }
 *
 * ============================================================================
 *  INTERVIEW Q&A — Redis with Spring Boot
 * ============================================================================
 *
 *  Q: How does Spring Boot auto-configure Redis caching?
 *  A: When spring-boot-starter-data-redis is on classpath and
 *     spring.cache.type=redis, Spring Boot creates:
 *     1. RedisConnectionFactory (LettuceConnectionFactory by default)
 *     2. RedisCacheManager (wraps RedisTemplate for @Cacheable support)
 *     3. StringRedisTemplate (for string operations)
 *     All with sensible defaults from application.yml.
 *
 *  Q: What is the difference between Lettuce and Jedis?
 *  A: Lettuce (default): Netty-based, non-blocking, thread-safe, async,
 *     supports reactive, connection pooling optional.
 *     Jedis: blocking, NOT thread-safe (needs pool), simpler.
 *     Lettuce is recommended for new projects (Spring Boot default).
 *
 *  Q: Why should I override the default serialization?
 *  A: Spring's default is JdkSerializationRedisSerializer — it's slow,
 *     produces large binary output, and the cached data is not human-readable.
 *     Jackson2JsonRedisSerializer is faster, produces readable JSON, and
 *     allows non-Java clients to read the cache (important for debugging).
 *
 *  Q: @Cacheable vs RedisTemplate — when to use what?
 *  A: @Cacheable: simple key-value caching, declarative, no boilerplate.
 *     Use for standard CRUD cache (getById, findByEmail).
 *     RedisTemplate: complex data structures, atomic operations, pub/sub,
 *     pipeline/batch operations, distributed locks.
 *     Use for counters, leaderboards, queues, geo queries.
 *
 *  Q: How do you handle Redis connection failure?
 *  A: Spring Boot provides resilience options:
 *     1. spring.redis.timeout → fail fast if Redis is down
 *     2. Circuit breaker pattern (Resilience4j) → fallback to DB
 *     3. @Cacheable with sync=false → exception propagation
 *     4. Can configure RedisCacheManager.builder()
 *        .cacheWriter(RedisCacheWriter.nonLockingRedisCacheWriter(cf))
 *        .withCacheErrorHandler(new SimpleCacheErrorHandler())
 *     In production: Redis Sentinel/Cluster for high availability.
 *
 *  Q: How do you implement distributed locking with Redis?
 *  A: redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit)
 *     = SET key value NX EX (atomic lock acquisition).
 *     Release: redisTemplate.delete(key) — only if owned.
 *     For production: Redlock algorithm (quorum-based) with Redisson library.
 *
 *  Q: What is cache stampede in Redis and how to prevent?
 *  A: Multiple requests simultaneously miss cache and hit DB.
 *     Prevention:
 *     1. @Cacheable(sync=true) — Spring key-level locking
 *     2. SETNX distributed lock — only 1 loads, others wait
 *     3. "Dog-pile effect" prevention — stale-while-revalidate
 *     4. Cache warming before peak traffic
 *
 *  Q: How do you monitor Redis cache performance?
 *  A: 1. Redis INFO command — hits, misses, evictions, memory
 *     2. Spring Boot Actuator / Micrometer — Redis metrics
 *     3. RedisInsight (GUI) — real-time monitoring
 *     4. Grafana + Prometheus — Redis Exporter dashboard
 *     Key metrics: hit ratio, eviction rate, memory fragmentation,
 *     connected clients, commands processed per second.
 */

public class RedisSpringIntegration {

    static final String REDIS_TEMPLATE_CONFIG = """
        @Configuration
        public class RedisConfig {

            @Bean
            public RedisTemplate<String, Object> redisTemplate(
                    RedisConnectionFactory cf) {
                RedisTemplate<String, Object> t = new RedisTemplate<>();
                t.setConnectionFactory(cf);

                // Key: String → bytes (simple)
                t.setKeySerializer(new StringRedisSerializer());

                // Value: Object → JSON (human-readable, fast)
                t.setValueSerializer(
                    new GenericJackson2JsonRedisSerializer());

                // Hash fields (for Redis Hash data type)
                t.setHashKeySerializer(new StringRedisSerializer());
                t.setHashValueSerializer(
                    new GenericJackson2JsonRedisSerializer());

                t.afterPropertiesSet();
                return t;
            }

            @Bean
            public StringRedisTemplate stringRedisTemplate(
                    RedisConnectionFactory cf) {
                return new StringRedisTemplate(cf);
            }
        }
        """;

    static final String REDIS_SERVICE_EXAMPLE = """
        @Service
        public class ProductRedisService {

            @Autowired
            private RedisTemplate<String, Object> redis;

            // String ops — simple cache
            public Product getProduct(Long id) {
                String key = "product:" + id;
                Product p = (Product) redis.opsForValue().get(key);
                if (p == null) {
                    p = repository.findById(id);
                    redis.opsForValue().set(key, p, 1, TimeUnit.HOURS);
                }
                return p;
            }

            // ZSet ops — trending leaderboard
            public void recordView(Long productId) {
                redis.opsForZSet().incrementScore(
                    \"trending\", productId.toString(), 1);
            }
            public Set<Object> getTrending(int n) {
                return redis.opsForZSet()
                    .reverseRange(\"trending\", 0, n - 1);
            }

            // Value ops — atomic counter
            public long incrementViews(String page) {
                return redis.opsForValue().increment(\"views:\" + page);
            }

            // List ops — feed/queue
            public void addToFeed(Long userId, String postId) {
                redis.opsForList().leftPush(\"feed:\" + userId, postId);
                redis.opsForList().trim(\"feed:\" + userId, 0, 999);
            }

            // Set ops — unique tags
            public void addTag(String tag, Long productId) {
                redis.opsForSet().add(\"tag:\" + tag, productId.toString());
            }

            // Geo ops — nearby
            public void updateLocation(String driverId,
                                        double lng, double lat) {
                redis.opsForGeo()
                    .add(\"drivers\", new Point(lng, lat), driverId);
            }
            public List<String> findNearby(double lng, double lat, double km) {
                Circle c = new Circle(new Point(lng, lat),
                    new Distance(km, RedisGeoCommands.DistanceUnit.KILOMETERS));
                GeoResults<GeoLocation<String>> results =
                    redis.opsForGeo().radius(\"drivers\", c);
                return results.getContent().stream()
                    .map(r -> r.getContent().getName())
                    .collect(Collectors.toList());
            }
        }
        """;

    static final String PER_CACHE_TTL_CONFIG = """
        @Configuration
        @EnableCaching
        public class RedisCacheConfig {

            @Bean
            public RedisCacheManager cacheManager(RedisConnectionFactory cf) {
                // Default config
                RedisCacheConfiguration defaults = RedisCacheConfig
                    .defaultCacheConfig()
                    .entryTtl(Duration.ofHours(1))
                    .serializeValuesWith(SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()))
                    .disableCachingNullValues();

                // Per-cache overrides
                Map<String, RedisCacheConfiguration> configs = new HashMap<>();
                configs.put(\"products\",
                    defaults.entryTtl(Duration.ofHours(2)));
                configs.put(\"sessions\",
                    defaults.entryTtl(Duration.ofMinutes(30)));
                configs.put(\"stock\",
                    defaults.entryTtl(Duration.ofSeconds(30)));
                configs.put(\"search\",
                    defaults.entryTtl(Duration.ofMinutes(5)));

                return RedisCacheManager.builder(cf)
                    .cacheDefaults(defaults)
                    .withInitialCacheConfigurations(configs)
                    .build();
            }
        }
        """;

    static final String HYBRID_CONFIG = """
        // L1 (Caffeine) + L2 (Redis) — best performance
        @Configuration
        @EnableCaching
        public class HybridCacheConfig {

            @Primary
            @Bean
            public CacheManager caffeineCacheManager() {
                CaffeineCacheManager cm = new CaffeineCacheManager();
                cm.setCaffeine(Caffeine.newBuilder()
                    .maximumSize(10_000)
                    .expireAfterWrite(10, TimeUnit.MINUTES)
                    .recordStats());
                return cm;
            }

            @Bean
            public CacheManager redisCacheManager(
                    RedisConnectionFactory cf) {
                return RedisCacheManager.builder(cf)
                    .cacheDefaults(RedisCacheConfig.defaultCacheConfig()
                        .entryTtl(Duration.ofHours(1)))
                    .build();
            }
        }

        // Service — uses L1 first, then L2
        @Service
        public class ProductService {
            @Cacheable(cacheNames = \"products\",
                       cacheManager = \"caffeineCacheManager\")
            // Falls back to Redis (configured as secondary)
            public Product getProduct(Long id) { ... }
        }
        """;

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   REDIS + SPRING BOOT — Integration Guide");
        System.out.println("============================================");

        System.out.println("\n=== TWO APPROACHES ===");
        System.out.println("  1. @Cacheable (declarative, auto-managed)");
        System.out.println("     → Simple cache: get, set, evict");
        System.out.println("     → Same annotations as Caffeine");
        System.out.println("     → RedisCacheManager handles serialization");
        System.out.println("  2. RedisTemplate (programmatic, full control)");
        System.out.println("     → Advanced: List, Set, ZSet, Geo, pub/sub");
        System.out.println("     → Atomic counters, distributed locks");
        System.out.println("     → Pipeline, batch, transaction ops");

        System.out.println("\n=== 1. RedisTemplate Config ===");
        System.out.println(REDIS_TEMPLATE_CONFIG);

        System.out.println("\n=== 2. Complete Service Example ===");
        System.out.println("  (Cache-Aside + RedisTemplate for advanced ops)");
        System.out.println(REDIS_SERVICE_EXAMPLE);

        System.out.println("\n=== 3. Per-Cache TTL Config ===");
        System.out.println(PER_CACHE_TTL_CONFIG);

        System.out.println("\n=== 4. Hybrid: Caffeine (L1) + Redis (L2) ===");
        System.out.println(HYBRID_CONFIG);

        System.out.println("\n=== ARCHITECTURE: Cache-Aside with Redis ===");
        System.out.println("  ┌──────────┐    ┌──────────┐    ┌──────────┐");
        System.out.println("  │ Client   │───>│ Service  │───>│   Redis  │");
        System.out.println("  │ Request  │    │ get()    │    │ GET key  │");
        System.out.println("  └──────────┘    └────┬─────┘    └────┬─────┘");
        System.out.println("                      │               │     ");
        System.out.println("                      │    ┌──────────┘     ");
        System.out.println("                      │    │ HIT → return  ");
        System.out.println("                      │    │               ");
        System.out.println("                      │    v MISS          ");
        System.out.println("                      │  ┌──────────┐     ");
        System.out.println("                      │  │    DB    │     ");
        System.out.println("                      │  │ SELECT * │     ");
        System.out.println("                      │  └────┬─────┘     ");
        System.out.println("                      │       │           ");
        System.out.println("                      └───┬───┘           ");
        System.out.println("                          │ SET key val EX TTL");
        System.out.println("                          v                ");
        System.out.println("                     ┌──────────┐         ");
        System.out.println("                     │  Return  │         ");
        System.out.println("                     │  Result  │         ");
        System.out.println("                     └──────────┘         ");

        System.out.println("\n=== DATA FLOW: @Cacheable with Redis ===");
        System.out.println("  @Cacheable(\"users\") getUser(1L)");
        System.out.println("  1. Key: users::1");
        System.out.println("  2. Redis: GET users::1");
        System.out.println("  3. If exists → deserialize JSON → return");
        System.out.println("  4. If missing → execute method → serialize →");
        System.out.println("     SETEX users::1 3600 (JSON)");
        System.out.println("  Result: Cache shared across ALL instances!");

        System.out.println("\n============================================");
        System.out.println("  REDIS IN PRODUCTION - Best Practices");
        System.out.println("============================================");
        System.out.println("  1. Always use connection pooling");
        System.out.println("  2. Override serialization to JSON (not JDK!)");
        System.out.println("  3. Set per-cache TTL (not one global TTL)");
        System.out.println("  4. Use Redis Sentinel/Cluster for HA");
        System.out.println("  5. Monitor: hit ratio, evictions, latency");
        System.out.println("  6. Consider L1 (Caffeine) + L2 (Redis) hybrid");
        System.out.println("  7. Handle Redis failure gracefully (fallback)");
        System.out.println("============================================");
    }
}
