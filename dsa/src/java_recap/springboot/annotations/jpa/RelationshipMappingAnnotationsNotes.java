package java_recap.springboot.annotations.jpa;

/*

=================================================================
        RELATIONSHIP MAPPING ANNOTATIONS
=================================================================

Annotations:
-------------
@OneToOne
@OneToMany
@ManyToOne
@ManyToMany
@JoinColumn
@JoinTable

Core Concepts:
---------------
1. Entity Relationships
2. Foreign Keys
3. ORM Association Mapping
4. Bidirectional Mapping
5. Cascade Operations
6. Fetch Strategies
7. Hibernate Proxies
8. N+1 Problem

=================================================================
                WHY RELATIONSHIPS EXIST?
=================================================================

Real-world systems contain connected data.

=================================================================
                EXAMPLES
=================================================================

User → Orders
Order → Products
Student → Courses
Employee → Department

=================================================================
                DATABASE WORLD
=================================================================

Relationships handled using:
-----------------------------

Foreign Keys

=================================================================
                OBJECT WORLD PROBLEM
=================================================================

Java objects use references,
NOT foreign keys.

=================================================================
                ORM CHALLENGE
=================================================================

How to map:
------------

Java Object References
        ↔
Database Foreign Keys

=================================================================
                SOLUTION
=================================================================

Relationship Mapping Annotations.

=================================================================
                TYPES OF RELATIONSHIPS
=================================================================

1. One-to-One
2. One-to-Many
3. Many-to-One
4. Many-to-Many

=================================================================
                1. @OneToOne
=================================================================

One entity connected to exactly one entity.

=================================================================
                REAL EXAMPLE
=================================================================

User ↔ Passport

One user has one passport.
One passport belongs to one user.

=================================================================
                DATABASE STRUCTURE
=================================================================

users table
passport table

passport.user_id → FK

=================================================================
                ENTITY EXAMPLE
=================================================================

@Entity
public class User {

    @OneToOne
    @JoinColumn(name = "passport_id")
    private Passport passport;

}

=================================================================
                WHAT @JoinColumn DOES?
=================================================================

Defines foreign key column.

=================================================================
                GENERATED COLUMN
=================================================================

passport_id

=================================================================
                INTERNAL WORKING
=================================================================

Hibernate stores:
------------------

passport_id FK in database.

Java object reference mapped
to foreign key internally.

=================================================================
                BIDIRECTIONAL MAPPING
=================================================================

@Entity
public class Passport {

    @OneToOne(mappedBy = "passport")
    private User user;

}

=================================================================
                mappedBy MEANING
=================================================================

This side is NOT owner.

Foreign key managed by other entity.

=================================================================
                IMPORTANT
=================================================================

Only ONE side owns relationship.

=================================================================
                WHY IMPORTANT?
=================================================================

Avoid duplicate foreign keys.

=================================================================
                REAL PROJECT USE CASE
=================================================================

1. User ↔ Profile
2. Employee ↔ IDCard
3. Order ↔ Invoice

=================================================================
                2. @OneToMany
=================================================================

One entity connected to multiple entities.

=================================================================
                REAL EXAMPLE
=================================================================

One User → Many Orders

=================================================================
                DATABASE STRUCTURE
=================================================================

users table
orders table

orders.user_id FK

=================================================================
                ENTITY EXAMPLE
=================================================================

@Entity
public class User {

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

}

=================================================================
                MANY SIDE
=================================================================

@Entity
public class Order {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

=================================================================
                IMPORTANT
=================================================================

@OneToMany usually inverse side.

@ManyToOne usually owning side.

=================================================================
                WHY?
=================================================================

Foreign key exists on many-side table.

=================================================================
                INTERNAL WORKING
=================================================================

Hibernate loads collection lazily
using proxy collection.

=================================================================
                IMPORTANT INTERVIEW TOPIC
=================================================================

LAZY LOADING

=================================================================
                WHAT IS LAZY LOADING?
=================================================================

Related entities loaded ONLY when needed.

=================================================================
                EXAMPLE
=================================================================

User loaded.
Orders NOT loaded immediately.

Only when:
------------

user.getOrders()

called.

=================================================================
                WHY IMPORTANT?
=================================================================

Performance optimization.

=================================================================
                DEFAULT FETCH TYPES
=================================================================

@OneToMany
------------
LAZY

@ManyToOne
------------
EAGER

=================================================================
                VERY IMPORTANT
=================================================================

Most Hibernate performance problems
come from bad fetch strategy.

=================================================================
                3. @ManyToOne
=================================================================

Many entities belong to one entity.

=================================================================
                REAL EXAMPLE
=================================================================

Many Employees → One Department

=================================================================
                ENTITY EXAMPLE
=================================================================

@ManyToOne
@JoinColumn(name = "department_id")
private Department department;

=================================================================
                IMPORTANT
=================================================================

Most commonly used relationship.

=================================================================
                WHY?
=================================================================

Most database relations are many-to-one.

=================================================================
                REAL PROJECT USE CASE
=================================================================

1. Orders → User
2. Employee → Department
3. Product → Category

=================================================================
                4. @ManyToMany
=================================================================

Many entities connected to many entities.

=================================================================
                REAL EXAMPLE
=================================================================

Students ↔ Courses

One student joins many courses.
One course has many students.

=================================================================
                DATABASE STRUCTURE
=================================================================

student table
course table

AND

join table:
------------

student_course

=================================================================
                ENTITY EXAMPLE
=================================================================

@ManyToMany

@JoinTable(
    name = "student_course",

    joinColumns = @JoinColumn(
        name = "student_id"
    ),

    inverseJoinColumns = @JoinColumn(
        name = "course_id"
    )
)

private List<Course> courses;

=================================================================
                WHAT @JoinTable DOES?
=================================================================

Creates intermediate join table.

=================================================================
                GENERATED TABLE
=================================================================

student_id | course_id

=================================================================
                IMPORTANT
=================================================================

@ManyToMany internally always uses
join table.

=================================================================
                REAL PROJECT USE CASE
=================================================================

1. Users ↔ Roles
2. Students ↔ Courses
3. Products ↔ Tags

=================================================================
                CASCADE OPERATIONS
=================================================================

Most important interview topic.

=================================================================
                WHAT IS CASCADE?
=================================================================

Parent operation automatically affects child.

=================================================================
                EXAMPLE
=================================================================

User saved
    ↓
Orders automatically saved

=================================================================
                TYPES
=================================================================

PERSIST
MERGE
REMOVE
ALL

=================================================================
                EXAMPLE
=================================================================

@OneToMany(
    cascade = CascadeType.ALL
)

=================================================================
                DANGER
=================================================================

Cascade REMOVE can delete huge data accidentally.

=================================================================
                FETCH TYPES
=================================================================

EAGER
LAZY

=================================================================
                EAGER
=================================================================

Load relationship immediately.

=================================================================
                LAZY
=================================================================

Load only when needed.

=================================================================
                IMPORTANT
=================================================================

LAZY usually better for performance.

=================================================================
                N+1 PROBLEM
=================================================================

Most important Hibernate problem.

=================================================================
                EXAMPLE
=================================================================

Load 100 users
    ↓
Each user loads orders separately

=================================================================
                RESULT
=================================================================

1 query for users
+
100 queries for orders

=================================================================
                TOTAL
=================================================================

101 queries

=================================================================
                WHY BAD?
=================================================================

Huge performance issue.

=================================================================
                SOLUTIONS
=================================================================

1. JOIN FETCH
2. EntityGraph
3. Batch fetching

=================================================================
                HIBERNATE PROXY
=================================================================

Used internally for lazy loading.

=================================================================
                WHAT IS PROXY?
=================================================================

Placeholder object created by Hibernate.

Actual data loaded later.

=================================================================
                LAZYINITIALIZATIONEXCEPTION
=================================================================

Very famous Hibernate exception.

=================================================================
                WHY HAPPENS?
=================================================================

Lazy object accessed after session closed.

=================================================================
                EXAMPLE
=================================================================

user.getOrders()

outside transaction/session.

=================================================================
                SOLUTION
=================================================================

1. Fetch inside transaction
2. JOIN FETCH
3. DTO projection

=================================================================
                OWNING SIDE
=================================================================

Relationship side managing foreign key.

=================================================================
                IMPORTANT
=================================================================

Only owning side updates relationship in DB.

=================================================================
                mappedBy
=================================================================

Used to define inverse side.

=================================================================
                INTERVIEW IMPORTANT
=================================================================

Q:
---
Difference between @OneToMany
and @ManyToOne?

=================================================================
                ANSWER
=================================================================

@OneToMany:
-------------
One parent → multiple children.

@ManyToOne:
-------------
Many children → one parent.

Foreign key exists on many-side table.

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
Why @ManyToOne default fetch is EAGER?

=================================================================
                ANSWER
=================================================================

Because child entity usually requires
parent information immediately.

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
What is N+1 problem?

=================================================================
                ANSWER
=================================================================

When ORM executes one query for parent data
and additional queries for each child entity,
causing major performance issues.

=================================================================
                TRICKY INTERVIEW QUESTION
=================================================================

Q:
---
Why @ManyToMany avoided in enterprise apps?

=================================================================
                ANSWER
=================================================================

Because join table often later needs:
--------------------------------------

1. Extra columns
2. Metadata
3. Better control

So explicit join entity preferred.

=================================================================
                ANOTHER TRICKY QUESTION
=================================================================

Q:
---
Why LAZY loading preferred?

=================================================================
                ANSWER
=================================================================

Avoid unnecessary DB queries
and improve performance.

=================================================================
                REAL PROJECT BEST PRACTICE
=================================================================

1. Prefer LAZY loading
2. Avoid EAGER everywhere
3. Use DTO projections
4. Avoid bidirectional relationships unnecessarily
5. Avoid exposing entities directly in APIs

=================================================================
                COMMON MISTAKES
=================================================================

1. Infinite recursion in JSON

User → Orders → User → Orders

2. EAGER loading everywhere

3. Using entities directly in APIs

4. Cascade REMOVE misuse

5. Ignoring N+1 problem

=================================================================
                REAL INTERVIEW ANSWER
=================================================================

Q. Explain internal working of Hibernate relationships.

Answer:
--------
Hibernate maps object references
to foreign key relationships internally.
It uses proxies for lazy loading,
tracks associations in persistence context,
and generates SQL joins/queries automatically.

=================================================================
                SUMMARY
=================================================================

@OneToOne
-----------
One ↔ One relationship

@OneToMany
------------
One ↔ Many relationship

@ManyToOne
------------
Many ↔ One relationship

@ManyToMany
-------------
Many ↔ Many relationship

@JoinColumn
-------------
Defines foreign key.

@JoinTable
------------
Defines join table.

Hibernate internally manages:
------------------------------

1. Foreign keys
2. Lazy loading
3. Proxies
4. Cascading
5. SQL generation

=================================================================

*/

public class RelationshipMappingAnnotationsNotes {
}