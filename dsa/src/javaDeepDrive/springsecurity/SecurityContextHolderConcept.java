package javaDeepDrive.springsecurity;

public class SecurityContextHolderConcept {

    /*
    ===========================================================
                    SECURITY CONTEXT HOLDER
    ===========================================================

    This is one of the MOST IMPORTANT
    concepts in Spring Security.

    Most developers use:

    SecurityContextHolder
            .getContext()
            .getAuthentication();

    every day.

    But very few understand:

    How SecurityContextHolder works?

    Where data is stored?

    How multiple users don't overwrite
    each other's Authentication?

    How Tomcat threads interact with it?

    Why ThreadLocal is used?

    ===========================================================
                    BIG QUESTION
    ===========================================================

    We learned:

    SecurityContext

        stores

    Authentication

    ===========================================================

    But who stores SecurityContext?

    Answer:

    SecurityContextHolder

    ===========================================================

    Relationship

    SecurityContextHolder

            |

            v

    SecurityContext

            |

            v

    Authentication

            |

            v

    UserDetails

    ===========================================================
                    SIMPLE DEFINITION
    ===========================================================

    SecurityContextHolder is a utility class.

    Responsibility:

    Store and provide access to
    SecurityContext.

    ===========================================================

    Think:

    SecurityContext

        = Locker

    Authentication

        = User Identity Card

    SecurityContextHolder

        = Building Manager
          managing lockers

    ===========================================================
                    CLASS STRUCTURE
    ===========================================================

    public class SecurityContextHolder {

        static SecurityContextHolderStrategy
                strategy;

    }

    ===========================================================

    Important:

    SecurityContextHolder is completely
    static.

    ===========================================================

    Therefore:

    We never create object.

    Example:

    SecurityContextHolder.getContext();

    ===========================================================
                MOST COMMON CODE
    ===========================================================

    Authentication authentication =

        SecurityContextHolder
                .getContext()
                .getAuthentication();

    ===========================================================

    This retrieves current user.

    ===========================================================
                HUGE INTERVIEW QUESTION
    ===========================================================

    How does Spring know current user?

    Example:

    1000 users using application.

    ===========================================================

    User A

    ADMIN

    ===========================================================

    User B

    USER

    ===========================================================

    User C

    MANAGER

    ===========================================================

    How does Spring return correct
    Authentication?

    ===========================================================

    Answer:

    ThreadLocal

    ===========================================================
                    THREADLOCAL
    ===========================================================

    This is the REAL MAGIC.

    SecurityContextHolder stores:

    SecurityContext

    inside

    ThreadLocal

    ===========================================================

    Simplified

    ThreadLocal<SecurityContext>

    ===========================================================

    Each thread gets its own copy.

    ===========================================================
                    TOMCAT FLOW
    ===========================================================

    Browser

        |

        v

    Tomcat

        |

        v

    Thread Pool

    ===========================================================

    Thread-1

    User A

    ===========================================================

    Thread-2

    User B

    ===========================================================

    Thread-3

    User C

    ===========================================================

    Each request handled by different thread.

    ===========================================================
                THREADLOCAL STORAGE
    ===========================================================

    Thread-1

        |

        v

    SecurityContext

        |

        v

    Authentication(User A)

    ===========================================================

    Thread-2

        |

        v

    SecurityContext

        |

        v

    Authentication(User B)

    ===========================================================

    Thread-3

        |

        v

    SecurityContext

        |

        v

    Authentication(User C)

    ===========================================================

    Therefore:

    No collision.

    No overwriting.

    ===========================================================
                COMPLETE REQUEST FLOW
    ===========================================================

    Browser

        |

        v

    Tomcat Thread

        |

        v

    SecurityContextHolderFilter

        |

        v

    Create SecurityContext

        |

        v

    Store In ThreadLocal

        |

        v

    Authentication

        |

        v

    Authorization

        |

        v

    Controller

        |

        v

    Response

        |

        v

    Clear ThreadLocal

    ===========================================================
                SECURITYCONTEXTHOLDERFILTER
    ===========================================================

    Spring Security 6+

    Uses:

    SecurityContextHolderFilter

    ===========================================================

    Responsibilities:

    Create Context

    Load Context

    Save Context

    Clear Context

    ===========================================================

    Important:

    Prevents user data leakage.

    ===========================================================
                WHAT IF CONTEXT NOT CLEARED?
    ===========================================================

    Imagine:

    Request-1

    User A

    Thread-1

    ===========================================================

    Request Ends

    Context NOT Cleared

    ===========================================================

    Request-2

    User B

    Same Thread-1 reused

    ===========================================================

    User B may see User A Authentication.

    Huge Security Problem.

    ===========================================================

    Therefore:

    Context always cleared.

    ===========================================================
                    INTERNAL FLOW
    ===========================================================

    Request Start

        |

        v

    Create SecurityContext

        |

        v

    Store ThreadLocal

        |

        v

    Process Request

        |

        v

    Finally Block

        |

        v

    Clear Context

    ===========================================================
                THREADLOCAL EXAMPLE
    ===========================================================

    Thread-1

       username = pankaj

    ===========================================================

    Thread-2

       username = rahul

    ===========================================================

    Thread-3

       username = aman

    ===========================================================

    Same code.

    Different users.

    Because:

    Different ThreadLocal values.

    ===========================================================
                MODES OF STORAGE
    ===========================================================

    SecurityContextHolder supports:

    1. MODE_THREADLOCAL

    2. MODE_INHERITABLETHREADLOCAL

    3. MODE_GLOBAL

    ===========================================================
                MODE_THREADLOCAL
    ===========================================================

    Default mode.

    Most common.

    Most secure.

    ===========================================================

    Each thread gets independent context.

    ===========================================================

    Thread-1

    User A

    ===========================================================

    Thread-2

    User B

    ===========================================================

    Cannot access each other's context.

    ===========================================================
                MODE_INHERITABLETHREADLOCAL
    ===========================================================

    Child threads inherit parent's context.

    ===========================================================

    Parent Thread

    User A

            |

            v

    Child Thread

    User A

    ===========================================================

    Useful in some async scenarios.

    Rarely used.

    ===========================================================
                MODE_GLOBAL
    ===========================================================

    Single context shared globally.

    ===========================================================

    Every thread sees same context.

    ===========================================================

    Not suitable for web applications.

    Almost never used.

    ===========================================================
                @ASYNC PROBLEM
    ===========================================================

    Very Important Senior Interview Topic.

    ===========================================================

    Request Thread

         |

         v

    SecurityContext Available

    ===========================================================

    @Async Thread

         |

         v

    SecurityContext Missing

    ===========================================================

    Why?

    ThreadLocal.

    Different thread.

    ===========================================================

    Example:

    @Async
    public void process() {

        Authentication auth =

        SecurityContextHolder
                .getContext()
                .getAuthentication();

    }

    ===========================================================

    auth may be null.

    ===========================================================
                WHY?
    ===========================================================

    Because ThreadLocal data does not
    automatically move to another thread.

    ===========================================================
                SOLUTIONS
    ===========================================================

    Solution 1

    MODE_INHERITABLETHREADLOCAL

    ===========================================================

    Solution 2

    DelegatingSecurityContextRunnable

    ===========================================================

    Solution 3

    DelegatingSecurityContextExecutor

    ===========================================================

    Solution 4

    Manually pass required information

    ===========================================================
                JWT FLOW
    ===========================================================

    JWT Request

         |

         v

    JWT Filter

         |

         v

    Create Authentication

         |

         v

    SecurityContext

         |

         v

    SecurityContextHolder

         |

         v

    ThreadLocal

         |

         v

    Controller

         |

         v

    Request Ends

         |

         v

    Clear Context

    ===========================================================
                SESSION FLOW
    ===========================================================

    Login

       |

       v

    SecurityContext

       |

       v

    HttpSession

       |

       v

    Future Requests

       |

       v

    Load Context

       |

       v

    ThreadLocal

    ===========================================================

    Stateful Authentication.

    ===========================================================
                COMPLETE INTERNAL FLOW
    ===========================================================

    Browser

       |

       v

    Embedded Tomcat

       |

       v

    Thread Pool

       |

       v

    Thread Selected

       |

       v

    ApplicationFilterChain

       |

       v

    DelegatingFilterProxy

       |

       v

    FilterChainProxy

       |

       v

    SecurityContextHolderFilter

       |

       v

    Create SecurityContext

       |

       v

    ThreadLocal

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

    SecurityContext

       |

       v

    AuthorizationFilter

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

    Response

       |

       v

    Clear ThreadLocal

    ===========================================================
                MOST ASKED INTERVIEW QUESTIONS
    ===========================================================

    Q1. What is SecurityContextHolder?

    Stores and provides access to
    SecurityContext.

    ------------------------------------------------

    Q2. Where does SecurityContextHolder
        store data?

    ThreadLocal.

    ------------------------------------------------

    Q3. Why ThreadLocal?

    Separate SecurityContext for each thread.

    ------------------------------------------------

    Q4. Why not static variable?

    Multiple users would overwrite each other.

    ------------------------------------------------

    Q5. What is default mode?

    MODE_THREADLOCAL

    ------------------------------------------------

    Q6. What is
        MODE_INHERITABLETHREADLOCAL?

    Child thread inherits parent context.

    ------------------------------------------------

    Q7. What is MODE_GLOBAL?

    Single context shared by all threads.

    ------------------------------------------------

    Q8. Why SecurityContext cleared?

    Prevent user data leakage.

    ------------------------------------------------

    Q9. Why Authentication becomes null
        inside @Async?

    Different thread.

    ThreadLocal data unavailable.

    ------------------------------------------------

    Q10. Which filter manages context?

    SecurityContextHolderFilter

    ===========================================================
                        THEORY
    ===========================================================

    SecurityContextHolder is NOT a database.

    SecurityContextHolder is NOT session.

    SecurityContextHolder is NOT cache.

    ===========================================================

    It is simply a static utility class
    that provides access to SecurityContext.

    ===========================================================

    The actual storage mechanism is:

    ThreadLocal

    ===========================================================

    Every incoming request gets a Tomcat thread.

    Every Tomcat thread gets its own:

    SecurityContext

    ===========================================================

    SecurityContext contains:

    Authentication

    ===========================================================

    Authentication contains:

    Principal

    Credentials

    Authorities

    ===========================================================

    Therefore complete hierarchy:

    SecurityContextHolder

            |

            v

    ThreadLocal

            |

            v

    SecurityContext

            |

            v

    Authentication

            |

            v

    UserDetails

            |

            v

    GrantedAuthorities

    ===========================================================

    This design ensures:

    1. Thread Safety

    2. User Isolation

    3. Fast Access

    4. No User Collision

    5. Secure Request Processing

    ===========================================================

    Spring Security's entire request-level
    identity management revolves around:

    SecurityContextHolder

            +

    ThreadLocal

    ===========================================================
    */
}