package springboot_deep_drive.maven;

/**
 * ===========================================================
 *             DEPENDENCY MANAGEMENT DEEP DIVE
 * ===========================================================
 *
 * WHAT IS DEPENDENCY MANAGEMENT?
 * -----------------------------------------------------------
 *
 * Dependency Management is a mechanism
 * to control dependency versions
 * from one central location.
 *
 *
 * ===========================================================
 * PROBLEM WITHOUT DEPENDENCY MANAGEMENT
 * ===========================================================
 *
 * Module A
 *
 * lombok 1.18.30
 *
 *
 * Module B
 *
 * lombok 1.18.34
 *
 *
 * Module C
 *
 * lombok 1.18.20
 *
 *
 * Problems:
 *
 * - Version conflicts
 * - Difficult maintenance
 * - Duplicate configurations
 *
 *
 * ===========================================================
 * SOLUTION
 * ===========================================================
 *
 * Define versions once.
 *
 * Child modules reuse them.
 *
 *
 * ===========================================================
 * EXAMPLE
 * ===========================================================
 *
 * <dependencyManagement>
 *
 *     <dependencies>
 *
 *         <dependency>
 *
 *             <groupId>
 *                 org.projectlombok
 *             </groupId>
 *
 *             <artifactId>
 *                 lombok
 *             </artifactId>
 *
 *             <version>
 *                 1.18.34
 *             </version>
 *
 *         </dependency>
 *
 *     </dependencies>
 *
 * </dependencyManagement>
 *
 *
 * ===========================================================
 * CHILD MODULE USAGE
 * ===========================================================
 *
 * <dependency>
 *
 *     <groupId>
 *         org.projectlombok
 *     </groupId>
 *
 *     <artifactId>
 *         lombok
 *     </artifactId>
 *
 * </dependency>
 *
 *
 * Version not required.
 *
 *
 * ===========================================================
 * IMPORTANT
 * ===========================================================
 *
 * dependencyManagement
 * DOES NOT
 * download dependency.
 *
 *
 * It only manages version information.
 *
 *
 * Actual dependency still required.
 *
 *
 * ===========================================================
 * DIFFERENCE
 * ===========================================================
 *
 * dependencies
 * ------------------
 *
 * Downloads dependency.
 *
 *
 * dependencyManagement
 * ------------------
 *
 * Manages dependency versions.
 *
 *
 * ===========================================================
 * SPRING BOOT EXAMPLE
 * ===========================================================
 *
 * spring-boot-starter-parent
 *
 * internally contains
 *
 * dependencyManagement
 *
 *
 * Therefore:
 *
 * We usually write
 *
 * <dependency>
 *
 *     <groupId>
 *         org.projectlombok
 *     </groupId>
 *
 *     <artifactId>
 *         lombok
 *     </artifactId>
 *
 * </dependency>
 *
 *
 * Without specifying version.
 *
 *
 * Spring Boot provides version.
 *
 *
 * ===========================================================
 * REAL PROJECT FLOW
 * ===========================================================
 *
 * Parent Project
 *        |
 *        +---- user-service
 *        |
 *        +---- order-service
 *        |
 *        +---- payment-service
 *
 *
 * Parent POM controls:
 *
 * - Dependency versions
 * - Plugin versions
 * - Common properties
 *
 *
 * Child modules inherit everything.
 *
 *
 * ===========================================================
 * BENEFITS
 * ===========================================================
 *
 * 1. Centralized version control
 *
 * 2. Easier upgrades
 *
 * 3. No duplicate versions
 *
 * 4. Consistent builds
 *
 * 5. Better maintenance
 *
 *
 * ===========================================================
 * INTERVIEW QUESTION
 * ===========================================================
 *
 * Q. Difference between
 * dependencyManagement
 * and dependencies?
 *
 *
 * dependencies:
 * Downloads dependency.
 *
 *
 * dependencyManagement:
 * Only manages version information.
 *
 * ===========================================================
 */
public class DependencyManagementDeepDive {

    public static void main(String[] args) {

        System.out.println("Dependency Management");

    }
}