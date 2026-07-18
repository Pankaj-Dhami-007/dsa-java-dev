package javaDeepDrive.springsecurity;

public class JwtConceptAndJwtServiceConcept {

    /*
    ===========================================================
                    JWT
                        +
                    JWT SERVICE
    ===========================================================

    JWT = JSON Web Token

    One of the most used authentication
    mechanisms in modern applications.

    Spring Boot

    React

    Angular

    Flutter

    Microservices

    Almost everywhere JWT is used.

    ===========================================================
                    BIG QUESTION
    ===========================================================

    Traditional Authentication

    Login

       |

       v

    Session Created

       |

       v

    Session Stored In Server

       |

       v

    User Identified Using Session

    ===========================================================

    JWT Authentication

    Login

       |

       v

    JWT Generated

       |

       v

    Client Stores JWT

       |

       v

    Client Sends JWT

       |

       v

    User Identified Using Token

    ===========================================================

    No Session Required.

    ===========================================================
                WHAT PROBLEM JWT SOLVES?
    ===========================================================

    Imagine:

    10 Million Users

    ===========================================================

    Session Based Authentication

    Server must store:

    Session Data

    Security Context

    User Information

    ===========================================================

    Huge Memory Consumption

    ===========================================================

    JWT

    Server stores nothing.

    ===========================================================

    Authentication information travels
    inside token itself.

    ===========================================================
                    STATELESS
    ===========================================================

    JWT Authentication is:

    Stateless

    ===========================================================

    Server remembers nothing.

    Every request contains identity.

    ===========================================================

    Request #1

    JWT

    ===========================================================

    Request #2

    JWT

    ===========================================================

    Request #3

    JWT

    ===========================================================

    Server validates token every time.

    ===========================================================
                    JWT STRUCTURE
    ===========================================================

    JWT contains:

    Header

    Payload

    Signature

    ===========================================================

    Structure

    header.payload.signature

    ===========================================================

    Example

    eyJhbGciOiJIUzI1NiJ9

    .

    eyJzdWIiOiJwYW5rYWoifQ

    .

    xxxxxxxxxxxxxxxxxxx

    ===========================================================
                    HEADER
    ===========================================================

    Contains metadata.

    Example

    {

      "alg":"HS256",

      "typ":"JWT"

    }

    ===========================================================

    alg

        Algorithm

    ===========================================================

    typ

        Token Type

    ===========================================================
                    PAYLOAD
    ===========================================================

    Contains Claims.

    Example

    {

       "sub":"pankaj",

       "role":"ADMIN",

       "exp":123456

    }

    ===========================================================

    sub

        Subject

        Usually username

    ===========================================================

    role

        User Role

    ===========================================================

    exp

        Expiration Time

    ===========================================================
                    IMPORTANT
    ===========================================================

    Payload is NOT encrypted.

    ===========================================================

    Anyone can decode it.

    ===========================================================

    Never store:

    Password

    OTP

    Secret Information

    ===========================================================
                    SIGNATURE
    ===========================================================

    Most important part.

    ===========================================================

    Protects against token tampering.

    ===========================================================

    Signature Generated Using

    Header

    +

    Payload

    +

    Secret Key

    ===========================================================

    If payload changes:

    Signature becomes invalid.

    ===========================================================
                LOGIN FLOW
    ===========================================================

    User Login

      |

      v

    UsernamePasswordAuthenticationFilter

      |

      v

    AuthenticationManager

      |

      v

    DaoAuthenticationProvider

      |

      v

    Authentication Success

      |

      v

    JWT Service

      |

      v

    Generate Token

      |

      v

    Return To Client

    ===========================================================
                    JWT SERVICE
    ===========================================================

    Usually custom service.

    Example

    JwtService

    ===========================================================

    Responsibilities

    1. Generate Token

    2. Validate Token

    3. Extract Username

    4. Extract Claims

    5. Check Expiration

    ===========================================================
                    STRUCTURE
    ===========================================================

    public class JwtService {

        generateToken()

        validateToken()

        extractUsername()

        extractClaim()

    }

    ===========================================================
                TOKEN GENERATION
    ===========================================================

    Authentication Success

         |

         v

    UserDetails

         |

         v

    JwtService

         |

         v

    Build Claims

         |

         v

    Create JWT

         |

         v

    Sign JWT

         |

         v

    Return Token

    ===========================================================
                CLAIMS
    ===========================================================

    Claims = Data stored inside JWT.

    ===========================================================

    Standard Claims

    sub

    iss

    aud

    exp

    iat

    nbf

    jti

    ===========================================================
                MOST COMMON CLAIMS
    ===========================================================

    sub

    Username

    ===========================================================

    exp

    Expiration

    ===========================================================

    iat

    Issued At

    ===========================================================

    jti

    Token Id

    ===========================================================
                CUSTOM CLAIMS
    ===========================================================

    Example

    {

        "userId":1,

        "role":"ADMIN",

        "department":"IT"

    }

    ===========================================================
                    SECRET KEY
    ===========================================================

    Extremely Important.

    ===========================================================

    Secret Key used for:

    Signing JWT

    Validating JWT

    ===========================================================

    Example

    secret =
    "my-super-secret-key"

    ===========================================================

    Server must protect this key.

    ===========================================================

    If leaked:

    Anyone can generate valid JWTs.

    ===========================================================
                TOKEN VALIDATION FLOW
    ===========================================================

    Incoming Request

          |

          v

    JwtAuthenticationFilter

          |

          v

    JwtService

          |

          v

    Validate Signature

          |

          v

    Validate Expiration

          |

          v

    Extract Username

          |

          v

    Create Authentication

    ===========================================================
                COMPLETE REQUEST FLOW
    ===========================================================

    Browser

       |

       v

    Authorization Header

    Bearer JWT

       |

       v

    JwtAuthenticationFilter

       |

       v

    JwtService.validateToken()

       |

       v

    Extract Username

       |

       v

    UserDetailsService

       |

       v

    UserDetails

       |

       v

    Authentication

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
                ACCESS TOKEN
    ===========================================================

    Short-Lived Token

    ===========================================================

    Example

    15 Minutes

    30 Minutes

    1 Hour

    ===========================================================

    Used in every request.

    ===========================================================
                REFRESH TOKEN
    ===========================================================

    Long-Lived Token

    ===========================================================

    Example

    7 Days

    30 Days

    90 Days

    ===========================================================

    Used to generate new access token.

    ===========================================================
                FLOW
    ===========================================================

    Login

       |

       +---- Access Token

       |

       +---- Refresh Token

    ===========================================================

    Access Token Expired

       |

       v

    Refresh Token API

       |

       v

    New Access Token

    ===========================================================
                HMAC VS RSA
    ===========================================================

    Very Important Interview Topic

    ===========================================================

    HMAC

    Same Key

    Sign

    Validate

    ===========================================================

    Example

    HS256

    ===========================================================

    Simpler

    Most common

    ===========================================================
                RSA
    ===========================================================

    Private Key

        Sign

    ===========================================================

    Public Key

        Validate

    ===========================================================

    Example

    RS256

    ===========================================================

    Useful in Microservices.

    ===========================================================
                JWT VS SESSION
    ===========================================================

    SESSION

    Server Stores State

    ===========================================================

    JWT

    Client Carries State

    ===========================================================

    SESSION

    Requires Memory

    ===========================================================

    JWT

    No Session Storage

    ===========================================================

    SESSION

    Easy Logout

    ===========================================================

    JWT

    Logout More Complex

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

       v

    UserDetailsService

       |

       v

    PasswordEncoder

       |

       v

    Authentication Success

       |

       v

    JwtService

       |

       v

    Generate JWT

       |

       v

    Return JWT

       |

       v

    Client Stores JWT

       |

       v

    Next Request

       |

       v

    JwtAuthenticationFilter

       |

       v

    JwtService

       |

       v

    Validate JWT

       |

       v

    Authentication

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

    JWT is not authentication.

    JWT is not authorization.

    ===========================================================

    JWT is only a token format.

    ===========================================================

    Authentication happens through:

    AuthenticationManager

    AuthenticationProvider

    ===========================================================

    JWT is generated AFTER successful
    authentication.

    ===========================================================

    JWT is later used to recreate
    Authentication object.

    ===========================================================

    JWT Service acts as:

    Token Factory

    +
    Token Validator

    ===========================================================

    Authentication Flow

    ------------------------------------------------

    Username + Password

            ↓

    AuthenticationManager

            ↓

    Authentication Success

            ↓

    JWT Generation

    ===========================================================

    Request Flow

    ------------------------------------------------

    JWT

            ↓

    JWT Validation

            ↓

    Authentication Creation

            ↓

    SecurityContextHolder

    ===========================================================

    Therefore JWT is simply a mechanism
    to transport user identity between
    requests without using sessions.

    ===========================================================
                MOST ASKED INTERVIEW QUESTIONS
    ===========================================================

    Q1. What is JWT?

    JSON Web Token.

    ------------------------------------------------

    Q2. Is JWT encrypted?

    No.

    Signed.

    ------------------------------------------------

    Q3. JWT Structure?

    Header.Payload.Signature

    ------------------------------------------------

    Q4. What are Claims?

    Data stored inside JWT.

    ------------------------------------------------

    Q5. Can password be stored in JWT?

    Never.

    ------------------------------------------------

    Q6. What validates JWT?

    JwtService.

    ------------------------------------------------

    Q7. Why JWT stateless?

    No server-side session storage.

    ------------------------------------------------

    Q8. Access Token vs Refresh Token?

    Access Token short-lived.

    Refresh Token long-lived.

    ------------------------------------------------

    Q9. HMAC vs RSA?

    HMAC uses same key.

    RSA uses public/private keys.

    ------------------------------------------------

    Q10. Does JWT authenticate user?

    No.

    JWT helps recreate Authentication.

    ===========================================================
                    MEMORY TRICK
    ===========================================================

    Login

        ↓

    AuthenticationManager

        ↓

    Authentication Success

        ↓

    JwtService

        ↓

    Generate JWT

        ↓

    Client

        ↓

    Request

        ↓

    JwtAuthenticationFilter

        ↓

    Validate JWT

        ↓

    Authentication

        ↓

    SecurityContextHolder

        ↓

    AuthorizationFilter

        ↓

    Controller

    ===========================================================
    */
}