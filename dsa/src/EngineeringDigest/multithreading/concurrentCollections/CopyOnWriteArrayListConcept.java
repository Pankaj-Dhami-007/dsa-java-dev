package EngineeringDigest.multithreading.concurrentCollections;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListConcept {

    public static void main(String[] args)
            throws InterruptedException {

        // thread-safe list
        CopyOnWriteArrayList<String> list =
                new CopyOnWriteArrayList<>();

        list.add("A");
        list.add("B");
        list.add("C");

        // reader thread
        Thread reader = new Thread(() -> {

            for (String value : list) {

                System.out.println(
                        Thread.currentThread().getName()
                                + " reading : "
                                + value
                );

                try {

                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // writer thread
        Thread writer = new Thread(() -> {

            try {

                Thread.sleep(1500);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list.add("D");

            System.out.println(
                    Thread.currentThread().getName()
                            + " added : D"
            );
        });

        reader.setName("Reader-Thread");
        writer.setName("Writer-Thread");

        reader.start();
        writer.start();

        reader.join();
        writer.join();

        System.out.println();
        System.out.println("Final List : " + list);
    }
}

/*

========================================================================
                COPYONWRITEARRAYLIST
========================================================================

CopyOnWriteArrayList is thread-safe version
of ArrayList.

========================================================================
WHY COPYONWRITEARRAYLIST EXISTS?
========================================================================

Normal ArrayList is:
        NOT thread-safe

========================================================================

If one thread modifies list
while another thread iterates list,
problem may occur.

========================================================================

Most common issue:
========================================================================

ConcurrentModificationException

========================================================================
OLD SOLUTION
========================================================================

Use:
        Collections.synchronizedList()

========================================================================

BUT PROBLEM
========================================================================

Entire list gets locked.

========================================================================

Performance decreases.

========================================================================
THIS IS WHY COPYONWRITEARRAYLIST EXISTS
========================================================================

Provides:
safe concurrent reading and modification
without ConcurrentModificationException.

========================================================================
WHAT IS COPYONWRITEARRAYLIST?
========================================================================

Thread-safe list where:
every modification creates
new copy of internal array.

========================================================================
MAIN IDEA
========================================================================

READ operations:
very fast and safe.

========================================================================

WRITE operations:
create new copied array.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Whenever:
- add()
- remove()
- update()

========================================================================

New internal array created.

========================================================================

Old array remains unchanged
for readers.

========================================================================
CODE FLOW
========================================================================

Initial list:
========================================================================

[A, B, C]

========================================================================

Reader thread starts iterating.

========================================================================

Meanwhile:
Writer thread adds:
        D

========================================================================

CopyOnWriteArrayList creates:
new copied array internally.

========================================================================

Reader thread continues safely
on old snapshot.

========================================================================

No exception occurs.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Iterator works on:
        snapshot copy

========================================================================

NOT live changing array.

========================================================================
OUTPUT CAN BE
========================================================================

Reader-Thread reading : A

Reader-Thread reading : B

Writer-Thread added : D

Reader-Thread reading : C

========================================================================

Final List : [A, B, C, D]

========================================================================
IMPORTANT OBSERVATION
========================================================================

Reader thread may NOT see:
        D

========================================================================

Because iterator uses:
old snapshot copy.

========================================================================
THIS IS VERY IMPORTANT FEATURE
========================================================================

Safe iteration during concurrent modification.

========================================================================
WHY NO ConcurrentModificationException?
========================================================================

Because readers iterate over:
immutable snapshot copy.

========================================================================

Original array not modified directly.

========================================================================
VERY IMPORTANT PERFORMANCE UNDERSTANDING
========================================================================

READS
------
Very fast

========================================================================

WRITES
-------
Expensive

========================================================================

Because:
full array copied every modification.

========================================================================
BEST USE CASE
========================================================================

Frequent reads
        +
Very few writes

========================================================================

Examples:
- configuration lists
- subscriber lists
- listener collections

========================================================================
BAD USE CASE
========================================================================

Frequent writes.

========================================================================

Because copying array repeatedly expensive.

========================================================================
COPYONWRITEARRAYLIST vs SYNCHRONIZEDLIST
========================================================================

synchronizedList
-----------------
Uses locking

========================================================================

CopyOnWriteArrayList
---------------------
Uses array copying

========================================================================

synchronizedList
-----------------
Iteration may still need external synchronization

========================================================================

CopyOnWriteArrayList
---------------------
Safe iteration automatically

========================================================================

synchronizedList
-----------------
Better for writes

========================================================================

CopyOnWriteArrayList
---------------------
Better for reads

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

CopyOnWriteArrayList iterators are:
        fail-safe

========================================================================

NOT:
        fail-fast

========================================================================
FAIL-FAST vs FAIL-SAFE
========================================================================

FAIL-FAST
-----------
Throws ConcurrentModificationException

========================================================================

FAIL-SAFE
-----------
Works safely on copied snapshot

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
people reading newspaper copy.

========================================================================

Editor updates master newspaper.

========================================================================

Readers continue reading old copy safely.

========================================================================

Exactly same concept.

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

1. Event listeners
2. Subscriber systems
3. Read-heavy applications
4. Configuration storage

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Thread-safe
2. Safe iteration
3. No ConcurrentModificationException
4. Fast reads
5. No external synchronization needed

========================================================================
IMPORTANT LIMITATION
========================================================================

Writes expensive due to:
full array copying.

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. Why CopyOnWriteArrayList introduced?

→ To provide thread-safe safe-iteration list.

========================================================================

2. How CopyOnWriteArrayList works internally?

→ Creates new array copy during modification.

========================================================================

3. Why no ConcurrentModificationException occurs?

→ Iterator works on snapshot copy.

========================================================================

4. Best use case of CopyOnWriteArrayList?

→ Frequent reads and fewer writes.

========================================================================

5. Are writes expensive?

→ Yes

========================================================================

6. What type of iterator used?

→ Fail-safe iterator.

========================================================================

7. Difference between fail-fast and fail-safe?

Fail-fast
-----------
Throws exception on modification

Fail-safe
-----------
Works on snapshot copy safely

========================================================================

MOST IMPORTANT INTERVIEW LINE

CopyOnWriteArrayList provides
thread-safe iteration
by creating a new array copy
during every modification.

========================================================================

*/