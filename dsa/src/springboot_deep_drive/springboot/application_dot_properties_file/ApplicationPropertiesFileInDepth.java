package springboot_deep_drive.springboot.application_dot_properties_file;

/*
============================================================
  APPLICATION.PROPERTIES - INTERVIEW NOTES
============================================================

============================================================
  SIMPLE DEFINITION (Tell interviewer in 1 line)
============================================================

application.properties is Spring Boot's default config file.
It's a key=value file that tells Spring Boot how to behave
(port, database, logging, etc.).

Spring Boot loads it automatically from
  src/main/resources/application.properties
No code needed.

============================================================
  WHY SPRING BOOT CREATED THIS? (Interview Answer)
============================================================

Before Spring Boot, there was NO standard way to configure
Spring apps. Every project had a different approach:

  Spring Core approaches:
  1. XML PropertyPlaceholderConfigurer
     <context:property-placeholder location="db.properties"/>
     - Manual XML config needed

  2. @PropertySource + Environment
     @PropertySource("classpath:db.properties")
     env.getProperty("db.url")
     - Still needed annotation on config class

  3. @Value annotation
     @Value("${db.url}")
     - Still needed PropertyPlaceholderConfigurer or @PropertySource

  4. System properties (-Ddb.url=...)
     - Hard to manage, no structure

  PROBLEMS with all these:
  - No standard file location
  - Manual loading code needed
  - No profile support (dev/prod)
  - No type safety (all strings)
  - No IDE autocomplete

Spring Boot fixed ALL of this with application.properties.

============================================================
  KEY FEATURES (Mention these in interview)
============================================================

  1. AUTO-LOADED - Just put file in classpath, Spring loads it
  2. PROFILES - application-dev.properties, application-prod.properties
  3. EXTERNAL CONFIG - Can override outside JAR (env vars, CLI args)
  4. TYPE-SAFE BINDING - @ConfigurationProperties binds to POJO

============================================================
  PROFILES (How to handle dev vs prod)
============================================================

  File naming:
    application.properties        (base - always loaded)
    application-dev.properties    (dev profile)
    application-prod.properties   (prod profile)

  Activate profile:
    application.properties:
      spring.profiles.active=dev

    OR command line:
      java -jar app.jar --spring.profiles.active=prod

    OR environment variable:
      SPRING_PROFILES_ACTIVE=prod

  Profile values OVERRIDE base values.

============================================================
  EXTERNAL CONFIG PRIORITY (Important for interview)
============================================================

  Later sources override earlier ones:

    1. Command line args (--server.port=8081)
    2. OS environment variables (SERVER_PORT=8081)
    3. application-{profile}.properties (outside JAR)
    4. application-{profile}.properties (inside JAR)
    5. application.properties (outside JAR)
    6. application.properties (inside JAR)
    7. @PropertySource on config classes
    8. Spring defaults (server.port=8080)

============================================================
  .properties vs .yml (Interview comparison)
============================================================

  .properties (flat key=value):
    server.port=8081
    spring.datasource.url=jdbc:mysql://localhost:3306/db

  .yml (hierarchical):
    server:
      port: 8081
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/db

  When to use what:
  - .properties -> Simple config, less error-prone
  - .yml -> Complex hierarchical config, lists, maps

============================================================
  PRACTICAL EXAMPLE - Real application.properties
============================================================

    # Server
    server.port=${PORT:8081}
    server.servlet.context-path=/api/v1

    # Database (use placeholders, NEVER hardcode secrets)
    spring.datasource.url=jdbc:postgresql://${DB_HOST:localhost}:5432/mydb
    spring.datasource.username=${DB_USER:root}
    spring.datasource.password=${DB_PASS}

    # JPA
    spring.jpa.hibernate.ddl-auto=validate
    spring.jpa.show-sql=false

    # Logging
    logging.level.root=WARN
    logging.level.com.myapp=INFO

    # Custom app props (accessed via @ConfigurationProperties)
    app.upload.max-file-size=10MB
    app.feature.new-checkout=true

============================================================
  HOW TO ACCESS PROPS IN CODE?
============================================================

  Method 1: @Value (simple, one property at a time)
    @Value("${app.feature.new-checkout:false}")
    private boolean newCheckoutEnabled;

  Method 2: @ConfigurationProperties (group of properties)
    @ConfigurationProperties(prefix = "app.upload")
    public class UploadProperties {
        private String maxFileSize;
        // getters + setters - Spring auto-binds!
    }

  Method 3: Environment (programmatic)
    @Autowired
    private Environment env;
    env.getProperty("server.port");

============================================================
  COMMON MISTAKES (What NOT to do)
============================================================

  X Hardcoding secrets:
    spring.datasource.password=admin123
    -> Exposed in version control!

  O Use placeholders:
    spring.datasource.password=${DB_PASSWORD}

  X Wrong property names (silently ignored):
    server.portnumber=9090

  X No profile separation:
    Putting prod DB URL in base application.properties

============================================================
  ALTERNATIVES (Interview question)
============================================================

  When to use what:
  - Simple app        -> application.properties
  - Complex config    -> application.yml
  - Docker/K8s        -> .properties defaults + env vars
  - Microservices     -> Spring Cloud Config Server
  - Secrets           -> Vault or K8s Secrets
  - Quick dev         -> application.properties in classpath

============================================================
  INTERVIEW Q&A
============================================================

  Q1: What is application.properties?
  A: Default config file in Spring Boot. Key=value format.
     Auto-loaded from src/main/resources. No code needed.

  Q2: Why not use Spring Core's @PropertySource?
  A: @PropertySource needs manual setup. application.properties
     is auto-loaded, supports profiles, external overrides,
     and type-safe binding.

  Q3: How to handle dev vs prod config?
  A: Use profile-specific files. application-dev.properties
     for dev, application-prod.properties for prod.
     Activate with spring.profiles.active=dev.

  Q4: How to override properties at runtime?
  A: Use CLI args (--server.port=9090) or env variables.
     They have higher priority than properties file.

  Q5: What is @ConfigurationProperties?
  A: Type-safe binding of property groups to POJO.
     Better than @Value for grouped config. Supports
     validation with @Validated.

  Q6: How to secure sensitive properties?
  A: NEVER hardcode. Use ${} placeholders + env variables.
     For production use Vault or K8s Secrets.

  Q7: properties vs yml?
  A: .properties = flat key=value. .yml = hierarchical.
     Same features. YAML better for complex configs.

============================================================
  QUICK REVISION - One-Liner Answers
============================================================

  Q: What is application.properties?
  A: Spring Boot's auto-loaded config file (key=value).

  Q: Location?
  A: src/main/resources/application.properties

  Q: Dev vs Prod config?
  A: Use profiles - application-dev.properties etc.

  Q: Override at runtime?
  A: CLI args or env vars (higher priority).

  Q: Access in code?
  A: @Value, @ConfigurationProperties, Environment.

  Q: @ConfigurationProperties vs @Value?
  A: @ConfigurationProperties = group binding + validation.
     @Value = single property.

  Q: How to handle secrets?
  A: ${} placeholders + env vars or Vault.

  Q: properties vs yml?
  A: .properties = flat. .yml = hierarchical.

  Q: Default port?
  A: 8080. Change with server.port.

============================================================
*/

public class ApplicationPropertiesFileInDepth {

}
