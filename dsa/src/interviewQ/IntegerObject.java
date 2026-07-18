package interviewQ;

public class IntegerObject {
    public static void main(String[] args) {
        Integer a = 100;
        Integer b = 100;
        Integer c = 1000;
        Integer d = 1000;
        String s1 = "hello";
        String s2 = "hello";
        String s3 = new String("hello");
        System.out.println(a == b);
        System.out.println(c == d);
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1.equals(s3));
    }
}
/*
Key Concept: Integer Caching in Java
In Java, Integer objects between -128 and 127 are cached by the JVM.
Integer a = 100;
Integer b = 100;
Both a and b point to the same cached object in memory.
So a == b is true because they reference the same object.
But for numbers outside -128 to 127, like 1000:

Integer c = 1000;
Integer d = 1000;
Java creates new Integer objects each time.
So c and d point to different objects, and c == d is false.

 */