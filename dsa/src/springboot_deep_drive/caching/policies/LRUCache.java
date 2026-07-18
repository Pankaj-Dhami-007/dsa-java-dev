package springboot_deep_drive.caching.policies;

/*
 * SIMPLE DEFINITION:
 * LRU Cache evicts the item that was accessed LONGEST AGO when it runs
 * out of space. "Least Recently Used" = oldest unused item goes first.
 *
 * REAL-LIFE ANALOGY:
 * Your desk has space for 3 books. You keep the ones you read recently.
 * When a new book arrives, you remove the one you haven't touched in the
 * longest time. That's LRU.
 *
 * ============================================================================
 *  LRU CACHE (Least Recently Used) — Eviction Policy
 * ============================================================================
 *
 *  WHAT IS LRU?
 *  ------------
 *  LRU evicts the LEAST RECENTLY USED entry when cache reaches capacity.
 *  "Recently used" = accessed (get/put) most recently.
 *
 *  KEY IDEA:
 *    Most recently accessed item → kept in cache (likely to be accessed again)
 *    Least recently accessed item → evicted first (likely not needed again)
 *
 * ============================================================================
 *  HOW IT WORKS (INTERVIEW STANDARD)
 * ============================================================================
 *
 *  Data Structure: HashMap + Doubly LinkedList
 *
 *    ┌──────────────────────────────────────────────────────────┐
 *    │  HashMap: key -> Node                                   │
 *    │  ┌────────────┐                                         │
 *    │  │  key:1  ───┼──> Node(1) ⇄ Node(3) ⇄ Node(2)         │
 *    │  │  key:2  ───┼──>  ↑              ↑              ↑    │
 *    │  │  key:3  ───┼──> HEAD        (middle)        TAIL    │
 *    │  └────────────┘       (MRU)                     (LRU)  │
 *    └──────────────────────────────────────────────────────────┘
 *
 *  - HEAD = Most Recently Used (MRU)
 *  - TAIL = Least Recently Used (LRU) → evicted when full
 *  - On GET: move node to HEAD
 *  - On PUT: if exists → move to HEAD (update value)
 *            if new → add to HEAD; if over capacity → remove TAIL
 *
 *  WHY DOUBLY LINKED LIST?
 *    O(1) removal from any position (unlike singly linked)
 *    O(1) move to front (detach + attach at head)
 *
 * ============================================================================
 *  TIME COMPLEXITY
 * ============================================================================
 *  GET: O(1) — HashMap lookup + pointer adjustments
 *  PUT: O(1) — HashMap insert/update + pointer adjustments
 *  EVICT: O(1) — remove TAIL node
 *
 * ============================================================================
 *  INTERVIEW Q&A — LRU Cache
 * ============================================================================
 *
 *  Q1: Implement LRU Cache from scratch.
 *  A1: See below — standard implementation using HashMap + Doubly LinkedList.
 *
 *  Q2: Can you implement LRU using LinkedHashMap?
 *  A2: Yes — Java's LinkedHashMap with accessOrder=true and override
 *      removeEldestEntry(). That's the easy way. But interviewers
 *      want the manual implementation (shown below).
 *
 *  Q3: What is the difference between LRU and LFU?
 *  A3: LRU evicts by TIME of last access (oldest unaccessed).
 *      LFU evicts by FREQUENCY of access (least frequently used).
 *      LRU is better when recently accessed items are likely reused.
 *      LFU is better for long-term popularity patterns.
 *
 *  Q4: What are real-world uses of LRU?
 *  A4: - Redis with allkeys-lru policy
 *      - CPU cache (data locality)
 *      - Database buffer pool (InnoDB)
 *      - Browser page cache
 *      - API response caching (most recent queries)
 *
 *  Q5: What are the drawbacks of LRU?
 *  A5: - "Scan resistance": a one-time scan of many keys flushes
 *        out genuinely popular keys (see: LRU-K, 2Q, ARC algorithms)
 *      - Not ideal for frequency-based patterns
 *      - Memory overhead of prev/next pointers
 *
 *  Q6: How would you make LRU thread-safe?
 *  A6: Use synchronized blocks, or ConcurrentHashMap + ReentrantLock,
 *      or ReadWriteLock. Java's LinkedHashMap is not thread-safe.
 *
 *  Q7: What is LRU-K?
 *  A7: Tracks last K accesses instead of just 1. More scan-resistant.
 *      Common: LRU-2 (keep last 2 access times). Used in PostgreSQL.
 *
 *  Q8: What is the difference between LRU and MRU?
 *  A8: LRU evicts LEAST recently used (most common).
 *      MRU evicts MOST recently used (used when older data is more likely
 *      to be accessed — e.g., looping through dataset).
 *
 * ============================================================================
 *  REAL-WORLD EXAMPLE
 * ============================================================================
 *  Redis with maxmemory-policy allkeys-lru:
 *    When Redis hits memory limit, it evicts keys using LRU approximation
 *    (not exact LRU — samples 5 keys, evicts the LRU among them for speed).
 *
 *  MySQL InnoDB Buffer Pool:
 *    Uses a variant of LRU with a midpoint insertion strategy.
 *    New pages go to the middle (not head) to prevent one-time scans
 *    from flushing hot pages.
 *
 * ============================================================================
 *  COMPARISON WITH OTHER POLICIES
 * ============================================================================
 *  ┌──────────┬─────────────────────────────────────────────────────────┐
 *  │ Policy   │ Evicts...                                              │
 *  ├──────────┼─────────────────────────────────────────────────────────┤
 *  │ LRU      │ Oldest accessed item (by time)                         │
 *  │ LFU      │ Least frequently accessed item (by count)              │
 *  │ FIFO     │ First inserted item (queue order)                      │
 *  │ TTL      │ Expired items (by timestamp)                           │
 *  │ Random   │ Random item (surprisingly effective at scale)          │
 *  │ MRU      │ Most recently accessed item                            │
 *  └──────────┴─────────────────────────────────────────────────────────┘
 */

import java.util.HashMap;
import java.util.Map;

/*
 * Responsibility: Implement and demonstrate LRU Cache from scratch
 * using HashMap + Doubly LinkedList (interview-standard approach).
 *
 * This class serves as both the implementation AND the demo.
 */
public class LRUCache<K, V> {

    // ================================================================
    //  NODE — inner class for Doubly Linked List
    // ================================================================
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // ================================================================
    //  FIELDS
    // ================================================================
    private final int capacity;
    private final Map<K, Node<K, V>> map;
    private Node<K, V> head;  // MRU (Most Recently Used)
    private Node<K, V> tail;  // LRU (Least Recently Used)

    // ================================================================
    //  CONSTRUCTOR
    // ================================================================
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>(capacity);
    }

    // ================================================================
    //  GET — O(1)
    // ================================================================
    public V get(K key) {
        Node<K, V> node = map.get(key);
        if (node == null) {
            System.out.println("   [LRU] GET " + key + " → MISS");
            return null;
        }
        moveToHead(node);
        System.out.println("   [LRU] GET " + key + " → HIT (value=" + node.value + ")");
        return node.value;
    }

    // ================================================================
    //  PUT — O(1)
    // ================================================================
    public void put(K key, V value) {
        Node<K, V> existing = map.get(key);

        if (existing != null) {
            existing.value = value;
            moveToHead(existing);
            System.out.println("   [LRU] PUT " + key + " = " + value + " (updated, moved to MRU)");
            return;
        }

        Node<K, V> newNode = new Node<>(key, value);
        map.put(key, newNode);
        addToHead(newNode);
        System.out.println("   [LRU] PUT " + key + " = " + value + " (added)");

        if (map.size() > capacity) {
            Node<K, V> lru = removeTail();
            map.remove(lru.key);
            System.out.println("   [LRU] EVICTED " + lru.key + " (capacity reached)");
        }
    }

    // ================================================================
    //  LINKED LIST OPERATIONS (all O(1))
    // ================================================================

    private void moveToHead(Node<K, V> node) {
        if (node == head) return;
        detach(node);
        addToHead(node);
    }

    private void detach(Node<K, V> node) {
        Node<K, V> prevNode = node.prev;
        Node<K, V> nextNode = node.next;

        if (prevNode != null) {
            prevNode.next = nextNode;
        } else {
            head = nextNode;
        }

        if (nextNode != null) {
            nextNode.prev = prevNode;
        } else {
            tail = prevNode;
        }

        node.prev = null;
        node.next = null;
    }

    private void addToHead(Node<K, V> node) {
        node.next = head;
        node.prev = null;

        if (head != null) {
            head.prev = node;
        }
        head = node;

        if (tail == null) {
            tail = node;
        }
    }

    private Node<K, V> removeTail() {
        if (tail == null) return null;
        Node<K, V> lru = tail;
        detach(lru);
        return lru;
    }

    // ================================================================
    //  UTILITY
    // ================================================================
    public void display() {
        System.out.print("   [LRU Cache] MRU → ");
        Node<K, V> current = head;
        while (current != null) {
            System.out.print(current.key + "=" + current.value);
            if (current.next != null) System.out.print(" → ");
            current = current.next;
        }
        System.out.println(" ← LRU");
        System.out.println("   [LRU Cache] Size: " + map.size() + "/" + capacity);
    }

    public int size() { return map.size(); }

    // ================================================================
    //  MAIN — Demo & Interview-Ready Test Cases
    // ================================================================
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   LRU CACHE — Complete Demo");
        System.out.println("============================================");

        LRUCache<Integer, String> cache = new LRUCache<>(3);

        System.out.println("\n=== Test 1: Basic PUT and GET ===");
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        cache.display();

        System.out.println("\n=== Test 2: GET moves node to MRU ===");
        cache.get(1);
        cache.display();

        System.out.println("\n=== Test 3: PUT new key triggers eviction ===");
        cache.put(4, "D");
        cache.display();

        System.out.println("\n=== Test 4: PUT existing key updates value ===");
        cache.put(1, "UpdatedA");
        cache.display();

        System.out.println("\n=== Test 5: GET non-existent key ===");
        String val = cache.get(99);
        assert val == null : "Should return null for missing key";

        System.out.println("\n=== Test 6: Full eviction sequence ===");
        LRUCache<String, String> stringCache = new LRUCache<>(2);
        stringCache.put("a", "apple");
        stringCache.put("b", "banana");
        stringCache.display();
        stringCache.put("c", "cherry");
        stringCache.display();
        stringCache.get("b");
        stringCache.put("d", "date");
        stringCache.display();

        System.out.println("\n=== Test 7: Edge case — capacity 1 ===");
        LRUCache<Integer, String> tinyCache = new LRUCache<>(1);
        tinyCache.put(1, "one");
        System.out.println("  After put(1): "); tinyCache.display();
        tinyCache.put(2, "two");
        System.out.println("  After put(2): "); tinyCache.display();

        System.out.println("\n============================================");
        System.out.println("  LRU Cache Demo Complete!");
        System.out.println("  Complexity: GET O(1), PUT O(1), EVICT O(1)");
        System.out.println("  Data Structure: HashMap + Doubly LinkedList");
        System.out.println("============================================");
    }
}
