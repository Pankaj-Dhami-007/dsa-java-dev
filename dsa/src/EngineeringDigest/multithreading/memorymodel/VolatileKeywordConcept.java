package EngineeringDigest.multithreading.memorymodel;

public class VolatileKeywordConcept {
}

/*

========================================================================
                    VOLATILE KEYWORD
========================================================================

Volatile is one of MOST IMPORTANT concepts
in Java multithreading.

========================================================================
WHY VOLATILE EXISTS?
========================================================================

In multithreading,
multiple threads share same memory.

========================================================================

Problem:
one thread changes variable,
but another thread may NOT see updated value.

========================================================================

This is called:
        Visibility Problem

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Each thread may keep:
        local cached copy
of variable.

========================================================================

Meaning:
threads may NOT always read directly
from main memory.

========================================================================
PROBLEM SCENARIO
========================================================================

Thread-1 updates variable:

        flag = true

========================================================================

BUT:
Thread-2 may still see:

        flag = false

========================================================================

because old value cached locally.

========================================================================

THIS IS:
        Stale Data Problem

========================================================================
THIS IS WHY VOLATILE EXISTS
========================================================================

volatile ensures:
all threads see latest updated value.

========================================================================
WHAT IS VOLATILE?
========================================================================

volatile is keyword that guarantees:
        visibility of variable changes
across threads.

========================================================================
SIMPLE DEFINITION
========================================================================

volatile forces threads
to read/write variable directly
from main memory.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Without volatile:
thread may use cached value.

========================================================================

With volatile:
thread always reads latest value
from main memory.

========================================================================
MAIN IDEA
========================================================================

Prevent stale/inconsistent values
between threads.

========================================================================
HOW VOLATILE WORKS?
========================================================================

volatile variable updates:
immediately visible to all threads.

========================================================================

Thread writes value
        ↓
Main memory updated immediately
        ↓
Other threads read latest value

========================================================================
WITHOUT VOLATILE
========================================================================

Thread-1:
        flag = true

========================================================================

Thread-2:
may still read old cached value:
        false

========================================================================
WITH VOLATILE
========================================================================

Thread-1:
        flag = true

========================================================================

Thread-2:
immediately sees:
        true

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

volatile solves:
        VISIBILITY problem

========================================================================

NOT:
        ATOMICITY problem

========================================================================
IMPORTANT LIMITATION
========================================================================

volatile does NOT make operations atomic.

========================================================================

Example:
========================================================================

count++

========================================================================

Still NOT thread-safe.

========================================================================

Because:
read-modify-write happens internally.

========================================================================
VOLATILE ONLY GUARANTEES
========================================================================

1. Visibility
2. Ordering

========================================================================

NOT:
compound operation safety.

========================================================================
WHEN TO USE VOLATILE?
========================================================================

Use when:
- one thread updates variable
- other threads only read variable
- no compound operations

========================================================================
COMMON USE CASES
========================================================================

1. Boolean flags
2. Shutdown signals
3. Status indicators
4. Configuration refresh flags

========================================================================
EXAMPLE
========================================================================

volatile boolean running = true;

========================================================================

One thread changes:
        running = false

========================================================================

Other threads immediately see update.

========================================================================
WHY SYNCHRONIZED NOT ALWAYS USED?
========================================================================

synchronized:
- expensive
- locking overhead exists

========================================================================

volatile lighter and faster
for simple visibility cases.

========================================================================
VOLATILE vs SYNCHRONIZED
========================================================================

VOLATILE
----------
Visibility guarantee only

========================================================================

SYNCHRONIZED
--------------
Visibility + atomicity

========================================================================

VOLATILE
----------
No locking

========================================================================

SYNCHRONIZED
--------------
Uses locking

========================================================================

VOLATILE
----------
Lightweight

========================================================================

SYNCHRONIZED
--------------
Heavier

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

All synchronized blocks also provide:
        visibility guarantee

========================================================================

Because monitor lock flushes memory.

========================================================================

But volatile does NOT provide:
        mutual exclusion

========================================================================
IMPORTANT INTERNAL CONCEPT
========================================================================

volatile prevents:
        CPU cache inconsistency

========================================================================

Forces:
        memory synchronization

========================================================================
REAL LIFE ANALOGY
========================================================================

Without volatile:
workers use old notice copy.

========================================================================

With volatile:
everyone checks latest central notice board.

========================================================================
VERY IMPORTANT LIMITATION
========================================================================

volatile NOT suitable for:
- counter increment
- banking transactions
- compound updates

========================================================================

Need:
- synchronized
OR
- Atomic classes

========================================================================
VOLATILE vs ATOMICINTEGER
========================================================================

volatile
----------
Visibility only

========================================================================

AtomicInteger
--------------
Visibility + atomic operations

========================================================================
REAL-WORLD USE CASES
========================================================================

1. Application shutdown flags
2. Feature toggles
3. Cache refresh indicators
4. Background thread control

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. Why volatile keyword used?

→ To solve visibility problem between threads.

========================================================================

2. What problem volatile solves?

→ Stale data / visibility issue.

========================================================================

3. Does volatile provide thread safety?

→ Partially, only visibility.

========================================================================

4. Does volatile provide atomicity?

→ No

========================================================================

5. Is count++ thread-safe with volatile?

→ No

========================================================================

6. Difference between volatile and synchronized?

volatile
----------
Visibility only

synchronized
--------------
Visibility + atomicity

========================================================================

7. Does volatile use locking?

→ No

========================================================================

8. Best use case of volatile?

→ Shared boolean flags.

========================================================================

9. Which problem caused by CPU cache?

→ Visibility problem.

========================================================================

MOST IMPORTANT INTERVIEW LINE

volatile guarantees visibility
of variable updates across threads
but does not provide atomicity.

========================================================================

*/