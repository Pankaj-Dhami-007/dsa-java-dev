package springboot_deep_drive.springboot.application_dot_properties_file;

/*
============================================================
  ApplicationRunner & CommandLineRunner - INTERVIEW NOTES
============================================================

============================================================
  SIMPLE DEFINITION (Tell interviewer in 1 line)
============================================================

Both are Spring Boot interfaces that let you run code
AFTER the application context is fully loaded but
BEFORE the application starts accepting requests.

They are used for initialization logic that should run
at startup.

============================================================
  WHY DO THEY EXIST? (Interview Answer)
============================================================

Question: "Why not just use a constructor or @PostConstruct?"

Answer:
  Constructor runs when bean is created - that's TOO EARLY.
  At that point, other beans may not be ready, properties
  may not be injected, database may not be connected.

  @PostConstruct runs after bean initialization - but still
  BEFORE the full application is ready (embedded server may
  not be up yet).

  ApplicationRunner / CommandLineRunner runs AFTER:
  - ApplicationContext is fully loaded
  - All beans are created
  - Embedded server (Tomcat) is started
  - Application is ready to serve requests

============================================================
  CommandLineRunner (Interface)
============================================================

  @FunctionalInterface
  public interface CommandLineRunner {
      void run(String... args) throws Exception;
  }

  Takes raw String array args (like main method).

  Example:
    @Component
    public class MyRunner implements CommandLineRunner {
        @Override
        public void run(String... args) {
            System.out.println("App started with args: "
                + Arrays.toString(args));
        }
    }

  When to use:
  - You need raw command line arguments
  - Simple startup tasks (no parsing needed)

============================================================
  ApplicationRunner (Interface)
============================================================

  @FunctionalInterface
  public interface ApplicationRunner {
      void run(ApplicationArguments args) throws Exception;
  }

  Takes ApplicationArguments (wraps raw args with
  useful methods).

  ApplicationArguments gives:
  - args.getSourceArgs()       -> raw String array
  - args.getOptionNames()      -> set of --option names
  - args.getOptionValues("key")-> values for --key=value
  - args.getNonOptionArgs()    -> non-option arguments

  Example:
    @Component
    public class MyAppRunner implements ApplicationRunner {
        @Override
        public void run(ApplicationArguments args) {
            System.out.println("All option names: "
                + args.getOptionNames());
            System.out.println("--server.port value: "
                + args.getOptionValues("server.port"));
        }
    }

  When to use:
  - You need parsed command line arguments
  - You need --key=value style options
  - Better for production code (more readable)

============================================================
  KEY DIFFERENCE (Interview Answer)
============================================================

  CommandLineRunner:
    run(String... args)     -> raw String array

    Example args received:
      --server.port=8081 --profile=dev

    You need to manually parse:
      for (String arg : args) {
          if (arg.startsWith("--server.port=")) {
              String port = arg.split("=")[1];
          }
      }

  ApplicationRunner:
    run(ApplicationArguments args) -> parsed wrapper

    Same args received:
      args.getOptionValues("server.port") -> ["8081"]
      args.getOptionValues("profile")     -> ["dev"]

    No manual parsing needed.

  WHICH TO USE IN INTERVIEW?

    Say: "Use ApplicationRunner for production code
    because it provides parsed arguments. Use
    CommandLineRunner only for simple cases where
    you don't need argument parsing."

============================================================
  PRACTICAL REAL-WORLD EXAMPLES
============================================================

  Example 1: Load data into database at startup
  -------------------------------------------------

    @Component
    public class DataInitializer implements ApplicationRunner {

        @Autowired private UserRepository userRepo;
        @Autowired private RoleRepository roleRepo;

        @Override
        public void run(ApplicationArguments args) {
            if (userRepo.count() == 0) {
                // Seed data only if DB is empty
                userRepo.save(new User("admin", "admin@email.com"));
                roleRepo.save(new Role("ADMIN"));
                System.out.println("Seed data loaded");
            }
        }
    }

  Example 2: Check external service health at startup
  -----------------------------------------------------

    @Component
    public class HealthChecker implements ApplicationRunner {

        @Value("${external.api.url}")
        private String apiUrl;

        @Override
        public void run(ApplicationArguments args) {
            try {
                // Try connecting to external service
                URL url = new URL(apiUrl + "/health");
                HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.connect();

                if (conn.getResponseCode() != 200) {
                    System.out.println("WARNING: External API down");
                    // Could send alert, log, etc.
                }
            } catch (Exception e) {
                System.out.println("WARNING: Cannot reach "
                    + apiUrl);
            }
        }
    }

  Example 3: Load cache at startup
  -----------------------------------

    @Component
    public class CacheLoader implements ApplicationRunner {

        @Autowired private ProductService productService;
        @Autowired private CacheManager cacheManager;

        @Override
        public void run(ApplicationArguments args) {
            // Pre-load top 100 products into cache
            List<Product> topProducts =
                productService.getTopProducts(100);

            Cache cache = cacheManager.getCache("products");
            topProducts.forEach(p ->
                cache.put(p.getId(), p));

            System.out.println("Cache loaded with "
                + topProducts.size() + " products");
        }
    }

  Example 4: Read CLI args to configure app mode
  -------------------------------------------------

    @Component
    public class AppModeConfigurator implements ApplicationRunner {

        @Override
        public void run(ApplicationArguments args) {
            if (args.containsOption("dry-run")) {
                System.out.println("Running in DRY RUN mode");
                // Don't process, just validate
            }

            if (args.containsOption("config-file")) {
                String configFile =
                    args.getOptionValues("config-file").get(0);
                System.out.println("Using config: " + configFile);
                // Load external config
            }
        }
    }

    Run: java -jar app.jar --dry-run --config-file=prod.yml

============================================================
  MULTIPLE RUNNERS - ORDERING WITH @Order
============================================================

  When you have multiple runners, use @Order to control
  execution sequence:

    @Component
    @Order(1)
    public class DatabaseMigration implements ApplicationRunner {
        @Override
        public void run(ApplicationArguments args) {
            System.out.println("Step 1: Run migrations");
        }
    }

    @Component
    @Order(2)
    public class CacheLoader implements ApplicationRunner {
        @Override
        public void run(ApplicationArguments args) {
            System.out.println("Step 2: Load cache");
        }
    }

    @Component
    @Order(3)
    public class HealthCheck implements ApplicationRunner {
        @Override
        public void run(ApplicationArguments args) {
            System.out.println("Step 3: Check health");
        }
    }

  Output:
    Step 1: Run migrations
    Step 2: Load cache
    Step 3: Check health

  Lower number = runs first.
  Default order = LOWEST_PRECEDENCE (last).

============================================================
  INTERVIEW Q&A
============================================================

  Q1: What is the difference between CommandLineRunner
      and ApplicationRunner?
  -----------------------------------------------
  Answer:
    Both run code after application startup.
    Difference is how they receive arguments:

    CommandLineRunner -> run(String... args)
      Raw String array, must parse manually.

    ApplicationRunner -> run(ApplicationArguments args)
      Parsed wrapper, gives getOptionValues(), containsOption(),
      getNonOptionArgs() etc.

    Use ApplicationRunner for production.

  Q2: When does ApplicationRunner run in the
      application lifecycle?
  -----------------------------------------------
  Answer:
    After ApplicationContext is fully initialized.
    After embedded server is started.
    Before application handles first request.

    Order: Constructor -> @PostConstruct -> CommandLineRunner
    -> Application is ready

  Q3: Can you have multiple ApplicationRunners?
  -----------------------------------------------
  Answer:
    Yes. Spring will call ALL of them.
    Use @Order annotation to control sequence.

  Q4: What is a real-world use case?
  -----------------------------------------------
  Answer:
    - Loading seed data into DB at first startup
    - Pre-loading cache with frequently used data
    - Checking external service health
    - Running database migrations
    - Initializing scheduled tasks
    - Sending startup notification (email/slack)
    - Creating default users/roles

  Q5: What happens if one runner throws an exception?
  -----------------------------------------------
  Answer:
    Application startup fails.
    Spring stops and reports the error.
    Application will not start serving requests.

    So handle exceptions properly:
      @Override
      public void run(ApplicationArguments args) {
          try {
              // risky code
          } catch (Exception e) {
              log.error("Startup task failed", e);
              // Decide: should app start or fail?
              // If critical, throw RuntimeException
              // If non-critical, log and continue
          }
      }

  Q6: Difference between @PostConstruct and Runner?
  -----------------------------------------------
  Answer:
    @PostConstruct:
    - Runs after bean's dependency injection
    - Bean may not be fully ready
    - Runs DURING context loading

    ApplicationRunner:
    - Runs AFTER context is fully loaded
    - All beans are available
    - Server is running

    Analogy:
    @PostConstruct = Getting dressed after bath
    Runner = Leaving the house after fully ready

  Q7: Can I use lambda instead of implementing interface?
  -----------------------------------------------
  Answer:
    Yes, both are @FunctionalInterface.
    Use @Bean method:

      @Bean
      public ApplicationRunner myRunner() {
          return args -> {
              System.out.println("App started with options: "
                  + args.getOptionNames());
          };
      }

    This is cleaner than creating separate class.

============================================================
  QUICK REVISION - One-Liner Answers
============================================================

  Q: What is CommandLineRunner?
  A: Interface to run code at startup, receives raw String[] args.

  Q: What is ApplicationRunner?
  A: Interface to run code at startup, receives parsed args.

  Q: Difference?
  A: ApplicationRunner gives parsed args (getOptionValues()),
     CommandLineRunner gives raw args (String[]).

  Q: When do they run?
  A: After context loaded + server started, before first request.

  Q: Real use case?
  A: Seed data, cache loading, health checks, initialization.

  Q: Multiple runners?
  A: Yes, use @Order to control sequence.

  Q: Runner vs @PostConstruct?
  A: Runner runs LATER (after full app startup).
     @PostConstruct runs during bean initialization.

  Q: Can runners fail?
  A: Yes, exception stops app startup.

  Q: Lambda syntax?
  A: @Bean public ApplicationRunner r() { return args -> {}; }

============================================================
*/

public class ApplicationRunnerAndCommandLineRunner {

}
