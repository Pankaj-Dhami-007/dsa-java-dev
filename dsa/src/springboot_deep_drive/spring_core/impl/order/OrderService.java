package springboot_deep_drive.spring_core.impl.order;

//import org.springframework.stereotype.Component;
import springboot_deep_drive.spring_core.impl.notification.NotificationService;
import springboot_deep_drive.spring_core.impl.payment.PaymentService;

/**
 * =============================================================================
 * ORDER SERVICE
 * =============================================================================
 *
 * Spring performs Constructor Injection automatically.
 *
 * Since PaymentService and NotificationService
 * are already Beans,
 * Spring injects them automatically.
 *
 *
 * =============================================================================
 * WHY INTERFACES?
 * =============================================================================
 *
 * BAD DESIGN
 *
 * private PaymentServiceImpl paymentService;
 *
 *
 * GOOD DESIGN
 *
 * private PaymentService paymentService;
 *
 *
 * Depending on abstraction instead of
 * concrete implementation.
 *
 * =============================================================================
 */

//@Component
public class OrderService {

    private final PaymentService paymentService;
    private final NotificationService notificationService;

    public OrderService(
            PaymentService paymentService,
            NotificationService notificationService
    ) {
        this.paymentService = paymentService;
        this.notificationService = notificationService;
    }

    public void placeOrder() {

        System.out.println("Order Placement Started");
        paymentService.processPayment();
        notificationService.sendNotification();
        System.out.println("Order Placed Successfully");
    }
}