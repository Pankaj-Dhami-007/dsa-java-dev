package springboot_deep_drive.springboot.fllter;

/*
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                     📖 CHAPTER 08 : ONCEPERREQUESTFILTER                                  ║
║                                                                                            ║
║                          ⏱ Reading Time : 12 Minutes                                      ║
║                          📈 Difficulty : 🟡 Intermediate                                  ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝


==============================================================================================
                                    🎯 LEARNING OBJECTIVES
==============================================================================================

By the end of this chapter, you will understand:

✔ What is OncePerRequestFilter?

✔ Why did Spring create it?

✔ What problem does it solve?

✔ How is it different from GenericFilterBean?

✔ When should we use it?


==============================================================================================
                           📖 WHY WAS OncePerRequestFilter CREATED?
==============================================================================================

GenericFilterBean solved one problem.

✔ Less boilerplate code.

But another problem still existed.

A Filter could execute multiple times
for the SAME HTTP request.

For example,

Client

   │

   ▼

Filter

   │

Forward Request

   │

Filter Again ❗

The same request passed through the Filter again.

Sometimes this behavior is expected,
but many applications don't want it.

Especially when doing

✔ JWT Authentication

✔ Logging

✔ Request Tracking

✔ Security Checks


==============================================================================================
                             💡 SPRING'S SOLUTION
==============================================================================================

Spring introduced

OncePerRequestFilter

It guarantees that the Filter executes
ONLY ONCE for a single HTTP request.

Even if the request is forwarded internally,

the Filter will not execute again.


==============================================================================================
                             ⚙ CLASS HIERARCHY
==============================================================================================

                Filter (Interface)

                        ▲

                        │

          GenericFilterBean (Abstract Class)

                        ▲

                        │

        OncePerRequestFilter (Abstract Class)

                        ▲

                        │

             Your Custom Filter


Notice

OncePerRequestFilter

already extends

GenericFilterBean.


==============================================================================================
                       WHAT DO WE IMPLEMENT NOW?
==============================================================================================

Instead of overriding

doFilter()

we override

doFilterInternal()


Example

@Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)

Notice

We now receive

✔ HttpServletRequest

✔ HttpServletResponse

instead of

✔ ServletRequest

✔ ServletResponse

This removes unnecessary casting.


==============================================================================================
                             🌍 REAL WORLD ANALOGY
==============================================================================================

Imagine airport security.

Passenger enters.

Security checks passport.

After security,

the passenger moves to another terminal.

Without OncePerRequestFilter

↓

Security checks the same passenger again.

With OncePerRequestFilter

↓

Passenger is identified as already verified.

No duplicate checking.


==============================================================================================
                         🔄 NORMAL FILTER VS ONCE FILTER
==============================================================================================

Normal Filter

Request

↓

Filter

↓

Forward

↓

Filter Again

↓

Controller


------------------------------------------------------------


OncePerRequestFilter

Request

↓

Filter

↓

Forward

↓

Skipped ✅

↓

Controller


==============================================================================================
                         💼 REAL WORLD USE CASES
==============================================================================================

OncePerRequestFilter is commonly used for

✔ JWT Authentication

✔ Token Validation

✔ Logging

✔ Correlation ID

✔ Request Tracing

✔ Security Filters

This is why Spring Security heavily uses it.


==============================================================================================
                         💎 INTERVIEW POINT
==============================================================================================

Question

Why does Spring Security prefer
OncePerRequestFilter?

Answer

Because authentication should happen
only once for every request.

Repeated authentication wastes resources
and may produce inconsistent results.


==============================================================================================
                           ⚠ COMMON MISTAKES
==============================================================================================

❌ Thinking it executes only once
for the whole application.

Wrong.

It executes once

PER REQUEST.

Not

once per application.


------------------------------------------------------------

❌ Assuming it is part of Servlet API.

Wrong.

It belongs to Spring Framework.


------------------------------------------------------------

❌ Overriding doFilter()

Wrong.

Override

doFilterInternal()

instead.


==============================================================================================
                     🆚 GENERICFILTERBEAN VS ONCEPERREQUESTFILTER
==============================================================================================

┌──────────────────────────┬──────────────────────────────────────────────┐
│ GenericFilterBean        │ OncePerRequestFilter                         │
├──────────────────────────┼──────────────────────────────────────────────┤
│ Spring Class             │ Spring Class                                │
│ Less Boilerplate         │ Less Boilerplate                            │
│ Can execute multiple     │ Executes once per request                   │
│ Override doFilter()      │ Override doFilterInternal()                 │
│ Parent Class             │ Child of GenericFilterBean                  │
└──────────────────────────┴──────────────────────────────────────────────┘


==============================================================================================
                                   📝 SUMMARY
==============================================================================================

✔ OncePerRequestFilter extends GenericFilterBean.

✔ Executes only once per HTTP request.

✔ Prevents duplicate Filter execution.

✔ Override doFilterInternal().

✔ Preferred for Security Filters.

✔ Widely used in Spring Security.


==============================================================================================
                                 📚 QUICK REVISION
==============================================================================================

Filter

↓

GenericFilterBean

↓

Less Boilerplate

↓

OncePerRequestFilter

↓

One Execution Per Request


━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🧠 MEMORY TRICK

Filter

↓

GenericFilterBean

↓

Less Code

↓

OncePerRequestFilter

↓

Less Code + No Duplicate Execution

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

💎 DID YOU KNOW?

If you create a JWT Authentication Filter in Spring Boot,
you will almost always extend OncePerRequestFilter.

That's why you'll see it in most production applications
and tutorials.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

➡ Next Chapter : Filter Ordering (@Order & FilterRegistrationBean)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/

public class OncePerRequestFilterDeepDive {

}