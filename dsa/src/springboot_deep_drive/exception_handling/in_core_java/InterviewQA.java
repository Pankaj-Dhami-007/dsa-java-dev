package springboot_deep_drive.exception_handling.in_core_java;

/*
 * ═══════════════════════════════════════════════════════════════════════════
 *    EXCEPTION HANDLING — COMPLETE INTERVIEW QUESTIONS & ANSWERS
 * ═══════════════════════════════════════════════════════════════════════════
 *
 *   These are the most frequently asked interview questions about exception
 *   handling in Java, with detailed, interview-ready answers.
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q1: What is an exception in Java?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Definition: An exception is an event that occurs during the execution of
 *   a program that disrupts the normal flow of instructions. It represents an
 *   error condition that the program can potentially recover from.
 *
 *   Example: Dividing a number by zero, accessing an array with invalid index,
 *   trying to open a file that doesn't exist.
 *
 *   Without exception handling, the program terminates abnormally.
 *   With exception handling, the program can continue execution gracefully.
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q2: What is the difference between Exception and Error?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   ┌──────────────┬────────────────────────────────┬────────────────────────────────┐
 *   │ Aspect        │ Exception                       │ Error                          │
 *   ├──────────────┼────────────────────────────────┼────────────────────────────────┤
 *   │ Definition    │ Problem that can be handled     │ Problem that CANNOT be handled │
 *   │               │ and recovered from              │ or recovered from              │
 *   │ Level         │ Program level                   │ JVM (system) level             │
 *   │ Should we     │ YES — catch and handle          │ NO — even if we catch, we     │
 *   │ catch?        │                                 │ cannot recover                 │
 *   │ Examples      │ IOException, NullPointer,       │ OutOfMemoryError,              │
 *   │               │ SQLException                   │ StackOverflowError             │
 *   │ Cause         │ External factors or code bugs   │ JVM issues (memory full,       │
 *   │               │                                 │ stack overflow)                │
 *   └──────────────┴────────────────────────────────┴────────────────────────────────┘
 *
 *   Key point: Both Exception and Error are subclasses of Throwable.
 *   The key difference is recoverability — Exception can be recovered,
 *   Error cannot.
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q3: What is the difference between Checked and Unchecked exceptions?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   ┌──────────────┬────────────────────────────────┬────────────────────────────────┐
 *   │ Aspect        │ Checked Exception               │ Unchecked Exception             │
 *   ├──────────────┼────────────────────────────────┼────────────────────────────────┤
 *   │ Definition    │ Exception checked at compile-   │ Exception that occurs at        │
 *   │               │ time. Compiler FORCES handling  │ runtime. Compiler does NOT     │
 *   │               │                                 │ enforce handling               │
 *   │ Parent class  │ Exception (except RuntimeException)│ RuntimeException               │
 *   │ Handling?     │ MANDATORY — try-catch or throws │ OPTIONAL — may or may not     │
 *   │               │                                 │ handle                         │
 *   │ Cause         │ EXTERNAL factors beyond control │ PROGRAMMING BUGS               │
 *   │               │ (File missing, network down)    │ (Null value, wrong index)      │
 *   │ Recovery?     │ YES — retry, fallback           │ NO — fix the code              │
 *   │ Should you    │ YES — must handle               │ NO — find and fix root cause  │
 *   │ catch?        │                                 │                                │
 *   │ Examples      │ IOException, SQLException,      │ NullPointerException,          │
 *   │               │ ClassNotFoundException          │ ArithmeticException,           │
 *   │               │                                 │ ArrayIndexOutOfBoundsException │
 *   └──────────────┴────────────────────────────────┴────────────────────────────────┘
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q4: What is the Exception Hierarchy in Java? (Draw the diagram)
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Answer: The hierarchy is as follows:
 *
 *     Throwable (root class)
 *       ├── Error (JVM level, cannot recover)
 *       │     ├── OutOfMemoryError
 *       │     ├── StackOverflowError
 *       │     └── NoClassDefFoundError
 *       │
 *       └── Exception (program level, can recover)
 *             ├── RuntimeException (unchecked — optional handling)
 *             │     ├── NullPointerException
 *             │     ├── ArithmeticException
 *             │     ├── ArrayIndexOutOfBoundsException
 *             │     ├── NumberFormatException
 *             │     └── IllegalArgumentException
 *             │
 *             └── Other exceptions (checked — must handle)
 *                   ├── IOException
 *                   ├── FileNotFoundException
 *                   ├── SQLException
 *                   ├── ClassNotFoundException
 *                   └── InterruptedException
 *
 *   Key rule: RuntimeException and its subclasses are UNCHECKED.
 *             Everything else under Exception is CHECKED.
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q5: What is the difference between final, finally, and finalize?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   ┌──────────────┬────────────────────────────────┬────────────────────────────────┐
 *   │ Keyword       │ Category                        │ What it does                   │
 *   ├──────────────┼────────────────────────────────┼────────────────────────────────┤
 *   │ final         │ Keyword (modifier)              │ - final class = cannot extend  │
 *   │               │                                 │ - final method = cannot override│
 *   │               │                                 │ - final variable = constant    │
 *   │ finally       │ Block in exception handling     │ - Block that ALWAYS executes   │
 *   │               │                                 │   after try/catch              │
 *   │               │                                 │ - Used for cleanup operations  │
 *   │ finalize      │ Method (Object class)           │ - Called by Garbage Collector  │
 *   │               │                                 │   before destroying an object  │
 *   │               │                                 │ - Deprecated since Java 9      │
 *   └──────────────┴────────────────────────────────┴────────────────────────────────┘
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q6: Can we have try without catch?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Answer: YES. try-finally is a valid combination.
 *   - try-finally: Cleanup code runs, but exception PROPAGATES to caller
 *   - Used when you want to guarantee resource cleanup but don't want to
 *     handle the exception at this level
 *
 *   Note: try WITHOUT catch or finally is a COMPILER ERROR.
 *   try { } alone → ❌ Not allowed
 *   try { } finally { } → ✅ Allowed
 *   try { } catch(...) { } → ✅ Allowed
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q7: Can we have multiple catch blocks? What is the order?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Answer: YES. Order must be from MOST SPECIFIC (child class) to MOST
 *   GENERAL (parent class).
 *
 *   Correct order:
 *     catch (FileNotFoundException e) { }     // ← Most specific (child of IOException)
 *     catch (IOException e) { }               // ← Less specific (parent)
 *     catch (Exception e) { }                 // ← Most general (grandparent)
 *
 *   Wrong order (compiler error):
 *     catch (Exception e) { }                 // ← Catches everything
 *     catch (IOException e) { }               // ← COMPILER ERROR: unreachable code
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q8: What is multi-catch in Java 7+?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Answer: Multi-catch allows catching MULTIPLE exception types in a SINGLE
 *   catch block when they need the SAME handling logic. Exceptions are
 *   separated by pipe (|) symbol.
 *
 *   Example:
 *     catch (IOException | SQLException e) {
 *         System.out.println("Error: " + e.getMessage());
 *     }
 *
 *   Rule: The exception variable in multi-catch is IMPLICITLY FINAL —
 *   you cannot reassign it.
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q9: What is try-with-resources? (Java 7+)
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Answer: try-with-resources is a try statement that automatically closes
 *   resources declared in the try header. Resources must implement the
 *   AutoCloseable interface. They are closed automatically at the end of
 *   the try block, in REVERSE order of declaration.
 *
 *   Example:
 *     try (FileReader fr = new FileReader("file.txt");
 *          BufferedReader br = new BufferedReader(fr)) {
 *         // Use resources
 *     } catch (IOException e) {
 *         // Handle exception
 *     }
 *     // ✅ fr and br are automatically closed (br first, then fr)
 *
 *   Advantages over traditional try-finally:
 *   1. No manual close() calls needed
 *   2. Resources closed in reverse declaration order
 *   3. Proper suppressed exception handling (original exception preserved)
 *   4. Cleaner, shorter code
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q10: What is a suppressed exception?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Answer: When both the try block AND the close() method (in try-with-resources)
 *   throw exceptions, the close() exception is "suppressed" and attached to
 *   the original exception thrown in the try block. This preserves the original
 *   exception rather than losing it.
 *
 *   Retrieving suppressed exceptions:
 *     catch (Exception e) {
 *         Throwable[] suppressed = e.getSuppressed();
 *         // Reads suppressed exceptions from close()
 *     }
 *
 *   In old try-finally, the close() exception would OVERRIDE the original
 *   exception, causing the original error to be lost.
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q11: What is the difference between throw and throws?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   ┌──────────────┬────────────────────────────────┬────────────────────────────────┐
 *   │ Aspect        │ throw                           │ throws                          │
 *   ├──────────────┼────────────────────────────────┼────────────────────────────────┤
 *   │ Definition    │ Actually throw an exception     │ Declare that method may throw  │
 *   │ Location      │ Inside method body              │ In method signature            │
 *   │ What follows  │ Instance of Throwable            │ Class name(s) of exceptions   │
 *   │               │ (new Exception("msg"))          │ (IOException, SQLException)    │
 *   │ Count          │ ONLY ONE at a time              │ MULTIPLE, comma-separated      │
 *   │ Purpose       │ CREATE the exception            │ INFORM the caller             │
 *   │ Syntax        │ throw new IOException();        │ void m() throws IOException;  │
 *   └──────────────┴────────────────────────────────┴────────────────────────────────┘
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q12: Can we create custom exceptions? How?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Answer: YES. Create a class that extends Exception (for checked) or
 *   RuntimeException (for unchecked).
 *
 *   Example — Custom Checked Exception:
 *     public class InsufficientBalanceException extends Exception {
 *         public InsufficientBalanceException(String message) {
 *             super(message);
 *         }
 *         public InsufficientBalanceException(String message, Throwable cause) {
 *             super(message, cause);
 *         }
 *     }
 *
 *   Example — Custom Unchecked Exception:
 *     public class UserNotFoundException extends RuntimeException {
 *         public UserNotFoundException(String message) {
 *             super(message);
 *         }
 *     }
 *
 *   Best practices:
 *   - Class name ends with "Exception"
 *   - Provide constructors with message and cause
 *   - Keep in dedicated package
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q13: What is exception propagation?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Answer: Exception propagation is the process by which an uncaught exception
 *   travels UP the call stack from the method where it occurred to the method
 *   that called it, continuing until either a matching catch block is found
 *   or the exception reaches the JVM, which terminates the program.
 *
 *   If main() also doesn't catch → JVM terminates and prints stack trace.
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q14: What is exception chaining?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Answer: Exception chaining (or wrapping) is the technique of catching a
 *   low-level exception and wrapping it in a higher-level exception while
 *   preserving the original exception as the "cause".
 *
 *   Example:
 *     catch (SQLException e) {
 *         throw new ServiceException("DB operation failed", e);
 *         // ✅ Original SQLException preserved as cause
 *     }
 *
 *   Later: e.getCause() returns the original SQLException.
 *
 *   Benefits:
 *   - Layer abstraction (DB details hidden from controller)
 *   - No information loss (root cause preserved)
 *   - Better debugging
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q15: Can we catch Error?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Answer: YES, technically you can catch Error (catch(Error e) is valid syntax).
 *   But you SHOULD NOT catch Error because:
 *   1. Errors are JVM-level problems (OutOfMemoryError, StackOverflowError)
 *   2. Even if you catch them, you CANNOT recover from them
 *   3. Catching OutOfMemoryError doesn't create more memory
 *
 *   Always catch specific Exception subclasses, not Error or Throwable.
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q16: Can we throw multiple exceptions from one method?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Answer: A method can only throw ONE exception at a time (one execution
 *   path). However, it can DECLARE multiple exceptions in the throws clause:
 *
 *     void process() throws IOException, SQLException {
 *         // Only ONE exception is thrown per method call
 *     }
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q17: What are the rules for exception handling in method overriding?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Answer: Three main rules:
 *   1. If parent doesn't declare throws → child can throw only UNCHECKED
 *      exceptions (RuntimeException).
 *   2. If parent declares throws → child can throw SAME, NARROWER (subtype),
 *      or NO exception. Child CANNOT throw BROADER or DIFFERENT checked excption.
 *   3. Child CAN always throw unchecked exceptions regardless of parent.
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q18: Can we have try-with-resources without catch?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Answer: YES. try-with-resources without catch is valid.
 *   Resources are still auto-closed. Any exception from the try block
 *   propagates to the caller.
 *
 *   Example:
 *     try (FileReader fr = new FileReader("file.txt")) {
 *         // read file
 *     }
 *     // ✅ fr is auto-closed, IOException propagates to caller
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q19: When does finally block NOT execute?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Answer: Only in THREE scenarios:
 *   1. System.exit() is called in try or catch
 *   2. JVM crashes (power failure, hardware issue, kill -9)
 *   3. Thread is killed using Thread.stop() (deprecated)
 *
 *   In all other cases (exception caught, not caught, return in try, etc.),
 *   finally ALWAYS executes.
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q20: What is the output of this code?
 *
 *     try { return 1; } finally { return 2; }
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Answer: The output is 2. The finally block's return statement OVERRIDES
 *   the try block's return statement. NEVER return from finally — it's
 *   meant for cleanup, not returning values.
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q21: What happens if catch block throws an exception?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Answer: The original exception (from try block) is LOST. The new exception
 *   (from catch block) propagates up the call stack. This is why you must be
 *   careful in catch blocks — don't throw new exceptions carelessly.
 *
 *   Example:
 *     try {
 *         throw new IOException("Original error");
 *     } catch (IOException e) {
 *         throw new RuntimeException("New error"); // Original exception LOST
 *     }
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q22: What is the difference between ClassNotFoundException and
 *      NoClassDefFoundError?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   ┌──────────────┬────────────────────────────────┬────────────────────────────────┐
 *   │ Aspect        │ ClassNotFoundException          │ NoClassDefFoundError           │
 *   ├──────────────┼────────────────────────────────┼────────────────────────────────┤
 *   │ Type          │ Checked Exception               │ Error                          │
 *   │ When occurs   │ Class.forName() fails at        │ Class was available at         │
 *   │               │ runtime (class not on classpath)│ compile-time but missing at   │
 *   │               │                                 │ runtime                        │
 *   │ Cause         │ Wrong class name, missing JAR   │ JAR missing from deployment,  │
 *   │               │                                 │ static init failure            │
 *   │ Recoverable?  │ Yes — might fix path            │ No — JVM/system issue         │
 *   │ Example       │ Class.forName("com.missing.Cls")│ Code compiled against a JAR   │
 *   │               │                                 │ that is not present at runtime │
 *   └──────────────┴────────────────────────────────┴────────────────────────────────┘
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q23: What is the meaning of "Throw early, catch late"?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Answer: It is a best practice principle:
 *
 *   "Throw early" = Validate inputs as early as possible (at method entry).
 *     If something is wrong, throw the exception immediately with a clear
 *     message. Don't wait until the error causes a different problem later.
 *
 *   "Catch late" = Handle exceptions at the HIGHEST appropriate layer.
 *     Don't catch in every method. Let the exception propagate to the layer
 *     that can make a decision about what to do (usually the controller or
 *     the UI layer).
 *
 *   Example:
 *     // BAD — catching too early
 *     public void process(String data) {
 *         try {
 *             if (data == null) throw new IllegalArgumentException();
 *         } catch (IllegalArgumentException e) {
 *             // ❌ Handled here, but this method doesn't know what to do
 *         }
 *     }
 *
 *     // GOOD — throw early, let caller decide
 *     public void process(String data) {
 *         if (data == null) throw new IllegalArgumentException("Data is null");
 *         // Caller (controller) will catch and decide response
 *     }
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q24: What are the best practices for exception handling?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   1. Catch SPECIFIC exceptions, not Exception or Throwable
 *   2. Don't SWALLOW exceptions (empty catch blocks)
 *   3. LOG exceptions with stack trace at the point of catch
 *   4. Use try-with-resources for AutoCloseable resources
 *   5. Throw early, catch late
 *   6. Include original cause when wrapping exceptions (chaining)
 *   7. Don't use exceptions for control flow (expensive)
 *   8. Create custom exceptions for business-specific errors
 *   9. Name custom exceptions ending with "Exception"
 *   10. Always clean up resources (finally or try-with-resources)
 *   11. Never return from finally block
 *   12. Document exceptions with @throws in Javadoc
 *
 * ───────────────────────────────────────────────────────────────────────────
 * Q25: Can we have a try block inside another try block? (Nested try)
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Answer: YES. You can nest try-catch blocks inside another try or catch
 *   block. This is used when a section of code within a try block might throw
 *   a different exception that needs different handling.
 *
 *   However, nested try blocks are rarely needed. Usually a single try with
 *   multiple catch blocks is sufficient. Deep nesting makes code hard to read.
 *
 *   Example:
 *     try {
 *         // Outer try
 *         try {
 *             // Inner try
 *             int[] arr = new int[5];
 *             arr[10] = 5;  // ArrayIndexOutOfBounds
 *         } catch (ArrayIndexOutOfBoundsException e) {
 *             System.out.println("Inner catch: " + e.getMessage());
 *         }
 *     } catch (Exception e) {
 *         System.out.println("Outer catch: " + e.getMessage());
 *     }
 */
public class InterviewQA {
}
