package interviewQ.sortingObjectInJava;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Book {
    private String title;
    private String author;
    private double price;

    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return title + " - " + author + " - $" + price;
    }
}

class AuthorComparator implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        return b1.getAuthor().compareTo(b2.getAuthor());
    }
}

class TitleComparator implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        return b1.getTitle().compareTo(b2.getTitle());
    }
}

class PriceComparator implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        return Double.compare(b1.getPrice(), b2.getPrice());
    }
}
public class SortingUsingComparatorInterface {
    public static void main(String[] args) {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Java Basics", "Alice", 500));
        books.add(new Book("Spring Boot", "Bob", 700));
        books.add(new Book("Java Basics", "Alice", 450));
        books.add(new Book("Hibernate", "Alice", 600));
        books.add(new Book("Spring Boot", "Alice", 700));

        // Step 1: sort by price
        Collections.sort(books, new PriceComparator());
        // Step 2: sort by title
        Collections.sort(books, new TitleComparator());
        // Step 3: sort by author
        Collections.sort(books, new AuthorComparator());

        // Print sorted list
        for (Book b : books) {
            System.out.println(b);
        }

    }
}


/*
    books.sort(
            Comparator.comparing(Book::getAuthor)          // first by author
                      .thenComparing(Book::getTitle)      // then by title
                      .thenComparingDouble(Book::getPrice) // then by price
        );
        
        
        or
        
    // Multi-level sorting using lambda
        books.sort((b1, b2) -> {
            // First compare by author
            int authorCompare = b1.getAuthor().compareTo(b2.getAuthor());
            if (authorCompare != 0) return authorCompare;

            // Then compare by title
            int titleCompare = b1.getTitle().compareTo(b2.getTitle());
            if (titleCompare != 0) return titleCompare;

            // Finally compare by price
            return Double.compare(b1.getPrice(), b2.getPrice());
        });
 */
