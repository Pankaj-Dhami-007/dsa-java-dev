package multithreading.thread_methods;

public class UserThreadVsDaemonThread {

    static void main() {

        Thread userThread = new Thread(() -> {
            System.out.println("User thread is running");
        });

        Thread daemonThread = new Thread(() -> {
            System.out.println("Daemon thread is running");
        });

        daemonThread.setDaemon(true);

        userThread.start();
        daemonThread.start();
    }
}