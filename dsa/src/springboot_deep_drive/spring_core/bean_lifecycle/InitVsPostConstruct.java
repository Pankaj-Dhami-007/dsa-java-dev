package springboot_deep_drive.spring_core.bean_lifecycle;

/**
 * =============================================================================
 * @PostConstruct vs init-method vs InitializingBean
 * =============================================================================
 *
 * Spring gives 3 ways to run init logic after DI is done.
 * Here's exactly when each runs and which to use.
 *
 * =============================================================================
 * EXECUTION ORDER (fixed, cannot change)
 * =============================================================================
 *
 *   @PostConstruct    →    InitializingBean    →    init-method
 *   (1st)                   (2nd)                   (3rd)
 *
 *
 * =============================================================================
 * COMPARISON
 * =============================================================================
 *
 * Feature              @PostConstruct    InitializingBean  init-method
 * ───────────────────  ────────────────  ────────────────  ───────────────
 * Package              jakarta.annotation Spring-specific  Spring-specific
 * Coupled to Spring?   NO                YES               YES
 * Works on @Component? YES               YES               NO (only @Bean)
 * Works on @Bean?      YES               NO                YES
 * Code style           Clean method      Override method   Method name
 * Exception handling   Unchecked only    Any               Any
 *
 *
 * =============================================================================
 * WHICH TO USE?
 * =============================================================================
 *
 * 1st choice: @PostConstruct
 *
 *   - No Spring dependency in your code
 *   - Clean, readable
 *   - Works with @Component AND @Bean
 *
 *
 * 2nd choice: initMethod (only if @Bean)
 *
 *   - For @Bean definitions in @Configuration
 *   - No need to modify the bean class itself
 *
 *
 * Avoid: InitializingBean
 *
 *   - Couples your code to Spring
 *   - Harder to test
 *   - Only for legacy code
 *
 *
 * =============================================================================
 * PRACTICAL EXAMPLES
 * =============================================================================
 *
 * // === BEST: @PostConstruct (preferred) ===
 * @Component
 * public class GoodService {
 *     @PostConstruct
 *     public void init() {
 *         System.out.println("Runs first");
 *     }
 * }
 *
 *
 * // === OK: initMethod with @Bean ===
 * public class PlainClass {
 *     public void start() {
 *         System.out.println("Runs third");
 *     }
 * }
 *
 * @Configuration
 * public class Config {
 *     @Bean(initMethod = "start")
 *     public PlainClass plain() {
 *         return new PlainClass();
 *     }
 * }
 *
 *
 * // === AVOID: InitializingBean ===
 * @Component  // Coupled to Spring framework!
 * public class LegacyService implements InitializingBean {
 *     @Override
 *     public void afterPropertiesSet() {
 *         System.out.println("Runs second");
 *     }
 * }
 *
 *
 * =============================================================================
 * SUMMARY (15 seconds)
 * =============================================================================
 *
 * Order: @PostConstruct → InitializingBean → initMethod
 *
 * Always use @PostConstruct.
 * Use initMethod only for @Bean with 3rd-party classes.
 * Avoid InitializingBean.
 *
 *
 * =============================================================================
 */
public class InitVsPostConstruct {
}
