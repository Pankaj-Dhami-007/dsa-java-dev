package springboot_deep_drive.spring_core;

/**
 * =============================================================================
 * SPRING CORE IMPLEMENTATION - PART 1
 * =============================================================================
 *
 * In the previous example (BeforeSpringCore.java),
 * we manually created and managed all objects.
 *
 * Spring Core was introduced to solve this problem.
 *
 * =============================================================================
 * WHAT IS SPRING?
 * =============================================================================
 *
 * Spring is a lightweight Java framework used for building
 * enterprise-level applications.
 *
 * It provides infrastructure support so developers can focus
 * on business logic instead of object management.
 *
 *
 * =============================================================================
 * SPRING FRAMEWORK MODULES
 * =============================================================================
 *
 * Spring Framework consists of multiple modules:
 *
 * 1. Spring Core
 * 2. Spring Beans
 * 3. Spring Context
 * 4. Spring Expression Language (SpEL)
 * 5. Spring AOP
 * 6. Spring JDBC
 * 7. Spring ORM
 * 8. Spring MVC
 * 9. Spring Security
 * 10. Spring Boot
 *
 *
 * =============================================================================
 * WHAT IS SPRING CORE?
 * =============================================================================
 *
 * Spring Core is the foundation of the Spring Framework.
 *
 * It provides:
 *
 * 1. IoC (Inversion of Control)
 * 2. Dependency Injection (DI)
 * 3. Bean Management
 * 4. Container Support
 *
 * Without Spring Core, the rest of Spring Framework
 * cannot function properly.
 *
 *
 * =============================================================================
 * WHY DO WE NEED SPRING CORE?
 * =============================================================================
 *
 * Consider the previous example:
 *
 * PaymentService paymentService =
 *      new PaymentService();
 *
 * NotificationService notificationService =
 *      new NotificationService();
 *
 * OrderService orderService =
 *      new OrderService(
 *          paymentService,
 *          notificationService
 *      );
 *
 * Here, developers are responsible for:
 *
 * - Creating objects
 * - Managing objects
 * - Connecting objects
 *
 * Spring Core removes this responsibility.
 *
 *
 * =============================================================================
 * WHAT IS INVERSION OF CONTROL (IoC)?
 * =============================================================================
 *
 * Traditional Java:
 *
 * Developer controls object creation.
 *
 * Example:
 *
 * PaymentService paymentService =
 *      new PaymentService();
 *
 *
 * With Spring:
 *
 * Spring Container controls object creation.
 *
 * The control of object creation is transferred
 * from the developer to the Spring Container.
 *
 * This transfer of control is called:
 *
 * Inversion of Control (IoC)
 *
 *
 * =============================================================================
 * WHAT IS DEPENDENCY INJECTION (DI)?
 * =============================================================================
 *
 * Dependency Injection is a design pattern where
 * dependencies are provided from outside the class.
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
 * Benefits:
 *
 * - Loose Coupling
 * - Better Testability
 * - Better Maintainability
 * - Easier Object Management
 *
 *
 * =============================================================================
 * WHAT IS A SPRING BEAN?
 * =============================================================================
 *
 * Any object managed by the Spring Container
 * is called a Bean.
 *
 *
 * Example:
 *
 * PaymentService
 *
 * If created using:
 *
 * new PaymentService()
 *
 * -> Normal Java Object
 *
 *
 * If created by Spring Container:
 *
 * -> Spring Bean
 *
 *
 * =============================================================================
 * WHAT IS AN IoC CONTAINER?
 * =============================================================================
 *
 * The IoC Container is responsible for:
 *
 * 1. Creating Beans
 * 2. Managing Beans
 * 3. Injecting Dependencies
 * 4. Managing Bean Lifecycle
 *
 *
 * Popular Container Implementations:
 *
 * 1. BeanFactory
 * 2. ApplicationContext
 *
 *
 * In real-world applications,
 * ApplicationContext is commonly used.
 *
 *
 * =============================================================================
 * HOW TO CREATE A SPRING CORE PROJECT?
 * =============================================================================
 *
 * Step 1:
 * Create a Maven Project.
 *
 *
 * Step 2:
 * Add Spring Dependency.
 *
 *
 * Maven Dependency:
 *
 * <dependency>
 *      <groupId>org.springframework</groupId>
 *      <artifactId>spring-context</artifactId>
 *      <version>6.x.x</version>
 * </dependency>
 *
 *
 * =============================================================================
 * INTERVIEW QUESTION
 * =============================================================================
 *
 * Q: Why do we add spring-context dependency instead of spring-core?
 *
 * Answer:
 *
 * spring-context automatically brings:
 *
 * - spring-core
 * - spring-beans
 * - spring-expression
 * - spring-context
 *
 * Therefore, most Spring applications use
 * spring-context directly.
 *
 *
 * =============================================================================
 * INTERVIEW QUESTION
 * =============================================================================
 *
 * Q: What problem does Spring Core solve?
 *
 * Answer:
 *
 * Spring Core eliminates manual object creation
 * and dependency management by introducing
 * the IoC Container and Dependency Injection.
 *
 *
 * =============================================================================
 * NEXT TOPIC
 * =============================================================================
 *
 * In the next file we will implement:
 *
 * 1. BeanFactory
 * 2. ApplicationContext
 * 3. @Component
 * 4. @Configuration
 * 5. @Bean
 * 6. Real Dependency Injection using Spring
 * 7. Converting our OrderService example into Spring-managed Beans
 *
 * =============================================================================
 */

public class SpringCoreImplementationConcept {

}