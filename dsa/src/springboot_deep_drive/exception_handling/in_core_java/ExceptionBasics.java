package springboot_deep_drive.exception_handling.in_core_java;

/*
 * ═══════════════════════════════════════════════════════════════════════════
 *    EXCEPTION BASICS — What is Exception, Hierarchy, Types of Exceptions
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 1. WHAT IS AN EXCEPTION? (Definition in Simple Words)
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Definition: An exception is an unwanted or unexpected event that occurs
 *   during the execution of a program (at runtime) and disrupts the normal
 *   flow of the program's instructions.
 *
 *   Real Life Analogy:
 *   ┌─────────────────────────────────────────────────────────────────────────┐
 *   │  You are driving a car to go to your office.                           │
 *   │  Normal flow: Start car → Drive → Reach office ✅                      │
 *   │  Exception:    Start car → Drive → ⚡ PUNCTURE ⚡ → Can't reach office  │
 *   │                                                                         │
 *   │  The puncture is an "exception" — it disrupts your normal journey.      │
 *   │  You can either:                                                        │
 *   │    1. Handle it: Change the tire yourself (try-catch)                   │
 *   │    2. Declare it: Call a mechanic and tell them to handle it (throws)   │
 *   │    3. Ignore it: Keep driving and damage the car (program crashes)      │
 *   └─────────────────────────────────────────────────────────────────────────┘
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 2. WHY DO WE NEED EXCEPTION HANDLING? (The Problem it Solves)
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Without exception handling, if an error occurs in a Java program:
 *     → The program stops immediately (crashes)
 *     → The line AFTER the error NEVER executes
 *     → User sees a scary error message (stack trace)
 *     → Resources (files, database connections) are NOT closed
 *
 *   Example — Without Exception Handling:
 *     public class WithoutHandling {
 *         public static void main(String[] args) {
 *             System.out.println("Line 1: Start");
 *             int result = 10 / 0;     // ⚠️ ArithmeticException occurs here
 *             System.out.println("Line 3: This will NEVER print");
 *             System.out.println("Line 4: This will also NEVER print");
 *         }
 *     }
 *
 *     Output:
 *       Line 1: Start
 *       Exception in thread "main" java.lang.ArithmeticException: / by zero
 *           at WithoutHandling.main(WithoutHandling.java:5)
 *     // ⚠️ Program crashes. Lines 3 and 4 never execute.
 *
 *   Example — WITH Exception Handling:
 *     public class WithHandling {
 *         public static void main(String[] args) {
 *             System.out.println("Line 1: Start");
 *             try {
 *                 int result = 10 / 0;     // ⚠️ Exception occurs here
 *             } catch (ArithmeticException e) {
 *                 System.out.println("Line 5: Cannot divide by zero");
 *             }
 *             System.out.println("Line 7: Program continues normally ✅");
 *         }
 *     }
 *
 *     Output:
 *       Line 1: Start
 *       Line 5: Cannot divide by zero
 *       Line 7: Program continues normally ✅
 *
 *   ✅ Benefits of Exception Handling:
 *     1. Program continues execution after the error
 *     2. User sees a friendly message instead of crash
 *     3. Resources can be properly closed (finally block)
 *     4. Error details can be logged for debugging
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 3. EXCEPTION HIERARCHY (The Family Tree)
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   In Java, all exceptions and errors are represented as classes.
 *   They all belong to one family with Throwable as the root.
 *
 *                         java.lang.Object
 *                               │
 *                               ▼
 *                     ┌─────────────────┐
 *                     │    Throwable     │ ← Grandfather of all exceptions
 *                     └────────┬────────┘
 *                              │
 *             ┌────────────────┴────────────────┐
 *             │                                  │
 *     ┌───────┴────────┐              ┌──────────┴──────────┐
 *     │     Error      │              │     Exception       │
 *     │ (JVM Level)    │              │ (Program Level)     │
 *     │ Can't handle   │              │ CAN handle          │
 *     └────────────────┘              └──────────┬──────────┘
 *                                                 │
 *                                    ┌────────────┴────────────┐
 *                                    │                         │
 *                            ┌───────┴───────┐       ┌────────┴────────┐
 *                            │   Checked     │       │   Unchecked     │
 *                            │  Exception    │       │ (RuntimeException│
 *                            │               │       │    )             │
 *                            └───────────────┘       └─────────────────┘
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 4. Throwable — THE ROOT CLASS (What it is)
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Definition: Throwable is the superclass of ALL errors and exceptions
 *   in Java. Only objects that are instances of Throwable (or its subclasses)
 *   can be thrown using the "throw" keyword or caught using "catch".
 *
 *   Key Methods in Throwable class:
 *
 *     ┌──────────────────────┬────────────────────────────────────────────────┐
 *     │ Method                │ What it does                                  │
 *     ├──────────────────────┼────────────────────────────────────────────────┤
 *     │ getMessage()          │ Returns the detail error message string       │
 *     │                       │ Example: "/ by zero"                          │
 *     │ toString()            │ Returns class name + message                  │
 *     │                       │ Example: "java.lang.ArithmeticException: / by zero" │
 *     │ printStackTrace()     │ Prints the full stack trace (which method      │
 *     │                       │ called which method) to the console           │
 *     │ getCause()            │ Returns the original cause (used in exception │
 *     │                       │ chaining)                                    │
 *     │ getSuppressed()       │ Returns exceptions that were suppressed       │
 *     │                       │ (used with try-with-resources)                │
 *     │ initCause(Throwable)  │ Sets the cause (if not set via constructor)   │
 *     └──────────────────────┴────────────────────────────────────────────────┘
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 5. Error — WHAT IT IS (JVM Level Problems)
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Definition: Error represents serious problems that a reasonable application
 *   should NOT try to catch or handle. These are abnormal conditions that occur
 *   at the JVM (Java Virtual Machine) level.
 *
 *   Key Characteristics of Error:
 *   ❌ You CAN catch it (syntax allows it) but you SHOULD NOT
 *   ❌ Even if you catch it, you usually CANNOT recover from it
 *   ❌ These are NOT caused by your code — they are JVM or system issues
 *   ❌ Errors are Unchecked (compiler does not force you to handle them)
 *
 *   Common Error Types:
 *
 *     ┌─────────────────────────┬─────────────────────────────────────────────┐
 *     │ Error Name               │ When it occurs                             │
 *     ├─────────────────────────┼─────────────────────────────────────────────┤
 *     │ OutOfMemoryError         │ JVM ran out of memory (too many objects)   │
 *     │ StackOverflowError       │ Infinite recursion (method calling itself   │
 *     │                          │ forever)                                   │
 *     │ NoClassDefFoundError     │ Class was available at compile-time but     │
 *     │                          │ missing at runtime                         │
 *     │ VirtualMachineError      │ JVM is broken or exhausted resources       │
 *     │ AssertionError           │ An assert statement failed                 │
 *     │ ExceptionInInitializerError│ A static initializer threw an exception  │
 *     └─────────────────────────┴─────────────────────────────────────────────┘
 *
 *   Example of StackOverflowError:
 *     public class StackOverflowExample {
 *         public static void infiniteRecursion() {
 *             infiniteRecursion();  // Calls itself forever
 *         }
 *
 *         public static void main(String[] args) {
 *             infiniteRecursion();
 *             // ⚠️ After some depth, JVM runs out of stack memory
 *             // → StackOverflowError
 *         }
 *     }
 *
 *   ⚠️ Should we catch Error? NO. Here's why:
 *     try {
 *         // some code
 *     } catch (OutOfMemoryError e) {
 *         // ❌ Pointless — JVM has no memory, catching won't create memory
 *     }
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 6. Exception — WHAT IT IS (Program Level Problems)
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Definition: Exception represents conditions that a program can reasonably
 *   expect to encounter and can handle/recover from.
 *
 *   Key Characteristics:
 *   ✅ You CAN and SHOULD catch and handle them
 *   ✅ They are caused by your application logic or external factors
 *   ✅ You CAN recover from them (retry, fallback, show user message)
 *
 *   There are TWO types of Exceptions:
 *   1. Checked Exception (Compile-time)
 *   2. Unchecked Exception (Runtime)
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 7. CHECKED EXCEPTION — Full Definition
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Definition: A checked exception is an exception that is checked at
 *   compile-time. The compiler FORCES you to handle it — either by using
 *   try-catch or by declaring it with the "throws" keyword. If you don't
 *   handle a checked exception, your code will NOT compile.
 *
 *   Key Characteristics:
 *   ✅ Compiler forces you to handle it (try-catch or throws)
 *   ✅ They occur due to EXTERNAL factors (not programming bugs)
 *   ✅ You CAN recover from them (retry, alternative approach)
 *   ✅ They are direct subclasses of Exception (NOT RuntimeException)
 *
 *   Common Checked Exceptions:
 *
 *     ┌─────────────────────────┬─────────────────────────────────────────────┐
 *     │ Exception Name           │ When it occurs                             │
 *     ├─────────────────────────┼─────────────────────────────────────────────┤
 *     │ IOException              │ An I/O operation failed or was interrupted  │
 *     │ FileNotFoundException    │ You tried to open a file that doesn't exist │
 *     │                          │ (Child of IOException)                     │
 *     │ EOFException             │ You reached end of file unexpectedly       │
 *     │ SQLException             │ Database access error (wrong credentials,  │
 *     │                          │ connection down)                           │
 *     │ ClassNotFoundException   │ You tried to load a class using            │
 *     │                          │ Class.forName() but it's not on classpath │
 *     │ InterruptedException     │ A thread was interrupted while sleeping    │
 *     │                          │ or waiting                                 │
 *     │ ParseException           │ You tried to parse a string in wrong      │
 *     │                          │ format (e.g., parsing "abc" as a date)    │
 *     │ CloneNotSupportedException │ You tried to clone an object that doesn't│
 *     │                          │ implement Cloneable                        │
 *     └─────────────────────────┴─────────────────────────────────────────────┘
 *
 *   Example — Checked Exception (Won't compile without handling):
 *     public class CheckedExample {
 *         public static void main(String[] args) {
 *             // ❌ This line will NOT compile
 *             FileReader reader = new FileReader("file.txt");
 *             // Compiler error: Unhandled exception: java.io.FileNotFoundException
 *         }
 *     }
 *
 *   ✅ Solution 1: Handle with try-catch
 *     public static void main(String[] args) {
 *         try {
 *             FileReader reader = new FileReader("file.txt");
 *             System.out.println("File opened successfully");
 *         } catch (FileNotFoundException e) {
 *             System.out.println("File not found! Using default file instead.");
 *             // Recovery: use a default configuration file
 *         }
 *         System.out.println("Program continues...");
 *     }
 *
 *   ✅ Solution 2: Declare with throws (let caller handle it)
 *     public static void main(String[] args) throws FileNotFoundException {
 *         FileReader reader = new FileReader("file.txt");
 *         // main() declares throws → JVM will handle it (print stack trace)
 *     }
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 8. UNCHECKED EXCEPTION (RuntimeException) — Full Definition
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Definition: An unchecked exception is an exception that occurs at runtime
 *   and is NOT checked by the compiler. The compiler does NOT force you to
 *   handle it. These exceptions are caused by programming bugs (logical errors)
 *   that should be FIXED in the code, not caught at runtime.
 *
 *   Key Characteristics:
 *   ❌ Compiler does NOT force you to handle them
 *   ❌ They occur due to PROGRAMMING BUGS (not external factors)
 *   ❌ You CAN catch them, but you SHOULD fix the root cause instead
 *   ❌ They are subclasses of RuntimeException
 *
 *   Common Unchecked Exceptions:
 *
 *     ┌─────────────────────────────┬─────────────────────────────────────────┐
 *     │ Exception Name               │ When it occurs (the bug in your code)  │
 *     ├─────────────────────────────┼─────────────────────────────────────────┤
 *     │ NullPointerException         │ You call a method on a null object     │
 *     │                              │ Example: String s = null; s.length();  │
 *     │ ArrayIndexOutOfBoundsException│ You try to access array with invalid   │
 *     │                              │ index                                  │
 *     │                              │ Example: int[] arr = {1,2}; arr[5];   │
 *     │ ArithmeticException          │ Division by zero                       │
 *     │                              │ Example: int x = 10 / 0;              │
 *     │ NumberFormatException        │ You try to parse a non-numeric string  │
 *     │                              │ Example: Integer.parseInt("abc");     │
 *     │ ClassCastException           │ You cast an object to wrong type       │
 *     │                              │ Example: Object x = "hello"; Integer i=│
 *     │                              │         (Integer) x;                   │
 *     │ IllegalArgumentException     │ You pass an invalid argument to method │
 *     │                              │ Example: setAge(-5)                    │
 *     │ IllegalStateException        │ You call a method when object is in    │
 *     │                              │ wrong state                            │
 *     │ IndexOutOfBoundsException    │ Index is out of range (parent of       │
 *     │                              │ ArrayIndexOutOfBoundsException)        │
 *     │ StringIndexOutOfBoundsException│ Invalid index in String              │
 *     │ UnsupportedOperationException │ Operation not supported by             │
 *     │                              │ implementation                         │
 *     │ ConcurrentModificationException│ Modifying collection while iterating │
 *     └─────────────────────────────┴─────────────────────────────────────────┘
 *
 *   Example — Unchecked Exception (Compiles fine, but crashes at runtime):
 *     public class UncheckedExample {
 *         public static void main(String[] args) {
 *             String name = null;              // name is null
 *             System.out.println(name.length()); // ⚠️ NullPointerException at RUNTIME
 *             // Code COMPILES fine — no compiler error
 *             // But CRASHES at runtime
 *         }
 *     }
 *
 *   ✅ The FIX is not to catch NullPointerException — it's to FIX THE CODE:
 *     public static void main(String[] args) {
 *         String name = "John";  // ✅ Fix: assign a real value
 *         // OR check for null:
 *         if (name != null) {
 *             System.out.println(name.length());
 *         }
 *     }
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 9. CHECKED vs UNCHECKED — COMPLETE COMPARISON
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   ┌──────────────────────┬──────────────────────────────────┬──────────────────────────────────┐
 *   │ Property              │ Checked Exception                 │ Unchecked Exception               │
 *   ├──────────────────────┼──────────────────────────────────┼──────────────────────────────────┤
 *   │ Definition            │ Exception checked at compile-time│ Exception occurs at runtime       │
 *   │                       │ Compiler FORCES you to handle it │ Compiler does NOT check it         │
 *   │ Parent Class          │ Exception (direct subclass)      │ RuntimeException                   │
 *   │ Compiler Check?       │ YES — must handle or declare    │ NO — optional to handle            │
 *   │ Cause                 │ EXTERNAL factors                 │ PROGRAMMING BUGS                   │
 *   │                       │ (File missing, network down,     │ (Null value, bad index,           │
 *   │                       │  DB connection failed)           │  wrong type cast)                  │
 *   │ Can you recover?      │ YES — retry, fallback,          │ NO — fix the code (don't catch)   │
 *   │                       │ alternative approach             │                                    │
 *   │ Should you catch?     │ YES — MUST handle               │ NO — fix the root cause instead   │
 *   │ Code compiles without │ NO — compiler ERROR              │ YES — compiles fine               │
 *   │ handling?             │                                  │                                    │
 *   │ Examples              │ IOException, FileNotFoundException│ NullPointerException               │
 *   │                       │ SQLException, ClassNotFoundException│ ArithmeticException               │
 *   │                       │ InterruptedException             │ ArrayIndexOutOfBoundsException     │
 *   │                       │                                  │ NumberFormatException              │
 *   └──────────────────────┴──────────────────────────────────┴──────────────────────────────────┘
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 10. HOW TO DECIDE — Is it Checked or Unchecked?
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   Ask yourself these questions:
 *
 *     Q: Is the error caused by EXTERNAL factors beyond my control?
 *        (File not found, network down, DB connection lost)
 *        → YES → It should be a CHECKED exception
 *
 *     Q: Is the error caused by a BUG in my code?
 *        (Null value, wrong index, bad cast, wrong argument)
 *        → YES → It should be an UNCHECKED exception (RuntimeException)
 *
 *   Rule of Thumb:
 *     ✅ Checked  = "I can recover from this" (handle it gracefully)
 *     ✅ Unchecked = "I should fix this bug" (catch only to log, then fix code)
 *
 *   Real World Example:
 *     ┌─────────────────────────────────────────────────────────────────────────┐
 *     │  ATM Machine Withdrawal:                                                │
 *     │                                                                         │
 *     │  Checked (Recoverable):                                                 │
 *     │    - Insufficient Balance → Show message "Low balance", don't dispense  │
 *     │    - Network Error → Retry transaction, show "Try again later"         │
 *     │                                                                         │
 *     │  Unchecked (Bug in Code):                                              │
 *     │    - NullPointerException → Someone forgot to initialize account obj   │
 *     │    - ArithmeticException → Division by zero in fee calculation         │
 *     └─────────────────────────────────────────────────────────────────────────┘
 *
 * ───────────────────────────────────────────────────────────────────────────
 * 11. KEY POINTS TO REMEMBER (For Interviews)
 * ───────────────────────────────────────────────────────────────────────────
 *
 *   1. Throwable is the ROOT class of all exceptions (not Exception).
 *   2. There are 3 categories: Error, Checked Exception, Unchecked Exception.
 *   3. Error = JVM level, cannot recover (OutOfMemoryError, StackOverflowError).
 *   4. Checked = compiler forces handling, external causes, can recover.
 *   5. Unchecked = compiler ignores, programming bugs, fix the code.
 *   6. RuntimeException and its subclasses = Unchecked. Everything else under
 *      Exception = Checked.
 *   7. You can catch Error technically, but you should NOT (pointless).
 *   8. Without exception handling → program crashes (abnormal termination).
 *   9. With exception handling → program continues gracefully.
 *   10. Always fix the ROOT CAUSE of unchecked exceptions, don't just catch them.
 */
public class ExceptionBasics {
}
