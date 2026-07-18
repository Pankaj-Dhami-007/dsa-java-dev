package designpattern.behavioral.strategy;

public interface PaymentStrategy {

    void pay(double amount);
}

// This is the common contract.
// All payment methods must implement pay().
//
// Interface defines a COMMON CONTRACT.
// Every payment method MUST implement pay()
