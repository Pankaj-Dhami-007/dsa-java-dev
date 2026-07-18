package javaDeepDrive.springsecurity;

public class SecurityContextConcept {

    /*
    ===========================================================
                    SECURITY CONTEXT
    ===========================================================

    This is one of the MOST IMPORTANT
    Spring Security concepts.

    Most developers know:

    SecurityContextHolder

    But don't know:

    What exactly is SecurityContext?

    Why does it exist?

    Who creates it?

    Who stores Authentication inside it?

    Who clears it?

    How does it survive across requests?

    ===========================================================
                    BIG QUESTION
    ===========================================================

    User authenticated successfully.

    Great.

    But now:

    How will Controller know
    who the current user is?

    Example:

    @GetMapping("/profile")
    public String profile() {

    }

    How does Spring know:

    Current User = Pankaj

    Role = ADMIN

    Authorities = DELETE_USER

    ===========================================================

    Answer:

    SecurityContext

    ===========================================================
                    SIMPLE DEFINITION
    ===========================================================

    SecurityContext is an object that stores:

    Current Authentication Object

    ===========================================================

    Internally

    SecurityContext

          |

          v

    Authentication

    ===========================================================

    That's it.

    Its main job is:

    Store Authentication.

    ===========================================================
                    INTERFACE
    ===========================================================

    public interface SecurityContext {

        Authentication getAuthentication();

        void setAuthentication(
                Authentication authentication
        );
    }

    ===========================================================
                    IMPORTANT
    ===========================================================

    SecurityContext DOES NOT store:

    Username directly

    Password directly

    Authorities directly

    ===========================================================

    It stores:

    Authentication

    ===========================================================

    Authentication contains:

    Principal

    Credentials

    Authorities

    Authenticated Flag

    ===========================================================
                    OBJECT HIERARCHY
    ===========================================================

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
                    REAL OBJECT GRAPH
    ===========================================================

    SecurityContext

      |

      v

    UsernamePasswordAuthenticationToken

      |

      +---- Principal

      |

      +---- Authorities

      |

      +---- Authenticated=true

    ===========================================================
                COMPLETE LOGIN FLOW
    ===========================================================

    Login Request

         |

         v

    UsernamePasswordAuthenticationFilter

         |

         v

    AuthenticationManager

         |

         v

    DaoAuthenticationProvider

         |

         v

    Authentication Success

         |

         v

    Create Authentication Object

         |

         v

    Store Into SecurityContext

    ===========================================================
                WHO CREATES SECURITYCONTEXT?
    ===========================================================

    Spring Security.

    Default Implementation:

    SecurityContextImpl

    ===========================================================

    Example:

    SecurityContext context =
            new SecurityContextImpl();

    ===========================================================

    Normally we don't create it manually.

    Spring creates it.

    ===========================================================
                STEP-BY-STEP INTERNAL FLOW
    ===========================================================

    Request Arrives

        |

        v

    SecurityContext Created

        |

        v

    Authentication Success

        |

        v

    Authentication Stored

        |

        v

    Controller Executes

        |

        v

    Request Ends

        |

        v

    Context Cleared

    ===========================================================
                WHERE IS AUTHENTICATION STORED?
    ===========================================================

    SecurityContext

        |

        v

    Authentication

    ===========================================================

    Example

    Authentication auth =

        new UsernamePasswordAuthenticationToken(
                user,
                null,
                authorities
        );

    context.setAuthentication(auth);

    ===========================================================
                ACCESSING CURRENT USER
    ===========================================================

    SecurityContext context =

        SecurityContextHolder.getContext();

    Authentication auth =

        context.getAuthentication();

    ===========================================================

    Authentication retrieved.

    ===========================================================
                CONTROLLER FLOW
    ===========================================================

    Request

      |

      v

    Authentication Filter

      |

      v

    SecurityContext

      |

      v

    Authentication Stored

      |

      v

    Controller

      |

      v

    SecurityContext Read

      |

      v

    Current User Available

    ===========================================================
                WHY SECURITYCONTEXT EXISTS?
    ===========================================================

    Imagine without SecurityContext.

    Controller would need:

    username

    role

    authorities

    passed manually everywhere.

    Example:

    service.getUser(
        username,
        role,
        permissions
    );

    Bad design.

    ===========================================================

    SecurityContext acts as central storage.

    ===========================================================
                AUTHORIZATION FLOW
    ===========================================================

    AuthorizationFilter

          |

          v

    SecurityContext

          |

          v

    Authentication

          |

          v

    Authorities

          |

          v

    Access Decision

    ===========================================================
                SECURITYCONTEXT IN JWT
    ===========================================================

    JWT Authentication

          |

          v

    JWT Filter

          |

          v

    Validate Token

          |

          v

    Create Authentication

          |

          v

    SecurityContext

          |

          v

    Continue Request

    ===========================================================

    Even JWT uses SecurityContext.

    ===========================================================
                VERY IMPORTANT QUESTION
    ===========================================================

    Is SecurityContext same as
    SecurityContextHolder?

    ===========================================================

    NO

    ===========================================================

    SecurityContext

        Stores Authentication

    ===========================================================

    SecurityContextHolder

        Stores SecurityContext

    ===========================================================

    Relationship:

    SecurityContextHolder

          |

          v

    SecurityContext

          |

          v

    Authentication

    ===========================================================
                REAL WORLD EXAMPLE
    ===========================================================

    Office Building

    ===========================================================

    Employee Card

        Authentication

    ===========================================================

    Employee Locker

        SecurityContext

    ===========================================================

    Building Storage Room

        SecurityContextHolder

    ===========================================================

    Storage Room

        contains

    Locker

        contains

    Employee Card

    ===========================================================
                REQUEST LIFECYCLE
    ===========================================================

    Request Starts

        |

        v

    Context Created

        |

        v

    Authentication Stored

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

    Response Generated

        |

        v

    Context Cleared

    ===========================================================
                INTERNAL FILTER FLOW
    ===========================================================

    Browser

        |

        v

    Tomcat

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

    SecurityContextHolderFilter

        |

        v

    SecurityContext Created

        |

        v

    Authentication Filter

        |

        v

    Authentication Stored

        |

        v

    AuthorizationFilter

        |

        v

    DispatcherServlet

        |

        v

    Controller

    ===========================================================
                SESSION BASED SECURITY
    ===========================================================

    Login

       |

       v

    Authentication Success

       |

       v

    SecurityContext

       |

       v

    Stored In Session

       |

       v

    Future Requests Reuse Context

    ===========================================================

    Stateful Authentication

    ===========================================================
                JWT BASED SECURITY
    ===========================================================

    Request

       |

       v

    JWT Filter

       |

       v

    Create SecurityContext

       |

       v

    Process Request

       |

       v

    Destroy Context

    ===========================================================

    Stateless Authentication

    ===========================================================
                COMMON INTERVIEW QUESTIONS
    ===========================================================

    Q1. What is SecurityContext?

    Stores Authentication object.

    ------------------------------------------------

    Q2. What does SecurityContext contain?

    Authentication.

    ------------------------------------------------

    Q3. What is default implementation?

    SecurityContextImpl.

    ------------------------------------------------

    Q4. Does SecurityContext store user?

    Indirectly.

    Through Authentication.

    ------------------------------------------------

    Q5. Does SecurityContext store authorities?

    Indirectly.

    Through Authentication.

    ------------------------------------------------

    Q6. Who creates SecurityContext?

    Spring Security.

    ------------------------------------------------

    Q7. Why SecurityContext needed?

    Central place for current user security data.

    ------------------------------------------------

    Q8. Is SecurityContext same as
        SecurityContextHolder?

    No.

    SecurityContext stores Authentication.

    SecurityContextHolder stores SecurityContext.

    ------------------------------------------------

    Q9. Does JWT use SecurityContext?

    Yes.

    ------------------------------------------------

    Q10. Where does AuthorizationFilter
         read permissions from?

    Authentication inside SecurityContext.

    ===========================================================
                    MEMORY TRICK
    ===========================================================

    UserDetails

         ↓

    Authentication

         ↓

    SecurityContext

         ↓

    SecurityContextHolder

         ↓

    AuthorizationFilter

         ↓

    Controller

    ===========================================================

    Remember:

    Authentication is the KING.

    SecurityContext stores the KING.

    SecurityContextHolder stores the CASTLE.

    ===========================================================
    */
}