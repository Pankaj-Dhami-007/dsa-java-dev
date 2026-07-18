package springboot_deep_drive.springboot.security.spring_security;

/*
================================================================
  SPRING SECURITY WITH JWT - COMPLETE INTEGRATION
================================================================

================================================================
  WHY JWT WITH SPRING SECURITY?
================================================================

  Spring Security is designed for session-based auth.
  JWT is stateless (no session). To use JWT with
  Spring Security, we need a custom filter that:

  1. Extracts JWT from Authorization header
  2. Verifies the JWT signature
  3. Creates Authentication object
  4. Sets it in SecurityContextHolder
  5. Skips session creation (STATELESS)

================================================================
  ARCHITECTURE OVERVIEW
================================================================

  +------------------------------------------------------------------+
  |                     SPRING SECURITY FILTER CHAIN                  |
  +------------------------------------------------------------------+
  |                                                                   |
  |  1. SecurityContextPersistenceFilter                              |
  |     -> SKIPPED (no session in JWT mode)                          |
  |                                                                   |
  |  2. CsrfFilter                                                    |
  |     -> DISABLED (no cookie-based auth, no CSRF risk)             |
  |                                                                   |
  |  3. CorsFilter                                                    |
  |     -> HANDLES cross-origin requests                              |
  |                                                                   |
  |  4. JwtAuthenticationFilter (CUSTOM)                              |
  |     -> Extracts JWT from Authorization header                     |
  |     -> Verifies JWT signature + expiry                            |
  |     -> Sets Authentication in SecurityContextHolder               |
  |                                                                   |
  |  5. ExceptionTranslationFilter                                    |
  |     -> Catches auth failures -> 401                               |
  |                                                                   |
  |  6. FilterSecurityInterceptor                                     |
  |     -> Checks authorization rules (hasRole, permitAll, etc.)      |
  |                                                                   |
  +------------------------------------------------------------------+
                                |
                                v
  +------------------------------------------------------------------+
  |                     CONTROLLER / API                              |
  +------------------------------------------------------------------+

================================================================
  COMPLETE IMPLEMENTATION
================================================================

  1. JWT UTILITY
  ----------------------------------------------------------------

  @Component
  public class JwtUtils {

      @Value("${jwt.secret}")
      private String secretKey;

      @Value("${jwt.expiry:900000}")
      private long expiry;  // 15 min

      private SecretKey getSigningKey() {
          return new SecretKeySpec(
              secretKey.getBytes(), "HmacSHA256");
      }

      public String generateToken(
              String userId, String role) {
          return Jwts.builder()
              .subject(userId)
              .claim("role", role)
              .issuedAt(new Date())
              .expiration(
                  new Date(System.currentTimeMillis()
                      + expiry))
              .signWith(getSigningKey())
              .compact();
      }

      public Claims verifyToken(String token) {
          return Jwts.parser()
              .verifyWith(getSigningKey())
              .build()
              .parseSignedClaims(token)
              .getPayload();
      }

      public boolean isTokenExpired(String token) {
          try {
              Claims claims = verifyToken(token);
              return claims.getExpiration()
                  .before(new Date());
          } catch (Exception e) {
              return true;
          }
      }
  }

  2. JWT AUTHENTICATION FILTER
  ----------------------------------------------------------------

  public class JwtAuthenticationFilter
      extends OncePerRequestFilter {

      @Autowired
      private JwtUtils jwtUtils;

      @Override
      protected void doFilterInternal(
              HttpServletRequest request,
              HttpServletResponse response,
              FilterChain chain)
              throws ServletException, IOException {

          // 1. Skip auth endpoints
          String path = request.getRequestURI();
          if (path.startsWith("/api/auth/")) {
              chain.doFilter(request, response);
              return;
          }

          // 2. Extract JWT from header
          String header =
              request.getHeader("Authorization");

          if (header == null ||
              !header.startsWith("Bearer ")) {
              chain.doFilter(request, response);
              return;
          }

          String token = header.substring(7);

          try {
              // 3. Verify JWT
              Claims claims =
                  jwtUtils.verifyToken(token);

              // 4. Create authentication
              List<GrantedAuthority> authorities =
                  List.of(new SimpleGrantedAuthority(
                      "ROLE_" + claims.get("role")));

              UsernamePasswordAuthenticationToken auth =
                  new UsernamePasswordAuthenticationToken(
                      claims.getSubject(),
                      null,
                      authorities);

              // 5. Set SecurityContext
              SecurityContextHolder.getContext()
                  .setAuthentication(auth);

          } catch (ExpiredJwtException e) {
              response.setStatus(401);
              response.getWriter().write(
                  "{\"error\":\"Token expired\"}");
              return;
          } catch (JwtException e) {
              response.setStatus(401);
              response.getWriter().write(
                  "{\"error\":\"Invalid token\"}");
              return;
          }

          chain.doFilter(request, response);
      }
  }

  3. SECURITY CONFIG
  ----------------------------------------------------------------

  @Configuration
  @EnableWebSecurity
  @EnableMethodSecurity
  public class SecurityConfig {

      @Autowired
      private JwtUtils jwtUtils;

      @Bean
      public SecurityFilterChain filterChain(
              HttpSecurity http) throws Exception {

          http
              // Disable CSRF (stateless JWT)
              .csrf(csrf -> csrf.disable())

              // Stateless session (no session)
              .sessionManagement(session ->
                  session.sessionCreationPolicy(
                      SessionCreationPolicy.STATELESS))

              // CORS
              .cors(cors -> cors.configurationSource(
                  corsConfigurationSource()))

              // URL authorization
              .authorizeHttpRequests(auth -> auth
                  .requestMatchers(
                      "/api/auth/**").permitAll()
                  .requestMatchers(
                      "/api/admin/**")
                          .hasRole("ADMIN")
                  .requestMatchers(
                      "/api/users/**")
                          .hasAnyRole("USER", "ADMIN")
                  .anyRequest().authenticated()
              )

              // Exception handling
              .exceptionHandling(ex -> ex
                  .authenticationEntryPoint(
                      (req, res, e) -> {
                          res.setStatus(401);
                          res.setContentType(
                              "application/json");
                          res.getWriter().write(
                              "{\"error\":\"Unauthorized\"}");
                      })
                  .accessDeniedHandler(
                      (req, res, e) -> {
                          res.setStatus(403);
                          res.setContentType(
                              "application/json");
                          res.getWriter().write(
                              "{\"error\":\"Forbidden\"}");
                      })
              )

              // Add JWT filter BEFORE Spring's
              // UsernamePasswordAuthenticationFilter
              .addFilterBefore(
                  new JwtAuthenticationFilter(jwtUtils),
                  UsernamePasswordAuthenticationFilter
                      .class);

          return http.build();
      }

      @Bean
      public CorsConfigurationSource
              corsConfigurationSource() {
          CorsConfiguration config =
              new CorsConfiguration();
          config.setAllowedOrigins(
              List.of("http://localhost:3000"));
          config.setAllowedMethods(
              List.of("GET","POST","PUT","DELETE",
                      "OPTIONS"));
          config.setAllowedHeaders(List.of("*"));
          config.setAllowCredentials(true);

          UrlBasedCorsConfigurationSource source =
              new UrlBasedCorsConfigurationSource();
          source.registerCorsConfiguration(
              "/**", config);
          return source;
      }
  }

  4. AUTH CONTROLLER (Login / Register)
  ----------------------------------------------------------------

  @RestController
  @RequestMapping("/api/auth")
  public class AuthController {

      @Autowired
      private AuthenticationManager authManager;

      @Autowired
      private JwtUtils jwtUtils;

      @Autowired
      private UserRepository userRepo;

      @Autowired
      private PasswordEncoder passwordEncoder;

      @PostMapping("/login")
      public ResponseEntity<?> login(
              @RequestBody LoginRequest request) {

          // 1. Authenticate via Spring Security
          Authentication authentication =
              authManager.authenticate(
                  new UsernamePasswordAuthenticationToken(
                      request.username(),
                      request.password()));

          // 2. Generate JWT
          UserDetails user =
              (UserDetails) authentication
                  .getPrincipal();

          String role = user.getAuthorities()
              .stream()
              .findFirst()
              .map(GrantedAuthority::getAuthority)
              .orElse("ROLE_USER")
              .replace("ROLE_", "");

          String token = jwtUtils.generateToken(
              user.getUsername(), role);

          return ResponseEntity.ok(
              new LoginResponse(token, 900));
      }

      @PostMapping("/register")
      public ResponseEntity<?> register(
              @RequestBody RegisterRequest request) {

          if (userRepo.existsByUsername(
                  request.username())) {
              return ResponseEntity.badRequest()
                  .body("Username already exists");
          }

          User user = new User();
          user.setUsername(request.username());
          user.setPassword(passwordEncoder.encode(
              request.password()));
          user.setRole("USER");
          userRepo.save(user);

          return ResponseEntity.ok("Registered");
      }
  }

  5. USER CONTROLLER (Protected)
  ----------------------------------------------------------------

  @RestController
  @RequestMapping("/api/users")
  public class UserController {

      @GetMapping
      @PreAuthorize("hasRole('ADMIN')")
      public List<User> getAllUsers() {
          return userRepo.findAll();
      }

      @GetMapping("/me")
      public User getMyProfile(
              @AuthenticationPrincipal
                  UserDetails userDetails) {
          return userRepo.findByUsername(
              userDetails.getUsername());
      }
  }

================================================================
  REQUEST FLOW - STEP BY STEP
================================================================

  LOGIN:
  ======

  POST /api/auth/login
  { "username": "john", "password": "pass123" }
       |
       | 1. Request arrives -> SecurityFilterChain
       | 2. JwtAuthenticationFilter checks path
       |    -> "/api/auth/login" starts with "/api/auth/"
       |    -> SKIPS (lets request pass)
       | 3. FilterSecurityInterceptor checks rules
       |    -> "/api/auth/**" is permitAll -> ALLOW
       | 4. Request reaches AuthController.login()
       | 5. AuthenticationManager.authenticate() called
       | 6. DaoAuthenticationProvider validates creds
       | 7. On success: JWT generated and returned
       |
       v
  Response: { "token": "eyJ...", "expiresIn": 900 }

  API CALL:
  =========

  GET /api/users/me
  Authorization: Bearer eyJ...
       |
       | 1. Request arrives -> SecurityFilterChain
       | 2. JwtAuthenticationFilter checks path
       |    -> "/api/users/me" does NOT start with
       |       "/api/auth/" -> PROCESS
       | 3. Extract token from "Bearer eyJ..."
       | 4. Verify JWT signature using secret key
       | 5. Check expiry (exp claim)
       | 6. Extract userId and role from claims
       | 7. Create UsernamePasswordAuthenticationToken
       | 8. Set SecurityContextHolder
       | 9. FilterSecurityInterceptor checks rules
       |    -> "/api/users/**" requires USER or ADMIN
       |    -> Authentication has role -> ALLOW
       | 10. Request reaches UserController.getMyProfile()
       | 11. @AuthenticationPrincipal injects user info
       |
       v
  Response: User profile data

================================================================
  FILTER POSITIONING - WHY addFilterBefore?
================================================================

  Standard filter order with JWT:

  1. SecurityContextPersistenceFilter
     -> No session, so does nothing

  2. HeaderWriterFilter
     -> Adds security headers

  3. CorsFilter
     -> Handles CORS

  4. CsrfFilter                         <-- DISABLED
     -> Skipped for stateless

  5. JwtAuthenticationFilter (OURS)     <-- HERE
     -> Sets SecurityContext from JWT

  6. LogoutFilter
     -> Handles logout

  7. UsernamePasswordAuthenticationFilter <-- NOT USED
     -> Skipped (no login form)

  8. ExceptionTranslationFilter
     -> Catches exceptions

  9. FilterSecurityInterceptor
     -> Checks authorization

  We add JwtAuthenticationFilter BEFORE
  UsernamePasswordAuthenticationFilter because:
  - JWT sets auth context BEFORE standard filters run
  - Standard auth filters (form, basic) are skipped
  - Our filter handles all JWT verification

================================================================
  COMPLETE DEPENDENCIES (pom.xml)
================================================================

  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
  </dependency>

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

================================================================
  APPLICATION PROPERTIES
================================================================

  # JWT Configuration
  jwt.secret=mySuperSecretKeyThatIsAtLeast256BitsLongForHS256
  jwt.access-token-expiry=900000         # 15 min
  jwt.refresh-token-expiry=604800000      # 7 days

  # BCrypt strength
  security.password-encoder.strength=10

================================================================
  SECURITY CONSIDERATIONS
================================================================

  1. Secret Key:
     - Use 256+ bit key for HS256
     - Store in env variable (not code)
     - Rotate periodically

  2. Token Storage:
     - Access token -> In-memory (JS variable)
     - Refresh token -> HttpOnly secure cookie

  3. Transport:
     - Always HTTPS
     - Never send tokens in URL

  4. Expiry:
     - Access token: 15 min
     - Refresh token: 7 days (with rotation)

  5. CSRF:
     - Disabled (not needed for header-based auth)
     - Re-enable if using cookies for AT

  6. CORS:
     - Restrict to specific origins
     - Never use allowCredentials + allowAllOrigins

  7. Logout:
     - Delete refresh token from DB
     - Clear SecurityContextHolder

================================================================
  TESTING JWT SECURITY
================================================================

  @WebMvcTest(UserController.class)
  class UserControllerTest {

      @Autowired
      private MockMvc mockMvc;

      @Test
      void shouldReturn401WithoutToken()
              throws Exception {
          mockMvc.perform(get("/api/users"))
              .andExpect(status().isUnauthorized());
      }

      @Test
      void shouldReturn200WithValidToken()
              throws Exception {
          mockMvc.perform(get("/api/users")
              .header("Authorization",
                  "Bearer " + validToken))
              .andExpect(status().isOk());
      }

      @Test
      void shouldReturn401WithExpiredToken()
              throws Exception {
          mockMvc.perform(get("/api/users")
              .header("Authorization",
                  "Bearer " + expiredToken))
              .andExpect(status().isUnauthorized());
      }

      @Test
      @WithMockUser(roles = "ADMIN")
      void shouldReturn200WithMockUser()
              throws Exception {
          mockMvc.perform(get("/api/users"))
              .andExpect(status().isOk());
      }
  }

================================================================
  JWT + SPRING SECURITY - KEY TAKEAWAY
================================================================

  1. Custom JwtAuthenticationFilter
     -> Extract, verify, set SecurityContext

  2. Stateless session
     -> sessionCreationPolicy(STATELESS)

  3. CSRF disabled
     -> No cookie-based auth, no CSRF risk

  4. addFilterBefore(UP filter)
     -> JWT filter runs before form login

  5. Exception handling
     -> 401 for auth failures, 403 for access denied

  6. PasswordEncoder
     -> BCrypt for password hashing

  7. UserDetailsService
     -> Load user from DB for authentication

================================================================
*/

public class SpringSecurityWithJwt {

}
