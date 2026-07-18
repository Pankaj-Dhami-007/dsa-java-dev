package EngineeringDigest.collections.set;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

public class CopyOnWriteArraySetDemo {

    public static void main(String[] args) {
        // CopyOnWriteArraySet:

// Thread-safe (uses CopyOnWriteArrayList internally)
// On every write → creates a NEW copy of array
// Read operations are very fast (no locking)
// Iterators are snapshot-based (do NOT reflect modifications)
// No duplicates allowed

// Best for:
// - Read-heavy, write-light scenarios

// Drawback:
// - Very expensive writes (copy whole array)

// ConcurrentSkipListSet:

// Thread-safe (uses ConcurrentSkipListMap internally)
// Sorted set (natural/comparator order)
// Uses skip list (CAS-based, non-blocking)
// Iterators are weakly consistent (may reflect updates)
// Better for frequent writes compared to CopyOnWriteArraySet

        CopyOnWriteArraySet<Integer> copyOnWriteSet = new CopyOnWriteArraySet<>();
        ConcurrentSkipListSet<Integer> concurrentSkipListSet = new ConcurrentSkipListSet<>();

        for (int i = 1; i <= 5; i++) {
            copyOnWriteSet.add(i);
            concurrentSkipListSet.add(i);
        }

        System.out.println("Initial CopyOnWriteArraySet: " + copyOnWriteSet);
        System.out.println("Initial ConcurrentSkipListSet: " + concurrentSkipListSet);

        System.out.println("\nIterating and modifying CopyOnWriteArraySet:");
        for (Integer num : copyOnWriteSet) {
            System.out.println("Reading from CopyOnWriteArraySet: " + num);
            // Attempting to modify the set during iteration
            copyOnWriteSet.add(6);
        }

        System.out.println("\nIterating and modifying ConcurrentSkipListSet:");
        for (Integer num : concurrentSkipListSet) {
            System.out.println("Reading from ConcurrentSkipListSet: " + num);
            // Attempting to modify the set during iteration
            concurrentSkipListSet.add(6);
        }
    }
    /*
    CopyOnWriteArraySet is optimized for read-heavy workloads with snapshot iteration,
    while ConcurrentSkipListSet is designed for concurrent sorted access with better write performance.
     */
}
