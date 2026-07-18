package javaDeepDrive.springsecurity;

public class AuthenticationManagerAndAuthenticationProviderConcept {

    /*
    ===========================================================
        AUTHENTICATION MANAGER
                    +
        AUTHENTICATION PROVIDER
    ===========================================================

    These two are among the most important
    Spring Security concepts.

    Most developers know:

    AuthenticationManager

    AuthenticationProvider

    But many don't understand:

    Why both exist?

    Why not directly authenticate user?

    Why ProviderManager exists?

    How Spring chooses provider?

    ===========================================================
                    BIG PICTURE
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

    ProviderManager

        |

        v

    AuthenticationProvider

        |

        v

    DaoAuthenticationProvider

        |

        v

    UserDetailsService

        |

        v

    PasswordEncoder

        |

        v

    Authentication Success

    ===========================================================
                    BIG QUESTION
    ===========================================================

    Why not:

    AuthenticationFilter

        |

        v

    UserDetailsService

    Directly?

    ===========================================================

    Because Spring Security supports:

    Username Password

    JWT

    OAuth2

    LDAP

    SAML

    Custom Authentication

    ===========================================================

    Every authentication mechanism has
    different logic.

    Therefore Spring uses Strategy Pattern.

    ===========================================================
                    SIMPLE ANALOGY
    ===========================================================

    Hospital

    ===========================================================

    Receptionist

        AuthenticationManager

    ===========================================================

    Doctor

        AuthenticationProvider

    ===========================================================

    Receptionist doesn't treat patients.

    Doctor does.

    ===========================================================

    AuthenticationManager doesn't authenticate.

    AuthenticationProvider authenticates.

    ===========================================================
            AUTHENTICATIONMANAGER
    ===========================================================

    AuthenticationManager is an interface.

    ===========================================================

    public interface AuthenticationManager {

        Authentication authenticate(
                Authentication authentication
        );

    }

    ===========================================================

    Only one method.

    authenticate()

    ===========================================================
                    RESPONSIBILITY
    ===========================================================

    AuthenticationManager acts as:

    Coordinator

    Delegator

    Entry Point

    ===========================================================

    It receives authentication request.

    Then delegates work.

    ===========================================================
                IMPORTANT INTERVIEW POINT
    ===========================================================

    AuthenticationManager

    DOES NOT

    authenticate user itself.

    ===========================================================

    It delegates authentication.

    ===========================================================
                DEFAULT IMPLEMENTATION
    ===========================================================

    ProviderManager

    ===========================================================

    Most important interview question.

    ===========================================================

    AuthenticationManager

            Interface

                |

                v

    ProviderManager

        Implementation

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

    ===========================================================
                PROVIDERMANAGER
    ===========================================================

    ProviderManager is the real engine.

    ===========================================================

    Responsibilities:

    1. Hold Providers

    2. Select Provider

    3. Delegate Authentication

    4. Return Result

    ===========================================================
                INTERNAL STRUCTURE
    ===========================================================

    ProviderManager

        |

        v

    List<AuthenticationProvider>

    ===========================================================

    Example

    DaoAuthenticationProvider

    JwtAuthenticationProvider

    OAuthAuthenticationProvider

    LDAPAuthenticationProvider

    ===========================================================
                WHY MULTIPLE PROVIDERS?
    ===========================================================

    Same application may support:

    Login Form

    Google Login

    JWT

    Corporate LDAP

    ===========================================================

    Different providers needed.

    ===========================================================
                AUTHENTICATIONPROVIDER
    ===========================================================

    AuthenticationProvider is interface.

    ===========================================================

    public interface AuthenticationProvider {

        Authentication authenticate(
                Authentication authentication
        );

        boolean supports(
                Class<?> authentication
        );
    }

    ===========================================================

    Two methods only.

    ===========================================================
                    RESPONSIBILITY
    ===========================================================

    Actual authentication happens here.

    Not inside AuthenticationManager.

    ===========================================================

    AuthenticationProvider is actual worker.

    ===========================================================
                SUPPORTS METHOD
    ===========================================================

    Extremely important.

    ===========================================================

    supports()

    tells Spring:

    Can I handle this Authentication type?

    ===========================================================

    Example:

    DaoAuthenticationProvider

    supports

    UsernamePasswordAuthenticationToken

    ===========================================================

    JWT Provider

    supports

    JwtAuthenticationToken

    ===========================================================
                INTERNAL FLOW
    ===========================================================

    ProviderManager

       |

       v

    Provider #1

    supports() ?

    ===========================================================

    No

    ===========================================================

    Provider #2

    supports() ?

    ===========================================================

    Yes

    ===========================================================

    authenticate()

    ===========================================================

    Authentication Success

    ===========================================================
                REAL EXAMPLE
    ===========================================================

    Providers

    ------------------------------------------------

    DaoAuthenticationProvider

    ------------------------------------------------

    JwtAuthenticationProvider

    ------------------------------------------------

    OAuth2AuthenticationProvider

    ===========================================================

    Incoming Token

    UsernamePasswordAuthenticationToken

    ===========================================================

    ProviderManager checks:

    DaoAuthenticationProvider.supports() ?

    YES

    ===========================================================

    Authentication delegated.

    ===========================================================
            DAOAUTHENTICATIONPROVIDER
    ===========================================================

    Most commonly used provider.

    Used in:

    Username Password Login

    ===========================================================

    Internal Flow

    DaoAuthenticationProvider

        |

        v

    UserDetailsService

        |

        v

    UserDetails

        |

        v

    PasswordEncoder

        |

        v

    Authentication Success

    ===========================================================
                STEP-BY-STEP LOGIN FLOW
    ===========================================================

    User Login

    username = pankaj

    password = 12345

    ===========================================================

    Step 1

    UsernamePasswordAuthenticationFilter

    creates:

    UsernamePasswordAuthenticationToken

    authenticated=false

    ===========================================================

    Step 2

    AuthenticationManager.authenticate()

    ===========================================================

    Step 3

    ProviderManager

    receives request.

    ===========================================================

    Step 4

    ProviderManager iterates providers.

    ===========================================================

    Step 5

    DaoAuthenticationProvider.supports()

    returns true.

    ===========================================================

    Step 6

    DaoAuthenticationProvider.authenticate()

    ===========================================================

    Step 7

    UserDetailsService.loadUserByUsername()

    ===========================================================

    Step 8

    UserDetails loaded.

    ===========================================================

    Step 9

    PasswordEncoder.matches()

    ===========================================================

    Step 10

    Success Authentication Object created.

    authenticated=true

    ===========================================================

    Step 11

    Return to ProviderManager.

    ===========================================================

    Step 12

    Return to AuthenticationManager.

    ===========================================================

    Step 13

    Store in SecurityContext.

    ===========================================================
                COMPLETE INTERNAL FLOW
    ===========================================================

    Browser

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

    AuthenticationProvider

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

    PasswordEncoder

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

    DispatcherServlet

       |

       v

    Controller

    ===========================================================
                AUTHENTICATIONMANAGER
                VS
                AUTHENTICATIONPROVIDER
    ===========================================================

    AuthenticationManager

    ------------------------------------------------

    Interface

    ------------------------------------------------

    Coordinator

    ------------------------------------------------

    Delegates Work

    ------------------------------------------------

    Entry Point

    ------------------------------------------------

    Default Implementation

    ProviderManager

    ===========================================================

    AuthenticationProvider

    ------------------------------------------------

    Interface

    ------------------------------------------------

    Actual Authentication

    ------------------------------------------------

    Verifies User

    ------------------------------------------------

    Supports Specific Token

    ------------------------------------------------

    Examples

    DaoAuthenticationProvider

    JWT Provider

    OAuth Provider

    ===========================================================
                THEORY
    ===========================================================

    Spring Security follows:

    Strategy Pattern

    ===========================================================

    AuthenticationManager

        defines contract

    ===========================================================

    ProviderManager

        selects strategy

    ===========================================================

    AuthenticationProvider

        executes strategy

    ===========================================================

    This design allows Spring Security
    to support multiple authentication
    mechanisms without changing framework code.

    ===========================================================

    AuthenticationManager answers:

    "Who should authenticate?"

    ===========================================================

    AuthenticationProvider answers:

    "I will authenticate."

    ===========================================================

    ProviderManager acts like:

    Smart Dispatcher

    ===========================================================

    Request arrives

        |

        v

    ProviderManager

        |

        v

    Select Correct Provider

        |

        v

    Authenticate

    ===========================================================
                MOST ASKED INTERVIEW QUESTIONS
    ===========================================================

    Q1. What is AuthenticationManager?

    Interface used as entry point
    for authentication.

    ------------------------------------------------

    Q2. Does AuthenticationManager
        authenticate user?

    No.

    Delegates authentication.

    ------------------------------------------------

    Q3. Default implementation?

    ProviderManager

    ------------------------------------------------

    Q4. What is AuthenticationProvider?

    Actual component performing
    authentication.

    ------------------------------------------------

    Q5. Why multiple providers?

    Different authentication mechanisms.

    ------------------------------------------------

    Q6. What does supports() do?

    Determines whether provider can
    authenticate specific token type.

    ------------------------------------------------

    Q7. Most common provider?

    DaoAuthenticationProvider

    ------------------------------------------------

    Q8. Who calls UserDetailsService?

    DaoAuthenticationProvider

    ------------------------------------------------

    Q9. Who calls PasswordEncoder?

    DaoAuthenticationProvider

    ------------------------------------------------

    Q10. AuthenticationManager vs
         AuthenticationProvider?

    Manager = Coordinator

    Provider = Worker

    ===========================================================
                    MEMORY TRICK
    ===========================================================

    Authentication Filter

            ↓

    AuthenticationManager

            ↓

    ProviderManager

            ↓

    AuthenticationProvider

            ↓

    DaoAuthenticationProvider

            ↓

    UserDetailsService

            ↓

    PasswordEncoder

            ↓

    Authentication Success

            ↓

    SecurityContext

            ↓

    SecurityContextHolder

    ===========================================================
    */
}