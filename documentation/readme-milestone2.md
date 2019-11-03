# Milestone 2

## Other Deliverables Included In This Milestone/Release

- Code (including the source and a jar executable)
  - JUnit Tests
- UML and sequence diagrams
- Documentation (javadocs and a user manual that includes design decisions)

## Authors

- Abdalla El Nakla
- Dani Hashweh
- John Breton
- Mohamed Radwan
- Samuel Gamelin

## Changes Since Previous Deliverable

In this milestone, the main feature that has been introduced to the game is the GUI component. The GUI component will enhance the user experience during gameplay. The game now features a board image that shows the user all the game pieces placed on the board. In this milestone, Unit testing has also been introduced. Unit testing is used to test the model component of the game to ensure the game runs smoothly.

The code for the game is designed using the MVC design pattern. During milestone 1 the model component was completed and submitted. In this milestone, the view and controller have also been added. There have also been some modifications to the model based on the TA's suggestions. The modifications made will ensure that delegation, encapsulation, and cohesion are fully used throughout the code. Based on the code review held during the weekly meeting we have also discussed improvements on existing code. These improvements are made to remove code redundancy, duplication and code smell. This will improve the software overall.

The major change in the UML class diagram from milestone 1 is that all text-based command processing has been removed, as that aspect of the game is no longer needed for
any purpose. Board and Piece-inheriting classes no longer need to represent themselves as strings, and as such methods related to this have been removed. The major addition to the class diagram in this milestone is the classes representing the view and controller potions of the game, as the GUI portion of the game must be delivered
for this milestone.

A document containing a user manual and design decisions has also been provided to allow the user
to understand the workings of the game and the reasoning behind the design choices that were made
to implement this GUI-based version of the game.

## Known Issues

- There are currently no known issues with the deliverables for milestone 2.

## Roadmap Ahead

For the next milestone (milestone 3), a solver that uses either
depth-first or breadth-first search to solve a puzzle will have to be implemented. This solver will have
to be integrated into the GUI in terms of hints that can be given to the user. Also, undo/redo features
will have to be provided to the user, allowing them to backtrack as far back as necessary. We will also
have to provide unit tests for any new classes. In addition to this, we will have to pay close attention
to and keep track of any changes we make to the UML delivered in this milestone and explain the reasons
as to why those changes were implemented. As we progress through the next milestone, we will also implement
the feedback given to us concerning this deliverable by the TA who is assigned to our group.
