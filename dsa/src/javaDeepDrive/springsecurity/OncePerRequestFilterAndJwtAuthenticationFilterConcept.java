package javaDeepDrive.springsecurity;

public class OncePerRequestFilterAndJwtAuthenticationFilterConcept {

    /*
    ===========================================================
            ONCEPERREQUESTFILTER
                        +
            JWTAUTHENTICATIONFILTER
    ===========================================================

    This is one of the MOST IMPORTANT topics
    for modern Spring Boot applications.

    Almost every production application using JWT
    contains:

    JwtAuthenticationFilter

    which extends

    OncePerRequestFilter

    ===========================================================
                    BIG PICTURE
    ===========================================================

    Browser

       |

       v

    Authorization Header

    Bearer eyJhbGciOi...

       |

       v

    JwtAuthenticationFilter

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

    AuthorizationFilter

       |

       v

    Controller

    ===========================================================
                WHY JWT FILTER EXISTS?
    ===========================================================

    Session Based Login

    Spring loads Authentication
    from HttpSession.

    ===========================================================

    JWT Login

    No Session.

    No HttpSession.

    No SecurityContextRepository.

    ===========================================================

    Therefore every request must:

    1. Read JWT

    2. Validate JWT

    3. Create Authentication

    4. Store in SecurityContext

    ===========================================================

    Who does this?

    JwtAuthenticationFilter

    ===========================================================
                COMPLETE JWT REQUEST FLOW
    ===========================================================

    Browser

      |

      v

    GET /users

      |

      v

    Authorization Header

    Bearer token

      |

      v

    Embedded Tomcat

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

    SecurityFilterChain

      |

      v

    JwtAuthenticationFilter

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
                SERVLET FILTER
    ===========================================================

    JWT Filter is actually a Servlet Filter.

    ===========================================================

    Servlet Filter Lifecycle

    ===========================================================

    Request

        |

        v

    Filter

        |

        v

    Controller

        |

        v

    Response

    ===========================================================
                GENERIC FILTER PROBLEM
    ===========================================================

    If we directly implement:

    Filter

    or

    GenericFilterBean

    ===========================================================

    Same request may trigger filter
    multiple times.

    ===========================================================

    FORWARD

    INCLUDE

    ERROR DISPATCH

    ASYNC DISPATCH

    ===========================================================

    Can cause duplicate execution.

    ===========================================================
                SOLUTION
    ===========================================================

    OncePerRequestFilter

    ===========================================================

    Guarantees:

    Filter executes only once
    per request.

    ===========================================================
                CLASS HIERARCHY
    ===========================================================

    Filter

       |

       v

    GenericFilterBean

       |

       v

    OncePerRequestFilter

       |

       v

    JwtAuthenticationFilter

    ===========================================================
                WHY SPRING CREATED IT?
    ===========================================================

    Security Filters should not run
    multiple times accidentally.

    ===========================================================

    JWT Validation

    Database Calls

    User Lookup

    Signature Verification

    ===========================================================

    Expensive Operations.

    ===========================================================

    Therefore:

    OncePerRequestFilter

    ===========================================================
                INTERNAL IDEA
    ===========================================================

    Request Attribute

    used internally.

    ===========================================================

    First Execution

       |

       v

    Mark Request

    FILTER_ALREADY_EXECUTED

    ===========================================================

    Second Execution Attempt

       |

       v

    Skip Filter

    ===========================================================

    Thus:

    Once Per Request

    ===========================================================
                MAIN METHOD
    ===========================================================

    protected void doFilterInternal(

        HttpServletRequest request,

        HttpServletResponse response,

        FilterChain filterChain

    )

    ===========================================================

    This is where JWT logic lives.

    ===========================================================
                JWT FILTER STRUCTURE
    ===========================================================

    public class JwtAuthenticationFilter
            extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(
            ...
        ) {

        }
    }

    ===========================================================
                STEP 1
                REQUEST ARRIVES
    ===========================================================

    GET /users

    ===========================================================

    Header

    Authorization:

    Bearer eyJhbGciOiJIUzI1Ni...

    ===========================================================
                STEP 2
                EXTRACT HEADER
    ===========================================================

    String authHeader =

        request.getHeader(
            "Authorization"
        );

    ===========================================================
                STEP 3
                VALIDATE FORMAT
    ===========================================================

    if(authHeader == null)

    skip

    ===========================================================

    if(!authHeader.startsWith("Bearer "))

    skip

    ===========================================================
                STEP 4
                EXTRACT TOKEN
    ===========================================================

    Bearer eyJhbGciOi...

            |

            v

    eyJhbGciOi...

    ===========================================================
                STEP 5
                VALIDATE TOKEN
    ===========================================================

    jwtService.validateToken(token)

    ===========================================================

    Validation Includes

    ===========================================================

    Signature Check

    Expiration Check

    Issuer Check

    Subject Check

    ===========================================================
                STEP 6
                EXTRACT USERNAME
    ===========================================================

    Token

        |

        v

    Username

    ===========================================================

    Example

    pankaj

    ===========================================================
                STEP 7
                LOAD USER
    ===========================================================

    UserDetailsService

        |

        v

    UserDetails

    ===========================================================
                STEP 8
                CREATE AUTHENTICATION
    ===========================================================

    Authentication auth =

        new
        UsernamePasswordAuthenticationToken(

            userDetails,

            null,

            userDetails.getAuthorities()

        );

    ===========================================================

    Notice:

    authenticated=true

    ===========================================================
                STEP 9
                STORE IN CONTEXT
    ===========================================================

    SecurityContextHolder

        .getContext()

        .setAuthentication(auth);

    ===========================================================

    Current User Available.

    ===========================================================
                STEP 10
                CONTINUE FILTER CHAIN
    ===========================================================

    filterChain.doFilter(
        request,
        response
    );

    ===========================================================

    Very Important.

    ===========================================================

    If forgotten:

    Request stops.

    Controller never executes.

    ===========================================================
                COMPLETE JWT FLOW
    ===========================================================

    JWT Request

         |

         v

    JwtAuthenticationFilter

         |

         v

    Extract Token

         |

         v

    Validate Token

         |

         v

    Extract Username

         |

         v

    UserDetailsService

         |

         v

    UserDetails

         |

         v

    Authentication

         |

         v

    SecurityContextHolder

         |

         v

    AuthorizationFilter

         |

         v

    Controller

    ===========================================================
                WHY BEFORE
                USERNAMEPASSWORDAUTHENTICATIONFILTER?
    ===========================================================

    Configuration

    ===========================================================

    http.addFilterBefore(

        jwtAuthenticationFilter,

        UsernamePasswordAuthenticationFilter.class

    );

    ===========================================================

    Important Interview Question.

    ===========================================================

    JWT user should already be authenticated

    before authorization starts.

    ===========================================================

    Flow

    JWT Filter

       |

       v

    Authentication Available

       |

       v

    Authorization Filter

       |

       v

    Access Decision

    ===========================================================
                FILTER ORDER
    ===========================================================

    SecurityContextHolderFilter

            |

            v

    LogoutFilter

            |

            v

    JwtAuthenticationFilter

            |

            v

    UsernamePasswordAuthenticationFilter

            |

            v

    AuthorizationFilter

            |

            v

    Controller

    ===========================================================
                WHY NOT AFTER?
    ===========================================================

    Suppose

    AuthorizationFilter executes first.

    ===========================================================

    Authentication missing.

    ===========================================================

    Authorization fails.

    ===========================================================

    401 Unauthorized

    ===========================================================

    Therefore:

    JWT Filter must execute first.

    ===========================================================
                EXCEPTION FLOW
    ===========================================================

    Invalid JWT

       |

       v

    Authentication NOT Created

       |

       v

    AuthorizationFilter

       |

       v

    Access Denied

       |

       v

    401 Response

    ===========================================================
                THREAD FLOW
    ===========================================================

    Tomcat Thread

        |

        v

    JwtAuthenticationFilter

        |

        v

    Authentication

        |

        v

    SecurityContextHolder

        |

        v

    ThreadLocal

        |

        v

    AuthorizationFilter

        |

        v

    Controller

    ===========================================================
                COMPLETE INTERNAL FLOW
    ===========================================================

    Browser

       |

       v

    Authorization Header

       |

       v

    Embedded Tomcat

       |

       v

    Tomcat Thread

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

    SecurityFilterChain

       |

       v

    SecurityContextHolderFilter

       |

       v

    JwtAuthenticationFilter

       |

       v

    UserDetailsService

       |

       v

    UserDetails

       |

       v

    UsernamePasswordAuthenticationToken

       |

       v

    SecurityContext

       |

       v

    SecurityContextHolder

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

    Clear Context

    ===========================================================
                        THEORY
    ===========================================================

    OncePerRequestFilter is a specialized
    Servlet Filter.

    ===========================================================

    Its purpose is:

    Prevent duplicate execution
    during one request lifecycle.

    ===========================================================

    JWTAuthenticationFilter extends it
    because JWT validation should happen
    exactly once per request.

    ===========================================================

    JWTAuthenticationFilter acts as:

    Authentication Creator

    ===========================================================

    Responsibilities:

    Read Token

    Validate Token

    Load User

    Create Authentication

    Populate SecurityContext

    Continue Request

    ===========================================================

    JWT Filter does NOT perform authorization.

    ===========================================================

    AuthorizationFilter performs authorization.

    ===========================================================

    JWT Filter only ensures:

    Current user is authenticated.

    ===========================================================

    Relationship

    JWT Filter

            ↓

    Authentication

            ↓

    SecurityContextHolder

            ↓

    AuthorizationFilter

            ↓

    Controller

    ===========================================================
                MOST ASKED INTERVIEW QUESTIONS
    ===========================================================

    Q1. Why extend OncePerRequestFilter?

    Execute filter only once per request.

    ------------------------------------------------

    Q2. Difference between Filter and
        OncePerRequestFilter?

    OncePerRequestFilter prevents
    duplicate execution.

    ------------------------------------------------

    Q3. Why JWT Filter needed?

    JWT applications are stateless.

    Authentication must be recreated
    on every request.

    ------------------------------------------------

    Q4. What does JWT Filter do?

    Extract token.

    Validate token.

    Create Authentication.

    ------------------------------------------------

    Q5. Where Authentication stored?

    SecurityContextHolder.

    ------------------------------------------------

    Q6. Why addFilterBefore()?

    JWT authentication must happen
    before authorization.

    ------------------------------------------------

    Q7. Which Authentication object
        usually created?

    UsernamePasswordAuthenticationToken.

    ------------------------------------------------

    Q8. What happens if filterChain.doFilter()
        not called?

    Request stops.

    Controller never executes.

    ------------------------------------------------

    Q9. Does JWT Filter authorize user?

    No.

    Only authenticates user.

    ------------------------------------------------

    Q10. Which filter checks permissions?

    AuthorizationFilter.

    ===========================================================
                    MEMORY TRICK
    ===========================================================

    Request

          ↓

    OncePerRequestFilter

          ↓

    JwtAuthenticationFilter

          ↓

    JWT Validation

          ↓

    UserDetailsService

          ↓

    Authentication

          ↓

    SecurityContextHolder

          ↓

    AuthorizationFilter

          ↓

    Controller

    ===========================================================
    */
}