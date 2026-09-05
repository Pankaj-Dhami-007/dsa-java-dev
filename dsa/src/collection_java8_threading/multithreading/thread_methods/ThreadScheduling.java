package multithreading.thread_methods;

public class ThreadScheduling {
    static void main() {

        MyThread1 t1 = new MyThread1();

        // Sets the thread priority
        t1.setPriority(Thread.MAX_PRIORITY);

        // Returns the thread priority
        System.out.println("Priority: " + t1.getPriority());

        t1.start();
    }
}

class MyThread1 extends Thread {

    @Override
    public void run() {

        // Hints the scheduler to give other threads a chance to execute
        // yield() = suggestion/hint, not command.
        Thread.yield();

        System.out.println(
                Thread.currentThread().getName() + " is running"
        );
    }
}

// Thread.yield() is a hint to the thread scheduler that the current thread is
// willing to pause and allow other eligible threads to execute; it is not guaranteed.