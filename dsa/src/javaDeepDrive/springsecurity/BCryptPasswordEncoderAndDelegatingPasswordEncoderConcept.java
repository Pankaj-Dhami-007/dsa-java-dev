package javaDeepDrive.springsecurity;

public class BCryptPasswordEncoderAndDelegatingPasswordEncoderConcept {

    /*
    ===========================================================
            BCRYPT PASSWORD ENCODER
                        +
            DELEGATING PASSWORD ENCODER
    ===========================================================

    This is one of the most important
    security topics.

    Most developers know:

    BCryptPasswordEncoder

    But don't know:

    Why BCrypt?

    How BCrypt works?

    What is Salt?

    What is Cost Factor?

    Why same password generates
    different hashes?

    What is DelegatingPasswordEncoder?

    Why Spring Security uses {bcrypt}?

    ===========================================================
                    BIG QUESTION
    ===========================================================

    Suppose user password:

    12345

    ===========================================================

    Should database store:

    12345

    ?

    ===========================================================

    NEVER

    ===========================================================

    If database leaks:

    Every user's password exposed.

    Massive security breach.

    ===========================================================
                    WRONG APPROACH
    ===========================================================

    Database

    ------------------------------------------------

    username = pankaj

    password = 12345

    ------------------------------------------------

    Hacker gets DB.

    Immediately sees password.

    ===========================================================
                    BETTER APPROACH
    ===========================================================

    Database

    ------------------------------------------------

    username = pankaj

    password =
    $2a$10$adshjkasdjkashdkj

    ------------------------------------------------

    Password hidden.

    ===========================================================
                    HASHING
    ===========================================================

    Password

         |

         v

    Hash Function

         |

         v

    Hash Value

    ===========================================================

    Example

    12345

        |

        v

    HASH

        |

        v

    xxxxxxxxxxxxxx

    ===========================================================
                    IMPORTANT
    ===========================================================

    Hashing

    !=

    Encryption

    ===========================================================

    Encryption

    Reversible

    ===========================================================

    Hashing

    One Way

    ===========================================================

    Cannot recover original password.

    ===========================================================
                    WHY BCRYPT?
    ===========================================================

    Old Algorithms

    MD5

    SHA1

    SHA256

    ===========================================================

    Problems

    Too Fast

    Easy To Brute Force

    Rainbow Table Attacks

    ===========================================================

    BCrypt designed specifically
    for passwords.

    ===========================================================
                    BCRYPT FEATURES
    ===========================================================

    1. Salt

    2. Slow Hashing

    3. Cost Factor

    4. Adaptive Security

    ===========================================================
                    SALT
    ===========================================================

    Salt = Random Value

    Added before hashing.

    ===========================================================

    Password

    12345

    ===========================================================

    Salt

    abc123

    ===========================================================

    Combined

    abc12312345

    ===========================================================

    Hash Generated

    ===========================================================
                    WHY SALT?
    ===========================================================

    Without Salt

    User A

    password = 12345

    ===========================================================

    User B

    password = 12345

    ===========================================================

    Same Hash

    Dangerous

    ===========================================================

    Attackers immediately know:

    Both users use same password.

    ===========================================================
                    WITH SALT
    ===========================================================

    User A

    Salt A

    Hash A

    ===========================================================

    User B

    Salt B

    Hash B

    ===========================================================

    Same password

    Different hashes

    ===========================================================
                    EXAMPLE
    ===========================================================

    password = 12345

    ===========================================================

    Hash #1

    $2a$10$abcde.....

    ===========================================================

    Hash #2

    $2a$10$xyzab.....

    ===========================================================

    Both valid.

    ===========================================================
                HUGE INTERVIEW QUESTION
    ===========================================================

    Why same password creates
    different hashes?

    ===========================================================

    Because BCrypt generates
    random salt every time.

    ===========================================================
                    COST FACTOR
    ===========================================================

    BCrypt is intentionally slow.

    ===========================================================

    Example

    new BCryptPasswordEncoder(10);

    ===========================================================

    10 = strength

    ===========================================================

    Higher strength

            |

            v

    More secure

            |

            v

    Slower hashing

    ===========================================================
                    HASH STRUCTURE
    ===========================================================

    Example

    $2a$10$abcdefghijklmnopqrstuv

    ===========================================================

    Breakdown

    $2a$

        BCrypt Version

    ===========================================================

    10

        Cost Factor

    ===========================================================

    Remaining

        Salt + Hash

    ===========================================================
                    REGISTRATION FLOW
    ===========================================================

    User Password

        12345

            |

            v

    BCryptPasswordEncoder

            |

            v

    Generate Salt

            |

            v

    Generate Hash

            |

            v

    Save To Database

    ===========================================================
                    LOGIN FLOW
    ===========================================================

    User Password

        12345

            |

            v

    Load Stored Hash

            |

            v

    BCrypt.matches()

            |

            v

    Extract Salt From Hash

            |

            v

    Recalculate Hash

            |

            v

    Compare

            |

            v

    true / false

    ===========================================================
                VERY IMPORTANT
    ===========================================================

    BCrypt never decrypts password.

    ===========================================================

    Instead:

    Recreates hash.

    Then compares.

    ===========================================================
                SPRING SECURITY CODE
    ===========================================================

    PasswordEncoder encoder =

        new BCryptPasswordEncoder();

    ===========================================================

    String hash =

        encoder.encode("12345");

    ===========================================================

    boolean valid =

        encoder.matches(
            "12345",
            hash
        );

    ===========================================================
            DELEGATING PASSWORD ENCODER
    ===========================================================

    Extremely important
    Spring Security interview topic.

    ===========================================================

    Question:

    What if application has:

    Old Users -> SHA256

    New Users -> BCrypt

    ?

    ===========================================================

    How can Spring know
    which algorithm to use?

    ===========================================================

    Solution:

    DelegatingPasswordEncoder

    ===========================================================
                    IDEA
    ===========================================================

    Store algorithm information
    with password.

    ===========================================================

    Example

    {bcrypt}$2a$10$xxxxx

    ===========================================================

    {sha256}xxxxx

    ===========================================================

    {noop}12345

    ===========================================================
                    PASSWORD FORMAT
    ===========================================================

    {id}encodedPassword

    ===========================================================

    Examples

    {bcrypt}$2a$10$xxxx

    {pbkdf2}xxxx

    {scrypt}xxxx

    {argon2}xxxx

    ===========================================================
                INTERNAL FLOW
    ===========================================================

    Login

       |

       v

    DelegatingPasswordEncoder

       |

       v

    Read Prefix

       |

       v

    {bcrypt}

       |

       v

    Select BCryptPasswordEncoder

       |

       v

    matches()

    ===========================================================
                PASSWORD MIGRATION
    ===========================================================

    Legacy Application

    ------------------------------------------------

    Old Users

    SHA256

    ------------------------------------------------

    New Users

    BCrypt

    ===========================================================

    DelegatingPasswordEncoder
    supports both.

    ===========================================================

    No forced password reset required.

    ===========================================================
                PASSWORDENCODERFACTORIES
    ===========================================================

    Spring Security provides:

    PasswordEncoderFactories

    ===========================================================

    Example

    PasswordEncoder encoder =

        PasswordEncoderFactories
                .createDelegatingPasswordEncoder();

    ===========================================================

    Default encoder:

    BCrypt

    ===========================================================
                SUPPORTED ENCODERS
    ===========================================================

    BCrypt

    PBKDF2

    SCrypt

    Argon2

    NoOp

    SHA256 (legacy)

    ===========================================================
                COMPLETE REQUEST FLOW
    ===========================================================

    Browser

       |

       v

    Tomcat

       |

       v

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

       |

       +----------------+

       |                |

       v                v

    UserDetails     PasswordEncoder

       |                |

       +-------+--------+

               |

               v

       DelegatingPasswordEncoder

               |

               v

       BCryptPasswordEncoder

               |

               v

       Password Match

               |

               v

       Authentication Success

               |

               v

       SecurityContext

               |

               v

       SecurityContextHolder

    ===========================================================
                    THEORY
    ===========================================================

    BCrypt exists because password
    security requires more than hashing.

    ===========================================================

    Requirements:

    Slow Hashing

    Salt

    Adaptive Cost

    Brute Force Resistance

    ===========================================================

    BCrypt satisfies all.

    ===========================================================

    DelegatingPasswordEncoder exists
    because algorithms evolve.

    ===========================================================

    Today

        BCrypt

    Tomorrow

        Argon2

    ===========================================================

    Spring Security should support both.

    ===========================================================

    Therefore:

    DelegatingPasswordEncoder
    acts as algorithm router.

    ===========================================================

    BCryptPasswordEncoder
    performs actual hashing.

    ===========================================================

    Relationship

    DelegatingPasswordEncoder

            |

            v

    BCryptPasswordEncoder

            |

            v

    Hash Verification

    ===========================================================
                MOST ASKED INTERVIEW QUESTIONS
    ===========================================================

    Q1. Why BCrypt?

    Password-specific hashing algorithm.

    ------------------------------------------------

    Q2. Why not MD5?

    Too fast and insecure.

    ------------------------------------------------

    Q3. What is Salt?

    Random value added before hashing.

    ------------------------------------------------

    Q4. Why same password generates
        different hashes?

    Random salt.

    ------------------------------------------------

    Q5. What is Cost Factor?

    Controls BCrypt complexity.

    ------------------------------------------------

    Q6. Does BCrypt decrypt passwords?

    No.

    Recalculates hash and compares.

    ------------------------------------------------

    Q7. What is DelegatingPasswordEncoder?

    Routes to correct encoder based
    on password prefix.

    ------------------------------------------------

    Q8. What is {bcrypt}?

    Encoder identifier.

    ------------------------------------------------

    Q9. Why DelegatingPasswordEncoder?

    Support multiple algorithms.

    ------------------------------------------------

    Q10. Default encoder in modern
         Spring Security?

    BCrypt.

    ===========================================================
                    MEMORY TRICK
    ===========================================================

    Raw Password

            ↓

    DelegatingPasswordEncoder

            ↓

    BCryptPasswordEncoder

            ↓

    Salt

            ↓

    Hash

            ↓

    Database

            ↓

    Login

            ↓

    matches()

            ↓

    Authentication Success

    ===========================================================
    */
}