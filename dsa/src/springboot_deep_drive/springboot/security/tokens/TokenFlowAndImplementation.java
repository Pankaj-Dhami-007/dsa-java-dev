package springboot_deep_drive.springboot.security.tokens;

/*
================================================================
  TOKEN FLOW & SPRING BOOT IMPLEMENTATION
================================================================

================================================================
  COMPLETE TOKEN FLOW - Login to Expiry to Refresh
================================================================

  +------+                           +--------+     +----------+
  | USER |                           | SERVER |     | DATABASE |
  |Client|                           |        |     |          |
  +------+                           +--------+     +----------+
    |                                    |              |
    |============ PHASE 1: LOGIN ========|              |
    |                                    |              |
    | POST /auth/login                   |              |
    | {username, password}               |              |
    |----------------------------------->|              |
    |                                    |              |
    |                          1. Validate credentials  |
    |                          2. Find user in DB       |
    |                                    |------------->|
    |                                    |<-------------|
    |                          3. Create Access Token   |
    |                             sub: userId           |
    |                             role: userRole        |
    |                             exp: now + 15min      |
    |                                    |              |
    |                          4. Create Refresh Token  |
    |                             jti: uniqueId         |
    |                             exp: now + 7days      |
    |                                    |              |
    |                          5. Store refresh token   |
    |                             hash in DB            |
    |                             (user_id, token_hash, |
    |                              device_id, expires)  |
    |                                    |------------->|
    |                                    |<-------------|
    |                                    |              |
    | 6. Response:                       |              |
    | {                                   |              |
    |   accessToken: "eyJ...",          |              |
    |   refreshToken: "eyJ...",         |              |
    |   expiresIn: 900                   |              |
    | }                                  |              |
    |<-----------------------------------|              |
    |                                    |              |
    | 7. Client stores:                  |              |
    |    AccessToken  -> in-memory       |              |
    |    RefreshToken -> HttpOnly cookie |              |
    |                                    |              |
    |========== PHASE 2: NORMAL API CALL ==========     |
    |                                    |              |
    | GET /api/users                     |              |
    | Authorization: Bearer eyJ...      |              |
    |----------------------------------->|              |
    |                                    |              |
    |                          8. Extract AT from       |
    |                             "Bearer eyJ..."       |
    |                                    |              |
    |                          9. Verify signature      |
    |                          10. Check expiry:        |
    |                              exp > now ?          |
    |                                    |              |
    |                          11. Extract user info    |
    |                              from payload         |
    |                                    |              |
    |                          12. Set SecurityContext  |
    |                                    |              |
    | 13. Response with data             |              |
    |<-----------------------------------|              |
    |                                    |              |
    |========== PHASE 3: TOKEN EXPIRED ===============  |
    |                                    |              |
    | GET /api/users                     |              |
    | Authorization: Bearer eyJ...      |              |
    | (expired)                          |              |
    |----------------------------------->|              |
    |                                    |              |
    |                          14. Verify signature     |
    |                              (passes)             |
    |                          15. Check expiry:        |
    |                              exp < now ?          |
    |                              -> EXPIRED           |
    |                                    |              |
    | 16. Response: 401 Unauthorized     |              |
    |     { error: "Token expired" }     |              |
    |<-----------------------------------|              |
    |                                    |              |
    |========== PHASE 4: REFRESH TOKEN ================ |
    |                                    |              |
    | Client detects 401                 |              |
    |                                    |              |
    | POST /auth/refresh                 |              |
    | Cookie: refreshToken=eyJ...       |              |
    |----------------------------------->|              |
    |                                    |              |
    |                          17. Extract RT from      |
    |                              cookie               |
    |                                    |              |
    |                          18. Hash the RT          |
    |                                    |              |
    |                          19. Lookup hash in DB    |
    |                              (find matching RT)   |
    |                                    |------------->|
    |                                    |<-------------|
    |                                    |              |
    |                          20. Check:               |
    |                              - RT exists?         |
    |                              - Not expired?       |
    |                              - Not revoked?       |
    |                              - Same device?       |
    |                                    |              |
    |                          21. DELETE old RT from DB|
    |                              (token rotation)     |
    |                                    |------------->|
    |                                    |              |
    |                          22. Create NEW AT        |
    |                              (same user, new exp) |
    |                                    |              |
    |                          23. Create NEW RT        |
    |                              (new jti, new exp)   |
    |                                    |              |
    |                          24. Store new RT hash    |
    |                              in DB                |
    |                                    |------------->|
    |                                    |              |
    | 25. Response:                      |              |
    | {                                   |              |
    |   accessToken: "eyJ...",          |              |
    |   refreshToken: "eyJ..."          |              |
    | }                                  |              |
    |<-----------------------------------|              |
    |                                    |              |
    | 26. Client retries original API    |              |
    |     with NEW access token          |              |
    |                                    |              |

================================================================
  SPRING BOOT IMPLEMENTATION
================================================================

-----------------------
  1. ENTITY: RefreshToken
-----------------------

  @Entity
  @Table(name = "refresh_tokens")
  public class RefreshTokenEntity {

      @Id
      @GeneratedValue(strategy = IDENTITY)
      private Long id;

      @Column(nullable = false, unique = true)
      private String tokenHash;    // Hashed refresh token

      @Column(nullable = false)
      private String userId;       // Who owns this token

      @Column(nullable = false)
      private String deviceId;     // Which device

      @Column(nullable = false)
      private Instant expiresAt;   // When it expires

      @Column(nullable = false)
      private Instant createdAt;   // When created

      @Column(nullable = false)
      private boolean revoked;     // Is it revoked?
  }

-----------------------
  2. REPOSITORY
-----------------------

  @Repository
  public interface RefreshTokenRepository
      extends JpaRepository<RefreshTokenEntity, Long> {

      Optional<RefreshTokenEntity>
          findByTokenHash(String tokenHash);

      void deleteByUserId(String userId);
          // Used for logout from all devices

      void deleteByUserIdAndDeviceId(
          String userId, String deviceId);
          // Used for logout from specific device
  }

-----------------------
  3. SERVICE: Token Generation
-----------------------

  @Service
  public class TokenService {

      @Value("${jwt.secret}")
      private String secretKey;

      @Value("${jwt.access-token-expiry:900000}")
      private long accessTokenExpiry;  // 15 min

      @Value("${jwt.refresh-token-expiry:604800000}")
      private long refreshTokenExpiry; // 7 days

      @Autowired
      private RefreshTokenRepository repo;

      // ---------- ACCESS TOKEN ----------

      public String generateAccessToken(User user) {
          Date now = new Date();
          Date expiry = new Date(
              now.getTime() + accessTokenExpiry
          );

          return Jwts.builder()
              .subject(user.getId())              // sub
              .claim("role", user.getRole())      // role
              .claim("email", user.getEmail())    // email
              .issuedAt(now)                      // iat
              .expiration(expiry)                 // exp
              .issuer("myapp.com")                // iss
              .signWith(getSigningKey())
              .compact();
      }

      // ---------- REFRESH TOKEN ----------

      public String generateRefreshToken(
          User user, String deviceId
      ) {
          // 1. Create JWT refresh token
          Date now = new Date();
          Date expiry = new Date(
              now.getTime() + refreshTokenExpiry
          );

          String refreshToken = Jwts.builder()
              .subject(user.getId())
              .claim("deviceId", deviceId)
              .claim("type", "REFRESH")
              .issuedAt(now)
              .expiration(expiry)
              .signWith(getRefreshKey())
              .compact();

          // 2. Store hash in DB
          RefreshTokenEntity entity =
              new RefreshTokenEntity();
          entity.setTokenHash(hashToken(refreshToken));
          entity.setUserId(user.getId());
          entity.setDeviceId(deviceId);
          entity.setExpiresAt(expiry.toInstant());
          entity.setCreatedAt(now.toInstant());
          entity.setRevoked(false);

          repo.save(entity);

          return refreshToken;
      }

      // ---------- VERIFY ACCESS TOKEN ----------

      public Claims verifyAccessToken(String token) {
          return Jwts.parser()
              .verifyWith(getSigningKey())
              .build()
              .parseSignedClaims(token)
              .getPayload();
      }

      // ---------- VERIFY & ROTATE REFRESH TOKEN ----------

      public Tokens refreshAccessToken(
          String oldRefreshToken,
          String deviceId
      ) {
          // 1. Find in DB
          String hash = hashToken(oldRefreshToken);
          RefreshTokenEntity stored =
              repo.findByTokenHash(hash)
                  .orElseThrow(() ->
                      new RuntimeException("Invalid RT")
                  );

          // 2. Check revoked
          if (stored.isRevoked()) {
              // Possible token theft!
              // Invalidate ALL tokens for this user
              repo.deleteByUserId(stored.getUserId());
              throw new RuntimeException("RT revoked");
          }

          // 3. Check expiry
          if (stored.getExpiresAt()
              .isBefore(Instant.now())) {
              repo.delete(stored);
              throw new RuntimeException("RT expired");
          }

          // 4. DELETE old RT (rotation)
          repo.delete(stored);

          // 5. Get user and generate new tokens
          User user = userRepo.findById(
              stored.getUserId()
          ).orElseThrow();

          String newAT = generateAccessToken(user);
          String newRT = generateRefreshToken(
              user, deviceId
          );

          return new Tokens(newAT, newRT);
      }

      // ---------- HELPERS ----------

      private SecretKey getSigningKey() {
          return new SecretKeySpec(
              secretKey.getBytes(),
              "HmacSHA256"
          );
      }

      private String hashToken(String token) {
          return Hashing.sha256()
              .hashString(token, UTF_8)
              .toString();
      }
  }

-----------------------
  4. CONTROLLER
-----------------------

  @RestController
  @RequestMapping("/auth")
  public class AuthController {

      @Autowired private AuthService authService;
      @Autowired private TokenService tokenService;

      @PostMapping("/login")
      public ResponseEntity<?> login(
          @RequestBody LoginRequest request,
          @RequestHeader("User-Agent")
              String deviceId
      ) {
          // 1. Authenticate user
          User user = authService
              .authenticate(
                  request.username(),
                  request.password()
              );

          // 2. Generate tokens
          String at = tokenService
              .generateAccessToken(user);
          String rt = tokenService
              .generateRefreshToken(user, deviceId);

          // 3. Set RT as HttpOnly cookie
          ResponseCookie cookie =
              ResponseCookie.from("refreshToken", rt)
                  .httpOnly(true)
                  .secure(true)
                  .sameSite("Strict")
                  .path("/auth/refresh")
                  .maxAge(Duration.ofDays(7))
                  .build();

          return ResponseEntity.ok()
              .header("Set-Cookie", cookie.toString())
              .body(new LoginResponse(at, 900));
      }

      @PostMapping("/refresh")
      public ResponseEntity<?> refresh(
          @CookieValue("refreshToken")
              String refreshToken,
          @RequestHeader("User-Agent")
              String deviceId
      ) {
          Tokens tokens = tokenService
              .refreshAccessToken(
                  refreshToken, deviceId
              );

          ResponseCookie cookie =
              ResponseCookie
                  .from("refreshToken", tokens.rt())
                  .httpOnly(true)
                  .secure(true)
                  .sameSite("Strict")
                  .path("/auth/refresh")
                  .maxAge(Duration.ofDays(7))
                  .build();

          return ResponseEntity.ok()
              .header("Set-Cookie", cookie.toString())
              .body(
                  new LoginResponse(tokens.at(), 900)
              );
      }

      @PostMapping("/logout")
      public ResponseEntity<?> logout(
          @CookieValue("refreshToken")
              String refreshToken
      ) {
          // Delete RT from DB
          String hash =
              tokenService.hashToken(refreshToken);
          refreshTokenRepo.findByTokenHash(hash)
              .ifPresent(repo::delete);

          // Clear cookie
          ResponseCookie cookie =
              ResponseCookie
                  .from("refreshToken", "")
                  .maxAge(0)
                  .build();

          return ResponseEntity.ok()
              .header("Set-Cookie", cookie.toString())
              .body("Logged out");
      }
  }

-----------------------
  5. SECURITY FILTER
-----------------------

  public class JwtAuthenticationFilter
      extends OncePerRequestFilter {

      @Autowired private TokenService tokenService;

      @Override
      protected void doFilterInternal(
          HttpServletRequest request,
          HttpServletResponse response,
          FilterChain chain
      ) {
          // Skip auth endpoints
          String path = request.getRequestURI();
          if (path.startsWith("/auth/")) {
              chain.doFilter(request, response);
              return;
          }

          // Extract access token
          String header =
              request.getHeader("Authorization");

          if (header == null ||
              !header.startsWith("Bearer ")) {
              chain.doFilter(request, response);
              return;
          }

          String token = header.substring(7);

          try {
              // Verify access token
              Claims claims =
                  tokenService.verifyAccessToken(token);

              // Set authentication
              UsernamePasswordAuthenticationToken auth =
                  new UsernamePasswordAuthenticationToken(
                      claims.getSubject(),
                      null,
                      getAuthorities(
                          claims.get("role", String.class)
                      )
                  );

              SecurityContextHolder.getContext()
                  .setAuthentication(auth);

          } catch (ExpiredJwtException e) {
              // Let client know token expired
              response.setStatus(401);
              response.getWriter()
                  .write("{\"error\":\"Token expired\"}");
              return;

          } catch (Exception e) {
              SecurityContextHolder.clearContext();
          }

          chain.doFilter(request, response);
      }
  }

================================================================
  TOKEN ROTATION - WHY AND HOW?
================================================================

WHAT IS TOKEN ROTATION?
  Every time refresh is called,
  OLD refresh token is DELETED,
  NEW refresh token is issued.

WHY ROTATE?
  - If refresh token is stolen, it can be used ONCE
  - After you refresh, the old stolen token is DEAD
  - Limits damage from token theft

VISUAL - TOKEN ROTATION:

  Login:
    RT_v1 created -> stored in DB
    DB: [RT_v1_hash]

  Refresh #1:
    Client sends: RT_v1
    Server checks: RT_v1_hash matches DB? YES
    Server DELETES: RT_v1_hash from DB
    Server CREATES: RT_v2 -> stores RT_v2_hash
    Client gets: RT_v2
    DB: [RT_v2_hash]

  Attacker tries to use stolen RT_v1:
    Server checks: RT_v1_hash in DB? NO (deleted)
    Request REJECTED

  Refresh #2:
    Client sends: RT_v2
    Server checks: RT_v2_hash matches DB? YES
    Server DELETES: RT_v2_hash from DB
    Server CREATES: RT_v3 -> stores RT_v3_hash
    Client gets: RT_v3
    DB: [RT_v3_hash]

================================================================
  REUSAGE DETECTION (Anti-Theft)
================================================================

  If someone tries to use an ALREADY ROTATED token,
  it means a possible token theft!

  How to detect:

  Refresh request comes with RT_v1
    -> Not found in DB (already rotated)
    -> Possible theft!
    -> Action: Invalidate ALL tokens for this user
    -> User must login again

  This is called "reuse detection".

================================================================
  MULTI-DEVICE SUPPORT
================================================================

  Each device gets its own refresh token.

  DB Table: refresh_tokens
  +---------+-----------+------------------+------------+
  | user_id | device_id | token_hash       | expires    |
  +---------+-----------+------------------+------------+
  | user123 | "iphone15"| a1b2c3...        | 7 days     |
  | user123 | "macbook" | d4e5f6...        | 7 days     |
  | user123 | "android" | g7h8i9...        | 7 days     |
  +---------+-----------+------------------+------------+

  Login from iPhone -> creates separate RT for iPhone
  Login from MacBook -> creates separate RT for MacBook
  Logout from iPhone -> deletes only iPhone's RT
  Logout from all -> deletes ALL RTs for user

================================================================
  SECURITY BEST PRACTICES
================================================================

  1. STORAGE:
     Access Token  -> In-memory (JS variable)
     Refresh Token -> HttpOnly secure cookie (path=/auth/refresh)

  2. EXPIRY:
     Access Token  -> 15-30 minutes
     Refresh Token -> 7-30 days

  3. ROTATION:
     Always rotate refresh token on each use
     Delete old token before issuing new one

  4. REUSE DETECTION:
     If used token is presented again -> possible theft
     Invalidate ALL tokens for that user

  5. SIGNING:
     Use different keys for AT and RT (defense in depth)
     Use RS256 for distributed systems

  6. TRANSPORT:
     Always use HTTPS (never send tokens over HTTP)
     Use SameSite=Strict for cookies

  7. REVOCATION:
     Store refresh tokens in DB to enable revocation
     On logout -> delete refresh token from DB

  8. DEVICE TRACKING:
     Store device ID with each refresh token
     Allow users to see and manage active sessions

================================================================
*/

public class TokenFlowAndImplementation {

}
