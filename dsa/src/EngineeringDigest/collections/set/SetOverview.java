package EngineeringDigest.collections.set;

import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class SetOverview {

    public static void main(String[] args) {

        // SET (Java Collection)

// Definition:
// A Set is a collection that does NOT allow duplicate elements

// Properties:
// - No duplicates
// - Can contain at most one null (depends on implementation)
// - No index-based access

// Types of Set:

// 1. HashSet
// - Backed by HashMap
// - No order
// - O(1) average performance
// - Allows one null

// 2. LinkedHashSet
// - Maintains insertion order
// - Slightly slower than HashSet

// 3. TreeSet
// - Sorted (natural or comparator)
// - Uses Red-Black Tree
// - O(log n)
// - Does NOT allow null

// 4. EnumSet
// - Only for enum types
// - Internally uses bit vector
// - Very fast and memory efficient

// 5. ConcurrentSkipListSet
// - Thread-safe
// - Sorted
// - Uses skip list (CAS-based)
// - Alternative to TreeSet in multi-threading

// Interfaces:
// Set → SortedSet → NavigableSet

// NavigableSet features:
// - lower(), higher()
// - floor(), ceiling()
// - pollFirst(), pollLast()

// Immutable Set:
// Set.of(...) → truly immutable (Java 9+)
// - No null
// - No duplicates

        NavigableSet<Integer> set = new TreeSet<>();
        set.add(12);
        set.add(1);
        set.add(1);
        set.add(67);
        System.out.println(set);
        System.out.println(set.contains(12));
        System.out.println(set.remove(67));
        set.clear();
        System.out.println(set.isEmpty());
        for(int i: set){
            System.out.println(i);
        }

        // for thread safety

        Set<Integer> set1 =  new ConcurrentSkipListSet<>();

        // unmodifiable

        Set<Integer> integers = Set.of(1, 2, 3,4,5,6,7,8,9,54,4323,545,4545);
    }
}
