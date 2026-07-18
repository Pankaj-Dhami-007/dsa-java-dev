package springboot_deep_drive.springboot.fllter;

/*
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                      📖 CHAPTER 07 : GENERICFILTERBEAN                                    ║
║                                                                                            ║
║                          ⏱ Reading Time : 10 Minutes                                      ║
║                          📈 Difficulty : 🟡 Intermediate                                  ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝


==============================================================================================
                                    🎯 LEARNING OBJECTIVES
==============================================================================================

By the end of this chapter, you will understand:

✔ What is GenericFilterBean?

✔ Why did Spring create GenericFilterBean?

✔ Problems with implementing Filter directly.

✔ Advantages of GenericFilterBean.

✔ When should we use GenericFilterBean?


==============================================================================================
                           📖 WHY WAS GenericFilterBean CREATED?
==============================================================================================

Initially, developers created Filters like this.

public class LoggingFilter implements Filter {

}

This works perfectly.

However,

every Filter had to implement all three lifecycle methods.

✔ init()

✔ doFilter()

✔ destroy()

Even if init() and destroy() were not required,
developers still had to write them.

Result

Lots of unnecessary boilerplate code.


==============================================================================================
                              😓 PROBLEM WITH Filter
==============================================================================================

Example

public class LoggingFilter implements Filter {

    @Override
    public void init(...) {

    }

    @Override
    public void doFilter(...) {

    }

    @Override
    public void destroy() {

    }

}

Notice

Most Filters only need

✔ doFilter()

The other methods are usually empty.


==============================================================================================
                          💡 SPRING'S SOLUTION
==============================================================================================

Spring Framework introduced

GenericFilterBean

It is an abstract class.

Instead of implementing the Filter interface,

we simply extend GenericFilterBean.


Example

public class LoggingFilter extends GenericFilterBean {

}

Now,

Spring already provides default implementations
for unnecessary lifecycle methods.


==============================================================================================
                         ⚙ CLASS HIERARCHY
==============================================================================================

                Filter (Interface)

                        ▲

                        │

          GenericFilterBean (Abstract Class)

                        ▲

                        │

              Your Custom Filter


==============================================================================================
                     WHAT DO WE NEED TO IMPLEMENT NOW?
==============================================================================================

Only one method.

@Override
public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain)

That's it.

No need to implement

❌ init()

❌ destroy()

unless required.


==============================================================================================
                           🌍 REAL WORLD ANALOGY
==============================================================================================

Imagine every employee receives
a standard office setup.

✔ Laptop

✔ Email

✔ Desk

✔ ID Card

Now the employee only focuses on work.

GenericFilterBean works exactly like that.

Spring provides the common setup,

and we only write the Filter logic.


==============================================================================================
                           ✅ ADVANTAGES
==============================================================================================

✔ Less Boilerplate Code.

✔ Cleaner Implementation.

✔ Easier to Read.

✔ Spring Friendly.

✔ Default Lifecycle Handling.


==============================================================================================
                         ⚠ LIMITATIONS
==============================================================================================

GenericFilterBean does NOT solve

multiple execution problems.

If a request is forwarded,

the Filter may execute again.

This problem is solved by

OncePerRequestFilter

which we'll study next.


==============================================================================================
                           💎 INTERVIEW POINT
==============================================================================================

Question

Why use GenericFilterBean?

Answer

Because it removes unnecessary lifecycle code
and lets developers focus only on doFilter().


==============================================================================================
                            ⚠ COMMON MISTAKES
==============================================================================================

❌ Thinking GenericFilterBean executes only once.

Wrong.

It behaves exactly like a normal Filter.

The only difference is

less boilerplate code.


------------------------------------------------------------

❌ Assuming it belongs to the Servlet API.

Wrong.

GenericFilterBean belongs to Spring Framework.

It is NOT part of the Servlet API.


==============================================================================================
                              📝 SUMMARY
==============================================================================================

Filter Interface

↓

Need to implement

✔ init()

✔ doFilter()

✔ destroy()


GenericFilterBean

↓

Need to implement only

✔ doFilter()


==============================================================================================
                              📚 QUICK REVISION
==============================================================================================

✔ GenericFilterBean is a Spring class.

✔ It implements the Filter interface internally.

✔ It removes unnecessary lifecycle methods.

✔ Most custom Spring Filters extend GenericFilterBean.

✔ It does NOT guarantee single execution.


━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🧠 MEMORY TRICK

Filter

↓

Too Much Boilerplate

↓

GenericFilterBean

↓

Less Code

↓

Focus Only on doFilter()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

💎 DID YOU KNOW?

Spring Security internally builds many filters
using Spring's filter infrastructure rather than
developers implementing the raw Filter interface directly.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

➡ Next Chapter : OncePerRequestFilter

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/

public class GenericFilterBeanDeepDive {

}