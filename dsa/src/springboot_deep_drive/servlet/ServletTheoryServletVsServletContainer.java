package springboot_deep_drive.servlet;

/*
 * ══════════════════════════════════════════════════════════════════════
 *      SERVLET & SERVLET CONTAINER — COMPLETE DEVELOPER'S GUIDE
 * ══════════════════════════════════════════════════════════════════════
 *
 * ─────────────────────────────────────────────────────────────────────
 * 1. WHAT IS A SERVLET? — Theory
 * ─────────────────────────────────────────────────────────────────────
 *
 * 1.1 Definition
 *
 *   A Servlet is a Java class that runs on a server and handles client
 *   requests. It is the server-side component in Java's web development
 *   model. It follows the "request-response" programming model.
 *
 *        ┌──────────┐    HTTP Request     ┌──────────────┐
 *        │ Browser  │ ──────────────────▶  │   Servlet    │
 *        │ / curl   │                      │  (Java Class)│
 *        │          │ ◀──────────────────  │              │
 *        └──────────┘    HTTP Response     └──────────────┘
 *                                              │
 *                                              ├── Reads input (form, JSON, etc.)
 *                                              ├── Processes (business logic, DB)
 *                                              └── Writes output (HTML, JSON, etc.)
 *
 * 1.2 What problem does Servlet solve?
 *
 *   Before Servlets, CGI (Common Gateway Interface) was used.
 *   CGI spawned a NEW PROCESS for EACH request → slow, resource-heavy.
 *   Servlets run inside a JVM → ONE process, MANY threads → fast, efficient.
 *
 *   ┌─────────────────────────────────────────────────────────────────┐
 *   │  CGI (Old Way):                                                │
 *   │    Request 1  →  New Process (fork + exec) → Response          │
 *   │    Request 2  →  New Process (fork + exec) → Response          │
 *   │    ❌ Heavy, slow, OS-level overhead per request                │
 *   │                                                                 │
 *   │  Servlet (Java Way):                                            │
 *   │    Request 1  →  Thread from pool → Servlet.service() → Res    │
 *   │    Request 2  →  Thread from pool → Servlet.service() → Res    │
 *   │    ✅ Lightweight, fast, shared JVM resources                   │
 *   └─────────────────────────────────────────────────────────────────┘
 *
 * 1.3 The Servlet Interface (javax.servlet.Servlet)
 *
 *   Every servlet must implement this interface (directly or via HttpServlet):
 *
 *     ┌─────────────────────────────────────────────────────────────┐
 *     │  public interface Servlet {                                 │
 *     │      void init(ServletConfig config);       // called once  │
 *     │      void service(ServletRequest req,                       │
 *     │                   ServletResponse res);    // per request   │
 *     │      void destroy();                       // called once   │
 *     │      ServletConfig getServletConfig();                      │
 *     │      String getServletInfo();                               │
 *     │  }                                                          │
 *     └─────────────────────────────────────────────────────────────┘
 *
 *   ┌──────────────┬────────────┬──────────────────────────────────┐
 *   │ Method        │ Called     │ What YOU do in it                │
 *   ├──────────────┼────────────┼──────────────────────────────────┤
 *   │ init()        │ 1x on load │ Open DB connections, load config │
 *   │ service()     │ per request│ Handle the request               │
 *   │ destroy()     │ 1x on stop │ Close connections, cleanup       │
 *   └──────────────┴────────────┴──────────────────────────────────┘
 *
 * 1.4 HttpServlet — What YOU actually extend
 *
 *   Rather than implementing Servlet directly, you extend HttpServlet
 *   which provides HTTP-specific behavior:
 *
 *     ┌─────────────────────────────────────────────────────────────┐
 *     │  public abstract class HttpServlet extends GenericServlet   │
 *     │      implements Servlet {                                   │
 *     │                                                             │
 *     │      // service() reads the HTTP method and routes:         │
 *     │      protected void service(HttpServletRequest req,         │
 *     │                            HttpServletResponse res)         │
 *     │              throws IOException, ServletException {         │
 *     │          String method = req.getMethod();                   │
 *     │          if (method.equals("GET"))     doGet(req, res);     │
 *     │          if (method.equals("POST"))    doPost(req, res);    │
 *     │          if (method.equals("PUT"))     doPut(req, res);     │
 *     │          if (method.equals("DELETE"))  doDelete(req, res);  │
 *     │          // ... HEAD, OPTIONS, TRACE, etc.                  │
 *     │      }                                                      │
 *     │  }                                                          │
 *     └─────────────────────────────────────────────────────────────┘
 *
 *   HttpServletRequest & HttpServletResponse add HTTP-specific features:
 *
 *     HttpServletRequest                          HttpServletResponse
 *     ──────────────────────                      ───────────────────────
 *     getMethod()          → "GET"/"POST"          setStatus(200)
 *     getRequestURI()      → "/api/users"          setHeader("X-Custom","val")
 *     getHeader("Host")    → "localhost:8080"      setContentType("application/json")
 *     getParameter("id")   → "5"                   setContentLength(100)
 *     getSession()         → HttpSession           getWriter()      → PrintWriter
 *     getReader()          → BufferedReader        getOutputStream() → ServletOutputStream
 *     getCookies()         → Cookie[]
 *
 * 1.5 Example — Full Servlet
 *
 *   @WebServlet("/api/users")
 *   public class UserServlet extends HttpServlet {
 *
 *       private UserService userService;
 *
 *       @Override
 *       public void init() {
 *           userService = new UserService();  // setup once
 *       }
 *
 *       @Override
 *       protected void doGet(HttpServletRequest req, HttpServletResponse res)
 *               throws IOException {
 *           res.setContentType("application/json");
 *           List<User> users = userService.findAll();
 *           new ObjectMapper().writeValue(res.getWriter(), users);
 *       }
 *
 *       @Override
 *       protected void doPost(HttpServletRequest req, HttpServletResponse res)
 *               throws IOException {
 *           String json = req.getReader().lines().collect(Collectors.joining());
 *           User user = new ObjectMapper().readValue(json, User.class);
 *           User saved = userService.save(user);
 *           res.setStatus(201);
 *           new ObjectMapper().writeValue(res.getWriter(), saved);
 *       }
 *
 *       @Override
 *       public void destroy() {
 *           userService.cleanup();  // cleanup once
 *       }
 *   }
 *
 * 1.6 ServletConfig vs ServletContext
 *
 *   ┌──────────────────────────────────────────────────────────────────────────┐
 *   │  ServletConfig (per servlet)         ServletContext (per web app)        │
 *   │  ──────────────────────────          ────────────────────────────        │
 *   │  One per servlet                     One per web application             │
 *   │  Init params from web.xml:           Shared across all servlets:         │
 *   │    <init-param>                        <context-param>                   │
 *   │      <param-name>threshold</>            <param-name>dbUrl</>            │
 *   │      <param-value>100</>                 <param-value>jdbc:mysql://</>    │
 *   │    </init-param>                       </context-param>                  │
 *   │  getInitParameter("threshold")        getInitParameter("dbUrl")          │
 *   │  Only that servlet can access         All servlets in the same WAR can   │
 *   └──────────────────────────────────────────────────────────────────────────┘
 *
 * 1.7 RequestDispatcher — Forward vs Include
 *
 *   You can delegate from one servlet to another (or to a JSP):
 *
 *     // Forward: control passes to another servlet/JSP (server-side redirect)
 *     req.getRequestDispatcher("/WEB-INF/views/user.jsp").forward(req, res);
 *
 *     // Include: output of another servlet is included in current response
 *     req.getRequestDispatcher("/header").include(req, res);
 *
 *   Forward vs SendRedirect:
 *     ┌────────────┬──────────────────────────┬────────────────────────────┐
 *     │            │ forward()                 │ sendRedirect()              │
 *     ├────────────┼──────────────────────────┼────────────────────────────┤
 *     │ Where      │ Server-side (internal)    │ Client-side (browser does) │
 *     │ URL        │ Stays same                │ Changes in browser          │
 *     │ Speed      │ Fast (no network round)   │ Slower (2 requests)         │
 *     │ Use when   │ Internal routing          │ Redirect to another app    │
 *     └────────────┴──────────────────────────┴────────────────────────────┘
 *
 * 1.8 Registering Servlets — Two Ways
 *
 *   Way 1: Annotation (Servlet 3.0+, most common today)
 *     @WebServlet(name = "UserServlet", urlPatterns = "/api/users")
 *     @WebInitParam(name = "threshold", value = "100")
 *
 *   Way 2: web.xml (older way)
 *     <servlet>
 *       <servlet-name>UserServlet</servlet-name>
 *       <servlet-class>com.app.UserServlet</servlet-class>
 *       <init-param>
 *         <param-name>threshold</param-name>
 *         <param-value>100</param-value>
 *       </init-param>
 *     </servlet>
 *     <servlet-mapping>
 *       <servlet-name>UserServlet</servlet-name>
 *       <url-pattern>/api/users</url-pattern>
 *     </servlet-mapping>
 *
 * 1.9 Thread Safety in Servlets
 *
 *   IMPORTANT: One servlet instance serves ALL requests.
 *   If multiple requests arrive at the same time, doGet/doPost runs
 *   concurrently on different threads.
 *
 *     ┌────────────────────────────────────────────────────────┐
 *     │  Thread-1 → doPost(req1, res1) {                      │
 *     │      user.setName("Alice");                            │
 *     │      // ❌ Thread-2 might change name to "Bob" here   │
 *     │      db.save(user);  // saves "Bob" in wrong record   │
 *     │  }                                                     │
 *     │                                                         │
 *     │  Thread-2 → doPost(req2, res2) {                      │
 *     │      user.setName("Bob");                              │
 *     │      db.save(user);                                    │
 *     │  }                                                     │
 *     └────────────────────────────────────────────────────────┘
 *
 *   ✅ Solutions:
 *     • Avoid instance variables — use local variables in doGet/doPost
 *     • If you MUST use instance variables, synchronize or use thread-safe types
 *     • Better: design servlets to be stateless (no mutable instance fields)
 *
 * 1.10 Key Classes YOU use from javax.servlet.http
 *
 *     ┌─────────────────┬──────────────────────────────────────────────────┐
 *     │ Class            │ What it does                                    │
 *     ├─────────────────┼──────────────────────────────────────────────────┤
 *     │ HttpServlet      │ Base class you extend                           │
 *     │ HttpServletRequest│ Read request data (method, URI, headers, body)  │
 *     │ HttpServletResponse│ Write response (status, headers, body)         │
 *     │ HttpSession      │ Session data across requests (login state, etc) │
 *     │ Cookie           │ HTTP cookies                                    │
 *     │ HttpServletMapping │ Servlet mapping info (Servlet 4.0)            │
 *     └─────────────────┴──────────────────────────────────────────────────┘
 *
 * ─────────────────────────────────────────────────────────────────────
 * 2. WHAT IS A SERVLET CONTAINER? — Theory
 * ─────────────────────────────────────────────────────────────────────
 *
 * 2.1 Definition (Simple Words)
 *
 *   A Servlet Container is the runtime engine that hosts and runs servlets.
 *   It is the "operating system" for your servlet code.
 *
 *   Real-world analogy:
 *     ┌────────────────────────────────────────────────────────────────┐
 *     │  You = Chef who cooks dishes (servlets)                        │
 *     │  Container = Restaurant kitchen (Tomcat) that:                 │
 *     │    • Provides the stove, oven, utensils (port, threads, API)   │
 *     │    • Takes orders from customers (HTTP requests)               │
 *     │    • Routes orders to the right chef (maps URL → servlet)      │
 *     │    • Serves the finished dish to customer (sends response)     │
 *     │    • Cleans up after each order (thread pool management)       │
 *     └────────────────────────────────────────────────────────────────┘
 *
 * 2.2 What problem does the Container solve?
 *
 *   Without a container, you would have to write ALL of this yourself:
 *     ❌ ServerSocket to listen on a port
 *     ❌ Parse raw HTTP bytes manually
 *     ❌ Manage threads (create, destroy, pool)
 *     ❌ Map URLs to handlers
 *     ❌ Manage session cookies
 *     ❌ Handle security (auth, encryption)
 *     ❌ Manage class loading, library isolation
 *     ❌ Handle file uploads, compression, keep-alive
 *
 *   Container gives you all of this FOR FREE. You just write servlets.
 *
 * 2.3 Container's Full Responsibility List
 *
 *   ┌─────────────────────────────────────────────────────────────────────┐
 *   │  NETWORK COMMUNICATION                                              │
 *   │  ├── Opens ServerSocket on configured port (default 8080)           │
 *   │  ├── Accepts TCP connections (handles 3-way handshake)              │
 *   │  ├── Supports HTTP/1.1, HTTP/2, HTTPS (SSL/TLS)                    │
 *   │  ├── Handles keep-alive, chunked encoding, compression (gzip)      │
 *   │  └── Connection pooling and timeout management                     │
 *   │                                                                     │
 *   │  HTTP PROTOCOL HANDLING                                             │
 *   │  ├── Parses raw HTTP bytes: request line, headers, body             │
 *   │  ├── Handles different content types (JSON, form, multipart)        │
 *   │  ├── Handles character encoding (UTF-8, ISO-8859-1, etc.)          │
 *   │  └── Builds HttpServletRequest / HttpServletResponse objects       │
 *   │                                                                     │
 *   │  SERVLET LIFECYCLE MANAGEMENT                                       │
 *   │  ├── Discovers servlets (@WebServlet or web.xml)                   │
 *   │  ├── Loads servlet class via Class.forName()                       │
 *   │  ├── Instantiates: new UserServlet()                               │
 *   │  ├── Calls init(ServletConfig) — once                              │
 *   │  ├── For each request: calls service(req, res)                     │
 *   │  └── On shutdown: calls destroy() — once                           │
 *   │                                                                     │
 *   │  THREAD & CONCURRENCY MANAGEMENT                                   │
 *   │  ├── Maintains a thread pool (Tomcat default: 200 threads)         │
 *   │  ├── Each HTTP request gets ONE thread from the pool               │
 *   │  ├── Multiple simultaneous requests = multiple threads             │
 *   │  └── Thread returned to pool after response is sent                │
 *   │                                                                     │
 *   │  URL ROUTING (MAPPING)                                             │
 *   │  ├── Matches incoming URI to servlet patterns                      │
 *   │  ├── Strips context path: /myapp/api/users → /api/users            │
 *   │  ├── Wildcard mapping: /api/* → UserServlet                        │
 *   │  ├── Default servlet: / → serves static files                     │
 *   │  └── Exact match has priority over wildcard                        │
 *   │                                                                     │
 *   │  SESSION MANAGEMENT                                                 │
 *   │  ├── Creates HttpSession on first request (getSession())           │
 *   │  ├── Identifies returning users via JSESSIONID cookie              │
 *   │  ├── Stores session attributes: setAttribute / getAttribute        │
 *   │  ├── Session timeout & invalidation (default 30 min)               │
 *   │  └── Session persistence across restarts (optional)                │
 *   │                                                                     │
 *   │  SECURITY                                                           │
 *   │  ├── Declarative security (web.xml <security-constraint>)          │
 *   │  │    → "Only /admin/* for ADMIN role"                             │
 *   │  ├── Programmatic security (req.isUserInRole("ADMIN"))              │
 *   │  ├── Authentication: BASIC, DIGEST, FORM, CLIENT-CERT              │
 *   │  ├── SSL/TLS (HTTPS via <Connector port="8443" .../>)              │
 *   │  └── Realm-based user/role storage (memory, DB, LDAP)              │
 *   │                                                                     │
 *   │  CLASSLOADING & ISOLATION                                           │
 *   │  ├── Each web app (WAR) gets its own ClassLoader                   │
 *   │  ├── WEB-INF/classes loaded first                                  │
 *   │  ├── WEB-INF/lib/*.jar loaded next                                 │
 *   │  ├── Container shared libs loaded last                             │
 *   │  └── Apps CANNOT see each other's classes (isolation)             │
 *   │                                                                     │
 *   │  STATIC RESOURCE SERVING                                            │
 *   │  ├── Serves HTML, CSS, JS, images from the WAR root               │
 *   │  ├── DefaultServlet handles static files                           │
 *   │  └── You can configure welcome files (index.html, etc.)            │
 *   │                                                                     │
 *   │  ERROR HANDLING                                                     │
 *   │  ├── Catches unhandled exceptions from servlets                    │
 *   │  ├── Sends configurable error pages (500, 404, etc.)               │
 *   │  └── <error-page> configuration in web.xml                         │
 *   └─────────────────────────────────────────────────────────────────────┘
 *
 * 2.4 Popular Servlet Containers
 *
 *   ┌────────────┬──────────────┬──────────────┬────────────────────────────┐
 *   │ Container   │ Company       │ Embedded?     │ Used In                    │
 *   ├────────────┼──────────────┼──────────────┼────────────────────────────┤
 *   │ Tomcat      │ Apache        │ ✅ Spring Boot│ Most common, reference     │
 *   │ Jetty       │ Eclipse       │ ✅ Spring Boot│ Lightweight, embedded      │
 *   │ Undertow    │ JBoss/Red Hat │ ✅ Spring Boot│ High performance           │
 *   │ GlassFish   │ Oracle        │ ❌            │ Full Java EE server        │
 *   │ WildFly     │ JBoss/Red Hat │ ❌            │ Full Jakarta EE server     │
 *   └────────────┴──────────────┴──────────────┴────────────────────────────┘
 *
 *   Spring Boot 2.x uses Tomcat by default, with options for Jetty or Undertow.
 *
 * 2.5 Container vs Web Server vs Application Server
 *
 *   ┌──────────┬───────────────┬──────────────────┬─────────────────────────┐
 *   │           │ Web Server     │ Servlet Container│ Application Server       │
 *   │           │ (Apache, Nginx)│ (Tomcat)         │ (WildFly, WebLogic)     │
 *   ├──────────┼───────────────┼──────────────────┼─────────────────────────┤
 *   │ Handles   │ Static files   │ Dynamic content   │ Dynamic content +     │
 *   │           │ Reverse proxy  │ (Servlets, JSP)   │ EJB, JMS, JTA, CDI     │
 *   │ Protocols │ HTTP/HTTPS     │ HTTP/HTTPS        │ HTTP + RMI/IIOP        │
 *   │ Java      │ ❌ No         │ ✅ Yes            │ ✅ Yes (Full Java EE) │
 *   │ Complexity│ Low            │ Medium            │ High                    │
 *   └──────────┴───────────────┴──────────────────┴─────────────────────────┘
 *
 *   Typical setup: Nginx (reverse proxy) → Tomcat (servlets) → Database
 *
 * 2.6 Container + Servlet — Complete Picture
 *
 *   ┌─────────────────────────────────────────────────────────────────────┐
 *   │                    TOMCAT (Servlet Container)                       │
 *   │                                                                     │
 *   │  ┌──────────────────────────────────────────────────────────────┐   │
 *   │  │  Web Application (myapp.war)                                  │   │
 *   │  │                                                               │   │
 *   │  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐       │   │
 *   │  │  │ UserServlet   │  │ProductServlet│  │ OrderServlet │       │   │
 *   │  │  │ /api/users    │  │ /api/products│  │ /api/orders  │       │   │
 *   │  │  │               │  │              │  │              │       │   │
 *   │  │  │ doGet → list  │  │ doGet → list │  │ doGet → list │       │   │
 *   │  │  │ doPost → create│  │ doPost → cr.│  │ doPost → cr.│       │   │
 *   │  │  └──────────────┘  └──────────────┘  └──────────────┘       │   │
 *   │  │                                                               │   │
 *   │  │  ┌─────────────────────────────────────────────────────────┐  │   │
 *   │  │  │ Shared: ServletContext, Session, DB Connection Pool     │  │   │
 *   │  │  └─────────────────────────────────────────────────────────┘  │   │
 *   │  └──────────────────────────────────────────────────────────────┘   │
 *   │                                                                     │
 *   │  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐              │
 *   │  │ Thread Pool   │  │ Session Mgr  │  │ ClassLoader  │              │
 *   │  │ (200 threads) │  │ (JSESSIONID) │  │ (isolation)  │              │
 *   │  └──────────────┘  └──────────────┘  └──────────────┘              │
 *   │                                                                     │
 *   │  ┌──────────────────────────────────────────────────────────────┐   │
 *   │  │ Connector: HTTP/1.1 on port 8080                             │   │
 *   │  │            HTTPS on port 8443                                │   │
 *   │  └──────────────────────────────────────────────────────────────┘   │
 *   └─────────────────────────────────────────────────────────────────────┘
 *
 * 2.7 Without Container vs With Container
 *
 *   ┌─────────────────────────────────────┬─────────────────────────────────┐
 *   │  Without Container (raw Java)       │  With Container (Tomcat)       │
 *   ├─────────────────────────────────────┼─────────────────────────────────┤
 *   │  ServerSocket ss = new ServerSocket  │  Just write: @WebServlet       │
 *   │    (8080);                           │  public class MyServlet         │
 *   │  while(true) {                       │      extends HttpServlet {      │
 *   │    Socket s = ss.accept();           │      doGet(req, res) { ... }   │
 *   │    byte[] buf = new byte[1024];     │  }                              │
 *   │    s.getInputStream().read(buf);     │                                 │
 *   │    String raw = new String(buf);     │  Tomcat handles all network    │
 *   │    // parse HTTP manually...         │  code for you.                 │
 *   │    // create response bytes...       │                                 │
 *   │    s.getOutputStream().write(...);   │                                 │
 *   │  }                                   │                                 │
 *   └─────────────────────────────────────┴─────────────────────────────────┘
 *
 * 2.8 How Container Discovers Servlets (Startup Sequence)
 *
 *   When Tomcat starts and deploys your WAR:
 *
 *     1. Reads WEB-INF/web.xml (if exists)
 *     2. Scans @WebServlet, @WebFilter, @WebListener annotations
 *     3. For each servlet found:
 *        a. Loads class: Class.forName("com.app.UserServlet")
 *        b. Creates instance: new UserServlet()
 *        c. Calls init() — your setup code runs
 *        d. Registers URL pattern → servlet mapping
 *     4. Registers filters, listeners
 *     5. Initializes session manager, security realm
 *     6. Logs: "Servlet UserServlet mapped to [/api/users]"
 *     7. Ready to accept requests
 *
 *   If you add @WebServlet("/api/users") and deploy, Tomcat finds it
 *   automatically. No XML needed (Servlet 3.0+).
 *
 * ─────────────────────────────────────────────────────────────────────
 * 3. WAR vs JAR
 * ─────────────────────────────────────────────────────────────────────
 *
 *   ┌───────────┬──────────────────────────────┬──────────────────────────┐
 *   │           │            JAR                │          WAR             │
 *   ├───────────┼──────────────────────────────┼──────────────────────────┤
 *   │ Full Form │ Java ARchive                  │ Web ARchive              │
 *   │ Structure │ META-INF/                     │ WEB-INF/                 │
 *   │           │   MANIFEST.MF                 │   web.xml                │
 *   │           │ com/example/*.class           │   classes/               │
 *   │           │                               │   lib/                   │
 *   │ Entry     │ main() method                 │ No main()                │
 *   │ Point     │ "java -jar app.jar"           │ Container calls servlets │
 *   │ Container │ Not needed                    │ Tomcat/Jetty required    │
 *   │ Typical   │ Spring Boot fat JAR           │ Traditional web apps     │
 *   │ Use       │ (embedded server inside)      │ (external server)        │
 *   └───────────┴──────────────────────────────┴──────────────────────────┘
 *
 * ─────────────────────────────────────────────────────────────────────
 * 4. COMPLETE REQUEST FLOW — What Tomcat does during request
 * ─────────────────────────────────────────────────────────────────────
 *
 *   Client sends: POST /myapp/api/users HTTP/1.1
 *                 Content-Type: application/json
 *                 {"name":"John","email":"john@x.com"}
 *
 *   ╔══════════════════════════════════════════════════════════════╗
 *   ║                   WHAT TOMCAT DOES                          ║
 *   ╠══════════════════════════════════════════════════════════════╣
 *   ║                                                              ║
 *   ║  STEP 1: ACCEPT CONNECTION                                   ║
 *   ║  ─────────────────────────────────────────────────────────   ║
 *   ║  • Opens a TCP server socket on port 8080                    ║
 *   ║  • Accepts incoming TCP connection from client               ║
 *   ║  • Performs TCP 3-way handshake (SYN, SYN-ACK, ACK)         ║
 *   ║                                                              ║
 *   ║  STEP 2: RECEIVE RAW BYTES                                   ║
 *   ║  ─────────────────────────────────────────────────────────   ║
 *   ║  • Reads raw bytes from the socket connection                ║
 *   ║  • Buffers the incoming data                                 ║
 *   ║                                                              ║
 *   ║  STEP 3: PARSE HTTP REQUEST                                  ║
 *   ║  ─────────────────────────────────────────────────────────   ║
 *   ║  • Parses request line:   POST /myapp/api/users HTTP/1.1     ║
 *   ║    → Extracts HTTP method:   POST                            ║
 *   ║    → Extracts URI:           /myapp/api/users                ║
 *   ║    → Extracts protocol:      HTTP/1.1                        ║
 *   ║  • Parses headers:                                           ║
 *   ║    → Content-Type: application/json                          ║
 *   ║    → Content-Length: 37                                      ║
 *   ║    → Host: localhost:8080                                    ║
 *   ║    → Cookies, Accept, etc.                                   ║
 *   ║  • Parses body:                                              ║
 *   ║    → Reads Content-Length bytes from input stream            ║
 *   ║    → Buffers the body: {"name":"John","email":"john@x.com"}  ║
 *   ║  • Handles special cases:                                    ║
 *   ║    → Chunked transfer encoding                               ║
 *   ║    → URL-encoded form data (application/x-www-form-urlencoded)║
 *   ║    → Multipart form data (file uploads)                      ║
 *   ║                                                              ║
 *   ║  STEP 4: BUILD HTTP REQUEST OBJECT                           ║
 *   ║  ─────────────────────────────────────────────────────────   ║
 *   ║  • Creates HttpServletRequest object                         ║
 *   ║  • Populates it with parsed data:                            ║
 *   ║    → req.getMethod()          = "POST"                       ║
 *   ║    → req.getRequestURI()      = "/myapp/api/users"           ║
 *   ║    → req.getContentType()     = "application/json"           ║
 *   ║    → req.getHeader("Host")    = "localhost:8080"             ║
 *   ║    → req.getReader()          → reads JSON body             ║
 *   ║  • Creates associated HttpSession if session cookie exists   ║
 *   ║  • Populates request attributes (setAttribute/getAttribute)  ║
 *   ║                                                              ║
 *   ║  STEP 5: CREATE RESPONSE OBJECT                              ║
 *   ║  ─────────────────────────────────────────────────────────   ║
 *   ║  • Creates HttpServletResponse object                        ║
 *   ║  • Provides res.setStatus(), res.setHeader(),                ║
 *   ║    res.setContentType(), res.getWriter(), etc.               ║
 *   ║  • Response starts empty — YOU fill it                      ║
 *   ║                                                              ║
 *   ║  STEP 6: MAP URI TO SERVLET                                  ║
 *   ║  ─────────────────────────────────────────────────────────   ║
 *   ║  • Looks at @WebServlet("/api/users") or web.xml mappings    ║
 *   ║  • Strips context path: /myapp/api/users → /api/users        ║
 *   ║  • Finds matching servlet: UserServlet                       ║
 *   ║  • If servlet not loaded yet: loads class, instantiates,     ║
 *   ║    calls init()                                              ║
 *   ║                                                              ║
 *   ║  STEP 7: ASSIGN THREAD FROM POOL                            ║
 *   ║  ─────────────────────────────────────────────────────────   ║
 *   ║  • Picks a thread from its thread pool (default ~200)        ║
 *   ║  • Binds the request & response to this thread              ║
 *   ║  • Multiple requests = multiple threads on same servlet      ║
 *   ║                                                              ║
 *   ║  STEP 8: CALL YOUR SERVLET                                  ║
 *   ║  ─────────────────────────────────────────────────────────   ║
 *   ║  • Calls: servlet.service(req, res)                          ║
 *   ║  • HttpServlet.service() reads method = POST                 ║
 *   ║  • Calls: yourServlet.doPost(req, res)     ← YOUR CODE      ║
 *   ║                                                              ║
 *   ║  STEP 9: WAIT FOR YOUR CODE TO COMPLETE                    ║
 *   ║  ─────────────────────────────────────────────────────────   ║
 *   ║  • YOUR CODE runs inside doPost():                           ║
 *   ║    → req.getReader() — Tomcat provides the body stream      ║
 *   ║    → ObjectMapper parses JSON to Java object                ║
 *   ║    → Business logic executes                                 ║
 *   ║    → res.getWriter().write() — YOU write response data      ║
 *   ║  • Tomcat waits for your method to finish                   ║
 *   ║                                                              ║
 *   ║  STEP 10: SEND RESPONSE                                     ║
 *   ║  ─────────────────────────────────────────────────────────   ║
 *   ║  • Takes whatever YOU wrote to res.getWriter()              ║
 *   ║  • Wraps it with HTTP response:                              ║
 *   ║    → HTTP/1.1 201 Created                                   ║
 *   ║    → Content-Type: application/json                         ║
 *   ║    → Content-Length: 15                                     ║
 *   ║    → (empty line)                                           ║
 *   ║    → {"status":"ok"}                                        ║
 *   ║  • Flushes bytes to the socket                              ║
 *   ║  • Client receives HTTP response                            ║
 *   ║                                                              ║
 *   ║  STEP 11: CLEANUP                                           ║
 *   ║  ─────────────────────────────────────────────────────────   ║
 *   ║  • Returns thread to thread pool                            ║
 *   ║  • If Connection: keep-alive, keeps socket open             ║
 *   ║  • If Connection: close, closes socket                      ║
 *   ║  • Logs the request (access log)                            ║
 *   ║                                                              ║
 *   ╚══════════════════════════════════════════════════════════════╝
 *
 * ─────────────────────────────────────────────────────────────────────
 * 5. VISUAL DIAGRAM — Request Flow
 * ─────────────────────────────────────────────────────────────────────
 *
 *   ┌────────┐      ┌──────────────────────────────────────┐      ┌─────────────┐
 *   │ CLIENT │      │           TOMCAT                      │      │YOUR SERVLET │
 *   │        │      │                                      │      │             │
 *   │ curl   │──1──▶│  Accept TCP connection                │      │             │
 *   │        │      │  Parse raw bytes → HTTP method, URI, │      │             │
 *   │ POST   │      │  headers, body                        │      │             │
 *   │ JSON   │      │  Build HttpServletRequest &           │──2──▶│ doPost()    │
 *   │ body   │      │  HttpServletResponse                  │      │             │
 *   │        │      │  Map URI → UserServlet                │      │ Read JSON   │
 *   │        │      │  Assign thread                        │      │ Parse JSON  │
 *   │        │      │  Call service(req, res)               │      │ Save to DB  │
 *   │        │      │                                      │◀─────│ Write JSON  │
 *   │        │◀─────│  Build HTTP response bytes            │      │   response  │
 *   │        │      │  Flush to socket                      │      │             │
 *   │        │      │  Return thread to pool                │      │             │
 *   └────────┘      └──────────────────────────────────────┘      └─────────────┘
 *
 * ─────────────────────────────────────────────────────────────────────
 * 6. HOW TO READ JSON IN SERVLET (Concrete Example)
 * ─────────────────────────────────────────────────────────────────────
 *
 *   @Override
 *   protected void doPost(HttpServletRequest req, HttpServletResponse res)
 *           throws IOException {
 *
 *       // Tomcat parsed the JSON body — you just read it
 *       String json = req.getReader().lines().collect(Collectors.joining());
 *       // → {"name":"John","email":"john@x.com"}
 *
 *       User user = new ObjectMapper().readValue(json, User.class);
 *       userService.save(user);
 *
 *       // Tomcat will send whatever you write — you just set it
 *       res.setContentType("application/json");
 *       res.setStatus(201);
 *       res.getWriter().write("{\"id\":1,\"name\":\"John\"}");
 *   }
 *
 * ─────────────────────────────────────────────────────────────────────
 * 7. EXTERNAL TOMCAT SETUP (How servlet apps were deployed)
 * ─────────────────────────────────────────────────────────────────────
 *
 *   Project Build: mvn clean package → target/myapp.war
 *
 *   ┌───────────────────────────────────────────┐
 *   │  $TOMCAT_HOME/                             │
 *   │  ├── bin/                                  │
 *   │  │   ├── startup.sh       ← start Tomcat   │
 *   │  │   └── shutdown.sh      ← stop Tomcat    │
 *   │  ├── conf/                                 │
 *   │  │   ├── server.xml       ← port, hosts    │
 *   │  │   └── web.xml          ← default config │
 *   │  ├── webapps/                              │
 *   │  │   ├── ROOT.war                          │
 *   │  │   └── myapp.war        ← COPY HERE      │
 *   │  └── logs/                                 │
 *   │       └── catalina.out                     │
 *   └───────────────────────────────────────────┘
 *
 *   Steps:
 *     1. cp myapp.war $TOMCAT_HOME/webapps/
 *     2. $TOMCAT_HOME/bin/startup.sh
 *     3. http://localhost:8080/myapp/api/users   ← app is live
 *
 *   Tomcat auto-detects the WAR, extracts it, reads web.xml,
 *   discovers servlets, calls init(), and starts serving.
 *
 * ─────────────────────────────────────────────────────────────────────
 * 8. SERVLET LIFECYCLE
 * ─────────────────────────────────────────────────────────────────────
 *
 *   ┌─────────────────────────────────────────────────────────────┐
 *   │  TOMCAT STARTUP                                             │
 *   │    │                                                        │
 *   │    ├── Scans @WebServlet annotations / reads web.xml        │
 *   │    ├── Loads class: Class.forName("com.UserServlet")        │
 *   │    ├── Creates instance: new UserServlet()                  │
 *   │    ├── Calls: init(ServletConfig)       ← ONCE             │
 *   │    └── Ready to accept requests                             │
 *   │                                                            │
 *   │  EACH REQUEST                                               │
 *   │    │                                                        │
 *   │    ├── Thread from pool assigned                            │
 *   │    ├── Calls: service(req, res)                             │
 *   │    │   └── doPost/doGet/... ← YOUR CODE                    │
 *   │    └── Thread returned to pool                              │
 *   │                                                            │
 *   │  TOMCAT SHUTDOWN                                            │
 *   │    │                                                        │
 *   │    └── Calls: destroy()                ← ONCE              │
 *   └─────────────────────────────────────────────────────────────┘
 *
 * ─────────────────────────────────────────────────────────────────────
 * 9. DAILY REMINDERS
 * ─────────────────────────────────────────────────────────────────────
 *
 *   ▸ You write HttpServlet + doGet/doPost
 *   ▸ Tomcat handles: port, HTTP parsing, threads, session, security
 *   ▸ WAR  → deploy to external Tomcat
 *   ▸ JAR  → embedded Tomcat (Spring Boot)
 *   ▸ One servlet instance, many threads — NOT thread-safe by default
 *   ▸ req.getReader()  → read body (JSON, form, etc.)
 *   ▸ res.getWriter()  → write text response
 *   ▸ res.getOutputStream() → write binary (file download)
 *   ▸ @WebServlet("/path") or web.xml maps URL → servlet
 *   ▸ req.getParameter("name") → query param or form field
 *   ▸ req.getSession() → get/create session
 */
public class ServletTheoryServletVsServletContainer {
}
