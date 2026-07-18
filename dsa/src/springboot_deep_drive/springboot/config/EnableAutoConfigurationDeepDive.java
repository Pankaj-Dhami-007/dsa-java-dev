package springboot_deep_drive.springboot.config;

/*
================================================================
  @EnableAutoConfiguration - COMPLETE DEEP DIVE
================================================================

This is the HEART of Spring Boot.
This annotation makes Spring Boot "magical".

================================================================
  WHAT DOES @EnableAutoConfiguration DO?
================================================================

Simple Answer:
  It tells Spring Boot: "Look at the dependencies in
  my project and automatically create all required beans."

Example:
  If you add spring-boot-starter-web in pom.xml,
  Spring Boot auto-creates:
  - DispatcherServlet
  - Tomcat server
  - Jackson ObjectMapper
  - ViewResolvers
  - Error handlers
  - AND MORE...

You write ZERO configuration for these.

================================================================
  WHO EXECUTES @EnableAutoConfiguration?
================================================================

When Spring sees @EnableAutoConfiguration, it triggers:

  AutoConfigurationImportSelector

This is the BRAIN behind auto-configuration.

----------------------------------------------------------------
  COMPLETE INTERNAL FLOW
----------------------------------------------------------------

  @EnableAutoConfiguration
         |
         | Internally uses: @Import(AutoConfigurationImportSelector.class)
         v
  AutoConfigurationImportSelector
         |
         | Step 1: Read AutoConfiguration.imports file
         |         (from all JARs in classpath)
         v
  List of all possible auto-config classes
  (100+ classes like WebMvcAutoConfiguration,
   DataSourceAutoConfiguration, JacksonAutoConfiguration...)
         |
         | Step 2: Remove duplicates
         v
  Unique list of auto-config classes
         |
         | Step 3: Apply @AutoConfigureOrder, @AutoConfigureBefore, @AutoConfigureAfter
         v
  Sorted list
         |
         | Step 4: Apply @Conditional annotations
         |         (check each condition one by one)
         v
  Filtered list (only matching configs remain)
         |
         | Step 5: Register these configuration classes
         v
  Beans are created in IOC container
         |
         v
  Application is ready!

================================================================
  THE KEY FILE: AutoConfiguration.imports
================================================================

Location inside JAR:

  META-INF/spring/
  org.springframework.boot.autoconfigure.
  AutoConfiguration.imports

This file exists inside:
  - spring-boot-autoconfigure.jar (main one)
  - Each starter JAR can add its own entries

----------------------------------------------------------------
  SAMPLE CONTENT OF THIS FILE
----------------------------------------------------------------

  # This file lists ALL auto-configuration classes
  # Each line is a fully qualified class name

  org.springframework.boot.autoconfigure.
      web.servlet.WebMvcAutoConfiguration

  org.springframework.boot.autoconfigure.
      jdbc.DataSourceAutoConfiguration

  org.springframework.boot.autoconfigure.
      orm.jpa.HibernateJpaAutoConfiguration

  org.springframework.boot.autoconfigure.
      jackson.JacksonAutoConfiguration

  org.springframework.boot.autoconfigure.
      security.servlet.SecurityAutoConfiguration

  org.springframework.boot.autoconfigure.
      transaction.TransactionAutoConfiguration

... (100+ entries)

================================================================
  HOW pom.xml TRIGGERS AUTO-CONFIGURATION
================================================================

Complete flow when you add a dependency:

  pom.xml
    |
    | You add: spring-boot-starter-web
    v
  Maven downloads JARs:
    spring-boot-starter-web.jar
    spring-boot-starter-tomcat.jar  (embedded server)
    spring-webmvc.jar               (MVC framework)
    jackson-databind.jar            (JSON support)
    ...
    | Each JAR has .class files inside
    v
  JARs are added to classpath (runtime)
    |
    | Now these classes are available to JVM
    v
  Spring Boot's AutoConfigurationImportSelector
  reads AutoConfiguration.imports files
  from ALL JARs in classpath
    |
    | Finds: WebMvcAutoConfiguration
    v
  WebMvcAutoConfiguration has conditions:

  @ConditionalOnClass({
      Servlet.class,             // from tomcat-embed-core.jar
      DispatcherServlet.class,   // from spring-webmvc.jar
      WebMvcConfigurer.class     // from spring-webmvc.jar
  })
    |
    | Spring checks: "Does Servlet.class exist in JVM?"
    | YES -> because tomcat-embed-core.jar is in classpath
    | "Does DispatcherServlet.class exist?"
    | YES -> because spring-webmvc.jar is in classpath
    v
  Condition PASSES -> WebMvcAutoConfiguration loads
    |
    | @Bean methods execute
    v
  Beans created:
    DispatcherServlet
    Tomcat (embedded on port 8080)
    Jackson ObjectMapper
    ViewResolvers
    MessageConverters
    ... and more

================================================================
  VISUAL DIAGRAM - pom.xml to Running App
================================================================

  +------------------+
  |    pom.xml       |
  |  starter-web     |
  +--------+---------+
           |
           | Maven downloads
           v
  +------------------+
  |   JAR Files      |---- Contains .class files
  | (tomcat, jackson,|    (Java bytecode)
  |  spring-webmvc)  |
  +--------+---------+
           |
           | Added to classpath at runtime
           v
  +------------------+
  |  Classpath has   |
  |  Servlet.class   |
  |  DispatcherServlet.class  |
  |  ObjectMapper.class       |
  +--------+---------+
           |
           | AutoConfigurationImportSelector reads
           v
  +------------------+
  |AutoConfiguration.|
  |imports file      |---- Contains class names
  +--------+---------+
           |
           | Loads each class and checks conditions
           v
  +------------------+
  | @ConditionalOn   |
  | Class checks:    |
  | "Does Servlet.   |
  |  class exist?"   |---- YES (in classpath)
  +--------+---------+
           |
           | Condition passed
           v
  +------------------+
  | Configuration    |
  | class loads      |
  | @Bean methods    |
  | execute          |
  +--------+---------+
           |
           | Beans created
           v
  +------------------+
  | Tomcat starts    |
  | on port 8080     |
  | App is ready!    |
  +------------------+

================================================================
  CONDITIONAL ANNOTATIONS - THE DECISION MAKERS
================================================================

Spring Boot does NOT blindly configure everything.
It checks CONDITIONS first.

----------------------------------------------------------------
  1. @ConditionalOnClass
----------------------------------------------------------------

"Only configure if this class is in classpath"

  @Configuration
  @ConditionalOnClass(DataSource.class)
  public class DataSourceAutoConfiguration {
      // This loads only if DataSource class exists
      // DataSource class comes from spring-jdbc JAR
  }

Real example:
  If you add spring-boot-starter-data-jpa,
  spring-jdbc.jar is downloaded -> DataSource.class exists
  -> DataSourceAutoConfiguration activates
  -> DataSource bean created automatically

----------------------------------------------------------------
  2. @ConditionalOnMissingBean
----------------------------------------------------------------

"Only create bean if user has NOT created one"

  @Configuration
  public class JacksonAutoConfiguration {

      @Bean
      @ConditionalOnMissingBean(ObjectMapper.class)
      public ObjectMapper objectMapper() {
          return new ObjectMapper();
      }
  }

This is how customization works:

  User creates custom ObjectMapper:
      @Configuration
      public class MyConfig {
          @Bean
          public ObjectMapper objectMapper() {
              ObjectMapper om = new ObjectMapper();
              om.setSerializationInclusion(
                  Include.NON_NULL
              );
              return om;
          }
      }

  Spring Boot sees:
      - User's @Configuration processed FIRST
      - User's ObjectMapper bean registered
      - Auto-config checks @ConditionalOnMissingBean
      - Condition FAILS (user's bean exists)
      - Auto-config does NOT create ObjectMapper
      - User's custom ObjectMapper is used

Result: User can override any auto-configured bean.

----------------------------------------------------------------
  3. @ConditionalOnProperty
----------------------------------------------------------------

"Only configure if property is set"

  @Configuration
  @ConditionalOnProperty(
      name = "spring.datasource.url"
  )
  public class DataSourceAutoConfiguration {
      // Activates only when
      // spring.datasource.url is set in
      // application.properties
  }

----------------------------------------------------------------
  4. @ConditionalOnBean
----------------------------------------------------------------

"Only configure if another bean exists"

  @Configuration
  @ConditionalOnBean(DataSource.class)
  public class JdbcTemplateAutoConfiguration {
      @Bean
      public JdbcTemplate jdbcTemplate(
          DataSource ds
      ) {
          return new JdbcTemplate(ds);
      }
  }

----------------------------------------------------------------
  5. @ConditionalOnExpression
----------------------------------------------------------------

"Only configure if SpEL expression is true"

  @ConditionalOnExpression(
      "${my.feature.enabled:false}"
  )

================================================================
  IMPORTANT: ORDERING OF AUTO-CONFIGURATION
================================================================

Some auto-config classes depend on others.

Example:
  JdbcTemplateAutoConfiguration needs DataSource bean.

Spring Boot provides ordering annotations:

  @AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
  @AutoConfigureBefore(JdbcTemplateAutoConfiguration.class)
  @AutoConfigureAfter(DataSourceAutoConfiguration.class)

Spring Boot sorts all configs by these annotations
before applying conditions.

================================================================
  PRACTICAL EXAMPLES
================================================================

----------------------------------------------------------------
  EXAMPLE 1: Adding spring-boot-starter-web
----------------------------------------------------------------

  pom.xml:
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

  What gets auto-configured:
    - DispatcherServlet (handles HTTP requests)
    - Tomcat (embedded server on port 8080)
    - Jackson (converts Java <-> JSON)
    - ViewResolvers (for JSP/Thymeleaf)
    - MessageConverters (JSON, XML, String)
    - Static resource handler (CSS, JS, images)
    - Error handling (/error endpoint)
    - CORS configuration
    - Content negotiation

----------------------------------------------------------------
  EXAMPLE 2: Adding spring-boot-starter-data-jpa
----------------------------------------------------------------

  pom.xml:
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

  What gets auto-configured:
    - DataSource (HikariCP connection pool)
    - EntityManagerFactory (JPA)
    - TransactionManager (for @Transactional)
    - JPA Repository support
    - Hibernate dialect auto-detection
    - Schema generation (ddl-auto)

----------------------------------------------------------------
  EXAMPLE 3: Adding spring-boot-starter-security
----------------------------------------------------------------

  pom.xml:
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

  What gets auto-configured:
    - SecurityFilterChain (protects all endpoints)
    - AuthenticationManager
    - Default login page
    - PasswordEncoder
    - CSRF protection
    - CORS configuration

----------------------------------------------------------------
  EXAMPLE 4: Combining MULTIPLE starters
----------------------------------------------------------------

  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
  </dependency>

  Spring Boot evaluates ALL auto-config classes.
  Each one checks its own conditions.
  If conditions pass -> configures that feature.
  All work together seamlessly.

================================================================
  DEBUGGING AUTO-CONFIGURATION
================================================================

Sometimes you want to see WHICH auto-config classes
were applied and which were skipped.

Add this to application.properties:

  debug=true

Or in application.yml:

  debug: true

When you run the app, Spring Boot prints:

  ============================
  CONDITION EVALUATION REPORT
  ============================

  Positive matches:
  -----------------
  WebMvcAutoConfiguration matched:
    - @ConditionalOnClass found classes
      DispatcherServlet, WebMvcConfigurer
      (OnClassCondition)

  DataSourceAutoConfiguration matched:
    - @ConditionalOnClass found class
      DataSource (OnClassCondition)
    - @ConditionalOnProperty
      spring.datasource.url found
      (OnPropertyCondition)

  Negative matches:
  -----------------
  SecurityAutoConfiguration:
    Did not match:
      - @ConditionalOnClass found required class
        SecurityFilterChain
        (OnClassCondition)
      - @ConditionalOnMissingBean found beans of type
        SecurityFilterChain
        (OnBeanCondition)

  Exclusions:
  -----------
  None

================================================================
  DISABLING SPECIFIC AUTO-CONFIGURATION
================================================================

Option 1: Using @SpringBootApplication exclude

  @SpringBootApplication(
      exclude = SecurityAutoConfiguration.class
  )
  public class MyApplication { ... }

Option 2: Using properties

  spring.autoconfigure.exclude=
      org.springframework.boot.autoconfigure.
      security.servlet.SecurityAutoConfiguration

Option 3: Exclude multiple

  @SpringBootApplication(
      exclude = {
          SecurityAutoConfiguration.class,
          DataSourceAutoConfiguration.class
      }
  )

================================================================
  CREATING CUSTOM AUTO-CONFIGURATION
================================================================

You can create your own auto-configuration library.

Step 1: Create config class with conditions

  @Configuration
  @ConditionalOnClass(MyLibraryService.class)
  public class MyLibraryAutoConfiguration {

      @Bean
      @ConditionalOnMissingBean
      public MyLibraryService myLibraryService() {
          return new MyLibraryService();
      }
  }

Step 2: Create AutoConfiguration.imports file

  Location:
    src/main/resources/META-INF/spring/
    org.springframework.boot.autoconfigure.
    AutoConfiguration.imports

  Content:
    com.mylibrary.MyLibraryAutoConfiguration

Step 3: Package as JAR

When users add your library to pom.xml,
Spring Boot auto-configures MyLibraryService.

================================================================
  IMPORTANT: DEFERRED EXECUTION
================================================================

AutoConfigurationImportSelector implements
DeferredImportSelector.

What does "deferred" mean?
  Auto-config classes run AFTER user's @Configuration classes.

Why is this important?
  User can define custom beans first.
  Auto-config checks @ConditionalOnMissingBean.
  If user defined bean -> auto-config skips.

Flow:
  1. User's @Configuration classes
  2. @ComponentScan (finds user beans)
  3. Auto-config classes (deferred) -- checks conditions

This ensures user beans ALWAYS get priority.

================================================================
  COMPLETE STARTUP FLOW (VISUAL DIAGRAM)
================================================================

  main() - @SpringBootApplication
    |
    v
  SpringApplication.run()
    |
    |--- 1. Create StopWatch (track startup time)
    |
    |--- 2. Determine WebApplicationType
    |       (SERVLET, REACTIVE, NONE)
    |
    |--- 3. Load Spring.factories
    |       (ApplicationContextInitializer,
    |        ApplicationListener)
    |
    |--- 4. Create ApplicationContext
    |       (AnnotationConfigServletWebServer
    |        ApplicationContext)
    |
    |--- 5. Prepare Context
    |       |--- Set Environment
    |       |--- Load properties
    |       |--- Register BeanPostProcessors
    |
    |--- 6. REFRESH CONTEXT (THE MAIN STEP)
    |       |
    |       |--- 6a. BeanFactory preparation
    |       |
    |       |--- 6b. ConfigurationClassPostProcessor
    |       |       |
    |       |       |--- Reads @SpringBootApplication
    |       |       |--- Processes @ComponentScan
    |       |       |    (finds user beans)
    |       |       |
    |       |       |--- Processes @EnableAutoConfiguration
    |       |       |    (via AutoConfigurationImportSelector)
    |       |       |    |
    |       |       |    |--- Reads AutoConfiguration.imports
    |       |       |    |--- Loads 100+ config classes
    |       |       |    |--- Removes duplicates
    |       |       |    |--- Sorts by order
    |       |       |    |--- Filters by @Conditional
    |       |       |    |--- Registers matching configs
    |       |       |
    |       |       |--- Processes @Bean methods
    |       |
    |       |--- 6c. Register BeanPostProcessors
    |       |
    |       |--- 6d. Initialize MessageSource
    |       |
    |       |--- 6e. OnRefresh() -> CREATE EMBEDDED SERVER
    |       |       |--- TomcatServletWebServerFactory
    |       |       |--- Create Tomcat instance
    |       |       |--- Start on port 8080
    |       |
    |       |--- 6f. Finish bean creation
    |       |
    |       |--- 6g. Publish ContextRefreshedEvent
    |
    |--- 7. After Refresh
    |       |--- Call ApplicationRunner / CommandLineRunner
    |       |--- Publish ApplicationReadyEvent
    |
    v
  Application is READY to serve requests!

================================================================
  INTERVIEW QUESTIONS & ANSWERS
================================================================

Q1: What is @EnableAutoConfiguration?
---------------------------------------
Answer: It is the annotation that tells Spring Boot to
automatically configure beans based on:
  - Dependencies in classpath
  - Existing beans
  - Properties in application.properties
  - Environment settings

It internally uses AutoConfigurationImportSelector
which reads AutoConfiguration.imports file.

Q2: How does Spring Boot know WHICH beans to configure?
----------------------------------------------------------
Answer: Spring Boot checks conditions:

  @ConditionalOnClass  - "Does this class exist?"
  @ConditionalOnBean   - "Does this bean exist?"
  @ConditionalOnMissingBean - "Is this bean missing?"
  @ConditionalOnProperty    - "Is this property set?"

Only when conditions pass, the configuration is applied.

Q3: How does adding a dependency in pom.xml
    auto-configure Spring Boot?
-----------------------------------------------
Answer:
  1. Maven downloads JAR containing .class files
  2. JARs go to classpath
  3. AutoConfigurationImportSelector reads
     AutoConfiguration.imports files from ALL JARs
  4. Each auto-config class has @ConditionalOnClass
  5. Checks if required classes exist in classpath
  6. If yes -> condition passes -> configures beans

Q4: Can I override auto-configuration?
-----------------------------------------
Answer: YES. Two ways:

  1. Define your own @Bean:
     - Your @Configuration runs FIRST
     - Auto-config checks @ConditionalOnMissingBean
     - If your bean exists -> auto-config skips

  2. Exclude specific auto-config:
     @SpringBootApplication(
         exclude = DataSourceAutoConfiguration.class
     )

Q5: What is the difference between
    AutoConfiguration.imports and spring.factories?
------------------------------------------------------
Answer:
  AutoConfiguration.imports (Spring Boot 2.7+):
    - ONLY for auto-configuration classes
    - Used by AutoConfigurationImportSelector

  spring.factories (legacy):
    - General SPI mechanism
    - Used for ApplicationListeners, Initializers etc.

Q6: Why is AutoConfigurationImportSelector "deferred"?
---------------------------------------------------------
Answer: It implements DeferredImportSelector.

Meaning: Auto-config runs AFTER user's configuration.
This ensures:
  - User beans are created first
  - @ConditionalOnMissingBean correctly detects user beans
  - User can override any auto-configured bean

Q7: How to debug auto-configuration issues?
----------------------------------------------
Answer: Set debug=true in application.properties.

Spring Boot prints Condition Evaluation Report showing:
  - Positive matches (what was configured)
  - Negative matches (what was skipped and why)
  - Exclusions

Q8: What happens if TWO auto-config classes
    try to create the same bean?
------------------------------------------------
Answer: Spring Boot handles this via:

  1. @ConditionalOnMissingBean - only one creates the bean
  2. @AutoConfigureBefore / @AutoConfigureAfter
     - controls execution order
  3. @Primary - marks preferred bean
  4. User bean always wins (processed first)

Q9: Can Spring Boot work WITHOUT @EnableAutoConfiguration?
------------------------------------------------------------
Answer: YES, but you lose auto-configuration.

You would have to manually configure everything:
  - DataSource, TransactionManager, JPA
  - DispatcherServlet, ViewResolver
  - Jackson ObjectMapper
  - Security configuration

That is essentially Spring Core (not Spring Boot).

Q10: How many auto-configuration classes
     does Spring Boot have?
--------------------------------------------
Answer: Spring Boot has 100+ auto-configuration classes
in spring-boot-autoconfigure.jar.

Each starter adds more auto-config classes.

================================================================
  FINAL SUMMARY
================================================================

@EnableAutoConfiguration is what makes Spring Boot "magic".

  +--------------------------------------------------+
  |  @EnableAutoConfiguration                        |
  |                                                  |
  |  1. Read AutoConfiguration.imports               |
  |  2. Get 100+ auto-config classes                 |
  |  3. Check @Conditional for each                  |
  |  4. Create beans for matching configs            |
  |  5. Defer to user beans (priority)              |
  +--------------------------------------------------+

This one annotation saves developers from writing
hundreds of lines of configuration code.

================================================================
*/

public class EnableAutoConfigurationDeepDive {

}
