package designpattern.behavioral.strategy.impl;

import designpattern.behavioral.strategy.PaymentStrategy;

public class UpiPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid using UPI: " + amount);
    }
}
