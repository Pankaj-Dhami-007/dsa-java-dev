package EngineeringDigest.collections.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentHashMapDemo {

    /*
    ConcurrentHashMap = Thread safety + Performance + Scalability

    It solves:
Race conditions
Data corruption
Performance bottlenecks of synchronized maps
     */
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> map =  new ConcurrentHashMap<>();
        // Java 7 --> segment based locking --> 16 segments --> smaller hashmaps
        //Each segment = independent mini hash table
        //Each segment has its own Lock
        // Only the segment being written to or read from is locked
        // read: do not require locking unless there is a write operation happening on the same segment
        // write: lock
        //Better than Hashtable (whole map lock)

        // java 8 --> no segmentation
        //        --> Compare-And-Swap approach --> no locking except resizing or collision
        // Thread A last saw --> x = 45
        // Thread A work --> x to 50  update
        // if x is still 45(koi or thread ne change kiya ho skta h), then change it to 50 else don't change and retry
        //CAS is an atomic operation: Update value only if it hasn’t changed since I last saw it
        /*
        Time →
-----------------------------------

x = 45

Thread A reads → 45
Thread B updates → 60

Thread A tries:
    expected = 45
    actual = 60

CAS fails → retry
         */
        // put --> index

        // MAP --> SORTED --> THREAD SAFE --> ConcurrentSkipListMap
        // ConcurrentSkipListMap:
// Thread-safe + sorted map
// Uses skip list
// Slower than ConcurrentHashMap but maintains order

        /*
        Uses CAS + synchronized (fallback)
        Where CAS is used:
Inserting into empty bucket
✔ Where locking happens:
Collision (linked list / tree bin)
Resizing

PUT Operation---->
// put --> index

Let’s expand this properly:

Flow:
Calculate hash
Find bucket index
If empty:
CAS → insert node (no lock)
If not empty:
Synchronize on that bucket
Add node / tree

So:
Locking = bucket level, not segment
         */


        /*

        flow ----->
        // put --> index
        Step 1: Find index
        index = hash(key) % table.length

        Step 2: Check bucket
        Case 1: Bucket is EMPTY
        Uses CAS

if (table[index] == null) {
    CAS(table[index], null, newNode)
} // No lock needed

Case 2: Bucket NOT EMPTY (Collision)
then
synchronized (bucket) {
    // insert node
}
✔ Lock only that bucket



Simple Code Simulation of CAS

AtomicInteger x = new AtomicInteger(45);
// Thread A
boolean success = x.compareAndSet(45, 50);

Internally:
if (x == 45) {
    x = 50;
    return true;
} else {
    return false;
}
         */

        Map<String, Integer> map1 = new ConcurrentHashMap<>();
        map1.put("A", 5);
        map1.putIfAbsent("A", 10); // ignored  // Insert only if key is NOT present
        map1.putIfAbsent("B", 20); // inserted
        System.out.println(map1); // A=5, B=20

       // computeIfAbsent   - Runs function only if key is absent
       // computeIfAbsent is atomic
        // Lazy initialization
        //Creating collections inside map
        Map<String, List<String>> map2 = new ConcurrentHashMap<>();
        map2.computeIfAbsent("A", k -> new ArrayList<>()).add("Apple");
        map2.computeIfAbsent("A", k -> new ArrayList<>()).add("Mango");
        System.out.println(map2);  // A=[Apple, Mango]

       // compute --  Always runs (whether key exists or not)
        // Need full control
        // Insert + update + delete in one method



        // merge
        // If key absent → insert
        // If present → merge old + new
        // map.merge("A", 10, (oldVal, newVal) -> oldVal + newVal);
        /*
        equivalent to
        if (map.containsKey("A")) {
    map.put("A", old + new);
} else {
    map.put("A", new);
}
         */
        Map<String, Integer> map3 = new ConcurrentHashMap<>();
        map3.merge("A", 10, Integer::sum); // insert
        map3.merge("A", 5, Integer::sum);  // update
        System.out.println(map3); // o/p -> 15


        ConcurrentSkipListMap<Integer, String> concurrentSkipListMap =
                new ConcurrentSkipListMap<>();
        concurrentSkipListMap.put(3, "C");
        concurrentSkipListMap.put(1, "A");
        concurrentSkipListMap.put(2, "B");
        System.out.println(concurrentSkipListMap); // sorted

        Map<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(3, "C");
        treeMap.put(1, "A");
        treeMap.put(2, "B");
        System.out.println(treeMap);// sorted

        // TreeMap (Red-Black Tree) - Self-balancing binary tree, Maintains order using rotations,Strict structure
        // ConcurrentSkipListMap (Skip List) -Multi-level linked list, Probabilistic balancing, Easier for concurrency

    }
}

/*
ConcurrentSkipListMap
Thread-safe
Maintains sorted order of keys
High concurrency (non-blocking)

How It Works Internally
Instead of hash table or tree, it uses:
Skip List (probabilistic data structure)

What is a Skip List?

Imagine multiple levels of linked lists:

Level 3:      --------> 30 -------->
Level 2:   --> 10 ----> 30 ----> 50
Level 1:   5 -> 10 -> 20 -> 30 -> 40 -> 50

Higher levels "skip" elements → faster search

Why Skip List?
Search → O(log n)
Insert → O(log n)
Delete → O(log n)

Similar to balanced trees (like Red-Black Tree), but easier for concurrency


 */
