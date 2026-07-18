package EngineeringDigest.multithreading.lamda;

public class ThreadWithLamda {

    public static void main(String[] args) {

        // ============================================================
        // WITHOUT LAMBDA
        // ============================================================

        Runnable task1 = new Runnable() {

            @Override
            public void run() {

                System.out.println(
                        Thread.currentThread().getName()
                                + " running WITHOUT lambda"
                );
            }
        };

        Thread t1 = new Thread(task1);

        t1.setName("Thread-1");

        t1.start();

        // ============================================================
        // WITH LAMBDA
        // ============================================================

        Runnable task2 = () -> {

            System.out.println(
                    Thread.currentThread().getName()
                            + " running WITH lambda"
            );
        };

        Thread t2 = new Thread(task2);

        t2.setName("Thread-2");

        t2.start();

        // ============================================================
        // DIRECT LAMBDA INSIDE THREAD
        // ============================================================

        Thread t3 = new Thread(() -> {

            System.out.println(
                    Thread.currentThread().getName()
                            + " direct lambda thread"
            );
        });

        t3.setName("Thread-3");

        t3.start();
    }
}

/*

========================================================================
                    LAMBDA WITH THREAD
========================================================================

Before Java 8,
creating Runnable required:
        anonymous inner class

========================================================================

WITHOUT LAMBDA
========================================================================

Runnable task = new Runnable() {

    @Override
    public void run() {

    }
};

========================================================================

PROBLEM
========================================================================

Too much:
- boilerplate code
- verbosity
- unnecessary syntax

========================================================================

Java 8 introduced:
        Lambda Expression

========================================================================
WHAT IS LAMBDA?
========================================================================

Lambda is shorthand way
to implement functional interface.

========================================================================

SIMPLE DEFINITION
========================================================================

Lambda expression provides concise syntax
for implementing single-method interfaces.

========================================================================
FUNCTIONAL INTERFACE
========================================================================

Interface containing:
        only ONE abstract method

========================================================================

Runnable is functional interface because:

        public void run();

only one abstract method exists.

========================================================================

So Runnable supports lambda.

========================================================================
WITHOUT LAMBDA
========================================================================

Runnable task = new Runnable() {

    @Override
    public void run() {

        System.out.println("Hello");
    }
};

========================================================================
WITH LAMBDA
========================================================================

Runnable task = () -> {

    System.out.println("Hello");
};

========================================================================

SAME WORK
========================================================================

Less code.

========================================================================
LAMBDA SYNTAX
========================================================================

(parameters) -> {

        body

}

========================================================================

Runnable run() method has:
        no parameters

So:

        () -> { }

========================================================================
DIRECT THREAD CREATION
========================================================================

Thread t = new Thread(() -> {

    System.out.println("Hello");

});

========================================================================

VERY COMMON IN REAL PROJECTS
========================================================================

Most modern Java multithreading uses:
- lambda
- executor framework
- streams

========================================================================
WHY LAMBDA IMPORTANT?
========================================================================

1. Less code
2. Better readability
3. Cleaner syntax
4. Functional programming support
5. Easier concurrency code

========================================================================
MAIN BENEFIT WITH THREADS
========================================================================

Thread tasks become very concise.

========================================================================

WITHOUT LAMBDA
========================================================================

Too much unnecessary syntax:
- new Runnable()
- @Override
- public void run()

========================================================================

WITH LAMBDA
========================================================================

Only task logic visible.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Lambda does NOT create thread.

========================================================================

Thread still created using:
        Thread class

========================================================================

Lambda only simplifies:
        Runnable implementation

========================================================================
IMPORTANT INTERNAL CONCEPT
========================================================================

This:

        () -> { }

internally behaves like:
        Runnable implementation

========================================================================

JVM converts lambda into functional interface instance.

========================================================================
REAL INDUSTRY USAGE
========================================================================

Used heavily in:
- ExecutorService
- CompletableFuture
- Stream API
- async programming

========================================================================
REAL LIFE ANALOGY
========================================================================

WITHOUT LAMBDA
--------------
Writing full application form.

========================================================================

WITH LAMBDA
------------
Quick digital form.

========================================================================

Same purpose,
less writing.

========================================================================
IMPORTANT LIMITATION
========================================================================

Lambda works only with:
        Functional Interfaces

========================================================================

Examples:
- Runnable
- Callable
- Comparator
- Consumer
- Supplier

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What is lambda expression?

→ Short syntax for functional interface implementation.

========================================================================

2. Which Java version introduced lambda?

→ Java 8

========================================================================

3. Why Runnable supports lambda?

→ Because Runnable is functional interface.

========================================================================

4. Does lambda create thread?

→ No

========================================================================

5. What does lambda simplify here?

→ Runnable implementation.

========================================================================

6. What is functional interface?

→ Interface with one abstract method.

========================================================================

7. Main benefit of lambda with threads?

→ Cleaner and concise code.

========================================================================

8. Can lambda replace Thread class?

→ No

========================================================================

9. Which annotation used for functional interface?

→ @FunctionalInterface

========================================================================

10. Is Runnable functional interface?

→ Yes

========================================================================

MOST IMPORTANT INTERVIEW LINE

Lambda expressions simplify multithreading code
by providing concise implementation
of Runnable functional interface.

========================================================================

*/