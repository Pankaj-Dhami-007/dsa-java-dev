package EngineeringDigest.collections.list.vector;

import java.util.Vector;

// vector --- legacy class that implement list interface
// introduce before collection framework
// synchronized making it thread safe
// use where thread safety is a concern
// dynamic array like list
// random access (arrays , list)
// capacity like arrayList
public class VectorExample {
    public static void main(String[] args) {
        Vector<Integer> vector = new Vector<>(5, 3);//default resize capacity by double
        vector.add(1);
        vector.add(1);
        vector.add(1);
        vector.add(1);
        vector.add(1);
        vector.add(1);
        System.out.println(vector.capacity());

    }
}
