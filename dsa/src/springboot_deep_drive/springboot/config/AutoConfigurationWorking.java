package springboot_deep_drive.springboot.config;

public class AutoConfigurationWorking {

    /*
    =============================================================================
                            SPRING BOOT AUTO CONFIGURATION
    =============================================================================

    Q) What is Auto Configuration?

    Auto Configuration is a feature of Spring Boot that automatically configures
    our application based on:

        1. Dependencies (JARs) present in the classpath
        2. Existing Beans
        3. Application properties (application.properties/yml)

    Because of Auto Configuration, we don't need to manually configure
    DataSource, DispatcherServlet, ObjectMapper, etc.

    -----------------------------------------------------------------------------
    WITHOUT SPRING BOOT
    -----------------------------------------------------------------------------

    Developer has to manually configure everything.

        @Configuration
        public class AppConfig {

            @Bean
            public DataSource dataSource() {
                ...
            }

            @Bean
            public EntityManagerFactory entityManagerFactory() {
                ...
            }

            @Bean
            public PlatformTransactionManager transactionManager() {
                ...
            }
        }

    -----------------------------------------------------------------------------
    WITH SPRING BOOT
    -----------------------------------------------------------------------------

    Add dependency

            spring-boot-starter-data-jpa

    Add properties

            spring.datasource.url=...
            spring.datasource.username=root
            spring.datasource.password=root

    That's it.

    Spring Boot automatically creates all required beans.

    =============================================================================
                    HOW DOES SPRING BOOT KNOW WHAT TO CONFIGURE?
    =============================================================================

    This is where AutoConfiguration.imports comes into the picture.

    =============================================================================
    WHAT IS AutoConfiguration.imports ?
    =============================================================================

    AutoConfiguration.imports is a file present inside Spring Boot's
    "spring-boot-autoconfigure.jar".

    Location:

        META-INF/
            spring/
                org.springframework.boot.autoconfigure.AutoConfiguration.imports

    -----------------------------------------------------------------------------

    This file DOES NOT contain code.

    It only contains a LIST of Auto Configuration Classes.

    Example:

        org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration

        org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration

        org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration

        org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration

        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration

    Think of it as:

                    "LIST OF CONFIGURATIONS"

    Spring Boot simply reads this list during startup.

    =============================================================================
                        COMPLETE STARTUP FLOW
    =============================================================================

            Application Starts
                    |
                    |
                    V
        All JARs are added to Classpath
                    |
                    |
                    V
        @SpringBootApplication
                    |
                    |
                    V
        @EnableAutoConfiguration
                    |
                    |
                    V
        AutoConfigurationImportSelector
                    |
                    |
                    V
        Reads AutoConfiguration.imports
                    |
                    |
                    V
        Gets list of Auto Configuration Classes
                    |
                    |
                    V
        Loads one class at a time
                    |
                    |
                    V
        Checks Conditions
            |
            |--- @ConditionalOnClass
            |--- @ConditionalOnBean
            |--- @ConditionalOnMissingBean
            |--- @ConditionalOnProperty
                    |
                    |
             Condition Matched ?
                 /          \
               NO            YES
               |              |
          Skip Class     Load Configuration
                               |
                               |
                               V
                        Execute @Bean methods
                               |
                               |
                               V
                     Beans registered into IOC
                               |
                               |
                               V
                    Application Ready

    =============================================================================
                    PRACTICAL EXAMPLE (SPRING DATA JPA)
    =============================================================================

    Step 1

    We add dependency

        spring-boot-starter-data-jpa

    -----------------------------------------------------------------------------

    Step 2

    Maven downloads required JARs.

            spring-data-jpa
            hibernate
            jdbc
            transaction
            spring-boot-autoconfigure
            ...

    -----------------------------------------------------------------------------

    Step 3

    During startup,

    Spring Boot reads

        AutoConfiguration.imports

    It finds

        DataSourceAutoConfiguration

    -----------------------------------------------------------------------------

    Step 4

    It now checks

        @ConditionalOnClass(DataSource.class)

    Question:

        Is DataSource class available?

    YES

    because JPA dependency is present.

    -----------------------------------------------------------------------------

    Step 5

    Next condition

        Is spring.datasource.url available?

    YES

    because we wrote

        spring.datasource.url=...

    -----------------------------------------------------------------------------

    Step 6

    All conditions passed.

    So Spring Boot executes

        @Bean
        public DataSource dataSource() {
            ...
        }

    -----------------------------------------------------------------------------

    Result

        DataSource Bean is created.

    We never created it manually.

    =============================================================================
                ANOTHER PRACTICAL EXAMPLE (SPRING MVC)
    =============================================================================

    Dependency

        spring-boot-starter-web

    Startup

            |
            V

    AutoConfiguration.imports

            |

    Finds

        WebMvcAutoConfiguration

            |

    Checks

        Is DispatcherServlet available?

            |

           YES

            |

    Creates

        DispatcherServlet Bean

        RequestMappingHandlerMapping

        HandlerAdapter

        MessageConverters

    Entire Spring MVC gets configured automatically.

    =============================================================================
            WHY DOES SPRING BOOT CHECK CONDITIONS?
    =============================================================================

    Suppose you DON'T use Spring Security.

    Then

        spring-security

    JAR is not present.

    Therefore

        SecurityAutoConfiguration

    Condition

        @ConditionalOnClass(SecurityFilterChain.class)

    becomes FALSE.

    Result

        Security configuration is skipped.

    This makes startup faster and avoids unnecessary beans.

    =============================================================================
                WHY AutoConfiguration.imports IS IMPORTANT?
    =============================================================================

    Imagine this file didn't exist.

    Spring Boot would have no idea

        Which configuration classes should be loaded.

    Developers would have to manually import everything.

        @Import({
            DataSourceAutoConfiguration.class,
            WebMvcAutoConfiguration.class,
            SecurityAutoConfiguration.class,
            HibernateJpaAutoConfiguration.class
        })

    This would defeat the purpose of Spring Boot.

    =============================================================================
                        INTERVIEW ANSWER
    =============================================================================

    AutoConfiguration.imports is a metadata file present inside
    spring-boot-autoconfigure.jar.

    It contains the fully qualified names of all Auto Configuration classes.

    During application startup,
    AutoConfigurationImportSelector reads this file,
    loads every configuration class,
    checks its @Conditional annotations,
    and if all conditions are satisfied,
    executes its @Bean methods and registers the beans inside the
    Spring IOC Container automatically.

    That's how Spring Boot performs Auto Configuration.

    =============================================================================
    */
}