package designpattern.creational.factory.factorymethod;

public class FactoryMethodDesignPattern {

    /*
        ==========================================================
        FACTORY METHOD DESIGN PATTERN
        ==========================================================

        VERY IMPORTANT DESIGN PATTERN.

        MOST students confuse:

        - Factory Pattern
        - Factory Method Pattern

        They are related
        BUT NOT same.

     */



    /*
        ==========================================================
        SIMPLE UNDERSTANDING
        ==========================================================

        BASIC FACTORY PATTERN:

        One factory class creates all objects.

            NotificationFactory

        ----------------------------------------------------------

        FACTORY METHOD PATTERN:

        Each child factory creates
        its own object.

     */



    /*
        ==========================================================
        MAIN IDEA
        ==========================================================

        Parent defines factory method.

        Child factories decide:
        which object to create.

     */



    /*
        ==========================================================
        VERY SIMPLE DEFINITION
        ==========================================================

        Factory Method Pattern delegates
        object creation to child classes.

     */



    /*
        ==========================================================
        WHY FACTORY METHOD PATTERN?
        ==========================================================

        Problem in basic factory:

        Large if-else chains.

            if(type.equals("EMAIL"))
            else if(type.equals("SMS"))
            else if(type.equals("PUSH"))

        As application grows:
        factory becomes huge.

     */



    /*
        ==========================================================
        FACTORY METHOD SOLUTION
        ==========================================================

        Separate factory for each object.

        EmailFactory -> EmailNotification
        SMSFactory -> SMSNotification
        PushFactory -> PushNotification

        Cleaner architecture.

     */



    /*
        ==========================================================
        REAL LIFE ANALOGY
        ==========================================================

        Think:

        Car manufacturing company.

        Different factories create:
        - BMW
        - Audi
        - Tesla

        Central company says:
            "createCar()"

        But each factory decides
        actual car creation process.

     */



    public static void main(String[] args) {

        /*
            Parent factory reference

            Child factory object.
         */
        NotificationFactory factory1 =
                new EmailFactory();



        /*
            Factory method called.

            Child factory creates object.
         */
        Notification notification1 =
                factory1.createNotification();



        notification1.send();



        /*
            Another child factory.
         */
        NotificationFactory factory2 =
                new SMSFactory();



        Notification notification2 =
                factory2.createNotification();



        notification2.send();



        /*
            OUTPUT:

            Sending EMAIL Notification

            Sending SMS Notification

         */



        /*
            ======================================================
            INTERNAL FLOW
            ======================================================

            STEP-1:
            Client creates factory object.

            STEP-2:
            Factory method called.

            STEP-3:
            Child factory creates proper object.

            STEP-4:
            Parent reference receives child object.

         */



        /*
            ======================================================
            MOST IMPORTANT DIFFERENCE
            ======================================================

            BASIC FACTORY:

                One factory handles everything.

            FACTORY METHOD:

                Multiple factories exist.

         */



        /*
            ======================================================
            WHY BETTER THAN BASIC FACTORY?
            ======================================================

            New notification type?

            Add:
            - new notification class
            - new factory class

            Existing factories unchanged.

            Better Open Closed Principle.

         */



        /*
            ======================================================
            RUNTIME POLYMORPHISM
            ======================================================

            NotificationFactory factory =
                    new EmailFactory();

            Factory method implementation
            decided at runtime.

         */



        /*
            ======================================================
            REAL WORLD USAGE
            ======================================================

            VERY heavily used in frameworks.

            Examples:
            - Spring Framework
            - JDBC
            - Hibernate
            - Logging frameworks

         */



        /*
            ======================================================
            JDBC EXAMPLE
            ======================================================

            DriverManager.getConnection()

            Internally:
            Different DB drivers create
            different connection objects.

            MySQL
            PostgreSQL
            Oracle

         */



        /*
            ======================================================
            SPRING EXAMPLE
            ======================================================

            Spring BeanFactory

            Different bean creators
            create different bean types.

         */



        /*
            ======================================================
            ADVANTAGES
            ======================================================

            - removes large if-else
            - scalable
            - loosely coupled
            - extensible
            - cleaner architecture

         */



        /*
            ======================================================
            DISADVANTAGES
            ======================================================

            - too many classes
            - increased abstraction
            - more complexity

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Difference between Factory
            and Factory Method?

            Answer:

            Factory:
            one central factory class.

            Factory Method:
            multiple child factories.



            Q2:
            Main idea of Factory Method?

            Answer:
            Delegate object creation
            to child factories.



            Q3:
            Which principle improved?

            Answer:
            Open Closed Principle.



            Q4:
            Biggest advantage?

            Answer:
            Removes huge conditional logic.



            Q5:
            Which OOP concept heavily used?

            Answer:
            Polymorphism + Abstraction.



            Q6:
            Factory method defined where?

            Answer:
            Parent factory abstraction.



            Q7:
            Actual object creation happens where?

            Answer:
            Child factory classes.

         */



        /*
            ======================================================
            IS FACTORY METHOD COMPLETE?
            ======================================================

            BASIC UNDERSTANDING:
            YES COMPLETE.

            Still remaining:

            - Abstract Factory Pattern
            - Factory vs Abstract Factory
            - GUI toolkit examples
            - Enterprise architecture usage
            - Reflection factories
            - Registry factories

         */

    }

}



/*
    ==========================================================
    PRODUCT PARENT
    ==========================================================
 */
interface Notification {

    void send();
}



/*
    ==========================================================
    PRODUCT CHILD-1
    ==========================================================
 */
class EmailNotification implements Notification {

    @Override
    public void send() {

        System.out.println("Sending EMAIL Notification");
    }
}



/*
    ==========================================================
    PRODUCT CHILD-2
    ==========================================================
 */
class SMSNotification implements Notification {

    @Override
    public void send() {

        System.out.println("Sending SMS Notification");
    }
}



/*
    ==========================================================
    FACTORY PARENT
    ==========================================================

    Parent defines factory method.
 */
abstract class NotificationFactory {

    /*
        Factory Method

        Child factories override this.
     */
    abstract Notification createNotification();
}



/*
    ==========================================================
    FACTORY CHILD-1
    ==========================================================

    Responsible for Email object creation.
 */
class EmailFactory extends NotificationFactory {

    @Override
    Notification createNotification() {

        return new EmailNotification();
    }
}



/*
    ==========================================================
    FACTORY CHILD-2
    ==========================================================

    Responsible for SMS object creation.
 */
class SMSFactory extends NotificationFactory {

    @Override
    Notification createNotification() {

        return new SMSNotification();
    }
}