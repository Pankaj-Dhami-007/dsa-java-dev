package springboot_deep_drive.spring_mvc;

/*
 * ═══════════════════════════════════════════════════════════════════════
 *          SPRING MVC COMPONENTS — DEEP DIVE
 * ═══════════════════════════════════════════════════════════════════════
 *
 * ───────────────────────────────────────────────────────────────────────
 * 1. DISPATCHER SERVLET (Front Controller)
 * ───────────────────────────────────────────────────────────────────────
 *
 *   Class: org.springframework.web.servlet.DispatcherServlet
 *   Extends: HttpServlet (YES, it IS a servlet)
 *
 *   Responsibilities:
 *     ✅ Entry point for ALL HTTP requests in Spring MVC
 *     ✅ Receives request from Tomcat (service() → doDispatch())
 *     ✅ Coordinates all other components
 *     ✅ Holds WebApplicationContext (Spring container)
 *
 *   Initialization (init()):
 *
 *     DispatcherServlet.init()
 *       ├── initWebApplicationContext()
 *       │   ├── Creates or gets WebApplicationContext (Spring container)
 *       │   └── Stores it in ServletContext
 *       │
 *       ├── initStrategies(context)    ← Framework components initialized here
 *       │   ├── initMultipartResolver()      → CommonsMultipartResolver
 *       │   ├── initLocaleResolver()          → AcceptHeaderLocaleResolver
 *       │   ├── initThemeResolver()           → FixedThemeResolver
 *       │   ├── initHandlerMappings()         → RequestMappingHandlerMapping
 *       │   ├── initHandlerAdapters()         → RequestMappingHandlerAdapter
 *       │   ├── initHandlerExceptionResolvers()→ ExceptionHandlerExceptionResolver
 *       │   ├── initRequestToViewNameTranslator()
 *       │   └── initViewResolvers()           → InternalResourceViewResolver
 *       └── ...
 *
 *   Service Flow (doDispatch()):
 *
 *     doDispatch(request, response)
 *       ├── 1. multipartResolver.isMultipart() → wrap request if file upload
 *       ├── 2. handlerMapping.getHandler(request) → HandlerExecutionChain
 *       │          (contains Controller method + Interceptors)
 *       ├── 3. handlerAdapter.supports(handler) → checks if adapter can handle
 *       ├── 4. handlerAdapter.handle(request, response, handler)
 *       │       ├── Executes preHandle() interceptors
 *       │       ├── Invokes controller method
 *       │       └── Executes postHandle() interceptors
 *       ├── 5. processDispatchResult(request, response, handler, ModelAndView)
 *       │       ├── If exception → resolveException()
 *       │       ├── If ModelAndView → viewResolver.resolveViewName()
 *       │       └── view.render(model, request, response)
 *       └── 6. triggerAfterCompletion() interceptors
 *
 *   Registration (How DispatcherServlet is registered):
 *
 *     Way 1: web.xml (Traditional)
 *       <web-app>
 *         <servlet>
 *           <servlet-name>dispatcher</servlet-name>
 *           <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
 *           <load-on-startup>1</load-on-startup>
 *         </servlet>
 *         <servlet-mapping>
 *           <servlet-name>dispatcher</servlet-name>
 *           <url-pattern>/</url-pattern>
 *         </servlet-mapping>
 *       </web-app>
 *
 *     Way 2: WebApplicationInitializer (Servlet 3.0+, no XML)
 *       public class AppInitializer implements WebApplicationInitializer {
 *           public void onStartup(ServletContext ctx) {
 *               DispatcherServlet servlet = new DispatcherServlet(webAppContext);
 *               ServletRegistration.Dynamic reg = ctx.addServlet("dispatcher", servlet);
 *               reg.setLoadOnStartup(1);
 *               reg.addMapping("/");
 *           }
 *       }
 *
 *     Way 3: Spring Boot (auto)
 *       DispatcherServletAutoConfiguration auto-registers it for you.
 *
 * ───────────────────────────────────────────────────────────────────────
 * 2. HANDLER MAPPING
 * ───────────────────────────────────────────────────────────────────────
 *
 *   Interface: org.springframework.web.servlet.HandlerMapping
 *   Returns: HandlerExecutionChain (handler method + interceptors)
 *
 *   Popular implementations:
 *
 *     ┌────────────────────────────────┬──────────────────────────────────────┐
 *     │ Implementation                 │ What it does                         │
 *     ├────────────────────────────────┼──────────────────────────────────────┤
 *     │ RequestMappingHandlerMapping   │ Maps @RequestMapping / @GetMapping   │
 *     │                                │ / @PostMapping etc. to handler       │
 *     │                                │ methods. MOST COMMON in modern apps. │
 *     ├────────────────────────────────┼──────────────────────────────────────┤
 *     │ SimpleUrlHandlerMapping        │ Maps URL patterns directly to         │
 *     │                                │ handlers (XML/bean-based config)     │
 *     ├────────────────────────────────┼──────────────────────────────────────┤
 *     │ BeanNameUrlHandlerMapping      │ Maps bean names starting with "/"    │
 *     │                                │ to handler beans                     │
 *     ├────────────────────────────────┼──────────────────────────────────────┤
 *     │ WelcomePageHandlerMapping      │ Maps "/" to welcome page (SpringBoot)│
 *     ├────────────────────────────────┼──────────────────────────────────────┤
 *     │ DefaultServletHandlerMapping   │ Maps to default servlet for static   │
 *     │                                │ resources                            │
 *     └────────────────────────────────┴──────────────────────────────────────┘
 *
 *   How RequestMappingHandlerMapping works:
 *
 *     During startup:
 *       1. Scans all @Controller beans in context
 *       2. Reads @RequestMapping / @GetMapping / @PostMapping on class + method
 *       3. Builds a Map<RequestMappingInfo, HandlerMethod>
 *          ┌──────────────────────────────────────────────────────────┐
 *          │  Map internally:                                         │
 *          │  ┌─────────────────────────┬──────────────────────────┐  │
 *          │  │ Key (RequestMappingInfo) │ Value (HandlerMethod)     │  │
 *          │  ├─────────────────────────┼──────────────────────────┤  │
 *          │  │ GET /api/users           │ UserController.getAll()   │  │
 *          │  │ GET /api/users/{id}      │ UserController.getById() │  │
 *          │  │ POST /api/users          │ UserController.create()   │  │
 *          │  │ PUT /api/users/{id}      │ UserController.update()   │  │
 *          │  │ DELETE /api/users/{id}   │ UserController.delete()   │  │
 *          │  └─────────────────────────┴──────────────────────────┘  │
 *          └──────────────────────────────────────────────────────────┘
 *
 *     At request time:
 *       1. Receives HttpServletRequest
 *       2. Creates RequestMappingInfo from request (method + path)
 *       3. Matches against the map
 *       4. Returns HandlerExecutionChain (HandlerMethod + Interceptors)
 *
 * ───────────────────────────────────────────────────────────────────────
 * 3. HANDLER ADAPTER
 * ───────────────────────────────────────────────────────────────────────
 *
 *   Interface: org.springframework.web.servlet.HandlerAdapter
 *   Responsibility: Actually INVOKES the controller method
 *
 *   Popular implementations:
 *
 *     ┌────────────────────────────────┬──────────────────────────────────────┐
 *     │ Implementation                 │ What it does                         │
 *     ├────────────────────────────────┼──────────────────────────────────────┤
 *     │ RequestMappingHandlerAdapter   │ Handles @RequestMapping methods.     │
 *     │                                │ MOST COMMON.                         │
 *     ├────────────────────────────────┼──────────────────────────────────────┤
 *     │ SimpleControllerHandlerAdapter │ Handles old Controller interface     │
 *     ├────────────────────────────────┼──────────────────────────────────────┤
 *     │ HttpRequestHandlerAdapter      │ Handles HttpRequestHandler           │
 *     ├────────────────────────────────┼──────────────────────────────────────┤
 *     │ SimpleServletHandlerAdapter    │ Handles plain Servlet implementations│
 *     └────────────────────────────────┴──────────────────────────────────────┘
 *
 *   What RequestMappingHandlerAdapter does:
 *
 *     1. Determines method arguments (argument resolvers)
 *         ┌──────────────────────────────────────────────────────────────┐
 *         │ Argument Resolvers (over 30 built-in):                       │
 *         │   @RequestParam, @PathVariable, @RequestBody,               │
 *         │   @RequestHeader, @CookieValue, @SessionAttribute,          │
 *         │   @ModelAttribute, HttpServletRequest, HttpServletResponse,  │
 *         │   Principal, HttpSession, Model, BindingResult, etc.        │
 *         └──────────────────────────────────────────────────────────────┘
 *
 *     2. Resolves each method argument from request
 *        e.g. @PathVariable("id") → extracts from URI
 *        e.g. @RequestBody User → Jackson reads + deserializes body
 *
 *     3. Invokes the controller method via reflection
 *
 *     4. Processes return value (return value handlers)
 *         ┌──────────────────────────────────────────────────────────────┐
 *         │ Return Value Handlers:                                       │
 *         │   @ResponseBody → MessageConverter writes to response        │
 *         │   ModelAndView  → sent to ViewResolver                       │
 *         │   String (view name) → ViewResolver                          │
 *         │   ResponseEntity → status + headers + body                   │
 *         │   void          → nothing (handled by response already)      │
 *         └──────────────────────────────────────────────────────────────┘
 *
 * ───────────────────────────────────────────────────────────────────────
 * 4. CONTROLLER (Your Code)
 * ───────────────────────────────────────────────────────────────────────
 *
 *   This is what YOU write. It's the "C" in MVC.
 *
 *   Responsibilities:
 *     ✅ Receive request parameters
 *     ✅ Call service layer (business logic)
 *     ✅ Return data (REST) or view name (web app)
 *
 *   Typical structure:
 *
 *     @RestController
 *     @RequestMapping("/api/users")
 *     public class UserController {
 *
 *         @Autowired
 *         private UserService userService;   // ← loose coupling via DI
 *
 *         @GetMapping("/{id}")
 *         public ResponseEntity<User> getById(@PathVariable Long id) {
 *             User user = userService.findById(id);
 *             return ResponseEntity.ok(user);
 *         }
 *
 *         @PostMapping
 *         public ResponseEntity<User> create(@Valid @RequestBody User user) {
 *             User saved = userService.save(user);
 *             return ResponseEntity.created(...).body(saved);
 *         }
 *     }
 *
 * ───────────────────────────────────────────────────────────────────────
 * 5. VIEW RESOLVER
 * ───────────────────────────────────────────────────────────────────────
 *
 *   Interface: org.springframework.web.servlet.ViewResolver
 *   Responsibility: Resolves logical view name → actual View implementation
 *
 *   ┌────────────────────────────┬──────────────────────────────────────────┐
 *   │ Implementation             │ Resolves to                              │
 *   ├────────────────────────────┼──────────────────────────────────────────┤
 *   │ InternalResourceViewResolver│ JSP files (/WEB-INF/views/home.jsp)      │
 *   │ ThymeleafViewResolver      │ Thymeleaf templates (home.html)           │
 *   │ FreeMarkerViewResolver     │ FreeMarker templates                      │
 *   │ ContentNegotiatingViewResolver│ Based on Accept header → JSON/XML/HTML │
 *   └────────────────────────────┴──────────────────────────────────────────┘
 *
 *   Example — InternalResourceViewResolver:
 *
 *     @Bean
 *     public ViewResolver viewResolver() {
 *         InternalResourceViewResolver resolver = new InternalResourceViewResolver();
 *         resolver.setPrefix("/WEB-INF/views/");
 *         resolver.setSuffix(".jsp");
 *         return resolver;
 *     }
 *
 *     // Controller returns "home" → resolver resolves to:
 *     // /WEB-INF/views/home.jsp
 *
 * ───────────────────────────────────────────────────────────────────────
 * 6. MODEL, MODELANDVIEW, VIEW
 * ───────────────────────────────────────────────────────────────────────
 *
 *   Model:
 *     Interface: org.springframework.ui.Model
 *     Purpose: Carries data from Controller → View
 *
 *     @GetMapping("/user/{id}")
 *     public String getUser(@PathVariable Long id, Model model) {
 *         model.addAttribute("user", userService.findById(id));
 *         return "userProfile";  // → userProfile.jsp gets "user" attribute
 *     }
 *
 *   ModelAndView:
 *     Class: org.springframework.web.servlet.ModelAndView
 *     Combines model data + view name in one return object
 *
 *     @GetMapping("/user/{id}")
 *     public ModelAndView getUser(@PathVariable Long id) {
 *         ModelAndView mv = new ModelAndView("userProfile");
 *         mv.addObject("user", userService.findById(id));
 *         return mv;
 *     }
 *
 *   View:
 *     Interface: org.springframework.web.servlet.View
 *     Responsibility: Renders the actual HTML (or JSON/XML)
 *     Implementations: JstlView, ThymeleafView, MappingJackson2JsonView
 *
 * ───────────────────────────────────────────────────────────────────────
 * 7. HANDLER INTERCEPTOR
 * ───────────────────────────────────────────────────────────────────────
 *
 *   Interface: org.springframework.web.servlet.HandlerInterceptor
 *   Purpose: Intercept requests BEFORE and AFTER controller execution
 *
 *   ┌────────────────────────────────────────────────────────────────────┐
 *   │  Methods:                                                          │
 *   │                                                                    │
 *   │  preHandle(request, response, handler)                             │
 *   │    → Called BEFORE controller. Return true to continue,            │
 *   │      false to block the request.                                   │
 *   │    → Used for: Authentication, logging, rate limiting              │
 *   │                                                                    │
 *   │  postHandle(request, response, handler, modelAndView)              │
 *   │    → Called AFTER controller but BEFORE view rendering             │
 *   │    → Used for: Adding common model attributes, modifying response  │
 *   │                                                                    │
 *   │  afterCompletion(request, response, handler, exception)            │
 *   │    → Called AFTER view rendering completes                         │
 *   │    → Used for: Cleanup (closing resources, logging)                │
 *   └────────────────────────────────────────────────────────────────────┘
 *
 *   Example:
 *     public class AuthInterceptor implements HandlerInterceptor {
 *         @Override
 *         public boolean preHandle(HttpServletRequest req, ...) {
 *             if (req.getSession().getAttribute("user") == null) {
 *                 res.sendRedirect("/login");
 *                 return false;
 *             }
 *             return true;
 *         }
 *     }
 *
 * ───────────────────────────────────────────────────────────────────────
 * 8. MESSAGE CONVERTER (For REST APIs)
 * ───────────────────────────────────────────────────────────────────────
 *
 *   Interface: org.springframework.http.converter.HttpMessageConverter
 *   Purpose: Convert Java objects ↔ HTTP request/response bodies
 *
 *   ┌────────────────────────────────┬──────────────────────────────────┐
 *   │ Converter                      │ Converts                         │
 *   ├────────────────────────────────┼──────────────────────────────────┤
 *   │ MappingJackson2HttpMessageConverter│ Java ↔ JSON (Jackson)        │
 *   │ MappingJackson2XmlHttpMessageConverter│ Java ↔ XML (Jackson XML)  │
 *   │ StringHttpMessageConverter     │ String ↔ text/plain              │
 *   │ FormHttpMessageConverter       │ MultiValueMap ↔ form data        │
 *   │ ResourceHttpMessageConverter   │ Resource ↔ binary stream         │
 *   │ ByteArrayHttpMessageConverter  │ byte[] ↔ application/octet-stream│
 *   └────────────────────────────────┴──────────────────────────────────┘
 *
 *   How it works with @RequestBody / @ResponseBody:
 *
 *     @PostMapping
 *     public User create(@RequestBody User user) {
 *         // 1. Request comes with JSON body: {"name":"John"}
 *         // 2. DispatcherServlet selects MappingJackson2HttpMessageConverter
 *         // 3. Converter reads InputStream → deserializes to User object
 *         // 4. Your controller receives populated User object
 *         return userService.save(user);
 *         // 5. @ResponseBody triggers return value handler
 *         // 6. MappingJackson2HttpMessageConverter serializes User → JSON
 *         // 7. JSON written to HttpServletResponse
 *     }
 *
 * ───────────────────────────────────────────────────────────────────────
 * 9. EXCEPTION RESOLVER
 * ───────────────────────────────────────────────────────────────────────
 *
 *   Interface: org.springframework.web.servlet.HandlerExceptionResolver
 *   Purpose: Handle exceptions thrown during request processing
 *
 *   ┌────────────────────────────────┬──────────────────────────────────┐
 *   │ Implementation                 │ Behavior                         │
 *   ├────────────────────────────────┼──────────────────────────────────┤
 *   │ ExceptionHandlerExceptionResolver│ Handles @ExceptionHandler      │
 *   │ DefaultHandlerExceptionResolver│ Maps standard exceptions to      │
 *   │                                │ HTTP status codes                │
 *   │ ResponseStatusExceptionResolver│ Handles @ResponseStatus          │
 *   │ SimpleMappingExceptionResolver │ Maps exception class → view name │
 *   └────────────────────────────────┴──────────────────────────────────┘
 *
 *   Modern approach: @ControllerAdvice + @ExceptionHandler
 *
 *     @ControllerAdvice
 *     public class GlobalExceptionHandler {
 *
 *         @ExceptionHandler(UserNotFoundException.class)
 *         public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
 *             return ResponseEntity.status(404).body(new ErrorResponse(ex.getMessage()));
 *         }
 *     }
 *
 * ───────────────────────────────────────────────────────────────────────
 * 10. COMPONENT INTERACTION SUMMARY
 * ───────────────────────────────────────────────────────────────────────
 *
 *   ┌─────────────────────────────────────────────────────────────────────────┐
 *   │                                                                         │
 *   │   Request ──▶ DispatcherServlet ──▶ HandlerMapping                     │
 *   │                                         │                              │
 *   │                                         ▼                              │
 *   │                                  HandlerExecutionChain                  │
 *   │                                  (HandlerMethod + Interceptors)         │
 *   │                                         │                              │
 *   │                                         ▼                              │
 *   │                                  HandlerAdapter                         │
 *   │                                         │                              │
 *   │                           ┌─────────────┴─────────────┐               │
 *   │                           ▼                           ▼               │
 *   │                    Argument Resolvers          ReturnValueHandlers     │
 *   │                    (@PathVariable,             (@ResponseBody,          │
 *   │                     @RequestBody,               ResponseEntity,         │
 *   │                     @RequestParam)              ModelAndView)          │
 *   │                           │                           │               │
 *   │                           ▼                           │               │
 *   │                    Controller Method ◀────────────────┘               │
 *   │                           │                                          │
 *   │                           ▼                                          │
 *   │                    Service Layer                                      │
 *   │                           │                                          │
 *   │                           ▼                                          │
 *   │                    Return Value                                       │
 *   │                           │                                          │
 *   │               ┌───────────┴───────────┐                              │
 *   │               ▼                       ▼                              │
 *   │         @ResponseBody           ModelAndView                          │
 *   │               │                       │                              │
 *   │               ▼                       ▼                              │
 *   │      MessageConverter          ViewResolver                          │
 *   │      (Jackson → JSON)          (JSP/Thymeleaf)                       │
 *   │               │                       │                              │
 *   │               └───────────┬───────────┘                              │
 *   │                           ▼                                          │
 *   │                     Response Body                                     │
 *   │                                                                       │
 *   └─────────────────────────────────────────────────────────────────────────┘
 */
public class SpringMVCComponents {
}
