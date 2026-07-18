package springboot_deep_drive.springboot.fllter;

/*
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                           📖 CHAPTER 03 : FILTER CHAIN                                    ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝


==============================================================================================
                                    🎯 LEARNING OBJECTIVES
==============================================================================================

By the end of this chapter, you will understand:

✔ What is a Filter Chain?
✔ Why do we need multiple Filters?
✔ How does chain.doFilter() work?
✔ What happens if chain.doFilter() is not called?
✔ How Request and Response travel through the Filter Chain.


==============================================================================================
                                   📖 WHAT IS FILTER CHAIN?
==============================================================================================

A Filter Chain is an ordered collection of Filters.

Instead of putting all logic inside one Filter,
we divide responsibilities into multiple Filters.

Each Filter performs one specific task and then forwards
the request to the next Filter.

Example

Filter 1  → Logging

Filter 2  → Authentication

Filter 3  → JWT Validation

Controller → Business Logic

This sequence is called a Filter Chain.


==============================================================================================
                               🌍 REAL WORLD ANALOGY
==============================================================================================

Imagine entering an airport.

Passenger

      │

      ▼

Security Check

      │

      ▼

Passport Verification

      │

      ▼

Baggage Scan

      │

      ▼

Boarding

Each checkpoint performs only one responsibility.

Exactly the same happens inside a Filter Chain.


==============================================================================================
                                   ⚙ FILTER CHAIN FLOW
==============================================================================================

                          Incoming HTTP Request

Client
   │
   ▼
Filter 1
   │
   ▼
Filter 2
   │
   ▼
Filter 3
   │
   ▼
DispatcherServlet
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository


=========================== RESPONSE ===========================

Repository
   │
Service
   │
Controller
   │
DispatcherServlet
   │
Filter 3
   │
Filter 2
   │
Filter 1
   │
Client


Notice

✔ Request moves downward.

✔ Response moves upward.

✔ Every Filter gets two chances.

    Before Controller

    After Controller


==============================================================================================
                          🤔 WHY DO WE NEED FILTER CHAINS?
==============================================================================================

Imagine only one Filter.

It would contain:

✔ Logging

✔ Authentication

✔ JWT Validation

✔ CORS

✔ Timing

✔ Header Validation

Result

❌ Huge Class

❌ Difficult Maintenance

❌ Hard Testing

Instead,

Create multiple Filters.

Each Filter performs only one task.

This follows the

Single Responsibility Principle (SRP).


==============================================================================================
                             ⚙ HOW DOES chain.doFilter() WORK?
==============================================================================================

This is the most important line inside every Filter.

chain.doFilter(request, response);

Meaning

"I have completed my work.

Now pass the request to the next Filter."

Flow

Filter 1

↓

chain.doFilter()

↓

Filter 2

↓

chain.doFilter()

↓

Filter 3

↓

chain.doFilter()

↓

DispatcherServlet

↓

Controller


Without chain.doFilter(),

the request stops immediately.


==============================================================================================
                     ❌ WHAT IF chain.doFilter() IS NOT CALLED?
==============================================================================================

Suppose Filter 2 never calls

chain.doFilter(request, response);

Flow

Client

↓

Filter 1

↓

Filter 2

❌ STOP

Controller is never reached.

Response is never generated.

Remaining Filters are skipped.


This is one of the most common beginner mistakes.


==============================================================================================
                             🎯 INTERVIEW QUESTION
==============================================================================================

Q)

Why do we call chain.doFilter()?

Answer

It forwards the request to the next Filter in the chain.

If there is no next Filter,

the request reaches the DispatcherServlet.


==============================================================================================
                             💡 IMPORTANT OBSERVATION
==============================================================================================

Each Filter executes twice.

First Time

Before Controller

Second Time

After Controller returns the Response.


Flow

Before Request

↓

Filter 1

↓

Filter 2

↓

Controller

↓

Controller Returns

↓

Filter 2

↓

Filter 1

↓

Client


This allows a Filter to

✔ Modify Request

✔ Modify Response

✔ Log Execution Time

✔ Add Headers


==============================================================================================
                              ⚠ COMMON MISTAKES
==============================================================================================

❌ Forgetting chain.doFilter()

Result

Request never reaches Controller.


------------------------------------------------------------

❌ Writing all logic in one Filter.

Instead,

Create multiple Filters.


------------------------------------------------------------

❌ Assuming Response skips Filters.

Wrong.

Response also passes through every Filter.


==============================================================================================
                                    📝 SUMMARY
==============================================================================================

✔ Multiple Filters together form a Filter Chain.

✔ Each Filter performs one responsibility.

✔ chain.doFilter() forwards the request.

✔ Without chain.doFilter(), processing stops.

✔ Response travels back through every Filter.

✔ Filter Chain follows the Single Responsibility Principle.


==============================================================================================
                                  📚 QUICK REVISION
==============================================================================================

✔ Filter Chain = Collection of Filters.

✔ Request moves Filter → Filter → Controller.

✔ Response moves Controller → Filter → Client.

✔ chain.doFilter() forwards the request.

✔ Forgetting chain.doFilter() blocks the request.


━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

⏱ Reading Time : 10 Minutes

📈 Difficulty : 🟢 Beginner

➡ Next Chapter : doFilter() Internal Working

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/

public class FilterChainDeepDive {

}