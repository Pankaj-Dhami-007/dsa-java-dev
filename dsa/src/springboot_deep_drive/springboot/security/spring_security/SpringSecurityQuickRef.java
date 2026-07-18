package springboot_deep_drive.springboot.security.spring_security;

/*
================================================================
  SPRING SECURITY - QUICK REFERENCE
================================================================

  One-line explanations of every component and concept.
================================================================

================================================================
  CORE COMPONENTS
================================================================

  SecurityFilterChain
    Chain of HTTP filters that every request passes through.

  AuthenticationManager
    Orchestrates authentication by delegating to providers.

  AuthenticationProvider
    Does the actual authentication logic (validate creds).

  UserDetailsService
    Loads user data from DB/storage by username.

  UserDetails
    Core user info: username, password, authorities.

  PasswordEncoder
    Encodes passwords for storage, verifies against hash.

  SecurityContextHolder
    Thread-local storage for current user's auth context.

  SecurityContext
    Holds the Authentication object (who is logged in).

  Authentication
    Represents auth request or result (principal, creds,
    authorities).

  GrantedAuthority
    A permission or role granted to the user
    (e.g., ROLE_ADMIN).

  AccessDecisionManager
    Decides if user is allowed to access a resource
    (uses voters).

  FilterSecurityInterceptor
    Last filter - enforces URL/method authorization rules.

  ExceptionTranslationFilter
    Catches auth/access exceptions -> sends 401 or 403.

================================================================
  FILTERS IN ORDER
================================================================

  SecurityContextPersistenceFilter
    Restores SecurityContext from session before request,
    saves it after request.

  HeaderWriterFilter
    Adds security headers (Cache-Control, XSS, HSTS etc.)
    to the response.

  CorsFilter
    Handles Cross-Origin Resource Sharing (CORS) headers.

  CsrfFilter
    Validates CSRF tokens on state-changing requests
    (POST/PUT/DELETE).

  LogoutFilter
    Handles logout requests, clears session/context.

  UsernamePasswordAuthenticationFilter
    Intercepts POST /login with form credentials,
    creates UsernamePasswordAuthenticationToken.

  DefaultLoginPageGeneratingFilter
    Generates a default login page if none configured.

  BasicAuthenticationFilter
    Intercepts HTTP Basic auth header (Base64
    username:password).

  RequestCacheAwareFilter
    Caches request so user can be redirected back
    after login.

  SecurityContextHolderAwareRequestFilter
    Wraps HttpServletRequest with security-aware methods
    (isUserInRole, getUserPrincipal).

  RememberMeAuthenticationFilter
    Authenticates user from "remember-me" cookie.

  AnonymousAuthenticationFilter
    Creates anonymous Authentication if user not
    authenticated by previous filters.

  SessionManagementFilter
    Enforces session management rules
    (max sessions, fixation protection).

  ExceptionTranslationFilter
    Wraps the chain and catches AuthenticationException
    and AccessDeniedException.

  FilterSecurityInterceptor
    Final decision - checks if user can access the
    requested URL/method.

================================================================
  AUTHENTICATION FLOW (8 Steps)
================================================================

  1. Filter intercepts -> creates unauthenticated
     Authentication token.

  2. AuthenticationManager receives token -> finds
     matching AuthenticationProvider.

  3. Provider calls UserDetailsService
     -> loadUserByUsername(username).

  4. UserDetailsService fetches user from DB
     -> returns UserDetails.

  5. PasswordEncoder.matches(rawPassword, encodedPassword)
     -> verifies password.

  6. On match: Provider creates fully authenticated
     Authentication with roles.

  7. Authentication stored in SecurityContextHolder
     -> accessible for this request.

  8. SecurityContextPersistenceFilter saves context
     to HTTP session (for stateful apps).

================================================================
  AUTHORIZATION FLOW (4 Steps)
================================================================

  1. FilterSecurityInterceptor gets Authentication
     from SecurityContextHolder.

  2. Gathers ConfigAttributes
     (security rules from config/annotations).

  3. AccessDecisionManager votes using voters
     (RoleVoter, AuthenticatedVoter, CustomVoters).

  4. Allow -> request proceeds to controller
     Deny -> AccessDeniedException -> 403.

================================================================
  CONFIGURATION DSL (Lambda Style)
================================================================

  authorizeHttpRequests
    Configures URL-based authorization rules.

  requestMatchers
    Matches specific URL patterns for rules.

  permitAll
    Allows access without authentication.

  hasRole / hasAuthority
    Restricts access to users with specific role/authority.

  authenticated
    Requires user to be logged in (any role).

  csrf
    Configures or disables CSRF protection.

  sessionManagement
    Configures session creation policy and constraints.

  exceptionHandling
    Configures custom 401/403 handlers.

  cors
    Configures Cross-Origin Resource Sharing.

  headers
    Configures security headers.

  formLogin / httpBasic
    Configures form-based or HTTP Basic auth.

  logout
    Configures logout behavior.

  addFilterBefore / addFilterAfter
    Adds custom filter at specific position in chain.

================================================================
  METHOD SECURITY ANNOTATIONS
================================================================

  @EnableMethodSecurity
    Enables method-level security annotations (Boot 3+).

  @PreAuthorize("hasRole('ADMIN')")
    Checks SpEL expression BEFORE method execution.

  @PostAuthorize("returnObject.owner == auth.name")
    Checks SpEL AFTER method execution (uses return value).

  @Secured("ROLE_ADMIN")
    Simple role check (no SpEL support).

  @RolesAllowed("ADMIN")
    JSR-250 standard annotation.

================================================================
  PASSWORD ENCODERS
================================================================

  BCryptPasswordEncoder
    Strong hashing with built-in salt (RECOMMENDED).

  Argon2PasswordEncoder
    Memory-hard, resistant to GPU attacks (most secure).

  Pbkdf2PasswordEncoder
    Configurable iterations (good for legacy).

  SCryptPasswordEncoder
    Memory-hard function (hardware resistant).

  NoOpPasswordEncoder
    Plain text (NEVER use in production).

  DelegatingPasswordEncoder
    Supports multiple encodings during migration.

================================================================
  SESSION MANAGEMENT
================================================================

  SessionCreationPolicy.ALWAYS
    Create session if not exists.

  SessionCreationPolicy.IF_REQUIRED
    Create session if needed (default).

  SessionCreationPolicy.NEVER
    Don't create session, use existing if present.

  SessionCreationPolicy.STATELESS
    No sessions at all (JWT / token auth).

  maximumSessions(1)
    Limit concurrent sessions per user.

  sessionFixation().migrateSession()
    Create new session on login (prevent fixation).

================================================================
  COMMON EXCEPTIONS
================================================================

  AuthenticationException
    Base exception for auth failures (401).

  BadCredentialsException
    Wrong username or password.

  UsernameNotFoundException
    User not found in database.

  LockedException
    User account is locked.

  DisabledException
    User account is disabled.

  CredentialsExpiredException
    Password has expired.

  AccessDeniedException
    Authenticated but not authorized (403).

  InsufficientAuthenticationException
    Need higher level of authentication.

================================================================
  SECURITY HEADERS (Added Automatically)
================================================================

  Cache-Control: no-cache, no-store, max-age=0
    Prevents browser caching of sensitive pages.

  Pragma: no-cache
    HTTP/1.0 cache prevention.

  Expires: 0
    Forces immediate expiration.

  X-Content-Type-Options: nosniff
    Prevents MIME type sniffing.

  Strict-Transport-Security: max-age=31536000
    Enforces HTTPS (HSTS).

  X-Frame-Options: DENY
    Prevents clickjacking (no iframes).

  X-XSS-Protection: 0
    Disables legacy XSS filter (modern CSP handles this).

================================================================
  COMMON MISCONFIGURATIONS
================================================================

  CSRF enabled for stateless APIs
    Unnecessary for token-based auth.

  NoOpPasswordEncoder in production
    Plain text passwords -> instant breach.

  permitAll on /admin/**
    Admin endpoints exposed publicly.

  CORS with allowCredentials + allowAllOrigins
    Security risk (use specific origins).

  Verbose error messages
    "User not found" leaks info (use "Bad credentials").

  sessionCreationPolicy(ALWAYS) for JWT
    Unnecessary session overhead for token auth.

================================================================
  SPRING BOOT 3+ MIGRATION NOTES
================================================================

  REMOVED
    WebSecurityConfigurerAdapter
    WebSecurityConfigurer
    authorizeRequests() (use authorizeHttpRequests)

  NEW
    @EnableMethodSecurity (replaces @EnableGlobalMethodSecurity)
    Lambda DSL (.csrf(csrf -> csrf.disable()))
    SecurityFilterChain bean (instead of extending adapter)

  CHANGED
    authorizeHttpRequests returns AuthorizeHttpRequestsSpec
    Default security headers updated
    CSRF enabled by default for all requests (including GET)

================================================================
  TESTING ANNOTATIONS
================================================================

  @WithMockUser
    Creates a mock user in SecurityContext for testing.

  @WithMockUser(roles = "ADMIN")
    Mock user with specific roles.

  @WithUserDetails("username")
    Uses UserDetailsService to load real user.

  @WithAnonymousUser
    Simulates an anonymous user.

  @WithSecurityContext
    Custom context creation (for complex test scenarios).

================================================================
  30-SECOND CHEATSHEET
================================================================

  Security = SecurityFilterChain bean
    Define rules per URL pattern

  Auth = AuthenticationManager + Provider + UserDetailsService
    Who are you?

  AuthZ = FilterSecurityInterceptor + AccessDecisionManager
    What can you do?

  Passwords = BCryptPasswordEncoder
    Always hash, never store plain text

  JWT = Custom filter + STATELESS + CSRF disable
    addFilterBefore(yourFilter, UP auth filter)

  Exceptions = ExceptionTranslationFilter
    401 = not logged in, 403 = no permission

  Sessions = IF_REQUIRED for web, STATELESS for API
    Choose based on your app type

================================================================
*/

public class SpringSecurityQuickRef {

}
