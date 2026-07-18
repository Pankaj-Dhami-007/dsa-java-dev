package springboot_deep_drive.maven;

/**
 * ===========================================================
 *                    MAVEN COMPLETE GUIDE
 * ===========================================================
 *
 * WHAT IS MAVEN?
 * -----------------------------------------------------------
 * Maven is a Build Automation and Dependency Management Tool.
 *
 * It is mainly used in Java projects to:
 *
 * 1. Download external libraries automatically
 * 2. Build the project
 * 3. Run tests
 * 4. Package application into JAR/WAR
 * 5. Manage project dependencies
 * 6. Maintain project structure
 *
 * Maven was created by Apache.
 *
 *
 * ===========================================================
 * BEFORE MAVEN
 * ===========================================================
 *
 * Suppose project needs:
 *
 * - MySQL Driver
 * - Spring Framework
 * - Log4j
 * - Jackson
 *
 * Without Maven:
 *
 * 1. Open website
 * 2. Download JAR manually
 * 3. Put JAR inside project lib folder
 * 4. Configure classpath
 * 5. Download dependencies of dependencies manually
 *
 * Problem:
 *
 * - Time consuming
 * - Version conflicts
 * - Dependency management nightmare
 *
 *
 * ===========================================================
 * WITH MAVEN
 * ===========================================================
 *
 * Just add dependency inside pom.xml
 *
 * Maven automatically:
 *
 * 1. Downloads dependency
 * 2. Downloads transitive dependencies
 * 3. Maintains versions
 * 4. Adds everything into classpath
 *
 *
 * ===========================================================
 * WHAT IS BUILD?
 * ===========================================================
 *
 * Converting source code into executable artifact.
 *
 * Example:
 *
 * Source Code
 *      ↓
 * Compilation
 *      ↓
 * Testing
 *      ↓
 * Packaging
 *      ↓
 * JAR/WAR
 *
 *
 * ===========================================================
 * MAVEN IN SPRING BOOT
 * ===========================================================
 *
 * Spring Boot heavily depends on Maven.
 *
 * Common tasks:
 *
 * 1. Download Spring Boot dependencies
 * 2. Build executable JAR
 * 3. Run unit tests
 * 4. Package application
 * 5. Manage versions
 *
 *
 * ===========================================================
 * STANDARD MAVEN PROJECT STRUCTURE
 * ===========================================================
 *
 * project
 * │
 * ├── src
 * │   ├── main
 * │   │   ├── java
 * │   │   └── resources
 * │   │
 * │   └── test
 * │       ├── java
 * │       └── resources
 * │
 * ├── pom.xml
 * │
 * └── target
 *
 *
 * src/main/java
 *      Production code
 *
 * src/main/resources
 *      application.properties
 *      application.yml
 *
 * src/test/java
 *      JUnit test cases
 *
 * target
 *      Generated files after build
 *
 *
 * ===========================================================
 * WHAT IS POM.XML?
 * ===========================================================
 *
 * POM = Project Object Model
 *
 * Heart of Maven Project.
 *
 * Contains:
 *
 * - Project information
 * - Dependencies
 * - Plugins
 * - Build configuration
 * - Version details
 *
 *
 * ===========================================================
 * MAVEN REPOSITORIES
 * ===========================================================
 *
 * Maven stores dependencies in repositories.
 *
 * There are 3 types:
 *
 * 1. Local Repository
 * 2. Central Repository
 * 3. Remote Repository
 *
 *
 * ===========================================================
 * LOCAL REPOSITORY
 * ===========================================================
 *
 * Location:
 *
 * Windows:
 *
 * C:\Users\<user>\.m2\repository
 *
 *
 * Purpose:
 *
 * Downloaded JARs are stored locally.
 *
 *
 * ===========================================================
 * CENTRAL REPOSITORY
 * ===========================================================
 *
 * Official Maven repository.
 *
 * Maintained by Maven community.
 *
 * URL:
 *
 * https://repo.maven.apache.org
 *
 *
 * ===========================================================
 * REMOTE REPOSITORY
 * ===========================================================
 *
 * Company specific repository.
 *
 * Examples:
 *
 * Nexus
 * Artifactory
 * GitHub Packages
 *
 *
 * ===========================================================
 * COMPLETE DEPENDENCY DOWNLOAD FLOW
 * ===========================================================
 *
 * pom.xml
 *      ↓
 * Maven checks Local Repository
 *      ↓
 * Found?
 *      ↓
 * YES → Use Local Copy
 *
 * NO
 *      ↓
 * Check Remote Repository
 *      ↓
 * Check Maven Central
 *      ↓
 * Download JAR
 *      ↓
 * Store in .m2 repository
 *      ↓
 * Add to Classpath
 *
 *
 * ===========================================================
 * WHAT ARE TRANSITIVE DEPENDENCIES?
 * ===========================================================
 *
 * You add:
 *
 * spring-boot-starter-web
 *
 * Maven automatically downloads:
 *
 * - Spring Core
 * - Spring MVC
 * - Jackson
 * - Tomcat
 * - Logging libraries
 *
 * These are called Transitive Dependencies.
 *
 *
 * ===========================================================
 * CLASSPATH
 * ===========================================================
 *
 * Classpath tells JVM:
 *
 * "Where should I search classes?"
 *
 * Sources:
 *
 * 1. Current project classes
 * 2. Dependency JARs
 * 3. External libraries
 *
 * Maven automatically manages classpath.
 *
 *
 * ===========================================================
 * EXTERNAL LIBRARIES
 * ===========================================================
 *
 * Example:
 *
 * Gson
 * Lombok
 * Jackson
 * MySQL Driver
 *
 * Add dependency in pom.xml
 *
 * Maven downloads JAR and adds to classpath.
 *
 *
 * ===========================================================
 * HOW TO USE EXTERNAL LIBRARIES?
 * ===========================================================
 *
 * Step 1:
 * Add dependency in pom.xml
 *
 * Step 2:
 * Maven downloads JAR
 *
 * Step 3:
 * Import classes
 *
 * Example:
 *
 * import com.google.gson.Gson;
 *
 *
 * ===========================================================
 * MAVEN LIFECYCLE
 * ===========================================================
 *
 * Maven works using predefined lifecycles.
 *
 * There are 3 lifecycles:
 *
 * 1. Clean Lifecycle
 * 2. Default Lifecycle
 * 3. Site Lifecycle
 *
 *
 * ===========================================================
 * CLEAN LIFECYCLE
 * ===========================================================
 *
 * Goal:
 *
 * Remove previous build files.
 *
 * Phase:
 *
 * clean
 *
 * Command:
 *
 * mvn clean
 *
 * Deletes:
 *
 * target folder
 *
 *
 * ===========================================================
 * DEFAULT LIFECYCLE
 * ===========================================================
 *
 * Main build lifecycle.
 *
 * Phases:
 *
 * validate
 * compile
 * test
 * package
 * verify
 * install
 * deploy
 *
 *
 * validate
 * --------
 * Validate project structure.
 *
 *
 * compile
 * -------
 * Converts .java → .class
 *
 *
 * test
 * ----
 * Executes JUnit tests.
 *
 *
 * package
 * -------
 * Creates JAR/WAR.
 *
 *
 * install
 * -------
 * Copies artifact into local repository.
 *
 *
 * deploy
 * ------
 * Uploads artifact to remote repository.
 *
 *
 * ===========================================================
 * SITE LIFECYCLE
 * ===========================================================
 *
 * Generates project documentation.
 *
 * Commands:
 *
 * mvn site
 *
 *
 * ===========================================================
 * COMMON MAVEN COMMANDS
 * ===========================================================
 *
 * mvn clean
 *
 * mvn compile
 *
 * mvn test
 *
 * mvn package
 *
 * mvn install
 *
 * mvn clean install
 *
 * mvn dependency:tree
 *
 *
 * ===========================================================
 * MAVEN PLUGINS
 * ===========================================================
 *
 * Maven itself does not compile Java.
 *
 * It uses Plugins.
 *
 * Examples:
 *
 * maven-compiler-plugin
 * maven-surefire-plugin
 * spring-boot-maven-plugin
 * maven-jar-plugin
 *
 *
 * ===========================================================
 * NORMAL JAR VS SPRING BOOT JAR
 * ===========================================================
 *
 * Normal JAR:
 *
 * Contains:
 * - Project classes
 *
 * Requires external dependencies separately.
 *
 *
 * Spring Boot Fat JAR:
 *
 * Contains:
 * - Project classes
 * - All dependency JARs
 * - Embedded Tomcat
 *
 * Can run directly:
 *
 * java -jar app.jar
 *
 *
 * ===========================================================
 * MAVEN DEEP FLOW
 * ===========================================================
 *
 * Developer writes code
 *          ↓
 * pom.xml contains dependencies
 *          ↓
 * mvn clean install
 *          ↓
 * Maven reads pom.xml
 *          ↓
 * Resolves dependencies
 *          ↓
 * Downloads missing JARs
 *          ↓
 * Creates classpath
 *          ↓
 * Compiles source code
 *          ↓
 * Runs tests
 *          ↓
 * Packages JAR
 *          ↓
 * Stores artifact in .m2
 *          ↓
 * Application ready
 *
 *
 * ===========================================================
 * INTERVIEW QUESTIONS
 * ===========================================================
 *
 * Q. What is Maven?
 *
 * Q. Difference between JAR and WAR?
 *
 * Q. What is pom.xml?
 *
 * Q. What is dependency management?
 *
 * Q. What is transitive dependency?
 *
 * Q. What is Maven Repository?
 *
 * Q. Difference between install and deploy?
 *
 * Q. What is classpath?
 *
 * Q. Difference between normal JAR and Spring Boot JAR?
 *
 * ===========================================================
 */
public class MavenCompleteGuide {

    public static void main(String[] args) {

        System.out.println("Maven Complete Guide");

    }
}