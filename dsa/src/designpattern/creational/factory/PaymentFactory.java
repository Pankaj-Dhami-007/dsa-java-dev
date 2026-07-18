package designpattern.creational.factory;

import designpattern.creational.factory.impl.CardPayment;
import designpattern.creational.factory.impl.PaypalPayment;
import designpattern.creational.factory.impl.UpiPayment;

import java.util.HashMap;
import java.util.Map;

public class PaymentFactory {

    // Problem with this approach
    // Every time you add new payment → modify factory → violates Open/Closed Principle
    // PRO VERSION (Factory + Strategy Pattern)
    // Use Map instead of switch

//    public static Payment getPayment(String type) {
//
//        switch (type.toUpperCase()) {
//            case "UPI":
//                return new UpiPayment();
//            case "CARD":
//                return new CardPayment();
//            case "PAYPAL":
//                return new PaypalPayment();
//            default:
//                throw new IllegalArgumentException("Invalid payment type");
//        }
//    }

    // Replace implementations at runtime
    // Load dynamically from config / DB
    //Inject via framework
    // Switch-case can’t do this
    private static final Map<String, Payment> paymentMap = new HashMap<>();
    static {
        paymentMap.put("UPI", new UpiPayment());
        paymentMap.put("CARD", new CardPayment());
        paymentMap.put("PAYPAL", new PaypalPayment());
    }

    public static Payment getPayment(String type) {
        Payment payment = paymentMap.get(type.toUpperCase());
        if (payment == null) {
            throw new IllegalArgumentException("Invalid payment type");
        }
        return payment;
    }

    // Works with Dependency Injection (Spring)
    // @Autowired
    //Map<String, Payment> paymentMap;
    // @Component("UPI")
    //class UpiPayment implements Payment {}
    // You don’t modify factory at all
    //Just add new class with annotation
    // That’s true Open/Closed Principle compliance

    // Extensibility (Plugin-like Systems)


    // Even Better (Pro Version — Enum as Factory)
    // You can remove factory class completely
    // Enum with behavior (VERY POWERFUL)

}

/*

           +------------------+
           |   Payment        |  ← Strategy Interface
           +------------------+
              /     |     \
             /      |      \
   UPI     CARD   PAYPAL   ← Strategies

                ↑
         Factory selects this

         Internally:

Strategy → different payment methods
Factory → selects based on user input

Strategy defines multiple payment behaviors, and Factory is used to dynamically
select the appropriate strategy at runtime
 */
