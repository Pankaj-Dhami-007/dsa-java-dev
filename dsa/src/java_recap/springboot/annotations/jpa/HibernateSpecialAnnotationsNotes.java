package java_recap.springboot.annotations.jpa;

/*

=================================================================
        HIBERNATE SPECIAL ANNOTATIONS
=================================================================

Annotations:
-------------
@CreationTimestamp
@UpdateTimestamp
@Enumerated
@Lob
@Embedded
@Embeddable

Core Concepts:
---------------
1. Auditing
2. Enum Mapping
3. Large Object Storage
4. Value Objects
5. Embedded Objects
6. Database Optimization
7. Domain Modeling

=================================================================
                WHY THESE ANNOTATIONS EXIST?
=================================================================

Real enterprise systems need:
------------------------------

1. Automatic timestamps
2. Enum persistence
3. File/document storage
4. Reusable value objects
5. Cleaner domain models

=================================================================
                1. @CreationTimestamp
=================================================================

Most used auditing annotation.

=================================================================
                DEFINITION
=================================================================

@CreationTimestamp automatically stores
entity creation time.

=================================================================
                EXAMPLE
=================================================================

@CreationTimestamp
private LocalDateTime createdAt;

=================================================================
                WHAT HAPPENS?
=================================================================

When entity inserted:
----------------------

Hibernate automatically sets timestamp.

=================================================================
                INTERNAL WORKING
=================================================================

Entity Persisted
        ↓
Hibernate Intercepts Insert
        ↓
Current Timestamp Generated
        ↓
Value inserted into column

=================================================================
                GENERATED VALUE
=================================================================

2026-05-18T10:30:20

=================================================================
                WHY IMPORTANT?
=================================================================

Avoid manual timestamp handling.

=================================================================
                REAL PROJECT USE CASE
=================================================================

1. User registration time
2. Order creation time
3. Audit tracking
4. Payment creation time

=================================================================
                IMPORTANT
=================================================================

Value set ONLY during insert.

=================================================================
                AFTER INSERT
=================================================================

Field normally should NOT change.

=================================================================
                BEST PRACTICE
=================================================================

@Column(updatable = false)

=================================================================
                EXAMPLE
=================================================================

@CreationTimestamp

@Column(updatable = false)

private LocalDateTime createdAt;

=================================================================
                2. @UpdateTimestamp
=================================================================

Automatically updates timestamp
during entity update.

=================================================================
                EXAMPLE
=================================================================

@UpdateTimestamp
private LocalDateTime updatedAt;

=================================================================
                WHAT HAPPENS?
=================================================================

Whenever entity updated:
-------------------------

Hibernate updates timestamp automatically.

=================================================================
                INTERNAL WORKING
=================================================================

Entity Modified
        ↓
Dirty Checking Detects Change
        ↓
Before UPDATE query
        ↓
Timestamp auto-updated

=================================================================
                IMPORTANT
=================================================================

Uses Hibernate Dirty Checking internally.

=================================================================
                REAL PROJECT USE CASE
=================================================================

1. Last profile update
2. Last order modification
3. Last login update
4. Audit tracking

=================================================================
                VERY IMPORTANT
=================================================================

@UpdateTimestamp updates ONLY when:
------------------------------------

Hibernate detects entity change.

=================================================================
                INTERVIEW IMPORTANT
=================================================================

Q:
---
Difference between @CreationTimestamp
and @UpdateTimestamp?

=================================================================
                ANSWER
=================================================================

@CreationTimestamp:
--------------------
Set only during INSERT.

@UpdateTimestamp:
------------------
Updated during every UPDATE.

=================================================================
                3. @Enumerated
=================================================================

Most important enum mapping annotation.

=================================================================
                WHY NEEDED?
=================================================================

Java enums cannot directly map
properly to database without strategy.

=================================================================
                EXAMPLE ENUM
=================================================================

public enum Status {

    ACTIVE,
    INACTIVE,
    BLOCKED

}

=================================================================
                ENTITY
=================================================================

@Enumerated(EnumType.STRING)
private Status status;

=================================================================
                DATABASE VALUE
=================================================================

ACTIVE

=================================================================
                ENUM TYPES
=================================================================

1. ORDINAL
2. STRING

=================================================================
                1. ORDINAL
=================================================================

Stores enum index.

=================================================================
                EXAMPLE
=================================================================

ACTIVE → 0
INACTIVE → 1

=================================================================
                PROBLEM
=================================================================

Dangerous.

=================================================================
                WHY?
=================================================================

If enum order changes:
-----------------------

Database data becomes corrupted.

=================================================================
                EXAMPLE
=================================================================

Before:
--------

ACTIVE
INACTIVE

After:
-------

PENDING
ACTIVE
INACTIVE

=================================================================
                RESULT
=================================================================

Old values now point to wrong enums.

=================================================================
                VERY IMPORTANT
=================================================================

Never use ORDINAL in enterprise systems.

=================================================================
                2. STRING
=================================================================

Stores enum name.

=================================================================
                EXAMPLE
=================================================================

ACTIVE

=================================================================
                BENEFITS
=================================================================

1. Readable DB values
2. Safe against enum reordering
3. Better maintenance

=================================================================
                SMALL DISADVANTAGE
=================================================================

Consumes slightly more storage.

=================================================================
                BUT
=================================================================

Safety > storage optimization.

=================================================================
                BEST PRACTICE
=================================================================

Always use:
------------

EnumType.STRING

=================================================================
                REAL PROJECT USE CASE
=================================================================

1. OrderStatus
2. PaymentStatus
3. UserRole
4. TicketStatus

=================================================================
                4. @Lob
=================================================================

LOB =
------
Large Object

=================================================================
                DEFINITION
=================================================================

@Lob used for storing large data.

=================================================================
                TYPES
=================================================================

1. CLOB
2. BLOB

=================================================================
                CLOB
=================================================================

Character Large Object.

Used for:
-----------

Large text data.

=================================================================
                EXAMPLE
=================================================================

@Lob
private String description;

=================================================================
                USED FOR
=================================================================

1. Article content
2. Logs
3. Large JSON
4. HTML templates

=================================================================
                BLOB
=================================================================

Binary Large Object.

Used for:
-----------

Binary data/files.

=================================================================
                EXAMPLE
=================================================================

@Lob
private byte[] image;

=================================================================
                USED FOR
=================================================================

1. Images
2. PDFs
3. Documents
4. Videos

=================================================================
                IMPORTANT
=================================================================

Storing large files directly in DB
often not recommended.

=================================================================
                WHY?
=================================================================

1. Database bloat
2. Backup issues
3. Performance impact

=================================================================
                REAL ENTERPRISE APPROACH
=================================================================

Store file in:
---------------

S3 / File Storage

Store only:
------------

File URL/path in DB.

=================================================================
                INTERVIEW IMPORTANT
=================================================================

Q:
---
Why large files usually not stored
directly in database?

=================================================================
                ANSWER
=================================================================

1. Performance issues
2. Large DB backups
3. Expensive storage
4. Poor scalability

=================================================================
                5. @Embeddable
=================================================================

Very important DDD-style annotation.

=================================================================
                DEFINITION
=================================================================

@Embeddable defines reusable value object.

=================================================================
                IMPORTANT
=================================================================

Embeddable object has:
-----------------------

NO separate table.

=================================================================
                EXAMPLE
=================================================================

@Embeddable
public class Address {

    private String city;
    private String state;
    private String pincode;

}

=================================================================
                6. @Embedded
=================================================================

Embeds embeddable object into entity.

=================================================================
                EXAMPLE
=================================================================

@Entity
public class User {

    @Embedded
    private Address address;

}

=================================================================
                GENERATED TABLE
=================================================================

user table columns:
--------------------

city
state
pincode

=================================================================
                IMPORTANT
=================================================================

No separate address table created.

=================================================================
                WHY USE EMBEDDED OBJECTS?
=================================================================

Improves:
----------

1. Reusability
2. Domain modeling
3. Code organization
4. Clean architecture

=================================================================
                REAL PROJECT USE CASE
=================================================================

1. Address
2. Money
3. GeoLocation
4. ContactInfo

=================================================================
                INTERNAL WORKING
=================================================================

Hibernate flattens embedded object fields
into parent table columns.

=================================================================
                IMPORTANT DIFFERENCE
=================================================================

@Embeddable
-------------
No identity/primary key.

@Entity
---------
Independent lifecycle + identity.

=================================================================
                VALUE OBJECT CONCEPT
=================================================================

Embeddables represent:
-----------------------

Value Objects

=================================================================
                VALUE OBJECT
=================================================================

Object identified by values,
NOT identity.

=================================================================
                EXAMPLE
=================================================================

Address equality depends on:
-----------------------------

city + state + pincode

NOT database ID.

=================================================================
                ADVANCED EMBEDDED FEATURE
=================================================================

@AttributeOverrides

=================================================================
                WHY?
=================================================================

Customize column names.

=================================================================
                EXAMPLE
=================================================================

@Embedded

@AttributeOverrides({

    @AttributeOverride(
        name = "city",
        column = @Column(name = "home_city")
    )

})

=================================================================
                INTERVIEW IMPORTANT
=================================================================

Q:
---
Difference between @Entity
and @Embeddable?

=================================================================
                ANSWER
=================================================================

@Entity:
---------
Independent database table
with identity.

@Embeddable:
--------------
Reusable value object
without separate table.

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
Why EnumType.STRING preferred?

=================================================================
                ANSWER
=================================================================

Because ORDINAL becomes dangerous
when enum order changes.

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
How @UpdateTimestamp works internally?

=================================================================
                ANSWER
=================================================================

Hibernate dirty checking detects changes
and automatically updates timestamp
before executing UPDATE query.

=================================================================
                TRICKY INTERVIEW QUESTION
=================================================================

Q:
---
Can @Embeddable contain relationships?

YES.

But usually avoided for clean design.

=================================================================
                ANOTHER TRICKY QUESTION
=================================================================

Q:
---
Can multiple embedded objects exist?

YES.

Use:
-----

@AttributeOverride

to avoid column conflicts.

=================================================================
                COMMON MISTAKES
=================================================================

1. Using EnumType.ORDINAL

2. Storing huge files in DB

3. Using Entity instead of Value Object

4. Missing column overrides

=================================================================
                BEST PRACTICE
=================================================================

1. Use EnumType.STRING
2. Use embedded value objects
3. Store files externally
4. Use timestamps for auditing
5. Keep entities clean

=================================================================
                REAL INTERVIEW ANSWER
=================================================================

Q. Explain purpose of @Embeddable.

Answer:
--------
@Embeddable is used to create reusable
value objects whose fields are embedded
directly into parent entity table,
without creating separate table.

=================================================================
                SUMMARY
=================================================================

@CreationTimestamp
-------------------
Auto-sets creation timestamp.

@UpdateTimestamp
-----------------
Auto-updates modification timestamp.

@Enumerated
-------------
Maps Java enums to database.

@Lob
------
Stores large text/binary objects.

@Embeddable
-------------
Reusable value object.

@Embedded
-----------
Embeds value object into entity.

Hibernate internally handles:
------------------------------

1. Dirty checking
2. Enum conversion
3. Object flattening
4. Automatic auditing

=================================================================

*/

public class HibernateSpecialAnnotationsNotes {
}