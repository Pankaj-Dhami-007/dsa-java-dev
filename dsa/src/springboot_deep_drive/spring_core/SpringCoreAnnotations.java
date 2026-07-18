package springboot_deep_drive.spring_core;


/*

An annotation is a form of metadata that provides additional information about classes,
methods, fields, parameters, or other program elements.
Annotations do not directly affect program execution but can be processed by the compiler,
frameworks, or runtime environments to perform specific actions
 */

// Annotation is not the action; annotation is the instruction that tells someone else
// what action to perform.

/**
 * Annotation is metadata (information about code).
 *
 * It does not contain business logic.
 *
 * It provides instructions to the compiler,
 * framework, or runtime environment.
 *
 * Examples:
 *
 * @Override
 * @Component
 * @Service
 * @Autowired
 * @Bean
 *
 * An annotation itself does nothing.
 * The framework or compiler reads the annotation
 * and performs the required action.
 */


/**
 * =============================================================================
 * SPRING CORE ANNOTATIONS
 * =============================================================================
 *
 * Before learning Spring annotations,
 * we must first understand what an Annotation is.
 *
 *
 * =============================================================================
 * WHAT IS AN ANNOTATION?
 * =============================================================================
 *
 * An annotation is metadata about a program element.
 *
 * It provides additional information to the compiler,
 * framework, or runtime environment.
 *
 *
 * Example:
 *
 * @Override
 * public String toString() {
 *      return "Employee";
 * }
 *
 *
 * Here:
 *
 * @Override does not contain business logic.
 *
 * It only provides information that:
 *
 * "This method overrides a method from the parent class."
 *
 *
 * =============================================================================
 * WHAT DOES METADATA MEAN?
 * =============================================================================
 *
 * Metadata means:
 *
 * "Data about data"
 *
 *
 * Example:
 *
 * Class:
 *
 * public class PaymentService {
 * }
 *
 *
 * Metadata:
 *
 * @Component
 * public class PaymentService {
 * }
 *
 *
 * The class is actual data.
 *
 * @Component is metadata.
 *
 *
 * =============================================================================
 * WHY DOES SPRING USE ANNOTATIONS?
 * =============================================================================
 *
 * Before annotations became popular,
 * Spring applications were configured using XML.
 *
 *
 * Example:
 *
 * <bean id="paymentService"
 *       class="com.example.PaymentService"/>
 *
 *
 * Large applications contained thousands
 * of lines of XML configuration.
 *
 *
 * Problems:
 *
 * - Hard to maintain
 * - Difficult to read
 * - Configuration separated from code
 * - Error-prone
 *
 *
 * To solve these problems,
 * Spring introduced annotation-based configuration.
 *
 *
 * =============================================================================
 * XML VS ANNOTATIONS
 * =============================================================================
 *
 * XML Based:
 *
 * <bean id="paymentService"
 *       class="PaymentService"/>
 *
 *
 * Annotation Based:
 *
 * @Component
 * public class PaymentService {
 * }
 *
 *
 * Annotation-based configuration is:
 *
 * - Cleaner
 * - Easier
 * - More readable
 * - Less boilerplate
 *
 *
 * =============================================================================
 * HOW DOES SPRING PROCESS ANNOTATIONS?
 * =============================================================================
 *
 * Example:
 *
 * @Component
 * public class PaymentService {
 * }
 *
 *
 * During Component Scanning:
 *
 * Step 1:
 * Spring finds the annotation.
 *
 * Step 2:
 * Spring reads annotation metadata.
 *
 * Step 3:
 * Spring creates a Bean Definition.
 *
 * Step 4:
 * Spring creates the Bean.
 *
 * Step 5:
 * Spring stores the Bean in the Container.
 *
 *
 * =============================================================================
 * IMPORTANT SPRING CORE ANNOTATIONS
 * =============================================================================
 *
 * 1. @Configuration
 * 2. @ComponentScan
 * 3. @Component
 * 4. @Bean
 * 5. @Autowired
 * 6. @Primary
 * 7. @Qualifier
 * 8. @Scope
 * 9. @Lazy
 * 10. @Value
 *
 *
 * These annotations form the foundation
 * of Spring Core.
 *
 *
 * =============================================================================
 * @CONFIGURATION
 * =============================================================================
 *
 * Marks a class as a Spring Configuration Class.
 *
 *
 * Example:
 *
 * @Configuration
 * public class AppConfig {
 * }
 *
 *
 * Spring treats this class as a source
 * of Bean definitions.
 *
 *
 * =============================================================================
 * @COMPONENTSCAN
 * =============================================================================
 *
 * Tells Spring where to search for components.
 *
 *
 * Example:
 *
 * @ComponentScan(
 *      "com.company.service"
 * )
 *
 *
 * Spring scans the package and creates
 * Bean Definitions for discovered components.
 *
 *
 * =============================================================================
 * @COMPONENT
 * =============================================================================
 *
 * Marks a class as a Spring-managed component.
 *
 *
 * Example:
 *
 * @Component
 * public class PaymentService {
 * }
 *
 *
 * Spring automatically creates a Bean.
 *
 *
 * Default Bean Name:
 *
 * paymentService
 *
 *
 * =============================================================================
 * @BEAN
 * =============================================================================
 *
 * Used inside Configuration Classes.
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
 * Spring registers the returned object
 * as a Bean.
 *
 *
 * =============================================================================
 * @AUTOWIRED
 * =============================================================================
 *
 * Used for Dependency Injection.
 *
 *
 * Example:
 *
 * @Autowired
 * private PaymentService paymentService;
 *
 *
 * Spring automatically injects the dependency.
 *
 *
 * Modern Spring prefers Constructor Injection
 * instead of Field Injection.
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
 * =============================================================================
 * @PRIMARY
 * =============================================================================
 *
 * Used when multiple implementations exist.
 *
 *
 * Example:
 *
 * CardPaymentService
 * UpiPaymentService
 *
 *
 * Spring becomes confused because
 * multiple candidates are available.
 *
 *
 * @Primary tells Spring:
 *
 * "Use this Bean by default."
 *
 *
 * =============================================================================
 * @QUALIFIER
 * =============================================================================
 *
 * Used to select a specific Bean.
 *
 *
 * Example:
 *
 * @Qualifier("upiPaymentService")
 *
 *
 * Spring injects the specified Bean.
 *
 *
 * =============================================================================
 * @SCOPE
 * =============================================================================
 *
 * Defines Bean scope.
 *
 *
 * Example:
 *
 * @Scope("prototype")
 *
 *
 * Common scopes:
 *
 * singleton
 * prototype
 * request
 * session
 *
 *
 * Default scope:
 *
 * singleton
 *
 *
 * =============================================================================
 * @LAZY
 * =============================================================================
 *
 * Prevents Bean creation during container startup.
 *
 *
 * Bean will be created only when needed.
 *
 *
 * Example:
 *
 * @Lazy
 * @Component
 * public class ReportService {
 * }
 *
 *
 * =============================================================================
 * @VALUE
 * =============================================================================
 *
 * Injects values into Beans.
 *
 *
 * Example:
 *
 * @Value("${db.url}")
 * private String databaseUrl;
 *
 *
 * Spring reads the value from
 * configuration properties.
 *
 *
 * =============================================================================
 * ANNOTATION HIERARCHY
 * =============================================================================
 *
 * Spring Core Startup
 *          |
 *          V
 *
 * @Configuration
 *          |
 *          V
 *
 * @ComponentScan
 *          |
 *          V
 *
 * Find @Component Classes
 *          |
 *          V
 *
 * Create Bean Definitions
 *          |
 *          V
 *
 * Create Beans
 *          |
 *          V
 *
 * Resolve Dependencies
 *          |
 *          V
 *
 * @Autowired
 * @Qualifier
 * @Primary
 *          |
 *          V
 *
 * Store Beans In IoC Container
 *
 *
 * =============================================================================
 * INTERVIEW QUESTION
 * =============================================================================
 *
 * Q. Why are Spring annotations used?
 *
 * Answer:
 *
 * Spring annotations provide metadata that helps the
 * Spring Container identify components, create bean
 * definitions, perform dependency injection, configure
 * bean behavior, and reduce XML-based configuration.
 *
 *
 * =============================================================================
 * INTERVIEW QUESTION
 * =============================================================================
 *
 * Q. Which annotations are most important in Spring Core?
 *
 * Answer:
 *
 * @Configuration
 * @ComponentScan
 * @Component
 * @Bean
 * @Autowired
 * @Qualifier
 * @Primary
 * @Scope
 * @Lazy
 * @Value
 *
 * =============================================================================
 */
public class SpringCoreAnnotations {
}