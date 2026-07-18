package javaDeepDrive.springsecurity;

public class UserDetailsConcept {

    /*
    ===========================================================
                        USERDETAILS
    ===========================================================

    UserDetails is one of the core objects
    of Spring Security.

    Most developers use it every day but
    don't fully understand where it sits
    inside Spring Security architecture.

    This class is the heart of authenticated user information.

    ===========================================================
                    BIG QUESTION
    ===========================================================

    UserDetailsService loads user.

    Fine.

    But what does it return?

    Answer:

    UserDetails

    Example:

    UserDetailsService

            |

            v

    loadUserByUsername()

            |

            v

    UserDetails

    ===========================================================
                    SIMPLE DEFINITION
    ===========================================================

    UserDetails represents an authenticated user.
    It contains security-related information.

    Example:

    Username
    Password
    Roles
    Permissions
    Account Status

    ===========================================================
                    VERY IMPORTANT
    ===========================================================

    UserDetails

    IS NOT

    Your Entity.

    Many beginners think:

    UserEntity == UserDetails

    Wrong.

    ===========================================================
                    EXAMPLE ENTITY
    ===========================================================

    UserEntity

    {

        Long id;

        String username;

        String password;

        String mobile;

        String city;

        String state;

        String country;

        String address;

        LocalDate dob;

    }

    ===========================================================

    Spring Security does not care about:

    mobile

    city

    address

    dob

    etc.

    ===========================================================

    Spring Security only cares about:

    username

    password

    authorities

    account status

    ===========================================================
                    USERDETAILS INTERFACE
    ===========================================================

    public interface UserDetails {

        Collection<? extends GrantedAuthority>
            getAuthorities();

        String getPassword();

        String getUsername();

        boolean isAccountNonExpired();

        boolean isAccountNonLocked();

        boolean isCredentialsNonExpired();

        boolean isEnabled();
    }

    ===========================================================
                    UNDERSTANDING METHODS
    ===========================================================

    getUsername()

    Returns username.

    Example:

    pankaj

    ------------------------------------------------

    getPassword()

    Returns encrypted password.

    Example:

    $2a$10$sdjkhsdjkhkjsdh

    ------------------------------------------------

    getAuthorities()

    Returns permissions.

    Example:

    ROLE_ADMIN

    ROLE_USER

    DELETE_USER

    CREATE_USER

    ------------------------------------------------

    isEnabled()

    Whether account active.

    true

    false

    ------------------------------------------------

    isAccountNonLocked()

    Whether account locked.

    true

    false

    ------------------------------------------------

    isCredentialsNonExpired()

    Password expired?

    true

    false

    ------------------------------------------------

    isAccountNonExpired()

    Account expired?

    true

    false

    ===========================================================
                    REAL DATABASE EXAMPLE
    ===========================================================

    USER TABLE

    ------------------------------------------------

    id = 1

    username = pankaj

    password = bcrypt_hash

    enabled = true

    locked = false

    role = ADMIN

    ------------------------------------------------

    Converted Into

    ------------------------------------------------

    UserDetails

    username = pankaj

    password = bcrypt_hash

    authorities = ROLE_ADMIN

    enabled = true

    accountNonLocked = true

    ===========================================================
                COMPLETE AUTH FLOW
    ===========================================================

    Login Request

          |

          v

    UsernamePasswordAuthenticationFilter

          |

          v

    AuthenticationManager

          |

          v

    ProviderManager

          |

          v

    DaoAuthenticationProvider

          |

          v

    UserDetailsService

          |

          v

    UserDetails

          |

          v

    PasswordEncoder

          |

          v

    Authentication Object

          |

          v

    SecurityContextHolder

    ===========================================================
                    VERY IMPORTANT
    ===========================================================

    UserDetails eventually becomes:
    Principal

    ===========================================================
                    OBJECT HIERARCHY
    ===========================================================

    SecurityContextHolder

            |

            v

    SecurityContext

            |

            v

    Authentication

            |

            v

    Principal

            |

            v

    UserDetails

    ===========================================================

    This hierarchy is extremely important.

    Most interview questions come from here.

    ===========================================================
                    SECURITYCONTEXTHOLDER
    ===========================================================

    SecurityContextHolder

            |

            v

    SecurityContext

    Stores security information for
    current thread.

    ===========================================================
                    SECURITYCONTEXT
    ===========================================================

    SecurityContext

            |

            v

    Authentication

    Stores currently authenticated user.

    ===========================================================
                    AUTHENTICATION
    ===========================================================

    Authentication contains:

    Principal

    Credentials

    Authorities

    Authenticated Flag

    ===========================================================
                    PRINCIPAL
    ===========================================================

    Principal means:

    Current User

    Usually Principal contains:

    UserDetails

    Example:

    Authentication auth =
        SecurityContextHolder
            .getContext()
            .getAuthentication();

    Object principal =
        auth.getPrincipal();

    ===========================================================
                    REAL OBJECT GRAPH
    ===========================================================

    SecurityContextHolder

      |

      v

    SecurityContext

      |

      v

    UsernamePasswordAuthenticationToken

      |

      v

    Principal

      |

      v

    UserDetails

      |

      v

    Username

    Password

    Authorities

    ===========================================================
                SPRING PROVIDED USER CLASS
    ===========================================================

    Spring Security already provides:

    org.springframework.security.core.userdetails.User

    This class implements:

    UserDetails

    ===========================================================

    Example:

    UserDetails user =

        User.builder()
            .username("pankaj")
            .password(password)
            .roles("ADMIN")
            .build();

    ===========================================================
                    INTERNAL STRUCTURE
    ===========================================================

    User

       implements

    UserDetails

    ------------------------------------------------

    Contains:

    username

    password

    authorities

    enabled

    accountNonLocked

    accountNonExpired

    credentialsNonExpired

    ===========================================================
                CUSTOM USERDETAILS
    ===========================================================

    Enterprise applications often create:

    CustomUserDetails

    Example:

    public class CustomUserDetails
            implements UserDetails {

    }

    ===========================================================
                WHY CUSTOM USERDETAILS?
    ===========================================================

    Because enterprise systems need
    additional data.

    Example:

    User ID

    Client ID

    Organization ID

    Department ID

    Tenant ID

    Employee Code

    ===========================================================
                    EXAMPLE
    ===========================================================

    public class CustomUserDetails
            implements UserDetails {

        private Long userId;

        private String username;

        private String password;

        private Long organizationId;

    }

    ===========================================================

    Now anywhere in application:

    Authentication auth =
        SecurityContextHolder
            .getContext()
            .getAuthentication();

    CustomUserDetails user =

        (CustomUserDetails)
            auth.getPrincipal();

    ===========================================================

    Can directly access:

    userId

    organizationId

    etc.

    ===========================================================
                    GRANTEDAUTHORITY
    ===========================================================

    Another very important object.

    UserDetails contains:

    Collection<GrantedAuthority>

    ===========================================================

    Example:

    ROLE_ADMIN

    ROLE_USER

    DELETE_USER

    CREATE_USER

    ===========================================================

    Spring stores permissions as:

    GrantedAuthority

    objects.

    ===========================================================
                    SIMPLEGRANTEDAUTHORITY
    ===========================================================

    Default implementation.

    Example:

    new SimpleGrantedAuthority(
        "ROLE_ADMIN"
    );

    ===========================================================
                    INTERNAL STRUCTURE
    ===========================================================

    UserDetails

          |

          v

    Collection<GrantedAuthority>

          |

          v

    ROLE_ADMIN

    ROLE_USER

    DELETE_USER

    ===========================================================
                ACCOUNT STATUS CHECKS
    ===========================================================

    During login:

    DaoAuthenticationProvider checks:

    isEnabled()

    isAccountNonLocked()

    isCredentialsNonExpired()

    isAccountNonExpired()

    ===========================================================

    If any check fails:

    Authentication fails.

    ===========================================================
                EXAMPLE
    ===========================================================

    User exists.

    Password correct.

    But:

    account locked.

    ===========================================================

    Login fails.

    ===========================================================

    AuthenticationException thrown.

    ===========================================================
                INTERNAL LOGIN FLOW
    ===========================================================

    DaoAuthenticationProvider

          |

          v

    UserDetailsService

          |

          v

    UserDetails

          |

          v

    Account Status Check

          |

          v

    Password Check

          |

          v

    Create Authentication

          |

          v

    SecurityContextHolder

    ===========================================================
                HOW TO GET CURRENT USER?
    ===========================================================

    Authentication auth =

        SecurityContextHolder
            .getContext()
            .getAuthentication();

    UserDetails user =

        (UserDetails)
            auth.getPrincipal();

    ===========================================================
                OBJECT RELATIONSHIP
    ===========================================================

    UserDetailsService

          |

          v

    UserDetails

          |

          v

    Principal

          |

          v

    Authentication

          |

          v

    SecurityContext

          |

          v

    SecurityContextHolder

    ===========================================================

    Remember this chain.

    Entire Spring Security revolves around it.

    ===========================================================
                COMPLETE INTERNAL FLOW
    ===========================================================

    Browser

      |

      v

    Tomcat

      |

      v

    Servlet Filter Chain

      |

      v

    DelegatingFilterProxy

      |

      v

    FilterChainProxy

      |

      v

    SecurityFilterChain

      |

      v

    Authentication Filter

      |

      v

    AuthenticationManager

      |

      v

    ProviderManager

      |

      v

    DaoAuthenticationProvider

      |

      v

    UserDetailsService

      |

      v

    UserDetails

      |

      v

    Account Status Validation

      |

      v

    Password Validation

      |

      v

    Authentication

      |

      v

    SecurityContext

      |

      v

    SecurityContextHolder

      |

      v

    DispatcherServlet

      |

      v

    Controller

    ===========================================================
                    INTERVIEW QUESTIONS
    ===========================================================

    Q1. What is UserDetails?

    Represents authenticated user information.

    ------------------------------------------------

    Q2. Is UserDetails an Entity?

    No.

    It is Spring Security's user model.

    ------------------------------------------------

    Q3. What does UserDetails contain?

    Username

    Password

    Authorities

    Account Status

    ------------------------------------------------

    Q4. What is Principal?

    Current authenticated user.

    Usually UserDetails.

    ------------------------------------------------

    Q5. What is GrantedAuthority?

    Represents permission/authority.

    ------------------------------------------------

    Q6. What is default UserDetails implementation?

    org.springframework.security.core.userdetails.User

    ------------------------------------------------

    Q7. Why CustomUserDetails?

    To store extra business information.

    ------------------------------------------------

    Q8. Where is UserDetails stored?

    Principal

    inside Authentication.

    ------------------------------------------------

    Q9. Where Authentication stored?

    SecurityContext.

    ------------------------------------------------

    Q10. Where SecurityContext stored?

    SecurityContextHolder.

    ===========================================================
                    MEMORY TRICK
    ===========================================================

    UserDetailsService

          ↓

    UserDetails

          ↓

    Principal

          ↓

    Authentication

          ↓

    SecurityContext

          ↓

    SecurityContextHolder

    ===========================================================
    */
}