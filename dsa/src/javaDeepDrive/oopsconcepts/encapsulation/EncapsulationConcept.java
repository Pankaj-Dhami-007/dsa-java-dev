package javaDeepDrive.oopsconcepts.encapsulation;

/*

=========================================================
                    ENCAPSULATION
=========================================================

Definition:
Encapsulation in Java is the mechanism of wrapping
data (variables) and code (methods) together
as a single unit.

It provides:
- Data Hiding
- Controlled Access
- Security
- Flexibility

Real Life Example:
ATM Machine

User cannot directly access:
- bank balance
- database
- transaction logic

User interacts only through:
- withdraw()
- deposit()
- checkBalance()

=========================================================
                IMPORTANT POINTS
=========================================================

1. Variables are usually private
2. Access is given using methods
3. Getter and Setter methods are used
4. Achieves data hiding
5. Improves security

=========================================================
*/


public class EncapsulationConcept {

    public static void main(String[] args) {

        BankAccount account = new BankAccount();

        /*
        account.balance = 100000;
        ERROR

        Because balance is private
        */

        account.setBalance(5000);

        System.out.println(account.getBalance());



        account.deposit(2000);

        System.out.println(account.getBalance());



        account.withdraw(1000);

        System.out.println(account.getBalance());



        account.setAge(-10);

        System.out.println(account.getAge());



        account.setAge(25);

        System.out.println(account.getAge());
    }
}



/*
=========================================================
                    ENCAPSULATED CLASS
=========================================================

Rules:
1. Make variables private
2. Provide public getter/setter methods

=========================================================
*/

class BankAccount {

    // PRIVATE VARIABLES
    private double balance;

    private int age;



    // GETTER METHOD
    public double getBalance() {
        return balance;
    }



    // SETTER METHOD
    public void setBalance(double balance) {

        if (balance >= 0) {

            this.balance = balance;

        } else {

            System.out.println("Invalid Balance");
        }
    }



    // BUSINESS LOGIC METHODS
    public void deposit(double amount) {

        if (amount > 0) {

            balance = balance + amount;

            System.out.println(amount + " deposited");
        }
    }



    public void withdraw(double amount) {

        if (amount <= balance) {

            balance = balance - amount;

            System.out.println(amount + " withdrawn");

        } else {

            System.out.println("Insufficient Balance");
        }
    }



    // ENCAPSULATION WITH VALIDATION
    public void setAge(int age) {

        if (age > 0) {

            this.age = age;

        } else {

            System.out.println("Invalid Age");
        }
    }



    public int getAge() {
        return age;
    }
}



/*

=========================================================
                DATA HIDING
=========================================================

Encapsulation hides internal data from outside classes.

Achieved using:
- private
- protected
- public

---------------------------------------------------------
PRIVATE
---------------------------------------------------------

Accessible:
- Only inside same class

Example:

private int balance;

Most secure access modifier.

---------------------------------------------------------
PROTECTED
---------------------------------------------------------

Accessible:
- Same package
- Subclasses

---------------------------------------------------------
PUBLIC
---------------------------------------------------------

Accessible:
- Everywhere

=========================================================
                WHY ENCAPSULATION?
=========================================================

1. Security
2. Validation
3. Flexibility
4. Maintainability
5. Controlled Access

=========================================================
                BENEFITS
=========================================================

1. Data Security
   Prevents unauthorized access

2. Code Maintainability
   Easy to update

3. Code Reusability
   Reusable design

4. Flexibility
   Internal implementation can change safely

=========================================================
                REAL LIFE EXAMPLE
=========================================================

Bank Application

User cannot directly modify:
- balance
- account number

Access only through:
- deposit()
- withdraw()

This prevents invalid operations.

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. What is encapsulation?
Q2. How is encapsulation achieved?
Q3. Difference between abstraction and encapsulation?
Q4. What is data hiding?
Q5. Why use private variables?
Q6. What are getters and setters?
Q7. Can encapsulation exist without getters/setters?
Q8. Benefits of encapsulation?

=========================================================
                INTERVIEW ANSWER
=========================================================

Encapsulation in Java is the mechanism of wrapping
data and methods together into a single unit and
restricting direct access to internal data using
access modifiers like private.

It provides controlled access through public methods
such as getters and setters.

=========================================================

*/