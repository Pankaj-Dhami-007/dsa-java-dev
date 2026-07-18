package EngineeringDigest.collections.queue;

import java.util.PriorityQueue;


// PriorityQueue does NOT sort all elements — it only guarantees that
// the head is the smallest (or highest priority) element.
public class PriorityQueueDemo {
    public static void main(String[] args) {
        // part of the Queue interface
        // orders elements based on their natural ordering (for primitives lowest first)
        // custom comparator for customised ordering
        // does not allow null elements
        PriorityQueue<Integer> pq = new PriorityQueue   <>();
        pq.add(15);
        pq.add(10);
        pq.add(30);
        pq.add(5);
        System.out.println(pq); // not sorted
        while (!pq.isEmpty()){
            System.out.println(pq.poll());
        }

        // internal working
        // PriorityQueue is implemented as a min-heap by default (for natural ordering)
    }
}


// PRIORITY QUEUE

// Definition:
// A queue where elements are ordered based on priority (not FIFO)

// Default behavior:
// Min-heap (smallest element has highest priority)

// Internal structure:
// Binary Heap (array-based)

// Key properties:
// - Does NOT maintain full sorted order
// - Head is always the smallest element (by default)
// - No null elements allowed

// Ordering:
// - Natural ordering (Comparable)
// - Custom ordering (Comparator)

// Time Complexity:
// add()  → O(log n)
// poll() → O(log n)
// peek() → O(1)

// Important:
// Printing PriorityQueue does NOT show sorted order
// Only poll() gives sorted sequence
