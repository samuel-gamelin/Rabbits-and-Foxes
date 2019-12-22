## Rabbits and Foxes

This GitHub repository is used for the development of a game based on JumpIN' that follows the MVC design pattern.

## Development

##### Tools

- IDE: [Eclipse](https://www.eclipse.org/downloads/packages/release/2019-09/r/eclipse-ide-java-developers)
- JDK Version: [8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- UML: [Violet v0.16](http://www.horstmann.com/violet/violet-0.16c.jar)

##### Getting Started

1. From your terminal, run:
   ```
   git clone https://github.com/samuel-gamelin/SYSC-3110
   ```
   This will create a folder called SYSC-3110.
2. Open the Eclipse IDE, and click File -> Import.
3. Select Maven and then "Existing Maven Projects", then click Next.
4. Click Browse and select the SYSC-3110 folder that was just created. Click "Finish".

##### Rules for Contributions:

When contributing to the project please start on the issues page which is used for task tracking.

- Commits:
  - Only commit code to the corresponding milestone.
  - Please document your commits with changes and updates.
  - Add test cases for the updates made.
  - Merging to master will occur once every two weeks under the review of all developers on the project.
- Pull requests:
  - Open a pull request to the corresponding milestone.
  - Document your code.
  - If there are any conflicts during the merge please consult any developer on the team.
- Issues:
  - Issues will be used for task tracking.
  - When an issue is solved please document the changes that have been made.
  - Close the issue after you have committed your code.

##### Additional Tools

- Contributions are made through GitHub, on this repository.
  - Please download and install [git](https://git-scm.com/) and/or [GitHub Desktop](https://desktop.github.com/).
- Slack is used as the main chat for this project.
- GitHub issues will be used for task and ticket tracking.

## Current Class Diagram

<p style="text-align:right">
<img src="documentation/uml/classDiagram.png" alt="Class Diagram">
</p>
Date: December 2, 2019

## Current Sequence Diagram

<p style="text-align:right">
<img src="documentation/uml/sequenceDiagram.png" alt="Sequence Diagram">
</p>
Date: December 2, 2019

## Road Map

- Adding a level editor and save/load functionality
  - Adding the ability for users to creates their own levels via a GUI
  - Incorporating the solver to ensure that custom levels are solvable
  - Achieving saving and loading via serialization.
  - Updating and adding unit tests for all new features
  - Continuing to implement gradual improvements
  - Continuing to document the development and design process

## Known issues

Currently there are no none issues.

> If you notice a bug, please add it to Issues tab. Make sure you include how to recreate the bug!

## New features

- Milestone 4:
    - Save and load features
        - These features use JSON serialization
    - A level builder used to create and save custom levels 
    - A level selector screen, allowing the user to attempt any default or custom level
    - A dark theme, which is enabled everywhere in the game

## The Team

- [Mohamed Radwan](https://github.com/MohamedRadwan)
- [Samuel Gamelin](https://github.com/samuel-gamelin)
- [Dani Hashweh](https://github.com/danihashweh)
- [John Breton](https://github.com/john-breton)
- [Abdalla El Nakla](https://github.com/Abdoltim)

## Documentation

Documentation for the project can be found [here](https://docs.google.com/document/d/1F1drMjR9mFtCsQivzpvqP5nMX2gI0osJu4_xSTUs74g/edit?usp=sharing).

## Credits for Graphical Resources

The pictures and graphical resources used in this game were obtained from https://www.smartgames.eu/uk/one-player-games/jumpin.

## License and Disclaimer

> This application is for educational purposes. JumpIN' is a registered commercial product. The developers are not responsible for the distribution of this product.
