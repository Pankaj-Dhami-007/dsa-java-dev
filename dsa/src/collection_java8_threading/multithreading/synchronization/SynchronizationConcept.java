package EngineeringDigest.multithreading.synchronization;

public class SynchronizationConcept {

    public static void main(String[] args) {

    }
}

/*

========================================================================
                        SYNCHRONIZATION
========================================================================

Synchronization is one of the MOST IMPORTANT concepts
in multithreading.

Without synchronization:
- data corruption
- inconsistent output
- race conditions
- thread interference

can happen.

========================================================================
WHY SYNCHRONIZATION EXISTS?
========================================================================

In multithreading,
multiple threads share same resources.

Example:
- shared variable
- shared object
- shared list
- shared file
- shared database connection

========================================================================

Suppose:

        count = 0

Two threads increment count simultaneously.

========================================================================

Thread-1 reads:
        count = 0

Thread-2 also reads:
        count = 0

Thread-1 updates:
        count = 1

Thread-2 updates:
        count = 1

========================================================================

EXPECTED:
        2

ACTUAL:
        1

This problem is called:

========================================================================
                        RACE CONDITION
========================================================================

Race condition occurs when:
multiple threads access and modify shared data
simultaneously causing inconsistent result.

========================================================================
WHAT IS SYNCHRONIZATION?
========================================================================

Synchronization is mechanism that controls
access to shared resources so that
only one thread can access critical section
at a time.

========================================================================

SIMPLE DEFINITION
========================================================================

Synchronization ensures thread-safe access
to shared resources.

========================================================================

VERY IMPORTANT TERM
========================================================================

Critical Section
----------------
Part of code where shared resource is accessed.

========================================================================

Example

count++;

This is critical section if multiple threads use it.

========================================================================
GOAL OF SYNCHRONIZATION
========================================================================

1. Prevent race condition
2. Prevent data inconsistency
3. Ensure thread safety
4. Control shared resource access

========================================================================
THREAD SAFETY
========================================================================

Thread-safe means:
multiple threads can access code safely
without data corruption.

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
one bathroom
multiple people

If all enter together:
        chaos

Lock system allows:
        one person at a time

Synchronization works similarly.

========================================================================
HOW SYNCHRONIZATION WORKS INTERNALLY?
========================================================================

Java uses:
        monitor lock / intrinsic lock

Every object in Java has:
        one intrinsic lock

========================================================================

When thread enters synchronized code:

1. Thread acquires lock
2. Other threads wait
3. Thread finishes
4. Lock released
5. Next thread gets chance

========================================================================
IMPORTANT RULE
========================================================================

At one time,
only ONE thread can hold monitor lock.

========================================================================
HOW TO ACHIEVE SYNCHRONIZATION IN JAVA?
========================================================================

MAIN WAYS:

1. synchronized method
2. synchronized block
3. static synchronization
4. Lock interface
5. Atomic classes

========================================================================
1. synchronized METHOD
========================================================================

Locks entire method.

========================================================================

Example

        synchronized void show() {

        }

        ==
        void show() {
           synchronized (this) {
    }
}

========================================================================

Lock acquired on:
        current object

"Before entering this method, take the key of this object."
If another thread already has the key, wait.

========================================================================
2. synchronized BLOCK
========================================================================

Locks only specific part of code.

========================================================================

Example

        synchronized(this) {

        }

========================================================================

Better than synchronized method
because smaller critical section.

========================================================================
3. static SYNCHRONIZATION
========================================================================

Locks class-level lock.

========================================================================

Example

        synchronized static void test() {

        }

        or
        static void test() {

              synchronized (Test.class) {

          }
}

        he lock is NOT taken on an object (this).
        Because static methods belong to the class, not to individual objects.
        So Java locks the Class object.
        Test.class   is the lock object.

========================================================================

Lock acquired on:
        Class object

What is Test.class ?
Java creates one special object for every class  -> Test.class  is an object of type:   Class
Internally:  Class<Test>   There is only ONE class object per class in JVM.

JVM
 ├── Object t1
 ├── Object t2
 └── Class object Test.class   ← single shared object


 Difference

 Non-static synchronized -> (each object has separate lock)
 Static synchronized  -> (one shared lock for whole class)



========================================================================
4. LOCK INTERFACE
========================================================================

From:
        java.util.concurrent.locks

Example:
        ReentrantLock

Provides:
- manual locking
- tryLock()
- fairness
- advanced control

========================================================================
5. ATOMIC CLASSES
========================================================================

Examples:
- AtomicInteger
- AtomicLong

Used for:
thread-safe operations without synchronized.

========================================================================
IMPORTANT CONCEPT
========================================================================

Mutual Exclusion
----------------
Only one thread accesses critical section at a time.

Synchronization mainly provides:
        Mutual Exclusion

========================================================================
PROBLEMS WITHOUT SYNCHRONIZATION
========================================================================

1. Race Condition
2. Data Inconsistency
3. Thread Interference
4. Unpredictable Results

========================================================================
PROBLEMS WITH SYNCHRONIZATION
========================================================================

Too much synchronization may cause:

1. Deadlock
2. Performance issues
3. Thread starvation

========================================================================
DEADLOCK
========================================================================

Thread-1 waiting for Thread-2 lock
and Thread-2 waiting for Thread-1 lock.

Both wait forever.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Synchronization improves:
        safety

But reduces:
        performance

Because threads wait for lock.

========================================================================
WHEN SYNCHRONIZATION NEEDED?
========================================================================

Needed when:
multiple threads access shared mutable data.

========================================================================

NOT needed when:
- local variables
- immutable objects
- thread-local data

========================================================================
SHARED RESOURCE EXAMPLES
========================================================================

1. Bank account balance
2. Ticket booking system
3. Inventory management
4. Shared counter
5. Shared collection

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What is synchronization?

→ Mechanism to control access to shared resources.

========================================================================

2. Why synchronization needed?

→ To prevent race condition and data inconsistency.

========================================================================

3. What is race condition?

→ Multiple threads modifying shared data simultaneously
causing incorrect results.

========================================================================

4. What is thread safety?

→ Safe execution in multithreaded environment.

========================================================================

5. What is critical section?

→ Code accessing shared resource.

========================================================================

6. How many ways to achieve synchronization?

→
1. synchronized method
2. synchronized block
3. static synchronization
4. Lock interface
5. Atomic classes

========================================================================

7. Which lock used by synchronized keyword?

→ Intrinsic monitor lock.

========================================================================

8. Can two threads execute synchronized method simultaneously?

→ No (for same object lock)

========================================================================

9. Difference between synchronized method and block?

Method
-------
Locks complete method

Block
------
Locks specific code section

========================================================================

10. What is mutual exclusion?

→ Only one thread accesses critical section at a time.

========================================================================

11. What problems can synchronization cause?

→ Deadlock and performance overhead.

========================================================================

12. Which is better:
synchronized method or synchronized block?

→ synchronized block
because smaller critical section improves performance.

========================================================================

MOST IMPORTANT INTERVIEW LINE

Synchronization is mechanism that ensures
only one thread accesses shared critical resource
at a time to maintain thread safety.

========================================================================

*/