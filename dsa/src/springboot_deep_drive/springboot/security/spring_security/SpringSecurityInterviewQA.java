package springboot_deep_drive.springboot.security.spring_security;

/*
================================================================
  SPRING SECURITY - INTERVIEW QUESTIONS & ANSWERS
================================================================

================================================================
  Q1: What is Spring Security?
================================================================

Answer:
  Spring Security is a framework for authentication
  (who are you?) and authorization (what can you do?)
  in Java applications. It also protects against
  common attacks like CSRF, session fixation, etc.

================================================================
  Q2: What are the core components of Spring Security?
================================================================

Answer:
  1. SecurityFilterChain - Chain of HTTP filters
  2. AuthenticationManager - Orchestrates auth
  3. AuthenticationProvider - Does actual auth logic
  4. UserDetailsService - Loads user from DB
  5. PasswordEncoder - Encodes/verifies passwords
  6. SecurityContextHolder - Stores auth context
  7. SecurityContext - Holds Authentication object
  8. GrantedAuthority - Represents a role/permission
  9. FilterSecurityInterceptor - Enforces authorization

================================================================
  Q3: What is SecurityFilterChain?
================================================================

Answer:
  SecurityFilterChain is a chain of filters that every
  HTTP request passes through. Each filter handles one
  security concern (auth, CSRF, CORS, etc.).

  Defined as a @Bean in security config.

  You can have multiple SecurityFilterChain beans
  for different URL patterns with different rules.

================================================================
  Q4: What is the filter order in Spring Security?
================================================================

Answer:
  The request flow through filters in order:

  1. SecurityContextPersistenceFilter
     - Restores/saves SecurityContext from session
  2. CsrfFilter
     - Validates CSRF tokens
  3. LogoutFilter
     - Handles logout
  4. UsernamePasswordAuthenticationFilter
     - Handles form login
  5. BasicAuthenticationFilter
     - Handles HTTP Basic auth
  6. ExceptionTranslationFilter
     - Catches security exceptions
  7. FilterSecurityInterceptor
     - Enforces authorization (last filter)

================================================================
  Q5: How does authentication work in Spring Security?
================================================================

Answer:
  Flow:
  1. Filter intercepts request with credentials
  2. Creates unauthenticated Authentication token
  3. Delegates to AuthenticationManager
  4. Manager finds matching AuthenticationProvider
  5. Provider calls UserDetailsService.loadUserByUsername()
  6. PasswordEncoder.matches() verifies password
  7. On success: creates authenticated Authentication
  8. Stores it in SecurityContextHolder
  9. On failure: throws AuthenticationException

================================================================
  Q6: What is the difference between
       AuthenticationManager and AuthenticationProvider?
================================================================

Answer:
  AuthenticationManager:
  - The main orchestrator interface
  - Has one method: authenticate(Authentication)
  - Delegates to registered providers
  - Default implementation: ProviderManager

  AuthenticationProvider:
  - Does the actual auth logic
  - Supports specific auth types
  - Multiple providers can be configured
  - DaoAuthenticationProvider (default) handles
    username/password auth

================================================================
  Q7: What is UserDetailsService?
================================================================

Answer:
  UserDetailsService is an interface with one method:
    UserDetails loadUserByUsername(String username)

  It loads user data from a data source (DB, LDAP, etc.)
  and returns a UserDetails object.

  You implement this interface to provide custom
  user retrieval logic.

================================================================
  Q8: What is the difference between
       authentication and authorization?
================================================================

Answer:
  Authentication (authN):
  - "Who are you?"
  - Verifying user identity
  - Login process, token verification
  - Handled by AuthenticationManager

  Authorization (authZ):
  - "What can you do?"
  - Checking permissions/roles
  - URL security, method security
  - Handled by FilterSecurityInterceptor / @PreAuthorize

================================================================
  Q9: What is the SecurityContextHolder?
================================================================

Answer:
  SecurityContextHolder stores the SecurityContext
  for the current thread.

  It holds the Authentication object of the
  currently authenticated user.

  Modes:
  - MODE_THREADLOCAL (default): One context per thread
  - MODE_INHERITABLETHREADLOCAL: Child threads inherit
  - MODE_GLOBAL: Single context for all threads

  Access pattern:
    Authentication auth =
        SecurityContextHolder.getContext()
            .getAuthentication();

================================================================
  Q10: What is BCryptPasswordEncoder?
================================================================

Answer:
  BCryptPasswordEncoder is the default password encoder
  in Spring Security. It uses the BCrypt strong hashing
  algorithm.

  Features:
  - Generates random salt per password (23 chars)
  - Slow by design (configurable strength, default 10)
  - Each hash is different even for same password
  - Detects hash changes (upgrade on login)

  Usage:
    PasswordEncoder encoder =
        new BCryptPasswordEncoder();
    String hash = encoder.encode("mypassword");
    boolean match = encoder.matches(
        "mypassword", hash);

================================================================
  Q11: How do you configure Spring Security for a REST API?
================================================================

Answer:
  @Configuration
  @EnableWebSecurity
  public class SecurityConfig {

      @Bean
      public SecurityFilterChain filterChain(
              HttpSecurity http) throws Exception {
          http
              .csrf(csrf -> csrf.disable())
              .sessionManagement(session ->
                  session.sessionCreationPolicy(
                      SessionCreationPolicy.STATELESS))
              .authorizeHttpRequests(auth -> auth
                  .requestMatchers("/api/auth/**")
                      .permitAll()
                  .anyRequest().authenticated()
              );
          return http.build();
      }

      @Bean
      public PasswordEncoder passwordEncoder() {
          return new BCryptPasswordEncoder();
      }
  }

================================================================
  Q12: What is @PreAuthorize and @PostAuthorize?
================================================================

Answer:
  @PreAuthorize:
  - Checks authorization BEFORE method executes
  - Uses SpEL (Spring Expression Language)

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long id) { ... }

    @PreAuthorize(
        "hasRole('ADMIN') or #id == authentication.name")
    public User getUser(Long id) { ... }

  @PostAuthorize:
  - Checks authorization AFTER method executes
  - Can access return value

    @PostAuthorize(
        "returnObject.owner == authentication.name")
    public Document getDocument(Long id) { ... }

================================================================
  Q13: How does CSRF protection work in Spring Security?
================================================================

Answer:
  CSRF prevents attackers from making users perform
  unintended actions.

  How it works:
  1. Server generates a unique CSRF token
  2. Token is sent to client (in cookie or response)
  3. Client sends token back in header/parameter
  4. Server validates token on POST/PUT/DELETE

  For REST APIs with JWT (stateless):
  - CSRF is NOT needed
  - No cookie-based auth = no CSRF risk
  - Disable: csrf -> disable()

================================================================
  Q14: What is CORS and how to configure it?
================================================================

Answer:
  CORS (Cross-Origin Resource Sharing) controls which
  domains can access your API from browser.

    @Bean
    public CorsConfigurationSource
            corsConfigurationSource() {
        CorsConfiguration config =
            new CorsConfiguration();
        config.setAllowedOrigins(List.of(
            "http://localhost:3000"));
        config.setAllowedMethods(List.of(
            "GET","POST","PUT","DELETE"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(
            "/**", config);
        return source;
    }

  Then plug into HttpSecurity:
    http.cors(cors -> cors.configurationSource(...));

================================================================
  Q15: What are the session management options?
================================================================

Answer:
  SessionCreationPolicy:
  - ALWAYS: Create session if not exists
  - IF_REQUIRED: Create if needed (default)
  - NEVER: Don't create, use existing
  - STATELESS: No sessions (JWT auth)

  Concurrent sessions:
    http.sessionManagement(session ->
        session
            .sessionCreationPolicy(
                SessionCreationPolicy.IF_REQUIRED)
            .maximumSessions(1)
            .maxSessionsPreventsLogin(false)
            .expiredUrl("/login?expired")
    );

================================================================
  Q16: How do you handle exceptions in Spring Security?
================================================================

Answer:
  Two main exceptions:
  1. AuthenticationException (401)
     - User not authenticated
  2. AccessDeniedException (403)
     - User authenticated but no permission

  Custom handling:
    http.exceptionHandling(ex -> ex
        .authenticationEntryPoint(
            (req, res, e) -> {
                res.setStatus(401);
                res.getWriter().write(
                    "{\"error\":\"Unauthorized\"}");
            })
        .accessDeniedHandler(
            (req, res, e) -> {
                res.setStatus(403);
                res.getWriter().write(
                    "{\"error\":\"Forbidden\"}");
            })
    );

================================================================
  Q17: What is the difference between
       hasRole() and hasAuthority()?
================================================================

Answer:
  hasRole('ADMIN'):
  - Automatically checks for "ROLE_" prefix
  - hasRole('ADMIN') = hasAuthority('ROLE_ADMIN')

  hasAuthority('ROLE_ADMIN'):
  - Does NOT add any prefix
  - Must specify full authority name

  hasRole is shorthand for hasAuthority with ROLE_ prefix.

================================================================
  Q18: What is FilterSecurityInterceptor?
================================================================

Answer:
  FilterSecurityInterceptor is the LAST filter in the
  Spring Security filter chain. It makes the final
  authorization decision before a request reaches
  the controller.

  It uses:
  1. Authentication from SecurityContextHolder
  2. ConfigAttributes (security rules from config)
  3. AccessDecisionManager (voters decide)

  If access is denied, it throws AccessDeniedException
  (caught by ExceptionTranslationFilter -> 403).

================================================================
  Q19: What is the difference between
       @Secured and @PreAuthorize?
================================================================

Answer:
  @Secured("ROLE_ADMIN"):
  - Older annotation (Spring Security 3+)
  - Simple role check
  - Does NOT support SpEL
  - Must enable with @EnableGlobalMethodSecurity

  @PreAuthorize("hasRole('ADMIN')"):
  - Newer annotation (Spring Security 3+)
  - Supports SpEL expressions
  - More flexible (conditions, method args)
  - Must enable with @EnableMethodSecurity
  - RECOMMENDED for modern apps

================================================================
  Q20: How do you test Spring Security?
================================================================

Answer:
  @WebMvcTest(UserController.class)
  class UserControllerTest {

      @Autowired
      private MockMvc mockMvc;

      @Test
      @WithMockUser(roles = "ADMIN")
      void shouldReturnUsersWhenAdmin()
              throws Exception {
          mockMvc.perform(get("/api/users"))
              .andExpect(status().isOk());
      }

      @Test
      void shouldReturn401WhenNotAuth()
              throws Exception {
          mockMvc.perform(get("/api/users"))
              .andExpect(status().isUnauthorized());
      }

      @Test
      @WithMockUser(roles = "USER")
      void shouldReturn403WhenNotAdmin()
              throws Exception {
          mockMvc.perform(get("/api/users"))
              .andExpect(status().isForbidden());
      }
  }

================================================================
  Q21: How does Spring Security integrate with JWT?
================================================================

Answer:
  JWT integration uses a custom filter:

  1. Create JwtAuthenticationFilter
     - extends OncePerRequestFilter
     - Extracts JWT from Authorization header
     - Verifies JWT signature
     - Sets SecurityContext

  2. Register filter in SecurityFilterChain
     - addFilterBefore(customFilter,
       UsernamePasswordAuthenticationFilter.class)

  3. Disable session management
     - sessionCreationPolicy(STATELESS)

  4. Disable CSRF
     - csrf -> disable()

================================================================
  Q22: What is the difference between
       formLogin() and httpBasic()?
================================================================

Answer:
  formLogin():
  - For browser-based apps
  - Redirects to login page
  - Session-based authentication
  - Supports remember-me

  httpBasic():
  - For API clients / service-to-service
  - Base64 encoded credentials in header
  - No login page
  - Often used for internal APIs

  For JWT APIs: NONE of these are used.
  Use custom filter + permitAll on auth endpoints.

================================================================
  Q23: What is AnonymousAuthenticationFilter?
================================================================

Answer:
  AnonymousAuthenticationFilter is typically the 30th
  filter in the chain. If the user is NOT authenticated
  by this point (no previous filter set authentication),
  it creates an anonymous Authentication object.

  This allows:
  - public endpoints to work without auth
  - Code to check "isAnonymous()" instead of null check

  Default anonymous user has role: ROLE_ANONYMOUS

================================================================
  Q24: What is ExceptionTranslationFilter?
================================================================

Answer:
  ExceptionTranslationFilter wraps the rest of the
  filter chain (filters after it) in a try-catch.

  It handles:
  1. AuthenticationException (caught) -> 401
     - Sends auth challenge or redirects to login
  2. AccessDeniedException (caught) -> 403
     - Sends forbidden response
     - Checks if user is anonymous (redirect to login)
     - Or if user is authenticated (send 403)

  It does NOT handle exceptions from filters before it.

================================================================
  Q25: What is RememberMeAuthenticationFilter?
================================================================

Answer:
  Remember-me allows users to be remembered across
  browser restarts via a cookie.

  How it works:
  1. User logs in with "remember-me" checkbox
  2. Server creates a remember-me cookie
  3. On next visit, RememberMeAuthenticationFilter
     detects the cookie
  4. Authenticates user automatically

  Two approaches:
  - Simple hash-based (less secure)
  - Persistent token-based (stored in DB, more secure)

================================================================
  Q26: What is the difference between
       permitAll() and anonymous()?
================================================================

Answer:
  permitAll():
  - Allows access WITHOUT any authentication
  - Both anonymous AND authenticated users can access
  - No auth challenge sent

  anonymous():
  - Only ALLOWS anonymous users (not authenticated)
  - If authenticated user tries, access is denied
  - Rarely used, for special cases like login page
    (if already logged in, redirect to home)

================================================================
  Q27: What is SecurityContextPersistenceFilter?
================================================================

Answer:
  SecurityContextPersistenceFilter (1st filter in chain):

  Before the request:
  - Checks if HTTP session exists
  - Loads SecurityContext from session
  - Sets it in SecurityContextHolder

  After the request:
  - Saves SecurityContext back to session
  - Clears SecurityContextHolder

  For stateless APIs: This filter does nothing
  (no session created).

================================================================
  Q28: How do you implement multi-factor auth
       in Spring Security?
================================================================

Answer:
  Approach: Custom AuthenticationProvider

  1. First factor: Username + Password
     - Standard DaoAuthenticationProvider

  2. Second factor: OTP / TOTP
     - Custom OTPAuthenticationProvider

  Flow:
  1. User submits username + password
  2. If valid, server sends OTP (email/SMS)
  3. User submits OTP
  4. Custom provider validates OTP
  5. On success, user is fully authenticated

  Alternatively: Use Spring Security's MFA support
  with OAuth2 or separate auth endpoints.

================================================================
  Q29: What are the common security misconfigurations?
================================================================

Answer:
  1. Disabling CSRF for browser apps (cookie-based)
     - Leads to CSRF attacks

  2. Using NoOpPasswordEncoder
     - Plain text passwords in DB

  3. permitAll() on sensitive endpoints
     - Anyone can access admin APIs

  4. Too broad CORS (allow all origins)
     - Any website can call your API

  5. Too much info in error responses
     - "User not found" vs "Bad credentials"
     - Information leakage

  6. Session fixation not disabled
     - Attacker can hijack session

  7. Using deprecated WebSecurityConfigurerAdapter
     - Missing out on new security features

================================================================
  Q30: What's new in Spring Security 6+ / Boot 3+?
================================================================

Answer:
  1. WebSecurityConfigurerAdapter REMOVED
     - Use SecurityFilterChain bean instead

  2. @EnableMethodSecurity replaces
     @EnableGlobalMethodSecurity
     - New annotation with better defaults

  3. authorizeHttpRequests replaces authorizeRequests
     - Lambda DSL (new style)

  4. CSRF enabled by default for all requests
     - More secure by default

  5. Default security headers updated
     - Better defaults for XSS, content-type

  6. OAuth2 client improvements
     - Better support for PKCE, device flow

  7. PasswordEncoder defaults to BCrypt
     - DelegatingPasswordEncoder for migration

================================================================
  QUICK REVISION - One-Liner Answers
================================================================

  Q: What is Spring Security?
  A: Auth + authorization framework for Spring apps.

  Q: Core interface for auth?
  A: AuthenticationManager.

  Q: Who does actual auth?
  A: AuthenticationProvider.

  Q: Who loads users?
  A: UserDetailsService.

  Q: Who encodes passwords?
  A: PasswordEncoder.

  Q: Where is auth stored?
  A: SecurityContextHolder (thread-local).

  Q: First filter in chain?
  A: SecurityContextPersistenceFilter.

  Q: Last filter in chain?
  A: FilterSecurityInterceptor.

  Q: Who catches exceptions?
  A: ExceptionTranslationFilter.

  Q: CSRF disabled for?
  A: Stateless REST APIs.

  Q: Stateless session means?
  A: No HTTP session, JWT-based.

  Q: @PreAuthorize checks when?
  A: Before method executes.

  Q: BCrypt is what?
  A: Slow, salted password hashing.

  Q: hasRole vs hasAuthority?
  A: hasRole adds ROLE_ prefix automatically.

  Q: 401 vs 403?
  A: 401 = not authenticated, 403 = not authorized.

================================================================
*/

public class SpringSecurityInterviewQA {

}
