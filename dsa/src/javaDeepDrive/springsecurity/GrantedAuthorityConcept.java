package javaDeepDrive.springsecurity;

public class GrantedAuthorityConcept {

    /*
    ===========================================================
                    GRANTED AUTHORITY
    ===========================================================

    Most developers use:

    hasRole("ADMIN")

    hasAuthority("DELETE_USER")

    every day.

    But very few understand:

    What is GrantedAuthority?

    Why Spring Security stores everything
    as authorities internally?

    This topic is the bridge between:

    Authentication

            and

    Authorization

    ===========================================================
                    BIG PICTURE
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
    CREATE_USER

            |

            v

    Authorization Decision

    ===========================================================
                    SIMPLE DEFINITION
    ===========================================================

    GrantedAuthority represents:

    A permission granted to a user.

    Think:

    User has some privileges.
    Spring stores those privileges as:
    GrantedAuthority

    ===========================================================
                    INTERFACE
    ===========================================================

    public interface GrantedAuthority {

        String getAuthority();

    }

    Only one method.

    ===========================================================
                    WHAT DOES IT RETURN?
    ===========================================================

    Example:

    ROLE_ADMIN
    ROLE_USER
    CREATE_USER
    DELETE_USER
    APPROVE_ORDER

    ===========================================================

    Example:

    authority.getAuthority()

    returns

    "ROLE_ADMIN"

    ===========================================================
                    IMPORTANT THING
    ===========================================================

    Spring Security internally doesn't care
    whether something is:

    Role

    Permission

    Privilege

    Access Right

    Everything becomes:

    GrantedAuthority

    ===========================================================
                    EXAMPLE
    ===========================================================

    Database

    -----------------------------------

    User

    role = ADMIN

    -----------------------------------

    Spring converts:

    ADMIN

    into

    ROLE_ADMIN

    -----------------------------------

    Stored as:

    GrantedAuthority

    ===========================================================
                    WHY?
    ===========================================================

    Common internal representation.

    Authorization becomes simple.
    Spring compares strings.

    Example:

    ROLE_ADMIN

    DELETE_USER

    CREATE_USER

    ===========================================================
                    SIMPLEGRANTEDAUTHORITY
    ===========================================================

    Default implementation.

    Most commonly used class.

    Example:

    new SimpleGrantedAuthority(
        "ROLE_ADMIN"
    );

    ===========================================================

    Internally:

    private String authority;

    ===========================================================

    Stores:

    ROLE_ADMIN

    ROLE_USER

    DELETE_USER

    etc.

    ===========================================================
                    REAL EXAMPLE
    ===========================================================

    User:

    pankaj

    Role:

    ADMIN

    ===========================================================

    UserDetails

    {

        username = pankaj

        authorities = [

            ROLE_ADMIN

        ]

    }

    ===========================================================
                    ANOTHER EXAMPLE
    ===========================================================

    User:

    pankaj

    Permissions:

    CREATE_USER

    DELETE_USER

    UPDATE_USER

    VIEW_USER

    ===========================================================

    UserDetails

    {

        authorities = [

            CREATE_USER,

            DELETE_USER,

            UPDATE_USER,

            VIEW_USER

        ]

    }

    ===========================================================
                    ROLE VS AUTHORITY
    ===========================================================

    Very Common Interview Question.

    ------------------------------------------------

    Role

    High Level Permission

    Example:

    ADMIN

    USER

    MANAGER

    ------------------------------------------------

    Authority

    Fine-Grained Permission

    Example:

    CREATE_USER

    DELETE_USER

    UPDATE_USER

    ===========================================================
                    EXAMPLE
    ===========================================================

    Role:

    ADMIN

    contains

    CREATE_USER

    UPDATE_USER

    DELETE_USER

    VIEW_USER

    ===========================================================

    User

        |

        v

    ADMIN

        |

        v

    Authorities

    CREATE_USER

    UPDATE_USER

    DELETE_USER

    VIEW_USER

    ===========================================================
                    SPRING INTERNAL VIEW
    ===========================================================

    Spring treats role as authority.

    Example:

    ADMIN

    becomes

    ROLE_ADMIN

    ===========================================================

    Therefore:

    Role

            is

    Authority

    ===========================================================
                    HASROLE()
    ===========================================================

    Example:

    hasRole("ADMIN")

    ===========================================================

    Internally Spring converts:

    ADMIN

    into

    ROLE_ADMIN

    ===========================================================

    Then compares:

    User Authorities

            with

    ROLE_ADMIN

    ===========================================================
                    INTERNAL FLOW
    ===========================================================

    hasRole("ADMIN")

            |

            v

    Convert

            |

            v

    ROLE_ADMIN

            |

            v

    Compare Authorities

            |

            v

    Access Granted

    ===========================================================
                    HASAUTHORITY()
    ===========================================================

    Example:

    hasAuthority("DELETE_USER")

    ===========================================================

    No Conversion.

    Direct comparison.

    ===========================================================

    Compare:

    DELETE_USER

            with

    DELETE_USER

    ===========================================================
                    INTERVIEW QUESTION
    ===========================================================

    Difference?

    hasRole("ADMIN")

    becomes

    ROLE_ADMIN

    ===========================================================

    hasAuthority("ADMIN")

    stays

    ADMIN

    ===========================================================

    Completely different.

    ===========================================================
                    EXAMPLE
    ===========================================================

    User Authorities:

    ROLE_ADMIN

    ===========================================================

    hasRole("ADMIN")

    SUCCESS

    ===========================================================

    hasAuthority("ADMIN")

    FAIL

    ===========================================================

    Because authority stored:

    ROLE_ADMIN

    not

    ADMIN

    ===========================================================
                AUTHORIZATION FLOW
    ===========================================================

    Request

          |

          v

    Authentication Complete

          |

          v

    SecurityContextHolder

          |

          v

    Authentication

          |

          v

    Authorities

          |

          v

    AuthorizationFilter

          |

          v

    AuthorizationManager

          |

          v

    Access Decision

    ===========================================================
                WHERE AUTHORITIES LIVE?
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

    Collection<GrantedAuthority>

    ===========================================================
                    OBJECT GRAPH
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

            |

            v

    Collection<GrantedAuthority>

            |

            v

    ROLE_ADMIN

    CREATE_USER

    DELETE_USER

    ===========================================================
                    ENTERPRISE RBAC
    ===========================================================

    RBAC

    Role Based Access Control

    ===========================================================

    User

         |

         v

    Role

         |

         v

    Permissions

    ===========================================================

    Example:

    ADMIN

         |

         +--> CREATE_USER

         |

         +--> DELETE_USER

         |

         +--> UPDATE_USER

         |

         +--> VIEW_USER

    ===========================================================

    During Login

    Role + Permissions loaded.

    Converted into:

    Collection<GrantedAuthority>

    ===========================================================
                    DATABASE DESIGN
    ===========================================================

    users

    roles

    permissions

    role_permissions

    user_roles

    ===========================================================

    Login Flow

    User

         |

         v

    Roles

         |

         v

    Permissions

         |

         v

    GrantedAuthority

    ===========================================================
                INTERNAL AUTHORIZATION
    ===========================================================

    Request

      |

      v

    AuthorizationFilter

      |

      v

    AuthorizationManager

      |

      v

    SecurityContextHolder

      |

      v

    Authentication

      |

      v

    getAuthorities()

      |

      v

    Compare Required Authority

      |

      v

    Allow / Deny

    ===========================================================
                    REAL EXAMPLE
    ===========================================================

    Endpoint:

    DELETE /users/1

    Requires:

    DELETE_USER

    ===========================================================

    Current User Authorities:

    VIEW_USER

    CREATE_USER

    ===========================================================

    AuthorizationManager

    checks

    DELETE_USER

            exists?

    ===========================================================

    No

    ===========================================================

    Response

    403 Forbidden

    ===========================================================
                    INTERVIEW QUESTIONS
    ===========================================================

    Q1. What is GrantedAuthority?

    Represents permission granted
    to authenticated user.

    ------------------------------------------------

    Q2. How many methods exist?

    One.

    getAuthority()

    ------------------------------------------------

    Q3. Default implementation?

    SimpleGrantedAuthority

    ------------------------------------------------

    Q4. Where authorities stored?

    Authentication object.

    ------------------------------------------------

    Q5. Role vs Authority?

    Role

    High Level Permission

    Authority

    Fine-Grained Permission

    ------------------------------------------------

    Q6. What does hasRole() do?

    Adds ROLE_ prefix.

    ------------------------------------------------

    Q7. What does hasAuthority() do?

    Direct comparison.

    ------------------------------------------------

    Q8. Why ROLE_ADMIN?

    Spring stores roles as authorities.

    ------------------------------------------------

    Q9. Where authorization reads permissions?

    Authentication.getAuthorities()

    ------------------------------------------------

    Q10. Why GrantedAuthority?

    Common internal permission model.

    ===========================================================
                    MEMORY TRICK
    ===========================================================

    UserDetails

          ↓

    Collection<GrantedAuthority>

          ↓

    Authentication

          ↓

    SecurityContext

          ↓

    SecurityContextHolder

          ↓

    AuthorizationFilter

          ↓

    AuthorizationManager

          ↓

    Access Decision

    ===========================================================
    */
}