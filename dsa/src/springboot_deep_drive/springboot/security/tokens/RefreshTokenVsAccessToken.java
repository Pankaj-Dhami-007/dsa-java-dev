package springboot_deep_drive.springboot.security.tokens;

/*
================================================================
  REFRESH TOKEN vs ACCESS TOKEN - Complete Guide
================================================================

================================================================
  WHY DO WE NEED TWO TOKENS?
================================================================

Simple answer:
  One token cannot solve both security AND user experience.

  - If token lives long  -> stolen token usable for long time
  - If token lives short -> user logs in every 5 minutes

  Solution: Use TWO tokens with different lifetimes.

  Access Token  = short life (security)
  Refresh Token = long life  (convenience)

================================================================
  WHAT IS AN ACCESS TOKEN?
================================================================

Definition:
  A token that proves user identity for API requests.

Characteristics:
  - Sent on EVERY API request
  - Short lifetime (15-30 minutes)
  - Stateless (server does NOT store it)
  - Contains user info (sub, role, permissions)
  - Used by resource servers (APIs)

How it's sent:
  Authorization: Bearer <access_token>

================================================================
  WHAT IS A REFRESH TOKEN?
================================================================

Definition:
  A token used ONLY to get NEW access tokens.

Characteristics:
  - Sent ONLY to refresh endpoint
  - Long lifetime (7-30 days)
  - Stateful (server STORES it in DB)
  - Does NOT contain user info directly
  - Used by auth server only

How it's sent:
  POST /auth/refresh
  { "refreshToken": "eyJ..." }

  Or in HttpOnly cookie:
  Cookie: refreshToken=eyJ...

================================================================
  COMPARISON TABLE
================================================================

  +-----------------------+-------------------------------+-------------------------------+
  | Feature               | Access Token                  | Refresh Token                 |
  +-----------------------+-------------------------------+-------------------------------+
  | Purpose               | Authenticate API requests     | Get new access tokens         |
  +-----------------------+-------------------------------+-------------------------------+
  | Lifetime              | Short (15-30 min)             | Long (7-30 days)              |
  +-----------------------+-------------------------------+-------------------------------+
  | Sent to               | Every API endpoint            | Only /auth/refresh endpoint   |
  +-----------------------+-------------------------------+-------------------------------+
  | Server storage        | NOT stored (stateless)        | Stored in DB (stateful)       |
  +-----------------------+-------------------------------+-------------------------------+
  | Can be revoked        | NO (no server record)         | YES (delete from DB)          |
  +-----------------------+-------------------------------+-------------------------------+
  | Format                | JWT (with user data)          | JWT or opaque string          |
  +-----------------------+-------------------------------+-------------------------------+
  | Contains user data    | YES (sub, role, permissions)  | Usually just a random ID      |
  +-----------------------+-------------------------------+-------------------------------+
  | Risk if stolen        | High but limited time         | Very high (long-lived)        |
  +-----------------------+-------------------------------+-------------------------------+
  | Storage on client     | In-memory (JS variable)       | HttpOnly secure cookie        |
  +-----------------------+-------------------------------+-------------------------------+
  | Rotation              | Every time (new one issued)   | Each use creates new one      |
  +-----------------------+-------------------------------+-------------------------------+
  | Frequency of use      | Every API call                | Once every 15-30 min          |
  +-----------------------+-------------------------------+-------------------------------+
  | Transport             | Authorization header          | Cookie or request body        |
  +-----------------------+-------------------------------+-------------------------------+

================================================================
  WHY TWO TOKENS? - Detailed Explanation
================================================================

  +------------------------------------------------------------------+
  |  WHY ACCESS TOKEN IS SHORT-LIVED?                                |
  +------------------------------------------------------------------+

  Security:
  - If stolen, attacker can use it for MAX 15-30 min
  - After expiry, it becomes useless
  - You don't need to track it on server
  - No DB lookup for every API request (fast)

  +------------------------------------------------------------------+
  |  WHY REFRESH TOKEN IS LONG-LIVED?                                |
  +------------------------------------------------------------------+

  User Experience:
  - User doesn't login every 15 min
  - Token refresh happens automatically in background
  - User stays logged in for days without re-entering password

  +------------------------------------------------------------------+
  |  WHY NOT JUST USE A LONG-LIVED ACCESS TOKEN?                     |
  +------------------------------------------------------------------+

  If access token lives 7 days:
  - Stolen -> attacker has 7 days of access
  - Cannot be revoked (no server record)
  - Very dangerous

  +------------------------------------------------------------------+
  |  WHY NOT JUST REVOKE ACCESS TOKEN?                               |
  +------------------------------------------------------------------+

  Access token is STATELESS by design.
  Server does NOT store it.
  To revoke, you would need to:
  - Store it in DB (loses stateless benefit)
  - Check DB on every request (slow)

  That defeats the purpose of JWT.

================================================================
  WHAT HAPPENS INSIDE EACH TOKEN?
================================================================

Access Token (JWT format):
----------------------------

  HEADER:     {"alg": "HS256", "typ": "JWT"}
  PAYLOAD:    {
                "sub": "user123",        // Who is this user
                "role": "ADMIN",         // What can they do
                "permissions": ["READ", "WRITE"],
                "iat": 1700000000,       // When created
                "exp": 1700000900,       // When expires (15 min)
                "iss": "myapp.com",      // Who issued it
                "jti": "abc-123-def"     // Unique ID
              }
  SIGNATURE:  <signed with secret key>

Refresh Token (opaque or JWT):
--------------------------------

  Option A - Opaque (random string):
    "dGhpcyBpcyBhIHJlZnJlc2ggdG9rZW4uLi4="

    Server stores this in DB:
    +------------+----------------------------------+---------+
    | user_id    | refresh_token_hash               | expires |
    +------------+----------------------------------+---------+
    | user123    | a1b2c3d4e5f6...                  | 7 days  |
    +------------+----------------------------------+---------+

  Option B - JWT format:
    HEADER:     {"alg": "HS256", "typ": "JWT"}
    PAYLOAD:    {
                  "sub": "user123",
                  "type": "REFRESH",        // Different from access
                  "iat": 1700000000,
                  "exp": 1700604800,         // 7 days
                  "jti": "xyz-789-ghi",     // Unique ID
                  "tokenVersion": 3         // For revocation
                }
    SIGNATURE:  <signed with same/different key>

================================================================
  HOW THEY WORK TOGETHER - THE BIG PICTURE
================================================================

  TIME LINE:
  ====================================================================

  t=0:     User logs in
           -> Gets accessToken (valid till t+15min)
           -> Gets refreshToken (valid till t+7days)

  t=5min:  User calls API with accessToken
           -> Server verifies -> OK (not expired)

  t=14min: User calls API with accessToken
           -> Server verifies -> OK (still valid)

  t=16min: User calls API with accessToken
           -> Server returns 401 (expired)
           -> Client detects 401

  t=16min: Client calls /auth/refresh with refreshToken
           -> Server verifies refreshToken
           -> Server creates NEW accessToken (valid t+16 to t+31)
           -> Server creates NEW refreshToken (rotation)
           -> Client stores new tokens

  t=20min: User calls API with NEW accessToken
           -> Server verifies -> OK

  ... (cycle continues)

  t=7days: refreshToken expires
           -> User cannot refresh anymore
           -> User must login again

================================================================
  VISUAL - TOKEN LIFECYCLE
================================================================

  ACCESS TOKEN (short-lived):
  ================================================================
  >|---------|---------|---------|---------|---------|---------|
  0         15        30        45        60        75        90   minutes
  >.........|         >.........|         >.........|         
    Token 1    expires   Token 2    expires   Token 3    expires
    (given at    (refreshed at   (refreshed at
     login)      t=15)           t=30)

  REFRESH TOKEN (long-lived):
  ================================================================
  >|-------------------------------------------------------------|
  0                                                              7 days
  >............................................................|
    One refresh token (rotated on each use)
    Original issued at login
    Re-issued each time refresh is called

================================================================
  TOKEN STORAGE ON CLIENT - WHY DIFFERENT?
================================================================

  +---------------------+------------------------+-------------------------+
  | Where to store?     | Access Token           | Refresh Token           |
  +---------------------+------------------------+-------------------------+
  | In-memory (JS var)  | BEST choice            | NOT possible (lost on   |
  |                     | (XSS safe, but lost on |  page refresh)          |
  |                     |  page refresh)         |                         |
  +---------------------+------------------------+-------------------------+
  | localStorage        | XSS vulnerable         | XSS vulnerable          |
  |                     | (JS can read it)       | (JS can read it)        |
  +---------------------+------------------------+-------------------------+
  | sessionStorage      | XSS vulnerable         | XSS vulnerable          |
  |                     | (same as localStorage) |                         |
  +---------------------+------------------------+-------------------------+
  | HttpOnly cookie     | NOT recommended        | BEST choice             |
  |                     | (not accessible by JS, | (not accessible by JS,  |
  |                     |  but sent on ALL       |  sent only to /refresh) |
  |                     |  requests - CSRF risk) |                         |
  +---------------------+------------------------+-------------------------+

  Recommended approach:
  - Access Token  -> In-memory (JavaScript variable)
  - Refresh Token -> HttpOnly secure cookie (path=/auth/refresh)

================================================================
  SIMPLE ANALOGY
================================================================

  Think of it like a HOTEL KEY CARD:

  Access Token = ROOM KEY
  -------------
  - Opens your room door (accesses APIs)
  - Expires when you check out (short-lived)
  - If lost, someone can enter your room (limited time)
  - You get a new one at front desk every day

  Refresh Token = YOUR IDENTITY CARD
  --------------
  - You show it at front desk to get new room key
  - Valid for your entire stay (long-lived)
  - If lost, someone can get room keys (dangerous)
  - Kept safely in hotel safe (HttpOnly cookie)

  Without refresh token:
  - Your room key never expires
  - If you lose it, anyone can enter FOREVER

  Without access token:
  - You show your ID card to open room door
  - But ID card is more valuable and risky to use every time

================================================================
  SECURITY IMPLICATIONS
================================================================

  +------------------------------------------------------------------+
  |  WHAT IF ACCESS TOKEN IS STOLEN?                                 |
  +------------------------------------------------------------------+

  Impact: Attacker can access APIs for limited time (15-30 min)
  Mitigation: Short expiry limits damage

  +------------------------------------------------------------------+
  |  WHAT IF REFRESH TOKEN IS STOLEN?                                |
  +------------------------------------------------------------------+

  Impact: Attacker can generate new access tokens for 7-30 days
  Mitigation:
  - Use token rotation (old RT becomes invalid on use)
  - Store RT in HttpOnly cookie (JS can't read it)
  - User can revoke RT by logging out (delete from DB)

  +------------------------------------------------------------------+
  |  WHAT IF BOTH TOKENS ARE STOLEN?                                |
  +------------------------------------------------------------------+

  Impact: Attacker has full access until tokens expire
  Mitigation:
  - User logs out -> RT deleted from DB
  - Short AT expiry limits damage
  - Token versioning (increment on logout)

================================================================
  KEY TAKEAWAY
================================================================

  Access Token  = "I am user X, let me in RIGHT NOW"
  Refresh Token = "I am user X, give me a new pass"

  You NEED both because:
  1. Short access token = secure (limited damage if stolen)
  2. Long refresh token = convenient (no repeated logins)
  3. Refresh token is stored & tracked (can be revoked)
  4. Access token is stateless (fast, no DB lookup)

================================================================
*/

public class RefreshTokenVsAccessToken {

}
