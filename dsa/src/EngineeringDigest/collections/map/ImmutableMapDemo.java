package EngineeringDigest.collections.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ImmutableMapDemo {

    public static void main(String[] args) {

        // Immutable vs Unmodifiable Map:

// 1. Collections.unmodifiableMap(map)
// → Returns a read-only VIEW of original map
// → Changes in original map are reflected
// → Not truly immutable

// 2. Map.of() / Map.ofEntries()
// → Creates truly immutable map
// → Cannot modify after creation
// → No null key/value allowed

// Key Differences:
// unmodifiableMap → wrapper (backed by original map)
// Map.of → independent immutable map

// Exceptions:
// put(), remove() → UnsupportedOperationException

// When to use:
// unmodifiableMap → expose read-only view
// Map.of → fixed data, constants

        Map<String, Integer> map1 = new HashMap<>();
        map1.put("A", 1);
        map1.put("B", 2);
        Map<String, Integer> map2 = Collections.unmodifiableMap(map1);
        System.out.println(map2);
//        map2.put("C", 3); throws exception
        Map<String, Integer> map3 = Map.of("Shubham", 98, "Vivek", 89);
        map3.put("Akshit", 88);
        Map<String, Integer> map4 = Map.ofEntries(Map.entry("Akshit", 99), Map.entry("Vivek", 99));
    }
}
