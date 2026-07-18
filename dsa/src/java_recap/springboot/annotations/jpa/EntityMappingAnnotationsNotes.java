package java_recap.springboot.annotations.jpa;

/*

=================================================================
            ENTITY MAPPING ANNOTATIONS
=================================================================

Annotations:
-------------
@Entity
@Table
@Id
@GeneratedValue
@Column
@Transient

Core Concepts:
---------------
1. ORM
2. Hibernate
3. Entity Lifecycle
4. Object-Relational Mapping
5. Persistence Context
6. Primary Keys
7. Database Mapping

=================================================================
                WHY JPA EXISTS?
=================================================================

Before ORM:
------------

Developers manually wrote:
---------------------------

1. SQL Queries
2. ResultSet mapping
3. Object conversion
4. Connection management

=================================================================
                PROBLEMS
=================================================================

1. Huge boilerplate code
2. Error-prone mapping
3. Database dependency
4. Difficult maintenance
5. Productivity issues

=================================================================
                EXAMPLE WITHOUT ORM
=================================================================

ResultSet rs = stmt.executeQuery();

while(rs.next()) {

    User user = new User();

    user.setId(rs.getLong("id"));

    user.setName(rs.getString("name"));

}

=================================================================
                SOLUTION
=================================================================

ORM Frameworks.

=================================================================
                WHAT IS ORM?
=================================================================

ORM =
------
Object Relational Mapping

=================================================================
                MEANING
=================================================================

Maps:
------

Java Objects
    ↔
Database Tables

=================================================================
                IMPORTANT
=================================================================

ORM bridges gap between:
-------------------------

Object-Oriented world
and
Relational Database world.

=================================================================
                WHAT IS JPA?
=================================================================

JPA =
------
Jakarta Persistence API

=================================================================
                IMPORTANT
=================================================================

JPA is NOT implementation.

JPA is:
---------
Specification/Standard.

=================================================================
                IMPLEMENTATIONS
=================================================================

1. Hibernate
2. EclipseLink
3. OpenJPA

=================================================================
                MOST USED
=================================================================

Hibernate

=================================================================
                WHAT HIBERNATE DOES?
=================================================================

1. SQL generation
2. Object mapping
3. Dirty checking
4. Caching
5. Lazy loading
6. Transaction management

=================================================================
                1. @Entity
=================================================================

Most important JPA annotation.

=================================================================
                DEFINITION
=================================================================

@Entity tells Hibernate:

"This class maps to database table."

=================================================================
                EXAMPLE
=================================================================

@Entity
public class User {

}

=================================================================
                WHAT HAPPENS INTERNALLY?
=================================================================

Hibernate scans entity classes
during startup.

Creates metadata information.

=================================================================
                METADATA STORED
=================================================================

1. Table name
2. Columns
3. Relationships
4. Primary key
5. Constraints

=================================================================
                ENTITY INSTANCE
=================================================================

User object becomes:
---------------------

Persistent Entity

=================================================================
                IMPORTANT RULES
=================================================================

Entity class should:
---------------------

1. Have default constructor
2. Be non-final
3. Have primary key
4. Be managed by JPA

=================================================================
                ENTITY LIFECYCLE
=================================================================

TRANSIENT
    ↓
PERSISTENT
    ↓
DETACHED
    ↓
REMOVED

=================================================================
                VERY IMPORTANT
=================================================================

Most asked Hibernate interview topic.

=================================================================
                TRANSIENT STATE
=================================================================

Object exists only in memory.

NOT stored in DB.

Example:
---------

User user = new User();

=================================================================
                PERSISTENT STATE
=================================================================

Entity managed by Hibernate.

Changes tracked automatically.

=================================================================
                DETACHED STATE
=================================================================

Entity exists but no longer managed
by Persistence Context.

=================================================================
                REMOVED STATE
=================================================================

Entity marked for deletion.

=================================================================
                2. @Table
=================================================================

Used to customize table mapping.

=================================================================
                EXAMPLE
=================================================================

@Entity
@Table(name = "users")
public class User {

}

=================================================================
                WHY IMPORTANT?
=================================================================

Class name may differ from table name.

=================================================================
                DEFAULT BEHAVIOR
=================================================================

Class name becomes table name.

=================================================================
                ADVANCED FEATURES
=================================================================

@Table(
    name = "users",
    schema = "public",
    indexes = {
        @Index(name = "idx_email",
               columnList = "email")
    }
)

=================================================================
                REAL PROJECT USE CASE
=================================================================

1. Index optimization
2. Schema management
3. Legacy database mapping

=================================================================
                3. @Id
=================================================================

Defines primary key.

=================================================================
                DEFINITION
=================================================================

@Id marks unique identifier
for entity.

=================================================================
                EXAMPLE
=================================================================

@Id
private Long id;

=================================================================
                WHY PRIMARY KEY IMPORTANT?
=================================================================

Hibernate uses primary key for:
--------------------------------

1. Entity identification
2. Caching
3. Updates
4. Deletes
5. Relationships

=================================================================
                INTERNAL WORKING
=================================================================

Persistence Context stores entities
using primary key internally.

=================================================================
                VERY IMPORTANT
=================================================================

Without @Id:
-------------
Entity invalid.

Application startup fails.

=================================================================
                4. @GeneratedValue
=================================================================

Used for automatic ID generation.

=================================================================
                EXAMPLE
=================================================================

@Id
@GeneratedValue
private Long id;

=================================================================
                WHY IMPORTANT?
=================================================================

Avoid manual ID handling.

=================================================================
                GENERATION STRATEGIES
=================================================================

1. AUTO
2. IDENTITY
3. SEQUENCE
4. TABLE

=================================================================
                1. AUTO
=================================================================

Hibernate chooses strategy automatically.

=================================================================
                2. IDENTITY
=================================================================

Database auto-increment.

=================================================================
                EXAMPLE
=================================================================

MySQL AUTO_INCREMENT

=================================================================
                IMPORTANT
=================================================================

Insert query executes immediately
to fetch generated ID.

=================================================================
                PERFORMANCE IMPACT
=================================================================

Batch inserts become difficult.

=================================================================
                3. SEQUENCE
=================================================================

Uses database sequence.

Mostly used in:
----------------

PostgreSQL, Oracle

=================================================================
                BENEFITS
=================================================================

Better performance than IDENTITY.

Supports batch inserts.

=================================================================
                4. TABLE
=================================================================

Stores IDs in separate table.

Rarely used.

=================================================================
                INTERVIEW IMPORTANT
=================================================================

Q:
---
Difference between IDENTITY
and SEQUENCE?

=================================================================
                ANSWER
=================================================================

IDENTITY:
-----------
Uses auto-increment column.
Insert happens immediately.

SEQUENCE:
-----------
Uses sequence object.
Better batching/performance.

=================================================================
                5. @Column
=================================================================

Used to customize column mapping.

=================================================================
                EXAMPLE
=================================================================

@Column(name = "user_name")
private String username;

=================================================================
                DEFAULT BEHAVIOR
=================================================================

Field name becomes column name.

=================================================================
                ADVANCED FEATURES
=================================================================

@Column(
    nullable = false,
    unique = true,
    length = 100
)

=================================================================
                REAL PROJECT USE CASE
=================================================================

1. Unique email
2. Column length restriction
3. Non-null constraints

=================================================================
                IMPORTANT
=================================================================

@Column affects:
-----------------

Database schema generation.

=================================================================
                6. @Transient
=================================================================

Very important interview annotation.

=================================================================
                DEFINITION
=================================================================

@Transient tells Hibernate:

"Do NOT persist this field."

=================================================================
                EXAMPLE
=================================================================

@Transient
private String confirmPassword;

=================================================================
                WHY IMPORTANT?
=================================================================

Some fields needed only in application,
NOT database.

=================================================================
                REAL PROJECT USE CASE
=================================================================

1. confirmPassword
2. Temporary calculations
3. Derived fields
4. Runtime-only values

=================================================================
                INTERNAL WORKING
=================================================================

Hibernate ignores field completely.

No column generated.

=================================================================
                IMPORTANT DIFFERENCE
=================================================================

Java transient keyword
and
JPA @Transient
are different.

=================================================================
                JAVA transient
=================================================================

Used for serialization.

=================================================================
                JPA @Transient
=================================================================

Used for database persistence exclusion.

=================================================================
                REAL ENTITY EXAMPLE
=================================================================

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    @Transient
    private String confirmPassword;

}

=================================================================
                PERSISTENCE CONTEXT
=================================================================

Most important Hibernate concept.

=================================================================
                DEFINITION
=================================================================

Persistence Context =
----------------------

First-level cache managing entity lifecycle.

=================================================================
                WHAT DOES IT DO?
=================================================================

1. Tracks entities
2. Detects changes
3. Synchronizes with DB

=================================================================
                DIRTY CHECKING
=================================================================

Hibernate automatically detects
entity changes.

=================================================================
                EXAMPLE
=================================================================

User user = repo.findById(1);

user.setName("Pankaj");

=================================================================
                WHAT HAPPENS?
=================================================================

Hibernate automatically generates:
----------------------------------

UPDATE users SET name='Pankaj'

=================================================================
                WHY?
=================================================================

Dirty Checking mechanism.

=================================================================
                INTERVIEW IMPORTANT
=================================================================

Q:
---
Difference between JPA and Hibernate?

=================================================================
                ANSWER
=================================================================

JPA:
-----
Specification

Hibernate:
-----------
Implementation of JPA

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
What is Persistence Context?

=================================================================
                ANSWER
=================================================================

Persistence Context is first-level cache
where Hibernate manages entity lifecycle
and tracks changes.

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
Why @Transient used?

=================================================================
                ANSWER
=================================================================

To exclude fields from database persistence.

=================================================================
                TRICKY INTERVIEW QUESTION
=================================================================

Q:
---
Can entity exist without @Table?

YES.

Default class name becomes table name.

=================================================================
                ANOTHER TRICKY QUESTION
=================================================================

Q:
---
Why @Id mandatory?

Answer:
--------
Hibernate needs unique identifier
to manage entity state internally.

=================================================================
                COMMON MISTAKES
=================================================================

1. Using entities directly as DTOs

2. Missing default constructor

3. Wrong generation strategy

4. Storing temporary fields in DB

5. Large entities with unnecessary fields

=================================================================
                BEST PRACTICE
=================================================================

1. Keep entities clean
2. Use DTOs for APIs
3. Choose correct ID strategy
4. Use indexes carefully
5. Avoid business logic inside entities

=================================================================
                REAL INTERVIEW ANSWER
=================================================================

Q. Explain complete role of @Entity.

Answer:
--------
@Entity marks class as JPA-managed entity.
Hibernate maps class to database table,
tracks entity lifecycle,
manages persistence context,
and automatically generates SQL operations.

=================================================================
                SUMMARY
=================================================================

@Entity
---------
Maps class to DB table.

@Table
--------
Customizes table mapping.

@Id
-----
Primary key definition.

@GeneratedValue
----------------
Automatic ID generation.

@Column
---------
Custom column configuration.

@Transient
------------
Excludes field from persistence.

Hibernate internally manages
entity lifecycle and persistence context.

=================================================================

*/

public class EntityMappingAnnotationsNotes {
}