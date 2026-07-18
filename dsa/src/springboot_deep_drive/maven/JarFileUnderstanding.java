package springboot_deep_drive.maven;

/**
 * ===========================================================
 * WHAT IS A JAR FILE ?
 * ===========================================================
 *
 * JAR = Java Archive
 *
 * A JAR file is a package that contains Java compiled code
 * and related files in a single file.
 *
 * Extension:
 *
 * .jar
 *
 * Examples:
 *
 * mysql-connector-j-8.3.0.jar
 * lombok-1.18.34.jar
 * spring-core-6.2.0.jar
 *
 *
 * ===========================================================
 * WHY JAR WAS CREATED ?
 * ===========================================================
 *
 * Imagine you have 500 Java classes.
 *
 * After compilation:
 *
 * UserService.class
 * ProductService.class
 * OrderService.class
 * CustomerService.class
 * ...
 * 500 more files
 *
 * Sharing hundreds of files is difficult.
 *
 * So Java provides JAR.
 *
 * JAR combines all files into a single file.
 *
 *
 * ===========================================================
 * REAL LIFE EXAMPLE
 * ===========================================================
 *
 * Without JAR:
 *
 * 100 books carried separately
 *
 * With JAR:
 *
 * 100 books packed inside one box
 *
 * Easy to carry
 * Easy to share
 * Easy to version
 *
 *
 * ===========================================================
 * JAVA COMPILATION PROCESS
 * ===========================================================
 *
 * Step 1:
 * Write Java code
 *
 * UserService.java
 *
 * Step 2:
 * Compile code
 *
 * javac UserService.java
 *
 * Step 3:
 * Java creates
 *
 * UserService.class
 *
 * Step 4:
 * Multiple .class files are packaged into a JAR
 *
 * app.jar
 *
 *
 * Flow:
 *
 * .java
 *   ↓
 * Compilation
 *   ↓
 * .class
 *   ↓
 * Packaging
 *   ↓
 * .jar
 *
 *
 * ===========================================================
 * WHAT IS INSIDE A JAR ?
 * ===========================================================
 *
 * A JAR is actually a ZIP file.
 *
 * Yes.
 *
 * You can rename:
 *
 * app.jar
 *
 * to
 *
 * app.zip
 *
 * and open it.
 *
 *
 * Example structure:
 *
 * app.jar
 * │
 * ├── META-INF
 * │     └── MANIFEST.MF
 * │
 * ├── UserService.class
 * ├── ProductService.class
 * ├── OrderService.class
 * │
 * └── Other Resources
 *
 *
 * ===========================================================
 * WHAT IS META-INF ?
 * ===========================================================
 *
 * META-INF stores metadata.
 *
 * Metadata means:
 *
 * Information about the package.
 *
 *
 * ===========================================================
 * WHAT IS MANIFEST.MF ?
 * ===========================================================
 *
 * Manifest file contains information about the JAR.
 *
 * Example:
 *
 * Manifest-Version: 1.0
 * Main-Class: com.demo.Application
 *
 *
 * JVM reads this file to know:
 *
 * Which class should start first?
 *
 *
 * ===========================================================
 * NORMAL JAR
 * ===========================================================
 *
 * Contains:
 *
 * - Compiled classes
 * - Resources
 *
 * Usually used as a library.
 *
 * Examples:
 *
 * Lombok Jar
 * Hibernate Jar
 * Jackson Jar
 *
 *
 * These JARs are not started directly.
 *
 * Applications use them.
 *
 *
 * ===========================================================
 * EXECUTABLE JAR
 * ===========================================================
 *
 * Contains:
 *
 * - Classes
 * - Main class information
 *
 * Can run directly.
 *
 * Example:
 *
 * java -jar app.jar
 *
 *
 * JVM finds Main-Class in MANIFEST.MF
 * and starts execution.
 *
 *
 * ===========================================================
 * SPRING BOOT JAR
 * ===========================================================
 *
 * Spring Boot generally creates:
 *
 * app.jar
 *
 * which contains:
 *
 * - Your code
 * - Spring Framework jars
 * - Third-party jars
 * - Resources
 *
 *
 * Everything packaged together.
 *
 *
 * ===========================================================
 * FAT JAR / UBER JAR
 * ===========================================================
 *
 * Fat Jar means:
 *
 * One JAR containing:
 *
 * - Application code
 * - All dependencies
 *
 *
 * Example:
 *
 * app.jar
 * ├── Application Classes
 * ├── Spring Classes
 * ├── Hibernate Classes
 * ├── Jackson Classes
 * └── Other Libraries
 *
 *
 * Everything inside one file.
 *
 *
 * Advantage:
 *
 * Easy deployment.
 *
 * Copy one file and run.
 *
 *
 * ===========================================================
 * WITHOUT JAR WHAT WOULD HAPPEN ?
 * ===========================================================
 *
 * Suppose application has:
 *
 * - 500 classes
 * - 50 libraries
 *
 * You would need to send:
 *
 * 550+ files
 *
 * Difficult to manage.
 *
 *
 * JAR solves:
 *
 * - Packaging
 * - Sharing
 * - Deployment
 * - Versioning
 *
 *
 * ===========================================================
 * JAR VS SOURCE CODE
 * ===========================================================
 *
 * SOURCE CODE
 *
 * .java files
 *
 * Human readable
 *
 *
 * JAR
 *
 * Contains compiled .class files
 *
 * Not easy for humans to read.
 *
 *
 * ===========================================================
 * JAR VS CLASS FILES
 * ===========================================================
 *
 * CLASS FILES
 *
 * User.class
 * Product.class
 * Order.class
 *
 * Many files
 *
 *
 * JAR
 *
 * app.jar
 *
 * Single package
 *
 *
 * ===========================================================
 * JAR VS APPLICATION
 * ===========================================================
 *
 * Many beginners think:
 *
 * JAR = Application
 *
 * Wrong.
 *
 * JAR is just a package format.
 *
 * A library can be a JAR.
 * An application can also be a JAR.
 *
 *
 * Examples:
 *
 * Library Jar:
 *
 * mysql-connector.jar
 *
 *
 * Application Jar:
 *
 * ecommerce-app.jar
 *
 *
 * ===========================================================
 * WHERE MAVEN COMES INTO THE PICTURE ?
 * ===========================================================
 *
 * Maven mainly works with JAR files.
 *
 * When you add dependency:
 *
 * <dependency>
 *     ...
 * </dependency>
 *
 * Maven downloads required JAR files automatically.
 *
 *
 * Example:
 *
 * Spring Boot Starter Web
 *
 * Maven downloads many JAR files and stores them
 * in local repository.
 *
 *
 * We will learn that in upcoming topics.
 *
 *
 * ===========================================================
 * INTERVIEW QUICK REVISION
 * ===========================================================
 *
 * Q. What is JAR?
 *
 * Java Archive file used to package compiled Java
 * classes and resources into a single file.
 *
 *
 * Q. Why JAR is used?
 *
 * Easy sharing, deployment and dependency management.
 *
 *
 * Q. Can a JAR be executed?
 *
 * Yes, if it contains a Main-Class entry.
 *
 *
 * Q. Is JAR a ZIP file?
 *
 * Internally yes.
 *
 *
 * ===========================================================
 */

public class JarFileUnderstanding {

    public static void main(String[] args) {

        System.out.println("Understanding JAR Files");

    }

}