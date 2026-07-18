package springboot_deep_drive.maven;

/**
 * ===========================================================
 *              MAVEN COMMANDS CHEAT SHEET
 * ===========================================================
 *
 * mvn clean
 * -----------------------
 * Deletes target folder.
 *
 *
 * mvn compile
 * -----------------------
 * Compiles Java code.
 *
 * .java -> .class
 *
 *
 * mvn test
 * -----------------------
 * Executes JUnit test cases.
 *
 *
 * mvn package
 * -----------------------
 * Creates JAR/WAR.
 *
 *
 * mvn install
 * -----------------------
 * Creates artifact and
 * stores inside local repository.
 *
 *
 * mvn deploy
 * -----------------------
 * Uploads artifact to remote repository.
 *
 *
 * mvn clean install
 * -----------------------
 * Most common command.
 *
 * Clean old build
 * +
 * Build new artifact
 * +
 * Store in .m2
 *
 *
 * mvn dependency:tree
 * -----------------------
 * Shows dependency hierarchy.
 *
 *
 * mvn help:effective-pom
 * -----------------------
 * Shows final generated pom.
 *
 *
 * mvn spring-boot:run
 * -----------------------
 * Runs Spring Boot application.
 *
 *
 * ===========================================================
 * MOST USED COMMANDS IN INDUSTRY
 * ===========================================================
 *
 * mvn clean install
 *
 * mvn test
 *
 * mvn package
 *
 * mvn spring-boot:run
 *
 * mvn dependency:tree
 *
 * ===========================================================
 */
public class MavenCommandsDeepDive {

    public static void main(String[] args) {

        System.out.println("Maven Commands");

    }
}