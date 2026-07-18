package interviewQ.sortingObjectInJava;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Product implements Comparable<Product> {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    // Comparable: Sort by price (ascending)
    /*
    Purpose: Compare this object with another object (o) to determine order.
     */
    @Override
    public int compareTo(Product other) {
        return Double.compare(this.price, other.price);
        /*
        compareTo for primitive numbers
Primitive types (like int, double) do not have methods.
You cannot do this.price.compareTo(other.price) because price is double, not an object.
So Java provides static helper methods in wrapper classes:
Double.compare(a, b)
Integer.compare(a, b)
         */
    }

//    @Override
//    public int compareTo(Product other) {
//        // Sort by name (alphabetically)
//        return this.name.compareTo(other.name);
    //string.compareTo
//    }

    @Override
    public String toString() {
        return id + " - " + name + " - $" + price;
    }
}

public class SortUsingComparable {

    public static void main(String[] args) {

        List<Product> products = new ArrayList<>();
        products.add(new Product(3, "Laptop", 85000));
        products.add(new Product(1, "Phone", 40000));
        products.add(new Product(2, "Tablet", 25000));

        // Sorting using Comparable (by price)
        Collections.sort(products);

        // Print sorted list
        for (Product p : products) {
            System.out.println(p);
        }
    }
    }

