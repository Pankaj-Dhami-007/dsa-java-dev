package springboot_deep_drive;

/*
 * ============================================================
 *  SPRING BOOT DEEP DIVE - COMPLETE OVERVIEW NOTES
 * ============================================================
 *
 * Goal:
 * Understand how a web application evolved from Core Java
 * to Servlets to Spring Framework to Spring Boot.
 *
 * These notes are written in simple English for:
 * ✔ Interviews
 * ✔ Revision
 * ✔ Quick Understanding
 *
 * ============================================================
 * 1. WHAT IS A CLIENT ?
 * ============================================================
 *
 * Definition:
 * A client is anything that sends a request to a server.
 *
 * Real Life Examples:
 * - Chrome Browser
 * - Mobile App
 * - Postman
 * - React Application
 * - Angular Application
 *
 * Example:
 *
 * You open:
 * https://google.com
 *
 * Browser sends request → Google Server
 *
 * Browser = Client
 *
 *
 * ============================================================
 * 2. WHAT IS A SERVER ?
 * ============================================================
 *
 * Definition:
 * A server is a program/computer that receives requests,
 * processes them and sends responses back.
 *
 * Real Life Example:
 *
 * Customer → Waiter → Kitchen
 *
 * Customer = Client
 * Kitchen = Server
 *
 * Request:
 * "Give me Pizza"
 *
 * Response:
 * "Here is your Pizza"
 *
 *
 * ============================================================
 * 3. CLIENT-SERVER ARCHITECTURE
 * ============================================================
 *
 *        Request
 * Client ---------> Server
 *
 *        Response
 * Client <--------- Server
 *
 *
 * Example:
 *
 * Browser
 *    |
 *    | GET /users
 *    v
 * Spring Boot Application
 *    |
 *    | Fetch Data
 *    v
 * Database
 *
 *
 * ============================================================
 * 4. WHAT IS A WEB APPLICATION ?
 * ============================================================
 *
 * Definition:
 * An application that communicates over the internet using
 * HTTP/HTTPS protocol.
 *
 * Examples:
 * - Facebook
 * - Amazon
 * - Netflix
 * - Flipkart
 * - Gmail
 *
 *
 * ============================================================
 * 5. URL (UNIFORM RESOURCE LOCATOR)
 * ============================================================
 *
 * Example:
 *
 * https://www.google.com:443/search
 *
 * Breakdown:
 *
 * https      -> Protocol
 * www.google.com -> Domain
 * 443        -> Port
 * /search    -> Resource/Endpoint
 *
 *
 * General Structure:
 *
 * protocol://domain:port/path
 *
 *
 * ============================================================
 * 6. PROTOCOL
 * ============================================================
 *
 * Definition:
 * Rules for communication.
 *
 * Example:
 *
 * HTTP
 * HTTPS
 * FTP
 * SMTP
 *
 * Real Life Example:
 *
 * Two people speaking English.
 *
 * English = Protocol
 *
 *
 * ============================================================
 * 7. DOMAIN
 * ============================================================
 *
 * Definition:
 * Human readable website name.
 *
 * Examples:
 *
 * google.com
 * amazon.com
 * netflix.com
 *
 * Domain maps to IP Address.
 *
 *
 * ============================================================
 * 8. PORT
 * ============================================================
 *
 * Definition:
 * Logical door through which application communicates.
 *
 * Common Ports:
 *
 * 80  -> HTTP
 * 443 -> HTTPS
 * 3306 -> MySQL
 * 5432 -> PostgreSQL
 * 8080 -> Spring Boot Default
 *
 *
 * Example:
 *
 * localhost:8080
 *
 * localhost -> Machine
 * 8080 -> Application Port
 *
 *
 * ============================================================
 * 9. HTTP
 * ============================================================
 *
 * HyperText Transfer Protocol
 *
 * Used for communication between
 * Client and Server.
 *
 * Stateless Protocol.
 *
 * Means:
 * Every request is independent.
 *
 *
 * ============================================================
 * 10. HTTPS
 * ============================================================
 *
 * HTTP + SSL/TLS Security
 *
 * Data is encrypted.
 *
 * Used by:
 * - Banking
 * - Payment Apps
 * - E-Commerce
 *
 *
 * ============================================================
 * 11. API
 * ============================================================
 *
 * API = Application Programming Interface
 *
 * Definition:
 * A way for one application to communicate
 * with another application.
 *
 *
 * Example:
 *
 * Mobile App
 *      |
 *      | API Call
 *      v
 * Spring Boot Backend
 *
 *
 * ============================================================
 * 12. HTTP METHODS
 * ============================================================
 *
 * GET
 * Read Data
 *
 * POST
 * Create Data
 *
 * PUT
 * Update Entire Data
 *
 * PATCH
 * Partial Update
 *
 * DELETE
 * Delete Data
 *
 *
 * ============================================================
 * 13. HTTP REQUEST
 * ============================================================
 *
 * Client → Server
 *
 * Contains:
 *
 * 1. URL
 * 2. HTTP Method
 * 3. Headers
 * 4. Query Params
 * 5. Request Body
 *
 *
 * Example:
 *
 * POST /users
 *
 * Header:
 * Content-Type: application/json
 *
 * Body:
 *
 * {
 *   "name":"Ravi"
 * }
 *
 *
 * ============================================================
 * 14. HTTP RESPONSE
 * ============================================================
 *
 * Server → Client
 *
 * Contains:
 *
 * 1. Status Code
 * 2. Headers
 * 3. Response Body
 *
 *
 * Example:
 *
 * Status:
 * 200 OK
 *
 * Body:
 *
 * {
 *   "message":"User Created"
 * }
 *
 *
 * ============================================================
 * 15. IMPORTANT STATUS CODES
 * ============================================================
 *
 * 200 -> Success
 * 201 -> Created
 * 400 -> Bad Request
 * 401 -> Unauthorized
 * 403 -> Forbidden
 * 404 -> Not Found
 * 500 -> Internal Server Error
 *
 *
 * ============================================================
 * 16. HOW WEB SERVER WAS CREATED IN CORE JAVA
 * ============================================================
 *
 * Before Spring and Servlets,
 * developers manually created servers.
 *
 * Package:
 *
 * java.net
 *
 *
 * ============================================================
 * 17. SOCKET
 * ============================================================
 *
 * Definition:
 * Communication endpoint between
 * client and server.
 *
 * Think:
 *
 * Phone Call Connection
 *
 * Client Socket
 * ↕
 * Server Socket
 *
 *
 * ============================================================
 * 18. SERVER SOCKET
 * ============================================================
 *
 * Class:
 *
 * java.net.ServerSocket
 *
 * Responsibility:
 *
 * - Open Port
 * - Listen Requests
 * - Accept Connections
 *
 *
 * Example:
 *
 * ServerSocket server =
 *      new ServerSocket(8080);
 *
 *
 * ============================================================
 * 19. WHY WHILE(TRUE) LOOP ?
 * ============================================================
 *
 * Normal Java Program:
 *
 * main()
 * executes
 * ends
 * JVM stops
 *
 *
 * But Server Application:
 *
 * Must keep running forever.
 *
 * Therefore:
 *
 * while(true)
 * {
 *      accept request
 * }
 *
 *
 * ============================================================
 * 20. MANUAL HTTP SERVER FLOW
 * ============================================================
 *
 * 1. Open Port
 * 2. Wait Request
 * 3. Read Bytes
 * 4. Convert Bytes to Text
 * 5. Parse HTTP Request
 * 6. Identify Method
 * 7. Identify Endpoint
 * 8. Execute Logic
 * 9. Build Response
 * 10. Send Response
 *
 * Lots of Manual Work!
 *
 *
 * ============================================================
 * 21. READING REQUEST
 * ============================================================
 *
 * Using:
 *
 * InputStream
 * BufferedReader
 *
 * Read incoming bytes manually.
 *
 *
 * ============================================================
 * 22. PARSING REQUEST
 * ============================================================
 *
 * Extract:
 *
 * GET /users HTTP/1.1
 *
 * Need to manually identify:
 *
 * Method = GET
 * Endpoint = /users
 *
 *
 * ============================================================
 * 23. MAPPING ENDPOINTS MANUALLY
 * ============================================================
 *
 * if(path.equals("/users"))
 * {
 *      userLogic();
 * }
 * else if(path.equals("/products"))
 * {
 *      productLogic();
 * }
 *
 * Very difficult to maintain.
 *
 *
 * ============================================================
 * 24. BUILDING RESPONSE MANUALLY
 * ============================================================
 *
 * HTTP/1.1 200 OK
 * Content-Type: application/json
 *
 * {
 *    "message":"success"
 * }
 *
 * Entire response built manually.
 *
 *
 * ============================================================
 * 25. PROBLEM WITH CORE JAVA SERVER
 * ============================================================
 *
 * Too much boilerplate.
 *
 * Need to manually:
 *
 * ✔ Open Port
 * ✔ Read Request
 * ✔ Parse Request
 * ✔ Routing
 * ✔ Build Response
 * ✔ Thread Handling
 *
 *
 * ============================================================
 * 26. SERVLET INTRODUCTION
 * ============================================================
 *
 * Servlet = Java Web Component
 *
 * Helps process HTTP Requests.
 *
 * Eliminates most manual work.
 *
 *
 * ============================================================
 * 27. SERVLET CONTAINER
 * ============================================================
 *
 * Also Called:
 *
 * Web Container
 *
 * Examples:
 *
 * Tomcat
 * Jetty
 * Undertow
 *
 *
 * ============================================================
 * 28. RESPONSIBILITIES OF SERVLET CONTAINER
 * ============================================================
 *
 * ✔ Open Port
 * ✔ Accept Requests
 * ✔ Create Threads
 * ✔ Parse HTTP
 * ✔ Create Request Object
 * ✔ Create Response Object
 * ✔ Manage Servlet Lifecycle
 *
 *
 * ============================================================
 * 29. TOMCAT
 * ============================================================
 *
 * Most Popular Servlet Container.
 *
 * Embedded by Spring Boot.
 *
 * Default Port:
 *
 * 8080
 *
 *
 * ============================================================
 * 30. HOW TOMCAT HANDLES REQUEST
 * ============================================================
 *
 * Browser
 *    |
 *    v
 * Tomcat
 *    |
 *    v
 * Servlet
 *    |
 *    v
 * Response
 *
 *
 * ============================================================
 * 31. REQUEST & RESPONSE OBJECTS
 * ============================================================
 *
 * Tomcat creates:
 *
 * HttpServletRequest
 *
 * HttpServletResponse
 *
 * and passes them to Servlet.
 *
 *
 * ============================================================
 * 32. SPRING FRAMEWORK
 * ============================================================
 *
 * Spring Framework is a Java framework
 * for building enterprise applications.
 *
 * Main Goal:
 *
 * Reduce Boilerplate Code
 *
 *
 * ============================================================
 * 33. SPRING CORE
 * ============================================================
 *
 * Foundation of Spring.
 *
 * Provides:
 *
 * IOC
 * Dependency Injection
 * Bean Management
 *
 *
 * ============================================================
 * 34. IOC (INVERSION OF CONTROL)
 * ============================================================
 *
 * Traditional:
 *
 * We create objects.
 *
 * Spring:
 *
 * Spring creates objects.
 *
 *
 * ============================================================
 * 35. DEPENDENCY INJECTION
 * ============================================================
 *
 * Instead of:
 *
 * UserService service =
 *      new UserService();
 *
 * Spring injects automatically.
 *
 *
 * ============================================================
 * 36. SPRING ECOSYSTEM
 * ============================================================
 *
 * Spring Core
 * Spring MVC
 * Spring Data JPA
 * Spring Security
 * Spring Boot
 * Spring Cloud
 * Spring Batch
 *
 *
 * ============================================================
 * 37. SPRING MVC
 * ============================================================
 *
 * Used for Web Applications.
 *
 * MVC:
 *
 * Model
 * View
 * Controller
 *
 *
 * ============================================================
 * 38. SPRING DATA JPA
 * ============================================================
 *
 * Simplifies Database Operations.
 *
 * No need to write much SQL.
 *
 *
 * ============================================================
 * 39. SPRING SECURITY
 * ============================================================
 *
 * Authentication
 * Authorization
 * JWT
 * OAuth2
 *
 *
 * ============================================================
 * 40. SPRING BOOT
 * ============================================================
 *
 * Spring Boot =
 * Spring Framework + Auto Configuration
 *
 * Goal:
 *
 * Build Production Applications Fast.
 *
 *
 * ============================================================
 * 41. WHY SPRING BOOT ?
 * ============================================================
 *
 * ✔ Less Configuration
 * ✔ Embedded Tomcat
 * ✔ Starter Dependencies
 * ✔ Auto Configuration
 * ✔ Production Ready
 *
 *
 * ============================================================
 * 42. EMBEDDED TOMCAT
 * ============================================================
 *
 * No need to install external Tomcat.
 *
 * Spring Boot starts Tomcat automatically.
 *
 *
 * ============================================================
 * 43. COMPLETE REQUEST FLOW
 * ============================================================
 *
 * Browser
 *   |
 *   v
 * HTTP Request
 *   |
 *   v
 * Tomcat
 *   |
 *   v
 * DispatcherServlet
 *   |
 *   v
 * Controller
 *   |
 *   v
 * Service
 *   |
 *   v
 * Repository
 *   |
 *   v
 * Database
 *   |
 *   v
 * Response Back
 *
 *
 * ============================================================
 * 44. WEB APPLICATION COMMUNICATION
 * ============================================================
 *
 * Frontend
 *    |
 *    | HTTP/HTTPS
 *    v
 * Backend (Spring Boot)
 *    |
 *    | JDBC/JPA
 *    v
 * Database
 *
 *
 * ============================================================
 * 45. INTERVIEW ONE-LINE SUMMARY
 * ============================================================
 *
 * Core Java:
 * Manual Server Creation
 *
 * Servlet:
 * Handles HTTP Better
 *
 * Servlet Container:
 * Runs Servlets
 *
 * Tomcat:
 * Popular Servlet Container
 *
 * Spring:
 * Simplifies Java Development
 *
 * Spring MVC:
 * Web Layer Framework
 *
 * Spring Boot:
 * Rapid Development Framework
 *
 * Embedded Tomcat:
 * Built-in Web Server
 *
 * HTTP:
 * Client-Server Communication Protocol
 *
 * API:
 * Application Communication Contract
 *
 * ============================================================
 * END OF OVERVIEW
 * ============================================================
 */
public class SpringbootDeepDriveDemo {

}