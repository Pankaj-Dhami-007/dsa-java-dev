package EngineeringDigest.collections.queue;

import java.util.concurrent.ConcurrentLinkedDeque;

// ConcurrentLinkedDeque is a lock-free, high-performance deque designed for concurrent access using CAS,
// making it ideal for non-blocking multi-threaded systems.

public class ConcurrentLinkedDequeDemo {
    public static void main(String[] args) {
        // Deque + Thread-safe + Non-blocking
        // non-blocking, thread-safe double-ended queue
        // CAS
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("Element1");
        deque.addFirst("Element0");
        deque.addLast("Element2");
        System.out.println(deque);

        String first = deque.removeFirst();
        String last = deque.removeLast();
    }
}

// ConcurrentLinkedDeque

// Definition:
// A thread-safe, non-blocking, double-ended queue

// Key Features:
// - Lock-free (uses CAS - Compare-And-Swap)
// - Allows concurrent access from multiple threads
// - Non-blocking → no waiting (unlike BlockingQueue)
// - Supports insertion/removal from both ends

// Internal structure:
// - Doubly linked nodes
// - Uses atomic operations (CAS)

// Methods:
// addFirst(), addLast()
// pollFirst(), pollLast()
// peekFirst(), peekLast()

// Important:
// - Iterators are weakly consistent
// - Allows null? ❌ NO (like most concurrent collections)
