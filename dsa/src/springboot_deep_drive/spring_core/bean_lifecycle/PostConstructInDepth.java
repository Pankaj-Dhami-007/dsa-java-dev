package springboot_deep_drive.spring_core.bean_lifecycle;

/**
 * =============================================================================
 * @PostConstruct IN-DEPTH
 * =============================================================================
 *
 * A dedicated deep dive into the most commonly used
 * initialization callback in Spring.
 *
 * =============================================================================
 * WHAT IS @PostConstruct?
 * =============================================================================
 *
 * @PostConstruct is a JSR-250 annotation (not Spring-specific).
 *
 * It marks a method to be executed AFTER dependency injection
 * is complete and BEFORE the bean is ready for use.
 *
 *
 * In the Bean Lifecycle, it sits at:
 *
 * Instantiate → Inject Dependencies → Aware → BPP Before
 *      ↓
 * @PostConstruct  ← YOU ARE HERE
 *      ↓
 * InitializingBean → initMethod → BPP After → Bean Ready
 *
 *
 * Code:
 *
 * @Component
 * public class DatabaseManager {
 *
 *     private DataSource dataSource;
 *
 *     public DatabaseManager(DataSource dataSource) {
 *         this.dataSource = dataSource;
 *         // dataSource is injected, but can we use it yet?
 *         // Technically yes, but this is not the pattern.
 *         // Constructor should only assign, not DO work.
 *     }
 *
 *     @PostConstruct
 *     public void init() {
 *         // NOW we can safely use dataSource
 *         // to open connections, validate config, etc.
 *         System.out.println("DatabaseManager initialized!");
 *     }
 * }
 *
 *
 * =============================================================================
 * WHY DO WE NEED @PostConstruct?
 * =============================================================================
 *
 * Problem:
 *
 * The constructor has limitations:
 *
 * 1. Only constructor-injected deps are available
 * 2. Field/setter injection has NOT happened yet
 * 3. BeanPostProcessors have NOT run yet
 * 4. The bean is not fully configured
 *
 *
 * @PostConstruct solves this by running AFTER:
 *
 * ✓ Constructor called
 * ✓ All @Autowired fields injected
 * ✓ All @Autowired setters called
 * ✓ All @Value fields populated
 * ✓ All Aware interfaces called
 * ✓ BeanPostProcessors (before init) have already run
 *
 *
 * At @PostConstruct, the bean is fully wired.
 * You can safely use ALL dependencies.
 *
 *
 * =============================================================================
 * RULES & CONSTRAINTS
 * =============================================================================
 *
 * 1. The method must NOT take parameters
 *
 *    Correct:
 *    @PostConstruct
 *    public void init() { }
 *
 *    WRONG (compilation error):
 *    @PostConstruct
 *    public void init(String name) { }
 *
 *
 * 2. The method can be ANY access modifier
 *
 *    All of these work:
 *    @PostConstruct public void init() { }
 *    @PostConstruct protected void init() { }
 *    @PostConstruct void init() { }
 *    @PostConstruct private void init() { }
 *
 *
 * 3. The method can be static or non-static
 *
 *    For Spring beans, it is ALWAYS non-static.
 *    Static methods don't run in Spring context
 *    (not managed by BeanPostProcessor).
 *
 *
 * 4. The method can throw unchecked exceptions
 *
 *    If it throws, the bean is considered invalid
 *    and Spring will NOT make it available.
 *
 *
 * 5. Return type should be void
 *
 *    Spring ignores the return value anyway.
 *    public void init() is the standard.
 *
 *
 * 6. Only ONE @PostConstruct per class
 *
 *    If you put @PostConstruct on multiple methods,
 *    Spring throws an exception.
 *
 *    WRONG:
 *    @PostConstruct
 *    public void initA() { }
 *
 *    @PostConstruct
 *    public void initB() { }  // ERROR
 *
 *
 * =============================================================================
 * @PostConstruct vs INITIALIZING BEAN vs INIT-METHOD
 * =============================================================================
 *
 *                      @PostConstruct  InitializingBean  initMethod
 *                      ──────────────  ────────────────  ─────────
 * Standard             JSR-250         Spring-specific   Spring-specific
 *                                    (Bean lifecycle)
 * Coupling to Spring   NO              YES               YES
 * (framework import)                                                     (if using @Bean)
 * Supports @Component  YES             YES               NO (only @Bean)
 * Supports @Bean       YES             NO                YES
 * Execution order      1st             2nd               3rd
 * Popularity           ★★★★★           ★★                 ★★★
 *
 *
 * Recommendation:
 *
 * Always prefer @PostConstruct.
 *
 * It is standard Java (jakarta.annotation),
 * not coupled to Spring, and most readable.
 *
 *
 * =============================================================================
 * PRACTICAL EXAMPLE 1: INITIALIZE EXTERNAL CONNECTIONS
 * =============================================================================
 *
 * @Component
 * public class EmailService {
 *
 *     private final String smtpHost;
 *     private final int smtpPort;
 *     private Properties mailConfig;
 *
 *     public EmailService(
 *             @Value("${mail.smtp.host}") String host,
 *             @Value("${mail.smtp.port}") int port) {
 *         this.smtpHost = host;
 *         this.smtpPort = port;
 *         System.out.println("Constructor: stored config values");
 *     }
 *
 *     @PostConstruct
 *     public void connectToMailServer() {
 *         System.out.println(
 *             "@PostConstruct: Connecting to " +
 *             smtpHost + ":" + smtpPort);
 *         // Actual connection logic
 *         this.mailConfig = new Properties();
 *         mailConfig.setProperty("host", smtpHost);
 *         mailConfig.setProperty("port", String.valueOf(smtpPort));
 *     }
 *
 *     public void sendEmail(String to, String body) {
 *         if (mailConfig == null) {
 *             throw new IllegalStateException(
 *                 "Mail not initialized");
 *         }
 *         System.out.println("Sending email to " + to);
 *     }
 * }
 *
 *
 * =============================================================================
 * PRACTICAL EXAMPLE 2: LOAD CACHE / STATIC DATA
 * =============================================================================
 *
 * @Component
 * public class CountryCache {
 *
 *     private final CountryRepository repository;
 *     private Map<String, Country> cache;
 *
 *     public CountryCache(CountryRepository repository) {
 *         this.repository = repository;
 *     }
 *
 *     @PostConstruct
 *     public void loadCache() {
 *         System.out.println("@PostConstruct: Loading country cache");
 *         List<Country> all = repository.findAll();
 *         this.cache = all.stream()
 *             .collect(Collectors.toMap(Country::getCode, c -> c));
 *         System.out.println("Loaded " + cache.size() + " countries");
 *     }
 *
 *     public Country findByCode(String code) {
 *         return cache.get(code);
 *     }
 * }
 *
 *
 * =============================================================================
 * PRACTICAL EXAMPLE 3: VALIDATE CONFIGURATION
 * =============================================================================
 *
 * @Component
 * public class PaymentGateway {
 *
 *     @Value("${payment.api.key}")
 *     private String apiKey;
 *
 *     @Value("${payment.api.url}")
 *     private String apiUrl;
 *
 *     @PostConstruct
 *     public void validateConfig() {
 *         System.out.println("@PostConstruct: Validating config");
 *         if (apiKey == null || apiKey.isBlank()) {
 *             throw new IllegalStateException(
 *                 "payment.api.key is required");
 *         }
 *         if (apiUrl == null || apiUrl.isBlank()) {
 *             throw new IllegalStateException(
 *                 "payment.api.url is required");
 *         }
 *         System.out.println(
 *             "PaymentGateway configuration is valid");
 *     }
 * }
 *
 *
 * If validation fails, the bean is NOT added to the container.
 * Application startup fails immediately (fail-fast).
 *
 *
 * =============================================================================
 * @PostConstruct + @Lazy
 * =============================================================================
 *
 * @Lazy
 * @Component
 * public class HeavyResource {
 *
 *     @PostConstruct
 *     public void init() {
 *         System.out.println(
 *             "HeavyResource init: this runs on FIRST use");
 *     }
 * }
 *
 * With @Lazy, @PostConstruct does NOT run at startup.
 * It runs when the bean is first accessed.
 *
 *
 * =============================================================================
 * THE FULL FLOW WITH @PostConstruct
 * =============================================================================
 *
 *           new ApplicationContext()
 *                    │
 *                    ▼
 *        Read Configuration / Scan
 *                    │
 *                    ▼
 *           Create Bean Definitions
 *                    │
 *                    ▼
 *        +---------------------+
 *        |  Constructor        |
 *        +---------------------+
 *                    │
 *                    ▼
 *        +---------------------+
 *        |  Inject Dependencies|
 *        |  @Autowired, @Value |
 *        +---------------------+
 *                    │
 *                    ▼
 *        +---------------------+
 *        |  Aware Interfaces   |
 *        +---------------------+
 *                    │
 *                    ▼
 *        +---------------------+
 *        | BPP before init     |
 *        +---------------------+
 *                    │
 *                    ▼
 *        +---------------------+
 *        | @PostConstruct      | ← Runs here
 *        +---------------------+
 *                    │
 *                    ▼
 *        +---------------------+
 *        | InitializingBean    |
 *        | afterPropertiesSet()|
 *        +---------------------+
 *                    │
 *                    ▼
 *        +---------------------+
 *        | init-method         |
 *        +---------------------+
 *                    │
 *                    ▼
 *        +---------------------+
 *        | BPP after init      |
 *        +---------------------+
 *                    │
 *                    ▼
 *        +---------------------+
 *        | BEAN READY          |
 *        +---------------------+
 *
 *
 * =============================================================================
 * HOW SPRING INTERNALLY DISCOVERS @PostConstruct
 * =============================================================================
 *
 * Step 1: Spring internally uses a BeanPostProcessor called
 *         InitDestroyAnnotationBeanPostProcessor.
 *
 * Step 2: This BPP scans every bean for methods annotated
 *         with @PostConstruct (and @PreDestroy).
 *
 * Step 3: It caches the method references at startup.
 *
 * Step 4: During postProcessBeforeInitialization(),
 *         it invokes the cached @PostConstruct method.
 *
 *
 * This is why @PostConstruct works:
 *
 * BeanPostProcessor runs for every bean, and at the right
 * moment (before init), it calls your annotated method.
 *
 *
 * =============================================================================
 * COMMON MISTAKES
 * =============================================================================
 *
 * Mistake 1: Putting @PostConstruct in a @Configuration class
 *
 * @Configuration
 * public class AppConfig {
 *
 *     @PostConstruct           // Runs, but behavior is tricky
 *     public void init() { }   // BeanPostProcessors for config
 *                              // classes are different
 * }
 *
 *
 * Mistake 2: Relying on @PostConstruct order across beans
 *
 * @Component
 * public class A {
 *     @PostConstruct
 *     public void init() { }  // Does A run before B?
 * }
 *
 * @Component
 * public class B {
 *     @PostConstruct
 *     public void init() { }  // No guaranteed order!
 * }
 *
 * Solution: Use @DependsOn if order matters.
 *
 *
 * Mistake 3: Expecting @PostConstruct for prototype beans
 *            to run eagerly
 *
 * @Scope("prototype")
 * @Component
 * public class Report {
 *     @PostConstruct
 *     public void init() { }  // Runs EVERY time getBean() is called
 * }
 *
 * This is correct behavior, not a bug.
 *
 *
 * =============================================================================
 * @PostConstruct + EXCEPTION HANDLING
 * =============================================================================
 *
 * If @PostConstruct throws, the bean lifecycle FAILS.
 *
 * @Component
 * public class ConfigValidator {
 *
 *     @PostConstruct
 *     public void init() {
 *         throw new RuntimeException("DB not reachable");
 *     }
 * }
 *
 *
 * Result:
 *
 * - Bean is NOT added to container
 * - Application context fails to start
 * - Exception logged: BeanCreationException
 *
 * Root cause:
 * org.springframework.beans.factory.BeanCreationException:
 * Error creating bean with name 'configValidator':
 * Invocation of init method failed;
 * nested exception is java.lang.RuntimeException: DB not reachable
 *
 *
 * This is a feature, not a bug.
 * Fail-fast is better than silent failures at runtime.
 *
 *
 * =============================================================================
 * QUICK REFERENCE
 * =============================================================================
 *
 *  Aspect              Detail
 *  ──────────────────  ─────────────────────────────────────
 *  Package             jakarta.annotation.PostConstruct
 *  Target              Method
 *  Parameters          Zero (must be no-arg)
 *  Return type         void (return value ignored)
 *  Access modifier     Any (public, private, protected)
 *  Multiple per class  NO (exception thrown)
 *  Execution point     After DI, before bean ready
 *  Order across beans  Not guaranteed (use @DependsOn)
 *  With @Lazy          Runs on first use, not at startup
 *  With @Scope("prototype") Runs every getBean()
 *  Exception effect    Bean creation fails (fail-fast)
 *
 *
 * =============================================================================
 * KEY TAKEAWAYS (Summary)
 * =============================================================================
 *
 * 1. @PostConstruct is the SAFEST place to write init logic.
 *
 * 2. It runs AFTER all dependencies are injected.
 *
 * 3. Use it for: connecting to services, loading caches,
 *    validating config, scheduling tasks, starting threads.
 *
 * 4. It is JSR-250, NOT Spring-specific.
 *    Your code is portable across Jakarta EE containers.
 *
 * 5. Multiple @PostConstruct in one class is NOT allowed.
 *
 * 6. Only one method per class can have @PostConstruct.
 *
 * 7. If it throws, the whole bean (and app) may fail to start.
 *    This is GOOD — fail fast.
 *
 * 8. Prefer @PostConstruct over InitializingBean for
 *    framework-agnostic design.
 *
 *
 * =============================================================================
 * INTERVIEW QUESTIONS
 * =============================================================================
 *
 * Q1. What is @PostConstruct?
 *
 * Answer:
 *
 * @PostConstruct is a JSR-250 annotation that marks a method
 * to be executed after dependency injection is complete and
 * before the bean is fully available. It is the most common
 * way to perform initialization logic in Spring beans.
 *
 *
 * Q2. How is @PostConstruct different from a constructor?
 *
 * Answer:
 *
 * The constructor is called during instantiation (Step 4).
 * At that point, field and setter injection have not happened.
 * @PostConstruct runs later (Step 8) after ALL injection,
 * Aware callbacks, and BeanPostProcessor pre-processing
 * are complete. Use constructor to assign, @PostConstruct
 * to DO work.
 *
 *
 * Q3. Can I have multiple @PostConstruct methods in one class?
 *
 * Answer:
 *
 * No. Spring throws an exception if a class has more than
 * one @PostConstruct method.
 *
 *
 * Q4. What happens if @PostConstruct throws an exception?
 *
 * Answer:
 *
 * The bean creation fails. Spring throws
 * BeanCreationException and the application context
 * fails to start. This is intentional — fail-fast behavior.
 *
 *
 * Q5. Is @PostConstruct called for prototype beans?
 *
 * Answer:
 *
 * Yes. @PostConstruct runs every time a prototype bean
 * is created (every getBean() call). However, @PreDestroy
 * is NOT called for prototype beans.
 *
 *
 * Q6. What is the execution order of
 *     @PostConstruct, InitializingBean, and init-method?
 *
 * Answer:
 *
 * 1. @PostConstruct
 * 2. InitializingBean.afterPropertiesSet()
 * 3. Bean init-method (specified in @Bean(initMethod="..."))
 *
 *
 * Q7. Is @PostConstruct Spring-specific?
 *
 * Answer:
 *
 * No. It is a JSR-250 annotation from the Jakarta EE
 * (formerly Java EE) specification. It is supported by
 * any Jakarta EE container, not just Spring.
 *
 * =============================================================================
 */
public class PostConstructInDepth {
}
