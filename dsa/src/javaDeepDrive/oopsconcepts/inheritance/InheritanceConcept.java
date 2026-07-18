package javaDeepDrive.oopsconcepts.inheritance;


/*

Parent constructor executes first because child object contains parent state,
and JVM must initialize parent part before child part to maintain object consistency and
proper inheritance behavior.
 */

/*

Constructors are not inherited because constructors are responsible for initializing objects of
their own class. A parent constructor cannot initialize child-specific state, so Java uses
constructor chaining through super() instead of inheritance.
 */

/*

Inheritance
Inheritance is a mechanism where one class (the child class) inherits the properties and behaviors (methods) from another class (the parent class).
The subclass can also override methods of the superclass to provide its own implementation.
It is an "is-a" relationship, meaning the child class is a type of the parent class.


 */


/*

=========================================================
                INHERITANCE IN JAVA
=========================================================

Definition:
Inheritance is a mechanism where one class acquires
properties and behaviors of another class.

Keyword Used:
extends

Real Life Example:
Parent -> Vehicle
Child  -> Car

Car automatically gets:
- speed
- start()
- stop()

Advantages:
1. Code Reusability
2. Method Reusability
3. Achieve Hierarchical Relationship
4. Runtime Polymorphism Support

---------------------------------------------------------
IMPORTANT INTERVIEW POINTS
---------------------------------------------------------

1. Java does NOT support multiple inheritance with classes
   because of ambiguity problem (Diamond Problem)

2. Java supports:
   - Single Inheritance
   - Multilevel Inheritance
   - Hierarchical Inheritance

3. Multiple inheritance is achieved using interfaces.

4. Constructors are NOT inherited.

5. Private members are NOT directly accessible in child class.

=========================================================
                    QUESTIONS
=========================================================

Q1. What is inheritance?
Q2. Why do we use inheritance?
Q3. Which keyword is used for inheritance?
Q4. Does Java support multiple inheritance?
Q5. Difference between IS-A and HAS-A relationship?
Q6. Are constructors inherited?
Q7. Can private methods be inherited?
Q8. What is method overriding?
Q9. What is super keyword?
Q10. Difference between inheritance and composition?

=========================================================
                    CODE STARTS
=========================================================

*/


// ------------------------------------------------------
// Parent Class
// ------------------------------------------------------
class Animal {

    String name = "Animal";

    Animal() {
        System.out.println("Animal Constructor Called");
    }

    void eat() {
        System.out.println("Animal is eating");
    }

    void sleep() {
        System.out.println("Animal is sleeping");
    }
}



// ------------------------------------------------------
// Child Class
// ------------------------------------------------------
class Dog extends Animal {

    String breed = "Labrador";

    Dog() {
        System.out.println("Dog Constructor Called");
    }

    void bark() {
        System.out.println("Dog is barking");
    }
}



// ------------------------------------------------------
// Multilevel Inheritance
// ------------------------------------------------------
class Puppy extends Dog {

    void weep() {
        System.out.println("Puppy is weeping");
    }
}



// ------------------------------------------------------
// Hierarchical Inheritance
// ------------------------------------------------------
class Cat extends Animal {

    void meow() {
        System.out.println("Cat says meow");
    }
}



// ------------------------------------------------------
// Method Overriding
// ------------------------------------------------------
class Lion extends Animal {

    @Override
    void eat() {
        System.out.println("Lion eats meat");
    }
}



// ------------------------------------------------------
// Main Class
// ------------------------------------------------------
public class InheritanceConcept {

    public static void main(String[] args) {

        System.out.println("======== SINGLE INHERITANCE ========");

        Dog dog = new Dog();

        dog.eat();      // inherited
        dog.sleep();    // inherited
        dog.bark();     // own method

        System.out.println(dog.name);
        System.out.println(dog.breed);



        System.out.println("\n======== MULTILEVEL INHERITANCE ========");

        Puppy puppy = new Puppy();

        puppy.eat();
        puppy.bark();
        puppy.weep();



        System.out.println("\n======== HIERARCHICAL INHERITANCE ========");

        Cat cat = new Cat();

        cat.eat();
        cat.meow();



        System.out.println("\n======== METHOD OVERRIDING ========");

        Lion lion = new Lion();

        lion.eat();



        System.out.println("\n======== UPCASTING ========");

        Animal animal = new Dog();

        animal.eat();
        animal.sleep();

        // animal.bark(); // NOT ALLOWED



        System.out.println("\n======== DOWNCASTING ========");

        Dog d = (Dog) animal;

        d.bark();



        System.out.println("\n======== instanceof ========");

        System.out.println(dog instanceof Dog);
        System.out.println(dog instanceof Animal);
        System.out.println(cat instanceof Animal);

    }
}



/*

=========================================================
                OUTPUT UNDERSTANDING
=========================================================

Dog dog = new Dog();

Step 1:
Parent constructor executes first

Animal Constructor Called

Step 2:
Child constructor executes

Dog Constructor Called

---------------------------------------------------------

Animal animal = new Dog();

Reference Type  = Animal
Object Type     = Dog

Compile Time:
Checks Animal methods only

Runtime:
Overridden method from Dog executes

=========================================================
                TYPES OF INHERITANCE
=========================================================

1. Single Inheritance

Animal -> Dog

2. Multilevel Inheritance

Animal -> Dog -> Puppy

3. Hierarchical Inheritance

          Animal
         /      \
       Dog      Cat

4. Multiple Inheritance

NOT supported with classes in Java

class A {}
class B {}
class C extends A, B {} // INVALID

Reason:
Diamond Problem

=========================================================
                SUPER KEYWORD
=========================================================

Used to:
1. Access parent variables
2. Access parent methods
3. Call parent constructor

Example:

super.eat();
super.name;
super();

=========================================================
                IS-A RELATIONSHIP
=========================================================

Dog IS-A Animal

Inheritance always represents IS-A relationship.

=========================================================
                HAS-A RELATIONSHIP
=========================================================

Car HAS-A Engine

This is Composition, NOT inheritance.

=========================================================
                INTERVIEW TRAPS
=========================================================

1. Constructor is inherited?
   NO

2. Private methods inherited?
   NO

3. Can final class be inherited?
   NO

4. Can static methods be overridden?
   NO (method hiding)

5. Can we reduce visibility while overriding?
   NO

=========================================================
                MEMORY UNDERSTANDING
=========================================================

Dog Object Memory:

---------------------------------
| Animal Variables              |
| Animal Methods                |
| Dog Variables                 |
| Dog Methods                   |
---------------------------------

Child object contains parent properties.

=========================================================
                IMPORTANT DIFFERENCES
=========================================================

Inheritance:
- IS-A relationship
- Tight coupling

Composition:
- HAS-A relationship
- Loose coupling
- Preferred in modern design

=========================================================
                BEST PRACTICE
=========================================================

"Favor Composition over Inheritance"

Because inheritance creates tight dependency.

=========================================================

*/