package springboot_deep_drive.springboot.fllter;

/*
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                      📖 CHAPTER 09 : FILTER ORDERING                                      ║
║                                                                                            ║
║                          ⏱ Reading Time : 5 Minutes                                       ║
║                          📈 Difficulty : 🟢 Beginner                                      ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝


==============================================================================================
                                    🎯 LEARNING OBJECTIVES
==============================================================================================

By the end of this chapter, you will understand:

✔ What is Filter Ordering?

✔ Why is Filter Ordering important?

✔ How Spring Boot decides which Filter executes first?

✔ How to control Filter execution order.


==============================================================================================
                                📖 WHAT IS FILTER ORDERING?
==============================================================================================

A Spring Boot application can contain multiple Filters.

Example

    ✔ Logging Filter
    ✔ JWT Filter
    ✔ CORS Filter
    ✔ Audit Filter

Now the question is...

Who should execute first?

The sequence in which Filters execute is called

                            ★ Filter Ordering ★


==============================================================================================
                              🌍 REAL WORLD ANALOGY
==============================================================================================

Imagine Airport Security.

                     Passenger
                          │
                          ▼
               ┌────────────────────┐
               │ Identity Check     │
               └────────────────────┘
                          │
                          ▼
               ┌────────────────────┐
               │ Passport Check     │
               └────────────────────┘
                          │
                          ▼
               ┌────────────────────┐
               │ Security Scan      │
               └────────────────────┘
                          │
                          ▼
                     Boarding

If Security Scan happens BEFORE Identity Check,
the entire process becomes incorrect.

Exactly the same concept applies to Filters.


==============================================================================================
                               ⚙ FILTER EXECUTION FLOW
==============================================================================================

                          HTTP REQUEST

                    ┌──────────────┐
                    │    Client    │
                    └──────┬───────┘
                           │
                           ▼
                ┌────────────────────┐
                │ Logging Filter     │
                └─────────┬──────────┘
                          │
                          ▼
                ┌────────────────────┐
                │ JWT Filter         │
                └─────────┬──────────┘
                          │
                          ▼
                ┌────────────────────┐
                │ CORS Filter        │
                └─────────┬──────────┘
                          │
                          ▼
                ┌────────────────────┐
                │ DispatcherServlet  │
                └─────────┬──────────┘
                          │
                          ▼
                ┌────────────────────┐
                │ Controller         │
                └────────────────────┘


                         HTTP RESPONSE

                ┌────────────────────┐
                │ Controller         │
                └─────────┬──────────┘
                          │
                          ▼
                ┌────────────────────┐
                │ DispatcherServlet  │
                └─────────┬──────────┘
                          │
                          ▼
                ┌────────────────────┐
                │ CORS Filter        │
                └─────────┬──────────┘
                          │
                          ▼
                ┌────────────────────┐
                │ JWT Filter         │
                └─────────┬──────────┘
                          │
                          ▼
                ┌────────────────────┐
                │ Logging Filter     │
                └─────────┬──────────┘
                          │
                          ▼
                    ┌──────────────┐
                    │    Client    │
                    └──────────────┘


💡 Observation

Request  → Top to Bottom

Response → Bottom to Top


==============================================================================================
                          📦 HOW TO DEFINE THE ORDER?
==============================================================================================

Spring Boot provides two common approaches.

✔ @Order

✔ FilterRegistrationBean#setOrder()


Smaller Order Value

            =

Higher Priority


Example

    @Order(1)  → Executes First

    @Order(2)  → Executes Second

    @Order(3)  → Executes Third


==============================================================================================
                               🎯 INTERVIEW POINT
==============================================================================================

Question

Which Filter executes first?

Answer

The Filter having the LOWEST Order value.


Example

@Order(1)

↓

Runs before

↓

@Order(2)

↓

Runs before

↓

@Order(3)


==============================================================================================
                              ⚠ COMMON MISTAKES
==============================================================================================

❌ Assuming Filters execute randomly.

Wrong.

They execute according to their configured Order.


❌ Forgetting to define Order when multiple Filters exist.

This can produce unexpected behavior.


==============================================================================================
                                   📝 SUMMARY
==============================================================================================

✔ Multiple Filters execute one after another.

✔ Their execution sequence is called Filter Ordering.

✔ Lower Order value = Higher Priority.

✔ Request moves in configured order.

✔ Response returns in reverse order.


━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🧠 MEMORY TRICK

Order(1)

↓

Order(2)

↓

Order(3)

↓

Controller

↓

Order(3)

↓

Order(2)

↓

Order(1)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

➡ Next Chapter : Filter vs Interceptor

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/

public class FilterOrderingDeepDive {

}