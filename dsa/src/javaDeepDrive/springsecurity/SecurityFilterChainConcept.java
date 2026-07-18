package javaDeepDrive.springsecurity;

public class SecurityFilterChainConcept {

    /*
    ===========================================================
                    SECURITY FILTER CHAIN
    ===========================================================

    SecurityFilterChain is the heart of Spring Security.

    If you understand SecurityFilterChain,
    half of Spring Security becomes easy.

    Every request entering a Spring Boot application
    first passes through Security Filters.

    Controller is NOT reached directly.

    ===========================================================
                    WITHOUT SPRING SECURITY
    ===========================================================

    Request

       |

       v

    DispatcherServlet

       |

       v

    Controller

       |

       v

    Service

       |

       v

    Repository

       |

       v

    Database

    Very simple flow.

    ===========================================================
                    WITH SPRING SECURITY
    ===========================================================

    Request

       |

       v

    SecurityFilterChain

       |

       v

    DispatcherServlet

       |

       v

    Controller

       |

       v

    Service

       |

       v

    Repository

       |

       v

    Database

    Security layer sits before Spring MVC.

    ===========================================================
                    WHY FILTERS?
    ===========================================================

    Question:

    Why not perform security inside controller?

    Example:

    @GetMapping("/users")
    public List<User> getUsers() {

        // check login

        // check role

        // validate token

        // verify permissions

        // business logic
    }

    Problems:

    1. Duplicate code

    2. Hard maintenance

    3. Security mixed with business logic

    4. Difficult testing

    Solution:

    Handle security before controller.

    ===========================================================
                    WHAT IS FILTER?
    ===========================================================

    Filter is a component that intercepts request
    before it reaches target resource.

    Think:

    Security Guard at Office Entrance.

    Employee

       |

       v

    Security Guard

       |

       v

    Office

    Mapping:

    Employee
        -> Request

    Security Guard
        -> Filter

    Office
        -> Controller

    ===========================================================
                    WHAT IS SECURITY FILTER CHAIN?
    ===========================================================

    Spring Security contains multiple filters.
    These filters execute one by one.

    Example:

    Request

       |

       v

    Filter 1

       |

       v

    Filter 2

       |

       v

    Filter 3

       |

       v

    Filter 4

       |

       v

    Controller

    This sequence is called:

    Security Filter Chain

    ===========================================================
                    REAL REQUEST FLOW
    ===========================================================

    Request

      |

      v

    Cors Filter

      |

      v

    Csrf Filter

      |

      v

    Authentication Filter

      |

      v

    Authorization Filter

      |

      v

    Exception Filter

      |

      v

    Controller

    Every filter has specific responsibility.

    ===========================================================
                IMPORTANT BUILT-IN FILTERS
    ===========================================================

    Spring Security provides many filters.

    Common ones:

    1. SecurityContextHolderFilter
    2. CorsFilter
    3. CsrfFilter
    4. LogoutFilter

    5. UsernamePasswordAuthenticationFilter
    6. BasicAuthenticationFilter
    7. ExceptionTranslationFilter
    8. AuthorizationFilter

    Interviewers usually ask about:

    UsernamePasswordAuthenticationFilter
    AuthorizationFilter
    BasicAuthenticationFilter
    OncePerRequestFilter

    ===========================================================
                    FILTER CHAIN DECISION
    ===========================================================

    Example:

    Request:

    GET /admin/users

    Security checks:

    Is user logged in?

        YES

    Does user have ADMIN role?

        YES

    Allow request.

    Otherwise:

    403 Forbidden

    ===========================================================
                WHAT HAPPENS IF FILTER FAILS?
    ===========================================================

    Example:

    Request

       |

       v

    JWT Filter

       |

       v

    Invalid Token

    Request stops here.

    Controller never executes.

    Response:

    401 Unauthorized

    ===========================================================
                    FILTER EXECUTION ORDER
    ===========================================================

    Order matters.

    Example:

    JWT Validation

    must happen before

    Authorization Check

    Wrong Order:

    Authorization Filter

        before

    Authentication Filter

    Result:

    User not authenticated yet.

    Authorization fails.

    ===========================================================
                    SECURITY CONFIGURATION
    ===========================================================

    Modern Spring Security:

    @Bean
    public SecurityFilterChain
    securityFilterChain(
        HttpSecurity http
    ) throws Exception {

        return http.build();
    }

    This bean creates security filter chain.

    ===========================================================
                    OLD APPROACH
    ===========================================================

    Before Spring Security 5.7

    WebSecurityConfigurerAdapter

    was used.

    Example:

    public class SecurityConfig
            extends WebSecurityConfigurerAdapter

    This approach is deprecated.

    Modern projects use:

    SecurityFilterChain Bean

    ===========================================================
                    PERMIT ALL EXAMPLE
    ===========================================================

    http

        .authorizeHttpRequests(auth -> auth

            .requestMatchers(
                "/login",
                "/register"
            )

            .permitAll()

            .anyRequest()

            .authenticated()
        );

    Meaning:

    Login API

        Accessible to everyone

    Register API

        Accessible to everyone

    Remaining APIs

        Require authentication

    ===========================================================
                    ROLE BASED ACCESS
    ===========================================================

    http

        .authorizeHttpRequests(auth -> auth

            .requestMatchers("/admin/**")

            .hasRole("ADMIN")

            .requestMatchers("/user/**")

            .hasRole("USER")

            .anyRequest()

            .authenticated()
        );

    ===========================================================
                CUSTOM FILTER IN SPRING SECURITY
    ===========================================================

    Very common in JWT projects.

    Example:

    JWT Filter

    Request

       |

       v

    JWT Filter

       |

       v

    Validate Token

       |

       v

    SecurityContext

       |

       v

    Continue Request

    ===========================================================
                HOW CUSTOM FILTER ADDED?
    ===========================================================

    Example:

    http.addFilterBefore(
        jwtFilter,
        UsernamePasswordAuthenticationFilter.class
    );

    Meaning:

    Execute JWT Filter

    Before

    UsernamePasswordAuthenticationFilter

    ===========================================================
                WHY JWT FILTER BEFORE?
    ===========================================================

    Because:

    Authentication should happen first.

    Then authorization.

    Flow:

    JWT Filter

       |

       v

    User Authenticated

       |

       v

    Authorization Filter

       |

       v

    Controller

    ===========================================================
                    ONCEPERREQUESTFILTER
    ===========================================================

    Most JWT Filters extend:

    OncePerRequestFilter

    Reason:

    Ensures filter executes only once
    per request.

    Example:

    public class JwtFilter
            extends OncePerRequestFilter

    This is industry standard.

    Almost every Spring Boot JWT project uses it.

    ===========================================================
                    INTERNAL WORKING
    ===========================================================

    Request Arrives

        |

        v

    FilterChainProxy

        |

        v

    SecurityFilterChain

        |

        v

    Filter 1

        |

        v

    Filter 2

        |

        v

    Filter 3

        |

        v

    Controller

    Important:

    Actual implementation behind the scenes:

    FilterChainProxy

    manages all security filters.

    ===========================================================
                    JWT PROJECT FLOW
    ===========================================================

    Request

      |

      v

    JwtAuthenticationFilter

      |

      v

    Extract Token

      |

      v

    Validate Token

      |

      v

    Extract Username

      |

      v

    Load User

      |

      v

    Create Authentication

      |

      v

    SecurityContextHolder

      |

      v

    Authorization Filter

      |

      v

    Controller

    ===========================================================
                MOST ASKED INTERVIEW QUESTIONS
    ===========================================================

    Q1. What is SecurityFilterChain?

    Answer:

    Collection of security filters executed
    before request reaches controller.

    ---------------------------------------------------

    Q2. Why SecurityFilterChain?

    Answer:

    Separate security concerns from
    business logic.

    ---------------------------------------------------

    Q3. What is a filter?

    Answer:

    Component that intercepts request
    before target resource.

    ---------------------------------------------------

    Q4. Does request directly reach controller?

    Answer:

    No.

    It first passes through SecurityFilterChain.

    ---------------------------------------------------

    Q5. What is FilterChainProxy?

    Answer:

    Internal Spring Security component
    managing all security filters.

    ---------------------------------------------------

    Q6. How to add custom JWT filter?

    Answer:

    addFilterBefore()

    ---------------------------------------------------

    Q7. Why OncePerRequestFilter?

    Answer:

    Ensures filter executes only once
    for a request.

    ---------------------------------------------------

    Q8. What happens if authentication fails?

    Answer:

    Request stops.

    Controller never executes.

    ---------------------------------------------------

    Q9. Which filter is used in JWT projects?

    Answer:

    Custom JWT Filter extending
    OncePerRequestFilter.

    ---------------------------------------------------

    Q10. What replaced WebSecurityConfigurerAdapter?

    Answer:

    SecurityFilterChain Bean.

    ===========================================================
                    MEMORY TRICK
    ===========================================================

    Request

      ↓

    FilterChainProxy

      ↓

    SecurityFilterChain

      ↓

    JWT Filter

      ↓

    Authentication

      ↓

    Authorization

      ↓

    Controller

    Remember this flow.

    Every Spring Security project
    revolves around this architecture.
    ===========================================================
    */
}