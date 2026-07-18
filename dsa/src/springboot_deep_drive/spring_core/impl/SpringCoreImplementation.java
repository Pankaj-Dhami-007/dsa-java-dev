package springboot_deep_drive.spring_core.impl;

//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import springboot_deep_drive.spring_core.impl.order.OrderService;

/**
 * =============================================================================
 * SPRING CORE IMPLEMENTATION
 * =============================================================================
 *
 * STEP 1
 * Create IoC Container
 *
 * STEP 2
 * Read Configuration Metadata
 *
 * STEP 3
 * Scan Components
 *
 * STEP 4
 * Create Beans
 *
 * STEP 5
 * Resolve Dependencies
 *
 * STEP 6
 * Store Beans Inside Container
 *
 * =============================================================================
 *
 * VERY IMPORTANT INTERVIEW QUESTION
 *
 * Q. What is ApplicationContext?
 *
 * ApplicationContext is the advanced
 * implementation of Spring IoC Container.
 *
 * It creates, manages and injects Beans.
 *
 * =============================================================================
 */

public class SpringCoreImplementation {

    public static void main(String[] args) {

        /**
         * ============================================================
         * STARTING SPRING IOC CONTAINER
         * ============================================================
         *
         * Spring reads AppConfig metadata.
         *
         * Then:
         *
         * 1. Scans Components
         * 2. Creates Beans
         * 3. Resolves Dependencies
         * 4. Stores Beans
         *
         */

//        ApplicationContext context =
//                new AnnotationConfigApplicationContext(
//                        AppConfig.class
//                );

        /**
         * Asking Spring:
         *
         * Give me OrderService Bean.
         */

//        OrderService orderService =
//                context.getBean(OrderService.class);

    //    orderService.placeOrder();
    }
}