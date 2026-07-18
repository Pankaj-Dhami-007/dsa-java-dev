package springboot_deep_drive.springboot.security.spring_security;

/*
================================================================
  SPRING SECURITY - COMPLETE DEMO & ARCHITECTURE
================================================================

================================================================
  WHAT IS SPRING SECURITY?
================================================================

  Spring Security is a framework that provides:
  - Authentication (who are you?)
  - Authorization (what can you do?)
  - Protection against common attacks (CSRF, XSS, etc.)

  It is the de-facto standard for securing Spring apps.

================================================================
  CORE COMPONENTS
================================================================

  1. SecurityFilterChain
     -----------------------------------------------
     A chain of filters that every HTTP request passes
     through. Each filter handles one security concern.

  2. AuthenticationManager
     -----------------------------------------------
     The main strategy interface for authentication.
     It has one method: authenticate(Authentication).

  3. AuthenticationProvider
     -----------------------------------------------
     Does the actual authentication logic.
     Multiple providers can be configured (DB, LDAP, etc.)

  4. Authentication
     -----------------------------------------------
     Represents the authentication request or the
     authenticated principal. Stores credentials,
     authorities, and details.

  5. SecurityContextHolder
     -----------------------------------------------
     Holds the SecurityContext for the current thread.
     Stores who is authenticated for the current request.

  6. SecurityContext
     -----------------------------------------------
     Contains the Authentication object.
     Accessed via SecurityContextHolder.

  7. UserDetailsService
     -----------------------------------------------
     Loads user data from a database or other source.
     Returns a UserDetails object.

  8. UserDetails
     -----------------------------------------------
     Core user information interface.
     Contains username, password, authorities.

  9. GrantedAuthority
     -----------------------------------------------
     Represents a permission/role granted to the user.
     e.g., ROLE_USER, ROLE_ADMIN

  10. PasswordEncoder
      -----------------------------------------------
      Encodes and verifies passwords.
      Never store plain-text passwords!

  11. FilterChainProxy
      -----------------------------------------------
      A special filter that delegates to one or more
      SecurityFilterChain instances.

  12. WebSecurityConfigurerAdapter (DEPRECATED)
      -----------------------------------------------
      Old way of configuring security. Now replaced
      with SecurityFilterChain bean.

================================================================
  HOW IT ALL WORKS TOGETHER - REQUEST FLOW
================================================================

  REQUEST FLOW (Complete):

  Browser / Client
       |
       | HTTP Request
       v
  +------------------------------------------+
  |  FILTER CHAIN (springSecurityFilterChain) |
  +------------------------------------------+
       |
       | 1. SecurityContextPersistenceFilter
       |    - Restores SecurityContext from session
       |    - Saves it back after request
       |
       | 2. CsrfFilter
       |    - Validates CSRF token (state-changing requests)
       |    - Skips for stateless APIs
       |
       | 3. LogoutFilter
       |    - Handles logout requests
       |    - Invalidates session, clears context
       |
       | 4. UsernamePasswordAuthenticationFilter
       |    - Intercepts /login POST requests
       |    - Extracts username + password
       |    - Creates UsernamePasswordAuthenticationToken
       |    - Calls AuthenticationManager
       |
       | 5. BasicAuthenticationFilter
       |    - Handles HTTP Basic auth header
       |    - For API clients / service-to-service
       |
       | 6. ExceptionTranslationFilter
       |    - Catches security exceptions
       |    - Sends 401 (unauthorized) or 403 (forbidden)
       |
       | 7. FilterSecurityInterceptor
       |    - Last filter before the controller
       |    - Checks authorization rules
       |    - Throws AccessDeniedException if not allowed
       |
       v
  +------------------------------------------+
  |  AUTHENTICATION MANAGER                   |
  +------------------------------------------+
       |
       | authenticate(token)
       v
  +------------------------------------------+
  |  AUTHENTICATION PROVIDER(S)              |
  +------------------------------------------+
       |
       | 1. Load user: UserDetailsService.loadUserByUsername()
       | 2. Encode password: PasswordEncoder.matches()
       | 3. Create authenticated Authentication object
       |
       v
  +------------------------------------------+
  |  SecurityContextHolder                    |
  +------------------------------------------+
       |
       | Set Authentication in current thread
       |
       v
  +------------------------------------------+
  |  CONTROLLER / API                        |
  +------------------------------------------+
       |
       | Access user via:
       | - @AuthenticationPrincipal
       | - SecurityContextHolder.getContext()
       | - Principal parameter
       |
       v
  Response to Client

================================================================
  AUTHENTICATION FLOW - DETAILED
================================================================

  Step 1: User submits login credentials
  ----------------------------------------
  POST /login
  { "username": "john", "password": "secret123" }

  Step 2: UsernamePasswordAuthenticationFilter intercepts
  -------------------------------------------------------
  - Creates UsernamePasswordAuthenticationToken
    (unauthenticated, authorities = null)
  - Delegates to AuthenticationManager

  Step 3: AuthenticationManager finds a provider
  ------------------------------------------------
  - Loops through configured AuthenticationProviders
  - Finds DaoAuthenticationProvider (default)
  - Calls provider.authenticate(token)

  Step 4: DaoAuthenticationProvider loads user
  -----------------------------------------------
  - Calls UserDetailsService.loadUserByUsername("john")
  - UserDetailsService fetches from DB
  - Returns UserDetails (or throws UsernameNotFoundException)

  Step 5: Password verification
  -------------------------------
  - PasswordEncoder.matches(rawPassword, encodedPassword)
  - If match -> Success
  - If no match -> BadCredentialsException

  Step 6: Create authenticated token
  ------------------------------------
  - New UsernamePasswordAuthenticationToken(
      principal = UserDetails,
      credentials = null (cleared),
      authorities = granted authorities
    )

  Step 7: Store in SecurityContextHolder
  ----------------------------------------
  - SecurityContextHolder.getContext()
      .setAuthentication(authenticatedToken)
  - Also creates session (stateful) or returns JWT (stateless)

  Step 8: SecurityContextPersistenceFilter
  ------------------------------------------
  - Saves SecurityContext to HTTP session
  - On next request, restores it (no re-login needed)

================================================================
  AUTHORIZATION FLOW
================================================================

  After authentication, every request goes through:

  FilterSecurityInterceptor (last filter)
       |
       | 1. Get Authentication from SecurityContextHolder
       | 2. Get request attributes (URL, method)
       | 3. Get allowed authorities from config
       | 4. DecisionManager decides:
       |    - Has required role? -> Allow
       |    - Missing role? -> AccessDeniedException
       |
       | AccessDecisionManager uses voters:
       | - RoleVoter: Checks ROLE_ prefix
       | - AuthenticatedVoter: Checks auth level
       | - Custom voters: Your own logic

  Authorization checks happen at:
  1. URL level (security config)
  2. Method level (@PreAuthorize, @Secured)
  3. Code level (SecurityContextHolder checks)

================================================================
  DEFAULT SECURITY FILTER ORDER
================================================================

  1. ForceEagerSessionCreationFilter
  2. ChannelProcessingFilter
  3. WebAsyncManagerIntegrationFilter
  4. SecurityContextPersistenceFilter
  5. HeaderWriterFilter
  6. CorsFilter
  7. CsrfFilter
  8. LogoutFilter
  9. OAuth2AuthorizationRequestRedirectFilter
  10. Saml2WebSsoAuthenticationRequestFilter
  11. X509AuthenticationFilter
  12. AbstractPreAuthenticatedProcessingFilter
  13. CasAuthenticationFilter
  14. OAuth2LoginAuthenticationFilter
  15. Saml2WebSsoAuthenticationFilter
  16. UsernamePasswordAuthenticationFilter
  17. OpenIDAuthenticationFilter
  18. DefaultLoginPageGeneratingFilter
  19. DefaultLogoutPageGeneratingFilter
  20. ConcurrentSessionFilter
  21. DigestAuthenticationFilter
  22. BearerTokenAuthenticationFilter
  23. BasicAuthenticationFilter
  24. RequestCacheAwareFilter
  25. SecurityContextHolderAwareRequestFilter
  26. JaasApiIntegrationFilter
  27. RememberMeAuthenticationFilter
  28. AnonymousAuthenticationFilter
  29. OAuth2AuthorizationCodeGrantFilter
  30. SessionManagementFilter
  31. ExceptionTranslationFilter
  32. FilterSecurityInterceptor
  33. SwitchUserFilter

================================================================
  CONFIGURATION APPROACHES
================================================================

  1. SecurityFilterChain Bean (Recommended - Spring Boot 3+)
  -----------------------------------------------------------

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(
                HttpSecurity http) throws Exception {

            http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/public/**").permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                )
                .formLogin(form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/home")
                )
                .logout(logout -> logout
                    .logoutSuccessUrl("/login?logout")
                )
                .csrf(csrf -> csrf.disable())  // For APIs
                .sessionManagement(session ->
                    session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                    )
                );

            return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

  2. UserDetailsService Bean
  ---------------------------

    @Service
    public class CustomUserDetailsService
            implements UserDetailsService {

        @Autowired
        private UserRepository userRepo;

        @Override
        public UserDetails loadUserByUsername(
                String username) {

            User user = userRepo.findByUsername(username)
                .orElseThrow(() ->
                    new UsernameNotFoundException(
                        "User not found: " + username
                    )
                );

            return new org.springframework.security
                .core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    getAuthorities(user.getRoles())
                );
        }

        private Collection<GrantedAuthority>
                getAuthorities(Set<Role> roles) {
            return roles.stream()
                .map(role -> new SimpleGrantedAuthority(
                    "ROLE_" + role.getName()))
                .collect(Collectors.toList());
        }
    }

================================================================
  METHOD-LEVEL SECURITY
================================================================

  Enable with @EnableMethodSecurity

    @Configuration
    @EnableMethodSecurity
    public class MethodSecurityConfig {
    }

  Annotations:
  - @PreAuthorize("hasRole('ADMIN')")
    Check before method execution
  - @PostAuthorize("returnObject.owner == authentication.name")
    Check after method execution
  - @Secured("ROLE_ADMIN")
    Simple role check (older style)
  - @RolesAllowed("ADMIN")
    JSR-250 annotation

  Example:

    @RestController
    @RequestMapping("/api/users")
    public class UserController {

        @GetMapping
        @PreAuthorize("hasRole('ADMIN')")
        public List<User> getAllUsers() { ... }

        @GetMapping("/{id}")
        @PreAuthorize(
            "hasRole('ADMIN') or " +
            "@userSecurity.isOwner(#id)"
        )
        public User getUser(@PathVariable Long id) {
            ...
        }
    }

================================================================
  PASSWORD ENCODERS
================================================================

  BCryptPasswordEncoder (RECOMMENDED)
  - Uses BCrypt strong hashing
  - Built-in salt (random per password)
  - Adjustable strength (default 10)

  Argon2PasswordEncoder
  - Winner of Password Hashing Competition
  - Resistant to GPU attacks
  - More secure but slower

  Pbkdf2PasswordEncoder
  - PBKDF2 with configurable iterations
  - Good for legacy systems

  SCryptPasswordEncoder
  - Memory-hard function
  - Resistant to hardware attacks

  NoOpPasswordEncoder (NEVER use in production)
  - Plain text passwords
  - Testing only!

================================================================
  CSRF PROTECTION
================================================================

  What is CSRF?
  - Cross-Site Request Forgery
  - Attacker tricks user into performing actions
  - Exploits cookie-based auth (cookie sent automatically)

  How Spring Security handles it:
  - Generates CSRF token on server
  - Sends token to client (in cookie or response)
  - Client must include token in state-changing requests
  - Server validates token on each POST/PUT/DELETE

  For APIs (stateless JWT):
  - CSRF is not needed (no cookie-based auth)
  - Can disable: csrf -> disable()

================================================================
  CORS CONFIGURATION
================================================================

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
            "http://localhost:3000"));
        config.setAllowedMethods(List.of(
            "GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(
            "/**", config);
        return source;
    }

================================================================
  SESSION MANAGEMENT
================================================================

  Session Creation Policies:
  - ALWAYS: Create session if not exists
  - IF_REQUIRED: Create session if needed (default)
  - NEVER: Never create session, use existing if present
  - STATELESS: No session at all (JWT auth)

  Concurrent Session Control:
  - Limit max sessions per user
  - Expire oldest session on new login

    http.sessionManagement(session ->
        session
            .sessionCreationPolicy(
                SessionCreationPolicy.IF_REQUIRED)
            .maximumSessions(1)
            .maxSessionsPreventsLogin(false)
            .expiredUrl("/login?expired")
    );

================================================================
  EXCEPTION HANDLING
================================================================

  ExceptionTranslationFilter handles:

  AuthenticationException (401):
  - User not logged in
  - Token expired/invalid
  -> Redirect to login or send 401

  AccessDeniedException (403):
  - User logged in but no permission
  -> Send 403 Forbidden

  Custom handlers:

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http) throws Exception {

        http
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(
                    (request, response, authException) -> {
                        response.setStatus(401);
                        response.getWriter().write(
                            "{\"error\":\"Unauthorized\"}");
                    })
                .accessDeniedHandler(
                    (request, response, accessDeniedException) -> {
                        response.setStatus(403);
                        response.getWriter().write(
                            "{\"error\":\"Forbidden\"}");
                    })
            );
        return http.build();
    }

================================================================
  SECURITY HEADERS
================================================================

  Spring Security adds these headers automatically:

  - Cache-Control: no-cache, no-store, max-age=0
  - Pragma: no-cache
  - Expires: 0
  - X-Content-Type-Options: nosniff
  - Strict-Transport-Security: max-age=31536000
  - X-Frame-Options: DENY
  - X-XSS-Protection: 0

  Customize:

    http.headers(headers -> headers
        .frameOptions(f -> f.sameOrigin())
        .httpStrictTransportSecurity(hsts ->
            hsts.maxAgeInSeconds(31536000))
    );

================================================================
  COMPLETE SECURITY FILTER CHAIN EXAMPLE
================================================================

  @Configuration
  @EnableWebSecurity
  @EnableMethodSecurity
  public class SecurityConfig {

      @Autowired
      private CustomUserDetailsService userDetailsService;

      @Bean
      public SecurityFilterChain filterChain(
              HttpSecurity http) throws Exception {

          http
              // 1. Disable CSRF (stateless API)
              .csrf(csrf -> csrf.disable())

              // 2. CORS configuration
              .cors(cors -> cors.configurationSource(
                  corsConfigurationSource()))

              // 3. Stateless session
              .sessionManagement(session ->
                  session.sessionCreationPolicy(
                      SessionCreationPolicy.STATELESS))

              // 4. Authorization rules
              .authorizeHttpRequests(auth -> auth
                  .requestMatchers("/api/auth/**").permitAll()
                  .requestMatchers("/api/admin/**")
                      .hasRole("ADMIN")
                  .anyRequest().authenticated()
              )

              // 5. Exception handling
              .exceptionHandling(ex -> ex
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
              )

              // 6. Add JWT filter
              .addFilterBefore(
                  new JwtAuthenticationFilter(),
                  UsernamePasswordAuthenticationFilter.class
              );

          return http.build();
      }

      @Bean
      public PasswordEncoder passwordEncoder() {
          return new BCryptPasswordEncoder();
      }

      @Bean
      public AuthenticationManager authenticationManager(
              AuthenticationConfiguration config)
              throws Exception {
          return config.getAuthenticationManager();
      }

      @Bean
      public CorsConfigurationSource
              corsConfigurationSource() {
          CorsConfiguration config = new CorsConfiguration();
          config.setAllowedOrigins(List.of("*"));
          config.setAllowedMethods(List.of(
              "GET","POST","PUT","DELETE"));
          config.setAllowedHeaders(List.of("*"));
          UrlBasedCorsConfigurationSource source =
              new UrlBasedCorsConfigurationSource();
          source.registerCorsConfiguration(
              "/**", config);
          return source;
      }
  }

================================================================
  SECURITY VS FILTER COMPARISON
================================================================

  +--------------------------+--------------------------------------------+
  | Component                | Purpose                                    |
  +--------------------------+--------------------------------------------+
  | SecurityFilterChain      | Chain of security filters for HTTP req     |
  | AuthenticationManager    | Orchestrates authentication                |
  | AuthenticationProvider   | Does actual authentication logic           |
  | UserDetailsService       | Loads user from DB/storage                 |
  | PasswordEncoder          | Encodes and verifies passwords             |
  | SecurityContextHolder    | Stores current auth context (thread-local) |
  | SecurityContext          | Holds Authentication object                |
  | GrantedAuthority         | Represents a permission/role               |
  | AccessDecisionManager    | Decides if user can access resource        |
  | FilterSecurityInterceptor| Enforces authorization rules (last filter) |
  | ExceptionTranslationFilter| Converts exceptions to HTTP responses      |
  +--------------------------+--------------------------------------------+

================================================================
*/

public class SpringSecurityDemo {

}
