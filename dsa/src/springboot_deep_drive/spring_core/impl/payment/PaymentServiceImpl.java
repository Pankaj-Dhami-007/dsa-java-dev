package springboot_deep_drive.spring_core.impl.payment;

/**
 * =============================================================================
 * PAYMENT IMPLEMENTATION
 * =============================================================================
 *
 * @Component
 *
 * Marks this class as a Spring Bean.
 *
 * During Component Scanning,
 * Spring automatically creates its object.
 *
 * No need:
 *
 * new PaymentServiceImpl()
 *
 * =============================================================================
 */

//@Component
public class PaymentServiceImpl implements PaymentService {

    @Override
    public void processPayment() {

        System.out.println("Payment processed successfully.");
    }
}