package designpattern.creational.prototype;

public class PrototypeDesignPattern {

    /*
        ==========================================================
        PROTOTYPE DESIGN PATTERN
        ==========================================================

        LAST creational design pattern.

        VERY IMPORTANT for:
        - performance optimization
        - expensive object creation
        - game development
        - caching systems
        - frameworks

     */



    /*
        ==========================================================
        MAIN IDEA
        ==========================================================

        Instead of creating object from scratch,

        COPY existing object.

     */



    /*
        ==========================================================
        SIMPLE DEFINITION
        ==========================================================

        Prototype Pattern creates
        duplicate objects by cloning
        existing objects.

     */



    /*
        ==========================================================
        WHY THIS PATTERN NEEDED?
        ==========================================================

        Sometimes object creation is:
        - expensive
        - slow
        - complex

        Example:
        - DB calls
        - network calls
        - huge configuration loading
        - image processing

        Instead of creating new object repeatedly,
        we clone existing object.

     */



    /*
        ==========================================================
        REAL LIFE ANALOGY
        ==========================================================

        Think:

        Photocopy machine.

        Original document already exists.

        Instead of writing everything again,
        we create copy.

        Same concept in Prototype Pattern.

     */



    /*
        ==========================================================
        NORMAL OBJECT CREATION PROBLEM
        ==========================================================

        Suppose Employee object creation takes:

        - DB loading
        - validation
        - API calls
        - configuration setup

        Creating again and again becomes expensive.

     */



    /*
        ==========================================================
        PROTOTYPE SOLUTION
        ==========================================================

        Create one original object.

        Then clone it whenever needed.

     */



    public static void main(String[] args)
            throws CloneNotSupportedException {

        /*
            Original object creation.
         */
        Employee emp1 =
                new Employee(
                        101,
                        "Pankaj",
                        "Backend Developer"
                );



        /*
            Clone existing object.

            No heavy creation logic repeated.
         */
        Employee emp2 =
                (Employee) emp1.clone();



        /*
            Changing cloned object.
         */
        emp2.setName("Rahul");



        System.out.println(emp1);

        System.out.println(emp2);



        /*
            OUTPUT:

            Employee{
                id=101,
                name='Pankaj',
                role='Backend Developer'
            }

            Employee{
                id=101,
                name='Rahul',
                role='Backend Developer'
            }

         */



        /*
            ======================================================
            MAIN UNDERSTANDING
            ======================================================

            emp2 created by COPYING emp1.

            NOT using:
                new Employee()

         */



        /*
            ======================================================
            INTERNAL FLOW
            ======================================================

            STEP-1:
            Original object created.

            STEP-2:
            clone() method called.

            STEP-3:
            JVM creates duplicate object.

            STEP-4:
            New reference returned.

         */



        /*
            ======================================================
            IMPORTANT CONCEPT
            ======================================================

            Prototype Pattern mainly uses:

            CLONING

         */



        /*
            ======================================================
            JAVA SUPPORT
            ======================================================

            Java provides:

                Cloneable interface

            and:

                clone() method

         */



        /*
            ======================================================
            WHY Cloneable INTERFACE?
            ======================================================

            Marker interface.

            It tells JVM:

            "This object cloning is allowed."

         */



        /*
            ======================================================
            IF Cloneable NOT IMPLEMENTED
            ======================================================

            JVM throws:

                CloneNotSupportedException

         */



        /*
            ======================================================
            SHALLOW COPY
            ======================================================

            Current example is:
            SHALLOW COPY

            Primitive/simple values copied.

         */



        /*
            ======================================================
            DEEP COPY
            ======================================================

            If object contains nested objects:

                Address
                Department
                List

            Then we may need:
            DEEP COPY

            Meaning:
            nested objects also cloned.

         */



        /*
            ======================================================
            SHALLOW COPY PROBLEM
            ======================================================

            Nested object references may get shared.

            Changes in one object
            can affect another object.

         */



        /*
            ======================================================
            REAL WORLD USAGE
            ======================================================

            Used in:
            - game engines
            - object caching
            - Hibernate
            - Spring
            - document editors
            - graphics software

         */



        /*
            ======================================================
            GAME DEVELOPMENT EXAMPLE
            ======================================================

            Suppose enemy object creation expensive.

            Instead of:
                new Enemy()

            again and again,

            clone existing enemy template.

         */



        /*
            ======================================================
            SPRING FRAMEWORK
            ======================================================

            Spring supports:
                prototype bean scope

            New cloned-like object created
            for every request.

         */



        /*
            ======================================================
            PERFORMANCE BENEFIT
            ======================================================

            Cloning often faster than
            full object creation.

         */



        /*
            ======================================================
            ADVANTAGES
            ======================================================

            - faster object creation
            - reduces expensive initialization
            - reusable templates
            - improves performance

         */



        /*
            ======================================================
            DISADVANTAGES
            ======================================================

            - cloning complexity
            - deep copy difficult
            - circular reference issues

         */



        /*
            ======================================================
            FACTORY vs BUILDER vs PROTOTYPE
            ======================================================

            Factory:
            creates object directly

            Builder:
            creates step-by-step

            Prototype:
            copies existing object

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Main idea of Prototype Pattern?

            Answer:
            Create objects by cloning existing objects.



            Q2:
            Why use Prototype Pattern?

            Answer:
            Expensive object creation optimization.



            Q3:
            Which Java interface used?

            Answer:
            Cloneable.



            Q4:
            Which method used?

            Answer:
            clone()



            Q5:
            Difference between shallow and deep copy?

            Answer:

            Shallow:
            references shared

            Deep:
            nested objects also copied



            Q6:
            Is clone() method from Object class?

            Answer:
            Yes.



            Q7:
            What happens without Cloneable?

            Answer:
            CloneNotSupportedException



            Q8:
            Real-world analogy?

            Answer:
            Photocopy machine.

         */



        /*
            ======================================================
            IS PROTOTYPE PATTERN COMPLETE?
            ======================================================

            BASIC TO INTERMEDIATE:
            YES COMPLETE.

            CREATIONAL DESIGN PATTERNS NOW COMPLETE.

            Completed:
            - Singleton
            - Factory
            - Factory Method
            - Abstract Factory
            - Builder
            - Prototype

            NEXT MAJOR CATEGORY:

            STRUCTURAL DESIGN PATTERNS

            Includes:
            - Adapter
            - Decorator
            - Facade
            - Proxy
            - Composite
            - Bridge

         */

    }

}



/*
    ==========================================================
    PROTOTYPE CLASS
    ==========================================================

    Cloneable allows object cloning.
 */
class Employee implements Cloneable {

    private int id;

    private String name;

    private String role;



    public Employee(int id,
                    String name,
                    String role) {

        /*
            Suppose object creation expensive.

            Example:
            DB/API/config loading
         */

        System.out.println("Heavy Object Creation");

        this.id = id;

        this.name = name;

        this.role = role;
    }



    /*
        clone() method

        super.clone() creates shallow copy.
     */
    @Override
    protected Object clone()
            throws CloneNotSupportedException {

        return super.clone();
    }



    public void setName(String name) {

        this.name = name;
    }



    @Override
    public String toString() {

        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

}