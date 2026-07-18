package springboot_deep_drive.spring_mvc;

/*
 * ═══════════════════════════════════════════════════════════════════════
 *              SPRING MVC — INTERVIEW QUESTIONS & ANSWERS
 * ═══════════════════════════════════════════════════════════════════════
 *
 * ──── Q1: What is Spring MVC? ─────────────────────────────────────────
 *
 *   Spring MVC is a web framework built on the Servlet API that implements
 *   the MVC (Model-View-Controller) design pattern. It provides a clean
 *   separation between presentation (View), business logic (Controller),
 *   and data (Model).
 *
 * ──── Q2: What is DispatcherServlet? ──────────────────────────────────
 *
 *   DispatcherServlet is the front controller in Spring MVC. It IS a
 *   servlet (extends HttpServlet). It receives ALL incoming HTTP requests
 *   and delegates them to appropriate controller methods via HandlerMapping
 *   and HandlerAdapter.
 *
 * ──── Q3: How does Spring MVC process a request? (Full Flow) ──────────
 *
 *   1. Tomcat receives HTTP request, parses bytes → HttpServletRequest/Response
 *   2. Tomcat calls DispatcherServlet.service(req, res)
 *   3. DispatcherServlet.doDispatch() starts:
 *      a. HandlerMapping finds matching @Controller method
 *      b. HandlerAdapter invokes the method (resolves args)
 *      c. Controller executes business logic, returns result
 *      d. If @ResponseBody → MessageConverter serializes (JSON)
 *      e. If ModelAndView → ViewResolver resolves JSP → renders HTML
 *   4. Response sent back through Tomcat → Client
 *
 * ──── Q4: What is the role of the Servlet API in Spring MVC? ──────────
 *
 *   Spring MVC is built ON TOP of the Servlet API. DispatcherServlet is
 *   itself an HttpServlet. Tomcat (servlet container) still:
 *   listens on port, parses HTTP, manages thread pool, calls service().
 *   Spring MVC adds abstraction on top: @RequestMapping, @RequestBody,
 *   ViewResolver, etc.
 *
 * ──── Q5: Difference between @Controller and @RestController? ─────────
 *
 *   @Controller: Returns ModelAndView (JSP/Thymeleaf). Needs @ResponseBody
 *   on each method to return data directly.
 *
 *   @RestController: @Controller + @ResponseBody on all methods. Used for
 *   REST APIs. Returns data (JSON/XML) directly, not views.
 *
 * ──── Q6: What is @RequestMapping? What are its shortcut annotations? ──
 *
 *   @RequestMapping maps HTTP requests to handler methods.
 *   Shortcuts:
 *     @GetMapping   → @RequestMapping(method = GET)
 *     @PostMapping  → @RequestMapping(method = POST)
 *     @PutMapping   → @RequestMapping(method = PUT)
 *     @DeleteMapping→ @RequestMapping(method = DELETE)
 *     @PatchMapping → @RequestMapping(method = PATCH)
 *
 * ──── Q7: What is HandlerMapping? ──────────────────────────────────────
 *
 *   HandlerMapping maps incoming HTTP requests to handler methods
 *   (controller methods). The default implementation is
 *   RequestMappingHandlerMapping which scans @RequestMapping annotations.
 *   Returns HandlerExecutionChain (handler method + interceptors).
 *
 * ──── Q8: What is HandlerAdapter? ──────────────────────────────────────
 *
 *   HandlerAdapter invokes the controller method. RequestMappingHandlerAdapter
 *   is the default. It resolves method arguments (@PathVariable, @RequestParam,
 *   @RequestBody, etc.), invokes the method via reflection, and processes
 *   the return value (@ResponseBody → MessageConverter; ModelAndView →
 *   ViewResolver).
 *
 * ──── Q9: What is ViewResolver? ────────────────────────────────────────
 *
 *   ViewResolver resolves logical view names (returned by controller) to
 *   actual View implementations (JSP, Thymeleaf templates).
 *   Example: "home" → /WEB-INF/views/home.jsp (with InternalResourceViewResolver)
 *
 * ──── Q10: What is @RequestBody and @ResponseBody? ─────────────────────
 *
 *   @RequestBody: Binds HTTP request body to a Java object. Spring uses
 *   HttpMessageConverter (e.g., Jackson) to deserialize JSON/XML.
 *
 *   @ResponseBody: Writes Java object directly to HTTP response body.
 *   Spring uses HttpMessageConverter to serialize to JSON/XML.
 *
 * ──── Q11: What is @PathVariable and @RequestParam? ────────────────────
 *
 *   @PathVariable: Extracts value from URI template. URL: /users/5
 *     @GetMapping("/users/{id}")
 *     public User get(@PathVariable Long id)  // id = 5
 *
 *   @RequestParam: Extracts query parameter. URL: /users?name=John
 *     @GetMapping("/users")
 *     public List<User> get(@RequestParam String name)
 *
 * ──── Q12: What is @ControllerAdvice? ──────────────────────────────────
 *
 *   @ControllerAdvice is a global exception handler for all controllers.
 *   It contains @ExceptionHandler methods that catch exceptions thrown
 *   from any controller and return appropriate responses.
 *
 * ──── Q13: What is HandlerInterceptor? ─────────────────────────────────
 *
 *   HandlerInterceptor intercepts requests before and after controller
 *   execution. Three methods:
 *     preHandle()    — before controller (auth, logging)
 *     postHandle()   — after controller, before view render
 *     afterCompletion()— after complete request (cleanup)
 *
 * ──── Q14: Difference between Interceptor and Filter? ──────────────────
 *
 *   Filter: Servlet API (javax/jakarta.servlet). Works before/after any
 *   servlet. Cannot access Spring context directly.
 *
 *   Interceptor: Spring MVC. Works within DispatcherServlet, has access
 *   to handler method and ModelAndView. Can access Spring beans.
 *
 * ──── Q15: What is the root cause of "No mapping found for HTTP request"?─
 *
 *   DispatcherServlet cannot find a @RequestMapping that matches the
 *   incoming URL + HTTP method. Check:
 *   - URL pattern in the annotation
 *   - HTTP method (GET vs POST mismatch)
 *   - Controller is scanned by component-scanning
 *
 * ──── Q16: How does Spring MVC handle JSON? ────────────────────────────
 *
 *   Through HttpMessageConverter. When @RequestBody or @ResponseBody is
 *   used, MappingJackson2HttpMessageConverter converts between Java objects
 *   and JSON using Jackson library. Spring Boot auto-configures this.
 *
 * ──── Q17: What is Content Negotiation in Spring MVC? ──────────────────
 *
 *   Content negotiation allows the same endpoint to return different
 *   formats (JSON, XML, HTML) based on:
 *   - Accept header in request
 *   - URL suffix (.json, .xml)
 *   - Request parameter (?format=json)
 *
 * ──── Q18: How to configure Spring MVC without Spring Boot? ────────────
 *
 *   1. Add DispatcherServlet to web.xml or via WebApplicationInitializer
 *   2. Create WebApplicationContext with @Configuration + @ComponentScan
 *   3. Enable Spring MVC with @EnableWebMvc
 *   4. Configure ViewResolver, MessageConverter, etc. as @Bean
 *
 * ──── Q19: What is @EnableWebMvc? ──────────────────────────────────────
 *
 *   @EnableWebMvc imports Spring MVC's default configuration (DelegatingWebMvcConfiguration).
 *   It registers HandlerMapping, HandlerAdapter, MessageConverter, etc.
 *   Equivalent to <mvc:annotation-driven> in XML.
 *
 * ──── Q20: How does Spring Boot auto-configure Spring MVC? ─────────────
 *
 *   Spring Boot's spring-boot-starter-web includes spring-webmvc.
 *   Auto-configuration (WebMvcAutoConfiguration) automatically:
 *   - Registers DispatcherServlet
 *   - Configures Jackson for JSON
 *   - Registers default HandlerMapping, HandlerAdapter
 *   - Configures static resource handling
 *   - Registers error page (/error)
 *   All with sensible defaults (customizable via application.properties).
 *
 * ──── Q21: What is the difference between ModelAndView and Model? ──────
 *
 *   Model: Interface, add attributes via addAttribute(). View name returned
 *   as String.
 *
 *   ModelAndView: Class, combines both model data AND view name in one object.
 *
 * ──── Q22: Can we have multiple DispatcherServlets? ────────────────────
 *
 *   Yes. You can register multiple DispatcherServlets with different
 *   url-patterns and different WebApplicationContexts (e.g., one for
 *   web UI, one for API).
 *
 * ──── Q23: What is the role of WebApplicationContext? ──────────────────
 *
 *   WebApplicationContext is the Spring IoC container specific to web
 *   applications. It extends ApplicationContext and has access to
 *   ServletContext. DispatcherServlet holds a reference to it and uses
 *   it to resolve beans (controllers, services, etc.).
 *
 * ──── Q24: What happens if @Controller method returns null? ────────────
 *
 *   If null is returned and no @ResponseBody: the handler adapter treats
 *   it as "no ModelAndView" — the request is considered already handled
 *   (e.g., response already written via HttpServletResponse directly).
 *
 * ──── Q25: How does @ResponseEntity differ from @ResponseBody? ─────────
 *
 *   @ResponseBody: writes return value directly to response body.
 *   @ResponseEntity<T>: wrapper that also allows setting status code
 *   and headers along with the body.
 *
 *     @ResponseBody User       → 200 + JSON body
 *     ResponseEntity<User>     → Custom status + headers + JSON body
 */
public class SpringMVCInterviewQA {
}
