package springboot_deep_drive.caching.policies;

/*
 * SIMPLE DEFINITION:
 * FIFO Cache evicts the item that was INSERTED FIRST when full.
 * "FIFO" = First-In-First-Out. Like a queue at a ticket counter:
 * the first person in line gets served first and leaves first.
 *
 * REAL-LIFE ANALOGY:
 * A parking lot with limited spots. The FIRST car that entered
 * leaves FIRST when a new car needs space, regardless of how
 * often the car is used. That's FIFO.
 *
 * ============================================================================
 *  FIFO CACHE (First-In-First-Out) — Queue-Based Eviction
 * ============================================================================
 *
 *  HOW IT WORKS:
 *    - Items are stored in insertion order (Queue)
 *    - When full, the HEAD of the queue (oldest) is evicted
 *    - GET does NOT change eviction order (unlike LRU)
 *
 *  DATA STRUCTURE: Queue + HashMap
 *    - Queue: maintains insertion order
 *    - HashMap: O(1) lookups
 *
 *  DIAGRAM:
 *    put(A) → put(B) → put(C) → [Queue: A, B, C] → Full!
 *    put(D) → evict A → [Queue: B, C, D]
 *
 * ============================================================================
 *  FIFO vs LRU vs LFU
 * ============================================================================
 *  ┌────────────┬─────────────────────────────────────────────────┐
 *  │ Policy     │ What gets evicted?                             │
 *  ├────────────┼─────────────────────────────────────────────────┤
 *  │ FIFO       │ The OLDEST item (by insertion time)             │
 *  │ LRU        │ The LEAST RECENTLY USED item (by access time)   │
 *  │ LFU        │ The LEAST FREQUENTLY USED item (by access count)│
 *  └────────────┴─────────────────────────────────────────────────┘
 *
 *  PROS: Simplest policy, O(1) eviction, fair (no starvation)
 *  CONS: Ignores access patterns entirely
 *
 * ============================================================================
 *  INTERVIEW Q&A — FIFO Cache
 * ============================================================================
 *
 *  Q1: When would you use FIFO over LRU?
 *  A1: - Data has equal value regardless of access (logs, metrics)
 *      - Predictable eviction order needed (compliance)
 *      - Very low overhead required (embedded systems)
 *
 *  Q2: What is the main disadvantage of FIFO?
 *  A2: "Belady's Anomaly" — increasing cache size can INCREASE miss rate
 *      (unlike LRU/LFU where more space always helps).
 *      Also, FIFO ignores access patterns entirely.
 *
 *  Q3: What is the optimal replacement policy?
 *  A3: Belady's MIN algorithm — evict the item that will be used
 *      FARTHEST in the future. Requires knowing the future (impractical).
 *      Used as theoretical upper bound for comparison.
 *
 *  Q4: Is FIFO used in any real system?
 *  A4: - CPU TLB (some architectures)
 *      - Network routers (packet buffering)
 *      - Simple CDN edge caches
 *      - Web browser HTTP cache (older implementations)
 */

import java.util.*;

public class FIFOCache<K, V> {

    private final int capacity;
    private final Map<K, V> map;
    private final Queue<K> queue;

    public FIFOCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>(capacity);
        this.queue = new LinkedList<>();
    }

    public V get(K key) {
        V val = map.get(key);
        if (val == null) {
            System.out.println("   [FIFO] GET " + key + " → MISS");
            return null;
        }
        System.out.println("   [FIFO] GET " + key + " → HIT");
        return val;
        // NOTE: GET does NOT change eviction order (unlike LRU)
    }

    public void put(K key, V value) {
        if (map.containsKey(key)) {
            map.put(key, value);
            System.out.println("   [FIFO] PUT " + key + " = " + value + " (updated)");
            return;
        }

        if (map.size() >= capacity) {
            K oldest = queue.poll();
            map.remove(oldest);
            System.out.println("   [FIFO] EVICTED " + oldest + " (oldest entry)");
        }

        map.put(key, value);
        queue.offer(key);
        System.out.println("   [FIFO] PUT " + key + " = " + value + " (added)");
    }

    public void display() {
        System.out.println("   [FIFO Cache] Size: " + map.size() + "/" + capacity);
        System.out.print("   [FIFO Queue] First → ");
        queue.forEach(k -> System.out.print(k + "=" + map.get(k) + " → "));
        System.out.println("← Last (newest)");
    }

    public int size() { return map.size(); }

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   FIFO CACHE — Complete Demo");
        System.out.println("============================================");

        FIFOCache<String, String> cache = new FIFOCache<>(3);

        System.out.println("\n=== Test 1: Basic PUT ===");
        cache.put("a", "apple");
        cache.put("b", "banana");
        cache.put("c", "cherry");
        cache.display();

        System.out.println("\n=== Test 2: GET does NOT change order ===");
        cache.get("a");
        cache.get("a");
        cache.display(); // Order still: a → b → c (FIFO, not LRU)

        System.out.println("\n=== Test 3: PUT new key evicts oldest (FIFO) ===");
        cache.put("d", "date"); // evicts "a" (first inserted)
        cache.display();

        System.out.println("\n=== Test 4: Queue order maintained ===");
        cache.put("e", "elderberry"); // evicts "b"
        cache.display();

        System.out.println("\n=== Test 5: Update existing ===");
        cache.put("c", "UpdatedCherry");
        cache.display(); // Order unchanged

        System.out.println("\n=== Test 6: FIFO vs LRU difference ===");
        System.out.println("  Pattern: put(1), put(2), put(3), get(1)x5, put(4)");
        System.out.println("    FIFO evicts: 1 (oldest, even though heavily used)");
        System.out.println("    LRU  evicts: 2 or 3 (least recently used)");
        System.out.println("  → FIFO ignores access patterns");

        System.out.println("\n============================================");
        System.out.println("  FIFO Cache Demo Complete!");
        System.out.println("============================================");
    }
}
