package springboot_deep_drive.springboot.config;

/*
================================================================
  @SpringBootApplication - COMPLETE GUIDE
================================================================

================================================================
  WHAT IS @SpringBootApplication?
================================================================

It is the entry point of every Spring Boot application.

Simple definition:
  @SpringBootApplication = @SpringBootConfiguration
                         + @EnableAutoConfiguration
                         + @ComponentScan

Instead of writing 3 annotations, we write just 1.

----------------------------------------------------------------
  VISUAL DIAGRAM
----------------------------------------------------------------

          @SpringBootApplication
                   |
     ---------------+---------------
     |              |               |
     v              v               v
@SpringBoot   @EnableAuto     @ComponentScan
Configuration  Configuration
     |              |               |
     |              |               v
     |              |         Scans packages
     |              |         for @Component,
     |              |         @Service, etc.
     |              |
     |              v
     |         Auto-configures
     |         beans based on
     |         dependencies
     |
     v
  Marks this
  as MAIN config
  class

================================================================
  1. @SpringBootConfiguration
================================================================

@SpringBootConfiguration is just @Configuration
with a special name.

Definition from source:

  @Configuration
  public @interface SpringBootConfiguration {
  }

It is exactly same as @Configuration.

Why a separate name?
  - Spring Boot uses it to FIND the main class
  - Only ONE class in the app should have it
  - Spring Boot searches for this during startup

================================================================
  CAN WE USE @Configuration INSTEAD?
================================================================

Yes, but:

  @SpringBootApplication   <- preferred (has all 3 in 1)
  @Configuration            <- works but missing scanning + auto-config
  @SpringBootConfiguration <- Spring Boot looks for this specifically

Important:
  Spring Boot internally checks for
  @SpringBootConfiguration to identify main class.

================================================================
  2. @ComponentScan
================================================================

Tells Spring: "Find all beans in these packages."

----------------------------------------------------------------
  WHAT DOES IT FIND?
----------------------------------------------------------------

Spring scans for these annotations:

  @Component     - Generic bean
  @Service       - Service layer
  @Repository    - DAO layer
  @Controller    - Web controller
  @Configuration - Config class
  @RestController - REST controller

----------------------------------------------------------------
  HOW DOES SCANNING WORK INTERNALLY?
----------------------------------------------------------------

  main() starts
       |
       v
  SpringApplication.run()
       |
       v
  ConfigurationClassPostProcessor
  (special post processor)
       |
       v
  Reads @ComponentScan on main class
       |
       v
  Creates ClassPathBeanDefinitionScanner
       |
       v
  Scanner reads package of main class
  (e.g., com.myapp)
       |
       v
  Scans all classes in package + sub-packages
       |
       v
  For each class, checks bytecode using ASM
  (ASM reads .class file directly - FAST)
       |
       v
  If class has @Component (or similar) ->
  create BeanDefinition
       |
       v
  Register BeanDefinition in IOC container
       |
       v
  Later -> actual bean objects created

----------------------------------------------------------------
  WHY ASM AND NOT REFLECTION?
----------------------------------------------------------------

ASM (used by Spring):
  - Reads raw bytecode from .class file
  - Does NOT load class into JVM
  - Faster, uses less memory
  - Used during SCANNING phase

Reflection:
  - Loads class into JVM (triggers static blocks!)
  - Slower
  - Used during BEAN CREATION phase (later)

----------------------------------------------------------------
  PACKAGE SCANNING RULE (VERY IMPORTANT)
----------------------------------------------------------------

@SpringBootApplication scans:
  Package of main class + ALL sub-packages

Example - CORRECT:
  com.myapp
    MyApplication.java        <-- main class
    controller/               <-- scanned
    service/                  <-- scanned
    repository/               <-- scanned

Example - WRONG:
  com.myapp.startup
    MyApplication.java        <-- main class
  com.myapp.service           <-- NOT scanned!
  com.myapp.controller        <-- NOT scanned!

Result: Beans not found -> runtime errors.

----------------------------------------------------------------
  FIX FOR WRONG PACKAGE STRUCTURE
----------------------------------------------------------------

Use scanBasePackages:

  @SpringBootApplication(
      scanBasePackages = {
          "com.myapp.startup",
          "com.myapp.service",
          "com.myapp.controller"
      }
  )

Or use scanBasePackageClasses:

  @SpringBootApplication(
      scanBasePackageClasses = {
          ServiceMarker.class,
          ControllerMarker.class
      }
  )

----------------------------------------------------------------
  EXCLUDING PACKAGES FROM SCAN
----------------------------------------------------------------

  @SpringBootApplication
  @ComponentScan(
      excludeFilters = {
          @ComponentScan.Filter(
              type = FilterType.REGEX,
              pattern = "com\\.myapp\\.experimental.*"
          )
      }
  )

================================================================
  3. COMPLETE @SpringBootApplication EXAMPLE
================================================================

  @SpringBootApplication  // Combines all 3 annotations
  public class GameServiceApplication {

      public static void main(String[] args) {
          SpringApplication.run(
              GameServiceApplication.class,
              args
          );
      }
  }

What happens when you run main():
-------------------------------------

  Step 1: SpringApplication.run() called
  Step 2: Determine application type
          (Servlet/Reactive/None)
  Step 3: Load Spring.factories
  Step 4: Create ApplicationContext
  Step 5: Process @SpringBootApplication
          |
          +-- @SpringBootConfiguration found
          |   (marks this as main config)
          |
          +-- @ComponentScan starts
          |   (discovers @Service, @Controller etc.)
          |
          +-- @EnableAutoConfiguration starts
              (configures DataSource, JPA, Web etc.)
  Step 6: Start embedded Tomcat
  Step 7: Application ready

================================================================
  @SpringBootApplication ATTRIBUTES
================================================================

  @SpringBootApplication(
      // Exclude auto-config classes
      exclude = {
          DataSourceAutoConfiguration.class
      },
      // Exclude by fully qualified name
      excludeName = {
          "org.springframework.boot.autoconfigure.
              security.SecurityAutoConfiguration"
      },
      // Custom scan packages
      scanBasePackages = {
          "com.myapp"
      },
      // Custom scan by class reference
      scanBasePackageClasses = {
          MyApplication.class
      }
  )

================================================================
  COMMON INTERVIEW Q&A
================================================================

Q1: Can a Spring Boot app run without @SpringBootApplication?
---------------------------------------------------------------
Answer: YES.

Replace with:

  @Configuration
  @EnableAutoConfiguration
  @ComponentScan
  public class MyApp { ... }

But @SpringBootApplication is cleaner and preferred.

Q2: How does Spring Boot find the main class?
------------------------------------------------
Answer: Spring Boot scans classpath for class
with @SpringBootConfiguration (which is inside
@SpringBootApplication). There must be exactly ONE.

Q3: What happens if main class is not at root package?
--------------------------------------------------------
Answer: @ComponentScan will only scan main class
package and sub-packages. Other packages are missed.

Solution: Use scanBasePackages attribute.

Q4: Is @SpringBootConfiguration different from @Configuration?
----------------------------------------------------------------
Answer: @SpringBootConfiguration is @Configuration
with a specific name. Spring Boot uses it to identify
the MAIN configuration. You can have many @Configuration
but only one @SpringBootConfiguration.

================================================================
  SIMPLE SUMMARY
================================================================

@SpringBootApplication = ONE annotation that does THREE things:

  1. Tells Spring: "This is the main class"
     (@SpringBootConfiguration)

  2. Tells Spring: "Find all beans in my package"
     (@ComponentScan)

  3. Tells Spring: "Auto-configure based on dependencies"
     (@EnableAutoConfiguration)

Main class should be at ROOT package.
Spring Boot does everything automatically.

================================================================
*/

public class SpringBootApplicationDeepDive {

}
