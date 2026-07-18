package EngineeringDigest.collections.map;

import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapDemo {

    /*
    WeakHashMap is a class in Java (java.util) that stores keys as weak references.
    means If a key is no longer strongly referenced anywhere else in your program,
    it becomes eligible for Garbage Collection (GC) — and when that happens,
    the entry is automatically removed from the map.

    WeakHashMap = Map where keys can disappear automatically when they are no longer in use.
    Auto-cleans unused keys

     How It Works
WeakHashMap<Key, Value>
Key is stored as WeakReference
Value is stored as Strong Reference
If key is GC’d → entry is removed

Why Does This Exist?
Cache entries to disappear automatically
Avoid memory leaks

     */

    public static void main(String[] args) {
        WeakHashMap<String, Image> imageCache = new WeakHashMap<>();
        loadCache(imageCache);
        System.out.println(imageCache);
        System.gc();
        simulateApplicationRunning();
        System.out.println("Cache after running (some entries may be cleared): " + imageCache);
    }
    public static void loadCache(Map<String, Image> imageCache) {
        String k1 = new String("img1");
        String k2 = new String("img2");
        imageCache.put(k1, new Image("Image 1"));
        imageCache.put(k2, new Image("Image 2"));
    }
    private static void simulateApplicationRunning() {
        try {
            System.out.println("Simulating application running...");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Image {
    private String name;

    public Image(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
