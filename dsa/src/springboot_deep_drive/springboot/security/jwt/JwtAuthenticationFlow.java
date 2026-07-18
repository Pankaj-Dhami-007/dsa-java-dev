package springboot_deep_drive.springboot.security.jwt;

/*
================================================================
  JWT AUTHENTICATION FLOW - Complete Step by Step
================================================================

================================================================
  OVERVIEW OF THE FLOW
================================================================

There are 3 main phases in JWT authentication:

  Phase 1: LOGIN
           User sends credentials -> Server creates JWT

  Phase 2: API REQUEST
           Client sends JWT -> Server verifies -> Response

  Phase 3: TOKEN REFRESH
           Access token expired -> Use refresh token ->
           Get new access token

================================================================
  PHASE 1: LOGIN FLOW (How JWT is created)
================================================================

----------------------------------------------------------------
  STEP-BY-STEP
----------------------------------------------------------------

  +--------+                              +--------+     +----------+
  | CLIENT |                              | SERVER |     | DATABASE |
  |(Browser|                              |        |     |          |
  | /Mobile|                              |        |     |          |
  |  App)  |                              |        |     |          |
  +--------+                              +--------+     +----------+
      |                                       |              |
      |  1. POST /auth/login                  |              |
      |  { "username": "john",               |              |
      |    "password": "pass123" }            |              |
      |-------------------------------------->|              |
      |                                       |              |
      |                                       |  2. Find user|
      |                                       |  by username |
      |                                       |------------->|
      |                                       |              |
      |                                       |  3. User data|
      |                                       |  (or null)   |
      |                                       |<-------------|
      |                                       |              |
      |                                       |  4. Check    |
      |                                       |  password    |
      |                                       |  (BCrypt)    |
      |                                       |              |
      |                                       |  5. Create   |
      |                                       |  JWT payload |
      |                                       |  {           |
      |                                       |    sub: userId|
      |                                       |    role: user |
      |                                       |    iat: now  |
      |                                       |    exp: now+ |
      |                                       |     15min    |
      |                                       |  }           |
      |                                       |              |
      |                                       |  6. Sign JWT |
      |                                       |  using secret|
      |                                       |  key         |
      |                                       |              |
      | 7. Response                           |              |
      | {                                     |              |
      |   "accessToken": "eyJ...",            |              |
      |   "refreshToken": "eyJ...",           |              |
      |   "expiresIn": 900                    |              |
      | }                                     |              |
      |<--------------------------------------|              |
      |                                       |              |
      |  8. Store token                       |              |
      |  (localStorage /                      |              |
      |   memory)                             |              |
      |                                       |              |

================================================================
  DETAILED: Step 5 - Creating the JWT Payload
================================================================

  Date now = new Date();
  Date exp = new Date(now.getTime() + 15 * 60 * 1000);
                // 15 minutes from now

  String jwt = Jwts.builder()
      .subject(user.getId())              // sub
      .claim("role", user.getRole())      // custom claim
      .issuedAt(now)                      // iat
      .expiration(exp)                    // exp
      .issuer("myapp.com")                // iss
      .signWith(getSigningKey())          // HS256 or RS256
      .compact();

  Result (encoded JWT):
  eyJhbGciOiJIUzI1NiJ9.
  eyJzdWIiOiIxMjMiLCJyb2xlIjoiQURNSU4iLCJpYXQiOjE3MDAwMDAwMDAsImV4cCI6MTcwMDAwMDkwMH0.
  abc123signature...

================================================================
  DETAILED: Step 6 - Signing the JWT
================================================================

  Using HS256 (HMAC-SHA256):

    byte[] secretKey = "my-very-secret-key-at-least-256-bits".getBytes();

    String jwt = Jwts.builder()
        ...
        .signWith(new SecretKeySpec(
            secretKey,
            SignatureAlgorithm.HS256.getJcaName()
        ))
        .compact();

  Using RS256 (RSA):

    PrivateKey privateKey = loadPrivateKey();  // from file
    String jwt = Jwts.builder()
        ...
        .signWith(privateKey)  // uses RS256 by default
        .compact();

================================================================
  PHASE 2: API REQUEST FLOW (How JWT is verified)
================================================================

----------------------------------------------------------------
  STEP-BY-STEP
----------------------------------------------------------------

  +--------+                              +--------+     +----------+
  | CLIENT |                              | SERVER |     | DATABASE |
  +--------+                              +--------+     +----------+
      |                                       |              |
      |  1. GET /api/users                    |              |
      |  Authorization: Bearer eyJhbG...      |              |
      |  (JWT in header)                      |              |
      |-------------------------------------->|              |
      |                                       |              |
      |                          2. Security Filter Chain    |
      |                          intercepts request          |
      |                                       |              |
      |                          3. Extract JWT from header  |
      |                          "Bearer eyJhbG..."         |
      |                                       |              |
      |                          4. Split token by "."       |
      |                          header = part[0]            |
      |                          payload = part[1]           |
      |                          signature = part[2]         |
      |                                       |              |
      |                          5. Verify signature         |
      |                          expectedSig = HMACSHA256(   |
      |                            header + "." + payload,   |
      |                            secretKey)                |
      |                          if expectedSig != signature |
      |                            -> REJECT (401)          |
      |                                       |              |
      |                          6. Check expiration         |
      |                          decode payload             |
      |                          if exp < now -> REJECT     |
      |                                       |              |
      |                          7. Extract user info       |
      |                          sub = userId               |
      |                          role = user role           |
      |                                       |              |
      |                          8. Set authentication      |
      |                          in SecurityContext          |
      |                                       |              |
      |                          9. Process request          |
      |                          (controller handles it)    |
      |                                       |              |
      | 10. Response                          |              |
      |  { "users": [...] }                   |              |
      |<--------------------------------------|              |
      |                                       |              |

================================================================
  Spring Boot Security Filter Chain (simplified)
================================================================

  Request comes in
       |
       v
  +---------------------------------------+
  | SecurityFilterChain                   |
  |                                       |
  |  1. JwtAuthenticationFilter           |
  |     (Custom filter)                   |
  |     - Extract JWT from header         |
  |     - Validate signature              |
  |     - Validate expiry                 |
  |     - Set SecurityContext             |
  |                                       |
  |  2. UsernamePasswordAuthFilter        |
  |     (For login endpoint)              |
  |                                       |
  |  3. ExceptionHandlerFilter            |
  |     (Handle auth errors)              |
  |                                       |
  |  4. Controller                        |
  |     (Actual API logic)                |
  +---------------------------------------+

================================================================
  CODE EXAMPLE: JWT Validation in Spring Boot
================================================================

  public class JwtAuthenticationFilter
      extends OncePerRequestFilter {

      @Override
      protected void doFilterInternal(
          HttpServletRequest request,
          HttpServletResponse response,
          FilterChain chain
      ) {
          // 1. Get token from header
          String authHeader =
              request.getHeader("Authorization");

          if (authHeader == null ||
              !authHeader.startsWith("Bearer ")) {
              chain.doFilter(request, response);
              return;
          }

          String token = authHeader.substring(7); // after "Bearer "

          try {
              // 2. Parse and verify JWT
              Claims claims = Jwts.parser()
                  .verifyWith(getSigningKey())
                  .build()
                  .parseSignedClaims(token)
                  .getPayload();

              // 3. Extract user info
              String userId = claims.getSubject();
              String role = claims.get("role", String.class);

              // 4. Check expiry
              Date expiry = claims.getExpiration();
              if (expiry.before(new Date())) {
                  throw new RuntimeException("Token expired");
              }

              // 5. Set authentication
              UsernamePasswordAuthenticationToken auth =
                  new UsernamePasswordAuthenticationToken(
                      userId, null, getAuthorities(role)
                  );

              SecurityContextHolder.getContext()
                  .setAuthentication(auth);

          } catch (Exception e) {
              // Token invalid -> clear security context
              SecurityContextHolder.clearContext();
          }

          chain.doFilter(request, response);
      }
  }

================================================================
  PHASE 3: TOKEN REFRESH FLOW
================================================================

----------------------------------------------------------------
  WHY REFRESH TOKEN?
----------------------------------------------------------------

  Access token has short expiry (15 min).
  User should NOT login every 15 minutes.
  Refresh token solves this.

----------------------------------------------------------------
  STEP-BY-STEP REFRESH FLOW
----------------------------------------------------------------

  +--------+                              +--------+     +----------+
  | CLIENT |                              | SERVER |     | DATABASE |
  +--------+                              +--------+     +----------+
      |                                       |              |
      |  1. Access token EXPIRED              |              |
      |  API returns 401                      |              |
      |                                       |              |
      |  2. POST /auth/refresh                |              |
      |  Cookie: refreshToken=eyJ...          |              |
      |  (or body: { refreshToken })          |              |
      |-------------------------------------->|              |
      |                                       |              |
      |                          3. Verify refresh token     |
      |                          (same JWT verification)    |
      |                                       |              |
      |                          4. Check if refresh token  |
      |                          is in DB (revocation list) |
      |                                       |              |
      |                          5. Create NEW access token  |
      |                          (same user info, new exp)  |
      |                                       |              |
      |                          6. Create NEW refresh token|
      |                          (rotation for security)    |
      |                                       |              |
      | 7. Response                           |              |
      | {                                      |              |
      |   "accessToken": "eyJ...",             |              |
      |   "refreshToken": "eyJ...",            |              |
      |   "expiresIn": 900                     |              |
      | }                                      |              |
      |<--------------------------------------|              |
      |                                       |              |
      |  8. Retry original API                |              |
      |  with new access token                |              |
      |                                       |              |

================================================================
  TOKEN ROTATION (Security Best Practice)
================================================================

  Each time refresh is called:
    - OLD refresh token is INVALIDATED
    - NEW refresh token is issued

  Why?
    If someone steals refresh token,
    they can only use it ONCE.
    After you use it, the stolen token becomes invalid.

----------------------------------------------------------------
  VISUAL - Token Rotation
----------------------------------------------------------------

  User logs in:
    Server stores: refreshToken_v1 in DB
    User gets:     refreshToken_v1

  User refreshes:
    Server checks: refreshToken_v1 matches DB
    Server creates: refreshToken_v2 (new)
    Server stores:  refreshToken_v2 in DB
    Server deletes: refreshToken_v1 from DB
    User gets:      refreshToken_v2

  If attacker had refreshToken_v1:
    It is now INVALID (deleted from DB)

================================================================
  COMPLETE JWT AUTH ARCHITECTURE DIAGRAM
================================================================

  +=================================================================+
  |                    COMPLETE JWT FLOW                            |
  +=================================================================+

  REGISTRATION:
  +------+   POST /auth/register   +--------+    +----------+
  |Client|  {username, password}   | Server |--->| Database |
  +------+ ----------------------> +--------+    | (store   |
                                    | Store user  |  user +  |
                                    | Hash pwd    |  hashed  |
                                    | (BCrypt)    |  pwd)    |
                                    +--------+    +----------+

  LOGIN:
  +------+   POST /auth/login      +--------+    +----------+
  |Client|  {username, password}   | Server |--->| Database |
  +------+ ----------------------> +--------+    | (verify  |
                                    | Verify pwd  |  user)   |
                                    | Create AT   +----------+
                                    | Create RT   +----------+
                                    | Store RT    | (store   |
                                    +--------+    |  RT in   |
         <--- AT + RT              | DB)     |
                                    +----------+

  API REQUEST (with valid AT):
  +------+   GET /api/resource     +--------+
  |Client|  Authorization: Bearer  | Server |
  +------+   <JWT AT>            +--------+
         ----------------------> | Verify |
                                 | Sign.   |
                                 | Check   |
                                 | Expiry  |
                                 +--------+
         <--- Response           |

  TOKEN REFRESH:
  +------+   POST /auth/refresh   +--------+    +----------+
  |Client|  {refreshToken}        | Server |--->| Database |
  +------+ ----------------------> +--------+    | (verify  |
                                    | Verify RT   |  RT)     |
                                    | Check DB    +----------+
                                    | Create new  +----------+
                                    | AT + RT     | (update  |
                                    +--------+    |  RT in   |
         <--- New AT + New RT      | DB)     |
                                    +----------+

  LOGOUT:
  +------+   POST /auth/logout     +--------+    +----------+
  |Client|  {refreshToken}         | Server |--->| Database |
  +------+ ----------------------> +--------+    | (delete  |
                                    | Delete RT   |  RT      |
                                    | from DB     |  from DB)|
                                    +--------+    +----------+

================================================================
  IMPORTANT: WHERE TO STORE TOKENS?
================================================================

  +------------------+--------------------------+-------------------------+
  | Storage method   | Access Token             | Refresh Token           |
  +------------------+--------------------------+-------------------------+
  | Browser:         | localStorage (XSS risk)  | HttpOnly cookie         |
  | In-memory        | Safer but lost on        | (more secure)           |
  | (variable)       | page refresh)            |                         |
  +------------------+--------------------------+-------------------------+
  | Mobile:          | Secure storage           | Secure storage          |
  | (Android/iOS)    | (EncryptedSharedPref)    | (Keychain/Keystore)     |
  +------------------+--------------------------+-------------------------+

  Best practice for web apps:
    - Access Token: In memory (JS variable)
    - Refresh Token: HttpOnly secure cookie (not accessible by JS)
    - This prevents XSS attacks from stealing tokens

================================================================
  COMMON JWT IMPLEMENTATION IN SPRING BOOT
================================================================

  1. Add dependency:

     <dependency>
         <groupId>io.jsonwebtoken</groupId>
         <artifactId>jjwt-api</artifactId>
         <version>0.12.5</version>
     </dependency>
     <dependency>
         <groupId>io.jsonwebtoken</groupId>
         <artifactId>jjwt-impl</artifactId>
         <version>0.12.5</version>
         <scope>runtime</scope>
     </dependency>
     <dependency>
         <groupId>io.jsonwebtoken</groupId>
         <artifactId>jjwt-jackson</artifactId>
         <version>0.12.5</version>
         <scope>runtime</scope>
     </dependency>

  2. Create JwtService (generate + validate)

  3. Create JwtAuthenticationFilter (extract + verify)

  4. Configure SecurityFilterChain

  5. Create AuthController (login, refresh, logout)

================================================================
  SIMPLE SUMMARY - JWT Flow in 5 Steps
================================================================

  1. LOGIN
     User sends username + password
     Server validates and creates JWT

  2. STORE
     Client saves JWT (access + refresh token)

  3. SEND
     Client sends JWT in Authorization header
     on every API request

  4. VERIFY
     Server validates signature + expiry
     If valid -> process request
     If invalid -> reject (401 Unauthorized)

  5. REFRESH
     When access token expires,
     use refresh token to get new one

================================================================
*/

public class JwtAuthenticationFlow {

}
