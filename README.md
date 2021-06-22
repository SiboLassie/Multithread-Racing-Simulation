# Introduction
Design patterns are the best formalized practices a programmer can use to solve common problems when designing an application or system.
Design patterns can speed up the development process by providing tested, proven development paradigms.
Reusing design patterns help prevent subtle issues that cause major problems, and it also improves code readability for coders and architects who are familiar with the patterns.

Multi-threading extends the idea of multitasking into applications where you can subdivide specific operations within a single application into individual threads. 
Each of the threads can run in parallel. The OS divides processing time not only among different applications, but also among each thread within an application.
Multi-threading enables you to write in a way where multiple activities can proceed concurrently in the same program.

# Multithread-Racing-Simulation
a basic Racing Game that using a separate thread for each racer, using ExecutorService to create a thread pool, the race is simulating a Multithreaded process. 
this project also focusing on the implementation of Java Design Patterns.

Interactive GUI- the user can create a race uses the right parameters.

There are 3 types of maps: Air, Land, Naval, and 7 types of racers: plane, helicopter, car, bycicle, horse, speedboat, rowboat.

Mishap system- During the race there is a probability that one of the racers will go through an event (mishap), the state of the racer will change accordingly.

Exceptions system- all exceptions for the right parameters for each input and the logic of the race are handled and display the user an appropriate message.

<img src = "readme_imgs.jpg">

Design Patterns:

   Thread pool- using ExecutorService to create a Thread pool, each racer is inherit Runnable interface and can be run separately in the Thread pool using a queue.

   State- there are 4 states classes: active, broken, completed, disabled. used for the Mishaps system.

   Factory- use an "Arena Factory" to build an arena for the race, There are different types of arenas, using Reflection to load each arena constructor and get the right type.

   Builder- using Builder to build a simple Cars Race and also to build an arena and racers for the race , using Reflection to load each arena/racer constructor and get the right type.

   Decorator- using Decorator to decorate an existing racer instance and change his parameters like color etc..

   Prototype- Prototype receives any racer, or racer subclass that inherit from Cloneable, makes a copy of it and stores it, Prototype has no idea what these objects are, except that they are subclasses of racer.

   Observer- The Observer "notifyMsg" method is called when the subject changes, GUI is the Observer that get a message from an Observable and act matching, each racer is an observable that notify the Observer with a message of current state.

   Singleton- use a "RaceBuilder" Singleton to build the right arena and the matching racers objects.

