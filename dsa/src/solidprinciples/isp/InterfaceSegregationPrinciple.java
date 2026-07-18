package solidprinciples.isp;

public class InterfaceSegregationPrinciple {

    /*
        ==========================================================
        INTERFACE SEGREGATION PRINCIPLE (ISP)
        ==========================================================

        FOURTH principle of SOLID.

        VERY IMPORTANT for:
        - API design
        - framework development
        - microservices
        - clean architecture
        - enterprise systems

     */



    /*
        ==========================================================
        SIMPLE DEFINITION
        ==========================================================

        Clients should NOT be forced
        to depend on methods
        they do not use.

     */



    /*
        ==========================================================
        VERY SIMPLE UNDERSTANDING
        ==========================================================

        Do NOT create huge interfaces.

        Split interfaces properly.

     */



    /*
        ==========================================================
        MAIN IDEA
        ==========================================================

        One interface
            -> one specific responsibility

     */



    /*
        ==========================================================
        REAL LIFE ANALOGY
        ==========================================================

        Imagine restaurant worker interface:

            cookFood()
            driveTruck()
            doAccounting()
            cleanFloor()

        Every employee forced
        to implement everything.

        Completely wrong design.

     */



    /*
        ==========================================================
        BAD DESIGN EXAMPLE
        ==========================================================

        Interface:

            Worker {

                work();
                eat();
                sleep();
                code();
                test();
                deploy();

            }

        Every class forced to implement
        unnecessary methods.

     */



    /*
        ==========================================================
        WHY BAD?
        ==========================================================

        Problems:

        - empty methods
        - unsupported operations
        - tight coupling
        - difficult maintenance

     */



    /*
        ==========================================================
        ISP SOLUTION
        ==========================================================

        Split large interfaces
        into smaller focused interfaces.

     */



    public static void main(String[] args) {

        /*
            ======================================================
            DEVELOPER OBJECT
            ======================================================
         */

        Developer developer =
                new Developer();



        developer.code();



        /*
            OUTPUT:

            Developer Writing Code

         */



        /*
            ======================================================
            TESTER OBJECT
            ======================================================
         */

        Tester tester =
                new Tester();



        tester.test();



        /*
            OUTPUT:

            Tester Testing Application

         */



        /*
            ======================================================
            MAIN UNDERSTANDING
            ======================================================

            Developer class forced only
            to implement coding logic.

            Tester class forced only
            to implement testing logic.

         */



        /*
            ======================================================
            WHY ISP IMPORTANT?
            ======================================================

            Real enterprise systems contain:
            huge APIs/interfaces.

            Without ISP:
            classes implement unnecessary methods.

         */



        /*
            ======================================================
            MOST IMPORTANT KEYWORD
            ======================================================

            FAT INTERFACE

            Huge interface with unrelated methods.

            ISP tries to avoid fat interfaces.

         */



        /*
            ======================================================
            VERY IMPORTANT UNDERSTANDING
            ======================================================

            ISP mainly focuses on:

                CLIENT-SPECIFIC INTERFACES

         */



        /*
            ======================================================
            REAL WORLD COMPANY EXAMPLE
            ======================================================

            Amazon Seller APIs:

            Separate APIs:
            - inventory management
            - payment management
            - shipping management

            NOT one giant interface.

         */



        /*
            ======================================================
            SPRING FRAMEWORK CONNECTION
            ======================================================

            Spring uses many small focused interfaces.

            Example:

            BeanFactory
            ApplicationContext
            Environment

            Focused responsibilities.

         */



        /*
            ======================================================
            JAVA EXAMPLE
            ======================================================

            Java interfaces often follow ISP.

            Example:

            List
            Set
            Queue

            Different focused abstractions.

         */



        /*
            ======================================================
            BAD ISP SYMPTOMS
            ======================================================

            Signs of violation:

            - empty methods
            - UnsupportedOperationException
            - giant interfaces
            - unrelated methods together

         */



        /*
            ======================================================
            UnsupportedOperationException
            ======================================================

            MOST IMPORTANT interview topic.

            If implementation frequently throws:

                UnsupportedOperationException

            Often ISP violation exists.

         */



        /*
            ======================================================
            REAL INTERVIEW EXAMPLE
            ======================================================

            Printer interface:

                print()
                scan()
                fax()

            BasicPrinter only supports print.

            Forced scan/fax methods
            become useless.

            ISP violation.

         */



        /*
            ======================================================
            ISP SOLUTION FOR PRINTER
            ======================================================

            Separate interfaces:

                Printable
                Scannable
                Faxable

         */



        /*
            ======================================================
            ISP vs SRP
            ======================================================

            SRP:
            focused classes

            ISP:
            focused interfaces

         */



        /*
            ======================================================
            ADVANTAGES
            ======================================================

            - cleaner APIs
            - loose coupling
            - flexible architecture
            - easier maintenance
            - avoids unnecessary methods

         */



        /*
            ======================================================
            DISADVANTAGES
            ======================================================

            - more interfaces
            - more abstraction layers

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Definition of ISP?

            Answer:
            Clients should not depend
            on unused methods.



            Q2:
            Main goal of ISP?

            Answer:
            Small focused interfaces.



            Q3:
            What is Fat Interface?

            Answer:
            Huge interface with many unrelated methods.



            Q4:
            Biggest symptom of ISP violation?

            Answer:
            UnsupportedOperationException
            or empty methods.



            Q5:
            ISP mainly focuses on?

            Answer:
            Client-specific interfaces.



            Q6:
            Difference between SRP and ISP?

            Answer:

            SRP:
            focused classes

            ISP:
            focused interfaces



            Q7:
            Real-world example?

            Answer:
            Printer interface problem.



            Q8:
            Why giant interfaces bad?

            Answer:
            Classes forced to implement
            unnecessary behavior.



            Q9:
            Spring follows ISP how?

            Answer:
            Many small focused interfaces.



            Q10:
            Most important ISP keyword?

            Answer:
            Fat Interface.

         */



        /*
            ======================================================
            VERY IMPORTANT SENIOR LEVEL THINKING
            ======================================================

            Good API design means:

            Client should only see
            methods it actually needs.

         */



        /*
            ======================================================
            IS ISP COMPLETE?
            ======================================================

            YES BASIC TO INTERMEDIATE COMPLETE.

            Next:
                Dependency Inversion Principle (DIP)

            MOST IMPORTANT SOLID principle
            for Spring Framework.

         */

    }

}



/*
    ==========================================================
    SMALL FOCUSED INTERFACE-1
    ==========================================================
 */
interface CodingWork {

    void code();
}



/*
    ==========================================================
    SMALL FOCUSED INTERFACE-2
    ==========================================================
 */
interface TestingWork {

    void test();
}



/*
    ==========================================================
    IMPLEMENTATION-1
    ==========================================================
 */
class Developer implements CodingWork {

    @Override
    public void code() {

        System.out.println(
                "Developer Writing Code"
        );
    }

}



/*
    ==========================================================
    IMPLEMENTATION-2
    ==========================================================
 */
class Tester implements TestingWork {

    @Override
    public void test() {

        System.out.println(
                "Tester Testing Application"
        );
    }

}