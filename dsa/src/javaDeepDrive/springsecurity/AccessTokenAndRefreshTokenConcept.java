package javaDeepDrive.springsecurity;

public class AccessTokenAndRefreshTokenConcept {

    /*
    ===========================================================
                ACCESS TOKEN
                        +
                REFRESH TOKEN
    ===========================================================

    This is one of the most important
    production-level JWT topics.

    Most tutorials stop at:

    Login

        ↓

    JWT

        ↓

    Done

    ===========================================================

    Real production systems are much
    more complex.

    ===========================================================

    Because JWT expires.

    ===========================================================

    Question:

    What happens after expiration?

    Should user login every 15 minutes?

    ===========================================================

    No.

    ===========================================================

    Solution:

    Access Token

            +

    Refresh Token

    ===========================================================
                    BIG PICTURE
    ===========================================================

    Login

        |

        +------ Access Token

        |

        +------ Refresh Token

    ===========================================================

    Access Token

        Short Life

    ===========================================================

    Refresh Token

        Long Life

    ===========================================================

    Access Token expires.

    Refresh Token creates new Access Token.

    ===========================================================
                WHY ACCESS TOKEN EXPIRES?
    ===========================================================

    Security.

    ===========================================================

    Imagine:

    Hacker steals token.

    ===========================================================

    If token valid for:

    10 Years

    ===========================================================

    Hacker gets access for 10 Years.

    ===========================================================

    Dangerous.

    ===========================================================

    Therefore:

    Access Token should expire quickly.

    ===========================================================
                    COMMON VALUES
    ===========================================================

    Access Token

    5 Minutes

    15 Minutes

    30 Minutes

    1 Hour

    ===========================================================

    Most production systems:

    15-30 Minutes

    ===========================================================
                PROBLEM CREATED
    ===========================================================

    User using application.

    ===========================================================

    Token expires every 15 minutes.

    ===========================================================

    User forced to login repeatedly.

    ===========================================================

    Terrible user experience.

    ===========================================================

    Solution:

    Refresh Token.

    ===========================================================
                    REFRESH TOKEN
    ===========================================================

    Long-lived token.

    ===========================================================

    Purpose:

    Generate new Access Tokens.

    ===========================================================

    Common Expiry:

    7 Days

    30 Days

    60 Days

    90 Days

    ===========================================================
                    LOGIN FLOW
    ===========================================================

    User Login

        |

        v

    AuthenticationManager

        |

        v

    Authentication Success

        |

        v

    Generate Access Token

        |

        v

    Generate Refresh Token

        |

        v

    Return Both

    ===========================================================
                RESPONSE EXAMPLE
    ===========================================================

    {

        "accessToken":"xxxxx",

        "refreshToken":"yyyyy"

    }

    ===========================================================
                CLIENT STORAGE
    ===========================================================

    Mobile Apps

    ------------------------------------------------

    Access Token

    Secure Storage

    ------------------------------------------------

    Refresh Token

    Secure Storage

    ===========================================================

    Web Applications

    ------------------------------------------------

    HttpOnly Cookie

    Preferred

    ===========================================================

    Never Local Storage
    for sensitive applications.

    ===========================================================
                    REQUEST FLOW
    ===========================================================

    Request #1

       |

       v

    Access Token

       |

       v

    Success

    ===========================================================

    Request #2

       |

       v

    Access Token

       |

       v

    Success

    ===========================================================

    Request #3

       |

       v

    Access Token Expired

       |

       v

    401 Unauthorized

    ===========================================================
                REFRESH FLOW
    ===========================================================

    Access Token Expired

            |

            v

    Client Calls

    /refresh-token

            |

            v

    Refresh Token Sent

            |

            v

    Refresh Token Validation

            |

            v

    New Access Token

            |

            v

    Return To Client

    ===========================================================
                COMPLETE FLOW
    ===========================================================

    Login

       |

       +----- Access Token

       |

       +----- Refresh Token

    ===========================================================

    API Call

       |

       v

    Access Token

       |

       v

    Success

    ===========================================================

    Access Token Expired

       |

       v

    Refresh API

       |

       v

    Refresh Token

       |

       v

    New Access Token

       |

       v

    Continue Working

    ===========================================================
                IMPORTANT QUESTION
    ===========================================================

    Where should refresh token be stored?

    ===========================================================

    Small Projects

    Database

    ===========================================================

    High Traffic Systems

    Redis

    ===========================================================
                DATABASE APPROACH
    ===========================================================

    refresh_tokens

    ===========================================================

    id

    user_id

    token

    expiry_time

    revoked

    ===========================================================

    Easy

    Reliable

    ===========================================================
                REDIS APPROACH
    ===========================================================

    Refresh Token

            |

            v

    Redis

            |

            v

    Fast Lookup

    ===========================================================

    Common in microservices.

    ===========================================================
                HUGE SECURITY QUESTION
    ===========================================================

    Why store refresh token?

    Access token isn't stored.

    ===========================================================

    Because refresh token grants
    long-term access.

    ===========================================================

    We must be able to:

    Revoke it

    Delete it

    Track it

    ===========================================================
                TOKEN REVOCATION
    ===========================================================

    User clicks Logout.

    ===========================================================

    Access Token

    Cannot be removed.

    ===========================================================

    Why?

    Stateless.

    Already issued.

    ===========================================================

    Access Token remains valid
    until expiration.

    ===========================================================

    Refresh Token

    CAN be revoked.

    ===========================================================

    Delete from DB.

    Delete from Redis.

    ===========================================================

    User effectively logged out.

    ===========================================================
                REFRESH TOKEN ROTATION
    ===========================================================

    Advanced Security Technique.

    ===========================================================

    Old Refresh Token

         |

         v

    Generate New Refresh Token

         |

         v

    Invalidate Old Token

    ===========================================================

    Every refresh operation:

    New Refresh Token

    New Access Token

    ===========================================================

    Prevents token replay attacks.

    ===========================================================
                TOKEN THEFT SCENARIO
    ===========================================================

    Hacker steals refresh token.

    ===========================================================

    Without Rotation

    Token usable forever.

    ===========================================================

    With Rotation

    Token invalid after first use.

    ===========================================================

    Much safer.

    ===========================================================
                LOGOUT FLOW
    ===========================================================

    User Logout

       |

       v

    Delete Refresh Token

       |

       v

    Future Refresh Requests Fail

       |

       v

    User Must Login Again

    ===========================================================
                JWT BLACKLISTING
    ===========================================================

    Sometimes companies need:

    Immediate logout.

    ===========================================================

    JWT is stateless.

    Cannot remove token.

    ===========================================================

    Solution:

    Blacklist

    ===========================================================

    Token ID

        |

        v

    Redis

        |

        v

    Blocked

    ===========================================================

    Every request checks blacklist.

    ===========================================================

    Tradeoff:

    Lose some stateless benefits.

    ===========================================================
                COMPLETE INTERNAL FLOW
    ===========================================================

    Browser

       |

       v

    Login Request

       |

       v

    Tomcat

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

    PasswordEncoder

       |

       v

    Authentication Success

       |

       +--------------------+

       |                    |

       v                    v

    Access Token      Refresh Token

       |                    |

       |               Database/Redis
       |                    |
       +--------------------+

    ===========================================================

    API Request

       |

       v

    Access Token

       |

       v

    JwtAuthenticationFilter

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
                        THEORY
    ===========================================================

    Access Token and Refresh Token
    solve different problems.

    ===========================================================

    Access Token

    Problem Solved:

    Fast Stateless Authentication

    ===========================================================

    Refresh Token

    Problem Solved:

    Long-Term User Sessions

    ===========================================================

    Access Token should be:

    Short-lived

    Frequently renewed

    Not stored in database

    ===========================================================

    Refresh Token should be:

    Long-lived

    Stored securely

    Revocable

    Trackable

    ===========================================================

    Production Architecture

    ===========================================================

    Authentication Success

            ↓

    Access Token

            ↓

    API Calls

    ===========================================================

    Refresh Token

            ↓

    New Access Token Generation

    ===========================================================

    Together they provide:

    Security

    Scalability

    Better User Experience

    ===========================================================
            ACCESS TOKEN VS REFRESH TOKEN
    ===========================================================

    Access Token

    ------------------------------------------------

    Short Life

    ------------------------------------------------

    Sent Every Request

    ------------------------------------------------

    Authentication

    ------------------------------------------------

    Usually Not Stored

    ------------------------------------------------

    15-30 Minutes

    ===========================================================

    Refresh Token

    ------------------------------------------------

    Long Life

    ------------------------------------------------

    Used Occasionally

    ------------------------------------------------

    Token Renewal

    ------------------------------------------------

    Stored In DB/Redis

    ------------------------------------------------

    Days Or Months

    ===========================================================
                MOST ASKED INTERVIEW QUESTIONS
    ===========================================================

    Q1. Why Access Token expires quickly?

    Security.

    ------------------------------------------------

    Q2. Why Refresh Token needed?

    Generate new Access Tokens.

    ------------------------------------------------

    Q3. Which token used in every request?

    Access Token.

    ------------------------------------------------

    Q4. Which token stored in DB?

    Refresh Token.

    ------------------------------------------------

    Q5. What happens when Access Token expires?

    Refresh Token generates new one.

    ------------------------------------------------

    Q6. Why Refresh Token rotation?

    Prevent replay attacks.

    ------------------------------------------------

    Q7. Can JWT be revoked?

    Not easily.

    Usually via blacklist.

    ------------------------------------------------

    Q8. Logout in JWT architecture?

    Revoke refresh token.

    ------------------------------------------------

    Q9. DB vs Redis for Refresh Token?

    Redis faster.

    DB simpler.

    ------------------------------------------------

    Q10. Access Token vs Refresh Token?

    Access Token = Authentication

    Refresh Token = Renewal Mechanism

    ===========================================================
                    MEMORY TRICK
    ===========================================================

    Login

        ↓

    Authentication Success

        ↓

    Access Token

        ↓

    API Requests

        ↓

    Expired

        ↓

    Refresh Token

        ↓

    New Access Token

        ↓

    Continue Requests

    ===========================================================
    */
}