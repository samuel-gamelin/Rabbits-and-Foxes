## Rabbits and Foxes

This GitHub repository is used for the development of a game based on JumpIN' that follows the MVC design pattern.

## Development

##### Tools

- IDE: [Eclipse](https://www.eclipse.org/downloads/packages/release/2019-09/r/eclipse-ide-java-developers), [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
- JDK Version: [8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- Build Tool: [Maven](https://maven.apache.org/download.cgi)
- UML: [Violet v0.16](http://www.horstmann.com/violet/violet-0.16c.jar)

##### Getting Started

1. From your terminal, run:
   ```
   git clone https://github.com/samuel-gamelin/SYSC-3110
   ```
   This will create a folder called SYSC-3110.

2. Eclipse
    1. Open the Eclipse IDE, and click File -> Import.
    2. Select Maven and then Existing Maven Projects, then click Next.
    3. Click Browse and select the SYSC-3110 folder that was just created. Click Finish.
    
3. IntelliJ IDEA
    1. Open the IntelliJ IDEA IDE, and click File -> Open.
    2. Select the SYSC-3110 folder that was just created and click Open.

4. The entry point of the program is the main method of the MainMenu class under the ui package.
5. The project can be built by invoking a `mvn clean install` command from the project's root directory. This will generate a
runnable jar file in the target directory.

##### Additional Tools

- Contributions are made through GitHub, on this repository.
- GitHub issues are used for task and ticket tracking.

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

## Known issues

Currently there are no known issues.

> If you notice a bug, please add it to Issues tab. Make sure you include how to recreate the bug!

## Documentation

Documentation for the project can be found [here](documentation/Rabbits%20and%20Foxes%20Documentation%20-%20User%20Manual%20and%20Design%20Decisions.pdf).

## The Team

- [Mohamed Radwan](https://github.com/MohamedRadwan)
- [Samuel Gamelin](https://github.com/samuel-gamelin)
- [Dani Hashweh](https://github.com/danihashweh)
- [John Breton](https://github.com/john-breton)
- [Abdalla El Nakla](https://github.com/Abdoltim)

## Credits for Graphical Resources

The pictures and graphical resources used in this game were obtained [here](https://www.smartgames.eu/uk/one-player-games/jumpin).

## License and Disclaimer

> This application is for educational purposes. JumpIN' is a registered commercial product. The developers are not responsible for the distribution of this product.
