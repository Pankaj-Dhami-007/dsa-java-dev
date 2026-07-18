package springboot_deep_drive.exception_handling.in_core_java;

/*
 * ═══════════════════════════════════════════════════════════════════════════
 *    throw, throws, CUSTOM EXCEPTIONS, PROPAGATION — Complete Guide
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 1. throw KEYWORD — Definition
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Definition: The "throw" keyword is used to EXPLICITLY throw an exception.
 *   You use it when you want to create an exception object and hand it to the
 *   runtime system. Once thrown, the normal flow of the program stops and
 *   control moves to the nearest catch block.
 *
 *   Analogy:
 *   ┌─────────────────────────────────────────────────────────────────────────┐
 *   │  You are a factory quality inspector.                                   │
 *   │  You check each product coming off the assembly line.                   │
 *   │  If you find a defective product, you "throw" it into the reject bin.   │
 *   │  The supervisor (catch block) will then handle the defective product.   │
 *   │                                                                         │
 *   │  throw = "This product is defective. Someone handle it!"               │
 *   └─────────────────────────────────────────────────────────────────────────┘
 *
 *   Syntax:
 *     throw new ExceptionType("Error message");
 *
 *   Key Points:
 *   - throw is followed by an INSTANCE of Throwable (or its subclass)
 *   - After throw, execution STOPS immediately
 *   - Control goes to the nearest matching catch block
 *   - If no catch block matches, exception PROPAGATES up the call stack
 *
 *   Example 1 — Basic throw:
 *     public class ThrowExample {
 *         public static void checkAge(int age) {
 *             if (age < 0) {
 *                 // We explicitly create and throw an exception
 *                 throw new IllegalArgumentException(
 *                     "Age cannot be negative: " + age);
 *             }
 *             System.out.println("Age is valid: " + age);
 *         }
 *
 *         public static void main(String[] args) {
 *             checkAge(-5);  // ⚠️ Exception is thrown here
 *             System.out.println("This line NEVER executes");
 *         }
 *     }
 *
 *     Output:
 *       Exception in thread "main" java.lang.IllegalArgumentException:
 *           Age cannot be negative: -5
 *           at ThrowExample.checkAge(ThrowExample.java:5)
 *           at ThrowExample.main(ThrowExample.java:13)
 *
 *   Example 2 — throw with try-catch:
 *     public class ThrowWithCatch {
 *         public static void checkAge(int age) {
 *             if (age < 0) {
 *                 throw new IllegalArgumentException("Age: " + age);
 *             }
 *         }
 *
 *         public static void main(String[] args) {
 *             try {
 *                 checkAge(-5);
 *             } catch (IllegalArgumentException e) {
 *                 System.out.println("Invalid age: " + e.getMessage());
 *                 // Handle: ask user to enter again
 *             }
 *             System.out.println("Program continues...");
 *         }
 *     }
 *
 *     Output:
 *       Invalid age: Age: -5
 *       Program continues...
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 2. throws KEYWORD — Definition
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Definition: The "throws" keyword is used in the method signature to
 *   DECLARE that this method MIGHT throw certain types of exceptions. It's
 *   a warning to the CALLER: "I may throw these exceptions. You must handle
 *   them or declare them yourself."
 *
 *   Analogy:
 *   ┌─────────────────────────────────────────────────────────────────────────┐
 *   │  You work at a restaurant. The chef gives you a plate of hot soup.     │
 *   │  The chef says: "WARNING: This plate is hot (throws HotPlateException)"│
 *   │                                                                         │
 *   │  Now YOU (the caller) have two choices:                                 │
 *   │  1. Handle it: Use oven mitts to carry it (try-catch)                  │
 *   │  2. Pass the buck: Tell your manager "Plate is hot" (throws again)     │
 *   └─────────────────────────────────────────────────────────────────────────┘
 *
 *   Syntax:
 *     returnType methodName(parameters) throws ExceptionType1, ExceptionType2 {
 *         // method body
 *     }
 *
 *   Example 1 — throws Declaration:
 *     public class ThrowsExample {
 *         // This method declares: "I might throw FileNotFoundException"
 *         public static void readFile(String path) throws FileNotFoundException {
 *             FileReader reader = new FileReader(path);
 *             // If file doesn't exist → FileNotFoundException is thrown
 *             // We don't handle it here — we DECLARE it with throws
 *         }
 *
 *         // Caller must handle it:
 *         public static void main(String[] args) {
 *             try {
 *                 readFile("nonexistent.txt");  // ⚠️ Might throw exception
 *             } catch (FileNotFoundException e) {
 *                 System.out.println("File not found. Using default.");
 *                 // Recovery: use default file
 *             }
 *         }
 *     }
 *
 *   Example 2 — throws with Multiple Exceptions:
 *     public void processFile(String path)
 *             throws FileNotFoundException, IOException, ParseException {
 *
 *         FileReader reader = new FileReader(path);  // FileNotFoundException
 *         BufferedReader br = new BufferedReader(reader);
 *         String line = br.readLine();               // IOException
 *         int number = Integer.parseInt(line);       // NumberFormatException
 *         // ⚠️ NumberFormatException is RuntimeException — unchecked
 *         // We don't need to declare it (but we can)
 *     }
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 3. throw vs throws — COMPLETE COMPARISON
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   ┌──────────────┬──────────────────────────────────┬──────────────────────────────────┐
 *   │ Aspect        │ throw                             │ throws                           │
 *   ├──────────────┼──────────────────────────────────┼──────────────────────────────────┤
 *   │ Definition    │ Actually THROW an exception       │ DECLARE that method may throw    │
 *   │ Where used    │ Inside method BODY                │ In method SIGNATURE              │
 *   │ What follows  │ An INSTANCE of Throwable          │ CLASS NAMES of exceptions        │
 *   │               │ (new Exception("msg"))            │ (IOException, SQLException)      │
 *   │ How many      │ Only ONE exception at a time     │ Multiple, comma-separated        │
 *   │ Syntax        │ throw new IOException("err");    │ void m() throws IOException,    │
 *   │               │                                  │          SQLException            │
 *   │ Purpose       │ Actually cause the exception     │ Inform caller about risk         │
 *   │ Execution     │ STOPS program flow immediately   │ Method continues (it's just a    │
 *   │               │ (jumps to catch)                 │ declaration)                     │
 *   │ Example       │ if(x<0) throw new Exception()    │ void m() throws Exception        │
 *   └──────────────┴──────────────────────────────────┴──────────────────────────────────┘
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 4. METHOD OVERRIDING WITH EXCEPTIONS — Rules
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   When you override a method that has throws clause, there are strict rules
 *   about what exceptions the child method can throw.
 *
 *   Rule 1: If parent method does NOT declare throws:
 *     → Child can throw ONLY unchecked exceptions (RuntimeException)
 *     → Child CANNOT throw checked exceptions
 *
 *     class Parent {
 *         void display() { }  // No throws clause
 *     }
 *
 *     class Child extends Parent {
 *         // ✅ Allowed: Unchecked exceptions
 *         void display() throws NullPointerException { }
 *         void display() throws ArithmeticException { }
 *
 *         // ❌ NOT Allowed: Checked exceptions
 *         void display() throws IOException { }  // COMPILER ERROR
 *     }
 *
 *   Rule 2: If parent method DOES declare throws:
 *     → Child can throw: SAME exception, SUBTYPE (narrower), or NONE
 *     → Child CANNOT throw: BROADER or DIFFERENT checked exception
 *     → Child CAN always throw unchecked exceptions
 *
 *     class Parent {
 *         void read() throws IOException { }  // Parent declares IOException
 *     }
 *
 *     class Child extends Parent {
 *         // ✅ Allowed: Same exception
 *         void read() throws IOException { }
 *
 *         // ✅ Allowed: SUBTYPE (narrower) — FileNotFoundException is child of IOException
 *         void read() throws FileNotFoundException { }
 *
 *         // ✅ Allowed: NO throws (child can omit entirely)
 *         void read() { }
 *
 *         // ✅ Allowed: Unchecked exceptions (always allowed)
 *         void read() throws RuntimeException { }
 *
 *         // ❌ NOT Allowed: Broader exception
 *         void read() throws Exception { }  // COMPILER ERROR
 *
 *         // ❌ NOT Allowed: Different checked exception
 *         void read() throws SQLException { }  // COMPILER ERROR
 *     }
 *
 *   Rule 3: The child's throws CANNOT be broader than parent's throws.
 *           This follows Liskov Substitution Principle — child must be
 *           substitutable for parent without breaking caller's code.
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 5. CUSTOM EXCEPTIONS — Definition
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Definition: A custom exception is an exception class that YOU create
 *   to represent specific error conditions in YOUR application. Instead of
 *   using generic exceptions (like Exception or RuntimeException), you create
 *   meaningful exception names that describe the business error.
 *
 *   Why create custom exceptions?
 *   ┌─────────────────────────────────────────────────────────────────────────┐
 *   │  Without custom exception:                                              │
 *   │    throw new Exception("User not found");                               │
 *   │    → catch (Exception e) — catches EVERYTHING (too broad)              │
 *   │                                                                         │
 *   │  With custom exception:                                                 │
 *   │    throw new UserNotFoundException("User not found with id: 5");       │
 *   │    → catch (UserNotFoundException e) — catches ONLY this specific error │
 *   └─────────────────────────────────────────────────────────────────────────┘
 *
 *   ── 5.1 Creating a Custom Checked Exception ──────────────────────────────
 *
 *     // Step 1: Extend Exception class
 *     public class InsufficientBalanceException extends Exception {
 *
 *         // Constructor with error message
 *         public InsufficientBalanceException(String message) {
 *             super(message);  // Call parent constructor with message
 *         }
 *
 *         // Constructor with message + cause (for exception chaining)
 *         public InsufficientBalanceException(String message, Throwable cause) {
 *             super(message, cause);
 *         }
 *     }
 *
 *     // Step 2: Use it (compiler forces handling because it's checked)
 *     public class BankAccount {
 *         private double balance = 500;
 *
 *         public void withdraw(double amount) throws InsufficientBalanceException {
 *             if (amount > balance) {
 *                 throw new InsufficientBalanceException(
 *                     "Required: " + amount + ", Available: " + balance);
 *             }
 *             balance -= amount;
 *         }
 *
 *         public static void main(String[] args) {
 *             BankAccount acc = new BankAccount();
 *             try {
 *                 acc.withdraw(1000);  // ⚠️ Must handle InsufficientBalanceException
 *             } catch (InsufficientBalanceException e) {
 *                 System.out.println("Transaction failed: " + e.getMessage());
 *                 // Suggest: withdraw lower amount
 *             }
 *         }
 *     }
 *
 *   ── 5.2 Creating a Custom Unchecked Exception ────────────────────────────
 *
 *     // Step 1: Extend RuntimeException class
 *     public class UserNotFoundException extends RuntimeException {
 *
 *         // Constructor with message
 *         public UserNotFoundException(String message) {
 *             super(message);
 *         }
 *
 *         // Constructor with ID (convenience)
 *         public UserNotFoundException(Long id) {
 *             super("User not found with id: " + id);
 *         }
 *
 *         // Constructor with message + cause
 *         public UserNotFoundException(String message, Throwable cause) {
 *             super(message, cause);
 *         }
 *     }
 *
 *     // Step 2: Use it (compiler does NOT force handling — it's unchecked)
 *     public class UserService {
 *         private Map<Long, String> users = new HashMap<>();
 *
 *         public String getUserName(Long id) {
 *             String name = users.get(id);
 *             if (name == null) {
 *                 throw new UserNotFoundException(id);  // Unchecked — no try-catch needed
 *             }
 *             return name;
 *         }
 *     }
 *
 *     public class Main {
 *         public static void main(String[] args) {
 *             UserService service = new UserService();
 *             // ⚠️ No try-catch needed (compiler doesn't force)
 *             // But if user not found → program crashes
 *             System.out.println(service.getUserName(1L));
 *         }
 *     }
 *
 *   ── 5.3 When to use Checked vs Unchecked Custom Exception? ───────────────
 *
 *     ✅ Use CHECKED custom exception (extend Exception) when:
 *       - The caller SHOULD recover from this error
 *       - The error is caused by EXTERNAL factors
 *       - Example: InsufficientBalanceException, PaymentFailedException
 *       - The compiler should FORCE the caller to handle it
 *
 *     ✅ Use UNCHECKED custom exception (extend RuntimeException) when:
 *       - The error is a PROGRAMMING BUG or precondition failure
 *       - The caller CANNOT reasonably recover
 *       - Example: UserNotFoundException, InvalidOrderStateException
 *       - Used frequently in modern frameworks (Spring, Hibernate)
 *
 *   ── 5.4 Best Practices for Custom Exceptions ─────────────────────────────
 *
 *     ✅ Name ends with "Exception" (convention)
 *     ✅ Provide at least these constructors:
 *        - No-arg constructor
 *        - Constructor with message (String)
 *        - Constructor with message + cause (Throwable)
 *     ✅ Include relevant business context in the message
 *     ✅ Keep them in their own package (e.g., com.myapp.exception)
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 6. EXCEPTION CHAINING / WRAPPING — Definition
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Definition: Exception chaining (or wrapping) is the technique of catching
 *   a low-level exception and wrapping it in a higher-level (business) exception
 *   while PRESERVING the original exception as the "cause". This allows you to
 *   maintain layer abstraction without losing the root cause for debugging.
 *
 *   Analogy:
 *   ┌─────────────────────────────────────────────────────────────────────────┐
 *   │  You call a plumber to fix a leak.                                      │
 *   │  The plumber tries to fix it but fails because the pipe is rusted.      │
 *   │  Instead of telling you "The pipe is rusted" (technical detail),        │
 *   │  the plumber says: "I couldn't fix the leak because of a pipe issue."   │
 *   │  If you need more details, you can ask: "What exactly happened?"        │
 *   │  (getCause() gives you the original technical detail)                   │
 *   └─────────────────────────────────────────────────────────────────────────┘
 *
 *   Why use exception chaining?
 *     WITHOUT chaining:
 *       catch (SQLException e) {
 *           throw new ServiceException("Database error");
 *           // ❌ Original cause (SQLException) is LOST
 *       }
 *
 *     WITH chaining:
 *       catch (SQLException e) {
 *           throw new ServiceException("Database error", e);
 *           // ✅ Original cause (SQLException) is PRESERVED
 *           // You can get it later: caughtException.getCause()
 *       }
 *
 *   Example — Exception Chaining:
 *     public class UserService {
 *         public User getUser(Long id) {
 *             try {
 *                 return userRepository.findById(id);
 *             } catch (DataAccessException e) {
 *                 // Wrap low-level exception in business exception
 *                 // ✅ Original exception (e) is passed as cause
 *                 throw new UserServiceException(
 *                     "Failed to retrieve user with id: " + id, e);
 *             }
 *         }
 *     }
 *
 *     // In controller:
 *     try {
 *         userService.getUser(5L);
 *     } catch (UserServiceException e) {
 *         System.out.println("Business error: " + e.getMessage());
 *         // Prints: Failed to retrieve user with id: 5
 *
 *         Throwable cause = e.getCause();
 *         System.out.println("Technical cause: " + cause.getMessage());
 *         // Prints: Connection refused (original DataAccessException)
 *     }
 *
 *   ✅ Benefits of exception chaining:
 *     1. Layer abstraction — controller doesn't see DB details
 *     2. No information loss — original cause is preserved
 *     3. Better debugging — can trace from business error to root cause
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 7. EXCEPTION PROPAGATION — Definition
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Definition: Exception propagation is the process by which an exception
 *   travels UP the call stack from the method where it occurred to the method
 *   that called it, and then to that method's caller, and so on, until either
 *   a matching catch block is found OR the exception reaches the JVM (which
 *   terminates the program with a stack trace).
 *
 *   Analogy:
 *   ┌─────────────────────────────────────────────────────────────────────────┐
 *   │  You work in a company with 3 levels:                                   │
 *   │                                                                         │
 *   │  Level 1: Intern (method3) — "I can't do this task!"                    │
 *   │             ↓ (escalates to manager)                                    │
 *   │  Level 2: Manager (method2) — "I can't handle this either!"             │
 *   │             ↓ (escalates to director)                                   │
 *   │  Level 3: Director (method1) — "Fine, I'll handle it myself."           │
 *   │             → Solves the problem (catch)                                │
 *   │                                                                         │
 *   │  If NO ONE handles it → the CEO (JVM) fires everyone (crashes program)  │
 *   └─────────────────────────────────────────────────────────────────────────┘
 *
 *   Example — Exception Propagation:
 *     public class PropagationExample {
 *
 *         // Level 3: Intern's method
 *         public static void method3() {
 *             System.out.println("Inside method3");
 *             int result = 10 / 0;  // ⚠️ ArithmeticException occurs here
 *             // ❌ Not caught → propagates to method2
 *         }
 *
 *         // Level 2: Manager's method
 *         public static void method2() {
 *             System.out.println("Inside method2");
 *             method3();  // Exception propagates FROM method3
 *             // ❌ Not caught → propagates to method1
 *         }
 *
 *         // Level 1: Director's method
 *         public static void method1() {
 *             System.out.println("Inside method1");
 *             try {
 *                 method2();  // Exception propagates FROM method2
 *             } catch (ArithmeticException e) {
 *                 // ✅ CAUGHT HERE — handled by the director
 *                 System.out.println("Exception caught: " + e.getMessage());
 *             }
 *             System.out.println("Program continues normally");
 *         }
 *
 *         public static void main(String[] args) {
 *             method1();
 *         }
 *     }
 *
 *     Output:
 *       Inside method1
 *       Inside method2
 *       Inside method3
 *       Exception caught: / by zero
 *       Program continues normally
 *
 *   What happens if NO ONE catches?
 *     public class NoCatchExample {
 *         public static void method3() { int x = 10 / 0; }
 *         public static void method2() { method3(); }
 *         public static void method1() { method2(); }
 *
 *         public static void main(String[] args) {
 *             method1();
 *             // ⚠️ No catch anywhere
 *         }
 *     }
 *
 *     Output:
 *       Exception in thread "main" java.lang.ArithmeticException: / by zero
 *           at NoCatchExample.method3(NoCatchExample.java:2)
 *           at NoCatchExample.method2(NoCatchExample.java:3)
 *           at NoCatchExample.method1(NoCatchExample.java:4)
 *           at NoCatchExample.main(NoCatchExample.java:7)
 *       // ❌ JVM terminates the program (exception reached main)
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 8. IMPORTANT RULES — QUICK SUMMARY
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   1. throw = actually create and throw an exception (inside method body)
 *   2. throws = declare that method may throw (in method signature)
 *   3. After throw, method execution STOPS immediately
 *   4. throws can declare MULTIPLE exceptions (comma-separated)
 *   5. Override rule: child CANNOT throw broader checked exception than parent
 *   6. Custom checked exception → extends Exception
 *   7. Custom unchecked exception → extends RuntimeException
 *   8. Always include original cause when wrapping exceptions (chaining)
 *   9. Exception propagates UP the call stack until caught or reaches JVM
 *   10. If main() throws → JVM terminates program with stack trace
 */
public class ThrowThrowsAndCustom {
}
