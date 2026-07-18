package springboot_deep_drive.springboot.profiles;

/**
 * ===================================================================================
 * SPRING BOOT PROFILES & CONFIGURATION - COMPLETE REFERENCE GUIDE
 * ===================================================================================
 *
 * @author Engineering Digest
 * @version 1.0
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 1. PROFILES OVERVIEW
 * ===================================================================================
 *
 * Spring Profiles provide a way to segregate parts of application configuration
 * and make it available only in certain environments.
 *
 * ANALOGY: Different "wardrobes" for different occasions
 * ──────────────────────────────────────────────────────
 * | Occasion    | Wardrobe (Profile) | Contents              |
 * |-------------|--------------------|-----------------------|
 * | Development | dev                | Local DB, debug logs  |
 * | Testing     | test               | H2 DB, mock services  |
 * | Staging     | stage              | Staging DB, reduced   |
 * | Production  | prod               | Prod DB, optimized    |
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 2. DEFINING PROFILES - MULTIPLE APPROACHES
 * ===================================================================================
 *
 * APPROACH 1: application-{profile}.properties / .yml (RECOMMENDED)
 * ─────────────────────────────────────────────────────────────────
 * src/main/resources/
 * ├── application.properties          # Common properties
 * ├── application-dev.properties      # Development
 * ├── application-test.properties     # Testing
 * ├── application-prod.properties     # Production
 * └── application-local.properties    # Local development
 *
 * application.properties (common):
 * ─────────────────────────────────────────────────────────────────
 * spring.application.name=myapp
 * server.port=8080
 * logging.level.root=INFO
 *
 * application-dev.properties:
 * ─────────────────────────────────────────────────────────────────
 * spring.datasource.url=jdbc:h2:mem:devdb
 * spring.jpa.show-sql=true
 * logging.level.com.myapp=DEBUG
 * spring.profiles.active=dev
 *
 * application-prod.properties:
 * ─────────────────────────────────────────────────────────────────
 * spring.datasource.url=jdbc:postgresql://prod-db:5432/myapp
 * spring.jpa.show-sql=false
 * logging.level.com.myapp=WARN
 * server.port=8080
 *
 * ─────────────────────────────────────────────────────────────────
 *
 * APPROACH 2: application.yml with profile-specific sections
 * ─────────────────────────────────────────────────────────────────
 * spring:
 *   application:
 *     name: myapp
 *   profiles:
 *     active: dev
 * ---
 * spring:
 *   config:
 *     activate:
 *       on-profile: dev
 *   datasource:
 *     url: jdbc:h2:mem:devdb
 *   jpa:
 *     show-sql: true
 * ---
 * spring:
 *   config:
 *     activate:
 *       on-profile: prod
 *   datasource:
 *     url: jdbc:postgresql://prod-db:5432/myapp
 *   jpa:
 *     show-sql: false
 *
 * ─────────────────────────────────────────────────────────────────
 *
 * APPROACH 3: @Profile annotation on @Configuration / @Component
 * ─────────────────────────────────────────────────────────────────
 *
 * @Configuration
 * @Profile("dev")
 * public class DevConfig {
 *     @Bean
 *     public DataSource dataSource() { return new EmbeddedDatabaseBuilder().build(); }
 * }
 *
 * @Configuration
 * @Profile("prod")
 * public class ProdConfig {
 *     @Bean
 *     public DataSource dataSource() { return DataSourceBuilder.create().url(...).build(); }
 * }
 *
 * @Component
 * @Profile("test")
 * public class MockEmailService implements EmailService { ... }
 *
 * @Component
 * @Profile("!test")  // NOT test profile
 * public class RealEmailService implements EmailService { ... }
 *
 * @Profile expressions:
 * ─────────────────────────────────────────────────────────────────
 * @Profile("dev")           // Active when 'dev' is active
 * @Profile("!dev")          // Active when 'dev' is NOT active
 * @Profile({"dev", "test"}) // Active when 'dev' OR 'test' active
 * @Profile("dev & test")    // Active when BOTH 'dev' AND 'test' active
 * @Profile("dev | test")    // Active when 'dev' OR 'test' active (Spring 5.2+)
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 3. ACTIVATING PROFILES
 * ===================================================================================
 *
 * METHOD 1: application.properties / application.yml
 * ─────────────────────────────────────────────────────────────────
 * spring.profiles.active=dev
 *
 * METHOD 2: Command line argument (HIGHEST PRIORITY)
 * ─────────────────────────────────────────────────────────────────
 * java -jar myapp.jar --spring.profiles.active=prod
 * java -jar myapp.jar -Dspring.profiles.active=prod
 *
 * METHOD 3: Environment variable
 * ─────────────────────────────────────────────────────────────────
 * export SPRING_PROFILES_ACTIVE=prod
 * # Windows: set SPRING_PROFILES_ACTIVE=prod
 *
 * METHOD 4: Programmatically
 * ─────────────────────────────────────────────────────────────────
 * @SpringBootApplication
 * public class Application {
 *     public static void main(String[] args) {
 *         SpringApplication app = new SpringApplication(Application.class);
 *         app.setAdditionalProfiles("prod");
 *         app.run(args);
 *     }
 * }
 *
 * METHOD 5: SpringApplicationBuilder (Web applications)
 * ─────────────────────────────────────────────────────────────────
 * new SpringApplicationBuilder(Application.class)
 *     .profiles("prod")
 *     .run(args);
 *
 * METHOD 6: @ActiveProfiles (Testing)
 * ─────────────────────────────────────────────────────────────────
 * @SpringBootTest
 * @ActiveProfiles("test")
 * class MyServiceTest { ... }
 *
 * ─────────────────────────────────────────────────────────────────
 * PRIORITY ORDER (Highest to Lowest):
 * ─────────────────────────────────────────────────────────────────
 * 1. Command line args (--spring.profiles.active=prod)
 * 2. Environment variables (SPRING_PROFILES_ACTIVE)
 * 3. JVM System properties (-Dspring.profiles.active=prod)
 * 4. application.properties (spring.profiles.active=dev)
 * 5. SpringApplication.setAdditionalProfiles()
 * 6. @ActiveProfiles (tests only)
 * 7. Default profile (if none active)
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 4. PROFILE-SPECIFIC CONFIGURATION FILES
 * ===================================================================================
 *
 * FILE NAMING CONVENTION:
 * ─────────────────────────────────────────────────────────────────
 * application-{profile}.properties
 * application-{profile}.yml
 *
 * EXAMPLES:
 * ─────────────────────────────────────────────────────────────────
 * application-dev.properties
 * application-prod.yml
 * application-test.properties
 * application-staging.properties
 * application-local.properties
 *
 * LOADING ORDER (Spring Boot 2.4+):
 * ─────────────────────────────────────────────────────────────────
 * 1. application.properties (always loaded)
 * 2. application-{profile}.properties (for each active profile)
 * 3. Profile-specific files override common properties
 *
 * MULTIPLE PROFILES ACTIVE:
 * ─────────────────────────────────────────────────────────────────
 * spring.profiles.active=dev,aws,monitoring
 *
 * Loads in order:
 * 1. application.properties
 * 2. application-dev.properties
 * 3. application-aws.properties
 * 4. application-monitoring.properties
 * (Later profiles override earlier ones)
 *
 * PROFILE GROUPS (Spring Boot 2.4+):
 * ─────────────────────────────────────────────────────────────────
 * spring.profiles.group.production[0]=prod
 * spring.profiles.group.production[1]=mysql
 * spring.profiles.group.production[2]=monitoring
 *
 * Activate with: --spring.profiles.active=production
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 5. @ConfigurationProperties & TYPE-SAFE CONFIGURATION
 * ===================================================================================
 *
 * PREFERRED APPROACH (Spring Boot 2.0+):
 * ─────────────────────────────────────────────────────────────────
 *
 * application.yml:
 * ─────────────────────────────────────────────────────────────────
 * app:
 *   name: MyApplication
 *   version: 1.0.0
 *   database:
 *     url: jdbc:h2:mem:testdb
 *     username: sa
 *     password: ""
 *     pool-size: 10
 *   features:
 *     enable-cache: true
 *     enable-audit: false
 *   allowed-origins:
 *     - "http://localhost:3000"
 *     - "https://app.example.com"
 *
 * ─────────────────────────────────────────────────────────────────
 *
 * @ConfigurationProperties(prefix = "app")
 * @Validated
 * @ConstructorBinding  // Spring Boot 2.2+ for immutable configs
 * public record AppProperties(
 *     String name,
 *     String version,
 *     Database database,
 *     Features features,
 *     List<String> allowedOrigins
 * ) {
 *     public record Database(
 *         String url,
 *         String username,
 *         String password,
 *         @Min(1) @Max(100) int poolSize
 *     ) {}
 *
 *     public record Features(
 *         boolean enableCache,
 *         boolean enableAudit
 *     ) {}
 * }
 *
 * ─────────────────────────────────────────────────────────────────
 *
 * ENABLE CONFIGURATION PROPERTIES:
 * ─────────────────────────────────────────────────────────────────
 *
 * // Option 1: On main class
 * @SpringBootApplication
 * @EnableConfigurationProperties(AppProperties.class)
 * public class Application { ... }
 *
 * // Option 2: On configuration class
 * @Configuration
 * @EnableConfigurationProperties(AppProperties.class)
 * public class AppConfig { ... }
 *
 * // Option 3: Scan packages (Spring Boot 2.2+)
 * @ConfigurationPropertiesScan("com.myapp.config")
 * public class Application { ... }
 *
 * ─────────────────────────────────────────────────────────────────
 *
 * USAGE IN COMPONENTS:
 * ─────────────────────────────────────────────────────────────────
 * @Service
 * @RequiredArgsConstructor
 * public class MyService {
 *     private final AppProperties props;
 *
 *     public void printConfig() {
 *         System.out.println(props.name() + " v" + props.version());
 *         System.out.println("DB Pool: " + props.database().poolSize());
 *     }
 * }
 *
 * VALIDATION:
 * ─────────────────────────────────────────────────────────────────
 * Add @Validated on the record/class and use Bean Validation annotations:
 * @NotBlank, @Size, @Min, @Max, @Email, @URL, @Pattern, etc.
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 6. @Value vs @ConfigurationProperties
 * ===================================================================================
 *
 * | Feature              | @Value                    | @ConfigurationProperties    |
 * |---------------------|---------------------------|-----------------------------|
 * | Type Safety         | ❌ String only            | ✅ Full type support        |
 * | Validation          | ❌ Manual                 | ✅ @Validated + JSR-303     |
 * | Relaxed Binding     | ❌ No                     | ✅ Yes (camelCase, kebab)   |
 * | IDE Support         | ❌ Limited                | ✅ Full autocomplete        |
 * | Immutable (records) | ❌ No                     | ✅ Yes (constructor binding)|
 * | Nested Objects      | ❌ No                     | ✅ Yes                      |
 * | List/Map Binding    | ❌ Manual parsing         | ✅ Automatic                |
 * | Testing             | ❌ Hard to mock           | ✅ Easy to inject mock      |
 *
 * RECOMMENDATION: Always prefer @ConfigurationProperties
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 7. PROFILE-SPECIFIC BEANS WITH @Profile
 * ===================================================================================
 *
 * @Configuration
 * public class DataSourceConfig {
 *
 *     @Bean
 *     @Profile("dev")
 *     public DataSource devDataSource() {
 *         return new EmbeddedDatabaseBuilder()
 *             .setType(EmbeddedDatabaseType.H2)
 *             .addScript("schema.sql")
 *             .build();
 *     }
 *
 *     @Bean
 *     @Profile("test")
 *     public DataSource testDataSource() {
 *         return new EmbeddedDatabaseBuilder()
 *             .setType(EmbeddedDatabaseType.H2)
 *             .build();
 *     }
 *
 *     @Bean
 *     @Profile("prod")
 *     public DataSource prodDataSource(@Value("${spring.datasource.url}") String url,
 *                                       @Value("${spring.datasource.username}") String user,
 *                                       @Value("${spring.datasource.password}") String pass) {
 *         HikariDataSource ds = new HikariDataSource();
 *         ds.setJdbcUrl(url);
 *         ds.setUsername(user);
 *         ds.setPassword(pass);
 *         return ds;
 *     }
 * }
 *
 * SERVICE LAYER WITH PROFILE-SPECIFIC IMPLEMENTATIONS:
 * ─────────────────────────────────────────────────────────────────
 *
 * public interface EmailService { void send(String to, String subject, String body); }
 *
 * @Service
 * @Profile("dev")
 * public class ConsoleEmailService implements EmailService {
 *     public void send(String to, String subject, String body) {
 *         System.out.println("EMAIL TO " + to + ": " + subject);
 *     }
 * }
 *
 * @Service
 * @Profile("prod")
 * public class SmtpEmailService implements EmailService {
 *     public void send(String to, String subject, String body) { ... }
 * }
 *
 * @Service
 * @Profile("test")
 * public class MockEmailService implements EmailService {
 *     public void send(String to, String subject, String body) { /* no-op */


/**
 * ===================================================================================
 * 8. SPRING BOOT 2.4+ CONFIG FILE PROCESSING CHANGES
 * ===================================================================================
 *
 * KEY CHANGES (Spring Boot 2.4+):
 * ─────────────────────────────────────────────────────────────────
 * 1. spring.profiles.active NO LONGER supported in profile-specific files
 *    ❌ application-dev.properties CANNOT contain: spring.profiles.active=dev
 *
 * 2. Use spring.config.activate.on-profile instead:
 *    ─────────────────────────────────────────────────────────────────
 *    # application.yml (multi-document)
 *    spring:
 *      config:
 *        activate:
 *          on-profile: dev
 *    # OR in properties file
 *    spring.config.activate.on-profile=dev
 *
 * 3. Profile-specific files loaded in order of activation
 *    Last wins on property conflicts
 *
 * 4. spring.config.import for importing additional config
 *    spring.config.import=optional:file:./config.yml,optional:classpath:/extra.yml
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 9. EXTERNALIZED CONFIGURATION
 * ===================================================================================
 *
 * CONFIGURATION SOURCES (Priority Order - Highest First):
 * ─────────────────────────────────────────────────────────────────
 * 1. Command line arguments (--server.port=8081)
 * 2. SPRING_APPLICATION_JSON (env var / system prop)
 * 3. ServletConfig init parameters
 * 4. ServletContext init parameters
 * 5. JNDI attributes
 * 6. Java System properties (-Dserver.port=8081)
 * 7. OS Environment variables (SERVER_PORT=8081)
 * 8. RandomValuePropertySource (random.*)
 * 9. Profile-specific application-{profile}.properties OUTSIDE jar
 * 10. application.properties OUTSIDE jar
 * 11. Profile-specific application-{profile}.properties INSIDE jar
 * 12. application.properties INSIDE jar
 * 13. @PropertySource on @Configuration
 * 14. Default properties (SpringApplication.setDefaultProperties)
 *
 * EXTERNAL CONFIG LOCATIONS:
 * ─────────────────────────────────────────────────────────────────
 * --spring.config.location=file:/etc/myapp/config.yml
 * --spring.config.additional-location=file:/etc/myapp/override.yml
 *
 * SPRING CLOUD CONFIG (Distributed Config):
 * ─────────────────────────────────────────────────────────────────
 * spring:
 *   cloud:
 *     config:
 *       uri: http://config-server:8888
 *       profile: prod
 *       label: v1.0
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 10. YAML MULTI-DOCUMENT SYNTAX
 * ===================================================================================
 *
 * Use --- to separate documents in a single YAML file:
 * ─────────────────────────────────────────────────────────────────
 *
 * spring:
 *   application:
 *     name: myapp
 * ---
 * spring:
 *   config:
 *     activate:
 *       on-profile: dev
 *   datasource:
 *     url: jdbc:h2:mem:devdb
 *   jpa:
 *     show-sql: true
 * ---
 * spring:
 *   config:
 *     activate:
 *       on-profile: prod
 *   datasource:
 *     url: jdbc:postgresql://prod:5432/myapp
 *   jpa:
 *     show-sql: false
 *
 * BENEFITS:
 * ─────────────────────────────────────────────────────────────────
 * ✅ Single file for all environments
 * ✅ Easy to compare configs across profiles
 * ✅ Version controlled together
 * ✅ IDE support for YAML
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 11. TESTING WITH PROFILES
 * ===================================================================================
 *
 * @SpringBootTest
 * @ActiveProfiles("test")
 * @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
 * class UserServiceIntegrationTest {
 *
 *     @Autowired
 *     UserService userService;
 *
 *     @Test
 *     void testCreateUser() { ... }
 * }
 *
 * PROGRAMMATIC PROFILE ACTIVATION IN TESTS:
 * ─────────────────────────────────────────────────────────────────
 * @Test
 * void testWithProfile() {
 *     try (MockEnvironment env = new MockEnvironment()) {
 *         env.setActiveProfiles("dev");
 *         // or use SpringApplicationBuilder
 *     }
 * }
 *
 * @TestPropertySource for test-specific properties:
 * ─────────────────────────────────────────────────────────────────
 * @SpringBootTest
 * @TestPropertySource(properties = {
 *     "spring.datasource.url=jdbc:h2:mem:testdb",
 *     "app.feature.enabled=true"
 * })
 * class MyTest { ... }
 *
 * @TestPropertySource(locations = "classpath:test.properties")
 * class MyTest { ... }
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 12. CONDITIONAL CONFIGURATION (@ConditionalOn...)
 * ===================================================================================
 *
 * Spring Boot provides many @ConditionalOn... annotations:
 * ─────────────────────────────────────────────────────────────────
 *
 * @ConditionalOnProperty(name = "feature.enabled", havingValue = "true")
 * @ConditionalOnProperty(name = "app.mode", matchIfMissing = true)
 *
 * @ConditionalOnMissingBean(DataSource.class)
 * @ConditionalOnBean(RedisTemplate.class)
 *
 * @ConditionalOnClass(name = "org.springframework.data.redis.core.RedisTemplate")
 * @ConditionalOnMissingClass(name = "org.mongodb.MongoClient")
 *
 * @ConditionalOnExpression("${app.cache.enabled:true} and ${app.cache.type:redis == 'redis'}")
 *
 * @ConditionalOnResource(resources = "classpath:config.yml")
 *
 * @ConditionalOnWebApplication(type = Type.SERVLET)
 * @ConditionalOnNotWebApplication
 *
 * CUSTOM CONDITION:
 * ─────────────────────────────────────────────────────────────────
 * public class OnKubernetesCondition implements Condition {
 *     @Override
 *     public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
 *         return System.getenv("KUBERNETES_SERVICE_HOST") != null;
 *     }
 * }
 *
 * @Configuration
 * @Conditional(OnKubernetesCondition.class)
 * public class K8sConfig { ... }
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 13. RELAXED BINDING (Property Name Mapping)
 * ===================================================================================
 *
 * Spring Boot uses relaxed binding for @ConfigurationProperties:
 * ─────────────────────────────────────────────────────────────────
 *
 * Property Name          | Java Field
 * -----------------------|------------------
 * app.my-property        | myProperty
 * app.myProperty         | myProperty
 * app.MY_PROPERTY        | myProperty
 * app.my_property        | myProperty
 * app.myProperty         | myProperty
 *
 * For Maps:
 * app.config[key1]=value1
 * app.config.key2=value2
 *
 * For Lists:
 * app.servers[0]=server1
 * app.servers[1]=server2
 * app.servers=server1,server2,server3
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 14. PROFILES BEST PRACTICES
 * ===================================================================================
 *
 * 1. USE DEFAULT PROFILE FOR LOCAL DEVELOPMENT
 *    ─────────────────────────────────────────────────────────────────
 *    application.properties = local dev config
 *    application-prod.properties = production overrides
 *
 * 2. NEVER COMMIT SECRETS
 *    ─────────────────────────────────────────────────────────────────
 *    # application.properties (committed)
 *    spring.datasource.url=jdbc:h2:mem:dev
 *
 *    # application-prod.properties (NOT committed, external)
 *    spring.datasource.url=jdbc:postgresql://${DB_HOST}/${DB_NAME}
 *    spring.datasource.password=${DB_PASSWORD}
 *
 * 3. USE ENVIRONMENT VARIABLES FOR SECRETS
 *    ─────────────────────────────────────────────────────────────────
 *    spring.datasource.password=${DB_PASSWORD:changeme}
 *
 * 4. PROFILE GROUPS FOR COMPLEX DEPLOYMENTS
 *    ─────────────────────────────────────────────────────────────────
 *    spring.profiles.group.prod[0]=postgres
 *    spring.profiles.group.prod[1]=redis
 *    spring.profiles.group.prod[2]=monitoring
 *
 * 5. DOCUMENT REQUIRED PROPERTIES
 *    ─────────────────────────────────────────────────────────────────
 *    @ConfigurationProperties(prefix = "app")
 *    @ConstructorBinding
 *    public record AppConfig(
 *         @NotBlank String apiKey,
 *         @Valid DatabaseConfig database
 *    ) {}
 *
 * 6. USE @ConfigurationPropertiesScan FOR AUTO-DISCOVERY
 *    ─────────────────────────────────────────────────────────────────
 *    @SpringBootApplication
 *    @ConfigurationPropertiesScan("com.myapp.config")
 *    public class Application { }
 *
 * 7. TEST WITH @ActiveProfiles AND @TestPropertySource
 *    ─────────────────────────────────────────────────────────────────
 *
 * 8. AVOID PROFILE-SPECIFIC CODE IN BUSINESS LOGIC
 *    ─────────────────────────────────────────────────────────────────
 *    ❌ if (environment.acceptsProfiles("prod")) { ... }
 *    ✅ @Profile("prod") on @Bean / @Component
 *
 * 9. USE IMMUTABLE RECORDS FOR CONFIG (Java 17+)
 *    ─────────────────────────────────────────────────────────────────
 *
 * 10. VALIDATE CONFIG AT STARTUP
 *     ─────────────────────────────────────────────────────────────────
 *     @Validated on @ConfigurationProperties + @Validated on @Configuration
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 15. COMMON PROFILE PROPERTY EXAMPLES
 * ===================================================================================
 *
 * application-dev.properties:
 * ─────────────────────────────────────────────────────────────────
 * # Database
 * spring.datasource.url=jdbc:h2:mem:devdb;DB_CLOSE_DELAY=-1
 * spring.datasource.driver-class-name=org.h2.Driver
 * spring.jpa.hibernate.ddl-auto=create-drop
 * spring.jpa.show-sql=true
 * spring.h2.console.enabled=true
 *
 * # Logging
 * logging.level.root=INFO
 * logging.level.com.myapp=DEBUG
 * logging.pattern.console=%d{HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
 *
 * # Dev Tools
 * spring.devtools.restart.enabled=true
 * spring.devtools.livereload.enabled=true
 *
 * # Cache (simple in-memory)
 * spring.cache.type=simple
 *
 * ─────────────────────────────────────────────────────────────────
 *
 * application-prod.properties:
 * ─────────────────────────────────────────────────────────────────
 * # Database
 * spring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
 * spring.datasource.username=${DB_USERNAME}
 * spring.datasource.password=${DB_PASSWORD}
 * spring.datasource.hikari.maximum-pool-size=20
 * spring.jpa.hibernate.ddl-auto=validate
 * spring.jpa.show-sql=false
 *
 * # Logging
 * logging.level.root=WARN
 * logging.level.com.myapp=INFO
 * logging.file.name=/var/log/myapp/application.log
 * logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
 *
 * # Security
 * server.ssl.enabled=true
 * server.ssl.key-store=classpath:keystore.p12
 * server.ssl.key-store-password=${SSL_KEY_STORE_PASSWORD}
 *
 * # Actuator
 * management.endpoints.web.exposure.include=health,info,metrics,prometheus
 * management.endpoint.health.show-details=when-authorized
 *
 * # Cache (Redis)
 * spring.cache.type=redis
 * spring.redis.host=${REDIS_HOST}
 * spring.redis.port=${REDIS_PORT}
 *
 * ─────────────────────────────────────────────────────────────────
 *
 * application-test.properties:
 * ─────────────────────────────────────────────────────────────────
 * spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
 * spring.jpa.hibernate.ddl-auto=create-drop
 * spring.jpa.show-sql=false
 * logging.level.root=WARN
 * spring.cache.type=simple
 *
 * # Disable external services
 * app.external-api.enabled=false
 * app.email.enabled=false
 * app.sms.enabled=false
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 16. QUICK REFERENCE CHEAT SHEET
 * ===================================================================================
 *
 * ANNOTATIONS:
 * ─────────────────────────────────────────────────────────────────
 * @Profile("dev")                    // Activate when 'dev' active
 * @Profile("!prod")                  // Activate when 'prod' NOT active
 * @Profile({"dev", "test"})          // OR condition
 * @ActiveProfiles("test")            // On test class
 * @TestPropertySource(...)           // Test properties
 * @ConfigurationProperties("prefix") // Type-safe config binding
 * @EnableConfigurationProperties     // Enable @ConfigurationProperties beans
 * @ConfigurationPropertiesScan       // Auto-scan for @ConfigurationProperties
 * @ConstructorBinding                // Immutable config (records)
 * @Validated                         // Enable JSR-303 validation
 *
 * PROPERTIES:
 * ─────────────────────────────────────────────────────────────────
 * spring.profiles.active=dev,aws
 * spring.profiles.group.production=prod,mysql,monitoring
 * spring.config.activate.on-profile=dev
 * spring.config.import=optional:file:./config.yml
 * spring.config.location=file:/etc/app/config.yml
 *
 * COMMAND LINE:
 * ─────────────────────────────────────────────────────────────────
 * java -jar app.jar --spring.profiles.active=prod
 * java -jar app.jar --spring.config.location=file:/etc/app.yml
 * java -Dspring.profiles.active=prod -jar app.jar
 *
 * ENVIRONMENT:
 * ─────────────────────────────────────────────────────────────────
 * SPRING_PROFILES_ACTIVE=prod
 * SPRING_CONFIG_LOCATION=file:/etc/app.yml
 * DB_PASSWORD=secret
 *
 * TESTING:
 * ─────────────────────────────────────────────────────────────────
 * @SpringBootTest
 * @ActiveProfiles("test")
 * @TestPropertySource(properties = "key=value")
 * @TestPropertySource(locations = "classpath:test.properties")
 * ===================================================================================
 */

public class ConfigurationsAndProfilingInSpringboot {
    // This class serves as a documentation holder for Spring Boot Profiles & Configuration concepts.
    // See class-level Javadoc for complete reference guide.
}