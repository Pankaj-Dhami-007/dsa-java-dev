# 🌱 Spring JDBC Notes (Interview Ready)

> A concise guide to **Spring JDBC** covering JdbcTemplate, RowMapper, CRUD operations, exception handling, and best practices.

---

# 📚 Table of Contents

- What is Spring JDBC?
- Why Spring JDBC?
- JDBC vs Spring JDBC
- Spring JDBC Architecture
- JdbcTemplate
- Quick Revision

---

# What is Spring JDBC?

**Spring JDBC** is a module of the Spring Framework that simplifies database operations using JDBC.

It removes most of the repetitive JDBC code like creating connections, closing resources, handling exceptions, and processing `ResultSet`.

> **Interview Definition**
>
> Spring JDBC is a lightweight abstraction over JDBC that reduces boilerplate code using `JdbcTemplate`.

---

# Why Spring JDBC?

Writing database code using plain JDBC becomes repetitive.

Every operation requires:

- Loading Driver
- Creating Connection
- Creating Statement
- Executing SQL
- Handling Exceptions
- Closing Resources

Spring JDBC automates these tasks.

---

# Problems with JDBC

❌ Too much boilerplate code

❌ Manual Connection management

❌ Manual closing of resources

❌ Checked SQLExceptions

❌ Manual ResultSet mapping

Example (JDBC)

```java
Connection con = DriverManager.getConnection(...);

PreparedStatement ps = con.prepareStatement(sql);

ResultSet rs = ps.executeQuery();

while(rs.next()){
   ...
}

rs.close();
ps.close();
con.close();
```

A lot of code is repeated in every database operation.

---

# How Spring JDBC Solves It

Spring provides **JdbcTemplate** which handles most of the repetitive work.

✅ Opens Connection

✅ Executes SQL

✅ Maps Result

✅ Handles Exceptions

✅ Closes Resources

Developer only writes

- SQL
- Parameters
- Business Logic

---

# Spring JDBC Architecture

```text
      Controller
           │
           ▼
       Service Layer
           │
           ▼
    Repository Layer
           │
           ▼
      JdbcTemplate
           │
           ▼
      JDBC Driver
           │
           ▼
        Database
```

---

# JDBC vs Spring JDBC

| JDBC | Spring JDBC |
|------|-------------|
| More Code | Less Code |
| Manual Connection | Automatic |
| Manual Resource Closing | Automatic |
| Checked Exceptions | DataAccessException |
| Manual ResultSet Mapping | RowMapper |
| More Boilerplate | Cleaner Code |

---

# JdbcTemplate

## What is JdbcTemplate?

`JdbcTemplate` is the core class of Spring JDBC.

It executes SQL queries while automatically managing:

- Connection
- Statement
- ResultSet
- Exception Handling

> **Interview Definition**
>
> JdbcTemplate is the central class of Spring JDBC that simplifies database operations by removing repetitive JDBC code.

---

# How JdbcTemplate Works

```text
SQL Query
    │
    ▼
JdbcTemplate
    │
    ▼
Create Connection
    │
    ▼
Execute SQL
    │
    ▼
Map Result
    │
    ▼
Close Resources
```

---

# Common Methods

| Method | Purpose |
|---------|---------|
| update() | INSERT, UPDATE, DELETE |
| query() | Returns Multiple Rows |
| queryForObject() | Returns Single Row |
| queryForList() | Returns List |
| execute() | Execute Generic SQL |

---

# update()

Used for

- INSERT
- UPDATE
- DELETE

```java
jdbcTemplate.update(sql, name, email, age);
```

Returns

```
int

↓

Rows Affected
```

---

# query()

Used when multiple records are returned.

```java
List<Student> students =
jdbcTemplate.query(sql, rowMapper);
```

Returns

```
List<Object>
```

---

# queryForObject()

Used when only one record is expected.

```java
Student student =
jdbcTemplate.queryForObject(sql,rowMapper,id);
```

Returns

```
Single Object
```

---

# RowMapper

## What is RowMapper?

Database returns rows.

Java works with objects.

`RowMapper` converts each database row into a Java object.

```text
Database Row

      │

      ▼

RowMapper

      │

      ▼

Student Object
```

---

# Why RowMapper?

Without RowMapper

You manually read every column.

```java
student.setId(rs.getLong("id"));

student.setName(rs.getString("name"));
```

With RowMapper

Spring automatically maps each row.

Cleaner and reusable code.

---

# BeanPropertyRowMapper

Spring provides a ready-made implementation called

```
BeanPropertyRowMapper
```

It automatically maps

```
Database Column

↓

Java Field
```

Example

```
id

↓

id

-----------------

name

↓

name

-----------------

email

↓

email
```

No manual mapping required.

---

# CRUD using JdbcTemplate

## INSERT

```java
jdbcTemplate.update(sql,name,email,age);
```

---

## SELECT

```java
jdbcTemplate.query(sql,rowMapper);
```

---

## UPDATE

```java
jdbcTemplate.update(sql,age,id);
```

---

## DELETE

```java
jdbcTemplate.update(sql,id);
```

---

# Exception Handling

In JDBC

```
SQLException
```

In Spring JDBC

```
DataAccessException
```

Spring converts database-specific exceptions into a common exception hierarchy.

Benefits

✅ Cleaner Code

✅ Database Independent

---

# Best Practices

✅ Use `JdbcTemplate`

✅ Use `RowMapper`

✅ Keep SQL inside Repository

✅ Use Prepared Parameters

✅ Don't hardcode database credentials

✅ Use transactions when multiple queries depend on each other

---

# Interview Questions

### Why Spring JDBC?

To reduce boilerplate JDBC code.

---

### What is JdbcTemplate?

Core class that simplifies JDBC operations.

---

### Difference between query() and queryForObject()?

| query() | queryForObject() |
|----------|------------------|
| Multiple Rows | Single Row |
| Returns List | Returns Object |

---

### Why RowMapper?

To convert a database row into a Java object.

---

### Why DataAccessException?

It provides database-independent exception handling.

---

### Difference between JDBC and Spring JDBC?

| JDBC | Spring JDBC |
|------|-------------|
| Manual | Automatic |
| More Code | Less Code |
| SQLException | DataAccessException |
| Manual Mapping | RowMapper |

---

# Spring JDBC Flow

```text
Request

   │

   ▼

Controller

   │

   ▼

Service

   │

   ▼

Repository

   │

   ▼

JdbcTemplate

   │

   ▼

Database

   │

   ▼

Java Object

   │

   ▼

Response
```

---

# ⚡ Quick Revision

✅ Spring JDBC = Wrapper over JDBC

✅ Core Class = JdbcTemplate

✅ Multiple Rows = query()

✅ Single Row = queryForObject()

✅ Insert/Update/Delete = update()

✅ Object Mapping = RowMapper

✅ Auto Mapping = BeanPropertyRowMapper

✅ Exception = DataAccessException

✅ Less Code + Better Readability

---

# 🎯 Conclusion

Spring JDBC is built on top of JDBC to eliminate repetitive code while keeping full control over SQL. It provides cleaner, safer, and more maintainable database access through `JdbcTemplate`, `RowMapper`, and automatic resource management.

**Next Step:** Learn Spring Data JPA to understand how Spring further simplifies database operations by reducing even SQL writing.