package javaDeepDrive.objectClass.hash_code;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/*
===============================================================================
hashCode() Method Deep Drive
===============================================================================

Method Signature:

public int hashCode()

Present inside:

java.lang.Object

===============================================================================
Purpose of hashCode()
===============================================================================

Used to generate:

Integer hash value of object.

Main purpose:
- hashing
- fast searching
- bucket calculation
- collection optimization

===============================================================================
Where hashCode() Used
===============================================================================

Used heavily in:

- HashMap
- HashSet
- Hashtable
- caching systems

===============================================================================
Default hashCode() Behavior
===============================================================================

Default Object class implementation generates:

memory related integer hash value.

Mostly:
- different objects
- different hashcodes

===============================================================================
Simple Example
===============================================================================
*/

class Student {

    String name;
}

public class HashCodeMethod {

    public static void main(String[] args) {

        Student s1 = new Student();

        Student s2 = new Student();

        System.out.println(s1.hashCode());

        System.out.println(s2.hashCode());
    }
}

/*
===============================================================================
Possible Output
===============================================================================

1510467688
1995265320

Different values because:
- different objects
- different references

===============================================================================
Important Point
===============================================================================

hashCode() returns:
- int value

Not actual memory address directly.

===============================================================================
Why hashCode() Important
===============================================================================

Suppose HashMap contains:
10 lakh objects.

Without hashCode():
- full search required
- performance slow

With hashCode():
- direct bucket access
- fast searching

===============================================================================
HashMap Internal Working
===============================================================================

Step 1:
Calls hashCode()

Step 2:
Bucket/index calculated

Step 3:
Object stored inside bucket

Step 4:
During searching:
directly jumps to bucket

===============================================================================
Internal Flow
===============================================================================

Object
   ↓
hashCode()
   ↓
bucket/index generated
   ↓
fast searching possible

===============================================================================
equals() + hashCode() Relation
===============================================================================

Most important concept.

Rule:

If:

x.equals(y) == true

Then:

x.hashCode() must also be same.

===============================================================================
Important Point
===============================================================================

Same hashCode DOES NOT mean:
- objects equal

But:

equal objects MUST have same hashCode.

===============================================================================
Wrong Example
===============================================================================
*/

class Employee {

    int id;

    Employee(int id) {

        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null
                ||
                getClass() != obj.getClass()) {

            return false;
        }

        Employee employee = (Employee) obj;

        return id == employee.id;
    }
}

/*
===============================================================================
Problem
===============================================================================

equals() overridden
BUT hashCode() not overridden.

This breaks contract.

Collections behave incorrectly.

===============================================================================
Correct Example
===============================================================================
*/

class User {

    int id;
    String name;

    User(int id, String name) {

        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null
                ||
                getClass() != obj.getClass()) {

            return false;
        }

        User user = (User) obj;

        return id == user.id
                &&
                name.equals(user.name);
    }

    @Override
    public int hashCode() {

        int result = id;

        result =
                31 * result
                        +
                        name.hashCode();

        return result;
    }
}

/*
===============================================================================
Why 31 Used?
===============================================================================

31 is prime number.

Benefits:
- better distribution
- fewer collisions
- JVM optimization friendly

===============================================================================
hashCode() Override Example
===============================================================================
*/

class HashCodeOverrideExample {

    public static void main(String[] args) {

        User u1 =
                new User(1, "Pankaj");

        User u2 =
                new User(1, "Pankaj");

        System.out.println(
                u1.equals(u2));

        System.out.println(
                u1.hashCode());

        System.out.println(
                u2.hashCode());
    }
}

/*
===============================================================================
Possible Output
===============================================================================

true
87117826
87117826

Same because:
- logically equal
- same hashCode

===============================================================================
HashSet Example
===============================================================================
*/

class HashSetExample {

    public static void main(String[] args) {

        Set<User> users =
                new HashSet<>();

        users.add(
                new User(1, "Pankaj"));

        users.add(
                new User(1, "Pankaj"));

        System.out.println(
                users.size());
    }
}

/*
===============================================================================
Output
===============================================================================

1

Why?

Because:
- equals() says equal
- hashCode() same
- duplicate prevented

===============================================================================
What is Hash Collision?
===============================================================================

Collision means:

Different objects produce:
same hashCode.

Example:

Object1 -> 101
Object2 -> 101

Collision occurred.

===============================================================================
How HashMap Handles Collision
===============================================================================

Java internally uses:
- LinkedList
- Balanced Tree (Java 8+)

===============================================================================
Bad Practice
===============================================================================

@Override
public int hashCode() {
    return 1;
}

Very bad.

Why?
- all objects same bucket
- performance destroyed

===============================================================================
Modern Java Approach
===============================================================================

Using:

Objects.hash()

Cleaner code.

===============================================================================
Example
===============================================================================
*/

class Product {

    int id;
    String name;

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}

/*
===============================================================================
String hashCode()
===============================================================================

String class overrides hashCode().

Same content:
same hashCode.

===============================================================================
Example
===============================================================================
*/

class StringHashCodeExample {

    public static void main(String[] args) {

        String s1 = "Java";

        String s2 = "Java";

        System.out.println(
                s1.hashCode());

        System.out.println(
                s2.hashCode());
    }
}

/*
===============================================================================
Possible Output
===============================================================================

2301506
2301506

Same because:
same content.

===============================================================================
Records and hashCode()
===============================================================================

Records automatically generate:
- equals()
- hashCode()
- toString()

===============================================================================
Example
===============================================================================
*/

record Person(String name, int age) {
}

class RecordHashCodeExample {

    public static void main(String[] args) {

        Person p1 =
                new Person("Pankaj", 25);

        Person p2 =
                new Person("Pankaj", 25);

        System.out.println(
                p1.hashCode());

        System.out.println(
                p2.hashCode());
    }
}

/*
===============================================================================
Important Interview Questions
===============================================================================

Q) What is purpose of hashCode()?

Used for:
- hashing
- bucket calculation
- fast searching

-------------------------------------------------------------------------------

Q) Why override hashCode()?

Because:
equal objects must have same hashCode.

-------------------------------------------------------------------------------

Q) Can two different objects have same hashCode()?

Yes.
Collision possible.

-------------------------------------------------------------------------------

Q) If hashCode same, are objects equal?

No.

-------------------------------------------------------------------------------

Q) If equals() true, must hashCode same?

YES.
Mandatory rule.

-------------------------------------------------------------------------------

Q) What happens if equals() overridden but hashCode() not overridden?

Collections behave incorrectly.

-------------------------------------------------------------------------------

Q) Which collections depend heavily on hashCode()?

- HashMap
- HashSet
- Hashtable

-------------------------------------------------------------------------------

Q) Why 31 used?

Because:
- prime number
- better distribution
- JVM optimization friendly

===============================================================================
Most Important Interview Line
===============================================================================

hashCode() generates integer hash value for efficient storage and retrieval in hash-based collections.

If two objects are equal using equals(),
their hashCode() must also be equal.

===============================================================================
Quick Revision
===============================================================================

Object
   ↓
hashCode()
   ↓
bucket generated
   ↓
fast searching

equals() true
   ↓
hashCode() must same

===============================================================================
*/