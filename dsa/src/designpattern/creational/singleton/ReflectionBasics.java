package designpattern.creational.singleton;

public class ReflectionBasics {

    /*
        =========================================
        REFLECTION IN JAVA
        =========================================

        Reflection means:

        Java can inspect and manipulate
        classes at runtime.

        Runtime means:
        while application is running.

        -----------------------------------------

        Using reflection Java can:

        - access private variables
        - access private methods
        - access private constructors
        - create objects dynamically
        - inspect class metadata

        EVEN private things can be accessed.

     */



    /*
        =========================================
        WHY REFLECTION EXISTS?
        =========================================

        Many frameworks need it.

        Examples:
        - Spring Framework
        - Hibernate
        - JUnit
        - Jackson
        - Mockito

        These frameworks dynamically create objects
        without manually writing:

            new UserService()

     */



    public static void main(String[] args) {

        /*
            NORMAL OBJECT CREATION

            We directly call constructor.
         */
        Student s1 = new Student();



        /*
            =========================================
            REFLECTION CONCEPT
            =========================================

            Suppose constructor is private.

            Normally:

                new Student()

            would fail.

            BUT reflection can still access it.

         */



        /*
            Reflection can:

            Step 1:
            Get class information

            Step 2:
            Get constructor/method/field

            Step 3:
            Force accessibility

            Step 4:
            Create object dynamically

         */



        /*
            =========================================
            WHY IMPORTANT FOR SINGLETON?
            =========================================

            Singleton depends on:

            "private constructor"

            But reflection can break that rule.

            Reflection can access private constructor
            and create multiple objects.

            So singleton can break.

         */



        /*
            =========================================
            REAL LIFE ANALOGY
            =========================================

            Imagine:

            A room is private and locked.

            Normally nobody can enter.

            Reflection is like:
            master key of JVM.

            It can bypass restrictions.

         */



        /*
            =========================================
            SIMPLE FLOW OF REFLECTION
            =========================================

            Class<?> clazz = Student.class;

            Constructor<?> constructor =
                    clazz.getDeclaredConstructor();

            constructor.setAccessible(true);

            Student obj =
                    (Student) constructor.newInstance();

         */



        /*
            =========================================
            WHAT setAccessible(true) DOES?
            =========================================

            It disables Java access checks.

            So private becomes accessible.

         */



        /*
            =========================================
            WHY FRAMEWORKS USE REFLECTION?
            =========================================

            Example:
            Spring Framework

            Spring scans classes dynamically.

            Creates objects automatically.

            Injects dependencies.

            All done using reflection internally.

         */



        /*
            =========================================
            DISADVANTAGES OF REFLECTION
            =========================================

            - slower than normal calls
            - bypasses encapsulation
            - security risks
            - harder debugging

         */



        /*
            =========================================
            INTERVIEW QUESTIONS
            =========================================

            Q1:
            What is reflection?

            Answer:
            Ability to inspect and manipulate
            classes at runtime.



            Q2:
            Can reflection access private members?

            Answer:
            Yes.



            Q3:
            Which method bypasses private access?

            Answer:
            setAccessible(true)



            Q4:
            Why frameworks use reflection?

            Answer:
            Dynamic object creation and dependency injection.



            Q5:
            Can reflection break singleton?

            Answer:
            Yes.



            Q6:
            Is reflection slower?

            Answer:
            Yes compared to normal calls.

         */

    }

}



/*
    Separate class for learning
 */
class Student {

    public Student() {

        System.out.println("Student Object Created");
    }
}