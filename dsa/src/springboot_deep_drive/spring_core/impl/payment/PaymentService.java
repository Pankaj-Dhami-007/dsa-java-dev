package springboot_deep_drive.spring_core.impl.payment;

/**
 * =============================================================================
 * PAYMENT SERVICE CONTRACT
 * =============================================================================
 *
 * Interface represents WHAT should happen.
 *
 * Implementation represents HOW it happens.
 *
 * This helps achieve Loose Coupling.
 *
 * =============================================================================
 */

public interface PaymentService {

    void processPayment();
}