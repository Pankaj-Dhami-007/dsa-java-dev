package EngineeringDigest.collections.map;

import java.lang.ref.WeakReference;

// sonarLint
public class GC {
    public static void main(String[] args) {

//        Phone phone = new Phone("Apple", "16 pro max");
//        // here phone -> Strong reference
//        System.out.println(phone);
//        phone = null;// still memory allocated , so have to destroy this obj (jvm auto)
//        System.out.println(phone);
        WeakReference<Phone> phoneWeakReference = new WeakReference<>(new Phone("Apple", "16 pro max"));
        System.out.println(phoneWeakReference.get());
        System.gc();
        try {
            Thread.sleep(10000);
        } catch (Exception ignored) {
        }
        System.out.println(phoneWeakReference.get());

    }
}

class Phone {
    String brand;
    String model;

    public Phone(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
