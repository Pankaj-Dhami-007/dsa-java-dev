package springboot_deep_drive.spring_mvc;

/*
 * ═══════════════════════════════════════════════════════════════════════
 *                 SPRING MVC — COMPLETE DEVELOPER THEORY
 * ═══════════════════════════════════════════════════════════════════════
 *
 * ───────────────────────────────────────────────────────────────────────
 * 1. WHAT IS SPRING MVC?
 * ───────────────────────────────────────────────────────────────────────
 *
 * Spring MVC is a web framework built on top of the Servlet API.
 * It implements the MVC (Model-View-Controller) design pattern.
 *
 * used to -> rest apis, web app(html/jsp)
 *
 *   ┌──────────┐    ┌──────────────────────────────────┐    ┌──────────┐
 *   │  Client  │───▶│        Spring MVC                │───▶│  Model   │
 *   │ (Browser)│    │  ┌──────────┐ ┌───────────────┐  │    │ (Data)   │
 *   │          │    │  │Controller│→│Service/Repo   │  │    └──────────┘
 *   │  /curl   │    │  │(Handler) │ │(Business Logic)│  │
 *   │          │    │  └──────────┘ └───────────────┘  │    ┌──────────┐
 *   │          │◀───│  ┌──────────────────────────┐    │◀───│  View    │
 *   │          │    │  │      View (JSP/JSON)     │    │    │(Response)│
 *   └──────────┘    │  └──────────────────────────┘    │    └──────────┘
 *                   └──────────────────────────────────┘
 *
 *   It is part of the Spring Framework (NOT Spring Boot).
 *   Spring Boot auto-configures Spring MVC (spring-boot-starter-web).
 *
 * ───────────────────────────────────────────────────────────────────────
 * 2. WHY SPRING MVC? (Problems Raw Servlet Could Not Solve)
 * ───────────────────────────────────────────────────────────────────────
 *
 *   ┌──────────────────────────────────────┬──────────────────────────────────┐
 *   │ Problem in Raw Servlet               │ Spring MVC Solution              │
 *   ├──────────────────────────────────────┼──────────────────────────────────┤
 *   │ One servlet per URL → too many       │ One DispatcherServlet for all    │
 *   │ Manual routing (if/else on URI)       │ @RequestMapping("/api/users")    │
 *   │ Manual JSON parsing (getReader)      │ @RequestBody → Jackson auto      │
 *   │ Manual JSON writing (getWriter)      │ @ResponseBody → Jackson auto     │
 *   │ Manual parameter extraction          │ @RequestParam, @PathVariable     │
 *   │ Manual validation (if/else checks)   │ @Valid + Bean Validation         │
 *   │ Request + Response tightly coupled   │ Clean separation: Controller,    │
 *   │                                      │ Service, Repository layers        │
 *   │ No view resolution                   │ ViewResolver (JSP, Thymeleaf)    │
 *   │ No interceptors                      │ HandlerInterceptor               │
 *   │ No file upload handling              │ MultipartResolver                │
 *   └──────────────────────────────────────┴──────────────────────────────────┘
 *
 * ───────────────────────────────────────────────────────────────────────
 * 3. ROLE OF SERVLET IN SPRING MVC
 * ───────────────────────────────────────────────────────────────────────
 *
 *   DispatcherServlet IS A SERVLET. It extends HttpServlet.
 *
 *   ┌─────────────────────────────────────────────────────────────────────┐
 *   │  TOMCAT                             SPRING MVC                      │
 *   │  ┌──────────────┐                   ┌──────────────────────────┐    │
 *   │  │ Port 8080    │                  │ DispatcherServlet        │    │
 *   │  │ HTTP Parser  │── req/res ──────▶│   extends HttpServlet    │    │
 *   │  │ Thread Pool  │                  │   @WebServlet("/*")      │    │
 *   │  │ Session Mgr  │                  │                          │    │
 *   │  │ ClassLoader  │                  │ service() → doDispatch() │    │
 *   │  └──────────────┘                  │   ↓                      │    │
 *   │                                    │ HandlerMapping → Handler │    │
 *   │                                    │ HandlerAdapter → invoke  │    │
 *   │                                    │ Controller → return      │    │
 *   │                                    │ ViewResolver → render    │    │
 *   │                                    └──────────────────────────┘    │
 *   └─────────────────────────────────────────────────────────────────────┘
 *
 *   Tomcat still does ALL the same work (port, HTTP parse, thread pool, etc.)
 *   Difference: instead of calling YOUR servlet, Tomcat calls
 *   DispatcherServlet.service(), which then internally routes to YOUR
 *   @Controller method.
 *
 * ───────────────────────────────────────────────────────────────────────
 * 4. SPRING MVC ARCHITECTURE
 * ───────────────────────────────────────────────────────────────────────
 *
 *   ┌─────────────────────────────────────────────────────────────────────────┐
 *   │                         SPRING MVC                                      │
 *   │                                                                         │
 *   │   HTTP Request                                                          │
 *   │      │                                                                  │
 *   │      ▼                                                                  │
 *   │   ┌──────────────────┐   ┌────────────────┐   ┌────────────────────┐   │
 *   │   │ DispatcherServlet│──▶│ HandlerMapping  │──▶│ HandlerAdapter     │   │
 *   │   │  (Front Controller)│   │ (maps URL→method)│   │ (invokes method)   │   │
 *   │   └──────────────────┘   └────────────────┘   └────────────────────┘   │
 *   │         ▲                                              │                │
 *   │         │                                              ▼                │
 *   │         │                                    ┌────────────────────┐   │
 *   │         │                                    │   Controller       │   │
 *   │         │                                    │   (@RequestMapping)│   │
 *   │         │                                    └────────────────────┘   │
 *   │         │                                              │              │
 *   │         │                                              ▼              │
 *   │         │                                    ┌────────────────────┐   │
 *   │         │                                    │ Service / Business │   │
 *   │         │                                    │       Layer        │   │
 *   │         │                                    └────────────────────┘   │
 *   │         │                                              │              │
 *   │         │                                    ┌────────────────────┐   │
 *   │         │                                    │    ModelAndView    │   │
 *   │         │                                    │  or @ResponseBody  │   │
 *   │         │                                    └────────────────────┘   │
 *   │         │                                              │              │
 *   │         │                    ┌─────────────────────────┘              │
 *   │         │                    ▼                                        │
 *   │         │          ┌──────────────────┐    ┌──────────────────┐      │
 *   │         │          │  ViewResolver    │    │  MessageConverter │      │
 *   │         │          │  (JSP/Thymeleaf) │    │  (JSON/XML)      │      │
 *   │         │          └──────────────────┘    └──────────────────┘      │
 *   │         │                    │                       │               │
 *   │         └────────────────────┼───────────────────────┘               │
 *   │                              ▼                                       │
 *   │                    HTTP Response                                      │
 *   └─────────────────────────────────────────────────────────────────────────┘
 *
 * ───────────────────────────────────────────────────────────────────────
 * 5. COMPLETE REQUEST FLOW (Client → Server → Client)
 * ───────────────────────────────────────────────────────────────────────
 *
 *   Client sends: GET /api/users/5
 *
 *   ┌──────┐     ┌──────────┐     ┌──────────────────┐     ┌────────────────┐
 *   │Client│────▶│ Tomcat   │────▶│DispatcherServlet │────▶│ HandlerMapping │
 *   │      │     │          │     │                  │     │                │
 *   │curl  │     │1.Parse   │     │3. service()      │     │4. Look up      │
 *   │      │     │  HTTP    │     │   → doDispatch() │     │   @RequestMapping
 *   │GET   │     │2.Create  │     │                  │     │   → getHandler │
 *   │/api/ │     │  req/res │     │                  │     │                │
 *   │users/│     │          │     │                  │     │ UserController │
 *   │5     │     │          │     │                  │     │ .getById()     │
 *   └──────┘     └──────────┘     └──────────────────┘     └────────────────┘
 *      ▲                                                     │
 *      │                                                     ▼
 *      │                                               ┌────────────────┐
 *      │                                               │ HandlerAdapter │
 *      │                                               │                │
 *      │                                               │5. Invokes      │
 *      │                                               │   controller   │
 *      │                                               │   method       │
 *      │                                               └────────────────┘
 *      │                                                     │
 *      │                                                     ▼
 *      │                                               ┌────────────────┐
 *      │                                               │  Controller    │
 *      │                                               │                │
 *      │                                               │6. @GetMapping  │
 *      │                                               │   getUser()    │
 *      │                                               │7. Calls service│
 *      │                                               │8. Returns User │
 *      │                                               │   (@Response-  │
 *      │                                               │   Body)        │
 *      │                                               └────────────────┘
 *      │                                                     │
 *      │                                                     ▼
 *      │                                               ┌────────────────┐
 *      │                                               │MessageConverter│
 *      │                                               │                │
 *      │                                               │9. Jackson      │
 *      │                                               │   converts     │
 *      │                                               │   User→JSON    │
 *      │                                               └────────────────┘
 *      │                                                     │
 *      └──────────────────Tomcat flushes bytes───────────────┘
 *                        10. HTTP response sent
 *
 *   Step-by-step (What YOU need to know for interviews):
 *
 *     1. Tomcat receives HTTP request on port 8080
 *     2. Tomcat parses raw bytes → HttpServletRequest & HttpServletResponse
 *     3. Tomcat matches URL pattern → DispatcherServlet (registered for "/")
 *     4. Tomcat calls: DispatcherServlet.service(req, res)
 *     5. DispatcherServlet.doDispatch() starts:
 *        a. Gets HandlerMapping → finds which @Controller method matches
 *           e.g. UserController.getUser() with @GetMapping("/api/users/{id}")
 *        b. Gets HandlerAdapter → prepares method arguments
 *           (@PathVariable, @RequestParam, etc.)
 *        c. Invokes controller method
 *        d. Controller returns: ModelAndView (web app) OR @ResponseBody (REST)
 *        e. If @ResponseBody: MessageConverter serializes (e.g. Jackson → JSON)
 *        f. If ModelAndView: ViewResolver finds JSP → renders HTML
 *     6. Response written to HttpServletResponse
 *     7. Tomcat flushes response bytes to client
 *
 * ───────────────────────────────────────────────────────────────────────
 * 6. HOW TOMCAT WORKS IN SPRING MVC
 * ───────────────────────────────────────────────────────────────────────
 *
 *   Tomcat's role does NOT change. It is still the servlet container.
 *   The ONLY difference is which servlet Tomcat calls:
 *
 *   ┌──────────────────────────────────────────────────────────────────────┐
 *   │  RAW SERVLET                SPRING MVC                              │
 *   │  ───────────                ──────────                              │
 *   │  Tomcat → YourServlet       Tomcat → DispatcherServlet              │
 *   │           .doPost()                    .service() → doDispatch()    │
 *   │                                            → YourController.method()│
 *   │                                                                     │
 *   │  You write:                   You write:                            │
 *   │    @WebServlet("/api")         @Controller                          │
 *   │    class MyServlet             class MyController {                 │
 *   │      doGet(req,res){            @GetMapping("/api")                 │
 *   │        // manual parse          public Response method(){           │
 *   │        // manual JSON             // just business logic            │
 *   │        // manual write         }                                    │
 *   │      }                       }                                      │
 *   └──────────────────────────────────────────────────────────────────────┘
 *
 *   Tomcat still:
 *     ✅ Opens port 8080
 *     ✅ Parses HTTP bytes
 *     ✅ Manages thread pool
 *     ✅ Calls servlet.service() → DispatcherServlet
 *     ✅ Sends response bytes back
 *
 * ───────────────────────────────────────────────────────────────────────
 * 7. ANNOTATIONS IN SPRING MVC
 * ───────────────────────────────────────────────────────────────────────
 *
 *   ┌────────────────────┬──────────────────────────────────────────────────┐
 *   │ Annotation          │ What it does                                    │
 *   ├────────────────────┼──────────────────────────────────────────────────┤
 *   │ @Controller         │ Marks class as web controller (can return View) │
 *   │ @RestController     │ @Controller + @ResponseBody on every method     │
 *   │ @RequestMapping     │ Maps HTTP method + URL to handler method        │
 *   │ @GetMapping         │ Shorthand for @RequestMapping(method=GET)       │
 *   │ @PostMapping        │ Shorthand for @RequestMapping(method=POST)      │
 *   │ @PutMapping         │ Shorthand for @RequestMapping(method=PUT)       │
 *   │ @DeleteMapping      │ Shorthand for @RequestMapping(method=DELETE)    │
 *   │ @PatchMapping       │ Shorthand for @RequestMapping(method=PATCH)     │
 *   │ @RequestParam       │ Binds query parameter to method param           │
 *   │ @PathVariable       │ Binds URI template variable to method param     │
 *   │ @RequestBody        │ Binds HTTP request body to method param (JSON)  │
 *   │ @ResponseBody       │ Writes method return value directly to response │
 *   │ @ModelAttribute     │ Binds form data / adds to Model                 │
 *   │ @SessionAttribute   │ Binds session data to method param             │
 *   │ @RequestHeader      │ Binds HTTP header to method param              │
 *   │ @CookieValue        │ Binds cookie to method param                   │
 *   │ @ExceptionHandler   │ Handles exceptions thrown from controller      │
 *   │ @ControllerAdvice   │ Global exception handler for all controllers   │
 *   │ @CrossOrigin        │ Enables CORS on controller/method              │
 *   │ @InitBinder         │ Customizes data binding (date format, etc.)    │
 *   └────────────────────┴──────────────────────────────────────────────────┘
 *
 * ───────────────────────────────────────────────────────────────────────
 * 8. @Controller vs @RestController
 * ───────────────────────────────────────────────────────────────────────
 *
 *   ┌──────────────┬──────────────────────────────┬────────────────────────┐
 *   │               │ @Controller                  │ @RestController         │
 *   ├──────────────┼──────────────────────────────┼────────────────────────┤
 *   │ View?        │ Returns ModelAndView (JSP)   │ No View, returns data  │
 *   │ Response     │ ViewResolver resolves → HTML  │ MessageConverter→JSON  │
 *   │ @ResponseBody│ Needed on each method         │ Implicit on all methods│
 *   │ Used for     │ Web apps (HTML pages)         │ REST APIs (JSON/XML)   │
 *   │ Example      │ @Controller                    │ @RestController         │
 *   │              │ public String home(Model m){  │ public List<User>      │
 *   │              │   return "home"; // JSP      │   getUsers(){...}      │
 *   │              │ }                              │ }                      │
 *   └──────────────┴──────────────────────────────┴────────────────────────┘
 *
 * ───────────────────────────────────────────────────────────────────────
 * 9. LOOSE COUPLING IN SPRING MVC
 * ───────────────────────────────────────────────────────────────────────
 *
 *   Controller depends on Service INTERFACE, not concrete class.
 *   Spring DI (@Autowired) injects the implementation at runtime.
 *
 *     @RestController
 *     public class UserController {
 *
 *         @Autowired
 *         private UserService userService;     // ← INTERFACE reference
 *
 *         // UserService can be UserServiceImpl, MockUserService, etc.
 *         // Zero code change in controller
 *     }
 *
 *   Benefits:
 *     ✅ Change implementation without changing controller
 *     ✅ Easy unit testing (mock service)
 *     ✅ Each layer is replaceable
 *
 * ───────────────────────────────────────────────────────────────────────
 * 10. PRACTICAL EXAMPLE — Complete Controller
 * ───────────────────────────────────────────────────────────────────────
 *
 *     @RestController
 *     @RequestMapping("/api/users")
 *     public class UserController {
 *
 *         @Autowired
 *         private UserService userService;
 *
 *         @GetMapping
 *         public List<User> getAll() {
 *             return userService.findAll();
 *         }
 *
 *         @GetMapping("/{id}")
 *         public User getById(@PathVariable Long id) {
 *             return userService.findById(id);
 *         }
 *
 *         @PostMapping
 *         public User create(@RequestBody User user) {
 *             return userService.save(user);
 *         }
 *
 *         @PutMapping("/{id}")
 *         public User update(@PathVariable Long id, @RequestBody User user) {
 *             return userService.update(id, user);
 *         }
 *
 *         @DeleteMapping("/{id}")
 *         public void delete(@PathVariable Long id) {
 *             userService.delete(id);
 *         }
 *     }
 *
 * ───────────────────────────────────────────────────────────────────────
 * 11. KEY POINTS FOR INTERVIEWS
 * ───────────────────────────────────────────────────────────────────────
 *
 *   ▸ Spring MVC is built on Servlet API. DispatcherServlet IS a servlet.
 *   ▸ Tomcat does the same work as with raw servlets (port, parse, thread)
 *   ▸ DispatcherServlet is the front controller (single entry point)
 *   ▸ HandlerMapping maps URL → @Controller method
 *   ▸ HandlerAdapter invokes the method (handles argument resolution)
 *   ▸ @Controller → returns View (HTML), @RestController → returns data (JSON)
 *   ▸ MessageConverter converts Java ↔ JSON/XML (Jackson by default)
 *   ▸ ViewResolver resolves logical view name → actual JSP/Thymeleaf
 *   ▸ @Autowired achieves loose coupling via interface-based DI
 *   ▸ Spring MVC is NOT Spring Boot. Spring Boot auto-configures Spring MVC.
 */
public class SpringMVCDeepDrive {
}
