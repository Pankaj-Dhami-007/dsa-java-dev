package javaDeepDrive.springsecurity;

public class SecurityContextRepositoryAndSessionManagementConcept {

    /*
    ===========================================================
        SECURITY CONTEXT REPOSITORY
                        +
            SESSION MANAGEMENT
    ===========================================================

    This topic explains:

    How Spring remembers logged-in users.

    Most developers know:

    SecurityContextHolder

    But they don't know:

    How Authentication survives across requests?

    ===========================================================

    BIG QUESTION
    ===========================================================

    Request #1

    Login Success

    Authentication stored inside:

    SecurityContext

    ===========================================================

    Request Ends

    Thread dies.

    ThreadLocal cleared.

    ===========================================================

    Question:

    How does Spring know user is still
    logged in during Request #2 ?

    ===========================================================

    Answer:

    SecurityContextRepository

    +
    Session Management

    ===========================================================
                    COMPLETE FLOW
    ===========================================================

    Login Request

         |

         v

    Authentication Success

         |

         v

    SecurityContext

         |

         v

    SecurityContextRepository

         |

         v

    HttpSession

         |

         v

    Request Ends

    ===========================================================

    Next Request

         |

         v

    HttpSession

         |

         v

    SecurityContextRepository

         |

         v

    SecurityContextHolder

         |

         v

    Authentication Restored

    ===========================================================

    User remains logged in.

    ===========================================================
                    SECURITYCONTEXTREPOSITORY
    ===========================================================

    Interface responsible for:

    Loading SecurityContext

    Saving SecurityContext

    Removing SecurityContext

    ===========================================================

    Interface

    ===========================================================

    public interface SecurityContextRepository {

        SecurityContext loadContext(...);

        void saveContext(...);

    }

    ===========================================================

    Think:

    SecurityContextRepository

    = Storage Manager

    ===========================================================

    SecurityContextHolder

    = Runtime Memory

    ===========================================================

    SecurityContextRepository

    = Persistent Storage

    ===========================================================
                DEFAULT IMPLEMENTATION
    ===========================================================

    HttpSessionSecurityContextRepository

    ===========================================================

    Stores SecurityContext inside:

    HttpSession

    ===========================================================
                WHAT IS HTTP SESSION?
    ===========================================================

    Session = Server Side Storage

    ===========================================================

    Browser

         |

         v

    JSESSIONID

         |

         v

    Server Session

    ===========================================================

    Example

    Session Id

    ABC123XYZ

    ===========================================================

    Session Data

    SecurityContext

    Shopping Cart

    User Preferences

    ===========================================================
                LOGIN FLOW
    ===========================================================

    User Login

        |

        v

    Authentication Success

        |

        v

    SecurityContext Created

        |

        v

    SecurityContextHolder

        |

        v

    SecurityContextRepository

        |

        v

    HttpSession

        |

        v

    Saved

    ===========================================================
                WHAT GETS STORED?
    ===========================================================

    Session

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

    Spring stores complete
    authenticated user information.

    ===========================================================
                NEXT REQUEST FLOW
    ===========================================================

    Browser

        |

        v

    Sends JSESSIONID

        |

        v

    Tomcat

        |

        v

    Session Found

        |

        v

    SecurityContextRepository

        |

        v

    Load SecurityContext

        |

        v

    SecurityContextHolder

        |

        v

    Authentication Available

    ===========================================================
                SECURITYCONTEXTHOLDERFILTER
    ===========================================================

    Important Spring Security 6 Filter.

    Responsibilities:

    1. Load SecurityContext

    2. Store In SecurityContextHolder

    3. Continue Request

    4. Save Context

    5. Clear Context

    ===========================================================
                COMPLETE INTERNAL FLOW
    ===========================================================

    Request Start

         |

         v

    SecurityContextHolderFilter

         |

         v

    SecurityContextRepository

         |

         v

    Load SecurityContext

         |

         v

    SecurityContextHolder

         |

         v

    Controller Executes

         |

         v

    Save SecurityContext

         |

         v

    Clear ThreadLocal

    ===========================================================
                SESSION CREATION POLICY
    ===========================================================

    Extremely Important.

    ===========================================================

    Spring Security provides:

    SessionCreationPolicy

    ===========================================================

    ALWAYS

    IF_REQUIRED

    NEVER

    STATELESS

    ===========================================================
                POLICY 1
                ALWAYS
    ===========================================================

    Always create session.

    Even if not needed.

    Rarely used.

    ===========================================================
                POLICY 2
                IF_REQUIRED
    ===========================================================

    Default behavior.

    Create session only when needed.

    Most traditional applications.

    ===========================================================
                POLICY 3
                NEVER
    ===========================================================

    Don't create session.

    But use existing session if present.

    ===========================================================
                POLICY 4
                STATELESS
    ===========================================================

    Never create session.

    Never use session.

    Every request independently authenticated.

    ===========================================================

    Most important for JWT.

    ===========================================================
                    JWT FLOW
    ===========================================================

    Login

        |

        v

    JWT Generated

        |

        v

    Client Stores JWT

        |

        v

    Request

        |

        v

    Authorization Header

        |

        v

    JWT Filter

        |

        v

    SecurityContext Created

        |

        v

    Request Processed

        |

        v

    Context Destroyed

    ===========================================================

    No Session.

    No HttpSession.

    No JSESSIONID.

    ===========================================================
                STATEFUL VS STATELESS
    ===========================================================

    STATEFUL

    Spring remembers user.

    Uses Session.

    ===========================================================

    Login Once

    Access Many APIs

    ===========================================================

    Server stores state.

    ===========================================================

    Example

    Traditional Spring MVC

    ===========================================================

    STATELESS

    Server remembers nothing.

    Every request carries identity.

    ===========================================================

    JWT

    ===========================================================

    Request #1

    JWT

    ===========================================================

    Request #2

    JWT

    ===========================================================

    Request #3

    JWT

    ===========================================================

    Server stores nothing.

    ===========================================================
                SESSION FIXATION
    ===========================================================

    Important Security Topic.

    ===========================================================

    Attack

    Hacker forces known session id.

    User logs in.

    Hacker reuses session.

    ===========================================================

    Solution

    Spring Security changes session id
    after login.

    ===========================================================

    Session Fixation Protection.

    Enabled by default.

    ===========================================================
                CONCURRENT SESSION CONTROL
    ===========================================================

    Example:

    User allowed:

    Only 1 login.

    ===========================================================

    Login From Device A

    Success

    ===========================================================

    Login From Device B

    Old Session Expired

    ===========================================================

    Spring supports this.

    ===========================================================
                COMPLETE INTERNAL FLOW
    ===========================================================

    Browser

        |

        v

    Embedded Tomcat

        |

        v

    Thread

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

    SecurityContextRepository

        |

        v

    HttpSession

        |

        v

    SecurityContext

        |

        v

    Authentication

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

    Response

        |

        v

    Save SecurityContext

        |

        v

    Clear ThreadLocal

    ===========================================================
                STATELESS JWT FLOW
    ===========================================================

    Browser

        |

        v

    Authorization Header

        |

        v

    JWT Filter

        |

        v

    Validate JWT

        |

        v

    Create Authentication

        |

        v

    SecurityContextHolder

        |

        v

    Controller

        |

        v

    Response

        |

        v

    Clear Context

    ===========================================================

    No Session

    No Repository Save

    No JSESSIONID

    ===========================================================
                        THEORY
    ===========================================================

    SecurityContextHolder is temporary.

    It lives inside ThreadLocal.

    ===========================================================

    ThreadLocal dies after request.

    ===========================================================

    Therefore:

    Spring needs persistent storage.

    ===========================================================

    That storage mechanism is:

    SecurityContextRepository

    ===========================================================

    Default repository:

    HttpSessionSecurityContextRepository

    ===========================================================

    Session Based Security

    ------------------------------------------------

    Authentication

        ↓

    SecurityContext

        ↓

    HttpSession

        ↓

    Future Requests

    ===========================================================

    JWT Based Security

    ------------------------------------------------

    Authentication

        ↓

    SecurityContext

        ↓

    Current Request Only

    ===========================================================

    No Session.

    ===========================================================

    Therefore:

    Stateful Authentication

        Uses Session

    Stateless Authentication

        Uses Token

    ===========================================================

    SecurityContextRepository is the bridge
    between request-level security and
    cross-request persistence.

    ===========================================================
                MOST ASKED INTERVIEW QUESTIONS
    ===========================================================

    Q1. What is SecurityContextRepository?

    Stores and retrieves SecurityContext.

    ------------------------------------------------

    Q2. Default implementation?

    HttpSessionSecurityContextRepository.

    ------------------------------------------------

    Q3. What is stored in HttpSession?

    SecurityContext.

    ------------------------------------------------

    Q4. Why SecurityContextRepository needed?

    ThreadLocal dies after request.

    ------------------------------------------------

    Q5. What is SessionCreationPolicy?

    Controls session behavior.

    ------------------------------------------------

    Q6. Which policy used for JWT?

    STATELESS.

    ------------------------------------------------

    Q7. Difference between
        STATEFUL and STATELESS?

    Stateful

        Uses Session

    Stateless

        Uses Token

    ------------------------------------------------

    Q8. Does JWT use HttpSession?

    No.

    ------------------------------------------------

    Q9. What filter loads SecurityContext?

    SecurityContextHolderFilter.

    ------------------------------------------------

    Q10. Why Session Fixation protection?

    Prevent session hijacking attacks.

    ===========================================================
                    MEMORY TRICK
    ===========================================================

    Authentication

          ↓

    SecurityContext

          ↓

    SecurityContextRepository

          ↓

    HttpSession

          ↓

    Next Request

          ↓

    SecurityContextRepository

          ↓

    SecurityContextHolder

          ↓

    Controller

    ===========================================================
    */
}