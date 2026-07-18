package springboot_deep_drive.spring_core;

/**
 * =============================================================================
 * SPRING CORE ANNOTATIONS - COMPLETE GUIDE
 * =============================================================================
 *
 * Spring Core is built around metadata-driven programming.
 *
 * Metadata is provided through annotations.
 *
 * These annotations help Spring:
 *
 * 1. Discover classes
 * 2. Create Bean Definitions
 * 3. Create Bean Objects
 * 4. Inject Dependencies
 * 5. Configure Bean Lifecycle
 * 6. Manage Bean Scope
 *
 * =============================================================================
 * CLASSIFICATION OF SPRING CORE ANNOTATIONS
 * =============================================================================
 *
 * 1. Configuration Annotations
 * 2. Stereotype Annotations
 * 3. Dependency Injection Annotations
 * 4. Bean Configuration Annotations
 * 5. Bean Scope Annotations
 * 6. Bean Initialization Annotations
 * 7. Value Injection Annotations
 *
 * =============================================================================
 * 1. @Configuration
 * =============================================================================
 *
 * Package:
 * org.springframework.context.annotation.Configuration
 *
 * Definition:
 *
 * Marks a class as a Spring Configuration Class.
 *
 * A configuration class contains metadata that Spring
 * uses to create and configure Beans.
 *
 *
 * Why do we need it?
 *
 * Spring needs a starting point to understand
 * application configuration.
 *
 *
 * Example:
 *
 * @Configuration
 * public class AppConfig {
 * }
 *
 *
 * Internal Working:
 *
 * Spring reads this class while creating
 * the ApplicationContext.
 *
 *
 * Interview Point:
 *
 * @Configuration is a specialized form of @Component.
 *
 *
 * =============================================================================
 * 2. @ComponentScan
 * =============================================================================
 *
 * Package:
 * org.springframework.context.annotation.ComponentScan
 *
 * Definition:
 *
 * Tells Spring where to search for Components.
 *
 *
 * Example:
 *
 * @ComponentScan(
 *      "com.company.service"
 * )
 *
 *
 * Why do we need it?
 *
 * Without Component Scanning,
 * Spring would not know which classes
 * should become Beans.
 *
 *
 * Internal Working:
 *
 * Spring scans packages recursively.
 *
 * It searches for:
 *
 * @Component
 * @Service
 * @Repository
 * @Controller
 *
 *
 * =============================================================================
 * 3. @Component
 * =============================================================================
 *
 * Package:
 * org.springframework.stereotype.Component
 *
 * Definition:
 *
 * Marks a class as a Spring-managed Component.
 *
 *
 * Example:
 *
 * @Component
 * public class PaymentService {
 * }
 *
 *
 * Why do we need it?
 *
 * Spring must know that this class
 * should become a Bean.
 *
 *
 * Internal Working:
 *
 * Component Scanner finds this annotation.
 *
 * Spring creates:
 *
 * Bean Definition
 *      ↓
 * Bean Object
 *      ↓
 * Stores Bean in IoC Container
 *
 *
 * Default Bean Name:
 *
 * paymentService
 *
 *
 * =============================================================================
 * 4. @Service
 * =============================================================================
 *
 * Package:
 * org.springframework.stereotype.Service
 *
 * Definition:
 *
 * Specialized version of @Component.
 *
 * Represents Business Logic Layer.
 *
 *
 * Example:
 *
 * @Service
 * public class OrderService {
 * }
 *
 *
 * Why use it instead of @Component?
 *
 * Better readability.
 *
 * Developers immediately understand
 * this class belongs to Service Layer.
 *
 *
 * Internal Working:
 *
 * Functionally identical to @Component.
 *
 *
 * =============================================================================
 * 5. @Repository
 * =============================================================================
 *
 * Package:
 * org.springframework.stereotype.Repository
 *
 * Definition:
 *
 * Represents Data Access Layer.
 *
 *
 * Example:
 *
 * @Repository
 * public class UserRepository {
 * }
 *
 *
 * Why use it?
 *
 * Indicates database-related operations.
 *
 *
 * Additional Benefit:
 *
 * Spring translates database exceptions
 * into Spring DataAccessException hierarchy.
 *
 *
 * =============================================================================
 * 6. @Autowired
 * =============================================================================
 *
 * Package:
 * org.springframework.beans.factory.annotation.Autowired
 *
 * Definition:
 *
 * Performs automatic Dependency Injection.
 *
 *
 * Example:
 *
 * @Autowired
 * private PaymentService paymentService;
 *
 *
 * Modern Preferred Approach:
 *
 * Constructor Injection
 *
 *
 * Example:
 *
 * public OrderService(
 *      PaymentService paymentService
 * ) {
 *      this.paymentService = paymentService;
 * }
 *
 *
 * Internal Working:
 *
 * Spring searches the container
 * for a matching Bean.
 *
 * If found:
 *
 * Dependency is injected.
 *
 *
 * =============================================================================
 * 7. @Bean
 * =============================================================================
 *
 * Package:
 * org.springframework.context.annotation.Bean
 *
 * Definition:
 *
 * Registers a Bean manually.
 *
 *
 * Example:
 *
 * @Bean
 * public PaymentService paymentService() {
 *      return new PaymentService();
 * }
 *
 *
 * Why do we need it?
 *
 * Sometimes source code cannot be modified.
 *
 * Example:
 *
 * Third-party library classes.
 *
 *
 * Internal Working:
 *
 * Spring executes the method.
 *
 * Returned object becomes a Bean.
 *
 *
 * =============================================================================
 * 8. @Primary
 * =============================================================================
 *
 * Package:
 * org.springframework.context.annotation.Primary
 *
 * Definition:
 *
 * Marks one Bean as the default candidate.
 *
 *
 * Example:
 *
 * CardPaymentService
 * UpiPaymentService
 *
 *
 * Spring becomes confused.
 *
 * @Primary resolves ambiguity.
 *
 *
 * Example:
 *
 * @Primary
 * @Component
 * public class UpiPaymentService
 *      implements PaymentService {
 * }
 *
 *
 * =============================================================================
 * 9. @Qualifier
 * =============================================================================
 *
 * Package:
 * org.springframework.beans.factory.annotation.Qualifier
 *
 * Definition:
 *
 * Selects a specific Bean.
 *
 *
 * Example:
 *
 * @Qualifier("upiPaymentService")
 *
 *
 * Why do we need it?
 *
 * Multiple implementations may exist.
 *
 *
 * Example:
 *
 * PaymentService
 *      |
 *      +-- CardPaymentService
 *      +-- UpiPaymentService
 *
 *
 * @Qualifier tells Spring exactly
 * which implementation should be injected.
 *
 *
 * =============================================================================
 * 10. @Scope
 * =============================================================================
 *
 * Package:
 * org.springframework.context.annotation.Scope
 *
 * Definition:
 *
 * Defines Bean lifecycle scope.
 *
 *
 * Example:
 *
 * @Scope("prototype")
 * @Component
 * public class ReportService {
 * }
 *
 *
 * Common Scopes:
 *
 * singleton
 * prototype
 *
 *
 * Web Scopes:
 *
 * request
 * session
 * application
 *
 *
 * Default Scope:
 *
 * singleton
 *
 *
 * =============================================================================
 * 11. @Lazy
 * =============================================================================
 *
 * Package:
 * org.springframework.context.annotation.Lazy
 *
 * Definition:
 *
 * Delays Bean creation until first use.
 *
 *
 * Example:
 *
 * @Lazy
 * @Component
 * public class LargeReportService {
 * }
 *
 *
 * Why do we need it?
 *
 * Reduce startup time.
 *
 *
 * Default Behavior:
 *
 * Spring creates singleton Beans
 * during container startup.
 *
 *
 * @Lazy changes this behavior.
 *
 *
 * =============================================================================
 * 12. @Value
 * =============================================================================
 *
 * Package:
 * org.springframework.beans.factory.annotation.Value
 *
 * Definition:
 *
 * Injects external values into Beans.
 *
 *
 * Example:
 *
 * @Value("${db.url}")
 * private String databaseUrl;
 *
 *
 * Why do we need it?
 *
 * Avoid hardcoded values.
 *
 *
 * Common Sources:
 *
 * application.properties
 * application.yml
 * Environment Variables
 * System Properties
 *
 *
 * =============================================================================
 * 13. @DependsOn
 * =============================================================================
 *
 * Package:
 * org.springframework.context.annotation.DependsOn
 *
 * Definition:
 *
 * Forces Spring to create one Bean
 * before another Bean.
 *
 *
 * Example:
 *
 * @DependsOn("databaseConnection")
 * public class UserService {
 * }
 *
 *
 * Why do we need it?
 *
 * Explicit initialization order.
 *
 *
 * =============================================================================
 * 14. @Description
 * =============================================================================
 *
 * Package:
 * org.springframework.context.annotation.Description
 *
 * Definition:
 *
 * Adds descriptive information to a Bean.
 *
 *
 * Example:
 *
 * @Description(
 *      "Handles payment processing"
 * )
 *
 *
 * Mostly used for documentation.
 *
 *
 * =============================================================================
 * CORE ANNOTATION HIERARCHY
 * =============================================================================
 *
 * @Configuration
 *        |
 *        V
 * @ComponentScan
 *        |
 *        V
 * Finds:
 *
 * @Component
 * @Service
 * @Repository
 *
 *        |
 *        V
 * Creates Bean Definitions
 *        |
 *        V
 * Creates Beans
 *        |
 *        V
 *
 * Dependency Injection
 *
 * @Autowired
 * @Qualifier
 * @Primary
 *
 *        |
 *        V
 *
 * Bean Lifecycle Control
 *
 * @Scope
 * @Lazy
 * @DependsOn
 *
 *        |
 *        V
 *
 * Configuration Values
 *
 * @Value
 *
 *
 * =============================================================================
 * MOST IMPORTANT INTERVIEW QUESTION
 * =============================================================================
 *
 * Q. Which annotations are most important in Spring Core?
 *
 * Answer:
 *
 * @Configuration
 * @ComponentScan
 * @Component
 * @Service
 * @Repository
 * @Autowired
 * @Bean
 * @Qualifier
 * @Primary
 * @Scope
 * @Lazy
 * @Value
 * @DependsOn
 *
 *
 * These annotations form the foundation of
 * Spring Core and Dependency Injection.
 *
 * =============================================================================
 */
public class SpringCoreAnnotations_2 {
}