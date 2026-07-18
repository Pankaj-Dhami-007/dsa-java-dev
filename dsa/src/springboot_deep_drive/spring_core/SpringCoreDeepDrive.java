package springboot_deep_drive.spring_core;

/**
 * ============================================================================
 *                          SPRING CORE DEEP DIVE
 * ============================================================================
 *
 * ============================================================================
 * 1. WHAT IS SPRING CORE?
 * ============================================================================
 *
 * Spring Core is the foundation module of the Spring Framework.
 *
 * It provides:
 *  - Dependency Injection (DI)
 *  - Inversion of Control (IoC)
 *  - Bean Management
 *  - Object Lifecycle Management
 *
 * In simple words:
 *
 * Spring Core is responsible for creating objects, managing objects,
 * connecting objects, and destroying objects when they are no longer needed.
 *
 *
 * ============================================================================
 * 2. BEFORE SPRING - HOW PURE JAVA APPLICATIONS WORKED?
 * ============================================================================
 *
 * Imagine an E-Commerce Application:
 *
 * CustomerService -> uses -> EmailService
 *
 * Without Spring:
 *
 * public class CustomerService {
 *
 *     private EmailService emailService = new EmailService();
 *
 * }
 *
 * What happens here?
 *
 * CustomerService itself is creating EmailService object.
 *
 * CustomerService and EmailService become tightly coupled.
 *
 *
 * ============================================================================
 * 3. WHAT IS THE PROBLEM WITH THIS APPROACH?
 * ============================================================================
 *
 * Problem #1 : Tight Coupling
 *
 * CustomerService directly depends upon EmailService implementation.
 *
 * If tomorrow:
 *
 * EmailService -> SmsService
 *
 * We need to modify CustomerService code.
 *
 *
 * Problem #2 : Difficult Testing
 *
 * Suppose EmailService sends real emails.
 *
 * During testing we don't want real emails.
 *
 * We want:
 *
 * FakeEmailService
 *
 * But CustomerService itself creates EmailService object.
 *
 * Therefore replacing dependency becomes difficult.
 *
 *
 * Problem #3 : Object Creation Everywhere
 *
 * Every class creates its own objects.
 *
 * new A()
 * new B()
 * new C()
 * new D()
 *
 * Managing thousands of objects becomes messy.
 *
 *
 * Problem #4 : Scalability Issues
 *
 * In large projects:
 *
 * - Thousands of classes
 * - Hundreds of dependencies
 * - Multiple environments
 *
 * Manual object management becomes nightmare.
 *
 *
 * ============================================================================
 * 4. HOW SPRING SOLVES THIS?
 * ============================================================================
 *
 * Spring says:
 *
 * "Don't create objects yourself.
 *  Give me the responsibility."
 *
 * Instead of:
 *
 * CustomerService service = new CustomerService();
 *
 * Spring creates object.
 *
 * Spring manages object.
 *
 * Spring injects dependencies.
 *
 * Spring destroys object.
 *
 *
 * This idea is called:
 *
 * Inversion Of Control (IoC)
 *
 *
 * ============================================================================
 * 5. WHAT IS INVERSION OF CONTROL (IOC)?
 * ============================================================================
 *
 * Definition:
 *
 * Inversion Of Control means transferring the responsibility of object
 * creation and management from application code to Spring Container.
 *
 *
 * Without IoC:
 *
 * Developer controls object creation.
 *
 * With IoC:
 *
 * Spring controls object creation.
 *
 *
 * Example:
 *
 * Before:
 *
 * CustomerService service = new CustomerService();
 *
 *
 * After:
 *
 * CustomerService service =
 *      springContainer.getBean(CustomerService.class);
 *
 *
 * Spring creates object.
 * Spring gives object.
 *
 *
 * ============================================================================
 * 6. WHAT IS DEPENDENCY?
 * ============================================================================
 *
 * A dependency is simply an object required by another object.
 *
 * Example:
 *
 * CustomerService needs EmailService.
 *
 * Then:
 *
 * EmailService is dependency of CustomerService.
 *
 *
 * ============================================================================
 * 7. WHAT IS DEPENDENCY INJECTION (DI)?
 * ============================================================================
 *
 * Definition:
 *
 * Dependency Injection means providing required dependencies from outside
 * instead of creating them inside the class.
 *
 *
 * Without DI:
 *
 * class CustomerService{
 *
 *      private EmailService emailService =
 *              new EmailService();
 *
 * }
 *
 *
 * With DI:
 *
 * class CustomerService{
 *
 *      private EmailService emailService;
 *
 *      CustomerService(EmailService emailService){
 *          this.emailService = emailService;
 *      }
 *
 * }
 *
 *
 * Spring injects EmailService automatically.
 *
 *
 * ============================================================================
 * 8. IOC VS DI
 * ============================================================================
 *
 * IoC = Concept
 *
 * DI = Technique used to achieve IoC
 *
 *
 * Example:
 *
 * Goal:
 *      Inversion Of Control
 *
 * Method:
 *      Dependency Injection
 *
 *
 * ============================================================================
 * 9. WHAT IS TIGHT COUPLING?
 * ============================================================================
 *
 * Definition:
 *
 * When one class directly depends upon a specific implementation,
 * classes become tightly coupled.
 *
 *
 * Example:
 *
 * class CustomerService{
 *
 *      EmailService emailService =
 *          new EmailService();
 *
 * }
 *
 *
 * Problems:
 *
 * - Hard to modify
 * - Hard to test
 * - Hard to maintain
 * - Hard to scale
 *
 *
 * ============================================================================
 * 10. WHAT IS LOOSE COUPLING?
 * ============================================================================
 *
 * Definition:
 *
 * Classes depend upon abstractions (interfaces)
 * instead of implementations.
 *
 *
 * Example:
 *
 * interface NotificationService{}
 *
 * class EmailService
 *      implements NotificationService{}
 *
 * class SmsService
 *      implements NotificationService{}
 *
 *
 * class CustomerService{
 *
 *      NotificationService service;
 *
 * }
 *
 *
 * Now EmailService can be replaced by SmsService
 * without changing CustomerService.
 *
 *
 * Benefits:
 *
 * - Flexible
 * - Maintainable
 * - Testable
 * - Reusable
 *
 *
 * ============================================================================
 * 11. WHAT IS SPRING CONTAINER?
 * ============================================================================
 *
 * Definition:
 *
 * Spring Container is the brain of Spring Framework.
 *
 * Responsibilities:
 *
 * 1. Creates Objects
 * 2. Stores Objects
 * 3. Injects Dependencies
 * 4. Manages Lifecycle
 * 5. Destroys Objects
 *
 *
 * Think:
 *
 * Container = Object Factory + Dependency Manager
 *
 *
 * ============================================================================
 * 12. WHAT IS A BEAN?
 * ============================================================================
 *
 * Definition:
 *
 * Any object managed by Spring Container is called a Bean.
 *
 *
 * Example:
 *
 * @Service
 * public class CustomerService{}
 *
 *
 * CustomerService becomes Spring Bean.
 *
 *
 * ============================================================================
 * 13. WHAT IS BEAN LIFE CYCLE?
 * ============================================================================
 *
 * Bean Creation
 *      ↓
 * Dependency Injection
 *      ↓
 * Initialization
 *      ↓
 * Ready To Use
 *      ↓
 * Destruction
 *
 *
 * Spring manages entire lifecycle.
 *
 *
 * ============================================================================
 * 14. APPLICATION CONTEXT
 * ============================================================================
 *
 * ApplicationContext is an advanced Spring Container.
 *
 * It provides:
 *
 * - Bean Management
 * - Event Handling
 * - Internationalization
 * - Resource Loading
 *
 *
 * Most Spring Boot applications use ApplicationContext.
 *
 *
 * ============================================================================
 * 15. BEANFACTORY VS APPLICATIONCONTEXT
 * ============================================================================
 *
 * BeanFactory
 *
 * - Basic Container
 * - Lightweight
 * - Lazy Loading
 *
 *
 * ApplicationContext
 *
 * - Advanced Container
 * - More Features
 * - Widely Used
 * - Enterprise Ready
 *
 *
 * In real projects:
 *
 * ApplicationContext is used.
 *
 *
 * ============================================================================
 * 16. TYPES OF DEPENDENCY INJECTION
 * ============================================================================
 *
 * 1. Constructor Injection
 * 2. Setter Injection
 * 3. Field Injection
 *
 *
 * ============================================================================
 * Constructor Injection (Recommended)
 * ============================================================================
 *
 * Advantages:
 *
 * - Immutable Dependencies
 * - Easy Testing
 * - Clean Design
 * - Spring Recommended
 *
 *
 * ============================================================================
 * Setter Injection
 * ============================================================================
 *
 * Dependency injected through setter methods.
 *
 * Mostly used for optional dependencies.
 *
 *
 * ============================================================================
 * Field Injection
 * ============================================================================
 *
 * @Autowired
 * private EmailService emailService;
 *
 *
 * Not recommended.
 *
 * Reasons:
 *
 * - Difficult Testing
 * - Hidden Dependencies
 * - Reflection Based
 *
 *
 * ============================================================================
 * 17. @COMPONENT
 * ============================================================================
 *
 * Generic Spring Bean.
 *
 * Used when class does not belong to specific layer.
 *
 *
 * Example:
 *
 * @Component
 * public class UtilityClass{}
 *
 *
 * ============================================================================
 * 18. @SERVICE
 * ============================================================================
 *
 * Represents Business Logic Layer.
 *
 *
 * Example:
 *
 * @Service
 * public class CustomerService{}
 *
 *
 * Purpose:
 *
 * Business operations.
 *
 *
 * ============================================================================
 * 19. @REPOSITORY
 * ============================================================================
 *
 * Represents Data Access Layer.
 *
 *
 * Example:
 *
 * @Repository
 * public class CustomerRepository{}
 *
 *
 * Purpose:
 *
 * Database communication.
 *
 *
 * ============================================================================
 * 20. @CONTROLLER
 * ============================================================================
 *
 * Used in Spring MVC applications.
 *
 * Handles HTTP Requests.
 *
 *
 * ============================================================================
 * 21. @RESTCONTROLLER
 * ============================================================================
 *
 * Combination of:
 *
 * @Controller + @ResponseBody
 *
 *
 * Returns JSON Response.
 *
 *
 * ============================================================================
 * 22. @AUTOWIRED
 * ============================================================================
 *
 * Used for automatic dependency injection.
 *
 *
 * Example:
 *
 * @Autowired
 * EmailService emailService;
 *
 *
 * Spring finds dependency and injects it.
 *
 *
 * ============================================================================
 * 23. COMPONENT SCANNING
 * ============================================================================
 *
 * Spring scans project packages.
 *
 * Finds:
 *
 * - @Component
 * - @Service
 * - @Repository
 * - @Controller
 *
 * Creates beans automatically.
 *
 *
 * ============================================================================
 * 24. @BEAN
 * ============================================================================
 *
 * Used when source code cannot be modified.
 *
 *
 * Example:
 *
 * Third Party Library Classes
 *
 *
 * @Configuration
 * class AppConfig{
 *
 *      @Bean
 *      EmailService emailService(){
 *          return new EmailService();
 *      }
 *
 * }
 *
 *
 * ============================================================================
 * 25. @CONFIGURATION
 * ============================================================================
 *
 * Defines configuration class.
 *
 * Contains bean definitions.
 *
 *
 * ============================================================================
 * 26. BEAN SCOPES
 * ============================================================================
 *
 * Singleton (Default)
 * Prototype
 * Request
 * Session
 * Application
 *
 *
 * ============================================================================
 * Singleton
 * ============================================================================
 *
 * One Bean
 * One Object
 * Entire Application
 *
 *
 * ============================================================================
 * Prototype
 * ============================================================================
 *
 * New Bean
 * New Object
 * Every Request
 *
 *
 * ============================================================================
 * 27. WHY SPRING CORE BECAME POPULAR?
 * ============================================================================
 *
 * Because it solved:
 *
 * ✓ Tight Coupling
 * ✓ Object Creation Problems
 * ✓ Dependency Management
 * ✓ Scalability Issues
 * ✓ Testability Problems
 * ✓ Maintainability Problems
 *
 *
 * ============================================================================
 * 28. REAL WORLD FLOW
 * ============================================================================
 *
 * Application Starts
 *          ↓
 * Spring Container Starts
 *          ↓
 * Beans Created
 *          ↓
 * Dependencies Injected
 *          ↓
 * Application Ready
 *          ↓
 * Requests Handled
 *          ↓
 * Application Stops
 *          ↓
 * Beans Destroyed
 *
 *
 * ============================================================================
 * QUICK REVISION (INTERVIEW NOTES)
 * ============================================================================
 *
 * Spring Core
 *      Foundation of Spring Framework.
 *
 * IoC
 *      Spring controls object creation.
 *
 * DI
 *      Spring injects dependencies.
 *
 * Dependency
 *      Object required by another object.
 *
 * Bean
 *      Object managed by Spring.
 *
 * Container
 *      Creates and manages beans.
 *
 * ApplicationContext
 *      Advanced Spring Container.
 *
 * Tight Coupling
 *      Depends on implementation.
 *
 * Loose Coupling
 *      Depends on interface.
 *
 * @Component
 *      Generic Bean.
 *
 * @Service
 *      Business Logic Layer.
 *
 * @Repository
 *      Database Layer.
 *
 * @Controller
 *      Handles Web Requests.
 *
 * @RestController
 *      Returns JSON.
 *
 * @Autowired
 *      Automatic Dependency Injection.
 *
 * @Bean
 *      Manually Register Bean.
 *
 * @Configuration
 *      Configuration Class.
 *
 * Singleton Scope
 *      One Object Per Container.
 *
 * Prototype Scope
 *      New Object Every Time.
 *
 * Constructor Injection
 *      Best Practice.
 *
 * Spring Core Goal
 *      Build loosely coupled, maintainable,
 *      scalable and testable applications.
 *
 * ============================================================================
 */

public class SpringCoreDeepDrive {

}