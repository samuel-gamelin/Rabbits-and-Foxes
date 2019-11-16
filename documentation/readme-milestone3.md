# Milestone 3

## Other Deliverables Included In This Milestone/Release

- Code (including the source and a jar executable)
  - JUnit Tests
- UML and sequence diagrams
- Documentation (javadocs and a user manual that includes design decisions)

## Authors

- [Abdalla El Nakla](mailto:abdallaelnakla@cmail.carleton.ca)
- [Dani Hashweh](mailto:danihashweh@cmail.carleton.ca)
- [John Breton](mailto:johnbreton@cmail.carleton.ca)
- [Mohamed Radwan](mailto:mohamedradwan@cmail.carleton.ca)
- [Samuel Gamelin](mailto:samuelgamelin@cmail.carleton.ca)

## Changes Since Previous Deliverable

In this milestone, the main features that have been introduced to the game are the solver and the move undo/redo. 

The solver component will provide the user with hints to help them progress through the game. The solver uses a breadth-first search algorithm to traverse through all the possible moves within a given board to solve it. The solver can provide all the steps required to pass the level which is then cached. The winning path is cached so the path would only be recalculated when the user moves to a different path than the winning path. The solver component will improve the gameplay for the user since they will be able to progress through the game when they get stuck.

The levels are loaded using a JSON file that has each level as a string representation. The string representation of the level is then broken into a board object for the user to play. This will allow for easier modification for levels and level expansions. During this process, the project has been converted to maven to use an external JSON library. 

The undo and redo functions use two ArrayDeqeue variables to keep track of all the moves made. They are then used to make moves on the board based on the user's choice. The focus of these functions is to allow the user to explore different paths with the pieces in the puzzle. This will prevent the user from having to reset the game every time they want to try a different path.

A document containing a user manual and design decisions has also been provided to allow the user to understand the workings of the game and the reasoning behind the design choices that were made to implement the solver, JSON level representation, and the undo/redo.

## Known Issues

- There are currently no known issues with the deliverables for milestone 3.

## Roadmap Ahead

For the next and final milestone (milestone 4), a level builder that creates levels in a JSON format so that the user can save and load. The solver will then be used to validate the level the user has created.