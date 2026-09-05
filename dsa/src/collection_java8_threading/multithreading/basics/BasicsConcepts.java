package EngineeringDigest.multithreading.basics;

public class BasicsConcepts {

    public static void main(String[] args) {

    }
}

/*

Step 1 — First Understand the Computer
   A computer mainly has:
CPU → brain (does calculations/work)
RAM → temporary memory
Storage → permanent memory (SSD/HDD)

Step 2 — What is a CPU Core?
Inside CPU there are small workers called cores.

Example:
Dual core → 2 workers
Quad core → 4 workers
Octa core → 8 workers
Each core can execute instructions.
More cores = more work at the same time.

Step 3 — What is a Program?

A program is just a file containing instructions.

Examples:
Chrome.exe
Spotify.exe
Your Java .jar
VS Code

At rest, it’s only code stored on disk.
It is NOT running yet.

Step 4 — What is a Process?

When you RUN a program,
the OS (Windows/Linux/Mac) creates a process.

A process is:

A running instance of a program.
Example:
You double-click Chrome
OS creates a Chrome process

Now Chrome gets:
memory
CPU time
resources
files
network access


Step 5 — Process Memory

Each process gets its own memory space.
This is called process isolation.

That’s why:
if Spotify crashes,
Chrome usually still works.

Important Understanding

A process is:

heavyweight
isolated
expensive to create
has its own memory

Because of this, creating too many processes is costly.
That’s where threads come in.
And THAT is why multithreading exists.

"
Process
A process is an independent executing instance of a program having its own memory space and system resources.
"
*******
What is a Computer Actually?

A computer is a machine that:
Takes instructions
Executes instructions
Gives output

Example instruction:

add two numbers
open Chrome
play music
print text

But WHO executes instructions?   -> CPU

CPU = Brain of Computer

CPU stands for: Central Processing Unit

It is the actual worker of the computer.
Everything eventually goes to CPU.

When you:

open YouTube
run Java code
play games
use calculator

CPU is doing the work.



 */

// thread
/*

A process contains:

memory
resources
files
heap
code

BUT… Who actually executes instructions? -> Thread

Thread is the actual unit of execution.
Process provides environment.
Thread does the work.

thread -> A thread is the smallest unit of execution inside a process.
One Process Can Have Multiple Threads


 */

// memory

/*

A process can exist with:   multiple threads
But each thread shares:

heap memory
code segment
process resources

This sharing is VERY important.

Process vs Thread Memory

Suppose process memory is:

Process Memory
-----------------
Code
Heap
Files
Resources
-----------------

Now inside process:

Thread 1
Thread 2
Thread 3

All threads SHARE:

heap
code
files

BUT each thread has its OWN:

stack
program counter
registers

Why Separate Stack?

Because each thread executes independently.
t1 -> login, t2-> downloading
Both need separate execution history.
So each thread gets its own stack.

Thread Components

Each thread contains:

Component	Purpose
Stack	Method calls/local vars
Program Counter	Current instruction
Registers	CPU working values
State	Running/waiting/etc



 */

// multi tasking
// Multitasking is the ability of an operating system to execute multiple tasks (processes or threads)
// concurrently by managing and switching CPU time between them.

// multithreading
// it refer to the ability to execute multiple thread within a single process concurrently
