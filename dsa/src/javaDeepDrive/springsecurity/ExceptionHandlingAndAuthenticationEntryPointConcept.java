package javaDeepDrive.springsecurity;

/*

Request
   ↓
JwtAuthenticationFilter
   ↓
Authentication Created
   ↓
AuthorizationFilter
   ↓
Permission Check

        ┌───────────────────────┐
        │ Authentication Error  │
        └───────────────────────┘
                    ↓
      AuthenticationException
                    ↓
      ExceptionTranslationFilter
                    ↓
      AuthenticationEntryPoint
                    ↓
                401

------------------------------------------------

        ┌───────────────────────┐
        │ Authorization Error   │
        └───────────────────────┘
                    ↓
        AccessDeniedException
                    ↓
      ExceptionTranslationFilter
                    ↓
        AccessDeniedHandler
                    ↓
                403
 */

public class ExceptionHandlingAndAuthenticationEntryPointConcept {

    /*
    ===========================================================
            EXCEPTION HANDLING
                        +
            AUTHENTICATION ENTRY POINT
    ===========================================================

    This topic is extremely important.

    Most developers know:

    AuthenticationEntryPoint

    AccessDeniedHandler

    But don't understand:

    Who calls them?

    When are they called?

    Why 401 sometimes?

    Why 403 sometimes?

    What is ExceptionTranslationFilter?

    ===========================================================
                    BIG QUESTION
    ===========================================================

    Suppose request comes:

    GET /admin/users

    ===========================================================

    Case 1

    No JWT

    ===========================================================

    Case 2

    Invalid JWT

    ===========================================================

    Case 3

    Valid JWT

    But no ADMIN role

    ===========================================================

    How does Spring decide:

    401 ?

    403 ?

    ===========================================================

    Answer:

    ExceptionTranslationFilter

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

    AuthorizationFilter

        |

        v

    AuthenticationException ?

        |

        +------ YES

        |         |

        |         v

        |    AuthenticationEntryPoint

        |

        +------ NO

        |
        v

    AccessDeniedException ?

        |

        +------ YES

        |         |

        |         v

        |    AccessDeniedHandler

        |

        +------ NO

        |
        v

    Controller

    ===========================================================



    ###########################################################
                EXCEPTIONTRANSLATIONFILTER
    ###########################################################

    One of the most important filters.

    Most developers never use it directly.

    But Spring Security depends on it.

    ===========================================================

    Responsibility:

    Convert Security Exceptions

    into

    HTTP Responses

    ===========================================================

    Think:

    Security Exception Translator

    ===========================================================

    Internal Idea

    try {

        filterChain.doFilter()

    }

    catch(AuthenticationException e) {

        AuthenticationEntryPoint

    }

    catch(AccessDeniedException e) {

        AccessDeniedHandler

    }

    ===========================================================

    This is simplified internal behavior.

    ===========================================================



    ###########################################################
                AUTHENTICATIONEXCEPTION
    ###########################################################

    Means:

    User is NOT authenticated.

    ===========================================================

    Examples

    Invalid JWT

    Expired JWT

    Missing JWT

    Invalid Username

    Wrong Password

    Disabled Account

    Locked Account

    ===========================================================

    Authentication failed.

    ===========================================================

    Spring throws:

    AuthenticationException

    ===========================================================



    ###########################################################
                AUTHENTICATION ENTRY POINT
    ###########################################################

    Very Important Interface.

    ===========================================================

    public interface AuthenticationEntryPoint {

        void commence(
            HttpServletRequest request,

            HttpServletResponse response,

            AuthenticationException ex
        );
    }

    ===========================================================

    Responsibility:

    Start authentication failure response.

    ===========================================================

    Usually returns:

    401 Unauthorized

    ===========================================================

    Example

    No JWT

        |

        v

    AuthenticationException

        |

        v

    AuthenticationEntryPoint

        |

        v

    401

    ===========================================================



    ###########################################################
                    401 UNAUTHORIZED
    ###########################################################

    Means:

    Identity not verified.

    ===========================================================

    User is unknown.

    ===========================================================

    Examples

    Missing JWT

    Invalid JWT

    Expired JWT

    Wrong Credentials

    ===========================================================

    Response

    401 Unauthorized

    ===========================================================



    ###########################################################
                ACCESSDENIEDEXCEPTION
    ###########################################################

    Completely different.

    ===========================================================

    User IS authenticated.

    ===========================================================

    But user lacks permission.

    ===========================================================

    Example

    Endpoint:

    /admin/users

    ===========================================================

    Required:

    ROLE_ADMIN

    ===========================================================

    Current User:

    ROLE_USER

    ===========================================================

    User authenticated.

    Permission missing.

    ===========================================================

    Spring throws:

    AccessDeniedException

    ===========================================================



    ###########################################################
                ACCESSDENIEDHANDLER
    ###########################################################

    Interface

    ===========================================================

    public interface AccessDeniedHandler {

        void handle(
            HttpServletRequest request,

            HttpServletResponse response,

            AccessDeniedException ex
        );

    }

    ===========================================================

    Responsibility:

    Handle authorization failures.

    ===========================================================

    Usually returns:

    403 Forbidden

    ===========================================================



    ###########################################################
                    403 FORBIDDEN
    ###########################################################

    Means:

    I know who you are.

    ===========================================================

    But

    you are not allowed.

    ===========================================================

    Example

    JWT Valid

    Authentication Success

    ROLE_USER

    ===========================================================

    Endpoint

    requires

    ROLE_ADMIN

    ===========================================================

    Response

    403 Forbidden

    ===========================================================



    ###########################################################
                401 VS 403
    ###########################################################

    Extremely Important Interview Question.

    ===========================================================

    401

    Authentication Problem

    ===========================================================

    User not authenticated

    ===========================================================

    Missing JWT

    Invalid JWT

    Expired JWT

    Wrong Password

    ===========================================================

    403

    Authorization Problem

    ===========================================================

    User authenticated

    Permission missing

    ===========================================================

    ROLE_USER accessing ROLE_ADMIN API

    ===========================================================



    ###########################################################
                COMPLETE REQUEST FLOW
    ###########################################################

    Request

        |

        v

    JwtAuthenticationFilter

        |

        v

    Authentication Created

        |

        v

    SecurityContextHolder

        |

        v

    AuthorizationFilter

        |

        v

    Permission Check

        |

        +------ PASS

        |         |

        |         v

        |     Controller

        |

        +------ FAIL

                  |

                  v

          AccessDeniedException

                  |

                  v

          ExceptionTranslationFilter

                  |

                  v

          AccessDeniedHandler

                  |

                  v

          403

    ===========================================================



    ###########################################################
                INVALID JWT FLOW
    ###########################################################

    Request

       |

       v

    JwtAuthenticationFilter

       |

       v

    JWT Invalid

       |

       v

    AuthenticationException

       |

       v

    ExceptionTranslationFilter

       |

       v

    AuthenticationEntryPoint

       |

       v

    401 Unauthorized

    ===========================================================



    ###########################################################
            CUSTOM AUTHENTICATION ENTRY POINT
    ###########################################################

    Production applications usually create:

    CustomAuthenticationEntryPoint

    ===========================================================

    Why?

    Default response ugly.

    ===========================================================

    API applications return JSON.

    ===========================================================

    Example Response

    {

        "timestamp":"2026-01-01",

        "status":401,

        "error":"Unauthorized",

        "message":"Invalid JWT"

    }

    ===========================================================



    ###########################################################
                CUSTOM ACCESSDENIEDHANDLER
    ###########################################################

    Example Response

    {

        "timestamp":"2026-01-01",

        "status":403,

        "error":"Forbidden",

        "message":"Access Denied"

    }

    ===========================================================

    Better API experience.

    ===========================================================



    ###########################################################
                SPRING SECURITY CONFIG
    ###########################################################

    http

        .exceptionHandling(

            exception ->

                exception

                    .authenticationEntryPoint(

                        customEntryPoint

                    )

                    .accessDeniedHandler(

                        customAccessDeniedHandler

                    )

        );

    ===========================================================

    Now Spring knows:

    Authentication failure

            ↓

    CustomAuthenticationEntryPoint

    ===========================================================

    Authorization failure

            ↓

    CustomAccessDeniedHandler

    ===========================================================



    ###########################################################
                COMPLETE INTERNAL FLOW
    ###########################################################

    Browser

        |

        v

    Tomcat

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

    JwtAuthenticationFilter

        |

        v

    AuthorizationFilter

        |

        v

    ExceptionTranslationFilter

        |

        +-------------------+

        |                   |

        v                   v

    Authentication     AccessDenied
      Exception          Exception

        |                   |

        v                   v

    Authentication      AccessDenied
      EntryPoint          Handler

        |                   |

        v                   v

       401                 403

    ===========================================================



    ###########################################################
                        THEORY
    ###########################################################

    Spring Security separates:

    Authentication Failures

            from

    Authorization Failures

    ===========================================================

    Authentication asks:

    Who are you?

    ===========================================================

    Authorization asks:

    What are you allowed to do?

    ===========================================================

    Therefore:

    Authentication Failure

            ↓

    AuthenticationException

            ↓

    AuthenticationEntryPoint

            ↓

    401

    ===========================================================

    Authorization Failure

            ↓

    AccessDeniedException

            ↓

    AccessDeniedHandler

            ↓

    403

    ===========================================================

    ExceptionTranslationFilter acts as
    central exception coordinator.

    ===========================================================

    It converts Java security exceptions
    into HTTP responses.

    ===========================================================

    Without ExceptionTranslationFilter:

    Exceptions would reach container.

    Ugly responses generated.

    ===========================================================

    Spring Security provides:

    Structured security responses.

    ===========================================================



    ###########################################################
                MOST ASKED INTERVIEW QUESTIONS
    ###########################################################

    Q1. What is AuthenticationEntryPoint?

    Handles authentication failures.

    ------------------------------------------------

    Q2. What response usually returned?

    401 Unauthorized.

    ------------------------------------------------

    Q3. What is AccessDeniedHandler?

    Handles authorization failures.

    ------------------------------------------------

    Q4. What response usually returned?

    403 Forbidden.

    ------------------------------------------------

    Q5. Difference between 401 and 403?

    401

    Authentication failed.

    ------------------------------------------------

    403

    Authentication success
    but authorization failed.

    ------------------------------------------------

    Q6. What is ExceptionTranslationFilter?

    Converts security exceptions
    into HTTP responses.

    ------------------------------------------------

    Q7. Missing JWT gives?

    401

    ------------------------------------------------

    Q8. Invalid JWT gives?

    401

    ------------------------------------------------

    Q9. Valid JWT but insufficient role?

    403

    ------------------------------------------------

    Q10. Why create custom handlers?

    To return consistent JSON responses.

    ===========================================================



    ###########################################################
                    MEMORY TRICK
    ###########################################################

    Authentication Failure

            ↓

    AuthenticationException

            ↓

    AuthenticationEntryPoint

            ↓

    401

    ===========================================================

    Authorization Failure

            ↓

    AccessDeniedException

            ↓

    AccessDeniedHandler

            ↓

    403

    ===========================================================

    ExceptionTranslationFilter

            ↓

    Decides Which One To Call

    ===========================================================
    */
}