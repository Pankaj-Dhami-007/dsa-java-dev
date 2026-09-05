package EngineeringDigest.multithreading.synchronization;

public class LockConcept {

}

/*

========================================================================
                            LOCK CONCEPT
========================================================================

Lock is one of the core concepts of synchronization.

In multithreading,
multiple threads access shared resources.

To prevent:
- race condition
- data inconsistency
- thread interference

Java uses:
        Locks

========================================================================
WHAT IS LOCK?
========================================================================

Lock is mechanism that allows
only one thread to access critical section
at a time.

========================================================================

SIMPLE DEFINITION
========================================================================

Lock controls access to shared resource
in multithreaded environment.

========================================================================
WHY LOCK EXISTS?
========================================================================

Suppose:

        balance = 1000

Two threads try to withdraw money simultaneously.

Without lock:
- both threads modify balance together
- incorrect balance may occur

========================================================================

Lock ensures:
only one thread modifies shared data at one time.

========================================================================
IMPORTANT TERM
========================================================================

Critical Section
----------------
Code section accessing shared resource.

========================================================================

Example:
        count++
        balance--
        list.add()

========================================================================

These operations are NOT thread-safe.

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
one bathroom
many people

Lock system allows:
        only one person enters.

Others wait.

Same happens in synchronization.

========================================================================
HOW LOCK WORKS INTERNALLY?
========================================================================

Thread enters synchronized code
        ↓
thread acquires lock
        ↓
other threads blocked
        ↓
thread finishes
        ↓
lock released
        ↓
next thread acquires lock

========================================================================
IMPORTANT RULE
========================================================================

At one time,
only ONE thread can hold lock.

========================================================================
TYPES OF LOCKS IN JAVA
========================================================================

1. Intrinsic Lock / Monitor Lock
2. Explicit Lock
3. Reentrant Lock
4. Read Lock
5. Write Lock
6. Fair Lock
7. Optimistic Lock
8. Pessimistic Lock

========================================================================
1. INTRINSIC LOCK
========================================================================

Built-in lock provided by Java.

Used with:
        synchronized keyword

========================================================================

Every object in Java has:
        one intrinsic lock

========================================================================

Example:

        synchronized(obj) {

        }

========================================================================

Lock belongs to:
        obj object

========================================================================

ALSO CALLED
========================================================================

- Monitor Lock
- Object Lock

========================================================================
2. EXPLICIT LOCK
========================================================================

Provided by:
        java.util.concurrent.locks

Main interface:
        Lock

========================================================================

Example implementations:
- ReentrantLock
- ReadWriteLock

========================================================================

ADVANTAGES
========================================================================

More control than synchronized:
- manual locking
- tryLock()
- fairness
- interruptible locking

========================================================================
3. REENTRANT LOCK
========================================================================

Most commonly used explicit lock.

Provided by:
        ReentrantLock

========================================================================

REENTRANT MEANING
========================================================================

Same thread can acquire same lock multiple times.

========================================================================

Example scenario:
method A()
calls method B()

Both require same lock.

Thread can re-enter lock safely.

========================================================================

IMPORTANT
========================================================================

synchronized is also reentrant internally.

========================================================================
4. READ LOCK
========================================================================

Multiple threads can READ simultaneously.

========================================================================

Used when:
- read operations are frequent
- write operations are less

========================================================================

Improves performance.

========================================================================
5. WRITE LOCK
========================================================================

Only one thread can WRITE at a time.

During write:
- no read allowed
- no other write allowed

========================================================================
6. FAIR LOCK
========================================================================

Lock given in:
        First Come First Serve order

========================================================================

Prevents:
        Thread Starvation

========================================================================

IMPORTANT
========================================================================

Fair locks may reduce performance.

========================================================================
7. OPTIMISTIC LOCK
========================================================================

Assumes conflict is rare.

No immediate locking.

Checks conflict before update.

========================================================================

Commonly used in:
- databases
- JPA/Hibernate

========================================================================
8. PESSIMISTIC LOCK
========================================================================

Assumes conflict WILL happen.

Immediately locks resource.

========================================================================

Used in:
- banking systems
- transaction systems

========================================================================
OBJECT LOCK vs CLASS LOCK
========================================================================

OBJECT LOCK
------------
Each object has separate lock.

========================================================================

CLASS LOCK
-----------
Static synchronized methods use:
        Class-level lock

========================================================================

Example:
        ClassName.class

========================================================================
HOW TO ACHIEVE LOCKING IN JAVA?
========================================================================

1. synchronized method
2. synchronized block
3. Lock interface
4. Atomic classes
5. Concurrent collections

========================================================================
WHEN LOCK IS REQUIRED?
========================================================================

Needed when:
multiple threads access shared mutable data.

========================================================================

NOT needed for:
- local variables
- immutable objects
- thread-local data

========================================================================
IMPORTANT CONCEPT
========================================================================

Mutual Exclusion
----------------
Only one thread enters critical section at one time.

Locks mainly provide:
        Mutual Exclusion

========================================================================
PROBLEMS WITHOUT LOCK
========================================================================

1. Race Condition
2. Data Corruption
3. Inconsistent Output
4. Thread Interference

========================================================================
PROBLEMS WITH LOCKS
========================================================================

1. Deadlock
2. Starvation
3. Performance overhead
4. Reduced concurrency

========================================================================
DEADLOCK
========================================================================

Thread-1 waiting for Thread-2 lock
and Thread-2 waiting for Thread-1 lock.

Both wait forever.

========================================================================
STARVATION
========================================================================

One thread never gets CPU/lock.

========================================================================
LIVELock
========================================================================

Threads continuously respond to each other
but no progress happens.

========================================================================
IMPORTANT DIFFERENCE
========================================================================

synchronized
--------------
Automatic lock management.

Lock Interface
---------------
Manual lock management.

========================================================================

synchronized
--------------
Simple

Lock Interface
---------------
Flexible and powerful

========================================================================
USE CASES OF LOCK
========================================================================

1. Banking system
2. Ticket booking
3. Inventory management
4. Shared cache
5. Database transactions
6. Payment systems
7. Logging systems

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What is lock in Java?

→ Mechanism controlling access to shared resources.

========================================================================

2. Why lock needed?

→ To prevent race condition and ensure thread safety.

========================================================================

3. What is intrinsic lock?

→ Built-in object monitor lock used by synchronized.

========================================================================

4. Difference between synchronized and Lock interface?

synchronized
--------------
Implicit locking

Lock
-----
Explicit/manual locking

========================================================================

5. What is reentrant lock?

→ Same thread can acquire same lock multiple times.

========================================================================

6. What is mutual exclusion?

→ Only one thread accesses critical section at a time.

========================================================================

7. Difference between object lock and class lock?

Object Lock
------------
Per object

Class Lock
-----------
Per class

========================================================================

8. What is deadlock?

→ Two or more threads waiting forever for each other's locks.

========================================================================

9. What is starvation?

→ Thread never gets chance to execute.

========================================================================

10. Which package contains Lock interface?

→ java.util.concurrent.locks

========================================================================

11. Is synchronized reentrant?

→ Yes

========================================================================

12. Which lock improves read-heavy performance?

→ ReadWriteLock

========================================================================

13. What is fair lock?

→ Lock granted in request order.

========================================================================

MOST IMPORTANT INTERVIEW LINE

Lock is synchronization mechanism
that ensures controlled and thread-safe
access to shared resources.

========================================================================

*/