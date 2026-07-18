package designpattern.behavioral.strategy.impl;

import designpattern.behavioral.strategy.PaymentStrategy;

public class PaypalPayment implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println("Paid using Paypal: " + amount);
    }
}
