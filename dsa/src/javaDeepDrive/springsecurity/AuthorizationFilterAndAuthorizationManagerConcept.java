package javaDeepDrive.springsecurity;

public class AuthorizationFilterAndAuthorizationManagerConcept {

    /*
    ===========================================================
            AUTHORIZATION FILTER
                        +
            AUTHORIZATION MANAGER
    ===========================================================

    We have already learned:

    Authentication

    SecurityContextHolder

    JWT Filter

    AuthenticationManager

    AuthenticationProvider

    ===========================================================

    Now comes the final decision maker.

    ===========================================================

    Question:

    User authenticated successfully.

    JWT valid.

    Authentication object available.

    ===========================================================

    But:

    Can user access endpoint?

    ===========================================================

    Who decides this?

    ===========================================================

    AuthorizationFilter

                +

    AuthorizationManager

    ===========================================================



    ###########################################################
                    BIG PICTURE
    ###########################################################

    Request

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

    AuthorizationFilter

        |

        v

    AuthorizationManager

        |

        v

    Access Granted ?

        |

    +---+---+

    |       |

    v       v

    YES     NO

    |        |

    v        v

    Controller 403

    ===========================================================



    ###########################################################
                AUTHENTICATION VS AUTHORIZATION
    ###########################################################

    Authentication

    ------------------------------------------------

    Who are you?

    ------------------------------------------------

    Identity Verification

    ------------------------------------------------

    Username

    Password

    JWT

    ------------------------------------------------

    Output

    Authentication Object

    ===========================================================

    Authorization

    ------------------------------------------------

    What can you do?

    ------------------------------------------------

    Permission Verification

    ------------------------------------------------

    ROLE_ADMIN

    ROLE_USER

    DELETE_USER

    CREATE_USER

    ------------------------------------------------

    Output

    Access Decision

    ===========================================================



    ###########################################################
                AUTHORIZATION FILTER
    ###########################################################

    Very Important Spring Security Filter.

    ===========================================================

    Responsibility:

    Perform authorization checks.

    ===========================================================

    Before Spring Security 6

    FilterSecurityInterceptor

    ===========================================================

    Spring Security 6+

    AuthorizationFilter

    ===========================================================

    This is modern architecture.

    ===========================================================



    ###########################################################
                AUTHORIZATIONFILTER FLOW
    ###########################################################

    Request

        |

        v

    Authentication Exists?

        |

        v

    AuthorizationManager

        |

        v

    Access Granted?

        |

    +---+---+

    |       |

    v       v

    YES     NO

    |        |

    v        v

    Continue 403

    ===========================================================



    ###########################################################
                AUTHORIZATION MANAGER
    ###########################################################

    Core authorization engine.

    ===========================================================

    Interface

    ===========================================================

    public interface AuthorizationManager<T> {

        AuthorizationDecision check(

                Supplier<Authentication> authentication,

                T object

        );

    }

    ===========================================================

    Responsibility:

    Make access decisions.

    ===========================================================

    AuthenticationManager

        Authentication

    ===========================================================

    AuthorizationManager

        Authorization

    ===========================================================

    Don't confuse them.

    ===========================================================



    ###########################################################
                COMPLETE REQUEST FLOW
    ###########################################################

    Browser

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

    AuthorizationFilter

        |

        v

    AuthorizationManager

        |

        v

    AuthorizationDecision

        |

        v

    Controller

    ===========================================================



    ###########################################################
            REQUESTMATCHERDELEGATING
            AUTHORIZATIONMANAGER
    ###########################################################

    Huge Interview Topic.

    ===========================================================

    Default implementation used by Spring.

    ===========================================================

    Long Name.

    Important Class.

    ===========================================================

    Responsibility:

    Match URL

            +

    Apply Authorization Rule

    ===========================================================

    Example

    /admin/**

        -> ROLE_ADMIN

    ===========================================================

    /user/**

        -> ROLE_USER

    ===========================================================

    /public/**

        -> permitAll

    ===========================================================

    RequestMatcherDelegatingAuthorizationManager

    decides which rule applies.

    ===========================================================



    ###########################################################
                    HASROLE()
    ###########################################################

    Most used method.

    ===========================================================

    Example

    hasRole("ADMIN")

    ===========================================================

    Interview Trap.

    ===========================================================

    Spring internally converts:

    ADMIN

        |

        v

    ROLE_ADMIN

    ===========================================================

    Then compares authorities.

    ===========================================================

    User Authorities

    ROLE_ADMIN

    ROLE_USER

    ===========================================================

    Match Found.

    ===========================================================

    Access Granted.

    ===========================================================



    ###########################################################
                INTERNAL FLOW
    ###########################################################

    hasRole("ADMIN")

            |

            v

    Convert To

    ROLE_ADMIN

            |

            v

    Authentication.getAuthorities()

            |

            v

    Compare

            |

            v

    Decision

    ===========================================================



    ###########################################################
                    HASAUTHORITY()
    ###########################################################

    Example

    hasAuthority("DELETE_USER")

    ===========================================================

    No conversion.

    Direct comparison.

    ===========================================================

    Compare:

    DELETE_USER

            with

    DELETE_USER

    ===========================================================

    Access Granted.

    ===========================================================



    ###########################################################
                HASROLE VS HASAUTHORITY
    ###########################################################

    hasRole("ADMIN")

            ↓

    ROLE_ADMIN

    ===========================================================

    hasAuthority("ADMIN")

            ↓

    ADMIN

    ===========================================================

    Completely different.

    ===========================================================

    Common Interview Question.

    ===========================================================

    User Authority

    ROLE_ADMIN

    ===========================================================

    hasRole("ADMIN")

        PASS

    ===========================================================

    hasAuthority("ADMIN")

        FAIL

    ===========================================================



    ###########################################################
                    PERMITALL()
    ###########################################################

    Public endpoint.

    ===========================================================

    Example

    /login

    /register

    /swagger-ui

    ===========================================================

    No authentication required.

    ===========================================================

    Authorization always succeeds.

    ===========================================================



    ###########################################################
                    DENYALL()
    ###########################################################

    Nobody allowed.

    ===========================================================

    Access always denied.

    ===========================================================

    Useful during maintenance.

    ===========================================================



    ###########################################################
                AUTHENTICATED()
    ###########################################################

    User must be authenticated.

    ===========================================================

    Any authenticated user allowed.

    ===========================================================

    Roles not checked.

    ===========================================================

    Example

    /profile

    ===========================================================

    Authentication exists?

    YES

    ===========================================================

    Access Granted.

    ===========================================================



    ###########################################################
                ANONYMOUS()
    ###########################################################

    Only anonymous users allowed.

    ===========================================================

    Example

    Login Page

    Registration Page

    ===========================================================

    Logged in users blocked.

    ===========================================================



    ###########################################################
                    ACCESS()
    ###########################################################

    Advanced authorization.

    ===========================================================

    Custom logic.

    ===========================================================

    Example

    User owns resource.

    ===========================================================

    User department matches.

    ===========================================================

    Dynamic checks.

    ===========================================================



    ###########################################################
                REAL CONFIGURATION
    ###########################################################

    http

      .authorizeHttpRequests(auth ->

            auth

            .requestMatchers("/login/**")
            .permitAll()

            .requestMatchers("/admin/**")
            .hasRole("ADMIN")

            .requestMatchers("/user/**")
            .hasAnyRole(
                    "USER",
                    "ADMIN"
            )

            .anyRequest()
            .authenticated()

      );

    ===========================================================



    ###########################################################
            COMPLETE AUTHORIZATION FLOW
    ###########################################################

    Request

        |

        v

    SecurityContextHolder

        |

        v

    Authentication

        |

        v

    AuthorizationFilter

        |

        v

    RequestMatcher

        |

        v

    AuthorizationManager

        |

        v

    Required Authority

        |

        v

    Compare User Authorities

        |

    +---+---+

    |       |

    v       v

    YES     NO

    |        |

    v        v

    Controller

            AccessDeniedException

                    |

                    v

              AccessDeniedHandler

                    |

                    v

                    403

    ===========================================================



    ###########################################################
                INTERNAL OBJECT FLOW
    ###########################################################

    SecurityContextHolder

            |

            v

    SecurityContext

            |

            v

    Authentication

            |

            v

    Collection<GrantedAuthority>

            |

            v

    ROLE_ADMIN

    ROLE_USER

    DELETE_USER

    CREATE_USER

            |

            v

    AuthorizationManager

            |

            v

    AuthorizationDecision

    ===========================================================



    ###########################################################
                        THEORY
    ###########################################################

    Spring Security separates
    Authentication from Authorization.

    ===========================================================

    Authentication determines:

    WHO ARE YOU?

    ===========================================================

    Authorization determines:

    WHAT CAN YOU DO?

    ===========================================================

    Authentication produces:

    Authentication Object

    ===========================================================

    Authorization consumes:

    Authentication Object

    ===========================================================

    AuthorizationFilter acts as:

    Authorization Coordinator

    ===========================================================

    AuthorizationManager acts as:

    Decision Engine

    ===========================================================

    RequestMatcherDelegatingAuthorizationManager
    acts as:

    Rule Router

    ===========================================================

    URL

        ↓

    Rule Selection

        ↓

    Authority Comparison

        ↓

    Access Decision

    ===========================================================

    Therefore:

    JwtAuthenticationFilter

        Creates Authentication

    ===========================================================

    AuthorizationFilter

        Uses Authentication

    ===========================================================

    AuthorizationManager

        Makes Decision

    ===========================================================



    ###########################################################
                MOST ASKED INTERVIEW QUESTIONS
    ###########################################################

    Q1. What is AuthorizationFilter?

    Performs authorization checks.

    ------------------------------------------------

    Q2. What is AuthorizationManager?

    Makes access decisions.

    ------------------------------------------------

    Q3. Difference between
        AuthenticationManager and
        AuthorizationManager?

    AuthenticationManager

        Identity Verification

    AuthorizationManager

        Permission Verification

    ------------------------------------------------

    Q4. What replaced
        FilterSecurityInterceptor?

    AuthorizationFilter.

    ------------------------------------------------

    Q5. What does hasRole() do?

    Adds ROLE_ prefix.

    ------------------------------------------------

    Q6. What does hasAuthority() do?

    Direct comparison.

    ------------------------------------------------

    Q7. permitAll() meaning?

    Public access.

    ------------------------------------------------

    Q8. authenticated() meaning?

    Any authenticated user.

    ------------------------------------------------

    Q9. Which exception thrown when
        authorization fails?

    AccessDeniedException.

    ------------------------------------------------

    Q10. Which handler processes it?

    AccessDeniedHandler.

    ===========================================================



    ###########################################################
                    MEMORY TRICK
    ###########################################################

    JWT Filter

        ↓

    Authentication

        ↓

    SecurityContextHolder

        ↓

    AuthorizationFilter

        ↓

    AuthorizationManager

        ↓

    Authority Comparison

        ↓

    Access Decision

        ↓

    Controller

    ===========================================================
    */
}