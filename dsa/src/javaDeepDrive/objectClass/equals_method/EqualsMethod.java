package javaDeepDrive.objectClass.equals_method;

/*
===============================================================================
equals() Method Deep Drive
===============================================================================

Method Signature:

public boolean equals(Object obj)

Present inside:

java.lang.Object

===============================================================================
Purpose of equals()
===============================================================================

Used to compare:

Two objects logically.

Main purpose:
- content comparison
- logical equality
- business equality

===============================================================================
Default equals() Behavior
===============================================================================

Default Object class implementation compares:

Memory references.

Internal implementation:

public boolean equals(Object obj) {
    return (this == obj);
}

===============================================================================
Meaning of ==
===============================================================================

== compares:
- memory address
- object reference

Not actual object data.

===============================================================================
Simple Example
===============================================================================
*/

class Student {

    String name;
}

public class EqualsMethod {

    public static void main(String[] args) {

        Student s1 = new Student();
        s1.name = "Pankaj";

        Student s2 = new Student();
        s2.name = "Pankaj";

        // compares references
        System.out.println(s1 == s2);

        // internally also compares references
        System.out.println(s1.equals(s2));
    }
}

/*
===============================================================================
Output
===============================================================================

false
false

Why?

Because:
- different objects
- different memory locations

Even though content same.

===============================================================================
Memory Diagram
===============================================================================

s1 ---------> Object1
s2 ---------> Object2

Different references.

===============================================================================
Problem With Default equals()
===============================================================================

Real applications usually need:

- content comparison
- business comparison

Not memory address comparison.

===============================================================================
Why Override equals()
===============================================================================

Without overriding:
- logical comparison impossible
- duplicate detection fails
- collections behave incorrectly

===============================================================================
Overriding equals()
===============================================================================
*/

class Employee {

    int id;
    String name;

    Employee(int id, String name) {

        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {

        // same object check
        if (this == obj) {
            return true;
        }

        // null check
        if (obj == null) {
            return false;
        }

        // type check
        if (getClass() != obj.getClass()) {
            return false;
        }

        // downcasting
        Employee employee = (Employee) obj;

        // field comparison
        return this.id == employee.id
                &&
                this.name.equals(employee.name);
    }
}

/*
===============================================================================
equals() Internal Working
===============================================================================

Step 1:
Object passed as parameter.

Step 2:
Reference check happens.

Step 3:
Null check happens.

Step 4:
Type check happens.

Step 5:
Object casting happens.

Step 6:
Fields compared.

Step 7:
true/false returned.

===============================================================================
equals() Override Example
===============================================================================
*/

class EqualsOverrideExample {

    public static void main(String[] args) {

        Employee e1 =
                new Employee(1, "Pankaj");

        Employee e2 =
                new Employee(1, "Pankaj");

        System.out.println(e1.equals(e2));
    }
}

/*
===============================================================================
Output
===============================================================================

true

Because:
- id same
- name same

Logical comparison successful.

===============================================================================
Difference Between == and equals()
===============================================================================

==

- operator
- compares references
- compares primitives
- cannot be overridden

equals()

- method
- compares content logically
- can be overridden

===============================================================================
String Example
===============================================================================
*/

class StringEqualsExample {

    public static void main(String[] args) {

        String s1 = new String("Java");
        String s2 = new String("Java");

        System.out.println(s1 == s2);

        System.out.println(s1.equals(s2));
    }
}

/*
===============================================================================
Output
===============================================================================

false
true

Why?

String class overrides equals().

It compares:
- actual characters/content

===============================================================================
Important equals() Contract
===============================================================================

equals() must follow:

1. Reflexive
2. Symmetric
3. Transitive
4. Consistent
5. null comparison false

===============================================================================
1. Reflexive
===============================================================================

x.equals(x)

must always return:

true

===============================================================================
2. Symmetric
===============================================================================

If:

x.equals(y) == true

Then:

y.equals(x)

must also be true.

===============================================================================
3. Transitive
===============================================================================

If:

x.equals(y) == true

and

y.equals(z) == true

Then:

x.equals(z)

must also be true.

===============================================================================
4. Consistent
===============================================================================

Multiple calls should give same result
if object data unchanged.

===============================================================================
5. null Comparison
===============================================================================

x.equals(null)

must always return:

false

===============================================================================
Common Mistakes
===============================================================================

1. Forgetting null check

2. Using == for String comparison

Wrong:

this.name == employee.name

Correct:

this.name.equals(employee.name)

3. Forgetting hashCode()

4. Wrong casting

===============================================================================
Real Industry Use Cases
===============================================================================

Used heavily in:

- HashMap
- HashSet
- Hibernate
- Spring
- cache systems
- duplicate detection

===============================================================================
Important Note
===============================================================================

Whenever overriding equals():

Always override hashCode() also.

Because:
- both work together

Very important interview topic.

===============================================================================
Modern Java Record Example
===============================================================================

Records automatically generate:
- equals()
- hashCode()
- toString()

Example:
*/

record Person(String name, int age) {
}

class RecordEqualsExample {

    public static void main(String[] args) {

        Person p1 =
                new Person("Pankaj", 25);

        Person p2 =
                new Person("Pankaj", 25);

        System.out.println(p1.equals(p2));
    }
}

/*
===============================================================================
Output
===============================================================================

true

Automatically generated.

===============================================================================
Important Interview Questions
===============================================================================

Q) Difference between == and equals()?

== compares:
- references

equals() compares:
- logical content

-------------------------------------------------------------------------------

Q) Why override equals()?

To compare object data logically.

-------------------------------------------------------------------------------

Q) Why equals() parameter type is Object?

Because every class extends Object.

-------------------------------------------------------------------------------

Q) Can equals() be overloaded?

Yes.

But JVM/collections use:

equals(Object obj)

So overriding required.

===============================================================================
Most Important Interview Line
===============================================================================

equals() method is used for logical/content comparison between two objects.

Default Object class implementation compares memory references using == operator.

===============================================================================
Quick Revision
===============================================================================

Default equals()
        ↓
Reference comparison
        ↓
Not useful for business logic
        ↓
Override equals()
        ↓
Logical/content comparison

===============================================================================
*/