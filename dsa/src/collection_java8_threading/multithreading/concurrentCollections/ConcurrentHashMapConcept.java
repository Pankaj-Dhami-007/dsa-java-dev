package EngineeringDigest.multithreading.concurrentCollections;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapConcept {

    public static void main(String[] args)
            throws InterruptedException {

        // thread-safe map
        ConcurrentHashMap<Integer, String> map =
                new ConcurrentHashMap<>();

        // thread 1
        Thread t1 = new Thread(() -> {

            for (int i = 1; i <= 5; i++) {

                map.put(i, "Thread-1 Value-" + i);

                System.out.println(
                        Thread.currentThread().getName()
                                + " inserted : "
                                + i
                );
            }
        });

        // thread 2
        Thread t2 = new Thread(() -> {

            for (int i = 6; i <= 10; i++) {

                map.put(i, "Thread-2 Value-" + i);

                System.out.println(
                        Thread.currentThread().getName()
                                + " inserted : "
                                + i
                );
            }
        });

        t1.setName("Writer-1");
        t2.setName("Writer-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println();
        System.out.println("Final Map : ");
        System.out.println(map);
    }
}

/*

========================================================================
                    CONCURRENTHASHMAP
========================================================================

ConcurrentHashMap is MOST IMPORTANT
concurrent collection in Java.

========================================================================
WHY CONCURRENTHASHMAP EXISTS?
========================================================================

Normal HashMap is:
        NOT thread-safe

========================================================================

Multiple threads modifying HashMap
may cause:
- race condition
- inconsistent data
- data corruption

========================================================================

Sometimes even:
infinite loop internally.

========================================================================
OLD SOLUTION
========================================================================

Use:
        Hashtable

OR

Collections.synchronizedMap()

========================================================================

BUT PROBLEM
========================================================================

Entire map gets locked.

========================================================================

Only one thread can access map at a time.

========================================================================

Performance becomes poor.

========================================================================
THIS IS WHY CONCURRENTHASHMAP EXISTS
========================================================================

Provides:
- thread safety
- better concurrency
- better performance

========================================================================
WHAT IS CONCURRENTHASHMAP?
========================================================================

Thread-safe high-performance version
of HashMap.

========================================================================
IMPORTANT PACKAGE
========================================================================

        java.util.concurrent

========================================================================
MAIN IDEA
========================================================================

Allow multiple threads
to access map safely
WITHOUT locking entire map.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

ConcurrentHashMap uses:
- fine-grained locking
- CAS
- lock-free reads

========================================================================

instead of one big lock.

========================================================================
CODE FLOW
========================================================================

Two threads:
- Writer-1
- Writer-2

========================================================================

Both threads inserting data simultaneously.

========================================================================

ConcurrentHashMap safely handles:
concurrent modifications.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Multiple threads can:
- read simultaneously
- write safely

========================================================================

Much better concurrency than Hashtable.

========================================================================
OUTPUT CAN BE
========================================================================

Writer-1 inserted : 1
Writer-2 inserted : 6

Writer-1 inserted : 2
Writer-2 inserted : 7

========================================================================

Execution order may vary.

========================================================================

BUT:
data remains safe and consistent.

========================================================================
WHY HASHMAP UNSAFE?
========================================================================

HashMap internal structure
can become corrupted
during concurrent modification.

========================================================================

Because:
multiple threads modify buckets simultaneously.

========================================================================
WHY CONCURRENTHASHMAP SAFE?
========================================================================

Uses:
- bucket-level locking
- CAS operations
- synchronized only where necessary

========================================================================

Reduces contention significantly.

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

ConcurrentHashMap does NOT lock whole map.

========================================================================

Only required portion/bucket locked.

========================================================================
IMPORTANT INTERNAL CONCEPT
========================================================================

READ OPERATIONS
----------------
Mostly lock-free.

========================================================================

WRITE OPERATIONS
-----------------
Use minimal locking/CAS.

========================================================================

This gives:
high scalability.

========================================================================
HASHTABLE vs CONCURRENTHASHMAP
========================================================================

Hashtable
-----------
Entire table lock

========================================================================

ConcurrentHashMap
------------------
Fine-grained locking

========================================================================

Hashtable
-----------
Poor concurrency

========================================================================

ConcurrentHashMap
------------------
Better concurrency

========================================================================

Hashtable
-----------
Slower

========================================================================

ConcurrentHashMap
------------------
Faster

========================================================================
VERY IMPORTANT LIMITATION
========================================================================

Compound operations still may need:
additional synchronization.

========================================================================

Example:
========================================================================

if(map.containsKey(key)) {

    map.put(key, value);

}

========================================================================

Still not fully atomic together.

========================================================================
COMMON METHODS
========================================================================

1. put()
2. get()
3. remove()
4. putIfAbsent()
5. compute()

========================================================================
IMPORTANT METHOD
========================================================================

putIfAbsent()

========================================================================

Atomically inserts value
only if key absent.

========================================================================
REAL LIFE ANALOGY
========================================================================

Normal HashMap:
one small shop counter
everyone fighting together.

========================================================================

ConcurrentHashMap:
multiple counters available.

========================================================================

Less waiting,
better speed.

========================================================================
REAL-WORLD USE CASES
========================================================================

1. Cache systems
2. Session storage
3. Real-time analytics
4. API rate limiting
5. High-concurrency servers

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Thread-safe
2. High performance
3. Better scalability
4. Lock-free reads
5. Fine-grained locking

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. Why ConcurrentHashMap introduced?

→ To provide thread-safe high-performance map.

========================================================================

2. Is HashMap thread-safe?

→ No

========================================================================

3. Difference between Hashtable and ConcurrentHashMap?

Hashtable
-----------
Whole table lock

ConcurrentHashMap
------------------
Fine-grained locking

========================================================================

4. Does ConcurrentHashMap lock whole map?

→ No

========================================================================

5. Are reads lock-free in ConcurrentHashMap?

→ Mostly yes

========================================================================

6. Which package contains ConcurrentHashMap?

→ java.util.concurrent

========================================================================

7. Main advantage of ConcurrentHashMap?

→ Better concurrency and scalability.

========================================================================

8. Which operation useful for atomic insert?

→ putIfAbsent()

========================================================================

MOST IMPORTANT INTERVIEW LINE

ConcurrentHashMap provides
thread-safe high-performance concurrent access
using fine-grained locking and CAS mechanisms.

========================================================================

*/