package javaDeepDrive.springsecurity;

public class UsernamePasswordAuthenticationTokenConcept {

    /*
    ===========================================================
            USERNAME PASSWORD AUTHENTICATION TOKEN
    ===========================================================

    One of the most important classes in
    Spring Security.

    Most developers use it.

    Few understand it.

    Almost every login request eventually
    creates this object.

    ===========================================================
                    BIG QUESTION
    ===========================================================

    We learned:

    Authentication is interface.

    Question:

    Interfaces cannot create objects.

    So who implements Authentication?

    ===========================================================

    Common implementations:

    UsernamePasswordAuthenticationToken

    JwtAuthenticationToken

    OAuth2AuthenticationToken

    AnonymousAuthenticationToken

    Remember:

    Authentication = Interface

    UsernamePasswordAuthenticationToken
            = Implementation

    ===========================================================
                    CLASS HIERARCHY
    ===========================================================

    Object

      |

      v

    AbstractAuthenticationToken

      |

      v

    UsernamePasswordAuthenticationToken

      |

      v

    Authentication

    ===========================================================

    Actual Hierarchy

    Authentication (Interface)

            ↑

    AbstractAuthenticationToken

            ↑

    UsernamePasswordAuthenticationToken

    ===========================================================
                    WHY THIS CLASS?
    ===========================================================

    During Login

    User enters:

    username = pankaj

    password = 12345

    Spring needs object to carry:

    Username

    Password

    Authentication Status

    Authorities

    That object is:

    UsernamePasswordAuthenticationToken

    ===========================================================
                COMPLETE LOGIN FLOW
    ===========================================================

    Browser

       |

       v

    Login Request

       |

       v

    UsernamePasswordAuthenticationFilter

       |

       v

    UsernamePasswordAuthenticationToken

       |

       v

    AuthenticationManager

       |

       v

    ProviderManager

       |

       v

    DaoAuthenticationProvider

       |

       v

    Authenticated Token

       |

       v

    SecurityContextHolder

    ===========================================================
                FIRST CONSTRUCTOR
    ===========================================================

    public UsernamePasswordAuthenticationToken(
            Object principal,
            Object credentials)

    ===========================================================

    Used BEFORE authentication.

    Example:

    new UsernamePasswordAuthenticationToken(
            "pankaj",
            "12345"
    );

    ===========================================================

    Internal State

    principal

        pankaj

    credentials

        12345

    authorities

        empty

    authenticated

        false

    ===========================================================

    User merely CLAIMS identity.

    Verification not done.

    ===========================================================
                    IMPORTANT
    ===========================================================

    This constructor automatically sets:

    authenticated = false

    ===========================================================
                SECOND CONSTRUCTOR
    ===========================================================

    public UsernamePasswordAuthenticationToken(
            Object principal,
            Object credentials,
            Collection<? extends GrantedAuthority>
                    authorities)

    ===========================================================

    Used AFTER authentication.

    Example:

    new UsernamePasswordAuthenticationToken(

            userDetails,

            null,

            authorities
    );

    ===========================================================

    Internal State

    principal

        UserDetails

    credentials

        null

    authorities

        ROLE_ADMIN

        DELETE_USER

    authenticated

        true

    ===========================================================

    Very Important:

    Spring uses second constructor
    after successful login.

    ===========================================================
                WHY TWO CONSTRUCTORS?
    ===========================================================

    Login has two stages.

    ===========================================================

    Stage 1

    User claims identity.

    username

    password

    ===========================================================

    Stage 2

    Spring verifies identity.

    returns authenticated user.

    ===========================================================

    Therefore:

    Constructor #1

        Before Login

    Constructor #2

        After Login

    ===========================================================
                COMPLETE OBJECT FLOW
    ===========================================================

    User Login

      |

      v

    UsernamePasswordAuthenticationFilter

      |

      v

    Constructor #1

      |

      v

    AuthenticationManager

      |

      v

    ProviderManager

      |

      v

    DaoAuthenticationProvider

      |

      v

    Password Validation

      |

      v

    Constructor #2

      |

      v

    SecurityContextHolder

    ===========================================================
                WHAT IS PRINCIPAL?
    ===========================================================

    Before Login

    principal

        username string

    Example

    "pankaj"

    ===========================================================

    After Login

    principal

        UserDetails

    Example

    CustomUserDetails

    ===========================================================

    Very Common Interview Question.

    ===========================================================
                BEFORE LOGIN OBJECT
    ===========================================================

    UsernamePasswordAuthenticationToken

    ------------------------------------------------

    principal

        "pankaj"

    credentials

        "12345"

    authorities

        []

    authenticated

        false

    ------------------------------------------------

    Created By:

    UsernamePasswordAuthenticationFilter

    ===========================================================
                AFTER LOGIN OBJECT
    ===========================================================

    UsernamePasswordAuthenticationToken

    ------------------------------------------------

    principal

        UserDetails

    credentials

        null

    authorities

        ROLE_ADMIN

        ROLE_USER

    authenticated

        true

    ------------------------------------------------

    Created By:

    DaoAuthenticationProvider

    ===========================================================
                INTERNAL DAO FLOW
    ===========================================================

    DaoAuthenticationProvider

        |

        v

    retrieveUser()

        |

        v

    UserDetailsService

        |

        v

    UserDetails

        |

        v

    PasswordEncoder.matches()

        |

        v

    createSuccessAuthentication()

        |

        v

    UsernamePasswordAuthenticationToken

        authenticated=true

    ===========================================================
                SOURCE CODE IDEA
    ===========================================================

    Simplified

    protected Authentication
    createSuccessAuthentication(

        Object principal,

        Authentication authentication,

        UserDetails user

    ) {

        return new
        UsernamePasswordAuthenticationToken(

                principal,

                authentication.getCredentials(),

                authorities
        );
    }

    ===========================================================

    This is where second token is created.

    ===========================================================
                ABSTRACTAUTHENTICATIONTOKEN
    ===========================================================

    Parent Class.

    Provides:

    Authorities

    Authenticated Flag

    Details

    ===========================================================

    Child Class:

    UsernamePasswordAuthenticationToken

    adds:

    Principal

    Credentials

    ===========================================================
                AUTHORIZATION FLOW
    ===========================================================

    AuthorizationFilter

         |

         v

    SecurityContextHolder

         |

         v

    Authentication

         |

         v

    UsernamePasswordAuthenticationToken

         |

         v

    Authorities

         |

         v

    Access Decision

    ===========================================================
                SECURITYCONTEXT FLOW
    ===========================================================

    Authentication auth

        |

        v

    SecurityContext

        |

        v

    SecurityContextHolder

    ===========================================================

    Example:

    SecurityContextHolder
            .getContext()
            .setAuthentication(auth);

    ===========================================================
                ACCESS CURRENT USER
    ===========================================================

    Authentication auth =

        SecurityContextHolder
            .getContext()
            .getAuthentication();

    ===========================================================

    Actual Runtime Object

    UsernamePasswordAuthenticationToken

    ===========================================================

    Principal

    auth.getPrincipal()

    ===========================================================

    Authorities

    auth.getAuthorities()

    ===========================================================
                JWT DIFFERENCE
    ===========================================================

    Username Password Login

            |

            v

    UsernamePasswordAuthenticationToken

    ===========================================================

    JWT Login

            |

            v

    Often still uses

    UsernamePasswordAuthenticationToken

    ===========================================================

    Why?

    Because Spring only needs:

    Authenticated User

    Authorities

    ===========================================================

    Token source doesn't matter.

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

    UsernamePasswordAuthenticationFilter

      |

      v

    UsernamePasswordAuthenticationToken

         authenticated=false

      |

      v

    AuthenticationManager

      |

      v

    ProviderManager

      |

      v

    DaoAuthenticationProvider

      |

      v

    UserDetailsService

      |

      v

    UserDetails

      |

      v

    Password Validation

      |

      v

    UsernamePasswordAuthenticationToken

         authenticated=true

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

    Controller

    ===========================================================
                INTERVIEW QUESTIONS
    ===========================================================

    Q1. What is
        UsernamePasswordAuthenticationToken?

    Authentication implementation used for
    username/password authentication.

    ------------------------------------------------

    Q2. Does it implement Authentication?

    Yes.

    Through AbstractAuthenticationToken.

    ------------------------------------------------

    Q3. Why two constructors?

    One before authentication.

    One after authentication.

    ------------------------------------------------

    Q4. What is principal before login?

    Username String.

    ------------------------------------------------

    Q5. What is principal after login?

    UserDetails object.

    ------------------------------------------------

    Q6. Why create second token?

    To represent authenticated user.

    ------------------------------------------------

    Q7. Who creates first token?

    UsernamePasswordAuthenticationFilter.

    ------------------------------------------------

    Q8. Who creates second token?

    DaoAuthenticationProvider.

    ------------------------------------------------

    Q9. Where token stored?

    SecurityContext.

    ------------------------------------------------

    Q10. Where SecurityContext stored?

    SecurityContextHolder.

    ===========================================================
                    MEMORY TRICK
    ===========================================================

    Login Request

          ↓

    UsernamePasswordAuthenticationFilter

          ↓

    Token #1

    authenticated=false

          ↓

    AuthenticationManager

          ↓

    DaoAuthenticationProvider

          ↓

    UserDetailsService

          ↓

    PasswordEncoder

          ↓

    Token #2

    authenticated=true

          ↓

    SecurityContext

          ↓

    SecurityContextHolder

          ↓

    AuthorizationFilter

    ===========================================================
    */
}