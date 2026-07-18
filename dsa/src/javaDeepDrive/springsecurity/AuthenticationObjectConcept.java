package javaDeepDrive.springsecurity;

public class AuthenticationObjectConcept {

    /*
    ===========================================================
                    AUTHENTICATION OBJECT
    ===========================================================

    This is one of the MOST IMPORTANT classes
    in entire Spring Security.

    Almost every Spring Security component
    works with Authentication object.

    If you understand Authentication object,
    then SecurityContext,
    SecurityContextHolder,
    Authorization,
    JWT,
    UserDetails

    all become much easier.

    ===========================================================
                    BIG PICTURE
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

    Authentication Object

        |

        v

    AuthenticationManager

        |

        v

    AuthenticationProvider

        |

        v

    Authentication Object

        |

        v

    SecurityContext

        |

        v

    SecurityContextHolder

    ===========================================================

    Notice:

    Authentication object exists

    BEFORE login

    and

    AFTER login.

    This is extremely important.

    ===========================================================
                    INTERFACE
    ===========================================================

    public interface Authentication
            extends Principal, Serializable {

        Collection<? extends GrantedAuthority>
        getAuthorities();

        Object getCredentials();

        Object getDetails();

        Object getPrincipal();

        boolean isAuthenticated();

        void setAuthenticated(
                boolean authenticated);

    }

    ===========================================================
                    RESPONSIBILITY
    ===========================================================

    Authentication object stores:

    WHO IS USER ?

    WHAT AUTHORITIES USER HAS ?

    IS USER AUTHENTICATED ?

    ===========================================================
                    MOST IMPORTANT METHODS
    ===========================================================

    getPrincipal()

    Current user.

    ------------------------------------------------

    getCredentials()

    Password or credentials.

    ------------------------------------------------

    getAuthorities()

    Permissions.

    ------------------------------------------------

    isAuthenticated()

    Login successful?

    ===========================================================
                    BIG INTERVIEW QUESTION
    ===========================================================

    Why Authentication interface?

    Why not UserDetails directly?

    ===========================================================

    Because Spring Security supports:

    Username Password Login

    JWT Login

    OAuth2 Login

    LDAP Login

    SAML Login

    Custom Login

    ===========================================================

    Common abstraction needed.

    That abstraction is:

    Authentication

    ===========================================================
                BEFORE LOGIN OBJECT
    ===========================================================

    User enters:

    username = pankaj

    password = 12345

    ===========================================================

    UsernamePasswordAuthenticationFilter

    creates:

    Authentication auth =

        new UsernamePasswordAuthenticationToken(
                "pankaj",
                "12345"
        );

    ===========================================================

    Internally:

    principal = pankaj

    credentials = 12345

    authorities = empty

    authenticated = false

    ===========================================================

    Very Important

    User is NOT authenticated yet.

    This object only contains claim.

    ===========================================================
                    OBJECT STATE
    ===========================================================

    Authentication

    ------------------------------------------------

    principal

        pankaj

    credentials

        12345

    authorities

        []

    authenticated

        false

    ===========================================================
                    LOGIN FLOW
    ===========================================================

    Authentication Filter

        |

        v

    AuthenticationManager

        |

        v

    ProviderManager

        |

        v

    DaoAuthenticationProvider

    ===========================================================
                DAOAUTHENTICATIONPROVIDER
    ===========================================================

    DaoAuthenticationProvider receives:

    Authentication Object

    ===========================================================

    principal

        pankaj

    credentials

        12345

    ===========================================================

    Then:

    loadUserByUsername()

    Password Validation

    Account Checks

    ===========================================================
                IMPORTANT QUESTION
    ===========================================================

    After successful authentication

    does Spring modify existing object?

    ===========================================================

    NO

    ===========================================================

    Spring creates NEW Authentication object.

    This is extremely important.

    ===========================================================
                AFTER LOGIN OBJECT
    ===========================================================

    User authenticated successfully.

    Spring creates:

    Authentication auth =

        UsernamePasswordAuthenticationToken

    ===========================================================

    principal

        UserDetails

    credentials

        null or hidden

    authorities

        ROLE_ADMIN

        ROLE_USER

    authenticated

        true

    ===========================================================

    Notice:

    This is a NEW object.

    ===========================================================
                WHY NEW OBJECT?
    ===========================================================

    Security reason.

    Raw password should not continue
    travelling in application.

    ===========================================================

    BEFORE

    credentials = 12345

    ===========================================================

    AFTER

    credentials removed

    ===========================================================

    Safer design.

    ===========================================================
                    TWO OBJECTS
    ===========================================================

    First Object

    Created By

    UsernamePasswordAuthenticationFilter

    ------------------------------------------------

    principal

        username

    credentials

        password

    authenticated

        false

    ===========================================================

    Second Object

    Created By

    DaoAuthenticationProvider

    ------------------------------------------------

    principal

        UserDetails

    authorities

        permissions

    authenticated

        true

    ===========================================================

    Most developers don't know this.

    Common senior interview question.

    ===========================================================
                    PRINCIPAL
    ===========================================================

    Very Important Concept.

    Authentication

            |

            v

    Principal

    ===========================================================

    Principal means:

    Current User

    ===========================================================

    BEFORE LOGIN

    principal

        username string

    ===========================================================

    AFTER LOGIN

    principal

        UserDetails object

    ===========================================================

    Example

    UserDetails

        username

        password

        authorities

    ===========================================================
                    CREDENTIALS
    ===========================================================

    Credentials represent proof.

    Usually:

    Password

    ===========================================================

    Examples

    Password

    OTP

    JWT Token

    API Key

    Certificate

    ===========================================================

    Different authentication types
    use different credentials.

    ===========================================================
                    AUTHORITIES
    ===========================================================

    Stored inside Authentication.

    Example:

    ROLE_ADMIN

    DELETE_USER

    CREATE_USER

    ===========================================================

    AuthorizationFilter reads authorities
    from Authentication object.

    ===========================================================
                    DETAILS
    ===========================================================

    getDetails()

    Stores request specific information.

    Example:

    IP Address

    Session ID

    Device Information

    ===========================================================
                    OBJECT HIERARCHY
    ===========================================================

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

            |

            v

    UserDetails

            |

            v

    Authorities

    ===========================================================
                    SECURITYCONTEXT
    ===========================================================

    After authentication:

    SecurityContext context =

        SecurityContextHolder.getContext();

    context.setAuthentication(auth);

    ===========================================================

    Authentication stored here.

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

    Authentication Object #1

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

    Authentication Object #2

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

    DispatcherServlet

        |

        v

    Controller

    ===========================================================
                    JWT CASE
    ===========================================================

    JWT Authentication also creates
    Authentication object.

    ===========================================================

    JWT Filter

        |

        v

    Extract Token

        |

        v

    Validate Token

        |

        v

    Load UserDetails

        |

        v

    Create Authentication

        |

        v

    SecurityContextHolder

    ===========================================================

    Therefore:

    Authorization doesn't care whether user came from:

    Login Form

    JWT

    OAuth2

    LDAP

    ===========================================================

    It only looks at:

    Authentication object

    ===========================================================
                ACCESSING CURRENT USER
    ===========================================================

    Authentication authentication =

        SecurityContextHolder
            .getContext()
            .getAuthentication();

    ===========================================================

    UserDetails user =

        (UserDetails)
            authentication.getPrincipal();

    ===========================================================

    Authorities:

    authentication.getAuthorities();

    ===========================================================
                MOST ASKED INTERVIEW QUESTIONS
    ===========================================================

    Q1. What is Authentication?

    Represents authenticated user information.

    ------------------------------------------------

    Q2. What does Authentication contain?

    Principal

    Credentials

    Authorities

    Authenticated Flag

    ------------------------------------------------

    Q3. What is Principal?

    Current authenticated user.

    ------------------------------------------------

    Q4. What is Credentials?

    Proof of identity.

    Usually password.

    ------------------------------------------------

    Q5. Where is Authentication stored?

    SecurityContext.

    ------------------------------------------------

    Q6. Where is SecurityContext stored?

    SecurityContextHolder.

    ------------------------------------------------

    Q7. Why Authentication interface?

    Common abstraction for all
    authentication mechanisms.

    ------------------------------------------------

    Q8. Does Spring create one or two
    Authentication objects?

    Two.

    Before login

    authenticated=false

    After login

    authenticated=true

    ------------------------------------------------

    Q9. Why create second object?

    Security.

    Remove raw credentials.

    ------------------------------------------------

    Q10. Does AuthorizationFilter use
    UserDetails directly?

    No.

    Uses Authentication object.

    ===========================================================
                    MEMORY TRICK
    ===========================================================

    Authentication Filter

            ↓

    Authentication Object #1

            ↓

    AuthenticationManager

            ↓

    ProviderManager

            ↓

    DaoAuthenticationProvider

            ↓

    UserDetails

            ↓

    Authentication Object #2

            ↓

    SecurityContext

            ↓

    SecurityContextHolder

            ↓

    AuthorizationFilter

    ===========================================================
    */
}