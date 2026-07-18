package javaDeepDrive.objectClass;
/*
===============================================================================
ToString Method Deep Drive
===============================================================================

Method Signature:

public String toString()

Present inside:

java.lang.Object

===============================================================================
Purpose of toString()
===============================================================================

Used to return:

String representation of object.

Main purpose:
- readable output
- debugging
- logging
- object information display

===============================================================================
Default Implementation
===============================================================================

Inside Object class:

public String toString() {
    return getClass().getName() + "@" + Integer.toHexString(hashCode());
}

===============================================================================
Default Output Meaning
===============================================================================

Suppose:

class Student {
}

Student s = new Student();

System.out.println(s);

Output:

Student@36baf30c

Meaning:

Student
    -> class name

@
    -> separator

36baf30c
    -> hexadecimal hashCode

===============================================================================
Important Internal Concept
===============================================================================

When we do:

System.out.println(object);

Internally Java calls:

object.toString()

Automatically.

===============================================================================
Simple Example
===============================================================================
*/

class Student {

    String name;
    int age;
}

public class ToStringMethod {

    public static void main(String[] args) {

        Student student = new Student();

        student.name = "Pankaj";
        student.age = 25;

        // internally calls:
        // student.toString()
        System.out.println(student);
    }
}

/*
===============================================================================
Output
===============================================================================

Student@36baf30c

Not readable.

Because default Object class implementation executes.

===============================================================================
Why Override toString()
===============================================================================

Without overriding:
- unreadable logs
- difficult debugging
- object data hidden

With overriding:
- readable output
- easy debugging
- better monitoring

===============================================================================
Overriding toString()
===============================================================================
*/

class Employee {

    String name;
    int age;

    @Override
    public String toString() {

        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

class ToStringOverrideExample {

    public static void main(String[] args) {

        Employee employee = new Employee();

        employee.name = "Pankaj";
        employee.age = 25;

        System.out.println(employee);
    }
}

/*
===============================================================================
Output
===============================================================================

Employee{name='Pankaj', age=25}

Now output becomes readable.

===============================================================================
How JVM Works Internally
===============================================================================

Step 1:

System.out.println(employee)

Step 2:

println() checks object type.

Step 3:

Internally calls:

employee.toString()

Step 4:

Returned string printed on console.

===============================================================================
Real Industry Usage
===============================================================================

Used heavily in:

1. Logging
2. Debugging
3. Exception messages
4. Monitoring systems
5. API debugging
6. Framework internals

===============================================================================
Spring Boot Example
===============================================================================

Suppose entity:

class User {
    Long id;
    String name;
}

If logs print object:

User@72ab92

Very difficult debugging.

Better:

User{id=1, name='Pankaj'}

===============================================================================
Best Practice
===============================================================================

Include:
- important fields
- business identifiers

Avoid:
- passwords
- sensitive data
- huge nested objects

===============================================================================
Bad Practice Example
===============================================================================

@Override
public String toString() {
    return password;
}

Security risk.

===============================================================================
Modern Java Shortcut (Record)
===============================================================================

Records automatically generate:

- toString()
- equals()
- hashCode()

Example:
*/

record Person(String name, int age) {
}

class RecordToStringExample {

    public static void main(String[] args) {

        Person person = new Person("Pankaj", 25);

        System.out.println(person);
    }
}

/*
===============================================================================
Output
===============================================================================

Person[name=Pankaj, age=25]
Automatically generated.

===============================================================================
Difference Between print() and toString()
===============================================================================

print(object)
    -> internally calls toString()

Direct call:

object.toString()

Both same.

===============================================================================
Interview Question
===============================================================================

Q) Can toString() return null?

Yes.

But not recommended.

Because:
- logging issues
- debugging confusion

===============================================================================
Interview Question
===============================================================================

Q) Is toString() automatically called?

Yes.

Whenever object used in:
- println()
- logging
- string concatenation

===============================================================================
String Concatenation Example
===============================================================================
*/

class Product {

    String name = "Laptop";

    @Override
    public String toString() {
        return name;
    }
}

class StringConcatExample {

    public static void main(String[] args) {

        Product product = new Product();

        // internally calls product.toString()
        String result = "Product: " + product;

        System.out.println(result);
    }
}

/*
===============================================================================
Output
===============================================================================

Product: Laptop

===============================================================================
Most Important Interview Line
===============================================================================

The toString() method is used to provide string representation of an object.

Default implementation returns:

ClassName@HexadecimalHashCode

Mostly overridden for:
- readable logs
- debugging
- monitoring

===============================================================================
Quick Revision
===============================================================================

Object
    ↓
contains toString()
    ↓
default output unreadable
    ↓
override for readable output
    ↓
used heavily in logging/debugging

===============================================================================
*/
