package springboot_deep_drive.exception_handling.in_core_java;

/*
 * ═══════════════════════════════════════════════════════════════════════════
 *    TRY, CATCH, FINALLY, TRY-WITH-RESOURCES — Complete Beginner Guide
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 1. WHAT IS try-catch? (Definition)
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Definition: The try-catch block is Java's primary mechanism to handle
 *   exceptions. You put code that might throw an exception inside the "try"
 *   block, and you handle the exception in the "catch" block. If no exception
 *   occurs, the catch block is simply skipped.
 *
 *   Analogy:
 *   ┌─────────────────────────────────────────────────────────────────────────┐
 *   │  You are cooking in the kitchen.                                       │
 *   │                                                                         │
 *   │  try (Your cooking process):                                           │
 *   │    - You try to cut vegetables                                         │
 *   │    - You try to turn on the stove                                      │
 *   │    ❗ You might cut your finger (exception!)                            │
 *   │                                                                         │
 *   │  catch (What happens if you cut your finger):                          │
 *   │    - You stop cooking                                                  │
 *   │    - You get a bandage (handle the problem)                            │
 *   │    - You continue with your day (program continues)                    │
 *   │                                                                         │
 *   │  If you DON'T cut your finger → catch block is simply IGNORED ✅       │
 *   └─────────────────────────────────────────────────────────────────────────┘
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 2. BASIC SYNTAX — try-catch
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Syntax:
 *     try {
 *         // Step 1: Code that might throw an exception
 *         // Step 2: If exception occurs here → control jumps to catch
 *         // Step 3: Lines AFTER the exception are SKIPPED
 *     } catch (ExceptionType variableName) {
 *         // Step 4: Handle the exception
 *         // Step 5: Only executes if exception occurred
 *     }
 *     // Step 6: Code after try-catch ALWAYS executes (with or without exception)
 *
 *   Example 1 — No Exception (catch is skipped):
 *     public class NoExceptionExample {
 *         public static void main(String[] args) {
 *             System.out.println("1. Before try");
 *
 *             try {
 *                 System.out.println("2. Inside try - before division");
 *                 int result = 10 / 2;     // ✅ No exception (10/2 = 5)
 *                 System.out.println("3. Inside try - after division: " + result);
 *             } catch (ArithmeticException e) {
 *                 System.out.println("4. Catch block");   // NEVER executes
 *             }
 *
 *             System.out.println("5. After try-catch");
 *         }
 *     }
 *
 *     Output:
 *       1. Before try
 *       2. Inside try - before division
 *       3. Inside try - after division: 5
 *       5. After try-catch
 *       // ⚠️ Catch block (line 4) was SKIPPED — no exception occurred
 *
 *   Example 2 — Exception Occurs (catch executes):
 *     public class ExceptionExample {
 *         public static void main(String[] args) {
 *             System.out.println("1. Before try");
 *
 *             try {
 *                 System.out.println("2. Inside try - before division");
 *                 int result = 10 / 0;     // ⚠️ ArithmeticException!
 *                 System.out.println("3. Inside try - after division"); // SKIPPED
 *             } catch (ArithmeticException e) {
 *                 System.out.println("4. Catch block: " + e.getMessage());
 *                 // Prints: / by zero
 *             }
 *
 *             System.out.println("5. After try-catch");
 *         }
 *     }
 *
 *     Output:
 *       1. Before try
 *       2. Inside try - before division
 *       4. Catch block: / by zero
 *       5. After try-catch
 *       // ⚠️ Line 3 was SKIPPED because exception was thrown at line 2
 *       // ✅ But line 5 still executes because exception was HANDLED
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 3. MULTIPLE CATCH BLOCKS
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Definition: You can have multiple catch blocks after a single try block
 *   to handle different types of exceptions with different handling logic.
 *   Each catch block handles a specific exception type.
 *
 *   ⚠️ IMPORTANT RULE: You must order catch blocks from MOST SPECIFIC
 *   (child class) to MOST GENERAL (parent class). If you put the parent
 *   first, the child catch becomes unreachable (compiler error).
 *
 *   Syntax:
 *     try {
 *         // code that might throw different exceptions
 *     } catch (FileNotFoundException e) {     // ← MOST specific (child)
 *         System.out.println("File not found");
 *     } catch (IOException e) {               // ← LESS specific (parent)
 *         System.out.println("IO error");
 *     } catch (Exception e) {                 // ← LEAST specific (grandparent)
 *         System.out.println("Some other error");
 *     }
 *
 *   Example — Correct Order:
 *     public class MultipleCatchExample {
 *         public static void main(String[] args) {
 *             try {
 *                 // This code can throw different exceptions
 *                 int[] numbers = {1, 2, 3};
 *                 System.out.println(numbers[5]); // ArrayIndexOutOfBoundsException
 *
 *                 String str = null;
 *                 System.out.println(str.length()); // NullPointerException
 *             }
 *             catch (ArrayIndexOutOfBoundsException e) {
 *                 // Handles ONLY ArrayIndexOutOfBoundsException
 *                 System.out.println("Array index problem: " + e.getMessage());
 *             }
 *             catch (NullPointerException e) {
 *                 // Handles ONLY NullPointerException
 *                 System.out.println("Null value problem: " + e.getMessage());
 *             }
 *             catch (Exception e) {
 *                 // Handles ANY OTHER exception (RuntimeException, etc.)
 *                 System.out.println("Some other error: " + e.getMessage());
 *             }
 *         }
 *     }
 *
 *   ❌ WRONG Order (Compiler Error):
 *     try {
 *         // code
 *     } catch (Exception e) {                 // ← Parent first
 *         System.out.println("General error");
 *     } catch (IOException e) {               // ← ❌ COMPILER ERROR
 *         // This can NEVER be reached because Exception (parent)
 *         // already caught everything including IOException
 *         System.out.println("IO error");
 *     }
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 4. MULTI-CATCH (Java 7+ Feature)
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Definition: Multi-catch allows you to catch MULTIPLE different exception
 *   types in a SINGLE catch block if they need the SAME handling logic.
 *   You separate the exception types with a pipe symbol (|).
 *
 *   ⚠️ Important: The exception variable in multi-catch is implicitly
 *   FINAL — you cannot reassign it.
 *
 *   Syntax:
 *     try {
 *         // code that can throw IOException OR SQLException
 *     } catch (IOException | SQLException e) {
 *         // Same handling for both exceptions
 *         System.out.println("Database or File error: " + e.getMessage());
 *         // e = new IOException();  ← ❌ NOT allowed (e is final)
 *     }
 *
 *   Example — Before Java 7 (had to write separate catch blocks):
 *     try {
 *         // code
 *     } catch (IOException e) {
 *         System.out.println("Error: " + e.getMessage());
 *     } catch (SQLException e) {
 *         System.out.println("Error: " + e.getMessage());
 *     }
 *     // ❌ Repetitive code — same handling logic duplicated
 *
 *   Example — With Multi-catch (Java 7+):
 *     try {
 *         readFile();   // might throw IOException
 *         queryDB();    // might throw SQLException
 *     } catch (IOException | SQLException e) {
 *         // ✅ Single handler for both
 *         System.out.println("Error: " + e.getMessage());
 *         e.printStackTrace();
 *     }
 *
 *   ✅ When to use multi-catch:
 *     - Multiple exceptions need IDENTICAL handling logic
 *     - The exceptions are UNRELATED (not parent-child)
 *     - You don't need to reassign the exception variable
 *
 *   ❌ When NOT to use multi-catch:
 *     - Exceptions need DIFFERENT handling logic (use separate catches)
 *     - The exceptions are parent-child (IOException and FileNotFoundException)
 *       → This is redundant; catching the parent already catches the child
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 5. finally BLOCK — What it is
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Definition: The finally block is a block that ALWAYS executes regardless
 *   of whether an exception occurs or not. It is used for cleanup operations
 *   like closing files, database connections, or releasing resources.
 *
 *   Syntax:
 *     try {
 *         // risky code
 *     } catch (ExceptionType e) {
 *         // handle exception (optional)
 *     } finally {
 *         // ✅ ALWAYS executes (cleanup code here)
 *     }
 *
 *   Example — finally with No Exception:
 *     public class FinallyNoException {
 *         public static void main(String[] args) {
 *             try {
 *                 System.out.println("Inside try");
 *                 int result = 10 / 2;  // ✅ No exception
 *             } catch (ArithmeticException e) {
 *                 System.out.println("Inside catch");
 *             } finally {
 *                 System.out.println("INSIDE FINALLY — ALWAYS executes");
 *             }
 *             System.out.println("After try-catch-finally");
 *         }
 *     }
 *
 *     Output:
 *       Inside try
 *       INSIDE FINALLY — ALWAYS executes
 *       After try-catch-finally
 *       // ✅ finally ran even though NO exception occurred
 *
 *   Example — finally with Exception (Caught):
 *     public class FinallyWithCaughtException {
 *         public static void main(String[] args) {
 *             try {
 *                 System.out.println("Inside try");
 *                 int result = 10 / 0;  // ⚠️ ArithmeticException
 *             } catch (ArithmeticException e) {
 *                 System.out.println("Inside catch: " + e.getMessage());
 *             } finally {
 *                 System.out.println("INSIDE FINALLY — ALWAYS executes");
 *             }
 *             System.out.println("After try-catch-finally");
 *         }
 *     }
 *
 *     Output:
 *       Inside try
 *       Inside catch: / by zero
 *       INSIDE FINALLY — ALWAYS executes
 *       After try-catch-finally
 *       // ✅ finally ran even though exception OCCURRED and was CAUGHT
 *
 *   Example — finally with Exception (NOT Caught — Propagated):
 *     public class FinallyWithUncaughtException {
 *         public static void main(String[] args) {
 *             try {
 *                 System.out.println("Inside try");
 *                 int result = 10 / 0;  // ⚠️ ArithmeticException
 *             } finally {
 *                 // ✅ This STILL runs even though exception is NOT caught
 *                 System.out.println("INSIDE FINALLY — STILL executes");
 *             }
 *             // ❌ This line does NOT execute — exception propagates to main
 *         }
 *     }
 *
 *     Output:
 *       Inside try
 *       INSIDE FINALLY — STILL executes
 *       Exception in thread "main" java.lang.ArithmeticException: / by zero
 *       // ✅ finally ran BEFORE exception propagated
 *       // ❌ Program still crashed (no catch), but cleanup happened
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 6. WHEN DOES finally NOT EXECUTE?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   There are ONLY 3 scenarios where finally does NOT execute:
 *
 *   ┌──────┬─────────────────────────────────────────────────────────────────┐
 *   │ #     │ Scenario                                                        │
 *   ├──────┼─────────────────────────────────────────────────────────────────┤
 *   │ 1     │ System.exit(0) is called in try or catch                       │
 *   │       │ → JVM shuts down immediately, finally skipped                  │
 *   │ 2     │ JVM crashes (power failure, kill -9, hardware issue)           │
 *   │       │ → No time to execute finally                                   │
 *   │ 3     │ Thread is killed using Thread.stop() (deprecated)              │
 *   │       │ → Thread dies abruptly, finally skipped                        │
 *   └──────┴─────────────────────────────────────────────────────────────────┘
 *
 *   Example — System.exit() prevents finally:
 *     public class FinallyWithSystemExit {
 *         public static void main(String[] args) {
 *             try {
 *                 System.out.println("Inside try");
 *                 System.exit(0);  // ⚠️ JVM shuts down immediately
 *             } finally {
 *                 // ❌ This will NOT execute
 *                 System.out.println("This will NEVER print");
 *             }
 *         }
 *     }
 *
 *     Output:
 *       Inside try
 *       // ⚠️ Program ends. Finally did NOT execute.
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 7. IMPORTANT: finally with return Statement
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   ⚠️ If you have a return statement in both try and finally, the finally's
 *   return OVERRIDES the try's return. NEVER return from finally.
 *
 *   Example:
 *     public static int test() {
 *         try {
 *             return 1;       // Wants to return 1
 *         } finally {
 *             return 2;       // ❌ OVERRIDES the return 1!
 *         }
 *     }
 *
 *     public static void main(String[] args) {
 *         System.out.println(test());  // Prints: 2 (not 1)
 *     }
 *
 *   ✅ Always remember: finally is for CLEANUP, not for returning values.
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 8. try-finally WITHOUT catch (Valid Syntax)
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Definition: You can have try-finally without catch. This is useful when
 *   you want to guarantee cleanup (close resources) but you want the exception
 *   to PROPAGATE to the caller (not handle it here).
 *
 *   Syntax:
 *     try {
 *         // risky code
 *     } finally {
 *         // cleanup code (ALWAYS executes)
 *     }
 *     // Exception propagates to caller (not caught here)
 *
 *   Example:
 *     public class TryFinallyWithoutCatch {
 *         public static void main(String[] args) {
 *             try {
 *                 System.out.println("Opening file...");
 *                 // Simulating an exception
 *                 int result = 10 / 0;
 *             } finally {
 *                 System.out.println("Closing file... (cleanup)");
 *             }
 *             // ❌ This line is NOT reached (exception propagated)
 *         }
 *     }
 *
 *     Output:
 *       Opening file...
 *       Closing file... (cleanup)
 *       Exception in thread "main" java.lang.ArithmeticException: / by zero
 *
 *     ✅ Resource was closed (finally ran) but exception still propagated
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 9. TRY-WITH-RESOURCES (Java 7+)
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Definition: try-with-resources is a try statement that declares one or
 *   more resources in the try header. These resources are automatically
 *   closed at the end of the try block, regardless of whether an exception
 *   occurs. The resource must implement the AutoCloseable interface.
 *
 *   ✅ Benefits over traditional try-finally:
 *     1. Shorter, cleaner code (no finally block needed)
 *     2. Automatic resource closure (no manual close() call)
 *     3. Resources closed in REVERSE order of declaration
 *     4. Proper suppressed exception handling (doesn't lose original error)
 *
 *   Syntax:
 *     try (ResourceType resource = new ResourceType()) {
 *         // use the resource
 *     } catch (ExceptionType e) {
 *         // handle exception (optional)
 *     }
 *     // ✅ resource is automatically closed (no finally needed)
 *
 *   Example — Without try-with-resources (Old way, Java 6 and before):
 *     FileReader reader = null;
 *     BufferedReader br = null;
 *     try {
 *         reader = new FileReader("file.txt");
 *         br = new BufferedReader(reader);
 *         String line = br.readLine();
 *         System.out.println(line);
 *     } catch (IOException e) {
 *         System.out.println("Error: " + e.getMessage());
 *     } finally {
 *         // ❌ Manual cleanup — easy to forget, easy to get wrong
 *         if (br != null) {
 *             try { br.close(); } catch (IOException e) { }
 *         }
 *         if (reader != null) {
 *             try { reader.close(); } catch (IOException e) { }
 *         }
 *     }
 *
 *   Example — WITH try-with-resources (Java 7+):
 *     try (FileReader reader = new FileReader("file.txt");
 *          BufferedReader br = new BufferedReader(reader)) {
 *         // ✅ Resources declared in try header
 *         String line = br.readLine();
 *         System.out.println(line);
 *     } catch (IOException e) {
 *         System.out.println("Error: " + e.getMessage());
 *     }
 *     // ✅ Both reader and br are automatically closed
 *     // ✅ br closed first (reverse declaration order), then reader
 *     // ✅ No finally block needed!
 *
 *   Multiple Resources — Close Order:
 *     try (FileReader fr = new FileReader("a.txt");    // declared 1st
 *          FileReader fr2 = new FileReader("b.txt")) { // declared 2nd
 *         // use resources
 *     }
 *     // Close order: fr2 first (2nd declared), fr next (1st declared)
 *     // Resources are closed in REVERSE declaration order
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 10. SUPPRESSED EXCEPTIONS
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Definition: A suppressed exception is an exception that occurs while
 *   closing a resource (in try-with-resources) when the try block itself
 *   already threw an exception. Instead of losing the original exception,
 *   the close() exception is "suppressed" (attached) to the original exception.
 *
 *   Why is this needed?
 *   ┌─────────────────────────────────────────────────────────────────────────┐
 *   │  Old try-finally problem:                                                │
 *   │    try { throw new IOException("Disk full"); }                          │
 *   │    finally { stream.close(); // ALSO throws IOException }               │
 *   │                                                                         │
 *   │  ❌ In old try-finally:                                                  │
 *   │     The close() exception REPLACES the original "Disk full" exception    │
 *   │     You LOSE the original exception — you never know disk was full!     │
 *   │                                                                         │
 *   │  ✅ In try-with-resources:                                               │
 *   │     The close() exception is SUPPRESSED under the "Disk full" exception  │
 *   │     You can see BOTH: original + suppressed exception                   │
 *   └─────────────────────────────────────────────────────────────────────────┘
 *
 *   Example — try-with-resources preserves original exception:
 *     public class SuppressedExample implements AutoCloseable {
 *         public void doWork() {
 *             throw new RuntimeException("Error in work");  // Original exception
 *         }
 *         public void close() {
 *             throw new RuntimeException("Error in close"); // Suppressed exception
 *         }
 *     }
 *
 *     public class Main {
 *         public static void main(String[] args) {
 *             try (SuppressedExample r = new SuppressedExample()) {
 *                 r.doWork();  // ⚠️ throws RuntimeException
 *             } catch (RuntimeException e) {
 *                 System.out.println("Original: " + e.getMessage());
 *                 // Prints: Error in work
 *
 *                 Throwable[] suppressed = e.getSuppressed();
 *                 System.out.println("Suppressed count: " + suppressed.length);
 *                 System.out.println("Suppressed: " + suppressed[0].getMessage());
 *                 // Prints: Error in close
 *             }
 *         }
 *     }
 *
 *     Output:
 *       Original: Error in work
 *       Suppressed count: 1
 *       Suppressed: Error in close
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 11. CAN try EXIST WITHOUT catch OR finally?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   ❌ NO — try MUST be followed by at least ONE of: catch or finally.
 *
 *     try { }                          // ❌ COMPILER ERROR — no catch/finally
 *     try { } catch(...) { }           // ✅ Valid
 *     try { } finally { }              // ✅ Valid
 *     try { } catch(...) { } finally { } // ✅ Valid
 *     try (R r = new R()) { }          // ✅ Valid (try-with-resources is different)
 *     try (R r = new R()) { } catch(...) { } // ✅ Valid
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 12. IMPORTANT RULES (Quick Summary)
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   1. try MUST have at least one catch or finally block
 *   2. Order of multiple catches: specific (child) → general (parent)
 *   3. finally ALWAYS executes (except System.exit, JVM crash, thread death)
 *   4. NEVER return from finally (overrides try's return)
 *   5. Multi-catch (Java 7+): catch (IOException | SQLException e)
 *   6. Multi-catch variable is implicitly FINAL (can't reassign)
 *   7. try-with-resources: resources must implement AutoCloseable
 *   8. try-with-resources: resources closed in REVERSE declaration order
 *   9. Suppressed exceptions: close() errors attached to original exception
 *   10. try-with-resources is PREFERRED over try-finally for resource cleanup
 */
public class TryCatchFinally {
}
