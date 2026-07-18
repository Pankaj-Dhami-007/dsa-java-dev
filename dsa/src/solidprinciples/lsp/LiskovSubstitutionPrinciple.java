package solidprinciples.lsp;

public class LiskovSubstitutionPrinciple {

    /*
        ==========================================================
        LISKOV SUBSTITUTION PRINCIPLE (LSP)
        ==========================================================

        THIRD principle of SOLID.

        MOST misunderstood principle.

        VERY IMPORTANT in:
        - inheritance design
        - framework development
        - API architecture
        - enterprise applications

     */



    /*
        ==========================================================
        WHO INTRODUCED?
        ==========================================================

        Introduced by:
            Barbara Liskov

     */



    /*
        ==========================================================
        SIMPLE DEFINITION
        ==========================================================

        Child class should be able to replace
        parent class WITHOUT breaking behavior.

     */



    /*
        ==========================================================
        VERY SIMPLE UNDERSTANDING
        ==========================================================

        If child IS-A parent,

        then child should behave properly
        like parent.

     */



    /*
        ==========================================================
        MAIN IDEA
        ==========================================================

        Inheritance should NOT break system behavior.

     */



    /*
        ==========================================================
        REAL LIFE ANALOGY
        ==========================================================

        Suppose:

            Bird parent class

        Parent behavior:
            fly()

        Then Penguin extends Bird.

        Problem:
            Penguin cannot fly.

        Inheritance becomes wrong.

     */



    /*
        ==========================================================
        BAD DESIGN EXAMPLE
        ==========================================================

        Parent:

            Bird
                fly()

        Child:

            Penguin
                throw exception in fly()

        This violates LSP.

     */



    /*
        ==========================================================
        WHY BAD?
        ==========================================================

        Client expects:

            every Bird can fly

        But Penguin breaks expectation.

        System behavior becomes inconsistent.

     */



    /*
        ==========================================================
        LSP SOLUTION
        ==========================================================

        Correct abstraction design.
        Separate behaviors properly.

     */



    public static void main(String[] args) {

        /*
            ======================================================
            CORRECT LSP DESIGN
            ======================================================
         */

        Payment payment =
                new UPIPayment();



        processPayment(payment);



        /*
            OUTPUT:

            UPI Payment Processed

         */



        payment =
                new CreditCardPayment();



        processPayment(payment);



        /*
            OUTPUT:

            Credit Card Payment Processed

         */



        /*
            ======================================================
            MAIN UNDERSTANDING
            ======================================================

            Both child classes properly replace parent.
            No unexpected behavior.

         */



        /*
            ======================================================
            WHAT WOULD VIOLATE LSP?
            ======================================================

            Suppose:

            class FakePayment
                    implements Payment {

                throw exception in pay()

            }

            Then substitution breaks.

         */



        /*
            ======================================================
            INTERNAL THINKING
            ======================================================

            Parent contract must remain valid
            for all child classes.

         */



        /*
            ======================================================
            MOST IMPORTANT KEYWORD
            ======================================================

            BEHAVIORAL COMPATIBILITY

         */



        /*
            ======================================================
            WHY LSP IMPORTANT?
            ======================================================

            Real enterprise systems use:
            - interfaces
            - inheritance
            - polymorphism

            Bad inheritance design creates:
            - runtime bugs
            - fragile systems
            - unexpected behavior

         */



        /*
            ======================================================
            COMMON LSP VIOLATION
            ======================================================

            Rectangle-Square problem.

            Parent:
                Rectangle

            Child:
                Square

            Changing width affects height.

            Parent expectations break.

            VERY famous interview question.

         */



        /*
            ======================================================
            SPRING FRAMEWORK CONNECTION
            ======================================================

            Spring heavily depends on:
            interfaces + polymorphism.

            All implementations must properly
            substitute abstractions.

         */



        /*
            ======================================================
            DESIGN PATTERN CONNECTION
            ==========================================================

            Strategy Pattern heavily depends on LSP.

            Every strategy implementation
            must behave correctly.

         */



        /*
            ======================================================
            REAL WORLD COMPANY EXAMPLE
            ======================================================

            Payment Gateway System:

            Parent:
                Payment

            Childs:
                UPI
                Card
                Wallet

            All must properly process payment.

            No child should break workflow.

         */



        /*
            ======================================================
            MOST ASKED INTERVIEW QUESTION
            ======================================================

            Q:
            Why Penguin-Bird example
            violates LSP?

            Answer:

            Because parent expects all birds fly,
            but Penguin cannot.

            Child breaks parent contract.

         */



        /*
            ======================================================
            MOST IMPORTANT INTERVIEW UNDERSTANDING
            ======================================================

            LSP is NOT about syntax.

            It is about:
                BEHAVIOR CONSISTENCY

         */



        /*
            ======================================================
            BAD LSP SYMPTOMS
            ======================================================

            Signs of violation:

            - child throws unsupported exceptions
            - empty overridden methods
            - if(instanceof)
            - broken polymorphism

         */



        /*
            ======================================================
            instanceof SMELL
            ======================================================

            If code frequently checks:

                if(obj instanceof Child)

            often inheritance design wrong.

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Definition of LSP?

            Answer:
            Child should replace parent
            without breaking behavior.



            Q2:
            Main keyword of LSP?

            Answer:
            Behavioral compatibility.



            Q3:
            Most famous LSP example?

            Answer:
            Bird-Penguin problem.



            Q4:
            Rectangle-Square problem related to?

            Answer:
            LSP violation.



            Q5:
            Biggest symptom of bad LSP?

            Answer:
            Broken polymorphism.



            Q6:
            Why unsupported exceptions bad?

            Answer:
            Child breaks parent expectations.



            Q7:
            Which design pattern strongly depends on LSP?

            Answer:
            Strategy Pattern.



            Q8:
            Is LSP about syntax or behavior?

            Answer:
            Behavior.



            Q9:
            Why instanceof checks dangerous?

            Answer:
            Often indicates wrong inheritance.



            Q10:
            Real-world example?

            Answer:
            Payment gateway implementations.

         */



        /*
            ======================================================
            VERY IMPORTANT SENIOR LEVEL THINKING
            ======================================================

            Good inheritance means:

                child truly behaves
                like parent logically.

            NOT just:
                code reuse.

         */



        /*
            ======================================================
            IS LSP COMPLETE?
            ======================================================

            YES BASIC TO INTERMEDIATE COMPLETE.

            Next:
                Interface Segregation Principle (ISP)

            VERY IMPORTANT for API design.

         */

    }



    /*
        Parent abstraction.
     */
    public static void processPayment(
            Payment payment
    ) {

        payment.pay();
    }

}



/*
    ==========================================================
    ABSTRACTION
    ==========================================================
 */
interface Payment {

    void pay();
}



/*
    ==========================================================
    CHILD-1
    ==========================================================
 */
class UPIPayment implements Payment {

    @Override
    public void pay() {

        System.out.println(
                "UPI Payment Processed"
        );
    }

}



/*
    ==========================================================
    CHILD-2
    ==========================================================
 */
class CreditCardPayment implements Payment {

    @Override
    public void pay() {

        System.out.println(
                "Credit Card Payment Processed"
        );
    }

}