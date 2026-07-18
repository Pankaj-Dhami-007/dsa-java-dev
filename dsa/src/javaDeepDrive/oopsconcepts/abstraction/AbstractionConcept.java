package javaDeepDrive.oopsconcepts.abstraction;

/*

=========================================================
                    ABSTRACTION
=========================================================

Definition:
Abstraction is the process of hiding implementation
details and showing only essential functionality
to the user.

Example:
ATM Machine

User knows:
- withdraw money
- check balance

User does NOT know:
- database queries
- internal banking logic
- server communication

=========================================================
                IMPORTANT POINTS
=========================================================

1. Hides internal implementation
2. Shows only important features
3. Reduces complexity
4. Improves security
5. Achieved using:
   - Abstract Classes
   - Interfaces

=========================================================
                REAL LIFE EXAMPLES
=========================================================

1. Car
   User uses:
   - brake
   - accelerator
   - steering

   User does NOT know:
   - engine internals

2. Mobile Phone
   User clicks apps
   User does not know OS internals

=========================================================
*/


public class AbstractionConcept {

    public static void main(String[] args) {

        System.out.println("======== ABSTRACT CLASS ========");

        Animal dog = new Dog();

        dog.sound();

        dog.sleep();



        System.out.println("\n======== INTERFACE ========");

        Payment payment = new CreditCardPayment();

        payment.pay(5000);



        System.out.println("\n======== MULTIPLE ABSTRACTION ========");

        SmartPhone smartPhone = new SmartPhone();

        smartPhone.call();

        smartPhone.camera();

        smartPhone.music();
    }
}



/*
=========================================================
                ABSTRACT CLASS
=========================================================

- Declared using abstract keyword
- Cannot create object directly
- Can contain:
    1. Abstract methods
    2. Concrete methods

- Used when classes share common behavior

=========================================================
*/

abstract class Animal {

    // ABSTRACT METHOD
    abstract void sound();



    // CONCRETE METHOD
    void sleep() {
        System.out.println("Animal is sleeping");
    }
}



/*
=========================================================
                CHILD CLASS
=========================================================

If class extends abstract class,
it MUST implement abstract methods.

=========================================================
*/

class Dog extends Animal {

    @Override
    void sound() {

        System.out.println("Dog barks");
    }
}



/*
=========================================================
                    INTERFACE
=========================================================

Interface is a blueprint of a class.

Used for:
- 100% abstraction
- Multiple inheritance
- Loose coupling

=========================================================
*/

interface Payment {

    void pay(int amount);
}



/*
=========================================================
            INTERFACE IMPLEMENTATION
=========================================================
*/

class CreditCardPayment implements Payment {

    @Override
    public void pay(int amount) {

        System.out.println(amount + " paid using credit card");
    }
}



/*
=========================================================
            MULTIPLE INHERITANCE
=========================================================

Java does not support:

class A extends B, C

But supports multiple inheritance using interfaces.

=========================================================
*/

interface Camera {

    void camera();
}

interface MusicPlayer {

    void music();
}



class Phone {

    void call() {

        System.out.println("Calling...");
    }
}



/*
=========================================================
        MULTIPLE ABSTRACTION USING INTERFACES
=========================================================
*/

class SmartPhone extends Phone implements Camera, MusicPlayer {

    @Override
    public void camera() {

        System.out.println("Opening Camera");
    }

    @Override
    public void music() {

        System.out.println("Playing Music");
    }
}



/*

=========================================================
            ABSTRACT CLASS VS INTERFACE
=========================================================

ABSTRACT CLASS
-----------------------
- Uses abstract keyword
- Can have constructors
- Can have instance variables
- Can have abstract + concrete methods
- Supports partial abstraction

INTERFACE
-----------------------
- Uses interface keyword
- No constructors
- Variables are public static final
- Methods are public abstract by default
- Supports full abstraction

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. What is abstraction?
Q2. Difference between abstraction and encapsulation?
Q3. What is abstract class?
Q4. Can we create object of abstract class?
Q5. What is interface?
Q6. Difference between abstract class and interface?
Q7. Why multiple inheritance not supported in Java?
Q8. Can abstract class have constructor?
Q9. Can interface have methods implementation?
Q10. What is loose coupling?

=========================================================
                IMPORTANT RULES
=========================================================

1. Abstract class object cannot be created

INVALID:
---------
Animal a = new Animal();

2. Child class must implement abstract methods

3. Abstract method has no body

Example:
---------
abstract void sound();

4. Interface methods are public abstract by default

=========================================================
                INTERVIEW ANSWER
=========================================================

Abstraction is the process of hiding implementation
details and exposing only essential functionality
to the user.

In Java, abstraction is achieved using:
1. Abstract Classes
2. Interfaces

=========================================================

*/