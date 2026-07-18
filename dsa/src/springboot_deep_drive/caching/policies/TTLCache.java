package springboot_deep_drive.caching.policies;

/*
 * SIMPLE DEFINITION:
 * TTL Cache automatically DELETES entries after a fixed time period.
 * "TTL" = Time-To-Live. Each entry has an expiry timestamp. Once
 * expired, it's treated as a miss and removed.
 *
 * REAL-LIFE ANALOGY:
 * Milk has an expiry date. After that date, you throw it away.
 * You don't use it even if it's still in the fridge. That's TTL.
 *
 * ============================================================================
 *  TTL CACHE (Time-To-Live) — Expiry-Based Cache
 * ============================================================================
 *
 *  WHAT IS TTL?
 *  ------------
 *  TTL (Time-To-Live) is a duration after which a cache entry is
 *  considered STALE and is removed/refreshed.
 *
 *  WHY TTL?
 *  - Prevents serving STALE data forever
 *  - Automatically refreshes cache without manual invalidation
 *  - Solves "memory leak" problem (old unused data stays forever)
 *
 * ============================================================================
 *  HOW TTL WORKS
 * ============================================================================
 *
 *  DIAGRAM:
 *  ┌─────────────────────────────────────────────────────────────┐
 *  │  put("user:1", "Alice", TTL=60s)                           │
 *  │                                                             │
 *  │  Now:     10:00:00                                          │
 *  │  Expires: 10:01:00  (10:00:00 + 60s)                       │
 *  │                                                             │
 *  │  ┌──────┬──────────┬──────────────────────┐                │
 *  │  │ Key  │ Value    │ Expires At          │                │
 *  │  ├──────┼──────────┼──────────────────────┤                │
 *  │  │ u:1  │ "Alice"  │ 10:01:00            │ ← active       │
 *  │  │ u:2  │ "Bob"    │ 09:58:00            │ ← EXPIRED      │
 *  │  └──────┴──────────┴──────────────────────┘                │
 *  │                                                             │
 *  │  get("u:1") → "Alice"   (10:00:30 < 10:01:00 → HIT)       │
 *  │  get("u:2") → null      (10:00:30 > 09:58:00 → EXPIRED)   │
 *  └─────────────────────────────────────────────────────────────┘
 *
 * ============================================================================
 *  EVICTION vs EXPIRY (IMPORTANT INTERVIEW DISTINCTION)
 * ============================================================================
 *  ┌─────────────────┬────────────────────────────────────────────┐
 *  │ EVICTION        │ EXPIRY (TTL)                              │
 *  ├─────────────────┼────────────────────────────────────────────┤
 *  │ Cache is FULL   │ Entry has been in cache too long          │
 *  │ Remove old to   │ Remove stale to prevent serving old data  │
 *  │ make space      │                                            │
 *  │ Policy-driven   │ Time-driven                                │
 *  │ (LRU, LFU, etc) │ (fixed TTL per entry or default)          │
 *  └─────────────────┴────────────────────────────────────────────┘
 *
 *  REAL CACHES USE BOTH: TTL for freshness, LRU for space mgmt.
 *
 * ============================================================================
 *  TTL STRATEGIES
 * ============================================================================
 *
 *  1. FIXED TTL: Same expiry for all entries (simple, most common)
 *  2. VARIABLE TTL: Different TTL per entry based on data type
 *  3. SLIDING TTL: Extends TTL on every access (session timeout)
 *  4. RANDOMIZED TTL: Add jitter ±20% to prevent avalanche
 *
 * ============================================================================
 *  INTERVIEW Q&A — TTL Cache
 * ============================================================================
 *
 *  Q1: What happens when TTL expires? Is the data immediately deleted?
 *  A1: Two approaches:
 *      - LAZY: Delete on next GET (simpler)
 *      - ACTIVE: Background thread periodically scans & deletes expired
 *        Redis uses both.
 *
 *  Q2: How does Redis handle TTL?
 *  A2: Redis uses:
 *      1. Lazy: Check expiry on every key access
 *      2. Active: Every 100ms, sample 20 random keys, delete expired,
 *         repeat if >25% expired (adaptive algorithm)
 *
 *  Q3: Can TTL and LRU work together?
 *  A3: Yes! Redis with volatile-lru = LRU eviction on keys WITH TTL only.
 *      TTL = freshness, LRU = space management.
 *
 *  Q4: What is the "Cache Avalanche" problem with TTL?
 *  A4: Many keys with SAME TTL expire together → DB overload.
 *      Solution: Add random jitter to TTL.
 *
 *  Q5: What is the ideal TTL value?
 *  A5: User sessions: 15-30 min, Products: 1-24h, Stock: 1-5s, News: 1-5m
 *
 * ============================================================================
 *  REAL-WORLD EXAMPLES
 * ============================================================================
 *  - Redis: EXPIRE key 3600
 *  - HTTP: Cache-Control: max-age=3600
 *  - DNS: TTL=300 seconds
 *  - JWT: TTL=15 min
 *  - OTP: TTL=5 min
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * Responsibility: Implement a TTL-based cache with lazy + active expiry.
 */
public class TTLCache<K, V> {

    private static class CacheEntry<V> {
        final V value;
        final long expiryTime;

        CacheEntry(V value, long ttlMillis) {
            this.value = value;
            this.expiryTime = System.currentTimeMillis() + ttlMillis;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }

    private final Map<K, CacheEntry<V>> cache;
    private final long defaultTtlMillis;
    private final boolean slidingTtl;

    public TTLCache(long defaultTtlMillis) {
        this(defaultTtlMillis, false, false);
    }

    public TTLCache(long defaultTtlMillis, boolean slidingTtl) {
        this(defaultTtlMillis, slidingTtl, false);
    }

    public TTLCache(long defaultTtlMillis, boolean slidingTtl, boolean enableCleaner) {
        this.cache = new HashMap<>();
        this.defaultTtlMillis = defaultTtlMillis;
        this.slidingTtl = slidingTtl;
        if (enableCleaner) startCleanerThread();
    }

    public void put(K key, V value) {
        put(key, value, defaultTtlMillis);
    }

    public void put(K key, V value, long ttlMillis) {
        cache.put(key, new CacheEntry<>(value, ttlMillis));
        System.out.println("   [TTL] PUT " + key + " = " + value
            + " (expires in " + ttlMillis + "ms)");
    }

    public V get(K key) {
        CacheEntry<V> entry = cache.get(key);
        if (entry == null) {
            System.out.println("   [TTL] GET " + key + " → MISS (not found)");
            return null;
        }
        if (entry.isExpired()) {
            cache.remove(key);
            System.out.println("   [TTL] GET " + key + " → EXPIRED (removed lazily)");
            return null;
        }
        if (slidingTtl) {
            long remaining = entry.expiryTime - System.currentTimeMillis();
            cache.put(key, new CacheEntry<>(entry.value, remaining));
            System.out.println("   [TTL] GET " + key + " → HIT (sliding: TTL extended)");
        } else {
            System.out.println("   [TTL] GET " + key + " → HIT");
        }
        return entry.value;
    }

    public void remove(K key) { cache.remove(key); }
    public void clear() { cache.clear(); }
    public int size() { return cache.size(); }

    public void display() {
        System.out.println("   [TTL Cache] Entries: " + cache.size());
        cache.forEach((key, entry) -> {
            long remainingMs = entry.expiryTime - System.currentTimeMillis();
            String status = entry.isExpired()
                ? "EXPIRED"
                : "valid (" + remainingMs + "ms remaining)";
            System.out.println("      " + key + " = " + entry.value + " → " + status);
        });
    }

    private void startCleanerThread() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "ttl-cache-cleaner");
            t.setDaemon(true);
            return t;
        });
        scheduler.scheduleAtFixedRate(() -> {
            int removed = 0;
            var iterator = cache.entrySet().iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getValue().isExpired()) {
                    iterator.remove();
                    removed++;
                }
            }
            if (removed > 0)
                System.out.println("   [TTL-Cleaner] Removed " + removed + " expired entries");
        }, 5, 5, TimeUnit.SECONDS);
    }

    // ================================================================
    //  MAIN — Demo & Test
    // ================================================================
    public static void main(String[] args) throws InterruptedException {
        System.out.println("============================================");
        System.out.println("   TTL CACHE — Complete Demo");
        System.out.println("============================================");

        TTLCache<String, String> cache = new TTLCache<>(2000);

        System.out.println("\n=== Test 1: Basic PUT and GET ===");
        cache.put("user:1", "Alice");
        cache.put("user:2", "Bob", 5000);
        cache.display();

        System.out.println("\n=== Test 2: GET before expiry (HIT) ===");
        System.out.println("   Result: " + cache.get("user:1"));

        System.out.println("\n=== Test 3: Wait for TTL to expire... ===");
        Thread.sleep(2500);
        System.out.println("   After 2.5s:");
        System.out.println("   Result: " + cache.get("user:1"));
        cache.display();

        System.out.println("\n=== Test 4: Entry with longer TTL still valid ===");
        System.out.println("   user:2 = " + cache.get("user:2"));

        System.out.println("\n=== Test 5: Sliding TTL demo ===");
        TTLCache<String, String> slidingCache = new TTLCache<>(2000, true);
        slidingCache.put("session:1", "active");
        for (int i = 1; i <= 5; i++) {
            Thread.sleep(1000);
            String s = slidingCache.get("session:1");
            System.out.println("   After " + i + "s: " + (s != null ? "STILL VALID" : "EXPIRED"));
        }

        System.out.println("\n=== Test 6: Custom TTL per entry ===");
        cache.put("short", "I last 1 second", 1000);
        cache.put("long", "I last 10 seconds", 10000);
        Thread.sleep(1500);
        System.out.println("   short (after 1.5s): " + cache.get("short"));
        System.out.println("   long (after 1.5s):  " + cache.get("long"));

        System.out.println("\n=== Test 7: Update resets TTL ===");
        cache.put("temp", "old", 3000);
        Thread.sleep(1000);
        cache.put("temp", "updated", 5000);
        System.out.println("   temp after 1s sleep + update: " + cache.get("temp"));

        System.out.println("\n============================================");
        System.out.println("  TTL Cache Demo Complete!");
        System.out.println("============================================");
    }
}
