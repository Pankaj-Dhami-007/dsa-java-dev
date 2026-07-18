package designpattern.creational.factory.impl;

import designpattern.creational.factory.Payment;

public class CardPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Processing Card payment of " + amount);
    }
}
