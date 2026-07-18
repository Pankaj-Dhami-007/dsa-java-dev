package springboot_deep_drive.springboot.security.jwt;

/*
================================================================
  JWT STRUCTURE - Header . Payload . Signature
================================================================

================================================================
  WHAT IS A JWT TOKEN?
================================================================

A JWT is a string with 3 parts separated by dots (.):

  XXXXXXX.YYYYYYY.ZZZZZZZ
  Header  .Payload .Signature

  Each part is Base64Url encoded JSON.

================================================================
  VISUAL STRUCTURE
================================================================

  +=======================+====+=======================+====+=======================+
  |       HEADER          | .  |       PAYLOAD          | .  |      SIGNATURE        |
  |                       |    |                        |    |                       |
  | {                     |    | {                      |    | HMACSHA256(           |
  |   "alg": "HS256",     |    |   "sub": "user123",    |    |   base64UrlEncode(    |
  |   "typ": "JWT"        |    |   "name": "John",      |    |     header) + "." +   |
  | }                     |    |   "iat": 1700000000,   |    |   base64UrlEncode(    |
  |                       |    |   "exp": 1700003600    |    |     payload),         |
  |                       |    | }                      |    |   secretKey           |
  |                       |    |                        |    | )                     |
  +=======================+====+=======================+====+=======================+
       |                          |                              |
       v                          v                              v
  eyJhbGciOiJIUzI1NiJ9    .   eyJzdWIiOiJ1c2VyMTIzIiwib    .   s5x1fC9GSt6jYz8Xx7
       (Base64Url)               ftIjoiSm9obiIsImlhdCI6M         R0Z3vN2bB4mM1pQ2wL
                                  TcwMDAwMDAwMCwiZXhwIjox         9kK6jH
                                  NzAwMDAzNjAwfQ

================================================================
  PART 1: HEADER
================================================================

The header contains METADATA about the token.

  {
    "alg": "HS256",     // Algorithm used to sign
    "typ": "JWT"         // Token type (always JWT)
  }

Fields:
  alg (algorithm):
    - HS256 (HMAC with SHA-256) -> symmetric (same key for sign + verify)
    - RS256 (RSA with SHA-256)  -> asymmetric (private sign, public verify)
    - ES256 (ECDSA)             -> elliptic curve

  typ (type):
    - Always "JWT"

  cty (content type) - optional:
    - Used when JWT contains nested JWT

  kid (key ID) - optional:
    - Which key was used to sign (for key rotation)

================================================================
  Base64Url Encoding (NOT Encryption)
================================================================

VERY IMPORTANT: Base64Url is NOT encryption.

  Base64Url = Readable format (just encoding)
  Encryption = Hidden (needs key to decrypt)

Anyone can DECODE a JWT's header and payload.
  That is why you NEVER put passwords or secrets in payload.

Example:
  Header (encoded):  eyJhbGciOiJIUzI1NiJ9
  Header (decoded):  {"alg":"HS256","typ":"JWT"}

You can decode it yourself at:
  1. Copy the header part
  2. Use any base64 decoder (even browser console)
  3. See the JSON data

Only the SIGNATURE is protected (requires secret key).

================================================================
  PART 2: PAYLOAD
================================================================

The payload contains the DATA (claims).

  {
    "sub": "user123",      // Subject (usually user ID)
    "name": "John Doe",    // Custom claim
    "role": "ADMIN",       // Custom claim
    "iat": 1700000000,     // Issued At (timestamp)
    "exp": 1700003600,     // Expiration (timestamp)
    "iss": "myapp.com",    // Issuer (who created token)
    "aud": "mobile-app"    // Audience (who should use it)
  }

================================================================
  TYPES OF CLAIMS
================================================================

1. REGISTERED CLAIMS (standard, recommended):
-----------------------------------------------
  Claim   | Full Name        | Purpose
  ---------+------------------+---------------------------
  iss     | Issuer           | Who created the token
  sub     | Subject          | Who the token is about (user ID)
  aud     | Audience         | Who should use this token
  exp     | Expiration       | Token expiry time (UNIX timestamp)
  nbf     | Not Before       | Token valid from this time
  iat     | Issued At        | When token was created
  jti     | JWT ID           | Unique identifier (prevent replay)

2. PUBLIC CLAIMS (custom but registered):
------------------------------------------
  Can be defined in IANA registry.
  Example: "name", "preferred_username"

3. PRIVATE CLAIMS (custom to your app):
------------------------------------------
  Any custom data you want to include.
  Example: "role", "department", "permissions"

================================================================
  PART 3: SIGNATURE
================================================================

The signature is used to VERIFY that the token
was NOT modified after creation.

How signature is created (signing):

  signature = HMACSHA256(
      base64UrlEncode(header)
      + "." +
      base64UrlEncode(payload),
      secretKey
  )

  Input: header + "." + payload + secret key
  Output: a unique hash (signature)

----------------------------------------------------------------
  VISUAL - How Signature is Created
----------------------------------------------------------------

  Header (encoded)         Payload (encoded)
  eyJhbGciOiJIUzI1NiJ9    eyJzdWIiOiJ1c2VyMTIzIiwibmFtZSI6IkpvaG4iLCJleHAiOjE3MDAwMDAwMDB9
         |                         |
         +-----------+-------------+
                     |
                     v
       eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMTIzIiwibmFtZSI6IkpvaG4iLCJleHAiOjE3MDAwMDAwMDB9
                     |
                     +------------------+
                     |                  |
                     v                  v
              HMACSHA256           Secret Key
              Algorithm            "mySecretKey123"
                     |
                     +------------------+
                     |
                     v
              Signature (unique hash)
              s5x1fC9GSt6jYz8Xx7R0Z3vN2bB4mM1pQ2wL9kK6jH

----------------------------------------------------------------
  HOW VERIFICATION WORKS
----------------------------------------------------------------

When server receives a JWT, it does:

  1. Split token by dots -> header, payload, signature

  2. Compute expected signature:
     expectedSig = HMACSHA256(header + "." + payload, secretKey)

  3. Compare expectedSig with received signature

  4. If they MATCH -> token is VALID (not tampered)
     If they DIFFER -> token is INVALID (tampered or wrong key)

----------------------------------------------------------------
  VISUAL - How Verification Works
----------------------------------------------------------------

  Received JWT:
  eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjMifQ.s5x1fC9GSt6jYz8X

         |
         | Split by "."
         v
  +-----------+  +------------------+  +-------------+
  | Header    |  | Payload          |  | Signature   |
  | (encoded) |  | (encoded)        |  | (received)  |
  +-----------+  +------------------+  +-------------+
       |               |
       +-------+-------+
               |
               v
    Compute new signature using SAME secret key

               v
  +-----------------------------+
  | Expected Signature          |
  | (computed by server)        |
  +-----------------------------+
               |
               | Compare
               v
  +-----------------------------+
  | Received Signature          |
  | (from JWT)                  |
  +-----------------------------+
               |
        +------+------+
        |             |
     MATCH         NO MATCH
        |             |
        v             v
  Token VALID    Token INVALID
  (allow         (reject request)
   request)

================================================================
  HS256 vs RS256 - ALGORITHM COMPARISON
================================================================

  +------------------+---------------------------+---------------------------+
  | Feature          | HS256 (HMAC)              | RS256 (RSA)               |
  +------------------+---------------------------+---------------------------+
  | Key type         | Symmetric (SAME key)      | Asymmetric (PUBLIC +      |
  |                  | sign + verify             | PRIVATE key)              |
  +------------------+---------------------------+---------------------------+
  | Signing key      | Secret key (shared)       | Private key (secret)      |
  +------------------+---------------------------+---------------------------+
  | Verification key | Same secret key           | Public key (can share)    |
  +------------------+---------------------------+---------------------------+
  | Security         | Key must be shared        | Private key never shared  |
  |                  | between services          | Only public key shared    |
  +------------------+---------------------------+---------------------------+
  | Performance      | Faster                    | Slower                    |
  +------------------+---------------------------+---------------------------+
  | Best for         | Monolith / single service | Microservices / 3rd party |
  +------------------+---------------------------+---------------------------+
  | Key rotation     | Difficult (update all)    | Easy (rotate private key) |
  +------------------+---------------------------+---------------------------+

================================================================
  ACCESS TOKEN vs REFRESH TOKEN
================================================================

Most JWT systems use TWO tokens:

================================================================
  ACCESS TOKEN
================================================================

  Purpose:  Authenticate API requests
  Lifetime: Short (15-30 minutes)
  Stored:   Client memory / localStorage
  Sent:     In Authorization header on every request

  If stolen: Limited damage (expires quickly)

----------------------------------------------------------------
  REFRESH TOKEN
================================================================

  Purpose:  Get NEW access token when it expires
  Lifetime: Long (7-30 days)
  Stored:   HttpOnly cookie (more secure)
  Sent:     Only to /refresh-token endpoint

  If stolen: Attacker can get new access tokens
             (but not useful if stored in HttpOnly cookie)

----------------------------------------------------------------
  VISUAL - Access + Refresh Token Flow
----------------------------------------------------------------

  LOGIN:
  +------+                           +--------+
  |      | POST /login               |        |
  | User | username + password ----->| Server |
  |      |                           |        |
  |      | <-- AccessToken (15 min)  |        |
  |      | <-- RefreshToken (7 days) |        |
  +------+                           +--------+

  API CALL (Access Token valid):
  +------+                           +--------+
  |      | GET /api/data             |        |
  | User | Authorization: Bearer AT  | Server |
  |      | ------------------------>| Verify |
  |      | <----- Response          | AT     |
  +------+                           +--------+

  TOKEN EXPIRED (Use Refresh Token):
  +------+                           +--------+
  |      | POST /refresh-token       |        |
  | User | Cookie: RefreshToken ---->| Verify |
  |      |                           | RT     |
  |      | <-- New AccessToken       |        |
  |      | <-- New RefreshToken      |        |
  +------+                           +--------+

  API CALL (New Access Token):
  +------+                           +--------+
  |      | GET /api/data             |        |
  | User | Authorization: Bearer AT  | Server |
  |      | ------------------------>| Verify |
  |      | <----- Response          | AT     |
  +------+                           +--------+

================================================================
  WHY TWO TOKENS?
================================================================

  1. Security:
     Access token lives short time.
     If stolen, attacker can use it for max 15 min.

  2. User Experience:
     User doesn't need to login repeatedly.
     Refresh token gets new access token automatically.

  3. Revocation:
     Refresh token can be revoked (stored in DB).
     Access token cannot be revoked (stateless).

================================================================
  JWT SIZE ESTIMATION
================================================================

  Header:    ~50 bytes (base64)
  Payload:   ~100-500 bytes (depends on claims)
  Signature: ~43 bytes (HS256) / ~344 bytes (RS256)

  Total JWT: ~200-900 bytes

  Too much data in payload -> token becomes large
  -> More bandwidth on every request

  Best practice: Keep payload MINIMAL (just user ID + role)

================================================================
*/

public class JwtStructureExplained {

}
