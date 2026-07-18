package EngineeringDigest.collections.queue;

import java.util.Comparator;
import java.util.concurrent.*;

//BlockingQueue simplifies concurrency by automatically handling thread
// coordination using blocking operations instead of manual synchronization.

class Producer implements Runnable {
    private BlockingQueue<Integer> queue;
    private int value = 0;

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Producer produced: " + value);
                queue.put(value++);
                Thread.sleep(1000);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                System.out.println("Producer interrupted");
            }
        }
    }
}


class Consumer implements Runnable {
    private BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Integer value = queue.take();
                System.out.println("Consumer consumed: " + value);
                Thread.sleep(2000);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                System.out.println("Consumer interrupted");
            }
        }
    }
}

public class BockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        // thread-safe queue
        // wait for queue to become non-empty / wait for space
        // simplify concurrency problems like producer-consumer
        // standard queue --> immediately
        // empty --> remove ( no waiting )
        // full --> add ( no waiting )
        // Blocking queue
        // put -->  Blocks if the queue is full until space becomes available
        // take --> Blocks if the queue is empty until an element becomes available
        // offer --> Waits for space to become available, up to the specified timeout

        // A bounded, blocking queue backed by circular array
        // low memory overhead
        // uses a single lock for both enqueue and dequeue operations
        // more threads --> problem

        Thread producer = new Thread(new Producer(queue));
        Thread consumer = new Thread(new Consumer(queue));
        producer.start();
        consumer.start();
        BlockingQueue<Integer> queue1 =  new LinkedBlockingQueue<>();
        // optionally bounded backed by LinkedList
        // Uses two separate locks for enqueue and dequeue operations
        // Higher concurrency between producers and consumers
        BlockingQueue<String> queue2 = new PriorityBlockingQueue<>(11, Comparator.reverseOrder());
        // unbounded
        // Binary Heap as array and can grow dynamically
        // Head is based on their natural ordering or a provided Comparator like priority queue
        // put wont block
        queue2.add("apple");
        queue2.add("banana");
        queue2.add("cherry");
        System.out.println(queue2);
        BlockingQueue<Integer> queue3 =  new SynchronousQueue<>();
        // each insert operation must wait for a corresponding remove operation by another thread and vice versa.
        // it cannot store elements, capacity of at most one element
    }
}


// BLOCKING QUEUE

// Definition:
// Thread-safe queue designed for producer-consumer problems

// Key Feature:
// Automatically handles waiting (blocking)

// Core Methods:

// put(e)   → blocks if queue is FULL
// take()   → blocks if queue is EMPTY

// offer(e) → returns false if full (or waits with timeout)
// poll()   → returns null if empty (or waits with timeout)


// Difference from normal Queue:

// Normal Queue:
// - add/remove → immediate (no waiting)

// BlockingQueue:
// - put/take → wait automatically


// Implementations:

// 1. ArrayBlockingQueue
// - Bounded (fixed size)
// - Backed by circular array
// - Single lock → lower concurrency

// 2. LinkedBlockingQueue
// - Optionally bounded
// - Uses linked nodes
// - Two locks → better concurrency

// 3. PriorityBlockingQueue
// - Unbounded
// - Orders elements by priority (heap)
// - put() never blocks

// 4. SynchronousQueue
// - No storage (capacity = 0)
// - One thread inserts, another must remove immediately
