package java_17.records;


//Records were introduced to reduce boilerplate code
//for immutable data carrier classes.

/*
 * ===============================================================
 * JAVA 17 RECORDS
 * ===============================================================
 *
 * Records were introduced to reduce boilerplate code.
 *
 * Mostly used for:
 * -> DTO
 * -> Response Objects
 * -> Immutable Data
 * -> Request Models
 *
 *
 * BEFORE RECORDS:
 * -----------------------
 * Too much repetitive code:
 *
 * -> fields
 * -> constructor
 * -> getters
 * -> equals()
 * -> hashCode()
 * -> toString()
 *
 *
 * AFTER RECORDS:
 * -----------------------
 * Java automatically generates all of them.
 */

public class RecordsDemo {

    public static void main(String[] args) {

        // =========================================================
        // BEFORE JAVA RECORDS
        // =========================================================

        EmployeeOld emp1 =
                new EmployeeOld(1, "Rahul");

        System.out.println(emp1);

        System.out.println(emp1.getId());

        System.out.println(emp1.getName());




        // =========================================================
        // JAVA 17 RECORD
        // =========================================================

        EmployeeRecord emp2 =
                new EmployeeRecord(1, "Rahul");

        System.out.println(emp2);




        /*
         * In Record:
         *
         * getter methods are:
         *
         * id()
         * name()
         *
         * NOT getId()
         */

        System.out.println(emp2.id());

        System.out.println(emp2.name());




        // =========================================================
        // EQUALS() AUTOMATICALLY GENERATED
        // =========================================================

        EmployeeRecord e1 =
                new EmployeeRecord(1, "Rahul");

        EmployeeRecord e2 =
                new EmployeeRecord(1, "Rahul");

        System.out.println(e1.equals(e2));

        /*
         * Output:
         * true
         */




        // =========================================================
        // IMMUTABILITY
        // =========================================================

        /*
         * Record fields are final internally.
         *
         * Cannot modify:
         *
         * emp2.id = 100; // ERROR
         */




        // =========================================================
        // CUSTOM METHODS INSIDE RECORD
        // =========================================================

        Student student =
                new Student(101, "Priya");

        System.out.println(
                student.getStudentInfo()
        );




        // =========================================================
        // REAL PROJECT USE CASES
        // =========================================================

        /*
         * USE CASE 1
         * API Response DTO
         */

        ApiResponse response =
                new ApiResponse(
                        200,
                        "Success"
                );

        System.out.println(response);




        /*
         * USE CASE 2
         * Spring Boot Request DTO
         */

        /*
         * Example:
         *
         * public record UserRequest(
         *      String name,
         *      String email
         * ){}
         */




        /*
         * USE CASE 3
         * Kafka Event Messages
         */




        /*
         * USE CASE 4
         * Immutable Configuration Objects
         */




        // =========================================================
        // INTERNAL WORKING OF RECORD
        // =========================================================

        /*
         * This:
         *
         * record User(int id, String name){}
         *
         * internally becomes approximately:
         *
         * final class User {
         *
         *   private final int id;
         *   private final String name;
         *
         *   constructor
         *   getters
         *   equals()
         *   hashCode()
         *   toString()
         * }
         */




        // =========================================================
        // IMPORTANT INTERVIEW QUESTIONS
        // =========================================================

        /*
         * Q1 Why Records introduced?
         *
         * To reduce boilerplate code.
         */




        /*
         * Q2 Are Records immutable?
         *
         * Yes.
         *
         * Fields are final internally.
         */




        /*
         * Q3 Can Record extend class?
         *
         * NO
         *
         * Record already extends java.lang.Record
         */




        /*
         * Q4 Can Record implement interface?
         *
         * YES
         */




        /*
         * Q5 Can we add methods in Record?
         *
         * YES
         */




        /*
         * Q6 Difference between POJO and Record?
         *
         * Record:
         * -> immutable
         * -> less code
         * -> auto-generated methods
         */




        /*
         * Q7 Can Record have constructors?
         *
         * YES
         * Compact constructors possible.
         */




        /*
         * Q8 Can we create mutable Record?
         *
         * NO
         *
         * Record designed for immutable data.
         */




        /*
         * Q9 Where should we use Records?
         *
         * DTO
         * API response
         * read-only data
         */




        /*
         * Q10 Where NOT to use Records?
         *
         * JPA Entity classes
         *
         * because entities require mutability.
         */
    }
}




// ===============================================================
// BEFORE JAVA 17
// NORMAL CLASS
// ===============================================================

class EmployeeOld {

    private int id;

    private String name;



    public EmployeeOld(int id, String name) {

        this.id = id;
        this.name = name;
    }



    public int getId() {
        return id;
    }



    public String getName() {
        return name;
    }



    @Override
    public String toString() {
        return "EmployeeOld{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }



    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof EmployeeOld)) {
            return false;
        }

        EmployeeOld other =
                (EmployeeOld) obj;

        return id == other.id &&
                name.equals(other.name);
    }



    @Override
    public int hashCode() {

        return id + name.hashCode();
    }
}




// ===============================================================
// JAVA 17 RECORD
// ===============================================================

record EmployeeRecord(int id, String name) {

}




// ===============================================================
// RECORD WITH CUSTOM METHOD
// ===============================================================

record Student(int id, String name) {

    public String getStudentInfo() {

        return id + " - " + name;
    }
}




// ===============================================================
// REAL PROJECT DTO
// ===============================================================

record ApiResponse(
        int status,
        String message
) {

}