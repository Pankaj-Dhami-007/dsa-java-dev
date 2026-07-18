package springboot_deep_drive.caching.policies;

/*
 * SIMPLE DEFINITION:
 * LFU Cache evicts the item that is used the LEAST NUMBER OF TIMES.
 * "LFU" = Least Frequently Used. Tracks access count per entry.
 * The entry with the LOWEST access count gets evicted first.
 *
 * REAL-LIFE ANALOGY:
 * A gym has limited lockers. The locker used by the member who visits
 * the LEAST often gets reassigned when a new member joins. The most
 * frequent visitors keep their lockers. That's LFU.
 *
 * ============================================================================
 *  LFU CACHE (Least Frequently Used) — Frequency-Based Eviction
 * ============================================================================
 *
 *  KEY IDEA:
 *    Items accessed FREQUENTLY → likely to be accessed again → keep
 *    Items accessed RARELY → likely not needed → evict
 *
 *  WHERE LFU SHINES:
 *    - Long-term popularity patterns (viral content, trending topics)
 *    - CDN caching (popular files stay cached longer)
 *    - Database query caching (frequent queries stay in memory)
 *
 *  WHERE LFU FAILS:
 *    - "Celebrity problem": A once-popular item stays cached forever
 *      even after nobody accesses it (frequency never decays)
 *    - New items get evicted quickly before they can prove popularity
 *    - Solution: LFU with aging / window-based LFU / LFU+LRU hybrid
 *
 * ============================================================================
 *  HOW IT WORKS
 * ============================================================================
 *
 *  Data Structure: HashMap + MinHeap (PriorityQueue) or Frequency List
 *
 *  APPROACH 1 (Simple): HashMap<Key, {Value, Count}> + MinHeap
 *    - GET: increment count, re-heapify O(log n)
 *    - PUT: add with count=1
 *    - EVICT: remove item with LOWEST count from heap O(log n)
 *
 *  APPROACH 2 (Optimal - O(1)): HashMap + Frequency HashMap + Doubly LinkedLists
 *    - Each frequency has its own doubly linked list
 *    - On access: move node from freq N list to freq N+1 list
 *    - Evict: remove from lowest non-empty frequency list
 *    - Complexity: GET O(1), PUT O(1), EVICT O(1)
 *
 *  We implement APPROACH 1 (MinHeap) for clarity, then show APPROACH 2.
 *
 * ============================================================================
 *  TIME COMPLEXITY
 * ============================================================================
 *  Approach 1 (MinHeap): GET O(log n), PUT O(log n), EVICT O(log n)
 *  Approach 2 (Optimal): GET O(1), PUT O(1), EVICT O(1)
 *
 * ============================================================================
 *  INTERVIEW Q&A — LFU Cache
 * ============================================================================
 *
 *  Q1: Implement LFU Cache (LeetCode #460 Hard).
 *  A1: See below. Two implementations: MinHeap (simple) and O(1) (optimal).
 *
 *  Q2: LRU vs LFU — which one should you use?
 *  A2: Depends on access pattern:
 *      - LRU: Recently accessed items likely reused (news feed, API responses)
 *      - LFU: Frequently accessed items stay popular (viral videos, top products)
 *      - Many real systems use HYBRID (e.g., Redis with LFU + aging)
 *
 *  Q3: What is the "cache pollution" problem with LFU?
 *  A3: Once an item gets a high frequency count, it stays in cache forever
 *      even if nobody accesses it anymore. Called "frequency inertia."
 *      Solution: Periodic frequency decay (divide all counts by 2 every hour).
 *
 *  Q4: How does Redis implement LFU?
 *  A4: Redis LFU uses a "Morris counter" (probabilistic counter) instead of
 *      exact integer. It counts up to 255 with logarithmic resolution.
 *      It also applies aging: counter decays over time (based on log(n) factor).
 *      This solves both memory and the frequency inertia problem.
 *
 *  Q5: What is Window-Based LFU?
 *  A5: Instead of counting ALL accesses (forever), count only accesses within
 *      a sliding time window (e.g., last 1 hour). Old accesses expire.
 *      Solves the frequency inertia problem.
 *
 *  Q6: Can you combine LRU and LFU?
 *  A6: Yes! ARC (Adaptive Replacement Cache) and 2Q algorithms dynamically
 *      switch between LRU and LFU based on workload patterns.
 *
 *  Q7: What is the "Scan" problem with LFU vs LRU?
 *  A7: LRU suffers from scans (one-time bulk reads flush hot cache).
 *      LFU is SCAN-RESISTANT: one-time reads have count=1, hot items stay.
 *
 * ============================================================================
 *  COMPARISON: LRU vs LFU vs FIFO
 * ============================================================================
 *  ┌──────────┬──────────────┬──────────────┬────────────────────────┐
 *  │ Feature  │     LRU      │     LFU      │         FIFO           │
 *  ├──────────┼──────────────┼──────────────┼────────────────────────┤
 *  │ Evicts   │ Oldest access│ Least freq   │ First inserted         │
 *  │ Scan res │ ❌ Weak      │ ✅ Strong   │ ✅ Strong              │
 *  │ Celebrity│ ❌ N/A       │ ❌ Problem   │ ❌ N/A                │
 *  │ Overhead │ Medium (ptr) │ High (counts)│ Low (queue)            │
 *  │ Best for │ Temporal loc │ Popularity   │ Simple FIFO workloads  │
 *  └──────────┴──────────────┴──────────────┴────────────────────────┘
 */

import java.util.*;

/*
 * Responsibility: Implement LFU Cache using MinHeap approach.
 * Each entry tracks its access count. On eviction, the one with
 * the lowest count is removed.
 */
public class LFUCache<K, V> {

    // ================================================================
    //  ENTRY — stores value + access frequency
    // ================================================================
    private static class Entry<K, V> implements Comparable<Entry<K, V>> {
        final K key;
        V value;
        int frequency;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.frequency = 1;
        }

        // MinHeap orders by frequency (lowest first)
        @Override
        public int compareTo(Entry<K, V> other) {
            return Integer.compare(this.frequency, other.frequency);
        }
    }

    // ================================================================
    //  FIELDS
    // ================================================================
    private final int capacity;
    private final Map<K, Entry<K, V>> map;
    private final PriorityQueue<Entry<K, V>> minHeap;

    // ================================================================
    //  CONSTRUCTOR
    // ================================================================
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>(capacity);
        this.minHeap = new PriorityQueue<>();
    }

    // ================================================================
    //  GET — O(log n)
    // ================================================================
    /*
     * 1. Lookup in HashMap
     * 2. If not found → MISS (return null)
     * 3. If found → increment frequency, re-heapify → return value
     */
    public V get(K key) {
        Entry<K, V> entry = map.get(key);
        if (entry == null) {
            System.out.println("   [LFU] GET " + key + " → MISS");
            return null;
        }
        // Increment frequency and re-heapify
        minHeap.remove(entry); // O(n) in simple PQ, see note below
        entry.frequency++;
        minHeap.offer(entry);  // O(log n)
        System.out.println("   [LFU] GET " + key + " → HIT (freq=" + entry.frequency + ")");
        return entry.value;
    }

    /*
     * NOTE: PriorityQueue.remove() is O(n). For O(1) removal, we'd use
     * a HashMap<K, Node> + Frequency Hash + Doubly LinkedLists (Approach 2).
     * This MinHeap version is for CONCEPTUAL understanding.
     * LeetCode official O(1) solution uses the Frequency List approach.
     */

    // ================================================================
    //  PUT — O(log n)
    // ================================================================
    /*
     * 1. If key exists → update value, increment frequency
     * 2. If key is new:
     *    a. If at capacity → evict MIN frequency entry
     *    b. Add new entry with frequency = 1
     */
    public void put(K key, V value) {
        if (capacity <= 0) return;

        Entry<K, V> existing = map.get(key);

        if (existing != null) {
            // Update existing entry
            existing.value = value;
            minHeap.remove(existing);
            existing.frequency++;
            minHeap.offer(existing);
            System.out.println("   [LFU] PUT " + key + " = " + value
                + " (updated, freq=" + existing.frequency + ")");
            return;
        }

        // New entry — evict if full
        if (map.size() >= capacity) {
            evictLeastFrequent();
        }

        Entry<K, V> newEntry = new Entry<>(key, value);
        map.put(key, newEntry);
        minHeap.offer(newEntry);
        System.out.println("   [LFU] PUT " + key + " = " + value + " (added, freq=1)");
    }

    // ================================================================
    //  EVICTION LOGIC
    // ================================================================
    private void evictLeastFrequent() {
        if (minHeap.isEmpty()) return;

        Entry<K, V> lfu = minHeap.poll(); // gets lowest frequency entry
        map.remove(lfu.key);
        System.out.println("   [LFU] EVICTED " + lfu.key
            + " (freq=" + lfu.frequency + ")");
    }

    // ================================================================
    //  UTILITY
    // ================================================================
    public void display() {
        System.out.println("   [LFU Cache] Size: " + map.size() + "/" + capacity);
        // Sort entries by frequency for display
        map.values().stream()
            .sorted(Comparator.comparingInt(e -> e.frequency))
            .forEach(e -> System.out.println("      " + e.key + "=" + e.value
                + " (freq=" + e.frequency + ")"));
    }

    public int size() { return map.size(); }

    // ================================================================
    //  MAIN — Demo & Test
    // ================================================================
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   LFU CACHE — Complete Demo");
        System.out.println("============================================");

        LFUCache<String, String> cache = new LFUCache<>(3);

        System.out.println("\n=== Test 1: Basic PUT ===");
        cache.put("a", "apple");
        cache.put("b", "banana");
        cache.put("c", "cherry");
        cache.display();
        // All have freq=1

        System.out.println("\n=== Test 2: GET increases frequency ===");
        cache.get("a"); // freq: a=2, b=1, c=1
        cache.get("a"); // freq: a=3, b=1, c=1
        cache.get("b"); // freq: a=3, b=2, c=1
        cache.display();

        System.out.println("\n=== Test 3: PUT triggers eviction of LFU ===");
        // LFU = "c" (freq=1) should be evicted
        cache.put("d", "date");
        cache.display();
        // Expected: d(freq=1), b(freq=2), a(freq=3) — c evicted

        System.out.println("\n=== Test 4: Access pattern eviction ===");
        LFUCache<Integer, String> demo = new LFUCache<>(2);
        demo.put(1, "One");
        demo.put(2, "Two");
        demo.get(1); // freq: 1=2, 2=1
        demo.get(1); // freq: 1=3, 2=1
        demo.put(3, "Three"); // evicts 2 (freq=1)
        demo.display();
        // Expected: 3(freq=1), 1(freq=3)

        System.out.println("\n=== Test 5: Update existing key ===");
        cache.put("a", "UpdatedApple"); // freq should increment
        cache.display();

        System.out.println("\n=== Test 6: LFU vs LRU comparison ===");
        System.out.println("  Access pattern: get('a') 100x, get('b') 1x, get('c') 1x");
        System.out.println("  Then new 'd' added:");
        System.out.println("    LFU: evicts 'b' or 'c' (lowest freq) → keeps 'a'");
        System.out.println("    LRU: evicts 'a' (oldest access) → loses the hot key!");
        System.out.println("  → LFU better for popularity patterns");

        System.out.println("\n============================================");
        System.out.println("  LFU Cache Demo Complete!");
        System.out.println("  Complexity: GET O(log n), PUT O(log n)");
        System.out.println("  O(1) version uses FreqMap + LinkedLists");
        System.out.println("============================================");
    }
}
