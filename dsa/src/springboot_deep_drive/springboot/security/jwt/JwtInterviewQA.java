package springboot_deep_drive.springboot.security.jwt;

/*
================================================================
  JWT - INTERVIEW QUESTIONS & ANSWERS
================================================================

================================================================
  Q1: What is JWT and why was it created?
================================================================

Answer:
  JWT (JSON Web Token) is a token format used for
  authentication. It was created to solve problems
  with session-based auth:

  - Session auth stores data on server (memory intensive)
  - Session auth doesn't scale well (sticky sessions needed)
  - Cookies don't work well with mobile apps

  JWT is self-contained (user data inside token)
  and stateless (server stores nothing).

================================================================
  Q2: What is the structure of a JWT?
================================================================

Answer:
  A JWT has 3 parts separated by dots:

  HEADER.PAYLOAD.SIGNATURE

  1. HEADER: Contains algorithm and token type
     {"alg": "HS256", "typ": "JWT"}

  2. PAYLOAD: Contains claims (user data)
     {"sub": "123", "name": "John", "exp": 1700000000}

  3. SIGNATURE: Verifies token integrity
     HMACSHA256(header + "." + payload, secretKey)

  Each part is Base64Url encoded (not encrypted).

================================================================
  Q3: Is JWT encrypted? Can someone read JWT data?
================================================================

Answer:
  NO, JWT is NOT encrypted by default.

  Header and Payload are only Base64Url ENCODED.
  Anyone can decode and read them.

  What is protected?
  Only the SIGNATURE prevents tampering.

  If someone modifies header or payload,
  signature verification will FAIL.

  If you need confidentiality, use JWE
  (JSON Web Encryption) which actually encrypts.

  Best practice: Never put sensitive data
  (passwords, SSN) in JWT payload.

================================================================
  Q4: How does JWT verification work?
================================================================

Answer:
  When server receives a JWT:

  1. Split token by "." -> 3 parts
  2. Take header + "." + payload
  3. Compute new signature using SAME secret key
  4. Compare computed signature with received signature

  If MATCH -> Token is valid (not tampered)
  If NO MATCH -> Token is invalid (reject request)

  Additionally check:
  - Expiration (exp) not passed
  - Issuer (iss) matches
  - Audience (aud) matches

================================================================
  Q5: What is the difference between HS256 and RS256?
================================================================

Answer:
  HS256 (HMAC-SHA256):
  - Symmetric: SAME key for signing and verification
  - Faster
  - Good for single service / monolith
  - Key must be shared between services

  RS256 (RSA-SHA256):
  - Asymmetric: PRIVATE key signs, PUBLIC key verifies
  - Slower
  - Good for microservices / third-party auth
  - Private key never shared (only public key)

  Choose HS256 for internal, RS256 for distributed.

================================================================
  Q6: What is Access Token vs Refresh Token?
================================================================

Answer:
  Access Token:
  - Used to authenticate API requests
  - Short lifetime (15-30 minutes)
  - Sent in Authorization header
  - Stateless (no DB check)

  Refresh Token:
  - Used to get new access token
  - Long lifetime (7-30 days)
  - Stored in HttpOnly cookie (secure)
  - Can be revoked (stored in DB)

  Together they balance security + user experience.

================================================================
  Q7: How do you handle JWT expiration?
================================================================

Answer:
  Step 1: Client makes API call with expired JWT
  Step 2: Server returns 401 Unauthorized
  Step 3: Client calls /auth/refresh with refresh token
  Step 4: Server validates refresh token
  Step 5: Server creates NEW access token + NEW refresh token
  Step 6: Client retries original API with new access token

  This is transparent to user (no login prompt).

================================================================
  Q8: How do you logout a user with JWT?
================================================================

Answer:
  Since JWT is stateless, you cannot invalidate it directly.
  Solutions:

  1. Blacklist (server-side):
     - Store invalidated JWT IDs in Redis/DB
     - Check blacklist on every request
     - Con: Extra DB call (loses stateless benefit)

  2. Short expiry + Refresh token revocation:
     - Access token expires in 15 min
     - Delete refresh token from DB on logout
     - User cannot get new access token after logout

  3. Token version in DB:
     - Store token version in user table
     - Include version in JWT
     - Increment version on logout
     - Old tokens become invalid

  Most common: Option 2 (short expiry + RT revocation)

================================================================
  Q9: What are the security risks with JWT?
================================================================

Answer:
  1. XSS Attacks:
     Token stored in localStorage can be stolen
     by malicious JavaScript.

     Solution: Store in HttpOnly cookie (not accessible by JS)

  2. Token theft:
     If someone steals access token, they can use it.

     Solution: Short expiry (15 min), use HTTPS

  3. No revocation:
     Access tokens cannot be revoked (stateless).

     Solution: Short expiry + blacklist + refresh token

  4. Weak secret key:
     If secret key is weak, attacker can forge tokens.

     Solution: Use strong key (256+ bits), store in env variable

  5. Algorithm confusion:
     Attacker changes alg from RS256 to HS256,
     uses public key as secret key.

     Solution: Always validate algorithm on server

================================================================
  Q10: What is JWT in the context of Spring Boot?
================================================================

Answer:
  In Spring Boot, JWT is implemented using:

  1. jjwt library (io.jsonwebtoken) for JWT operations
  2. Spring Security for authentication
  3. Custom JwtAuthenticationFilter (extends OncePerRequestFilter)
  4. SecurityFilterChain configuration

  Flow:
  1. AuthController handles login -> returns JWT
  2. JwtAuthenticationFilter intercepts every request
  3. Extracts JWT from Authorization header
  4. Verifies signature using secret key / public key
  5. Sets SecurityContextHolder with user details
  6. Request passes to controller if valid

================================================================
  Q11: What is token rotation?
================================================================

Answer:
  Token rotation means issuing a NEW refresh token
  EVERY time a refresh is performed.

  Old refresh token becomes invalid.

  Why?
  If someone steals your refresh token:
  - They can use it ONCE
  - After you refresh, their token is useless
  - Limits damage from token theft

  Implementation:
  - Store refresh token hash in DB
  - On refresh: generate new, delete old
  - Compare incoming with stored

================================================================
  Q12: Where should you store JWT on client side?
================================================================

Answer:

  Browser (Web App):
  - Access Token -> JavaScript variable (memory)
    + Safe from XSS
    - Lost on page refresh (redirect to login)
  - Refresh Token -> HttpOnly secure cookie
    + Not accessible by JS (XSS safe)
    + Sent automatically to /refresh endpoint

  Mobile App:
  - Both tokens in secure device storage
    Android: EncryptedSharedPreferences
    iOS: Keychain

  AVOID localStorage for access token
  (vulnerable to XSS attacks).

================================================================
  Q13: Can JWT contain sensitive data like passwords?
================================================================

Answer:
  NO. NEVER put sensitive data in JWT payload.

  Reason: JWT header and payload are Base64Url encoded,
  NOT encrypted. Anyone can decode them.

  Decode a JWT:
  1. Copy the payload part (2nd segment)
  2. Use atob() in browser console
  3. See the JSON data

  What is safe to include?
  - User ID (sub)
  - Role
  - Permissions (non-sensitive)
  - Token metadata (exp, iat, iss)

  Best practice: Keep payload minimal.

================================================================
  Q14: What is the difference between JWT and OAuth2?
================================================================

Answer:
  JWT is a TOKEN FORMAT.
  OAuth2 is an AUTHORIZATION FRAMEWORK.

  JWT:
  - Defines HOW the token looks (header.payload.signature)
  - Can be used WITH or WITHOUT OAuth2

  OAuth2:
  - Defines WHO can issue tokens
  - Defines token types (access, refresh)
  - Defines grant types (authorization code, client credentials)
  - Often uses JWT as the token FORMAT

  Analogy:
  JWT = Letter format (envelope, paper, stamp)
  OAuth2 = Postal service (who delivers, how, to whom)

================================================================
  Q15: How do you handle multiple devices with JWT?
================================================================

Answer:
  Store multiple refresh tokens per user (one per device):

  Database table: refresh_tokens
  +----------+------------+-----------+
  | user_id  | device_id  | token     |
  +----------+------------+-----------+
  | 123      | "iphone15" | hash1     |
  | 123      | "macbook"  | hash2     |
  | 123      | "android"  | hash3     |
  +----------+------------+-----------+

  - Each device gets its own refresh token
  - Logout from one device = delete that device's token only
  - Logout from all = delete all tokens for user

================================================================
  Q16: What happens if secret key is compromised?
================================================================

Answer:
  If JWT secret key is leaked:
  - Attacker can FORGE valid tokens for any user
  - Attacker can VERIFY their own tokens

  Immediate actions:
  1. Rotate the secret key (change it)
  2. All existing tokens become invalid
  3. All users must login again
  4. Investigate how key was leaked

  Prevention:
  - Store key in environment variable (NOT in code)
  - Use vault/secret management (AWS Secrets Manager, HashiCorp)
  - Rotate keys periodically
  - For RS256: if private key leaks, public key doesn't help
    attacker sign tokens (but they can verify)

================================================================
  Q17: What is the difference between
       JWT and Session ID in cookies?
================================================================

Answer:

  +------------------+----------------------------+----------------------------+
  | Feature          | Session ID (Cookie)        | JWT                        |
  +------------------+----------------------------+----------------------------+
  | Where is user    | Server memory/database     | Inside the token (self-    |
  | data stored?     |                            | contained)                 |
  +------------------+----------------------------+----------------------------+
  | Server memory    | High (stores all sessions) | None (stateless)           |
  | usage            |                            |                            |
  +------------------+----------------------------+----------------------------+
  | Scale across     | Need shared session store  | Works naturally            |
  | servers          | (Redis)                    | (no shared state)          |
  +------------------+----------------------------+----------------------------+
  | Mobile support   | Difficult (cookies)        | Easy (header-based)        |
  +------------------+----------------------------+----------------------------+
  | Logout           | Instant (delete session)   | Need blacklist / short exp |
  +------------------+----------------------------+----------------------------+
  | Security         | CSRF vulnerable            | XSS vulnerable             |
  +------------------+----------------------------+----------------------------+
  | Payload size     | Tiny (just session ID)     | Larger (contains data)     |
  +------------------+----------------------------+----------------------------+

================================================================
  Q18: What is the difference between
       authentication and authorization in JWT context?
================================================================

Answer:
  Authentication (authN) = Who are you?
  Authorization (authZ) = What can you do?

  In JWT context:

  Authentication:
  - Verifying JWT signature proves user is who they claim
  - sub claim identifies the user
  - Handled by JwtAuthenticationFilter

  Authorization:
  - After authentication, check user's role/permissions
  - role claim in JWT determines access
  - Handled by @PreAuthorize or SecurityFilterChain

  Example:
  JWT shows: { sub: "123", role: "USER" }
  Authentication -> "User 123 is real" (verified by signature)
  Authorization -> "User has USER role, can access /api/users
                    but NOT /api/admin"

================================================================
  Q19: How do you implement JWT in a
       microservices architecture?
================================================================

Answer:
  Option 1: Shared secret (HS256)
  - All services share same secret key
  - Any service can verify JWT
  - Simple but key must be securely shared

  Option 2: Public key (RS256)
  - Auth service signs with private key
  - All services verify with public key
  - More secure (private key only with auth service)
  - Public key distributed via endpoint or config

  Option 3: API Gateway verifies
  - Gateway validates JWT
  - Passes user info to downstream services
  - Services trust gateway (no JWT verification needed)
  - Clean but single point of failure

  Recommended: Option 2 (RS256 with public key)

================================================================
  Q20: What are the best practices for JWT?
================================================================

Answer:

  1. Short access token expiry (15 min)
  2. Use refresh tokens with rotation
  3. Store refresh token in HttpOnly cookie
  4. Keep access token in memory (not localStorage)
  5. Use strong secret key (256+ bits)
  6. Use RS256 for microservices
  7. Validate all claims (exp, iss, aud)
  8. Never store sensitive data in payload
  9. Always use HTTPS
  10. Key rotation periodically
  11. Validate algorithm (prevent alg confusion)
  12. Log all auth failures (monitor attacks)

================================================================
  QUICK REVISION - One-Liner Answers
================================================================

  Q: What is JWT?
  A: A self-contained token with header, payload, signature.

  Q: Why JWT over sessions?
  A: Stateless, scalable, works with mobile, no server storage.

  Q: 3 parts of JWT?
  A: Header (alg + type), Payload (claims), Signature (verify).

  Q: Is JWT encrypted?
  A: No, only base64 encoded. Signature prevents tampering.

  Q: HS256 vs RS256?
  A: HS256 = same key for sign+verify. RS256 = private+public key.

  Q: Access vs Refresh token?
  A: Access = short-lived for APIs. Refresh = long-lived to get new access.

  Q: How to logout?
  A: Delete refresh token from DB. Access token expires automatically.

  Q: Where to store tokens?
  A: Access in memory. Refresh in HttpOnly cookie.

  Q: Can JWT be revoked?
  A: Not directly. Use short expiry + blacklist + RT revocation.

  Q: Biggest JWT risk?
  A: XSS can steal tokens. Protect with HttpOnly cookies + CSP.

================================================================
*/

public class JwtInterviewQA {

}
