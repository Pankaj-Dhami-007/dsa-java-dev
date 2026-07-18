package javaDeepDrive.objectClass.get_class;

/*
===============================================================================
getClass() Method Deep Drive
===============================================================================

Method Signature:

public final Class<?> getClass()

Present inside:

java.lang.Object

===============================================================================
Purpose of getClass()
===============================================================================

Used to get:

Runtime class information of object.

Main purpose:
- runtime metadata
- reflection
- framework internals
- dynamic operations

===============================================================================
Important Point
===============================================================================

getClass() is:

final method

Meaning:
- cannot override

===============================================================================
Return Type
===============================================================================

Returns:

Class<?> object

This Class object contains:
- class name
- methods
- fields
- constructors
- package info

===============================================================================
Simple Example
===============================================================================
*/

class Student {

}

public class GetClassMethod {

    public static void main(String[] args) {

        Student student =
                new Student();

        System.out.println(
                student.getClass());
    }
}

/*
===============================================================================
Output
===============================================================================

class javaDeepDrive.objectClass.Student

===============================================================================
Important Point
===============================================================================

getClass() returns:

Class object representation.

===============================================================================
Getting Only Class Name
===============================================================================
*/

class Employee {

}

class GetClassNameExample {

    public static void main(String[] args) {

        Employee employee =
                new Employee();

        System.out.println(
                employee.getClass().getName());
    }
}

/*
===============================================================================
Output
===============================================================================

javaDeepDrive.objectClass.Employee

===============================================================================
Difference Between getName() and getSimpleName()
===============================================================================
*/

class User {

}

class GetSimpleNameExample {

    public static void main(String[] args) {

        User user =
                new User();

        System.out.println(
                user.getClass().getName());

        System.out.println(
                user.getClass().getSimpleName());
    }
}

/*
===============================================================================
Output
===============================================================================

javaDeepDrive.objectClass.User

User

===============================================================================
Meaning
===============================================================================

getName()
- full package name

getSimpleName()
- only class name

===============================================================================
Internal Working
===============================================================================

Every object internally stores:
- runtime type metadata

getClass() returns that metadata object.

===============================================================================
Flow
===============================================================================

Object
   ↓
getClass()
   ↓
returns Class object
   ↓
metadata accessible

===============================================================================
Checking Runtime Type
===============================================================================
*/

class Animal {

}

class Dog extends Animal {

}

class RuntimeTypeExample {

    public static void main(String[] args) {

        Animal animal =
                new Dog();

        System.out.println(
                animal.getClass());
    }
}

/*
===============================================================================
Output
===============================================================================

class javaDeepDrive.objectClass.Dog

===============================================================================
Important Point
===============================================================================

getClass() always returns:
- actual runtime object type

Not reference type.

===============================================================================
Real Industry Use Cases
===============================================================================

Used heavily in:
- Spring Framework
- Hibernate
- Reflection API
- Serialization
- JSON libraries
- Dependency Injection

===============================================================================
Reflection Example
===============================================================================
*/

class ReflectionExample {

    public static void main(String[] args) {

        String value = "Java";

        Class<?> clazz =
                value.getClass();

        System.out.println(
                clazz.getName());

        System.out.println(
                clazz.getMethods().length);
    }
}

/*
===============================================================================
Possible Output
===============================================================================

java.lang.String
90

===============================================================================
Meaning
===============================================================================

getMethods()
returns:
- all public methods

===============================================================================
Accessing Constructors
===============================================================================
*/

class ConstructorExample {

    public static void main(String[] args) {

        Student student =
                new Student();

        Class<?> clazz =
                student.getClass();

        System.out.println(
                clazz.getConstructors().length);
    }
}

/*
===============================================================================
Accessing Fields
===============================================================================
*/

class FieldExample {

    String name;
    int age;
}

class FieldReflectionExample {

    public static void main(String[] args) {

        FieldExample fieldExample =
                new FieldExample();

        Class<?> clazz =
                fieldExample.getClass();

        System.out.println(
                clazz.getDeclaredFields().length);
    }
}

/*
===============================================================================
Possible Output
===============================================================================

2

===============================================================================
getClass() vs instanceof
===============================================================================

instanceof
- checks inheritance also

getClass()
- checks exact runtime class

===============================================================================
Example
===============================================================================
*/

class Parent {

}

class Child extends Parent {

}

class GetClassVsInstanceOf {

    public static void main(String[] args) {

        Parent parent =
                new Child();

        System.out.println(
                parent instanceof Parent);

        System.out.println(
                parent.getClass() == Parent.class);
    }
}

/*
===============================================================================
Output
===============================================================================

true
false

===============================================================================
Why?
===============================================================================

instanceof:
- checks inheritance hierarchy

getClass():
- exact runtime class comparison

===============================================================================
Using .class Keyword
===============================================================================
*/

class ClassKeywordExample {

    public static void main(String[] args) {

        Class<Student> clazz =
                Student.class;

        System.out.println(
                clazz.getName());
    }
}

/*
===============================================================================
Difference
===============================================================================

Student.class
- compile time class reference

object.getClass()
- runtime class reference

===============================================================================
Framework Usage
===============================================================================

Spring/Hibernate use getClass() for:
- proxies
- metadata analysis
- dependency injection
- entity management

===============================================================================
Important Note
===============================================================================

Many frameworks create:
- dynamic proxy classes

So actual runtime class may differ.

===============================================================================
Example
===============================================================================

User object may internally become:

User$$SpringProxy

===============================================================================
Modern Java Records
===============================================================================

Records also inherit Object class.

So records also support:
- getClass()

===============================================================================
Example
===============================================================================
*/

record Person(String name, int age) {
}

class RecordGetClassExample {

    public static void main(String[] args) {

        Person person =
                new Person("Pankaj", 25);

        System.out.println(
                person.getClass());
    }
}

/*
===============================================================================
Important Interview Questions
===============================================================================

Q) What does getClass() return?

Returns:
- Class object containing runtime metadata.

-------------------------------------------------------------------------------

Q) Can getClass() be overridden?

No.

Because:
- final method

-------------------------------------------------------------------------------

Q) Difference between getClass() and instanceof?

getClass()
- exact runtime type

instanceof
- inheritance hierarchy checking

-------------------------------------------------------------------------------

Q) Which package contains Class class?

java.lang

-------------------------------------------------------------------------------

Q) What is reflection?

Ability to inspect/manipulate class metadata at runtime.

-------------------------------------------------------------------------------

Q) Difference between .class and getClass()?

.class
- compile time

getClass()
- runtime

===============================================================================
Most Important Interview Line
===============================================================================

getClass() returns Class object containing runtime metadata of the current object.

Used heavily in reflection and framework internals.

===============================================================================
Quick Revision
===============================================================================

Object
   ↓
getClass()
   ↓
returns Class object
   ↓
runtime metadata accessible

===============================================================================
*/