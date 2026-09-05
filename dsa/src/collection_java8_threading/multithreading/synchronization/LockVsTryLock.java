package EngineeringDigest.multithreading.synchronization;

public class LockVsTryLock {

}

/*

========================================================================
                DIFFERENCE BETWEEN lock() vs tryLock()
========================================================================

Suppose Thread-1 already using lock.

Now Thread-2 comes.

========================================================================
lock()
========================================================================

        lock.lock();

Meaning:

        "I will wait here until lock becomes free"

========================================================================

If Thread-1 takes 10 sec,
then Thread-2 waits 10 sec.

Thread-2 cannot do anything else.

========================================================================

REAL LIFE EXAMPLE
========================================================================

Bathroom occupied.

You stand outside and wait forever.

========================================================================

tryLock()
========================================================================

        lock.tryLock();

Meaning:

        "If lock available give me,
         otherwise I will not wait"

========================================================================

If lock busy:
        immediately returns false

Then thread can:
- do other work
- retry later
- skip task

========================================================================

REAL LIFE EXAMPLE
========================================================================

Bathroom occupied.

You check once.

If busy:
        you leave and come later.

========================================================================

MAIN DIFFERENCE
========================================================================

lock()
--------
waits indefinitely

tryLock()
----------
does not wait

========================================================================

INTERVIEW STYLE ANSWER
========================================================================

lock() blocks thread until lock becomes available,
whereas tryLock() tries to acquire lock immediately
and returns false if lock is unavailable instead of waiting.

========================================================================

*/