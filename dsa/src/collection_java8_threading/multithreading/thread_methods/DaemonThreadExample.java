package multithreading.thread_methods;

// A daemon thread is a background/supporting thread that provides services to user threads.
public class DaemonThreadExample {

    static void main() {

        MyThread2 t1 = new MyThread2();

        // Makes t1 a daemon thread
        t1.setDaemon(true);

        // Checks whether t1 is a daemon thread
        System.out.println("Is Daemon: " + t1.isDaemon());

        t1.start();
    }
}

class MyThread2 extends Thread {

    @Override
    public void run() {
        System.out.println("Daemon thread is running");
    }
}