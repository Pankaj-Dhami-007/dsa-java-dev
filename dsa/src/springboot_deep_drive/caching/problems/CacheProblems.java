package springboot_deep_drive.caching.problems;

/*
 * ============================================================================
 *  THREE CRITICAL CACHING PROBLEMS (MUST-KNOW FOR INTERVIEWS)
 * ============================================================================
 *
 *  1. CACHE PENETRATION  — Data that doesn't exist anywhere
 *  2. CACHE AVALANCHE    — Mass simultaneous expiry
 *  3. CACHE BREAKDOWN    — Single hot key expiry
 *
 *  ┌─────────────────────────────────────────────────────────────────────┐
 *  │  PROBLEM            CAUSE                    IMPACT                │
 *  ├─────────────────────────────────────────────────────────────────────┤
 *  │  Penetration    Requesting non-existent data   DB overload         │
 *  │  Avalanche      Mass TTL expiry               DB overload         │
 *  │  Breakdown      Single hot key expires        DB overload (spike) │
 *  └─────────────────────────────────────────────────────────────────────┘
 */

/*
 * SIMPLE DEFINITION:
 * Cache Penetration = constantly requesting data that DOESN'T EXIST
 * in either cache OR database. Every request bypasses cache and hits DB.
 *
 * REAL-LIFE ANALOGY:
 * Someone keeps calling a restaurant to order a dish that's NOT on the
 * menu. Every time, the waiter checks the kitchen (DB) even though
 * they know it doesn't exist. Eventually the kitchen gets overwhelmed.
 *
 * ============================================================================
 *  PROBLEM 1: CACHE PENETRATION
 * ============================================================================
 *
 *  DIAGRAM:
 *    Client ──> GET "user:99999" ──> Cache (MISS)
 *       └──> GET "user:99999" ──> Cache (MISS)
 *          └──> GET "user:99999" ──> Cache (MISS)
 *             └──> ...all hit DB because data NEVER exists
 *
 *  WHY IT HAPPENS:
 *    - Attacker sends random IDs that don't exist
 *    - Legitimate queries for deleted/removed data
 *
 *  SOLUTIONS:
 *    1. CACHE NULL VALUES: Cache the result even if it's null
 *       put(key, null, ttl=short) → next requests hit cache, not DB
 *
 *    2. BLOOM FILTER: Probabilistic data structure that quickly tells
 *       if an item DEFINITELY does NOT exist (or might exist)
 *       ┌────────────┐   ┌──────────┐   ┌──────────┐
 *       │  Request   │→  │ Bloom    │→  │ Cache    │→  │ DB │
 *       │            │   │ Filter   │   │          │   │    │
 *       │            │   │ "no" →   │   │          │   │    │
 *       │            │   │ reject   │   │          │   │    │
 *       └────────────┘   └──────────┘   └──────────┘   └────┘
 *
 *    3. PARAMETER VALIDATION: Reject obviously invalid IDs early
 *       (e.g., user IDs must be positive integers, not -1 or "abc")
 *
 *  INTERVIEW Q: How does a Bloom Filter work?
 *  A: Bit array of size M + K hash functions. On "add": hash key K times,
 *     set those K bits to 1. On "check": hash key K times, if ANY bit is 0
 *     → definitely NOT present. If all 1 → probably present (small false
 *     positive rate). Can't delete items from basic Bloom Filter.
 */

/*
 * SIMPLE DEFINITION:
 * Cache Avalanche = a LARGE NUMBER of cache keys EXPIRE AT THE SAME
 * TIME, causing a massive wave of requests to hit the database.
 *
 * REAL-LIFE ANALOGY:
 * A stadium's floodlights all have the same bulb lifespan. After exactly
 * 1000 hours, ALL lights go out simultaneously. Total darkness.
 * If they had staggered lifespans, only a few would fail at a time.
 *
 * ============================================================================
 *  PROBLEM 2: CACHE AVALANCHE
 * ============================================================================
 *
 *  DIAGRAM:
 *    ┌────────────┐       12:00:00 PM
 *    │  Key: A    │ ──── EXPIRED ────┐
 *    │  TTL: 3600 │                  │
 *    │  Set: 11AM │                  │
 *    ├────────────┤                  ├──> ALL requests go to DB
 *    │  Key: B    │ ──── EXPIRED ────┘    → DB CRASH
 *    │  TTL: 3600 │
 *    │  Set: 11AM │
 *    ├────────────┤
 *    │  Key: C    │ ──── EXPIRED
 *    │  TTL: 3600 │
 *    │  Set: 11AM │
 *    └────────────┘
 *
 *  CAUSES:
 *    - All keys have the SAME TTL
 *    - Cache server restarts (all keys lost at once)
 *    - Bulk cache invalidation (deployments clear everything)
 *
 *  SOLUTIONS:
 *    1. RANDOMIZE TTL: Add jitter to TTL values
 *       TTL = baseTTL + random(-600, +600) seconds
 *
 *    2. CACHE WARMING: Pre-populate cache before traffic arrives
 *       (background job reloads cache before peak hours)
 *
 *    3. CIRCUIT BREAKER: If DB starts failing, stop requests
 *       and serve stale cache or degraded response
 *
 *    4. MULTI-LEVEL CACHE: L1 (local memory) + L2 (Redis)
 *       L1 may miss, but L2 still has data
 *
 *    5. LOCKING / MUTEX: Only ONE request loads from DB,
 *       others wait or use stale data
 *
 *  INTERVIEW Q: How do you prevent avalanche during cache restart?
 *  A: 1) Persistent cache (Redis RDB/AOF) — data survives restart
 *     2) Cache warming (lazy loading at startup)
 *     3) Gradual restart (rolling restart of cache cluster)
 *     4) Hybrid: L1 local cache survives app restart
 */

/*
 * SIMPLE DEFINITION:
 * Cache Breakdown (Hotkey Invalidation) = a SINGLE popular cache key
 * expires while THOUSANDS of requests are trying to access it.
 * Like a celebrity bottleneck — everyone wants the same data at once.
 *
 * REAL-LIFE ANALOGY:
 * A famous bakery's "best cake" sells out. MOMENTARILY unavailable.
 * Hundreds of customers rush to the kitchen asking when it's ready.
 * The kitchen gets overwhelmed by the sudden crowd.
 *
 * ============================================================================
 *  PROBLEM 3: CACHE BREAKDOWN (Hotkey Invalidation)
 * ============================================================================
 *
 *  DIAGRAM:
 *           GET "trending:post:1"
 *               │
 *     ┌─────────┼─────────┐
 *     │         │         │
 *     v         v         v
 *   Cache     Cache     Cache
 *   MISS      MISS      MISS
 *     │         │         │
 *     └─────┬──┘─────────┘
 *           v
 *         DATABASE (overloaded by 1000 concurrent requests for 1 key)
 *
 *  WHY IT'S DIFFERENT FROM AVALANCHE:
 *    - Avalanche: MANY keys expire at once (broad)
 *    - Breakdown: ONE key expires, but it's a SUPER HOT key (deep)
 *
 *  SOLUTIONS:
 *    1. MUTEX LOCK: Only 1 thread loads from DB, others wait
 *       ┌────────┐  tryLock()  ┌──────────┐
 *       │Thread A│ ──────────> │LOAD FROM DB│ ← only A does this
 *       │Thread B│ ──────────> │  WAIT     │ ← B, C, D wait
 *       │Thread C│ ──────────> │  WAIT     │
 *       └────────┘             └──────────┘
 *
 *    2. PERPETUAL TTL (Logical Expiry): Never actually expire the key.
 *       Instead, a background thread refreshes it before expiry.
 *       Users always see cached data (slightly stale is OK).
 *
 *    3. LOCAL + REMOTE CACHE: L1 cache (local) survives even if
 *       Redis key expires. L1 TTL > L2 TTL.
 *
 *  INTERVIEW Q: How would you implement mutex-based breakdown prevention?
 *  A: Use Redis SETNX (SET if Not eXists) as a distributed lock:
 *      1. Check cache → MISS
 *      2. Try SETNX lock_key with TTL=5s
 *      3. If SUCCESS → load from DB → populate cache → release lock
 *      4. If FAIL → sleep 50ms → retry cache (or use stale data)
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CacheProblems {

    private static final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, String> database = new ConcurrentHashMap<>();
    private static final AtomicInteger dbHitCount = new AtomicInteger(0);

    static {
        database.put("user:valid", "Real User Data");
    }

    // ================================================================
    //  DEMO 1: Cache Penetration — requesting non-existent keys
    // ================================================================
    static class PenetrationDemo {

        /*
         * BEFORE FIX: Every request for non-existent key hits DB
         */
        static String badRead(String key) {
            String val = cache.get(key);
            if (val != null) return val;

            // Cache MISS — always hits DB
            val = database.get(key);
            dbHitCount.incrementAndGet();
            if (val != null) {
                cache.put(key, val);
            }
            return val; // null for non-existent → but DB was hit!
        }

        /*
         * AFTER FIX: Cache null values with short TTL
         */
        static String goodRead(String key) {
            String val = cache.get(key);
            if (val != null) {
                // We cached "NULL_SENTINEL" to represent "doesn't exist"
                if ("NULL_SENTINEL".equals(val)) {
                    System.out.println("   [Penetration Fix] Cache says: doesn't exist (no DB hit)");
                    return null;
                }
                return val;
            }

            val = database.get(key);
            dbHitCount.incrementAndGet();
            if (val != null) {
                cache.put(key, val);
            } else {
                // Cache the NULL with short TTL (not shown here, but concept)
                cache.put(key, "NULL_SENTINEL");
                System.out.println("   [Penetration Fix] Cached null for " + key);
            }
            return val;
        }

        static void demo() {
            System.out.println("\n─────── CACHE PENETRATION DEMO ───────");

            System.out.println("\n  BEFORE FIX (no null caching):");
            dbHitCount.set(0);
            for (int i = 0; i < 5; i++) {
                badRead("user:non-existent");
            }
            System.out.println("   DB hits: " + dbHitCount.get() + " (should be 5 — ALL missed)");

            System.out.println("\n  AFTER FIX (caching null values):");
            cache.clear();
            dbHitCount.set(0);
            for (int i = 0; i < 5; i++) {
                goodRead("user:non-existent");
            }
            System.out.println("   DB hits: " + dbHitCount.get()
                + " (should be 1 — first miss, then cached null)");
        }
    }

    // ================================================================
    //  DEMO 2: Cache Avalanche — mass expiry
    // ================================================================
    static class AvalancheDemo {

        /*
         * Simulate keys expiring at same time.
         * Solution: stagger TTL with jitter.
         */
        static void demo() {
            System.out.println("\n─────── CACHE AVALANCHE DEMO ──────────");

            int baseTtl = 3600; // 1 hour
            System.out.println("  Base TTL: " + baseTtl + "s");

            System.out.println("\n  WITHOUT jitter (all expire together):");
            for (int i = 1; i <= 5; i++) {
                int ttl = baseTtl;
                System.out.println("   Key:" + i + " TTL=" + ttl + "s"
                    + " → expires at same time!");
            }

            System.out.println("\n  WITH jitter (randomized ±20%):");
            for (int i = 1; i <= 5; i++) {
                int jitter = (int)(baseTtl * 0.2 * (Math.random() * 2 - 1));
                int ttl = baseTtl + jitter;
                System.out.println("   Key:" + i + " TTL=" + ttl + "s"
                    + " (jitter=" + jitter + "s) → staggered expiry");
            }

            System.out.println("\n  Other avalanche preventions:");
            System.out.println("   1. Cache warming before peak traffic");
            System.out.println("   2. Circuit breaker (serve stale on DB failure)");
            System.out.println("   3. Multi-level cache (L1 + Redis)");
            System.out.println("   4. Redis persistence (RDB/AOF) — survive restart");
        }
    }

    // ================================================================
    //  DEMO 3: Cache Breakdown — hot key expiry
    // ================================================================
    static class BreakdownDemo {

        private static String hotKeyValue = "Trending Post #1";
        private static final Object lock = new Object();

        /*
         * WITHOUT mutex: All threads hit DB simultaneously
         */
        static String badHotKeyRead(String key) {
            String val = cache.get(key);
            if (val != null) return val;

            // Multiple threads ALL reach here on cache miss
            System.out.println("   [Breakdown] THREAD " + Thread.currentThread().getId()
                + " hitting DB for hot key!");
            simulateDbDelay();
            val = database.get(key);
            cache.put(key, val);
            return val;
        }

        /*
         * WITH mutex: Only 1 thread loads from DB, others wait
         */
        static String goodHotKeyRead(String key) {
            String val = cache.get(key);
            if (val != null) return val;

            synchronized (lock) {
                // Double-check: another thread might have loaded it
                val = cache.get(key);
                if (val != null) return val;

                System.out.println("   [Breakdown Fix] Thread " + Thread.currentThread().getId()
                    + " WINS the lock — loading from DB");
                simulateDbDelay();
                val = database.get(key);
                cache.put(key, val);
            }
            return val;
        }

        static void simulateDbDelay() {
            try { Thread.sleep(50); } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        static void demo() {
            System.out.println("\n─────── CACHE BREAKDOWN DEMO ──────────");

            database.put("trending:1", hotKeyValue);

            System.out.println("\n  WITHOUT mutex (all threads hit DB):");
            cache.clear();
            // Simulate 3 concurrent cache misses
            Thread t1 = new Thread(() -> badHotKeyRead("trending:1"));
            Thread t2 = new Thread(() -> badHotKeyRead("trending:1"));
            Thread t3 = new Thread(() -> badHotKeyRead("trending:1"));
            t1.start(); t2.start(); t3.start();
            try { t1.join(); t2.join(); t3.join(); } catch (InterruptedException e) {}

            System.out.println("\n  WITH mutex (only 1 hits DB):");
            cache.clear();
            Thread t4 = new Thread(() -> goodHotKeyRead("trending:1"));
            Thread t5 = new Thread(() -> goodHotKeyRead("trending:1"));
            Thread t6 = new Thread(() -> goodHotKeyRead("trending:1"));
            t4.start(); t5.start(); t6.start();
            try { t4.join(); t5.join(); t6.join(); } catch (InterruptedException e) {}

            System.out.println("\n  Other breakdown preventions:");
            System.out.println("   1. Perpetual TTL (background refresh)");
            System.out.println("   2. Local cache + Redis (L1 survives)");
            System.out.println("   3. Distributed lock (Redis SETNX)");
            System.out.println("   4. Serve stale data while refreshing");
        }
    }

    // ================================================================
    //  MAIN
    // ================================================================
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   CACHE PENETRATION, AVALANCHE & BREAKDOWN");
        System.out.println("============================================");

        PenetrationDemo.demo();
        AvalancheDemo.demo();
        BreakdownDemo.demo();

        System.out.println("\n============================================");
        System.out.println("  KEY TAKEAWAYS:");
        System.out.println("============================================");
        System.out.println("  PENETRATION: Non-existent data → cache null + Bloom Filter");
        System.out.println("  AVALANCHE:   Mass expiry → randomize TTL + cache warming");
        System.out.println("  BREAKDOWN:   Hot key expiry → mutex lock + perpetual TTL");
        System.out.println("============================================");
    }
}
