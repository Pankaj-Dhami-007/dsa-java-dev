package designpattern.creational.singleton;

public class SingletonDesignPattern {

    // Only one object exists in the entire application + global access.

    public static void main(String[] args) {

        // A class that allows only ONE instance to be created
        // and provides a global access point to it.

        // Why do we use it?

        // Sometimes you need only one object in the entire system

        // Examples:
        // Logger
        // Database Connection Manager
        // Configuration Manager
        // Cache Manager
        // Thread Pool Manager

        // If multiple objects are created:
        // inconsistent behavior
        // duplicate resource usage
        // memory waste
        // connection overload

        // Real-Life Example:
        // President of a country
        // Only one president exists at a time
        // Everyone refers to the same person

        // Banking System Example:
        // DB Connection Manager

        // Opening DB connection is expensive
        // So one manager controls all connections

        // WRONG:
        // Every service creates new DB manager

        // CORRECT:
        // One central DB manager object

    }
}