package javaDeepDrive.springsecurity;

public class AuthorizationConcept {

    /*
    ===========================================================
                        AUTHORIZATION
    ===========================================================

    Authentication answers:

    WHO ARE YOU?

    Authorization answers:

    WHAT ARE YOU ALLOWED TO DO?

    Authentication and Authorization are
    completely different concepts.

    Many freshers mix them together.

    Interviewers love asking:

    Explain Authentication vs Authorization.

    ===========================================================
                    REAL WORLD EXAMPLE
    ===========================================================

    Airport Example

    Authentication:

    Show Passport

    Airport verifies identity.

    You are Pankaj.

    --------------------------------------------

    Authorization:

    Can you enter VIP Lounge?
    Can you enter Pilot Cabin?
    Can you enter Security Room?
    Different permissions.

    Same person.

    Different access levels.

    ===========================================================
                    SIMPLE APPLICATION
    ===========================================================

    User Types

    ADMIN

    MANAGER

    USER

    APIs

    GET /profile
    GET /users
    DELETE /users/{id}

    ===========================================================

    USER

    Can access:

    /profile

    ===========================================================

    MANAGER

    Can access:

    /profile

    /users

    ===========================================================

    ADMIN

    Can access:

    /profile

    /users

    DELETE /users

    ===========================================================

    This access control is called:

    Authorization

    ===========================================================
                COMPLETE REQUEST FLOW
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

    SecurityFilterChain

      |

      v

    Authentication Filter

      |

      v

    Authentication Success

      |

      v

    SecurityContextHolder

      |

      v

    Authorization Filter

      |

      v

    Access Decision

      |

      v

    Controller

    ===========================================================
                AUTHENTICATION FIRST
    ===========================================================

    Important Rule

    Authentication ALWAYS happens first.
    Then Authorization.
    Never reverse.

    Wrong:

    Check permissions first.
    Then identify user.
    Makes no sense.

    ===========================================================
                AUTHORIZATION FILTER
    ===========================================================

    Spring Security contains:

    AuthorizationFilter

    Responsibility:

    Determine whether current user
    can access requested resource.

    ===========================================================
                EXAMPLE REQUEST
    ===========================================================

    Logged In User:

    pankaj

    Role:

    USER

    Request:

    DELETE /users/1

    Configuration:

    DELETE /users/**

    Requires:

    ADMIN

    ===========================================================

    AuthorizationFilter checks:

    Current User Role

            vs

    Required Role

    USER

            vs

    ADMIN

    Result:

    Access Denied

    Response:

    403 Forbidden

    ===========================================================
                WHERE USER COMES FROM?
    ===========================================================

    AuthorizationFilter reads:
    SecurityContextHolder

    Flow:

    SecurityContextHolder

            |

            v

    SecurityContext

            |

            v

    Authentication

            |

            v

    Principal

    Authorities

    ===========================================================
                AUTHENTICATION OBJECT
    ===========================================================

    Authentication contains:

    Principal
    Credentials
    Authorities
    Authenticated Flag

    Example:

    Authentication

        username = pankaj

        authorities =
            ROLE_ADMIN
            ROLE_USER

    ===========================================================
                WHAT ARE AUTHORITIES?
    ===========================================================

    Authority means:
    Permission

    Example:

    CREATE_USER

    DELETE_USER

    UPDATE_USER

    VIEW_USER

    ===========================================================
                WHAT ARE ROLES?
    ===========================================================

    Role is a collection of authorities.

    Example:

    ADMIN

       CREATE_USER
       DELETE_USER
       UPDATE_USER
       VIEW_USER

    ---------------------------------------

    USER

       VIEW_USER

    ===========================================================
                IMPORTANT INTERVIEW POINT
    ===========================================================

    Internally Spring Security treats:

    Role

    as

    Authority

    Example:

    ROLE_ADMIN
    ROLE_USER

    Actually stored as authorities.

    ===========================================================
                ROLE STORAGE
    ===========================================================

    Example:

    Database

    id = 1

    username = pankaj

    role = ADMIN

    ===========================================================

    Spring converts it into:

    ROLE_ADMIN

    ===========================================================

    Notice:

    ROLE_

    prefix automatically used.

    ===========================================================
                HASROLE()
    ===========================================================

    Example:

    hasRole("ADMIN")

    Spring internally converts:

    ADMIN

        into

    ROLE_ADMIN

    Then comparison happens.

    ===========================================================
                INTERNAL PROCESS
    ===========================================================

    hasRole("ADMIN")

        |

        v

    ROLE_ADMIN

        |

        v

    Compare With Authorities

        |

        v

    Access Granted / Denied

    ===========================================================
                HASAUTHORITY()
    ===========================================================

    Example:

    hasAuthority("DELETE_USER")
    No prefix added.
    Direct comparison.

    ===========================================================
                DIFFERENCE
    ===========================================================

    hasRole("ADMIN")

    becomes

    ROLE_ADMIN

    ===========================================================

    hasAuthority("ADMIN")

    becomes

    ADMIN

    ===========================================================

    Completely different.

    ===========================================================
                INTERVIEW QUESTION
    ===========================================================

    Which one should we use?

    Small Project
    Roles sufficient.

    ------------------------------------------------

    Enterprise Project

    Authorities preferred.

    Because fine-grained permissions.

    Example:

    CREATE_ORDER
    UPDATE_ORDER
    DELETE_ORDER
    APPROVE_ORDER
    EXPORT_ORDER

    ===========================================================
                REQUEST MATCHER EXAMPLE
    ===========================================================

    http

        .authorizeHttpRequests(auth -> auth

            .requestMatchers("/admin/**")

            .hasRole("ADMIN")

            .requestMatchers("/user/**")

            .hasRole("USER")

            .anyRequest()

            .authenticated()
        );

    ===========================================================
                FLOW OF ABOVE CONFIG
    ===========================================================

    Request:

    /admin/users

          |

          v

    Authentication Complete

          |

          v

    AuthorizationFilter

          |

          v

    Match Rule

          |

          v

    hasRole("ADMIN")

          |

          v

    ROLE_ADMIN

          |

          v

    Compare Authorities

          |

          v

    Allow / Deny

    ===========================================================
                WHAT IS PRINCIPAL?
    ===========================================================

    Principal means:

    Logged In User

    Example:

    UserDetails

    Principal contains:

    Username

    Roles

    Account Status

    ===========================================================
                ACCESS DENIED FLOW
    ===========================================================

    Request

       |

       v

    Authentication Success

       |

       v

    Authorization Check

       |

       v

    Access Denied

       |

       v

    AccessDeniedException

       |

       v

    ExceptionTranslationFilter

       |

       v

    403 Response

    ===========================================================
                WHY 401 VS 403?
    ===========================================================

    Very Important Interview Question

    ===========================================================

    401 Unauthorized

    Means:

    User NOT authenticated.

    Missing token.
    Invalid token.
    Login required.

    ===========================================================

    403 Forbidden

    Means:

    User authenticated.
    But lacks permission.

    ===========================================================

    Example:

    No JWT Token

    Response:

    401

    ===========================================================

    JWT Valid

    User Role = USER
    Endpoint Requires ADMIN
    Response:

    403

    ===========================================================
                METHOD LEVEL SECURITY
    ===========================================================

    Authorization not only at URL level.
    Also possible at method level.

    Example:

    @PreAuthorize(
        "hasRole('ADMIN')"
    )

    public void deleteUser() {

    }

    ===========================================================
                METHOD LEVEL FLOW
    ===========================================================

    Request

      |

      v

    Controller

      |

      v

    Service Method

      |

      v

    @PreAuthorize

      |

      v

    Security Check

      |

      v

    Execute Method

    ===========================================================
                INTERNAL AUTHORIZATION FLOW
    ===========================================================

    Request

      |

      v

    AuthorizationFilter

      |

      v

    AuthorizationManager

      |

      v

    SecurityContextHolder

      |

      v

    Authentication

      |

      v

    Authorities

      |

      v

    Access Decision

      |

      v

    Allow / Deny

    ===========================================================
                OLD SPRING SECURITY
    ===========================================================

    Older versions used:

    AccessDecisionManager

    AccessDecisionVoter

    ===========================================================

    Flow

    AuthorizationFilter

         |

         v

    AccessDecisionManager

         |

         v

    Voters

         |

         v

    Decision

    ===========================================================

    Spring Security 6+

    Uses:

    AuthorizationManager

    Simpler architecture.

    ===========================================================
                COMPLETE INTERNAL FLOW
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

    SecurityContextHolder

      |

      v

    AuthorizationFilter

      |

      v

    AuthorizationManager

      |

      v

    Authorities Check

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

    ===========================================================
                INTERVIEW QUESTIONS
    ===========================================================

    Q1. What is Authorization?

    Determines what authenticated user
    can access.

    ------------------------------------------------

    Q2. Authentication vs Authorization?

    Authentication:

    Who are you?

    Authorization:

    What can you do?

    ------------------------------------------------

    Q3. What is AuthorizationFilter?

    Performs authorization checks.

    ------------------------------------------------

    Q4. Where are permissions stored?

    Inside Authentication object.

    ------------------------------------------------

    Q5. Where Authentication stored?

    SecurityContext.

    ------------------------------------------------

    Q6. Difference between
        hasRole() and hasAuthority()?

    hasRole("ADMIN")

    becomes

    ROLE_ADMIN

    ------------------------------------------------

    hasAuthority("ADMIN")

    remains

    ADMIN

    ------------------------------------------------

    Q7. Difference between
        401 and 403?

    401

    Not authenticated.

    403

    Authenticated but not authorized.

    ------------------------------------------------

    Q8. What is Principal?

    Logged-in user.

    ------------------------------------------------

    Q9. What is GrantedAuthority?

    Represents permission.

    ------------------------------------------------

    Q10. Role vs Authority?

    Role = Collection of Authorities.

    ===========================================================
                    MEMORY TRICK
    ===========================================================

    Authentication

          ↓

    SecurityContextHolder

          ↓

    AuthorizationFilter

          ↓

    AuthorizationManager

          ↓

    Authorities

          ↓

    Access Decision

          ↓

    Controller

    ===========================================================
    */
}