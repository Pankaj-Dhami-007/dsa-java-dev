package java_recap.springboot.annotations.validation;

/*

=================================================================
            @Valid AND @Validated
=================================================================

Core Concepts:
---------------
1. Input Validation
2. Bean Validation API
3. Hibernate Validator
4. Request Validation
5. Fail Fast Validation
6. DTO Validation
7. Nested Validation
8. Validation Groups

=================================================================
                WHY VALIDATION EXISTS?
=================================================================

Imagine frontend sends:

{
   "name": "",
   "email": "wrong-email",
   "age": -10
}

Without validation:
--------------------

Invalid data enters database.

=================================================================
                PROBLEMS
=================================================================

1. Corrupted data
2. Business rule violation
3. Database inconsistency
4. Security issues
5. Application crashes

=================================================================
                SOLUTION
=================================================================

Validation Layer.

=================================================================
                WHAT IS VALIDATION?
=================================================================

Validation means:
------------------

Checking whether incoming data
follows business rules or not.

=================================================================
                EXAMPLES
=================================================================

1. Email must be valid
2. Password length >= 8
3. Age cannot be negative
4. Name cannot be blank

=================================================================
                WHO PROVIDES VALIDATION?
=================================================================

Spring Boot uses:
------------------

Bean Validation API

Implementation:
----------------

Hibernate Validator

=================================================================
                IMPORTANT
=================================================================

Hibernate Validator is default implementation
of Jakarta Bean Validation specification.

=================================================================
                1. @Valid
=================================================================

Most commonly used validation annotation.

=================================================================
                DEFINITION
=================================================================

@Valid tells Spring:

"Validate this object before method execution."

=================================================================
                REAL PROJECT EXAMPLE
=================================================================

POST /users

Request:
----------

{
   "name": "",
   "email": "wrong"
}

=================================================================
                DTO
=================================================================

public class UserRequest {

    @NotBlank
    private String name;

    @Email
    private String email;

}

=================================================================
                CONTROLLER
=================================================================

@PostMapping("/users")

public String createUser(

    @Valid
    @RequestBody UserRequest request

) {

    return "USER CREATED";

}

=================================================================
                WHAT HAPPENS INTERNALLY?
=================================================================

Client Request
        ↓
JSON converted into DTO
        ↓
@Valid detected
        ↓
Hibernate Validator executes
        ↓
Checks all validation annotations
        ↓
If validation fails:
        ↓
Exception thrown
        ↓
400 Bad Request returned

=================================================================
                VERY IMPORTANT
=================================================================

Method executes ONLY IF
validation passes.

=================================================================
                VALIDATION FLOW
=================================================================

JSON Request
    ↓
Jackson Deserialization
    ↓
DTO Object Created
    ↓
Validation Engine Runs
    ↓
Constraint Checks
    ↓
Success → Controller Executes
Failure → Exception

=================================================================
                COMMON VALIDATION ANNOTATIONS
=================================================================

@NotNull
@NotBlank
@NotEmpty
@Email
@Size
@Min
@Max
@Pattern

=================================================================
                REAL PROJECT USE CASES
=================================================================

User Registration:
-------------------

1. Email validation
2. Password validation
3. Phone validation

Payment APIs:
---------------

1. Amount validation
2. Currency validation

Booking Systems:
-----------------

1. Date validation
2. Seat count validation

=================================================================
                IMPORTANT DIFFERENCE
=================================================================

@NotNull
-----------
Value must not be null

@NotEmpty
------------
Not null + size > 0

@NotBlank
------------
Not null + trimmed text not empty

=================================================================
                EXAMPLES
=================================================================

@NotNull
Accepts:
---------
""

@NotEmpty
Rejects:
---------
""

@NotBlank
Rejects:
---------
"   "

=================================================================
                VALIDATION FAILURE
=================================================================

Spring throws:
---------------

MethodArgumentNotValidException

=================================================================
                RESPONSE
=================================================================

400 BAD REQUEST

=================================================================
                WHY 400?
=================================================================

Client sent invalid request data.

=================================================================
                2. @Validated
=================================================================

Advanced version of validation.

=================================================================
                DEFINITION
=================================================================

@Validated enables:
--------------------

1. Method-level validation
2. Validation groups
3. Advanced validation features

=================================================================
                MOST IMPORTANT DIFFERENCE
=================================================================

@Valid:
--------
Basic validation

@Validated:
-------------
Advanced Spring validation

=================================================================
                METHOD LEVEL VALIDATION
=================================================================

@Service
@Validated
public class PaymentService {

    public void pay(

        @Min(1)
        int amount

    ) {

    }

}

=================================================================
                WHAT HAPPENS?
=================================================================

Before method execution:
-------------------------

Spring validates parameter.

=================================================================
                INTERNAL WORKING
=================================================================

Spring creates proxy around bean.
Proxy intercepts method call.
Validation runs before execution.

=================================================================
                VERY IMPORTANT
=================================================================

Method validation uses:
------------------------

AOP Proxy Mechanism

=================================================================
                VALIDATION GROUPS
=================================================================

Most important enterprise feature.

=================================================================
                WHY NEEDED?
=================================================================

Different validation rules
for different operations.

=================================================================
                EXAMPLE
=================================================================

User Create:
-------------
Password mandatory

User Update:
-------------
Password optional

=================================================================
                GROUP EXAMPLE
=================================================================

public interface CreateGroup {}
public interface UpdateGroup {}

=================================================================
                DTO
=================================================================

public class UserRequest {

    @NotBlank(groups = CreateGroup.class)
    private String password;

}

=================================================================
                USAGE
=================================================================

@PostMapping

public void create(

    @Validated(CreateGroup.class)
    @RequestBody UserRequest request

) {

}

=================================================================
                WHY IMPORTANT?
=================================================================

Enterprise applications often require:
---------------------------------------

Different validation flows.

=================================================================
                NESTED VALIDATION
=================================================================

Most important real-world feature.

=================================================================
                EXAMPLE
=================================================================

public class UserRequest {

    @Valid
    private AddressRequest address;

}

=================================================================
                WHY @Valid INSIDE FIELD?
=================================================================

Triggers nested object validation.

=================================================================
                WITHOUT @Valid
=================================================================

Nested object validation skipped.

=================================================================
                CUSTOM VALIDATION
=================================================================

You can create custom validators.

=================================================================
                EXAMPLE
=================================================================

@ValidPassword

=================================================================
                USED FOR
=================================================================

1. Strong password rules
2. Business constraints
3. Custom formats

=================================================================
                VALIDATION EXCEPTION FLOW
=================================================================

Invalid Request
    ↓
Validator detects failure
    ↓
ConstraintViolationException
OR
MethodArgumentNotValidException
    ↓
Global Exception Handler
    ↓
400 Response

=================================================================
                REAL LIFE PROJECT SCENARIO
=================================================================

E-commerce Checkout API:
-------------------------

1. Product quantity > 0
2. Address mandatory
3. Payment method valid
4. Coupon format valid

Validation prevents invalid orders.

=================================================================
                INTERVIEW IMPORTANT
=================================================================

Q:
---
Difference between @Valid and @Validated?

=================================================================
                ANSWER
=================================================================

@Valid:
--------
Standard Bean Validation annotation.

@Validated:
-------------
Spring-specific annotation supporting:
1. Validation groups
2. Method validation
3. Advanced validation features

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
How does @Valid work internally?

=================================================================
                ANSWER
=================================================================

1. Spring detects @Valid
2. Bean Validation API invoked
3. Hibernate Validator checks constraints
4. Validation exception thrown if failed
5. Controller method skipped

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
Why validation should happen in DTO
instead of Entity?

=================================================================
                ANSWER
=================================================================

DTO validation:
----------------

1. API layer validation
2. Better separation of concerns
3. Different rules for different APIs
4. Prevent entity pollution

=================================================================
                TRICKY INTERVIEW QUESTION
=================================================================

Q:
---
Does @Valid work without validation dependency?

NO.

Need:
------

spring-boot-starter-validation

=================================================================
                ANOTHER TRICKY QUESTION
=================================================================

Q:
---
Why method validation requires @Validated?

Answer:
--------
Because Spring uses proxy-based interception
for method-level validation.

=================================================================
                COMMON MISTAKES
=================================================================

1. Validating entities directly

2. Missing @Valid on nested objects

3. Using @NotNull instead of @NotBlank

4. Not handling validation exceptions properly

=================================================================
                BEST PRACTICE
=================================================================

1. Validate DTOs
2. Use nested validation carefully
3. Use validation groups in enterprise apps
4. Centralize validation exception handling
5. Use custom validators for business rules

=================================================================
                REAL INTERVIEW ANSWER
=================================================================

Q. Explain complete flow of @Valid.

Answer:
--------
1. Client sends request
2. Jackson converts JSON into DTO
3. Spring detects @Valid
4. Hibernate Validator validates constraints
5. If validation fails → exception thrown
6. If validation succeeds → controller executes

=================================================================
                SUMMARY
=================================================================

@Valid
--------
Basic object validation.

@Validated
-------------
Advanced Spring validation.

Hibernate Validator performs actual validation.

Validation prevents invalid data
from entering business layer/database.

=================================================================

*/

public class ValidationAnnotationsNotes {
}