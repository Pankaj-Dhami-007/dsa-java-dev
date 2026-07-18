package javaDeepDrive.string;

public class ParseVsValueOf {

    public static void main(String[] args) {

        // valueOf()
        // valueOf() is a static method that converts a primitive or a String into a wrapper object.
        // It uses caching (from -128 to 127), so it is more memory efficient than using new.

        // primitive to object
        int x = 10;
        Integer obj1 = Integer.valueOf(x);
        Integer obj2 = Integer.valueOf("20");
        System.out.println(obj1); // 10
        System.out.println(obj2); // 20

        Integer a = Integer.valueOf(100);
        Integer b = Integer.valueOf(100);
        System.out.println(a == b); // true (same object from cache)
        Integer x1 = Integer.valueOf(200);
        Integer y = Integer.valueOf(200);
        System.out.println(x1 == y); // false (new objects)

        // parseInt() vs valueOf()

        // parseInt → returns primitive int
        // "parseInt() returns a primitive int, while valueOf() returns an Integer object and
        // uses caching, so it is more memory efficient."
        // // parseInt() → does NOT accept primitive
        //// so we simulate using literal (still no String variable used)
        int a1 = Integer.parseInt("100"); // required internally as String
        // valueOf → returns Integer object
        Integer b2 = Integer.valueOf("100");
        System.out.println(a1); // 100
        System.out.println(b2); // 100
    }
}
