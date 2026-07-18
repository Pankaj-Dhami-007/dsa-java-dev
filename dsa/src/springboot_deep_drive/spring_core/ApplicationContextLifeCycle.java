package springboot_deep_drive.spring_core;

/**
 * =============================================================================
 * APPLICATION CONTEXT (IOC CONTAINER) INTERNAL WORKFLOW
 * =============================================================================
 *
 * Most developers write only this line:
 *
 * ApplicationContext context =
 *      new AnnotationConfigApplicationContext(AppConfig.class);
 *
 * But internally Spring performs many operations before
 * the application becomes ready.
 *
 * Understanding these steps is extremely important because
 * they explain how Spring manages objects and dependencies.
 *
 * =============================================================================
 * WHAT IS A BEAN DEFINITION?
 * =============================================================================
 *
 * Bean Definition is the metadata (information)
 * that describes a bean.
 *
 * It is NOT the actual object.
 *
 * Spring first creates Bean Definitions and later
 * uses them to create actual Bean objects.
 *
 *
 * Example:
 *
 * @Component
 * public class PaymentServiceImpl {
 * }
 *
 *
 * Spring creates metadata similar to:
 *
 * Bean Name:
 * paymentServiceImpl
 *
 * Bean Class:
 * PaymentServiceImpl.class
 *
 * Scope:
 * singleton
 *
 * Dependencies:
 * none
 *
 *
 * This metadata is called Bean Definition.
 *
 *
 * =============================================================================
 * BEAN DEFINITION VS BEAN OBJECT
 * =============================================================================
 *
 * Bean Definition
 * ----------------
 * Blueprint
 * Metadata
 * Description
 *
 *
 * Bean Object
 * -----------
 * Actual object
 * Created in memory
 * Used by application
 *
 *
 * Example:
 *
 * Bean Definition
 *      ↓
 * PaymentServiceImpl.class
 *
 *
 * Bean Object
 *      ↓
 * new PaymentServiceImpl()
 *
 *
 * =============================================================================
 * STEP 1 : SPRING STARTS THE IOC CONTAINER
 * =============================================================================
 *
 * ApplicationContext context =
 *      new AnnotationConfigApplicationContext(AppConfig.class);
 *
 * Spring starts its IoC Container.
 *
 * From this point onward,
 * Spring becomes responsible for managing objects.
 *
 *
 * =============================================================================
 * STEP 2 : SPRING READS CONFIGURATION METADATA
 * =============================================================================
 *
 * Example:
 *
 * @Configuration
 * @ComponentScan("springboot_deep_drive.spring_core.impl")
 * public class AppConfig {
 * }
 *
 *
 * Spring reads this configuration class.
 *
 * This class tells Spring:
 *
 * - Which packages should be scanned
 * - Which beans should be created
 * - How the application should be configured
 *
 *
 * =============================================================================
 * STEP 3 : SPRING PROCESSES COMPONENT SCANNING
 * =============================================================================
 *
 * Spring reads:
 *
 * @ComponentScan(
 *      "springboot_deep_drive.spring_core.impl"
 * )
 *
 *
 * Then it starts scanning all classes
 * inside the specified package.
 *
 *
 * =============================================================================
 * STEP 4 : SPRING FINDS COMPONENT CLASSES
 * =============================================================================
 *
 * Spring searches for:
 *
 * @Component
 * @Service
 * @Repository
 * @Controller
 *
 *
 * Example:
 *
 * @Component
 * public class PaymentServiceImpl
 *
 *
 * @Component
 * public class NotificationServiceImpl
 *
 *
 * @Component
 * public class OrderService
 *
 *
 * These classes become Bean Candidates.
 *
 *
 * =============================================================================
 * STEP 5 : SPRING CREATES BEAN DEFINITIONS
 * =============================================================================
 *
 * Spring creates metadata for every Bean Candidate.
 *
 *
 * PaymentServiceImpl
 *
 * Bean Name:
 * paymentServiceImpl
 *
 * Bean Class:
 * PaymentServiceImpl.class
 *
 * Scope:
 * singleton
 *
 * Dependencies:
 * none
 *
 *
 * OrderService
 *
 * Bean Name:
 * orderService
 *
 * Bean Class:
 * OrderService.class
 *
 * Scope:
 * singleton
 *
 * Dependencies:
 *
 * - PaymentService
 * - NotificationService
 *
 *
 * No objects are created yet.
 *
 * Spring is only preparing blueprints.
 *
 *
 * =============================================================================
 * STEP 6 : SPRING CREATES BEAN OBJECTS
 * =============================================================================
 *
 * Using Bean Definitions,
 * Spring starts creating actual objects.
 *
 *
 * Equivalent Java Code:
 *
 * PaymentService payment =
 *      new PaymentServiceImpl();
 *
 *
 * NotificationService notification =
 *      new NotificationServiceImpl();
 *
 *
 * Spring performs this automatically.
 *
 *
 * =============================================================================
 * STEP 7 : SPRING RESOLVES DEPENDENCIES
 * =============================================================================
 *
 * OrderService requires:
 *
 * PaymentService
 * NotificationService
 *
 *
 * Spring checks the container:
 *
 * "Do these beans exist?"
 *
 * YES
 *
 *
 * Spring injects them automatically.
 *
 *
 * Equivalent Java:
 *
 * new OrderService(
 *      payment,
 *      notification
 * );
 *
 *
 * This process is called:
 *
 * Dependency Injection (DI)
 *
 *
 * =============================================================================
 * STEP 8 : SPRING STORES BEANS INSIDE THE CONTAINER
 * =============================================================================
 *
 * After creation,
 * all beans are stored inside the IoC Container.
 *
 *
 * Container
 *
 * -------------------------
 * paymentServiceImpl
 * notificationServiceImpl
 * orderService
 * -------------------------
 *
 *
 * =============================================================================
 * STEP 9 : APPLICATION REQUESTS A BEAN
 * =============================================================================
 *
 * OrderService orderService =
 *      context.getBean(OrderService.class);
 *
 *
 * Spring searches the container
 * and returns the already created Bean.
 *
 *
 * It does NOT create a new object every time.
 *
 *
 * =============================================================================
 * COMPLETE FLOW
 * =============================================================================
 *
 * Application Starts
 *          |
 *          V
 *
 * Create ApplicationContext
 *          |
 *          V
 *
 * Read AppConfig
 *          |
 *          V
 *
 * Process @ComponentScan
 *          |
 *          V
 *
 * Find Component Classes
 *          |
 *          V
 *
 * Create Bean Definitions
 *          |
 *          V
 *
 * Create Bean Objects
 *          |
 *          V
 *
 * Resolve Dependencies
 *          |
 *          V
 *
 * Store Beans In Container
 *          |
 *          V
 *
 * Application Calls getBean()
 *          |
 *          V
 *
 * Spring Returns Existing Bean
 *
 *
 * =============================================================================
 * INTERVIEW QUESTION
 * =============================================================================
 *
 * Q. What is a Bean Definition?
 *
 * Answer:
 *
 * A Bean Definition is the metadata representation of a bean.
 * It contains information such as bean name, bean class,
 * scope, dependencies, initialization methods and lifecycle
 * configuration. Spring first creates Bean Definitions and
 * then uses them to instantiate actual bean objects.
 *
 * =============================================================================
 */
public class ApplicationContextLifeCycle {
}