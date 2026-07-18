package designpattern.behavioral.command;

import java.util.ArrayList;
import java.util.List;

public class CommandDesignPattern {

    /*
        ==========================================================
        COMMAND DESIGN PATTERN
        ==========================================================

        VERY IMPORTANT behavioral design pattern.

        Heavily used in:
        - undo/redo systems
        - task queues
        - job schedulers
        - remote controls
        - transaction systems
        - Spring Framework

     */



    /*
        ==========================================================
        MAIN IDEA
        ==========================================================

        Convert request into object.

     */



    /*
        ==========================================================
        SIMPLE DEFINITION
        ==========================================================

        Command Pattern encapsulates
        request/action as object.

     */



    /*
        ==========================================================
        VERY SIMPLE UNDERSTANDING
        ==========================================================

        Instead of directly calling method,

        create command object
        representing action.

     */



    /*
        ==========================================================
        REAL LIFE ANALOGY
        ==========================================================

        Restaurant ordering system.

        Customer gives order.

        Waiter writes order ticket.

        Chef executes order later.

        ----------------------------------------------------------

        Customer:
            Client

        Waiter Ticket:
            Command Object

        Chef:
            Receiver

     */



    /*
        ==========================================================
        MAIN PROBLEM
        ==========================================================

        Tight coupling between:
        sender and receiver.

        Example:

            button.pressTVOn()

        Button directly tied to TV.

     */



    /*
        ==========================================================
        COMMAND SOLUTION
        ==========================================================

        Encapsulate operation inside
        separate command object.

        Then sender only executes command.

     */



    public static void main(String[] args) {

        /*
            Receiver object.

            Actual business logic holder.
         */
        TV tv = new TV();



        /*
            Command object.

            Encapsulates request.
         */
        Command turnOnCommand =
                new TurnOnTVCommand(tv);



        Command turnOffCommand =
                new TurnOffTVCommand(tv);



        /*
            Invoker object.
         */
        RemoteControl remote =
                new RemoteControl();



        /*
            Execute commands.
         */
        remote.setCommand(turnOnCommand);

        remote.pressButton();



        remote.setCommand(turnOffCommand);

        remote.pressButton();



        /*
            OUTPUT:

            TV Turned ON

            TV Turned OFF

         */



        /*
            ======================================================
            MAIN UNDERSTANDING
            ======================================================

            RemoteControl does NOT know:
            - TV internals
            - ON/OFF logic

            It only executes command object.

         */



        /*
            ======================================================
            INTERNAL FLOW
            ======================================================

            STEP-1:
            Client creates receiver.

            STEP-2:
            Client creates command object.

            STEP-3:
            Invoker receives command.

            STEP-4:
            Invoker executes command.

            STEP-5:
            Command delegates work to receiver.

         */



        /*
            ======================================================
            VERY IMPORTANT CONCEPT
            ======================================================

            Request becomes OBJECT.

            This enables:
            - queueing
            - undo/redo
            - delayed execution
            - scheduling

         */



        /*
            ======================================================
            WHY COMMAND PATTERN IMPORTANT?
            ======================================================

            Real systems need:
            - task scheduling
            - async processing
            - retry systems
            - transaction commands

         */



        /*
            ======================================================
            UNDO/REDO SYSTEM
            ======================================================

            Commands can store previous state.

            Then undo() possible.

            Example:
            - text editors
            - Photoshop
            - IDEs

         */



        /*
            ======================================================
            TASK QUEUE SYSTEMS
            ======================================================

            Commands stored in queue.

            Workers execute later.

            Example:
            - job schedulers
            - Kafka consumers
            - async tasks

         */



        /*
            ======================================================
            SPRING FRAMEWORK USAGE
            ======================================================

            Spring task executors
            and schedulers use
            command-like concepts.

         */



        /*
            ======================================================
            COMPOSITION OVER INHERITANCE
            ======================================================

            Command HAS-A Receiver.

         */



        /*
            ======================================================
            KEY PLAYERS
            ======================================================

            1. Command
                action abstraction

            2. Concrete Command
                actual request implementation

            3. Receiver
                actual business logic

            4. Invoker
                triggers command

            5. Client
                creates everything

         */



        /*
            ======================================================
            REAL WORLD USAGE
            ======================================================

            Remote controls

            Payment processing queues

            Transaction systems

            Job schedulers

            Event queues

         */



        /*
            ======================================================
            COMMAND vs STRATEGY
            ======================================================

            Strategy:
            interchangeable algorithms

            Command:
            encapsulated request/action

         */



        /*
            ======================================================
            COMMAND vs OBSERVER
            ======================================================

            Observer:
            notification system

            Command:
            request encapsulation

         */



        /*
            ======================================================
            ADVANTAGES
            ======================================================

            - loose coupling
            - undo/redo support
            - queue support
            - delayed execution
            - command history

         */



        /*
            ======================================================
            DISADVANTAGES
            ======================================================

            - many command classes
            - complexity increases

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Main purpose of Command Pattern?

            Answer:
            Encapsulate request as object.



            Q2:
            Real-world analogy?

            Answer:
            Restaurant order ticket.



            Q3:
            Biggest advantage?

            Answer:
            Loose coupling between sender and receiver.



            Q4:
            Which systems heavily use it?

            Answer:
            Undo/Redo systems.



            Q5:
            What enables queueing/scheduling?

            Answer:
            Request converted into object.



            Q6:
            Who executes actual business logic?

            Answer:
            Receiver.



            Q7:
            Invoker knows business logic?

            Answer:
            No.



            Q8:
            Difference from Strategy?

            Answer:

            Strategy:
            algorithm selection

            Command:
            action encapsulation

         */



        /*
            ======================================================
            IS COMMAND PATTERN COMPLETE?
            ======================================================

            YES BASIC TO INTERMEDIATE COMPLETE.

            Next Important Behavioral Pattern:

                CHAIN OF RESPONSIBILITY

         */

    }

}



/*
    ==========================================================
    COMMAND INTERFACE
    ==========================================================

    Common command contract.
 */
interface Command {

    void execute();
}



/*
    ==========================================================
    RECEIVER
    ==========================================================

    Actual business logic class.
 */
class TV {

    public void turnOn() {

        System.out.println("TV Turned ON");
    }



    public void turnOff() {

        System.out.println("TV Turned OFF");
    }

}



/*
    ==========================================================
    CONCRETE COMMAND-1
    ==========================================================

    Encapsulates ON request.
 */
class TurnOnTVCommand implements Command {

    /*
        HAS-A relationship.
     */
    private TV tv;



    public TurnOnTVCommand(TV tv) {

        this.tv = tv;
    }



    @Override
    public void execute() {

        tv.turnOn();
    }

}



/*
    ==========================================================
    CONCRETE COMMAND-2
    ==========================================================

    Encapsulates OFF request.
 */
class TurnOffTVCommand implements Command {

    private TV tv;



    public TurnOffTVCommand(TV tv) {

        this.tv = tv;
    }



    @Override
    public void execute() {

        tv.turnOff();
    }

}



/*
    ==========================================================
    INVOKER
    ==========================================================

    Triggers command execution.
 */
class RemoteControl {

    private Command command;



    public void setCommand(Command command) {

        this.command = command;
    }



    /*
        Invoker executes command
        without knowing actual logic.
     */
    public void pressButton() {

        command.execute();
    }

}