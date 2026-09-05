package multithreading.thread_methods;

// synchronized (object) { }  → synchronized block
// synchronized void method() { } → synchronized method
public class NotifyVsNotifyAll {

    private static final Object lock = new Object();

    static void main() {

//        Thread t1 = new Thread(() -> waitForSignal(), "Thread-1");
        Thread t1 = new Thread(NotifyVsNotifyAll::waitForSignal, "Thread-1");
        Thread t2 = new Thread(() -> waitForSignal(), "Thread-2");

        t1.start();
        t2.start();

        Thread notifier = new Thread(() -> {
            synchronized (lock) {
                // Wakes up one waiting thread
                lock.notify();

                // Wakes up all waiting threads
                // lock.notifyAll();
            }
        });

        notifier.start();
    }

    static void waitForSignal() {
        synchronized (lock) {
            try {
                System.out.println(Thread.currentThread().getName() + " is waiting");

                lock.wait();

                System.out.println(Thread.currentThread().getName() + " resumed");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

/*

  Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("Thread-1 waiting");
                    lock.wait();
                    System.out.println("Thread-1 resumed");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("Thread-2 waiting");
                    lock.wait();
                    System.out.println("Thread-2 resumed");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread t3 = new Thread(() -> {
            synchronized (lock) {

                // Wake one waiting thread
                lock.notify();

                // Wake all waiting threads
                // lock.notifyAll();
            }
        });

        t1.start();
        t2.start();
        t3.start();

 */