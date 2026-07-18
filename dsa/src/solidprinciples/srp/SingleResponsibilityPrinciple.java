package solidprinciples.srp;

public class SingleResponsibilityPrinciple {

    /*
        ==========================================================
        SINGLE RESPONSIBILITY PRINCIPLE (SRP)
        ==========================================================

        FIRST principle of SOLID.

        MOST IMPORTANT principle.

        Extremely heavily used in:
        - Spring Boot
        - Microservices
        - Enterprise applications
        - Clean Architecture

     */



    /*
        ==========================================================
        SIMPLE DEFINITION
        ==========================================================

        A class should have ONLY ONE reason to change.

     */



    /*
        ==========================================================
        VERY SIMPLE UNDERSTANDING
        ==========================================================

        One class
            -> one responsibility/job

     */



    /*
        ==========================================================
        MAIN IDEA
        ==========================================================

        Do NOT put multiple responsibilities
        inside one class.

     */



    /*
        ==========================================================
        REAL LIFE ANALOGY
        ==========================================================

        Imagine one employee handling:

        - coding
        - testing
        - deployment
        - HR
        - accounting

        Complete chaos.

        Same problem happens in software.

     */



    /*
        ==========================================================
        BAD DESIGN EXAMPLE
        ==========================================================

        Suppose User class does:

        - user data handling
        - database saving
        - email sending
        - PDF generation

        TOO MANY responsibilities.

     */



    /*
        ==========================================================
        WHY BAD?
        ==========================================================

        Different reasons to change exist.

        Example:

        DB logic changes
            -> User class changes

        Email format changes
            -> User class changes

        PDF logic changes
            -> User class changes

        One class affected by many departments.

     */



    /*
        ==========================================================
        SRP SOLUTION
        ==========================================================

        Split responsibilities.

        Example:

        UserService
            -> business logic

        UserRepository
            -> DB operations

        EmailService
            -> email handling

        PDFService
            -> PDF generation

     */



    public static void main(String[] args) {

        /*
            ======================================================
            PROPER SRP DESIGN
            ======================================================
         */

        User user =
                new User(
                        "Pankaj",
                        "pankaj@gmail.com"
                );



        /*
            Each class has single responsibility.
         */

        UserRepository repository =
                new UserRepository();



        repository.saveUser(user);



        EmailService emailService =
                new EmailService();



        emailService.sendEmail(user);



        /*
            OUTPUT:

            User saved in DB:
            Pankaj

            Email sent to:
            pankaj@gmail.com

         */



        /*
            ======================================================
            MAIN UNDERSTANDING
            ======================================================

            User class:
                only user data

            Repository:
                only DB logic

            EmailService:
                only email logic

         */



        /*
            ======================================================
            WHY SRP IMPORTANT?
            ======================================================

            Real enterprise applications huge.

            Without SRP:
            classes become massive.

            Called:
                GOD CLASSES

         */



        /*
            ======================================================
            GOD CLASS PROBLEM
            ======================================================

            One class handling:
            - DB
            - business
            - API
            - validation
            - logging
            - email

            Difficult to:
            - understand
            - test
            - maintain
            - scale

         */



        /*
            ======================================================
            BENEFITS OF SRP
            ======================================================

            - cleaner code
            - easier maintenance
            - easier testing
            - better scalability
            - loose coupling
            - smaller classes

         */



        /*
            ======================================================
            TESTING BENEFIT
            ======================================================

            Small focused classes easier to unit test.

            Example:

            EmailService test
            does NOT require DB testing.

         */



        /*
            ======================================================
            SPRING FRAMEWORK CONNECTION
            ======================================================

            Spring Boot architecture itself
            follows SRP heavily.

            Example layers:

            Controller
                -> request handling

            Service
                -> business logic

            Repository
                -> DB logic

            DTO
                -> data transfer

         */



        /*
            ======================================================
            MICROSERVICES CONNECTION
            ======================================================

            Microservices idea also connected to SRP.

            One service
                -> one business responsibility.

         */



        /*
            ======================================================
            VERY IMPORTANT UNDERSTANDING
            ======================================================

            SRP does NOT mean:
                one method per class

            It means:
                one REASON TO CHANGE.

         */



        /*
            ======================================================
            REAL WORLD COMPANY EXAMPLE
            ======================================================

            Amazon Order System:

            OrderService
                -> order business logic

            PaymentService
                -> payment logic

            NotificationService
                -> notifications

            InventoryService
                -> stock management

         */



        /*
            ======================================================
            BAD SRP SYMPTOMS
            ======================================================

            Signs of SRP violation:

            - very large classes
            - too many methods
            - unrelated methods
            - difficult testing
            - frequent modifications

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Definition of SRP?

            Answer:
            A class should have only
            one reason to change.



            Q2:
            Main goal of SRP?

            Answer:
            Focused and maintainable classes.



            Q3:
            Biggest benefit?

            Answer:
            Easier maintenance and testing.



            Q4:
            What is God Class?

            Answer:
            Class with too many responsibilities.



            Q5:
            Spring architecture follows SRP how?

            Answer:
            Layer separation:
            controller/service/repository.



            Q6:
            Does SRP mean one method per class?

            Answer:
            No.



            Q7:
            Main keyword of SRP?

            Answer:
            One responsibility.



            Q8:
            Why easier testing?

            Answer:
            Responsibilities isolated.

         */



        /*
            ======================================================
            IS SRP COMPLETE?
            ======================================================

            YES BASIC TO INTERMEDIATE COMPLETE.

            Next:
                Open Closed Principle (OCP)

            MOST IMPORTANT principle
            for design patterns.

         */

    }

}



/*
    ==========================================================
    DATA CLASS
    ==========================================================

    Only user data responsibility.
 */
class User {

    private String name;

    private String email;



    public User(String name,
                String email) {

        this.name = name;

        this.email = email;
    }



    public String getName() {

        return name;
    }



    public String getEmail() {

        return email;
    }

}



/*
    ==========================================================
    DB RESPONSIBILITY
    ==========================================================
 */
class UserRepository {

    public void saveUser(User user) {

        System.out.println(
                "User saved in DB: "
                        + user.getName()
        );
    }

}



/*
    ==========================================================
    EMAIL RESPONSIBILITY
    ==========================================================
 */
class EmailService {

    public void sendEmail(User user) {

        System.out.println(
                "Email sent to: "
                        + user.getEmail()
        );
    }

}