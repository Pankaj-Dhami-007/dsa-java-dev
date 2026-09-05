package multithreading.thread_methods;

public class ThreadInformation {
    static void main() {

        // Returns the currently executing thread
        Thread currentThread = Thread.currentThread();

        // Returns the name of the thread
        System.out.println("Thread Name: " + currentThread.getName());

        // Changes the name of the thread
        currentThread.setName("Main-Worker");

        // Returns the updated thread name
        System.out.println("Updated Thread Name: " + currentThread.getName());

        // Returns the unique ID of the thread
        System.out.println("Thread ID: " + currentThread.getId());

        // Returns the current state of the thread
        System.out.println("Thread State: " + currentThread.getState());

        // Checks whether the thread is still alive
        System.out.println("Is Alive: " + currentThread.isAlive());
    }
}