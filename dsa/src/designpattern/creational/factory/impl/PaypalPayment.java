package designpattern.creational.factory.impl;

import designpattern.creational.factory.Payment;

public class PaypalPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Processing PayPal payment of " + amount);
    }
}
