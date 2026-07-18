package javaDeepDrive.springsecurity;

public class UserDetailsServiceConcept {

    /*
    ===========================================================
                    USERDETAILSSERVICE
    ===========================================================

    UserDetailsService is one of the most important
    interfaces in Spring Security.

    Almost every authentication mechanism eventually
    needs user information.

    Question:

    Where will Spring Security get user data?

    Database?

    LDAP?

    Active Directory?

    External Service?

    Spring Security doesn't know.
    That's why UserDetailsService exists.

    ===========================================================
                    SIMPLE DEFINITION
    ===========================================================

    UserDetailsService is responsible for:
    Loading user information from some source.

    Example:

    Database
    Cache
    LDAP
    External Authentication Server
    Microservice

    ===========================================================
                    INTERFACE
    ===========================================================

    public interface UserDetailsService {

        UserDetails loadUserByUsername(
            String username
        );

    }

    Only one method.

    But extremely important.

    ===========================================================
                    BIG PICTURE
    ===========================================================

    Browser

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

    Database

    ===========================================================
                WHY USERDETAILSSERVICE?
    ===========================================================

    Imagine Spring Security directly accessing DB.

    Problem:

    Every project has different tables.

    Example:

    Project A

    users

    ---------------------

    Project B

    employee_master

    ---------------------

    Project C

    customer_account

    ---------------------

    Spring Security cannot know your schema.

    Therefore:

    Spring delegates user lookup to you.

    Using:

    UserDetailsService

    ===========================================================
                COMPLETE AUTHENTICATION FLOW
    ===========================================================

    User Login

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

    UserRepository

      |

      v

    Database

      |

      v

    UserDetails

      |

      v

    Password Validation

      |

      v

    Authentication Success

    ===========================================================
                STEP 1
                LOGIN REQUEST
    ===========================================================

    POST /login

    {

       "username":"pankaj",
       "password":"12345"

    }

    ===========================================================
                STEP 2
                AUTHENTICATION TOKEN
    ===========================================================

    UsernamePasswordAuthenticationFilter
    creates:

    UsernamePasswordAuthenticationToken

    Example:

    username = pankaj
    password = 12345
    authenticated = false

    ===========================================================
                STEP 3
                AUTHENTICATIONMANAGER
    ===========================================================

    AuthenticationManager receives token.

    Delegates to:

    ProviderManager

    ===========================================================
                STEP 4
                DAOAUTHENTICATIONPROVIDER
    ===========================================================

    ProviderManager selects:

    DaoAuthenticationProvider
    Because username/password login.

    ===========================================================
                STEP 5
                USERDETAILSSERVICE
    ===========================================================

    DaoAuthenticationProvider executes:

    loadUserByUsername("pankaj")

    ===========================================================
                WHAT HAPPENS INTERNALLY?
    ===========================================================

    DaoAuthenticationProvider

         |

         v

    UserDetailsService

         |

         v

    UserRepository

         |

         v

    Database

         |

         v

    User Entity

         |

         v

    UserDetails

         |

         v

    DaoAuthenticationProvider

    ===========================================================
                EXAMPLE DATABASE RECORD
    ===========================================================

    id = 1
    username = pankaj
    password = $2a$10$asdjahsdjkashd
    role = ADMIN
    active = true

    ===========================================================
                USERDETAILS OBJECT
    ===========================================================

    Spring Security never works directly
    with Entity.

    It works using:

    UserDetails

    ===========================================================
                WHY NOT ENTITY?
    ===========================================================

    Example:

    UserEntity

    contains

    id

    username

    mobile

    address

    age

    city

    state

    country

    ------------------------------------------------

    Spring Security doesn't care.

    It only needs:

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
                WHAT USERDETAILS CONTAINS?
    ===========================================================

    Username

    Password

    Authorities

    Account Locked Status

    Enabled Status

    Expired Status

    ===========================================================
                SPRING PROVIDED USER CLASS
    ===========================================================

    Spring Security provides:

    org.springframework.security.core.userdetails.User

    Example:

    UserDetails user =
        User.builder()
            .username("pankaj")
            .password(encodedPassword)
            .roles("ADMIN")
            .build();

    This class already implements:

    UserDetails

    ===========================================================
                CUSTOM USERDETAILS
    ===========================================================

    Many projects create:

    CustomUserDetails

    Example:

    public class CustomUserDetails
            implements UserDetails {

    }

    ===========================================================
                WHY CUSTOM USERDETAILS?
    ===========================================================

    Need extra information.

    Example:

    employeeId

    department

    clientId

    organizationId

    tenantId

    ===========================================================
                FLOW WITH CUSTOM USERDETAILS
    ===========================================================

    Database Entity

          |

          v

    UserEntity

          |

          v

    CustomUserDetails

          |

          v

    Authentication

          |

          v

    SecurityContextHolder

    ===========================================================
                CUSTOM IMPLEMENTATION
    ===========================================================

    @Service
    public class MyUserDetailsService
            implements UserDetailsService {

        @Override
        public UserDetails loadUserByUsername(
                String username) {

            UserEntity user =
                repository.findByUsername(
                    username
                );

            return new CustomUserDetails(user);
        }
    }

    ===========================================================
                IMPORTANT EXCEPTION
    ===========================================================

    User not found.

    Must throw:

    UsernameNotFoundException

    Example:

    throw new UsernameNotFoundException(
        "User Not Found"
    );

    ===========================================================
                WHY EXCEPTION?
    ===========================================================

    DaoAuthenticationProvider expects:

    User Found

        OR

    UsernameNotFoundException

    ===========================================================
                PASSWORD VALIDATION FLOW
    ===========================================================

    UserDetails returned.

    Example:

    Username

    Password Hash

    Authorities

    ------------------------------------------------

    DaoAuthenticationProvider now performs:

    PasswordEncoder.matches()

    ===========================================================
                IMPORTANT POINT
    ===========================================================

    UserDetailsService

    DOES NOT

    validate password.

    ------------------------------------------------

    DaoAuthenticationProvider

    validates password.

    ===========================================================

    Very common interview question.

    ===========================================================
                RESPONSIBILITY SPLIT
    ===========================================================

    UserDetailsService

    Responsibility:

    Load User

    --------------------------------------------

    PasswordEncoder

    Responsibility:

    Verify Password

    --------------------------------------------

    AuthenticationProvider

    Responsibility:

    Authenticate User

    ===========================================================
                INTERNAL DAO FLOW
    ===========================================================

    DaoAuthenticationProvider

          |

          v

    retrieveUser()

          |

          v

    UserDetailsService

          |

          v

    UserDetails

          |

          v

    additionalAuthenticationChecks()

          |

          v

    PasswordEncoder.matches()

          |

          v

    Authentication Success

    ===========================================================
                USERDETAILS IN SECURITYCONTEXT
    ===========================================================

    After authentication:

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
                ACCESSING CURRENT USER
    ===========================================================

    Authentication authentication =

        SecurityContextHolder
            .getContext()
            .getAuthentication();

    UserDetails user =

        (UserDetails)
            authentication.getPrincipal();

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

    Repository

      |

      v

    Database

      |

      v

    UserEntity

      |

      v

    UserDetails

      |

      v

    PasswordEncoder

      |

      v

    Authentication

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

    Q1. What is UserDetailsService?

    Loads user information from datasource.

    ------------------------------------------------

    Q2. How many methods exist?

    One.

    loadUserByUsername()

    ------------------------------------------------

    Q3. What does UserDetailsService do?

    Loads user.

    Does NOT authenticate.

    ------------------------------------------------

    Q4. Who validates password?

    PasswordEncoder through
    DaoAuthenticationProvider.

    ------------------------------------------------

    Q5. Why UserDetails?

    Spring Security requires a standard
    user representation.

    ------------------------------------------------

    Q6. Can Spring Security use Entity directly?

    No.

    It works with UserDetails.

    ------------------------------------------------

    Q7. What happens if user not found?

    UsernameNotFoundException.

    ------------------------------------------------

    Q8. What is Principal?

    Usually UserDetails object of
    authenticated user.

    ------------------------------------------------

    Q9. What is default implementation
    of UserDetails?

    Spring Security User class.

    ------------------------------------------------

    Q10. UserDetailsService vs UserDetails?

    UserDetailsService

        Loads user

    UserDetails

        Represents user

    ===========================================================
                    MEMORY TRICK
    ===========================================================

    AuthenticationManager

          ↓

    ProviderManager

          ↓

    DaoAuthenticationProvider

          ↓

    UserDetailsService

          ↓

    Repository

          ↓

    Database

          ↓

    UserDetails

          ↓

    PasswordEncoder

          ↓

    Authentication

          ↓

    SecurityContextHolder

    ===========================================================
    */
}