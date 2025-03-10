# General Description

Design in Java a set of classes for a (very simplified) simulation of urban traffic. Ensure that your solution is object‐oriented, particularly enabling the simulation to be extended with new elements.

## Simulation Elements

The simulation should include, among others, the following elements:
- **Passengers**
- **Vehicles (in this simulation, only trams)**
- **Tram Lines**
- **Stops**

Vehicles may vary, but each has a side number and is assigned to a specific line. Each line has its route described in the provided data (see "data"). The route also specifies the dwell time at the terminus (loop). A vehicle always follows the same route: it travels one way on its route, stops at the loop, then returns (using the same route) and stops at the loop again. (Note: Trams are considered vehicles.) 

The first trams start their journey at 6:00. After 23:00, trams only complete the trips they have already started. (For instance, if an unlucky driver departs from the loop at 23:00, they must complete a full circuit; trams are not left overnight at the opposite end of the route.)  
Trams on the same line are dispatched in the morning from both ends of the route: half from one end and half from the other (if the number is odd, one extra tram departs from the first end). The trams leave in the morning at equal intervals calculated as the round-trip time (including the dwell time at the loop) divided by the number of trams on that route. Each tram has its own capacity, which is identical for all trams (a parameter provided in the task).

A tram line's route consists of a series of stops. Stops have designated names and a set capacity (common for all stops). There is no distinction between stops for different directions.

## Passenger Behavior

Each passenger lives near a stop. Every morning, at a random time between 6:00 and 12:00 (the time is randomly selected anew each day), the passenger goes to their nearby stop. If the stop is already at full capacity, they give up on traveling for that day; otherwise, they wait for a tram (regardless of which line or direction). 

When a tram arrives, the passenger attempts to board. If successful, they randomly select one of the remaining stops along the route (in one direction), ride to that stop, and then try to alight. If alighting is not possible (because the stop is full), they continue riding, hoping that on a subsequent circuit they will be able to disembark. Once they finally reach a stop, they repeat the process: waiting for a tram, attempting to board, and so on.  
*(For the sake of passenger comfort, it is assumed that passengers bring a thermos and a supply of sandwiches from home or purchase an unhealthy snack from a vending machine while waiting. However, these aspects are omitted in the actual implementation.)*

When a tram arrives at a stop, it first lets off the passengers who intended to get off there (this process may partially fail if the stop is full). The order in which passengers disembark is arbitrary. Then, waiting passengers attempt to board the tram (again, this may partially fail if the tram has reached full capacity).

## Simulation Duration

The simulation runs for a predetermined number of days (as provided in the data). It begins on a Monday (although in this version of the task the day of the week does not affect the simulation).

At the end of each simulated day, all passengers return to their homes (e.g., by walking or taking a taxi, although taxis are not simulated—every morning, all passengers start at home). At night, trams arrive at the appropriate ends of their routes (this is not simulated; it is simply assumed that they are in position the next morning).

### Additional Assumptions
- Vehicles do not operate past midnight. (It is assumed that a route takes less than an hour, and after 23:00 vehicles no longer depart with passengers; at most, they might return to the depot.)

## Program Output

The program should print the following three items:

1. **Input Parameters:** The parameters read from the input.
2. **Detailed Simulation Log:** A line-by-line log of the simulation. Each line should start with the event time (simulation day and clock time) followed by a colon and then a description of the event.  
   _Example:_  
   `46, 15:23: Passenger 213 boarded tram of line 1 (side number 14) with the intention of riding to stop 'Centrum'.`
3. **Simulation Statistics:** At a minimum:
   - The total number of passenger trips (each trip by one passenger on a vehicle is counted individually).
   - The average waiting time at stops (i.e., the average time a passenger waited at a stop).
   - For each simulation day:
     - The total number of trips (counted as above).
     - The total waiting time at stops (sum of waiting times for all passengers).

The results should be printed to standard output (using System.out in Java).

## Simulation Organization

The simulation is managed using an event queue. From the program’s perspective, the timeline is a list of events sorted in non-decreasing order by their occurrence time. Any object that anticipates an action in the future inserts an event (containing itself and the scheduled time) into the queue. The simulation then proceeds by repeatedly extracting the first event (i.e., the one with the earliest occurrence time) from the queue and asking the corresponding object to process it. This continues for the specified duration of the simulation (which is measured in virtual time, not real time) or until the event queue is empty. In our simulation, since no journeys occur at night, the event queue is expected to be empty by the end of each day.

Time in our simulation is measured in virtual minutes (not real minutes) starting from the beginning of the day or simulation (whichever is more convenient; note that in this task, no events cross from one day to the next). We assume a day is approximately 60*24 minutes long.

The event queue can be implemented in various ways. Possible implementations include:
- A sorted array that keeps track of its fill level. The array size should double when additional space is needed for new events.
- A sorted linked list of events (similar to the implementation taught in introductory C programming courses, where each element points to its successor).
- A heap (as commonly used in algorithms and data structures).

In this task, any of these implementations is acceptable (all will be scored equally) as long as they support the following operations: inserting a new event, retrieving the earliest event, and checking whether the queue is empty. It is also required that the program include an interface for the event queue (implemented by your custom queue) so that it can be easily replaced if necessary (only one implementation is required for this task).

Attempting to retrieve an event from an empty queue should cause an assertion to be thrown.

Events may be organized into a class hierarchy.

*Note:* Although the standard Java library provides classes for event queues, this task requires you to implement your own version (choosing one of the implementations mentioned above).
