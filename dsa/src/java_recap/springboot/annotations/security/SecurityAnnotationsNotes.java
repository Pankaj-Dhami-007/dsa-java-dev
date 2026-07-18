package java_recap.springboot.annotations.security;

/*

=================================================================
            SPRING SECURITY ANNOTATIONS
=================================================================

Annotations:
-------------
@EnableWebSecurity
@PreAuthorize
@Secured
@RolesAllowed

Core Concepts:
---------------
1. Authentication
2. Authorization
3. RBAC
4. Security Filter Chain
5. Method-Level Security
6. JWT Security
7. Spring Security Internals

=================================================================
                WHY SECURITY EXISTS?
=================================================================

Without security:
------------------

Anyone can:
------------

1. Access APIs
2. Modify data
3. Delete resources
4. Steal information

=================================================================
                SECURITY GOALS
=================================================================

1. Authentication
2. Authorization
3. Data Protection

=================================================================
                AUTHENTICATION
=================================================================

WHO are you?

=================================================================
                EXAMPLE
=================================================================

Login using:
-------------

username + password

=================================================================
                AUTHORIZATION
=================================================================

What are you allowed to do?

=================================================================
                EXAMPLE
=================================================================

ADMIN:
-------
Can delete users

NORMAL USER:
--------------
Cannot delete users

=================================================================
                WHAT IS SPRING SECURITY?
=================================================================

Spring Security is framework for:
----------------------------------

1. Authentication
2. Authorization
3. Session management
4. CSRF protection
5. JWT security
6. Method-level security

=================================================================
                INTERNAL WORKING
=================================================================

Request
   ↓
Security Filter Chain
   ↓
Authentication Check
   ↓
Authorization Check
   ↓
Controller

=================================================================
                MOST IMPORTANT
=================================================================

Spring Security works mainly using:
------------------------------------

Servlet Filters

=================================================================
                SECURITY FILTER CHAIN
=================================================================

Most important Spring Security concept.

=================================================================
                WHAT IS IT?
=================================================================

Chain of security filters executed
before controller reached.

=================================================================
                EXAMPLES
=================================================================

1. JWT Filter
2. Authentication Filter
3. Authorization Filter
4. CSRF Filter

=================================================================
                FLOW
=================================================================

HTTP Request
    ↓
Security Filters
    ↓
Authentication
    ↓
Authorization
    ↓
Controller

=================================================================
                1. @EnableWebSecurity
=================================================================

Main annotation enabling Spring Security.

=================================================================
                DEFINITION
=================================================================

@EnableWebSecurity tells Spring:

"Enable Spring Security infrastructure."

=================================================================
                EXAMPLE
=================================================================

@Configuration
@EnableWebSecurity
public class SecurityConfig {

}

=================================================================
                WHAT HAPPENS INTERNALLY?
=================================================================

Spring Boot:
-------------

1. Registers security filters
2. Creates filter chain
3. Enables authentication system
4. Enables authorization system

=================================================================
                IMPORTANT
=================================================================

Modern Spring Security mainly configured using:
------------------------------------------------

SecurityFilterChain Bean

=================================================================
                EXAMPLE
=================================================================

@Bean
public SecurityFilterChain security(
        HttpSecurity http
) throws Exception {

    return http.build();

}

=================================================================
                2. @PreAuthorize
=================================================================

Most important method-level security annotation.

=================================================================
                DEFINITION
=================================================================

@PreAuthorize checks authorization
BEFORE method execution.

=================================================================
                EXAMPLE
=================================================================

@PreAuthorize("hasRole('ADMIN')")

public void deleteUser() {

}

=================================================================
                WHAT HAPPENS?
=================================================================

Before method executes:
------------------------

Spring checks current user role.

=================================================================
                IF USER NOT ADMIN
=================================================================

Access denied exception thrown.

=================================================================
                INTERNAL WORKING
=================================================================

Request
    ↓
Authentication completed
    ↓
AOP Proxy intercepts method
    ↓
Security expression evaluated
    ↓
Access granted/denied

=================================================================
                VERY IMPORTANT
=================================================================

@PreAuthorize internally uses:
--------------------------------

Spring AOP

=================================================================
                EXPRESSIONS
=================================================================

hasRole('ADMIN')

hasAnyRole('ADMIN','MANAGER')

isAuthenticated()

permitAll()

denyAll()

=================================================================
                REAL PROJECT USE CASE
=================================================================

1. Admin APIs
2. Premium features
3. Role-based access
4. Tenant restrictions

=================================================================
                JWT FLOW
=================================================================

JWT Token
    ↓
JWT Filter validates token
    ↓
Authentication stored in SecurityContext
    ↓
@PreAuthorize checks roles

=================================================================
                SECURITY CONTEXT
=================================================================

Very important interview topic.

=================================================================
                WHAT IS IT?
=================================================================

Stores authenticated user information.

=================================================================
                STORES
=================================================================

1. Username
2. Roles
3. Authorities
4. Authentication object

=================================================================
                3. @Secured
=================================================================

Older role-based security annotation.

=================================================================
                EXAMPLE
=================================================================

@Secured("ROLE_ADMIN")

public void deleteUser() {

}

=================================================================
                IMPORTANT
=================================================================

Works only with roles.

=================================================================
                LIMITATION
=================================================================

Less flexible than @PreAuthorize.

=================================================================
                WHY?
=================================================================

No expression support.

=================================================================
                4. @RolesAllowed
=================================================================

JSR standard security annotation.

=================================================================
                EXAMPLE
=================================================================

@RolesAllowed("ADMIN")

public void deleteUser() {

}

=================================================================
                IMPORTANT
=================================================================

Java standard annotation,
not Spring-specific.

=================================================================
                DIFFERENCE
=================================================================

@PreAuthorize:
----------------
Most powerful/flexible

@Secured:
-----------
Simple role checks

@RolesAllowed:
----------------
Standard Java role annotation

=================================================================
                METHOD SECURITY
=================================================================

Most important enterprise topic.

=================================================================
                WHY NEEDED?
=================================================================

Even if URL secured,
service methods should also be secured.

=================================================================
                EXAMPLE
=================================================================

AdminService.deleteUser()

Only admins allowed.

=================================================================
                ENABLE METHOD SECURITY
=================================================================

@EnableMethodSecurity

=================================================================
                WHAT HAPPENS?
=================================================================

Spring enables AOP interception
for security annotations.

=================================================================
                REAL PROJECT FLOW
=================================================================

Client Request
    ↓
JWT Token Sent
    ↓
JWT Filter validates token
    ↓
User Authentication stored
    ↓
@PreAuthorize checks role
    ↓
Method executes

=================================================================
                ROLE VS AUTHORITY
=================================================================

Most asked interview topic.

=================================================================
                ROLE
=================================================================

High-level permission.

Example:
---------

ADMIN
USER

=================================================================
                AUTHORITY
=================================================================

Fine-grained permission.

Example:
---------

READ_USER
DELETE_USER

=================================================================
                IMPORTANT
=================================================================

Roles internally converted to:
-------------------------------

ROLE_ADMIN
ROLE_USER

=================================================================
                ACCESS DENIED FLOW
=================================================================

Unauthorized Request
    ↓
Security check fails
    ↓
AccessDeniedException
    ↓
403 Forbidden

=================================================================
                AUTHENTICATION FAILURE
=================================================================

Invalid token/login:
---------------------

401 Unauthorized

=================================================================
                401 VS 403
=================================================================

401:
-----
Not authenticated

403:
-----
Authenticated but not authorized

=================================================================
                REAL PROJECT USE CASES
=================================================================

E-commerce:
------------

ADMIN → manage products
USER → place orders

Banking:
---------

ADMIN → approve transactions
CUSTOMER → view accounts

Game Systems:
--------------

MODERATOR → ban players
PLAYER → play games

=================================================================
                INTERVIEW IMPORTANT
=================================================================

Q:
---
Difference between authentication
and authorization?

=================================================================
                ANSWER
=================================================================

Authentication:
----------------
Who are you?

Authorization:
----------------
What can you access?

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
How does @PreAuthorize work internally?

=================================================================
                ANSWER
=================================================================

Spring uses AOP proxy interception.
Before method execution,
security expressions evaluated
against current authenticated user.

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
Why @PreAuthorize preferred over @Secured?

=================================================================
                ANSWER
=================================================================

Because @PreAuthorize supports:
--------------------------------

1. Complex expressions
2. Dynamic checks
3. Method parameters
4. Multiple conditions

=================================================================
                TRICKY INTERVIEW QUESTION
=================================================================

Q:
---
Does Spring Security work using AOP?

PARTIALLY.

=================================================================
                ANSWER
=================================================================

URL security:
--------------
Filter Chain

Method security:
-----------------
AOP Proxies

=================================================================
                ANOTHER TRICKY QUESTION
=================================================================

Q:
---
Can controller security alone
protect application?

NO.

=================================================================
                WHY?
=================================================================

Service methods may still be accessed internally.

=================================================================
                COMMON MISTAKES
=================================================================

1. Storing passwords in plain text

2. Securing only controllers

3. Confusing 401 and 403

4. Using roles everywhere instead of authorities

=================================================================
                BEST PRACTICE
=================================================================

1. Use JWT for stateless APIs
2. Secure service layer
3. Prefer @PreAuthorize
4. Use role hierarchy carefully
5. Store minimal data in JWT

=================================================================
                REAL INTERVIEW ANSWER
=================================================================

Q. Explain Spring Security request flow.

Answer:
--------
1. Request enters Security Filter Chain
2. Authentication filter validates user/token
3. Authentication stored in SecurityContext
4. Authorization checks executed
5. If authorized → controller executes
6. Otherwise access denied response returned

=================================================================
                SUMMARY
=================================================================

@EnableWebSecurity
-------------------
Enables Spring Security infrastructure.

@PreAuthorize
---------------
Method-level authorization using expressions.

@Secured
----------
Role-based authorization.

@RolesAllowed
---------------
Standard Java role authorization.

Spring Security internally uses:
---------------------------------

1. Security Filter Chain
2. Servlet Filters
3. AOP Proxies
4. SecurityContext

=================================================================

*/

public class SecurityAnnotationsNotes {
}