package springboot_deep_drive.caching.springboot.redis;

/*
 * ============================================================================
 *  REDIS — INTRODUCTION & SETUP WITH SPRING BOOT
 * ============================================================================
 *
 *  SIMPLE DEFINITION:
 *  Redis (REmote DIctionary Server) is an in-memory data structure store
 *  used as a database, cache, message broker, and queue. It's the MOST
 *  popular choice for distributed caching in production systems.
 *
 *  REAL-LIFE ANALOGY:
 *  Redis is like a super-fast personal assistant who remembers EVERYTHING
 *  you tell them instantly. They can store strings, lists, sets, maps,
 *  and even geo-locations. Multiple people (app instances) can ask the
 *  same assistant — everyone gets the SAME answer. If the assistant
 *  forgets (restart), they have a notepad (disk persistence) to remember.
 *
 * ============================================================================
 *  WHY REDIS? (vs other caching solutions)
 * ============================================================================
 *
 *  ┌──────────────┬──────────┬──────────┬──────────┬──────────┐
 *  │ Feature      │ Redis    │ Caffeine │ Memcached│ DB       │
 *  ├──────────────┼──────────┼──────────┼──────────┼──────────┤
 *  │ Speed        │ ~1ms     │ ~0.01ms  │ ~1ms     │ ~50ms    │
 *  │ Data structs │ 10+      │ KV only  │ KV only  │ Tables   │
 *  │ Persistence  │ ✅ RDB   │ ❌       │ ❌       │ ✅       │
 *  │             │   /AOF   │          │          │          │
 *  │ Replication  │ ✅       │ ❌       │ ✅       │ ✅       │
 *  │ Pub/Sub      │ ✅       │ ❌       │ ❌       │ ❌       │
 *  │ Transactions │ ✅       │ ❌       │ ❌       │ ✅       │
 *  │ Lua scripts  │ ✅       │ ❌       │ ❌       │ ❌       │
 *  │ Geo queries  │ ✅       │ ❌       │ ❌       │ ❌       │
 *  └──────────────┴──────────┴──────────┴──────────┴──────────┘
 *
 *  REDIS IS THE BEST CHOICE WHEN:
 *  1. Multiple app instances need SHARED cache
 *  2. You need rich data structures (not just key-value)
 *  3. You need persistence (cache survives restart)
 *  4. You need high availability (replication + sentinel)
 *  5. You need sub-millisecond latency at scale
 *
 * ============================================================================
 *  REDIS KEY FEATURES (Interview Answers)
 * ============================================================================
 *
 *  1. IN-MEMORY STORAGE: All data in RAM → extremely fast reads/writes
 *     (100K+ ops/sec per instance)
 *
 *  2. RICH DATA STRUCTURES:
 *     ┌────────────┬────────────────────────────────────────────┐
 *     │ Type       │ Example Use                                │
 *     ├────────────┼────────────────────────────────────────────┤
 *     │ String     │ Simple cache value, counters (INCR)        │
 *     │ List       │ Queue, feed, timeline                      │
 *     │ Set        │ Unique items, tags, followers              │
 *     │ Sorted Set │ Leaderboard, trending, range queries       │
 *     │ Hash       │ Object fields (user profile)               │
 *     │ Geo        │ Nearby locations, Uber-style queries       │
 *     │ Bitmap     │ Bloom filter, analytics                    │
 *     │ Stream     │ Event sourcing, message queue              │
 *     └────────────┴────────────────────────────────────────────┘
 *
 *  3. PERSISTENCE (Two modes):
 *     - RDB (Snapshot): Point-in-time dump every N minutes
 *     - AOF (Append-Only File): Every write operation logged
 *     - Can use BOTH for best safety
 *
 *  4. REPLICATION & HA:
 *     - Master-Replica: Async replication (eventual consistency)
 *     - Redis Sentinel: Auto-failover (monitor + promote)
 *     - Redis Cluster: Auto-sharding across nodes (16,384 slots)
 *
 *  5. ATOMIC OPERATIONS:
 *     - INCR, DECR, SETNX — atomic, no race conditions
 *     - Lua scripting — server-side transactions
 *     - Transactions (MULTI/EXEC) — optimistic locking (WATCH)
 *
 * ============================================================================
 *  REDIS VS CAFFEINE — WHEN TO USE WHAT (CRITICAL INTERVIEW)
 * ============================================================================
 *
 *  USE CAFFEINE (Local) WHEN:
 *    - Data is local to ONE app instance (config, reference data)
 *    - You need ABSOLUTE lowest latency (~μs)
 *    - Data can be regenerated on restart
 *    - Hit ratio is predictable (small, hot datasets)
 *
 *  USE REDIS (Distributed) WHEN:
 *    - Multiple app instances need SAME cache
 *    - Cache must SURVIVE app restarts
 *    - You need advanced data structures (Geo, Sorted Set)
 *    - You need pub/sub, rate limiting, distributed locks
 *    - Dataset is LARGE (Redis supports up to 512MB per key)
 *
 *  BEST: L1 (Caffeine) + L2 (Redis) Hybrid:
 *    @Cacheable(cacheNames = {"caffeine", "redis"})
 *    Checks local first (fast) → Redis second → DB last
 *
 * ============================================================================
 *  SPRING BOOT + REDIS SETUP (Dependencies & Config)
 * ============================================================================
 *
 *  === GRADLE ===
 *  implementation 'org.springframework.boot:spring-boot-starter-data-redis'
 *  implementation 'org.apache.commons:commons-pool2'      // Connection pool
 *  implementation 'redis.clients:jedis'                     // or Lettuce (default)
 *
 *  === MAVEN ===
 *  <dependency>
 *      <groupId>org.springframework.boot</groupId>
 *      <artifactId>spring-boot-starter-data-redis</artifactId>
 *  </dependency>
 *  <dependency>
 *      <groupId>org.apache.commons</groupId>
 *      <artifactId>commons-pool2</artifactId>
 *  </dependency>
 *
 *  === APPLICATION.YML (Full Configuration) ===
 *  spring:
 *    cache:
 *      type: redis                          # Use Redis as cache provider
 *      redis:
 *        time-to-live: 3600000              # Default TTL: 1 hour (ms)
 *        cache-null-values: false           # Don't cache nulls
 *        use-key-prefix: true               # Prefix keys (default: cacheName::)
 *        key-prefix: "myapp:"               # Custom prefix
 *
 *    redis:
 *      host: localhost                      # Redis server host
 *      port: 6379                           # Redis server port
 *      password:                            # Optional password
 *      database: 0                          # DB index (0-15)
 *      timeout: 2000ms                      # Connection timeout
 *      connect-timeout: 2000ms              # Socket connect timeout
 *      lettuce:                             # Lettuce client config
 *        pool:
 *          max-active: 16                   # Max connections
 *          max-idle: 8                      # Max idle connections
 *          min-idle: 4                      # Min idle connections
 *          max-wait: -1ms                   # Max wait for connection (-1 = forever)
 *        shutdown-timeout: 100ms            # Shutdown timeout
 *      client-type: lettuce                 # lettuce or jedis (lettuce default)
 *
 *  === REDIS STANDALONE vs CLUSTER vs SENTINEL ===
 *
 *  # STANDALONE (Single instance — dev/test)
 *  spring.redis.host=localhost
 *  spring.redis.port=6379
 *
 *  # SENTINEL (High Availability — auto-failover)
 *  spring.redis.sentinel.master=mymaster
 *  spring.redis.sentinel.nodes=host1:26379,host2:26379,host3:26379
 *
 *  # CLUSTER (Horizontal scaling — sharding)
 *  spring.redis.cluster.nodes=host1:6379,host2:6379,host3:6379
 *  spring.redis.cluster.max-redirects=3
 *
 * ============================================================================
 *  INSTALLING REDIS (Quick Reference)
 * ============================================================================
 *
 *  # DOCKER (Easiest for dev — single command):
 *  docker run -d --name redis-stack -p 6379:6379 -p 8001:8001 redis/redis-stack
 *    # Port 6379 = Redis, 8001 = RedisInsight (GUI)
 *
 *  # MAC (Homebrew):
 *  brew install redis && brew services start redis
 *
 *  # LINUX (Ubuntu/Debian):
 *  sudo apt install redis-server
 *  sudo systemctl enable redis && sudo systemctl start redis
 *
 *  # WINDOWS (WSL2 recommended):
 *  wsl --install -d Ubuntu
 *  sudo apt install redis-server
 *
 *  # VERIFY:
 *  redis-cli ping
 *  # Should return: PONG
 *
 * ============================================================================
 *  ARCHITECTURE DIAGRAM — Spring Boot + Redis
 * ============================================================================
 *
 *  ┌─────────────────────────────────────────────────────────────────────┐
 *  │  APPLICATION (Spring Boot)                                         │
 *  │  ┌──────────────────────────────────────────────────────────────┐  │
 *  │  │  @Service Layer                                              │  │
 *  │  │  ┌─────────────┐  @Cacheable  ┌──────────────────┐          │  │
 *  │  │  │ UserService │ ────────────>│ CacheInterceptor │          │  │
 *  │  │  │ ProductSvc  │              │ (AOP Proxy)      │          │  │
 *  │  │  └─────────────┘              └────────┬─────────┘          │  │
 *  │  │                                        │                     │  │
 *  │  │                                        v                     │  │
 *  │  │  ┌─────────────────────────────────────────────┐            │  │
 *  │  │  │ RedisCacheManager                          │            │  │
 *  │  │  │  - RedisConnectionFactory                  │            │  │
 *  │  │  │  - RedisCacheConfiguration (TTL, prefix)   │            │  │
 *  │  │  │  - RedisCacheWriter                        │            │  │
 *  │  │  └───────────────────┬─────────────────────────┘            │  │
 *  │  └──────────────────────┼──────────────────────────────────────┘  │
 *  └─────────────────────────┼─────────────────────────────────────────┘
 *                            │ TCP/IP (:6379)
 *                            v
 *  ┌─────────────────────────────────────────────────────────────────┐
 *  │  REDIS SERVER                                                   │
 *  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
 *  │  │ In-Memory    │  │ Persistence  │  │ Replication  │          │
 *  │  │ Data Store   │  │ (RDB / AOF)  │  │ (Master→Repl)│          │
 *  │  └──────────────┘  └──────────────┘  └──────────────┘          │
 *  └─────────────────────────────────────────────────────────────────┘
 *
 * ============================================================================
 *  INTERVIEW Q&A — Redis Basics & Setup
 * ============================================================================
 *
 *  Q: What is Redis and why is it called a "data structure server"?
 *  A: Redis is an in-memory data structure store. Unlike simple caches
 *     (Memcached) which only store key-value strings, Redis supports
 *     10+ data types (String, List, Set, Sorted Set, Hash, Geo, Streams)
 *     with built-in operations for each type. Hence "data structure server."
 *
 *  Q: Redis vs Memcached — which one for caching?
 *  A: Choose Redis for: rich data types, persistence, replication, pub/sub,
 *     Lua scripting, and advanced use cases (rate limiting, distributed locks).
 *     Choose Memcached for: pure key-value, multi-threaded simplicity.
 *     Redis is the industry standard for new projects.
 *
 *  Q: How does Redis persistence work?
 *  A: Two modes: RDB (snapshot) saves full dataset every N minutes/keys changed.
 *     AOF (Append-Only File) logs every write operation, replayable on restart.
 *     RDB is faster to load but may lose data. AOF is more durable but slower.
 *     Best practice: enable both (RDB for backup, AOF for durability).
 *
 *  Q: What is Redis Sentinel vs Redis Cluster?
 *  A: Sentinel provides HIGH AVAILABILITY (auto-failover, monitoring).
 *     Cluster provides HORIZONTAL SCALING (automatic sharding across nodes).
 *     Sentinel: 1 master + N replicas. Cluster: N masters + N replicas.
 *     For most caching use cases, Sentinel is sufficient.
 *
 *  Q: What is connection pooling and why use it?
 *  A: Creating a new TCP connection per request is expensive. Connection pool
 *     maintains a set of pre-established connections. Springs's Lettuce client
 *     supports async/non-blocking with connection pooling for high throughput.
 *     Recommended: max-active=16 (per app instance), max-idle=8.
 *
 *  Q: How do you secure Redis in production?
 *  A: 1. Set a STRONG password (requirepass)
 *     2. Enable TLS for in-transit encryption
 *     3. Bind to specific IPs (not 0.0.0.0)
 *     4. Rename dangerous commands (FLUSHALL, CONFIG)
 *     5. Run on dedicated server (not shared)
 *     6. Use Redis ACLs (Access Control Lists — Redis 6+)
 *
 *  Q: What are the common Redis deployment topologies?
 *  A: 1. Standalone: single instance (dev/test)
 *     2. Master-Replica: 1 master + N replicas (read scaling)
 *     3. Sentinel: auto-failover (HA — production standard)
 *     4. Cluster: auto-sharding + replication (large scale)
 *     5. Redis Enterprise / Redis Cloud: managed (easiest)
 */

public class RedisIntroSetup {

    static final String DEPENDENCIES = """
        <!-- Maven -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-pool2</artifactId>
        </dependency>

        // Gradle
        implementation 'org.springframework.boot:spring-boot-starter-data-redis'
        implementation 'org.apache.commons:commons-pool2'
        """;

    static final String APPLICATION_YML = """
        spring:
          cache:
            type: redis
            redis:
              time-to-live: 3600000       # 1 hour default TTL
              cache-null-values: false
              use-key-prefix: true
              key-prefix: "myapp:"

          redis:
            host: localhost
            port: 6379
            password:
            database: 0
            timeout: 2000ms
            lettuce:
              pool:
                max-active: 16
                max-idle: 8
                min-idle: 4
                max-wait: -1ms

        # For Redis Cluster:
        # spring.redis.cluster.nodes=node1:6379,node2:6379,node3:6379

        # For Redis Sentinel:
        # spring.redis.sentinel.master=mymaster
        # spring.redis.sentinel.nodes=s1:26379,s2:26379,s3:26379
        """;

    static final String DIAGRAM = """
           ┌────────────────────────────────────────────────────────┐
           │  Spring Boot App Instance 1     Instance 2  Instance 3 │
           │  ┌──────────────────────┐  ┌──────┐  ┌──────┐        │
           │  │ @Cacheable(\"users\")  │  │      │  │      │        │
           │  │ @Cacheable(\"prods\")  │  │ ...  │  │ ...  │        │
           │  └──────────┬───────────┘  └──┬───┘  └──┬───┘        │
           └─────────────┼─────────────────┼──────────┼────────────┘
                         │                 │          │
                         └────────┬────────┴──────────┘
                                  │ TCP (:6379)
                                  v
           ┌────────────────────────────────────────────────────────┐
           │  REDIS (SHARED across all instances)                   │
           │  ┌──────────────────────────────────────────────────┐  │
           │  │  users:1 → {id:1, name:\"Alice\"}                  │  │
           │  │  users:2 → {id:2, name:\"Bob\"}                    │  │
           │  │  products:100 → {id:100, sku:\"ABC\", price:99}    │  │
           │  │  stock:100 → {quantity:50}                        │  │
           │  │  trending → ZSET (video:1=1000, video:2=500)      │  │
           │  └──────────────────────────────────────────────────┘  │
           └────────────────────────────────────────────────────────┘

        BENEFIT: All 3 app instances see the SAME cache.
        If Instance 1 caches user:1 → Instance 2 reads it instantly!
        """;

    static final String REDIS_ARCHITECTURE = """
        ┌─────────────────────────────────────────────────────────────────┐
        │  REDIS DEPLOYMENT MODES                                        │
        ├─────────────────────────────────────────────────────────────────┤
        │                                                                │
        │  STANDALONE                    SENTINEL                        │
        │  ┌──────────┐                  ┌──────────────────────┐       │
        │  │  Redis   │                  │  Sentinel  Sentinel  │       │
        │  │  Master  │                  │  ┌──────────────┐   │       │
        │  └──────────┘                  │  │   Master     │   │       │
        │                                │  ├──────────────┤   │       │
        │  CLUSTER                       │  │   Replica    │   │       │
        │  ┌──────┐ ┌──────┐ ┌──────┐   │  ├──────────────┤   │       │
        │  │ M1/R1│ │ M2/R2│ │ M3/R3│   │  │   Replica    │   │       │
        │  └──────┘ └──────┘ └──────┘   │  └──────────────┘   │       │
        │  (sharding + replication)      │  (auto-failover)     │       │
        └───────────────────────────────────────────────────────────────┘
        """;

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   REDIS — Introduction & Spring Boot Setup");
        System.out.println("============================================");

        System.out.println("\n=== WHAT IS REDIS? ===");
        System.out.println("  In-memory data structure store");
        System.out.println("  10+ data types: String, List, Set, Sorted Set,");
        System.out.println("  Hash, Geo, Bitmap, HyperLogLog, Streams");
        System.out.println("  Speed: 100K+ ops/sec, ~1ms latency");
        System.out.println("  Persistence: RDB (snapshot) + AOF (write log)");

        System.out.println("\n=== DATA STRUCTURES OVERVIEW ===");
        System.out.println("  ┌────────────┬────────────────────┬─────────────────────┐");
        System.out.println("  │ Type       │ Redis Command      │ Use Case            │");
        System.out.println("  ├────────────┼────────────────────┼─────────────────────┤");
        System.out.println("  │ String     │ GET/SET/INCR       │ Cache, counter      │");
        System.out.println("  │ List       │ LPUSH/RPOP/BRPOP   │ Queue, feed         │");
        System.out.println("  │ Set        │ SADD/SMEMBERS      │ Tags, followers     │");
        System.out.println("  │ Sorted Set │ ZADD/ZRANGE        │ Leaderboard, trends │");
        System.out.println("  │ Hash       │ HSET/HGETALL       │ Object fields       │");
        System.out.println("  │ Geo        │ GEOADD/GEORADIUS   │ Nearby locations    │");
        System.out.println("  │ Stream     │ XADD/XREADGROUP    │ Event sourcing      │");
        System.out.println("  └────────────┴────────────────────┴─────────────────────┘");

        System.out.println("\n=== REDIS VS CAFFEINE (When to use what) ===");
        System.out.println("  CAFFEINE (Local):  ~10μs, same JVM, not shared");
        System.out.println("    → Config data, reference data, small hot datasets");
        System.out.println("  REDIS (Distributed): ~1ms, shared across instances");
        System.out.println("    → Sessions, product catalog, user data, counters");
        System.out.println("  BEST: L1 (Caffeine) + L2 (Redis) hybrid!");

        System.out.println("\n=== DEPENDENCIES ===");
        System.out.println(DEPENDENCIES);

        System.out.println("\n=== APPLICATION.YML ===");
        System.out.println(APPLICATION_YML);

        System.out.println("\n=== ARCHITECTURE (Shared Cache) ===");
        System.out.println(DIAGRAM);

        System.out.println("\n=== REDIS DEPLOYMENT MODES ===");
        System.out.println(REDIS_ARCHITECTURE);

        System.out.println("\n=== QUICK REFERENCE ===");
        System.out.println("  Install: docker run -d --name redis -p 6379:6379 redis");
        System.out.println("  Verify:  redis-cli ping → PONG");
        System.out.println("  GUI:     redis-stack (port 8001)");
        System.out.println("  Spring:  spring-boot-starter-data-redis + config above");

        System.out.println("\n============================================");
        System.out.println("  Next: RedisSpringIntegration — code + examples");
        System.out.println("============================================");
    }
}
