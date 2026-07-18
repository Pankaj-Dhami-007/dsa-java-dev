package springboot_deep_drive;

/**
 * ============================================================
 * 🌍 CLIENT SERVER ARCHITECTURE - COMPLETE DEEP DIVE
 * ============================================================
 *
 * Imagine:
 *
 * Client  ---> Request ---> Server
 * Client <--- Response --- Server
 *
 * Example:
 *
 * Browser (Client)
 *        |
 *        | GET https://amazon.com/products
 *        v
 * Amazon Server
 *
 * Server processes request and sends response back.
 *
 * ============================================================
 * WHAT IS CLIENT?
 * ============================================================
 *
 * Client is any application/device that requests data.
 *
 * Examples:
 *
 * 1. Chrome Browser
 * 2. Mobile App
 * 3. Postman
 * 4. React Application
 * 5. Flutter Application
 *
 * Client's job:
 *
 * - Send Request
 * - Receive Response
 * - Display Data
 *
 *
 * ============================================================
 * WHAT IS SERVER?
 * ============================================================
 *
 * Server is a machine/software that receives requests,
 * processes them and returns responses.
 *
 * Examples:
 *
 * 1. Spring Boot Application
 * 2. NodeJS Application
 * 3. Python Flask Application
 * 4. Django Application
 *
 * Server Responsibilities:
 *
 * - Authentication
 * - Authorization
 * - Business Logic
 * - Database Communication
 * - Response Generation
 *
 *
 * ============================================================
 * TYPES OF SERVERS
 * ============================================================
 *
 * 1. Web Server
 *    Example:
 *      Apache
 *      Nginx
 *
 * 2. Application Server
 *    Example:
 *      Spring Boot
 *      Tomcat
 *      JBoss
 *
 * 3. Database Server
 *    Example:
 *      MySQL
 *      PostgreSQL
 *      Oracle
 *
 * 4. File Server
 *
 * 5. Mail Server
 *
 *
 * ============================================================
 * COMPLETE REQUEST FLOW
 * ============================================================
 *
 * User enters:
 *
 * https://google.com
 *
 * Step 1:
 * Browser checks cache.
 *
 * Step 2:
 * Browser asks DNS:
 *
 * "What is IP of google.com ?"
 *
 * DNS returns:
 *
 * 142.250.xxx.xxx
 *
 * Step 3:
 * Browser creates TCP connection.
 *
 * Step 4:
 * HTTPS Handshake happens.
 *
 * Step 5:
 * HTTP Request sent.
 *
 * Step 6:
 * Server processes request.
 *
 * Step 7:
 * Response returned.
 *
 * Step 8:
 * Browser renders page.
 *
 *
 * ============================================================
 * WHAT IS IP ADDRESS?
 * ============================================================
 *
 * Every device connected to network has an address.
 *
 * Example:
 *
 * 192.168.1.10
 *
 * Think of it as house address.
 *
 * Without IP:
 * Computer cannot locate another computer.
 *
 *
 * ============================================================
 * IPV4
 * ============================================================
 *
 * Example:
 *
 * 192.168.1.100
 *
 * Total:
 *
 * 32 Bits
 *
 *
 * ============================================================
 * IPV6
 * ============================================================
 *
 * Example:
 *
 * 2001:0db8:85a3:0000:0000:8a2e:0370:7334
 *
 * Total:
 *
 * 128 Bits
 *
 *
 * ============================================================
 * WHAT IS PORT?
 * ============================================================
 *
 * IP identifies MACHINE.
 *
 * Port identifies APPLICATION.
 *
 * Example:
 *
 * Laptop IP:
 *
 * 192.168.1.10
 *
 * Applications:
 *
 * Chrome      -> Port ?
 * SpringBoot  -> Port 8080
 * MySQL       -> Port 3306
 * PostgreSQL  -> Port 5432
 *
 *
 * Real Address:
 *
 * IP + Port
 *
 * Example:
 *
 * 192.168.1.10:8080
 *
 *
 * ============================================================
 * WELL KNOWN PORTS
 * ============================================================
 *
 * HTTP  -> 80
 * HTTPS -> 443
 * FTP   -> 21
 * SSH   -> 22
 * SMTP  -> 25
 * MYSQL -> 3306
 *
 *
 * ============================================================
 * WHAT IS URL?
 * ============================================================
 *
 * URL = Uniform Resource Locator
 *
 * Example:
 *
 * https://amazon.com/products/10
 *
 * Breakdown:
 *
 * https      -> Protocol
 * amazon.com -> Domain
 * /products  -> Path
 * /10        -> Resource ID
 *
 *
 * ============================================================
 * URL STRUCTURE
 * ============================================================
 *
 * https://amazon.com:443/products?id=10
 *
 * https       -> Protocol
 * amazon.com  -> Domain
 * 443         -> Port
 * products    -> Path
 * id=10       -> Query Parameter
 *
 *
 * ============================================================
 * WHAT IS DNS?
 * ============================================================
 *
 * DNS = Domain Name System
 *
 * Human remembers:
 *
 * google.com
 *
 * Computer understands:
 *
 * 142.250.x.x
 *
 * DNS converts:
 *
 * Domain -> IP Address
 *
 *
 * ============================================================
 * DNS FLOW
 * ============================================================
 *
 * Browser
 *    |
 *    v
 * Local Cache
 *    |
 *    v
 * ISP DNS
 *    |
 *    v
 * Root DNS
 *    |
 *    v
 * TLD DNS (.com)
 *    |
 *    v
 * Authoritative DNS
 *    |
 *    v
 * IP Returned
 *
 *
 * ============================================================
 * WHAT IS HTTP?
 * ============================================================
 *
 * HTTP
 *
 * Hyper Text Transfer Protocol
 *
 * Used for communication between:
 *
 * Client <-> Server
 *
 * Stateless Protocol
 *
 *
 * ============================================================
 * COMMON HTTP METHODS
 * ============================================================
 *
 * GET
 * POST
 * PUT
 * PATCH
 * DELETE
 *
 *
 * ============================================================
 * HTTP STATUS CODES
 * ============================================================
 *
 * 200 OK
 * 201 CREATED
 * 400 BAD REQUEST
 * 401 UNAUTHORIZED
 * 403 FORBIDDEN
 * 404 NOT FOUND
 * 500 INTERNAL SERVER ERROR
 *
 *
 * ============================================================
 * WHAT IS HTTPS?
 * ============================================================
 *
 * HTTPS = HTTP + SSL/TLS
 *
 * Data encrypted during transmission.
 *
 * Browser
 *    |
 *    | Encrypted Data
 *    v
 * Server
 *
 *
 * Benefits:
 *
 * - Secure
 * - Prevents Data Theft
 * - Prevents Man In The Middle Attack
 *
 *
 * ============================================================
 * WHY PORT 80?
 * ============================================================
 *
 * Default Port for HTTP
 *
 * Example:
 *
 * http://google.com
 *
 * Actually:
 *
 * http://google.com:80
 *
 *
 * ============================================================
 * WHY PORT 443?
 * ============================================================
 *
 * Default Port for HTTPS
 *
 * Example:
 *
 * https://google.com
 *
 * Actually:
 *
 * https://google.com:443
 *
 *
 * ============================================================
 * HOW SPRING BOOT RUNS ON MY PC?
 * ============================================================
 *
 * When you start:
 *
 * mvn spring-boot:run
 *
 * OR
 *
 * java -jar app.jar
 *
 * Spring Boot starts Embedded Tomcat.
 *
 *
 * Flow:
 *
 * JVM
 *   |
 *   v
 * Spring Boot
 *   |
 *   v
 * Embedded Tomcat
 *   |
 *   v
 * Listening on Port 8080
 *
 *
 * Example:
 *
 * http://localhost:8080
 *
 *
 * ============================================================
 * WHAT IS LOCALHOST?
 * ============================================================
 *
 * localhost
 *
 * Means:
 *
 * 127.0.0.1
 *
 * Your own machine.
 *
 *
 * ============================================================
 * WHAT HAPPENS WHEN I HIT
 * http://localhost:8080/api/users
 * ============================================================
 *
 * Browser
 *   |
 *   v
 * localhost
 *   |
 *   v
 * Port 8080
 *   |
 *   v
 * Embedded Tomcat
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
 * WHAT IS REVERSE PROXY?
 * ============================================================
 *
 * Client never directly talks to Spring Boot.
 *
 * Usually:
 *
 * Client
 *   |
 *   v
 * Nginx
 *   |
 *   v
 * Spring Boot
 *
 *
 * Nginx acts as Reverse Proxy.
 *
 *
 * Benefits:
 *
 * - Load Balancing
 * - SSL Termination
 * - Security
 * - Caching
 * - Rate Limiting
 *
 *
 * ============================================================
 * REVERSE PROXY FLOW
 * ============================================================
 *
 * Browser
 *   |
 *   v
 * https://amazon.com
 *   |
 *   v
 * Nginx (443)
 *   |
 *   v
 * Spring Boot (8080)
 *
 *
 * User never sees:
 *
 * 8080
 *
 *
 * ============================================================
 * REAL WORLD FLOW
 * ============================================================
 *
 * User
 *   |
 *   v
 * Browser
 *   |
 *   v
 * DNS
 *   |
 *   v
 * Public IP
 *   |
 *   v
 * Nginx
 *   |
 *   v
 * Spring Boot
 *   |
 *   v
 * MySQL
 *   |
 *   v
 * Response
 *
 *
 * ============================================================
 * COMPLETE INTERVIEW ANSWER
 * ============================================================
 *
 * URL
 *  ↓
 * DNS
 *  ↓
 * IP Address
 *  ↓
 * Port
 *  ↓
 * Reverse Proxy (Nginx)
 *  ↓
 * Spring Boot
 *  ↓
 * Controller
 *  ↓
 * Service
 *  ↓
 * Repository
 *  ↓
 * Database
 *  ↓
 * Response
 *
 *
 * ============================================================
 * END OF CLIENT SERVER ARCHITECTURE
 * ============================================================
 **/

public class ClientServerArchitectureNotes {

}

/**
 * WHAT IS DNS?
 *
 * DNS stands for Domain Name System.
 *
 * Humans remember names like:
 * google.com
 * amazon.com
 *
 * Computers do not understand these names.
 * Computers understand IP addresses.
 *
 * Example:
 *
 * google.com -> 142.250.xxx.xxx
 *
 * DNS converts a domain name into an IP address.
 *
 * Think of DNS as a phone contact list.
 *
 * You remember:
 * "Mom"
 *
 * Your phone stores:
 * 9876543210
 *
 * DNS works in a similar way.
 */

/**
 * ============================================================
 * PORT
 * ============================================================
 *
 * WHAT IS A PORT?
 * ----------------
 *
 * A port is a number that identifies a specific application
 * running on a computer.
 *
 * WHY DO WE NEED A PORT?
 * ----------------------
 *
 * One computer can run many applications.
 *
 * Example:
 *
 * MySQL
 * Spring Boot
 * Chrome
 * Redis
 *
 * The IP address identifies the computer.
 *
 * The port identifies the application.
 *
 * Example:
 *
 * IP Address : 192.168.1.10
 * Port       : 8080
 *
 * Full Address:
 *
 * 192.168.1.10:8080
 *
 * COMMON PORTS
 * ------------
 *
 * HTTP  -> 80
 * HTTPS -> 443
 * MySQL -> 3306
 * PostgreSQL -> 5432
 *
 * SUMMARY
 * -------
 *
 * IP identifies a machine.
 * Port identifies an application.
 */