package javaDeepDrive.springsecurity;

public class SpringSecurityConcept {

    /*
    ===========================================================
                    SPRING SECURITY COMPLETE ROADMAP
    ===========================================================

    Spring Security is a framework that provides:

    1. Authentication
    2. Authorization
    3. Protection against common attacks
    4. Session Management
    5. JWT Security
    6. OAuth2 Integration
    7. Method-Level Security

    ===========================================================
                    WHY SPRING SECURITY?
    ===========================================================

    Imagine:

    Banking Application

    Login API
    Transfer Money API
    Account Details API

    Without Security:

    Anyone can call APIs.

    Problems:

    Unauthorized Access
    Data Leakage
    Account Hacking
    Fraud Transactions

    Solution:

    Spring Security

    ===========================================================
                    CORE RESPONSIBILITIES
    ===========================================================

    Spring Security handles:

    1. User Authentication

       Who are you?

    2. Authorization

       What are you allowed to do?

    3. Session Management

       Track logged-in users.

    4. Password Encryption

       Store passwords securely.

    5. CSRF Protection

       Prevent forged requests.

    6. CORS Handling

       Allow trusted frontend applications.

    ===========================================================
                    AUTHENTICATION VS AUTHORIZATION
    ===========================================================

    Authentication

    Verify identity.

    Example:

    Username = admin
    Password = admin123

    System verifies credentials.

    Authorization

    Verify permissions.

    Example:

    ADMIN -> Can delete users

    USER -> Cannot delete users

    ===========================================================
                    HIGH LEVEL FLOW
    ===========================================================

    Client

      |

      v

    Security Filter Chain

      |

      v

    Authentication Filter

      |

      v

    Authentication Manager

      |

      v

    Authentication Provider

      |

      v

    UserDetailsService

      |

      v

    Database

      |

      v

    User Found

      |

      v

    Password Verification

      |

      v

    Security Context

      |

      v

    Controller

    ===========================================================
                    IMPORTANT COMPONENTS
    ===========================================================

    1. SecurityFilterChain

       Entry point of security.

    2. Authentication

       Represents logged-in user.

    3. AuthenticationManager

       Coordinates authentication.

    4. AuthenticationProvider

       Performs actual authentication.

    5. UserDetailsService

       Loads user from database.

    6. PasswordEncoder

       Encrypts passwords.

    7. SecurityContext

       Stores logged-in user details.

    8. SecurityContextHolder

       Holds SecurityContext.

    ===========================================================
                    SECURITY FILTER CHAIN
    ===========================================================

    Every request first passes through:

    Security Filter Chain

    Example:

    GET /users

    Request

      |

      v

    JWT Filter

      |

      v

    Authentication Filter

      |

      v

    Authorization Filter

      |

      v

    Controller

    ===========================================================
                    PASSWORD STORAGE
    ===========================================================

    NEVER STORE:

    password = admin123

    STORE:

    $2a$10$hduwud823jdhw...

    BCryptPasswordEncoder used.

    ===========================================================
                    SESSION BASED AUTHENTICATION
    ===========================================================

    Old Traditional Approach

    User Login

      |

      v

    Session Created

      |

      v

    Session ID Stored

      |

      v

    Server Maintains State

    Stateful

    ===========================================================
                    JWT AUTHENTICATION
    ===========================================================

    Modern Approach

    User Login

      |

      v

    JWT Generated

      |

      v

    Client Stores Token

      |

      v

    Client Sends Token

      |

      v

    Server Validates Token

    Stateless

    ===========================================================
                    JWT STRUCTURE
    ===========================================================

    Header

    Payload

    Signature

    Example:

    eyJhbGciOiJIUzI1NiJ9

    .

    eyJzdWIiOiJwYW5rYWoifQ

    .

    xxxxxxxxxxxxxxxxxx

    ===========================================================
                    ROLE BASED ACCESS
    ===========================================================

    ADMIN

      Create User
      Delete User
      Update User

    USER

      View Data

    Example:

    hasRole("ADMIN")

    ===========================================================
                    METHOD LEVEL SECURITY
    ===========================================================

    @PreAuthorize

    @PostAuthorize

    @Secured

    Example:

    @PreAuthorize("hasRole('ADMIN')")

    ===========================================================
                    SECURITY ATTACKS HANDLED
    ===========================================================

    1. CSRF

    2. Session Fixation

    3. Clickjacking

    4. Password Attacks

    5. Unauthorized Access

    ===========================================================
                    SPRING SECURITY LEARNING ORDER
    ===========================================================

    STEP 1

    Security Architecture

    STEP 2

    Authentication

    STEP 3

    Authorization

    STEP 4

    UserDetailsService

    STEP 5

    PasswordEncoder

    STEP 6

    SecurityContext

    STEP 7

    Security Filter Chain

    STEP 8

    JWT

    STEP 9

    JWT Filter

    STEP 10

    Method Security

    STEP 11

    OAuth2

    STEP 12

    Security Best Practices

    ===========================================================
                    MOST ASKED INTERVIEW QUESTIONS
    ===========================================================

    Q1. Authentication vs Authorization?

    Q2. What is SecurityFilterChain?

    Q3. Why PasswordEncoder?

    Q4. Why BCrypt?

    Q5. What is SecurityContext?

    Q6. What is AuthenticationManager?

    Q7. AuthenticationProvider vs UserDetailsService?

    Q8. How JWT authentication works?

    Q9. Why OncePerRequestFilter?

    Q10. Session vs JWT?

    Q11. Role vs Authority?

    Q12. What is CSRF?

    Q13. Why disable CSRF in JWT?

    Q14. How OAuth2 works?

    Q15. Explain complete login flow in Spring Security.

    ===========================================================
    */
}