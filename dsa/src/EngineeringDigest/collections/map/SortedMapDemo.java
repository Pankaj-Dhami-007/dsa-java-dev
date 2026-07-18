package EngineeringDigest.collections.map;

import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class SortedMapDemo {

    public static void main(String[] args) {

        /*
        SortedMap  = keys are sorted
NavigableMap = SortedMap + extra navigation features
         */
        // 🔹 SortedMap (Basic sorting)
        SortedMap<Integer, String> map =
                new TreeMap<>((a, b) -> b - a); // descending order

        map.put(91, "Vivek");
        map.put(99, "Shubham");
        map.put(78, "Mohit");
        map.put(77, "Vipul");

        System.out.println("SortedMap: " + map);

        System.out.println("First Key: " + map.firstKey());
        System.out.println("Last Key: " + map.lastKey());

        System.out.println("HeadMap (<91): " + map.headMap(91)); // exclude 91
        System.out.println("TailMap (>=91): " + map.tailMap(91));

        // NavigableMap (Advanced features)
        NavigableMap<Integer, String> navMap = new TreeMap<>();

        navMap.put(1, "One");
        navMap.put(5, "Five");
        navMap.put(3, "Three");

        System.out.println("\nNavigableMap: " + navMap);

        // Navigation methods
        System.out.println("lowerKey(4): " + navMap.lowerKey(4));   // <4
        System.out.println("floorKey(3): " + navMap.floorKey(3));   // <=3
        System.out.println("ceilingKey(3): " + navMap.ceilingKey(3)); // >=3
        System.out.println("higherKey(3): " + navMap.higherKey(3)); // >3

        // Entry methods
        System.out.println("higherEntry(1): " + navMap.higherEntry(1));

        // Reverse order
        System.out.println("Descending Map: " + navMap.descendingMap());

        // Remove first and last
        System.out.println("Poll First: " + navMap.pollFirstEntry());
        System.out.println("Poll Last: " + navMap.pollLastEntry());

        System.out.println("After Poll: " + navMap);
    }
}

/*
Example
SortedMap Use -> Store marks sorted by roll number
NavigableMap Use :
You store prices:
100 → Basic
200 → Standard
500 → Premium

Now user enters 350
navMap.floorKey(350); // 200

Closest plan = Standard
This is why NavigableMap is powerful
 */