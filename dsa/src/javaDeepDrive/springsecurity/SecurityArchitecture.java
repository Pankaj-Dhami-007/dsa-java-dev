package javaDeepDrive.springsecurity;

public class SecurityArchitecture {

    /*
    ===========================================================
                SPRING SECURITY ARCHITECTURE
    ===========================================================

    Before learning JWT, Filters, UserDetailsService,
    AuthenticationManager etc., first understand the
    complete architecture.

    Most Spring Security interview questions directly or
    indirectly come from this architecture.

    ===========================================================
                    BIG PICTURE
    ===========================================================

                          Request

                             |

                             v

                  Security Filter Chain

                             |

                             v

                   Authentication Filter

                             |

                             v

                  Authentication Manager

                             |

                             v

                  Authentication Provider

                             |

                             v

                     UserDetailsService

                             |

                             v

                          Database

                             |

                             v

                     User Details Found

                             |

                             v

                     Password Validation

                             |

                             v

                      Authentication

                             |

                             v

                       SecurityContext

                             |

                             v

                         Controller

                             |

                             v

                          Response

    ===========================================================
                WHY THIS MUCH LAYERING?
    ===========================================================

    Question:

    Why not simply:

    Controller
        |
        v
    Database

    Why so many components?

    Answer:

    Spring Security follows:

    Separation Of Concerns

    Every component has one responsibility.

    This makes:

    1. Easy Maintenance
    2. Easy Testing
    3. Easy Customization
    4. Loose Coupling

    ===========================================================
                COMPLETE LOGIN FLOW
    ===========================================================

    Example:

    Username = pankaj

    Password = 12345

    User clicks Login

    Request:

    POST /login

    {
       "username":"pankaj",
       "password":"12345"
    }

    ===========================================================
                    STEP 1
    ===========================================================

    Request enters Spring Application.

    Before reaching Controller:

    Security Filter Chain intercepts request.

    Important:

    Every request first goes through filters.
    Controller is NOT reached immediately.

    Flow:

    Request

       |

       v

    Security Filter Chain

    ===========================================================
                    STEP 2
    ===========================================================

    Authentication Filter extracts credentials.

    Example:

    username = pankaj
    password = 12345

    Creates:

    UsernamePasswordAuthenticationToken

    Example:

    Authentication auth =
        new UsernamePasswordAuthenticationToken(
            username,
            password
        );

    This token is currently:

    NOT AUTHENTICATED

    ===========================================================
                    STEP 3
    ===========================================================

    AuthenticationManager receives token.
    AuthenticationManager does not verify credentials.

    It acts like a coordinator.

    Flow:

    Authentication Filter

        |

        v

    AuthenticationManager

    ===========================================================
                    STEP 4
    ===========================================================

    AuthenticationManager forwards request to:
    AuthenticationProvider

    Think:

    Manager = Team Lead
    Provider = Actual Worker

    ===========================================================
                    STEP 5
    ===========================================================

    AuthenticationProvider performs authentication.

    Question:

    How does provider know user details?

    Answer:
    UserDetailsService

    Flow:

    AuthenticationProvider

         |

         v

    UserDetailsService

    ===========================================================
                    STEP 6
    ===========================================================

    UserDetailsService loads user from database.

    Example:

    SELECT * FROM users
    WHERE username='pankaj'

    User found.

    Returns:

    UserDetails Object

    Example:

    Username

    Encoded Password

    Roles

    Account Status

    ===========================================================
                    STEP 7
    ===========================================================

    Password Verification

    Raw Password:

    12345

    Stored Password:

    $2a$10$XKJHSDJKHSDKJ...

    AuthenticationProvider uses:

    PasswordEncoder

    Example:

    passwordEncoder.matches(
        rawPassword,
        encodedPassword
    );

    If match:

    Authentication Successful

    Otherwise:

    Authentication Failed

    ===========================================================
                    STEP 8
    ===========================================================

    Authenticated Object Created

    Example:

    Authentication

        username
        authorities
        authenticated=true

    Now user is officially authenticated.

    ===========================================================
                    STEP 9
    ===========================================================

    Authentication stored inside:

    SecurityContext

    Example:

    SecurityContext

         |

         v

    Authentication

    ===========================================================
                    STEP 10
    ===========================================================

    SecurityContext stored inside:

    SecurityContextHolder

    Think:

    SecurityContextHolder

          contains

    SecurityContext

          contains

    Authentication

          contains

    Logged In User

    ===========================================================
                AFTER LOGIN SUCCESS
    ===========================================================

    Future requests can access:

    Logged In User

    Example:

    Authentication auth =
        SecurityContextHolder
            .getContext()
            .getAuthentication();

    ===========================================================
                COMPLETE COMPONENT RELATIONSHIP
    ===========================================================

    Request

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

    UserDetailsService

      |

      v

    Database

      |

      v

    PasswordEncoder

      |

      v

    Authentication

      |

      v

    SecurityContext

      |

      v

    SecurityContextHolder

      |

      v

    Controller

    ===========================================================
                    REAL WORLD EXAMPLE
    ===========================================================

    Airport Security

    Passenger

       |

       v

    Security Gate

       |

       v

    Identity Verification

       |

       v

    Officer Checks Passport

       |

       v

    Database Verification

       |

       v

    Access Granted

    Mapping:

    Passenger
        -> Request

    Security Gate
        -> SecurityFilterChain

    Officer
        -> AuthenticationProvider

    Passport Database
        -> UserDetailsService + DB

    Approval
        -> Authentication

    ===========================================================
                    STATEFUL ARCHITECTURE
    ===========================================================

    Traditional Session Based Login

    Login

       |

       v

    Session Created

       |

       v

    Session ID Stored

       |

       v

    Every Request Uses Session

    Server remembers user.

    Called:

    Stateful Authentication

    ===========================================================
                    STATELESS ARCHITECTURE
    ===========================================================

    JWT Based Login

    Login

       |

       v

    JWT Generated

       |

       v

    Client Stores Token

       |

       v

    Every Request Sends Token

       |

       v

    Token Validated

    Server does not store session.

    Called:

    Stateless Authentication

    ===========================================================
                COMMON INTERVIEW QUESTIONS
    ===========================================================

    Q1. Explain Spring Security Architecture.

    Q2. Explain complete authentication flow.

    Q3. Where does request go first?

    Answer:
    Security Filter Chain.

    Q4. What is AuthenticationManager?

    Answer:
    Coordinator that delegates authentication.

    Q5. What is AuthenticationProvider?

    Answer:
    Performs actual authentication.

    Q6. What is UserDetailsService?

    Answer:
    Loads user from data source.

    Q7. Why PasswordEncoder?

    Answer:
    Passwords are stored encrypted.

    Q8. What is SecurityContext?

    Answer:
    Stores authenticated user.

    Q9. What is SecurityContextHolder?

    Answer:
    Holds SecurityContext.

    Q10. Which component talks to database?

    Answer:
    UserDetailsService.

    ===========================================================
                    IMPORTANT MEMORY TRICK
    ===========================================================

    Request

      ↓

    Filter

      ↓

    Manager

      ↓

    Provider

      ↓

    UserDetailsService

      ↓

    Database

      ↓

    PasswordEncoder

      ↓

    Authentication

      ↓

    SecurityContext

      ↓

    SecurityContextHolder

      ↓

    Controller

    Remember this flow.

    This is the foundation of entire Spring Security.
    ===========================================================
    */
}