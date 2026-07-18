package springboot_deep_drive.maven;

/**
 * ===========================================================
 *           CLASSPATH & EXTERNAL LIBRARIES DEEP DIVE
 * ===========================================================
 *
 * WHAT IS CLASSPATH?
 * -----------------------------------------------------------
 *
 * Classpath tells JVM:
 *
 * "Where should I search for classes?"
 *
 * JVM can only execute classes that it can find.
 *
 *
 * ===========================================================
 * WHY CLASSPATH IS NEEDED?
 * ===========================================================
 *
 * Example:
 *
 * UserService.java
 *
 * uses
 *
 * EmployeeService.java
 *
 * JVM must know where EmployeeService.class exists.
 *
 *
 * ===========================================================
 * CLASSPATH SOURCES
 * ===========================================================
 *
 * JVM searches classes from:
 *
 * 1. Current Project Classes
 * 2. Dependency JAR Files
 * 3. External Libraries
 * 4. Custom Paths
 *
 *
 * ===========================================================
 * BEFORE MAVEN
 * ===========================================================
 *
 * Developer manually downloaded:
 *
 * mysql.jar
 * gson.jar
 * lombok.jar
 *
 * Then manually added them into:
 *
 * Project Build Path
 *
 *
 * Problems:
 *
 * - Time consuming
 * - Version conflicts
 * - Missing dependencies
 * - Difficult maintenance
 *
 *
 * ===========================================================
 * MAVEN SOLUTION
 * ===========================================================
 *
 * Add dependency in pom.xml
 *
 * Maven:
 *
 * 1. Downloads jar
 * 2. Downloads transitive dependencies
 * 3. Adds jars to classpath
 * 4. Maintains versions
 *
 *
 * ===========================================================
 * EXTERNAL LIBRARIES
 * ===========================================================
 *
 * External libraries are pre-written code
 * developed by someone else.
 *
 *
 * Examples:
 *
 * Spring Framework
 * Hibernate
 * Jackson
 * Gson
 * Lombok
 * MySQL Driver
 * Kafka Client
 *
 *
 * ===========================================================
 * HOW EXTERNAL LIBRARY IS USED?
 * ===========================================================
 *
 * Step 1:
 * Add dependency
 *
 * Step 2:
 * Maven downloads jar
 *
 * Step 3:
 * Jar stored in .m2 repository
 *
 * Step 4:
 * Added to classpath
 *
 * Step 5:
 * Import classes
 *
 *
 * Example:
 *
 * import com.google.gson.Gson;
 *
 *
 * ===========================================================
 * COMPLETE FLOW
 * ===========================================================
 *
 * pom.xml
 *      ↓
 * Maven Reads Dependency
 *      ↓
 * Downloads Jar
 *      ↓
 * Stores in .m2
 *      ↓
 * Adds to Classpath
 *      ↓
 * Compiler finds classes
 *      ↓
 * JVM loads classes
 *
 *
 * ===========================================================
 * CLASS LOADING
 * ===========================================================
 *
 * JVM does not load all classes at startup.
 *
 * Classes are loaded when required.
 *
 *
 * Example:
 *
 * MyController
 *      ↓
 * MyService
 *      ↓
 * MyRepository
 *
 *
 * JVM loads classes as needed.
 *
 *
 * ===========================================================
 * IMPORTANT INTERVIEW QUESTION
 * ===========================================================
 *
 * Q. What happens after adding dependency in pom.xml?
 *
 * Answer:
 *
 * Maven downloads jar from repository,
 * stores it inside local repository,
 * adds it to classpath,
 * compiler and JVM can now access classes.
 *
 * ===========================================================
 */
public class ClasspathAndExternalLibraries {

    public static void main(String[] args) {

        System.out.println("Classpath & External Libraries");

    }
}