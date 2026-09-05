package EngineeringDigest.collections.queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class QueueDemo {

    public static void main(String[] args) {

        // QUEUE (FIFO - First In First Out)

// Core Operations:

// INSERT:
// add(e)   → throws exception if full
// offer(e) → returns false if full

// REMOVE:
// remove() → removes head, throws exception if empty
// poll()   → removes head, returns null if empty

// EXAMINE (peek without removing):
// element() → throws exception if empty
// peek()    → returns null if empty


// Implementations:

// 1. LinkedList
// - Not thread-safe
// - Unbounded queue

// 2. ArrayBlockingQueue
// - Thread-safe
// - Fixed capacity (bounded queue)
// - Used in producer-consumer

        Queue<Integer> queue1 = new LinkedList<>();
        queue1.add(1);
        System.out.println(queue1.size());

        System.out.println(queue1.remove(1)); // throws exception if empty
        System.out.println(queue1.poll());

//        System.out.println(queue1.element());  // throws exception if empty
        System.out.println(queue1.peek());

        Queue<Integer> queue2 =  new ArrayBlockingQueue<>(2);
        System.out.println(queue2.add(1)); // true
        System.out.println(queue2.offer(2)); // true

//         System.out.println(queue2.add(3)); // throws exception
        System.out.println(queue2.offer(3)); // false

    }
}
