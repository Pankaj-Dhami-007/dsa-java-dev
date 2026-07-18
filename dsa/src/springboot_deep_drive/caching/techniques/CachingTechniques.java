package springboot_deep_drive.caching.techniques;

/*
 * ============================================================================
 *  CACHING TECHNIQUES / PATTERNS — Complete Guide
 * ============================================================================
 *
 *  There are 6 major caching patterns. Each solves a different trade-off
 *  between consistency, performance, and complexity.
 *
 * ============================================================================
 *  1. CACHE-ASIDE (Lazy Loading) ★ MOST COMMON
 * ============================================================================
 *
 *    DIAGRAM:
 *    ┌────────┐  1. Get(key)    ┌──────────┐
 *    │        │ ──────────────> │  Cache   │
 *    │ Client │                 │ (Redis)  │
 *    │        │ <── HIT ─────── │          │
 *    │        │                 └──────────┘
 *    │        │  2. MISS
 *    │        │ ──────────────────┐
 *    │        │                   v
 *    │        │              ┌──────────┐
 *    │        │  3. Query    │   DB     │
 *    │        │ ──────────>  │          │
 *    │        │ <── result ──│          │
 *    │        │              └──────────┘
 *    │        │  4. Put(key, result) back to cache
 *    │        │ ──────────────────┐
 *    │        │                   v
 *    │        │              ┌──────────┐
 *    │        │ <────────────│  Cache   │ ← populated
 *    └────────┘              └──────────┘
 *
 *    HOW: Application code manages cache manually.
 *         1. Check cache → HIT? return
 *         2. MISS → query DB → populate cache → return
 *         3. On UPDATE: invalidate cache (delete), not update
 *
 *    PROS: Simple, flexible, cache only holds what's requested
 *    CONS: 3 network hops on miss, stale data possible (race condition)
 *
 *    INTERVIEW Q: What's the stale data problem in Cache-Aside?
 *    A: Thread A reads (miss) → Thread B writes DB + deletes cache
 *       → Thread A writes stale data into cache. Solution: use
 *       Write-Through or lease/locking mechanism.
 *
 * ============================================================================
 *  2. READ-THROUGH CACHE
 * ============================================================================
 *
 *    DIAGRAM:
 *    ┌────────┐  Get(key)    ┌──────────┐  Load(key)  ┌──────────┐
 *    │ Client │ ──────────> │  Cache   │ ──────────> │   DB     │
 *    │        │ <────────── │ (e.g.    │ <────────── │          │
 *    │        │   result    │  Redis)  │   result    └──────────┘
 *    └────────┘             └──────────┘
 *                              (always consistent)
 *
 *    HOW: Cache library (e.g., Caffeine, EHCache) auto-loads from DB
 *         on miss. Application only talks to cache.
 *
 *    PROS: Cleaner code (no manual load), cache controls consistency
 *    CONS: Cache provider must know how to load data (coupling)
 *
 *    INTERVIEW Q: Read-Through vs Cache-Aside?
 *    A: Cache-Aside: App manages both cache + DB calls.
 *       Read-Through: App talks only to cache; cache handles DB.
 *       Read-Through is more abstracted.
 *
 * ============================================================================
 *  3. WRITE-THROUGH CACHE
 * ============================================================================
 *
 *    DIAGRAM:
 *    ┌────────┐ Write(key,val) ┌──────────┐  Write to DB  ┌──────────┐
 *    │ Client │ ─────────────> │  Cache   │ ────────────> │   DB     │
 *    │        │ <── OK ─────── │          │    (sync)     │          │
 *    └────────┘                └──────────┘               └──────────┘
 *
 *    HOW: Every write goes to cache FIRST. Cache immediately writes
 *         to DB synchronously. Write returns only after DB confirms.
 *
 *    PROS: Strong consistency (cache always matches DB)
 *    CONS: Higher write latency (waiting for DB), no write speed benefit
 *
 *    USE WHEN: You need read speed but writes are less frequent
 *              (e.g., user profile updates)
 *
 * ============================================================================
 *  4. WRITE-BEHIND (Write-Back) CACHE
 * ============================================================================
 *
 *    DIAGRAM:
 *    ┌────────┐ Write(key,val) ┌──────────┐    Batch    ┌──────────┐
 *    │ Client │ ─────────────> │  Cache   │ ──async───> │   DB     │
 *    │        │ <── OK (fast)─ │          │  (later)    │          │
 *    └────────┘                └──────────┘             └──────────┘
 *
 *    HOW: Write to cache → return immediately. Cache batches writes
 *         and flushes to DB asynchronously (every N seconds).
 *
 *    PROS: Very fast writes (no DB wait), reduces DB write load
 *    CONS: Data loss risk if cache crashes before flush to DB
 *
 *    USE WHEN: Write-heavy apps (logging, analytics, counters)
 *              where some data loss is acceptable
 *
 *    INTERVIEW Q: Write-Behind vs Write-Through?
 *    A: Through = sync (safe but slower). Behind = async (fast but risky).
 *       Through ensures no loss; Behind batches writes for throughput.
 *
 * ============================================================================
 *  5. WRITE-AROUND CACHE
 * ============================================================================
 *
 *    DIAGRAM:
 *    ┌────────┐ Write ──────────────> ┌──────────┐
 *    │ Client │                       │   DB     │
 *    │        │ <── OK ────────────── │          │
 *    │        │                       └──────────┘
 *    │        │
 *    │        │ Read ────> ┌──────────┐  (cache populated
 *    │        │ <──────── │  Cache   │   only on read miss)
 *    └────────┘           └──────────┘
 *
 *    HOW: Write directly to DB (bypass cache). Cache is only populated
 *         on read miss.
 *
 *    PROS: Cache doesn't get polluted with data that's written once
 *          and never read again.
 *    CONS: Read misses are more frequent (cache not pre-populated)
 *
 * ============================================================================
 *  6. REFRESH-AHEAD CACHE
 * ============================================================================
 *
 *    HOW: Cache proactively refreshes expiring entries BEFORE they expire.
 *         Predicts what will be accessed and reloads it.
 *
 *    PROS: Minimizes cache misses, always fresh data
 *    CONS: Complex to implement, wastes resources on unused refreshes
 *
 *    USE WHEN: Predictable access patterns (e.g., trending articles)
 *
 * ============================================================================
 *  COMPARISON TABLE (INTERVIEW)
 * ============================================================================
 *  ┌─────────────────┬──────────┬──────────┬──────────┬──────────────┐
 *  │ Technique       │ Read Spd │ Wr Spd   │ Consist  │ Complexity   │
 *  ├─────────────────┼──────────┼──────────┼──────────┼──────────────┤
 *  │ Cache-Aside     │ High     │ Medium   │ Eventual │ Low          │
 *  │ Read-Through    │ High     │ Medium   │ Eventual │ Medium       │
 *  │ Write-Through   │ High     │ Low      │ Strong   │ Medium       │
 *  │ Write-Behind    │ High     │ Very Hi  │ Eventual │ High         │
 *  │ Write-Around    │ Medium   │ High     │ Strong   │ Low          │
 *  │ Refresh-Ahead   │ Very Hi  │ Medium   │ Eventual │ Very High    │
 *  └─────────────────┴──────────┴──────────┴──────────┴──────────────┘
 *
 * ============================================================================
 *  INTERVIEW Q&A — Caching Techniques
 * ============================================================================
 *
 *  Q: Which caching pattern does Redis use by default?
 *  A: Cache-Aside — app code decides when to read/write cache.
 *
 *  Q: Which pattern does Caffeine (Java) use?
 *  A: Read-Through + Write-Through with async refresh-ahead.
 *
 *  Q: When would you use Write-Behind in a real system?
 *  A: Clickstream analytics. Millions of clicks/min. Write to Redis,
 *     batch flush to DB every 5 seconds. Losing a few clicks is OK.
 *
 *  Q: What is "thundering herd" in caching?
 *  A: Multiple requests simultaneously miss cache for same key → all hit DB.
 *     Solved with mutex locking (only 1 thread loads, others wait).
 *
 *  Q: How do you handle cache invalidation?
 *  A: 3 strategies:
 *     1. TTL expiry (automatic)
 *     2. Explicit delete on update (Cache-Aside)
 *     3. Version-based (compare and swap)
 *
 * ============================================================================
 *  REAL-WORLD EXAMPLES OF EACH PATTERN
 * ============================================================================
 *  - Cache-Aside:     Most apps using Redis with manual get/set
 *  - Read-Through:    Hibernate L2 cache (reads go through cache)
 *  - Write-Through:   Banking systems (every write must be in DB)
 *  - Write-Behind:    YouTube view counter (async, occasional loss OK)
 *  - Write-Around:    Blog CMS (write article to DB, only cache on read)
 *  - Refresh-Ahead:   Netflix trending recommendations
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Responsibility: Demonstrate ALL caching techniques with runnable code.
 *
 * We use a simple simulated DB and show each pattern in action.
 */
public class CachingTechniques {

    /*
     * Simulated Database — stores "source of truth" data
     * Each read takes 200ms simulated delay
     */
    static class Database {
        final ConcurrentHashMap<String, String> store = new ConcurrentHashMap<>();

        String read(String key) {
            simulateDelay(200); // DB is slow
            String val = store.get(key);
            System.out.println("      [DB] Read: " + key + " -> " + val);
            return val;
        }

        void write(String key, String val) {
            simulateDelay(150); // DB write is also slow
            store.put(key, val);
            System.out.println("      [DB] Written: " + key + " = " + val);
        }

        private void simulateDelay(long ms) {
            try { Thread.sleep(ms); } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /*
     * Simple in-memory cache store
     */
    static class CacheStore {
        final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        final AtomicInteger hits = new AtomicInteger(0);
        final AtomicInteger misses = new AtomicInteger(0);

        String get(String key) {
            String val = map.get(key);
            if (val != null) hits.incrementAndGet();
            else misses.incrementAndGet();
            return val;
        }

        void put(String key, String val) {
            map.put(key, val);
        }

        void delete(String key) {
            map.remove(key);
        }

        void clear() {
            map.clear();
            hits.set(0);
            misses.set(0);
        }

        double hitRatio() {
            int total = hits.get() + misses.get();
            return total == 0 ? 0 : (hits.get() * 100.0 / total);
        }
    }

    // ================================================================
    //  TEST HARNESS
    // ================================================================
    public static void main(String[] args) {
        Database db = new Database();
        CacheStore cache = new CacheStore();

        // Pre-populate DB
        db.store.put("user:1", "Alice");
        db.store.put("user:2", "Bob");
        db.store.put("user:3", "Charlie");

        System.out.println("============================================");
        System.out.println("   CACHING TECHNIQUES DEMO");
        System.out.println("============================================");

        // ================================================================
        // 1. CACHE-ASIDE DEMO
        // ================================================================
        System.out.println("\n─────── 1. CACHE-ASIDE (Lazy Loading) ───────");
        // "Application manages both cache and DB"
        cacheAside_Read(cache, db, "user:1");
        cacheAside_Read(cache, db, "user:1"); // should be HIT now
        cacheAside_Read(cache, db, "user:999"); // non-existent key

        cache.clear();
        db.store.clear();
        db.store.put("product:1", "Laptop");

        // ================================================================
        // 2. READ-THROUGH DEMO
        // ================================================================
        System.out.println("\n─────── 2. READ-THROUGH (Cache loads DB) ────");
        /*
         * In Read-Through, the cache itself knows how to load from DB.
         * We simulate this by passing a loader function.
         */
        readThrough_Read(cache, "p:1", key ->
            db.read(key) != null ? db.read(key) : null
        );
        readThrough_Read(cache, "p:1", key ->
            db.read(key) != null ? db.read(key) : null
        ); // HIT

        cache.clear();
        db.store.clear();
        db.store.put("count:1", "100");

        // ================================================================
        // 3. WRITE-THROUGH DEMO
        // ================================================================
        System.out.println("\n─────── 3. WRITE-THROUGH (Sync write) ───────");
        /*
         * Write to cache + DB together. Both must succeed.
         * If DB fails, cache write is rolled back (simulated).
         */
        writeThrough(cache, db, "count:1", "200");
        System.out.println("   Cache = " + cache.get("count:1")
            + ", DB = " + db.store.get("count:1"));

        cache.clear();
        db.store.clear();
        db.store.put("analytics:visits", "0");

        // ================================================================
        // 4. WRITE-BEHIND DEMO
        // ================================================================
        System.out.println("\n─────── 4. WRITE-BEHIND (Async batch) ───────");
        /*
         * Write to cache immediately. DB is updated later in batch.
         * Simulate: write 5 updates, then flush to DB.
         */
        WriteBehindBuffer buffer = new WriteBehindBuffer(db);
        buffer.write("analytics:visits", "1");
        buffer.write("analytics:visits", "2");
        buffer.write("analytics:visits", "3");
        // Cache has latest, DB still has old value
        System.out.println("   Cache (latest): " + cache.get("analytics:visits"));
        System.out.println("   DB (stale before flush): " + db.store.get("analytics:visits"));
        buffer.flush();
        System.out.println("   DB (after flush): " + db.store.get("analytics:visits"));

        // ================================================================
        // 5. WRITE-AROUND DEMO
        // ================================================================
        System.out.println("\n─────── 5. WRITE-AROUND (Skip cache) ─────────");
        /*
         * Write directly to DB. Cache only populated on read miss.
         */
        writeAround(cache, db, "report:q1", "Q1 Data: $1M revenue");
        System.out.println("   Cache after write-around: " + cache.get("report:q1"));
        // Now read — it will miss and populate
        cacheAside_Read(cache, db, "report:q1");
        System.out.println("   Cache after read-miss: " + cache.get("report:q1"));

        // ================================================================
        // SUMMARY
        // ================================================================
        System.out.println("\n============================================");
        System.out.println("  PATTERN QUICK REFERENCE");
        System.out.println("============================================");
        System.out.println("  Cache-Aside:   App manages cache + DB manually");
        System.out.println("  Read-Through:  Cache loads DB on miss");
        System.out.println("  Write-Through: Cache + DB updated together (sync)");
        System.out.println("  Write-Behind:  Cache updated, DB async batch");
        System.out.println("  Write-Around:  Write to DB only, cache on read");
        System.out.println("  Refresh-Ahead: Pre-load before expiry");
        System.out.println("\n  Hit ratio: Typically > 90% is good.");
        System.out.println("  Best for reads: Read-Through + Refresh-Ahead");
        System.out.println("  Best for writes: Write-Behind (fastest)");
    }

    // ================================================================
    // CACHE-ASIDE IMPLEMENTATION
    // ================================================================
    static String cacheAside_Read(CacheStore cache, Database db, String key) {
        // 1. Check cache
        String val = cache.get(key);
        if (val != null) {
            System.out.println("   [CACHE-ASIDE] HIT: " + key + " = " + val);
            return val;
        }
        // 2. Cache MISS → load from DB
        System.out.println("   [CACHE-ASIDE] MISS: " + key + " → loading from DB");
        val = db.read(key);
        if (val != null) {
            // 3. Populate cache
            cache.put(key, val);
        }
        // 4. On update: call cacheAside_Update(key) which does
        //    db.write(key, newVal); cache.delete(key);
        System.out.println("   [CACHE-ASIDE] Result: " + val);
        return val;
    }

    // ================================================================
    // READ-THROUGH IMPLEMENTATION
    // ================================================================
    @FunctionalInterface
    interface DbLoader {
        String load(String key);
    }

    static String readThrough_Read(CacheStore cache, String key, DbLoader loader) {
        String val = cache.get(key);
        if (val != null) {
            System.out.println("   [READ-THROUGH] HIT: " + key + " = " + val);
            return val;
        }
        System.out.println("   [READ-THROUGH] MISS → cache calls loader");
        val = loader.load(key);
        if (val != null) {
            cache.put(key, val);
        }
        System.out.println("   [READ-THROUGH] Result: " + val);
        return val;
    }

    // ================================================================
    // WRITE-THROUGH IMPLEMENTATION
    // ================================================================
    static void writeThrough(CacheStore cache, Database db, String key, String val) {
        System.out.println("   [WRITE-THROUGH] Writing " + key + " = " + val);
        // Write to cache first
        cache.put(key, val);
        // Then synchronously write to DB
        db.write(key, val);
        // If DB fails → rollback cache (in real code, use transaction)
        System.out.println("   [WRITE-THROUGH] Done (both cache + DB updated)");
    }

    // ================================================================
    // WRITE-BEHIND IMPLEMENTATION
    // ================================================================
    static class WriteBehindBuffer {
        private final Database db;
        private final ConcurrentHashMap<String, String> buffer = new ConcurrentHashMap<>();

        WriteBehindBuffer(Database db) {
            this.db = db;
        }

        void write(String key, String val) {
            System.out.println("   [WRITE-BEHIND] Buffered: " + key + " = " + val);
            buffer.put(key, val); // just cache for now
        }

        void flush() {
            System.out.println("   [WRITE-BEHIND] Flushing " + buffer.size()
                + " entries to DB...");
            buffer.forEach((k, v) -> {
                System.out.println("      Flushing " + k + " -> " + v);
                db.write(k, v);
            });
            buffer.clear();
            System.out.println("   [WRITE-BEHIND] Flush complete");
        }
    }

    // ================================================================
    // WRITE-AROUND IMPLEMENTATION
    // ================================================================
    static void writeAround(CacheStore cache, Database db, String key, String val) {
        System.out.println("   [WRITE-AROUND] Writing " + key + " directly to DB");
        db.write(key, val);
        // Do NOT write to cache. Optionally invalidate existing cache.
        cache.delete(key);
        System.out.println("   [WRITE-AROUND] Cache entry deleted (if existed)");
    }
}
