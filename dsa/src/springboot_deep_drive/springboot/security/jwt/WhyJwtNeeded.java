package springboot_deep_drive.springboot.security.jwt;

/*
================================================================
  WHY JWT? - Problems with Session Authentication
================================================================

================================================================
  THE OLD WAY: Session-Based Authentication
================================================================

How websites worked before JWT:

  Step 1: User logs in with username + password
  Step 2: Server creates a SESSION (stored in server memory/database)
  Step 3: Server sends back a SESSION ID (cookie)
  Step 4: Browser stores the cookie
  Step 5: On every request, browser sends cookie
  Step 6: Server looks up session in memory/database
  Step 7: If session found -> user is authenticated

----------------------------------------------------------------
  VISUAL DIAGRAM - Session Auth
----------------------------------------------------------------

  +--------+                               +--------+
  |        |  POST /login                  |        |
  |  User  |  username + password  ------->| Server |
  | (Client|                               |        |
  |Browser)|  <------- Set-Cookie: SESSION |        |
  |        |         ID=abc123             |        |
  |        |                               |--------|
  |        |  GET /api/data                |        |
  |        |  Cookie: SESSION_ID=abc123 -->| Lookup |
  |        |                               | session|
  |        |  <------ Response with data   | in DB  |
  +--------+                               +--------+

================================================================
  PROBLEMS WITH SESSION-BASED AUTH
================================================================

PROBLEM 1: Server stores session in memory/DB
-----------------------------------------------
  Every active user's data is stored on server.
  For 1 million users -> server stores 1 million sessions.
  Memory usage grows with number of users.

PROBLEM 2: Scaling is hard
-----------------------------
  User logs in on Server A.
  Next request goes to Server B (load balancer).
  Server B does NOT have the session.
  User has to login again!

  Solution: Sticky sessions or shared Redis.
  Both add complexity.

PROBLEM 3: Mobile apps don't work well with cookies
-----------------------------------------------------
  Mobile apps (Android/iOS) don't handle cookies
  the same way browsers do.
  Session-based auth is designed for browsers.

PROBLEM 4: CORS issues
-------------------------
  Cookies have restrictions across different domains.
  Microservices architecture becomes difficult.

PROBLEM 5: CSRF attacks
-------------------------
  Session cookies are vulnerable to
  Cross-Site Request Forgery attacks.
  Extra protection needed.

================================================================
  THE JWT SOLUTION
================================================================

JWT (JSON Web Token) solves ALL these problems.

How JWT works:
  - Server creates a TOKEN (not a session)
  - Token contains user data INSIDE it
  - Server signs the token (digitally)
  - Client stores the token (localStorage/memory)
  - Client sends token on every request
  - Server verifies the signature
  - NO session storage on server!

================================================================
  VISUAL DIAGRAM - JWT Auth
================================================================

  +--------+                               +--------+
  |        |  POST /login                  |        |
  |  User  |  username + password  ------->| Server |
  | (Client|                               |        |
  |Browser)|  <------ JWT Token            | Create |
  |  or    |         (signed string)       | JWT    |
  | Mobile |                               |--------|
  |  App   |  GET /api/data                |        |
  |        |  Header: Authorization        | Verify |
  |        |  Bearer <JWT>       -------->| JWT    |
  |        |                               | Sign.  |
  |        |  <------ Response with data   |        |
  +--------+                               +--------+

  KEY DIFFERENCE:
  Server does NOT store session.
  All user info is inside the JWT itself.

================================================================
  SESSION vs JWT - COMPARISON TABLE
================================================================

  +----------------------+----------------------+----------------------+
  | Feature              | Session Auth         | JWT Auth             |
  +----------------------+----------------------+----------------------+
  | Where is user data   | Server memory/DB     | Inside the token     |
  | stored?              |                      | (self-contained)     |
  +----------------------+----------------------+----------------------+
  | Server memory usage  | Grows with users     | Zero (stateless)     |
  +----------------------+----------------------+----------------------+
  | Scaling              | Need shared session  | Naturally scales     |
  |                      | store (Redis)        | (any server works)   |
  +----------------------+----------------------+----------------------+
  | Mobile support       | Difficult (cookies)  | Easy (just send      |
  |                      |                      | token in header)     |
  +----------------------+----------------------+----------------------+
  | Cross-domain/API     | CORS issues          | Works smoothly       |
  +----------------------+----------------------+----------------------+
  | Logout               | Instant (delete      | Token remains valid  |
  |                      | session from server) | until expiry         |
  +----------------------+----------------------+----------------------+
  | Token size           | Small (just ID)      | Larger (has user     |
  |                      |                      | data inside)         |
  +----------------------+----------------------+----------------------+
  | Security             | CSRF vulnerable      | XSS vulnerable       |
  |                      |                      | (store carefully)    |
  +----------------------+----------------------+----------------------+

================================================================
  WHEN TO USE WHAT?
================================================================

Use SESSION auth when:
  - Traditional web app with server-side rendering
  - You need instant logout ability
  - Application is on single server
  - CSRF protection is easier to implement

Use JWT auth when:
  - Mobile apps (Android/iOS)
  - Microservices / distributed systems
  - Stateless APIs (REST)
  - Single Page Applications (React, Angular)
  - Multiple clients (web + mobile + third-party)

================================================================
  WHAT IS JWT?
================================================================

JWT (JSON Web Token) is:

  A string that contains JSON data in encoded form
  + a digital signature to verify it is authentic.

Real example of a JWT:

  eyJhbGciOiJIUzI1NiJ9.
  eyJzdWIiOiJ1c2VyMTIzIiwibmFtZSI6IkpvaG4iLCJleHAiOjE3MDAwMDAwMDB9.
  s5x1fC9GSt6jYz8Xx7R0Z3vN2bB4mM1pQ2wL9kK6jH

  This string has 3 parts separated by dots (.):
  1. Header      (encoded JSON)
  2. Payload     (encoded JSON)
  3. Signature   (for verification)

================================================================
  SIMPLE ANALOGY
================================================================

Think of JWT like a DIGITAL ID CARD:

  +------------------+
  |   ID CARD        |
  |                  |
  | Name: John       |  <- Payload (user data)
  | Role: Admin      |
  | Expires: 1 hour  |
  |                  |
  | Seal of Gov.     |  <- Signature (verified by server)
  +------------------+

  - The ID card contains info about the person (payload)
  - The official seal proves it's real (signature)
  - Anyone can read the card (base64 encoded, not encrypted)
  - But NO ONE can modify it without breaking the seal
  - You show this card to enter buildings (access API)

================================================================
*/

public class WhyJwtNeeded {

}
