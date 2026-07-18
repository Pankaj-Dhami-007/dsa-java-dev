package java_recap.springboot.annotations.revision;

/*

#################################################################
        SPRING BOOT ANNOTATIONS QUICK REVISION
#################################################################

=================================================================
                STEREOTYPE ANNOTATIONS
=================================================================

@Component
------------
Generic Spring-managed bean.

@Service
----------
Business logic layer bean.

@Repository
-------------
Persistence layer bean with exception translation.

@Controller
-------------
MVC controller returning views.

@RestController
----------------
REST controller returning JSON/XML.

=================================================================
                DEPENDENCY INJECTION
=================================================================

@Autowired
-------------
Automatically inject dependency.

@Qualifier
-------------
Select specific bean among multiple beans.

@Primary
-----------
Default bean when multiple candidates exist.

@Lazy
--------
Delay bean initialization until needed.

=================================================================
                CONFIGURATION
=================================================================

@Configuration
----------------
Defines Spring configuration class.

@Bean
-------
Registers method return object as Spring Bean.

@Value
--------
Inject property/configuration value.

@PropertySource
----------------
Load external properties file.

=================================================================
                VALIDATION
=================================================================

@Valid
--------
Triggers object validation.

@Validated
-------------
Advanced validation with groups/method validation.

@NotNull
-----------
Field must not be null.

@NotEmpty
------------
Field must not be null or empty.

@NotBlank
------------
Field must not be null, empty, or blank spaces.

@Size
-------
Validates length/collection size.

@Min
------
Minimum numeric value.

@Max
------
Maximum numeric value.

@Positive
-----------
Value must be greater than 0.

@Negative
-----------
Value must be less than 0.

@Email
--------
Valid email format.

@Pattern
----------
Regex-based validation.

@Past
-------
Date must be in past.

@Future
---------
Date must be in future.

=================================================================
                EXCEPTION HANDLING
=================================================================

@ExceptionHandler
------------------
Handles specific exceptions.

@ControllerAdvice
-------------------
Global exception handler for MVC.

@RestControllerAdvice
-----------------------
Global JSON exception handler.

@ResponseStatus
----------------
Defines HTTP status for exception/response.

=================================================================
                JPA / HIBERNATE
=================================================================

@Entity
---------
Maps class to database table.

@Table
--------
Custom table configuration.

@Id
-----
Primary key field.

@GeneratedValue
----------------
Automatic ID generation.

@Column
---------
Custom column mapping.

@Transient
------------
Exclude field from DB persistence.

=================================================================
                RELATIONSHIP MAPPING
=================================================================

@OneToOne
-----------
One-to-one relationship mapping.

@OneToMany
------------
One-to-many relationship mapping.

@ManyToOne
------------
Many-to-one relationship mapping.

@ManyToMany
-------------
Many-to-many relationship mapping.

@JoinColumn
-------------
Defines foreign key column.

@JoinTable
------------
Defines join/intermediate table.

=================================================================
                HIBERNATE SPECIAL
=================================================================

@CreationTimestamp
-------------------
Auto-set creation timestamp.

@UpdateTimestamp
-----------------
Auto-update modification timestamp.

@Enumerated
-------------
Maps enum to DB value.

@Lob
------
Stores large text/binary data.

@Embeddable
-------------
Reusable value object.

@Embedded
-----------
Embeds value object into entity.

=================================================================
                TRANSACTIONS
=================================================================

@Transactional
----------------
Executes method inside DB transaction.

@EnableTransactionManagement
-----------------------------
Enables transaction infrastructure.

=================================================================
                AOP
=================================================================

@Aspect
---------
Defines aspect class.

@Before
---------
Runs before method execution.

@After
--------
Runs after method execution.

@AfterReturning
----------------
Runs after successful execution.

@AfterThrowing
----------------
Runs when exception occurs.

@Around
---------
Controls entire method execution.

@Pointcut
-----------
Reusable interception expression.

@EnableAspectJAutoProxy
------------------------
Enables Spring AOP proxies.

=================================================================
                SECURITY
=================================================================

@EnableWebSecurity
-------------------
Enables Spring Security infrastructure.

@PreAuthorize
---------------
Method-level authorization using expressions.

@Secured
----------
Role-based authorization.

@RolesAllowed
---------------
Java standard role authorization.

=================================================================
                CACHING
=================================================================

@EnableCaching
----------------
Enables cache infrastructure.

@Cacheable
------------
Stores method result in cache.

@CachePut
-----------
Updates cache after execution.

@CacheEvict
-------------
Removes cache entries.

=================================================================
                ASYNC
=================================================================

@EnableAsync
--------------
Enables async execution support.

@Async
--------
Runs method in background thread.

=================================================================
                SCHEDULING
=================================================================

@EnableScheduling
------------------
Enables task scheduling infrastructure.

@Scheduled
------------
Executes methods automatically on schedule.

=================================================================
                TESTING
=================================================================

@SpringBootTest
----------------
Loads full Spring Boot application context.

@MockBean
-----------
Replaces real bean with mock.

@WebMvcTest
-------------
Loads only MVC/controller layer.

@DataJpaTest
--------------
Loads only JPA/repository layer.

=================================================================
                PROFILES & CONDITIONALS
=================================================================

@Profile
----------
Loads bean for specific environment profile.

@Conditional
--------------
Custom condition-based bean loading.

@ConditionalOnProperty
-----------------------
Loads bean based on property value.

@ConditionalOnMissingBean
--------------------------
Creates bean only if missing.

=================================================================
                EVENTS
=================================================================

@EventListener
---------------
Handles published application events.

@TransactionalEventListener
----------------------------
Handles events based on transaction phase.

#################################################################
                MOST IMPORTANT INTERNALS
#################################################################

Spring IOC Container
----------------------
Manages beans and dependency injection.

Spring AOP Proxy
-----------------
Used internally by:
1. @Transactional
2. @Async
3. @Cacheable
4. Security
5. AOP

Hibernate Persistence Context
------------------------------
Tracks entity lifecycle and dirty checking.

DispatcherServlet
------------------
Front controller of Spring MVC.

Security Filter Chain
----------------------
Handles Spring Security request flow.

ApplicationEventPublisher
---------------------------
Publishes application events.

#################################################################
                MOST IMPORTANT INTERVIEW FACTS
#################################################################

1. Spring uses proxy-based AOP internally.

2. Self-invocation breaks:
   - @Transactional
   - @Async
   - @Cacheable

3. Constructor injection preferred.

4. LAZY loading preferred in JPA.

5. EnumType.STRING preferred over ORDINAL.

6. @RestController =
   @Controller + @ResponseBody

7. @Service/@Repository/@Controller
   internally extend @Component.

8. Spring Boot auto-configuration heavily uses:
   - @ConditionalOnProperty
   - @ConditionalOnMissingBean

9. @Transactional rolls back RuntimeException by default.

10. Spring Security:
    - 401 → Unauthenticated
    - 403 → Unauthorized

#################################################################

*/

public class SpringBootAnnotationsQuickRevision {
}