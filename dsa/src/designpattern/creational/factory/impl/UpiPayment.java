package designpattern.creational.factory.impl;

import designpattern.creational.factory.Payment;

public class UpiPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Processing UPI payment of " + amount);
    }
}
