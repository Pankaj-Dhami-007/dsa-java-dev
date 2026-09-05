package EngineeringDigest.multithreading.basics;

public class HowJvmHandleThread {

    public static void main(String[] args) {

        System.out.println("Hello");

        // returns currently executing thread
        Thread t = Thread.currentThread();

        // thread name
        System.out.println(t.getName());

        // thread priority
        System.out.println(t.getPriority());

        // thread state
        System.out.println(t.getState());
    }
}

/*

======================== HOW JVM HANDLES THREAD ========================

1. When JVM starts a Java program,
   JVM automatically creates one thread.

2. This default thread is called:
        MAIN THREAD

3. Main thread starts execution from:
        public static void main(String[] args)

4. So main() method always runs inside main thread.

5. JVM internally does:

        create main thread
              ↓
        call main() method
              ↓
        execute statements line by line

6. Thread.currentThread()
   returns reference of currently executing thread.

========================================================================

OUTPUT EXAMPLE

Hello
main
5
RUNNABLE

========================================================================

IMPORTANT POINTS

1. Every Java program has at least one thread.
        → main thread

2. JVM controls creation of main thread.

3. CPU actually executes threads.

4. A process can contain multiple threads.

5. Java program execution starts with main thread.

========================================================================

FLOW

JVM Starts
    ↓
Main Thread Created
    ↓
main() Method Called
    ↓
Statements Execute
    ↓
Program Ends

========================================================================

*/