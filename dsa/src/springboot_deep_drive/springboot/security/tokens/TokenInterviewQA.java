package springboot_deep_drive.springboot.security.tokens;

/*
================================================================
  TOKENS - INTERVIEW QUESTIONS & ANSWERS
================================================================

================================================================
  Q1: What is a token in authentication?
================================================================

Answer:
  A token is a piece of data that proves a user
  has been authenticated. It replaces session IDs
  and cookies in modern (stateless) authentication.

  Instead of storing session on server,
  the token itself contains user identity.

================================================================
  Q2: What is Bearer token authentication?
================================================================

Answer:
  Bearer token is a type of token where
  "whoever bears (holds) the token gets access."

  Client sends:
    Authorization: Bearer <token>

  Server trusts:
    - If token is valid (verified signature)
    - If token is not expired
    -> User is who token says they are

  No additional proof needed (no PIN, no biometric).

================================================================
  Q3: Why do we need access token + refresh token?
================================================================

Answer:
  One token cannot solve both security AND UX.

  - Long-lived token: Convenient but dangerous if stolen
  - Short-lived token: Secure but user logs in every 15 min

  Solution = two tokens:
    Access Token  (15 min) -> Security (limited damage if stolen)
    Refresh Token (7 days) -> UX (no repeated login)

================================================================
  Q4: What is the flow of token-based authentication?
================================================================

Answer:
  1. Login:
     User sends credentials -> Server validates
     -> Server generates AT + RT
     -> Client stores AT in memory, RT in HttpOnly cookie

  2. API calls:
     Client sends AT in Authorization header
     -> Server verifies AT signature + expiry
     -> If valid, process request

  3. Token expired:
     Server returns 401 -> Client detects
     -> Client sends RT to /auth/refresh
     -> Server verifies RT in DB -> Creates new AT + RT (rotation)
     -> Client retries original request with new AT

  4. Logout:
     Client calls /auth/logout
     -> Server deletes RT from DB
     -> AT expires naturally in 15 min

================================================================
  Q5: What is token rotation and why is it important?
================================================================

Answer:
  Token rotation = Every time you refresh,
  you get a NEW refresh token and the OLD one is DELETED.

  Why important?
  - If RT is stolen, attacker can use it ONCE
  - After you refresh, old stolen RT is dead
  - Limits damage from token theft

  Without rotation:
  - Stolen RT works for 7-30 days (disaster)

  With rotation:
  - Stolen RT works only until victim's next refresh
  - Then it's invalidated

================================================================
  Q6: What is reuse detection in token rotation?
================================================================

Answer:
  Reuse detection = detecting when a stolen token
  is being used by an attacker.

  Scenario:
  1. You have RT_v1 (stored in DB)
  2. Attacker steals RT_v1
  3. You refresh -> Server creates RT_v2, DELETES RT_v1 from DB
  4. Attacker tries to use RT_v1 -> NOT FOUND in DB
  5. Server detects: "Someone used an already rotated token"
  6. Possible theft detected!

  Action on reuse detection:
  - Invalidate ALL tokens for that user
  - Force user to login again
  - Log security event for investigation

================================================================
  Q7: Where should tokens be stored on client side?
================================================================

Answer:
  +--------------------+---------------------------+---------------------------+
  | Storage            | Access Token              | Refresh Token             |
  +--------------------+---------------------------+---------------------------+
  | In-memory (JS var) | BEST (XSS safe)           | NOT possible (lost on     |
  |                    |                           |  page refresh)            |
  +--------------------+---------------------------+---------------------------+
  | HttpOnly cookie    | NOT recommended           | BEST (XSS safe,           |
  |                    | (CSRF risk, sent on all   |  sent only to /refresh)   |
  |                    |  requests)                |                           |
  +--------------------+---------------------------+---------------------------+
  | localStorage       | AVOID (XSS vulnerable)    | AVOID (XSS vulnerable)    |
  +--------------------+---------------------------+---------------------------+

  Best practice:
  - Access Token  -> In-memory (JavaScript variable)
  - Refresh Token -> HttpOnly secure cookie (path=/auth/refresh)

================================================================
  Q8: How do you handle logout with tokens?
================================================================

Answer:
  Since access tokens are stateless (not stored on server),
  you cannot invalidate them directly.

  Logout approach:
  1. Client calls POST /auth/logout
  2. Server deletes refresh token from DB
  3. Server clears HttpOnly cookie (set Max-Age=0)
  4. Client clears access token from memory
  5. Access token expires naturally in 15 min

  The user is effectively logged out because:
  - No refresh token = cannot get new access token
  - Old access token will expire within 15 min

  For immediate revocation:
  - Use token blacklist (store JTI in Redis)
  - Check blacklist on every request
  - OR use token version in user table

================================================================
  Q9: How do you handle multi-device login with tokens?
================================================================

Answer:
  Each device gets its own refresh token.

  DB Table: refresh_tokens
  +----------+------------+------------------+---------+
  | user_id  | device_id  | token_hash       | expires |
  +----------+------------+------------------+---------+
  | 123      | "iphone15" | a1b2c3...        | 7 days  |
  | 123      | "macbook"  | d4e5f6...        | 7 days  |
  | 123      | "android"  | g7h8i9...        | 7 days  |
  +----------+------------+------------------+---------+

  - Login from iPhone -> creates separate RT
  - Login from MacBook -> creates separate RT
  - Logout from iPhone -> deletes only iPhone's RT
  - Logout from all -> deletes ALL RTs for user

  Device ID comes from User-Agent header or custom header.

================================================================
  Q10: How does token refresh work exactly?
================================================================

Answer:
  Step by step:

  1. Client detects AT is expired (401 response)
  2. Client sends RT to POST /auth/refresh
     - RT sent in HttpOnly cookie or request body
     - Device ID sent in header
  3. Server receives RT
  4. Server hashes the RT
  5. Server looks up hash in DB
  6. Server validates:
     a) RT exists in DB? -> If not, reject
     b) RT is revoked? -> If yes, possible theft, reject
     c) RT is expired? -> If yes, reject
  7. Server DELETES old RT from DB (rotation)
  8. Server creates NEW access token (15 min)
  9. Server creates NEW refresh token (7 days)
  10. Server stores new RT hash in DB
  11. Server returns new AT + RT
  12. Client retries original request with new AT

================================================================
  Q11: What is the difference between
       opaque token and JWT token?
================================================================

Answer:
  +------------------+--------------------------+--------------------------+
  | Feature          | Opaque Token             | JWT                      |
  +------------------+--------------------------+--------------------------+
  | Format           | Random string            | header.payload.signature |
  +------------------+--------------------------+--------------------------+
  | Self-contained?  | No (just an ID)          | Yes (contains user data) |
  +------------------+--------------------------+--------------------------+
  | Server lookup    | Required (DB/Redis)      | Not required (stateless) |
  +------------------+--------------------------+--------------------------+
  | User info        | Stored on server         | Inside token             |
  +------------------+--------------------------+--------------------------+
  | Revocation       | Easy (delete from DB)    | Hard (need blacklist)    |
  +------------------+--------------------------+--------------------------+
  | Best for         | Refresh tokens           | Access tokens            |
  +------------------+--------------------------+--------------------------+

  Common approach:
  - Access Token = JWT (fast, no DB lookup)
  - Refresh Token = Opaque (stored in DB, revocable)

================================================================
  Q12: How do you prevent CSRF attacks with tokens?
================================================================

Answer:
  CSRF (Cross-Site Request Forgery) happens when
  cookies are automatically sent by the browser.

  Prevention strategies:

  1. Don't put AT in cookies:
     - Access token in Authorization header (not cookie)
     - Header is NOT automatically sent by browser

  2. For refresh token cookies:
     - Set SameSite=Strict
     - Set path=/auth/refresh (narrow scope)
     - Don't use cookie for any other endpoint

  3. Use custom header:
     - Require X-Requested-With header for sensitive ops
     - Browser doesn't allow custom headers in cross-origin

================================================================
  Q13: How do you prevent XSS attacks with tokens?
================================================================

Answer:
  XSS (Cross-Site Scripting) lets attacker inject JS
  that can read localStorage/sessionStorage.

  Prevention strategies:

  1. Store AT in memory (JS variable):
     - Lives only in current page context
     - Cannot be read by injected script from another page

  2. Store RT in HttpOnly cookie:
     - Not accessible by JavaScript at all
     - Even if XSS runs, it cannot read the cookie

  3. Implement CSP (Content Security Policy):
     - Restricts what scripts can run
     - Blocks inline scripts and external sources

  4. Sanitize all user inputs:
     - Prevent script injection at source

================================================================
  Q14: What is the difference between
       self-encoded token and JWT?
================================================================

Answer:
  Self-encoded token (simple):
  - Just encode userId + expiry in Base64
  - No signature, no verification
  - Anyone can create fake tokens
  - NOT secure, NEVER use

  JWT:
  - Has cryptographic signature
  - Cannot be forged without secret key
  - Standard format (header, payload, signature)
  - Widely supported by libraries

  Analogy:
  Self-encoded = Sticker with name written in pencil
  JWT = Government-issued ID with hologram

================================================================
  Q15: How do you handle token refresh for mobile apps?
================================================================

Answer:
  Mobile apps don't have cookies (traditionally).

  For mobile:
  - Store both tokens in secure device storage
    Android: EncryptedSharedPreferences
    iOS: Keychain

  - Send RT in request body (not cookie):
    POST /auth/refresh
    { "refreshToken": "eyJ..." }

  - Use PKCE (Proof Key for Code Exchange) for
    additional security

  - Use biometric lock to access stored tokens
    (fingerprint, face ID)

================================================================
  Q16: What is the Stateless vs Stateful token debate?
================================================================

Answer:
  Stateless token (JWT access token):
  - Server stores NOTHING
  - All info is inside token
  - PRO: Fast (no DB check), scalable
  - CON: Cannot revoke immediately

  Stateful token (opaque refresh token):
  - Server stores token in DB
  - Token is just a random ID
  - PRO: Can revoke anytime (delete from DB)
  - CON: DB lookup on every use

  Best practice:
  - Access token = Stateless (fast for API calls)
  - Refresh token = Stateful (revocable)

================================================================
  Q17: How do you implement token blacklist?
================================================================

Answer:
  Token blacklist stores invalid tokens so they
  cannot be used even if not expired.

  Implementation:
  1. On logout: Add token's JTI (JWT ID) to Redis
     with TTL = token's remaining expiry
  2. On every request: Check if token's JTI is in blacklist
  3. If found: Reject request (401)

  Why Redis?
  - Fast (in-memory)
  - Automatic expiry (TTL)
  - Distributed (all servers check same Redis)

  Code pattern (pseudo):
    if (redis.isBlacklisted(claims.getJti())) {
        throw new TokenBlacklistedException();
    }

================================================================
  Q18: What are the security best practices for tokens?
================================================================

Answer:
  1. SHORT AT EXPIRY: 15-30 min (limits stolen token damage)
  2. TOKEN ROTATION: New RT every refresh
  3. REUSE DETECTION: Detect stolen token usage
  4. DIFFERENT KEYS: Use different keys for AT and RT
  5. HTTPONLY COOKIE: Store RT in HttpOnly cookie
  6. SAME-SITE: Strict SameSite policy for cookies
  7. HTTPS ONLY: Never send tokens over HTTP
  8. CSP: Content Security Policy to prevent XSS
  9. DEVICE TRACKING: Track sessions per device
  10. AUDIT LOGGING: Log all auth events
  11. RATE LIMITING: Limit refresh attempts
  12. KEY ROTATION: Rotate signing keys periodically

================================================================
  Q19: How do you handle concurrent refresh requests?
================================================================

Answer:
  Problem: User has multiple tabs.
  All tabs detect token expiry at same time.
  All send refresh request -> Race condition.

  Issue:
  - Old RT gets used multiple times
  - Rotation detects "reuse" -> All tokens invalidated

  Solutions:

  1. Locking (synchronized):
     - Process refresh requests sequentially
     - Only first request succeeds
     - Others get new tokens from first request

  2. Idempotency key:
     - Client sends unique idempotency key
     - Server processes only first request
     - Returns same result for duplicate keys

  3. Short window tolerance:
     - Allow RT to be used within 2 seconds of rotation
     - Prevents accidental invalidation

  4. Client-side debounce:
     - Queue refresh requests
     - Only send one at a time

================================================================
  Q20: What is the ideal token expiry time?
================================================================

Answer:
  Access Token:
  - 15 minutes (standard)
  - 5-10 min for high-security apps (banking)
  - 30 min for low-security apps (reading content)

  Refresh Token:
  - 7 days (standard)
  - 1 day for high-security apps
  - 30 days for remember-me functionality
  - Can be extended on use (sliding session)

  Rule of thumb:
  - Shorter = more secure, worse UX
  - Longer = better UX, less secure
  - Balance based on your app's security needs

================================================================
  QUICK REVISION - One-Liner Answers
================================================================

  Q: What is Bearer token?
  A: Whoever holds the token gets access.

  Q: Why two tokens instead of one?
  A: Security (short AT) + UX (long RT).

  Q: Token rotation?
  A: New RT every refresh, old one deleted.

  Q: Reuse detection?
  A: Using already-rotated token = possible theft.

  Q: Where to store AT?
  A: In-memory (JavaScript variable).

  Q: Where to store RT?
  A: HttpOnly secure cookie.

  Q: How to logout?
  A: Delete RT from DB, clear cookie, AT expires.

  Q: Multi-device support?
  A: One RT per device, separate entries in DB.

  Q: AT stateless?
  A: Yes, server stores nothing. Fast but not revocable.

  Q: RT stateful?
  A: Yes, stored in DB. Slower but revocable.

  Q: CSRF prevention?
  A: Don't put AT in cookies. Use SameSite=Strict.

  Q: XSS prevention?
  A: AT in memory, RT in HttpOnly cookie.

  Q: Token blacklist?
  A: Redis store with JTI + TTL.

  Q: Best AT expiry?
  A: 15 minutes.

  Q: Best RT expiry?
  A: 7 days.

================================================================
*/

public class TokenInterviewQA {

}
