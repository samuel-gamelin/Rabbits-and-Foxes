# Milestone 4

## Other Deliverables Included In This Milestone/Release

- Code (including the source and a jar executable) along with JUnit test suites
- UML class and sequence diagrams
- Documentation (javadoc and a user manual that includes design decisions)

## Authors

- [Abdalla El Nakla](mailto:abdallaelnakla@cmail.carleton.ca)
- [Dani Hashweh](mailto:danihashweh@cmail.carleton.ca)
- [John Breton](mailto:johnbreton@cmail.carleton.ca)
- [Mohamed Radwan](mailto:mohamedradwan@cmail.carleton.ca)
- [Samuel Gamelin](mailto:samuelgamelin@cmail.carleton.ca)

## Changes Since Previous Deliverable

In this milestone, the main features that have been introduced to the game are save/load features and a level builder,
along with accompanying tests.

The save and load features allow the user to save and load their progress, whether it is from a default or custom level,
into and from a JSON-formatted file.

The level builder allows the user to manually create their own levels, which they can save and load at any time. These
levels can be loaded (and deleted) from the level selector.

Additional changes in this milestone, which were implemented based on feedback received in milestone 3 include adding a
MovablePiece interface that only Rabbit and Fox implement, avoiding unnecessary code in the Mushroom class, along with
enhanced tests for the solver and tests for the undo/redo functionality.

A document containing a user manual and design decisions has also been provided to allow the user to understand the
workings of the game and the reasoning behind the design choices that were made to implement the level builder along
with the saving and loading of levels.

## Known Issues

- There are currently no known issues with the deliverables for milestone 4.
