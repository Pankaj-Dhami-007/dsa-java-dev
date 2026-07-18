package springboot_deep_drive.spring_core;

/**
 * ============================================================================
 *                     IoC AND DI RELATIONSHIP - DEEP DIVE
 * ============================================================================
 *
 * Most Spring beginners memorize:
 *
 *      IoC = Inversion Of Control
 *      DI  = Dependency Injection
 *
 * But interviewers usually ask:
 *
 *      "What is the relationship between IoC and DI?"
 *
 * If you cannot answer that, it means concepts are not clear.
 *
 *
 * ============================================================================
 * FIRST UNDERSTAND THE BIGGEST MYTH
 * ============================================================================
 *
 * Many developers think:
 *
 *      IoC == DI
 *
 * WRONG ❌
 *
 * They are related but NOT the same thing.
 *
 *
 * ============================================================================
 * SIMPLE RELATIONSHIP
 * ============================================================================
 *
 * IoC = Goal
 *
 * DI = One Technique To Achieve That Goal
 *
 *
 * Think:
 *
 *      Destination = Delhi
 *      Vehicle = Car
 *
 * Delhi is not Car.
 *
 * Car helps you reach Delhi.
 *
 *
 * Similarly:
 *
 *      IoC is the destination.
 *      DI is the vehicle.
 *
 *
 * ============================================================================
 * WHAT IS IoC?
 * ============================================================================
 *
 * Definition:
 *
 * Inversion Of Control means transferring the control of object creation,
 * object management and dependency management from application code
 * to an external framework/container.
 *
 *
 * Normal Java:
 *
 * Developer controls object creation.
 *
 *
 * Spring:
 *
 * Spring Container controls object creation.
 *
 *
 * Control is inverted.
 *
 *
 * ============================================================================
 * WHAT IS DI?
 * ============================================================================
 *
 * Definition:
 *
 * Dependency Injection means providing dependencies from outside
 * instead of creating them inside the class.
 *
 *
 * Instead of:
 *
 *      new EmailService()
 *
 * inside CustomerService,
 *
 * EmailService is supplied from outside.
 *
 *
 * ============================================================================
 * RELATIONSHIP IN ONE LINE
 * ============================================================================
 *
 * DI is one of the most popular ways to implement IoC.
 *
 *
 * ============================================================================
 * REAL LIFE EXAMPLE #1 - RESTAURANT
 * ============================================================================
 *
 * Without IoC:
 *
 * You go into kitchen.
 * You cook food yourself.
 * You clean utensils.
 * You serve yourself.
 *
 * You control everything.
 *
 *
 * With IoC:
 *
 * Restaurant handles everything.
 *
 * You simply order.
 *
 *
 * Control shifted:
 *
 *      YOU
 *          ↓
 *      RESTAURANT
 *
 *
 * That shift is IoC.
 *
 *
 * ============================================================================
 * WHERE IS DI IN THIS EXAMPLE?
 * ============================================================================
 *
 * You order:
 *
 *      Paneer Butter Masala
 *
 *
 * Restaurant provides food.
 *
 * You don't create food.
 *
 *
 * Food is injected into your table.
 *
 *
 * That's DI.
 *
 *
 * ============================================================================
 * REAL LIFE EXAMPLE #2 - CAR
 * ============================================================================
 *
 * Imagine a Car.
 *
 * Car depends upon Engine.
 *
 *
 * BAD DESIGN:
 *
 * class Car{
 *
 *      Engine engine = new Engine();
 *
 * }
 *
 *
 * Car creates engine itself.
 *
 *
 * ============================================================================
 * PROBLEM
 * ============================================================================
 *
 * What if tomorrow:
 *
 * PetrolEngine
 * DieselEngine
 * ElectricEngine
 *
 * ?
 *
 *
 * Car class must change.
 *
 *
 * Tight Coupling.
 *
 *
 * ============================================================================
 * BETTER DESIGN
 * ============================================================================
 *
 * class Car{
 *
 *      private Engine engine;
 *
 *      public Car(Engine engine){
 *          this.engine = engine;
 *      }
 *
 * }
 *
 *
 * Engine comes from outside.
 *
 *
 * This is DI.
 *
 *
 * ============================================================================
 * WHO CREATES ENGINE?
 * ============================================================================
 *
 * Option 1:
 *
 * Developer creates.
 *
 * Option 2:
 *
 * Spring creates.
 *
 *
 * When Spring creates and injects:
 *
 * IoC + DI together.
 *
 *
 * ============================================================================
 * BEFORE SPRING
 * ============================================================================
 *
 * interface NotificationService{}
 *
 * class EmailService
 *      implements NotificationService{}
 *
 *
 * class CustomerService{
 *
 *      NotificationService service;
 *
 *      CustomerService(NotificationService service){
 *          this.service = service;
 *      }
 *
 * }
 *
 *
 * Main Class:
 *
 * NotificationService service =
 *      new EmailService();
 *
 * CustomerService customerService =
 *      new CustomerService(service);
 *
 *
 * ============================================================================
 * IMPORTANT OBSERVATION
 * ============================================================================
 *
 * DI is happening.
 *
 * Because dependency comes from outside.
 *
 *
 * BUT
 *
 * Spring is not involved.
 *
 *
 * IoC Container is not involved.
 *
 *
 * ============================================================================
 * AFTER SPRING
 * ============================================================================
 *
 * @Service
 * class EmailService
 *      implements NotificationService{}
 *
 *
 * @Service
 * class CustomerService{
 *
 *      private final NotificationService service;
 *
 *      public CustomerService(
 *              NotificationService service){
 *
 *          this.service = service;
 *      }
 *
 * }
 *
 *
 * Spring Container:
 *
 * 1. Creates EmailService
 * 2. Creates CustomerService
 * 3. Injects EmailService
 * 4. Manages Lifecycle
 *
 *
 * ============================================================================
 * WHAT CHANGED?
 * ============================================================================
 *
 * Before:
 *
 * Developer performed wiring.
 *
 *
 * After:
 *
 * Spring performs wiring.
 *
 *
 * This is IoC.
 *
 *
 * ============================================================================
 * CONTROL FLOW COMPARISON
 * ============================================================================
 *
 * WITHOUT SPRING
 *
 * Developer
 *      ↓
 * Creates Objects
 *      ↓
 * Connects Objects
 *      ↓
 * Manages Objects
 *
 *
 *
 * WITH SPRING
 *
 * Developer
 *      ↓
 * Defines Beans
 *      ↓
 * Spring Container
 *      ↓
 * Creates Objects
 *      ↓
 * Injects Dependencies
 *      ↓
 * Manages Lifecycle
 *
 *
 * ============================================================================
 * WHY IoC WAS NEEDED?
 * ============================================================================
 *
 * Small Project:
 *
 * 5 Classes
 *
 * Manual Wiring Easy.
 *
 *
 * Large Project:
 *
 * 500 Classes
 *
 * Manual Wiring Nightmare.
 *
 *
 * Spring Container automates:
 *
 * ✓ Object Creation
 * ✓ Dependency Resolution
 * ✓ Lifecycle Management
 * ✓ Configuration Management
 *
 *
 * ============================================================================
 * COMMON INTERVIEW QUESTION
 * ============================================================================
 *
 * Q. Can Dependency Injection exist without Spring?
 *
 * YES.
 *
 * Example:
 *
 * Constructor Injection in Core Java.
 *
 *
 * Spring did not invent DI.
 *
 *
 * ============================================================================
 * COMMON INTERVIEW QUESTION
 * ============================================================================
 *
 * Q. Did Spring invent IoC?
 *
 * NO.
 *
 * IoC is a design principle.
 *
 * Spring popularized and implemented it effectively.
 *
 *
 * ============================================================================
 * COMMON INTERVIEW QUESTION
 * ============================================================================
 *
 * Q. Can IoC exist without DI?
 *
 * YES.
 *
 * IoC can be achieved using:
 *
 * - Dependency Injection
 * - Service Locator
 * - Factory Pattern
 * - Template Method Pattern
 *
 *
 * DI is only one way.
 *
 *
 * ============================================================================
 * COMMON INTERVIEW QUESTION
 * ============================================================================
 *
 * Q. Which is more important, IoC or DI?
 *
 * IoC.
 *
 * Because IoC is the bigger concept.
 *
 * DI is one implementation technique.
 *
 *
 * ============================================================================
 * INTERVIEW READY ANSWER
 * ============================================================================
 *
 * Q. Explain relationship between IoC and DI.
 *
 * Answer:
 *
 * Inversion Of Control (IoC) is a design principle where the control of
 * object creation and dependency management is transferred from application
 * code to a container or framework.
 *
 * Dependency Injection (DI) is a technique used to achieve IoC by supplying
 * dependencies from outside rather than creating them inside the class.
 *
 * In Spring Framework, the IoC Container achieves IoC using Dependency
 * Injection. Therefore, IoC is the goal and DI is the mechanism used
 * to achieve that goal.
 *
 *
 * ============================================================================
 * 30 SECOND REVISION
 * ============================================================================
 *
 * IoC
 * ----
 * Control transferred to framework.
 *
 * DI
 * ----
 * Dependencies supplied from outside.
 *
 * Relationship
 * ------------
 * IoC = Goal
 * DI  = Technique
 *
 * Spring
 * ------
 * Spring Container achieves IoC using DI.
 *
 * DI Without Spring?
 * ------------------
 * YES
 *
 * IoC Without DI?
 * ---------------
 * YES
 *
 * Spring Invented DI?
 * -------------------
 * NO
 *
 * Spring Invented IoC?
 * --------------------
 * NO
 *
 * Spring Popularized Both?
 * ------------------------
 * YES
 *
 * ============================================================================
 */

public class IocAndDiRelationShip {

}