package springboot_deep_drive.springboot.security.tokens;

/*
================================================================
  STATELESS vs STATEFUL TOKENS - Complete Guide
================================================================

================================================================
  WHAT DOES STATELESS MEAN?
================================================================

  Stateless means the server does NOT store any session
  or token information. All data needed to verify a
  request is contained within the request itself.

  "Server trusts the token, not its own memory."

================================================================
  WHAT DOES STATEFUL MEAN?
================================================================

  Stateful means the server stores token/session data
  in a database, cache, or memory. The token is just
  a reference (key) to look up the stored data.

  "Server trusts its own database, not the token."

================================================================
  COMPARISON TABLE
================================================================

  +-----------------------+-------------------------------+-------------------------------+
  | Feature               | Stateless (JWT AT)            | Stateful (Session / Opaque RT)|
  +-----------------------+-------------------------------+-------------------------------+
  | Where is data?        | Inside the token              | In server DB/cache            |
  +-----------------------+-------------------------------+-------------------------------+
  | Server storage        | NONE                          | Yes (DB, Redis, memory)       |
  +-----------------------+-------------------------------+-------------------------------+
  | DB lookup per req?    | NO                            | YES                           |
  +-----------------------+-------------------------------+-------------------------------+
  | Speed                 | FAST (no I/O)                 | SLOWER (DB/Redis call)        |
  +-----------------------+-------------------------------+-------------------------------+
  | Scalability           | Excellent (no shared state)   | Needs shared storage (Redis)  |
  +-----------------------+-------------------------------+-------------------------------+
  | Revocation            | NOT possible (unless          | Instant (delete from DB)      |
  |                       |  blacklisted)                 |                               |
  +-----------------------+-------------------------------+-------------------------------+
  | Payload size          | Larger (contains user data)   | Tiny (just a random ID)       |
  +-----------------------+-------------------------------+-------------------------------+
  | Security (theft)      | If stolen, usable until exp   | If stolen, usable until       |
  |                       |                               | revoked or expired            |
  +-----------------------+-------------------------------+-------------------------------+
  | Logout                | AT keeps working until exp    | Instant (delete session)      |
  +-----------------------+-------------------------------+-------------------------------+
  | Mobile support        | Easy (header-based)           | Easy (token in header/body)   |
  +-----------------------+-------------------------------+-------------------------------+
  | Complexity            | Simple (no storage setup)     | Needs DB/Redis setup          |
  +-----------------------+-------------------------------+-------------------------------+

================================================================
  REAL-WORLD ANALOGY
================================================================

  STATELESS = Cash (JWT Access Token)
  ------------------------------------
  - You carry value IN the token itself
  - No bank (server) needs to check your balance
  - If lost, anyone can use it (until it expires)
  - Fast transaction (no bank lookup)
  - Cannot "revoke" a $10 bill

  STATEFUL = Debit Card (Session / Opaque Token)
  ----------------------------------------------
  - Card is just a reference to your bank account
  - Bank (server) must verify balance on each use
  - If lost, you call bank and FREEZE the account
  - Slower transaction (bank lookup needed)
  - Can revoke instantly (cancel the card)

================================================================
  WHERE EACH IS USED IN TOKEN AUTH
================================================================

  MODERN TOKEN ARCHITECTURE (Best of Both):

  +------------------+      +------------------+
  |  ACCESS TOKEN    |      |  REFRESH TOKEN   |
  |  (Stateless JWT) |      |  (Stateful Opaque)|
  +------------------+      +------------------+
  | - No DB lookup   |      | - Stored in DB   |
  | - Fast API calls |      | - Can revoke     |
  | - Self-contained |      | - Rotation       |
  | - 15 min expiry  |      | - 7 day expiry   |
  +------------------+      +------------------+

  Why this mix?
  ==============
  - API calls are FAST (no DB check for AT)
  - Security is MAINTAINED (RT can be revoked)
  - User experience is GOOD (auto-refresh)
  - Scalability is EXCELLENT (no shared state for AT)

================================================================
  DEEP DIVE - STATELESS (JWT Access Token)
================================================================

  What happens on each API request:

    Client                          Server
      |                               |
      | GET /api/users                |
      | Authorization: Bearer eyJ...  |
      |------------------------------>|
      |                               |
      |                    1. Extract token from header
      |                    2. Decode header + payload
      |                    3. Verify signature using secret key
      |                    4. Check expiry (exp claim)
      |                    5. Extract user info (sub, role)
      |                    6. Set SecurityContext
      |                    7. NO DATABASE CALL
      |                               |
      | 200 OK with data              |
      |<------------------------------|

  Key point: Server does NOT check any database.
  Token itself proves user identity.

  PROS:
  - Fast (~1ms verification, no network I/O)
  - Scales horizontally (any server can verify)
  - No shared session store needed
  - Works offline (if public key cached)

  CONS:
  - Cannot revoke access token
  - Larger request size (token in every request)
  - Payload visible (base64 decoded)

================================================================
  DEEP DIVE - STATEFUL (Opaque Refresh Token)
================================================================

  What happens on each refresh request:

    Client                          Server                    Database
      |                               |                         |
      | POST /auth/refresh            |                         |
      | Cookie: refreshToken=abc123   |                         |
      |------------------------------>|                         |
      |                               |                         |
      |                    1. Extract RT from cookie            |
      |                    2. Hash the RT                       |
      |                    3. Lookup hash in DB                 |
      |                               |------------------------>|
      |                               |<------------------------|
      |                    4. Validate:                         |
      |                       - Exists in DB?                   |
      |                       - Not revoked?                    |
      |                       - Not expired?                    |
      |                    5. DELETE old RT (rotation)          |
      |                               |------------------------>|
      |                    6. Generate new AT + RT              |
      |                    7. Store new RT hash in DB           |
      |                               |------------------------>|
      |                               |                         |
      | 200 OK with new tokens        |                         |
      |<------------------------------|                         |

  Key point: Server MUST check database on every use.
  Token itself is just a random reference.

  PROS:
  - Can revoke instantly (delete from DB)
  - Token theft is detectable (reuse detection)
  - Small token size
  - No sensitive data in token

  CONS:
  - Slower (DB lookup on every use)
  - Needs shared DB/Redis for multi-server
  - More complex infrastructure
  - Single point of failure (DB)

================================================================
  WHEN TO USE WHAT?
================================================================

  USE STATELESS (JWT) when:
  ---------------------------
  - You need high performance (many API calls/sec)
  - You have many server instances (horizontal scaling)
  - You don't need instant revocation
  - You want simple architecture
  - Mobile apps (no cookie storage)

  Examples:
  - Microservices API gateway
  - Public APIs with many consumers
  - Serverless / lambda functions
  - IoT devices (limited storage)

  USE STATEFUL (Session/Opaque) when:
  ------------------------------------
  - You need instant revocation (security-critical)
  - You want to track user sessions
  - You need to limit concurrent logins
  - You need device management
  - Compliance requirements (audit trails)

  Examples:
  - Banking apps
  - Admin panels
  - Healthcare systems (HIPAA)
  - Enterprise SaaS (user management)

================================================================
  THE HYBRID APPROACH (RECOMMENDED)
================================================================

  +------------------------------------------------------------------+
  |  Access Token (Stateless JWT)  +  Refresh Token (Stateful)       |
  +------------------------------------------------------------------+

  Best of both worlds:

  Access Token (Stateless):
  - Used for API calls
  - No DB lookup (fast)
  - Short expiry (15 min)
  - Self-contained user info

  Refresh Token (Stateful):
  - Used only for refresh
  - Stored in DB
  - Long expiry (7 days)
  - Can be revoked
  - Token rotation

  STATELESS = Speed
  STATEFUL = Control
  BOTH = Speed + Control

================================================================
  IMPLEMENTATION PATTERNS
================================================================

  Pattern 1: Pure Stateless (JWT for everything)
  --------------------------------------------------
  - No refresh tokens
  - JWT lives 15-30 min
  - On expiry, user re-logs in
  - Simplest but worst UX

  Pattern 2: Pure Stateful (Opaque tokens for everything)
  -------------------------------------------------------
  - Every API call checks DB
  - Instant revocation
  - Slower but more secure
  - Used in legacy session-based apps

  Pattern 3: Hybrid (JWT AT + Opaque RT) [RECOMMENDED]
  -----------------------------------------------------
  - JWT for API calls (fast)
  - Opaque RT stored in DB (revocable)
  - Token rotation on refresh
  - Best balance of speed + security

  Pattern 4: Double Stateful (Both tokens in DB)
  --------------------------------------------------
  - Both AT and RT stored in DB
  - AT is a random string (not JWT)
  - Can revoke both instantly
  - Most secure but slowest
  - Used in highest security apps

================================================================
  SCALABILITY CONSIDERATIONS
================================================================

  Stateless (JWT):
  +-----------------------------+
  | Load Balancer                |
  +-----------------------------+
         |     |     |
    +----+ +----+ +----+
    |Svr1| |Svr2| |Svr3|  <- Each verifies JWT independently
    +----+ +----+ +----+     No shared state needed!
         |     |     |
    +----+----+----+----+
    |     Database       |  <- Only for RT storage
    +--------------------+

  - Any server can handle any request
  - Add/remove servers freely
  - No session affinity needed

  Stateful (Session/Opaque):
  +-----------------------------+
  | Load Balancer (sticky)       |
  +-----------------------------+
         |     |     |
    +----+ +----+ +----+
    |Svr1| |Svr2| |Svr3|
    +----+ +----+ +----+
         |     |     |
    +----+----+----+----+
    |     Redis Cache     |  <- Shared session store
    +--------------------+
         |     |     |
    +----+----+----+----+
    |     Database       |
    +--------------------+

  - Requires sticky sessions or shared cache
  - Redis becomes critical infrastructure
  - Cache miss = slow DB fallback

================================================================
  SECURITY COMPARISON
================================================================

  +------------------+--------------------------+--------------------------+
  | Attack           | Stateless (JWT AT)       | Stateful (Opaque)        |
  +------------------+--------------------------+--------------------------+
  | Token theft      | Used until expiry        | Used until revoked/      |
  |                  | (15 min max)             | expired (can revoke now) |
  +------------------+--------------------------+--------------------------+
  | Replay attack    | Same token works until   | Same token works until   |
  |                  | expiry                   | revoked                  |
  +------------------+--------------------------+--------------------------+
  | Forged token     | Requires secret key      | Random string cannot     |
  |                  | (cryptographic)          | be guessed               |
  +------------------+--------------------------+--------------------------+
  | Logout           | AT still works           | Instant revoke           |
  |                  | until expiry             |                          |
  +------------------+--------------------------+--------------------------+
  | Data leakage     | Payload is visible       | No data in token         |
  |                  | (base64 decode)          | (just random ID)         |
  +------------------+--------------------------+--------------------------+

================================================================
  SPRING BOOT IMPLEMENTATION - Stateless Filter
================================================================

  // JwtAuthenticationFilter - Stateless authentication
  // Every request is authenticated by verifying JWT
  // No session, no DB lookup

  public class JwtAuthenticationFilter
      extends OncePerRequestFilter {

      @Override
      protected void doFilterInternal(...) {
          // 1. Extract JWT from Authorization header
          String token = extractToken(request);

          // 2. Verify JWT (cryptographic, stateless)
          Claims claims = jwtService.verifyToken(token);

          // 3. Create auth object from token data
          UsernamePasswordAuthenticationToken auth =
              new UsernamePasswordAuthenticationToken(
                  claims.getSubject(), null, authorities
              );

          // 4. Set SecurityContext (no session stored)
          SecurityContextHolder.getContext()
              .setAuthentication(auth);

          chain.doFilter(request, response);
      }
  }

================================================================
  SPRING BOOT IMPLEMENTATION - Stateful Filter
================================================================

  // SessionAuthenticationFilter - Stateful authentication
  // Every request looks up session/token in DB

  public class SessionAuthenticationFilter
      extends OncePerRequestFilter {

      @Autowired
      private SessionRepository sessionRepo;

      @Override
      protected void doFilterInternal(...) {
          // 1. Extract session ID from cookie/header
          String sessionId = extractSessionId(request);

          // 2. Lookup session in DB (stateful)
          Session session =
              sessionRepo.findById(sessionId)
                  .orElse(null);

          if (session == null ||
              session.isExpired()) {
              response.setStatus(401);
              return;
          }

          // 3. Create auth from stored session data
          UsernamePasswordAuthenticationToken auth =
              new UsernamePasswordAuthenticationToken(
                  session.getUserId(), null,
                  session.getAuthorities()
              );

          SecurityContextHolder.getContext()
              .setAuthentication(auth);

          chain.doFilter(request, response);
      }
  }

================================================================
  REAL-WORLD EXAMPLES
================================================================

  STATELESS (JWT):
  ----------------
  - Google APIs (OAuth2 access tokens)
  - Auth0 tokens
  - GitHub API tokens
  - Firebase Authentication
  - AWS Cognito tokens

  STATEFUL (Session/Opaque):
  -------------------------
  - Express.js sessions (server memory)
  - Django sessions (database)
  - ASP.NET session state
  - Banking apps (opaque tokens in DB)
  - Enterprise SSO systems

  HYBRID (JWT AT + Stateful RT):
  -------------------------------
  - Modern Spring Boot apps
  - NestJS with JWT + Redis
  - Keycloak (JWT AT + offline tokens)
  - Okta (JWT AT + refresh tokens)

================================================================
  KEY TAKEAWAY
================================================================

  Stateless (JWT):
  + Fast, scalable, simple
  - Cannot revoke, visible payload

  Stateful (Opaque):
  + Revocable, secure, small token
  - Slower, needs shared storage

  Hybrid (Recommended):
  + Fast API calls (stateless AT)
  + Revocable sessions (stateful RT)
  + Best of both worlds

  "Use stateless for speed,
   use stateful for control,
   use both for the win."

================================================================
  INTERVIEW QUICK ANSWERS
================================================================

  Q: What is the main difference?
  A: Stateless = data in token. Stateful = data in DB.

  Q: Which is faster?
  A: Stateless (no DB lookup).

  Q: Which is more secure?
  A: Stateful (can revoke instantly).

  Q: Can JWT be revoked?
  A: No, not without blacklist (which makes it stateful).

  Q: Why use both?
  A: Speed of stateless + security of stateful.

  Q: What makes JWT stateless?
  A: Server doesn't store it. Everything is in the token.

  Q: What makes session stateful?
  A: Server stores session data. Cookie is just a key.

  Q: How to revoke JWT?
  A: You can't. Use blacklist (Redis) or short expiry.

  Q: Which scales better?
  A: Stateless (no shared state needed).

  Q: Which is more complex?
  A: Stateful (needs DB/Redis setup).

================================================================
*/

public class StatelessVsStateFul {

}

