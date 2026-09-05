package multithreading.thread_methods;

public class IsInterruptedVsInterrupted {

    static void main() {

        Thread t1 = new Thread(() -> {

            // Checks current thread's interrupt status and clears it
            System.out.println("interrupted(): " + Thread.interrupted());

            // Checks interrupt status without clearing it
            System.out.println("isInterrupted(): " + Thread.currentThread().isInterrupted());
        });

        t1.start();
    }
}