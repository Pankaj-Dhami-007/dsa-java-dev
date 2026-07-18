package javaDeepDrive.objectClass;

/*
===============================================================================
Object Class Deep Drive
===============================================================================

Object class:
- root parent of all Java classes
- present inside java.lang package
- automatically available
- no need to import

Every class directly or indirectly extends:

Object

===============================================================================
Why Object Class Exists
===============================================================================

Without Object class:
- common behavior impossible
- polymorphism weak
- collections difficult
- framework support difficult

Object class provides:
- common methods
- common parent reference
- runtime support
- thread communication methods

===============================================================================
Compiler Behavior
===============================================================================

If we write:

class Student {
}

Compiler internally creates:

class Student extends Object {
}

Automatically.

===============================================================================
Real Use Case
===============================================================================

Because every class extends Object:

Object obj1 = new String("Hello");
Object obj2 = new Integer(10);
Object obj3 = new Student();

All valid.

This is foundation of:
- polymorphism
- collections
- generics
- frameworks

===============================================================================
Most Important Methods
===============================================================================

1. toString()
   -> object representation

2. equals()
   -> content comparison

3. hashCode()
   -> hashing support

4. getClass()
   -> runtime metadata

5. clone()
   -> object copy

6. finalize()
   -> cleanup before GC (deprecated)

7. wait()
8. notify()
9. notifyAll()
   -> thread communication

===============================================================================
Separate Deep Drive Files
===============================================================================

ToStringDeepDrive.java
EqualsDeepDrive.java
HashCodeDeepDrive.java
EqualsHashCodeDeepDrive.java
GetClassDeepDrive.java
CloneDeepDrive.java
FinalizeDeepDrive.java
ThreadCommunicationDeepDrive.java

===============================================================================
Most Asked Interview Topics
===============================================================================

Highest priority:

1. equals()
2. hashCode()
3. equals + hashCode contract
4. toString()
5. clone()
6. wait/notify

===============================================================================
Important Interview Line
===============================================================================

Object class is the root parent of all Java classes and provides common methods used for:

- comparison
- hashing
- threading
- reflection
- object representation

===============================================================================
Real Framework Usage
===============================================================================

Spring/Hibernate heavily use:

- equals()
- hashCode()
- getClass()
- toString()

Especially for:
- entity management
- caching
- proxy handling
- collections

===============================================================================
Best Practice
===============================================================================

Modern Java mostly uses:

- toString()
- equals()
- hashCode()
- getClass()

Very less usage:

- clone()
- finalize()

wait/notify mostly replaced by:

java.util.concurrent package

===============================================================================
Quick Revision
===============================================================================

Object Class
    ↓
Common parent of all classes
    ↓
Provides common methods
    ↓
Supports polymorphism + runtime features

===============================================================================
*/

public class ObjectClassDeepDrive {

}

