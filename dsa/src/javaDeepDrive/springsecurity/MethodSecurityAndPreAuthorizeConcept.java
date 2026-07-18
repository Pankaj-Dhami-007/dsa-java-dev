package javaDeepDrive.springsecurity;

public class MethodSecurityAndPreAuthorizeConcept {

    /*
    ===========================================================
                METHOD SECURITY
                        +
                PREAUTHORIZE
    ===========================================================

    We already learned:

    Request Level Security

    Example:

    /admin/**

        hasRole("ADMIN")

    ===========================================================

    Question:

    What if controller endpoint is secure
    but service method is called from
    somewhere else?

    ===========================================================

    Example

    AdminController

            |

            v

    UserService.deleteUser()

    ===========================================================

    Another Developer

            |

            v

    Calls UserService.deleteUser()

    directly.

    ===========================================================

    URL Security bypassed.

    ===========================================================

    Solution:

    Method Security

    ===========================================================

    Method Security protects methods.

    Not URLs.

    ===========================================================



    ###########################################################
                    BIG PICTURE
    ###########################################################

    Request

        |

        v

    AuthorizationFilter

        |

        v

    Controller

        |

        v

    Method Security

        |

        v

    Service Method

        |

        v

    Execute

    ===========================================================

    URL Security

        Protects URLs

    ===========================================================

    Method Security

        Protects Methods

    ===========================================================



    ###########################################################
                WHY METHOD SECURITY?
    ###########################################################

    Imagine

    /admin/delete-user

    secured.

    ===========================================================

    But

    UserService.deleteUser()

    not secured.

    ===========================================================

    Another component can call it.

    ===========================================================

    Security hole.

    ===========================================================

    Method Security closes that hole.

    ===========================================================



    ###########################################################
                ENABLE METHOD SECURITY
    ###########################################################

    Spring Security 6

    ===========================================================

    @EnableMethodSecurity

    ===========================================================

    Example

    @Configuration

    @EnableMethodSecurity

    class SecurityConfig {

    }

    ===========================================================

    Without this annotation

    Method Security disabled.

    ===========================================================



    ###########################################################
                PREAUTHORIZE
    ###########################################################

    Most important annotation.

    ===========================================================

    Runs BEFORE method execution.

    ===========================================================

    Example

    @PreAuthorize(
        "hasRole('ADMIN')"
    )
    public void deleteUser() {

    }

    ===========================================================

    Spring checks authorization.

    ===========================================================

    If allowed

        execute method

    ===========================================================

    If denied

        exception

    ===========================================================



    ###########################################################
                COMPLETE FLOW
    ###########################################################

    Method Call

         |

         v

    @PreAuthorize

         |

         v

    Authorization Check

         |

    +----+----+

    |         |

    v         v

    PASS      FAIL

    |           |

    v           v

    Method   AccessDeniedException

    ===========================================================



    ###########################################################
                HASROLE()
    ###########################################################

    Example

    @PreAuthorize(
      "hasRole('ADMIN')"
    )

    ===========================================================

    Internal Conversion

    ADMIN

        ↓

    ROLE_ADMIN

    ===========================================================

    Authorities compared.

    ===========================================================



    ###########################################################
                HASAUTHORITY()
    ###########################################################

    Example

    @PreAuthorize(
      "hasAuthority('DELETE_USER')"
    )

    ===========================================================

    Direct comparison.

    ===========================================================

    No ROLE_ prefix added.

    ===========================================================



    ###########################################################
                HASANYROLE()
    ###########################################################

    Example

    @PreAuthorize(
      "hasAnyRole('ADMIN','MANAGER')"
    )

    ===========================================================

    ROLE_ADMIN

    OR

    ROLE_MANAGER

    ===========================================================

    Any one match.

    ===========================================================



    ###########################################################
                HASANYAUTHORITY()
    ###########################################################

    Example

    @PreAuthorize(
       "hasAnyAuthority(
            'CREATE_USER',
            'DELETE_USER'
       )"
    )

    ===========================================================

    Any authority match accepted.

    ===========================================================



    ###########################################################
                AUTHENTICATED()
    ###########################################################

    Example

    @PreAuthorize(
       "isAuthenticated()"
    )

    ===========================================================

    Any logged-in user.

    ===========================================================

    Roles not checked.

    ===========================================================



    ###########################################################
                SPEL EXPRESSIONS
    ###########################################################

    Extremely Important.

    ===========================================================

    @PreAuthorize uses:

    SpEL

    ===========================================================

    Spring Expression Language

    ===========================================================

    Example

    @PreAuthorize(

      "#userId == authentication.principal.id"

    )

    ===========================================================

    Meaning:

    User can access only own data.

    ===========================================================



    ###########################################################
                METHOD PARAMETERS
    ###########################################################

    Example

    public User getUser(Long userId)

    ===========================================================

    @PreAuthorize(

      "#userId ==
      authentication.principal.id"

    )

    ===========================================================

    Method parameter accessible.

    ===========================================================



    ###########################################################
                AUTHENTICATION ACCESS
    ###########################################################

    Authentication object available
    inside SpEL.

    ===========================================================

    Example

    authentication.name

    ===========================================================

    authentication.authorities

    ===========================================================

    authentication.principal

    ===========================================================

    Very powerful.

    ===========================================================



    ###########################################################
                POSTAUTHORIZE
    ###########################################################

    Runs AFTER method execution.

    ===========================================================

    Example

    @PostAuthorize(

      "returnObject.owner ==
       authentication.name"

    )

    ===========================================================

    Method executes first.

    ===========================================================

    Result checked afterwards.

    ===========================================================



    ###########################################################
                PREFILTER
    ###########################################################

    Runs BEFORE method execution.

    ===========================================================

    Filters collection arguments.

    ===========================================================

    Example

    List<User>

            ↓

    Filter Data

            ↓

    Pass To Method

    ===========================================================



    ###########################################################
                POSTFILTER
    ###########################################################

    Runs AFTER method execution.

    ===========================================================

    Filters returned collection.

    ===========================================================

    Example

    Method returns:

    100 Users

    ===========================================================

    Filter

    ===========================================================

    Return:

    10 Users

    ===========================================================



    ###########################################################
                METHOD SECURITY FLOW
    ###########################################################

    Controller

        |

        v

    Service Proxy

        |

        v

    Method Security Check

        |

        v

    AuthorizationManager

        |

    +---+---+

    |       |

    v       v

    PASS    FAIL

    |         |

    v         v

    Method   Exception

    ===========================================================



    ###########################################################
                AOP PROXY MAGIC
    ###########################################################

    Huge Interview Topic.

    ===========================================================

    Question:

    How does Spring intercept method calls?

    ===========================================================

    Answer:

    AOP Proxy

    ===========================================================

    Actual Object

    UserService

    ===========================================================

    Spring Creates

    UserServiceProxy

    ===========================================================

    Controller

            |

            v

    Proxy

            |

            v

    Security Check

            |

            v

    Real Method

    ===========================================================

    Method Security depends heavily
    on Spring AOP.

    ===========================================================



    ###########################################################
            METHOD SECURITY INTERCEPTOR
    ###########################################################

    Internal Component.

    ===========================================================

    Responsible for:

    Reading annotations

    Evaluating expressions

    Invoking AuthorizationManager

    ===========================================================

    Think:

    Method Version Of

    AuthorizationFilter

    ===========================================================



    ###########################################################
            URL SECURITY VS METHOD SECURITY
    ###########################################################

    URL SECURITY

    ------------------------------------------------

    Protect URLs

    ------------------------------------------------

    AuthorizationFilter

    ------------------------------------------------

    Request Level

    ------------------------------------------------

    Web Layer

    ===========================================================

    METHOD SECURITY

    ------------------------------------------------

    Protect Methods

    ------------------------------------------------

    Method Security Interceptor

    ------------------------------------------------

    Service Level

    ------------------------------------------------

    Business Layer

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

    Security Filters

        |

        v

    Authentication

        |

        v

    AuthorizationFilter

        |

        v

    Controller

        |

        v

    Service Proxy

        |

        v

    MethodSecurityInterceptor

        |

        v

    AuthorizationManager

        |

        v

    @PreAuthorize

        |

        v

    Service Method

        |

        v

    Repository

    ===========================================================



    ###########################################################
                        THEORY
    ###########################################################

    Spring Security provides
    multiple layers of protection.

    ===========================================================

    Layer 1

    Request Security

    ===========================================================

    Protects URLs.

    ===========================================================

    Layer 2

    Method Security

    ===========================================================

    Protects Business Methods.

    ===========================================================

    Defense In Depth.

    ===========================================================

    Even if URL security is bypassed,

    Method Security still protects
    business operations.

    ===========================================================

    Method Security works using:

    Spring AOP

            +

    AuthorizationManager

            +

    SecurityContextHolder

    ===========================================================

    Authentication stored inside:

    SecurityContextHolder

    ===========================================================

    Method Security reads:

    Authentication

            ↓

    Authorities

            ↓

    SpEL Expression

            ↓

    Access Decision

    ===========================================================

    Therefore:

    Request Security

    answers

    Can user access URL?

    ===========================================================

    Method Security

    answers

    Can user execute method?

    ===========================================================

    Enterprise applications often use both.

    ===========================================================



    ###########################################################
                SELF INVOCATION PROBLEM
    ###########################################################

    Huge Senior Interview Question.

    ===========================================================

    Example

    class UserService {

        @PreAuthorize(
            "hasRole('ADMIN')"
        )
        public void deleteUser() {

        }

        public void process() {

            deleteUser();

        }

    }

    ===========================================================

    Problem:

    process()

            ↓

    deleteUser()

    ===========================================================

    Proxy bypassed.

    ===========================================================

    Security annotation may not execute.

    ===========================================================

    Why?

    Because call happens inside same object.

    ===========================================================

    This is called:

    Self Invocation Problem

    ===========================================================

    Very common interview question.

    ===========================================================



    ###########################################################
                MOST ASKED INTERVIEW QUESTIONS
    ###########################################################

    Q1. What is Method Security?

    Security applied on methods.

    ------------------------------------------------

    Q2. How to enable it?

    @EnableMethodSecurity

    ------------------------------------------------

    Q3. What is @PreAuthorize?

    Authorization before method execution.

    ------------------------------------------------

    Q4. What is @PostAuthorize?

    Authorization after method execution.

    ------------------------------------------------

    Q5. What language does
        @PreAuthorize use?

    SpEL.

    ------------------------------------------------

    Q6. Why does Method Security
        need AOP?

    To intercept method calls.

    ------------------------------------------------

    Q7. What is Self Invocation Problem?

    Internal method call bypasses proxy.

    ------------------------------------------------

    Q8. URL Security vs Method Security?

    URL protects endpoints.

    Method Security protects methods.

    ------------------------------------------------

    Q9. Which object provides current user?

    Authentication from SecurityContextHolder.

    ------------------------------------------------

    Q10. Why use both URL and Method Security?

    Defense in Depth.

    ===========================================================



    ###########################################################
                    MEMORY TRICK
    ###########################################################

    Request

        ↓

    AuthorizationFilter

        ↓

    Controller

        ↓

    Service Proxy

        ↓

    MethodSecurityInterceptor

        ↓

    @PreAuthorize

        ↓

    AuthorizationManager

        ↓

    Access Decision

        ↓

    Service Method

    ===========================================================
    */
}