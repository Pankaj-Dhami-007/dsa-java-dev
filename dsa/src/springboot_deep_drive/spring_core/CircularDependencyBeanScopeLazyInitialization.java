package springboot_deep_drive.spring_core;

/**
 * =============================================================================
 * CIRCULAR DEPENDENCY, BEAN SCOPE & LAZY INITIALIZATION
 * =============================================================================
 *
 * Three important Spring Core concepts that control
 * how beans are created, scoped, and wired together.
 *
 * =============================================================================
 * WHAT IS A BEAN DEFINITION? (Prerequisite)
 * =============================================================================
 *
 * Before discussing scope, we must understand Bean Definition.
 *
 * Bean Definition = Metadata (blueprint) of a bean.
 *
 * It is NOT the actual object.
 *
 * It contains:
 *
 * - Bean class (which class to instantiate)
 * - Bean name / ID
 * - Scope (singleton / prototype)
 * - Dependencies (what other beans it needs)
 * - Lazy / Eager flag
 * - Init and destroy method names
 *
 *
 * Example Bean Definition (internal Spring representation):
 *
 * Bean Name:      "paymentService"
 * Bean Class:     PaymentServiceImpl.class
 * Scope:          singleton
 * Dependencies:   [ ]
 * Lazy:           false
 *
 *
 * Spring creates one Bean Definition per @Component or @Bean.
 *
 * Then uses that definition to create actual Bean Objects.
 *
 * Bean Definition                        Bean Object
 * ───────────────                        ───────────
 * Blueprint                              Actual instance
 * Metadata (information)                 Concrete object in memory
 * Created during scanning                Created during instantiation
 *
 *
 * =============================================================================
 * WHAT DOES "SINGLETON MEANS 1 INSTANCE PER BEAN DEFINITION" MEAN?
 * =============================================================================
 *
 * This is the key point:
 *
 * Singleton scope = 1 instance PER BEAN DEFINITION per IoC container.
 *
 * NOT 1 instance globally for the whole application.
 *
 *
 * Example:
 *
 * @Component   → BeanDef: "paymentService"     → 1 singleton instance
 * @Component   → BeanDef: "orderService"       → 1 singleton instance
 * @Component   → BeanDef: "notificationService" → 1 singleton instance
 *
 * Container
 * ┌──────────────────────────────────────────────────┐
 * │ BeanDef: paymentService    Instance: PaySvc@123  │
 * │ BeanDef: orderService      Instance: OrdSvc@456  │  ← separate instances
 * │ BeanDef: notificationSvc   Instance: NotifSvc@789│
 * └──────────────────────────────────────────────────┘
 *
 * Each @Component = one BeanDef = one instance.
 *
 * If you have 5 @Component classes, you have 5 singleton instances.
 *
 * So you are RIGHT: singleton = 1 instance per bean definition.
 *
 *
 * Now compare with prototype:
 *
 * @Scope("prototype") @Component → BeanDef: "reportService"
 *
 * Every getBean() call creates a NEW instance from the SAME BeanDef.
 *
 *
 * =============================================================================
 * 1. CIRCULAR DEPENDENCY
 * =============================================================================
 *
 * Definition:
 *
 * Circular Dependency occurs when Bean A depends on Bean B,
 * and Bean B depends on Bean A.
 *
 *          A ──────► B
 *          ▲         │
 *          │         │
 *          └─────────┘
 *
 * Spring cannot decide which bean to create first.
 *
 *
 * Example:
 *
 * @Component
 * public class A {
 *     private final B b;
 *     public A(B b) { this.b = b; }    // A needs B
 * }
 *
 * @Component
 * public class B {
 *     private final A a;
 *     public B(A a) { this.a = a; }    // B needs A
 * }
 *
 *
 * Spring throws:
 * BeanCurrentlyInCreationException
 *
 * "Requested bean is currently in creation:
 *  Is there an unresolvable circular reference?"
 *
 *
 * =============================================================================
 * WHY DOES THIS HAPPEN?
 * =============================================================================
 *
 * Spring creates singleton beans eagerly during startup.
 *
 * Step 1: Spring starts creating A.
 * Step 2: Spring sees A needs B.
 * Step 3: Spring starts creating B.
 * Step 4: Spring sees B needs A.
 * Step 5: A is not ready yet.
 *
 * DEADLOCK.
 *
 *
 * =============================================================================
 * SOLUTIONS FOR CIRCULAR DEPENDENCY
 * =============================================================================
 *
 * Solution 1: @Lazy on one side
 *
 * @Component
 * public class A {
 *     private final B b;
 *     public A(@Lazy B b) { this.b = b; }
 * }
 *
 * Spring creates a proxy instead of the actual B.
 * Actual B is created only when A actually uses it.
 *
 *
 * Solution 2: Setter Injection (not recommended)
 *
 * @Component
 * public class A {
 *     private B b;
 *     @Autowired
 *     public void setB(B b) { this.b = b; }
 * }
 *
 * Problem: Mutable, hard to test, breaks immutability.
 *
 *
 * Solution 3: @Autowired on field (not recommended)
 *
 * Same issues as Setter Injection.
 *
 *
 * Solution 4: Restructure the code
 *
 * Extract the shared dependency into a third class.
 *
 *     A ────► C ◄──── B
 *
 * C contains the common logic.
 * A and B depend on C only.
 *
 *
 * Best Practice:
 *
 * Circular Dependency is a design smell.
 * Always prefer Solution 4 (restructure).
 *
 *
 * =============================================================================
 * 2. BEAN SCOPE
 * =============================================================================
 *
 * Scope defines the lifecycle and visibility of a Bean.
 *
 * @Scope("scopeName")
 *
 * Default scope: singleton
 *
 *
 * =============================================================================
 * SINGLETON SCOPE
 * =============================================================================
 *
 * 1 instance per Bean Definition per IoC Container.
 *
 * @Scope("singleton")   // default, can be omitted
 * @Component
 * public class ReportService { }
 *
 * BeanDef: "reportService"
 * Container
 * ┌──────────────────────────────────────┐
 * │  ONE instance (shared everywhere)   │
 * │                                      │
 * │  getBean("reportService") ─────► same│
 * │  getBean("reportService") ─────► same│
 * │  @Autowired ReportService  ─────► same│
 * └──────────────────────────────────────┘
 *
 * Different BeanDefs get different instances:
 *
 * reportService    → 1 instance (from its BeanDef)
 * orderService     → 1 instance (from its BeanDef)
 * paymentService   → 1 instance (from its BeanDef)
 *
 * Use when:
 * - Stateless beans
 * - Shared utility/service beans
 * - Thread-safe classes
 *
 *
 * =============================================================================
 * PROTOTYPE SCOPE
 * =============================================================================
 *
 * New instance every time the bean is requested.
 *
 * @Scope("prototype")
 * @Component
 * public class ReportService { }
 *
 * Container
 * ┌──────────────────────────────────────┐
 * │  getBean() ─────► new instance       │
 * │  getBean() ─────► new instance       │
 * │  getBean() ─────► new instance       │
 * └──────────────────────────────────────┘
 *
 * Use when:
 * - Stateful beans
 * - Objects that should not be shared
 *
 * Important:
 * Spring does NOT manage the complete lifecycle
 * of prototype beans. destroy() is not called.
 *
 *
 * =============================================================================
 * SINGLETON VS PROTOTYPE
 * =============================================================================
 *
 *               Singleton          Prototype
 *               ─────────          ─────────
 * Instances     1 per container    New per request
 * Creation      At startup         On demand
 * Memory        Shared             Separate
 * Thread-safety Required           Not required
 * destroy()     Called             Not called
 *
 * Default:      YES                NO
 *
 *
 * =============================================================================
 * WEB SCOPES (Spring MVC / WebFlux)
 * =============================================================================
 *
 * @Scope("request")
 * One instance per HTTP request.
 *
 * @Scope("session")
 * One instance per HTTP session.
 *
 * @Scope("application")
 * One instance per ServletContext.
 *
 *
 * =============================================================================
 * SCOPE MAPPING
 * =============================================================================
 *
 * singleton     ─── 1 per Bean Definition per IoC Container
 * prototype     ─── 1 per getBean()
 * request       ─── 1 per HTTP Request
 * session       ─── 1 per HTTP Session
 * application   ─── 1 per ServletContext
 * websocket     ─── 1 per WebSocket
 *
 *
 * =============================================================================
 * 3. LAZY INITIALIZATION
 * =============================================================================
 *
 * By default, Spring creates all singleton beans
 * at container startup (eager initialization).
 *
 * @Lazy delays bean creation until first use.
 *
 *
 * Example:
 *
 * @Lazy
 * @Component
 * public class HeavyReportService {
 *     public HeavyReportService() {
 *         System.out.println("Created!");
 *     }
 * }
 *
 * Output:
 *
 * Container starts ─► No output
 * context.getBean() ─► "Created!"
 *
 *
 * =============================================================================
 * EAGER VS LAZY
 * =============================================================================
 *
 * Eager (Default)
 *
 * Container start
 *      │
 *      ▼
 * All singleton beans are created
 *      │
 *      ▼
 * Application ready
 *
 *
 * Lazy
 *
 * Container start
 *      │
 *      ▼
 * Beans are NOT created
 *      │
 *      ▼
 * First getBean() call
 *      │
 *      ▼
 * Bean is created
 *
 *
 * =============================================================================
 * WHERE TO USE @Lazy
 * =============================================================================
 *
 * 1. Heavy beans that are not always needed
 *    (e.g. ReportService, BatchProcessor)
 *
 * 2. Reduce application startup time
 *
 * 3. Break circular dependencies
 *
 *
 * Where NOT to use @Lazy:
 *
 * 1. Frequently used beans (lazy adds overhead)
 * 2. Beans needed for initial requests
 * 3. Configuration beans (may cause cascading lazy loading)
 *
 *
 * =============================================================================
 * @Lazy ON @Configuration
 * =============================================================================
 *
 * @Lazy
 * @Configuration
 * public class AppConfig {
 *     @Bean
 *     public BeanA beanA() { return new BeanA(); }
 *
 *     @Bean
 *     public BeanB beanB() { return new BeanB(); }
 * }
 *
 * All @Bean methods inside this @Configuration
 * become lazy. Beans are created only when first requested.
 *
 *
 * =============================================================================
 * CIRCULAR DEPENDENCY + @Lazy EXAMPLE
 * =============================================================================
 *
 * // Bean A depends on Bean B
 * @Component
 * public class A {
 *     private final B b;
 *
 *     public A(@Lazy B b) {
 *         this.b = b;
 *     }
 *
 *     public void doSomething() {
 *         b.help();  // B is created here (first use)
 *     }
 * }
 *
 * // Bean B depends on Bean A
 * @Component
 * public class B {
 *     private final A a;
 *
 *     public B(A a) {
 *         this.a = a;  // A already exists in container
 *     }
 * }
 *
 *
 * Flow:
 *
 * 1. Spring creates A
 * 2. Spring sees A needs B
 * 3. @Lazy creates a proxy, NOT the actual B
 * 4. A is fully created and stored
 * 5. Spring creates B (A already exists, no problem)
 * 6. B is fully created and stored
 * 7. When A calls b.help(), proxy creates real B
 *
 *
 * =============================================================================
 * COMPLETE REFERENCE TABLE
 * =============================================================================
 *
 * Concept              Annotation         Behavior
 * ───────              ──────────         ────────
 * Singleton            @Scope("singleton") 1 instance per BeanDef per container
 * Prototype            @Scope("prototype") New instance per request
 * Eager Init           (default)           Created at startup
 * Lazy Init            @Lazy               Created on first use
 * Circular Dependency  @Lazy + proxy       Breaks the cycle
 *
 *
 * =============================================================================
 * INTERVIEW QUESTIONS
 * =============================================================================
 *
 * Q1. What is a Circular Dependency in Spring?
 *
 * Answer:
 *
 * Circular Dependency occurs when Bean A depends on Bean B
 * and Bean B depends on Bean A. Spring cannot determine
 * which bean to create first, resulting in
 * BeanCurrentlyInCreationException.
 *
 *
 * Q2. How can Circular Dependency be resolved?
 *
 * Answer:
 *
 * 1. Use @Lazy on one side to create a proxy
 * 2. Use Setter Injection (not recommended)
 * 3. Redesign the code to remove the cycle (recommended)
 *
 *
 * Q3. What is the default scope in Spring?
 *
 * Answer:
 *
 * singleton. One instance per bean definition per IoC Container.
 *
 *
 * Q4. What is the difference between singleton and prototype?
 *
 * Answer:
 *
 * singleton: one instance per bean definition per container, created eagerly,
 * shared across the application.
 *
 * prototype: new instance per request, created on demand,
 * not shared, destroy lifecycle not managed.
 *
 *
 * Q5. What is @Lazy used for?
 *
 * Answer:
 *
 * @Lazy delays bean creation until first use.
 * It is used to reduce startup time, optimize memory,
 * and break circular dependencies.
 *
 *
 * Q6. What happens when a prototype bean is injected
 *     into a singleton bean?
 *
 * Answer:
 *
 * The singleton bean receives the prototype bean
 * only once at injection time. The prototype bean
 * becomes de facto singleton within that scope.
 *
 * To get a new prototype instance every time,
 * use @Scope with proxyMode or look-up method injection.
 *
 *
 * =============================================================================
 */
public class CircularDependencyBeanScopeLazyInitialization {
}
