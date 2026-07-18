package designpattern.creational.factory.enums;

import designpattern.creational.factory.Payment;
import designpattern.creational.factory.impl.CardPayment;
import designpattern.creational.factory.impl.PaypalPayment;
import designpattern.creational.factory.impl.UpiPayment;

public enum PaymentType {

    UPI {
        public Payment create() {
            return new UpiPayment();
        }
    },
    CARD {
        public Payment create() {
            return new CardPayment();
        }
    },
    PAYPAL {
        public Payment create() {
            return new PaypalPayment();
        }
    };

    public abstract Payment create();
}

/*
use->
Payment payment = PaymentType.UPI.create();
payment.pay(500);


No factory class needed
✔ No switch-case
✔ No map
✔ Fully type-safe
✔ Open/Closed (just add new enum constant)

Enum approach is powerful but:
Harder to extend dynamically (runtime additions)
Not ideal if implementations come from external modules
 */
