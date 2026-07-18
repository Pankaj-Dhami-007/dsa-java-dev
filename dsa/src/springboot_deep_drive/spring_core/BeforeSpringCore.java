package springboot_deep_drive.spring_core;

/**
 * =============================================================================
 * BEFORE SPRING CORE
 * =============================================================================
 *
 * Before Spring Framework, developers were responsible for:
 *
 * 1. Creating objects manually.
 * 2. Managing dependencies manually.
 * 3. Managing object lifecycle manually.
 * 4. Wiring objects together manually.
 *
 * As applications grow, this approach becomes difficult to maintain.
 *
 * Example:
 *
 * An E-Commerce Application
 *
 * When a customer places an order:
 *
 * 1. OrderService handles order placement.
 * 2. PaymentService processes payment.
 * 3. NotificationService sends confirmation notifications.
 *
 * Without Spring, all these objects must be created manually.
 *
 * =============================================================================
 * OBJECT RELATIONSHIP
 * =============================================================================
 *
 * OrderService
 *      |
 *      +------> PaymentService
 *      |
 *      +------> NotificationService
 *
 * OrderService depends on PaymentService and NotificationService.
 *
 * These dependent objects are called Dependencies.
 *
 * =============================================================================
 * NOTE
 * =============================================================================
 *
 * Dependency Injection existed before Spring.
 *
 * Example:
 *
 * public OrderService(PaymentService paymentService) {
 *      this.paymentService = paymentService;
 * }
 *
 * This is Constructor Injection.
 *
 * Spring did NOT invent Dependency Injection.
 * Spring automates Dependency Injection using the IoC Container.
 *
 * =============================================================================
 */


/* ============================================================================
 * PAYMENT SERVICE
 * ============================================================================
 */
class PaymentService {

    public void processPayment() {
        System.out.println("Payment processed successfully.");
    }
}


/* ============================================================================
 * NOTIFICATION SERVICE
 * ============================================================================
 */
class NotificationService {

    public void sendNotification() {
        System.out.println("Order confirmation notification sent.");
    }
}


/* ============================================================================
 * ORDER SERVICE
 * ============================================================================
 */
class OrderService {

    private final PaymentService paymentService;
    private final NotificationService notificationService;

    /**
     * Constructor Injection
     *
     * Dependencies are being provided from outside the class.
     */
    public OrderService(
            PaymentService paymentService,
            NotificationService notificationService
    ) {
        this.paymentService = paymentService;
        this.notificationService = notificationService;
    }

    public void placeOrder() {

        System.out.println("Starting order placement process...");

        paymentService.processPayment();

        notificationService.sendNotification();

        System.out.println("Order placed successfully.");
    }
}


/* ============================================================================
 * APPLICATION ENTRY POINT
 * ============================================================================
 */
public class BeforeSpringCore {

    public static void main(String[] args) {

        /**
         * Step 1
         * Create all dependency objects manually.
         */
        PaymentService paymentService =
                new PaymentService();

        NotificationService notificationService =
                new NotificationService();

        /**
         * Step 2
         * Inject dependencies manually.
         */
        OrderService orderService =
                new OrderService(
                        paymentService,
                        notificationService
                );

        /**
         * Step 3
         * Use the object.
         */
        orderService.placeOrder();
    }
}


/**
 * =============================================================================
 * OUTPUT
 * =============================================================================
 *
 * Starting order placement process...
 * Payment processed successfully.
 * Order confirmation notification sent.
 * Order placed successfully.
 *
 * =============================================================================
 * PROBLEMS WITH THIS APPROACH
 * =============================================================================
 *
 * Problem #1
 * Manual Object Creation
 *
 * Every object must be created using the 'new' keyword.
 *
 *
 * Problem #2
 * Manual Dependency Wiring
 *
 * Developers must connect all objects manually.
 *
 *
 * Problem #3
 * Difficult Maintenance
 *
 * If the application contains hundreds of services,
 * object management becomes difficult.
 *
 *
 * Problem #4
 * Difficult Testing
 *
 * Replacing dependencies with mock implementations
 * requires additional effort.
 *
 *
 * Problem #5
 * No Centralized Object Management
 *
 * Every developer creates objects independently.
 *
 *
 * =============================================================================
 * WHAT SPRING WILL DO?
 * =============================================================================
 *
 * Spring says:
 *
 * "Give me the responsibility of creating and managing objects."
 *
 * Spring IoC Container will:
 *
 * 1. Create objects.
 * 2. Manage objects.
 * 3. Inject dependencies.
 * 4. Manage lifecycle.
 *
 * These managed objects are called Beans.
 *
 * This is the foundation of Spring Core.
 *
 * =============================================================================
 */