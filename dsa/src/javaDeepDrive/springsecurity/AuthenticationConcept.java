package javaDeepDrive.springsecurity;

public class AuthenticationConcept {

    /*
    ===========================================================
                    AUTHENTICATION
    ===========================================================

    Authentication is the process of verifying:

    "WHO ARE YOU?"

    Before allowing access, application must verify:

    Are you really the person you claim to be?

    ===========================================================
                    SIMPLE EXAMPLE
    ===========================================================

    Login Form

    Username : pankaj

    Password : 12345

    User says:

    "I am Pankaj"

    Application says:

    "Prove it"

    User provides password.

    Application verifies credentials.

    If correct:

    Authentication Success

    If wrong:

    Authentication Failure

    ===========================================================
            AUTHENTICATION VS AUTHORIZATION
    ===========================================================

    Authentication

    WHO ARE YOU?

    Example:

    Username + Password

    ------------------------------------------

    Authorization

    WHAT CAN YOU DO?

    Example:

    ADMIN

    Can delete users

    USER

    Cannot delete users

    ------------------------------------------

    Authentication ALWAYS happens first.

    Then Authorization.

    ===========================================================
                COMPLETE REQUEST FLOW
    ===========================================================

    Most developers only know:

    Request
      ↓
    Filter
      ↓
    Controller

    But internally many more components exist.

    ===========================================================
                COMPLETE INTERNAL FLOW
    ===========================================================

    Browser / Mobile App

          |

          v

    TCP Connection

          |

          v

    Embedded Tomcat

          |

          v

    Tomcat Connector

          |

          v

    Servlet Container

          |

          v

    Servlet Filter Chain

          |

          v

    DelegatingFilterProxy

          |

          v

    FilterChainProxy

          |

          v

    SecurityFilterChain

          |

          v

    Authentication Filter

          |

          v

    AuthenticationManager

          |

          v

    AuthenticationProvider

          |

          v

    UserDetailsService

          |

          v

    Database

          |

          v

    PasswordEncoder

          |

          v

    Authentication Object

          |

          v

    SecurityContext

          |

          v

    SecurityContextHolder

          |

          v

    DispatcherServlet

          |

          v

    Controller

          |

          v

    Service

          |

          v

    Repository

          |

          v

    Database

          |

          v

    Response

    ===========================================================
                STEP 1
                REQUEST ARRIVES
    ===========================================================

    Browser sends:

    POST /login

    {
       username : "pankaj",
       password : "12345"
    }

    Request reaches:

    Embedded Tomcat

    ===========================================================
                WHAT IS TOMCAT?
    ===========================================================

    Tomcat is a Servlet Container.

    Responsibilities:

    1. Accept HTTP Request
    2. Create HttpServletRequest
    3. Create HttpServletResponse

    4. Manage Threads
    5. Execute Filters
    6. Execute Servlets

    Spring Boot application actually runs
    inside embedded Tomcat.

    ===========================================================
                STEP 2
                FILTER CHAIN
    ===========================================================

    Tomcat creates request object.

    Then request enters:
    Servlet Filter Chain

    Example:

    Logging Filter
    Security Filter
    Custom Filters
    etc.

    ===========================================================
                STEP 3
                DELEGATING FILTER PROXY
    ===========================================================

    Important Interview Question.

    Spring Security is NOT directly registered
    into Tomcat filter chain.

    Instead:

    Tomcat Filter Chain

          |

          v

    DelegatingFilterProxy

          |

          v

    Spring Security

    ===========================================================
                WHY DELEGATINGFILTERPROXY?
    ===========================================================

    Tomcat knows Filters.

    Tomcat does NOT know Spring Beans.
    Spring Security filters are Spring Beans.
    So bridge is needed.

    That bridge is:

    DelegatingFilterProxy

    Think:

    Tomcat World

          ↔

    Spring World

    Bridge:

    DelegatingFilterProxy

    ===========================================================
                STEP 4
                FILTERCHAINPROXY
    ===========================================================

    DelegatingFilterProxy forwards request to:

    FilterChainProxy

    FilterChainProxy is actual entry point
    of Spring Security.

    Important:

    Many developers think SecurityFilterChain
    is entry point.

    Not exactly.

    Real flow:

    DelegatingFilterProxy

         ↓

    FilterChainProxy

         ↓

    SecurityFilterChain

    ===========================================================
                WHAT FILTERCHAINPROXY DOES?
    ===========================================================

    Responsibilities:

    1. Find matching SecurityFilterChain
    2. Execute filters
    3. Maintain order
    4. Manage security processing

    ===========================================================
                STEP 5
                SECURITYFILTERCHAIN
    ===========================================================

    FilterChainProxy selects:

    SecurityFilterChain

    Example:

    /api/**

    Uses:

    JWT Security Chain

    ----------------------------------

    /admin/**

    Uses:

    Admin Security Chain

    ===========================================================
                STEP 6
                AUTHENTICATION FILTER
    ===========================================================

    Example:

    UsernamePasswordAuthenticationFilter

    Responsibilities:

    1. Extract username
    2. Extract password
    3. Create Authentication object

    Example:

    Authentication auth =
        new UsernamePasswordAuthenticationToken(
            username,
            password
        );

    Important:

    User NOT authenticated yet.
    This object only contains credentials.

    ===========================================================
                TOKEN STATE
    ===========================================================

    Before Authentication

    authenticated = false

    Example:

    UsernamePasswordAuthenticationToken

    (
       username,
       password
    )

    User merely claims identity.
    Verification not done yet.

    ===========================================================
                STEP 7
                AUTHENTICATIONMANAGER
    ===========================================================

    Authentication Filter calls:

    AuthenticationManager

    Example:

    authenticationManager.authenticate(auth)

    ===========================================================
                WHAT IS AUTHENTICATIONMANAGER?
    ===========================================================

    AuthenticationManager is coordinator.
    It delegates authentication work.

    Think:

    Hospital Receptionist
    Receptionist doesn't perform surgery.
    Receptionist forwards patient
    to correct doctor.

    Same here.

    AuthenticationManager forwards
    authentication request.

    ===========================================================
                STEP 8
                PROVIDERMANAGER
    ===========================================================

    Actual implementation:

    ProviderManager
    Important Interview Question.
    AuthenticationManager is interface.
    ProviderManager is default implementation.

    ===========================================================
                STEP 9
                AUTHENTICATIONPROVIDER
    ===========================================================

    ProviderManager forwards request to:

    AuthenticationProvider

    Examples:

    DaoAuthenticationProvider
    JwtAuthenticationProvider
    OAuthAuthenticationProvider
    LDAPAuthenticationProvider

    ===========================================================
                WHY MULTIPLE PROVIDERS?
    ===========================================================

    Application may support:

    Username Password Login
    Google Login
    JWT Login
    LDAP Login

    Different provider for each mechanism.

    ===========================================================
                STEP 10
                DAOAUTHENTICATIONPROVIDER
    ===========================================================

    Most common provider.

    Used for:

    Username Password Authentication

    Responsibilities:

    Load User
    Verify Password
    Create Authenticated User

    ===========================================================
                STEP 11
                USERDETAILSSERVICE
    ===========================================================

    DaoAuthenticationProvider calls:

    UserDetailsService

    Example:

    loadUserByUsername()

    ===========================================================
                USER LOOKUP FLOW
    ===========================================================

    DaoAuthenticationProvider

            |

            v

    UserDetailsService

            |

            v

    UserRepository

            |

            v

    Database

    ===========================================================
                STEP 12
                USERDETAILS
    ===========================================================

    Database record:

    id = 1
    username = pankaj
    password = bcrypt_hash
    role = ADMIN

    Convert into:

    UserDetails Object

    Contains:

    Username
    Password
    Authorities
    Account Status

    ===========================================================
                STEP 13
                PASSWORDENCODER
    ===========================================================

    Password verification starts.

    Raw Password: 12345

    Stored Password: $2a$10$xxxxxxxxxxx

    PasswordEncoder checks:

    matches(
        rawPassword,
        encodedPassword
    )

    ===========================================================
                WHY NOT EQUALS()?
    ===========================================================

    Wrong:

    raw.equals(encoded)

    Because:

    Database stores encrypted password.

    Raw password never equals encrypted password.

    PasswordEncoder understands hashing.

    ===========================================================
                STEP 14
                AUTHENTICATION SUCCESS
    ===========================================================

    Provider creates new Authentication object.

    Important:

    Previous object

    authenticated=false

    --------------------------------

    New object

    authenticated=true

    Contains:
    Username
    Roles
    Authorities
    Principal

    ===========================================================
                STEP 15
                SECURITYCONTEXT
    ===========================================================

    Authentication stored inside:

    SecurityContext
    SecurityContext

         |

         v

    Authentication

    ===========================================================
                STEP 16
                SECURITYCONTEXTHOLDER
    ===========================================================

    SecurityContextHolder

          |

          v

    SecurityContext

          |

          v

    Authentication

    ===========================================================
                WHY STORE IT?
    ===========================================================

    Later any component can access:

    Logged In User

    Example:

    SecurityContextHolder
            .getContext()
            .getAuthentication()

    ===========================================================
                STEP 17
                CONTROLLER EXECUTION
    ===========================================================

    Now request reaches:

    DispatcherServlet

          |

          v

    Controller

    Because authentication succeeded.

    ===========================================================
                JWT AUTHENTICATION FLOW
    ===========================================================

    JWT works differently.

    Request

         |

         v

    JwtAuthenticationFilter

         |

         v

    Extract Token

         |

         v

    Validate Signature

         |

         v

    Extract Username

         |

         v

    Load User

         |

         v

    Create Authentication

         |

         v

    SecurityContextHolder

         |

         v

    Continue Filter Chain

    No Password Verification.
    No Session.

    Stateless.

    ===========================================================
                INTERVIEW QUESTIONS
    ===========================================================

    Q1. What is Authentication?

    Verify identity of user.

    ------------------------------------------------

    Q2. What is AuthenticationManager?

    Coordinator that delegates authentication.

    ------------------------------------------------

    Q3. What is default implementation?

    ProviderManager.

    ------------------------------------------------

    Q4. What is AuthenticationProvider?

    Component that performs actual authentication.

    ------------------------------------------------

    Q5. What is DaoAuthenticationProvider?

    Username/password authentication provider.

    ------------------------------------------------

    Q6. Why UserDetailsService?

    Loads user from datasource.

    ------------------------------------------------

    Q7. Why PasswordEncoder?

    Passwords are hashed.

    ------------------------------------------------

    Q8. What is DelegatingFilterProxy?

    Bridge between Tomcat and Spring Security.

    ------------------------------------------------

    Q9. What is FilterChainProxy?

    Internal Spring Security entry point.

    ------------------------------------------------

    Q10. Where is authenticated user stored?

    SecurityContext.

    ------------------------------------------------

    Q11. Where is SecurityContext stored?

    SecurityContextHolder.

    ------------------------------------------------

    Q12. AuthenticationManager vs
         AuthenticationProvider?

    Manager delegates.

    Provider authenticates.

    ===========================================================
                MEMORY TRICK
    ===========================================================

    Browser

      ↓

    Tomcat

      ↓

    Servlet Filter Chain

      ↓

    DelegatingFilterProxy

      ↓

    FilterChainProxy

      ↓

    SecurityFilterChain

      ↓

    Authentication Filter

      ↓

    AuthenticationManager

      ↓

    ProviderManager

      ↓

    AuthenticationProvider

      ↓

    UserDetailsService

      ↓

    Database

      ↓

    PasswordEncoder

      ↓

    Authentication

      ↓

    SecurityContext

      ↓

    SecurityContextHolder

      ↓

    DispatcherServlet

      ↓

    Controller

    ===========================================================
    */
}