# ☕ JDBC Notes (Interview Ready)

> A concise, interview-focused guide to **JDBC (Java Database Connectivity)** with diagrams, examples, and quick revision notes.

---

# 📚 Table of Contents

- What is JDBC?
- Why JDBC?
- How Java Communicates with Database
- JDBC Architecture
- JDBC API vs JDBC Driver
- JDBC Workflow
- Quick Revision

---

# What is JDBC?

**JDBC (Java Database Connectivity)** is the **standard Java API** used to communicate with relational databases like:

- MySQL
- PostgreSQL
- Oracle
- SQL Server

Using JDBC, a Java application can:

- Connect to a database
- Execute SQL queries
- Retrieve data
- Insert new records
- Update existing records
- Delete records
- Manage transactions

Simply put,

> **JDBC acts as a bridge between a Java application and a relational database.**

---

# Why JDBC?

Suppose you write

```java
String sql = "SELECT * FROM students";
```

This only creates a Java String inside JVM.

❌ Database still doesn't know anything.

To send this SQL to MySQL, Java needs a communication mechanism.

That mechanism is **JDBC**.

Without JDBC,

- Java cannot connect to DB
- SQL cannot be executed
- Results cannot be returned

---

# Real World Example

Imagine ordering food.

```
Customer
    │
    ▼
Swiggy / Zomato
    │
    ▼
Restaurant
```

Similarly,

```
Java Application
        │
        ▼
      JDBC
        │
        ▼
   MySQL Database
```

JDBC works like **Swiggy**.

It carries your request to the database and brings the response back.

---

# How Java Communicates with Database

Java Application and Database are **two completely different programs.**

```
          Server A                          Server B

+------------------------+         +--------------------+
|                        |         |                    |
|  Java Application      | <-----> |     MySQL DB       |
|                        |         |                    |
+------------------------+         +--------------------+
          JVM
```

They may run

- On same computer
- On different computers
- On cloud servers
- Across different countries

Because both are independent applications,

Java **cannot directly call MySQL methods**.

Instead, communication happens over a network.

---

# Why Database Driver is Required?

Every database has its own communication protocol.

For example

MySQL

- MySQL Protocol

PostgreSQL

- PostgreSQL Protocol

Oracle

- Oracle Network Protocol

Java doesn't understand all these protocols.

Therefore each database company provides its own **JDBC Driver**.

Example

| Database | Driver |
|----------|--------|
| MySQL | MySQL Connector/J |
| PostgreSQL | PostgreSQL JDBC Driver |
| Oracle | Oracle JDBC Driver |

Think of Driver as a translator.

```
Java
   │
   ▼
JDBC Driver
   │
   ▼
Database Language
```

---

# JDBC Architecture

```
                   Java Application
                          │
                          ▼
                    JDBC Interfaces
                          │
        ┌─────────────────┼──────────────────┐
        ▼                 ▼                  ▼
   MySQL Driver     Postgre Driver     Oracle Driver
        │                 │                  │
        ▼                 ▼                  ▼
      MySQL          PostgreSQL           Oracle
```

Notice

Java always talks to **JDBC Interfaces**.

It never directly communicates with MySQL.

That's why changing the database requires very little code change.

---

# JDBC API vs JDBC Driver

Many beginners confuse these.

## JDBC API

JDBC API is provided by Java.

Package

```java
java.sql
```

It contains interfaces like

- Connection
- Statement
- PreparedStatement
- ResultSet
- DriverManager

These only define **WHAT** should happen.

Example

```
Connection

connect()

close()

commit()

rollback()
```

No implementation exists here.

---

## JDBC Driver

Driver is provided by database vendors.

Examples

- MySQL Connector/J
- PostgreSQL Driver
- Oracle Driver

Driver implements JDBC interfaces.

It tells Java

- How to connect
- How to authenticate
- How SQL should be sent
- How results should be received

So,

```
JDBC API

↓

Defines Rules

↓

JDBC Driver

↓

Implements Rules
```

---

# Internal Working of JDBC

When you execute

```java
statement.executeQuery(sql);
```

Internally,

```
Java Program

      │

      ▼

JDBC API

      │

      ▼

JDBC Driver

      │

      ▼

Network Request

      │

      ▼

MySQL Server

      │

      ▼

Execute SQL

      │

      ▼

Generate Result

      │

      ▼

Driver Converts Result

      │

      ▼

ResultSet

      │

      ▼

Java Objects
```

---

# Complete JDBC Workflow

```
Write SQL

      │

      ▼

Create Connection

      │

      ▼

Create Statement

      │

      ▼

Execute SQL

      │

      ▼

Receive ResultSet

      │

      ▼

Read Data

      │

      ▼

Close Resources
```

---

# ⭐ Interview Notes

### What is JDBC?

> JDBC is the standard Java API used for communicating with relational databases.

---

### Why JDBC?

Because Java cannot directly understand database communication protocols.

---

### Why Driver is required?

Driver translates JDBC calls into database-specific network requests.

---

### Difference between API and Driver?

| JDBC API | JDBC Driver |
|-----------|-------------|
| Provided by Java | Provided by Database Vendor |
| Contains Interfaces | Contains Implementations |
| Database Independent | Database Specific |

---

# ⚡ Quick Revision

✅ JDBC = Java Database Connectivity

✅ Package = java.sql

✅ JDBC = Bridge between Java and Database

✅ Driver = Translator

✅ Java talks to JDBC Interfaces

✅ Driver talks to Database

✅ Every Database has its own Driver

---

---

# 🔌 Database Connection

## What is a Database Connection?

A **Database Connection** is an active communication session between a Java application and the database server.

Think of it as a **telephone call** between Java and MySQL.

As long as the call is active, both can exchange information.

Once the connection is closed, communication stops.

> **Interview Definition**
>
> A Connection is a JDBC interface that represents an active session between a Java application and a relational database. It allows Java to execute SQL statements, manage transactions, and retrieve results.

---

# Why Do We Need a Connection?

Without a connection,

❌ Java cannot send SQL.

❌ Database cannot return data.

Every SQL operation starts with creating a connection.

```
Java Application
       │
       ▼
 Connection Established
       │
       ▼
 Execute SQL
       │
       ▼
 Receive Result
```

---

# What Can a Connection Do?

A Connection allows Java to:

- Execute SQL Queries
- Create Statements
- Create PreparedStatements
- Start Transactions
- Commit Transactions
- Rollback Transactions
- Read Database Metadata
- Close Database Session

---

# JDBC Connection URL

Example

```java
String url = "jdbc:mysql://localhost:3306/student_db";
```

Let's understand every part.

```
jdbc:mysql://localhost:3306/student_db

│
├── jdbc
│      JDBC Protocol
│
├── mysql
│      Database Type
│
├── localhost
│      Database Host
│
├── 3306
│      Port Number
│
└── student_db
       Database Name
```

---

## Components Explained

### jdbc

Indicates JDBC protocol.

Every JDBC URL starts with

```
jdbc:
```

---

### mysql

Specifies which database driver should be used.

Examples

```
jdbc:mysql://

jdbc:postgresql://

jdbc:oracle:
```

---

### localhost

Means database is running on the same computer.

If database is on another server

```
jdbc:mysql://192.168.1.50:3306/student_db
```

or

```
jdbc:mysql://company-db.com:3306/student_db
```

---

### Port

Default MySQL Port

```
3306
```

PostgreSQL

```
5432
```

SQL Server

```
1433
```

---

### Database Name

The database where tables exist.

Example

```
student_db
employee_db
college_db
```

---

# Creating a Connection

```java
Connection connection =
DriverManager.getConnection(
        url,
        username,
        password
);
```

This single line performs multiple operations internally.

---

# What Happens Internally?

```
DriverManager

        │

Reads JDBC URL

        │

Finds Correct Driver

        │

Loads Driver

        │

Sends Username

        │

Sends Password

        │

Authenticates User

        │

Creates Session

        │

Returns Connection Object
```

---

# DriverManager

## What is DriverManager?

DriverManager is a utility class provided by JDBC.

Its main job is

> **Finding the correct JDBC Driver and creating a Connection object.**

Think of DriverManager as a receptionist.

```
Customer

     │

"I want MySQL"

     │

Receptionist

     │

Finds MySQL Expert

     │

Connects Call
```

Exactly same,

```
Java

     │

DriverManager

     │

Find MySQL Driver

     │

Create Connection
```

---

# Why DriverManager?

Imagine Java has multiple JDBC Drivers.

- MySQL Driver
- Oracle Driver
- PostgreSQL Driver

How will Java know which one to use?

DriverManager reads the JDBC URL.

Example

```
jdbc:mysql:
```

↓

Use MySQL Driver

```
jdbc:postgresql:
```

↓

Use PostgreSQL Driver

Everything happens automatically.

---

# Is Class.forName() Required?

Old JDBC Code

```java
Class.forName("com.mysql.cj.jdbc.Driver");
```

Earlier, Java required manually loading the driver.

Since **JDBC 4.0**, this is **NOT required**.

As long as the driver JAR is present in the project,

Java automatically loads it.

✅ Modern Projects

No need to write

```java
Class.forName(...)
```

---

# Statement

## What is Statement?

Statement is used to execute **simple SQL queries**.

It executes SQL exactly as written.

Example

```java
Statement statement =
connection.createStatement();
```

---

# Statement Flow

```
Connection

      │

createStatement()

      │

Statement

      │

Execute SQL

      │

Database

      │

Result
```

---

# Executing SQL

Statement provides three important methods.

| Method | Used For | Returns |
|---------|-----------|----------|
| executeQuery() | SELECT | ResultSet |
| executeUpdate() | INSERT UPDATE DELETE CREATE ALTER DROP | int |
| execute() | Any SQL | boolean |

---

# executeQuery()

Used when SQL returns rows.

Example

```java
SELECT * FROM students;
```

```java
ResultSet rs =
statement.executeQuery(sql);
```

Return Type

```
ResultSet
```

Used only for

✅ SELECT

---

# executeUpdate()

Used when database data changes.

Example

```sql
INSERT

UPDATE

DELETE
```

Returns

```
int
```

Meaning

```
1

↓

One row affected

0

↓

No rows affected

5

↓

Five rows updated
```

Even DDL commands like

```
CREATE TABLE

ALTER TABLE

DROP TABLE
```

can be executed using executeUpdate().

---

# execute()

Most generic method.

```java
boolean result =
statement.execute(sql);
```

Returns

```
true

↓

ResultSet Returned

false

↓

Update Count Returned
```

Usually used when

- SQL type is unknown
- Stored Procedures
- Generic Database Utilities

For normal applications,

Prefer

```
SELECT

↓

executeQuery()

--------------------

INSERT

↓

executeUpdate()

--------------------

UPDATE

↓

executeUpdate()

--------------------

DELETE

↓

executeUpdate()
```

---

# Comparison

| Method | Returns | Used For |
|----------|----------|------------|
| executeQuery() | ResultSet | SELECT |
| executeUpdate() | int | INSERT UPDATE DELETE DDL |
| execute() | boolean | Unknown SQL |

---

# Best Practices

✅ Use executeQuery() only for SELECT

✅ Use executeUpdate() for INSERT UPDATE DELETE

✅ Use execute() only when SQL type is unknown

---

# ⭐ Interview Questions

### What is Connection?

A Connection represents an active communication session between Java and Database.

---

### Why DriverManager is used?

It locates the correct JDBC Driver and creates a Connection.

---

### Why Class.forName() is no longer required?

Because JDBC 4.0 automatically loads drivers available on the classpath.

---

### Difference between executeQuery() and executeUpdate()?

| executeQuery() | executeUpdate() |
|----------------|-----------------|
| SELECT | INSERT UPDATE DELETE |
| Returns ResultSet | Returns int |

---

# ⚡ Quick Revision

✅ Connection = Database Session

✅ DriverManager = Creates Connection

✅ createStatement() = Creates Statement

✅ executeQuery() = SELECT

✅ executeUpdate() = INSERT UPDATE DELETE

✅ execute() = Generic Method

✅ Class.forName() = Mostly Not Required (JDBC 4.0+)

---

---

# 🛡️ PreparedStatement

## What is PreparedStatement?

**PreparedStatement** is a special type of Statement used to execute **parameterized SQL queries**.

Instead of directly placing values inside the SQL query, it uses **placeholders (`?`)** and binds the values separately.

> **Interview Definition**
>
> PreparedStatement is a precompiled SQL statement that allows parameters to be bound separately from the SQL query, making execution faster, safer, and immune to SQL Injection attacks.

---

# Why was PreparedStatement Introduced?

Suppose we want to insert a student.

Using Statement,

```java
String sql =
"INSERT INTO students(name,email,age) VALUES('"
+ name + "','"
+ email + "',"
+ age + ")";
```

Looks simple...

But it creates many problems.

❌ SQL Injection

❌ Quotation Errors

❌ Difficult to Read

❌ Poor Performance

❌ Manual Data Formatting

That's why PreparedStatement was introduced.

---

# How PreparedStatement Works

Instead of writing

```sql
INSERT INTO students
VALUES('Rahul',22)
```

we write

```sql
INSERT INTO students
VALUES(?,?)
```

Later,

Java fills these placeholders.

```java
preparedStatement.setString(1,"Rahul");
preparedStatement.setInt(2,22);
```

Finally,

Database receives

```sql
INSERT INTO students
VALUES('Rahul',22)
```

---

# Internal Flow

```
SQL with ?

        │

        ▼

PreparedStatement

        │

Bind Values

        │

        ▼

Driver

        │

        ▼

Database

        │

Execute SQL

        │

Return Result
```

Notice,

SQL Structure and Data are sent separately.

This is the biggest advantage.

---

# Placeholder (?)

Every **?** represents one parameter.

Example

```sql
INSERT INTO students
VALUES(?,?,?)
```

Mapping

```
First ?

↓

Name

------------------

Second ?

↓

Email

------------------

Third ?

↓

Age
```

Parameter indexing starts from

```
1

NOT

0
```

This is one of the most common interview questions.

---

# Creating PreparedStatement

```java
String sql =
"""
INSERT INTO students(name,email,age)
VALUES(?,?,?)
""";

PreparedStatement ps =
connection.prepareStatement(sql);
```

Binding values

```java
ps.setString(1,"Rahul");

ps.setString(2,"rahul@gmail.com");

ps.setInt(3,22);
```

Execution

```java
ps.executeUpdate();
```

---

# Why is PreparedStatement Faster?

Statement

```
SQL

↓

Database Parses SQL

↓

Execute

-----------------------

Again

↓

Parse

↓

Execute

-----------------------

Again

↓

Parse

↓

Execute
```

PreparedStatement

```
SQL

↓

Parsed Once

↓

Execution Plan Created

↓

Reuse Same Plan

↓

Execute Again

↓

Execute Again

↓

Execute Again
```

Database doesn't need to parse the SQL every time.

This improves performance.

---

# SQL Injection

One of the most important interview topics.

Suppose login query is

```java
String sql =
"SELECT * FROM users WHERE username='"
+ username +
"' AND password='"
+ password + "'";
```

Normal Input

```
Username

Rahul

Password

12345
```

Query becomes

```sql
SELECT *
FROM users
WHERE username='Rahul'
AND password='12345'
```

Works correctly.

---

## Hacker Input 😈

Suppose attacker enters

```
Username

Rahul

Password

' OR '1'='1
```

Query becomes

```sql
SELECT *
FROM users
WHERE username='Rahul'
AND password=''
OR '1'='1'
```

Since

```
1 = 1

↓

Always TRUE
```

Database returns all users.

Authentication bypassed.

This attack is called

# SQL Injection

---

# How PreparedStatement Prevents SQL Injection

PreparedStatement never mixes SQL structure and user input.

```
SQL

↓

SELECT *
FROM users
WHERE username=?
AND password=?

↓

Values Bound Separately

↓

Database Treats Input as DATA

NOT SQL
```

Even if attacker enters

```
' OR '1'='1
```

Database treats it as plain text.

Login fails.

Application remains secure.

---

# Statement vs PreparedStatement

| Statement | PreparedStatement |
|------------|-------------------|
| Dynamic SQL | Parameterized SQL |
| Slow | Faster |
| Vulnerable to SQL Injection | Safe |
| Difficult to Maintain | Easy to Read |
| SQL Parsed Every Time | Parsed Once |

---

# ResultSet

## What is ResultSet?

When a SELECT query executes,

Database returns rows.

Those rows are stored inside a

**ResultSet**

Example

```sql
SELECT *
FROM students;
```

Database

```
ID   Name

1    Rahul

2    Amit

3    Pankaj
```

Java receives

```
ResultSet
```

Think of ResultSet as a **table returned by the database**.

---

# Cursor

One of the favourite interview questions.

When ResultSet is created,

Cursor is **NOT** on first row.

It is

```
Before First Row
```

```
             Cursor

               │

               ▼

+----+---------+------+
|ID  | Name    | Age  |
+----+---------+------+
|1   | Rahul   |22    |
|2   | Amit    |25    |
|3   | Pankaj  |21    |
+----+---------+------+
```

---

# next()

Calling

```java
resultSet.next();
```

moves cursor

```
Before First Row

↓

Row 1

↓

Row 2

↓

Row 3

↓

false
```

Returns

```
true

↓

Row Exists

false

↓

No More Rows
```

---

# Reading Data

```java
while(resultSet.next()){

System.out.println(
resultSet.getLong("id"));

System.out.println(
resultSet.getString("name"));

System.out.println(
resultSet.getString("email"));

System.out.println(
resultSet.getInt("age"));

}
```

Column Name is preferred over Column Index because

✅ More Readable

✅ Easier Maintenance

✅ Less Error-Prone

Instead of

```java
getString(2)
```

prefer

```java
getString("name")
```

---

# Mapping ResultSet to Java Object

Database returns rows.

Java works with objects.

So we convert every row into an object.

```
Database Row

        │

        ▼

ResultSet

        │

        ▼

Student Object

        │

        ▼

ArrayList<Student>
```

Example

```java
Student student = new Student();

student.setId(
resultSet.getLong("id"));

student.setName(
resultSet.getString("name"));

student.setEmail(
resultSet.getString("email"));

student.setAge(
resultSet.getInt("age"));
```

This process is called

**Row Mapping**

---

# Best Practices

✅ Always use PreparedStatement.

✅ Never build SQL using String concatenation.

✅ Always use column names.

✅ Close ResultSet after use.

✅ Close PreparedStatement.

✅ Close Connection.

---

# ⭐ Interview Questions

### Why PreparedStatement is preferred?

Because it

- Prevents SQL Injection
- Improves Performance
- Is Easy to Maintain
- Handles Data Types Automatically

---

### Why does ResultSet cursor start before the first row?

Because JDBC allows the program to decide whether data exists by calling `next()`. The first call both checks for a row and moves the cursor to it.

---

### Why parameter index starts from 1?

JDBC specification defines parameter numbering from **1**, not **0**.

---

### Why should we avoid Statement?

Because it is vulnerable to SQL Injection and reparses SQL on every execution.

---

# ⚡ Quick Revision

✅ PreparedStatement = Secure SQL

✅ ? = Placeholder

✅ setString() = Bind String

✅ setInt() = Bind Integer

✅ Parsed Once

✅ Faster

✅ Prevents SQL Injection

✅ ResultSet = Returned Rows

✅ next() = Move Cursor

✅ Row → Java Object = Mapping

---

---

# 🚀 CRUD Operations using JDBC

CRUD stands for the four basic operations performed on a database.

| Operation | SQL Command | JDBC Method |
|-----------|------------|-------------|
| Create | INSERT | executeUpdate() |
| Read | SELECT | executeQuery() |
| Update | UPDATE | executeUpdate() |
| Delete | DELETE | executeUpdate() |

---

# CRUD Flow

```text
          Java Application
                 │
                 ▼
          JDBC Connection
                 │
                 ▼
       PreparedStatement
                 │
                 ▼
         Execute SQL Query
                 │
      ┌──────────┼──────────┐
      ▼          ▼          ▼
   INSERT      SELECT     UPDATE
      │          │          │
      └──────────┼──────────┘
                 ▼
             MySQL Database
```

---

# Create (INSERT)

```java
String sql = """
INSERT INTO students(name,email,age)
VALUES(?,?,?)
""";

PreparedStatement ps = connection.prepareStatement(sql);

ps.setString(1, "Rahul");
ps.setString(2, "rahul@gmail.com");
ps.setInt(3, 22);

int rows = ps.executeUpdate();

System.out.println(rows + " row inserted");
```

---

# Read (SELECT)

```java
String sql = "SELECT * FROM students";

PreparedStatement ps = connection.prepareStatement(sql);

ResultSet rs = ps.executeQuery();

while (rs.next()) {

    System.out.println(
            rs.getLong("id")
            + " "
            + rs.getString("name")
    );
}
```

---

# Update

```java
String sql = """
UPDATE students
SET age=?
WHERE id=?
""";

PreparedStatement ps = connection.prepareStatement(sql);

ps.setInt(1, 25);
ps.setLong(2, 1);

ps.executeUpdate();
```

---

# Delete

```java
String sql =
"DELETE FROM students WHERE id=?";

PreparedStatement ps =
connection.prepareStatement(sql);

ps.setLong(1,1);

ps.executeUpdate();
```

---

# Closing JDBC Resources

Every opened resource consumes memory.

Always close

- ResultSet
- PreparedStatement
- Connection

Order

```text
ResultSet

↓

PreparedStatement

↓

Connection
```

Never close Connection first.

---

# try-with-resources (Recommended)

Instead of manually closing resources,

Use

```java
try (

    Connection connection =
            DriverManager.getConnection(url,user,password);

    PreparedStatement ps =
            connection.prepareStatement(sql);

    ResultSet rs =
            ps.executeQuery()

){

    while(rs.next()){

        System.out.println(
                rs.getString("name"));
    }

}
```

Advantages

✅ Automatic Closing

✅ Cleaner Code

✅ No Memory Leaks

✅ Exception Safe

---

# Resource Closing Flow

```text
Open Connection
        │
        ▼
Create PreparedStatement
        │
        ▼
Execute Query
        │
        ▼
Read ResultSet
        │
        ▼
Close ResultSet
        │
        ▼
Close PreparedStatement
        │
        ▼
Close Connection
```

---

# Common JDBC Errors

## 1. Driver Not Found

```
ClassNotFoundException
```

Reason

- Driver JAR missing

---

## 2. Wrong URL

```
No suitable driver found
```

Reason

- Incorrect JDBC URL

---

## 3. Authentication Failed

```
Access denied
```

Reason

- Wrong username/password

---

## 4. Table Doesn't Exist

```
Table doesn't exist
```

Reason

- Wrong table name

---

## 5. SQL Syntax Error

```
SQLSyntaxErrorException
```

Reason

- Invalid SQL query

---

# Best Practices

✅ Prefer **PreparedStatement** over Statement.

✅ Use **try-with-resources**.

✅ Close all JDBC resources.

✅ Never concatenate user input into SQL.

✅ Use meaningful variable names.

✅ Handle exceptions properly.

✅ Store database credentials securely (never hardcode them).

✅ Use transactions when multiple SQL operations must succeed together.

---

# JDBC Interview Questions

### 1. What is JDBC?

JDBC is the standard Java API used to communicate with relational databases.

---

### 2. Why is JDBC needed?

Because Java cannot directly communicate with a database. JDBC acts as a bridge.

---

### 3. Difference between JDBC API and JDBC Driver?

| JDBC API | JDBC Driver |
|----------|-------------|
| Provided by Java | Provided by Database Vendor |
| Defines interfaces | Implements interfaces |

---

### 4. Why is PreparedStatement preferred?

- Prevents SQL Injection
- Better Performance
- Easier Maintenance
- Supports Parameter Binding

---

### 5. Difference between Statement and PreparedStatement?

| Statement | PreparedStatement |
|------------|-------------------|
| Dynamic SQL | Parameterized SQL |
| Vulnerable to SQL Injection | Secure |
| Slower | Faster |

---

### 6. Difference between executeQuery() and executeUpdate()?

| executeQuery() | executeUpdate() |
|----------------|-----------------|
| SELECT | INSERT, UPDATE, DELETE |
| Returns ResultSet | Returns int |

---

### 7. What does ResultSet store?

The rows returned by a SELECT query.

---

### 8. Why does ResultSet cursor start before the first row?

To allow `next()` to both check for data and move to the first row.

---

### 9. What is SQL Injection?

A security vulnerability where malicious SQL is injected through user input.

---

### 10. Why should we use try-with-resources?

It automatically closes JDBC resources and prevents memory leaks.

---

# One-Page JDBC Cheat Sheet

```text
JDBC
│
├── API → java.sql
│
├── DriverManager
│       │
│       ▼
│   Connection
│       │
│       ▼
│ PreparedStatement
│       │
│       ▼
│ executeQuery()
│ executeUpdate()
│ execute()
│
├── ResultSet
│       │
│       ▼
│ Java Objects
│
└── Close Resources
```

---

# Complete JDBC Flow

```text
Java Program
      │
      ▼
DriverManager
      │
      ▼
Connection
      │
      ▼
PreparedStatement
      │
      ▼
Execute SQL
      │
      ▼
Database
      │
      ▼
ResultSet
      │
      ▼
Java Objects
      │
      ▼
Close Resources
```

---

# 🎯 Quick Revision

- JDBC = Java Database Connectivity
- `java.sql` = JDBC API package
- Driver = Translates JDBC calls into database-specific protocol
- DriverManager = Creates database connections
- Connection = Active session with the database
- Statement = Executes static SQL
- PreparedStatement = Parameterized, secure, and faster
- ResultSet = Holds rows returned by SELECT
- `executeQuery()` = SELECT
- `executeUpdate()` = INSERT / UPDATE / DELETE / DDL
- `execute()` = Generic method
- `next()` = Moves ResultSet cursor
- `try-with-resources` = Automatically closes resources

---

# 🎉 Conclusion

JDBC is the foundation of database programming in Java. Understanding **Connection**, **DriverManager**, **PreparedStatement**, **ResultSet**, and the complete execution flow is essential before moving to higher-level frameworks like **Hibernate**, **JPA**, and **Spring Data JPA**.

Master these core concepts first, and frameworks will become much easier to understand.