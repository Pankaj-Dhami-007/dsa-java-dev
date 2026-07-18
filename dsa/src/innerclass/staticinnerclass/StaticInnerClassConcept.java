package innerclass.staticinnerclass;

public class StaticInnerClassConcept {

    /*
        ==========================================================
        STATIC INNER CLASS
        ==========================================================

        VERY IMPORTANT CORE JAVA CONCEPT.

        Also heavily used in:
        - Builder Pattern
        - Collections Framework
        - Spring
        - Hibernate
        - Enterprise applications

     */



    /*
        ==========================================================
        WHAT IS STATIC INNER CLASS?
        ==========================================================

        A class declared inside another class
        using static keyword.

        Example:

            class Outer {

                static class Inner {

                }

            }

     */



    /*
        ==========================================================
        SIMPLE UNDERSTANDING
        ==========================================================

        Static Inner Class belongs to:

            OUTER CLASS

        NOT outer object.

     */



    /*
        ==========================================================
        MAIN DIFFERENCE
        ==========================================================

        NON-STATIC INNER CLASS:
            needs outer object

        STATIC INNER CLASS:
            does NOT need outer object

     */



    /*
        ==========================================================
        WHY STATIC INNER CLASS CREATED?
        ==========================================================

        Sometimes inner class logically belongs
        to outer class

        BUT does not need outer object data.

        Then static inner class is best.

     */



    /*
        ==========================================================
        REAL LIFE ANALOGY
        ==========================================================

        Think:

        Car company

        Inside company:
            EngineSpecification class

        Specification belongs to company logically.

        But specification does not need
        actual car object.

        So:
            static inner class

     */



    /*
        ==========================================================
        OUTER CLASS VARIABLES
        ==========================================================
     */

    private int normalVariable = 100;

    private static int staticVariable = 200;



    /*
        ==========================================================
        STATIC INNER CLASS
        ==========================================================
     */
    static class StaticInner {

        public void display() {

            System.out.println("Inside Static Inner Class");



            /*
                IMPORTANT:

                Static inner class CANNOT directly access:

                    normalVariable

                WHY?

                Because normalVariable belongs to object.

                But static inner class has NO outer object.
             */



            /*
                ALLOWED:

                static members of outer class.
             */
            System.out.println(staticVariable);

        }

    }



    /*
        ==========================================================
        NON STATIC INNER CLASS
        ==========================================================
     */
    class NonStaticInner {

        public void show() {

            System.out.println("Inside Non Static Inner");



            /*
                NON-STATIC inner class CAN access:

                - normal variables
                - static variables

                WHY?

                Because it is connected
                to outer object.
             */
            System.out.println(normalVariable);

            System.out.println(staticVariable);

        }

    }



    public static void main(String[] args) {

        /*
            ======================================================
            STATIC INNER CLASS OBJECT CREATION
            ======================================================

            NO outer object needed.
         */
        StaticInner inner1 =
                new StaticInner();



        inner1.display();



        /*
            ======================================================
            NON STATIC INNER CLASS OBJECT CREATION
            ======================================================

            Outer object REQUIRED.
         */

        StaticInnerClassConcept outer =
                new StaticInnerClassConcept();



        NonStaticInner inner2 =
                outer.new NonStaticInner();



        inner2.show();



        /*
            OUTPUT:

            Inside Static Inner Class
            200

            Inside Non Static Inner
            100
            200

         */



        /*
            ======================================================
            MAIN UNDERSTANDING
            ======================================================

            STATIC INNER CLASS:

            Connected with class level.

            NON STATIC INNER CLASS:

            Connected with object level.

         */



        /*
            ======================================================
            MEMORY UNDERSTANDING
            ======================================================

            NON STATIC INNER CLASS:

            Internally stores hidden reference
            of outer object.

            Means:
            more memory usage.

            ------------------------------------------------------

            STATIC INNER CLASS:

            No hidden outer object reference.

            More memory efficient.

         */



        /*
            ======================================================
            WHY BUILDER PATTERN USES
            STATIC INNER CLASS?
            ======================================================

            Builder logically belongs to User class.

            BUT Builder does NOT need
            existing User object.

            Builder creates User object.

            So:

                static class Builder

            PERFECT choice.

         */



        /*
            ======================================================
            BUILDER PATTERN EXAMPLE
            ======================================================

            class User {

                static class Builder {

                }

            }

            Why static?

            Because:

            You can directly do:

                new User.Builder()

            WITHOUT:

                new User().new Builder()

         */



        /*
            ======================================================
            IF BUILDER WAS NON STATIC
            ======================================================

            BAD DESIGN:

                User user = new User();

                User.Builder builder =
                        user.new Builder();

            Problem:

            Need User object before Builder.

            But Builder itself responsible
            for creating User object.

            Circular problem.

         */



        /*
            ======================================================
            REAL WORLD USAGE
            ======================================================

            VERY heavily used.

            Examples:

            1. Builder Pattern

            2. Collections Framework

            3. Map.Entry

            4. Helper classes

            5. DTO Builders

            6. Parser utilities

         */



        /*
            ======================================================
            STATIC INNER CLASS ADVANTAGES
            ======================================================

            - memory efficient
            - no outer object dependency
            - better encapsulation
            - logically grouped classes
            - cleaner code

         */



        /*
            ======================================================
            NON STATIC INNER CLASS ADVANTAGES
            ======================================================

            - directly accesses outer object state
            - tightly connected behaviors

         */



        /*
            ======================================================
            WHEN TO USE STATIC INNER CLASS?
            ======================================================

            Use when:

            - inner class does not need outer object
            - helper class needed
            - builder pattern
            - utility grouping
            - memory optimization

         */



        /*
            ======================================================
            WHEN TO USE NON STATIC INNER CLASS?
            ======================================================

            Use when:

            inner class heavily depends
            on outer object state.

         */



        /*
            ======================================================
            REAL LIFE EXAMPLE
            ======================================================

            Company class

            --------------------------------------------------

            static inner class:

                Company.Policy

            Policies belong to company,
            not specific employee object.

            --------------------------------------------------

            non static inner class:

                Employee.Address

            Address belongs to specific employee object.

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Can static inner class access
            non-static outer variables?

            Answer:
            No directly.



            Q2:
            Why?

            Answer:
            Because no outer object exists.



            Q3:
            Can non-static inner class access
            outer variables?

            Answer:
            Yes.



            Q4:
            Why Builder class static?

            Answer:
            Builder does not need outer object.



            Q5:
            Which is more memory efficient?

            Answer:
            Static inner class.



            Q6:
            Does non-static inner class store
            outer object reference?

            Answer:
            Yes.



            Q7:
            Object creation syntax difference?

            Answer:

            Static:
                new Outer.Inner()

            Non-static:
                outer.new Inner()



            Q8:
            Which one tightly coupled with object?

            Answer:
            Non-static inner class.

         */



        /*
            ======================================================
            IS STATIC INNER CLASS COMPLETE?
            ======================================================

            YES BASIC TO INTERMEDIATE COMPLETE.

            Still advanced topics possible:

            - anonymous inner class
            - local inner class
            - lambda relation
            - bytecode internals
            - hidden outer reference internally
            - JVM generated class files

         */

    }

}