package springboot_deep_drive.maven;

/**
 * ===========================================================
 * LIBRARY VS APPLICATION
 * ===========================================================
 *
 * Before learning Maven, first understand what a Library
 * and an Application are.
 *
 * Most Maven concepts revolve around managing libraries.
 *
 * -----------------------------------------------------------
 * WHAT IS AN APPLICATION?
 * -----------------------------------------------------------
 *
 * An Application is a complete software that users can run.
 *
 * Examples:
 * - WhatsApp
 * - Instagram
 * - Chrome Browser
 * - Spring Boot Project
 *
 * An application usually contains:
 * - Business Logic
 * - UI
 * - Database Connection
 * - APIs
 * - Configuration
 *
 * It can run independently.
 *
 * Example:
 *
 * public static void main(String[] args){
 *     System.out.println("Application Started");
 * }
 *
 * This is an application because it has an entry point
 * (main method) and can run by itself.
 *
 *
 * -----------------------------------------------------------
 * WHAT IS A LIBRARY?
 * -----------------------------------------------------------
 *
 * A Library is a collection of reusable code.
 *
 * A library cannot run independently.
 *
 * It provides functionality that another application can use.
 *
 * Examples:
 *
 * - Lombok
 * - Jackson
 * - Hibernate
 * - Spring Framework
 * - Apache Commons
 *
 *
 * Example:
 *
 * public class MathUtils {
 *
 *     public static int add(int a, int b){
 *         return a + b;
 *     }
 *
 * }
 *
 * This class is useful but cannot run itself.
 *
 * Another application will use it.
 *
 *
 * -----------------------------------------------------------
 * REAL LIFE EXAMPLE
 * -----------------------------------------------------------
 *
 * Application = House
 *
 * Library = Furniture inside house
 *
 * House can exist independently.
 *
 * Furniture alone cannot be used as a house.
 *
 *
 * -----------------------------------------------------------
 * WHY LIBRARIES ARE IMPORTANT?
 * -----------------------------------------------------------
 *
 * Imagine creating everything from scratch:
 *
 * - JSON Parsing
 * - Logging
 * - Database Connectivity
 * - Security
 * - Email Sending
 *
 * It would take months.
 *
 * Instead developers use libraries.
 *
 *
 * -----------------------------------------------------------
 * PROBLEM BEFORE MAVEN
 * -----------------------------------------------------------
 *
 * Earlier developers downloaded JAR files manually.
 *
 * Example:
 *
 * project
 * ├── libs
 * │   ├── jackson.jar
 * │   ├── hibernate.jar
 * │   ├── mysql.jar
 *
 * Problems:
 *
 * 1. Manual download
 * 2. Version conflicts
 * 3. Missing dependencies
 * 4. Hard to maintain
 * 5. Team synchronization issues
 *
 * Maven solves all these problems.
 *
 *
 * -----------------------------------------------------------
 * LIBRARY VS APPLICATION SUMMARY
 * -----------------------------------------------------------
 *
 * APPLICATION:
 * - Can run independently
 * - Has main method
 * - Final software product
 *
 * LIBRARY:
 * - Reusable code
 * - Used by applications
 * - Cannot run independently
 *
 *
 * -----------------------------------------------------------
 * WHAT YOU SHOULD LEARN NEXT?
 * -----------------------------------------------------------
 *
 * After understanding Library and Application,
 * next topic should be:
 *
 * 1. What is JAR?
 * 2. What is WAR?
 * 3. How Java code becomes executable?
 *
 * Because Maven mainly manages JAR files.
 *
 * ===========================================================
 */

public class LibraryVsApplication {

    public static void main(String[] args) {

        System.out.println("Library vs Application");

    }

}