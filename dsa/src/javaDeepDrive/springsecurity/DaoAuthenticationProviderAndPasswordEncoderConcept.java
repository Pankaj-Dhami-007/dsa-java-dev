package javaDeepDrive.springsecurity;

public class DaoAuthenticationProviderAndPasswordEncoderConcept {

    /*
    ===========================================================
            DAO AUTHENTICATION PROVIDER
                        +
                PASSWORD ENCODER
    ===========================================================

    These two topics are tightly connected.

    DaoAuthenticationProvider cannot work
    without PasswordEncoder.

    PasswordEncoder is useless without
    AuthenticationProvider.

    Therefore learn them together.

    ===========================================================
                    BIG PICTURE
    ===========================================================

    UsernamePasswordAuthenticationFilter

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

                +------------+

                |            |

                v            v

    UserDetailsService    PasswordEncoder

                |

                v

    UserDetails

                |

                v

    Authentication Success

    ===========================================================
                    BIG QUESTION
    ===========================================================

    User enters:

    username = pankaj

    password = 12345

    ===========================================================

    Spring loads:

    username = pankaj

    password =

    $2a$10$asdasdhsjdhasd

    ===========================================================

    Question:

    How does Spring compare:

    12345

    with

    $2a$10$asdasdhsjdhasd

    ?

    ===========================================================

    Answer:

    PasswordEncoder

    ===========================================================
                DAOAUTHENTICATIONPROVIDER
    ===========================================================

    Most common AuthenticationProvider.

    Used for:

    Username + Password Authentication

    ===========================================================

    DAO

    means

    Data Access Object

    ===========================================================

    Why DAO?

    Because provider loads user data
    from some datasource.

    Usually database.

    ===========================================================
                    RESPONSIBILITIES
    ===========================================================

    DaoAuthenticationProvider handles:

    1. Load User

    2. Validate Account Status

    3. Validate Password

    4. Create Authenticated User

    ===========================================================
                    INTERNAL FLOW
    ===========================================================

    DaoAuthenticationProvider

            |

            v

    UserDetailsService

            |

            v

    UserDetails

            |

            v

    Account Checks

            |

            v

    Password Validation

            |

            v

    Authenticated Token

    ===========================================================
                    STEP 1
                    LOGIN REQUEST
    ===========================================================

    User submits:

    username = pankaj

    password = 12345

    ===========================================================
                    STEP 2
    ===========================================================

    UsernamePasswordAuthenticationFilter

    creates:

    UsernamePasswordAuthenticationToken

    authenticated=false

    ===========================================================
                    STEP 3
    ===========================================================

    AuthenticationManager

            |

            v

    ProviderManager

            |

            v

    DaoAuthenticationProvider

    ===========================================================
                    STEP 4
    ===========================================================

    DaoAuthenticationProvider

    calls:

    loadUserByUsername()

    ===========================================================

    Example

    UserDetails user =

        userDetailsService
            .loadUserByUsername(
                "pankaj"
            );

    ===========================================================
                    STEP 5
    ===========================================================

    UserDetails returned.

    Example

    username = pankaj

    password =
    $2a$10$sdjkhaskjdh

    role = ADMIN

    ===========================================================
                    STEP 6
                    ACCOUNT CHECKS
    ===========================================================

    DaoAuthenticationProvider checks:

    isEnabled()

    isAccountNonLocked()

    isAccountNonExpired()

    isCredentialsNonExpired()

    ===========================================================

    Any failure

            |

            v

    AuthenticationException

    ===========================================================
                    STEP 7
                    PASSWORD CHECK
    ===========================================================

    Raw Password

    12345

    ===========================================================

    Encoded Password

    $2a$10$sdjkhaskjdh

    ===========================================================

    PasswordEncoder.matches()

    ===========================================================
                    IMPORTANT
    ===========================================================

    Spring NEVER does:

    rawPassword.equals(dbPassword)

    ===========================================================

    Wrong

    Because passwords are encrypted.

    ===========================================================
                    PASSWORDENCODER
    ===========================================================

    Interface

    ===========================================================

    public interface PasswordEncoder {

        String encode(
                CharSequence rawPassword
        );

        boolean matches(
                CharSequence rawPassword,

                String encodedPassword
        );
    }

    ===========================================================
                    TWO METHODS
    ===========================================================

    encode()

    Convert plain password
    into encoded password.

    ===========================================================

    matches()

    Verify password.

    ===========================================================
                PASSWORD CREATION FLOW
    ===========================================================

    User Registration

            |

            v

    password = 12345

            |

            v

    passwordEncoder.encode()

            |

            v

    $2a$10$hjdhjasdjk

            |

            v

    Database

    ===========================================================
                LOGIN FLOW
    ===========================================================

    User Login

            |

            v

    password = 12345

            |

            v

    passwordEncoder.matches()

            |

            v

    true / false

    ===========================================================
                    HUGE QUESTION
    ===========================================================

    Why not decrypt password and compare?

    ===========================================================

    Because passwords should NEVER be
    decryptable.

    ===========================================================

    Password hashing is one-way.

    ===========================================================

    Original Password

        |

        v

    Hash

        |

        v

    Cannot reverse.

    ===========================================================
                    BCRYPT
    ===========================================================

    Most commonly used PasswordEncoder.

    ===========================================================

    BCryptPasswordEncoder

    ===========================================================

    Example

    PasswordEncoder encoder =

        new BCryptPasswordEncoder();

    ===========================================================
                    GENERATED HASH
    ===========================================================

    password = 12345

    ===========================================================

    Hash Example

    $2a$10$u3hjshjksdhjkash

    ===========================================================
                    VERY IMPORTANT
    ===========================================================

    Same password.

    Different hashes.

    ===========================================================

    Example

    12345

        |

        v

    Hash 1

    $2a$10$AAAA

    ===========================================================

    12345

        |

        v

    Hash 2

    $2a$10$BBBB

    ===========================================================

    Both valid.

    ===========================================================
                    WHY?
    ===========================================================

    BCrypt uses:

    Salt

    ===========================================================

    Salt = Random value added before hashing.

    ===========================================================

    Protects against:

    Rainbow Table Attacks

    Precomputed Hash Attacks

    ===========================================================
                    INTERNAL MATCHES()
    ===========================================================

    PasswordEncoder.matches(

        rawPassword,

        encodedPassword

    );

    ===========================================================

    Simplified Logic

    Hash(rawPassword)

            |

            v

    Compare

            |

            v

    Stored Hash

    ===========================================================

    Match?

            |

            v

    true

    ===========================================================
                AUTHENTICATION SUCCESS
    ===========================================================

    Password Match

            |

            v

    Create New

    UsernamePasswordAuthenticationToken

            |

            v

    authenticated=true

            |

            v

    Return To ProviderManager

    ===========================================================
                INTERNAL SOURCE FLOW
    ===========================================================

    DaoAuthenticationProvider

        authenticate()

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

    preAuthenticationChecks()

              |

              v

    additionalAuthenticationChecks()

              |

              v

    PasswordEncoder.matches()

              |

              v

    createSuccessAuthentication()

              |

              v

    Authenticated Token

    ===========================================================
                COMPLETE REQUEST FLOW
    ===========================================================

    Browser

       |

       v

    Embedded Tomcat

       |

       v

    Thread

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

    AuthenticationManager

       |

       v

    ProviderManager

       |

       v

    DaoAuthenticationProvider

       |

       +--------------------+

       |                    |

       v                    v

    UserDetailsService   PasswordEncoder

       |                    |

       +---------+----------+

                 |

                 v

         Authentication Success

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
            DAOAUTHENTICATIONPROVIDER
                    VS
                USERDETAILSSERVICE
    ===========================================================

    UserDetailsService

    Loads User

    ===========================================================

    DaoAuthenticationProvider

    Authenticates User

    ===========================================================

    UserDetailsService

    DOES NOT

    Validate Password

    ===========================================================

    DaoAuthenticationProvider

    DOES

    Validate Password

    ===========================================================
                PASSWORDENCODER
                VS
                BCRYPT
    ===========================================================

    PasswordEncoder

    Interface

    ===========================================================

    BCryptPasswordEncoder

    Implementation

    ===========================================================

    Similar To

    List

        Interface

    ArrayList

        Implementation

    ===========================================================
                        THEORY
    ===========================================================

    Spring Security separates concerns.

    ===========================================================

    UserDetailsService

        Loads User

    ===========================================================

    PasswordEncoder

        Verifies Password

    ===========================================================

    DaoAuthenticationProvider

        Coordinates Authentication

    ===========================================================

    This design follows:

    Single Responsibility Principle

    ===========================================================

    Every component performs one job.

    ===========================================================

    DaoAuthenticationProvider acts like:

    Authentication Orchestrator

    ===========================================================

    UserDetailsService acts like:

    User Fetcher

    ===========================================================

    PasswordEncoder acts like:

    Password Validator

    ===========================================================

    Together they perform authentication.

    ===========================================================
                MOST ASKED INTERVIEW QUESTIONS
    ===========================================================

    Q1. What is DaoAuthenticationProvider?

    Default provider for
    username/password authentication.

    ------------------------------------------------

    Q2. Why DAO?

    Because it loads user from datasource.

    ------------------------------------------------

    Q3. Who calls UserDetailsService?

    DaoAuthenticationProvider.

    ------------------------------------------------

    Q4. Who validates password?

    DaoAuthenticationProvider
    using PasswordEncoder.

    ------------------------------------------------

    Q5. Does UserDetailsService
        validate password?

    No.

    ------------------------------------------------

    Q6. Why PasswordEncoder?

    Passwords stored as hashes.

    ------------------------------------------------

    Q7. Why BCrypt preferred?

    Uses salt and strong hashing.

    ------------------------------------------------

    Q8. Can same password generate
        different hashes?

    Yes.

    Due to salt.

    ------------------------------------------------

    Q9. encode() vs matches()?

    encode()

        Create Hash

    matches()

        Verify Password

    ------------------------------------------------

    Q10. What happens after password
         verification succeeds?

    Authenticated token created.

    ===========================================================
                    MEMORY TRICK
    ===========================================================

    UsernamePasswordAuthenticationFilter

                ↓

    AuthenticationManager

                ↓

    ProviderManager

                ↓

    DaoAuthenticationProvider

                ↓

    UserDetailsService

                ↓

    UserDetails

                ↓

    PasswordEncoder.matches()

                ↓

    Authenticated Token

                ↓

    SecurityContext

                ↓

    SecurityContextHolder

    ===========================================================
    */
}




/*

Tomcat
 ↓
DelegatingFilterProxy
 ↓
FilterChainProxy
 ↓
SecurityFilterChain
 ↓
UsernamePasswordAuthenticationFilter
 ↓
AuthenticationManager
 ↓
ProviderManager
 ↓
DaoAuthenticationProvider
 ↓
UserDetailsService
 ↓
PasswordEncoder
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
 */