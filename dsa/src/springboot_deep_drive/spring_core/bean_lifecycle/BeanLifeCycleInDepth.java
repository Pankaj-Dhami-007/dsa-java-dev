package springboot_deep_drive.spring_core.bean_lifecycle;

/**
 * =============================================================================
 * BEAN LIFECYCLE IN-DEPTH (Singleton Scope)
 * =============================================================================
 *
 * The Bean Lifecycle is the sequence of steps Spring performs
 * from the moment a bean is conceived (as a BeanDef) until
 * it is destroyed when the container shuts down.
 *
 * This is NOT about ApplicationContext startup.
 * This is about what happens to ONE bean.
 *
 *
 * COMPLETE LIFECYCLE FLOW (Singleton)
 * =============================================================================
 *
 *   ┌──────────────────────────────────────────────────┐
 *   │         1. IoC Container Created                 │
 *   │     new AnnotationConfigApplicationContext(...)   │
 *   └────────────────┬─────────────────────────────────┘
 *                    │
 *                    ▼
 *   ┌──────────────────────────────────────────────────┐
 *   │         2. Read Configuration                    │
 *   │     @Configuration, @ComponentScan, XML           │
 *   └────────────────┬─────────────────────────────────┘
 *                    │
 *                    ▼
 *   ┌──────────────────────────────────────────────────┐
 *   │         3. Create Bean Definitions               │
 *   │     Metadata: name, class, scope, deps           │
 *   └────────────────┬─────────────────────────────────┘
 *                    │
 *                    ▼
 *   ┌──────────────────────────────────────────────────┐
 *   │         4. Instantiate Object                    │
 *   │     Constructor called (new instance)            │
 *   └────────────────┬─────────────────────────────────┘
 *                    │
 *                    ▼
 *   ┌──────────────────────────────────────────────────┐
 *   │         5. Populate Properties                   │
 *   │     @Autowired / setter / field injection        │
 *   └────────────────┬─────────────────────────────────┘
 *                    │
 *                    ▼
 *   ┌──────────────────────────────────────────────────┐
 *   │         6. Aware Interfaces                      │
 *   │     BeanNameAware, ApplicationContextAware, etc  │
 *   └────────────────┬─────────────────────────────────┘
 *                    │
 *                    ▼
 *   ┌──────────────────────────────────────────────────┐
 *   │         7. BeanPostProcessor (before)            │
 *   │     postProcessBeforeInitialization()            │
 *   └────────────────┬─────────────────────────────────┘
 *                    │
 *                    ▼
 *   ┌──────────────────────────────────────────────────┐
 *   │         8. Initialization Callbacks              │
 *   │     @PostConstruct → InitializingBean → init-method│
 *   └────────────────┬─────────────────────────────────┘
 *                    │
 *                    ▼
 *   ┌──────────────────────────────────────────────────┐
 *   │         9. BeanPostProcessor (after)             │
 *   │     postProcessAfterInitialization()             │
 *   └────────────────┬─────────────────────────────────┘
 *                    │
 *                    ▼
 *   ┌──────────────────────────────────────────────────┐
 *   │        10. BEAN IS READY TO USE                  │
 *   │     Stored in container, available to app        │
 *   └─────────────────────────────────────────────────┘
 *                    │
 *                    ▼  (when container closes)
 *   ┌──────────────────────────────────────────────────┐
 *   │        11. Destruction Callbacks                 │
 *   │     @PreDestroy → DisposableBean → destroy-method│
 *   └────────────────┬─────────────────────────────────┘
 *                    │
 *                    ▼
 *   ┌──────────────────────────────────────────────────┐
 *   │        12. BEAN IS DESTROYED                     │
 *   └─────────────────────────────────────────────────┘
 *
 *
 * =============================================================================
 * STEP 1: IoC CONTAINER CREATED
 * =============================================================================
 *
 * This is the trigger. The container must start before
 * any beans can exist.
 *
 * Code:
 *
 * ApplicationContext context =
 *     new AnnotationConfigApplicationContext(AppConfig.class);
 *
 * What happens:
 *
 * Spring allocates memory, initializes internal registries,
 * and prepares to read configuration.
 *
 * Without this step, no beans exist.
 *
 *
 * =============================================================================
 * STEP 2: READ CONFIGURATION
 * =============================================================================
 *
 * Spring reads the source of bean definitions.
 *
 * Sources can be:
 *
 * - @Configuration class (Java-based)
 * - XML files (legacy)
 * - Component scanning
 *
 * Code:
 *
 * @Configuration
 * @ComponentScan("com.example")
 * public class AppConfig { }
 *
 * What happens:
 *
 * Spring identifies which packages to scan,
 * which @Bean methods exist, and what XML files to parse.
 *
 *
 * =============================================================================
 * STEP 3: CREATE BEAN DEFINITIONS
 * =============================================================================
 *
 * Spring creates a BeanDef for every discovered bean.
 *
 * BeanDef = Metadata blueprint (NOT the actual object).
 *
 * What Spring stores for each bean:
 *
 * ┌─────────────────────────────────────────────┐
 * │ BeanDef: "userService"                      │
 * ├─────────────────────────────────────────────┤
 * │ Class:          UserServiceImpl.class       │
 * │ Scope:          singleton                   │
 * │ Lazy:           false                       │
 * │ Dependencies:   [userRepository]            │
 * │ Init method:    init()                      │
 * │ Destroy method: destroy()                   │
 * └─────────────────────────────────────────────┘
 *
 * Multiple BeanDefs are created here.
 * No objects are created yet — only blueprints.
 *
 *
 * =============================================================================
 * STEP 4: INSTANTIATE OBJECT
 * =============================================================================
 *
 * Spring uses the BeanDef to create the actual object.
 *
 * For singleton beans (default, non-lazy), this happens
 * eagerly during container startup.
 *
 * Spring calls the constructor.
 *
 * Code (what Spring does internally):
 *
 * UserService userService = new UserServiceImpl();
 *
 *
 * Practical Example:
 *
 * @Component
 * public class UserService {
 *     public UserService() {
 *         System.out.println("Step 4: Constructor called");
 *     }
 * }
 *
 * Output at startup:
 * Step 4: Constructor called
 *
 *
 * Key points:
 *
 * - Constructor injection dependencies must be available
 * - If constructor throws, bean creation fails
 * - At this point, fields are NULL (no DI yet)
 *
 *
 * =============================================================================
 * STEP 5: POPULATE PROPERTIES (Dependency Injection)
 * =============================================================================
 *
 * Spring injects dependencies into the bean.
 *
 * After the object exists, Spring fills:
 *
 * - @Autowired fields
 * - @Autowired setters
 * - @Value fields
 * - Constructor-injected params (done in Step 4)
 *
 *
 * Practical Example:
 *
 * @Component
 * public class UserService {
 *
 *     private final UserRepository repo;
 *
 *     @Value("${app.name}")
 *     private String appName;          // set after constructor
 *
 *     public UserService(UserRepository repo) {
 *         this.repo = repo;            // constructor injection
 *     }
 *
 *     @Autowired
 *     public void setAppName(String name) {  // setter injection
 *         this.appName = name;
 *     }
 * }
 *
 *
 * Order of injection for one bean:
 *
 * 1. Constructor injection (during instantiation)
 * 2. Field injection (@Autowired on fields)
 * 3. Setter injection (@Autowired on methods)
 *
 *
 * =============================================================================
 * STEP 6: AWARE INTERFACES
 * =============================================================================
 *
 * If a bean implements certain "Aware" interfaces,
 * Spring calls them AFTER DI is done.
 *
 * These give the bean access to Spring infrastructure.
 *
 *
 * Common Aware interfaces (in call order):
 *
 * Interface                  What it provides
 * ─────────────────────────  ─────────────────────────
 * BeanNameAware              Bean's own name (String)
 * BeanClassLoaderAware       ClassLoader
 * BeanFactoryAware           BeanFactory reference
 * EnvironmentAware           Environment (properties)
 * ApplicationContextAware    Full ApplicationContext
 *
 *
 * Practical Example:
 *
 * @Component
 * public class UserService implements ApplicationContextAware {
 *
 *     private ApplicationContext appCtx;
 *
 *     @Override
 *     public void setApplicationContext(
 *             ApplicationContext ctx) {
 *         this.appCtx = ctx;
 *         System.out.println(
 *             "Step 6: Aware - got ApplicationContext");
 *     }
 * }
 *
 *
 * Why is this useful?
 *
 * To programmatically get other beans or environment info.
 *
 * But prefer DI over Aware interfaces
 * unless absolutely necessary.
 *
 *
 * =============================================================================
 * STEP 7: BEAN POST PROCESSOR (Before Initialization)
 * =============================================================================
 *
 * BeanPostProcessor is a special Spring hook that lets you
 * modify or wrap a bean BEFORE and AFTER initialization.
 *
 *
 * Interface:
 *
 * public class CustomBPP implements BeanPostProcessor {
 *
 *     @Override
 *     public Object postProcessBeforeInitialization(
 *             Object bean, String beanName) {
 *         // Called for EVERY bean
 *         return bean;
 *     }
 *
 *     @Override
 *     public Object postProcessAfterInitialization(
 *             Object bean, String beanName) {
 *         return bean;
 *     }
 * }
 *
 *
 * Practical Example (before init):
 *
 * @Component
 * public class AuditBPP implements BeanPostProcessor {
 *
 *     @Override
 *     public Object postProcessBeforeInitialization(
 *             Object bean, String beanName) {
 *         if (bean instanceof UserService) {
 *             System.out.println(
 *                 "Step 7: BPP before init - auditing "
 *                 + beanName);
 *         }
 *         return bean;
 *     }
 * }
 *
 *
 * Common uses:
 *
 * - Wrap bean in a proxy (AOP)
 * - Modify bean properties
 * - Audit / Logging
 * - Validation
 *
 *
 * =============================================================================
 * STEP 8: INITIALIZATION CALLBACKS
 * =============================================================================
 *
 * Three ways to run custom logic after all dependencies
 * are set and Aware calls are done.
 *
 *
 * Method 1: @PostConstruct (JSR-250 annotation — most common)
 *
 * @Component
 * public class UserService {
 *
 *     @PostConstruct
 *     public void init() {
 *         System.out.println(
 *             "Step 8a: @PostConstruct called");
 *         // e.g., open file, validate config, load cache
 *     }
 * }
 *
 *
 * Method 2: InitializingBean interface
 *
 * @Component
 * public class UserService implements InitializingBean {
 *
 *     @Override
 *     public void afterPropertiesSet() {
 *         System.out.println(
 *             "Step 8b: InitializingBean called");
 *     }
 * }
 *
 *
 * Method 3: Custom init-method in @Bean
 *
 * @Configuration
 * public class AppConfig {
 *     @Bean(initMethod = "customInit")
 *     public UserService userService() {
 *         return new UserService();
 *     }
 * }
 *
 * public class UserService {
 *     public void customInit() {
 *         System.out.println(
 *             "Step 8c: initMethod called");
 *     }
 * }
 *
 *
 * Execution ORDER (for a single bean):
 *
 * 1. @PostConstruct
 * 2. InitializingBean.afterPropertiesSet()
 * 3. Custom init-method
 *
 *
 * =============================================================================
 * STEP 9: BEAN POST PROCESSOR (After Initialization)
 * =============================================================================
 *
 * Same as Step 7, but this runs AFTER init callbacks.
 *
 *
 * @Component
 * public class ProxyBPP implements BeanPostProcessor {
 *
 *     @Override
 *     public Object postProcessAfterInitialization(
 *             Object bean, String beanName) {
 *         if (bean instanceof UserService) {
 *             System.out.println(
 *                 "Step 9: BPP after init - wrapping "
 *                 + beanName);
 *             // Wrap in proxy (this is how AOP works)
 *         }
 *         return bean;
 *     }
 * }
 *
 *
 * This is where Spring AOP creates proxies.
 * e.g., @Transactional, @Cacheable are implemented
 * by wrapping the bean here.
 *
 *
 * =============================================================================
 * STEP 10: BEAN IS READY TO USE
 * =============================================================================
 *
 * The bean is fully initialized and stored in the container.
 *
 * It can now be:
 *
 * - Retrieved via context.getBean()
 * - Injected into other beans
 * - Used in business logic
 *
 *
 * Container state after startup:
 *
 * ┌──────────────────────────────────────────┐
 * │  Container (Ready)                       │
 * │                                          │
 * │  singleton beans:                        │
 * │  ┌────────────────────────────────────┐ │
 * │  │ userService  → UserService@123    │ │
 * │  │ userRepo     → UserRepo@456       │ │
 * │  │ auditBPP     → AuditBPP@789       │ │
 * │  └────────────────────────────────────┘ │
 * └──────────────────────────────────────────┘
 *
 *
 * =============================================================================
 * STEP 11: DESTRUCTION CALLBACKS
 * =============================================================================
 *
 * When the container shuts down (context.close()),
 * Spring calls destruction callbacks for singleton beans.
 *
 * Prototype beans are NOT destroyed by Spring.
 *
 *
 * Three destruction methods:
 *
 *
 * Method 1: @PreDestroy (JSR-250)
 *
 * @Component
 * public class UserService {
 *
 *     @PreDestroy
 *     public void shutdown() {
 *         System.out.println(
 *             "Step 11a: @PreDestroy called");
 *         // Close connections, release resources
 *     }
 * }
 *
 *
 * Method 2: DisposableBean interface
 *
 * @Component
 * public class UserService implements DisposableBean {
 *
 *     @Override
 *     public void destroy() {
 *         System.out.println(
 *             "Step 11b: DisposableBean called");
 *     }
 * }
 *
 *
 * Method 3: Custom destroy-method in @Bean
 *
 * @Bean(destroyMethod = "cleanup")
 * public UserService userService() {
 *     return new UserService();
 * }
 *
 *
 * Execution ORDER (for a single bean):
 *
 * 1. @PreDestroy
 * 2. DisposableBean.destroy()
 * 3. Custom destroy-method
 *
 *
 * =============================================================================
 * STEP 12: BEAN IS DESTROYED
 * =============================================================================
 *
 * After all destroy callbacks, the bean is eligible
 * for garbage collection.
 *
 * The container removes the bean from its registry.
 * The object is no longer managed by Spring.
 *
 *
 * =============================================================================
 * COMPLETE PRACTICAL EXAMPLE (Single Bean)
 * =============================================================================
 *
 * import javax.annotation.PostConstruct;
 * import javax.annotation.PreDestroy;
 * import org.springframework.beans.factory.DisposableBean;
 * import org.springframework.beans.factory.InitializingBean;
 * import org.springframework.beans.factory.BeanNameAware;
 * import org.springframework.context.ApplicationContextAware;
 * import org.springframework.stereotype.Component;
 *
 * @Component
 * public class DemoBean implements
 *         BeanNameAware,
 *         InitializingBean,
 *         DisposableBean {
 *
 *     private String beanName;
 *
 *     // === Step 4: Constructor ===
 *     public DemoBean() {
 *         System.out.println("1. Constructor");
 *     }
 *
 *     // === Step 5: DI happens here (not shown) ===
 *
 *     // === Step 6: Aware ===
 *     @Override
 *     public void setBeanName(String name) {
 *         this.beanName = name;
 *         System.out.println("2. BeanNameAware: " + name);
 *     }
 *
 *     // === Step 8a: @PostConstruct ===
 *     @PostConstruct
 *     public void postConstruct() {
 *         System.out.println("3. @PostConstruct");
 *     }
 *
 *     // === Step 8b: InitializingBean ===
 *     @Override
 *     public void afterPropertiesSet() {
 *         System.out.println("4. InitializingBean.afterPropertiesSet()");
 *     }
 *
 *     // === Step 10: Bean is ready ===
 *     public void doWork() {
 *         System.out.println("5. Bean is USED: doWork()");
 *     }
 *
 *     // === Step 11a: @PreDestroy ===
 *     @PreDestroy
 *     public void preDestroy() {
 *         System.out.println("6. @PreDestroy");
 *     }
 *
 *     // === Step 11b: DisposableBean ===
 *     @Override
 *     public void destroy() {
 *         System.out.println("7. DisposableBean.destroy()");
 *     }
 * }
 *
 *
 * Output when container starts and shuts down:
 *
 * 1. Constructor
 * 2. BeanNameAware: demoBean
 * 3. @PostConstruct
 * 4. InitializingBean.afterPropertiesSet()
 *     [BPP before/after wrap around steps 3-4]
 * 5. Bean is USED: doWork()
 *     ... application runs ...
 * 6. @PreDestroy
 * 7. DisposableBean.destroy()
 *
 *
 * =============================================================================
 * IMPORTANT: PRESERVE THIS ORDER FOR @Bean METHOD
 * =============================================================================
 *
 * @Configuration
 * public class AppConfig {
 *
 *     @Bean(
 *         initMethod = "customInit",
 *         destroyMethod = "customDestroy"
 *     )
 *     public AnotherBean anotherBean() {
 *         return new AnotherBean();
 *     }
 * }
 *
 *
 * public class AnotherBean {
 *
 *     @PostConstruct
 *     public void postConstruct() {
 *         // 1st init callback
 *     }
 *
 *     public void customInit() {
 *         // 2nd init callback (after @PostConstruct)
 *     }
 *
 *     @PreDestroy
 *     public void preDestroy() {
 *         // 1st destroy callback
 *     }
 *
 *     public void customDestroy() {
 *         // 2nd destroy callback (after @PreDestroy)
 *     }
 * }
 *
 *
 * =============================================================================
 * QUICK REFERENCE TABLE
 * =============================================================================
 *
 * Step  What happens               Hook / Mechanism
 * ────  ─────────────────────────  ───────────────────────────
 * 1     Container created          new ApplicationContext()
 * 2     Read configuration         @Configuration / XML
 * 3     Create Bean Definitions    Component scanning / @Bean
 * 4     Instantiate object         Constructor
 * 5     Populate properties        @Autowired / @Value
 * 6     Aware interfaces           BeanNameAware, etc.
 * 7     BPP before init            postProcessBeforeInit()
 * 8     Init callbacks             @PostConstruct → InitBean → init-method
 * 9     BPP after init             postProcessAfterInit()
 * 10    Bean READY                 Available in container
 * 11    Destroy callbacks          @PreDestroy → DisposableBean → destroy-method
 * 12    Bean destroyed             GC eligible
 *
 *
 * =============================================================================
 * KEY TAKEAWAYS (Summary)
 * =============================================================================
 *
 * 1. Singleton beans go through ALL 12 steps eagerly
 *    (unless @Lazy, which delays steps 4-9).
 *
 * 2. Prototype beans only go through steps 4-9.
 *    Steps 11-12 are NOT called by Spring.
 *
 * 3. BeanPostProcessor runs for EVERY bean in the container.
 *    This is the extension point used by AOP, @Transactional,
 *    @Cacheable, and most Spring features.
 *
 * 4. The lifecycle order is FIXED. You cannot change it.
 *    But you can hook into any step using the right mechanism.
 *
 * 5. @PostConstruct / @PreDestroy are the most common
 *    and preferred way for init/destroy logic.
 *
 * 6. Aware interfaces should be avoided unless you
 *    genuinely need container infrastructure.
 *
 * 7. BeanPostProcessor is the MOST POWERFUL hook.
 *    It is how Spring itself extends bean behavior.
 *
 *
 * =============================================================================
 * INTERVIEW QUESTIONS
 * =============================================================================
 *
 * Q1. What is the Bean Lifecycle in Spring?
 *
 * Answer:
 *
 * The Bean Lifecycle is the sequence of steps Spring
 * performs from creating a bean definition to destroying
 * the bean instance. It includes instantiation, dependency
 * injection, aware interface callbacks, bean post-processors,
 * initialization callbacks, and destruction callbacks.
 *
 *
 * Q2. What is the difference between init-method,
 *     @PostConstruct, and InitializingBean?
 *
 * Answer:
 *
 * They all run during initialization, in this fixed order:
 *
 * 1. @PostConstruct (JSR-250 annotation)
 * 2. InitializingBean.afterPropertiesSet()
 * 3. Custom init-method (specified in @Bean)
 *
 * @PostConstruct is the most commonly used because it
 * does not couple your code to Spring interfaces.
 *
 *
 * Q3. What is BeanPostProcessor?
 *
 * Answer:
 *
 * BeanPostProcessor is a Spring interface that allows
 * custom modification of bean instances before and after
 * initialization callbacks. It runs for EVERY bean in the
 * container. It is the foundation of AOP, proxies, and
 * most Spring meta-features.
 *
 *
 * Q4. Are destroy callbacks called for prototype beans?
 *
 * Answer:
 *
 * No. Spring does NOT manage the full lifecycle of
 * prototype beans. Only singleton beans receive
 * destruction callbacks when the container shuts down.
 *
 *
 * Q5. What is the order of dependency injection?
 *
 * Answer:
 *
 * 1. Constructor injection (during instantiation)
 * 2. Field injection (@Autowired on fields)
 * 3. Setter injection (@Autowired on methods)
 *
 * All injection happens BEFORE Aware interfaces
 * and BEFORE init callbacks.
 *
 * =============================================================================
 */
public class BeanLifeCycleInDepth {
}
