package java_17.sealed_classes;

// Sealed Classes provide controlled inheritance
//by allowing only specific permitted subclasses.

/*
 * ===============================================================
 * JAVA 17 SEALED CLASSES
 * ===============================================================
 *
 * Sealed Classes are used to:
 *
 * -> restrict inheritance
 * -> control class hierarchy
 * -> allow only specific subclasses
 *
 *
 * BEFORE JAVA 17
 * ------------------------
 * Any class could extend your class.
 *
 * Problem:
 * -> uncontrolled inheritance
 * -> security issues
 * -> invalid hierarchy
 * -> maintenance problems
 *
 *
 * JAVA 17 SOLUTION
 * ------------------------
 * sealed class
 *
 * Only permitted classes can extend it.
 */

public class SealedClassesDemo {

    public static void main(String[] args) {

        // =========================================================
        // ALLOWED CLASSES
        // =========================================================

        Shape circle =
                new Circle();

        circle.draw();




        Shape rectangle =
                new Rectangle();

        rectangle.draw();




        // =========================================================
        // REAL PROJECT EXAMPLE
        // =========================================================

        Payment payment =
                new CreditCardPayment();

        payment.pay();




        // =========================================================
        // PATTERN MATCHING BENEFIT
        // =========================================================

        /*
         * Since hierarchy is fixed,
         * compiler knows all possible subclasses.
         *
         * Very useful with switch pattern matching.
         */




        // =========================================================
        // IMPORTANT INTERVIEW QUESTIONS
        // =========================================================

        /*
         * Q1 Why Sealed Classes introduced?
         *
         * To control inheritance hierarchy.
         */




        /*
         * Q2 Difference between final
         * and sealed?
         *
         * final:
         * -> nobody can extend
         *
         * sealed:
         * -> only permitted classes can extend
         */




        /*
         * Q3 Which modifiers allowed
         * for child classes?
         *
         * final
         * sealed
         * non-sealed
         */




        /*
         * Q4 Can Sealed interface exist?
         *
         * YES
         */




        /*
         * Q5 Real use case of Sealed Classes?
         *
         * Payment systems
         * API response hierarchy
         * Domain modeling
         * Finite type systems
         */
    }
}




// ===============================================================
// BEFORE JAVA 17 PROBLEM
// ===============================================================

/*
 * Imagine this normal class:
 */

class OldShape {

    public void draw() {
        System.out.println("Drawing Shape");
    }
}



/*
 * ANYONE can extend this class.
 */

class Triangle extends OldShape {

}



/*
 * Even invalid classes can extend.
 */

class WrongShape extends OldShape {

}




// ===============================================================
// JAVA 17 SEALED CLASS
// ===============================================================

/*
 * sealed:
 * restricts inheritance
 *
 * permits:
 * defines allowed subclasses
 */

sealed class Shape permits Circle, Rectangle {

    public void draw() {

        System.out.println("Drawing Shape");
    }
}




// ===============================================================
// ALLOWED CHILD CLASSES
// ===============================================================

/*
 * final:
 * nobody can extend further
 */

final class Circle extends Shape {

    @Override
    public void draw() {

        System.out.println("Drawing Circle");
    }
}




/*
 * non-sealed:
 * inheritance open again
 */

non-sealed class Rectangle extends Shape {

    @Override
    public void draw() {

        System.out.println("Drawing Rectangle");
    }
}




/*
 * Since Rectangle is non-sealed,
 * it can now be extended.
 */

class RoundedRectangle extends Rectangle {

}




// ===============================================================
// SEALED INTERFACE
// ===============================================================

sealed interface Payment permits CreditCardPayment, UPIPayment {

    void pay();
}


final class CreditCardPayment implements Payment {

    @Override
    public void pay() {

        System.out.println("Credit Card Payment");
    }
}




final class UPIPayment implements Payment {

    @Override
    public void pay() {

        System.out.println("UPI Payment");
    }
}