package EngineeringDigest.multithreading.concurrentCollections;

public class ConcurrentCollectionsConcept {
}

/*

========================================================================
                CONCURRENT COLLECTIONS
========================================================================

Concurrent Collections are thread-safe collection classes
designed for multithreading environments.

========================================================================
WHY CONCURRENT COLLECTIONS EXIST?
========================================================================

Normal collections like:
- ArrayList
- HashMap
- HashSet

are:
        NOT thread-safe

========================================================================

Multiple threads accessing/modifying them
may cause:
- race condition
- inconsistent data
- ConcurrentModificationException

========================================================================
PROBLEM EXAMPLE
========================================================================

Multiple threads updating:
========================================================================

HashMap

========================================================================

May corrupt internal structure.

========================================================================

Data inconsistency occurs.

========================================================================
OLD SOLUTION
========================================================================

Use:
        synchronized collections

========================================================================

Example:
========================================================================

Collections.synchronizedList()

========================================================================

BUT problem:
entire collection gets locked.

========================================================================

This causes:
- blocking
- poor scalability
- reduced performance

========================================================================
THIS IS WHY CONCURRENT COLLECTIONS EXIST
========================================================================

Provide:
- thread safety
- better concurrency
- higher performance
- fine-grained locking

========================================================================
WHAT ARE CONCURRENT COLLECTIONS?
========================================================================

Thread-safe optimized collections
designed for concurrent access.

========================================================================
IMPORTANT PACKAGE
========================================================================

        java.util.concurrent

========================================================================
MOST IMPORTANT CONCURRENT COLLECTIONS
========================================================================

1. ConcurrentHashMap
2. CopyOnWriteArrayList
3. ConcurrentLinkedQueue
4. BlockingQueue

========================================================================
1. CONCURRENTHASHMAP
========================================================================

Thread-safe version of:
        HashMap

========================================================================

Allows:
multiple threads to read/write safely.

========================================================================

IMPORTANT
========================================================================

Does NOT lock entire map.

========================================================================

Uses:
fine-grained locking/CAS internally.

========================================================================

Much faster than synchronized HashMap.

========================================================================
WHY CONCURRENTHASHMAP IMPORTANT?
========================================================================

HashMap becomes unsafe
in multithreading.

========================================================================

ConcurrentHashMap solves this efficiently.

========================================================================
IMPORTANT FEATURE
========================================================================

Multiple threads can:
- read simultaneously
- update different segments safely

========================================================================
2. COPYONWRITEARRAYLIST
========================================================================

Thread-safe version of:
        ArrayList

========================================================================

Whenever modification happens:
new copy of array created.

========================================================================

Read operations become:
very fast and safe.

========================================================================
WHY COPYONWRITEARRAYLIST USEFUL?
========================================================================

Best when:
- reads very frequent
- writes very less

========================================================================

Example:
- configuration lists
- subscribers list

========================================================================
IMPORTANT LIMITATION
========================================================================

Writes expensive because:
full array copied.

========================================================================
3. CONCURRENTLINKEDQUEUE
========================================================================

Thread-safe non-blocking queue.

========================================================================

Used for:
high-concurrency task sharing.

========================================================================
4. BLOCKINGQUEUE
========================================================================

Special queue supporting:
- producer-consumer pattern
- automatic waiting

========================================================================

If queue empty:
consumer waits automatically.

========================================================================

If queue full:
producer waits automatically.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Concurrent collections internally use:
- CAS
- fine-grained locking
- lock-free algorithms

========================================================================

Instead of heavy synchronization.

========================================================================
WHY BETTER THAN SYNCHRONIZED COLLECTIONS?
========================================================================

synchronized collections:
lock entire structure.

========================================================================

Concurrent collections:
allow better parallelism.

========================================================================
SYNCHRONIZED COLLECTION vs CONCURRENT COLLECTION
========================================================================

SYNCHRONIZED COLLECTION
------------------------
Single big lock

========================================================================

CONCURRENT COLLECTION
----------------------
Fine-grained locking/CAS

========================================================================

SYNCHRONIZED COLLECTION
------------------------
Lower scalability

========================================================================

CONCURRENT COLLECTION
----------------------
Higher scalability

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

ConcurrentHashMap allows:
concurrent reads and safer writes.

========================================================================

Much better than Hashtable.

========================================================================
HASHTABLE vs CONCURRENTHASHMAP
========================================================================

Hashtable
-----------
Entire table locked

========================================================================

ConcurrentHashMap
------------------
Partial locking / CAS

========================================================================

ConcurrentHashMap much faster.

========================================================================
REAL LIFE ANALOGY
========================================================================

Synchronized collection:
one big gate for all workers.

========================================================================

Concurrent collection:
multiple gates for different workers.

========================================================================

Less waiting,
better performance.

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

1. Caching systems
2. Session management
3. Task queues
4. Messaging systems
5. High-concurrency servers
6. Event processing

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Thread-safe
2. Better performance
3. Better scalability
4. Reduced locking
5. High concurrency support

========================================================================
IMPORTANT LIMITATION
========================================================================

Some concurrent collections:
more memory expensive
or more complex internally.

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. Why concurrent collections introduced?

→ To provide efficient thread-safe collections.

========================================================================

2. Which package contains concurrent collections?

→ java.util.concurrent

========================================================================

3. Is HashMap thread-safe?

→ No

========================================================================

4. Which class is thread-safe version of HashMap?

→ ConcurrentHashMap

========================================================================

5. Difference between Hashtable and ConcurrentHashMap?

Hashtable
-----------
Entire lock

ConcurrentHashMap
------------------
Fine-grained locking/CAS

========================================================================

6. Best use case of CopyOnWriteArrayList?

→ Frequent reads and fewer writes.

========================================================================

7. Which queue useful for producer-consumer?

→ BlockingQueue

========================================================================

8. Main advantage of concurrent collections?

→ Better concurrency with thread safety.

========================================================================

MOST IMPORTANT INTERVIEW LINE

Concurrent collections provide
high-performance thread-safe data structures
using fine-grained locking and lock-free mechanisms.

========================================================================

*/