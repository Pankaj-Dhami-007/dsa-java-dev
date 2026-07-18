package EngineeringDigest.collections.map;

import java.util.EnumMap;
import java.util.Map;

public class EnumMapDemo {
// Special Map designed only for enum keys
    public static void main(String[] args) {

        // EnumMap:
// Internal Working:
// Uses an array internally
// Size = number of enum constants
// Index = enum.ordinal()
// Example:
// Day.TUESDAY.ordinal() = 1 → stored at index 1

// [_,"Gym",_,_,_,_,_]

// No hashing involved → faster than HashMap
// Very memory efficient (no Node objects)

// Key Features:
// Only enum keys allowed
// Maintains natural order of enum (declaration order)
// Not thread-safe

// Performance:
// Faster than HashMap
// O(1) operations (direct array access)

// Null Handling:
// Null key ❌ NOT allowed
// Null value ✅ allowed

// When to use:
// When keys are fixed enum constants
// Example: days, states, directions, roles

// Alternative:
// Instead of HashMap<Day, String>, prefer EnumMap for better performance

        Map<Day, String> map = new EnumMap<>(Day.class);
        map.put(Day.TUESDAY, "Gym");
        map.put(Day.MONDAY, "Walk");
        String s = map.get(Day.TUESDAY);
        System.out.println(map);
    }
}

enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}
