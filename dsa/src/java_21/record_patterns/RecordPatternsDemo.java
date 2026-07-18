package java_21.record_patterns;



/*
Automatically extracting values from a record object into variables.

First Understand Normal Record
record Person(String name, int age) {}

Create object:

Person p = new Person("Pankaj", 25);
WITHOUT Record Pattern (Old Way)

To access values:

String name = p.name();
int age = p.age();

System.out.println(name);
System.out.println(age);
Here:
manually calling getters
manually extracting values

What is Destructuring?

Destructuring means:
Breaking an object into smaller individual values automatically.
Like opening a box and directly taking items out.

WITH Record Pattern (Java 21)
if (p instanceof Person(String name, int age)) {
    System.out.println(name);
    System.out.println(age);
}

Now Java automatically:

checks object type
extracts fields
stores into variables

This automatic extraction = destructuring

Old Style
if (p instanceof Person person) {

    String name = person.name();
    int age = person.age();

    System.out.println(name);
}

You manually extract values.

Record Pattern Style
if (p instanceof Person(String name, int age)) {

    System.out.println(name);
}

Java extracts automatically.

Person(String name, int age)

Means:

If object is Person:
    extract first component into name
    extract second component into age



    Nested Record Pattern (Very Powerful)
record Address(String city) {}
record Employee(String name, Address address) {}

Object:

Employee emp =
    new Employee("Pankaj",
        new Address("Delhi"));

Old style:

String city = emp.address().city();

Record Pattern:

if (emp instanceof Employee(
        String name,
        Address(String city))) {

    System.out.println(city);
}

Java automatically goes inside nested object too.

Very useful.



 */
/*
 * ===============================================================
 * JAVA 21 RECORD PATTERNS
 * ===============================================================
 *
 * PURPOSE:
 * ------------------------------------------------
 *
 * Easier extraction (destructuring)
 * of values from records.
 *
 *
 * BEFORE JAVA 21
 * ------------------------------------------------
 *
 * Needed:
 * -> instanceof
 * -> manual getter calls
 *
 *
 * JAVA 21 SOLUTION
 * ------------------------------------------------
 *
 * Record Patterns
 */

public class RecordPatternsDemo {

    public static void main(String[] args) {

        // =========================================================
        // CREATE RECORD
        // =========================================================

        Person person =
                new Person("Rahul", 25);




        // =========================================================
        // BEFORE JAVA 21
        // =========================================================

        if (person instanceof Person) {
            String name = person.name();
            int age = person.age();

            System.out.println(name);

            System.out.println(age);
        }




        /*
         * Problem:
         *
         * Manual extraction.
         */




        // =========================================================
        // JAVA 21 RECORD PATTERN
        // =========================================================

        if (person instanceof Person(

                String name,
                int age

        )) {

            System.out.println(name);
            System.out.println(age);
        }




        /*
         * Automatically extracts:
         *
         * -> name
         * -> age
         */




        // =========================================================
        // NESTED RECORD PATTERNS
        // =========================================================

        Address address =
                new Address("Delhi");



        Employee employee =
                new Employee(

                        "Priya",
                        address
                );




        if (employee instanceof Employee(

                String empName,

                Address(String city)

        )) {

            System.out.println(empName);

            System.out.println(city);
        }




        /*
         * Deep nested extraction possible.
         */




        // =========================================================
        // SWITCH WITH RECORD PATTERNS
        // =========================================================

        Object obj = person;



        String result = switch (obj) {

            case Person(String name, int age) ->

                    "Person : " + name
                            + " Age : " + age;

            default ->

                    "Unknown";
        };



        System.out.println(result);




        // =========================================================
        // WHY IMPORTANT?
        // =========================================================

        /*
         * Cleaner code
         * Less boilerplate
         * Better readability
         * Functional style
         */




        // =========================================================
        // REAL PROJECT USE CASES
        // =========================================================

        /*
         * USE CASE 1
         * API Response Processing
         */




        /*
         * USE CASE 2
         * Event Processing
         */




        /*
         * USE CASE 3
         * JSON-like object parsing
         */




        /*
         * USE CASE 4
         * DTO extraction
         */




        /*
         * USE CASE 5
         * Pattern-based logic
         */




        // =========================================================
        // INTERVIEW QUESTIONS
        // =========================================================

        /*
         * Q1 What are Record Patterns?
         *
         * Record destructuring feature.
         */




        /*
         * Q2 Why introduced?
         *
         * Simplify record data extraction.
         */




        /*
         * Q3 Main advantage?
         *
         * Less boilerplate getter calls.
         */




        /*
         * Q4 Can nested records be extracted?
         *
         * YES
         */




        /*
         * Q5 Why useful with switch?
         *
         * Cleaner pattern-based logic.
         */
    }
}




// ===============================================================
// RECORDS
// ===============================================================

record Person(
        String name,
        int age
) {

}




record Address(
        String city
) {

}




record Employee(
        String name,
        Address address
) {

}