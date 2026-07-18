package springboot_deep_drive.maven;

/**
 * ===========================================================
 *                    POM.XML DEEP DIVE
 * ===========================================================
 *
 * POM = Project Object Model
 *
 * pom.xml is the heart of every Maven project.
 *
 * Maven reads pom.xml to understand:
 *
 * - Project information
 * - Dependencies
 * - Plugins
 * - Build configurations
 * - Repository locations
 * - Packaging type
 * - Version management
 *
 *
 * ===========================================================
 * SAMPLE POM.XML
 * ===========================================================
 *
 * <project>
 *
 *     <modelVersion>4.0.0</modelVersion>
 *
 *     <groupId>com.company</groupId>
 *
 *     <artifactId>employee-service</artifactId>
 *
 *     <version>1.0.0</version>
 *
 *     <packaging>jar</packaging>
 *
 * </project>
 *
 *
 * ===========================================================
 * 1. modelVersion
 * ===========================================================
 *
 * Defines which POM model version Maven should use.
 *
 * Current standard:
 *
 * 4.0.0
 *
 *
 * Example:
 *
 * <modelVersion>4.0.0</modelVersion>
 *
 *
 * Almost every Maven project uses:
 *
 * 4.0.0
 *
 *
 * ===========================================================
 * 2. groupId
 * ===========================================================
 *
 * Represents organization/company/project owner.
 *
 * Usually written in reverse domain format.
 *
 *
 * Examples:
 *
 * com.google
 * org.springframework
 * com.amazon
 * com.mycompany
 *
 *
 * Example:
 *
 * <groupId>com.code.monks</groupId>
 *
 *
 * Think of it as:
 *
 * Package name for project identification.
 *
 *
 * ===========================================================
 * 3. artifactId
 * ===========================================================
 *
 * Unique name of project/module.
 *
 *
 * Example:
 *
 * <artifactId>employee-service</artifactId>
 *
 *
 * Examples:
 *
 * user-service
 * payment-service
 * inventory-service
 * hrms-backend
 *
 *
 * Final artifact name:
 *
 * employee-service-1.0.0.jar
 *
 *
 * ===========================================================
 * 4. version
 * ===========================================================
 *
 * Project version.
 *
 *
 * Example:
 *
 * <version>1.0.0</version>
 *
 *
 * Common versions:
 *
 * 1.0.0
 * 1.1.0
 * 2.0.0
 * 3.0.1
 *
 *
 * SNAPSHOT Version:
 *
 * <version>1.0.0-SNAPSHOT</version>
 *
 *
 * SNAPSHOT means:
 *
 * Project is under development.
 *
 *
 * Release Version:
 *
 * <version>1.0.0</version>
 *
 *
 * Stable production release.
 *
 *
 * ===========================================================
 * MAVEN ARTIFACT COORDINATES
 * ===========================================================
 *
 * Every Maven artifact is uniquely identified by:
 *
 * groupId
 * artifactId
 * version
 *
 *
 * Example:
 *
 * org.springframework.boot
 * spring-boot-starter-web
 * 3.3.0
 *
 *
 * Combined:
 *
 * org.springframework.boot:
 * spring-boot-starter-web:
 * 3.3.0
 *
 *
 * ===========================================================
 * 5. packaging
 * ===========================================================
 *
 * Defines output type.
 *
 *
 * Example:
 *
 * <packaging>jar</packaging>
 *
 *
 * Available Types:
 *
 * jar
 * war
 * pom
 * ear
 *
 *
 * jar
 * ----
 *
 * Normal Java application.
 *
 *
 * war
 * ----
 *
 * Web application deployment.
 *
 *
 * pom
 * ----
 *
 * Parent project or aggregator project.
 *
 *
 * ===========================================================
 * JAR VS WAR
 * ===========================================================
 *
 * JAR
 * ---
 *
 * Java Archive
 *
 * Used for:
 *
 * Java Applications
 * Spring Boot Applications
 *
 *
 * Run:
 *
 * java -jar app.jar
 *
 *
 * WAR
 * ---
 *
 * Web Archive
 *
 * Used for:
 *
 * Traditional Web Applications
 *
 *
 * Deploy into:
 *
 * Tomcat
 * WebLogic
 * JBoss
 *
 *
 * ===========================================================
 * DEPENDENCIES
 * ===========================================================
 *
 * Dependencies are external libraries used by project.
 *
 *
 * Example:
 *
 * <dependency>
 *     <groupId>mysql</groupId>
 *     <artifactId>mysql-connector-j</artifactId>
 *     <version>9.0.0</version>
 * </dependency>
 *
 *
 * Maven downloads:
 *
 * mysql jar
 *
 *
 * Stores:
 *
 * .m2 repository
 *
 *
 * Adds:
 *
 * classpath
 *
 *
 * ===========================================================
 * COMMON SPRING BOOT DEPENDENCIES
 * ===========================================================
 *
 * spring-boot-starter-web
 *
 * spring-boot-starter-data-jpa
 *
 * spring-boot-starter-security
 *
 * spring-boot-starter-test
 *
 * lombok
 *
 * mysql-connector-j
 *
 *
 * ===========================================================
 * TRANSITIVE DEPENDENCIES
 * ===========================================================
 *
 * You add:
 *
 * spring-boot-starter-web
 *
 *
 * Maven automatically downloads:
 *
 * spring-core
 * spring-context
 * spring-web
 * spring-mvc
 * jackson
 * tomcat
 *
 *
 * These are called:
 *
 * Transitive Dependencies
 *
 *
 * ===========================================================
 * DEPENDENCY TREE
 * ===========================================================
 *
 * Command:
 *
 * mvn dependency:tree
 *
 *
 * Example:
 *
 * spring-boot-starter-web
 *    |
 *    +--- spring-web
 *    |
 *    +--- spring-core
 *    |
 *    +--- jackson
 *    |
 *    +--- tomcat
 *
 *
 * Useful for:
 *
 * Dependency conflict debugging.
 *
 *
 * ===========================================================
 * DEPENDENCY SCOPES
 * ===========================================================
 *
 * Scope decides:
 *
 * When dependency is available.
 *
 *
 * -----------------------------------------------------------
 * 1. COMPILE
 * -----------------------------------------------------------
 *
 * Default scope.
 *
 *
 * Available:
 *
 * Compile
 * Test
 * Runtime
 *
 *
 * Example:
 *
 * Spring Framework
 *
 *
 * -----------------------------------------------------------
 * 2. PROVIDED
 * -----------------------------------------------------------
 *
 * Available:
 *
 * Compile
 *
 * Not packaged in final artifact.
 *
 *
 * Example:
 *
 * Servlet API
 *
 *
 * Because Tomcat already provides it.
 *
 *
 * -----------------------------------------------------------
 * 3. RUNTIME
 * -----------------------------------------------------------
 *
 * Needed only during execution.
 *
 *
 * Example:
 *
 * Database Drivers
 *
 *
 * -----------------------------------------------------------
 * 4. TEST
 * -----------------------------------------------------------
 *
 * Only for testing.
 *
 *
 * Example:
 *
 * JUnit
 * Mockito
 *
 *
 * Not included in production build.
 *
 *
 * -----------------------------------------------------------
 * 5. SYSTEM
 * -----------------------------------------------------------
 *
 * Rarely used.
 *
 *
 * Dependency comes from local machine path.
 *
 *
 * Example:
 *
 * C:\libs\abc.jar
 *
 *
 * Not recommended.
 *
 *
 * ===========================================================
 * PROPERTIES
 * ===========================================================
 *
 * Used to avoid hardcoding values.
 *
 *
 * Example:
 *
 * <properties>
 *     <java.version>21</java.version>
 * </properties>
 *
 *
 * Usage:
 *
 * ${java.version}
 *
 *
 * Benefits:
 *
 * Centralized version management.
 *
 *
 * ===========================================================
 * PARENT POM
 * ===========================================================
 *
 * Spring Boot projects usually inherit:
 *
 * spring-boot-starter-parent
 *
 *
 * Example:
 *
 * <parent>
 *
 *     <groupId>
 *         org.springframework.boot
 *     </groupId>
 *
 *     <artifactId>
 *         spring-boot-starter-parent
 *     </artifactId>
 *
 *     <version>
 *         3.5.0
 *     </version>
 *
 * </parent>
 *
 *
 * Benefits:
 *
 * - Dependency versions managed
 * - Plugin versions managed
 * - Build defaults configured
 *
 *
 * ===========================================================
 * DEPENDENCY MANAGEMENT
 * ===========================================================
 *
 * Used in large projects.
 *
 * Central place to define versions.
 *
 *
 * Example:
 *
 * <dependencyManagement>
 *
 *     <dependencies>
 *
 *         <dependency>
 *             <groupId>org.projectlombok</groupId>
 *             <artifactId>lombok</artifactId>
 *             <version>1.18.34</version>
 *         </dependency>
 *
 *     </dependencies>
 *
 * </dependencyManagement>
 *
 *
 * Child projects can use dependency
 * without repeating version.
 *
 *
 * ===========================================================
 * BUILD SECTION
 * ===========================================================
 *
 * Build configuration is defined here.
 *
 *
 * Example:
 *
 * <build>
 *     ...
 * </build>
 *
 *
 * Contains:
 *
 * Plugins
 * Final name
 * Build customizations
 *
 *
 * ===========================================================
 * MAVEN PLUGINS
 * ===========================================================
 *
 * Maven uses plugins to perform tasks.
 *
 *
 * Maven itself doesn't:
 *
 * - Compile Java
 * - Run Tests
 * - Create JAR
 *
 *
 * Plugins perform these tasks.
 *
 *
 * ===========================================================
 * COMPILER PLUGIN
 * ===========================================================
 *
 * <plugin>
 *     <groupId>
 *         org.apache.maven.plugins
 *     </groupId>
 *
 *     <artifactId>
 *         maven-compiler-plugin
 *     </artifactId>
 * </plugin>
 *
 *
 * Purpose:
 *
 * Compile Java code.
 *
 *
 * ===========================================================
 * SUREFIRE PLUGIN
 * ===========================================================
 *
 * Runs JUnit test cases.
 *
 *
 * Plugin:
 *
 * maven-surefire-plugin
 *
 *
 * Used during:
 *
 * mvn test
 *
 *
 * ===========================================================
 * SPRING BOOT MAVEN PLUGIN
 * ===========================================================
 *
 * Most important Spring Boot plugin.
 *
 *
 * Plugin:
 *
 * spring-boot-maven-plugin
 *
 *
 * Responsibilities:
 *
 * - Create executable jar
 * - Repackage application
 * - Embed dependencies
 *
 *
 * Command:
 *
 * mvn spring-boot:run
 *
 *
 * ===========================================================
 * REPOSITORIES
 * ===========================================================
 *
 * By default:
 *
 * Maven Central Repository
 *
 *
 * Custom repository:
 *
 * <repositories>
 *
 *     <repository>
 *
 *         <id>company-repo</id>
 *
 *         <url>
 *             https://repo.company.com
 *         </url>
 *
 *     </repository>
 *
 * </repositories>
 *
 *
 * ===========================================================
 * MULTI MODULE PROJECT
 * ===========================================================
 *
 * Parent Project
 *      |
 *      +---- user-service
 *      |
 *      +---- payment-service
 *      |
 *      +---- inventory-service
 *
 *
 * Parent POM:
 *
 * packaging = pom
 *
 *
 * Child modules inherit:
 *
 * Common dependencies
 * Common plugins
 * Common versions
 *
 *
 * ===========================================================
 * EFFECTIVE POM
 * ===========================================================
 *
 * Final POM generated by Maven after merging:
 *
 * - Parent POM
 * - Child POM
 * - Defaults
 * - Plugin configurations
 *
 *
 * Command:
 *
 * mvn help:effective-pom
 *
 *
 * Very useful for debugging.
 *
 *
 * ===========================================================
 * INTERVIEW QUESTIONS
 * ===========================================================
 *
 * Q. What is pom.xml?
 *
 * Q. What is groupId?
 *
 * Q. What is artifactId?
 *
 * Q. What is version?
 *
 * Q. What is dependency scope?
 *
 * Q. Difference between compile and runtime scope?
 *
 * Q. What is dependencyManagement?
 *
 * Q. What is parent pom?
 *
 * Q. What is transitive dependency?
 *
 * Q. What is effective pom?
 *
 * Q. What is spring-boot-maven-plugin?
 *
 * Q. Difference between jar and war?
 *
 * ===========================================================
 */
public class PomXmlDeepDive {

    public static void main(String[] args) {

        System.out.println("POM XML Deep Dive");

    }
}